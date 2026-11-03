package co.umariana.isaacs.modelo;

/**
 * Un tema del Isaacsfy: un audio con su título, categoría y nivel de ritmo.
 *
 * <p>La clase ya tiene sus atributos, constructor y getters. Lo que falta es {@link #validar()}
 * (tarea de MODELO).
 */
public class Tema {

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private final int ritmo;
    private final double duracionSeg;
    private final String descripcion;
    private final String archivo;

    public Tema(String codigo, String titulo, String categoria, int ritmo,
                   double duracionSeg, String descripcion, String archivo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.ritmo = ritmo;
        this.duracionSeg = duracionSeg;
        this.descripcion = descripcion;
        this.archivo = archivo;
    }

    /**
     * Revisa que el tema sea válido. Si no lo es, lanza {@link IllegalArgumentException}
     * con un mensaje que explique el problema. Reglas:
     * <ul>
     *   <li>{@code codigo}, {@code titulo}, {@code categoria} y {@code archivo} no pueden ser nulos ni estar en blanco.</li>
     *   <li>{@code ritmo} debe estar entre 1 y 10 (incluidos).</li>
     *   <li>{@code duracionSeg} debe ser mayor que 0.</li>
     *   <li>{@code descripcion} puede estar vacía, pero no ser nula.</li>
     *   <li>Ningún texto puede contener el carácter {@code ;} (separa los campos del archivo .txt)
     *       ni saltos de línea.</li>
     * </ul>
     */
    public void validar() {
        // TODO (MODELO): implementar las reglas descritas arriba.
        throw new UnsupportedOperationException("TODO: validar()");
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public int getRitmo() { return ritmo; }
    public double getDuracionSeg() { return duracionSeg; }
    public String getDescripcion() { return descripcion; }
    public String getArchivo() { return archivo; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + categoria + ", ritmo " + ritmo + ")";
    }
}
