package co.umariana.isaacs.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.umariana.isaacs.modelo.Tema;

/**
 * /registrar  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li><b>GET:</b> reenvía a {@code /WEB-INF/vistas/formulario.jsp}.</li>
 *   <li><b>POST:</b> lee los parámetros {@code codigo, titulo, categoria, ritmo, duracion,
 *       descripcion, archivo}; crea un {@code Tema} (el ritmo es un entero y la duración un
 *       decimal) y llama a {@code Contexto.servicio().agregar(...)}. Si todo sale bien, redirige a
 *       {@code "listar"} ({@code resp.sendRedirect("listar")}).</li>
 *   <li><b>Si algo falla</b> ({@link NumberFormatException} o {@link IllegalArgumentException}), NO redirige:
 *       pone el mensaje de la excepción en el atributo {@code "error"} y reenvía de nuevo a
 *       {@code formulario.jsp}, que debe mostrar ese error.</li>
 * </ul>
 */
@WebServlet("/registrar")
public class RegistrarTema extends HttpServlet {

    private static final String FORMULARIO = "/WEB-INF/vistas/formulario.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(FORMULARIO).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // para que las tildes y la ñ lleguen bien desde el formulario

        String codigo = req.getParameter("codigo");
        String titulo = req.getParameter("titulo");
        String categoria = req.getParameter("categoria");
        String textoRitmo = req.getParameter("ritmo");
        String textoDuracion = req.getParameter("duracion");
        String descripcion = req.getParameter("descripcion");
        String archivo = req.getParameter("archivo");

        try {
            int ritmo = Integer.parseInt(textoRitmo.trim());
            double duracion = Double.parseDouble(textoDuracion.trim());

            Tema tema = new Tema(codigo, titulo, categoria, ritmo, duracion, descripcion, archivo);
            Contexto.servicio().agregar(tema); // el modelo valida y rechaza códigos repetidos

            resp.sendRedirect("listar");
        } catch (NumberFormatException e) {
            // NumberFormatException es hija de IllegalArgumentException: se atiende primero
            // para dar un mensaje más claro que el de Java ("For input string: ...").
            mostrarError(req, resp, "El ritmo debe ser un número entero y la duración un número decimal.");
        } catch (IllegalArgumentException e) {
            mostrarError(req, resp, e.getMessage());
        } catch (NullPointerException e) {
            // Falta el campo ritmo o duración en la petición.
            mostrarError(req, resp, "El ritmo y la duración son obligatorios.");
        }
    }

    /** Pone el mensaje en el atributo "error" y vuelve a mostrar el formulario (sin redirigir). */
    private void mostrarError(HttpServletRequest req, HttpServletResponse resp, String mensaje)
            throws ServletException, IOException {
        req.setAttribute("error", mensaje);
        req.getRequestDispatcher(FORMULARIO).forward(req, resp);
    }
}