package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contiene la informacion de puntajes en la partida.
 * @author Equipo 1
 */
public class Partida implements Serializable {
    @JsonProperty("puntajes")
    private Map<String, Integer> puntajes;
    
    /**
     * Crea una nueva partida vacia.
     */
    public Partida() {
        this.puntajes = new HashMap<>();
    }
    
    /**
     * Constructor utilizado para permitir la serializacion
     * @param jugadores Lista de jugadores
     */
    public Partida(List<String> jugadores) {
        this.puntajes = new HashMap<>();
        
        jugadores.forEach(jugador -> {
            this.puntajes.put(jugador, 0);
        });
    }
    
    /**
     * Se crea una partida con la informacion ya cargada de puntajes de los 
     * jugadores.
     * @param puntajes Mapa con los puntajes y jugadores.
     */
    public Partida(Map<String, Integer> puntajes) {
        this.puntajes = puntajes;
    }

    /**
     * Obtiene los puntajes de todos los jugadores.
     * @return Lista de puntajes.
     */
    public Map<String, Integer> getPuntajes() {
        return puntajes;
    }

    /**
     * @param puntajes the puntajes to set
     */
    public void setPuntajes(Map<String, Integer> puntajes) {
        this.puntajes = puntajes;
    }
    
    /**
     * Agrega un nuevo jugador a la partida con el nombre dado.
     * @param jugador Nombre del jugador.
     */
    public void agregarJugador(String jugador) {
        if (!this.puntajes.containsKey(jugador)) {
            this.puntajes.put(jugador, 0);
        }
    }
    
    /**
     * Elimina al jugador de la partida con el nombre especificado.
     * @param jugador Nombre del jugador.
     * @return true Si se pudo eliminar el jugador, false en caso contrario.
     */
    public boolean eliminarJugador(String jugador) {
        return this.puntajes.remove(jugador) != null;
    }
    
    /**
     * Actualiza el puintaje de un jugador en la partida con el nombre dado.
     * @param jugador Nombre del jugador.
     * @param puntaje Puntaje a asignar.
     */
    public void actualizarPuntajeJugador(String jugador, int puntaje) {
        if (this.puntajes.containsKey(jugador)) {
            this.puntajes.put(jugador, puntaje);
        }
    }
    
    /**
     * Obtiene el puntaje del jugador con el nombre dado en la partida.
     * @param jugador Nombre del jugador.
     * @return Puntaje del jugador.
     */
    public int obtenerPuntajeJugador(String jugador) {
        return this.puntajes.get(jugador);
    }
    
}
