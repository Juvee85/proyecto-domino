/*
 * InicioModelo.java
 */
package inicio;

import interfacesObservador.ObservadorAbrirPantallaCrearSala;
import interfacesObservador.ObservadorAbrirPantallaSalasDisponibles;
import interfacesObservador.ObservadorAbrirPantallaUnirASala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class InicioModelo {

    List<ObservadorAbrirPantallaUnirASala> observadoresUnir;

    List<ObservadorAbrirPantallaCrearSala> observadoresCrearSala;

    List<ObservadorAbrirPantallaSalasDisponibles> observadoresAbrirPantallaSalasDisponibles;

    public InicioModelo() {
        observadoresUnir = new ArrayList<>();
        observadoresCrearSala = new ArrayList<>();
        observadoresAbrirPantallaSalasDisponibles = new ArrayList<>();
    }

    public void anhadirObservadorUnirASala(ObservadorAbrirPantallaUnirASala observador) {
        observadoresUnir.add(observador);
    }

    public void anhadirObservadorCrearSala(ObservadorAbrirPantallaCrearSala observador) {
        observadoresCrearSala.add(observador);
    }

    public void anhadirObservadorSalasDisponibles(ObservadorAbrirPantallaSalasDisponibles observador) {
        observadoresAbrirPantallaSalasDisponibles.add(observador);
    }

    public void notificarUnirASala() {
        for (ObservadorAbrirPantallaUnirASala observador : observadoresUnir) {
            observador.actualizar();
        }
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
