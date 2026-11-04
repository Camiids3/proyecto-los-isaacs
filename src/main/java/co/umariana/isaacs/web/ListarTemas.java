package co.umariana.isaacs.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.umariana.isaacs.modelo.ServicioTemas;
import co.umariana.isaacs.modelo.Tema;

/**
 * GET /listar  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li>Si el parámetro {@code orden} vale {@code "ranking"}, el atributo {@code "temas"} de la petición
 *       es {@code Contexto.servicio().ranking()}; en cualquier otro caso es {@code listar()}.</li>
 *   <li>Siempre pone el atributo {@code "promedios"} = {@code promedioPorCategoria()}.</li>
 *   <li>Reenvía (forward) a {@code /WEB-INF/vistas/listar.jsp}, que debe mostrar la tabla de temas
 *       (con un botón para eliminar cada uno) y los promedios.</li>
 * </ul>
 */
@WebServlet("/listar")
public class ListarTemas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioTemas servicio = Contexto.servicio();

        // Con ?orden=ranking se muestra el ranking; con cualquier otro valor (o sin él), la lista normal.
        List<Tema> temas;
        if ("ranking".equals(req.getParameter("orden"))) {
            temas = servicio.ranking();
        } else {
            temas = servicio.listar();
        }

        req.setAttribute("temas", temas);
        req.setAttribute("promedios", servicio.promedioPorCategoria());
        req.getRequestDispatcher("/WEB-INF/vistas/listar.jsp").forward(req, resp);
    }
}