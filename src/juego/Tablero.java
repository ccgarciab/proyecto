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
import java.util.Random;
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
        this.rand = new Random();
        
        this.dificultad = 1;
        this.tiempo = 0;
        this.periodo = 199;
        
        this.carrilesSeguros = new ArrayList(0);
        this.vias = new ArrayList(0);
        this.rios = new ArrayList(0);
        this.todosLosCarriles = new ArrayList(0);
        this.rana = new Personaje(0, 1, dimension);
        /*este segmento solo es una prueba, demostrando guardar algunos carriles, 
        algunos de ellos con sus propios objetos*/
        for(int i = 0; i<5; i++){
            this.vias.add(new CarrilDinamico((93*i)+31, 1, "via.png", dimension, this.dificultad, i%2==0));
            this.rios.add(new CarrilReactivo(93*i, 4, "agua.png", dimension, this.dificultad, i%2==0));
            this.carrilesSeguros.add(new Carril((93*i)+62, 2, "pasto.png", dimension));
        }
        for(int i = 0; i<5; i++){   
            this.carrilesSeguros.get(i).colocarObjeto();
            this.carrilesSeguros.get(i).colocarObjeto();
            this.carrilesSeguros.get(i).colocarObjeto();
            this.carrilesSeguros.get(i).colocarObjeto();            
        }
        this.todosLosCarriles.addAll(vias);
        this.todosLosCarriles.addAll(carrilesSeguros);
        this.todosLosCarriles.addAll(rios);
        
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        /*aunnque por ahora es una prueba, el paintComponent final será muy parecido
            con un for para dibujar todos los carriles (los cuales a su vez dibujan todos los objetos)*/
        for(Carril carril: this.todosLosCarriles){
            carril.dibujar(g, this);
            carril.cambiarFotogramas();
            if(scrolling){
                carril.scrollEnY(); 
            }
        }
        for(CarrilDinamico via: this.vias){
            if(this.tiempo%75 == 0 && this.rand.nextDouble()>0.4){
                via.lanzarMovil();
            }
        }
        for(CarrilReactivo rio: this.rios){
            if(this.tiempo%75 == 0 && this.rand.nextDouble()>0.6){
                rio.lanzarMovil();
            }
        }
        if(scrolling){
            this.rana.scrollEnY();
        }
        this.rana.dibujar(g, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        tiempo++;
        if(this.tiempo == this.periodo){
            this.tiempo = 1;
            this.dificultad++;
        }
        this.rotarCarriles();
        this.checkScrolling();
        repaint();
    }
    
    public void checkScrolling(){
        if(this.tiempo%this.periodo/this.dificultad == 0||this.rana.getRefY()>(3*this.getHeight())/4){
            scrolling = true;
        }else{
            scrolling = false;
        }
    }
    
    public void rotarCarriles(){
        boolean cambio = false;
        for(Carril carril: this.todosLosCarriles){
            if(carril.getRefY()<0){
                carril.getObjetos().clear();
                carril = null;
                this.todosLosCarriles.remove(carril);
                cambio = true;
            }
        }
        if(cambio){
            Carril nuevoCarril = null;
            double aleatorio = this.rand.nextDouble();
            if(aleatorio>0.66){
                nuevoCarril = new Carril(this.getHeight(), 2, "pasto.png", dimension);
                this.carrilesSeguros.add(nuevoCarril);
            }else if(aleatorio>0.33){
                nuevoCarril = new CarrilDinamico(this.getHeight(), 1, "via.png", dimension, this.dificultad, rand.nextBoolean());
                this.vias.add((CarrilDinamico)nuevoCarril);
            }else{
                nuevoCarril = new CarrilReactivo(this.getHeight(), 4, "agua.png", dimension, this.dificultad, rand.nextBoolean());
                this.rios.add((CarrilReactivo)nuevoCarril);
            }
            for(int i = 0; i<6; i++){
                nuevoCarril.colocarObjeto();
                nuevoCarril.colocarMonedas();
            }
            this.todosLosCarriles.add(nuevoCarril);
        }
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
    private ArrayList<Carril> todosLosCarriles;
    private Personaje rana;
    private Timer timer; 
    private Dimension dimension;
    private int dificultad;
    private int tiempo;
    private int periodo;
    private boolean scrolling;
    private Random rand;
}
