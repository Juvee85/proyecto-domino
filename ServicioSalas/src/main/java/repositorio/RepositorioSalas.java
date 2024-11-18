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
        s.setJugadores(Arrays.asList());

        Sala s2 = new Sala();
        s2.setNombre("Domino Pro");
        s2.setMaxJugadores(3);
        s2.setContrasena(null);
        s2.setJugadoresEnSala(1);
        s2.setJugadores(Collections.emptyList());
        salas.add(s2);

        Sala s3 = new Sala();
        s3.setNombre("Quick Match");
        s3.setMaxJugadores(2);
        s3.setContrasena("pass2");
        s3.setJugadoresEnSala(2);
        s3.setJugadores(Collections.emptyList());
        salas.add(s3);

        Sala s4 = new Sala();
        s4.setNombre("Classic Domino");
        s4.setMaxJugadores(4);
        s4.setContrasena(null);
        s4.setJugadoresEnSala(1);
        s4.setJugadores(Collections.emptyList());
        salas.add(s4);
        
        Sala s5 = new Sala();
        s5.setNombre("Domino Challenge");
        s5.setMaxJugadores(2);
        s5.setContrasena("challenger");
        s5.setJugadoresEnSala(1);
        s5.setJugadores(Collections.emptyList());
        salas.add(s5);

// Añade las salas a tu lógica de negocio o lista global
        this.salas.addAll(Arrays.asList(s, s2, s3, s4, s5));
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
     * Indica si la partida con la direccion de red dada existe
     *
     * @param nombreSala Nombre de la asala a buscar
     * @return true si existe la partida
     */
    public boolean existePartida(String nombreSala) {
        return this.salas.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .count() > 0;
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

        throw new RepositorioSalasException("No se pudo crear la sala debido a un error, es probable que tengas una sala abierta...");
    }

    /**
     * Elimina la sala del repositorio de salas
     *
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

}
