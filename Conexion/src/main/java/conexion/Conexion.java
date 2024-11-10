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
import java.util.Map;
import servicio.ContratoServicio;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class Conexion implements Runnable {

    private Socket s1 = null;
    private ObjectMapper mapper;
    private DataInputStream reader = null;
    private DataOutputStream writer = null;

    public Conexion() {
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
                notificar(evento);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
    }

    public void notificar(Map evento) {

    }
}
