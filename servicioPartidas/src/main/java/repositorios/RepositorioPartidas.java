package repositorios;

import entidades.Jugador;
import repositorios.excepciones.RepositorioPartidasException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio para gestionar sesiones de juego de dominó, incluyendo puntajes y
 * estado del juego.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class RepositorioPartidas {

    private static volatile RepositorioPartidas instance;
    private final Map<String, PartidaInfo> partidas;

    /**
     * Clase interna para almacenar información de las partidas, incluyendo los
     * puntajes.
     */
    private static class PartidaInfo {

        private final Jugador anfitrion;
        private final List<String> jugadores;
        private final Map<String, Integer> puntajes;
        private boolean finalizada;
        private String ganador;

        PartidaInfo(Jugador anfitrion) {
            this.anfitrion = Objects.requireNonNull(anfitrion, "Anfitrión no puede ser null");
            this.jugadores = Collections.synchronizedList(new ArrayList<>());
            this.puntajes = new ConcurrentHashMap<>();
            this.jugadores.add(anfitrion.getNombre());
            this.puntajes.put(anfitrion.getNombre(), 0);
            this.finalizada = false;
        }
    }

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private RepositorioPartidas() {
        this.partidas = new ConcurrentHashMap<>();
    }

    /**
     * Obtiene la instancia única del repositorio.
     *
     * @return Instancia de RepositorioPartidas
     */
    public static RepositorioPartidas getInstance() {
        RepositorioPartidas result = instance;
        if (result == null) {
            synchronized (RepositorioPartidas.class) {
                result = instance;
                if (result == null) {
                    instance = result = new RepositorioPartidas();
                }
            }
        }
        return result;
    }

    /**
     * Crea una nueva sesión de juego.
     *
     * @param salaId Identificador de la sala de juego
     * @param anfitrion Jugador anfitrión
     * @throws RepositorioPartidasException Si la sala ya existe
     */
    public void crearPartida(String salaId, Jugador anfitrion) throws RepositorioPartidasException {
        Objects.requireNonNull(salaId, "ID de sala no puede ser null");
        Objects.requireNonNull(anfitrion, "Anfitrión no puede ser null");

        if (partidas.containsKey(salaId)) {
            throw new RepositorioPartidasException("Ya existe una partida con el ID: " + salaId);
        }

        PartidaInfo partidaInfo = new PartidaInfo(anfitrion);
        partidas.put(salaId, partidaInfo);
    }

    /**
     * Añade un jugador a una partida existente.
     *
     * @param salaId Identificador de la sala de juego
     * @param nombreJugador Nombre del jugador a añadir
     * @throws RepositorioPartidasException Si la sala no existe o ya está finalizada
     */
    public void añadirJugador(String salaId, String nombreJugador) throws RepositorioPartidasException {
        Objects.requireNonNull(salaId, "ID de sala no puede ser null");
        Objects.requireNonNull(nombreJugador, "Nombre de jugador no puede ser null");

        PartidaInfo partidaInfo = obtenerPartida(salaId);

        if (partidaInfo.finalizada) {
            throw new RepositorioPartidasException("No se pueden añadir jugadores a una partida finalizada");
        }

        synchronized (partidaInfo.jugadores) {
            if (!partidaInfo.jugadores.contains(nombreJugador)) {
                partidaInfo.jugadores.add(nombreJugador);
                partidaInfo.puntajes.put(nombreJugador, 0);
            }
        }
    }

    /**
     * Actualiza el puntaje de un jugador en una partida.
     *
     * @param salaId Identificador de la sala de juego
     * @param nombreJugador Nombre del jugador
     * @param puntaje Nuevo puntaje
     * @throws RepositorioPartidasException Si la sala no existe, está finalizada o el jugador no está en la partida
     */
    public void actualizarPuntaje(String salaId, String nombreJugador, int puntaje)
            throws RepositorioPartidasException {
        Objects.requireNonNull(salaId, "ID de sala no puede ser null");
        Objects.requireNonNull(nombreJugador, "Nombre de jugador no puede ser null");

        PartidaInfo partidaInfo = obtenerPartida(salaId);

        if (partidaInfo.finalizada) {
            throw new RepositorioPartidasException("No se puede actualizar puntaje en una partida finalizada");
        }

        if (!partidaInfo.jugadores.contains(nombreJugador)) {
            throw new RepositorioPartidasException("El jugador no forma parte de esta partida");
        }

        if (puntaje < 0) {
            throw new RepositorioPartidasException("El puntaje no puede ser negativo");
        }

        partidaInfo.puntajes.put(nombreJugador, puntaje);
    }

    /**
     * Obtiene el puntaje actual de un jugador.
     *
     * @param salaId Identificador de la sala de juego
     * @param nombreJugador Nombre del jugador
     * @return Puntaje actual
     * @throws RepositorioPartidasException Si la sala o el jugador no existen
     */
    public int obtenerPuntaje(String salaId, String nombreJugador) throws RepositorioPartidasException {
        PartidaInfo partidaInfo = obtenerPartida(salaId);

        if (!partidaInfo.puntajes.containsKey(nombreJugador)) {
            throw new RepositorioPartidasException("El jugador no forma parte de esta partida");
        }

        return partidaInfo.puntajes.get(nombreJugador);
    }

    /**
     * Obtiene todos los puntajes de una partida.
     *
     * @param salaId Identificador de la sala de juego
     * @return Mapa con nombres de jugadores y sus puntajes
     * @throws RepositorioPartidasException Si la sala no existe
     */
    public Map<String, Integer> obtenerPuntajes(String salaId) throws RepositorioPartidasException {
        return new HashMap<>(obtenerPartida(salaId).puntajes);
    }

    /**
     * Finaliza una partida.
     *
     * @param salaId Identificador de la sala de juego
     * @param ganador Nombre del ganador
     * @throws RepositorioPartidasException Si la sala no existe o ya está finalizada
     */
    public void finalizarPartida(String salaId, String ganador) throws RepositorioPartidasException {
        Objects.requireNonNull(salaId, "ID de sala no puede ser null");
        Objects.requireNonNull(ganador, "Nombre del ganador no puede ser null");

        PartidaInfo partidaInfo = obtenerPartida(salaId);

        if (partidaInfo.finalizada) {
            throw new RepositorioPartidasException("La partida ya está finalizada");
        }

        if (!partidaInfo.jugadores.contains(ganador)) {
            throw new RepositorioPartidasException("El ganador no forma parte de esta partida");
        }

        partidaInfo.finalizada = true;
        partidaInfo.ganador = ganador;
    }

    /**
     * Obtiene la información de una partida.
     *
     * @param salaId Identificador de la sala de juego
     * @return Información de la partida
     * @throws RepositorioPartidasException Si la sala no existe
     */
    private PartidaInfo obtenerPartida(String salaId) throws RepositorioPartidasException {
        PartidaInfo partidaInfo = partidas.get(salaId);

        if (partidaInfo == null) {
            throw new RepositorioPartidasException("No existe una partida con el ID: " + salaId);
        }

        return partidaInfo;
    }

    /**
     * Obtiene la lista de jugadores de una partida.
     *
     * @param salaId Identificador de la sala de juego
     * @return Lista de nombres de jugadores
     * @throws RepositorioPartidasException Si la sala no existe
     */
    public List<String> obtenerJugadores(String salaId) throws RepositorioPartidasException {
        return new ArrayList<>(obtenerPartida(salaId).jugadores);
    }

    /**
     * Verifica si una partida está finalizada.
     *
     * @param salaId Identificador de la sala de juego
     * @return true si la partida está finalizada, false de lo contrario
     * @throws RepositorioPartidasException Si la sala no existe
     */
    public boolean estaFinalizada(String salaId) throws RepositorioPartidasException {
        return obtenerPartida(salaId).finalizada;
    }

    /**
     * Obtiene al ganador de una partida finalizada.
     *
     * @param salaId Identificador de la sala de juego
     * @return Nombre del ganador
     * @throws RepositorioPartidasException Si la sala no existe o si la partida no está finalizada
     */
    public String obtenerGanador(String salaId) throws RepositorioPartidasException {
        PartidaInfo partidaInfo = obtenerPartida(salaId);

        if (!partidaInfo.finalizada) {
            throw new RepositorioPartidasException("La partida aún no está finalizada");
        }

        return partidaInfo.ganador;
    }
}
