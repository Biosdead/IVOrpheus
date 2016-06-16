/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IVOrpheus2Final;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;

/**
 *
 * @author lennonsalesfurtado
 */
public class ThreadDsd extends Thread {
    private boolean firstTime = true;
    public void run() {
        
        if (Vis3D.IsPlot3D) {
            Interface.plot3D.plotCanvas.invalidate();
            Interface.plot3D.plotCanvas.repaint();
        }else{
            Interface.plot2D.plotCanvas.invalidate();
            Interface.plot2D.plotCanvas.repaint();
        }
        while (true) {
            if (LegendPanelIV.container.isValid()) {
                if (firstTime) {
                try {
//                    Interface.Quadrant4.doClick();
                    synchronized(this){
                    wait(200);
                    }
                    System.out.println("Thread Inside");
                    BtnListener.infoVisModule.DetailsOnDemand();
                    break;
                } catch (NumberFormatException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadDsd.class.getName()).log(Level.SEVERE, null, ex);
                }
                Interface.plot2D.plotCanvas.invalidate();
                Interface.plot2D.plotCanvas.repaint();
                try {
                    synchronized(this){
                    wait(200);
                    }
                    System.out.println("Thread Inside 2");
                    BtnListener.infoVisModule.DetailsOnDemand();
                    break;
                } catch (NumberFormatException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadDsd.class.getName()).log(Level.SEVERE, null, ex);
                }
                firstTime = false;
            }
            
        }
            if (LegendPanelIV.container.isValid()) {
                try {
                    synchronized(this){
                    wait(200);
                    }
                    System.out.println("Thread Inside 2");
                    BtnListener.infoVisModule.DetailsOnDemand();
                    break;
                } catch (NumberFormatException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadDsd.class.getName()).log(Level.SEVERE, null, ex);
                }
               }
            }
       
  }
}