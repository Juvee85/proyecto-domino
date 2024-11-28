/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Pozo;
import java.io.Serializable;

/**
 *
 * @author diana
 */
public class ReiniciarPozoRespuestaEvento implements Serializable{
    
    @JsonProperty("nombre_evento")
    private String nombre;
    
    
    @JsonProperty("mensaje")
    private String mensaje;
    
     /**
     * Constructor que inicializa el evento con el pozo actualizado y un mensaje.
     *
     * @param nombre  el nombre del evento
     * @param mensaje mensaje descriptivo de la operación
     */
    
   public ReiniciarPozoRespuestaEvento(String nombre, String mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
    }
    
  
    public ReiniciarPozoRespuestaEvento() {
    }

   
    
    /**
     * @return the nombre del evento
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}

    
