package IMATVI;

import IVOrpheus2Final.Main;
import IVOrpheus2Final.Interface;
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
         
//                    Interface.menu_Voltar.doClick();
                    
         try {
//        	 Main.Interface.setContentPane(Interface.plot3D);
//        	 Main.Interface.add(BorderLayout.NORTH, Interface.barra_menu);
             Interface.BtnsIFC();
             
            /* if (Interface.painelIFC.getParent() == null) {
                 desktopIFC.add(painelIFC);
         		        desktopIFC.setDoubleBuffered(true);
                         desktopIFC.setComponentZOrder(painelIFC,desktopIFC.getComponentCount()-2);
                         painelIFC.putClientProperty("dragMode", "fixed");
         		}
             */
             if (Interface.painelIFC.getParent() == null) {
            	 Interface.desktopIFC.add(Interface.painelIFC);
            	 Interface.desktopIFC.setDoubleBuffered(true);
            	 Interface.desktopIFC.setComponentZOrder(Interface.painelIFC,Interface.desktopIFC.getComponentCount()-2);
                 Main.Interface.setContentPane(((IVOrpheus2Final.Interface)Main.Interface).PainelIFC());
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