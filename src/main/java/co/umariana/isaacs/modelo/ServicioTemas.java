package co.umariana.isaacs.modelo;

import java.util.List;
import java.util.Map;

/**
 * Lo que la aplicación web necesita del modelo. NO se modifica.
 * {@link GestorTemas} es la implementación (tarea de MODELO).
 */
public interface ServicioTemas {

    /** Valida el tema, rechaza códigos repetidos y guarda el cambio en el archivo. */
    void agregar(Tema tema);

    /** Devuelve el tema con ese código, o {@code null} si no existe. */
    Tema buscar(String codigo);

    /** Elimina el tema con ese código y guarda el cambio. Devuelve {@code false} si no existía. */
    boolean eliminar(String codigo);

    /** Todos los temas, en el orden de la lista. */
    List<Tema> listar();

    /** Lista NUEVA ordenada de mayor a menor ritmo (empate: por código). No altera el orden de la lista. */
    List<Tema> ranking();

    /** Promedio de ritmo por categoría, ordenado alfabéticamente por categoría. */
    Map<String, Double> promedioPorCategoria();

    /** Cantidad de temas guardados. */
    int total();

    /** Tema donde está el cursor (el reproductor), o {@code null} si no hay temas. */
    Tema actual();

    /** Mueve el cursor al siguiente tema y lo devuelve ({@code null} si no hay temas). */
    Tema siguiente();

    /** Mueve el cursor al tema anterior y lo devuelve ({@code null} si no hay temas). */
    Tema anterior();
}
