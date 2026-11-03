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
<nav>
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="listar" class="activo">Temas</a>
    <a href="registrar">Registrar</a>
    <a href="reproductor">Reproductor</a>
</nav>

<main>
    <h2><%= esRanking ? "Ranking por ritmo" : "Temas guardados" %></h2>

    <div class="orden">
        <a class="boton <%= esRanking ? "secundario" : "" %>" href="listar">Orden de la lista</a>
        <a class="boton <%= esRanking ? "" : "secundario" %>" href="listar?orden=ranking">Ranking por ritmo</a>
    </div>

    <% if (temas == null || temas.isEmpty()) { %>
        <p class="vacio">
            Todavía no hay temas guardados.
            Agrégalos desde el <a href="catalogo">Catálogo</a> o <a href="registrar">regístralos a mano</a>.
        </p>
    <% } else { %>
        <div class="tabla-scroll">
            <table>
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
                    <tr>
                        <% if (esRanking) { %><td><%= puesto %></td><% } %>
                        <td><%= Html.esc(t.getCodigo()) %></td>
                        <td><%= Html.esc(t.getTitulo()) %></td>
                        <td><%= Html.esc(t.getCategoria()) %></td>
                        <td>
                            <meter min="1" max="10" value="<%= t.getRitmo() %>"></meter>
                            <%= t.getRitmo() %>/10
                        </td>
                        <td><%= formatoDuracion(t.getDuracionSeg()) %></td>
                        <td>
                            <form method="post" action="eliminar"
                                  onsubmit="return confirm('¿Eliminar este tema?');">
                                <input type="hidden" name="codigo" value="<%= Html.esc(t.getCodigo()) %>">
                                <button type="submit" class="peligro">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    <% } %>

    <h2 style="margin-top:2rem">Ritmo promedio por categoría</h2>
    <% if (promedios == null || promedios.isEmpty()) { %>
        <p class="vacio">Sin datos todavía.</p>
    <% } else { %>
        <ul class="promedios">
            <% for (Map.Entry<String, Double> e : promedios.entrySet()) { %>
                <li>
                    <%= Html.esc(e.getKey()) %>:
                    <strong><%= String.format(Locale.US, "%.2f", e.getValue()) %></strong>
                </li>
            <% } %>
        </ul>
    <% } %>
</main>

<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
