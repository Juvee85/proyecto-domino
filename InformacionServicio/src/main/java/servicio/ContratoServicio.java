
package servicio;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * Representa la informacion de un servicio
 * @author Saul Neri
 */
public class ContratoServicio implements Serializable {
    @JsonProperty("nombre")
    private String nombreServicio;
    
    @JsonProperty("host")
    private String host;
    
    @JsonProperty("puerto")
    private Integer puerto;
    
    @JsonProperty("eventos")
    private List<String> eventosEscuchables;
    
    /**
     * Constructor vacio para permitir la serializacion
     */
    public ContratoServicio() {
        
    }
    
    /**
     * Crea un nuevo contrato de servicio con su informacion principal
     * @param nombreServicio Nombre del servicio
     * @param host Nombre del host o IP del servicio
     * @param puerto Puerto en el que atiende el servicio
     */
    public ContratoServicio(String nombreServicio, String host, Integer puerto) {
        this.nombreServicio = nombreServicio;
        this.host = host;
        this.puerto = puerto;
    }
    
    /**
     * Obtiene el nombre del servicio.
     *
     * @return el nombre del servicio.
     */
    public String getNombreServicio() {
        return nombreServicio;
    }

    /**
     * Establece el nombre del servicio.
     *
     * @param nombreServicio el nombre del servicio a establecer.
     */
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    /**
     * Obtiene el host del servicio.
     *
     * @return el host del servicio.
     */
    public String getHost() {
        return host;
    }

    /**
     * Establece el host del servicio.
     *
     * @param host el host a establecer.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Obtiene el puerto en el que atiende el servicio.
     *
     * @return el puerto del servicio.
     */
    public Integer getPuerto() {
        return puerto;
    }

    /**
     * Establece el puerto en el que atiende el servicio.
     *
     * @param puerto el puerto a establecer.
     */
    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    /**
     * Obtiene la lista de eventos que el servicio puede escuchar.
     *
     * @return la lista de eventos escuchables.
     */
    public List<String> getEventosEscuchables() {
        return eventosEscuchables;
    }

    /**
     * Establece la lista de eventos que el servicio puede escuchar.
     *
     * @param eventosEscuchables la lista de eventos a establecer.
     */
    public void setEventosEscuchables(List<String> eventosEscuchables) {
        this.eventosEscuchables = eventosEscuchables;
    }

    /**
     * Agrega un nuevo evento a la lista de eventos escuchables.
     *
     * @param evento el evento a agregar.
     */
    public void agregarEventoEscuchable(String evento) {
        if (eventosEscuchables != null) {
            eventosEscuchables.add(evento);
        }
    }

    /**
     * Elimina un evento de la lista de eventos escuchables.
     *
     * @param evento el evento a eliminar.
     * @return true si el evento fue eliminado, false si no estaba presente.
     */
    public boolean eliminarEventoEscuchable(String evento) {
        return eventosEscuchables != null && eventosEscuchables.remove(evento);
    }

    /**
     * Verifica si un evento está en la lista de eventos escuchables.
     *
     * @param evento el evento a verificar.
     * @return true si el evento está en la lista, false en caso contrario.
     */
    public boolean contieneEventoEscuchable(String evento) {
        return eventosEscuchables != null && eventosEscuchables.contains(evento);
    }
}
