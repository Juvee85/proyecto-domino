
package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Representa una sala de juego
 * @author Equipo 1
 */
public class Sala implements Serializable {
    @JsonProperty("info_partida")
    private Partida partida;
    @JsonProperty("nombre_sala")
    private String nombre;
    @JsonProperty("host")
    private String host;
    @JsonProperty("puerto")
    private Integer puerto;
    @JsonProperty("contrasena")
    private String contrasena;
    @JsonProperty("tiene_contrasena")
    private boolean tieneContrasena;
    
    /**
     * Crea una sala nueva sin contrasena por defecto
     * @param anfitrion Anfitrion de la sala
     */
    public Sala(Jugador anfitrion) {
        this.partida = new Partida(anfitrion);
        this.tieneContrasena = false;
    }

    /**
     * Devuelve la informacion de la partida
     * @return Partida que aloja la sala
     */
    public Partida getPartida() {
        return partida;
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
    public Integer getPuerto() {
        return puerto;
    }

    /**
     * Establece el puerto de la sala
     *
     * @param puerto el puerto a establecer.
     */
    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }
}
