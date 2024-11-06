
package servicios;

import java.net.Socket;
import servicio.ContratoServicio;

/**
 * Representa la integracion de un servicio en el bus
 * @author Saul Neri
 */
public class Servicio {
    private ContratoServicio contrato;
    private Socket socket;
    
    public Servicio(Socket socket) {
        this.socket = socket;
    }

    /**
     * Obtiene el contrato del servicio
     * @return El contrato del servicio
     */
    public ContratoServicio getContrato() {
        return contrato;
    }

    /**
     * Asigna el contrato al servicio
     * @param contrato Contrato de servicio a asignar
     */
    public void setContrato(ContratoServicio contrato) {
        this.contrato = contrato;
    }

    /**
     * Obtiene el socket del servicio
     * @return El socket interno del servicio
     */
    public Socket getSocket() {
        return socket;
    }
    
    
    
}
