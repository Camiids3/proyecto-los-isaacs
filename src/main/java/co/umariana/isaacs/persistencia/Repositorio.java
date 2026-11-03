package co.umariana.isaacs.persistencia;

import co.umariana.isaacs.modelo.Tema;

import java.io.IOException;
import java.util.List;

/** Contrato de la persistencia. NO se modifica. {@link RepositorioTxt} es la implementación. */
public interface Repositorio {

    /** Lee todos los temas guardados. Si todavía no hay nada guardado, devuelve una lista vacía. */
    List<Tema> cargar() throws IOException;

    /** Guarda TODOS los temas, reemplazando lo que hubiera antes. */
    void guardar(List<Tema> temas) throws IOException;
}
