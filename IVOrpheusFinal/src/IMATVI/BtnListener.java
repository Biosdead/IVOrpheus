package IMATVI;
import IVOrpheus2Final.Main;
import IVOrpheus2Final.ThreadInteraction;
import IVOrpheus2Final.BtnListener;
import IVOrpheus2Final.Vis3D;
import IVOrpheus2Final.Reconhecedor;
import IVOrpheus2Final.Interface;
import IVOrpheus2Final.ThreadDsd;
import IVOrpheus2Final.LegendPanelIV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.speech.AudioException;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;

import static IVOrpheus2Final.LegendPanelIV.MaisDeUmaVez;

public class BtnListener implements ActionListener,MenuListener {
    
	public static JFrame Inter = Main.Interface; // Adicionar e suas dependencias no IVOrpheus
    public static boolean MoverGirar = false, Redimensionar = false, ConfigurarBool = false, FiltrarBool = false, Inicio = false, Fim = false,
            PrimeiroNumeroInicio = true, PrimeiroNumeroFim = true, ConfigurarCor = false, FiltrarCor = false, ConfigurarForma = false, FiltrarForma = false, ConfigurarTamanho = false, FiltrarTamanho = false, FiltrarCategoricos = false; // true para mover e false para girar
    public static double EscalaValor = 1.0; // true para mover e false para girar
    public static double EscalaLimiteMax, EscalaLimiteMin; // true para mover e false para girar
    public static boolean EixoXTradutor = false, EixoYTradutor = false, EixoZTradutor = false, LegendaPrimeiraVez2D = true, LegendaPrimeiraVez3D = true, EscalaEI = false, MoverEI = false, GirarEI = false, DetalhesFlag = false; // EI - Estado Inicial
    public static int EixoIndice;
    public static JTextField textField;
    public static Vis3D infoVisModule = new Vis3D();
    
    
	@Override
	public void actionPerformed(ActionEvent acao) {
		EscalaLimiteMax = 1.8;
		EscalaLimiteMin = 0.2;          
		
		// Implementa as acoes geradas ao apertar os botões
		if(acao.getSource() == Interface.Confirmar){

            Main.att.SetPath(Interface.GetBgBase().replaceAll("\\s+", ""));
            try {      
            	
            	 if (DetalhesFlag) {
                     Interface.BtnsDetails();
                     Reconhecedor.GramDetalhes();
                     FiltrarCategoricos = false;
                     
                     }else{
                     Interface.BtnsIFC();
                     Reconhecedor.GramIFC();
                     }
          Main.Interface.setContentPane(((IVOrpheus2Final.Interface)Main.Interface).PainelIFC());
          Interface.painelIFC.setSelected(true);
          Interface.desktopIFC.setComponentZOrder(Interface.painelIFC,Interface.desktopIFC.getComponentCount()-2);
            	} catch (GrammarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EngineStateError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PropertyVetoException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
           
/*
            if (ConfigurarCor) {
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();
                FiltrarCor = false;
                infoVisModule.indexCor = -1;
                for (int i = 0; i <= Interface.atributos.length - 1; i++) {

//                    if (Interface.atributos[i].isSelected()) {
//                    if (Interface.atributos[i] != null && Interface.atributos[i].isSelected()) {
                    if (Interface.atributos[i].isSelected()) {
                        try {
                            infoVisModule.PlotCor(Interface.IndicesOriginais[i], Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));

                        } catch (NumberFormatException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GrammarException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }


            if (ConfigurarForma) {
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();
                FiltrarForma = false;
                infoVisModule.indexForma = -1;
                int limiarForma = 4;
                for (int i = 0; i <= Interface.atributos.length-1; i++) { // Colocar o tamanho correto correspondente a quantiade de atributos disposta no painel Formas
//                    System.out.println("Att = " + Interface.atributos[i] + " IO = " + Interface.IndicesOriginais[i]);
//                    System.out.println(Interface.atributos.length);
                    try {
//                        if (Interface.atributos[i] != null && Interface.atributos[i].isSelected() && (Main.att.GetUniqueValues(i).length <= limiarForma)) {
                        if (Interface.atributos[i].isSelected() && (Main.att.GetUniqueValues(i).length <= limiarForma)) {

                            infoVisModule.PlotForma(Interface.IndicesOriginais[i], Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                ConfigurarForma = false;
            }


            if (ConfigurarTamanho) {
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();
                FiltrarTamanho = false;
                infoVisModule.indexTamanho = -1;
                for (int i = 0; i < Interface.atributos.length; i++) {

//                    if (Interface.atributos[i] != null && Interface.atributos[i].isSelected()) {
                    if (Interface.atributos[i].isSelected()) {
                        try {
                            infoVisModule.PlotTamanho(Interface.IndicesOriginais[i], Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GrammarException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

*/
            if (ConfigurarCor) {
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();
                FiltrarCor = false;
                infoVisModule.indexCor = -1;
                for (int i = 0; i <= Interface.CorSize - 1; i++) {
//                for (int i = 0; i <= Interface.atributos.length - 1; i++) { //tava funcionando com um erro
//                    System.out.println("CCCCC1 " + Interface.CorSize);
//                    if (Interface.atributos[i].isSelected()) {
//                    if (Interface.atributos[i] != null && Interface.atributos[i].isSelected()) {
                    if (Interface.atributos[i].isSelected()) {
                        try {
                            infoVisModule.PlotCor(Interface.IndicesOriginais[i], Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));

                        } catch (NumberFormatException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GrammarException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }


            if (ConfigurarForma) {
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();
                FiltrarForma = false;
                infoVisModule.indexForma = -1;
                int limiarForma = 4;
//                for (int i = 0; i <= Interface.atributos.length-1; i++) { // Colocar o tamanho correto correspondente a quantiade de atributos disposta no painel Formas
                for (int i = 0; i <= Interface.FormaSize-1; i++) { // Colocar o tamanho correto correspondente a quantiade de atributos disposta no painel Formas
//                    System.out.println("Att = " + Interface.atributos[i] + " IO = " + Interface.IndicesOriginais[i]);
//                    System.out.println(Interface.atributos.length);
                    try {
//                        if (Interface.atributos[i] != null && Interface.atributos[i].isSelected() && (Main.att.GetUniqueValues(i).length <= limiarForma)) {
                        if (Interface.atributos[i].isSelected() && (Main.att.GetUniqueValues(i).length <= limiarForma)) {

                            infoVisModule.PlotForma(Interface.IndicesOriginais[i], Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                ConfigurarForma = false;
            }


            if (ConfigurarTamanho) {
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();
                FiltrarTamanho = false;
                infoVisModule.indexTamanho = -1;
//                for (int i = 0; i < Interface.atributos.length; i++) {
                for (int i = 0; i < Interface.TamanhoSize; i++) {

//                    if (Interface.atributos[i] != null && Interface.atributos[i].isSelected()) {
                    if (Interface.atributos[i].isSelected()) {
                        try {
                            infoVisModule.PlotTamanho(Interface.IndicesOriginais[i], Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GrammarException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
            if (ConfigurarBool) {
                FiltrarBool = false;

                // Toda a vez que o eixo for configurado os filtros são resetados
                Vis3D.FiltrarCorCategorico.clear();
                Vis3D.FiltrarFormaCategorico.clear();
                Vis3D.FiltrarTamanhoCategorico.clear();

                for (int i = 0; i < Interface.atributos.length; i++) {
//                    if (Interface.atributos[i] != null && Interface.atributos[i].isSelected()) {
                    if (Interface.atributos[i].isSelected()) {
                        if (EixoXTradutor) {   //Fazer O Eixo Reconhecer
                            System.out.println("DENTRO DO X Config");
                            Vis3D.FiltrarXNumerico.clear();
                            Vis3D.FiltrarXCategorico.clear();
                            try {
                                infoVisModule.PlotBaseX(i, Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                            } catch (NumberFormatException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (GrammarException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        if (EixoYTradutor) {
                            System.out.println("DENTRO DO YConfig");
                            Vis3D.FiltrarYNumerico.clear();
                            Vis3D.FiltrarYCategorico.clear();
                            try {
                                infoVisModule.PlotBaseY(i, Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                            } catch (NumberFormatException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (GrammarException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        if (EixoZTradutor) {
                            Vis3D.FiltrarZNumerico.clear();
                            Vis3D.FiltrarZCategorico.clear();
                            try {
                                infoVisModule.PlotBaseZ(i, Interface.atributos[i].getText().substring(Interface.atributos[i].getText().indexOf('-') + 1));
                            } catch (NumberFormatException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (GrammarException ex) {
                                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }

                    }
                }


            }

            if (FiltrarBool) {
                ConfigurarBool = false;

                if (infoVisModule.FiltroNumerico) {

                    if (EixoXTradutor) {   //Fazer O Eixo Reconhecer
//                        System.out.println("DENTRO DO X Filtrar");
                        if (!Vis3D.FiltrarXNumerico.contains(Interface.InicioLbl.getText())) {
//                                             Interface.FiltrarXNumerico.add(0,Interface.InicioLbl.getText());
                            Vis3D.FiltrarXNumerico.set(0, Interface.InicioLbl.getText());
                        }
                        if (!Vis3D.FiltrarXNumerico.contains(Interface.FimLbl.getText())) {
//                                             Interface.FiltrarXNumerico.add(1,Interface.FimLbl.getText());
                            Vis3D.FiltrarXNumerico.set(1, Interface.FimLbl.getText());
                        }
                    }
                    if (EixoYTradutor) {
//                        System.out.println("DENTRO DO Y Filtrar Numerico");
                        if (!Vis3D.FiltrarYNumerico.contains(Interface.InicioLbl.getText())) {
//                                    Interface.FiltrarYNumerico.add(0,Interface.InicioLbl.getText());
                            Vis3D.FiltrarYNumerico.set(0, Interface.InicioLbl.getText());


                        }
                        if (!Vis3D.FiltrarYNumerico.contains(Interface.FimLbl.getText())) {
//                                     Interface.FiltrarYNumerico.add(1,Interface.FimLbl.getText());
                            Vis3D.FiltrarYNumerico.set(1, Interface.FimLbl.getText());
                        }
                    }
                    if (EixoZTradutor) {
//                        System.out.println("DENTRO DO Z Filtrar");
                        if (!Vis3D.FiltrarZNumerico.contains(Interface.InicioLbl.getText())) {
//                                        Interface.FiltrarZNumerico.add(0,Interface.InicioLbl.getText());
                            Vis3D.FiltrarZNumerico.set(0, Interface.InicioLbl.getText());
                        }
                        if (!Vis3D.FiltrarZNumerico.contains(Interface.FimLbl.getText())) {
//                                        Interface.FiltrarZNumerico.add(1,Interface.FimLbl.getText());
                            Vis3D.FiltrarZNumerico.set(1, Interface.FimLbl.getText());
                        }
                    }

                } else {

                    for (int i = 0; i < Interface.Categoricos.length; i++) {
                        if (!Interface.Categoricos[i].isSelected()) {

                            if (EixoXTradutor) {   //Fazer O Eixo Reconhecer
//                                System.out.println("DENTRO DO X Filtrar");
                                if (!Vis3D.FiltrarXCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarXCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
                                }
                            }
                            if (EixoYTradutor) {
//                                System.out.println("DENTRO DO Y Filtrar Categorico");
                                if (!Vis3D.FiltrarYCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
//                                             Interface.FiltrarXCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1));
                                    Vis3D.FiltrarYCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
                                }

                            }
                            if (EixoZTradutor) {
//                                System.out.println("DENTRO DO Z Filtrar");
                                if (!Vis3D.FiltrarZCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarZCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
                                }
                            }
                            if (FiltrarCor) {
//				     if (!Interface.FiltrarCorCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1))) {
                                if (!Vis3D.FiltrarCorCategorico.containsKey(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
//                                             Vis3D.FiltrarCorCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1));
                                    Vis3D.FiltrarCorCategorico.put(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1), i);
//                                             Interface.FiltrarCorCategoricoInt.add(i);
                                }
//                                   if (!Interface.FiltrarCorCategorico.contains(i)) {
//                                             Interface.FiltrarCorCategorico.add(i);
//                                            }     
                            }
                            if (FiltrarForma) {
//				System.out.println("DENTRO DO Z Filtrar");
//                                    if (!Interface.FiltrarFormaCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1))) {
                                if (!Vis3D.FiltrarFormaCategorico.containsKey(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarFormaCategorico.put(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1), i);
//                                             Interface.FiltrarFormaCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1));
//                                             Interface.FiltrarFormaCategoricoInt.add(i);
                                }
//                                }
//                                   if (!Interface.FiltrarFormaCategorico.contains(i)) {
//                                             Interface.FiltrarFormaCategorico.add(i);
//                                            }     
                            }
//                                   
                            if (FiltrarTamanho) {
//				System.out.println("DENTRO DO Z Filtrar");
//                                   if (!Interface.FiltrarTamanhoCategorico.contains(i)) {
//                                             Interface.FiltrarTamanhoCategorico.add(i);
//                                            }     
//                                   if (!Interface.FiltrarTamanhoCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1))) {
                                if (!Vis3D.FiltrarTamanhoCategorico.containsKey(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarTamanhoCategorico.put(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1), i);
//                                             Interface.FiltrarTamanhoCategorico.add(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1));
//                                             Interface.FiltrarTamanhoCategoricoInt.add(i);
                                }
                            }


                        } else {
                            if (EixoXTradutor) {
                                if (Vis3D.FiltrarXCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarXCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
                                }
                            }
                            if (EixoYTradutor) {
                                if (Vis3D.FiltrarYCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarYCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
                                }
                            }
                            if (EixoZTradutor) {
                                if (Vis3D.FiltrarZCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarZCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
                                }
                            }

                            if (FiltrarCor) {
//                                     if (Interface.FiltrarCorCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1))) {
                                if (Vis3D.FiltrarCorCategorico.containsKey(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
//                                        Interface.FiltrarCorCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1));
                                    Vis3D.FiltrarCorCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
//                                        Interface.FiltrarCorCategoricoInt.remove(i);
                                }
                            }

                            if (FiltrarForma) {
//                                     if (Interface.FiltrarFormaCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1))) {
                                if (Vis3D.FiltrarFormaCategorico.containsKey(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarFormaCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));
//                                        Interface.FiltrarFormaCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1));
//                                    Interface.FiltrarFormaCategoricoInt.remove(i);
                                }
                            }

                            if (FiltrarTamanho) {
//                                     if (Interface.FiltrarTamanhoCategorico.contains(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-')+1))) {
                                if (Vis3D.FiltrarTamanhoCategorico.containsKey(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1))) {
                                    Vis3D.FiltrarTamanhoCategorico.remove(Interface.Categoricos[i].getText().substring(Interface.Categoricos[i].getText().indexOf('-') + 1));

                                }
                            }
                        }
                    }

                }
            }


            try{
            infoVisModule.AllAxisChecked();
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }



            Interface.menu_Voltar.setEnabled(true);
            Interface.Cancelar.setEnabled(true);
 
             
           

            FiltrarTamanho = false;
            FiltrarForma = false;
            FiltrarCor = false;
            
//            Ativa e desativa o cenário
 
           ///* 
            
            try {
               // infoVisModule.Scenario1();
                infoVisModule.Scenario2();
               // infoVisModule.Scenario3();
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //*/
            
            
            if (Interface.menu_Screenshot.isSelected()) { //Fecha o JOptionPane
               /*
            	String TempInput = textField.getText();
                File f = null;
                int ImgCopies = 0;
                while (true) {
                    f = new File(System.getProperty("user.home") + "/Documents/Lira-Screenshots/" + TempInput + ".png");
                    System.out.println("TempInput" + TempInput);

                    if (f.exists()) {
                        ImgCopies += 1;
                        TempInput = TempInput + " " + ImgCopies;
                    } else {
                        break;
                    }
                }


                try {
                    f.createNewFile();
                    if (Vis3D.IsPlot3D) {
                        Interface.plot3D.toGraphicFile(f);
                    } else {
                        Interface.plot2D.toGraphicFile(f);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }


                Interface.barra_menu.setVisible(true);
                Window w = SwingUtilities.getWindowAncestor(textField);
                if (w != null) {
                    w.setVisible(false);
                }

                Interface.barra_menu.setVisible(true);
                */
            }
            
            if (DetalhesFlag) {
             Interface.menu_legenda.doClick();
             } 
            
                                if (Interface.detalhes.length != 0 ) {
                                    for (int i = 0; i < Interface.detalhes.length; i++) { // Bloco para salvar  os botões selecionados no menu DSD
                                        if (Interface.detalhes[i].isSelected()) {
                
                //                            if (EixoXTradutor) {   //
                                            if (!Vis3D.SalvaAttDsd.contains(Interface.detalhes[i].getText().substring(Interface.detalhes[i].getText().indexOf('-') + 1))) {
                                                Vis3D.SalvaAttDsd.add(Interface.detalhes[i].getText().substring(Interface.detalhes[i].getText().indexOf('-') + 1));
                                                Vis3D.SalvaAttDsdInt.add(i);
                                            }
                //                            }
                                        } else {
                //                            if (EixoXTradutor) {
                                            if (Vis3D.SalvaAttDsd.contains(Interface.detalhes[i].getText().substring(Interface.detalhes[i].getText().indexOf('-') + 1))) {
                                                Vis3D.SalvaAttDsd.remove(Interface.detalhes[i].getText().substring(Interface.detalhes[i].getText().indexOf('-') + 1));
                                                Vis3D.SalvaAttDsdInt.remove(Vis3D.SalvaAttDsdInt.indexOf(i));
                                            }
                //                            }
                                        }
                                    }
                
                
                                
                                DetalhesFlag = false;
                              }
                            else{
//                                Interface.LimparPainel(Interface.desktopIFC);
//                                try {
//                //                    Reconhecedor.GramIFC();
//                                    Interface.BtnsIFC();
//                                    Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
//                                    Interface.painelIFC.setSelected(true);
//                                    Interface.desktopIFC.setComponentZOrder(Interface.painelIFC, Interface.desktopIFC.getComponentCount() - 2);
//                                } catch (GrammarException e) {
//                                    e.printStackTrace();
//                                } catch (EngineStateError e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                } catch (PropertyVetoException ex) {
//                                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//                                }
                                
                                }
            
                                FiltrarCategoricos = false;
                                Interface.menu_Voltar.setEnabled(true);
        
                }else if(acao.getSource() == Interface.Cancelar){
                	ConfigurarCor = false;
                    ConfigurarForma = false;
                    ConfigurarTamanho = false;
                    FiltrarCor = false;
                    FiltrarTamanho = false;
                    FiltrarForma = false;
                    EixoXTradutor = false;
                    EixoYTradutor = false;
                    EixoZTradutor = false;
                    FiltrarCategoricos = false;
                    
                	try {
						Reconhecedor.GramIFC();
					} catch (GrammarException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (EngineStateError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    Interface.Voltar();
                    Interface.BtnsIFC();
                    try {
                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelIFC());
                        Interface.painelIFC.setSelected(true);
                        Interface.desktopIFC.setComponentZOrder(Interface.painelIFC, Interface.desktopIFC.getComponentCount() - 2);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (EngineStateError ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Interface.menu_Voltar.setEnabled(true);
                    Main.Interface.setVisible(true);

                    if (Interface.menu_Screenshot.isSelected()) { //Fecha o JOptionPane
                        /*Window w = SwingUtilities.getWindowAncestor(textField);
                        if (w != null) {
                            w.setVisible(false);
                        }*/
                    }
                    Interface.barra_menu.setVisible(true);

                    Interface.menu_Detalhes.setSelected(false); // toda as vezes que apertar cancelar deixa detahles falso


		}	
		
                else if(acao.getSource() == Interface.menu_legenda){

                    if (((infoVisModule.indexCor != -1) || (infoVisModule.indexForma != -1) || (infoVisModule.indexTamanho != -1))) {
                        if ((infoVisModule.IsPlot3D) && (LegendaPrimeiraVez3D)) {
                            LegendPanelIV.MaisDeUmaVez = 0;
                            LegendPanelIV.plotPanel = Interface.plot3D;
                            LegendPanelIV.orientation = LegendPanelIV.VERTICAL;
                            Interface.plot3D.plotLegend = new LegendPanelIV(Interface.plot3D, LegendPanelIV.VERTICAL);
                            Interface.plot3D.add(Interface.plot3D.plotLegend, BorderLayout.WEST);
                            System.out.println("Inside 3D");
                            LegendaPrimeiraVez3D = false;
                        } else if ((!infoVisModule.IsPlot3D) && (LegendaPrimeiraVez2D)) {
//                            BreadCrumbs.plotPanel = Interface.plot2D;
//                            BreadCrumbs.orientation = BreadCrumbs.HORIZONTAL;
//                            Interface.plot2D.plotLegend = new BreadCrumbs(Interface.plot2D, BreadCrumbs.HORIZONTAL);
//                            Interface.plot2D.add(Interface.plot2D.plotLegend, BorderLayout.NORTH);
                            LegendPanelIV.plotPanel = Interface.plot2D;
                            LegendPanelIV.orientation = LegendPanelIV.VERTICAL;
                            Interface.plot2D.plotLegend = new LegendPanelIV(Interface.plot2D, LegendPanelIV.VERTICAL);
                            Interface.plot2D.add(Interface.plot2D.plotLegend, BorderLayout.WEST);
//                            Interface.plot2D.add(LegendPanelIV.BreadCrumbscontainer, BorderLayout.NORTH);
                            System.out.println("Inside 2D");
                            LegendaPrimeiraVez2D = false;
                        }

                    }

                    if (Interface.menu_legenda.isSelected()) {
                        if ((infoVisModule.indexCor != -1) || (infoVisModule.indexForma != -1) || (infoVisModule.indexTamanho != -1)) {
                            if (infoVisModule.IsPlot3D) {
                                Interface.plot3D.plotLegend.updateLegends();
                                Interface.plot3D.plotLegend.setVisible(true);
                            } else {
                                Interface.plot2D.plotLegend.updateLegends();
                                Interface.plot2D.plotLegend.setVisible(true);
                            }
                        }

                    } else {

                        if ((infoVisModule.indexCor != -1) || (infoVisModule.indexForma != -1) || (infoVisModule.indexTamanho != -1)) {
                            if (infoVisModule.IsPlot3D) {
                                Interface.plot3D.plotLegend.setVisible(false);
                            } else {
                                Interface.plot2D.plotLegend.setVisible(false);
                            }
                        }
                    }


                    if (Interface.menu_Detalhes.isSelected()) {



                        EscalaEI = false;
                        MoverEI = false;
                        GirarEI = false;


                        Main.Interface.setVisible(true);
                        Interface.menu_Voltar.setEnabled(false);


//                                 File fi = new File("src/BreadCrumbs/");       /// Seta o diretório onde estão as imagens do breadcrumbs para serem apagadas 
//                                 if (fi.listFiles().length > 0)
//                                 for(File file: fi.listFiles()) file.delete(); /// Apaga todos as imagens contidas na pasta BreadCrumbs
//                                 infoVisModule.BreadCrumbsLogic();
                        (new ThreadDsd()).start();


                    }
                }else if(acao.getSource() == Interface.menu_ajuda){
                	 System.out.println("Ajuda ainda não implementado");
                     try {
//                                       Interface.BtnsDetails();
//         				Reconhecedor.GramIFC();
                                         if (DetalhesFlag) {
                                         Interface.BtnsDetails();
                                         }else{
         				Interface.BtnsIFC();
                                         }
         				 Main.Interface.setContentPane(((IVOrpheus2Final.Interface)Main.Interface).PainelIFC());
                              Interface.painelIFC.setSelected(true);
                              Interface.desktopIFC.setComponentZOrder(Interface.painelIFC,Interface.desktopIFC.getComponentCount()-2);
         			} catch (GrammarException e) {
         				// TODO Auto-generated catch block
         				e.printStackTrace();
         			} catch (EngineStateError e) {
         				// TODO Auto-generated catch block
         				e.printStackTrace();
         			} catch (IOException e) {
         				// TODO Auto-generated catch block
         				e.printStackTrace();
         			} catch (PropertyVetoException ex) {
                                 Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                             }
                     try {
                         infoVisModule.DetailsOnDemand();
//                                  infoVisModule.DSD();
                     } catch (NumberFormatException ex) {
                         Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (FileNotFoundException ex) {
                         Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (IOException ex) {
                         Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (GrammarException ex) {
                         Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                     }
            
		}else if (acao.getSource() == Interface.Quadrant1) {
                    try {
                        //                        infoVisModule.Proximo(infoVisModule.VerificaSelecionadoPainelLegenda()); 
                        //                        System.out.println("proximo " + infoVisModule.marcador);
//                        Interface.LimparPainel(Interface.desktopIFC);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());

                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant1();

                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }


                } else if (acao.getSource() == Interface.Quadrant2) {
                    try {
//                        infoVisModule.Quadrant2();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant2();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (acao.getSource() == Interface.Quadrant3) {
                    try {
//                        infoVisModule.Quadrant3();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant3();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (acao.getSource() == Interface.Quadrant4) {
                    try {
//                        infoVisModule.Quadrant4();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant4();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (acao.getSource() == Interface.Quadrant5) {
                    try {
//                        infoVisModule.Quadrant5();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant5();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (acao.getSource() == Interface.Quadrant6) {
                    try {
//                        infoVisModule.Quadrant6();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant6();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (acao.getSource() == Interface.Quadrant7) {
                    try {
//                        infoVisModule.Quadrant7();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant7();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (acao.getSource() == Interface.Quadrant8) {
                    try {
//                        infoVisModule.Quadrant8();
//                        Interface.LimparPainel(Interface.desktopInteragir);
//                        Interface.BtnsDetails();
//                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
                        (new ThreadDsd()).start();
                        infoVisModule.Quadrant8();
                    } catch (IOException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GrammarException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
		
		
		}else if (acao.getSource() == Interface.menu_Detalhes) {
			ConfigurarBool = true;
                    				try {
                    					Reconhecedor.GramAtributos();
										//Reconhecedor.GramDetalhes();
									} catch (GrammarException
											| EngineStateError e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//                           Interface.plot2D.plotLegend.note(infoVisModule.PontoSelecionado);
                    DetalhesFlag = Interface.menu_Detalhes.isSelected();
                    if (DetalhesFlag) {
                        Interface.LimparPainel(Interface.desktopCarregar);
                        try {
                            Interface.Detalhes(Main.att.GetFileAttributes());
                            Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                        } catch (GrammarException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (EngineStateError ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }else if(acao.getSource() == Interface.Interagir){
                	try {
        				Reconhecedor.GramInteragir();
                        Interface.BtnsI();
                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());

        			} catch (GrammarException e) {
                        // TODO Auto-generated catch block
        				e.printStackTrace();
                    } catch (EngineStateError e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }            
		}
		else if(acao.getSource() == Interface.Escala){
			try {
				Reconhecedor.GramEscala();
			} catch (GrammarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EngineStateError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Interface.EscalaAtual.setText("Escala Atual: " + EscalaValor);
            Interface.BtnEscala();
            Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelInteragir());
            try {
				Interface.painelInteragir.setSelected(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Interface.desktopInteragir.setComponentZOrder(Interface.painelInteragir, Interface.desktopInteragir.getComponentCount() - 2);
//		} catch (GrammarException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
//        } catch (EngineStateError e) {
            // TODO Auto-generated catch block
 //           e.printStackTrace();
  //      } catch (PropertyVetoException ex) {
   //         Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
        //}
        Main.Interface.setVisible(true);	
		}
		else if(acao.getSource() == Interface.Aumentar){
			ThreadInteraction.Aumentar();
            (new ThreadInteraction()).start();
		}
                else if(acao.getSource() == Interface.Diminuir){
                	 ThreadInteraction.Diminuir();
                     (new ThreadInteraction()).start();
                }
                else if(acao.getSource() == Interface.Mover){
                	MoverGirar = true; //
                    EscalaEI = false;
                    MoverEI = true;
                    GirarEI = false;

                    Interface.BtnMover();
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelInteragir());
                    try {
                        Interface.painelInteragir.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Interface.desktopInteragir.setComponentZOrder(Interface.painelInteragir, Interface.desktopInteragir.getComponentCount() - 2);
                    Main.Interface.setVisible(true);
                        
                }else if(acao.getSource() == Interface.Frente){
                	  ThreadInteraction.Frente();
                      (new ThreadInteraction()).start();
//                      }
                      Main.Interface.setVisible(true);
                    }
		else if(acao.getSource() == Interface.Tras){
			ThreadInteraction.Tras();
            (new ThreadInteraction()).start();	
		}
		else if(acao.getSource() == Interface.Cima){
			ThreadInteraction.Cima();
            (new ThreadInteraction()).start();
		}
		else if(acao.getSource() == Interface.Baixo){
			ThreadInteraction.Baixo();
            (new ThreadInteraction()).start();
		}
		else if(acao.getSource() == Interface.Esquerda){
			ThreadInteraction.Esquerda();
            (new ThreadInteraction()).start();
		}
		else if(acao.getSource() == Interface.Direita){
			ThreadInteraction.Direita();
            (new ThreadInteraction()).start();
		}else if(acao.getSource() == Interface.Girar){
			MoverGirar = false;
            EscalaEI = false;
            MoverEI = false;
            GirarEI = true;

            try {
				Reconhecedor.GramMG();
                Interface.BtnGirar();
                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelInteragir());
                Interface.painelInteragir.setSelected(true);
                Interface.desktopInteragir.setComponentZOrder(Interface.painelInteragir, Interface.desktopInteragir.getComponentCount() - 2);
//			} catch (GrammarException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
            } catch (EngineStateError e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (PropertyVetoException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Main.Interface.setVisible(true);
		}
		else if(acao.getSource() == Interface.Filtrar){

            try {
                ConfigurarBool = false;
                FiltrarBool = true;
				Reconhecedor.GramFC();
                Interface.painelCarregar.setTitle("Filtar Eixo");
                Interface.BtnsF();
                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelIFC());
            } catch (GrammarException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (EngineStateError e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}  else if(acao.getSource() == Interface.Configurar){
			 try {
	                ConfigurarBool = true;
	                FiltrarBool = false;
	                FiltrarTamanho = false;
	                FiltrarForma = false;
	                FiltrarCor = false;
	                ConfigurarCor = true;
	                ConfigurarForma = false;
	                ConfigurarTamanho = false;
					Reconhecedor.GramFC();
	                Interface.painelCarregar.setTitle("Configurar");
	                Interface.BtnsC();
	                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());

	            } catch (GrammarException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } catch (EngineStateError e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
		}
		else if(acao.getSource() == Interface.Cor){
			if (ConfigurarBool) {
                try {
				Reconhecedor.GramAtributos();
                    ConfigurarCor = true;
                    ConfigurarForma = false;
                    ConfigurarTamanho = false;
                    FiltrarCor = false;
                    FiltrarForma = false;
                    FiltrarTamanho = false;
                    Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Cor");
                    IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelCarregar());
                } catch (GrammarException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (EngineStateError e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (FiltrarBool) {
                ConfigurarCor = false;
                ConfigurarForma = false;
                ConfigurarTamanho = false;
                FiltrarCor = true;
                FiltrarForma = false;
                FiltrarTamanho = false;
                FiltrarCategoricos = true;
                Interface.InicioBtn.setVisible(false);
                Interface.InicioFixoLbl.setVisible(false);
                Interface.InicioLbl.setVisible(false);
                Interface.FimBtn.setVisible(false);
                Interface.FimFixoLbl.setVisible(false);
                Interface.FimLbl.setVisible(false);
                EixoIndice = 0;
                            try {
								Reconhecedor.GramFiltrar();
							} catch (GrammarException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EngineStateError e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                Interface.LimparPainel(Interface.desktopCarregar);
                try {
                    Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(infoVisModule.indexCor));
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }



            }
                }
		else if(acao.getSource() == Interface.Forma){
			if (ConfigurarBool) {


                try {
				Reconhecedor.GramAtributos();
                    ConfigurarForma = true;
                    ConfigurarCor = false;
                    ConfigurarTamanho = false;
                    FiltrarCor = false;
                    FiltrarForma = false;
                    FiltrarTamanho = false;
                    Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Forma");
                    IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelCarregar());
                } catch (GrammarException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (EngineStateError e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (FiltrarBool) {
                ConfigurarCor = false;
                FiltrarCor = false;
                ConfigurarForma = false;
                ConfigurarTamanho = false;
                FiltrarTamanho = false;
                FiltrarForma = true;
                FiltrarCategoricos = true;
                Interface.InicioBtn.setVisible(false);
                Interface.InicioFixoLbl.setVisible(false);
                Interface.InicioLbl.setVisible(false);
                Interface.FimBtn.setVisible(false);
                Interface.FimFixoLbl.setVisible(false);
                Interface.FimLbl.setVisible(false);
                EixoIndice = 0;
                            try {
								Reconhecedor.GramFiltrar();
							} catch (GrammarException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EngineStateError e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                Interface.LimparPainel(Interface.desktopCarregar);
                try {
                    Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(infoVisModule.indexForma));
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }



            }
				}
		else if(acao.getSource() == Interface.Tamanho){
			 if (ConfigurarBool) {


	                try {
					Reconhecedor.GramAtributos();
	                    ConfigurarTamanho = true;
	                    ConfigurarCor = false;
	                    ConfigurarForma = false;
	                    FiltrarCor = false;
	                    FiltrarForma = false;
	                    FiltrarTamanho = false;
	                    Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Tamanho");
	                    IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
	                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelCarregar());
	                } catch (GrammarException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                } catch (EngineStateError e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	            } else if (FiltrarBool) {
	                ConfigurarCor = false;
	                FiltrarCor = false;
	                ConfigurarForma = false;
	                ConfigurarTamanho = false;
	                FiltrarTamanho = true;
	                FiltrarForma = false;
	                ConfigurarForma = false;
	                FiltrarCategoricos = true;
	                Interface.InicioBtn.setVisible(false);
	                Interface.InicioFixoLbl.setVisible(false);
	                Interface.InicioLbl.setVisible(false);
	                Interface.FimBtn.setVisible(false);
	                Interface.FimFixoLbl.setVisible(false);
	                Interface.FimLbl.setVisible(false);
	                EixoIndice = 0;
	                            try {
									Reconhecedor.GramFiltrar();
								} catch (GrammarException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (EngineStateError e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                Interface.LimparPainel(Interface.desktopCarregar);
	                try {
	                    Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(infoVisModule.indexTamanho));
	                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
	                } catch (FileNotFoundException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (IOException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (GrammarException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (EngineStateError ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                }



	            }
			}
		else if(acao.getSource() == Interface.EixoX){
			 if (FiltrarBool) {
	                try {
	                    EixoXTradutor = true;
	                    EixoZTradutor = false;
	                    EixoYTradutor = false;
	                    ConfigurarBool = false;
	                    ConfigurarCor = false;
	                    Interface.painelCarregar.setTitle("Filtrar X - " + infoVisModule.LabelX);
//	                            if(Main.att.AttTypes().get(infoVisModule.indexX).equals("FLOAT")){
	                    if (!Main.att.AttTypes().get(infoVisModule.indexX).equals("STRING") && Main.att.GetUniqueValues(infoVisModule.indexX).length >= 20) {

	                        Reconhecedor.GramFiltrar();
	                        Interface.LimparPainel(Interface.desktopCarregar);
	                        Interface.BtnFiltrarDecimal();
	                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
	                        if (!Vis3D.FiltrarXNumerico.isEmpty()) {
	                            Interface.InicioLbl.setText("" + Vis3D.FiltrarXNumerico.get(0));
	                            Interface.FimLbl.setText("" + Vis3D.FiltrarXNumerico.get(1));
	                        } else {
	                            Vis3D.FiltrarXNumerico.add(0, "" + infoVisModule.GetMinMaxValueX()[0]);
	                            Vis3D.FiltrarXNumerico.add(1, "" + infoVisModule.GetMinMaxValueX()[1]);
	                            Interface.InicioLbl.setText("" + Vis3D.FiltrarXNumerico.get(0));
	                            Interface.FimLbl.setText("" + Vis3D.FiltrarXNumerico.get(1));
//	                                Interface.InicioLbl.setText(""+Interface.GetMinMaxValueX()[0]);
//	                                Interface.FimLbl.setText(""+Interface.GetMinMaxValueX()[1]); 
	                        }

	                        Interface.InicioFixoLbl.setText("X inicia em: " + infoVisModule.GetMinMaxValueX()[0]);
	                        Interface.FimFixoLbl.setText("X termina em: " + infoVisModule.GetMinMaxValueX()[1]);
	                        EixoIndice = 0;
	                    } else {
	                    	FiltrarCategoricos = true;
	                        Interface.InicioBtn.setVisible(false);
	                        Interface.InicioFixoLbl.setVisible(false);
	                        Interface.InicioLbl.setVisible(false);
	                        Interface.FimBtn.setVisible(false);
	                        Interface.FimFixoLbl.setVisible(false);
	                        Interface.FimLbl.setVisible(false);
	                        EixoIndice = 0;
	                            Reconhecedor.GramFiltrar();
	                        Interface.LimparPainel(Interface.desktopCarregar);
	                        Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(infoVisModule.indexX));
	                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
	                    }
	                } catch (FileNotFoundException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (IOException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (GrammarException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (EngineStateError ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                }
	            }
	            if (ConfigurarBool) {

	                EixoXTradutor = true;
	                EixoZTradutor = false;
	                EixoYTradutor = false;
	                FiltrarBool = false;
	                ConfigurarCor = false;
				try {
					Reconhecedor.GramAtributos();
				} catch (GrammarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EngineStateError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                Interface.LimparPainel(Interface.desktopCarregar);
	                FiltrarCategoricos = true;
	                Interface.InicioBtn.setVisible(false);
	                Interface.InicioFixoLbl.setVisible(false);
	                Interface.InicioLbl.setVisible(false);
	                Interface.FimBtn.setVisible(false);
	                Interface.FimFixoLbl.setVisible(false);
	                Interface.FimLbl.setVisible(false);
	                Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Eixo X");
	                try {
	                    IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
	                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
	                } catch (IOException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (GrammarException ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (EngineStateError ex) {
	                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
	                }

	            }
		}
		else if(acao.getSource() == Interface.EixoY){
			if (FiltrarBool) {
                EixoXTradutor = false;
                EixoZTradutor = false;
                EixoYTradutor = true;
                ConfigurarBool = false;
                ConfigurarCor = false;
                Interface.painelCarregar.setTitle("Filtrar Y - " + infoVisModule.LabelY);
                try {

//                            if(Main.att.AttTypes().get(infoVisModule.indexY).equals("FLOAT")){
                    if (!Main.att.AttTypes().get(infoVisModule.indexY).equals("STRING") && Main.att.GetUniqueValues(infoVisModule.indexY).length >= 20) {


                            Reconhecedor.GramFiltrar();
                        Interface.LimparPainel(Interface.desktopCarregar);
                        Interface.BtnFiltrarDecimal();
                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                        if (!Vis3D.FiltrarYNumerico.isEmpty()) {
                            Interface.InicioLbl.setText("" + Vis3D.FiltrarYNumerico.get(0));
                            Interface.FimLbl.setText("" + Vis3D.FiltrarYNumerico.get(1));
                        } else {
                            Vis3D.FiltrarYNumerico.add(0, "" + infoVisModule.GetMinMaxValueY()[0]);
                            Vis3D.FiltrarYNumerico.add(1, "" + infoVisModule.GetMinMaxValueY()[1]);
                            Interface.InicioLbl.setText("" + Vis3D.FiltrarYNumerico.get(0));
                            Interface.FimLbl.setText("" + Vis3D.FiltrarYNumerico.get(1));
//                                Interface.InicioLbl.setText(""+Interface.GetMinMaxValueY()[0]);
//                                Interface.FimLbl.setText(""+Interface.GetMinMaxValueY()[1]); 
                        }
                        Interface.InicioFixoLbl.setText("Eixo Y inicia em: " + infoVisModule.GetMinMaxValueY()[0]);
                        Interface.FimFixoLbl.setText("Eixo Y termina em: " + infoVisModule.GetMinMaxValueY()[1]);
                        EixoIndice = 1;
                    } else {
                    	FiltrarCategoricos = true;
                        Interface.InicioBtn.setVisible(false);
                        Interface.InicioFixoLbl.setVisible(false);
                        Interface.InicioLbl.setVisible(false);
                        Interface.FimBtn.setVisible(false);
                        Interface.FimFixoLbl.setVisible(false);
                        Interface.FimLbl.setVisible(false);
                        EixoIndice = 1;
                        Interface.LimparPainel(Interface.desktopCarregar);
                           Reconhecedor.GramFiltrar();
                        Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(infoVisModule.indexY));
                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ConfigurarBool) {
                EixoYTradutor = true;
                EixoXTradutor = false;
                EixoZTradutor = false;
                FiltrarBool = false;
                ConfigurarCor = false;
                Interface.InicioBtn.setVisible(false);
                Interface.InicioFixoLbl.setVisible(false);
                Interface.InicioLbl.setVisible(false);
                Interface.FimBtn.setVisible(false);
                Interface.FimFixoLbl.setVisible(false);
                Interface.FimLbl.setVisible(false);

                try {
                      Reconhecedor.GramAtributos();
                    Interface.LimparPainel(Interface.desktopCarregar);
                    Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Eixo Y");
                    IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }



            }
                }
		else if(acao.getSource() == Interface.EixoZ){

            if (FiltrarBool) {
                EixoXTradutor = false;
                EixoYTradutor = false;
                EixoZTradutor = true;
                ConfigurarBool = false;
                ConfigurarCor = false;
                EixoIndice = 2;
                Interface.painelCarregar.setTitle("Filtrar Z - " + infoVisModule.LabelZ);
                try {
//                            if(Main.att.AttTypes().get(infoVisModule.indexZ).equals("FLOAT")){
                    if (!Main.att.AttTypes().get(infoVisModule.indexZ).equals("STRING") && Main.att.GetUniqueValues(infoVisModule.indexZ).length >= 20) {
                            Reconhecedor.GramFiltrar();

                        Interface.LimparPainel(Interface.desktopCarregar);
                        Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Eixo Z");
                        Interface.BtnFiltrarDecimal();
                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                        if (!Vis3D.FiltrarZNumerico.isEmpty()) {
                            Interface.InicioLbl.setText("" + Vis3D.FiltrarZNumerico.get(0));
                            Interface.FimLbl.setText("" + Vis3D.FiltrarZNumerico.get(1));
                        } else {
                            Vis3D.FiltrarZNumerico.add(0, "" + infoVisModule.GetMinMaxValueZ()[0]);
                            Vis3D.FiltrarZNumerico.add(1, "" + infoVisModule.GetMinMaxValueZ()[1]);
                            Interface.InicioLbl.setText("" + Vis3D.FiltrarZNumerico.get(0));
                            Interface.FimLbl.setText("" + Vis3D.FiltrarZNumerico.get(1));
//                                Interface.InicioLbl.setText(""+Interface.GetMinMaxValueZ()[0]);
//                                Interface.FimLbl.setText(""+Interface.GetMinMaxValueZ()[1]); 
                        }
                        Interface.InicioFixoLbl.setText("Z inicia em: " + infoVisModule.GetMinMaxValueZ()[0]);
                        Interface.FimFixoLbl.setText("Z termina em: " + infoVisModule.GetMinMaxValueZ()[1]);
                        EixoIndice = 2;
                    } else {
                    	FiltrarCategoricos = true;
                        Interface.InicioBtn.setVisible(false);
                        Interface.InicioFixoLbl.setVisible(false);
                        Interface.InicioLbl.setVisible(false);
                        Interface.FimBtn.setVisible(false);
                        Interface.FimFixoLbl.setVisible(false);
                        Interface.FimLbl.setVisible(false);
                        EixoIndice = 2;
                     Reconhecedor.GramFiltrar();
                        Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(infoVisModule.indexZ));
                        Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ConfigurarBool) {
                EixoZTradutor = true;
                EixoXTradutor = false;
                EixoYTradutor = false;
                FiltrarBool = false;
                ConfigurarCor = false;
                Interface.InicioBtn.setVisible(false);
                Interface.InicioFixoLbl.setVisible(false);
                Interface.InicioLbl.setVisible(false);
                Interface.FimBtn.setVisible(false);
                Interface.FimFixoLbl.setVisible(false);
                Interface.FimLbl.setVisible(false);

                try {
                            Reconhecedor.GramAtributos();
                    Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle() + " Eixo Z");
                    IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
		}
		
		else if (acao.getSource() == Interface.BreadCrumb1) {
            try {
                infoVisModule.BreadCrumbs1();
                Interface.LimparPainel(Interface.desktopInteragir);
                Interface.BtnsDetails();
                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
                (new ThreadDsd()).start();
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.BreadCrumb2) {
            try {
                infoVisModule.BreadCrumbs2();
                Interface.LimparPainel(Interface.desktopInteragir);
                Interface.BtnsDetails();
                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
                (new ThreadDsd()).start();
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.BreadCrumb3) {
            try {
                infoVisModule.BreadCrumbs3();
                Interface.LimparPainel(Interface.desktopInteragir);
                Interface.BtnsDetails();
                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
                (new ThreadDsd()).start();
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.BreadCrumb4) {
            try {
                infoVisModule.BreadCrumbs4();
                Interface.LimparPainel(Interface.desktopInteragir);
                Interface.BtnsDetails();
                Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
                (new ThreadDsd()).start();
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.EstadoInicial) {
            try {
                infoVisModule.EstadoInicial();
//                Interface.LimparPainel(Interface.desktopInteragir);
//                Interface.BtnsDetails();
//                Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
//                (new ThreadDsd()).start();
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.Parar) {
            try {
                infoVisModule.Parar();
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.zero) {
            try {
                infoVisModule.DesenhandoCoordenadas(0);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.one) {
            try {
                infoVisModule.DesenhandoCoordenadas(1);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.two) {
            try {
                infoVisModule.DesenhandoCoordenadas(2);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.three) {
            try {
                infoVisModule.DesenhandoCoordenadas(3);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.four) {
            try {
                infoVisModule.DesenhandoCoordenadas(4);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.five) {
            try {
                infoVisModule.DesenhandoCoordenadas(5);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.six) {
            try {
                infoVisModule.DesenhandoCoordenadas(6);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.seven) {
            try {
                infoVisModule.DesenhandoCoordenadas(7);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.eight) {
            try {
                infoVisModule.DesenhandoCoordenadas(8);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.getSource() == Interface.nine) {
            try {
                infoVisModule.DesenhandoCoordenadas(9);
            } catch (IOException ex) {
                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

 @Override
        public void menuSelected(MenuEvent acao) {

           if(acao.getSource() == Interface.menu_Carregar){
        	   FiltrarBool = false;
        	   ConfigurarBool = false;
               Interface.BtnsBase();
               Interface.painelCarregar.setTitle("Carregar Base");
               try {
                   Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
                   Interface.painelCarregar.setSelected(true);
                   Interface.desktopCarregar.setComponentZOrder(Interface.painelCarregar, Interface.desktopCarregar.getComponentCount() - 2);
               } catch (GrammarException ex) {
                   Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
               } catch (EngineStateError ex) {
                   Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                   Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
               } catch (PropertyVetoException ex) {
                   Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
               }
               Main.Interface.setVisible(true);
               Interface.menu_Voltar.setEnabled(false);
           }
            
            if(acao.getSource() == Interface.menu_Voltar){
                try {
					Reconhecedor.GramIFC();
				} catch (GrammarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EngineStateError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Interface.menu_Detalhes.setSelected(false);
                Interface.Voltar();
                Interface.BtnsIFC();
                try {
                    Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Inter).PainelIFC());
                    Interface.painelIFC.setSelected(true);
                    Interface.desktopIFC.setComponentZOrder(Interface.painelIFC, Interface.desktopIFC.getComponentCount() - 2);
                } catch (GrammarException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EngineStateError ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Interface.setVisible(true);
                Robot robot;

                try {
                    robot = new Robot();
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                } catch (AWTException ex) {
                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
            if (acao.getSource() == Interface.menu_Screenshot) {
               /*
            	File fi = new File(System.getProperty("user.home") + "/Documents/Lira-Screenshots");
                Object[] options = {Interface.Confirmar, Interface.Cancelar};
                JPanel panel = new JPanel();
                panel.add(new JLabel("Com qual nome você deseja salvar esta imagem? \n"));
                textField = new JTextField(20);
                panel.add(textField);
                if (!fi.exists()) {
                    fi.mkdir();
                }
                Interface.barra_menu.setVisible(false);
                if (Vis3D.IsPlot3D) {
                    textField.setText("X- " + Interface.plot3D.getAxis(0).getLegend() + " Y- " + Interface.plot3D.getAxis(1).getLegend() + " Z- " + Interface.plot3D.getAxis(2).getLegend());
                    int resultPScreen = JOptionPane.showOptionDialog(null, panel, "Captura de Tela",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, null);
//                           input = (String)JOptionPane.showInputDialog(null, "Com qual nome você deseja salvar esta imagem",
//                           "Captura de Tela", JOptionPane.QUESTION_MESSAGE,null,null,"X- "+Interface.plot3D.getAxis(0).getLegend()+" Y- "+Interface.plot3D.getAxis(1).getLegend()+" Z- "+Interface.plot3D.getAxis(2).getLegend());
                } else {
                    textField.setText("X- " + Interface.plot3D.getAxis(0).getLegend() + " Y- " + Interface.plot3D.getAxis(1).getLegend());
                    int resultPScreen = JOptionPane.showOptionDialog(null, panel, "Captura de Tela",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, null);
//                             input = (String)JOptionPane.showInputDialog(null, "Com qual nome você deseja salvar esta imagem",
//                           "Captura de Tela", JOptionPane.OK_CANCEL_OPTION, null,null,"X- "+Interface.plot3D.getAxis(0).getLegend()+" Y- "+Interface.plot3D.getAxis(1).getLegend());
                }

*/
            }

            if (acao.getSource() == Interface.menu_Salvar) {
            }

            
            
            }

        @Override
        public void menuDeselected(MenuEvent acao) {
            
        }

        @Override
        public void menuCanceled(MenuEvent e) {
        }       
}


//Adicionar o Inter e suas dependencias no IVOrpheus
//Desmarcar os Reconhecedores Gram
//Falta Funcionar o Filtrar , configurar.
//Mover, Girar e Escala