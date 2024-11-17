package com.equipo1.logicadomino;

import DTOS.PartidaDTO;
import DTOS.SalaDTO;
import com.equipo1.convertidores.FichaConverter;
import com.equipo1.convertidores.JugadorConverter;
import com.equipo1.convertidores.PartidaConverter;
import com.equipo1.convertidores.SalaConverter;
import conexion.Conexion;
import entidades.ConfiguracionJuego;
import entidades.Jugador;
import entidades.Partida;
import entidades.Pozo.Ficha;
import entidades.Sala;
import entidades.Tablero;
import interfacesObservador.ObservadorAbrirPantallaCrearSala;
import interfacesObservador.ObservadorAbrirPantallaSalasDisponibles;
import interfacesObservador.ObservadorAbrirPantallaUnirASala;
import interfacesObservador.salas.ObservadorCrearSala;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediador.MediadorPantallas;
import observador.ObservadorConexion;

/**
 *
 * @author
 */
public class LogicaDomino implements ObservadorConexion {

    private Partida partida;
    private Sala sala;
    private Jugador jugador;
    private Conexion conexion;

    /**
     *
     */
    public void inicio() {
        // TODO: ver si es mejor crear conexion al BUS desde el inicio
        ObservadorAbrirPantallaCrearSala observadorCrearSala = () -> {
            crearSala();
            conexion = new Conexion();
            conexion.agregarObservador(this);
            Thread hilo = new Thread(conexion);
            hilo.start();
        };

        ObservadorAbrirPantallaUnirASala observadorUnirASala = () -> {
            unirASala();
        };

        ObservadorAbrirPantallaSalasDisponibles observadorSalasDisponibles = () -> {
            obtenerSalasDisponibles();
        };

        MediadorPantallas.getInstance().mostrarMenuPrincipal(observadorCrearSala, observadorUnirASala, observadorSalasDisponibles);
    }

    public void crearSala() {
        ObservadorCrearSala crearSala
                = (SalaDTO salaDTO, String nombreJugador) -> {
                    sala = new SalaConverter().convertFromDTO(salaDTO);
                    jugador = new Jugador();
                    jugador.setNombre(nombreJugador);
                    jugador.setNumero(0);
                    jugador.setAvatar("");

                    try {
                        conexion.enviarEvento(crearEventoSolicitarCrearSala(sala));
                    } catch (IOException ex) {
                        Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };

        MediadorPantallas.getInstance().mostrarMenuPantallaCrearSala(crearSala);
    }

    public void obtenerSalasDisponibles() {
        conexion = new Conexion();
        conexion.agregarObservador(this);
        try {
            conexion.enviarEvento(crearEventoSolicitarSalasDisponibles());
        } catch (IOException ex) {
            Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
        }
        // obtener salas desde observador a conexión
        // Poner lo siguiente dentro del update en el caso que reciba el evento con las salas disponibles
        List<SalaDTO> salas = new ArrayList<>();
        // Agregar datos de la respuesta
        MediadorPantallas.getInstance().mostrarPantallaSalasDisponibles(salas);
    }

    /**
     *
     */
    public void unirASala() {
        // MediadorPantallas.getInstance().mostrarPantallaUnirASala(observador);
    }

    /**
     *
     */
    public void inicializarJuego() {

        Jugador anfitrion = new Jugador();
        anfitrion.setNombre("Alfonso");
        anfitrion.setAvatar("DonAlfonsoDestroyer");

        Partida partida = new Partida(anfitrion);

        // se crea la configuracion (solo 3 jugadores y 5 fichas para cada uno)
        ConfiguracionJuego configuracion = new ConfiguracionJuego(3, 5);
        partida.setConfiguracion(configuracion);

        // se crea otro jugador
        Jugador jugador2 = new Jugador();
        jugador2.setNombre("Pedro");
        jugador2.setAvatar("ElPerroShesh14");
        boolean agregado = partida.agregarJugador(jugador2);
        System.out.println(agregado);

        // se inicia la partida
        partida.iniciar();

        System.out.println("Fichas jugador 1: ");
        for (Ficha f : anfitrion.obtenerFichas()) {
            System.out.println(f);
        }

        System.out.println("Fichas jugador 2: ");
        for (Ficha f : jugador2.obtenerFichas()) {
            System.out.println(f);
        }

        System.out.println("Estado de la partida: " + partida.getEstado());

        Tablero tab = new Tablero();

        // Agrega fichas de prueba
        Ficha ficha1 = partida.getPozo().sacarFicha();
        Ficha ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        ficha1 = partida.getPozo().sacarFicha();
        ficha2 = partida.getPozo().sacarFicha();
        tab.agregarFichaExtremoIzquierdo(ficha1);
        tab.agregarFichaExtremoDerecho(ficha2);

        partida.setTablero(tab);
        // Hasta aqui se agregan datos de prueba

        this.partida = partida;

        PartidaDTO dto = new PartidaConverter().convertFromEntity(partida);

        MediadorPantallas.getInstance().mostrarPantallaJuego(dto);

        MediadorPantallas.getInstance().anhadirObservador((jugador, ficha) -> {
            try {
                anhadirFichaTablero(new JugadorConverter().convertFromDTO(jugador),
                        new FichaConverter().convertFromDTO(ficha));
            } catch (IOException ex) {
                Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     *
     * @param jugador
     * @param ficha
     * @throws IOException
     */
    public void anhadirFichaTablero(Jugador jugador, Ficha ficha) throws IOException {
        Tablero tablero = partida.getTablero();

        if (tablero.agregarFichaExtremoIzquierdo(ficha)
                || tablero.agregarFichaExtremoDerecho(ficha)) {
            partida.getJugadores().get(0).sacarFicha(ficha);
        }

        MediadorPantallas.getInstance().actualizarPantalla(new PartidaConverter().convertFromEntity(partida));

        conexion.enviarEvento(crearEvento(jugador, ficha));
    }

    /**
     *
     * @param jugador
     * @param ficha
     * @return
     */
    private Map<String, Object> crearEvento(Jugador jugador, Ficha ficha) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("jugador", jugador);
        mapa.put("ficha", ficha);

        return mapa;
    }

    /**
     *
     * @param sala
     * @return
     */
    private Map<String, Object> crearEventoSolicitarCrearSala(Sala sala) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "CrearSalaSolicitud");
        sala.setJugadores(Arrays.asList(this.jugador));
        mapa.put("sala", sala);

        return mapa;
    }

    private Map<String, Object> crearEventoSolicitarSalasDisponibles() {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "SolicitarSalasDisponibles");

        return mapa;
    }

    @Override
    public void actualizar(Map evento) {
        // TODO: Detectar que evento es y actuar en consecuencia...
        System.out.println(evento.toString());
        System.out.println("HERE");

        String nombreEvento = (String) evento.get("nombre_evento");

        if (nombreEvento == "evento") {
            MediadorPantallas.getInstance().mostrarSalaEspera();
        }
    }
}
