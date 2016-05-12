/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IMATVI;

import IMATVI.BtnListener;
import IMATVI.Interface;

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
        if (Esquerda) {
            Interface.plot3D.setLocation((int) (Interface.plot3D.getX() - 1), Interface.plot3D.getY());
        }
        if (Direita) {
            Interface.plot3D.setLocation(Interface.plot3D.getX() + 1, Interface.plot3D.getY());
        }
        if (Cima) {
           Interface.plot3D.move(Interface.plot3D.getX(), Interface.plot3D.getY() - 1);
        }
        if (Baixo) {
            Interface.plot3D.move(Interface.plot3D.getX(), Interface.plot3D.getY() + 1);
        }
        if (Tras) {
            Interface.plot3D.move(Interface.plot3D.getX() - 1, Interface.plot3D.getY() - 1);
        }
        if (Frente) {
            Interface.plot3D.move(Interface.plot3D.getX() + 1, Interface.plot3D.getY() + 1);
        }
        
    }else{
    
        if (Esquerda) {
            Interface.plot3D.rotate(0.0000004, 0);
            E++;
        }
        if (Direita) {
            Interface.plot3D.rotate(-0.0000004, 0);
            D++;
        }
        if (Cima) {
            Interface.plot3D.rotate(0, 0.0000004);
            C++;
        }
        if (Baixo) {
            Interface.plot3D.rotate(0, -0.0000004);
            B++;
        }
        if (Tras) {
            Interface.plot3D.rotate(Interface.plot3D.getX() + 0.0000004, Interface.plot3D.getY() + 0.0000002);
            T++;
        }
        if (Frente) {
            Interface.plot3D.rotate(Interface.plot3D.getX() + 0.0000004, Interface.plot3D.getY());
            F++;
        }
    }
    
    if (Diminuir) {
         if (BtnListener.EscalaValor < BtnListener.EscalaLimiteMax) {
//                BtnListener.EscalaValor += 0.2;
                BtnListener.EscalaValor += 0.000004;
            }
            Interface.plot3D.setDefaultZoom(BtnListener.EscalaValor);
            Interface.plot3D.rotate(0, 0);
            Interface.EscalaAtual.setText("Escala Atual: " + BtnListener.EscalaValor);
    }
    if (Aumentar) {
        
         if (BtnListener.EscalaValor > BtnListener.EscalaLimiteMin) {
//             BtnListener.EscalaValor -= 0.2;
             BtnListener.EscalaValor -= 0.0000004;
            }
            Interface.plot3D.setDefaultZoom(BtnListener.EscalaValor);
            Interface.plot3D.rotate(0, 0);
            Interface.EscalaAtual.setText("Escala Atual: " + BtnListener.EscalaValor);
            
    }
    
}

}

}
