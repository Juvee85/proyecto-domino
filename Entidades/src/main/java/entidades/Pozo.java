package entidades;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author neri
 */
public class Pozo {

    private static Pozo instance;

    private final List<Ficha> fichas;

    private Pozo() {
        this.fichas = new Stack<>();
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
                Ficha f = new Ficha(i, j);
                this.fichas.add(f);
            }
        }
        
        this.revolverFichas();
    }
    
    private void revolverFichas() {
        Collections.shuffle(this.fichas);
    }
    
    public List<Ficha> obtenerJuegoDeDominos(int cantidadFichas) {
        return null;
    }
    
    public Ficha sacarFicha() {
        return this.fichas.removeLast();
    }
    
    public void meterFicha(Ficha ficha) {
        this.fichas.add(ficha);
    }
    
    public void meterListaFichas(List<Ficha> listaFichas) {
        this.fichas.addAll(listaFichas);
    }
}
