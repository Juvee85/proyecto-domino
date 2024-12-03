/*
 * MediadorPantallas.java
 */
package mediador;

import DTOS.JugadorDTO;
import DTOS.SalaDTO;
import crearSala.CrearSala;
import crearSala.CrearSalaControlador;
import crearSala.CrearSalaModelo;
import inicio.Inicio;
import inicio.InicioControlador;
import inicio.InicioModelo;
import interfacesObservador.ObservadorAbrirPantallaCrearSala;
import interfacesObservador.ObservadorAbrirPantallaSalasDisponibles;
import interfacesObservador.ObservadorAnhadirFicha;
import interfacesObservador.ObservadorCambiarEstadoListo;
import interfacesObservador.ObservadorRegresarPantallaAnterior;
import interfacesObservador.ObservadorSalirSala;
import interfacesObservador.ObservadorUnirASala;
import interfacesObservador.salas.ObservadorCrearSala;
import java.util.List;
import salaEspera.SalaEspera;
import salaEspera.SalaEsperaControlador;
import salaEspera.SalaEsperaModelo;
import salasDisponibles.SalasDisponibles;
import salasDisponibles.SalasDisponiblesControlador;
import salasDisponibles.SalasDisponiblesModelo;
import tablero.ControladorSeleccionarFicha;
import tablero.TableroGUI;
import tablero.TableroModelo;

/**
 * Clase que controla la instanciación de las pantallas de juego
 *
 * @author Juventino López García - 00000248547
 */
public class MediadorPantallas {

    private TableroModelo modeloTableroJuego;
    private SalaEsperaModelo modeloSalaEspera;
    private static MediadorPantallas instance;

    private MediadorPantallas() {
    }

    public static MediadorPantallas getInstance() {
        if (instance == null) {
            instance = new MediadorPantallas();
        }
        return instance;
    }

    public void mostrarMenuPrincipal(ObservadorAbrirPantallaCrearSala observadorCrearSala,
            ObservadorAbrirPantallaSalasDisponibles observadorSalasDisponibles) {
        InicioModelo modelo = new InicioModelo();
        modelo.anhadirObservadorCrearSala(observadorCrearSala);
        modelo.anhadirObservadorSalasDisponibles(observadorSalasDisponibles);
        Inicio vista = new Inicio(modelo);
        InicioControlador controlador = new InicioControlador(vista, modelo);
        vista.setVisible(true);
    }

    public void mostrarPantallaSalasDisponibles(List<SalaDTO> salas, ObservadorUnirASala observador,
            ObservadorRegresarPantallaAnterior observadorRegresar) {
        SalasDisponiblesModelo modelo = new SalasDisponiblesModelo();
        modelo.setSalasDisponibles(salas);
        modelo.anhadirObservador(observador);
        modelo.anhadirObservadorRegresarPantalla(observadorRegresar);
        SalasDisponibles vista = new SalasDisponibles(modelo);
        SalasDisponiblesControlador controlador = new SalasDisponiblesControlador(modelo, vista);
        vista.setVisible(true);
    }

    public void mostrarMenuPantallaCrearSala(ObservadorCrearSala observador, ObservadorRegresarPantallaAnterior observadorRegresar) {
        CrearSalaModelo modelo = new CrearSalaModelo();
        modelo.anhadirObservador(observador);
        modelo.anhadirObservadorRegresarPantalla(observadorRegresar);
        CrearSala vista = new CrearSala(modelo);
        CrearSalaControlador controlador = new CrearSalaControlador(vista, modelo);
        vista.setVisible(true);
    }

    public void mostrarSalaEspera(List<JugadorDTO> jugadores, ObservadorSalirSala observadorSalirSala, ObservadorCambiarEstadoListo observadorCambiarEstado) {
        SalaEsperaModelo modelo = new SalaEsperaModelo();
        modelo.setJugadores(jugadores);
        SalaEspera vista = new SalaEspera(modelo);
        modelo.anhadirObservador(vista);
        modelo.anhadirObservadorSalirSala(observadorSalirSala);
        modelo.anhadirObservadorCambiarEstado(observadorCambiarEstado);
        this.modeloSalaEspera = modelo;
        SalaEsperaControlador controlador = new SalaEsperaControlador(vista, modelo);
        vista.setVisible(true);
    }

    public void mostrarPantallaTableroJuego(List<JugadorDTO> jugadoresEnJuego, 
            JugadorDTO jugadorLocal, ObservadorAnhadirFicha observadorAnhadirFicha, int cantidadFichasRestantes) {
        TableroModelo modelo = new TableroModelo();
        modelo.setJugadorLocal(jugadorLocal);
        modelo.setJugadores(jugadoresEnJuego);
        modelo.setCantidadFichasRestantes(cantidadFichasRestantes);
        TableroGUI vista = new TableroGUI(modelo);
        modelo.anhadirObservador(vista);
        modelo.anhadirObservadorAnhadirFicha(observadorAnhadirFicha);
        
        ControladorSeleccionarFicha controlador = new ControladorSeleccionarFicha(vista, modelo);
        vista.setVisible(true);
    }

    /**
     * Actualiza la tabla de dos maneras, en caso de que un jugador haya
     * abandonado la partida, la lista contendra a todos menos a ese jugador. En
     * caso de que un nuevo jugador se haya unido, se mostrara en la tabla.
     *
     * @param nuevosjugadores Lista de los jugadores actuales en la sala
     */
    public void actualizarPantallaSalaEspera(List<JugadorDTO> nuevosjugadores) {
        modeloSalaEspera.setJugadores(nuevosjugadores);
        modeloSalaEspera.notificarObservadores();
    }

}
