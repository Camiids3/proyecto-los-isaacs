package co.umariana.isaacs.modelo;

import co.umariana.isaacs.estructura.Lista;
import co.umariana.isaacs.persistencia.Repositorio;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GestorTemas implements ServicioTemas {

    private final Lista<Tema> lista;
    private final Repositorio repositorio;

    public GestorTemas(Lista<Tema> lista, Repositorio repositorio) {
        this.lista = lista;
        this.repositorio = repositorio;
        try {
            List<Tema> guardados = repositorio.cargar();
            for (Tema t : guardados) {
                this.lista.agregar(t);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Error al cargar los temas del archivo", e);
        }
    }

    private void guardarCambios() {
        try {
            repositorio.guardar(listar());
        } catch (IOException e) {
            throw new UncheckedIOException("Error al guardar los temas en el archivo", e);
        }
    }

    @Override
    public void agregar(Tema tema) {
        tema.validar();
        if (buscar(tema.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un tema con el código: " + tema.getCodigo());
        }
        lista.agregar(tema);
        guardarCambios();
    }

    @Override
    public Tema buscar(String codigo) {
        for (Tema t : lista) {
            if (t.getCodigo().equals(codigo)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < lista.tamano(); i++) {
            if (lista.obtener(i).getCodigo().equals(codigo)) {
                lista.eliminar(i);
                guardarCambios();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tema> listar() {
        List<Tema> todos = new ArrayList<>();
        for (Tema t : lista) {
            todos.add(t);
        }
        return todos;
    }

    // ==========================================
    // MÉTODOS DE ESTADÍSTICAS Y REPRODUCTOR
    // ==========================================

    @Override
    public List<Tema> ranking() {
        // Pedimos la lista normal y la ordenamos
        List<Tema> ordenados = listar();
        ordenados.sort((t1, t2) -> {
            // Compara ritmo de mayor a menor (t2 vs t1)
            int compRitmo = Integer.compare(t2.getRitmo(), t1.getRitmo());
            if (compRitmo == 0) {
                // Si empatan, ordena alfabéticamente por código (t1 vs t2)
                return t1.getCodigo().compareTo(t2.getCodigo());
            }
            return compRitmo;
        });
        return ordenados;
    }

    @Override
    public Map<String, Double> promedioPorCategoria() {
        // Mapas auxiliares para sumar ritmos y contar cuántos hay por categoría
        Map<String, Double> suma = new HashMap<>();
        Map<String, Integer> conteo = new HashMap<>();

        for (Tema t : lista) {
            suma.put(t.getCategoria(), suma.getOrDefault(t.getCategoria(), 0.0) + t.getRitmo());
            conteo.put(t.getCategoria(), conteo.getOrDefault(t.getCategoria(), 0) + 1);
        }

        // TreeMap organiza automáticamente las llaves (categorías) en orden alfabético
        Map<String, Double> promedios = new TreeMap<>();
        for (String cat : suma.keySet()) {
            promedios.put(cat, suma.get(cat) / conteo.get(cat));
        }
        
        return promedios;
    }

    @Override
    public int total() {
        return lista.tamano();
    }

    @Override
    public Tema actual() {
        return lista.actual();
    }

    @Override
    public Tema siguiente() {
        lista.avanzar();
        return lista.actual();
    }

    @Override
    public Tema anterior() {
        lista.retroceder();
        return lista.actual();
    }
}