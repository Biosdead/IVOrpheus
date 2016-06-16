package IMATVI;

import IMATVI.Main;
import IMATVI.Interface;
import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;

/**
 *
 * @author lennonsalesfurtado
 */
public class ThreadIFC extends Thread {
     public void run() {
         
                    
         try {
             Interface.BtnsIFC();

             if (Interface.painelIFC.getParent() == null) {
            	 Interface.desktopIFC.add(Interface.painelIFC);
            	 Interface.desktopIFC.setDoubleBuffered(true);
            	 Interface.desktopIFC.setComponentZOrder(Interface.painelIFC,Interface.desktopIFC.getComponentCount()-2);
                 Main.Interface.setContentPane(((Interface)Main.Interface).PainelIFC());
                 Interface.painelIFC.setSelected(true);
                   }
         } catch (GrammarException ex) {
             Logger.getLogger(ThreadIFC.class.getName()).log(Level.SEVERE, null, ex);
         } catch (EngineStateError ex) {
             Logger.getLogger(ThreadIFC.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(ThreadIFC.class.getName()).log(Level.SEVERE, null, ex);
         } catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         Interface.plot3D.rotate(0, 0); // O rotate serve apenas para atualizar a GUI do jamathplot para o plot3D ficar no centro da tela e não no canto



     }

}