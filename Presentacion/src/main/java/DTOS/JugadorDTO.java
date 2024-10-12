/*
 * JugadorDTO.java
 */
package DTOS;

import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547 - 09/10/2024
 */
public class JugadorDTO {

    private String nombre;
    private String avatar;
    private List<FichaDTO> fichas;  // Las fichas en la mano del jugador
    private int partidasGanadas;
    private boolean anfitrion;
    private int numero;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<FichaDTO> getFichas() {
        return fichas;
    }

    public void setFichas(List<FichaDTO> fichas) {
        this.fichas = fichas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public boolean esAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion(boolean anfitrion) {
        this.anfitrion = anfitrion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Seleccionar cualquier ficha de la mano
    public FichaDTO seleccionarFicha(int indice) {
        if (indice >= 0 && indice < fichas.size()) {
            return fichas.get(indice);
        }
        return null;
    }

    // Jugar una ficha si es válida
    public boolean jugarFicha(FichaDTO ficha, TableroDTO tablero) {
        if (tablero.puedeAgregarFicha(ficha)) {
            fichas.remove(ficha);
            return true;
        }
        return false;
    }
}
