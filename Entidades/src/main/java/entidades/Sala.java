
package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una sala de juego
 * @author Equipo 1
 */
public class Sala implements Serializable {

    @JsonProperty("nombre_sala")
    private String nombre;
    @JsonProperty("max_jugadores")
    private int maxJugadores;
    @JsonProperty("jugadores_en_sala")
    private int jugadoresEnSala;
    @JsonProperty("numero_de_fichas_por_jugador")
    private int numeroFichasPorJugador;
    @JsonProperty("contrasena")
    private String contrasena;
    @JsonProperty("tiene_contrasena")
    private boolean tieneContrasena;
    @JsonProperty("jugadores")
    private List<Jugador> jugadores;
    
    /**
     * Crea una sala nueva sin contrasena por defecto
     */
    public Sala() {
        this.tieneContrasena = false;
        this.jugadores = new ArrayList<>();
    }

    /**
     * Devuelve el nombre de la sala
     * @return El nombre de la sala
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de la sala
     * @param nombre Nombre de la sala a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la contrasena de la sala
     * @return Contrasena de la sala
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Asigna la contrasena a la sala
     * @param contrasena Contrasena a asignar
     */
    public void setContrasena(String contrasena) {
        /*
        if (contrasena == null) {
            this.tieneContrasena = false;
            this.contrasena = null;
        }
        else if (contrasena.isBlank() || contrasena.isEmpty()) {
            this.tieneContrasena = false;
            this.contrasena = null;
        } else if (contrasena.length() >= 4 && contrasena.length() <= 18) {
            this.contrasena = contrasena;
        }
        */
        this.contrasena = contrasena;
    }

    /**
     * Indica si la sala tiene contrasena o no
     * @return tieneContrasena
     */
    public boolean tieneContrasena() {
        return tieneContrasena;
    }

    /**
     * Obtiene la cantidad maxima de jugadores permitidos en la sala
     * @return Cantidad maxima jugadores
     */
    public int getMaxJugadores() {
        return maxJugadores;
    }

    /**
     * Asigna la cantidad maxima de jugadores en la sala
     * @param maxJugadores Cantidad Maxima jugadores a asignar
     */
    public void setMaxJugadores(int maxJugadores) {
        this.maxJugadores = maxJugadores;
    }

    /**
     * Obtiene la cantidad de fichas repartidas a cada jugador
     * @return Cantidad de fichas a repartir a cada jugador
     */
    public int getNumeroFichasPorJugador() {
        return numeroFichasPorJugador;
    }

    /**
     * Asigna la cantidad de fichas a repartir a cada jugador
     * @param numeroFichasPorJugador Cantidad de fichas a repartir a cada jugador
     */
    public void setNumeroFichasPorJugador(int numeroFichasPorJugador) {
        this.numeroFichasPorJugador = numeroFichasPorJugador;
    }

    /**
     * Obtiene la cantidad de jugadores actuales en la sala
     * @return the jugadoresEnSala
     */
    public int getJugadoresEnSala() {
        this.jugadoresEnSala = (this.jugadores != null) ? this.jugadores.size() : 0;
        return jugadoresEnSala;
    }

    /**
     * Asigna la cantidad de jugadores actuales en la sala
     * @param jugadoresEnSala Cantidad de jugadores en la sala
     */
    public void setJugadoresEnSala(int jugadoresEnSala) {
        this.jugadoresEnSala = jugadoresEnSala;
    }

    /**
     * @return the jugadores
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * @param jugadores the jugadores to set
     */
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    
}
