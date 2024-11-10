package conexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import observador.ObservableConexion;
import observador.ObservadorConexion;
import servicio.ContratoServicio;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class Conexion implements Runnable, ObservableConexion {

    /**
     * TODO: PARA EL JUEGO, DEBERIA DE HABER UNA MANERA DE ESPERAR SOLO UN TIPO
     * DE EVENTO SEGUN SEA EL CASO, SI ES TURNO DE ALGUIEN MAS, ENTONCES EL EVENTO
     * QUE ESPERAS QUE MANDE EL BUS ES DE cambiarTurno U OTRO SOLAMENTE.
     */
    
    private Socket s1 = null;
    private ObjectMapper mapper;
    private DataInputStream reader = null;
    private DataOutputStream writer = null;
    
    private List<ObservadorConexion> observadores;

    private Map evento;
    
    public Conexion() {
        
        this.observadores = new ArrayList<>();
        
        try {
            s1 = new Socket("localhost", 15_001);
            reader = new DataInputStream(s1.getInputStream());
            writer = new DataOutputStream(s1.getOutputStream());
            mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            ContratoServicio contrato = this.getContratoServicio();
            
            contrato.setHost(s1.getInetAddress().getHostAddress());
            contrato.setPuerto(s1.getPort());
            
            System.out.println(contrato);
            
            String contratoJSON = this.mapper.writeValueAsString(contrato);
            
            System.out.println(contratoJSON);
            
            writer.writeUTF(contratoJSON);
            writer.flush();
            
        } catch (IOException e) {
            System.out.print("IO Exception");
            e.printStackTrace();
            // TODO: Detener la presentacion...
            
        }
    }

    /**
     * 
     * @return 
     */
    public ContratoServicio getContratoServicio() {
        ContratoServicio contrato = new ContratoServicio();

        contrato.setNombreServicio("Cliente");
        
        contrato.setEventosEscuchables(new ArrayList<>());
        
        contrato.agregarEventoEscuchable("CrearSalaRespuesta");
        contrato.agregarEventoEscuchable("IniciarPartidaRespuesta");
        contrato.agregarEventoEscuchable("ObtenerSalasRespuesta");
        contrato.agregarEventoEscuchable("IniciarPartidaRespuesta");
        
        return contrato;
    }

    /**
     * 
     * @param evento
     * @throws IOException 
     */
    public void enviarEvento(Map<String, Object> evento) throws IOException {
        String eventoJSON = this.mapper.writeValueAsString(evento);
        
        writer.writeUTF(eventoJSON);
        writer.flush();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Map evento = mapper.readValue(reader.readUTF(), Map.class);
                this.evento = evento;
                notificarEvento();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
    }

    @Override
    public void notificarEvento() {
        this.observadores.forEach(obs -> obs.actualizar(evento));
    }

    @Override
    public void agregarObservador(ObservadorConexion observador) {
        this.observadores.add(observador);
    }

    @Override
    public void removerObservador(ObservadorConexion observador) {
        this.observadores.remove(observador);
    }
}
