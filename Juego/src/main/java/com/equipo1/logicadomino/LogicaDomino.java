package com.equipo1.logicadomino;

import DTOS.JugadorDTO;
import DTOS.SalaDTO;
import com.equipo1.convertidores.FichaConverter;
import com.equipo1.convertidores.JugadorConverter;
import com.equipo1.convertidores.SalaConverter;
import com.equipo1.convertidores.TableroConverter;
import conexion.Conexion;
import entidades.Jugador;
import entidades.Partida;
import entidades.Pozo.Ficha;
import entidades.Sala;
import entidades.Tablero;
import entidades.TrenFichas;
import filtro.FiltroEventos;
import interfacesObservador.ObservadorAbrirPantallaCrearSala;
import interfacesObservador.ObservadorAbrirPantallaSalasDisponibles;
import interfacesObservador.ObservadorAnhadirFicha;
import interfacesObservador.ObservadorCambiarEstadoListo;
import interfacesObservador.ObservadorRegresarPantallaAnterior;
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
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;
import mediador.MediadorPantallas;
import observador.ObservadorConexion;

/**
 *
 * @author
 */
public class LogicaDomino implements ObservadorConexion {

    private Partida partida;
    private Tablero tablero;
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

        ObservadorAbrirPantallaCrearSala observadorCrearSala = this::crearSala;

        ObservadorAbrirPantallaSalasDisponibles observadorSalasDisponibles = this::obtenerSalasDisponibles;

        MediadorPantallas.getInstance().mostrarMenuPrincipal(observadorCrearSala, observadorSalasDisponibles);
    }

    public void crearSala() {
        filtro.restringirEventosPorEstado(FiltroEventos.Estado.CREAR_SALA);

        ObservadorCrearSala crearSala
                = (SalaDTO salaDTO, String nombreJugador) -> {
                    sala = new SalaConverter().convertFromDTO(salaDTO);
                    jugador = new Jugador();
                    jugador.setNombre(nombreJugador);
                    jugador.setNumero(0);
                    jugador.setAvatar("");
                    jugador.setListo(false);
                    jugador.esAnfitrion(true);

                    System.out.println("### crearSala: %s".formatted(sala));

                    try {
                        conexion.enviarEvento(crearEventoSolicitarCrearSala(sala));
                    } catch (IOException ex) {
                        Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };

        ObservadorRegresarPantallaAnterior observadorRegresar = this::inicio;

        MediadorPantallas.getInstance().mostrarMenuPantallaCrearSala(crearSala, observadorRegresar);
    }

    public void obtenerSalasDisponibles() {
        filtro.restringirEventosPorEstado(FiltroEventos.Estado.SALAS_DISPONIBLES);

        try {
            conexion.enviarEvento(crearEventoSolicitarSalasDisponibles());
        } catch (IOException ex) {
            Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param evento
     */
    @Override
    public void actualizar(Map<String, Object> evento) {
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

                if (salasEncontradas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron salas abiertas en el servidor... Intentelo mas tarde.", "Unirse a Salas", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SalaConverter convertidorSala = new SalaConverter();

                this.salasDisponibles = salasEncontradas;

                List<SalaDTO> salasDTO = new ArrayList<>();
                this.salasDisponibles.forEach(salaEncontrada -> salasDTO.add(convertidorSala.convertFromEntity(salaEncontrada)));

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
                    Jugador jugadorEnSala = obtenerJugadorDesdeMapa(mapaJugador);
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
                if (!validarSalaCorrecta(nombreSala)) {
                    return;
                }

                Jugador jugadorNuevo = new Jugador();

                if (evento.get("jugador") instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> jugadorMap = (LinkedHashMap<String, Object>) evento.get("jugador");
                    jugadorNuevo.setNombre((String) jugadorMap.get("nombre"));
                    jugadorNuevo.setAvatar((String) jugadorMap.get("avatar"));
                    jugadorNuevo.setNumero(0);

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

                if (!validarSalaCorrecta(nombreSala)) {
                    return;
                }

                if (this.jugador == null) {
                    return;
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
                if (!validarSalaCorrecta(nombreSala)) {
                    return;
                }

                /*
                 * Descartar evento en caso de que el cambio se duplique
                 * (que no se reciba el evento que nosotros mismos mandamos)
                 */
                if (!validarJugadorDiferente(nombreJugador)) {
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

                if (jugador.esAnfitrion() && sala.getJugadores().size() > 1 && sala.getJugadores().stream().allMatch(j -> j.estaListo())) {

                    try {
                        conexion.enviarEvento(crearEventoIniciarPartidaSolicitud());
                    } catch (IOException ex) {
                        Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            break;

            case "IniciarPartidaRespuesta": {
                filtro.restringirEventosPorEstado(FiltroEventos.Estado.EN_PARTIDA);
                Map<String, Object> salaIniciar = (Map<String, Object>) evento.get("sala");
                int cantidadFichasRestantes = (int) evento.get("fichas_restantes");

                sala = obtenerSalaDesdeMapa(salaIniciar);

                Jugador jugadorNuevo = sala.getJugadores()
                        .stream()
                        .filter(j -> j.getNombre().equals(jugador.getNombre()))
                        .findFirst()
                        .orElse(null);

                if (jugadorNuevo == null) {
                    return;
                }

                this.jugador = jugadorNuevo;

                ObservadorAnhadirFicha observador = ((jugador, ficha, direccion) -> {
                    try {
                        anhadirFichaTablero(new JugadorConverter().convertFromDTO(jugador),
                                new FichaConverter().convertFromDTO(ficha), direccion);

                        MediadorPantallas.getInstance().actualizarFichaAgregada(new TableroConverter().convertFromEntity(tablero),
                                new JugadorConverter().createFromEntities(sala.getJugadores()));

                        conexion.enviarEvento(crearEventoJugarFicha(new FichaConverter().convertFromDTO(ficha), direccion));
                    } catch (IOException ex) {
                        Logger.getLogger(LogicaDomino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                MediadorPantallas.getInstance().mostrarPantallaTableroJuego(new JugadorConverter().createFromEntities(sala.getJugadores()),
                        new JugadorConverter().convertFromEntity(jugador),
                        observador, cantidadFichasRestantes);

                tablero = new Tablero(new Ficha(6, 6));
                MediadorPantallas.getInstance().actualizarFichaAgregada(new TableroConverter().convertFromEntity(tablero),
                        new JugadorConverter().createFromEntities(sala.getJugadores()));
            }
            break;

            case "PrimeraFichaAgregada": {
                Map<String, Object> mapaJugador = (Map<String, Object>) evento.get("jugador");
                String nombreSala = (String) evento.get("sala");
                Map<String, Object> mapaFicha = (Map<String, Object>) evento.get("ficha");

                Jugador jugadorJugada = obtenerJugadorDesdeMapa(mapaJugador);
                Ficha fichaAgregada = obtenerFichaDesdeMapa(mapaFicha);

                String nombreJugador = jugadorJugada.getNombre();

                if (!validarSalaCorrecta(nombreSala)) {
                    return;
                }
                if (!validarJugadorDiferente(nombreJugador)) {
                    return;
                }

                this.tablero = new Tablero(fichaAgregada);

                MediadorPantallas.getInstance().actualizarFichaAgregada(new TableroConverter().convertFromEntity(tablero),
                        new JugadorConverter().createFromEntities(sala.getJugadores()));
            }
            break;
            case "FichaAgregadaATablero": {
                Map<String, Object> mapaJugador = (Map<String, Object>) evento.get("jugador");
                String nombreSala = (String) evento.get("sala");
                Map<String, Object> mapaFicha = (Map<String, Object>) evento.get("ficha");
                String direccion = (String) evento.get("direccion");
                String turnoJugador = (String) evento.get("turno_actual");

                Jugador jugadorJugada = obtenerJugadorDesdeMapa(mapaJugador);
                Ficha fichaAgregada = obtenerFichaDesdeMapa(mapaFicha);

                String nombreJugador = jugadorJugada.getNombre();

                if (!validarSalaCorrecta(nombreSala)) {
                    return;
                }
                if (!validarJugadorDiferente(nombreJugador)) {
                    return;
                }
                
                System.out.println("### TURNO AHORA !!! %s".formatted(turnoJugador));
                
                
                if (turnoJugador.equals(this.jugador.getNumero())) {
                    // habilitar seleccion de ficha...
                    System.out.println("### TE TOCA!!! %s".formatted(turnoJugador));
                }

                System.out.println("### JUGADOR: %s".formatted(jugadorJugada));
                System.out.println("### DIRECCIONÑ %s".formatted(direccion));
                System.out.println("### FICHAÑ %s".formatted(fichaAgregada));
                System.out.println("### NOMBRE SALAÑ %s".formatted(nombreJugador));
                
                anhadirFichaTablero(jugadorJugada, fichaAgregada, direccion);

                MediadorPantallas.getInstance().actualizarFichaAgregada(new TableroConverter().convertFromEntity(tablero),
                        new JugadorConverter().createFromEntities(sala.getJugadores()));
            }
            break;
        }

    }

    private boolean validarSalaCorrecta(String nombreSala) {
        if (this.sala != null) {
            if (!this.sala.getNombre().equals(nombreSala)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean validarJugadorDiferente(String nombreJugador) {
        if (this.jugador != null) {
            if (this.jugador.getNombre().equals(nombreJugador)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public void anhadirFichaTablero(Jugador jugador, Ficha ficha, String direccion) {
        if (direccion.equalsIgnoreCase("Derecha") && tablero.agregarFichaExtremoDerecho(ficha)) {
            jugador.sacarFicha(ficha);
            List<Jugador> jugadores = sala.getJugadores().stream().map(j -> (j.getNombre().equals(jugador.getNombre()) ? jugador : j))
                    .collect(toList());
            sala.setJugadores(jugadores);
        }
        if (direccion.equalsIgnoreCase("izquierda") && tablero.agregarFichaExtremoIzquierdo(ficha)) {
            jugador.sacarFicha(ficha);
            List<Jugador> jugadores = sala.getJugadores().stream().map(j -> (j.getNombre().equals(jugador.getNombre()) ? jugador : j))
                    .collect(toList());
            sala.setJugadores(jugadores);
        }
    }

    private Tablero obtenerTableroDesdeMapa(Map<String, Object> mapaTablero) {
        Tablero tableroConvertido = new Tablero();
        tableroConvertido.setFichas((TrenFichas) mapaTablero.get("tren_fichas"));

        return tableroConvertido;
    }

    private Sala obtenerSalaDesdeMapa(Map<String, Object> mapaSala) {
        Sala salaConvertida = new Sala();
        List<Jugador> jugadoresEnSala = new ArrayList<>();
        salaConvertida.setJugadoresEnSala((int) mapaSala.get("jugadores_en_sala"));
        salaConvertida.setNombre((String) mapaSala.get("nombre_sala"));
        salaConvertida.setMaxJugadores((int) mapaSala.get("max_jugadores"));

        List<Map<String, Object>> mapasJugadores = (List<Map<String, Object>>) mapaSala.get("jugadores");

        for (Map<String, Object> mapaJugador : mapasJugadores) {
            jugadoresEnSala.add(obtenerJugadorDesdeMapa(mapaJugador));
        }

        salaConvertida.setJugadores(jugadoresEnSala);

        return salaConvertida;
    }

    private Jugador obtenerJugadorDesdeMapa(Map<String, Object> mapaJugador) {
        Jugador jugadorConvertido = new Jugador();
        jugadorConvertido.setNombre((String) mapaJugador.get("nombre"));
        jugadorConvertido.setAvatar((String) mapaJugador.get("avatar"));
        jugadorConvertido.setNumero((int) mapaJugador.get("numero_jugador"));
        jugadorConvertido.setListo((boolean) mapaJugador.get("listo"));
        jugadorConvertido.esAnfitrion((boolean) mapaJugador.get("es_anfitrion"));

        List<Map<String, Object>> mapasFichas = (List<Map<String, Object>>) mapaJugador.get("fichas");
        List<Ficha> fichasMano = new ArrayList<>();
        for (Map<String, Object> mapaFicha : mapasFichas) {
            fichasMano.add(obtenerFichaDesdeMapa(mapaFicha));
        }

        jugadorConvertido.asignarFichas(fichasMano);

        return jugadorConvertido;
    }

    private Ficha obtenerFichaDesdeMapa(Map<String, Object> mapaFicha) {
        int puntosCabeza = (int) mapaFicha.get("puntos_cabeza");
        int puntosCola = (int) mapaFicha.get("puntos_cola");

        return new Ficha(puntosCabeza, puntosCola);
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

                if (jugador.esAnfitrion() && sala.getJugadores().size() > 1 && sala.getJugadores().stream().allMatch(j -> j.estaListo())) {
                    conexion.enviarEvento(crearEventoIniciarPartidaSolicitud());
                }
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

        ObservadorRegresarPantallaAnterior observadorRegresar = this::inicio;

        MediadorPantallas.getInstance().mostrarPantallaSalasDisponibles(salas, observadorUnirASala, observadorRegresar);
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
        mapa.put("contraseña", contrasenha);
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
    private Map<String, Object> crearEventoJugarFicha(Ficha ficha, String direccion) {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "JugarFichaSolicitud");
        mapa.put("sala", sala.getNombre());
        mapa.put("jugador", jugador);
        mapa.put("ficha", ficha);
        mapa.put("direccion", direccion);

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

    private Map<String, Object> crearEventoIniciarPartidaSolicitud() {
        HashMap<String, Object> mapa = new HashMap<>();

        mapa.put("nombre_evento", "IniciarPartidaSolicitud");
        mapa.put("sala", this.sala);

        return mapa;
    }
}
