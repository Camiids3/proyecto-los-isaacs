package co.umariana.isaacs.modelo;

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

    public void validar() {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo ni estar en blanco.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo ni estar en blanco.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede ser nula ni estar en blanco.");
        }
        if (archivo == null || archivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede ser nulo ni estar en blanco.");
        }
        if (ritmo < 1 || ritmo > 10) {
            throw new IllegalArgumentException("El ritmo debe estar entre 1 y 10.");
        }
        if (duracionSeg <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que 0.");
        }
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripción no puede ser nula.");
        }
        String[] textos = {codigo, titulo, categoria, descripcion, archivo};
        for (String texto : textos) {
            if (texto.contains(";") || texto.contains("\n") || texto.contains("\r")) {
                throw new IllegalArgumentException("Los textos no pueden contener ';' ni saltos de línea.");
            }
        }
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