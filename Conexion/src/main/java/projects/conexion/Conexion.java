package projects.conexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public Conexion() {
        try {
            s1 = new Socket("localhost", 15_001);
            reader = new BufferedReader(new InputStreamReader(s1.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(s1.getOutputStream()));
            mapper = new ObjectMapper();

            ContratoServicio contrato = new ContratoServicio("cliente",
                    s1.getInetAddress().getHostAddress(), s1.getLocalPort());

            contrato.setEventosEscuchables(new ArrayList<>());
            contrato.agregarEventoEscuchable("cambio-turno");
            contrato.agregarEventoEscuchable("ficha-agregada");
            mapper.writeValue(writer, contrato);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("IO Exception");
        }
    }

    public void enviarEvento(Map<String, Object> evento) throws IOException {
        mapper.writeValue(writer, evento);
        writer.flush();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Map evento = mapper.readValue(reader, Map.class);
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
