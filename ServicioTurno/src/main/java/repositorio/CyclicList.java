/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author diana
 * @param <T>
 */
public class CyclicList<T> {

    private List<T> list;
    private int index = 0;

    public CyclicList(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacÃ­a.");
        }
        this.list = list;
    }

    /**
     * Devuelve el valor actual en el ciclo y cambia al siguiente despues de
     * devolver el valor.
     *
     * @return
     */
    public T next() {
        T value = list.get(index);
        index = (index + 1) % list.size(); // Incremento cÃ­clico
        return value;
    }

    /**
     * Obtiene el valor actual sin pasar al siguiente despues de devolver el
     * mismo.
     *
     * @return
     */
    public T current() {
        return list.get(index);
    }

    /**
     * Agrega un nuevo elemento a la lista ciclica.
     *
     * @param value
     */
    public void add(T value) {
        if (this.list != null) {
            this.list.add(value);
        }
    }

    /*
    public static void main(String args[]) {
     CyclicList<String> turnos = new CyclicList<>(new ArrayList<>(Arrays.asList("Jugador 1", "Jugador 2", "Jugador 3")));

       for (int i=0; i < 10; i++) {
         System.out.println(turnos.next());
   }

      turnos.add("Jugador 4");

      System.out.println("Despues de la primera iteracion");

      for (int i=0; i < 10; i++) {
                                
      System.out.println(turnos.next());
                        }


                }
     */
}
