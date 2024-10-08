/*
 * TrenDominos.java
 */
package entidades;

import entidades.Pozo.Ficha;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 *
 * @author Juventino López García - 00000248547 - 27/09/2024
 */
public class TrenFichas implements Iterable<Ficha> {

    private Nodo nodo;

    private class Nodo {

        Ficha dato;
        Nodo hijoIzq;
        Nodo hijoDer;

        /**
         * Inicializa el atributo dato al valor de su parametro
         *
         * @param dato Dato a almacenar en el nodo
         */
        public Nodo(Ficha dato) {
            this.dato = dato;
        }
    }

    private class IteradorFichas implements Iterator<Ficha> {

        private Stack<Nodo> pilaNodos;
        private Nodo nodoActual;
        private boolean enLadoIzq = true;

        /**
         * Inicializa los atributos de la clase
         */
        public IteradorFichas() {
            // Pila para almacenar los nodos izquierdos
            pilaNodos = new Stack<>();
            nodoActual = nodo;
            if (nodoActual != null) {
                pilaNodos.push(nodoActual);
            }
        }

        /**
         * Determina si aun hay nodos del arbol binario sin recorrer
         *
         * @return true si aun hay nodos del arbol binario sin recorrer, false
         * en caso contrario.
         */
        @Override
        public boolean hasNext() {
            // Si aun hay nodos en la pila
            return !pilaNodos.empty();
        }

        @Override
        public Ficha next() {
            Nodo nodoSig = null;
            // Extrae el primer elemento de la pila, luego añade al hijo derecho
            // y por ultimo al izquierdo para que sea el siguiente por recorrer
            if (!pilaNodos.empty()) {
                nodoSig = pilaNodos.pop();
                if (nodoSig.hijoDer != null) {
                    pilaNodos.push(nodoSig.hijoDer);
                }
                if (nodoSig.hijoIzq != null) {
                    pilaNodos.push(nodoSig.hijoIzq);
                }
                if (enLadoIzq && nodoSig.hijoIzq == null && nodoSig.hijoDer == null) {
                    enLadoIzq = false;
                }
            } else {
                throw new NoSuchElementException();
            }
            return nodoSig.dato;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean isEnLadoIzq() {
            return enLadoIzq;
        }

    }

    public TrenFichas(Ficha primeraMula) {
        this.nodo = new Nodo(primeraMula);
    }

    
    public TrenFichas() {

    }

    public void agregarFichaExtremoIzquierdo(Ficha ficha) {
        Nodo actual = nodo;
        while (actual.hijoIzq != null) {
            actual = actual.hijoIzq;
        }
        actual.hijoIzq = new Nodo(ficha);
    }

    public void agregarFichaExtremoDerecho(Ficha ficha) {
        Nodo actual = nodo;
        while (actual.hijoDer != null) {
            actual = actual.hijoDer;
        }
        actual.hijoDer = new Nodo(ficha);
    }

    public Ficha obtenerFichaExtremoIzquierdo() {
        return obtenerFichaIzquierda(nodo);
    }

    private Ficha obtenerFichaIzquierda(Nodo nodo) {
        return (nodo.hijoIzq == null) ? nodo.dato : obtenerFichaIzquierda(nodo.hijoIzq);
    }

    public Ficha obtenerFichaExtremoDerecho() {
        return obtenerFichaDerecha(nodo);
    }

    private Ficha obtenerFichaDerecha(Nodo nodo) {
        return (nodo.hijoDer == null) ? nodo.dato : obtenerFichaDerecha(nodo.hijoDer);
    }

    @Override
    public Iterator<Ficha> iterator() {
        return new IteradorFichas();
    }
}
