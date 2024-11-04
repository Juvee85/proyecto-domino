
package repositorio;

import entidades.Sala;
import java.util.ArrayList;
import java.util.List;

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
     * @param host Host o IP de la sala
     * @param puerto Puerto de la sala
     * @return true si existe la partida
     */
    public boolean existePartida(String host, int puerto) {
        return this.salas.stream()
                .filter(s -> s.getHost().equals(host) && s.getPuerto() == puerto)
                .count() > 0;
    }
    
    /**
     * Obtiene todas las salas activas del juego
     * @return Lista de salas
     */
    public List<Sala> getSalasActivas() {
        return this.salas;
    }
    
    /**
     * Agrega una nueva sala al repositorio
     * @param sala Sala nueva
     */
    public void agregarSala(Sala sala) {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getHost().equals(sala.getHost()) && s.getPuerto() == s.getPuerto())
                .findFirst().orElse(null);
        
        if (encontrada == null) {
            this.salas.add(sala);
        }
    }
    
    /**
     * Elimina la sala del repositorio de salas
     * @param host Host de la sala a buscar
     * @param puerto Puerto de la sala a buscar
     * @return Sala si se logro eliminar
     */
    public Sala eliminarSala(String host, int puerto) {
        Sala encontrada = this.salas.stream()
                .filter(s -> s.getHost().equals(host) && s.getPuerto() == puerto)
                .findFirst().orElse(null);
        
        if (encontrada == null) {
            return null;
        }
        
        this.salas.remove(encontrada);
        
        return encontrada;
    }
    
}
