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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Cristian
 */
public class Tablero extends JPanel implements ActionListener, KeyListener{

    public Tablero(int ancho, int alto) {
        this.dimension = new Dimension(ancho, alto);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.timer = new Timer(100, this);
        this.timer.start();
        
        this.dificultad = 1;
        this.t = 0;
        
        this.carrilesSeguros = new ArrayList(0);
        this.vias = new ArrayList(0);
        this.rios = new ArrayList(0);
        this.rana = new Personaje(100, 1, dimension);
        /*este segmento solo es una prueba, demostrando guardar algunos carriles, 
        algunos de ellos con sus propios objetos*/
        for(int i = 0; i<5; i++){
            this.vias.add(new CarrilDinamico((93*i)+31, 1, "via.png", dimension, this.dificultad, i%2==0));
            this.rios.add(new CarrilReactivo(93*i, 4, "agua.png", dimension, this.dificultad, i%2==0));
            this.carrilesSeguros.add(new Carril((93*i)+62, 2, "pasto.png", dimension));
        }
        for(int i = 0; i<5; i++){   
            this.carrilesSeguros.get(i).colocarArbol(i*20);
            this.carrilesSeguros.get(i).colocarArbol(i*60);
        }
        
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        /*aunnque por ahora es una prueba, el paintComponent final será muy parecido
            con un for para dibujar todos los carriles (los cuales a su vez dibujan todos los objetos)*/
        //this.rana.setX(t/100);
        for(Carril carril: this.carrilesSeguros){
            carril.dibujar(g, this);
            carril.cambiarFotogramas();
        }
        for(CarrilDinamico via: this.vias){
            via.dibujar(g, this);
            via.cambiarFotogramas();
            if(t%100 == 0){
                via.lanzarMovil();
            }
        }
        for(CarrilReactivo rio: this.rios){
            rio.dibujar(g, this);
            rio.cambiarFotogramas();
            if(t%150 == 0){
                rio.lanzarMovil();
            }
        }
        this.rana.dibujar(g, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        t++;
        if(t==2000){
            t = 0;
        }
        if(t%250==0){
            dificultad++;
        }
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            this.rana.keyPressed(e);
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    private ArrayList<Carril> carrilesSeguros;
    private ArrayList<CarrilDinamico> vias;
    private ArrayList<CarrilReactivo> rios;
    private Personaje rana;
    private Timer timer; 
    private Dimension dimension;
    private int dificultad;
    private int t;
}
