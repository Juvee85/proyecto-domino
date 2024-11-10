/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesObservador.salas;

import DTOS.SalaDTO;

/**
 *
 * @author neri
 */
public interface ObservableCrearSala {
    public void notificar(SalaDTO sala);

    public void anhadirObservador(ObservadorCrearSala observador);

    public void removerObservador(ObservadorCrearSala observador);
}
