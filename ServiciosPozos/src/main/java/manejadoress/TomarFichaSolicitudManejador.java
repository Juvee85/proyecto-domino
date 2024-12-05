package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Pozo;
import entidades.Pozo.Ficha;
import entidades.Sala;
import eventos.CrearPozoRespuestaEvento;
import eventos.PozoErrorEvento;
import eventos.TomarFichaRespuestaEvento;
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
public class TomarFichaSolicitudManejador extends ManejadorEvento {

    private static final RepositorioPozos repositorio = RepositorioPozos.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    public TomarFichaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
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
    private TomarFichaRespuestaEvento tomarFicha(String nombreSala, String jugador) throws RepositorioPozoException {
        Sala s = new Sala();
        s.setNombre(nombreSala);
        Pozo pozo = repositorio.obtenerPozo(s);
        
        Ficha ficha = pozo.sacarFicha();
        int fichasRestantes = pozo.fichasRestantes();
        
        return new TomarFichaRespuestaEvento(nombreSala, ficha, fichasRestantes, jugador);
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

            String nombreSala = jsonNode.get("sala").asText();
            String jugador = jsonNode.get("jugador").asText();

            TomarFichaRespuestaEvento evento = this.tomarFicha(nombreSala, jugador);

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
