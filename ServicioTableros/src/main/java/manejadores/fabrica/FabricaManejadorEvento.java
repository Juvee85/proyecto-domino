package manejadores.fabrica;

import java.net.Socket;
import manejadores.AgregarFichaTableroSolicitudManejador;
import manejadores.CrearTableroPartidaSolicitudManejador;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;
import manejadores.MulaMayorAgregadaSolicitudManejador;

/**
 * Fabrica concreta para crear manejadores de eventos del servicio tableros
 *
 * @author Saul Neri
 */
public class FabricaManejadorEvento implements FabricaManejadorEventoAbstracto {

    /**
     * Crea una nueva instancia de una fabrica de manejadores de eventos para el
     * servicio
     */
    public FabricaManejadorEvento() {

    }

    /**
     * Obtiene una instancia de un manejador de eventos segun el nombre del
     * evento
     *
     * @param nombreEvento Nombre del evento recibido
     * @param socket Socket de la conexion con el servicio
     * @param eventoSerializado Evento serializado en formato JSON
     * @return ManejadorEvento abstracto
     */
    @Override
    public ManejadorEvento obtenerManejador(String nombreEvento, Socket socket, String eventoSerializado) {
        switch (nombreEvento) {
            case "CrearTableroPartidaSolicitud" -> {
                return new CrearTableroPartidaSolicitudManejador(socket, eventoSerializado);
            }
            case "JugarFichaSolicitud" -> {
                return new AgregarFichaTableroSolicitudManejador(socket, eventoSerializado);
            }
            
            case "MulaMayorAgregadaSolicitud" -> {
                return new MulaMayorAgregadaSolicitudManejador(socket, eventoSerializado);
            }

        }

        return null;
    }
}
