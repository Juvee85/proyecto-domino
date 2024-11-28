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
import filtro.FiltroEventos;
import interfacesObservador.ObservadorAbrirPantallaCrearSala;
import interfacesObservador.ObservadorAbrirPantallaSalasDisponibles;
import interfacesObservador.ObservadorAbrirPantallaUnirASala;
import interfacesObservador.ObservadorCambiarEstadoListo;
import interfacesObservador.ObservadorSalirSala;
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
    private FiltroEventos filtro = FiltroEventos.getInstance();

    public LogicaDomino() {
        this.conexion = new Conexion();
        this.conexion.agregarObservador(this);
        this.conexion.start();
    }

    /**
     *
     */
    public void inicio() {

        filtro.restringirEventosPorEstado(FiltroEventos.Estado.INICIO);

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

        // TODO: RESTRINGIR FILTRO "INICIO"
        filtro.restringirEventosPorEstado(FiltroEventos.Estado.CREAR_SALA);

        ObservadorCrearSala crearSala
                = (SalaDTO salaDTO, String nombreJugador) -> {
                    sala = new SalaConverter().convertFromDTO(salaDTO);
                    jugador = new Jugador();
                    jugador.setNombre(nombreJugador);
                    jugador.setNumero(0);
                    jugador.setAvatar("");
                    jugador.setListo(false);

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
        // TODO: RESTRINGIR FILTRO SALAS_DISPONIBLES
        filtro.restringirEventosPorEstado(FiltroEventos.Estado.SALAS_DISPONIBLES);

        try {
            conexion.enviarEvento(crearEventoSolicitarSalasDisponibles());
        } catch (IOException ex) {
            Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @Override
    public void actualizar(Map evento) {
        // TODO: Detectar que evento es y actuar en consecuencia...
        System.out.println(evento.toString());
        System.out.println("HERE");

        String nombreEvento = (String) evento.get("nombre_evento");
        switch (nombreEvento) {
            case "CrearSalaRespuesta":
                desplegarPantallaSalaEspera(Arrays.asList(new JugadorConverter().convertFromEntity(this.jugador)));

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
                desplegarPantallaSalaEspera(new JugadorConverter().createFromEntities(jugadoresEnSala));
            }
            break;
            case "JugadorUnidoASala": {

                /**
                 * Cuando un nuevo jugador se une a la sala...
                 */
                String nombreSala = (String) evento.get("nombre_sala");

                // si no hay sala es porque no se esta en esta parte del flujo del programa...
                if (sala != null) {
                    if (!sala.getNombre().equals(nombreSala)) {
                        return;
                    }
                } else {
                    return;
                }

                Jugador jugadorNuevo = new Jugador();

                if (evento.get("jugador") instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> jugadorMap = (LinkedHashMap<String, Object>) evento.get("jugador");
                    jugadorNuevo.setNombre((String) jugadorMap.get("nombre"));
                    jugadorNuevo.setAvatar((String) jugadorMap.get("avatar"));
                    jugadorNuevo.setNumero(0);
                    // Configura los demás campos de la clase Jugador...
                }

                String nombreJugador = jugadorNuevo.getNombre();

                // verificar si el jugador ya esta en la sala
                boolean jugadorRegistrado = this.sala.getJugadores()
                        .stream()
                        .anyMatch(j -> j.getNombre().equals(nombreJugador));

                if (!jugadorRegistrado) {
                    List<Jugador> listaNuevaJugadores = new ArrayList<>(this.sala.getJugadores());
                    listaNuevaJugadores.add(jugadorNuevo);
                    this.sala.setJugadores(listaNuevaJugadores);
                }
                JugadorConverter convertidor = new JugadorConverter();

                // convierte a todos los jugadores a DTO
                List<JugadorDTO> nuevaListaJugadores = this.sala.getJugadores()
                        .stream()
                        .map(convertidor::convertFromEntity)
                        .collect(Collectors.toList());
                MediadorPantallas.getInstance().actualizarPantallaSalaEspera(nuevaListaJugadores);
            }
            break;
            case "JugadorAbandonaSala": {
                /**
                 * Se actualiza la tabla de los jugadores que siguen en la sala
                 * de espera
                 */
                String nombreSala = (String) evento.get("nombre_sala");
                String nombreJugador = (String) evento.get("id_jugador");

                // si no hay sala es porque no se esta en esta parte del flujo del programa...
                if (sala != null) {
                    if (!sala.getNombre().equals(nombreSala)) {
                        return;
                    }
                } else {
                    return;
                }

                if (this.jugador == null) {
                    return;
                }

                // si el anfitrion se va elimina la sala del repositorio...
                if (this.jugador.getNombre().equals(nombreJugador)) {
                    //return;
                }

                System.out.println(nombreJugador);

                // verificar si el jugador ya esta en la sala
                Jugador jugador = this.sala.getJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombreJugador))
                        .findFirst()
                        .orElse(null);
                if (jugador == null) {
                    return;
                }

                // quita al jugador con el nombre especificado de la lista...
                if (this.sala.getJugadores().removeIf(j -> j.getNombre().equals(nombreJugador))) {
                    System.out.println("### Se elimino el jugador de la lista de jugadores interna...");
                }

                JugadorConverter convertidor = new JugadorConverter();

                // convierte a todos los jugadores a DTO
                List<JugadorDTO> nuevaListaJugadores = this.sala.getJugadores()
                        .stream()
                        .map(j -> convertidor.convertFromEntity(j))
                        .collect(Collectors.toList());
                MediadorPantallas.getInstance().actualizarPantallaSalaEspera(nuevaListaJugadores);
            }
            break;
            case "JugadorCambioEstadoListo": {

                /**
                 * Cuando un jugador en la sala cambia su estado empezar la
                 * partida.
                 */
                JugadorConverter convertidor = new JugadorConverter();

                String nombreSala = (String) evento.get("nombre_sala");
                String nombreJugador = (String) evento.get("id_jugador");
                Boolean estaListo = (Boolean) evento.get("listo");

                /*
                 * Descartar evento en caso de no estar en una sala 
                 */
                if (this.sala != null) {
                    if (!this.sala.getNombre().equals(nombreSala)) {
                        return;
                    }
                } else {
                    return;
                }

                /*
                 * Descartar evento en caso de que el cambio se duplique
                 * (que no se reciba el evento que nosotros mismos mandamos)
                 */
                if (this.jugador != null) {
                    if (this.jugador.getNombre().equals(nombreJugador)) {
                        return;
                    }
                } else {
                    return;
                }

                // verificar si el jugador ya esta en la sala
                Jugador jugadorEncontrado = this.sala.getJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombreJugador))
                        .findFirst()
                        .orElse(null);
                if (jugadorEncontrado == null) {
                    return;
                }

                jugadorEncontrado.setListo(estaListo);

                int posicion = this.sala.getJugadores().indexOf(jugadorEncontrado);

                if (posicion < 0) {
                    System.out.println("### No se encontro el jugador a cambiar el estado a LISTO");
                    return;
                }

                Jugador anteriorJugador = this.sala.getJugadores().set(posicion, jugadorEncontrado);
                if (anteriorJugador == null) {
                    System.out.println("### No se pudo agregar el jugador a la lista de jugadores");
                    return;
                }

                // convierte a todos los jugadores a DTO
                List<JugadorDTO> nuevaListaJugadores = this.sala.getJugadores()
                        .stream()
                        .map(j -> convertidor.convertFromEntity(j))
                        .collect(Collectors.toList());
                MediadorPantallas.getInstance().actualizarPantallaSalaEspera(nuevaListaJugadores);
            }
            break;
        }

    }

    public void desplegarPantallaSalaEspera(List<JugadorDTO> listaJugadores) {

        filtro.restringirEventosPorEstado(FiltroEventos.Estado.SALA_ESPERA);

        ObservadorSalirSala observadorSalirSala = () -> {
            try {
                conexion.enviarEvento(crearEventoSolicitarSalirSala());
                obtenerSalasDisponibles();
            } catch (IOException ex) {
                Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        ObservadorCambiarEstadoListo observadorCambiarEstado = () -> {
            try {
                if (jugador == null) {
                    return;
                }

                jugador.setListo(!jugador.estaListo());

                boolean estadoJugador = jugador.estaListo();
                String nombreJugador = jugador.getNombre();

                conexion.enviarEvento(crearEventoJugadorListoEmpezarPartida(nombreJugador, estadoJugador));

                System.out.println("####>>>>> AFTER");

                // se cambia el estado del jugador en local...
                JugadorConverter convertidor = new JugadorConverter();

                Jugador jugadorEncontrado = sala.getJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombreJugador))
                        .findFirst()
                        .orElse(null);

                if (jugadorEncontrado == null) {
                    System.out.println("#### NO ENCONTRADO!!!!");
                    return;
                }
                
                int posicion = sala.getJugadores().indexOf(jugadorEncontrado);

                if (posicion < 0) {
                    System.out.println("### No se encontro el jugador a cambiar el estado a LISTO");
                    return;
                }

                Jugador anteriorJugador = sala.getJugadores().set(posicion, jugador);
                if (anteriorJugador == null) {
                    System.out.println("### No se pudo agregar el jugador a la lista de jugadores");
                    return;
                }

                // se convierte la lsita de jugadores y se actualiza la pantalla
                // convierte a todos los jugadores a DTO
                List<JugadorDTO> nuevaListaJugadores = sala.getJugadores()
                        .stream()
                        .map(convertidor::convertFromEntity)
                        .collect(Collectors.toList());
                MediadorPantallas.getInstance().actualizarPantallaSalaEspera(nuevaListaJugadores);
            } catch (IOException ex) {
                Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        MediadorPantallas.getInstance().mostrarSalaEspera(listaJugadores, observadorSalirSala, observadorCambiarEstado);
    }

    public void desplegarSalasDisponibles(List<SalaDTO> salas) {
        ObservadorUnirASala observadorUnirASala = (nombreJugador, contrasenha, salaDTO) -> {
            Sala salaUnir = new SalaConverter().convertFromDTO(salaDTO);
            jugador = new Jugador();
            jugador.setNombre(nombreJugador);
            try {
                conexion.enviarEvento(crearEventoSolicitarUnirASala(nombreJugador, contrasenha, salaUnir));
            } catch (IOException ex) {
                Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        MediadorPantallas.getInstance().mostrarPantallaSalasDisponibles(salas, observadorUnirASala);
    }

    /**
     * Crea un evento para indicar que el jugador se quiere unir a una sala.
     *
     * @param nombreJugador Nombre del jugador en local.
     * @param contrasenha Contrasena de la sala.
     * @param salaUnir Sala a unirse.
     * @return Map con la informacion del evento
     */
    private Map<String, Object> crearEventoSolicitarUnirASala(String nombreJugador, String contrasenha, Sala salaUnir) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "UnirseSalaSolicitud");
        mapa.put("id_jugador", nombreJugador);
        mapa.put("nombre_sala", salaUnir.getNombre());

        return mapa;
    }

    private Map<String, Object> crearEventoSolicitarSalirSala() {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "AbandonarSalaSolicitud");
        mapa.put("id_jugador", jugador.getNombre());
        mapa.put("nombre_sala", sala.getNombre());

        // se quita esta informacion para que no ejecute el evento de JugadorAbandonaSala
        this.jugador = null;
        this.sala = null;
        this.partida = null;

        return mapa;
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
     * Crea un evento para solicitar la creacion de una nueva sala por parte del
     * jugador local.
     *
     * @param sala Informacion de la sala a crear
     * @return Map con la informacion del evento
     */
    private Map<String, Object> crearEventoSolicitarCrearSala(Sala sala) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "CrearSalaSolicitud");
        sala.setJugadores(Arrays.asList(this.jugador));
        mapa.put("sala", sala);

        return mapa;
    }

    /**
     * Crea un evento para cuando solicitamos ver las salas abiertas en el
     * juego.
     *
     * @return Map con la informacion del evento.
     */
    private Map<String, Object> crearEventoSolicitarSalasDisponibles() {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "ObtenerSalasSolicitud");

        return mapa;
    }

    /**
     * Crea un evento para indicar que estamos listos para iniciar la partida.
     *
     * @param nombreJugador Nombre del jugador en local.
     * @param estadoListo Estado del jugador.
     * @return Map con la informacion del evento.
     */
    private Map<String, Object> crearEventoJugadorListoEmpezarPartida(String nombreJugador, boolean estadoListo) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "CambiarEstadoJugadorListoSolicitud");
        mapa.put("id_jugador", nombreJugador);
        mapa.put("nombre_sala", this.sala.getNombre());
        mapa.put("listo", estadoListo);

        return mapa;
    }
}
