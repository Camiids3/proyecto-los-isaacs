package co.umariana.isaacs.web;

import co.umariana.isaacs.estructura.ListaDoble;
import co.umariana.isaacs.modelo.GestorTemas;
import co.umariana.isaacs.modelo.Tema;
import co.umariana.isaacs.persistencia.RepositorioTxt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;

/** Arma el modelo al arrancar el servidor (lista + archivo + gestor). NO se modifica. */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        Path archivo = Path.of(System.getProperty("temas.archivo", "temas.txt"));
        try {
            Contexto.usar(new GestorTemas(new ListaDoble<Tema>(), new RepositorioTxt(archivo)));
            evento.getServletContext().log("Isaacsfy listo. Datos en: " + archivo.toAbsolutePath());
        } catch (Throwable e) {
            // Mientras el equipo no termine sus clases, la aplicación arranca igual pero sin modelo.
            evento.getServletContext().log("El modelo todavía no está disponible: " + e);
            Contexto.usar(null);
        }
    }
}
