package manejadores.fabrica;

import java.net.Socket;
import java.util.Objects;
import manejadores.CrearPartidaSolicitudManejador;
import manejadores.ActualizarPuntajeSolicitudManejador;
import manejadores.FinalizarPartidaSolicitudManejador;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;

/**
 * Fábrica concreta para crear manejadores de eventos relacionados con sesiones de juego.
 *
 * @autor Sebastian Murrieta Verduzco - 233463
 */
public class FabricaManejadorPartidas implements FabricaManejadorEventoAbstracto {

    @Override
    public ManejadorEvento obtenerManejador(String nombreEvento, Socket socket, String eventoSerializado) {
        Objects.requireNonNull(nombreEvento, "El nombre del evento no puede ser null");
        Objects.requireNonNull(socket, "El socket no puede ser null");
        Objects.requireNonNull(eventoSerializado, "El evento serializado no puede ser null");

        return switch (nombreEvento) {
            case "CrearPartidaSolicitud" ->
                new CrearPartidaSolicitudManejador(socket, eventoSerializado);
            case "ActualizarPuntajeSolicitud" ->
                new ActualizarPuntajeSolicitudManejador(socket, eventoSerializado);
            case "FinalizarPartidaSolicitud" ->
                new FinalizarPartidaSolicitudManejador(socket, eventoSerializado);
            default ->
                throw new IllegalArgumentException("Tipo de evento no soportado: " + nombreEvento);
        };
    }
}
