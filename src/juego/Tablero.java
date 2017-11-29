/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Cristian
 */
public class Tablero extends JPanel implements ActionListener{

    public Tablero(int ancho, int alto) {
        this.dimension = new Dimension(ancho, alto);
        this.setFocusable(true);
        this.timer = new Timer(100, this);
        this.timer.start();
        this.carriles = new ArrayList();
        /*este segmento solo es una prueba, demostrando guardar algunos carriles, 
        algunos de ellos con sus propios objetos*/
        CarrilDinamico  via = null;
        for(int i = 0; i<5; i++){
            this.carriles.add(new Carril(93*i, 4, "agua.png", dimension));
            via = new CarrilDinamico((93*i)+31, 1, "via.png", dimension, i%2==0);
            via.lanzarMovil();
            this.carriles.add(via);
            this.carriles.add(new Carril((93*i)+62, 2, "pasto.png", dimension));
            this.carriles.get((3*i)+2).colocarArbol(i*20);
        }
        
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        /*aunnque por ahora es una prueba, el paintComponent final será muy parecido
            con un for para dibujar todos los carriles (los cuales a su vez dibujan todos los objetos)*/
        for(Carril carril: this.carriles){
            carril.dibujar(g, this);
            carril.cambiarFotogramas();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    private ArrayList<Carril> carriles;
    private Timer timer; 
    private Dimension dimension;
}
