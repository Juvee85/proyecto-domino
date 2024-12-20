/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadores.fabrica;

import java.net.Socket;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;
import manejadoress.CrearTurnosSolicitudManejador;
import manejadoress.ObtenerTurnoFichaJugadaManejador;

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
            case "CrearTableroPartidaRespuesta":
                return new CrearTurnosSolicitudManejador(socket, eventoSerializado);
            case "FichaAgregadaTableroRespuesta": 
                return new ObtenerTurnoFichaJugadaManejador(socket, eventoSerializado);
            case "MulaMayorAgregadaRespuesta":
                
        }
        
        return null;
    }
}
    
