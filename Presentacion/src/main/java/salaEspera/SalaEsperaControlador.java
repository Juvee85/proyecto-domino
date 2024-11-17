/*
 * SalaEsperaControlador.java
 */
package salaEspera;

import interfacesObservador.Observador;

/**
 * 
 * @author Juventino López García - 00000248547
 */
public class SalaEsperaControlador implements Observador {
    
    private SalaEspera vista;
    private SalaEsperaModelo modelo;

    public SalaEsperaControlador(SalaEspera vista, SalaEsperaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.anhadirObservador(this);
    }

    @Override
    public void actualizar() {
        
    }
    
}
