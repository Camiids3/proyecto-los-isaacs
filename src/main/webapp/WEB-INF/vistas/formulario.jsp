<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.umariana.isaacs.web.Html" %>
<%!
    /** Devuelve el parámetro ya escapado (o vacío) para no perder lo escrito cuando hay un error. */
    private static String valor(HttpServletRequest req, String nombre) {
        String v = req.getParameter(nombre);
        return v == null ? "" : Html.esc(v);
    }
%>
<%
    // Atributo que pone el servlet RegistrarTema cuando algo falla
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar tema · Isaacsfy</title>
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
    <a href="registrar" class="activo">Registrar</a>
    <a href="reproductor">Reproductor</a>
</nav>

<main>
    <h2>Registrar un tema</h2>

    <% if (error != null) { %>
        <p class="error" role="alert">✖ <%= Html.esc(error) %></p>
    <% } %>

    <form class="formulario" method="post" action="registrar">
        <div class="fila">
            <div class="campo">
                <label for="codigo">Código</label>
                <input type="text" id="codigo" name="codigo" required placeholder="T004"
                       value="<%= valor(request, "codigo") %>">
            </div>
            <div class="campo">
                <label for="titulo">Título</label>
                <input type="text" id="titulo" name="titulo" required
                       value="<%= valor(request, "titulo") %>">
            </div>
        </div>

        <div class="campo">
            <label for="categoria">Categoría</label>
            <input type="text" id="categoria" name="categoria" required placeholder="Electrónica"
                   value="<%= valor(request, "categoria") %>">
        </div>

        <div class="fila">
            <div class="campo">
                <label for="ritmo">Ritmo</label>
                <input type="number" id="ritmo" name="ritmo" min="1" max="10" step="1" required
                       value="<%= valor(request, "ritmo") %>">
                <small>Un número entero de 1 a 10.</small>
            </div>
            <div class="campo">
                <label for="duracion">Duración (segundos)</label>
                <input type="number" id="duracion" name="duracion" min="0.1" step="0.1" required
                       value="<%= valor(request, "duracion") %>">
                <small>Por ejemplo, 125.3</small>
            </div>
        </div>

        <div class="campo">
            <label for="descripcion">Descripción</label>
            <textarea id="descripcion" name="descripcion" rows="3"><%= valor(request, "descripcion") %></textarea>
            <small>Opcional.</small>
        </div>

        <div class="campo">
            <label for="archivo">Archivo de audio</label>
            <input type="text" id="archivo" name="archivo" list="audios" required placeholder="springs.mp3"
                   value="<%= valor(request, "archivo") %>">
            <datalist id="audios">
                <option value="gabriawll.mp3">
                <option value="whatdoyousee.mp3">
                <option value="springs.mp3">
            </datalist>
            <small>Nombre de un archivo de la carpeta <code>media/</code>.</small>
        </div>

        <div>
            <button type="submit">Guardar tema</button>
            <a class="boton secundario" href="listar">Cancelar</a>
        </div>
    </form>
</main>

<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
