package co.umariana.isaacs.web;

import co.umariana.isaacs.modelo.Tema;
import co.umariana.isaacs.modelo.ServicioTemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Servicio de mentira que anota lo que le piden: permite probar los servlets sin el resto del equipo. */
class ServicioFalso implements ServicioTemas {

    final List<Tema> datos = new ArrayList<>();
    final List<Tema> ranking = new ArrayList<>();
    final Map<String, Double> promedios = new TreeMap<>();
    final List<String> llamadas = new ArrayList<>();
    RuntimeException fallarAlAgregar = null;
    Tema cursorActual = null;
    Tema cursorSiguiente = null;
    Tema cursorAnterior = null;

    @Override public void agregar(Tema l) {
        llamadas.add("agregar");
        if (fallarAlAgregar != null) throw fallarAlAgregar;
        datos.add(l);
    }
    @Override public Tema buscar(String codigo) { llamadas.add("buscar"); return null; }
    @Override public boolean eliminar(String codigo) { llamadas.add("eliminar:" + codigo); return true; }
    @Override public List<Tema> listar() { llamadas.add("listar"); return datos; }
    @Override public List<Tema> ranking() { llamadas.add("ranking"); return ranking; }
    @Override public Map<String, Double> promedioPorCategoria() { llamadas.add("promedios"); return promedios; }
    @Override public int total() { return datos.size(); }
    @Override public Tema actual() { llamadas.add("actual"); return cursorActual; }
    @Override public Tema siguiente() { llamadas.add("siguiente"); return cursorSiguiente; }
    @Override public Tema anterior() { llamadas.add("anterior"); return cursorAnterior; }
}
