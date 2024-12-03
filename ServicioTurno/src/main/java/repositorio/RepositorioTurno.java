/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import entidades.Sala;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import repositorios.excepciones.RepositorioTurnoException;


/**
 *
 * @author diana
 */
public class RepositorioTurno {
    private static RepositorioTurno instance;
   
    /**
     * Contiene las colas de turnos asociadas a salas por su nombre.
     */
    private Map<String, Queue<String>> turnos;

    /**
     * Constructor privado oculto
     */
    private RepositorioTurno() {
        this.turnos = new HashMap<>();
    }

    /**
     * Obtiene la instancia del repositorio de turnos
     *
     * @return RepositorioTurno
     */
    public static RepositorioTurno getInstance() {
        if (instance == null) {
            instance = new RepositorioTurno();
        }
        return instance;
    }

    /**
     * Crea una nueva cola de turnos para la sala indicada.
     *
     * @param sala Sala para la que se creará la cola de turnos.
     * @throws RepositorioTurnoException si ya existe una cola para la sala.
     */
    public void crearTurno(Sala sala) throws RepositorioTurnoException {
        if (this.turnos.containsKey(sala.getNombre())) {
            throw new RepositorioTurnoException("La sala \"" + sala.getNombre() + "\" ya cuenta con una cola de turnos activa en el sistema.");
        }
        this.turnos.put(sala.getNombre(), new LinkedList<>());
    }

        /**
     * Asigna un nuevo turno único a la sala indicada.
     *
     * @param sala Sala a la que se asignará el turno.
     * @return El identificador del turno asignado.
     * @throws RepositorioTurnoException si no existe una cola asociada a la sala.
     */
    public String asignarTurno(Sala sala) throws RepositorioTurnoException {
        // Verifica que exista la cola de turnos para la sala
        if (!this.turnos.containsKey(sala.getNombre())) {
            throw new RepositorioTurnoException("No existe una cola de turnos asociada a la sala con el nombre: " + sala.getNombre());
        }

        // Genera un identificador único para el turno
        String turno = generarIdentificadorTurno(sala);

        // Agrega el turno a la cola correspondiente
        this.turnos.get(sala.getNombre()).add(turno);

        // Retorna el identificador del turno asignado
        return turno;
    }

    /**
     * Genera un identificador único para el turno basado en la sala y el tamaño actual de la cola.
     *
     * @param sala Sala para la que se genera el turno.
     * @return Un identificador único de turno.
     */
    private String generarIdentificadorTurno(Sala sala) {
        int numTurnos = this.turnos.get(sala.getNombre()).size() + 1;
        return sala.getNombre() + "-T" + numTurnos;
    }

    
}
