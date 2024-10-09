
package com.equipo1.logicadomino;

import entidades.ConfiguracionJuego;
import entidades.Jugador;
import entidades.Partida;
import entidades.Pozo.Ficha;
import tablero.TableroGUI;

/**
 *
 * @author 
 */
public class LogicaDomino {

    public static void main(String[] args) {
        Jugador anfitrion = new Jugador();
        anfitrion.setNombre("Alfonso");
        anfitrion.setAvatar("DonAlfonsoDestroyer");
        anfitrion.setPartidasGanadas(0);
        
        Partida partida = new Partida(anfitrion);
        
        // se crea la configuracion (solo 3 jugadores y 5 fichas para cada uno)
        ConfiguracionJuego configuracion = new ConfiguracionJuego(3, 5);
        partida.setConfiguracion(configuracion);
        
        // se crea otro jugador
        Jugador jugador2 = new Jugador();
        jugador2.setNombre("Pedro");
        jugador2.setAvatar("ElPerroShesh14");
        jugador2.setPartidasGanadas(0);
        boolean agregado = partida.agregarJugador(jugador2);
        System.out.println(agregado);
        
        // se inicia la partida
        partida.iniciar();
        
        System.out.println("Fichas jugador 1: ");
        for (Ficha f: anfitrion.obtenerFichas()) {
            System.out.println(f);
        }
        
        System.out.println("Fichas jugador 2: ");
        for (Ficha f: jugador2.obtenerFichas()) {
            System.out.println(f);
        }
        
        System.out.println("Estado de la partida: " + partida.getEstado());
        
        TableroGUI tablero = new TableroGUI(partida);
        tablero.setVisible(true);
        
    }
}
