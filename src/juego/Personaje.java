/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 *
 * @author Cristian
 */
public class Personaje extends Objeto{
    
    public Personaje(int refY, int fotogramas, Dimension dimension) {
        super(refY, fotogramas, "rana.png", dimension);
    }
    
    public void keyPressed(KeyEvent e) throws IOException {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN: this.refY += 10;break;
            case KeyEvent.VK_UP: this.refY -= 10;break;
            case KeyEvent.VK_LEFT: this.refX -= 10;break;
            case KeyEvent.VK_RIGHT: this.refX += 10;break;
              
          }
    }
}
