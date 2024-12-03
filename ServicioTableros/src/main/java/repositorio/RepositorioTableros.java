
package repositorio;

import entidades.Pozo.Ficha;
import entidades.Sala;
import entidades.Tablero;
import java.util.HashMap;
import java.util.Map;
import repositorio.excepciones.RepositorioTablerosException;

/**
 * Contiene los tableros de las partidas activas en el sistema
 * @author Saul Neri
 * @version 1.0
 */
public class RepositorioTableros {

    private static RepositorioTableros instance;

    /**
     * Contiene los tableros asociados a salas por su nombre
     */
    private Map<String, Tablero> tableros;

    /**
     * Constructor privado oculto
     */
    private RepositorioTableros() {
        this.tableros = new HashMap<>();
    }

    /**
     * Obtiene la instancia del repositorio de tableros
     *
     * @return RepositorioTableros
     */
    public static RepositorioTableros getInstance() {
        if (instance == null) {
            instance = new RepositorioTableros();
        }

        return instance;
    }

    /**
     * Registra un nuevo tablero asociado a una partida de una sala activa en el sistema
     * @param sala Informacion de la sala
     * @return Tablero guardado
     * @throws RepositorioTablerosException Si no se puede crear el tablero
     */
    public Tablero crearTablero(Sala sala) throws RepositorioTablerosException {
        if (sala == null) {
            throw new RepositorioTablerosException("La sala ingresada es null");
        }
        boolean tableroExiste = this.tableros.containsKey(sala.getNombre());
        if (tableroExiste) {
            throw new RepositorioTablerosException("La sala \"%s\" ya cuenta con un tablero activo en el sistema");
        }

        Tablero tablero = new Tablero();
        
        this.tableros.put(sala.getNombre(), tablero);
        
        return tablero;
    }

    /**
     * Elimina un tablero asociado a una partida en una sala activa en el sistema
     * @param sala Informacion de la sala
     * @throws RepositorioTablerosException Si no se puede eliminar el tablero
     */
    public void eliminarTablero(Sala sala) throws RepositorioTablerosException {
        
        boolean noExiste = !(this.tableros.containsKey(sala.getNombre()));
        if (noExiste) {
            throw new RepositorioTablerosException("No existe un tablero asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
        
        this.tableros.remove(sala.getNombre());
    }
    
    /**
     * Agrega una ficha en alguno de los dos lados del tren de fichas en el tablero
     * @param sala Informacion de la sala
     * @param ficha Ficha a poner en el tablero
     * @param direccion Direccion en donde se pondra la ficha en el tren de fichas (Izquierda o Derecha)
     * @throws RepositorioTablerosException Si no se puede agregar la ficha al tablero
     */
    public void agregarFichaEnTablero(Sala sala, Ficha ficha, String direccion) throws RepositorioTablerosException {
        Tablero tablero = this.tableros.get(sala.getNombre());
        
        boolean noExiste = tablero == null;
        if (noExiste) {
            throw new RepositorioTablerosException("No existe un tablero asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
         
        if (tablero.estaVacio()) {
            if (ficha.esMula()) {
                tablero = new Tablero(ficha);
                // se reemplaza el tablero con uno nuevo y listo para empezar...
                this.tableros.put(sala.getNombre(), tablero);
                
                return;
            }
        }
        
        if (direccion.equalsIgnoreCase("derecha")) {
            tablero.agregarFichaExtremoDerecho(ficha);
        } 
        else if (direccion.equalsIgnoreCase("izquierda")) {
            tablero.agregarFichaExtremoIzquierdo(ficha);
        } else {
            throw new RepositorioTablerosException("No se pudo agregar la ficha al tablero ya que no se especifico su direccion");
        }
    }
    
    /**
     * Se reinicia el tablero asociado a una partida de una sala activa a su estado inicial
     * @param sala Informacion de la sala
     * @throws RepositorioTablerosException Si no se puede reiniciar el tablero
     */
    public void reiniciarTablero(Sala sala) throws RepositorioTablerosException {
        boolean noExiste = !(this.tableros.containsKey(sala.getNombre()));
        if (noExiste) {
            throw new RepositorioTablerosException("No existe un tablero asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
        
        this.tableros.put(sala.getNombre(), new Tablero());
    }
}
