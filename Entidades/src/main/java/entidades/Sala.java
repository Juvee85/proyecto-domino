
package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

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
    @JsonProperty("host")
    private String host;
    @JsonProperty("puerto")
    private int puerto;
    @JsonProperty("contrasena")
    private String contrasena;
    @JsonProperty("tiene_contrasena")
    private boolean tieneContrasena;
    
    /**
     * Crea una sala nueva sin contrasena por defecto
     */
    public Sala() {
        this.tieneContrasena = false;
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
        if (contrasena.isBlank() || contrasena.isEmpty()) {
            this.tieneContrasena = false;
            this.contrasena = null;
        } else if (contrasena.length() >= 5 && contrasena.length() <= 18) {
            this.contrasena = contrasena;
        }
    }

    /**
     * Indica si la sala tiene contrasena o no
     * @return tieneContrasena
     */
    public boolean tieneContrasena() {
        return tieneContrasena;
    }
    
    /**
     * Obtiene el host de la partida
     *
     * @return el host de de la partida
     */
    public String getHost() {
        return host;
    }

    /**
     * Establece el host de la partida
     *
     * @param host el host a establecer.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Obtiene el puerto de la sala
     *
     * @return el puerto la sala
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * Establece el puerto de la sala
     *
     * @param puerto el puerto a establecer.
     */
    public void setPuerto(int puerto) {
        this.puerto = puerto;
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
     * Obtiene la cantidad de jugadores actuales en la sala
     * @return the jugadoresEnSala
     */
    public int getJugadoresEnSala() {
        return jugadoresEnSala;
    }

    /**
     * Asigna la cantidad de jugadores actuales en la sala
     * @param jugadoresEnSala Cantidad de jugadores en la sala
     */
    public void setJugadoresEnSala(int jugadoresEnSala) {
        this.jugadoresEnSala = jugadoresEnSala;
    }
    
    
}
