<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Locale, java.util.Map, co.umariana.isaacs.modelo.Tema, co.umariana.isaacs.web.Html" %>
<%!
    /** Convierte segundos a m:ss (ej.: 125.3 -> 2:05). */
    private static String formatoDuracion(double segundos) {
        int total = (int) Math.round(segundos);
        return (total / 60) + ":" + String.format("%02d", total % 60);
    }
%>
<%
    // Atributos que pone el servlet ListarTemas
    @SuppressWarnings("unchecked")
    List<Tema> temas = (List<Tema>) request.getAttribute("temas");
    @SuppressWarnings("unchecked")
    Map<String, Double> promedios = (Map<String, Double>) request.getAttribute("promedios");
    boolean esRanking = "ranking".equals(request.getParameter("orden"));

    // Resumen para las tarjetas de arriba
    int cantidad = (temas == null) ? 0 : temas.size();
    double sumaRitmo = 0, sumaDuracion = 0;
    if (temas != null) {
        for (Tema t : temas) { sumaRitmo += t.getRitmo(); sumaDuracion += t.getDuracionSeg(); }
    }
    double ritmoGeneral = cantidad == 0 ? 0 : sumaRitmo / cantidad;
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Temas · Isaacsfy</title>
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
    <a href="listar" class="activo">Temas</a>
    <a href="registrar">Registrar</a>
    <a href="reproductor">Reproductor</a>
</div></nav>

<main>
    <h2><%= esRanking ? "🏆 Ranking por ritmo" : "📋 Temas guardados" %></h2>

    <div class="resumen">
        <div class="dato"><b><%= cantidad %></b><span>temas</span></div>
        <div class="dato"><b><%= String.format(Locale.US, "%.1f", ritmoGeneral) %></b><span>ritmo promedio</span></div>
        <div class="dato"><b><%= formatoDuracion(sumaDuracion) %></b><span>duración total</span></div>
    </div>

    <div class="barra-herramientas">
        <div class="orden">
            <a class="boton <%= esRanking ? "secundario" : "" %>" href="listar">📋 Lista</a>
            <a class="boton <%= esRanking ? "" : "secundario" %>" href="listar?orden=ranking">🏆 Ranking</a>
        </div>
        <div class="buscador">
            <input type="search" id="buscar" placeholder="🔎 Buscar por código, título o categoría..." autocomplete="off">
        </div>
    </div>

    <% if (temas == null || temas.isEmpty()) { %>
        <p class="vacio">
            Todavía no hay temas guardados.<br>
            Agrégalos desde el <a href="catalogo">Catálogo</a> o <a href="registrar">regístralos a mano</a>.
        </p>
    <% } else { %>
        <div class="tabla-scroll">
            <table id="tabla">
                <thead>
                    <tr>
                        <% if (esRanking) { %><th>#</th><% } %>
                        <th>Código</th>
                        <th>Título</th>
                        <th>Categoría</th>
                        <th>Ritmo</th>
                        <th>Duración</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                <% int puesto = 0; %>
                <% for (Tema t : temas) { puesto++; %>
                    <tr style="--i: <%= puesto %>"
                        data-texto="<%= Html.esc((t.getCodigo() + " " + t.getTitulo() + " " + t.getCategoria()).toLowerCase()) %>">
                        <% if (esRanking) { %>
                            <td><span class="puesto <%= puesto <= 3 ? "p" + puesto : "" %>"><%= puesto %></span></td>
                        <% } %>
                        <td class="codigo"><%= Html.esc(t.getCodigo()) %></td>
                        <td class="titulo"><%= Html.esc(t.getTitulo()) %></td>
                        <td><span class="etiqueta"><%= Html.esc(t.getCategoria()) %></span></td>
                        <td>
                            <span class="barra"><span style="width:<%= t.getRitmo() * 10 %>%;background:hsl(<%= 160 - t.getRitmo() * 14 %>,80%,55%)"></span></span>
                            <%= t.getRitmo() %>/10
                        </td>
                        <td><%= formatoDuracion(t.getDuracionSeg()) %></td>
                        <td>
                            <form method="post" action="eliminar" class="form-eliminar">
                                <input type="hidden" name="codigo" value="<%= Html.esc(t.getCodigo()) %>">
                                <button type="submit" class="peligro">🗑 Eliminar</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <p class="vacio" id="sinResultados" style="display:none; margin-top:1rem">
            Ningún tema coincide con tu búsqueda.
        </p>
    <% } %>

    <h3>Ritmo promedio por categoría</h3>
    <% if (promedios == null || promedios.isEmpty()) { %>
        <p class="vacio">Sin datos todavía.</p>
    <% } else { %>
        <ul class="promedios">
            <% for (Map.Entry<String, Double> e : promedios.entrySet()) { %>
                <li>
                    <%= Html.esc(e.getKey()) %>
                    <strong><%= String.format(Locale.US, "%.2f", e.getValue()) %></strong>
                    <span class="barra"><span style="width:<%= Math.round(e.getValue() * 10) %>%;background:hsl(<%= Math.round(160 - e.getValue() * 14) %>,80%,55%)"></span></span>
                </li>
            <% } %>
        </ul>
    <% } %>
</main>

<footer>Proyecto final · Estructuras de Datos I</footer>

<script>
    // Búsqueda en vivo sobre la tabla
    (function () {
        var buscador = document.getElementById("buscar");
        var filas = document.querySelectorAll("#tabla tbody tr");
        var aviso = document.getElementById("sinResultados");
        if (!buscador || filas.length === 0) { return; }
        buscador.addEventListener("input", function () {
            var q = buscador.value.toLowerCase().trim();
            var visibles = 0;
            filas.forEach(function (fila) {
                var coincide = fila.getAttribute("data-texto").indexOf(q) !== -1;
                fila.style.display = coincide ? "" : "none";
                if (coincide) { visibles++; }
            });
            aviso.style.display = visibles === 0 ? "" : "none";
        });
    })();

    // Confirmar antes de eliminar
    document.querySelectorAll(".form-eliminar").forEach(function (form) {
        form.addEventListener("submit", function (ev) {
            if (!confirm("¿Eliminar este tema?")) { ev.preventDefault(); return; }
            var boton = form.querySelector("button");
            boton.disabled = true;
            boton.textContent = "Eliminando...";
        });
    });
</script>
</body>
</html>
