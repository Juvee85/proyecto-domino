/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manejadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import repositorio.RepositorioSalas;

/**
 * Se encarga de manejar el evento en base al nombre del mismo
 * @author Saul Neri
 * @deprecated 
 */
public class EventosManejador {
    
    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    
    // Instancia única de la clase
    private static EventosManejador instancia;
    
    private Map<String, ManejadorEvento> manejadores;
    
    /**
     * Constructor privado oculto
     */
    private EventosManejador() {
        manejadores = new HashMap<>();
        registrarManejadores();
    }

    // Método estático para obtener la instancia única
    public static synchronized EventosManejador obtenerInstancia() {
        if (instancia == null) {
            instancia = new EventosManejador();
        }
        return instancia;
    }

    /**
     * Carga todos los manejadores para las rutas posibles
     */
    private void registrarManejadores() {
        //this.manejadores.put("crearSalaEventoSolicitud", value)
    }
    
    public void manejaEvento() {
        
    }
    
    /**
     * Obtiene todos los eventos que maneja el servicio
     * @return 
     */
    public List<String> getListaNombresEventos() {
        return this.manejadores.keySet().stream().collect(Collectors.toList());
    }
    
    public void manejadorAgregarSala(String eventoSerializado) {
        System.out.println(eventoSerializado);
    }
            
}
