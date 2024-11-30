/*
 * PartidaDTO.java
 */
package DTOS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contiene la informacion de los puntajes de los jugadores.
 * @author Juventino López García - 00000248547 
 */
public class PartidaDTO {
    /**
     * Puntajes de los jugadores.
     */
    private Map<String, Integer> puntajes;
    
    /**
     * Constructor utilizado para permitir la serializacion
     * @param jugadores Lista de jugadores
     */
    public PartidaDTO(List<String> jugadores) {
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
    public PartidaDTO(Map<String, Integer> puntajes) {
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
