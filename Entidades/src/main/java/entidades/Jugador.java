package entidades;

import java.io.Serializable;
import java.util.List;

import entidades.Pozo.Ficha;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class Jugador implements Serializable {
    private String nombre;
    private String avatar;
    private List<Ficha> fichas;
    private int partidasGanadas;
    private boolean anfitrion;
    private int numero;

    public Jugador() {
        this.fichas = new ArrayList<>();
    }
    
    /**
     * Asigna si el jugador es un anfitrion o no
     * @param anfitrion 
     */
    public void esAnfitrion(boolean anfitrion) {
        this.anfitrion = anfitrion;
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
     * Asigna fichas al jugador
     * @param juegoFichas
     */
    public void asignarFichas(List<Ficha> juegoFichas) {
        this.fichas = juegoFichas;
    }

    /**
     * @return the fichas
     */
    public List<Ficha> obtenerFichas() {
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

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
}
