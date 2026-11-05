<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Locale, co.umariana.isaacs.modelo.Tema, co.umariana.isaacs.web.Html" %>
<%!
    /** Convierte segundos a m:ss (ej.: 125.3 -> 2:05). */
    private static String formatoDuracion(double segundos) {
        int total = (int) Math.round(segundos);
        return (total / 60) + ":" + String.format("%02d", total % 60);
    }
%>
<%
    // Atributos que pone el servlet Reproductor ("tema" puede ser null si no hay temas)
    Tema tema = (Tema) request.getAttribute("tema");
    Object total = request.getAttribute("total");

    // Entre más ritmo tiene el tema, más rápido se mueve el ecualizador (de 1.2 s a 0.3 s)
    double velocidad = 1.3;
    if (tema != null) { velocidad = Math.max(0.25, 1.3 - tema.getRitmo() * 0.1); }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reproductor · Isaacsfy</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<header>
    <h1>🎧 Isaacsfy</h1>
    <p>La lista de reproducción oficial de Los Isaacs</p>
</header>
<nav><div class="interior">
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="listar">Temas</a>
    <a href="registrar">Registrar</a>
    <a href="reproductor" class="activo">Reproductor</a>
</div></nav>

<main>
    <h2>▶️ Reproductor</h2>

    <% if (tema == null) { %>
        <p class="vacio">
            No hay temas para reproducir.<br>
            Agrega alguno desde el <a href="catalogo">Catálogo</a> o <a href="registrar">regístralo a mano</a>.
        </p>
    <% } else { %>
        <section class="reproductor" id="reproductor">
            <div class="disco"><span>🎵</span></div>
            <h2><%= Html.esc(tema.getTitulo()) %></h2>
            <p class="datos">
                <span class="etiqueta"><%= Html.esc(tema.getCategoria()) %></span> ·
                Ritmo <%= tema.getRitmo() %>/10 ·
                <%= formatoDuracion(tema.getDuracionSeg()) %>
                <% if (total != null) { %> · <%= total %> temas en la lista<% } %>
            </p>
            <% if (tema.getDescripcion() != null && !tema.getDescripcion().isEmpty()) { %>
                <p class="descripcion"><%= Html.esc(tema.getDescripcion()) %></p>
            <% } %>

            <div class="ecualizador" style="--vel: <%= String.format(Locale.US, "%.2f", velocidad) %>s" aria-hidden="true">
                <i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i>
            </div>

            <audio id="audio" controls preload="metadata" src="media/<%= Html.esc(tema.getArchivo()) %>">
                Tu navegador no soporta el elemento de audio.
            </audio>

            <div class="controles">
                <a class="boton secundario" id="anterior" href="reproductor?accion=anterior">⏮ Anterior</a>
                <a class="boton principal" id="alternar" href="#" role="button">▶ Reproducir</a>
                <a class="boton secundario" id="siguiente" href="reproductor?accion=siguiente">Siguiente ⏭</a>
            </div>

            <p class="atajos">
                Atajos: <kbd>←</kbd> anterior · <kbd>Espacio</kbd> reproducir/pausar · <kbd>→</kbd> siguiente
            </p>
        </section>

        <script>
            (function () {
                var audio = document.getElementById("audio");
                var caja = document.getElementById("reproductor");
                var alternar = document.getElementById("alternar");

                function mostrarEstado() {
                    var sonando = !audio.paused && !audio.ended;
                    caja.classList.toggle("sonando", sonando);
                    alternar.textContent = sonando ? "⏸ Pausar" : "▶ Reproducir";
                }

                function reproducirOPausar() {
                    if (audio.paused) {
                        audio.play().catch(function () { });
                    } else {
                        audio.pause();
                    }
                }

                audio.addEventListener("play", mostrarEstado);
                audio.addEventListener("pause", mostrarEstado);
                audio.addEventListener("ended", mostrarEstado);

                alternar.addEventListener("click", function (ev) {
                    ev.preventDefault();
                    reproducirOPausar();
                });

            
                document.addEventListener("keydown", function (ev) {
                    if (ev.target.tagName === "INPUT" || ev.target.tagName === "TEXTAREA") { return; }
                    if (ev.key === "ArrowRight") {
                        location.href = "reproductor?accion=siguiente";
                    } else if (ev.key === "ArrowLeft") {
                        location.href = "reproductor?accion=anterior";
                    } else if (ev.key === " " && ev.target.tagName !== "AUDIO" && ev.target.tagName !== "A") {
                        ev.preventDefault();
                        reproducirOPausar();
                    }
                });

            
                if (location.search.indexOf("accion=") !== -1) {
                    audio.play().catch(function () { });
                }
                mostrarEstado();
            })();
        </script>
    <% } %>
</main>

<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>