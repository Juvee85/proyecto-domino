/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadores.fabrica;

import java.net.Socket;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;
import manejadoress.CrearPozoSolicitudManejador;
import manejadoress.EliminarPozoSolicitudManejador;
import manejadoress.ObtenerPozoSolicitudManejador;
import manejadoress.ReiniciarPozoSolicitudManejador;
import manejadoress.TomarFichaSolicitudManejador;

/**
 *
 * @author diana
 */
public class FabricaManejadorEvento implements FabricaManejadorEventoAbstracto{

    public FabricaManejadorEvento() {
    }

    @Override
    public ManejadorEvento obtenerManejador(String nombreEvento, Socket socket, String eventoSerializado) {
        switch (nombreEvento) {
            case "CrearPozoPartidaSolicitud": 
                return new CrearPozoSolicitudManejador(socket, eventoSerializado);
            case "EliminarPozoSolicitud":
                return new EliminarPozoSolicitudManejador(socket, eventoSerializado);
            case "ObtenerPozosSolicitud":
                return new ObtenerPozoSolicitudManejador(socket, eventoSerializado);
            case "ReiniciarPozoSolicitud": 
                    return new ReiniciarPozoSolicitudManejador(socket, eventoSerializado);
            case "TomarFichaSolicitud":
                return new TomarFichaSolicitudManejador(socket, eventoSerializado);
        }
        
        return null;
    }
    
}
