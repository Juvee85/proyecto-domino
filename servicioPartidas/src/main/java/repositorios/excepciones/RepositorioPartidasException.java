/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package repositorios.excepciones;

/**
 * Excepción personalizada para manejar errores relacionados con el repositorio de partidas.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class RepositorioPartidasException extends Exception {

    /**
     * Constructor por defecto.
     */
    public RepositorioPartidasException() {
        super("Ocurrió un error en el repositorio de partidas.");
    }

    /**
     * Constructor que permite especificar un mensaje personalizado.
     * 
     * @param message Mensaje de error.
     */
    public RepositorioPartidasException(String message) {
        super(message);
    }

    /**
     * Constructor que permite especificar un mensaje y una causa.
     * 
     * @param message Mensaje de error.
     * @param cause   Causa original de la excepción.
     */
    public RepositorioPartidasException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que permite especificar una causa.
     * 
     * @param cause Causa original de la excepción.
     */
    public RepositorioPartidasException(Throwable cause) {
        super(cause);
    }
}
