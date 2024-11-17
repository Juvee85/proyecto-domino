
package repositorio;

import entidades.Sala;
import java.util.ArrayList;
import java.util.List;
import repositorio.excepciones.RepositorioSalasException;

/**
 * Aloja las salas de partidas de domino activas del juego
 * @author Saul Neri
 */
public class RepositorioSalas {
    private static RepositorioSalas instance;
    
    private List<Sala> salas;
    
    /**
     * Constructor privado oculto
     */
    private RepositorioSalas() {
        this.salas = new ArrayList<>();
    }
    
    /**
     * Obtiene la instancia del repositorio
     * @return 
     */
    public static RepositorioSalas getInstance() {
        if (instance == null) {
            instance = new RepositorioSalas();
        }
        
        return instance;
    }
    
    /**
     * Indica si la partida con la direccion de red dada existe
     * @param nombreSala Nombre de la asala a buscar
     * @return true si existe la partida
     */
    public boolean existePartida(String nombreSala) {
        return this.salas.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .count() > 0;
    }
    
    /**
     * Obtiene todas las salas activas del juego
     * @return Lista de salas
     */
    public List<Sala> getSalas() {
        return this.salas;
    }
    
    /**
     * Agrega una nueva sala al repositorio
     * @param sala Sala nueva
     * @throws repositorio.excepciones.RepositorioSalasException Si no se puede
     * agregar la sala
     */
    public void agregarSala(Sala sala) throws RepositorioSalasException {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getHost().equals(sala.getHost()) && s.getPuerto() == s.getPuerto())
                .findFirst().orElse(null);
        
        if (encontrada == null) {
            this.salas.add(sala);
            return;
        }
        
        throw new RepositorioSalasException("No se pudo crear la sala debido a un error, es probable que tengas una sala abierta...");
    }
    
    /**
     * Elimina la sala del repositorio de salas
     * @param host Host de la sala a buscar
     * @param puerto Puerto de la sala a buscar
     * @return Sala si se logro eliminar
     * @throws repositorio.excepciones.RepositorioSalasException si no se puede 
     * eliminar la sala del sistema
     */
    public Sala eliminarSala(String host, int puerto) throws RepositorioSalasException {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getHost().equals(host) && s.getPuerto() == puerto)
                .findFirst().orElse(null);
        
        if (encontrada == null) {
            throw new RepositorioSalasException("La sala que se intenta eliminar no existe...");
        }
        
        this.salas.remove(encontrada);
        
        return encontrada;
    }
    
}
