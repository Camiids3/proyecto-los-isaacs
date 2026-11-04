package co.umariana.isaacs.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * POST /eliminar  (tarea de SERVLETS)
 *
 * <p>Contrato: toma el parámetro {@code codigo}, llama a {@code Contexto.servicio().eliminar(codigo)}
 * y redirige a {@code "listar"}.
 */
@WebServlet("/eliminar")
public class EliminarTema extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codigo = req.getParameter("codigo");
        Contexto.servicio().eliminar(codigo);
        resp.sendRedirect("listar");
    }
}