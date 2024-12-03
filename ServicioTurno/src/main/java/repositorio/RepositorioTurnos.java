/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import entidades.Sala;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repositorios.excepciones.RepositorioTurnoException;


/**
 *
 * @author diana
 */
public class RepositorioTurnos {
    private static RepositorioTurnos instance;
   
    /**
     * Contiene las colas de turnos asociadas a salas por su nombre.
     */
    private Map<String, CyclicList<String>> turnos; 

    /**
     * Constructor privado oculto
     */
    private RepositorioTurnos() {
        this.turnos = new HashMap<>();
    }

    /**
     * Obtiene la instancia del repositorio de turnos
     *
     * @return RepositorioTurno
     */
    public static RepositorioTurnos getInstance() {
        if (instance == null) {
            instance = new RepositorioTurnos();
        }
        return instance;
    }

    /**
     * Crea una nueva cola de turnos para la sala indicada.
     *
     * @param sala Sala para la que se creará la cola de turnos.
     * @return 
     * @throws RepositorioTurnoException si ya existe una cola para la sala.
     */
    public String crearTurno(Sala sala) throws RepositorioTurnoException {
    if (this.turnos.containsKey(sala.getNombre())) {
        throw new RepositorioTurnoException("La sala \"" + sala.getNombre() + "\" ya cuenta con una cola de turnos activa en el sistema.");
    }

    
    List<String> jugadores = new ArrayList<>(Arrays.asList("Jugador 1", "Jugador 2", "Jugador 3"));

    // Crea una lista cíclica con los jugadores y la asocia a la sala
    CyclicList<String> listaCiclica = new CyclicList<>(jugadores);
    this.turnos.put(sala.getNombre(), listaCiclica);

    // Retornar el primer jugador en la lista cíclica
    return listaCiclica.current();
    }

   
     /**
     * Obtiene la lista cíclica de turnos asociada a una sala.
     *
     * @param sala Sala para la cual se desean obtener los turnos.
     * @return Lista cíclica de turnos de la sala.
     * @throws RepositorioTurnoException si no existe una cola de turnos para la sala.
     */
    public CyclicList<String> obtenerTurnos(Sala sala) throws RepositorioTurnoException {
        CyclicList<String> turnosSala = this.turnos.get(sala.getNombre());

        if (turnosSala == null) {
            throw new RepositorioTurnoException("No se encontraron turnos para la sala \"" + sala.getNombre() + "\".");
        }

        return turnosSala;
}

    
}