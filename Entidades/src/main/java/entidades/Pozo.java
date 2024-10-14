package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa al pozo en el juego de domino, el cual tambien es el encargado de
 * generar las fichas
 *
 * @author
 */
public class Pozo {

    private final List<Ficha> fichas;

    /**
     * Clase de ficha de domino concreta
     */
    public static class Ficha implements Serializable {

        private boolean esMula;
        private int puntosCabeza;
        private int puntosCola;

        public Ficha(int puntosCabeza, int puntosCola) {
            this.puntosCabeza = puntosCabeza;
            this.puntosCola = puntosCola;

            this.esMula = puntosCabeza == puntosCola;
        }

        public void flip() {
            int tmpCabeza = this.puntosCabeza;
            this.puntosCabeza = this.puntosCola;
            this.puntosCola = tmpCabeza;
        }

        public boolean esMula() {
            return esMula;
        }

        public int getPuntosCabeza() {
            return puntosCabeza;
        }

        public int getPuntosCola() {
            return puntosCola;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Ficha other = (Ficha) obj;

            return (this.puntosCola == other.puntosCola && this.puntosCabeza == other.puntosCabeza
                    || this.puntosCola == other.puntosCabeza && this.puntosCabeza == other.puntosCola);

        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + this.puntosCabeza;
            hash = 79 * hash + this.puntosCola;
            return hash;
        }

        @Override
        public String toString() {
            return String.format("[%d|%d]", this.puntosCabeza, this.puntosCola);
        }
    }

    public Pozo() {
        this.fichas = new ArrayList<>();
        this.inicializarFichas();
    }

    private void inicializarFichas() {
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                Ficha f = new Ficha(i, j);
                this.fichas.add(f);
            }
        }

        this.revolverFichas();
    }

    private void revolverFichas() {
        Collections.shuffle(this.fichas);
    }

    public List<Ficha> obtenerJuegoDeFichas(int cantidadFichas) {

        if (cantidadFichas < 0) {
            return new ArrayList<>();
        }

        List<Ficha> juegoFichas = new ArrayList<>();
        while (cantidadFichas > 0 && this.fichasRestantes() > 0) {
            juegoFichas.add(this.fichas.remove(this.fichas.size() - 1));
            cantidadFichas--;
        }

        return juegoFichas;
    }

    public Ficha sacarFicha() {
        return this.fichas.removeLast();
    }

    public void meterFicha(Ficha ficha) {
        this.fichas.add(ficha);
        this.revolverFichas();
    }

    public void meterListaFichas(List<Ficha> listaFichas) {
        this.fichas.addAll(listaFichas);
        this.revolverFichas();
    }

    public int fichasRestantes() {
        return this.fichas.size();
    }
}
