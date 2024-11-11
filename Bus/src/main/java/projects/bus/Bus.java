package projects.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import servicios.Servicio;
import servicio.ContratoServicio;
import servicios.RepositorioServicios;
import servicios.ServicioManejador;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class Bus {

    private RepositorioServicios repositorioServicios = RepositorioServicios.getInstance();

    private final int PORT = 15_001;
    private ServerSocket servidor = null;

    private ObjectMapper jsonMapper = null;

    public Bus() {
        Servicio servicio = null;

        jsonMapper = new ObjectMapper();

        try {
            servidor = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("ERROR: Se producto un error al intentar arrancar el ESB... [error: %s]".formatted(e.getMessage()));
            return;
        }
        
        DataInputStream servicioMensaje = null;

        while (true) {

            System.out.println("[=!=] ESPERANDO CONEXIONES DE SERVICIOS...");

            try {
                // espera hasta recibir una nueva conexion
                Socket socket = servidor.accept();
                System.out.println("[!] NUEVO SERVICIO CONECTADO %s::%d".formatted(socket.getInetAddress(), socket.getPort()));

                servicio = new Servicio(socket);
                // obtiene el puerto de conexion del servicio
                int puertoConexion = socket.getPort();
                // Se obtiene el contrato del mensaje del servicio
                servicioMensaje = new DataInputStream(socket.getInputStream());

                String mensajeJSON = servicioMensaje.readUTF();

                System.out.println(mensajeJSON);

                ContratoServicio contrato = jsonMapper.readValue(mensajeJSON, ContratoServicio.class);

                // se asigna el puerto de conexion del servicio al contrato
                contrato.setPuerto(puertoConexion);
                // asigna el contrato al servicio
                servicio.setContrato(contrato);
                
                System.out.println("Servicio(%s, %d, %s, %s)".formatted(
                        servicio.getContrato().getNombreServicio(), 
                        servicio.getContrato().getEventosEscuchables().size(),
                        servicio.getContrato().getHost(),
                        servicio.getContrato().getPuerto()
                    )
                );
                
                for (String nombre: contrato.getEventosEscuchables()) {
                    System.out.println(nombre);
                }

                // ase agrega el servicio al repositorio para consultarlo cuando reciba mensajes de un tercero
                repositorioServicios.agregarServicio(servicio);
                
                System.out.println("Servicios registrados: " + repositorioServicios.obtenerServicios().size());
                
                // se crea el manejador para ese 
                new ServicioManejador(servicio).start();
            } catch (Exception ex) {
                System.out.println("[ERROR AL INTEGRAR SERVICIO]: No se pudo integrar el servicio al bus... [error: %s]".formatted(ex.getMessage()));
            }
        }
    }

    /**
     * 
     * @param inputStream
     * @return
     * @throws IOException 
     */
    public static String leerMensaje(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new Bus();
    }
}
