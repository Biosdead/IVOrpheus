/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IVOrpheus2Final;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.math.plot.render.AWTDrawer3D;
import org.math.plot.render.Projection3D;

/**
 *
 * @author lennonsalesfurtado
 */
public class ThreadInteraction extends Thread {
public static boolean Esquerda;    
public static boolean Direita;    
public static boolean Cima;    
public static boolean Baixo;    
public static boolean Tras;    
public static boolean Frente;  
public static boolean Aumentar;  
public static boolean Diminuir;  
public static boolean Continuar = true;  
public static int E,D,C,B,F,T;  

public static void Esquerda(){
Esquerda = true;
Direita = false;
Cima = false;
Baixo = false;
Tras = false;
Frente = false;
Diminuir = false;
Aumentar = false;
Continuar = true;
}

public static void Direita(){
Esquerda = false;
Direita = true;
Cima = false;
Baixo = false;
Tras = false;
Frente = false;
Diminuir = false;
Aumentar = false;
Continuar = true;
}
public static void Cima(){
Esquerda = false;
Direita = false;
Cima = true;
Baixo = false;
Tras = false;
Frente = false;
Diminuir = false;
Aumentar = false;
Continuar = true;
}

public static void Baixo(){
Esquerda = false;
Direita = false;
Cima = false;
Baixo = true;
Tras = false;
Frente = false;
Diminuir = false;
Aumentar = false;
Continuar = true;
}

public static void Tras(){
Esquerda = false;
Direita = false;
Cima = false;
Baixo = false;
Tras = true;
Frente = false;
Diminuir = false;
Aumentar = false;
Continuar = true;
}
public static void Frente(){
Esquerda = false;
Direita = false;
Cima = false;
Baixo = false;
Tras = false;
Frente = true;
Diminuir = false;
Aumentar = false;
Continuar = true;
}

public static void Aumentar(){
Esquerda = false;
Direita = false;
Cima = false;
Baixo = false;
Tras = false;
Frente = false;
Diminuir = false;
Aumentar = true;
Continuar = true;
}

public static void Diminuir(){
Esquerda = false;
Direita = false;
Cima = false;
Baixo = false;
Tras = false;
Frente = false;
Aumentar = false;
Diminuir = true;
Continuar = true;
}

public static void ZeraTudo(){
E =0;
D =0;
C =0;
B =0;
F =0;
T =0;
}

public void run() {
while(Continuar){

    if (BtnListener.MoverGirar) {
        Interface.plot2D.plotCanvas.ActionMode = 1; //Traslation
        if (Esquerda) {
            if (Vis3D.IsPlot3D) {
            Interface.plot3D.setLocation(Interface.plot3D.getX() - 1, Interface.plot3D.getY()); 
            }else{
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mousePress(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2 + Interface.plot2D.plotCanvas.getWidth()/20, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mouseRelease(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.Esquerda.getX()+Interface.Esquerda.getWidth()/2,Interface.Esquerda.getY()+Interface.Esquerda.getHeight()/2);
            } catch (AWTException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Continuar = false;
            }
          }
        if (Direita) {
            if (Vis3D.IsPlot3D) {
            Interface.plot3D.setLocation(Interface.plot3D.getX() + 1, Interface.plot3D.getY());
            }else{
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mousePress(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2 - Interface.plot2D.plotCanvas.getWidth()/20, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mouseRelease(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.Direita.getX()+Interface.Direita.getWidth()/2,Interface.Direita.getY()+Interface.Direita.getHeight()/2);
            } catch (AWTException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Continuar = false;
            }
        }
        if (Cima) {
            if (Vis3D.IsPlot3D) {
            Interface.plot3D.move(Interface.plot3D.getX(), Interface.plot3D.getY() + 1);
            }else{
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mousePress(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2 + Interface.plot2D.plotCanvas.getHeight()/20);
                robot.mouseRelease(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.Cima.getX()+Interface.Cima.getWidth()/2,Interface.Cima.getY()+Interface.Cima.getHeight()/2);
            } catch (AWTException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Continuar = false;
            }
           }
        if (Baixo) {
            if (Vis3D.IsPlot3D) {
            Interface.plot3D.move(Interface.plot3D.getX(), Interface.plot3D.getY() - 1);
           }else{
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mousePress(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2 - Interface.plot2D.plotCanvas.getHeight()/20);
                robot.mouseRelease(InputEvent.BUTTON1_MASK); 
                robot.mouseMove(Interface.Baixo.getX()+Interface.Baixo.getWidth()/2,Interface.Baixo.getY()+Interface.Baixo.getHeight()/2);
            } catch (AWTException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Continuar = false;
            }
        }
        if (Tras) {
            Interface.plot3D.move(Interface.plot3D.getX() - 1, Interface.plot3D.getY() - 1);
        }
        if (Frente) {
            Interface.plot3D.move(Interface.plot3D.getX() + 1, Interface.plot3D.getY() + 1);
        }
        
    }else{
//        Interface.plot2D.plotCanvas.ActionMode = 0; //Dilatation
        if (Esquerda) {
            Interface.plot3D.rotate(0.0000001, 0);
            E++;
        }
        if (Direita) {
            Interface.plot3D.rotate(-0.0000001, 0);
            D++;
        }
        if (Cima) {
            Interface.plot3D.rotate(0, 0.0000001);
            C++;
        }
        if (Baixo) {
            Interface.plot3D.rotate(0, -0.0000001);
            B++;
        }
        if (Tras) {
            Interface.plot3D.rotate(Interface.plot3D.getX() + 0.0000001, Interface.plot3D.getY() + 0.0000001);
            T++;
        }
        if (Frente) {
            Interface.plot3D.rotate(Interface.plot3D.getX() + 0.0000001, Interface.plot3D.getY());
            F++;
        }
    }
    
    if (Diminuir) {
        if (Vis3D.IsPlot3D) {
         if (BtnListener.EscalaValor < BtnListener.EscalaLimiteMax) {
                BtnListener.EscalaValor += 0.0000001;
            }
            Interface.plot3D.setDefaultZoom(BtnListener.EscalaValor);
            Interface.plot3D.rotate(0, 0);
            Interface.EscalaAtual.setText("Escala Atual: " + BtnListener.EscalaValor);
    }else{
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mouseWheel(-1); 
                robot.mouseMove(Interface.Diminuir.getX()+Interface.Diminuir.getWidth()/2,Interface.Diminuir.getY()+Interface.Diminuir.getHeight()/2);
            } catch (AWTException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Continuar = false;
        }
    }
    if (Aumentar) {
        if (Vis3D.IsPlot3D) {
            
         if (BtnListener.EscalaValor > BtnListener.EscalaLimiteMin) {
             BtnListener.EscalaValor -= 0.0000001;
            }
            Interface.plot3D.setDefaultZoom(BtnListener.EscalaValor);
            Interface.plot3D.rotate(0, 0);
            Interface.EscalaAtual.setText("Escala Atual: " + BtnListener.EscalaValor);
     }else{
            Robot robot;
            try {
                robot = new Robot();
                robot.mouseMove(Interface.plot2D.plotCanvas.getWidth()/2, Interface.plot2D.plotCanvas.getHeight()/2);
                robot.mouseWheel(1);
                robot.mouseMove(Interface.Aumentar.getX()+Interface.Aumentar.getWidth()/2,Interface.Aumentar.getY()+Interface.Aumentar.getHeight()/2);
            } catch (AWTException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        Continuar = false;

        }
    }
}

}

}
