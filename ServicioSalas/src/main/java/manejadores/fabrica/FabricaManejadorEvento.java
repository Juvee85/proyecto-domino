/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manejadores.fabrica;

import java.net.Socket;
import manejadores.CrearSalaSolicitudManejador;
import manejadores.EliminarSalaSolicitudManejador;
import manejadores.ManejadorEvento;
import manejadores.ObtenerSalasSolicitudManejador;

/**
 *
 * @author Saul Neri
 */
public class FabricaManejadorEvento {
    
    /**
     * Obtiene una instancia de un manejador de eventos segun el nombre del evento
     * @param nombreEvento Nombre del evento recibido
     * @param socket Socket de la conexion con el servicio
     * @param eventoSerializado Evento serializado en formato JSON
     * @return ManejadorEvento abstracto
     */
    public static ManejadorEvento obtenerManejador(String nombreEvento, Socket socket, String eventoSerializado) {
        switch (nombreEvento) {
            case "CrearSalaSolicitud": 
                return new CrearSalaSolicitudManejador(socket, eventoSerializado);
            case "EliminarSalaSolicitud":
                return new EliminarSalaSolicitudManejador(socket, eventoSerializado);
            case "ObtenerSalasSolicitud":
                return new ObtenerSalasSolicitudManejador(socket, eventoSerializado);
        }
        
        return null;
    }
}
