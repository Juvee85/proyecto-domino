/*
 * PartidaDTO.java
 */
package DTOS;

import java.util.List;

/**
 * 
 * @author Juventino López García - 00000248547 
 */
public class PartidaDTO {
    private List<JugadorDTO> jugadores;
    private TableroDTO tablero;
    private PozoDTO pozo;

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }

    public TableroDTO getTablero() {
        return tablero;
    }

    public void setTablero(TableroDTO tablero) {
        this.tablero = tablero;
    }

    public PozoDTO getPozo() {
        return pozo;
    }

    public void setPozo(PozoDTO pozo) {
        this.pozo = pozo;
    }
    
}
