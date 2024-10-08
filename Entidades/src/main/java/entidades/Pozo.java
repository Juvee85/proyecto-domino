package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Representa al pozo en el juego de domino, el cual tambien es el encargado de generar las fichas
 * @author neri
 */
public class Pozo {

    private static Pozo instance;
    private final List<Ficha> fichas;

    /**
     * Clase de ficha de domino concreta
     */
    private class FichaDomino implements Ficha {
        private boolean esMula;
        private int puntosCabeza;
        private int puntosCola;

        protected FichaDomino(int puntosCabeza, int puntosCola) {
            this.puntosCabeza = puntosCabeza;
            this.puntosCola = puntosCola;

            this.esMula = puntosCabeza == puntosCola;
        }

        @Override
        public void flip() {
            int tmpCabeza = this.puntosCabeza;
            this.puntosCabeza = this.puntosCola;
            this.puntosCola = tmpCabeza;
        }

        @Override
        public boolean esMula() {
            return esMula;
        }

        @Override
        public int getPuntosCabeza() {
            return puntosCabeza;
        }

        @Override
        public int getPuntosCola() {
            return puntosCola;
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

    public static Pozo getInstance() {
        if (instance == null) {
            instance = new Pozo();
        }

        return instance;
    }

    
    private void inicializarFichas() {
        for (int i=0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                Ficha f = new FichaDomino(i, j);
                this.fichas.add(f);
            }
        }
        
        this.revolverFichas();
    }
    
    private void revolverFichas() {
        Collections.shuffle(this.fichas);
    }
    
    public List<Ficha> obtenerJuegoDeFichas(int cantidadFichas) {  
        List<Ficha> juegoFichas = this.fichas.stream()
                .limit(cantidadFichas)
                .collect(Collectors.toList());
        
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
