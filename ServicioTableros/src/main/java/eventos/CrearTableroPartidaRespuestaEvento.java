/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import entidades.Tablero;
import java.io.Serializable;

/**
 * Evento de respuesta para ser enviado al servicio de Salas para actualizar
 * la informacion de los jugadores.
 * @author Saul Neri
 */
public class CrearTableroPartidaRespuestaEvento implements Serializable {
    @JsonProperty("nombre_evento")
    private String nombreEvento;
    
    @JsonProperty("sala")
    private Sala sala;
    
    @JsonProperty("fichas_restantes")
    private int fichasRestantes;
    
    public CrearTableroPartidaRespuestaEvento(Sala sala, int fichasRestantes) {
        this.nombreEvento = "CrearTableroPartidaRespuesta";
        this.sala = sala;
        this.fichasRestantes = fichasRestantes;
    }

    /**
     * @return the nombreEvento
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }


    public int obtenerFichasRestantes() {
        return fichasRestantes;
    }

    public void setFichas_restantes(int fichasRestantes) {
        this.fichasRestantes = fichasRestantes;
    }
    
    
}
