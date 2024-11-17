/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;

/**
 * Se encarga de manejar una solicitud de eliminacion de una sala
 * @author Saul Neri
 */
public class EliminarSalaSolicitudManejador extends ManejadorEvento {
    
    

    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;
    
    public EliminarSalaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        //this.nombreEvento = nombreEvento;
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    
}
