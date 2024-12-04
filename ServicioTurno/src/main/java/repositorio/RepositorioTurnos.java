/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import entidades.Sala;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

        List<String> jugadoresNombres = sala.getJugadores().stream().map(j -> j.getNombre()).collect(Collectors.toList());
        // TODO: evaluar los nombres de los jugadores a ver si no es null..
        
        List<String> jugadores = new ArrayList<>(jugadoresNombres);

        // barajea los turnos...
        Collections.shuffle(jugadores);
        
        // Crea una lista cíclica con los jugadores y la asocia a la sala
        CyclicList<String> listaCiclica = new CyclicList<>(jugadores);
        this.turnos.put(sala.getNombre(), listaCiclica);

        // Retornar el primer jugador en la lista cíclica
        return listaCiclica.next();
    }

    /**
     * Obtiene la lista cíclica de turnos asociada a una sala.
     *
     * @param sala Sala para la cual se desean obtener los turnos.
     * @return Lista cíclica de turnos de la sala.
     * @throws RepositorioTurnoException si no existe una cola de turnos para la
     * sala.
     */
    public CyclicList<String> obtenerTurnos(Sala sala) throws RepositorioTurnoException {
        CyclicList<String> turnosSala = this.turnos.get(sala.getNombre());

        if (turnosSala == null) {
            throw new RepositorioTurnoException("No se encontraron turnos para la sala \"" + sala.getNombre() + "\".");
        }

        return turnosSala;
    }
    
    /**
     * Obtiene el nombre del jugador que sigue de hacer su jugada.
     * @param nombreSala
     * @return
     * @throws RepositorioTurnoException 
     */
    public String obtenerJugadorSiguienteTurno(String nombreSala) throws RepositorioTurnoException {
        CyclicList<String> turnosSala = this.turnos.get(nombreSala);

        if (turnosSala == null) {
            throw new RepositorioTurnoException("No se encontraron turnos para la sala \"" + nombreSala + "\".");
        }
        
        return turnosSala.next();
    }

}
