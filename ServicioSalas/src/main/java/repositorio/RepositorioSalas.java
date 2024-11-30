package repositorio;

import entidades.Jugador;
import entidades.Sala;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import repositorio.excepciones.RepositorioSalasException;

/**
 * Aloja las salas de partidas de domino activas del juego
 *
 * @author Saul Neri
 */
public class RepositorioSalas {

    private static RepositorioSalas instance;

    private List<Sala> salas;

    /**
     * Constructor privado oculto
     */
    private RepositorioSalas() {
        this.salas = new ArrayList<>();


        Sala s = new Sala();
        s.setNombre("RCS Ack");
        s.setMaxJugadores(4);
        s.setContrasena("12345");
        s.setJugadoresEnSala(1);
        s.setJugadores(new ArrayList<>());

        Sala s2 = new Sala();
        s2.setNombre("Domino Pro");
        s2.setMaxJugadores(3);
        s2.setContrasena(null);
        s2.setJugadoresEnSala(0);
        s2.setJugadores(new ArrayList());
        salas.add(s2);

        Sala s3 = new Sala();
        s3.setNombre("Quick Match");
        s3.setMaxJugadores(2);
        s3.setContrasena("pass2");
        s3.setJugadoresEnSala(2);
        s3.setJugadores(new ArrayList<>());
        salas.add(s3);

// Añade las salas a tu lógica de negocio o lista global
        this.salas.add(s);
        this.salas.add(s2);
        this.salas.add(s3);
    }

    /**
     * Obtiene la instancia del repositorio
     *
     * @return
     */
    public static RepositorioSalas getInstance() {
        if (instance == null) {
            instance = new RepositorioSalas();
        }

        return instance;
    }

    /**
     * Devuelve la sala con el nombre dado si es que existe.
     * @param nombreSala Nombre de la sala a buscar.
     * @return Sala si la encuentra, null en caso contrario
     */
    public Sala existeSala(String nombreSala) {
        return this.salas
                .stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .findFirst()
                .orElse(null);
    }
    

    /**
     * Obtiene todas las salas activas del juego
     *
     * @return Lista de salas
     */
    public List<Sala> getSalas() {
        return this.salas;
    }

    /**
     * Agrega una nueva sala al repositorio
     *
     * @param sala Sala nueva
     * @throws repositorio.excepciones.RepositorioSalasException Si no se puede
     * agregar la sala
     */
    public void agregarSala(Sala sala) throws RepositorioSalasException {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getNombre().equals(sala.getNombre()))
                .findFirst().orElse(null);

        if (encontrada == null) {
            this.salas.add(sala);
            return;
        }

        sala.getJugadores().getFirst().esAnfitrion(true);
        sala.setJugadoresEnSala(sala.getJugadores().size());

        throw new RepositorioSalasException("No se pudo crear la sala debido a un error, es probable que tengas una sala abierta...");
    }

    /**
     * Elimina la sala del repositorio de salas
     *
     * @param nombreSala Nombre de la sala a eliminar
     * @return Sala si se logro eliminar
     * @throws repositorio.excepciones.RepositorioSalasException si no se puede
     * eliminar la sala del sistema
     */
    public Sala eliminarSala(String nombreSala) throws RepositorioSalasException {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .findFirst().orElse(null);

        if (encontrada == null) {
            throw new RepositorioSalasException("La sala que se intenta eliminar no existe...");
        }

        this.salas.remove(encontrada);

        return encontrada;
    }

    /**
     * Elimina a un jugador de la sala, ya sea por peticion o por decision del
     * host
     *
     * @param nombreJugador Nombre del jugador a sacar
     * @param nombreSala Nombre de la sala en cuestion
     * @throws RepositorioSalasException
     */
    public void sacarJugadorDeSala(String nombreJugador, String nombreSala) throws RepositorioSalasException {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .findFirst().orElse(null);

        if (encontrada == null) {
            throw new RepositorioSalasException("La sala no existe...");
        }

        Jugador jugador = encontrada.getJugadores().stream()
                .filter(j -> j.getNombre().equals(nombreJugador))
                .findFirst()
                .orElse(null);

        if (jugador == null) {
            throw new RepositorioSalasException("No existe el jugador especificado en la sala...");
        }

        boolean eliminado = encontrada.getJugadores().remove(jugador);

        if (!eliminado) {
            //throw new RepositorioSalasException("No se pudo sacar al jugador de la sala");
            System.out.println("### NO SE PUDO ELIMINAR AL JUGADOR DE LA SALA");
        } else {
            encontrada.setJugadoresEnSala(encontrada.getJugadoresEnSala() - 1);
        }
    }

    /**
     * Cambia el estado listo de un jugador de una sala con el estado dado.
     *
     * @param nombreSala Nombre de la sala.
     * @param nombreJugador Nombre del jugador en la sala.
     * @param estadoListo Estado del jugador a cambiar.
     * @throws RepositorioSalasException si ocurre un error al cambiar el
     * estado.
     */
    public void cambiarEstadoListoJugador(String nombreSala, String nombreJugador, boolean estadoListo) throws RepositorioSalasException {

        if (nombreSala == null || nombreSala.isBlank() || nombreSala.isEmpty()) {
            throw new RepositorioSalasException("El campo 'nombre_sala' esta vacio, no se pudo cambiar el estado del jugador");
        }

        if (nombreJugador == null || nombreJugador.isBlank() || nombreJugador.isEmpty()) {
            throw new RepositorioSalasException("El campo 'id_jugador' esta vacio, no se pudo cambiar el estado del jugador");
        }

        Sala encontrada = this.salas.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .findFirst().orElse(null);

        if (encontrada == null) {
            throw new RepositorioSalasException("La sala no existe...");
        }

        Jugador jugador = encontrada.getJugadores().stream()
                .filter(j -> j.getNombre().equals(nombreJugador))
                .findFirst()
                .orElse(null);

        if (jugador == null) {
            throw new RepositorioSalasException("No existe el jugador especificado en la sala...");
        }

        jugador.setListo(estadoListo);

        int posicion = encontrada.getJugadores().indexOf(jugador);

        if (posicion < 0) {
            System.out.println("### No se encontro el jugador a cambiar el estado a LISTO");
            throw new RepositorioSalasException("No se pudo cambiar el estado del jugador en la sala.");
        }

        Jugador anteriorJugador = encontrada.getJugadores().set(posicion, jugador);
        if (anteriorJugador == null) {
            System.out.println("### No se pudo agregar el jugador a la lista de jugadores");
            throw new RepositorioSalasException("No se pudo cambiar el estado del jugador en la sala.");
        }
        
        System.out.println("### ESTADO JUGADOR %s: %s".formatted(jugador.getNombre(), estadoListo));
    }

}
