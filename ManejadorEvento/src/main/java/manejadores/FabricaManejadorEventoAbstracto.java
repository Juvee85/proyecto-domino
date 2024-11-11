
package manejadores;

import java.net.Socket;
import manejadores.ManejadorEvento;

/**
 * Define una fabrica abstracta para crear manejadores de eventos concretos en distintos servicios
 * @author neri
 */
public interface FabricaManejadorEventoAbstracto {
    
    /**
     * Obtiene una instancia de un manejador de eventos segun el nombre del evento
     * @param nombreEvento Nombre del evento recibido
     * @param socket Socket de la conexion con el servicio
     * @param eventoSerializado Evento serializado en formato JSON
     * @return ManejadorEvento abstracto
     */
    public ManejadorEvento obtenerManejador(String nombreEvento, Socket socket, String eventoSerializado);
}
