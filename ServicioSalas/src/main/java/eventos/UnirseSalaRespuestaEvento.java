package eventos;

/**
 * Evento de respuesta para las solicitudes de unirse a una sala
 */
public class UnirseSalaRespuestaEvento {
    private final boolean exitoso;
    private final String mensaje;
    private final String nombreSala;
    private final String idJugador;
    
    public UnirseSalaRespuestaEvento(boolean exitoso, String mensaje, String nombreSala, String idJugador) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.nombreSala = nombreSala;
        this.idJugador = idJugador;
    }
    
    // Getters necesarios para la serialización JSON
    public boolean isExitoso() {
        return exitoso;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public String getNombreSala() {
        return nombreSala;
    }
    
    public String getIdJugador() {
        return idJugador;
    }
}