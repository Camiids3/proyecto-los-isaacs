package co.umariana.isaacs.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.umariana.isaacs.modelo.ServicioTemas;
import co.umariana.isaacs.modelo.Tema;

/**
 * GET /reproductor  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li>Parámetro {@code accion}: {@code "siguiente"} usa {@code Contexto.servicio().siguiente()},
 *       {@code "anterior"} usa {@code anterior()}; sin parámetro (o con otro valor) usa {@code actual()}.</li>
 *   <li>Pone el tema resultante (puede ser {@code null}) en el atributo {@code "tema"} y la cantidad
 *       total en {@code "total"}.</li>
 *   <li>Reenvía a {@code /WEB-INF/vistas/reproductor.jsp}, que debe mostrar el tema, un reproductor
 *       {@code <audio>} (los archivos están en {@code media/}) y los botones Anterior / Siguiente.</li>
 * </ul>
 */
@WebServlet("/reproductor")
public class Reproductor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioTemas servicio = Contexto.servicio();
        String accion = req.getParameter("accion");

        // Se mueve el cursor según la acción; sin acción (o una desconocida) se queda donde está.
        Tema tema;
        if ("siguiente".equals(accion)) {
            tema = servicio.siguiente();
        } else if ("anterior".equals(accion)) {
            tema = servicio.anterior();
        } else {
            tema = servicio.actual();
        }

        req.setAttribute("tema", tema);
        req.setAttribute("total", servicio.total());
        req.getRequestDispatcher("/WEB-INF/vistas/reproductor.jsp").forward(req, resp);
    }
}