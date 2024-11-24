package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Pozo.Ficha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un jugador en el juego
 *
 * @author Equipo 1
 */
public class Jugador implements Serializable {

    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("fichas")
    private List<Ficha> fichas;
    @JsonProperty("es_anfitrion")
    private boolean anfitrion;
    @JsonProperty("numero_jugador")
    private int numero;

    public Jugador() {
        this.fichas = new ArrayList<>();
    }

    /**
     * Asigna si el jugador es un anfitrion o no
     *
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
     *
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

    public Ficha sacarFicha(Ficha ficha) {
        Ficha removida = null;
        for (Ficha ficha1 : fichas) {
            if (ficha.equals(ficha1)) {
                removida = ficha1;
                break;
            }
        }
        fichas.remove(removida);

        return removida;
    }

    public void agregarFicha(Ficha ficha) {
        fichas.add(ficha);
    }

    @Override
    public String toString() {
        return "Jugador{"
                + "nombre='" + nombre + '\''
                + ", avatar='" + avatar + '\''
                + ", fichas=" + (fichas != null ? fichas : "[]")
                + ", anfitrion=" + anfitrion
                + ", numero=" + numero
                + '}';
    }
}
