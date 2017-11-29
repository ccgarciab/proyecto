/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import interfaz.Niveles;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
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

    public Tablero(int ancho, int alto, int dificultad) {
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
        int alturaFotograma = 31;
        this.rana = new Personaje(800-(alturaFotograma*24), 1, dimension);
        int cantidadIniciales =(1800/31)/3;
        int restante = alto-alturaFotograma;
        System.out.println("can inic = "+cantidadIniciales);
        /*este segmento solo es una prueba, demostrando guardar algunos carriles, 
        algunos de ellos con sus propios objetos*/
        while(restante>0){
            this.vias.add(new CarrilDinamico(restante, 1, "via.png", dimension, dificultad, this.rand.nextBoolean()));
            restante -= alturaFotograma;
            this.rios.add(new CarrilReactivo(restante, 4, "agua.png", dimension, dificultad, this.rand.nextBoolean()));
            restante -= alturaFotograma;
            this.carrilesSeguros.add(new Carril(restante, 2, "pasto.png", dimension));
            restante -= alturaFotograma;
        }

        this.todosLosCarriles.addAll(vias);
        this.todosLosCarriles.addAll(carrilesSeguros);
        this.todosLosCarriles.addAll(rios);
        for(int i = 0; i<this.todosLosCarriles.size(); i++){
            this.todosLosCarriles.get(i).colocarMonedas();
            this.todosLosCarriles.get(i).colocarObjeto();
            this.todosLosCarriles.get(i).colocarObjeto();
            this.todosLosCarriles.get(i).colocarObjeto();
            this.todosLosCarriles.get(i).colocarObjeto();
            this.todosLosCarriles.get(i).colocarObjeto();
        }
        
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
        this.validarColisiones();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        tiempo++;
        if(this.tiempo == this.periodo){
            this.tiempo = 1;
            this.dificultad++;
        }
//        this.validarColisiones();
        this.rotarCarriles();
        this.checkScrolling();
        if(this.rana.isVivo()){
            repaint();
        }else{
            System.out.println("game over");
            Niveles obj = new Niveles();
            obj.setVisible(true);
            timer.stop();
            
        }
    }
    
    public void checkScrolling(){
        if(this.tiempo%this.periodo == 0||this.rana.getRefY()>(3*this.getHeight())/4){
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
            Carril nuevoCarril;
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
    
    public void validarColisiones(){
        boolean hayColision = false;
        String nombreObjeto = "";
        int indiceCarriles = 0;
        int indiceObjetos = 0;
        Rectangle rectanguloRana = this.rana.obtenerRectangulo();
        for(Carril carril: this.todosLosCarriles){
            indiceObjetos = 0;
            for(Objeto objeto: carril.getObjetos()){
                if(rectanguloRana.intersects(objeto.obtenerRectangulo())){
                    hayColision = true;
                    nombreObjeto = objeto.getNombreArchivo();
                    break;
                }
                indiceObjetos++;
            }
            if(hayColision){
                break;
            }
            indiceCarriles++;
        }
        if(hayColision){
            if(nombreObjeto.contains("carro")){
                this.rana.toggleVida();
            }else if(nombreObjeto.contains("arbol")){
                this.rana.undoMove();
            }else if(nombreObjeto.contains("moneda")){
                this.rana.subirPuntaje();
                this.todosLosCarriles.get(indiceCarriles).getObjetos().remove(this.todosLosCarriles.get(indiceCarriles).getObjetos().get(indiceObjetos));
            }else if(nombreObjeto.contains("tronco")){
                this.rana.setX(this.todosLosCarriles.get(indiceCarriles).getObjetos().get(indiceObjetos).getX());
            }
            return;
        }
        for(CarrilReactivo rio: this.rios){
            if(rectanguloRana.intersects(rio.obtenerRectangulo())){
                this.rana.toggleVida();
                break;
            }
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
