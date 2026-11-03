package co.umariana.isaacs.web;

import co.umariana.isaacs.modelo.ServicioTemas;

/**
 * Punto único de acceso al modelo desde los servlets. NO se modifica.
 * Los servlets hacen {@code Contexto.servicio().listar()}, etc.
 */
public final class Contexto {

    private static volatile ServicioTemas servicio;

    private Contexto() {
    }

    public static ServicioTemas servicio() {
        ServicioTemas s = servicio;
        if (s == null) {
            throw new IllegalStateException("El modelo no está disponible todavía. Revise la consola del servidor: "
                    + "probablemente falta implementar alguna clase (GestorTemas, ListaDoble o RepositorioTxt).");
        }
        return s;
    }

    /** Lo usa {@link AppListener} al arrancar y las pruebas para poner un servicio de mentira. */
    public static void usar(ServicioTemas nuevo) {
        servicio = nuevo;
    }
}
