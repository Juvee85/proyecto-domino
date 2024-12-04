package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Pozo;
import entidades.Sala;
import eventos.CrearPozoRespuestaEvento;
import eventos.PozoErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import manejadores.ManejadorEvento;
import repositorio.excepciones.RepositorioPozoException;
import repositorios.RepositorioPozos;

/**
 *
 * @author diana
 */
public class CrearPozoSolicitudManejador extends ManejadorEvento {

    private static final RepositorioPozos repositorio = RepositorioPozos.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    public CrearPozoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * Se crea el pozo y se hace la reparticion de fichas.
     *
     * @param sala
     * @return
     * @throws RepositorioPozoException
     */
    private CrearPozoRespuestaEvento crearPozo(Sala sala) throws RepositorioPozoException {

        Pozo pozo = repositorio.crearPozo(sala);
        if (pozo == null) {
            throw new RepositorioPozoException("No se encontro el pozo asociado a la sala");
        }

        if (sala.getJugadores() == null) {
            throw new RepositorioPozoException("La sala no cuenta con jugadores");
        }

        // NOTE: debug
        System.out.println("### Fichas restantes ANTES de la reparticion: %s".formatted(pozo.fichasRestantes()));

        for (int i = 0; i < sala.getJugadores().size(); i++) {
            sala.getJugadores().get(i).asignarFichas(pozo.obtenerJuegoDeFichas(sala.getNumFichasPorJugador()));
            // muestra las fichas
            System.out.println("### Fichas: %s".formatted(sala.getJugadores().get(i).obtenerFichas()));

        }

        System.out.println("### Fichas restantes del pozo: %s".formatted(pozo.fichasRestantes()));

        // Retornar el evento con la respuesta
        return new CrearPozoRespuestaEvento(sala, pozo.fichasRestantes());
    }

    /**
     *
     * @param mensaje
     */
    private void enviaRespuestaError(String mensaje) {
        PozoErrorEvento error = new PozoErrorEvento(mensaje);

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

            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            JsonNode salaSerializada = jsonNode.get("sala");

            Sala sala = objectMapper.treeToValue(salaSerializada, Sala.class);

            CrearPozoRespuestaEvento evento = this.crearPozo(sala);

            String eventoJSON = objectMapper.writeValueAsString(evento);

            respuesta.writeUTF(eventoJSON);

            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();

            this.enviaRespuestaError("No se pudo crear el pozo debido a un error en el servidor, por favor intente más tarde...");

        } catch (RepositorioPozoException ex) {
            this.enviaRespuestaError(ex.getMessage());
        }
    }

}
