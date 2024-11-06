package servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import projects.bus.Bus;

/**
 * Se encarga de manejar los mensajes y respuesas a un servicio integrado al ESB
 * por medio de un hilo
 *
 * @author Saul Neri
 */
public class ServicioManejador extends Thread {

    private static final RepositorioServicios repositorioServicios = RepositorioServicios.getInstance();
    
    /**
     * Servicio del cual se hace cargo el manejador
     */
    private Servicio servicio;

    /**
     * Objeto para mapeo JSON
     */
    private ObjectMapper jsonMapper;
    /**
     * Equivalente a Request
     */
    private BufferedReader reader = null;

    /**
     * Equivalente a Response
     */
    private BufferedWriter writer = null;

    /**
     * Constructor para un nuevo hilo manejador para un servicio
     *
     * @param servicio Servicio a manejar
     */
    public ServicioManejador(Servicio servicio) {
        
        this.jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        
        try {
            this.servicio = servicio;
            writer = new BufferedWriter(new OutputStreamWriter(servicio.getSocket().getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(servicio.getSocket().getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        
        DataOutputStream respuestaResponsable = null;
        
        // mientras la conexion entre el ESB y el servicio en cuestion este viva...
        while (true) {
            try {
                
                String mensajeTexto = reader.lines().collect(Collectors.joining("\n"));
                
                // captura el mensaje proveniente del servicio...
                Map mensaje = jsonMapper.readValue(reader, Map.class);
                
                String nombreEvento = mensaje.get("nombre_evento").toString();
                if (nombreEvento == null || nombreEvento.isBlank() || nombreEvento.isEmpty()) {
                    throw new IllegalArgumentException("No se pudo determinar el nombre del evento");
                }
                
                // redirije el evento del servicio recibido a los demas responsables de dicho evento
                List<Servicio> responsables = repositorioServicios.obtenerResponsablesEvento(nombreEvento);
                for (Servicio responsable: responsables) {
                    respuestaResponsable = new DataOutputStream(responsable.getSocket().getOutputStream());
                    System.out.println("[MENSAJE RECIBIDO: %s]".formatted(mensajeTexto));
                    respuestaResponsable.writeUTF(mensajeTexto);
                    respuestaResponsable.flush();
                    System.out.println("[REDIRIGIENDO MENSAJE A: %s".formatted(responsable.getContrato().getNombreServicio()));
                }
             
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (IllegalArgumentException ex) {
                System.out.println("[ERROR] Ocurrio un error de recepcion de mensaje: %s".formatted(ex.getMessage()));
            }
        }
    }
}
