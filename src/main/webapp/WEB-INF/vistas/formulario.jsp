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
    String ritmo = valor(request, "ritmo");
    if (ritmo.isEmpty()) { ritmo = "5"; }
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
<nav><div class="interior">
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="listar">Temas</a>
    <a href="registrar" class="activo">Registrar</a>
    <a href="reproductor">Reproductor</a>
</div></nav>

<main>
    <h2>➕ Registrar un tema</h2>

    <% if (error != null) { %>
        <p class="error" role="alert">✖ <%= Html.esc(error) %></p>
        <br>
    <% } %>

    <div class="formulario-grid">
        <form class="formulario" id="formulario" method="post" action="registrar">
            <div class="fila">
                <div class="campo">
                    <label for="codigo">Código</label>
                    <input type="text" id="codigo" name="codigo" required placeholder="T004" autocomplete="off"
                           value="<%= valor(request, "codigo") %>">
                </div>
                <div class="campo">
                    <label for="titulo">Título</label>
                    <input type="text" id="titulo" name="titulo" required placeholder="Nombre de la canción"
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
                    <div class="ritmo-valor">
                        <label for="ritmo">Ritmo</label>
                        <output id="ritmoValor"><%= ritmo %></output>
                    </div>
                    <input type="range" id="ritmo" name="ritmo" min="1" max="10" step="1" value="<%= ritmo %>">
                    <small id="ritmoTexto"></small>
                </div>
                <div class="campo">
                    <label for="duracion">Duración (segundos)</label>
                    <input type="number" id="duracion" name="duracion" min="0.1" step="0.1" required placeholder="125.3"
                           value="<%= valor(request, "duracion") %>">
                    <small id="duracionTexto">Por ejemplo, 125.3</small>
                </div>
            </div>

            <div class="campo">
                <label for="descripcion">Descripción</label>
                <textarea id="descripcion" name="descripcion" rows="3" maxlength="200"><%= valor(request, "descripcion") %></textarea>
                <small class="contador"><span id="contador">0</span>/200 · opcional</small>
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
                <div class="chips">
                    <button type="button" class="chip" data-archivo="gabriawll.mp3">🎵 gabriawll.mp3</button>
                    <button type="button" class="chip" data-archivo="whatdoyousee.mp3">🎵 whatdoyousee.mp3</button>
                    <button type="button" class="chip" data-archivo="springs.mp3">🎵 springs.mp3</button>
                </div>
                <small>Nombre de un archivo de la carpeta <code>media/</code>. Toca uno para llenarlo.</small>
            </div>

            <div>
                <button type="submit" id="guardar">💾 Guardar tema</button>
                <a class="boton secundario" href="listar">Cancelar</a>
            </div>
        </form>

        <aside class="vista-previa">
            <small>Vista previa</small>
            <div class="caratula">💿</div>
            <h4 id="pvTitulo">Título del tema</h4>
            <p id="pvCategoria">Categoría</p>
            <p id="pvDatos"></p>
            <p id="pvArchivo"></p>
        </aside>
    </div>
</main>

<footer>Proyecto final · Estructuras de Datos I</footer>

<script>
    (function () {
        var $ = function (id) { return document.getElementById(id); };
        var textos = ["Muy tranquilo", "Tranquilo", "Suave", "Relajado", "Medio",
                      "Animado", "Enérgico", "Intenso", "Muy intenso", "Máxima energía"];

        function minutos(seg) {
            var total = Math.round(seg);
            var s = total % 60;
            return Math.floor(total / 60) + ":" + (s < 10 ? "0" : "") + s;
        }

        function actualizar() {
            var ritmo = parseInt($("ritmo").value, 10) || 5;
            var duracion = parseFloat($("duracion").value);
            $("ritmoValor").textContent = ritmo;
            $("ritmoTexto").textContent = textos[ritmo - 1] || "";
            $("duracionTexto").textContent = isNaN(duracion) ? "Por ejemplo, 125.3" : "Son " + minutos(duracion) + " (min:seg)";
            $("contador").textContent = $("descripcion").value.length;

            $("pvTitulo").textContent = $("titulo").value || "Título del tema";
            $("pvCategoria").textContent = $("categoria").value || "Categoría";
            $("pvDatos").textContent = "Ritmo " + ritmo + "/10" + (isNaN(duracion) ? "" : " · " + minutos(duracion));
            $("pvArchivo").textContent = $("archivo").value ? "🎵 " + $("archivo").value : "";
        }

        ["codigo", "titulo", "categoria", "ritmo", "duracion", "descripcion", "archivo"].forEach(function (id) {
            $(id).addEventListener("input", actualizar);
        });

        document.querySelectorAll(".chip").forEach(function (chip) {
            chip.addEventListener("click", function () {
                $("archivo").value = chip.getAttribute("data-archivo");
                actualizar();
            });
        });

        $("formulario").addEventListener("submit", function () {
            var boton = $("guardar");
            boton.disabled = true;
            boton.textContent = "Guardando...";
        });

        actualizar();
    })();
</script>
</body>
</html>
