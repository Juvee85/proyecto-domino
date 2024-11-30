/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Jugador;
import entidades.Pozo;
import entidades.Pozo.Ficha;
import entidades.Sala;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repositorio.excepciones.RepositorioPozoException;

/**
 *
 * @author diana
 */
public class RepositorioPozos {
    
    private static RepositorioPozos instance;
     /**
     * Contiene los pozos asociados a salas por su nombre
     */
    private Map<String, Pozo> pozos;

    /**
     * Constructor privado oculto
     */
    private RepositorioPozos() {
        this.pozos = new HashMap<>();
    }
    
      /**
     * Obtiene la instancia del repositorio de pozos
     *
     * @return RepositorioPozo
     */
    public static RepositorioPozos getInstance() {
        if (instance == null) {
            instance = new RepositorioPozos();
        }

        return instance;
   
   }

    /**
    * Crea un nuevo pozo asociado a una sala. 
    * Si la sala ya tiene un pozo, lanza una excepción.
    * También asigna 4 fichas a cada jugador de la sala desde el nuevo pozo.
    *
    * @param sala la sala para la cual se creará el pozo
    * @return el nuevo pozo creado
    * @throws RepositorioPozoException si ya existe un pozo asociado a la sala
    */
   public Pozo crearPozo(Sala sala) throws RepositorioPozoException {
    try {
        // Verifica si ya existe un pozo para la sala
        boolean pozoExiste = this.pozos.containsKey(sala.getNombre());
        if (pozoExiste) {
            throw new RepositorioPozoException("La sala \"" + sala.getNombre() + "\" ya cuenta con un pozo activo en el sistema.");
        }
        
        Pozo nuevoPozo = new Pozo();
        
        this.pozos.put(sala.getNombre(), nuevoPozo);
        
        return nuevoPozo;
    } catch (RepositorioPozoException e) {
        System.err.println("Error al crear el pozo: " + e.getMessage());
        throw e; 
    } 
        
}
    
        
   /**
     * Elimina el pozo asociado a una sala si existe.
     * Si no existe un pozo para la sala, lanza una excepción.
     *
     * @param sala la sala cuyo pozo será eliminado
     * @throws RepositorioPozoException si no existe un pozo asociado a la sala
     */
    public void eliminarPozo(Sala sala) throws RepositorioPozoException {
        boolean noExiste = !(this.pozos.containsKey(sala.getNombre()));
        if (noExiste) {
            throw new RepositorioPozoException("No existe un pozo asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
        
        this.pozos.remove(sala.getNombre());
    }
    
        
    /**
     * Reinicia el pozo asociado a una sala, reemplazándolo por uno nuevo.
     * Si no existe un pozo para la sala, lanza una excepción.
     *
     * @param sala la sala cuyo pozo será reiniciado
     * @throws RepositorioPozoException si no existe un pozo asociado a la sala
     */
    public void reiniciarPozo(Sala sala) throws RepositorioPozoException {
        boolean noExiste = !(this.pozos.containsKey(sala.getNombre()));
        if (noExiste) {
            throw new RepositorioPozoException("No existe un pozo asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
        
        this.pozos.put(sala.getNombre(), new Pozo());
    }

        
    /**
     * Obtiene el pozo asociado a una sala. 
     * Si no existe un pozo para la sala, lanza una excepción.
     *
     * @param sala la sala cuyo pozo se quiere obtener
     * @return el pozo asociado a la sala
     * @throws RepositorioPozoException si no existe un pozo asociado a la sala
     */
    public Pozo obtenerPozo(Sala sala) throws RepositorioPozoException{
        boolean noExiste = !(this.pozos.containsKey(sala.getNombre()));
    if (noExiste) {
        throw new RepositorioPozoException("No existe un pozo asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
    }
    return this.pozos.get(sala.getNombre());
}

        
    /**
     * Devuelve fichas de un jugador al pozo de la sala indicada.
     * Si el jugador no tiene fichas para devolver, lanza una excepción.
     *
     * @param sala la sala que contiene el pozo
     * @param fichasDelJugador las fichas que se devolverán al pozo
     * @throws RepositorioPozoException si no hay fichas para devolver
     */
    public void meterFicha(Sala sala, List<Ficha> fichasDelJugador) throws RepositorioPozoException {
    // Obtener el pozo asociado a la sala
    Pozo pozo = obtenerPozo(sala);

    if (fichasDelJugador == null || fichasDelJugador.isEmpty()) {
        throw new RepositorioPozoException("El jugador no tiene fichas para devolver al pozo.");
    }

    // Devolver las fichas del jugador al pozo en dado caso que el jugador salga de la partida
    for (Ficha ficha : fichasDelJugador) {
        if (ficha != null) {
            pozo.meterFicha(ficha);
        }
    }
}

    
    
    /**
     * Distribuye 4 fichas a cada jugador de una sala desde su pozo.
     * Si no hay jugadores en la sala o no existe el pozo, lanza una excepción.
     *
     * @param sala la sala para la cual se distribuirán las fichas
     * @return una lista de listas de fichas asignadas a cada jugador
     * @throws RepositorioPozoException si no hay jugadores en la sala o no existe el pozo
     */
    public List<List<Ficha>> obtenerJuegoFicha(Sala sala) throws RepositorioPozoException {
      
       //Esto verifica que si el pozo de la sala existe
        Pozo pozo = obtenerPozo(sala);
        
        //Verifica la cantidad de jugadores en la sala
        int jugadoresEnSala = sala.getJugadoresEnSala();
        if(jugadoresEnSala <=0){
            throw new RepositorioPozoException("La sala no tiene jugadores asignados");
            
        }
        //Lista para almacenar las fichas de cada jugador
        List<List<Ficha>> fichasPorJugador = new ArrayList<>();
        
        //Asignar fichas al jugador
        for(int i = 0; i <jugadoresEnSala; i++){
            List<Ficha> fichasJugador = new ArrayList<>();
            for(int j = 0; j< 4; j++){
                
        //Sacar la ficha del pozo
                Ficha ficha = pozo.sacarFicha();
                fichasJugador.add(ficha);
                
            }
            fichasPorJugador.add(fichasJugador);
        }
        return fichasPorJugador;
    }
 
    
}