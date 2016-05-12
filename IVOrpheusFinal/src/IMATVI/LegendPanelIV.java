package IMATVI;

import IMATVI.Vis3D;
import IMATVI.Interface;
import IMATVI.LegendPanelIV;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.speech.recognition.GrammarException;

import javax.swing.*;

import org.math.plot.*;
import org.math.plot.canvas.*;
import org.math.plot.components.LegendPanel;
import org.math.plot.plots.*;

public class LegendPanelIV extends LegendPanel implements ComponentListener {

    private static final long serialVersionUID = 1L;
    public static PlotCanvas plotCanvas;
    public static PlotPanel plotPanel;
    public static LinkedList<Legend> legends;
    public static int INVISIBLE = -1;
    public static int VERTICAL = 0;
    public static int HORIZONTAL = 1;
    public static int orientation;
    public static int MaisDeUmaVez = 0;
    private int maxHeight;
    private int maxWidth;
    public static JPanel container;
    public static JPanel BreadCrumbscontainer;
    private int inset = 5;
    public static String[] TamanhosDesenhos = new String[3];
    public static String[] FormasDesenhos = new String[4];
    public Font font;
    public int aux = 0;
    public int TotaldePontos = 0;

    public LegendPanelIV(PlotPanel _plotPanel, int _orientation) {
        super(plotPanel, 0);
        updateLegends();
//        add(BreadCrumbscontainer);
        add(container);
        


    }

    public void updateLegends() {
        if (MaisDeUmaVez == 0) { //Resolve o problema do painel não estava atualizando
        TamanhosDesenhos[0] = "◽"; //Inicializa os desenhos de tamanho e forma
        TamanhosDesenhos[1] = "◻";
        TamanhosDesenhos[2] = "▢";
        FormasDesenhos[0] = "◯";
        FormasDesenhos[1] = "X";
        FormasDesenhos[2] = "△";
        FormasDesenhos[3] = "▢";
        font = new Font("Times", Font.BOLD, 16); // Ajusta a fonte dos nomes Cores/Formas/Tamanhos presentes no painel legenda
        plotCanvas = plotPanel.plotCanvas;   // Insere o canvas do Plot na legenda para que ambos plotem no mesmo canvas
        plotCanvas.attachLegend(this);
        container = new JPanel();
        container.setBackground(plotCanvas.getBackground());
        container.setLayout(new GridLayout(1, 1, inset, inset));
        container.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), null)); // Desenha as bordas do Painel legenda
        setBackground(plotCanvas.getBackground()); //Seta o BackGround para a cor de fundo da aplicação
        addComponentListener(this);
        setLayout(new GridBagLayout());  
        }
        MaisDeUmaVez++; // Variável utilizada para criar e inicializar apenas uma vez o container e os desenhos
        TotaldePontos = Vis3D.cores.size() -1; // o -1 tira o nulo da soma total
         
        if (orientation != INVISIBLE) {
            container.removeAll(); //Remove todos os componentes do container (Painel da Legenda)
            JLabel name = new JLabel("Cores (" + Vis3D.GetColorName() + ")");
            int size = 1; 
            name.setSize(new Dimension(size, size));
            name.setLayout(new GridBagLayout());
            name.setFont(font);
            LegendPanelIV.container.add(name);
            maxHeight = 1;
            maxWidth = 1;
            maxWidth = (int) Math.max(maxWidth, name.getPreferredSize().getWidth());
            legends = new LinkedList<Legend>();           //Apresentar apenas informações simples
            int tempC = -1;
            int pC = 0;
            for (Plot plot : plotCanvas.getPlots()) {
                if ((plot.getVisible())&&(tempC != Vis3D.MapaDeCores.get(pC))){
                    double QtdCorPorcento = Collections.frequency(Vis3D.cores, Vis3D.coresUnicas.get(Vis3D.MapaDeCores.get(pC)));
//                    double QtdCorPorcento = Collections.frequency(Vis3D.cores, Vis3D.coresUnicas.get(pC));
                    double porcentagem = ((double) (QtdCorPorcento * 100)) / (double) (TotaldePontos); 
                    plot.setName(Vis3D.coresUnicas.get(Vis3D.MapaDeCores.get(pC)) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
//                    plot.setName(Vis3D.coresUnicas.get(pC) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                    Legend l = new Legend(plot);
                    legends.add(l);
                    maxWidth = (int) Math.max(maxWidth, l.getPreferredSize().getWidth());
                    maxHeight = (int) Math.max(maxHeight, l.getPreferredSize().getHeight());
                    container.add(l);
                    tempC = Vis3D.MapaDeCores.get(pC);
                   pC++; 
                }else //Este 
                     pC++; //E este usado para resolver o problema da legenda não está mostrando o escopo inteiro
                
//                                  
            }
            
            if ((Vis3D.indexForma != -1) && (Vis3D.indexTamanho != -1)) {
//                try {
//                    s = Main.att.GetUniqueValues(Vis3D.indexForma);
//                    s2 = Main.att.GetUniqueValues(Vis3D.indexTamanho);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(LegendPanelIV.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(LegendPanelIV.class.getName()).log(Level.SEVERE, null, ex);
//                }

//                JLabel forma = new JLabel("Formas (" + Vis3D.GetShapeName() + ")");
//                forma.setSize(new Dimension(size, size));
//                forma.setLayout(new GridBagLayout());
//                forma.setFont(font);
//                LegendPanelIV.container.add(forma);
//                maxWidth = (int) Math.max(maxWidth, forma.getPreferredSize().getWidth());
//
//                for (int i = 0; i <= s.length - 1; i++) {
//                    double QtdFormaPorcento = Collections.frequency(Vis3D.formas, s[i]);
//                    double porcentagem = ((double) (QtdFormaPorcento * 100)) / (double) (Vis3D.TotalCorPorcento - 1);
//                    JLabel forma1 = new JLabel(FormasDesenhos[i] + " - " + s[i] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
//                    forma1.setSize(new Dimension(size, size));
//                    forma1.setLayout(new GridBagLayout());
//                    LegendPanelIV.container.add(forma1);
//                    maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());
//                }
//                Inicio da forma
                
                JLabel forma = new JLabel("Formas (" + Vis3D.GetShapeName() + ")");
                forma.setSize(new Dimension(size, size));
                forma.setLayout(new GridBagLayout());
                forma.setFont(font);
                container.add(forma);
                maxWidth = (int) Math.max(maxWidth, forma.getPreferredSize().getWidth());
                int tempF = -1;
                int pF = 0;
                ArrayList<String> listaVerificaForma = new ArrayList<String>();
                if (!Vis3D.FiltrarFormaCategorico.isEmpty() && !Vis3D.FiltrarFormaCategorico.equals(null) && Vis3D.FiltrarFormaCategorico != null) {
                    for (int p = 0; p <= plotCanvas.getPlots().size() - 1; p++) {
                        if (!Vis3D.FiltrarFormaCategorico.containsValue(Vis3D.MapaDeFormas.get(p))) {

                            if (plotCanvas.getPlot(p).getVisible()&&(tempF != Vis3D.MapaDeFormas.get(pF))){
                                    double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(Vis3D.MapaDeFormas.get(p)));
//                                    double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(p));
                                    double porcentagem = ((QtdFormaPorcento * 100) / (TotaldePontos));
                                    JLabel forma1 = new JLabel(FormasDesenhos[Vis3D.MapaDeFormas.get(p)] + " - " + Vis3D.formasUnicas.get(Vis3D.MapaDeFormas.get(p)) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
//                                    JLabel forma1 = new JLabel(FormasDesenhos[Vis3D.MapaDeFormas.get(p)] + " - " + Vis3D.formasUnicas.get(p) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                                    forma1.setSize(new Dimension(size, size));
                                    forma1.setLayout(new GridBagLayout());
                                    if (!listaVerificaForma.contains(forma1.getText())) {  //Corrige os multiplos plots
                                    listaVerificaForma.add(forma1.getText());
                                    container.add(forma1);
                                    tempF = Vis3D.MapaDeFormas.get(pF);
                                    maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());
                                    }
                                    
//                                    LegendPanelIV.container.add(forma1);
//                                    tempF = Vis3D.MapaDeTamanhos.get(pF);
//                                    maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());

                               
                            }
                        }
                        pF++;
                    }
                } else {

                    for (int i = 0; i <= Vis3D.formasUnicas.size() - 1; i++) {
                        double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(i));
                        double porcentagem = ((QtdFormaPorcento * 100) / (TotaldePontos));
                        JLabel forma1 = new JLabel(FormasDesenhos[i] + " - " + Vis3D.formasUnicas.get(i) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                        forma1.setSize(new Dimension(size, size));
                        forma1.setLayout(new GridBagLayout());
                        container.add(forma1);
                        maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());
                    }
                }
                
                //Fim da forma e inicio do tamanho
                
                JLabel tamanho = new JLabel("Tamanhos (" + Vis3D.GetSizeName() + ")");
                tamanho.setSize(new Dimension(size, size));
                tamanho.setLayout(new GridBagLayout());
                tamanho.setFont(font);
                LegendPanelIV.container.add(tamanho);
                maxWidth = (int) Math.max(maxWidth, tamanho.getPreferredSize().getWidth());
                int tempT = -1;
                int pT = 0;
                ArrayList<String> listaVerificaTamanho = new ArrayList<String>();
                if (!Vis3D.FiltrarTamanhoCategorico.isEmpty() && !Vis3D.FiltrarTamanhoCategorico.equals(null) && Vis3D.FiltrarTamanhoCategorico != null) {
                    for (int p = 0; p <= plotCanvas.getPlots().size() - 1; p++) {
                        if (!Vis3D.FiltrarTamanhoCategorico.containsValue(Vis3D.MapaDeTamanhos.get(p))) {

                            if (plotCanvas.getPlot(p).getVisible()&&(tempT != Vis3D.MapaDeTamanhos.get(pT))) {
                                
                                    double QtdTamanhoPorcento = Collections.frequency(Vis3D.tamanhos, Vis3D.tamanhosUnicos.get(Vis3D.MapaDeTamanhos.get(p)));
                                    double porcentagem = ((QtdTamanhoPorcento * 100) / (TotaldePontos));
                                    JLabel tamanho1 = new JLabel(TamanhosDesenhos[Vis3D.MapaDeTamanhos.get(p)] + " - " + Vis3D.tamanhosUnicos.get(Vis3D.MapaDeTamanhos.get(p)) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                                    tamanho1.setSize(new Dimension(size, size));
                                    tamanho1.setLayout(new GridBagLayout());
                                     if (!listaVerificaTamanho.contains(tamanho1.getText())) {  //Corrige os multiplos plots
                                    listaVerificaTamanho.add(tamanho1.getText());
                                    LegendPanelIV.container.add(tamanho1);
                                    tempT = Vis3D.MapaDeFormas.get(pT);
                                    maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());
                                    }
//                                    LegendPanelIV.container.add(tamanho1);
//                                    tempT = Vis3D.MapaDeTamanhos.get(pT);
//                                    maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());

                                
                            }
                        }
                        pT++;
                    }
                } else {

                    for (int i = 0; i <= Vis3D.tamanhosUnicos.size() - 1; i++) {
                        double QtdTamanhoPorcento = Collections.frequency(Vis3D.tamanhos, Vis3D.tamanhosUnicos.get(i));
                        double porcentagem = ((QtdTamanhoPorcento * 100) / (TotaldePontos));
                        JLabel tamanho1 = new JLabel(TamanhosDesenhos[i] + " - " + Vis3D.tamanhosUnicos.get(i) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                        tamanho1.setSize(new Dimension(size, size));
                        tamanho1.setLayout(new GridBagLayout());
                        LegendPanelIV.container.add(tamanho1);
                        maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());
                    }
                }

//                JLabel tamanho = new JLabel("Tamanhos (" + Vis3D.GetSizeName() + ")");
//                tamanho.setSize(new Dimension(size, size));
//                tamanho.setLayout(new GridBagLayout());
//                tamanho.setFont(font);
//                LegendPanelIV.container.add(tamanho);
//                maxWidth = (int) Math.max(maxWidth, tamanho.getPreferredSize().getWidth());
//
//                for (int i = 0; i <= s2.length - 1; i++) {
//                    double QtdTamanhoPorcento = Collections.frequency(Vis3D.tamanhos, s2[i]);
//                    double porcentagem = (QtdTamanhoPorcento * 100) / (double) (Vis3D.TotalCorPorcento - 1);
//                    JLabel tamanho1 = new JLabel(TamanhosDesenhos[i] + " - " + s2[i] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
//                    tamanho1.setSize(new Dimension(size, size));
//                    tamanho1.setLayout(new GridBagLayout());
//                    LegendPanelIV.container.add(tamanho1);
//                    maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());
//                }

            } else if ((Vis3D.indexForma != -1) && (Vis3D.indexTamanho == -1)) {
                JLabel forma = new JLabel("Formas (" + Vis3D.GetShapeName() + ")");
                forma.setSize(new Dimension(size, size));
                forma.setLayout(new GridBagLayout());
                forma.setFont(font);
                container.add(forma);
                maxWidth = (int) Math.max(maxWidth, forma.getPreferredSize().getWidth());
                int temp = -1;
                ArrayList<String> listaVerificaForma = new ArrayList<String>();
                if (!Vis3D.FiltrarFormaCategorico.isEmpty() && !Vis3D.FiltrarFormaCategorico.equals(null) && Vis3D.FiltrarFormaCategorico != null) {
                    for (int p = 0; p <= plotCanvas.getPlots().size() - 1; p++) {
                        if (!Vis3D.FiltrarFormaCategorico.containsValue(Vis3D.MapaDeFormas.get(p))) {

                            if (plotCanvas.getPlot(p).getVisible()) {
                                if (temp != Vis3D.MapaDeFormas.get(p)) { // Tira pontos iguais da legenda
                                    System.out.println(" ELDEST 1 ");
//                                      double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(Vis3D.MapaDeFormas.get(p)));
                                    double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(p));
                                    double porcentagem = ((QtdFormaPorcento * 100) / (TotaldePontos));
//                                    JLabel forma1 = new JLabel(FormasDesenhos[Vis3D.MapaDeFormas.get(p)] + " - " + Vis3D.formasUnicas.get(p) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                                    JLabel forma1 = new JLabel(FormasDesenhos[Vis3D.MapaDeFormas.get(p)] + " - " + Vis3D.formasUnicas.get(Vis3D.MapaDeFormas.get(p)) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                                    forma1.setSize(new Dimension(size, size));
                                    forma1.setLayout(new GridBagLayout());
                                    if (!listaVerificaForma.contains(forma1.getText())) {  //Corrige os multiplos plots
                                    listaVerificaForma.add(forma1.getText());
                                    container.add(forma1);
                                    temp = Vis3D.MapaDeFormas.get(p);
                                    maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());
                                    }
//                                    LegendPanelIV.container.add(forma1);
//                                    temp = Vis3D.MapaDeFormas.get(p);
//                                    maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());

                                }
                            }
                        }
                    }
                } else {
                    System.out.println(" ELDEST 2 ");
                    for (int i = 0; i <= Vis3D.formasUnicas.size() - 1; i++) {
                        double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(i));
//                        double QtdFormaPorcento = Collections.frequency(Vis3D.formas, Vis3D.formasUnicas.get(Vis3D.MapaDeFormas.get(i)));
                        double porcentagem = ((QtdFormaPorcento * 100) / (TotaldePontos));
                        JLabel forma1 = new JLabel(FormasDesenhos[i] + " - " + Vis3D.formasUnicas.get(i) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
//                        JLabel forma1 = new JLabel(FormasDesenhos[Vis3D.MapaDeFormas.get(i)] + " - " + Vis3D.formasUnicas.get(Vis3D.MapaDeFormas.get(i)) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                        forma1.setSize(new Dimension(size, size));
                        forma1.setLayout(new GridBagLayout());
                        container.add(forma1);
                        maxWidth = (int) Math.max(maxWidth, forma1.getPreferredSize().getWidth());
                    }
                }
            } else if ((Vis3D.indexForma == -1) && (Vis3D.indexTamanho != -1)) {
                JLabel tamanho = new JLabel("Tamanhos (" + Vis3D.GetSizeName() + ")");
                tamanho.setSize(new Dimension(size, size));
                tamanho.setLayout(new GridBagLayout());
                tamanho.setFont(font);
                LegendPanelIV.container.add(tamanho);
                maxWidth = (int) Math.max(maxWidth, tamanho.getPreferredSize().getWidth());
                int tempT = -1;
                ArrayList<String> listaVerificaTamanho = new ArrayList<String>();
                if (!Vis3D.FiltrarTamanhoCategorico.isEmpty() && !Vis3D.FiltrarTamanhoCategorico.equals(null) && Vis3D.FiltrarTamanhoCategorico != null) {
                    for (int p = 0; p <= plotCanvas.getPlots().size() - 1; p++) {
                        if (!Vis3D.FiltrarTamanhoCategorico.containsValue(Vis3D.MapaDeTamanhos.get(p))) {

                            if (plotCanvas.getPlot(p).getVisible()) {
                                if (tempT != Vis3D.MapaDeTamanhos.get(p)) {
                                    double QtdTamanhoPorcento = Collections.frequency(Vis3D.tamanhos, Vis3D.tamanhosUnicos.get(Vis3D.MapaDeTamanhos.get(p)));
                                    double porcentagem = ((QtdTamanhoPorcento * 100) / (TotaldePontos));
                                    JLabel tamanho1 = new JLabel(TamanhosDesenhos[Vis3D.MapaDeTamanhos.get(p)] + " - " + Vis3D.tamanhosUnicos.get(Vis3D.MapaDeTamanhos.get(p)) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                                    tamanho1.setSize(new Dimension(size, size));
                                    tamanho1.setLayout(new GridBagLayout());
                                     if (!listaVerificaTamanho.contains(tamanho1.getText())) {  //Corrige os multiplos plots
                                    listaVerificaTamanho.add(tamanho1.getText());
                                    LegendPanelIV.container.add(tamanho1);
                                    tempT = Vis3D.MapaDeFormas.get(p);
                                    maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());
                                    }
//                                    LegendPanelIV.container.add(tamanho1);
//                                    tempT = Vis3D.MapaDeTamanhos.get(p);
//                                    maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());

                                }
                            }
                        }
                    }
                } else {

                    for (int i = 0; i <= Vis3D.tamanhosUnicos.size() - 1; i++) {
                        double QtdTamanhoPorcento = Collections.frequency(Vis3D.tamanhos, Vis3D.tamanhosUnicos.get(i));
                        double porcentagem = ((QtdTamanhoPorcento * 100) / (TotaldePontos));
                        JLabel tamanho1 = new JLabel(TamanhosDesenhos[i] + " - " + Vis3D.tamanhosUnicos.get(i) + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
                        tamanho1.setSize(new Dimension(size, size));
                        tamanho1.setLayout(new GridBagLayout());
                        LegendPanelIV.container.add(tamanho1);
                        maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());
                    }
                } 
                
//                try {
//                    s = Main.att.GetUniqueValues(Vis3D.indexTamanho);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(LegendPanelIV.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(LegendPanelIV.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                JLabel tamanho = new JLabel("Tamanhos (" + Vis3D.GetSizeName() + ")");
//                tamanho.setSize(new Dimension(size, size));
//                tamanho.setLayout(new GridBagLayout());
//                tamanho.setFont(font);
//                LegendPanelIV.container.add(tamanho);
//                maxWidth = (int) Math.max(maxWidth, tamanho.getPreferredSize().getWidth());
//
//                for (int i = 0; i <= s.length - 1; i++) {
//                    double QtdTamanhoPorcento = Collections.frequency(Vis3D.tamanhos, s[i]);
//                    double porcentagem = (QtdTamanhoPorcento * 100) / (double) (Vis3D.TotalCorPorcento - 1);
//                    JLabel tamanho1 = new JLabel(TamanhosDesenhos[i] + " - " + s[i] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%");
//                    tamanho1.setSize(new Dimension(size, size));
//                    tamanho1.setLayout(new GridBagLayout());
//                    LegendPanelIV.container.add(tamanho1);
//                    maxWidth = (int) Math.max(maxWidth, tamanho1.getPreferredSize().getWidth());
//                }
            }
            
            
//              if (Vis3D.xl1.size() > 9) {
//                    Image img = null;
//                    Image newimg2 = null;
//                try {
//                    img = ImageIO.read(getClass().getResource("Teste.png")); //testar
//                    newimg2 = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
//   
//                } catch (IOException ex) {
//                    Logger.getLogger(BreadCrumbs.class.getName()).log(Level.SEVERE, null, ex);
//                }
////                    buttonBrowse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/browse.png")));
//                    Interface.BreadCrumb1.setBorderPainted(false);
//                    Interface.BreadCrumb1.setFocusPainted(false);
//                    Interface.BreadCrumb1.setContentAreaFilled(false);
//                    Interface.BreadCrumb1.setIcon(new ImageIcon(newimg2));
//                    Interface.BreadCrumb1.setSize(40,40);
////                      maxWidth = (int) Math.max(maxWidth, Interface.BreadCrumb1.getPreferredSize().getWidth());
////                      maxHeight = (int) Math.max(maxHeight, Interface.BreadCrumb1.getPreferredSize().getHeight());
//                      container.add(Interface.BreadCrumb1);
//                }

        }
//       Bread(); // Funcao que add bread Crumbs na legenda
       
       updateSize();
       plotCanvas.linkedLegendPanel.repaint();
       plotCanvas.repaint();
        }
    
    public void Bread(){
    JLabel BreadCrumbsLbl = new JLabel("BreadCrumbs ");    
    BreadCrumbscontainer = new JPanel();
    BreadCrumbscontainer.setBackground(plotCanvas.getBackground());
    BreadCrumbscontainer.setLayout(new GridLayout(1, 1, inset, inset));
    BreadCrumbscontainer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), null)); // Desenha as bordas do Painel legenda
    
    container.add(BreadCrumbsLbl);
            if (Vis3D.xl1.size() > 9) {
                    File fi = new File("/BreadCrumbs/");
                   
                    
                    Image img = null;
//                try {
//                    img = ImageIO.read(getClass().getResource("/BreadCrumbs/0.png")); //testar
//                    
//                } catch (IOException ex) {
//                    Logger.getLogger(BreadCrumbs.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                    Interface.BreadCrumb1.setIcon(new ImageIcon(img));
                    Interface.BreadCrumb1.setSize(50,33);
//                      maxWidth = (int) Math.max(maxWidth, Interface.BreadCrumb1.getPreferredSize().getWidth());
//                      maxHeight = (int) Math.max(maxHeight, Interface.BreadCrumb1.getPreferredSize().getHeight());
//                      BreadCrumbscontainer.add(Interface.BreadCrumb1);
                      container.add(Interface.BreadCrumb1);
                       if (Vis3D.IQuad == 1) {
                           Interface.BreadCrumb2.setSize(50,33);
                           container.add(Interface.BreadCrumb2);
                        }else if (Vis3D.IQuad == 2) {
                           Interface.BreadCrumb2.setSize(50,33);
                           container.add(Interface.BreadCrumb2);
                           Interface.BreadCrumb3.setSize(50,33);
                           container.add(Interface.BreadCrumb3);
                        }else if (Vis3D.IQuad == 3) {
                           Interface.BreadCrumb2.setSize(50,33);
                           container.add(Interface.BreadCrumb2);
                           Interface.BreadCrumb3.setSize(50,33);
                           container.add(Interface.BreadCrumb3);
                           Interface.BreadCrumb4.setSize(50,33);
                           container.add(Interface.BreadCrumb4);
                        }else if (Vis3D.IQuad >= 4) {
                           System.out.println("Logica acima");
                        }
            }
    
//    int nw = 1;
//            if (maxWidth < plotCanvas.getWidth()) {
//                nw = plotCanvas.getWidth() / (maxWidth + inset);
//            }
//            int nh = 1 + legends.size() / nw;
//
//            ((GridLayout) (BreadCrumbscontainer.getLayout())).setRows(nh);
//            ((GridLayout) (BreadCrumbscontainer.getLayout())).setColumns(1 + legends.size() / nh);
//
//            BreadCrumbscontainer.setPreferredSize(new Dimension((maxWidth + inset) * (1 + legends.size() / nh), (maxHeight + inset) * nh));
    BreadCrumbscontainer.updateUI();
    }

    private void updateSize() {
        if (orientation == VERTICAL) {
            int nh = 1;
            if (maxHeight < plotCanvas.getHeight()) {
                nh = plotCanvas.getHeight() / (maxHeight + inset);
            }
//                         int nw = 1 + legends.size() / nh;
            int nw = 1 + container.getComponentCount() / nh;
//            System.out.println("Ctn " + container.getComponentCount() + " " + "leg " + legends.size());
            ((GridLayout) (container.getLayout())).setColumns(nw);
//                         ((GridLayout) (container.getLayout())).setRows(1 + legends.size() / nw);
            ((GridLayout) (container.getLayout())).setRows(1 + container.getComponentCount() / nw);

//                         container.setPreferredSize(new Dimension((maxWidth + inset) * nw, (maxHeight + inset) * (1 + legends.size() / nw)));
            container.setPreferredSize(new Dimension((maxWidth + inset) * nw, (maxHeight + inset) * (1 + container.getComponentCount() / nw)));

        } else if (orientation == HORIZONTAL) {
            int nw = 1;
            if (maxWidth < plotCanvas.getWidth()) {
                nw = plotCanvas.getWidth() / (maxWidth + inset);
            }
            int nh = 1 + legends.size() / nw;

            ((GridLayout) (container.getLayout())).setRows(nh);
            ((GridLayout) (container.getLayout())).setColumns(1 + legends.size() / nh);

            container.setPreferredSize(new Dimension((maxWidth + inset) * (1 + legends.size() / nh), (maxHeight + inset) * nh));

        }
        container.updateUI();
        
        
//        if (Interface.menu_Detalhes.isSelected()) {
//                        (new ThreadDsd()).start();
//
//                    } 
        

    }

    public void note(int i) {
        if (orientation != INVISIBLE) {
            legends.get(i).setBackground(PlotCanvas.NOTE_COLOR);
            legends.get(i).name.setForeground(plotPanel.getBackground());
        }
    }

    public void nonote(int i) {
//        if (orientation != INVISIBLE) {
//            legends.get(i).setBackground(plotPanel.getBackground());
//            legends.get(i).name.setForeground(PlotCanvas.NOTE_COLOR);
//        }
    }

    public void componentResized(ComponentEvent e) {
        if (orientation != INVISIBLE) {
            updateSize();
        }
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public class Legend extends JPanel {

        private static final long serialVersionUID = 1L;
        JPanel color;
        JLabel name;
        Plot plot;

        public Legend(Plot p) {
            plot = p;

            setLayout(new BorderLayout(2, 2));

            color = new JPanel();
            name = new JLabel();

            setBackground(Color.WHITE);

            update();

            add(color, BorderLayout.WEST);
            add(name, BorderLayout.CENTER);

            name.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                        if (plotCanvas.allowEdit && e.getClickCount() > 1) {
                            editText();
                        }
                    }
                    if (plotCanvas.allowNote && e.getClickCount() <= 1) {
                        note_nonote();
                    }
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });

            color.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                        if (plotCanvas.allowEdit && e.getClickCount() > 1) {
                            editColor();
                        }
                    }
                    if (plotCanvas.allowNote && e.getClickCount() <= 1) {
                        note_nonote();
                    }
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });
        }

        public void editText() {
            String name1 = JOptionPane.showInputDialog(plotCanvas, "Choose name", plot.getName());
            if (name1 != null) {
                plot.setName(name1);
                update();
                updateLegends();
            }
        }

        public void editColor() {
            Color c = JColorChooser.showDialog(plotCanvas, "Choose plot color", plot.getColor());
            if (c != null) {
                plot.setColor(c);
                update();
                plotCanvas.repaint();
            }
        }

        public void update() {
            int size = name.getFont().getSize();
            color.setSize(new Dimension(size, size));
            color.setPreferredSize(new Dimension(size, size));
            color.setBackground(plot.getColor());
            name.setText(plot.getName());
            repaint();
        }

        public void note_nonote() {
            plot.noted = !plot.noted;
            plotCanvas.repaint();
        }
    }
}
