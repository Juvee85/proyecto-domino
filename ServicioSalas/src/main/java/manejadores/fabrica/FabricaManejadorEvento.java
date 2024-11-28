/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadores.fabrica;

import java.net.Socket;
import manejadores.AbandonarSalaSolicitudManejador;
import manejadores.CambiarEstadoListoSolicitudManejador;
import manejadores.CrearSalaSolicitudManejador;
import manejadores.EliminarSalaSolicitudManejador;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;
import manejadores.ObtenerSalasSolicitudManejador;
import manejadores.UnirseSalaManejador;

/**
 * Fabrica concreta para crear manejadores de eventos del servicio salas
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
            case "CrearSalaSolicitud" -> {
                return new CrearSalaSolicitudManejador(socket, eventoSerializado);
            }
            case "EliminarSalaSolicitud" -> {
                return new EliminarSalaSolicitudManejador(socket, eventoSerializado);
            }
            case "ObtenerSalasSolicitud" -> {
                return new ObtenerSalasSolicitudManejador(socket, eventoSerializado);
            }
            case "UnirseSalaSolicitud" -> {
                return new UnirseSalaManejador(socket, eventoSerializado);
            }
            case "AbandonarSalaSolicitud" -> {
                return new AbandonarSalaSolicitudManejador(socket, eventoSerializado);
            }
            case "CambiarEstadoJugadorListoSolicitud" -> {
                return new CambiarEstadoListoSolicitudManejador(socket, eventoSerializado);
            }
        }

        return null;
    }
}
