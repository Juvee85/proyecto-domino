package com.equipo1.logicadomino;

import DTOS.JugadorDTO;
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
import interfacesObservador.ObservadorUnirASala;
import interfacesObservador.salas.ObservadorCrearSala;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
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
    private List<Sala> salasDisponibles;
    private Conexion conexion;

    public LogicaDomino() {
        this.conexion = new Conexion();
        this.conexion.agregarObservador(this);
        this.conexion.start();
    }

    /**
     *
     */
    public void inicio() {
        // TODO: ver si es mejor crear conexion al BUS desde el inicio
        ObservadorAbrirPantallaCrearSala observadorCrearSala = () -> {
            crearSala();
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

                    System.out.println("### crearSala: %s".formatted(sala));

                    try {
                        conexion.enviarEvento(crearEventoSolicitarCrearSala(sala));
                    } catch (IOException ex) {
                        Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };

        MediadorPantallas.getInstance().mostrarMenuPantallaCrearSala(crearSala);
    }

    public void obtenerSalasDisponibles() {
        try {
            conexion.enviarEvento(crearEventoSolicitarSalasDisponibles());
        } catch (IOException ex) {
            Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
        }
        // obtener salas desde observador a conexión
        // Poner lo siguiente dentro del update en el caso que reciba el evento con las salas disponibles
        //List<SalaDTO> salas = new ArrayList<>();
        // Agregar datos de la respuesta
        //MediadorPantallas.getInstance().mostrarPantallaSalasDisponibles(salas);
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

        mapa.put("nombre_evento", "ObtenerSalasSolicitud");

        return mapa;
    }

    @Override
    public void actualizar(Map evento) {
        // TODO: Detectar que evento es y actuar en consecuencia...
        System.out.println(evento.toString());
        System.out.println("HERE");

        String nombreEvento = (String) evento.get("nombre_evento");
        switch (nombreEvento) {
            case "CrearSalaRespuesta":
                MediadorPantallas.getInstance().mostrarSalaEspera(Arrays.asList(new JugadorConverter().convertFromEntity(this.jugador)));
                break;
            case "ObtenerSalasRespuesta": {
                System.out.println("### ObtenerSalasRespuesta CACHADO!!!");
                System.out.println(evento.toString());

                List<Map<String, Object>> mapasSalas = (List<Map<String, Object>>) evento.get("salas");
                List<Sala> salasEncontradas = new ArrayList<>();

                for (Map<String, Object> mapaSala : mapasSalas) {
                    Sala sala = new Sala();
                    sala.setNombre((String) mapaSala.get("nombre_sala"));
                    sala.setMaxJugadores((int) mapaSala.get("max_jugadores"));
                    sala.setJugadoresEnSala((int) mapaSala.get("jugadores_en_sala"));
                    sala.setContrasena((String) mapaSala.get("contrasena"));
                    // Asigna los jugadores de la sala si corresponde
                    salasEncontradas.add(sala);
                }

//                List<Sala> salasEncontradas = (List<Sala>) evento.get("salas");
                if (salasEncontradas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron salas abiertas en el servidor... Intentelo mas tarde.", "Unirse a Salas", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SalaConverter convertidorSala = new SalaConverter();

                this.salasDisponibles = salasEncontradas;

                List<SalaDTO> salasDTO = new ArrayList<>();
                this.salasDisponibles.forEach(sala -> salasDTO.add(convertidorSala.convertFromEntity(sala)));

                desplegarSalasDisponibles(salasDTO);
            }
            break;
            case "UnirseSalaRespuesta": {
                System.out.println("### UnirseSalaRespuesta CACHADO");

                Map<String, Object> mapaSala = (Map<String, Object>) evento.get("sala");
                Sala salaUnir = new Sala();
                List<Jugador> jugadoresEnSala = new ArrayList<>();

                salaUnir.setJugadoresEnSala((int) mapaSala.get("jugadores_en_sala"));
                salaUnir.setNombre((String) mapaSala.get("nombre_sala"));
                salaUnir.setMaxJugadores((int) mapaSala.get("max_jugadores"));

                List<Map<String, Object>> mapasJugadores = (List<Map<String, Object>>) mapaSala.get("jugadores");
                for (Map<String, Object> mapaJugador : mapasJugadores) {
                    Jugador jugadorEnSala = new Jugador();
                    jugadorEnSala.setNombre((String) mapaJugador.get("nombre"));
                    jugadoresEnSala.add(jugadorEnSala);
                }

                salaUnir.setJugadores(jugadoresEnSala);

                sala = salaUnir;
                MediadorPantallas.getInstance().mostrarSalaEspera(new JugadorConverter().createFromEntities(jugadoresEnSala));
            }
            break;
            case "JugadorUnidoASala": {
                
                /**
                 * Cuando un nuevo jugador se une a la sala...
                 */
                
                String nombreSala = (String) evento.get("nombreSala");

                // si no hay sala es porque no se esta en esta parte del flujo del programa...
                if (sala != null) {
                    if (!sala.getNombre().equals(nombreSala)) {
                        return;
                    }
                } else {
                    return;
                }

                final Jugador jugadorNuevo = new Jugador();

                if (evento.get("jugador") instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> jugadorMap = (LinkedHashMap<String, Object>) evento.get("jugador");
                    jugadorNuevo.setNombre((String) jugadorMap.get("nombre"));
                    jugadorNuevo.setAvatar((String) jugadorMap.get("avatar"));
                    jugadorNuevo.setNumero(0);
                    // Configura los demás campos de la clase Jugador...
                } /*else {
                    // Si no es LinkedHashMap, asume que ya es un Jugador
                    jugadorNuevo = (Jugador) evento.get("jugador");
                    MediadorPantallas.getInstance().actualizarPantallaSalaEspera(new JugadorConverter().convertFromEntity(jugadorNuevo));
                }*/
                
                // verificar si el jugador ya esta en la sala
                boolean jugadorRegistrado = this.sala.getJugadores()
                        .stream()
                        .filter(j -> j.getNombre().equals(jugadorNuevo.getNombre()))
                        .findFirst()
                        .orElse(null) != null;
                
                if (!jugadorRegistrado) {
                    this.sala.getJugadores().add(jugador);
                }
                
                JugadorConverter convertidor = new JugadorConverter();
                
                // convierte a todos los jugadores a DTO
                List<JugadorDTO> nuevaListaJugadores = this.sala.getJugadores()
                        .stream()
                        .map(j -> convertidor.convertFromEntity(j))
                        .collect(Collectors.toList());
                
                // actualiza el frame...
                MediadorPantallas.getInstance().actualizarPantallaSalaEspera(nuevaListaJugadores);
            }
            break;
        }

    }

    public void desplegarSalasDisponibles(List<SalaDTO> salas) {
        ObservadorUnirASala observadorUnirASala = (nombreJugador, contrasenha, salaDTO) -> {
            Sala salaUnir = new SalaConverter().convertFromDTO(salaDTO);
            try {
                conexion.enviarEvento(crearEventoSolicitarUnirASala(nombreJugador, contrasenha, salaUnir));
            } catch (IOException ex) {
                Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        MediadorPantallas.getInstance().mostrarPantallaSalasDisponibles(salas, observadorUnirASala);
    }

    private Map<String, Object> crearEventoSolicitarUnirASala(String nombreJugador, String contrasenha, Sala salaUnir) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "UnirseSalaSolicitud");
        mapa.put("id_jugador", nombreJugador);
        mapa.put("nombre_sala", salaUnir.getNombre());

        return mapa;
    }
}
