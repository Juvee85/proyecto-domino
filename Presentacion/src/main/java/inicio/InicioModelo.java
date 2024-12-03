/*
 * InicioModelo.java
 */
package inicio;

import interfacesObservador.ObservadorAbrirPantallaCrearSala;
import interfacesObservador.ObservadorAbrirPantallaSalasDisponibles;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class InicioModelo {

    List<ObservadorAbrirPantallaCrearSala> observadoresCrearSala;

    List<ObservadorAbrirPantallaSalasDisponibles> observadoresAbrirPantallaSalasDisponibles;

    public InicioModelo() {
        observadoresCrearSala = new ArrayList<>();
        observadoresAbrirPantallaSalasDisponibles = new ArrayList<>();
    }

    public void anhadirObservadorCrearSala(ObservadorAbrirPantallaCrearSala observador) {
        observadoresCrearSala.add(observador);
    }

    public void anhadirObservadorSalasDisponibles(ObservadorAbrirPantallaSalasDisponibles observador) {
        observadoresAbrirPantallaSalasDisponibles.add(observador);
    }

    public void notificarCrearSala() {
        for (ObservadorAbrirPantallaCrearSala observador : observadoresCrearSala) {
            observador.actualizar();
        }
    }

    public void notificarSalasDisponibles() {
        for (ObservadorAbrirPantallaSalasDisponibles observador : observadoresAbrirPantallaSalasDisponibles) {
            observador.actualizar();
        }
    }
}
