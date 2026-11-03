package co.umariana.isaacs.modelo;

import co.umariana.isaacs.persistencia.Repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Repositorio de mentira (en memoria) para probar el modelo sin depender del archivo .txt. */
class RepositorioFalso implements Repositorio {

    List<Tema> guardados = new ArrayList<>();
    int vecesGuardado = 0;
    boolean fallar = false;

    @Override
    public List<Tema> cargar() throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        return new ArrayList<>(guardados);
    }

    @Override
    public void guardar(List<Tema> temas) throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        guardados = new ArrayList<>(temas);
        vecesGuardado++;
    }
}
