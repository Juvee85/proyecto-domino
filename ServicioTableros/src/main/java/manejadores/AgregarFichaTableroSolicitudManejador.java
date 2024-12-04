/*
 * AgregarFichaTableroSolicitudManejador.java
 */
package manejadores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Jugador;
import entidades.Pozo.Ficha;
import entidades.Sala;
import eventos.FichaAgregadaATableroEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioTableros;
import repositorio.excepciones.RepositorioTablerosException;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class AgregarFichaTableroSolicitudManejador extends ManejadorEvento {

    private static final RepositorioTableros repositorio = RepositorioTableros.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    public AgregarFichaTableroSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    private FichaAgregadaATableroEvento agregarFichaATablero(Sala sala, Ficha ficha, Jugador jugador, String direccion) throws RepositorioTablerosException {
        repositorio.agregarFichaEnTablero(sala, ficha, direccion);

        return new FichaAgregadaATableroEvento(sala, ficha, jugador, direccion);
    }

    private void enviaRespuestaError(String mensaje) {
        TablerosErrorEvento error = new TablerosErrorEvento(mensaje);

        String errorSerializado;

        try {
            errorSerializado = objectMapper.writeValueAsString(error);

            respuesta.writeUTF(errorSerializado);
            respuesta.flush();
        } catch (JsonProcessingException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        } catch (IOException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        }
    }

    @Override
    public void run() {
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(AgregarFichaTableroSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // Acceder a los valores directamente
            JsonNode salaSerializada = jsonNode.get("sala");
            JsonNode fichaSerializada = jsonNode.get("ficha");
            JsonNode jugadorSerializado = jsonNode.get("jugador");
            String direccion = jsonNode.get("direccion").asText();

            Sala sala = objectMapper.treeToValue(salaSerializada, Sala.class);
            Ficha ficha = objectMapper.treeToValue(fichaSerializada, Ficha.class);
            Jugador jugador = objectMapper.treeToValue(jugadorSerializado, Jugador.class);

            FichaAgregadaATableroEvento evento = this.agregarFichaATablero(sala, ficha, jugador, direccion);
            String eventoJSON = objectMapper.writeValueAsString(evento);

            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("### No se ha podido agregar la ficha al tablero debido a un error en el servidor, porfavor intente mas tarde...");
        } catch (RepositorioTablerosException ex) {
            this.enviaRespuestaError(ex.getMessage());
        }
    }
}
