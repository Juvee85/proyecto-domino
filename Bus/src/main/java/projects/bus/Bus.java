package projects.bus;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicio.ContratoServicio;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class Bus {

    private final int PORT = 15_001;
    RepositorioServicios repositorio;
    private ServerSocket servidor;
    private List<Socket> sockets;
    ObjectMapper mapper;

    public static void main(String[] args) {
        new Bus();
    }

    public Bus() {
        servidor = null;
        Socket socket = null;
        mapper = new ObjectMapper();
        repositorio = new RepositorioServicios();

        try {
            servidor = new ServerSocket(PORT);
        } catch (IOException e) {

        }

        while (true) {
            try {
                socket = servidor.accept();

                sockets.add(socket);
                ContratoServicio contrato = mapper.readValue(socket.getInputStream(), ContratoServicio.class);

                repositorio.agregarServicio(contrato);
                new HiloServer(socket).start();
            } catch (IOException e) {

            }

        }

    }

    class HiloServer extends Thread {

        private Socket socket;
        private ObjectMapper mapper;
        private BufferedReader reader = null;
        private BufferedWriter writer = null;

        private HiloServer(Socket socket) {
            try {
                this.socket = socket;
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Map mensaje = mapper.readValue(reader, Map.class);
                    List<ContratoServicio> servicios = repositorio.obtenerServicios();

                    for (ContratoServicio servicio : servicios) {
                        for (Socket socket1 : sockets) {
                            int port = socket1.getLocalPort();
                            Integer puertoServicio = servicio.getPuerto();
                            if (port == puertoServicio) {
                                mapper.writeValue(writer, mensaje);
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
