package co.umariana.isaacs.estructura;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista DOBLEMENTE enlazada del equipo (tarea de ESTRUCTURA).
 *
 * <p>Cada nodo conoce al nodo {@code siguiente} y al {@code anterior}. Implemente la lista con sus
 * propios nodos: no se permite usar {@code ArrayList}, {@code LinkedList} ni ninguna otra colección
 * de {@code java.util} para guardar los elementos (las pruebas lo revisan).
 *
 * <p>Se espera que guarde la {@code cabeza} y la {@code cola}, de modo que agregar al final y
 * retroceder el cursor sean operaciones inmediatas (sin recorrer toda la lista).
 *
 * <p>Comportamiento del cursor: se DETIENE en los extremos. {@link #avanzar()} desde el último
 * elemento devuelve {@code false} y el cursor no se mueve; {@link #retroceder()} desde el primero
 * también devuelve {@code false}. En una lista vacía ambos devuelven {@code false}.
 *
 * <p>Al eliminar el elemento donde está el cursor, el cursor pasa al siguiente; si no había
 * siguiente, pasa al anterior.
 */
public class ListaDoble<T> implements Lista<T> {

    /** Nodo propio: guarda el dato y conoce al siguiente y al anterior. */
    private class Nodo {
        T dato;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo cabeza;   // primer nodo de la lista
    private Nodo cola;     // último nodo de la lista
    private Nodo cursor;   // nodo "actual" del reproductor
    private int tamano;    // cuántos elementos hay

    @Override
    public void agregar(T elemento) {
        Nodo nuevo = new Nodo(elemento);
        if (estaVacia()) {
            // Es el único nodo: es cabeza, cola y donde empieza el cursor.
            cabeza = nuevo;
            cola = nuevo;
            cursor = nuevo;
        } else {
            // Se enlaza después de la cola, en los dos sentidos.
            nuevo.anterior = cola;
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        tamano++;
    }

    /** Devuelve el nodo de esa posición (el índice ya debe estar validado). */
    private Nodo nodoEn(int indice) {
        Nodo puntero = this.cabeza;
        for (int i = 0; i < indice; i++) {
            puntero = puntero.siguiente;
        }
        return puntero;
    }

    @Override
    public void insertar(int indice, T elemento) {
        if (indice < 0 || indice > tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        // Insertar al final (o en una lista vacía) es lo mismo que agregar.
        if (indice == tamano) {
            agregar(elemento);
            return;
        }
        Nodo nuevo = new Nodo(elemento);
        // El nodo que hoy está en esa posición quedará justo después del nuevo.
        Nodo siguiente = nodoEn(indice);
        nuevo.siguiente = siguiente;
        nuevo.anterior = siguiente.anterior;
        if (siguiente.anterior == null) {
            cabeza = nuevo;                       // se insertó al inicio
        } else {
            siguiente.anterior.siguiente = nuevo; // el de atrás ahora apunta al nuevo
        }
        siguiente.anterior = nuevo;
        tamano++;
    }

    @Override
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return nodoEn(indice).dato;
    }

    @Override
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo nodo = nodoEn(indice);
        // Si se elimina el nodo del cursor, pasa al siguiente (o al anterior si no hay).
        if (nodo == cursor) {
            cursor = (nodo.siguiente != null) ? nodo.siguiente : nodo.anterior;
        }
        // Se saca el nodo de la cadena, en los dos sentidos.
        if (nodo.anterior == null) {
            cabeza = nodo.siguiente;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
        }
        if (nodo.siguiente == null) {
            cola = nodo.anterior;
        } else {
            nodo.siguiente.anterior = nodo.anterior;
        }
        tamano--;
        return nodo.dato;
    }

    @Override
    public int tamano() {
        return tamano;
    }

    @Override
    public boolean estaVacia() {
        return tamano == 0;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cola = null;
        cursor = null;
        tamano = 0;
    }

    @Override
    public T actual() {
        // Si no hay cursor (lista vacía), devuelve null.
        return cursor == null ? null : cursor.dato;
    }

    @Override
    public boolean avanzar() {
        // Se detiene en el último: no hay siguiente.
        if (cursor == null || cursor.siguiente == null) {
            return false;
        }
        cursor = cursor.siguiente;
        return true;
    }

    @Override
    public boolean retroceder() {
        // Se detiene en el primero. Usa el enlace "anterior", sin recorrer la lista.
        if (cursor == null || cursor.anterior == null) {
            return false;
        }
        cursor = cursor.anterior;
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo puntero = cabeza;

            @Override
            public boolean hasNext() {
                return puntero != null;
            }

            @Override
            public T next() {
                if (puntero == null) {
                    throw new NoSuchElementException();
                }
                T dato = puntero.dato;
                puntero = puntero.siguiente;
                return dato;
            }
        };
    }
}
