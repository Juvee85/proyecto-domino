package eventos;

/**
 * Evento de respuesta para la solicitud de finalizar partida
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class FinalizarPartidaRespuestaEvento {

    private String salaId;
    private String mensaje;
    private String estado;
    private String ganador;

    public FinalizarPartidaRespuestaEvento() {
    }

    public FinalizarPartidaRespuestaEvento(String salaId, String ganador) {
        this.salaId = salaId;
        this.ganador = ganador;
        this.estado = "completado";
        this.mensaje = "Partida finalizada exitosamente";
    }

    // Getters y setters
    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
}
