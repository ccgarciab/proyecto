/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public abstract class Dibujable {

    public Dibujable(int refY, int fotogramas, String nombreImagen, Dimension dimension) {
        this.refY = refY;
        this.refX = 0;
        this.fotogramas = fotogramas;
        this.fotogramaActual = 0;
        this.nombreObjeto = nombreImagen;
        this.imagen = cargarImagen(nombreImagen);
        this.largoPantalla = (int)dimension.getWidth();
        this.altoPantalla = (int)dimension.getHeight();
    }

    public final Image cargarImagen(String nombreImagen){
        ImageIcon ii = new ImageIcon(nombreImagen);
        this.altoFotograma = ii.getIconHeight();
        this.anchoFotograma = ii.getIconWidth()/this.fotogramas;
        Image image = ii.getImage();
        return image;
    }
    
    //itera atraves de las imagenes que conforman la animacion
    public abstract void cambiarFotogramas();
    
    public int getRefY(){
        return this.refY;
    }
    
    public String getNombreArchivo(){
        return this.nombreObjeto;
    }
    
    public abstract void dibujar(Graphics g, JPanel panel);
    
    //mueve la posicion del objeto en el panel hacia arriba
    public abstract void scrollEnY();
    
    protected int refY;
    protected int refX;
    protected Image imagen;
    //fotogramas == cantidad de imagenes que conforman la animacion
    protected int fotogramas;
    protected int anchoFotograma;
    protected int altoFotograma;
    protected int fotogramaActual;
    //valores que se obtienen desde el frame que contiene la viasualización
    protected int largoPantalla;
    protected int altoPantalla;
    protected String nombreObjeto;
}
