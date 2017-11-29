/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class Carril extends Dibujable{

    public Carril(int refY, int fotogramas, String nombreImagen, Dimension dimension) {
        super(refY, fotogramas, nombreImagen, dimension);
        this.objetos = new ArrayList(0);
        this.nombreObjetoPropio = "arbol";
        this.rand = new Random();
    }

    @Override
    public void dibujar(Graphics g, JPanel panel) {
        for(int i = 0; i<(this.largoPantalla/this.anchoFotograma)+1; i++){
            g.drawImage(imagen, refX+(i*this.anchoFotograma), refY, refX+((i+1)*this.anchoFotograma), 
                refY+this.altoFotograma, this.anchoFotograma*this.fotogramaActual, 
                0, this.anchoFotograma*this.fotogramaActual + this.anchoFotograma, this.altoFotograma, panel);
        }
        for(Objeto objeto: this.objetos){
            objeto.dibujar(g, panel);
        }
    }
    
    @Override
    public void scrollEnY(){
        this.refY -= this.altoFotograma;
        for(Objeto objeto: this.objetos){
            objeto.scrollEnY();
        }
    }
    
    public void colocarObjeto(){
        boolean intersectando = true;
        Rectangle rect = null;
        Objeto objeto = new Objeto(refY, 2, this.nombreObjetoPropio+".png",new Dimension(this.largoPantalla, this.altoPantalla));
        while(intersectando){
            objeto.setX(rand.nextInt(this.largoPantalla));
            rect = objeto.obtenerRectangulo();
            for(int i = 0; i<this.objetos.size(); i++){
                if(rect.intersects(this.objetos.get(i).obtenerRectangulo())){
                    break;
                }else if(i == this.objetos.size()-1){
                    intersectando = false;
                }
            }
            if(this.objetos.isEmpty()){
                break;
            }
        }
        this.objetos.add(objeto);
    }
    
    public void colocarMonedas(){
        boolean intersectando = true;
        Rectangle rect = null;
        Objeto objeto = new Objeto(refY, 8, "moneda.png",new Dimension(this.largoPantalla, this.altoPantalla));
        while(intersectando){
            objeto.setX(rand.nextInt(this.largoPantalla));
            rect = objeto.obtenerRectangulo();
            for(int i = 0; i<this.objetos.size(); i++){
                if(rect.intersects(this.objetos.get(i).obtenerRectangulo())){
                    break;
                }else if(i == this.objetos.size()-1){
                    intersectando = false;
                }
            }
            if(this.objetos.isEmpty()){
                break;
            }
        }
        this.objetos.add(objeto);
    }
    
    @Override
    public void cambiarFotogramas(){
        if(fotogramaActual == fotogramas-1){
            fotogramaActual = 0;
        }else{
            fotogramaActual++;
        }
        for(Objeto objeto: this.objetos){
            objeto.cambiarFotogramas();
        }
    }
    
    public ArrayList<Objeto> getObjetos(){
        return this.objetos;
    }
    
    protected ArrayList<Objeto> objetos; 
    protected String nombreObjetoPropio;
    protected  Random rand;
}
