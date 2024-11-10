
package observador;

import java.util.Map;

/**
 *
 * @author neri
 */
public interface ObservableConexion {
    public void notificarEvento();
    public void agregarObservador(ObservadorConexion observador);
    public void removerObservador(ObservadorConexion observador);
    
}
