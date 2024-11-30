package serializacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.ConfiguracionJuego;
import entidades.Jugador;
import entidades.Partida;
import entidades.Pozo;
import entidades.Pozo.Ficha;
import entidades.Tablero;
import entidades.TrenFichas;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Saul Neri
 */
public class Serializacion {
    public static void main(String[] args) {
        /*
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        
        Pozo pozo = new Pozo();
        
        String jsonTxt = null;
        
        try {
            jsonTxt = mapper.writeValueAsString(pozo);
            System.out.println(jsonTxt);
        } catch (JsonProcessingException ex) {
            
        }
        
        try {
            Pozo pozoParsed = mapper.readValue(jsonTxt, Pozo.class);
            
            System.out.println(pozoParsed.fichasRestantes());
            
            Ficha ficha = pozoParsed.sacarFicha();
            
            System.out.println(ficha);
        } catch (JsonProcessingException ex) {
            System.out.println("ERROR:" + ex.getMessage());
        }
        
        
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        // Crear una ficha para agregar al TrenFichas
        Ficha ficha1 = pozo.sacarFicha();
        Ficha ficha2 = pozo.sacarFicha();
        Ficha ficha3 = pozo.sacarFicha();
        Ficha ficha4 = pozo.sacarFicha();
        Ficha ficha5 = pozo.sacarFicha();
        Ficha ficha6 = pozo.sacarFicha();
        TrenFichas tren = new TrenFichas(ficha1);

        // Agregar más fichas
        tren.agregarFichaExtremoIzquierdo(ficha2);
        tren.agregarFichaExtremoDerecho(ficha3);
        tren.agregarFichaExtremoDerecho(ficha4);
        tren.agregarFichaExtremoDerecho(ficha5);
        tren.agregarFichaExtremoDerecho(ficha6);

        // Serializar a JSON
        String json = null;
        try {
            json = objectMapper.writeValueAsString(tren);
            System.out.println("Serialized JSON: " + json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        // Deserializar de JSON
        TrenFichas deserializedTren = null;
        try {
            deserializedTren = objectMapper.readValue(json, TrenFichas.class);
            System.out.println("Deserialized TrenFichas: " + deserializedTren);
            Ficha fichaObtenida = deserializedTren.obtenerFichaExtremoDerecho();
            System.out.println("FICHAS EXTREMO CONCUERDAN? %s".formatted(
                    ((fichaObtenida.getPuntosCabeza() == ficha6.getPuntosCabeza()) &&
                    (fichaObtenida.getPuntosCola() == ficha6.getPuntosCola())) ? "SI" : "NO"
            ));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("-----------------[JUGADOR]------------------");
        
        Jugador j = new Jugador();
        j.setNombre("chacal45");
        j.setAvatar("Leopardo");
        j.setNumero(1);
        j.agregarFicha(ficha1);
        j.agregarFicha(ficha2);
        j.agregarFicha(ficha3);
        
        try {
            json = objectMapper.writeValueAsString(j);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Jugador deserializadoJugador = objectMapper.readValue(json, Jugador.class);
            System.out.println("Datos coinciden: %s".formatted(j.getNombre().equalsIgnoreCase(deserializadoJugador.getNombre()) ? "SI" : "NO"));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("---------[PARTIDA]----------");
        
        Tablero tablero = new Tablero();
        ConfiguracionJuego config = new ConfiguracionJuego(3,6);
        
        Partida partida = new Partida(j);
        partida.setConfiguracion(config);
        partida.setTablero(tablero);
        
        try {
            json = objectMapper.writeValueAsString(partida);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Serializacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
}
