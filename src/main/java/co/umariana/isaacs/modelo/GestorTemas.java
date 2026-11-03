package co.umariana.isaacs.modelo;

import co.umariana.isaacs.estructura.Lista;
import co.umariana.isaacs.persistencia.Repositorio;

import java.util.List;
import java.util.Map;

/**
 * Reglas de negocio del Isaacsfy (tarea de MODELO).
 *
 * <p>Guarda los temas en una {@link Lista} (la estructura de datos del equipo) y, después de
 * cada cambio, los guarda con el {@link Repositorio}. No debe usar {@code ArrayList} ni otra
 * colección de Java para guardar los datos: eso lo hace la {@code Lista}.
 */
public class GestorTemas implements ServicioTemas {

    private final Lista<Tema> lista;
    private final Repositorio repositorio;

    /**
     * Al crearse, carga con el repositorio los temas ya guardados y los agrega a la lista.
     * Si el repositorio lanza {@link java.io.IOException}, se relanza como {@link java.io.UncheckedIOException}.
     */
    public GestorTemas(Lista<Tema> lista, Repositorio repositorio) {
        this.lista = lista;
        this.repositorio = repositorio;
        // TODO (MODELO): cargar los temas del repositorio y agregarlos a la lista.
    }

    @Override
    public void agregar(Tema tema) {
        // TODO (MODELO): validar (tema.validar()), rechazar código repetido
        //  (IllegalArgumentException), agregar al final de la lista y guardar con el repositorio.
        throw new UnsupportedOperationException("TODO: agregar()");
    }

    @Override
    public Tema buscar(String codigo) {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: buscar()");
    }

    @Override
    public boolean eliminar(String codigo) {
        // TODO (MODELO): si existe, eliminarlo de la lista, guardar y devolver true.
        throw new UnsupportedOperationException("TODO: eliminar()");
    }

    @Override
    public List<Tema> listar() {
        // TODO (MODELO): devolver los temas en el orden de la lista.
        throw new UnsupportedOperationException("TODO: listar()");
    }

    @Override
    public List<Tema> ranking() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: ranking()");
    }

    @Override
    public Map<String, Double> promedioPorCategoria() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: promedioPorCategoria()");
    }

    @Override
    public int total() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: total()");
    }

    @Override
    public Tema actual() {
        // TODO (MODELO): usar el cursor de la lista.
        throw new UnsupportedOperationException("TODO: actual()");
    }

    @Override
    public Tema siguiente() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: siguiente()");
    }

    @Override
    public Tema anterior() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: anterior()");
    }
}
