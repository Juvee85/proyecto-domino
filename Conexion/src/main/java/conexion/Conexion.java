package conexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import filtro.FiltroEventos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import observador.ObservableConexion;
import observador.ObservadorConexion;
import servicio.ContratoServicio;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class Conexion extends Thread implements ObservableConexion {

    /**
     * TODO: PARA EL JUEGO, DEBERIA DE HABER UNA MANERA DE ESPERAR SOLO UN TIPO
     * DE EVENTO SEGUN SEA EL CASO, SI ES TURNO DE ALGUIEN MAS, ENTONCES EL
     * EVENTO QUE ESPERAS QUE MANDE EL BUS ES DE cambiarTurno U OTRO SOLAMENTE.
     */
    private Socket s1 = null;
    private ObjectMapper mapper;
    private DataInputStream reader = null;
    private DataOutputStream writer = null;

    private List<ObservadorConexion> observadores;

    private Map evento;

    private FiltroEventos filtro = FiltroEventos.getInstance();
    
    public Conexion() {

        this.observadores = new ArrayList<>();

        try {
            s1 = new Socket("localhost", 15_001);
            reader = new DataInputStream(s1.getInputStream());
            writer = new DataOutputStream(s1.getOutputStream());
            mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            ContratoServicio contrato = Conexion.getContratoServicio();

            contrato.setHost(s1.getInetAddress().getHostAddress());
            contrato.setPuerto(s1.getPort());

            System.out.println(contrato);

            String contratoJSON = this.mapper.writeValueAsString(contrato);

            System.out.println(contratoJSON);

            writer.writeUTF(contratoJSON);
            writer.flush();

        } catch (IOException e) {
            System.out.print("IO Exception");
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor... Porfavor intente mas tarde.", "Error de Conexion", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static ContratoServicio getContratoServicio() {
        ContratoServicio contrato = new ContratoServicio();

        contrato.setNombreServicio("Cliente");

        contrato.setEventosEscuchables(new ArrayList<>());

        contrato.agregarEventoEscuchable("CrearSalaRespuesta");
        contrato.agregarEventoEscuchable("IniciarPartidaRespuesta");
        contrato.agregarEventoEscuchable("ObtenerSalasRespuesta");
        contrato.agregarEventoEscuchable("IniciarPartidaRespuesta");
        contrato.agregarEventoEscuchable("UnirseSalaRespuesta");
        contrato.agregarEventoEscuchable("JugadorUnidoASala");
        contrato.agregarEventoEscuchable("JugadorAbandonaSala");
        contrato.agregarEventoEscuchable("JugadorCambioEstadoListo");

        return contrato;
    }

    /**
     *Envia el evento dado al ESB
     * @param evento Evento en formato Map
     * @throws IOException
     */
    public void enviarEvento(Map<String, Object> evento) throws IOException {
        String eventoJSON = this.mapper.writeValueAsString(evento);

        System.out.println("[>] EVENTO A ENVIAR AL BUS...");
        System.out.println(eventoJSON);

        writer.writeUTF(eventoJSON);
        writer.flush();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Map evento = mapper.readValue(reader.readUTF(), Map.class);
                
                String nombreEvento = (String) evento.get("nombre_evento");
                
                if (filtro.eventoPermitido(nombreEvento)) { 
                    this.evento = evento;
                    notificarEvento();
                }
                
                // DEBUG
                else {
                    System.out.println("EVENTO DESCARTADO: %s".formatted(nombreEvento));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
    }

    @Override
    public void notificarEvento() {
        System.out.println("[<] Evento proveniente del bus:");
        System.out.println(this.evento.toString());
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
