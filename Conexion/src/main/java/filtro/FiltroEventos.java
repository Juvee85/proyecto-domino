/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package filtro;

import conexion.Conexion;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Filtra los eventos que provienen del BUS para solo dejar pasar
 * los permitidos por un filtro de estado.
 * @author Saul Neri
 */
public class FiltroEventos {
    
    private static FiltroEventos instance;
    
    private Map<String, Boolean> eventosEscuchables;

    /**
     * 
     */
    public enum Estado {
        INICIO,
        CREAR_SALA,
        SALAS_DISPONIBLES,
        SALA_ESPERA,
        PARTIDA
    };
    
    /**
     * Constructor privado para la construccion de la instancia unica
     */
    private FiltroEventos() {
        List<String> contrato = Conexion.getContratoServicio().getEventosEscuchables();
        
        this.eventosEscuchables = new HashMap();
        
        contrato.forEach(evento -> this.eventosEscuchables.put(evento, Boolean.TRUE));
    }
    
    /**
     * Obtiene la instancia unica del filtro de eventos
     * @return FiltroEventos
     */
    public static FiltroEventos getInstance() {
        if (instance == null) {
            instance = new FiltroEventos();
        }
        
        return instance;
    }
    
    /**
     *
     */
    private void permitirSolamenteEventos(List<String> eventosPermitidos) {
        if (eventosPermitidos == null || eventosPermitidos.isEmpty()) return;
        Set<String> keys = this.eventosEscuchables.keySet();
        keys.forEach(evento -> this.eventosEscuchables.put(evento, Boolean.FALSE));
        eventosPermitidos.forEach(evento -> this.eventosEscuchables.put(evento, Boolean.TRUE));
    }
    
    /**
     * Hace que solo los eventos que llegan cuando la aplicacion esta en cierto
     * estado sean atendidos, dejando de lado los demas.
     * @param estado Estado en el que se encuentra la aplicacion
     */
    public void restringirEventosPorEstado(Estado estado) {
        switch (estado) {
            case Estado.INICIO: {
            }
            break;
            case Estado.CREAR_SALA: {
                this.permitirSolamenteEventos(Arrays.asList(
                        "CrearSalaRespuesta",
                        "SalaError"
                ));
            }
            break;
            case Estado.SALAS_DISPONIBLES: {
                this.permitirSolamenteEventos(Arrays.asList(
                        "ObtenerSalasRespuesta",
                        "UnirseSalaRespuesta",
                        "SalaError"
                ));
            }
            break;
            case Estado.SALA_ESPERA: {
                this.permitirSolamenteEventos(Arrays.asList(
                        "JugadorUnidoASala",
                        "JugadorAbandonaSala",
                        "SalaError"
                ));
            }
            break;
        }
    }
    
    /**
     * Indica si se esta escuchando ese evento en la aplicacion
     * @param evento
     * @return 
     */
    public boolean eventoPermitido(String evento) {
        Boolean permitido = this.eventosEscuchables.get(evento);
        
        if (permitido == null) return false;
        
        return permitido;
    }
}
