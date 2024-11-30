package repositorios.excepciones;

/**
 * Excepción personalizada para manejar errores relacionados con el repositorio
 * de partidas en un entorno de bus de servicios.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class RepositorioPartidasException extends Exception {

    // Códigos de error específicos para diferentes tipos de fallos
    public enum ErrorCode {
        PARTIDA_NO_ENCONTRADA,
        PARTIDA_YA_EXISTE,
        JUGADOR_NO_PERMITIDO,
        ESTADO_INVALIDO,
        ERROR_INTERNO,
        LIMITE_EXCEDIDO
    }

    // Código de error específico para facilitar manejo y clasificación
    private final ErrorCode errorCode;

    /**
     * Constructor por defecto con código de error genérico
     */
    public RepositorioPartidasException() {
        this("Ocurrió un error en el repositorio de partidas.", ErrorCode.ERROR_INTERNO);
    }

    /**
     * Constructor con mensaje personalizado
     *
     * @param message Mensaje descriptivo del error
     */
    public RepositorioPartidasException(String message) {
        this(message, ErrorCode.ERROR_INTERNO);
    }

    /**
     * Constructor con mensaje y código de error específico
     *
     * @param message Mensaje descriptivo del error
     * @param errorCode Código de error específico
     */
    public RepositorioPartidasException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor con mensaje, causa y código de error
     *
     * @param message Mensaje descriptivo del error
     * @param cause Causa original de la excepción
     * @param errorCode Código de error específico
     */
    public RepositorioPartidasException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Obtiene el código de error específico
     *
     * @return Código de error
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Método de fábrica para crear una excepción de partida no encontrada
     *
     * @param salaId Identificador de la sala
     * @return Excepción con código de error específico
     */
    public static RepositorioPartidasException partidaNoEncontrada(String salaId) {
        return new RepositorioPartidasException(
                "No se encontró la partida con ID: " + salaId,
                ErrorCode.PARTIDA_NO_ENCONTRADA
        );
    }

    /**
     * Método de fábrica para crear una excepción de partida ya existente
     *
     * @param salaId Identificador de la sala
     * @return Excepción con código de error específico
     */
    public static RepositorioPartidasException partidaYaExiste(String salaId) {
        return new RepositorioPartidasException(
                "Ya existe una partida con ID: " + salaId,
                ErrorCode.PARTIDA_YA_EXISTE
        );
    }
}
