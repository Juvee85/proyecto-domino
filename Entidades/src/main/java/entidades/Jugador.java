package entidades;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Saul Neri
 */
public class Jugador implements Serializable {
    private String nombre;
    private String avatar;
    private List<Ficha> fichas;
    private int partidasGanadas;
    private boolean anfitrion;

    public Jugador() {
        
    }
    
    public boolean esAnfitrion() {
        return this.anfitrion;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the fichas
     */
    public List<Ficha> getFichas() {
        return fichas;
    }

    /**
     * @return the partidasGanadas
     */
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * @param partidasGanadas the partidasGanadas to set
     */
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }
}
