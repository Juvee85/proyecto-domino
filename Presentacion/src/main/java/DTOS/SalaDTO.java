package DTOS;

/**
 * Representa una sala en el juego
 *
 * @author Saul Neri
 */
public class SalaDTO {

    private String nombre;
    private int maxJugadores;
    private int jugadoresEnSala;
    private int numeroFichasPorJugador;
    private String host;
    private int puerto;
    private String contrasena;
    private boolean tieneContrasena;

    /**
     * Crea una sala nueva sin contrasena por defecto
     */
    public SalaDTO() {
        this.tieneContrasena = false;
    }

    /**
     * Devuelve el nombre de la sala
     *
     * @return El nombre de la sala
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de la sala
     *
     * @param nombre Nombre de la sala a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la contrasena de la sala
     *
     * @return Contrasena de la sala
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Asigna la contrasena a la sala
     *
     * @param contrasena Contrasena a asignar
     */
    public void setContrasena(String contrasena) {
        /*
        if (contrasena.isBlank() || contrasena.isEmpty()) {
            this.tieneContrasena = false;
            this.contrasena = null;
        } else if (contrasena.length() >= 5 && contrasena.length() <= 18) {
            this.contrasena = contrasena;
            this.tieneContrasena = true;
        }*/
        this.contrasena = contrasena;
    }

    /**
     * Indica si la sala tiene contrasena o no
     *
     * @return tieneContrasena
     */
    public boolean tieneContrasena() {
        if (this.contrasena != null) {
            return tieneContrasena;
        }

        return false;
    }

    /**
     * Obtiene el host de la partida
     *
     * @return el host de de la partida
     */
    public String getHost() {
        return host;
    }

    /**
     * Establece el host de la partida
     *
     * @param host el host a establecer.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Obtiene el puerto de la sala
     *
     * @return el puerto la sala
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * Establece el puerto de la sala
     *
     * @param puerto el puerto a establecer.
     */
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Obtiene el numero de fichas que se reparte a un jugador
     *
     * @return Numero de fichas a repartir a jugador
     */
    public int getNumeroFichasPorJugador() {
        return numeroFichasPorJugador;
    }

    /**
     * Establece el numero de fichas que se reparte a un jugador
     *
     * @param numeroFichasPorJugador Numero de fichas a repartir a jugador
     */
    public void setNumeroFichasPorJugador(int numeroFichasPorJugador) {
        this.numeroFichasPorJugador = numeroFichasPorJugador;
    }

    /**
     * Obtiene la cantidad maxima de jugadores permitidos en la sala
     *
     * @return Cantidad maxima jugadores
     */
    public int getMaxJugadores() {
        return maxJugadores;
    }

    /**
     * Asigna la cantidad maxima de jugadores en la sala
     *
     * @param maxJugadores Cantidad Maxima jugadores a asignar
     */
    public void setMaxJugadores(int maxJugadores) {
        this.maxJugadores = maxJugadores;
    }

    /**
     * Obtiene la cantidad de jugadores actuales en la sala
     *
     * @return the jugadoresEnSala
     */
    public int getJugadoresEnSala() {
        return jugadoresEnSala;
    }

    /**
     * Asigna la cantidad de jugadores actuales en la sala
     *
     * @param jugadoresEnSala Cantidad de jugadores en la sala
     */
    public void setJugadoresEnSala(int jugadoresEnSala) {
        this.jugadoresEnSala = jugadoresEnSala;
    }

}
