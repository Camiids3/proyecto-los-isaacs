package co.umariana.isaacs.persistencia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import co.umariana.isaacs.modelo.Tema;

/**
 * Guarda los temas en un archivo de texto (tarea de PERSISTENCIA).
 *
 * <p>Formato: UNA línea por tema, con los campos separados por {@code ;} y en este orden:
 * <pre>codigo;titulo;categoria;ritmo;duracionSeg;descripcion;archivo</pre>
 * Ejemplo:
 * <pre>L001;Recall;Instrumental;2;1.32;Tema de ejemplo de todos los días;recall.mp3</pre>
 * <ul>
 *   <li>Codificación UTF-8 (para las tildes y la ñ).</li>
 *   <li>{@code duracionSeg} se escribe con punto decimal.</li>
 *   <li>{@code cargar}: si el archivo no existe devuelve una lista vacía; ignora las líneas en
 *       blanco y las líneas dañadas (cantidad de campos distinta de 7 o números inválidos).</li>
 *   <li>{@code guardar}: reemplaza todo el contenido del archivo y crea las carpetas que falten.
 *       Una lista vacía deja el archivo vacío.</li>
 * </ul>
 */
public class RepositorioTxt implements Repositorio {

    private final Path archivo;

    public RepositorioTxt(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Tema> cargar() throws IOException {
        List<Tema> temas = new ArrayList<>();

        // Si el archivo no existe, devolver lista vacía
        if (!Files.exists(archivo)) {
            return temas;
        }

        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        for (String linea : lineas) {
            // Ignorar líneas en blanco
            if (linea.isBlank()) {
                continue;
            }

            // -1 conserva los campos vacíos del final (ej. descripción vacía)
            String[] campos = linea.split(";", -1);

            // Ignorar líneas dañadas: cantidad de campos distinta de 7
            if (campos.length != 7) {
                continue;
            }

            try {
                String codigo = campos[0];
                String titulo = campos[1];
                String categoria = campos[2];
                int ritmo = Integer.parseInt(campos[3].trim());
                double duracionSeg = Double.parseDouble(campos[4].trim());
                String descripcion = campos[5];
                String nombreArchivo = campos[6];

                temas.add(new Tema(codigo, titulo, categoria, ritmo,
                        duracionSeg, descripcion, nombreArchivo));
            } catch (NumberFormatException e) {
                // Números inválidos: línea dañada, se ignora
            }
        }
        return temas;
    }

    @Override
    public void guardar(List<Tema> temas) throws IOException {
        // Crear las carpetas que falten
        Path padre = archivo.toAbsolutePath().getParent();
        if (padre != null) {
            Files.createDirectories(padre);
        }

        List<String> lineas = new ArrayList<>();
        for (Tema t : temas) {
            lineas.add(String.join(";",
                    t.getCodigo(),
                    t.getTitulo(),
                    t.getCategoria(),
                    String.valueOf(t.getRitmo()),
                    String.valueOf(t.getDuracionSeg()), // siempre con punto decimal
                    t.getDescripcion(),
                    t.getArchivo()));
        }

        // Reemplaza todo el contenido; con lista vacía queda el archivo vacío
        Files.write(archivo, lineas, StandardCharsets.UTF_8);
    }
}