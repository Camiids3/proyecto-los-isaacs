<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.umariana.isaacs.modelo.Tema, co.umariana.isaacs.web.Html" %>
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
<nav>
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="listar">Temas</a>
    <a href="registrar">Registrar</a>
    <a href="reproductor" class="activo">Reproductor</a>
</nav>

<main>
    <h2>Reproductor</h2>

    <% if (tema == null) { %>
        <p class="vacio">
            No hay temas para reproducir.
            Agrega alguno desde el <a href="catalogo">Catálogo</a> o <a href="registrar">regístralo a mano</a>.
        </p>
    <% } else { %>
        <section class="reproductor">
            <div class="disco">💿</div>
            <h2><%= Html.esc(tema.getTitulo()) %></h2>
            <p class="datos">
                <%= Html.esc(tema.getCategoria()) %> ·
                Ritmo <%= tema.getRitmo() %>/10 ·
                <%= formatoDuracion(tema.getDuracionSeg()) %>
                <% if (total != null) { %> · <%= total %> temas en la lista<% } %>
            </p>
            <% if (tema.getDescripcion() != null && !tema.getDescripcion().isEmpty()) { %>
                <p><%= Html.esc(tema.getDescripcion()) %></p>
            <% } %>

            <audio id="audio" controls preload="metadata" src="media/<%= Html.esc(tema.getArchivo()) %>">
                Tu navegador no soporta el elemento de audio.
            </audio>

            <div class="controles">
                <a class="boton secundario" href="reproductor?accion=anterior">⏮ Anterior</a>
                <a class="boton secundario" href="reproductor?accion=siguiente">Siguiente ⏭</a>
            </div>
        </section>

        <script>
            // Al pulsar Anterior/Siguiente la página se recarga: se intenta seguir reproduciendo.
            // Si el navegador lo bloquea, simplemente se queda en pausa.
            var audio = document.getElementById("audio");
            if (audio && location.search.indexOf("accion=") !== -1) {
                audio.play().catch(function () { });
            }
        </script>
    <% } %>
</main>

<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
