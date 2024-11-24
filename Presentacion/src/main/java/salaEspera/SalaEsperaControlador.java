/*
 * SalaEsperaControlador.java
 */
package salaEspera;

import interfacesObservador.Observador;
import java.awt.event.ActionEvent;

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
        this.vista.anhadirObservadorSalirSala((ActionEvent e) -> {
            this.modelo.notificarObservadoresSalirSala();
            this.vista.dispose();
        });
    }

    @Override
    public void actualizar() {
        
    }
    
}
