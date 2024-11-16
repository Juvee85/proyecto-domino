/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package repositorio.excepciones;

/**
 * Excepcion usada para manejar errores en el acceso o consulta del repositorio de tableros
 * @author Saul Neri
 * @version 1.0
 */
public class RepositorioTablerosException extends Exception {
    public RepositorioTablerosException(String msg) {
        super(msg);
    }
}
