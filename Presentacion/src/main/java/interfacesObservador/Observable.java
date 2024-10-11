/*
 * Observable.java
 */
package interfacesObservador;

/**
 *
 * @author Juventino López García - 00000248547 - 09/10/2024
 */
public interface Observable {

    public void notificar();

    public void anhadirObservador(Observador observador);

    public void removerObservador(Observador observador);
}
