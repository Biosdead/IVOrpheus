package IMATVI;

import IMATVI.Main;
import IMATVI.ThreadInteraction;
import IMATVI.BtnListener;
import IMATVI.Vis3D;
import IMATVI.Interface;
import IMATVI.ThreadDsd;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;

import org.math.plot.Plot3DPanel;
import org.math.plot.canvas.Plot3DCanvas;
import org.math.plot.canvas.PlotCanvas;
import org.math.plot.plots.ScatterPlot;
import org.math.plot.render.AWTDrawer2D;
import org.math.plot.render.AWTDrawer3D;
import org.math.plot.utils.Array;

/**
 *
 * @author lennonsalesfurtado
 */
public class Vis3D extends Plot3DCanvas { //InfoVisModule future

    //Formas Geometricas para os pontos
    public static int DOT_PATTERN = 1, CROSS_PATTERN = 2;
    public static boolean[][] TRIANGLE_PATTERN;
    public static boolean[][] SQUARE_PATTERN;
    public static boolean[][] PSQUARE_PATTERN;
    public static boolean[][] MSQUARE_PATTERN; //Para preencher o quadrado basta acrescentar um _ no primeiro cara.
    public static boolean[][] GSQUARE_PATTERN;
    public static boolean[][] PTRIANGLE_PATTERN;
    public static boolean[][] MTRIANGLE_PATTERN;
    public static boolean[][] GTRIANGLE_PATTERN;
    //Lista Matrix
    public static ArrayList<String> FiltrarXNumerico = new ArrayList<String>();
    public static ArrayList<String> FiltrarXCategorico = new ArrayList<String>();
    public static ArrayList<String> FiltrarYNumerico = new ArrayList<String>();
    public static ArrayList<String> FiltrarYCategorico = new ArrayList<String>();
    public static ArrayList<String> FiltrarZNumerico = new ArrayList<String>();
    public static ArrayList<String> FiltrarZCategorico = new ArrayList<String>();
    public static ArrayList<String> SalvaAttDsd = new ArrayList<String>();
    public static ArrayList<Integer> SalvaAttDsdInt = new ArrayList<Integer>();
    public static ArrayList<String> MatrizDSD = new ArrayList<String>(); // Matriz que guarda as listas que serão apresentadas no detalhes sobre demanda(DSD)
    //Booleans Eixos
    public static boolean eixoXSetado = false, eixoYSetado = false, eixoZSetado = false, firstTime = true, FiltroNumerico = false, eixoCorSetado = false, eixoFormaSetado = false, eixoTamanhoSetado = false, IsPlot3D = false;
    // Marcador proxAnt        
    public static int marcador = 0, PontoSelecionado = 0;
    //Nome dos Eixos
    public static String LabelX, LabelY, LabelZ, LabelCor, LabelForma, LabelTamanho;
    //Modelo de Cores
    public static Color[] ModeloCores = new Color[8];
    //Nome das cores
    public static String[] NomeDasCores = new String[8];
    //Nome das cores
    public static String[] NomeDasFormas = new String[4];
    //Nome das cores
    public static String[] NomeDosTamanhos = new String[3];
    //Mapa de Cores
    public static HashMap<Integer, Integer> MapaDeCores = new HashMap<Integer, Integer>();
    public static HashMap<String, Integer> FiltrarCorCategorico = new HashMap<String, Integer>(); // Organizar isso aqui
    public static HashMap<String, Integer> FiltrarFormaCategorico = new HashMap<String, Integer>();
    public static HashMap<String, Integer> FiltrarTamanhoCategorico = new HashMap<String, Integer>();
    //Mapa de Formas
    public static HashMap<Integer, Integer> MapaDeFormas = new HashMap<Integer, Integer>();
    //Mapa de Tamanhos
    public static HashMap<Integer, Integer> MapaDeTamanhos = new HashMap<Integer, Integer>();
    public static String[] UniqueShapes; //Combustivel para testar cor
    public static String[] uniqueSizes; //Combustivel para testar cor
    public static String[] UniqueColors; //Combustivel para testar cor
    public static List<String> cores;
    public static List<String> formas;
    public static List<String> tamanhos;
    public static List<String> xl1;
    public static List<String> xl1Coord = new ArrayList<>();
    public static List<String> yl1;
    public static List<String> yl1Coord = new ArrayList<>();
    public static List<String> zl1;
    public static List<String> zl1Coord = new ArrayList<>();
    public static List<String> coresUnicas;
    public static List<String> formasUnicas;
    public static List<String> tamanhosUnicos;
    public static int TotalCorPorcento;
    public static int TotalFormaPorcento;
    public static int TotalTamanhoPorcento;
    public static int[] QtdCorPorcento;
    public static int[] QtdFormaPorcento;
    public static int[] QtdTamanhoPorcento;
    public static int[] QtdPontosPorcento;
    public static int QtdPontosVisuais = 100; // Serve para identificar quantos pontos estão sendo visualmente despostos na visualização
    //Indice dos Eixos
    public static int indexX = -1, indexY = -1, indexZ = -1, indexCor = -1, indexForma = -1, indexTamanho = -1;
    public static boolean Detalhes = false;
    private Graphics2D comp2D;
    private PlotCanvas canvas;
    //Limites dos Quadrantes
    public static double QLX = 0, QHX = 1, QLY = 0, QHY = 1, QLZ = 0, QHZ = 1; //Q - Quadrant L- Low H- High 
    //Vetores do Detalhes Sobre demanda (DSD)
    public static List<Double> FiltrarXDSD = new ArrayList<Double>(); // Pos 0 minimo, pos 1 maximo
    public static List<Double> FiltrarYDSD = new ArrayList<Double>(); // Pos 0 minimo, pos 1 maximo
    public static List<Double> FiltrarZDSD = new ArrayList<Double>(); // Pos 0 minimo, pos 1 maximo
    public static List<Double> BCsListValues = new ArrayList<Double>(); // Armazena os valores dos breadcrumbs em uma lista.
//        public static double[] FiltrarXDSD = new double[2];
//        public static double[] FiltrarYDSD = new double[2];
//        public static double[] FiltrarZDSD = new double[2];
    public static String LegendSize, LegendShape;
    public static int IQuad; // Indice do quadrante usado para mostrar quantos quadrantes o usuario ja clicou.

    public Vis3D() {
        DOT_PATTERN = 1;
        CROSS_PATTERN = 2;
        TRIANGLE_PATTERN = stringToPattern("_", "___#___", "__#_#__", "__#_#__", "_#___#_", "_#___#_", "#######");
        SQUARE_PATTERN = stringToPattern("_", "######", "#____#", "#____#", "#____#", "#____#", "######");
        PSQUARE_PATTERN = stringToPattern("_", "######", "#____#", "#____#", "#____#", "#____#", "######");
        MSQUARE_PATTERN = stringToPattern("_", "############", "##________##", "##________##", "##________##", "##________##", "##________##", "##________##", "##________##", "##________##", "############"); //Para preencher o quadrado basta acrescentar um _ no primeiro cara.
        GSQUARE_PATTERN = stringToPattern("_", "##################", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "##################"); //Para preencher o quadrado basta acrescentar um _ no primeiro cara.
        PTRIANGLE_PATTERN = stringToPattern("_", "___#___", "__#_#__", "__#_#__", "_#___#_", "_#___#_", "#######");
        MTRIANGLE_PATTERN = stringToPattern("_", "______##______", "____##__##____", "____##__#____", "__##______##__", "__##______##__", "#############");
        GTRIANGLE_PATTERN = stringToPattern("_", "_________###_________", "______###___###______", "______###___#______", "___###_________###___", "___###_________###___", "###################");

        //Inicializando modelo de cores
        ModeloCores[0] = Color.BLUE;
        ModeloCores[1] = Color.RED;
//        ModeloCores[2] = Color.GREEN;
        ModeloCores[2] = Color.decode("#0FB809"); //Dark Green
        ModeloCores[3] = Color.ORANGE;
//        ModeloCores[4] = Color.CYAN;
        ModeloCores[4] = Color.decode("#7D13E8"); // Purple
        ModeloCores[5] = Color.MAGENTA;
//        ModeloCores[6] = Color.YELLOW;
        ModeloCores[6] = Color.decode("#B86F09"); //
        ModeloCores[7] = Color.PINK;

        //Nome das Cores para o Filtro
//        NomeDasCores[0] = "Azul";
//        NomeDasCores[1] = "Vermelho";
//        NomeDasCores[2] = "Verde";
//        NomeDasCores[3] = "laranja";
//        NomeDasCores[4] = "Ciano";
//        NomeDasCores[5] = "Magenta";
//        NomeDasCores[6] = "Amarelo";
//        NomeDasCores[7] = "Rosa";
//      Método alternativo para representar as Cores
        NomeDasCores[0] = "█";
        NomeDasCores[1] = "█";
        NomeDasCores[2] = "█";
        NomeDasCores[3] = "█";
        NomeDasCores[4] = "█";
        NomeDasCores[5] = "█";
        NomeDasCores[6] = "█";
        NomeDasCores[7] = "█";



        //Nome Dos tamanhos para os Filtros
//        NomeDosTamanhos[0] = "Pequeno";
//        NomeDosTamanhos[1] = "Médio";
//        NomeDosTamanhos[2] = "Grande";
        //Método alternativo para representar os Tamanhos no filtro de Tamanhos.
        NomeDosTamanhos[0] = "◽"; 
        NomeDosTamanhos[1] = "◻";
        NomeDosTamanhos[2] = "▢";
       // NomeDosTamanhos[0] = "P"; 
       // NomeDosTamanhos[1] = "M";
       // NomeDosTamanhos[2] = "G";
        
        //Nome Das formas para os Filtros
//        NomeDasFormas[0] = "Ponto";
//        NomeDasFormas[1] = "Cruz/Xis";
//        NomeDasFormas[2] = "Triangulo";
//        NomeDasFormas[3] = "Quadrado";
        //Método alternativo para representar As Formas no filtro de formas.
        NomeDasFormas[0] = "◯";
        NomeDasFormas[1] = "X";
        NomeDasFormas[2] = "△";
        NomeDasFormas[3] = "▢";
        
    }

    public Vis3D(PlotCanvas _canvas) {
        DOT_PATTERN = 1;
        CROSS_PATTERN = 2;
        TRIANGLE_PATTERN = stringToPattern("_", "___#___", "__#_#__", "__#_#__", "_#___#_", "_#___#_", "#######");
        SQUARE_PATTERN = stringToPattern("_", "######", "#____#", "#____#", "#____#", "#____#", "######");
        PSQUARE_PATTERN = stringToPattern("_", "######", "#____#", "#____#", "#____#", "#____#", "######");
        MSQUARE_PATTERN = stringToPattern("_", "############", "##________##", "##________##", "##________##", "##________##", "##________##", "##________##", "##________##", "##________##", "############"); //Para preencher o quadrado basta acrescentar um _ no primeiro cara.
        GSQUARE_PATTERN = stringToPattern("_", "##################", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "###____________###", "##################"); //Para preencher o quadrado basta acrescentar um _ no primeiro cara.
        PTRIANGLE_PATTERN = stringToPattern("_", "___#___", "__#_#__", "__#_#__", "_#___#_", "_#___#_", "#######");
        MTRIANGLE_PATTERN = stringToPattern("_", "______##______", "____##__##____", "____##__#____", "__##______##__", "__##______##__", "#############");
        GTRIANGLE_PATTERN = stringToPattern("_", "_________###_________", "______###___###______", "______###___#______", "___###_________###___", "___###_________###___", "###################");

        //Inicializando modelo de cores
        ModeloCores[0] = Color.BLUE;
        ModeloCores[1] = Color.RED;
        ModeloCores[2] = Color.GREEN;
        ModeloCores[3] = Color.ORANGE;
        ModeloCores[4] = Color.CYAN;
        ModeloCores[5] = Color.MAGENTA;
        ModeloCores[6] = Color.YELLOW;
        ModeloCores[7] = Color.PINK;

        //Nome das Cores para o Filtro
        NomeDasCores[0] = "Azul";
        NomeDasCores[1] = "Vermelho";
        NomeDasCores[2] = "Verde";
        NomeDasCores[3] = "laranja";
        NomeDasCores[4] = "Ciano";
        NomeDasCores[5] = "Magenta";
        NomeDasCores[6] = "Amarelo";
        NomeDasCores[7] = "Rosa";

        //Nome Dos tamanhos para os Filtros
        NomeDosTamanhos[0] = "Pequeno";
        NomeDosTamanhos[1] = "Médio";
        NomeDosTamanhos[2] = "Grande";

        //Nome Das formas para os Filtros
        NomeDasFormas[0] = "Ponto";
        NomeDasFormas[1] = "Cruz/Xis";
        NomeDasFormas[2] = "Triangulo";
        NomeDasFormas[3] = "Quadrado";

        ActionMode = ZOOM;
        draw = new AWTDrawer2D(_canvas);
        initGraphics((Graphics2D) _canvas.getGraphics());

//                super();
    }

    public void initDrawer() {
        draw = new AWTDrawer2D(this);
    }

    public void AbstractDrawer(PlotCanvas _canvas) {
        canvas = _canvas;
    }

    public void initGraphics(Graphics2D _comp2D) {
        comp2D = _comp2D;
    }

    public static void plot3DVis(int x, int y, int z, String eixox, String eixoy, String eixoz) throws FileNotFoundException, IOException {
        Interface.Filtrar.setEnabled(true);
        Interface.Interagir.setEnabled(true);
        Interface.Cor.setEnabled(true);
        Interface.Forma.setEnabled(true);
        Interface.Tamanho.setEnabled(true);

        if (firstTime) {
            Interface.plot2D.removeAllPlots();
            Interface.plot3D.removeAllPlots();

        }

        String[] x1 = Main.matrix[x];
        String[] y1 = Main.matrix[y];
        String[] z1 = Main.matrix[z];
        String[] cor;
        String[] forma; //Combustivel para testar cor 5
        String[] tamanho; //Combustivel para testar cor 5
//    String[] formasUnicas; //Combustivel para testar cor
//    String[] tamanhosUnicos; //Combustivel para testar cor
//    String[] coresUnicas;
//    List<String> cores;
//    List<String> formas;
//    List<String> tamanhos;

        if (indexCor == -1) {
            cor = Main.matrix[5];; //Combustivel para testar cor 5
            coresUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(5)));
            cores = new ArrayList<String>(Arrays.asList(cor));
        } else {
            cor = Main.matrix[indexCor]; //Combustivel para testar cor 5
            coresUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(indexCor)));
            cores = new ArrayList<String>(Arrays.asList(cor));
        }
        if (indexForma == -1) {
            forma = Main.matrix[1];; //Combustivel para testar cor 5
            formasUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(1)));
            formas = new ArrayList<String>(Arrays.asList(forma));
        } else {
            forma = Main.matrix[indexForma]; //Combustivel para testar cor 5
            formasUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(indexForma)));
            formas = new ArrayList<String>(Arrays.asList(forma));
        }
        if (indexTamanho == -1) {
            tamanho = Main.matrix[2]; //Combustivel para testar cor 5
            tamanhosUnicos = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(2)));
            tamanhos = new ArrayList<String>(Arrays.asList(tamanho));
        } else {
            tamanho = Main.matrix[indexTamanho]; //Combustivel para testar cor 5
            tamanhosUnicos = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(indexTamanho)));
            tamanhos = new ArrayList<String>(Arrays.asList(tamanho));
        }


//    List<String> xl1 = new ArrayList<String>(Arrays.asList(x1));
//    List<String> yl1 = new ArrayList<String>(Arrays.asList(y1));
//    List<String> zl1 = new ArrayList<String>(Arrays.asList(z1));
        xl1 = new ArrayList<String>(Arrays.asList(x1));
        yl1 = new ArrayList<String>(Arrays.asList(y1));
        zl1 = new ArrayList<String>(Arrays.asList(z1));
        List<String> tempList;


        
        
        if (!SalvaAttDsdInt.isEmpty()) {
            for (int i = 0; i < SalvaAttDsdInt.size(); i++) {
                System.out.println("SalvaAttDsdInt " + Arrays.toString(Main.matrix[SalvaAttDsdInt.get(i)]));
                    MatrizDSD.addAll(Arrays.asList(Main.matrix[SalvaAttDsdInt.get(i)]));
            }
         }
        
        MapaDeCores.clear();
        MapaDeTamanhos.clear();
        MapaDeFormas.clear();


        System.out.println("Novo filtro Começo 5");

        if (FiltrarXCategorico.isEmpty()) {


            if (!Main.att.AttTypes().get(indexX).equals("FLOAT")) {
                if ((BtnListener.EixoIndice == 0) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int j = 0; j <= Interface.Categoricos.length - 1; j++) {
                        if (!Interface.Categoricos[j].isSelected()) {
                            while (true) {

                                if (xl1.contains(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1))) {

                                    yl1.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    cores.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    formas.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    tamanhos.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    zl1.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    xl1.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    
                                } else {
                                    break;
                                }
                            }

                        }
                    }
                }

            }
        } else {

            if (!Main.att.AttTypes().get(indexX).equals("FLOAT")) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    System.out.println("Dentro da Flag 2");
                    for (int j = 0; j < FiltrarXCategorico.size(); j++) {
                        while (true) {

                            if (xl1.contains(FiltrarXCategorico.get(j))) {

                                yl1.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                zl1.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                cores.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                formas.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                tamanhos.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                xl1.remove(xl1.indexOf(FiltrarXCategorico.get(j)));

                            } else {
                                break;
                            }
                        }
                    }
                }

            }

        }


        if (FiltrarYCategorico.isEmpty()) {

            if (!Main.att.AttTypes().get(indexY).equals("FLOAT")) {
                if ((BtnListener.EixoIndice == 1) && (BtnListener.FiltrarBool) && (Interface.Categoricos != null)) { // Falta verificar se e numerico ou categorico
                    for (int j = 0; j <= Interface.Categoricos.length - 1; j++) {
                        if (!Interface.Categoricos[j].isSelected()) {

                            while (true) {
                                if (yl1.contains(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1))) {
                                    xl1.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    zl1.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    cores.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    formas.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    tamanhos.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    yl1.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));

                                } else {
                                    break;
                                }
                            }
                            System.out.println("Flag 3");
                        }

                    }
                }
            }

        } else {
            if (!Main.att.AttTypes().get(indexY).equals("FLOAT")) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int j = 0; j < FiltrarYCategorico.size(); j++) {
                        while (true) {

                            if (yl1.contains(FiltrarYCategorico.get(j))) {

                                xl1.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                zl1.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                cores.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                formas.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                tamanhos.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                yl1.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                            } else {
                                break;
                            }
                        }
                        System.out.println("Flag 4");


                    }

                }

            }


        }

        if (FiltrarZCategorico.isEmpty()) {

            if (!Main.att.AttTypes().get(indexZ).equals("FLOAT")) {
                if ((BtnListener.EixoIndice == 2) && (BtnListener.FiltrarBool)) {
                    for (int j = 0; j <= Interface.Categoricos.length - 1; j++) {
                        if (!Interface.Categoricos[j].isSelected()) {
                            while (true) {

                                if (zl1.contains(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1))) {
                                    xl1.remove(zl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    yl1.remove(zl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    cores.remove(zl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    formas.remove(zl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    tamanhos.remove(zl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    zl1.remove(zl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));

                                } else {
                                    break;
                                }

                                System.out.println("Flag 5");
                            }
                        }
                    }

                }

            }
        } else {

            if (!Main.att.AttTypes().get(indexZ).equals("FLOAT")) {
                if ((BtnListener.FiltrarBool)) {
                    for (int j = 0; j < FiltrarZCategorico.size(); j++) {
                        while (true) {

                            if (zl1.contains(FiltrarZCategorico.get(j))) {
                                xl1.remove(zl1.indexOf(FiltrarZCategorico.get(j)));
                                yl1.remove(zl1.indexOf(FiltrarZCategorico.get(j)));
                                cores.remove(zl1.indexOf(FiltrarZCategorico.get(j)));
                                formas.remove(zl1.indexOf(FiltrarZCategorico.get(j)));
                                tamanhos.remove(zl1.indexOf(FiltrarZCategorico.get(j)));
                                zl1.remove(zl1.indexOf(FiltrarZCategorico.get(j)));

                                System.out.println("Array " + z1.length);

                                System.out.println("Lista " + zl1.size());
                            } else {
                                break;
                            }
                        }

                    }

                }

            }

        }

        if (FiltrarXNumerico.isEmpty()) {



            if (!Main.att.AttTypes().get(indexX).equals("STRING") && Main.att.GetUniqueValues(indexX).length >= 20) {
                FiltrarXNumerico.add(0, "" + GetMinMaxValueX()[0]);
                FiltrarXNumerico.add(1, "" + GetMinMaxValueX()[1]);
                if ((BtnListener.EixoIndice == 0) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= xl1.size() - 1; i++) {

                        while (true) {

                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(xl1.get(i).toString()) <= Double.parseDouble(FiltrarXNumerico.get(0))) || (Double.parseDouble(xl1.get(i).toString()) >= Double.parseDouble(FiltrarXNumerico.get(1)))) { //Bug solucionado, porém não otimizado(Todas as vezes que allplot é chamado )

                                    zl1.remove(xl1.indexOf(xl1.get(i)));
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }
                    }
                    System.out.println("Flag 7");

                }

            }

        } else {
            if (!Main.att.AttTypes().get(indexX).equals("STRING") && Main.att.GetUniqueValues(indexX).length >= 20) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= xl1.size() - 1; i++) {
                        while (true) {

                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(xl1.get(i).toString()) <= Double.parseDouble(FiltrarXNumerico.get(0))) || (Double.parseDouble(xl1.get(i).toString()) >= Double.parseDouble(FiltrarXNumerico.get(1)))) {

                                    zl1.remove(xl1.indexOf(xl1.get(i)));
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }



                    }

                }

            }

        }


        if (FiltrarYNumerico.isEmpty()) {
            if (!Main.att.AttTypes().get(indexY).equals("STRING") && Main.att.GetUniqueValues(indexY).length >= 20) {
                if ((BtnListener.EixoIndice == 1) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= yl1.size() - 1; i++) {


                        while (true) {

                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(yl1.get(i).toString()) <= Double.parseDouble(FiltrarYNumerico.get(0))) || (Double.parseDouble(yl1.get(i).toString()) >= Double.parseDouble(FiltrarYNumerico.get(1)))) {
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    zl1.remove(yl1.indexOf(yl1.get(i)));
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                }
                System.out.println("Flag 9");
            }
        } else {
            if (!Main.att.AttTypes().get(indexY).equals("STRING") && Main.att.GetUniqueValues(indexY).length >= 20) {
                System.out.println("Dentro da Flag 10");

                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= yl1.size() - 1; i++) {
                        if ((yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                            System.out.println("Lista " + Double.parseDouble(yl1.get(i)));
                            System.out.println("Filtrar Gravado " + Double.parseDouble(FiltrarYNumerico.get(0)));
                        }
                        while (true) {

                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(yl1.get(i).toString()) <= Double.parseDouble(FiltrarYNumerico.get(0))) || (Double.parseDouble(yl1.get(i).toString()) >= Double.parseDouble(FiltrarYNumerico.get(1)))) {
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    zl1.remove(yl1.indexOf(yl1.get(i)));
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }

                }
            }


        }

        if (FiltrarZNumerico.isEmpty()) {

            if (!Main.att.AttTypes().get(indexZ).equals("STRING") && Main.att.GetUniqueValues(indexZ).length >= 20) {
                if ((BtnListener.EixoIndice == 2) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= zl1.size() - 1; i++) {
                        while (true) {

                            if ((zl1.contains(zl1.get(i))) && (zl1.get(i) != null) && (!zl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(zl1.get(i).toString()) <= Double.parseDouble(FiltrarZNumerico.get(0))) || (Double.parseDouble(zl1.get(i).toString()) >= Double.parseDouble(FiltrarZNumerico.get(1)))) {
                                    xl1.remove(zl1.indexOf(zl1.get(i)));
                                    yl1.remove(zl1.indexOf(zl1.get(i)));
                                    cores.remove(zl1.indexOf(zl1.get(i)));
                                    formas.remove(zl1.indexOf(zl1.get(i)));
                                    tamanhos.remove(zl1.indexOf(zl1.get(i)));
                                    zl1.remove(zl1.indexOf(zl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }

                }
            }
        } else {
            if (!Main.att.AttTypes().get(indexZ).equals("STRING") && Main.att.GetUniqueValues(indexZ).length >= 20) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= zl1.size() - 1; i++) {

                        while (true) {

                            if ((zl1.contains(zl1.get(i))) && (zl1.get(i) != null) && (!zl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(zl1.get(i).toString()) <= Double.parseDouble(FiltrarZNumerico.get(0))) || (Double.parseDouble(zl1.get(i).toString()) >= Double.parseDouble(FiltrarZNumerico.get(1)))) {
                                    xl1.remove(zl1.indexOf(zl1.get(i)));
                                    yl1.remove(zl1.indexOf(zl1.get(i)));
                                    cores.remove(zl1.indexOf(zl1.get(i)));
                                    formas.remove(zl1.indexOf(zl1.get(i)));
                                    tamanhos.remove(zl1.indexOf(zl1.get(i)));
                                    zl1.remove(zl1.indexOf(zl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }


                    }

                }

            }
        }


//     if(Detalhes){
//         if (Main.att.AttTypes().get(x).equals("STRING")) {
//         for (int d = 0; d < FiltrarXDSD.size()-1;) {
//            for(int i = 0; i <= xl1.size()-1;i++){
//                String str = xl1.get(i).toString();
//                while(true){
//                 if((Interface.plot3D.getAxis(0).getStringMap().containsValue(xl1.get(i)))&&(xl1.get(i) != null)&&(!xl1.get(i).equals("null"))){
//                      if((Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) < FiltrarXDSD.get(d)) || (Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) > FiltrarXDSD.get(d+1))){
//                                 cores.remove(xl1.indexOf(xl1.get(i)));
//                                 formas.remove(xl1.indexOf(xl1.get(i)));
//                                 tamanhos.remove(xl1.indexOf(xl1.get(i)));
//                                 zl1.remove(xl1.indexOf(xl1.get(i)));
//                                 yl1.remove(xl1.indexOf(xl1.get(i)));
//                                 xl1.remove(xl1.indexOf(xl1.get(i)));
//                                 }else {
//                                    break;
//                                
//                          } 
//                      }else{
//                     System.out.println("AAAAAAAAA "+ Interface.plot3D.getAxis(0).getStringMap().containsValue(xl1.get(i)));
//                     System.out.println("BBBBBBBBB "+ xl1.contains(xl1.get(i)));
//                                    break;
//                                }         
//                      }
//
//            }
//            d += 2;
//         }
//         }else{
//        for (int d = 0; d < FiltrarXDSD.size()-1;) {
//         for(int i = 0; i <= xl1.size()-1;i++){
//                while(true){
//                 if((xl1.contains(xl1.get(i)))&&(xl1.get(i) != null)&&(!xl1.get(i).equals("null"))){
//                      if ((Double.parseDouble(xl1.get(i)) < FiltrarXDSD.get(d)) || (Double.parseDouble(xl1.get(i)) > FiltrarXDSD.get(d+1))){
//                                 cores.remove(xl1.indexOf(xl1.get(i)));
//                                 formas.remove(xl1.indexOf(xl1.get(i)));
//                                 tamanhos.remove(xl1.indexOf(xl1.get(i)));
//                                 zl1.remove(xl1.indexOf(xl1.get(i)));
//                                 yl1.remove(xl1.indexOf(xl1.get(i)));
//                                 xl1.remove(xl1.indexOf(xl1.get(i)));
//                                 }else {
//                                    break;
//                                
//                          } 
//                      }else{
//                                    break;
//                                } 
//                      }
//
//            }
//         d += 2;
//         }
//         }
//         
//         if (Main.att.AttTypes().get(y).equals("STRING")) {
//             
//            for (int d = 0; d < FiltrarYDSD.size()-1;) {
//               for(int i = 0; i <= yl1.size()-1;i++){
//                while(true){
//                 if((Interface.plot3D.getAxis(1).getStringMap().containsValue(yl1.get(i)))&&(yl1.get(i) != null)&&(!yl1.get(i).equals("null"))){
//                      if((Interface.plot3D.getAxis(1).getStringMap().get(yl1.get(i)) < FiltrarYDSD.get(d)) || (Interface.plot3D.getAxis(1).getStringMap().get(yl1.get(i)) > FiltrarYDSD.get(d+1))){
//                                 cores.remove(yl1.indexOf(yl1.get(i)));
//                                 formas.remove(yl1.indexOf(yl1.get(i)));
//                                 tamanhos.remove(yl1.indexOf(yl1.get(i)));
//                                 zl1.remove(yl1.indexOf(yl1.get(i)));
//                                 xl1.remove(yl1.indexOf(yl1.get(i)));
//                                 yl1.remove(yl1.indexOf(yl1.get(i)));
//                                 }else {
//                                    break;
//                                
//                          } 
//                      }else{
//                                    break;
//                                } 
//                      }
//
//            }
//               d+=2;
//            }
//         }else{
//        for (int d = 0; d < FiltrarYDSD.size()-1;) {
//            for(int i = 0; i <= yl1.size()-1;i++){
//                while(true){
//                 if((yl1.contains(yl1.get(i)))&&(yl1.get(i) != null)&&(!yl1.get(i).equals("null"))){
//                      if ((Double.parseDouble(yl1.get(i)) < FiltrarYDSD.get(d)) || (Double.parseDouble(yl1.get(i)) > FiltrarYDSD.get(d+1))){
//                                 cores.remove(yl1.indexOf(yl1.get(i)));
//                                 formas.remove(yl1.indexOf(yl1.get(i)));
//                                 tamanhos.remove(yl1.indexOf(yl1.get(i)));
//                                 zl1.remove(yl1.indexOf(yl1.get(i)));
//                                 xl1.remove(yl1.indexOf(yl1.get(i)));
//                                 yl1.remove(yl1.indexOf(yl1.get(i)));
//                                 }else {
//                                    break;
//                                
//                          } 
//                      }else{
//                                    break;
//                                } 
//                      }
//
//            }
//            d+=2;
//         }
//         }
//         
//         if (Main.att.AttTypes().get(z).equals("STRING")) {
//             
//            for (int d = 0; d < FiltrarZDSD.size()-1;) {
//               for(int i = 0; i <= zl1.size()-1;i++){
//                while(true){
//                 if((Interface.plot3D.getAxis(2).getStringMap().containsValue(zl1.get(i)))&&(zl1.get(i) != null)&&(!zl1.get(i).equals("null"))){
//                      if((Interface.plot3D.getAxis(2).getStringMap().get(zl1.get(i)) < FiltrarZDSD.get(d)) || (Interface.plot3D.getAxis(2).getStringMap().get(zl1.get(i)) > FiltrarZDSD.get(d+1))){
//                                 cores.remove(zl1.indexOf(zl1.get(i)));
//                                 formas.remove(zl1.indexOf(zl1.get(i)));
//                                 tamanhos.remove(zl1.indexOf(zl1.get(i)));
//                                 xl1.remove(zl1.indexOf(zl1.get(i)));
//                                 yl1.remove(zl1.indexOf(zl1.get(i)));
//                                 zl1.remove(zl1.indexOf(zl1.get(i)));
//                                 }else {
//                                    break;
//                                
//                          } 
//                      }else{
//                                    break;
//                                } 
//                      }
//
//            }
//               d+=2;
//            }
//         }else{
//        for (int d = 0; d < FiltrarZDSD.size()-1;) {
//            for(int i = 0; i <= zl1.size()-1;i++){
//                while(true){
//                 if((zl1.contains(zl1.get(i)))&&(zl1.get(i) != null)&&(!zl1.get(i).equals("null"))){
//                      if ((Double.parseDouble(zl1.get(i)) < FiltrarZDSD.get(d)) || (Double.parseDouble(zl1.get(i)) > FiltrarZDSD.get(d+1))){
//                                 cores.remove(zl1.indexOf(zl1.get(i)));
//                                 formas.remove(zl1.indexOf(zl1.get(i)));
//                                 tamanhos.remove(zl1.indexOf(zl1.get(i)));
//                                 xl1.remove(zl1.indexOf(zl1.get(i)));
//                                 yl1.remove(zl1.indexOf(zl1.get(i)));
//                                 zl1.remove(zl1.indexOf(zl1.get(i)));
//                                 }else {
//                                    break;
//                                
//                          } 
//                      }else{
//                                    break;
//                                } 
//                      }
//
//            }
//            d+=2;
//         }
//         }
//         
//          System.out.println("Flag 9X");
//    
//   }









//        if (Detalhes) {
//            if (Main.att.AttTypes().get(x).equals("STRING")) {
////         for (int d = 0; d < FiltrarXDSD.size()-1;) {
////                System.out.println("abdlfhkbkln;A'OFB;LLKNas;odinocxn c c;/ " + xl1.get(0));
////                System.out.println("abdlfhkbkln;A'OFB;LLKNas;odinocxn c c;/ " + Interface.plot3D.getAxis(0).getStringMap().get("" + xl1.get(0)));
////                System.out.println("abdlfhkbkln;A'OFB;LLKNas;odinocxn c c;/ " + Interface.plot3D.getAxis(1).getStringMap().get(xl1.get(0)));
////                System.out.println("abdlfhkbkln;A'OFB;LLKNas;odinocxn c c;/ " + Interface.plot3D.getAxis(2).getStringMap().get(xl1.get(0)));
//                for (int d = 0; d < 2; d++) {
//                    for (int i = 0; i <= xl1.size() - 1; i++) {
//                        String str = xl1.get(i).toString();
//                        while (true) {
////                 if((Interface.plot2D.getAxis(0).getStringMap().containsValue(xl1.get(i)))&&(xl1.get(i) != null)&&(!xl1.get(i).equals("null"))){
//                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
////                 if((xl1.contains(xl1.get(i)))&&(xl1.get(i) != null)&&(!xl1.get(i).equals("null"))){
//                                if ((Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) == null) || (Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) < FiltrarXDSD.get(d)) || (Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) > FiltrarXDSD.get(d + 1))) {
//
//                                    //                      if((Main.att.GetUniqueValues(xl1) <= Q1LX) || (Interface.plot2D.getAxis(0).getStringMap().get("\u200f"+xl1.get(i)) >= Q1HX)){
//                                    System.out.println("RRRRXXXXX " + xl1.indexOf(xl1.get(i)) + " " + xl1.get(i) + " " + yl1.get(i));
//                                    cores.remove(xl1.indexOf(xl1.get(i)));
//                                    formas.remove(xl1.indexOf(xl1.get(i)));
//                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
//                                    yl1.remove(xl1.indexOf(xl1.get(i)));
//                                    zl1.remove(xl1.indexOf(xl1.get(i)));
//                                    xl1.remove(xl1.indexOf(xl1.get(i)));
//                                } else {
//                                    break;
//
//                                }
//                            } else {
//                                System.out.println("AAAAAAAAA " + Interface.plot3D.getAxis(0).getStringMap().containsValue(xl1.get(i)));
//                                System.out.println("BBBBBBBBB " + xl1.contains(xl1.get(i)));
//                                break;
//                            }
//                        }
//
//                    }
//                    d += 2;
//
//                }
//            } else {
//                for (int d = 0; d < FiltrarXDSD.size() - 1; d++) {
//                    for (int i = 0; i <= xl1.size() - 1; i++) {
//                        while (true) {
//                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
//                                if ((Double.parseDouble(xl1.get(i)) < FiltrarXDSD.get(d)) || (Double.parseDouble(xl1.get(i)) > FiltrarXDSD.get(d + 1))) {
//                                    System.out.println("RRRRXXXXX " + xl1.indexOf(xl1.get(i)) + " " + xl1.get(i) + " " + yl1.get(i));
//
//                                    cores.remove(xl1.indexOf(xl1.get(i)));
//                                    formas.remove(xl1.indexOf(xl1.get(i)));
//                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
//                                    yl1.remove(xl1.indexOf(xl1.get(i)));
//                                    zl1.remove(xl1.indexOf(xl1.get(i)));
//                                    xl1.remove(xl1.indexOf(xl1.get(i)));
//                                } else {
//                                    break;
//
//                                }
//                            } else {
//                                break;
//                            }
//                        }
//
//                    }
//                    d += 2;
//                }
//            }
//
//            if (Main.att.AttTypes().get(y).equals("STRING")) {
//                for (int d = 0; d < FiltrarYDSD.size() - 1;) {
//                    for (int i = 0; i <= yl1.size() - 1; i++) {
//                        while (true) {
////                 if((Interface.plot2D.getAxis(1).getStringMap().containsValue(yl1.get(i)))&&(yl1.get(i) != null)&&(!yl1.get(i).equals("null"))){
//                            if (yl1.contains(yl1.get(i)) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
//                                if ((Interface.plot3D.getAxis(1).getStringMap().get(yl1.get(i)) == null) || (Interface.plot3D.getAxis(1).getStringMap().get(yl1.get(i)) < FiltrarYDSD.get(d)) || (Interface.plot3D.getAxis(1).getStringMap().get(yl1.get(i)) > FiltrarYDSD.get(d + 1))) {
//                                    System.out.println("RRRRYYYYYY " + xl1.indexOf(xl1.get(i)) + " " + xl1.get(i) + " " + yl1.get(i));
//
//                                    cores.remove(yl1.indexOf(yl1.get(i)));
//                                    formas.remove(yl1.indexOf(yl1.get(i)));
//                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
//                                    xl1.remove(yl1.indexOf(yl1.get(i)));
//                                    zl1.remove(yl1.indexOf(yl1.get(i)));
//                                    yl1.remove(yl1.indexOf(yl1.get(i)));
//                                } else {
//                                    break;
//
//                                }
//                            } else {
//                                break;
//                            }
//                        }
//
//                    }
//                    d += 2;
//                }
//            } else {
//                for (int d = 0; d < FiltrarYDSD.size() - 1; d++) {
//                    for (int i = 0; i <= yl1.size() - 1; i++) {
//                        while (true) {
//                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
//                                if ((Double.parseDouble(yl1.get(i)) < FiltrarYDSD.get(d)) || (Double.parseDouble(yl1.get(i)) > FiltrarYDSD.get(d + 1))) {
//                                    System.out.println("RRRRYYYYYYY " + xl1.indexOf(xl1.get(i)) + " " + xl1.get(i) + " " + yl1.get(i));
//
//                                    cores.remove(yl1.indexOf(yl1.get(i)));
//                                    formas.remove(yl1.indexOf(yl1.get(i)));
//                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
//                                    xl1.remove(yl1.indexOf(yl1.get(i)));
//                                    zl1.remove(yl1.indexOf(yl1.get(i)));
//                                    yl1.remove(yl1.indexOf(yl1.get(i)));
//                                } else {
//                                    break;
//
//                                }
//                            } else {
//                                break;
//                            }
//                        }
//
//                    }
//                    d += 2;
//                }
//            }
//
//            if (Main.att.AttTypes().get(z).equals("STRING")) {
//                for (int d = 0; d < FiltrarZDSD.size() - 1;) {
//                    for (int i = 0; i <= zl1.size() - 1; i++) {
//                        while (true) {
////                 if((Interface.plot2D.getAxis(1).getStringMap().containsValue(yl1.get(i)))&&(yl1.get(i) != null)&&(!yl1.get(i).equals("null"))){
//                            if (zl1.contains(zl1.get(i)) && (zl1.get(i) != null) && (!zl1.get(i).equals("null"))) {
//                                if ((Interface.plot3D.getAxis(2).getStringMap().get(zl1.get(i)) == null) || (Interface.plot3D.getAxis(2).getStringMap().get(zl1.get(i)) < FiltrarZDSD.get(d)) || (Interface.plot3D.getAxis(2).getStringMap().get(zl1.get(i)) > FiltrarZDSD.get(d + 1))) {
//                                    System.out.println("RRRRYYYYYY " + xl1.indexOf(xl1.get(i)) + " " + xl1.get(i) + " " + yl1.get(i));
//
//                                    cores.remove(zl1.indexOf(zl1.get(i)));
//                                    formas.remove(zl1.indexOf(zl1.get(i)));
//                                    tamanhos.remove(zl1.indexOf(zl1.get(i)));
//                                    xl1.remove(zl1.indexOf(zl1.get(i)));
//                                    yl1.remove(zl1.indexOf(zl1.get(i)));
//                                    zl1.remove(zl1.indexOf(zl1.get(i)));
//                                } else {
//                                    break;
//
//                                }
//                            } else {
//                                break;
//                            }
//                        }
//
//                    }
//                    d += 2;
//                }
//            } else {
//                for (int d = 0; d < FiltrarZDSD.size() - 1; d++) {
//                    for (int i = 0; i <= zl1.size() - 1; i++) {
//                        while (true) {
//                            if ((zl1.contains(zl1.get(i))) && (zl1.get(i) != null) && (!zl1.get(i).equals("null"))) {
//                                if ((Double.parseDouble(zl1.get(i)) < FiltrarZDSD.get(d)) || (Double.parseDouble(zl1.get(i)) > FiltrarZDSD.get(d + 1))) {
//                                    System.out.println("RRRRYYYYYYY " + xl1.indexOf(xl1.get(i)) + " " + xl1.get(i) + " " + yl1.get(i));
//
//                                    cores.remove(zl1.indexOf(zl1.get(i)));
//                                    formas.remove(zl1.indexOf(zl1.get(i)));
//                                    tamanhos.remove(zl1.indexOf(zl1.get(i)));
//                                    xl1.remove(zl1.indexOf(zl1.get(i)));
//                                    yl1.remove(zl1.indexOf(zl1.get(i)));
//                                    zl1.remove(zl1.indexOf(zl1.get(i)));
//                                } else {
//                                    break;
//
//                                }
//                            } else {
//                                break;
//                            }
//                        }
//
//                    }
//                    d += 2;
//                }
//            }
            
            
            
            if (Detalhes && !FiltrarXDSD.isEmpty()) {
            if (Main.att.AttTypes().get(x).equals("STRING")) {
                for (int d = 0; d < 2; d++) {
                    for (int i = 0; i <= xl1.size() - 1; i++) {
                        String str = xl1.get(i).toString();
                        while (true) {
                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) == null) || (Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) < FiltrarXDSD.get(d)) || (Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)) > FiltrarXDSD.get(d + 1))) {
                                    
                                     //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    zl1.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;

                }
            } else {
                for (int d = 0; d < FiltrarXDSD.size() - 1;) {
                    for (int i = 0; i <= xl1.size() - 1; i++) {
                        while (true) {
                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(xl1.get(i)) < FiltrarXDSD.get(d)) || (Double.parseDouble(xl1.get(i)) > FiltrarXDSD.get(d + 1))) {
                                    
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    zl1.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            }

            if (Main.att.AttTypes().get(y).equals("STRING")) {
                for (int d = 0; d < FiltrarYDSD.size() - 1;) {
                    for (int i = 0; i <= yl1.size() - 1; i++) {
                        while (true) {
                            if (yl1.contains(yl1.get(i)) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Interface.plot3D.getAxis(2).getStringMap().get(yl1.get(i)) == null) || (Interface.plot3D.getAxis(2).getStringMap().get(yl1.get(i)) < FiltrarYDSD.get(d)) || (Interface.plot3D.getAxis(2).getStringMap().get(yl1.get(i)) > FiltrarYDSD.get(d + 1))) {
                                    
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    zl1.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            } else {
                for (int d = 0; d < FiltrarYDSD.size() - 1;) {
                    for (int i = 0; i <= yl1.size() - 1; i++) {
                        while (true) {
                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(yl1.get(i)) < FiltrarYDSD.get(d)) || (Double.parseDouble(yl1.get(i)) > FiltrarYDSD.get(d + 1))) {
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    zl1.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            }
            if (Main.att.AttTypes().get(z).equals("STRING")) {
                for (int d = 0; d < FiltrarZDSD.size() - 1;) {
                    for (int i = 0; i <= zl1.size() - 1; i++) {
                        while (true) {
                            if (zl1.contains(zl1.get(i)) && (zl1.get(i) != null) && (!zl1.get(i).equals("null"))) {
                                if ((Interface.plot3D.getAxis(1).getStringMap().get(zl1.get(i)) == null) || (Interface.plot3D.getAxis(1).getStringMap().get(zl1.get(i)) < FiltrarZDSD.get(d)) || (Interface.plot3D.getAxis(1).getStringMap().get(zl1.get(i)) > FiltrarZDSD.get(d + 1))) {
                                    
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(zl1.indexOf(zl1.get(i)));
                                    formas.remove(zl1.indexOf(zl1.get(i)));
                                    tamanhos.remove(zl1.indexOf(zl1.get(i)));
                                    xl1.remove(zl1.indexOf(zl1.get(i)));
                                    yl1.remove(zl1.indexOf(zl1.get(i)));
                                    zl1.remove(zl1.indexOf(zl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            } else {
                for (int d = 0; d < FiltrarZDSD.size() - 1;) {
                    for (int i = 0; i <= zl1.size() - 1; i++) {
                        while (true) {
                            if ((zl1.contains(zl1.get(i))) && (zl1.get(i) != null) && (!zl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(zl1.get(i)) < FiltrarZDSD.get(d)) || (Double.parseDouble(zl1.get(i)) > FiltrarZDSD.get(d + 1))) {
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    cores.remove(zl1.indexOf(zl1.get(i)));
                                    formas.remove(zl1.indexOf(zl1.get(i)));
                                    tamanhos.remove(zl1.indexOf(zl1.get(i)));
                                    xl1.remove(zl1.indexOf(zl1.get(i)));
                                    yl1.remove(zl1.indexOf(zl1.get(i)));
                                    zl1.remove(zl1.indexOf(zl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            }

            System.out.println("Flag 9X");
            System.out.println("QLX " + QLX);
            System.out.println("QHX " + QHX);
            System.out.println("QLY " + QLY);
            System.out.println("QHY " + QHY);
            System.out.println("QLZ " + QLZ);
            System.out.println("QHZ " + QHZ);
            System.out.println("XXX " + xl1);
            System.out.println("YYY " + yl1);
            System.out.println("ZZZ " + zl1);
            System.out.println("CCC " + cores);
            System.out.println("XXX " + xl1.size());
            System.out.println("YYY " + yl1.size());;
            System.out.println("ZZZ " + zl1.size());;
            System.out.println("CCC " + cores.size());
            System.out.println("CUU " + coresUnicas.size());
            System.out.println("CUU " + coresUnicas);


//            //Ignorando os pontos oclusos da aplicação,  
//            List<String> StrList = new ArrayList<>();
//            int contador = xl1.size() - 1; // -1 é referente ao nulo no final 
//            if (xl1.size() < 30) {
//                for (int i = 0; i < xl1.size() - 1; i++) {
//                    String str1 = xl1.get(i) + yl1.get(i) + zl1.get(i);
//                    if (!StrList.contains(str1)) {
//                        StrList.add(str1);
//                        for (int j = 0; j < xl1.size() - 1; j++) {
//                            String str2 = xl1.get(j) + yl1.get(j) + zl1.get(j);
//                            if (i != j && str1.equals(str2)) {
//                                contador--;
//                            }
//                        }
//                    }
//
//                }
//
//            }
//
//            System.out.println("Contador " + contador);
//            QtdPontosVisuais = contador;
            
            
        
        
        }
            
                //Ignorando os pontos oclusos da aplicação,  
        List<String> StrList = new ArrayList<>();
        List<String> AuxMatrizDsd = new ArrayList<>();
        int contador = xl1.size() - 1; // -1 é referente ao nulo no final 
        if (!xl1Coord.isEmpty()) {
            xl1Coord.clear();
            yl1Coord.clear();
            zl1Coord.clear();

        }

        if (xl1.size() < 30) {
            for (int i = 0; i < xl1.size() - 1; i++) {
                String str1 = xl1.get(i) + yl1.get(i) + zl1.get(i);
                if (!StrList.contains(str1)) {
                    xl1Coord.add(xl1.get(i));
                    yl1Coord.add(yl1.get(i));
                    zl1Coord.add(zl1.get(i));
                    if (!MatrizDSD.isEmpty()) {
                        for (int j = 0; j < SalvaAttDsdInt.size(); j++) {
                            AuxMatrizDsd.add(MatrizDSD.get((j * xl1.size()) + i));
                        }
                    }
                    StrList.add(str1);
                    for (int j = 0; j < xl1.size() - 1; j++) {
                        String str2 = xl1.get(j) + yl1.get(j) + zl1.get(i);
                        if (i != j && str1.equals(str2)) {
                            contador--;
                        }
                    }
                }

            }
            if (!AuxMatrizDsd.isEmpty()) {
                MatrizDSD.clear();
                MatrizDSD.addAll(AuxMatrizDsd);
            }

        }
        //Fim da remoção dos pontos oclusos
        
        
         QtdPontosVisuais = contador;
        
        //Inicio Método alternativo
        if (Interface.menu_Detalhes.isSelected() && QtdPontosVisuais <= 10) { // Método alternativo Utilizado para chamar o Painel Detalhes com os números de 0..9 e a desenhar simultanêamente os números na tela através da thread Java
                Interface.LimparPainel(Interface.desktopIFC);
                Interface.BtnsDetails();
            try {
                Main.Interface.setContentPane(((Interface) Main.Interface).PainelIFC());
            } catch (GrammarException ex) {
                Logger.getLogger(Vis3D.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EngineStateError ex) {
                Logger.getLogger(Vis3D.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Controle 0");
        }
        //Fim Método alternativo
        
        
        
        
        List<Integer> CoresUnicasIndex = new ArrayList<>();
        List<Integer> FormasUnicasIndex = new ArrayList<>();
        List<Integer> TamanhosUnicosIndex = new ArrayList<>();

        for (int i = 0; i < coresUnicas.size(); i++) {
            CoresUnicasIndex.add(i);
        }

        for (int i = 0; i <= coresUnicas.size() - 1; i++) {
            if (!cores.contains(coresUnicas.get(i))) {
                CoresUnicasIndex.remove(i);
                coresUnicas.remove(i);
                i--;
            }
        }

        
        for (int i = 0; i < formasUnicas.size(); i++) {
            FormasUnicasIndex.add(i);
        }

        for (int i = 0; i <= formasUnicas.size() - 1; i++) {
            if (!formas.contains(formasUnicas.get(i))) {
                FormasUnicasIndex.remove(i);
                formasUnicas.remove(i);
                i--;
            }

        }

        for (int i = 0; i < tamanhosUnicos.size(); i++) {
            TamanhosUnicosIndex.add(i);
        }

        for (int i = 0; i <= tamanhosUnicos.size() - 1; i++) {
            if (!tamanhos.contains(tamanhosUnicos.get(i))) {
                TamanhosUnicosIndex.remove(i);
                tamanhosUnicos.remove(i);
                i--;
            }

        }

//        for (int i = 0; i <= coresUnicas.size() - 1; i++) {
//            if (!cores.contains(coresUnicas.get(i))) {
//                coresUnicas.remove(i);
//                i--;
//            }
//
//        }
//        for (int i = 0; i <= formasUnicas.size() - 1; i++) {
//            if (!formas.contains(formasUnicas.get(i))) {
//                formasUnicas.remove(i);
//                i--;
//            }
//
//        }
//        for (int i = 0; i <= tamanhosUnicos.size() - 1; i++) {
//            if (!tamanhos.contains(tamanhosUnicos.get(i))) {
//                tamanhosUnicos.remove(i);
//                i--;
//            }
//
//        }


        int smallest = xl1.size();

        // Plotador  Comum XYZ    
        if (indexCor == -1 && indexForma == -1 && indexTamanho == -1) {

            ScatterPlot[] p = new ScatterPlot[1];
            Object[][] XYZ = new Object[smallest - 1][3]; //-1 para tirar o null
            System.out.println("HAHAHAHAHAHAAHHAHA " + zl1.size());
            for (int j = 0; j < xl1.size(); j++) {
                if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null) { //ambos os meios para tirar o null estao gerando um error
                    XYZ[j][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                    XYZ[j][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                    XYZ[j][2] = yl1.get(j);
                    
                }
            }
            System.out.println("hbfadsbckdsvn;pib " + XYZ.length);
//    Interface.plot3D.resetMapData();
//    Interface.plot3D.removeAllPlots();
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            p[0] = new ScatterPlot("Base de Carros", Color.BLUE, 1, 4, Interface.plot3D.mapData(XYZ));
            Interface.plot3D.addPlot(p[0]);
//    p[0] = new ScatterPlot("Base de Carros", Color.BLUE , 1, 4, Interface.plot3D.mapData(XYZ)); 
//    Interface.plot3D.addPlot(p[0]);       
            System.out.println(" Esse é o fim " + IsPlot3D);
        } else if (indexCor != -1 && indexForma == -1 && indexTamanho == -1) {
//      Fim do plotador Comum XYZ


            //    Inicio do plotador Cor

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size()];
//    Interface.plot3D.resetMapData();
//    Interface.plot3D.removeAllPlots();
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int ValorCor = 0;
            TotalCorPorcento = xl1.size() - 1;
            QtdCorPorcento = new int[coresUnicas.size()];

            for (int i = 0; i <= coresUnicas.size() - 1; i++) {
                Object[][] XYZ = new Object[Collections.frequency(cores, coresUnicas.get(i))][3];

                int mark = 0;
                for (int j = 0; j <= xl1.size() - 1; j++) {
                    if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                        XYZ[mark][0] = xl1.get(j);
                        XYZ[mark][1] = zl1.get(j);
                        XYZ[mark][2] = yl1.get(j);
                        mark++;

                    }
                }

                MapaDeObjetos.put(i, XYZ);
                ValorCor = i;
                QtdCorPorcento[i] = mark;
//    int porcentagem = (QtdCorPorcento[i]*100)/TotalPorcento;
                double porcentagem = ((double) QtdCorPorcento[i] * 100) / (double) (TotalCorPorcento - 1);
                
                if (ValorCor < 8) {
                    p[i] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[i], 1, 4, Interface.plot3D.mapData(XYZ));
                    MapaDeCores.put(i, ValorCor);
                } else {
                    ValorCor = 0;
                    p[i] = new ScatterPlot(coresUnicas.get(i).toString() + " " + QtdCorPorcento[i] + "%", ModeloCores[i], 1, 4, Interface.plot3D.mapData(XYZ));
                    MapaDeCores.put(i, ValorCor);
                }

                Interface.plot3D.addPlot(p[i]);


            }

            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));

            }
//          SetaExtremidadesDosEixos();



//    Fim do plotador Cor

        } else if (indexCor == -1 && indexForma == -1 && indexTamanho != -1) {

            //     Plotador T

            ScatterPlot[] p = new ScatterPlot[tamanhosUnicos.size()];
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            TotalCorPorcento = xl1.size() - 1;
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            int ValorTamanho = 0;
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();

            for (int i = 0; i <= tamanhosUnicos.size() - 1; i++) {
                Object[][] XYZ = new Object[Collections.frequency(tamanhos, tamanhosUnicos.get(i))][3];

                int mark = 0;
                for (int j = 0; j <= tamanhos.size() - 1; j++) {
                    if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && tamanhos.get(j) != null && tamanhos.get(j).equals(tamanhosUnicos.get(i))) { //ambos os meios para tirar o null estao gerando um error
                        XYZ[mark][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                        XYZ[mark][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                        XYZ[mark][2] = yl1.get(j);
                        mark++;
                    }

                }
                MapaDeObjetos.put(i, XYZ);
                QtdTamanhoPorcento[i] = mark;
                ValorTamanho = i;
                System.out.println("KKAKAAKKAKAKKA " + i + " QQQQQQQQQQQ " + XYZ.length);
                if (ValorTamanho < 3) {
                    if (ValorTamanho == 0) {
                        p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                        MapaDeTamanhos.put(i, ValorTamanho);
                    } else if (ValorTamanho == 1) {
                        p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 8, Interface.plot3D.mapData(XYZ));
                        MapaDeTamanhos.put(i, ValorTamanho);
                    } else if (ValorTamanho == 2) {
                        p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 16, Interface.plot3D.mapData(XYZ));
                        MapaDeTamanhos.put(i, ValorTamanho);
                    }
                } else {
                    ValorTamanho = 0;
                    p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                    MapaDeTamanhos.put(i, ValorTamanho);
                }
                Interface.plot3D.addPlot(p[i]);
//    ValorTamanho++;
                System.out.println("i " + i);

            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));

            }
//    SetaExtremidadesDosEixos();

//   Fim do Plotador T

        } else if (indexCor == -1 && indexForma != -1 && indexTamanho == -1) {

//     Plotador F

            ScatterPlot[] p = new ScatterPlot[formasUnicas.size()];
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            int ValorForma = 0;
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();

            for (int i = 0; i <= formasUnicas.size() - 1; i++) {
                Object[][] XYZ = new Object[Collections.frequency(formas, formasUnicas.get(i))][3];

                int mark = 0;
                for (int j = 0; j <= formas.size() - 1; j++) {
                    if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && formas.get(j) != null && formas.get(j).equals(formasUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                        XYZ[mark][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                        XYZ[mark][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                        XYZ[mark][2] = yl1.get(j);
                        mark++;
                    }

                }
                MapaDeObjetos.put(i, XYZ);
                QtdFormaPorcento[i] = mark;
                ValorForma = i;
                System.out.println("KKAKAAKKAKAKKA " + i + " QQQQQQQQQQQ " + XYZ.length);
                if (ValorForma < 4) {
                    if (ValorForma == 0) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                        p[i].setDotPattern(DOT_PATTERN);
                        MapaDeFormas.put(i, 0);
                    } else if (ValorForma == 1) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                        p[i].setDotPattern(CROSS_PATTERN);
                        MapaDeFormas.put(i, 1);
                    } else if (ValorForma == 2) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                        p[i].setDotPattern(PTRIANGLE_PATTERN);
                        MapaDeFormas.put(i, 2);
                    } else if (ValorForma == 3) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                        p[i].setDotPattern(PSQUARE_PATTERN);
                        MapaDeFormas.put(i, 3);
                    }
                } else {
                    ValorForma = 0;
                    p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                    p[i].setDotPattern(DOT_PATTERN);
                    MapaDeFormas.put(i, 0);
                }
                Interface.plot3D.addPlot(p[i]);
//    ValorForma++;

            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));

            }
//    SetaExtremidadesDosEixos();

//   Fim do Plotador F

        } else if (indexCor == -1 && indexForma != -1 && indexTamanho != -1) {

//     Inicio do plotador TF

            ScatterPlot[] p = new ScatterPlot[formasUnicas.size() * tamanhosUnicos.size()];
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            int ValorForma = 0;
            int ValorTamanho = 0;
            int plotar = 0;
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdt = 0;
            int mdf = 0;

            for (int f = 0; f <= formasUnicas.size() - 1; f++) {
                for (int t = 0; t <= tamanhosUnicos.size() - 1; t++) {
                    Object[][] XYZ;
                    int mark = 0;
                    int mark1 = 0;

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t))) { //ambos os meios para tirar o null estao gerando um error

                            mark++;
                        }

                    }
                    if (mark != 0) {

                        XYZ = new Object[mark][3];
                    } else {
                        XYZ = new Object[10][3];
                    }

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t))) { //ambos os meios para tirar o null estao gerando um error
                            XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                            XYZ[mark1][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                            XYZ[mark1][2] = yl1.get(j);
                            mark1++;
                        }

                    }

                    if (mark != 0) {

                        MapaDeObjetos.put(f, XYZ);
                        ValorForma = f;
                        ValorTamanho = t;

                        if (ValorForma < 4) {
                            if (ValorForma == 0) {

                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 2) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else if (ValorForma == 1) {
                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(CROSS_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(CROSS_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 2) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(CROSS_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(CROSS_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else if (ValorForma == 2) {
                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(MTRIANGLE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == ValorForma) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(GTRIANGLE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else if (ValorForma == 3) {
                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(PSQUARE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(MSQUARE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 2) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(GSQUARE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(PSQUARE_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            }

                        } else {
                            ValorForma = 0;
                            if (ValorTamanho < 3) {
                                if (ValorTamanho == 0) {
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                } else if (ValorTamanho == 1) {
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                } else if (ValorTamanho == 2) {
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else {
                                ValorTamanho = 0;
                                p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot3D.mapData(XYZ));
                                p[plotar].setDotPattern(DOT_PATTERN);
                                MapaDeFormas.put(mdf, ValorForma);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                            }
                        }
                        QtdFormaPorcento[ValorForma] += mark1;
                        QtdTamanhoPorcento[ValorTamanho] += mark1;
                        Interface.plot3D.addPlot(p[plotar]);
                        plotar++;
                        mdf++;
                        mdt++;

                    }


                }

            }
//    System.out.println("MDO "+MapaDeObjetos.size()+ " Interface.plot3D " + Interface.plot3D.getPlots().size());
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));

            }
//     SetaExtremidadesDosEixos();

//     Fim do plotador TF
        } else if (indexCor != -1 && indexForma == -1 && indexTamanho != -1) {
//     Inicio Plotador CT

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size() * tamanhosUnicos.size()];    //qualque coisa colocar o valor 1000
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            int ValorTamanho = 0;
            int ValorCor = 0;
            int plotar = 0;
            TotalCorPorcento = xl1.size();
            QtdCorPorcento = new int[coresUnicas.size()];
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            QtdPontosPorcento = new int[tamanhosUnicos.size() * coresUnicas.size()];
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdt = 0;
            int mdc = 0;


            for (int i = 0; i <= coresUnicas.size() - 1; i++) {



                for (int f = 0; f <= tamanhosUnicos.size() - 1; f++) {
                    Object[][] XYZ;
                    int mark = 0;
                    int mark1 = 0;

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && tamanhos.get(j).equals(tamanhosUnicos.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                            mark++;
                        }

                    }
                    if (mark != 0) {

                        XYZ = new Object[mark][3];
                    } else {
                        XYZ = new Object[10][3];
                    }



                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && tamanhos.get(j).equals(tamanhosUnicos.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                            XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                            XYZ[mark1][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                            XYZ[mark1][2] = yl1.get(j);
                            mark1++;
                        }

                    }


//    System.out.println("IIIIIIIIIII "+ i + " FFFFFFFFFFFF " + f + " XYZZZZZZZZZZZZ " + XYZ.length + " xxxxx "+xl1.size());
                    if (mark != 0) {
                        ValorCor = i;
                        ValorTamanho = f;
//           QtdPontosPorcento[i] += mark1;
                        QtdPontosPorcento[i] += XYZ.length;
//           int porcentagem = (QtdPontosPorcento[i]*100)/TotalPorcento;  
                        double porcentagem = ((double) QtdPontosPorcento[i] * 100) / (double) (TotalCorPorcento - 1);

                        if (ValorCor < 8) {

                            if (ValorTamanho < 3) {
                                if (ValorTamanho == 0) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 1) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 2) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                }
                            } else {
                                ValorTamanho = 0;
                                p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                MapaDeObjetos.put(plotar, XYZ);
                                plotar++;
                            }
                        } else {
                            ValorCor = 0;
                            if (ValorTamanho < 3) {
                                if (ValorTamanho == 0) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 1) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 2) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                }
                            } else {
                                ValorTamanho = 0;
                                p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                MapaDeObjetos.put(plotar, XYZ);
                                plotar++;
                            }
                        }
                        QtdTamanhoPorcento[ValorTamanho] += mark1;
                        QtdCorPorcento[ValorCor] += mark1;
                        mdc++;
                        mdt++;
//      plotar = i;
                        // Atencao aqui talves a forma de plotar talves nao esteja correta

//       Interface.plot3D.addPlot(p[plotar]);



//       Interface.plot3D.addPlot(p[plotar]);
//       ValorTamanho = f;
//       ValorCor = i;
                        Interface.plot3D.addPlot(p[i]);
//       Interface.plot3D.addPlot(p[i]);

                    }

//       plotar++;    
                }



            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
//        int porcentagem = (MapaDeObjetos.get(mdo).length*100)/xl1.size();  
                double porcentagem = ((double) MapaDeObjetos.get(mdo).length * 100) / (double) (xl1.size() - 1);
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));
                Interface.plot3D.changePlotName(mdo, coresUnicas.get(MapaDeCores.get(mdo)).toString() + " + " + NomeDosTamanhos[MapaDeTamanhos.get(mdo)] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + " % ");
                System.out.println("IIIIIIIIIII " + mdo + " XYZZZZZZZZZZZZ " + MapaDeObjetos.get(mdo).length + " xxxxx " + xl1.size());
            }

//     SetaExtremidadesDosEixos();
//     Fim do Plotador CT

        } else if (indexCor != -1 && indexForma != -1 && indexTamanho == -1) {

//    inicio do Plotador CF

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size() * formasUnicas.size() + 1];    //qualque coisa colocar o valor 1000
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            int ValorForma = 0;
            int ValorCor = 0;
            int plotar = 0;
            TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            QtdCorPorcento = new int[coresUnicas.size()];
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdc = 0;
            int mdf = 0;

            for (int i = 0; i <= coresUnicas.size() - 1; i++) {



                for (int f = 0; f <= formasUnicas.size() - 1; f++) {
                    Object[][] XYZ;
                    int mark = 0;
                    int mark1 = 0;

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                            mark++;
                        }

                    }
                    if (mark != 0) {

                        XYZ = new Object[mark][3];
                    } else {
                        XYZ = new Object[10][3];
                    }

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                            XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                            XYZ[mark1][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                            XYZ[mark1][2] = yl1.get(j);
                            mark1++;
                        }

                    }


                    System.out.println("IIIIIIIIIII " + i + " FFFFFFFFFFFF " + f + " XYZZZZZZZZZZZZ " + XYZ.length);
                    if (mark != 0) {
                        QtdCorPorcento[ValorCor] += mark1;
                        int porcentagem = (QtdCorPorcento[i] * 100) / TotalCorPorcento;


                        if (ValorCor < 8) {

                            if (ValorForma < 4) {

                                if (ValorForma == 0) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);

                                } else if (ValorForma == 1) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(CROSS_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);
                                } else if (ValorForma == 2) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);
                                } else if (ValorForma == 3) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                    p[plotar].setDotPattern(PSQUARE_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);
                                }
                            } else {
                                ValorForma = 0;
                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                p[plotar].setDotPattern(DOT_PATTERN);
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeFormas.put(mdf, ValorForma);
                                MapaDeObjetos.put(plotar, XYZ);
                            }
                        } else {
                            ValorCor = 0;
                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ)); //?
                            MapaDeCores.put(mdc, ValorCor);
                            MapaDeFormas.put(mdf, ValorForma);
                            MapaDeObjetos.put(plotar, XYZ);
                        }
                        Interface.plot3D.addPlot(p[plotar]);
                        plotar++;
                        mdc++;
                        mdf++;
                    }

                    QtdFormaPorcento[ValorForma] += mark1;
                    ValorForma = f;
                    ValorCor = i;


                }
            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
//        int porcentagem = (MapaDeObjetos.get(mdo).length*100)/xl1.size(); 
                double porcentagem = ((double) MapaDeObjetos.get(mdo).length * 100) / (double) (xl1.size() - 1);
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));
                Interface.plot3D.changePlotName(mdo, coresUnicas.get(MapaDeCores.get(mdo)).toString() + " + " + NomeDasFormas[MapaDeFormas.get(mdo)] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + " % ");
                System.out.println("IIIIIIIIIII " + mdo + " XYZZZZZZZZZZZZ " + MapaDeObjetos.get(mdo).length + " xxxxx " + xl1.size());
            }
//     SetaExtremidadesDosEixos();
//     Fim do Plotador CF
        } else if (indexCor != -1 && indexForma != -1 && indexTamanho != -1) {

//  inicio do plotador CFT 

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size() * formasUnicas.size() * tamanhosUnicos.size()];
            Interface.plot3D.resetMapData();
            Interface.plot3D.removeAllPlots();
            int ValorForma = 0;
            int ValorCor = 0;
            int ValorTamanho = 0;
            int plotar = 0;
            TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            QtdCorPorcento = new int[coresUnicas.size()];
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdc = 0;
            int mdf = 0;
            int mdt = 0;

            for (int i = 0; i <= coresUnicas.size() - 1; i++) {



                for (int f = 0; f <= formasUnicas.size() - 1; f++) {
                    for (int t = 0; t <= tamanhosUnicos.size() - 1; t++) {
                        Object[][] XYZ;
                        int mark = 0;
                        int mark1 = 0;

                        for (int j = 0; j <= xl1.size() - 1; j++) {
                            if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                                mark++;
                            }

                        }
                        if (mark != 0) {

                            XYZ = new Object[mark][3];
                        } else {
                            XYZ = new Object[10][3];
                        }

                        for (int j = 0; j < xl1.size() - 1; j++) {
                            if (xl1.get(j) != null && yl1.get(j) != null && zl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                                XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                                XYZ[mark1][1] = zl1.get(j); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
                                XYZ[mark1][2] = yl1.get(j);
                                mark1++;
                            }

                        }


                        System.out.println("IIIIIIIIIII " + i + " FFFFFFFFFFFF " + f + " XYZZZZZZZZZZZZ " + XYZ.length);
                        if (mark != 0) {
                            QtdCorPorcento[ValorCor] += mark1;
                            int porcentagem = (QtdCorPorcento[i] * 100) / TotalCorPorcento;


                            if (ValorCor < 8) {

                                if (ValorForma < 4) {
                                    if (ValorForma == 0) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(DOT_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(DOT_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(DOT_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else if (ValorForma == 1) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(CROSS_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(CROSS_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(CROSS_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(CROSS_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else if (ValorForma == 2) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(MTRIANGLE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(GTRIANGLE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else if (ValorForma == 3) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(PSQUARE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(MSQUARE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                                p[plotar].setDotPattern(GSQUARE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(PSQUARE_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    }

                                } else {
                                    ValorForma = 0;
                                    if (ValorTamanho < 3) {
                                        if (ValorTamanho == 0) {
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        } else if (ValorTamanho == 1) {
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        } else if (ValorTamanho == 2) {
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot3D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else {
                                        ValorTamanho = 0;
                                        p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeCores.put(mdc, ValorCor);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                        MapaDeObjetos.put(plotar, XYZ);
                                    }
                                }
                            } else {
                                ValorCor = 0;
                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot3D.mapData(XYZ)); //?
//            MapaDeCores.put(plotar, ValorCor);
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeFormas.put(mdf, ValorForma);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                MapaDeObjetos.put(plotar, XYZ);
                            }
                            Interface.plot3D.addPlot(p[plotar]);
                            plotar++;
                            mdc++;
                            mdf++;
                            mdt++;
                        }
                        QtdCorPorcento[ValorCor] += mark1;
                        QtdFormaPorcento[ValorForma] += mark1;
                        QtdTamanhoPorcento[ValorTamanho] += mark1;

                        ValorForma = f;
                        ValorCor = i;
                        ValorTamanho = t;
                    }
                }
            }

            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
//        int porcentagem = (MapaDeObjetos.get(mdo).length*100)/xl1.size();  
                double porcentagem = ((double) MapaDeObjetos.get(mdo).length * 100) / (double) (xl1.size() - 1);
                Interface.plot3D.changePlotData(mdo, Interface.plot3D.mapData(MapaDeObjetos.get(mdo)));
                Interface.plot3D.changePlotName(mdo, coresUnicas.get(MapaDeCores.get(mdo)).toString() + " + " + NomeDasFormas[MapaDeFormas.get(mdo)] + " + " + NomeDosTamanhos[MapaDeTamanhos.get(mdo)] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + " % ");
                System.out.println("IIIIIIIIIII " + mdo + " XYZZZZZZZZZZZZ " + MapaDeObjetos.get(mdo).length + " xxxxx " + xl1.size());
            }
//    SetaExtremidadesDosEixos();
//  Fim do Antigo plotador CFT

        }






        if (!FiltrarFormaCategorico.isEmpty() && !FiltrarFormaCategorico.equals(null) && FiltrarFormaCategorico != null) {
            for (int p = 0; p <= Interface.plot3D.getPlots().size() - 1; p++) {
                if (FiltrarFormaCategorico.containsValue(MapaDeFormas.get(p))) {
                    Interface.plot3D.getPlot(p).setVisible(false);
                    TotalFormaPorcento -= Interface.plot3D.getPlot(p).getData().length;
                    System.out.println("FOFOFOFFOFOFOFOFOFO " + p + "  " + (Interface.plot3D.getPlot(p).getData().length));
                    System.out.println("Total F " + TotalFormaPorcento);
                }

            }

        }


        if (!FiltrarCorCategorico.isEmpty() && !FiltrarCorCategorico.equals(null) && FiltrarCorCategorico != null) {
            for (int p = 0; p <= Interface.plot3D.getPlots().size() - 1; p++) {
                if (FiltrarCorCategorico.containsValue(MapaDeCores.get(p))) {
                    Interface.plot3D.getPlot(p).setVisible(false);
                    TotalCorPorcento -= Interface.plot3D.getPlot(p).getData().length;
                    System.out.println("COROCOROCOROCOROCOR " + p + "  " + (Interface.plot3D.getPlot(p).getData().length));
                    System.out.println("Total C " + TotalCorPorcento);
                }
            }

        }



        if (!FiltrarTamanhoCategorico.isEmpty() && !FiltrarTamanhoCategorico.equals(null) && FiltrarTamanhoCategorico != null) {
            for (int p = 0; p <= Interface.plot3D.getPlots().size() - 1; p++) {
                if (FiltrarTamanhoCategorico.containsValue(MapaDeTamanhos.get(p))) {
                    Interface.plot3D.getPlot(p).setVisible(false);
                    TotalTamanhoPorcento -= Interface.plot3D.getPlot(p).getData().length;
                    System.out.println("TATATAATATATATATATTAAT " + p + "  " + (Interface.plot3D.getPlot(p).getData().length));
                    System.out.println("Total T " + TotalTamanhoPorcento);
                }
            }

        }



        ArrayList<String> TempCor = new ArrayList<String>(FiltrarCorCategorico.keySet());
        ArrayList<String> TempForma = new ArrayList<String>(FiltrarFormaCategorico.keySet());
        ArrayList<String> TempTamanho = new ArrayList<String>(FiltrarTamanhoCategorico.keySet());

//                      System.out.println("CCCCCCCCCCCCCCCCCCCCCC  "+cores.size());
//                      System.out.println("FFFFFFFFFFFFFFFFFFFFFF  "+formas.size());
//                      System.out.println("TTTTTTTTTTTTTTTTTTTTTT  "+tamanhos.size());


        if (!TempCor.isEmpty() && !TempCor.equals(null) && TempCor != null) {
            for (int p = 0; p <= TempCor.size() - 1; p++) {
                System.out.println("FFFFFFFFFF " + TempCor.get(p));
                System.out.println("BCGDBCGDCBGDCBGDCBGDCB " + TempCor.get(p).substring(0, TempCor.get(p).indexOf(" ")));
                while (true) {
                    if (cores.contains(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" ")))) {
                        System.out.println("IIIIIININININININININI " + cores.indexOf(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" "))));

                        formas.remove(cores.indexOf(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" "))));
                        tamanhos.remove(cores.indexOf(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" "))));
                        cores.remove(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" ")));

                    } else {
                        break;
                    }
                }
            }

        }
        if (!TempForma.isEmpty() && !TempForma.equals(null) && TempForma != null) {
            for (int p = 0; p <= TempForma.size() - 1; p++) {
                while (true) {
                    if (formas.contains(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" ")))) {
                        tamanhos.remove(formas.indexOf(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" "))));
                        cores.remove(formas.indexOf(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" "))));
                        formas.remove(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" ")));
                    } else {
                        break;
                    }
                }
            }

        }
        if (!TempTamanho.isEmpty() && !TempTamanho.equals(null) && TempTamanho != null) {
            for (int p = 0; p <= TempTamanho.size() - 1; p++) {
                while (true) {
                    if (tamanhos.contains(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" ")))) {
                        formas.remove(tamanhos.indexOf(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" "))));
                        cores.remove(tamanhos.indexOf(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" "))));
                        tamanhos.remove(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" ")));
                    } else {
                        break;
                    }
                }
            }

        }

//             if (!FiltrarFormaCategorico.isEmpty() && !FiltrarFormaCategorico.equals(null) && FiltrarFormaCategorico != null){
//                          for (int i = 0; i <= FiltrarFormaCategorico.size()-1; i++) {
//                                    for (int p = 0; p <= Interface.plot3D.getPlots().size()-1; p++) {
//                                        if (FiltrarFormaCategorico.containsValue(MapaDeFormas.get(p))) {
//                                            IMATVI.Interface.plot3D.getPlot(p).setVisible(false);
//                                        }
//                                     
//                                    }
//                                    
//                                }
//                                }
//                    
//                    
//                      if (!FiltrarCorCategorico.isEmpty() && !FiltrarCorCategorico.equals(null) && FiltrarCorCategorico != null) {
//                          System.out.println("Estou Vazio");
//                           for (int i = 0; i <= FiltrarCorCategorico.size()-1; i++) {
//                                    for (int p = 0; p <= Interface.plot3D.getPlots().size()-1; p++) {
//                                        if (FiltrarCorCategorico.containsValue(MapaDeCores.get(p))) {
//                                        Interface.plot3D.getPlot(p).setVisible(false);
//                                        }
//                                    }
//                                    
//                                }
//                        }
//
//                    if (!FiltrarTamanhoCategorico.isEmpty() && !FiltrarTamanhoCategorico.equals(null) && FiltrarTamanhoCategorico != null) {
//                         System.out.println("Estou Cheio Tamanho");
//                          for (int i = 0; i <= FiltrarTamanhoCategorico.size()-1; i++) {
//                                    for (int p = 0; p <= Interface.plot3D.getPlots().size()-1; p++) {
//                                        if (FiltrarTamanhoCategorico.containsValue(MapaDeTamanhos.get(p))) {
//                                            IMATVI.Interface.plot3D.getPlot(p).setVisible(false);
//                                        }
//                                    }
//                                    
//                                }
//                                }


//                     Fim do Filtro de Conjuntos de Plots   

//         SetaExtremidadesDosEixos();

        Interface.plot3D.setAxisLabel(0, eixox);
        Interface.plot3D.setAxisLabel(1, eixoz);
        Interface.plot3D.setAxisLabel(2, eixoy);

        Interface.plot3D.repaint();
    }

    public static void plot2DVis(int x, int y, String eixox, String eixoy) throws FileNotFoundException, IOException {

        Interface.Filtrar.setEnabled(true);
        Interface.Interagir.setEnabled(true);
        Interface.Cor.setEnabled(true);
        Interface.Forma.setEnabled(true);
        Interface.Tamanho.setEnabled(true);

        if (firstTime) {
            Interface.plot2D.removeAllPlots();
            Interface.plot3D.removeAllPlots();

        }

        String[] x1 = Main.matrix[x];
        String[] y1 = Main.matrix[y];
        String[] cor;
        String[] forma; //Combustivel para testar cor 5
        String[] tamanho; //Combustivel para testar cor 5
        
//        xy =  Main.matrix[y]; //Combustivel para testar cor 5
//        xy =  Main.matrix[x]; //Combustivel para testar cor 5
        
        
//    System.out.println("shahashahsahshashashas " + x1.length);
//    String[] coresUnicas;
//    String[] formasUnicas; //Combustivel para testar cor
//    String[] tamanhosUnicos; //Combustivel para testar cor
//    List<String> cores;
//    List<String> formas;
//    List<String> tamanhos;

        if (indexCor == -1) {
            cor = Main.matrix[5]; //Combustivel para testar cor 5
//                coresUnicas = Arrays.asList(Main.att.GetUniqueValues(5));
            coresUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(5)));
            cores = new ArrayList<String>(Arrays.asList(cor));
        } else {
            cor = Main.matrix[indexCor]; //Combustivel para testar cor 5
//                coresUnicas = Arrays.asList(Main.att.GetUniqueValues(indexCor));
            coresUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(indexCor)));
            cores = new ArrayList<String>(Arrays.asList(cor));
        }
        if (indexForma == -1) {
            forma = Main.matrix[1]; //Combustivel para testar cor 5
            formasUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(1)));
            formas = new ArrayList<String>(Arrays.asList(forma));
        } else {
            forma = Main.matrix[indexForma]; //Combustivel para testar cor 5
            formasUnicas = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(indexForma)));
            formas = new ArrayList<String>(Arrays.asList(forma));
        }
        if (indexTamanho == -1) {
            tamanho = Main.matrix[2]; //Combustivel para testar cor 5
            tamanhosUnicos = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(2)));
            tamanhos = new ArrayList<String>(Arrays.asList(tamanho));
        } else {
            tamanho = Main.matrix[indexTamanho]; //Combustivel para testar cor 5
            tamanhosUnicos = new ArrayList(Arrays.asList(Main.att.GetUniqueValues(indexTamanho)));
            tamanhos = new ArrayList<String>(Arrays.asList(tamanho));
        }


//    List<String> xl1 = new ArrayList<String>(Arrays.asList(x1));
//    List<String> yl1 = new ArrayList<String>(Arrays.asList(y1));
        xl1 = new ArrayList<String>(Arrays.asList(x1));
        yl1 = new ArrayList<String>(Arrays.asList(y1));
//    System.out.println("shahsahsahsahsahs2 " + xl1.size());
//    List<Float> yl1 = new ArrayList<Float>();
//    List<Double> yl1 = new ArrayList<Double>();
//    List<Object> yl1 = new ArrayList<Object>();

//    for (int i = 0; i <= y1.length-1; i++) {
//        if(y1[i] != null || y1[i] != ""){
//            if (i == y1.length-1) {
//              System.out.println("ANU "+Double.parseDouble(y1[y1.length-2]));  
//              yl1.add(Double.parseDouble(y1[y1.length-2]));
//            }else
//                yl1.add(Double.parseDouble(y1[i]));
//            
////               System.out.println("ANU "+Double.parseDouble(y1[i])); 
//               
////        double temp = Double.parseDouble(y1[i]);
//       
////        yl1.set(yl1.indexOf(yl1.get(i)), temp);
//        }
//    }
//    
//    yl1 = yl1D;
        
        
//        if (!SalvaAttDsdInt.isEmpty()) {
//            String[] AttDSD;
//            MatrizDSD = new ArrayList[SalvaAttDsdInt.size()][xl1.size()-1];
//            for (int i = 0; i < SalvaAttDsdInt.size(); i++) {
//                AttDSD = Main.matrix[SalvaAttDsdInt.get(i)]; //Combustivel para testar cor 5
//            
//                MatrizDSD[i][i] = new ArrayList(Arrays.asList(AttDSD));
////                MatrizDSD[i] = new ArrayList(Arrays.asList(AttDSD));
//            }
//            
//        }
        
        if (!SalvaAttDsdInt.isEmpty()) {
            for (int i = 0; i < SalvaAttDsdInt.size(); i++) {
                System.out.println("SalvaAttDsdInt " + Arrays.toString(Main.matrix[SalvaAttDsdInt.get(i)]));
                    MatrizDSD.addAll(Arrays.asList(Main.matrix[SalvaAttDsdInt.get(i)]));
            }
         }
        
        

        MapaDeCores.clear();
        MapaDeTamanhos.clear();
        MapaDeFormas.clear();

        System.out.println("Novo filtro Começo 5");

        if (FiltrarXCategorico.isEmpty()) {


            if (!Main.att.AttTypes().get(x).equals("FLOAT")) {
                System.out.println("Flag 1");
                if ((BtnListener.EixoIndice == 0) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int j = 0; j <= Interface.Categoricos.length - 1; j++) {
                        if (Interface.Categoricos[j].isSelected()) {
                            while (true) {

                                if (xl1.contains(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1))) {

                                    xl1.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    yl1.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    cores.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    formas.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    tamanhos.remove(xl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                } else {
                                    break;
                                }
                            }

                        }
                    }
                }

            }
        } else {

            if (!Main.att.AttTypes().get(x).equals("FLOAT")) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    System.out.println("Dentro da Flag 2");
                    for (int j = 0; j < FiltrarXCategorico.size(); j++) {
                        while (true) {

                            if (xl1.contains(FiltrarXCategorico.get(j))) {

                                yl1.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                cores.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                formas.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                tamanhos.remove(xl1.indexOf(FiltrarXCategorico.get(j)));
                                xl1.remove(xl1.indexOf(FiltrarXCategorico.get(j)));

                            } else {
                                break;
                            }
                        }
                    }
                }

            }

        }


        if (FiltrarYCategorico.isEmpty()) {

            if (!Main.att.AttTypes().get(y).equals("FLOAT")) {
                if ((BtnListener.EixoIndice == 1) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int j = 0; j <= Interface.Categoricos.length - 1; j++) {
                        if (Interface.Categoricos[j].isSelected()) {

                            while (true) {
                                if (yl1.contains(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1))) {
                                    xl1.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    cores.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    formas.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    tamanhos.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));
                                    yl1.remove(yl1.indexOf(Interface.Categoricos[j].getText().substring(Interface.Categoricos[j].getText().indexOf('-') + 1)));

                                } else {
                                    break;
                                }
                            }
                            System.out.println("Flag 3");
                        }

                    }
                }
            }

        } else {
            if (!Main.att.AttTypes().get(y).equals("FLOAT")) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int j = 0; j < FiltrarYCategorico.size(); j++) {
                        while (true) {

                            if (yl1.contains(FiltrarYCategorico.get(j))) {

                                xl1.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                cores.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                formas.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                tamanhos.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                                yl1.remove(yl1.indexOf(FiltrarYCategorico.get(j)));
                            } else {
                                break;
                            }
                        }
                        System.out.println("Flag 4");


                    }

                }

            }


        }


        if (FiltrarXNumerico.isEmpty()) {



            if (!Main.att.AttTypes().get(x).equals("STRING") && Main.att.GetUniqueValues(x).length >= 20) {
                FiltrarXNumerico.add(0, "" + GetMinMaxValueX()[0]);
                FiltrarXNumerico.add(1, "" + GetMinMaxValueX()[1]);
                if ((BtnListener.EixoIndice == 0) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= xl1.size() - 1; i++) {

                        while (true) {

                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(xl1.get(i).toString()) <= Double.parseDouble(FiltrarXNumerico.get(0))) || (Double.parseDouble(xl1.get(i).toString()) >= Double.parseDouble(FiltrarXNumerico.get(1)))) { //Bug solucionado, porém não otimizado(Todas as vezes que allplot é chamado )
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }
                    }
                    System.out.println("Flag 7");

                }

            }

        } else {
            if (!Main.att.AttTypes().get(x).equals("STRING") && Main.att.GetUniqueValues(x).length >= 20) {
                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= xl1.size() - 1; i++) {
                        while (true) {

                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(xl1.get(i).toString()) <= Double.parseDouble(FiltrarXNumerico.get(0))) || (Double.parseDouble(xl1.get(i).toString()) >= Double.parseDouble(FiltrarXNumerico.get(1)))) {
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }



                    }

                }

            }
            System.out.println("Flag 8");
        }


        if (FiltrarYNumerico.isEmpty()) {
            if (!Main.att.AttTypes().get(y).equals("STRING") && Main.att.GetUniqueValues(y).length >= 20) {
                if ((BtnListener.EixoIndice == 1) && (BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= yl1.size() - 1; i++) {


                        while (true) {

                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(yl1.get(i).toString()) <= Double.parseDouble(FiltrarYNumerico.get(0))) || (Double.parseDouble(yl1.get(i).toString()) >= Double.parseDouble(FiltrarYNumerico.get(1)))) {
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                }
                System.out.println("Flag 9");
            }
        } else {
            if (!Main.att.AttTypes().get(y).equals("STRING") && Main.att.GetUniqueValues(y).length >= 20) {
                System.out.println("Dentro da Flag 10");

                if ((BtnListener.FiltrarBool)) { // Falta verificar se e numerico ou categorico
                    for (int i = 0; i <= yl1.size() - 1; i++) {
                        if ((yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
//                    System.out.println("Lista "+Double.parseDouble(yl1.get(i)));
                            System.out.println("Filtrar Gravado " + Double.parseDouble(FiltrarYNumerico.get(0)));
                        }
                        while (true) {

                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(yl1.get(i).toString()) <= Double.parseDouble(FiltrarYNumerico.get(0))) || (Double.parseDouble(yl1.get(i).toString()) >= Double.parseDouble(FiltrarYNumerico.get(1)))) {
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }

                }
            }


        }

        if (Detalhes && !FiltrarXDSD.isEmpty()) {
            if (Main.att.AttTypes().get(x).equals("STRING")) {
                for (int d = 0; d < 2; d++) {
                    for (int i = 0; i <= xl1.size() - 1; i++) {
                        String str = xl1.get(i).toString();
                        while (true) {
                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Interface.plot2D.getAxis(0).getStringMap().get(xl1.get(i)) == null) || (Interface.plot2D.getAxis(0).getStringMap().get(xl1.get(i)) < FiltrarXDSD.get(d)) || (Interface.plot2D.getAxis(0).getStringMap().get(xl1.get(i)) > FiltrarXDSD.get(d + 1))) {
                                    
                                     //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;

                }
            } else {
                for (int d = 0; d < FiltrarXDSD.size() - 1;) {
                    for (int i = 0; i <= xl1.size() - 1; i++) {
                        while (true) {
                            if ((xl1.contains(xl1.get(i))) && (xl1.get(i) != null) && (!xl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(xl1.get(i)) < FiltrarXDSD.get(d)) || (Double.parseDouble(xl1.get(i)) > FiltrarXDSD.get(d + 1))) {
                                    
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(xl1.indexOf(xl1.get(i)));
                                    formas.remove(xl1.indexOf(xl1.get(i)));
                                    tamanhos.remove(xl1.indexOf(xl1.get(i)));
                                    yl1.remove(xl1.indexOf(xl1.get(i)));
                                    xl1.remove(xl1.indexOf(xl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            }

            if (Main.att.AttTypes().get(y).equals("STRING")) {
                for (int d = 0; d < FiltrarYDSD.size() - 1;) {
                    for (int i = 0; i <= yl1.size() - 1; i++) {
                        while (true) {
                            if (yl1.contains(yl1.get(i)) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Interface.plot2D.getAxis(1).getStringMap().get(yl1.get(i)) == null) || (Interface.plot2D.getAxis(1).getStringMap().get(yl1.get(i)) < FiltrarYDSD.get(d)) || (Interface.plot2D.getAxis(1).getStringMap().get(yl1.get(i)) > FiltrarYDSD.get(d + 1))) {
                                    
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            } else {
                for (int d = 0; d < FiltrarYDSD.size() - 1;) {
                    for (int i = 0; i <= yl1.size() - 1; i++) {
                        while (true) {
                            if ((yl1.contains(yl1.get(i))) && (yl1.get(i) != null) && (!yl1.get(i).equals("null"))) {
                                if ((Double.parseDouble(yl1.get(i)) < FiltrarYDSD.get(d)) || (Double.parseDouble(yl1.get(i)) > FiltrarYDSD.get(d + 1))) {
                                    //Inicio do remover Detalhes
                                    if (!SalvaAttDsdInt.isEmpty()) {
                                        for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                                            MatrizDSD.remove((xl1.size()*k)+(xl1.indexOf(xl1.get(i)))-k);
                                        }
                                     }
                                    //Fim do remover Detalhes
                                    cores.remove(yl1.indexOf(yl1.get(i)));
                                    formas.remove(yl1.indexOf(yl1.get(i)));
                                    tamanhos.remove(yl1.indexOf(yl1.get(i)));
                                    xl1.remove(yl1.indexOf(yl1.get(i)));
                                    yl1.remove(yl1.indexOf(yl1.get(i)));
                                } else {
                                    break;

                                }
                            } else {
                                break;
                            }
                        }

                    }
                    d += 2;
                }
            }
            System.out.println("Flag 9X");
            System.out.println("QLX " + QLX);
            System.out.println("QHX " + QHX);
            System.out.println("QLY " + QLY);
            System.out.println("QHY " + QHY);
            System.out.println("XXX " + xl1);
            System.out.println("YYY " + yl1);
            System.out.println("CCC " + cores);
            System.out.println("XXX " + xl1.size());
            System.out.println("YYY " + yl1.size());;
            System.out.println("CCC " + cores.size());
            System.out.println("CUU " + coresUnicas.size());
            System.out.println("CUU " + coresUnicas);


        }


        //Ignorando os pontos oclusos da aplicação,  
        List<String> StrList = new ArrayList<>();
        List<String> AuxMatrizDsd = new ArrayList<>();
        int contador = xl1.size() - 1; // -1 é referente ao nulo no final 
        if (!xl1Coord.isEmpty()) {
            xl1Coord.clear();
            yl1Coord.clear();

        }

        if (xl1.size() < 30) {
            for (int i = 0; i < xl1.size() - 1; i++) {
                String str1 = xl1.get(i) + yl1.get(i);
                if (!StrList.contains(str1)) {
                    xl1Coord.add(xl1.get(i));
                    yl1Coord.add(yl1.get(i));
                    if (!MatrizDSD.isEmpty()) {
                        for (int j = 0; j < SalvaAttDsdInt.size(); j++) {
//                            AuxMatrizDsd.add(MatrizDSD.get(i * SalvaAttDsdInt.size()));
                            AuxMatrizDsd.add(MatrizDSD.get((j * xl1.size()) + i));
                        }
                    }
                    StrList.add(str1);
                    for (int j = 0; j < xl1.size() - 1; j++) {
                        String str2 = xl1.get(j) + yl1.get(j);
                        if (i != j && str1.equals(str2)) {
                            contador--;
                        }
                    }
                }

            }
            if (!AuxMatrizDsd.isEmpty()) {
                MatrizDSD.clear();
                MatrizDSD.addAll(AuxMatrizDsd);
            }

        }
        //Fim da remoção dos pontos oclusos
        
        System.out.println("Contador " + contador);
        System.out.println("XCoord " + coresUnicas.size());
        System.out.println("YCoord " + formasUnicas.size());
        QtdPontosVisuais = contador;

        //Inicio Método alternativo
        if (Interface.menu_Detalhes.isSelected() && QtdPontosVisuais <= 10) { // Método alternativo Utilizado para chamar o Painel Detalhes com os números de 0..9 e a desenhar simultanêamente os números na tela através da thread Java
                Interface.LimparPainel(Interface.desktopIFC);
                Interface.BtnsDetails();
            try {
                Main.Interface.setContentPane(((Interface) Main.Interface).PainelIFC());
            } catch (GrammarException ex) {
                Logger.getLogger(Vis3D.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EngineStateError ex) {
                Logger.getLogger(Vis3D.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Fim Método alternativo


        List<Integer> CoresUnicasIndex = new ArrayList<>();
        List<Integer> FormasUnicasIndex = new ArrayList<>();
        List<Integer> TamanhosUnicosIndex = new ArrayList<>();

        for (int i = 0; i < coresUnicas.size(); i++) {
            CoresUnicasIndex.add(i);
        }

        for (int i = 0; i <= coresUnicas.size() - 1; i++) {
            if (!cores.contains(coresUnicas.get(i))) {
                CoresUnicasIndex.remove(i);
                coresUnicas.remove(i);
                i--;
            }
        }

        
        for (int i = 0; i < formasUnicas.size(); i++) {
            FormasUnicasIndex.add(i);
        }

        for (int i = 0; i <= formasUnicas.size() - 1; i++) {
            if (!formas.contains(formasUnicas.get(i))) {
                FormasUnicasIndex.remove(i);
                formasUnicas.remove(i);
                i--;
            }

        }

        for (int i = 0; i < tamanhosUnicos.size(); i++) {
            TamanhosUnicosIndex.add(i);
        }

        for (int i = 0; i <= tamanhosUnicos.size() - 1; i++) {
            if (!tamanhos.contains(tamanhosUnicos.get(i))) {
                TamanhosUnicosIndex.remove(i);
                tamanhosUnicos.remove(i);
                i--;
            }

        }
        
        int smallest = xl1.size();
        if (smallest > yl1.size()) {
            smallest = yl1.size();
        }


        TotalCorPorcento = xl1.size();
        TotalFormaPorcento = xl1.size();
        TotalTamanhoPorcento = xl1.size();


        // Plotador  Comum XY    
        if (indexCor == -1 && indexForma == -1 && indexTamanho == -1) {
            ScatterPlot[] p = new ScatterPlot[1];
            Object[][] XYZ = new Object[smallest - 1][2]; //-1 para tirar o null
            for (int j = 0; j <= xl1.size() - 1; j++) {
                if (xl1.get(j) != null && yl1.get(j) != null) { //ambos os meios para tirar o null estao gerando um error
                    XYZ[j][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                    XYZ[j][1] = yl1.get(j);  //Inserir o -1 para ver se tira o null
//    	XYZ[j][1] = yl1.get(j)+"\u200f"; //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado
//    	XYZ[j][1] = Double.parseDouble(yl1.get(j)); //\u200f o obejto XYZ não aceita !String então para converter é utilizado o \u200f que é o caractere vázio que concatenado com o valor !String é plotado

                }
            }
//    Arrays.sort(XYZ[0][1]);
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
            p[0] = new ScatterPlot("Base de Carros", Color.BLUE, 1, 4, Interface.plot2D.mapData(XYZ));
            Interface.plot2D.addPlot(p[0]);

//    Interface.plot2D.changePlotData(0, Interface.plot2D.mapData(XYZ));
        } else if (indexCor != -1 && indexForma == -1 && indexTamanho == -1) {
//      Fim do plotador Comum XYZ

//    Inicio do plotador Cor
//    coresUnicas = Main.att.GetUniqueValues(cores);
            ScatterPlot[] p = new ScatterPlot[coresUnicas.size()];
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int ValorCor = 0;
//    TotalCorPorcento = xl1.size()-1;
            QtdCorPorcento = new int[coresUnicas.size()];
            for (int i = 0; i <= coresUnicas.size() - 1; i++) {
//        if (Collections.frequency(cores, coresUnicas.get(i)) != 0) {
                Object[][] XYZ = new Object[Collections.frequency(cores, coresUnicas.get(i))][2];



                int mark = 0;
                for (int j = 0; j <= xl1.size() - 1; j++) {
                    if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
//            System.out.println("UUUUUUUUUUU  "+cores.get(j).equals(coresUnicas[0]));
//        XYZ[mark][0] = "\u200f" +xl1.get(j);  
//        XYZ[mark][1] = "\u200f" +yl1.get(j);
                        XYZ[mark][0] = xl1.get(j);
                        XYZ[mark][1] = yl1.get(j);
                        mark++;

                    }
                }

                MapaDeObjetos.put(i, XYZ);
                ValorCor = i;
//                ValorCor = CoresUnicasIndex.get(i);
                QtdCorPorcento[i] = mark;
                System.out.println("XYZ TST " + XYZ.length);
                System.out.println("FRQ CORES " + Collections.frequency(cores, coresUnicas.get(i)));

//    int porcentagem = (QtdCorPorcento[i]*100)/TotalPorcento;
                double porcentagem = ((double) (QtdCorPorcento[i] * 100)) / (double) (TotalCorPorcento - 1);
                if (ValorCor < 8) {
//           p[i] = new ScatterPlot(coresUnicas.get(i).toString()+" ➞ "+ porcentagem+"%",ModeloCores[i] , 1, 4, Interface.plot2D.mapData(XYZ)); 
                    p[i] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[CoresUnicasIndex.get(i)], 1, 4, Interface.plot2D.mapData(XYZ));
                    MapaDeCores.put(i, ValorCor);
//           MapaDeCores.put(i,CoresUnicasIndex.get(i));
                } else {
                    ValorCor = 0;
                    p[i] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + QtdCorPorcento[i] + "%", ModeloCores[i], 1, 4, Interface.plot2D.mapData(XYZ));
                    MapaDeCores.put(i, ValorCor);
                }

                Interface.plot2D.addPlot(p[i]);

//        }

            }

            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));

            }
//          SetaExtremidadesDosEixos();



//    Fim do plotador Cor

        } else if (indexCor == -1 && indexForma == -1 && indexTamanho != -1) {

            //     Plotador T

            ScatterPlot[] p = new ScatterPlot[tamanhosUnicos.size()];
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
//    TotalCorPorcento = xl1.size()-1;
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            int ValorTamanho = 0;
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();

            for (int i = 0; i <= tamanhosUnicos.size() - 1; i++) {
                Object[][] XYZ = new Object[Collections.frequency(tamanhos, tamanhosUnicos.get(i))][2];

                int mark = 0;
                for (int j = 0; j <= tamanhos.size() - 1; j++) {
                    if (xl1.get(j) != null && yl1.get(j) != null && tamanhos.get(j) != null && tamanhos.get(j).equals(tamanhosUnicos.get(i))) { //ambos os meios para tirar o null estao gerando um error
                        XYZ[mark][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                        XYZ[mark][1] = yl1.get(j);
//        XYZ[mark][0] = "\u200f" +xl1.get(j);  //Inserir o -1 para ver se tira o null
//        XYZ[mark][1] = "\u200f" +yl1.get(j);
                        mark++;
                    }

                }
                MapaDeObjetos.put(i, XYZ);
                QtdTamanhoPorcento[i] = mark;
                ValorTamanho = i;
                System.out.println("KKAKAAKKAKAKKA " + i + " QQQQQQQQQQQ " + XYZ.length);
                if (ValorTamanho < 3) {
                    if (ValorTamanho == 0) {
                        p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                        MapaDeTamanhos.put(i, ValorTamanho);
                    } else if (ValorTamanho == 1) {
                        p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 8, Interface.plot2D.mapData(XYZ));
                        MapaDeTamanhos.put(i, ValorTamanho);
                    } else if (ValorTamanho == 2) {
                        p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 16, Interface.plot2D.mapData(XYZ));
                        MapaDeTamanhos.put(i, ValorTamanho);
                    }
                } else {
                    ValorTamanho = 0;
                    p[i] = new ScatterPlot(tamanhosUnicos.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                    MapaDeTamanhos.put(i, ValorTamanho);
                }
                Interface.plot2D.addPlot(p[i]);
//    ValorTamanho++;
                System.out.println("i " + i);

            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));

            }
//    SetaExtremidadesDosEixos();

//   Fim do Plotador T

        } else if (indexCor == -1 && indexForma != -1 && indexTamanho == -1) {

//     Plotador F

            ScatterPlot[] p = new ScatterPlot[formasUnicas.size()];
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
//    TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            int ValorForma = 0;
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();

            for (int i = 0; i <= formasUnicas.size() - 1; i++) {
                Object[][] XYZ = new Object[Collections.frequency(formas, formasUnicas.get(i))][2];

                int mark = 0;
                for (int j = 0; j <= formas.size() - 1; j++) {
                    if (xl1.get(j) != null && yl1.get(j) != null && formas.get(j) != null && formas.get(j).equals(formasUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                        XYZ[mark][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                        XYZ[mark][1] = yl1.get(j);
//        XYZ[mark][0] = "\u200f" +xl1.get(j);  //Inserir o -1 para ver se tira o null
//        XYZ[mark][1] = "\u200f" +yl1.get(j);
                        mark++;
                    }

                }
                MapaDeObjetos.put(i, XYZ);
                QtdFormaPorcento[i] = mark;
                ValorForma = i;
                System.out.println("KKAKAAKKAKAKKA " + i + " QQQQQQQQQQQ " + XYZ.length);
                if (ValorForma < 4) {
                    if (ValorForma == 0) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                        p[i].setDotPattern(DOT_PATTERN);
                        MapaDeFormas.put(i, 0);
                    } else if (ValorForma == 1) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                        p[i].setDotPattern(CROSS_PATTERN);
                        MapaDeFormas.put(i, 1);
                    } else if (ValorForma == 2) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                        p[i].setDotPattern(PTRIANGLE_PATTERN);
                        MapaDeFormas.put(i, 2);
                    } else if (ValorForma == 3) {
                        p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                        p[i].setDotPattern(PSQUARE_PATTERN);
                        MapaDeFormas.put(i, 3);
                    }
                } else {
                    ValorForma = 0;
                    p[i] = new ScatterPlot(coresUnicas.get(i).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                    p[i].setDotPattern(DOT_PATTERN);
                    MapaDeFormas.put(i, 0);
                }
                Interface.plot2D.addPlot(p[i]);
//    ValorForma++;

            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));

            }
//    SetaExtremidadesDosEixos();

//   Fim do Plotador F

        } else if (indexCor == -1 && indexForma != -1 && indexTamanho != -1) {

//     Inicio do plotador TF

            ScatterPlot[] p = new ScatterPlot[formasUnicas.size() * tamanhosUnicos.size()];
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
//    TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            int ValorForma = 0;
            int ValorTamanho = 0;
            int plotar = 0;
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdt = 0;
            int mdf = 0;

            for (int f = 0; f <= formasUnicas.size() - 1; f++) {
                for (int t = 0; t <= tamanhosUnicos.size() - 1; t++) {
                    Object[][] XYZ;
                    int mark = 0;
                    int mark1 = 0;

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t))) { //ambos os meios para tirar o null estao gerando um error

                            mark++;
                        }

                    }
                    if (mark != 0) {

                        XYZ = new Object[mark][2];
                    } else {
                        XYZ = new Object[10][2];
                    }

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t))) { //ambos os meios para tirar o null estao gerando um error
                            XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                            XYZ[mark1][1] = yl1.get(j);
//        XYZ[mark1][0] = "\u200f" +xl1.get(j);  //Inserir o -1 para ver se tira o null
//    	XYZ[mark1][1] = "\u200f" +yl1.get(j);
                            mark1++;
                        }

                    }

                    if (mark != 0) {

                        MapaDeObjetos.put(f, XYZ);
                        ValorForma = f;
                        ValorTamanho = t;

                        if (ValorForma < 4) {
                            if (ValorForma == 0) {

                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 2) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else if (ValorForma == 1) {
                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(CROSS_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(CROSS_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 2) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(CROSS_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(CROSS_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else if (ValorForma == 2) {
                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(MTRIANGLE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == ValorForma) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(GTRIANGLE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else if (ValorForma == 3) {
                                if (ValorTamanho < 3) {
                                    if (ValorTamanho == 0) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(PSQUARE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 1) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(MSQUARE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    } else if (ValorTamanho == 2) {
                                        p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(GSQUARE_PATTERN);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                    }
                                } else {
                                    ValorTamanho = 0;
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(PSQUARE_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            }

                        } else {
                            ValorForma = 0;
                            if (ValorTamanho < 3) {
                                if (ValorTamanho == 0) {
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                } else if (ValorTamanho == 1) {
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 8, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                } else if (ValorTamanho == 2) {
                                    p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 16, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                }
                            } else {
                                ValorTamanho = 0;
                                p[plotar] = new ScatterPlot(formasUnicas.get(f).toString(), ModeloCores[0], 1, 4, Interface.plot2D.mapData(XYZ));
                                p[plotar].setDotPattern(DOT_PATTERN);
                                MapaDeFormas.put(mdf, ValorForma);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                            }
                        }
                        QtdFormaPorcento[ValorForma] += mark1;
                        QtdTamanhoPorcento[ValorTamanho] += mark1;
                        Interface.plot2D.addPlot(p[plotar]);
                        plotar++;
                        mdf++;
                        mdt++;

                    }


                }

            }
//    System.out.println("MDO "+MapaDeObjetos.size()+ " Interface.plot2D " + Interface.plot2D.getPlots().size());
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));

            }
//     SetaExtremidadesDosEixos();

//     Fim do plotador TF
        } else if (indexCor != -1 && indexForma == -1 && indexTamanho != -1) {
//     Inicio Plotador CT

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size() * tamanhosUnicos.size()];    //qualque coisa colocar o valor 1000
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
            int ValorTamanho = 0;
            int ValorCor = 0;
            int plotar = 0;
//    TotalCorPorcento = xl1.size();
            QtdCorPorcento = new int[coresUnicas.size()];
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            QtdPontosPorcento = new int[tamanhosUnicos.size() * coresUnicas.size()];
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdt = 0;
            int mdc = 0;


            for (int i = 0; i <= coresUnicas.size() - 1; i++) {



                for (int f = 0; f <= tamanhosUnicos.size() - 1; f++) {
                    Object[][] XYZ;
                    int mark = 0;
                    int mark1 = 0;

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && tamanhos.get(j).equals(tamanhosUnicos.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                            mark++;
                        }

                    }
                    if (mark != 0) {

                        XYZ = new Object[mark][2];
                    } else {
                        XYZ = new Object[10][2];
                    }



                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && tamanhos.get(j).equals(tamanhosUnicos.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                            XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                            XYZ[mark1][1] = yl1.get(j);
//        XYZ[mark1][0] = "\u200f" +xl1.get(j);  //Inserir o -1 para ver se tira o null
//    	XYZ[mark1][1] = "\u200f" +yl1.get(j);
                            mark1++;
                        }

                    }


//    System.out.println("IIIIIIIIIII "+ i + " FFFFFFFFFFFF " + f + " XYZZZZZZZZZZZZ " + XYZ.length + " xxxxx "+xl1.size());
                    if (mark != 0) {
//                        ValorCor = CoresUnicasIndex.get(i);
//                        ValorTamanho = TamanhosUnicosIndex.get(f);
                        ValorCor = i; // Estava Funcionado com esta linha e a próxima descomentada
                        ValorTamanho = f;
//           QtdPontosPorcento[i] += mark1;
                        QtdPontosPorcento[i] += XYZ.length;
//           int porcentagem = (QtdPontosPorcento[i]*100)/TotalPorcento;  
                        double porcentagem = ((double) (QtdCorPorcento[i] * 100)) / (double) (TotalCorPorcento - 1);

                        if (ValorCor < 8) {

                            if (ValorTamanho < 3) {
                                if (ValorTamanho == 0) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 1) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 2) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                }
                            } else {
                                ValorTamanho = 0;
                                p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                MapaDeObjetos.put(plotar, XYZ);
                                plotar++;
                            }
                        } else {
                            ValorCor = 0;
                            if (ValorTamanho < 3) {
                                if (ValorTamanho == 0) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 1) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                } else if (ValorTamanho == 2) {
                                    p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeTamanhos.put(mdt, ValorTamanho);
                                    MapaDeObjetos.put(plotar, XYZ);
                                    plotar++;
                                }
                            } else {
                                ValorTamanho = 0;
                                p[i] = new ScatterPlot(coresUnicas.get(ValorCor).toString() + " + " + NomeDosTamanhos[ValorTamanho] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                MapaDeObjetos.put(plotar, XYZ);
                                plotar++;
                            }
                        }
                        QtdTamanhoPorcento[ValorTamanho] += mark1;
                        QtdCorPorcento[ValorCor] += mark1;
                        mdc++;
                        mdt++;
//      plotar = i;
                        // Atencao aqui talves a forma de plotar talves nao esteja correta

//       Interface.plot2D.addPlot(p[plotar]);



//       Interface.plot2D.addPlot(p[plotar]);
//       ValorTamanho = f;
//       ValorCor = i;
                        Interface.plot2D.addPlot(p[i]);
//       Interface.plot2D.addPlot(p[i]);

                    }

//       plotar++;    
                }



            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
//        int porcentagem = (MapaDeObjetos.get(mdo).length*100)/xl1.size();  
                double porcentagem = ((double) (MapaDeObjetos.get(mdo).length * 100)) / (double) (xl1.size() - 1);
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));
                Interface.plot2D.changePlotName(mdo, coresUnicas.get(MapaDeCores.get(mdo)).toString() + " + " + NomeDosTamanhos[MapaDeTamanhos.get(mdo)] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + " % ");
                System.out.println("IIIIIIIIIII " + mdo + " XYZZZZZZZZZZZZ " + MapaDeObjetos.get(mdo).length + " xxxxx " + xl1.size());
            }

//     SetaExtremidadesDosEixos();
//     Fim do Plotador CT

        } else if (indexCor != -1 && indexForma != -1 && indexTamanho == -1) {

//    inicio do Plotador CF

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size() * formasUnicas.size() + 1];    //qualque coisa colocar o valor 1000
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
            int ValorForma = 0;
            int ValorCor = 0;
            int plotar = 0;
//    TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            QtdCorPorcento = new int[coresUnicas.size()];
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdc = 0;
            int mdf = 0;

            for (int i = 0; i <= coresUnicas.size() - 1; i++) {



                for (int f = 0; f <= formasUnicas.size() - 1; f++) {
                    Object[][] XYZ;
                    int mark = 0;
                    int mark1 = 0;

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                            mark++;
                        }

                    }
                    if (mark != 0) {

                        XYZ = new Object[mark][2];
                    } else {
                        XYZ = new Object[10][2];
                    }

                    for (int j = 0; j <= xl1.size() - 1; j++) {
                        if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                            XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                            XYZ[mark1][1] = yl1.get(j);
//        XYZ[mark1][0] = "\u200f" +xl1.get(j);  //Inserir o -1 para ver se tira o null
//    	XYZ[mark1][1] = "\u200f" +yl1.get(j);
                            mark1++;
                        }

                    }


                    System.out.println("IIIIIIIIIII " + i + " FFFFFFFFFFFF " + f + " XYZZZZZZZZZZZZ " + XYZ.length);
                    
                    
                    
                    if (mark != 0) {
//                        QtdCorPorcento[ValorCor] += mark1;
////       int porcentagem = (QtdCorPorcento[i]*100)/TotalPorcento;
                        double porcentagem = ((double) QtdCorPorcento[i] * 100) / (double) (TotalCorPorcento - 1);
                        ValorForma = f; //  Estava Funcionando que este código nao comentado
//                        ValorForma = FormasUnicasIndex.get(f);
//                        ValorCor = CoresUnicasIndex.get(i);
                        if (ValorCor < 8) {

                            if (ValorForma < 4) {

                                if (ValorForma == 0) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(DOT_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);

                                } else if (ValorForma == 1) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(CROSS_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);
                                } else if (ValorForma == 2) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);
                                } else if (ValorForma == 3) {
                                    p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                    p[plotar].setDotPattern(PSQUARE_PATTERN);
                                    MapaDeCores.put(mdc, ValorCor);
                                    MapaDeFormas.put(mdf, ValorForma);
                                    MapaDeObjetos.put(plotar, XYZ);
                                }
                            } else {
                                ValorForma = 0;
                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                p[plotar].setDotPattern(DOT_PATTERN);
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeFormas.put(mdf, ValorForma);
                                MapaDeObjetos.put(plotar, XYZ);
                            }
                        } else {
                            ValorCor = 0;
                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ)); //?
                            MapaDeCores.put(mdc, ValorCor);
                            MapaDeFormas.put(mdf, ValorForma);
                            MapaDeObjetos.put(plotar, XYZ);
                        }
                        Interface.plot2D.addPlot(p[plotar]);
                        plotar++;
                        mdc++;
                        mdf++;

                    }

//                    QtdFormaPorcento[ValorForma] += mark1;
                    ValorCor = i; // Estava Funcionando que este código nao comentado
//                    ValorCor = CoresUnicasIndex.get(i);

                }
                
//                ValorCor = CoresUnicasIndex.get(i);

            }
            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
//        int porcentagem = (MapaDeObjetos.get(mdo).length*100)/xl1.size(); 
                double porcentagem = ((double) MapaDeObjetos.get(mdo).length * 100) / (double) (xl1.size() - 1);
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));
                Interface.plot2D.changePlotName(mdo, coresUnicas.get(MapaDeCores.get(mdo)).toString() + " + " + NomeDasFormas[MapaDeFormas.get(mdo)] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + " % ");
                System.out.println("IIIIIIIIIII " + mdo + " XYZZZZZZZZZZZZ " + MapaDeObjetos.get(mdo).length + " xxxxx " + xl1.size());
            }
//     SetaExtremidadesDosEixos();
//     Fim do Plotador CF
        } else if (indexCor != -1 && indexForma != -1 && indexTamanho != -1) {

//  inicio do plotador CFT 

            ScatterPlot[] p = new ScatterPlot[coresUnicas.size() * formasUnicas.size() * tamanhosUnicos.size()];
            Interface.plot2D.resetMapData();
            Interface.plot2D.removeAllPlots();
            int ValorForma = 0;
            int ValorCor = 0;
            int ValorTamanho = 0;
            int plotar = 0;
//    TotalCorPorcento = xl1.size();
            QtdFormaPorcento = new int[formasUnicas.size()];
            QtdTamanhoPorcento = new int[tamanhosUnicos.size()];
            QtdCorPorcento = new int[coresUnicas.size()];
            HashMap<Integer, Object[][]> MapaDeObjetos = new HashMap<Integer, Object[][]>();
            int mdc = 0;
            int mdf = 0;
            int mdt = 0;

            for (int i = 0; i <= coresUnicas.size() - 1; i++) {



                for (int f = 0; f <= formasUnicas.size() - 1; f++) {
                    for (int t = 0; t <= tamanhosUnicos.size() - 1; t++) {
                        Object[][] XYZ;
                        int mark = 0;
                        int mark1 = 0;

                        for (int j = 0; j <= xl1.size() - 1; j++) {
                            if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error

                                mark++;
                            }

                        }
                        if (mark != 0) {
                            XYZ = new Object[mark][2];
                        } else {
                            XYZ = new Object[10][2];
                        }

                        for (int j = 0; j < xl1.size() - 1; j++) {
                            if (xl1.get(j) != null && yl1.get(j) != null && cores.get(j) != null && formas.get(j).equals(formasUnicas.get(f)) && tamanhos.get(j).equals(tamanhosUnicos.get(t)) && cores.get(j).equals(coresUnicas.get(i))) { //ambos os meios para tirar o null estao gerando um error
                                XYZ[mark1][0] = xl1.get(j);  //Inserir o -1 para ver se tira o null
                                XYZ[mark1][1] = yl1.get(j);
//        XYZ[mark1][0] = "\u200f" +xl1.get(j);  //Inserir o -1 para ver se tira o null
//    	XYZ[mark1][1] = "\u200f" +yl1.get(j);
                                mark1++;
                            }

                        }


                        System.out.println("IIIIIIIIIII " + i + " FFFFFFFFFFFF " + f + " XYZZZZZZZZZZZZ " + XYZ.length);
                        if (mark != 0) {
                            QtdCorPorcento[ValorCor] += mark1;
                            int porcentagem = (QtdCorPorcento[i] * 100) / TotalCorPorcento;
                            ValorForma = f;    // Estava Funcionando que este código nao comentado
                            ValorTamanho = t;  // Estava Funcionando que este código nao comentado
//                            ValorForma = FormasUnicasIndex.get(f);
//                            ValorTamanho = TamanhosUnicosIndex.get(t);

                            if (ValorCor < 8) {

                                if (ValorForma < 4) {
                                    if (ValorForma == 0) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(DOT_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(DOT_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(DOT_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else if (ValorForma == 1) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(CROSS_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(CROSS_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(CROSS_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(CROSS_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else if (ValorForma == 2) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(MTRIANGLE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(GTRIANGLE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(PTRIANGLE_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else if (ValorForma == 3) {
                                        if (ValorTamanho < 3) {
                                            if (ValorTamanho == 0) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(PSQUARE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 1) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(MSQUARE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            } else if (ValorTamanho == 2) {
                                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                                p[plotar].setDotPattern(GSQUARE_PATTERN);
                                                MapaDeCores.put(mdc, ValorCor);
                                                MapaDeFormas.put(mdf, ValorForma);
                                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                                MapaDeObjetos.put(plotar, XYZ);
                                            }
                                        } else {
                                            ValorTamanho = 0;
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(PSQUARE_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    }

                                } else {
                                    ValorForma = 0;
                                    if (ValorTamanho < 3) {
                                        if (ValorTamanho == 0) {
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        } else if (ValorTamanho == 1) {
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 8, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        } else if (ValorTamanho == 2) {
                                            p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 16, Interface.plot2D.mapData(XYZ));
                                            p[plotar].setDotPattern(DOT_PATTERN);
                                            MapaDeCores.put(mdc, ValorCor);
                                            MapaDeFormas.put(mdf, ValorForma);
                                            MapaDeTamanhos.put(mdt, ValorTamanho);
                                            MapaDeObjetos.put(plotar, XYZ);
                                        }
                                    } else {
                                        ValorTamanho = 0;
                                        p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ));
                                        p[plotar].setDotPattern(DOT_PATTERN);
                                        MapaDeCores.put(mdc, ValorCor);
                                        MapaDeFormas.put(mdf, ValorForma);
                                        MapaDeTamanhos.put(mdt, ValorTamanho);
                                        MapaDeObjetos.put(plotar, XYZ);
                                    }
                                }
                            } else {
                                ValorCor = 0;
                                p[plotar] = new ScatterPlot(coresUnicas.get(i).toString() + " " + porcentagem + "%", ModeloCores[ValorCor], 1, 4, Interface.plot2D.mapData(XYZ)); //?
//            MapaDeCores.put(plotar, ValorCor);
                                MapaDeCores.put(mdc, ValorCor);
                                MapaDeFormas.put(mdf, ValorForma);
                                MapaDeTamanhos.put(mdt, ValorTamanho);
                                MapaDeObjetos.put(plotar, XYZ);
                            }
                            Interface.plot2D.addPlot(p[plotar]);
                            plotar++;
                            mdc++;
                            mdf++;
                            mdt++;
                        }
                        QtdCorPorcento[ValorCor] += mark1;
                        QtdFormaPorcento[ValorForma] += mark1;
                        QtdTamanhoPorcento[ValorTamanho] += mark1;

                        ValorCor = i; // Estava Funcionando que este código nao comentado
//                        ValorCor = CoresUnicasIndex.get(i);
                    }
                }
            }

            for (int mdo = 0; mdo < MapaDeObjetos.size(); mdo++) {
//        int porcentagem = (MapaDeObjetos.get(mdo).length*100)/xl1.size();  
                double porcentagem = ((double) MapaDeObjetos.get(mdo).length * 100) / (double) (xl1.size() - 1);
                Interface.plot2D.changePlotData(mdo, Interface.plot2D.mapData(MapaDeObjetos.get(mdo)));
                Interface.plot2D.changePlotName(mdo, coresUnicas.get(MapaDeCores.get(mdo)).toString() + " + " + NomeDasFormas[MapaDeFormas.get(mdo)] + " + " + NomeDosTamanhos[MapaDeTamanhos.get(mdo)] + " ➞ " + new DecimalFormat("##.##").format(porcentagem) + " % ");
                System.out.println("IIIIIIIIIII " + mdo + " XYZZZZZZZZZZZZ " + MapaDeObjetos.get(mdo).length + " xxxxx " + xl1.size());
            }
//    SetaExtremidadesDosEixos();
//  Fim do Antigo plotador CFT

        }




        if (!FiltrarFormaCategorico.isEmpty() && !FiltrarFormaCategorico.equals(null) && FiltrarFormaCategorico != null) {
            for (int p = 0; p <= Interface.plot2D.getPlots().size() - 1; p++) {
                if (FiltrarFormaCategorico.containsValue(MapaDeFormas.get(p))) {
                    Interface.plot2D.getPlot(p).setVisible(false);
                    TotalFormaPorcento -= Interface.plot2D.getPlot(p).getData().length;
                    System.out.println("FOFOFOFFOFOFOFOFOFO " + p + "  " + (Interface.plot2D.getPlot(p).getData().length));
                    System.out.println("Total F " + TotalFormaPorcento);
                }

            }

        }


        if (!FiltrarCorCategorico.isEmpty() && !FiltrarCorCategorico.equals(null) && FiltrarCorCategorico != null) {
            for (int p = 0; p <= Interface.plot2D.getPlots().size() - 1; p++) {
                if (FiltrarCorCategorico.containsValue(MapaDeCores.get(p))) {
                    Interface.plot2D.getPlot(p).setVisible(false);
                    TotalCorPorcento -= Interface.plot2D.getPlot(p).getData().length;
                    System.out.println("COROCOROCOROCOROCOR " + p + "  " + (Interface.plot2D.getPlot(p).getData().length));
                    System.out.println("Total C " + TotalCorPorcento);
                }
            }

        }




//                      if (!FiltrarCorCategorico.isEmpty() && !FiltrarCorCategorico.equals(null) && FiltrarCorCategorico != null) {
//                          System.out.println("Estou Vazio");
//                           for (int i = 0; i <= FiltrarCorCategorico.size()-1; i++) {
//                                    for (int p = 0; p <= Interface.plot2D.getPlots().size()-1; p++) {
//                                        if (FiltrarCorCategorico.containsValue(MapaDeCores.get(p))) {
//                                        Interface.plot2D.getPlot(p).setVisible(false);
//                                        TotalCorPorcento -= Interface.plot2D.getPlot(p).getData().length -1;
//                                        System.out.println("COROCOROCOROCOROCOR "+ p + "  " + (Interface.plot2D.getPlot(p).getData().length-1));
//                                        }
//                                    }
//                                    
//                                }
//                        }

        if (!FiltrarTamanhoCategorico.isEmpty() && !FiltrarTamanhoCategorico.equals(null) && FiltrarTamanhoCategorico != null) {
            for (int p = 0; p <= Interface.plot2D.getPlots().size() - 1; p++) {
                if (FiltrarTamanhoCategorico.containsValue(MapaDeTamanhos.get(p))) {
                    Interface.plot2D.getPlot(p).setVisible(false);
                    TotalTamanhoPorcento -= Interface.plot2D.getPlot(p).getData().length;
                    System.out.println("TATATAATATATATATATTAAT " + p + "  " + (Interface.plot2D.getPlot(p).getData().length));
                    System.out.println("Total T " + TotalTamanhoPorcento);
                }
            }

        }



        ArrayList<String> TempCor = new ArrayList<String>(FiltrarCorCategorico.keySet());
        ArrayList<String> TempForma = new ArrayList<String>(FiltrarFormaCategorico.keySet());
        ArrayList<String> TempTamanho = new ArrayList<String>(FiltrarTamanhoCategorico.keySet());

//                      System.out.println("CCCCCCCCCCCCCCCCCCCCCC  "+cores.size());
//                      System.out.println("FFFFFFFFFFFFFFFFFFFFFF  "+formas.size());
//                      System.out.println("TTTTTTTTTTTTTTTTTTTTTT  "+tamanhos.size());


        if (!TempCor.isEmpty() && !TempCor.equals(null) && TempCor != null) {
            for (int p = 0; p <= TempCor.size() - 1; p++) {
                System.out.println("FFFFFFFFFF " + TempCor.get(p));
                System.out.println("BCGDBCGDCBGDCBGDCBGDCB " + TempCor.get(p).substring(0, TempCor.get(p).indexOf(" ")));
                while (true) {
                    if (cores.contains(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" ")))) {
                        System.out.println("IIIIIININININININININI " + cores.indexOf(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" "))));

                        formas.remove(cores.indexOf(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" "))));
                        tamanhos.remove(cores.indexOf(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" "))));
                        cores.remove(TempCor.get(p).substring(0, TempCor.get(p).indexOf(" ")));

                    } else {
                        break;
                    }
                }
            }

        }
        if (!TempForma.isEmpty() && !TempForma.equals(null) && TempForma != null) {
            for (int p = 0; p <= TempForma.size() - 1; p++) {
                while (true) {
                    if (formas.contains(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" ")))) {
                        tamanhos.remove(formas.indexOf(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" "))));
                        cores.remove(formas.indexOf(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" "))));
                        formas.remove(TempForma.get(p).substring(0, TempForma.get(p).indexOf(" ")));
                    } else {
                        break;
                    }
                }
            }

        }
        if (!TempTamanho.isEmpty() && !TempTamanho.equals(null) && TempTamanho != null) {
            for (int p = 0; p <= TempTamanho.size() - 1; p++) {
                while (true) {
                    if (tamanhos.contains(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" ")))) {
                        formas.remove(tamanhos.indexOf(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" "))));
                        cores.remove(tamanhos.indexOf(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" "))));
                        tamanhos.remove(TempTamanho.get(p).substring(0, TempTamanho.get(p).indexOf(" ")));
                    } else {
                        break;
                    }
                }
            }

        }

//                      System.out.println("CCCCCCCCCCCCCCCCCCCCCCDDD  "+cores.size());
//                                        System.out.println("FFFFFFFFFFFFFFFFFFFFFFDDD  "+formas.size());
//                                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTDDD  "+tamanhos.size());


//                     for (int p = 0; p <= Interface.plot2D.getPlots().size()-1; p++) {
//                         if (Interface.plot2D.getPlot(p).getVisible()) {
//                             Interface.plot2D.getPlot(p).getData().length;
//                         }
//                     }

//                     Fim do Filtro de Conjuntos de Plots   

//         SetaExtremidadesDosEixos();


        Interface.plot2D.setAxisLabel(0, eixox);
        Interface.plot2D.setAxisLabel(1, eixoy);

        Interface.plot2D.repaint();

//        if (Interface.menu_Detalhes.isSelected()) {
//         try{
//                            Interface.LimparPainel(Interface.desktopInteragir);
//                            Interface.BtnsDetails();
//                            Main.Interface.setContentPane(((IMATVI.Interface)Main.Interface).PainelIFC());
//                           } catch (GrammarException ex) {
//                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (EngineStateError ex) {
//                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (IOException ex) {
//                            Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//        }
    }

    public static boolean[][] stringToPattern(String empty, String... c) { // Converte os padroes das variaveis DOT_triangule/Square_pattern em formas geometricas (Original da classe, AbstractDrawer)
        boolean[][] p = new boolean[c.length][];
        for (int i = 0; i < p.length; i++) {
            p[i] = stringToPattern(empty, c[i]);
        }
        return p;
    }

    public static boolean[] stringToPattern(String empty, String c) {
        boolean[] p = new boolean[c.length()];
        for (int i = 0; i < p.length; i++) {
            p[i] = !(c.substring(i, i + 1).equals(empty));
        }
        return p;
    }

    public static void LegendLogicPanel() {
//     if ((indexForma != -1)&&(indexTamanho != -1)) {
//                              
//                LegendaForma = new String();
//                LegendaTamanho = new String();
//                LegendaForma = "<html><h2 align ='center'>Formas ("+ LabelForma +") </h2>";
//                LegendaTamanho = "<html><h2 align ='center'>Tamanho ("+LabelTamanho+") </h2>";
//                String[] s = Main.att.GetUniqueValues(indexForma);
//                String[] s2 = Main.att.GetUniqueValues(indexTamanho);
//                              for(int i = 0; i <= s.length-1;i++){
//                                  int porcentagem = (infoVisModule.QtdFormaPorcento[i]*100)/infoVisModule.TotalCorPorcento;
//                                  LegendaForma = LegendaForma+"<br align = 'center'>"+ s[i]+" - (" + infoVisModule.NomeDasFormas[i] + " "+ porcentagem + "%)  </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                              for(int i = 0; i <= s2.length-1;i++){
//                                  int porcentagem = (infoVisModule.QtdTamanhoPorcento[i]*100)/infoVisModule.TotalCorPorcento;
//                                  LegendaTamanho = LegendaTamanho+"<br align = 'center'>"+ s2[i]+" - ("+ infoVisModule.NomeDosTamanhos[i] + " "+ porcentagem + "%) </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                              
//                LblLegendaForma.setSize(ScreenInternalW,ScreenInternalH/2);
//                LblLegendaForma.setText(LegendaForma);
//                LblLegendaForma.setLocation(WidthScreen - WidthScreen/10-LblLegendaForma.getPreferredSize().width/2,ButtonY); //Caso AtualEscala der problema remover o ButtonX/100*2
//		
//                LblLegendaTamanho.setSize(ScreenInternalW,ScreenInternalH/2);
//                LblLegendaTamanho.setText(LegendaTamanho);
//		LblLegendaTamanho.setLocation(WidthScreen - WidthScreen/10-LblLegendaTamanho.getPreferredSize().width/2,ScreenInternalH/2-ButtonY);
//		
//		
//		desktopInteragir.add(LblLegendaForma);
//		desktopInteragir.add(LblLegendaTamanho);
////		desktopInteragir.add(plot3D);
//                if (IsPlot3D) {
//                desktopInteragir.add(plot3D);
//                }else
//                desktopInteragir.add(plot2D);
//                
//                         }else if ((indexForma != -1)&&(indexTamanho == -1)) {
//                LegendaForma = new String();
//                LegendaForma = "<html><h2 align ='center'>Formas ("+ LabelForma +") </h2>";
//                String[] s = Main.att.GetUniqueValues(indexForma);
//                              for(int i = 0; i <= s.length-1;i++){
//                                  int porcentagem = (infoVisModule.QtdFormaPorcento[i]*100)/infoVisModule.TotalCorPorcento;
//                                  LegendaForma = LegendaForma+"<br align = 'center'>"+ s[i]+" - (" + infoVisModule.NomeDasFormas[i] + " "+ porcentagem + "%)  </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                             
//                              
//                LblLegendaForma.setSize(ScreenInternalW,ScreenInternalH/2);
//                LblLegendaForma.setText(LegendaForma);
//                LblLegendaForma.setLocation(WidthScreen - WidthScreen/10-LblLegendaForma.getPreferredSize().width/2,ButtonY); //Caso AtualEscala der problema remover o ButtonX/100*2
//		
//		desktopInteragir.add(LblLegendaForma);
////		desktopInteragir.add(plot3D);
//                if (IsPlot3D) {
//                desktopInteragir.add(plot3D);
//                }else
//                desktopInteragir.add(plot2D);
//                             
//                         } else if ((indexForma == -1)&&(indexTamanho != -1)) {
//                LegendaTamanho = new String();
//                LegendaTamanho = "<html><h2 align ='center'>Tamanhos ("+ LabelTamanho +") </h2>";
//                String[] s = Main.att.GetUniqueValues(indexTamanho);
//               
//                              for(int i = 0; i <= s.length-1;i++){
//                                   int porcentagem = (infoVisModule.QtdTamanhoPorcento[i]*100)/infoVisModule.TotalCorPorcento;
//                                  LegendaTamanho = LegendaTamanho+"<br align = 'center'>"+ s[i]+" - ("+ infoVisModule.NomeDosTamanhos[i] + " "+ porcentagem + "%) </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                             
//                              
//                LblLegendaTamanho.setSize(ScreenInternalW,ScreenInternalH/2);
//                LblLegendaTamanho.setText(LegendaTamanho);
//		LblLegendaTamanho.setLocation(WidthScreen - WidthScreen/10-LblLegendaTamanho.getPreferredSize().width/2,ScreenInternalH/2-ButtonY);
//		
//		desktopInteragir.add(LblLegendaTamanho);
////		desktopInteragir.add(plot3D);
//                if (IsPlot3D) {
//                desktopInteragir.add(plot3D);
//                }else
//                desktopInteragir.add(plot2D);
//                         }         
    }

    public static void PlotBaseX(int x, String eixox) throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        IsPlot3D = false;
        indexX = x;
        LabelX = " X- " +eixox;
        eixoXSetado = true;
        AllAxisChecked();
    }

    public static void PlotBaseY(int y, String eixoy) throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        IsPlot3D = false;
        indexY = y;
        LabelY = " Y- " +eixoy;
        eixoYSetado = true;
        AllAxisChecked();
    }

    public static void PlotBaseZ(int z, String eixoz) throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        IsPlot3D = false;
        indexZ = z;
        LabelZ = " Z- " +eixoz;
        eixoZSetado = true;
        AllAxisChecked();
    }

    public static void PlotCor(int cor, String eixocor) throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        indexCor = cor;
        LabelCor = eixocor;
        eixoCorSetado = true;
        AllAxisChecked();
    }

    public static void PlotForma(int forma, String eixoforma) throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        indexForma = forma;
        LabelForma = eixoforma;
        eixoFormaSetado = true;
        AllAxisChecked();
    }

    public static void PlotTamanho(int tamanho, String eixotamanho) throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        indexTamanho = tamanho;
        LabelTamanho = eixotamanho;
        eixoTamanhoSetado = true;
        AllAxisChecked();
    }

    public static void AllAxisChecked() throws NumberFormatException, FileNotFoundException, IOException, GrammarException {


        if ((indexX != -1) && (indexY != -1) && (indexZ != -1)) {
            IsPlot3D = true;
        }
        if (IsPlot3D) {


            if ((indexX != -1) && (indexY != -1) && (indexZ != -1)) {
                plot3DVis(indexX, indexY, indexZ, LabelX, LabelY, LabelZ);
                eixoXSetado = false;
                eixoYSetado = false;
                eixoZSetado = false;
            }
        } else {
            if ((indexX != -1) && (indexY != -1)) {
                plot2DVis(indexX, indexY, LabelX, LabelY);
                eixoXSetado = false;
                eixoYSetado = false;
            } else if ((indexX != -1) && (indexZ != -1)) {
                plot2DVis(indexX, indexZ, LabelX, LabelZ);
                eixoXSetado = false;
                eixoZSetado = false;
            } else if ((indexY != -1) && (indexZ != -1)) {
                plot2DVis(indexY, indexZ, LabelY, LabelZ);
                eixoYSetado = false;
                eixoZSetado = false;
            }
        }
        
        try {
                Interface.SetPane(Main.Interface);
                } catch (PropertyVetoException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GrammarException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EngineStateError ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       
//    }
//        
//        Interface.LimparPainel(Interface.desktopCarregar);
//        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());              
//        
//               if (Interface.menu_Detalhes.isSelected()) {
//                    if (!Interface.menu_legenda.isSelected()) {
//                        Interface.menu_legenda.doClick();
//                    } 
////                  else {
////                        
////                    }
////                    try {
//                        IQuad = 0;
//                        Interface.BtnsDetails();
//                        (new ThreadDsd()).start();
////                    } catch (GrammarException ex) {
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (EngineStateError ex) {
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (IOException ex) {
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                        (new ThreadDsd()).start();
//                } else {
//                    if (Interface.menu_legenda.isSelected()) {
//                        Interface.menu_legenda.doClick();
//
//                    }
////                    try {
////                        Interface.LimparPainel(Interface.desktopCarregar);
//                        Interface.BtnsIFC();
////                        Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
////                    } catch (GrammarException ex) {
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (EngineStateError ex) {
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (IOException ex) {
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                        Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
////                    }
//                }
        
//        try {
////                    Reconhecedor.GramIFC();
//                    Interface.BtnsIFC();
//                    Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
//                    Interface.painelIFC.setSelected(true);
//                    Interface.desktopIFC.setComponentZOrder(Interface.painelIFC, Interface.desktopIFC.getComponentCount() - 2);
//                } catch (GrammarException e) {
//                    e.printStackTrace();
//                } catch (EngineStateError e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (PropertyVetoException ex) {
//                    Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
        }
    

    public static int GetIndexX() {
        return indexX;
    }

    public static int GetIndexY() {
        return indexY;
    }

    public static int GetIndexZ() {
        return indexZ;
    }

    public static int GetIndexColor() {
        return indexCor;
    }

    public static int GetIndexShape() {
        return indexForma;
    }

    public static int GetIndexSize() {
        return indexTamanho;
    }

    public static void SetIndexX(int x) {
        indexX = x;
    }

    public static void SetIndexY(int y) {
        indexY = y;
    }

    public static void SetIndexZ(int z) {
        indexZ = z;
    }

    public static void SetIndexColor(int ic) {
        indexCor = ic;
    }

    public static void SetIndexShape(int is) {
        indexForma = is;
    }

    public static void SetIndexSize(int iz) {
        indexTamanho = iz;
    }

    public static String GetNameX() {
        return LabelX;
    }

    public static String GetNameY() {
        return LabelY;
    }

    public static String GetNameZ() {
        return LabelZ;
    }

    public static String GetColorName() {
        return LabelCor;
    }

    public static String GetShapeName() {
        return LabelForma;
    }

    public static String GetSizeName() {
        return LabelTamanho;
    }

    public static double[] GetMinMaxValueX() { // O primeiro Valor e o menor e o segundo.
        boolean TemNulo = false;
//            String[] VetorS = Main.matrix[GetIndexX()];
//		double[] Vetor = new double[VetorS.length];
        double[] Vetor = new double[xl1.size()];
//		for(int i = 0; i<=VetorS.length-1;i++){
        for (int i = 0; i <= xl1.size() - 1; i++) {
//                        if(VetorS[i]!=null){
            if (xl1.get(i) != null) {
//			Vetor[i] = Double.parseDouble(VetorS[i]);
                Vetor[i] = Double.parseDouble(xl1.get(i));
            } else {
                TemNulo = true;
            }
        }
        double min, max;
        Arrays.sort(Vetor);
        if (TemNulo) {
            min = Vetor[1];
        } else {
            min = Vetor[0];
        }
        max = Vetor[Vetor.length - 1];
        double[] retorno = new double[2];
        retorno[0] = min;
        retorno[1] = max;
        return retorno;
    }

    public static double[] GetMinMaxValueY() { // O primeiro Valor e o menor e o segundo.
        boolean TemNulo = false;
//		String[] VetorS = Main.matrix[GetIndexY()];
//		double[] Vetor = new double[VetorS.length];
        double[] Vetor = new double[yl1.size()];
//		for(int i = 0; i<=VetorS.length-1;i++){
        for (int i = 0; i <= yl1.size() - 1; i++) {
//			Vetor[i] = Double.parseDouble(VetorS[i]);
//                    if (VetorS[i] != null) {
            if (yl1.get(i) != null) {
//                    Vetor[i] = Double.parseDouble(VetorS[i]);
                Vetor[i] = Double.parseDouble(yl1.get(i));
            } else {
                TemNulo = true;
            }
        }
        double min, max;
        Arrays.sort(Vetor);
        if (TemNulo) {
            min = Vetor[1];
        } else {
            min = Vetor[0];
        }
        max = Vetor[Vetor.length - 1];
        double[] retorno = new double[2];
        retorno[0] = min;
        retorno[1] = max;
        return retorno;
    }

    public static double[] GetMinMaxValueZ() { // O primeiro Valor e o menor e o segundo.
        boolean TemNulo = false;
        double[] Vetor = new double[zl1.size()];
        for (int i = 0; i <= zl1.size() - 1; i++) {
            if (zl1.get(i) != null) {
                Vetor[i] = Double.parseDouble(zl1.get(i));
            } else {
                TemNulo = true;
            }
        }
        double min, max;
        Arrays.sort(Vetor);
        if (TemNulo) {
            min = Vetor[1];
        } else {
            min = Vetor[0];
        }
        max = Vetor[Vetor.length - 1];
        double[] retorno = new double[2];
        retorno[0] = min;
        retorno[1] = max;
        return retorno;
//                boolean TemNulo = false;	
//                String[] VetorS = Main.matrix[GetIndexZ()];
//		double[] Vetor = new double[VetorS.length-1];
//		for(int i = 0; i<=VetorS.length-1;i++){
//			if(VetorS[i]!=null){
//			Vetor[i] = Double.parseDouble(VetorS[i]);
//			}else
//                        TemNulo = true;
//                }
//		double min,max;
//		Arrays.sort(Vetor);
//		if (TemNulo) {
//                min = Vetor[1];
//            }else
//		min = Vetor[0];
//		max = Vetor[Vetor.length-1];
//		double[] retorno = new double[2];
//		retorno[0] = min; 
//		retorno[1] = max;
//		    return retorno;
    }

    public static void SetaEixosLabels() throws FileNotFoundException, IOException {
        double dx;
        double dy;
        double dz;
        double[] origin = new double[3];
        double[] end = new double[3];

        origin[0] = 0.0;
        origin[1] = 0.0;
        origin[2] = 0.0;
        end[0] = 22.0;
        end[1] = 1.0;
        end[2] = 1.0;
        HashMap<String, Double> MapaX = new HashMap<String, Double>();
        //Mapa de Formas
        HashMap<String, Double> MapaY = new HashMap<String, Double>();
        //Mapa de Tamanhos
        HashMap<String, Double> MapaZ = new HashMap<String, Double>();

        for (int xi = 0; xi < Main.att.GetUniqueValues(GetIndexX()).length; xi++) {
            dx = (double) xi;
            MapaX.put(Main.att.GetUniqueValues(GetIndexX())[xi], dx);
        }
        for (int yi = 0; yi < Main.att.GetUniqueValues(GetIndexY()).length; yi++) {
            dy = (double) yi;
            MapaY.put(Main.att.GetUniqueValues(GetIndexY())[yi], dy);
        }
        for (int zi = 0; zi < Main.att.GetUniqueValues(GetIndexZ()).length; zi++) {
            dz = (double) zi;
            MapaZ.put(Main.att.GetUniqueValues(GetIndexZ())[zi], dz);
        }

//            plot3D.getAxis(0).setStringMap(MapaX);
//            plot3D.getAxis(1).setStringMap(MapaZ);
//            plot3D.getAxis(2).setStringMap(MapaY);
//            plot3D.getAxis(0).setOrigin(origin);
//            plot3D.getAxis(0).setEnd(end);
//            plot3D.getAxis(1).setOrigin(origin);
//            plot3D.getAxis(1).setEnd(end);
//            plot3D.getAxis(2).setOrigin(origin);
//            plot3D.getAxis(2).setEnd(end);
    }

//        public static void SetaExtremidadesDosEixos() throws FileNotFoundException, IOException{
////            plot3D.setFixedBounds(0,0,Main.att.GetUniqueValues(infoVisModule.indexX).length-1); // Para o Eixo X
////            plot3D.setFixedBounds(1,0,Main.att.GetUniqueValues(infoVisModule.indexY).length-1); // Para o Eixo Y
////            plot3D.setFixedBounds(2,0,Main.att.GetUniqueValues(infoVisModule.indexZ).length-1); // Para o Eixo Z
//            
//        }
//    void Legend(String LegendaForma, String LegendaTamanho) throws FileNotFoundException, IOException {
//        if ((indexForma != -1)&&(indexTamanho != -1)) {
//                              
//                
//                LegendaForma = "<html><h2 align ='center'>Formas ("+ LabelForma +") </h2>";
//                LegendaTamanho = "<html><h2 align ='center'>Tamanho ("+LabelTamanho+") </h2>";
//                String[] s = Main.att.GetUniqueValues(indexForma);
//                String[] s2 = Main.att.GetUniqueValues(indexTamanho);
//                              for(int i = 0; i <= s.length-1;i++){
//                                  int porcentagem = (QtdFormaPorcento[i]*100)/TotalCorPorcento;
//                                  LegendaForma = LegendaForma+"<br align = 'center'>"+ s[i]+" - (" + NomeDasFormas[i] + " "+ porcentagem + "%)  </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                              for(int i = 0; i <= s2.length-1;i++){
//                                  int porcentagem = (QtdTamanhoPorcento[i]*100)/TotalCorPorcento;
//                                  LegendaTamanho = LegendaTamanho+"<br align = 'center'>"+ s2[i]+" - ("+ NomeDosTamanhos[i] + " "+ porcentagem + "%) </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                              
//                Interface.LblLegendaForma.setText(LegendaForma);
//                Interface.LblLegendaTamanho.setText(LegendaTamanho);
//                
//                
//		
//		Interface.desktopInteragir.add(Interface.LblLegendaForma);
//		Interface.desktopInteragir.add(Interface.LblLegendaTamanho);
//                if (IsPlot3D) {
//                Interface.desktopInteragir.add(Interface.plot3D);
//                }else
//                Interface.desktopInteragir.add(Interface.plot2D);
//                
//                         }else if ((indexForma != -1)&&(indexTamanho == -1)) {
//                LegendaForma = new String();
//                LegendaForma = "<html><h2 align ='center'>Formas ("+ LabelForma +") </h2>";
//                String[] s = Main.att.GetUniqueValues(indexForma);
//                              for(int i = 0; i <= s.length-1;i++){
//                                  int porcentagem = (QtdFormaPorcento[i]*100)/TotalCorPorcento;
//                                  LegendaForma = LegendaForma+"<br align = 'center'>"+ s[i]+" - (" + NomeDasFormas[i] + " "+ porcentagem + "%)  </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                             
//                              
////                LblLegendaForma.setSize(ScreenInternalW,ScreenInternalH/2);
//                Interface.LblLegendaForma.setText(LegendaForma);
////                LblLegendaForma.setLocation(WidthScreen - WidthScreen/10-LblLegendaForma.getPreferredSize().width/2,ButtonY); //Caso AtualEscala der problema remover o ButtonX/100*2
//		
//		Interface.desktopInteragir.add(Interface.LblLegendaForma);
////		desktopInteragir.add(plot3D);
//                if (IsPlot3D) {
//                Interface.desktopInteragir.add(Interface.plot3D);
//                }else
//                Interface.desktopInteragir.add(Interface.plot2D);
//                             
//                         } else if ((indexForma == -1)&&(indexTamanho != -1)) {
//                LegendaTamanho = new String();
//                LegendaTamanho = "<html><h2 align ='center'>Tamanhos ("+ LabelTamanho +") </h2>";
//                String[] s = Main.att.GetUniqueValues(indexTamanho);
//               
//                              for(int i = 0; i <= s.length-1;i++){
//                                   int porcentagem = (QtdTamanhoPorcento[i]*100)/TotalCorPorcento;
//                                  LegendaTamanho = LegendaTamanho+"<br align = 'center'>"+ s[i]+" - ("+ NomeDosTamanhos[i] + " "+ porcentagem + "%) </br>";
////			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+" (" + NomeDasFormas[i] + ") " );
//                              }
//                             
//                              
////                LblLegendaTamanho.setSize(ScreenInternalW,ScreenInternalH/2);
//                Interface.LblLegendaTamanho.setText(LegendaTamanho);
////		LblLegendaTamanho.setLocation(WidthScreen - WidthScreen/10-LblLegendaTamanho.getPreferredSize().width/2,ScreenInternalH/2-ButtonY);
//		
//		Interface.desktopInteragir.add(Interface.LblLegendaTamanho);
////		desktopInteragir.add(plot3D);
//                if (IsPlot3D) {
//                Interface.desktopInteragir.add(Interface.plot3D);
//                }else
//                Interface.desktopInteragir.add(Interface.plot2D);
//                         }
//        
//        LegendSize = LegendaTamanho;
//        LegendShape = LegendaForma;
//		
//    }
    public void DetailsOnDemand() throws NumberFormatException, FileNotFoundException, IOException, GrammarException {
        Detalhes = Interface.menu_Detalhes.isSelected();
        //Detalhes usando os quadrantes do Simon
        Interface.plot2D.plotCanvas.allowNote = true;
        Interface.plot2D.plotCanvas.allowNoteCoord = true;
        Interface.plot3D.plotCanvas.allowNote = true;
        Interface.plot3D.plotCanvas.allowNoteCoord = true;
        
//        System.out.println("0741623795461249856891725489047218947982347890123 - " + Vis3D.QtdPontosVisuais);
//        Interface.BtnsDetails();
//                    Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
        
        
        if (QtdPontosVisuais > 9) {


            if (IsPlot3D) {
                draw = new AWTDrawer3D(Interface.plot3D.plotCanvas);
                draw.initGraphics((Graphics2D) Interface.plot3D.plotCanvas.getGraphics());
                draw.canvas = Interface.plot3D.plotCanvas;
                draw.canvas.initDrawer();

                double sx;
                double sxi = 0;
                double sy;  // fim do quadrante y
                double syi = 0; // inicio do quadrante y
                double sz; // inicio do quadrante y
                double szi = 0; // inicio do quadrante y




                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }




                System.out.println(" szi " + szi);
                System.out.println(" sz " + sz);
                System.out.println(" syi " + syi);
                System.out.println(" sy " + sy);



                double[][] pc = new double[16][3];
                double[][] pc2 = new double[16][3];
                double[][] pc3 = new double[16][3];
                double[][] pc4 = new double[16][3];
                double[][] pc5 = new double[16][3];
                double[][] pc6 = new double[16][3];
                double[][] pc7 = new double[16][3];
                double[][] pc8 = new double[16][3];


                pc[0][0] = sxi;
                pc[0][1] = szi;
                pc[0][2] = (sy - syi) / 2 + syi;
                pc[1][0] = (sx - sxi) / 2 + sxi;
                pc[1][1] = szi;
                pc[1][2] = (sy - syi) / 2 + syi;
                pc[2][0] = (sx - sxi) / 2 + sxi;
                pc[2][1] = szi;
                pc[2][2] = sy;
                pc[3][0] = sxi;
                pc[3][1] = szi;
                pc[3][2] = sy;
                pc[4][0] = pc[0][0];
                pc[4][1] = pc[0][1];
                pc[4][2] = pc[0][2];
                pc[5][0] = pc[3][0];
                pc[5][1] = pc[3][1];
                pc[5][2] = pc[3][2];
                pc[6][0] = sxi;
                pc[6][1] = (sz - szi) / 2 + szi;
                pc[6][2] = sy;
                pc[7][0] = (sx - sxi) / 2 + sxi;
                pc[7][1] = (sz - szi) / 2 + szi;
                pc[7][2] = sy;
                pc[8][0] = pc[2][0];
                pc[8][1] = pc[2][1];
                pc[8][2] = pc[2][2];
                pc[9][0] = pc[7][0];
                pc[9][1] = pc[7][1];
                pc[9][2] = pc[7][2];
                pc[10][0] = (sx - sxi) / 2 + sxi;
                pc[10][1] = (sz - szi) / 2 + szi;
                pc[10][2] = (sy - syi) / 2 + syi;
                pc[11][0] = pc[1][0];
                pc[11][1] = pc[1][1];
                pc[11][2] = pc[1][2];
                pc[12][0] = pc[10][0];
                pc[12][1] = pc[10][1];
                pc[12][2] = pc[10][2];
                pc[13][0] = sxi;
                pc[13][1] = (sz - szi) / 2 + szi;
                pc[13][2] = (sy - syi) / 2 + syi;
                pc[14][0] = pc[6][0];
                pc[14][1] = pc[6][1];
                pc[14][2] = pc[6][2];
                pc[15][0] = pc[13][0];
                pc[15][1] = pc[13][1];
                pc[15][2] = pc[13][2];

//    pc[0][0] = sxi;
//    pc[0][1] = (sy - syi)/2 + syi;
//    pc[0][2] = szi;
//    pc[1][0] = (sx - sxi)/2 + sxi;
//    pc[1][1] = (sy - syi)/2 + syi;
//    pc[1][2] = szi;
//    pc[2][0] = (sx - sxi)/2 + sxi;
//    pc[2][1] = sy;
//    pc[2][2] = szi;
//    pc[3][0] = sxi;
//    pc[3][1] = sy;
//    pc[3][2] = szi;
//    pc[4][0] = pc[0][0];
//    pc[4][1] = pc[0][1];
//    pc[4][2] = pc[0][2];
//    pc[5][0] = pc[3][0];
//    pc[5][1] = pc[3][1];
//    pc[5][2] = pc[3][2];
//    pc[6][0] = sxi;
//    pc[6][1] = sy;
//    pc[6][2] = (sz - szi)/2 + szi;
//    pc[7][0] = (sx - sxi)/2 + sxi;
//    pc[7][1] = sy;
//    pc[7][2] = (sz - szi)/2 + szi;
//    pc[8][0] = pc[2][0];
//    pc[8][1] = pc[2][1];
//    pc[8][2] = pc[2][2];
//    pc[9][0] = pc[7][0];
//    pc[9][1] = pc[7][1];
//    pc[9][2] = pc[7][2];
//    pc[10][0] = (sx - sxi)/2 + sxi;
//    pc[10][1] = (sy - syi)/2 + syi;;
//    pc[10][2] = (sz - szi)/2 + szi;
//    pc[11][0] = pc[1][0];
//    pc[11][1] = pc[1][1];
//    pc[11][2] = pc[1][2];
//    pc[12][0] = pc[10][0];
//    pc[12][1] = pc[10][1];
//    pc[12][2] = pc[10][2];
//    pc[13][0] = sxi;
//    pc[13][1] = (sy - syi)/2 + syi;;
//    pc[13][2] = (sz - szi)/2 + szi;
//    pc[14][0] = pc[6][0];
//    pc[14][1] = pc[6][1];
//    pc[14][2] = pc[6][2];
//    pc[15][0] = pc[13][0];
//    pc[15][1] = pc[13][1];
//    pc[15][2] = pc[13][2];

                pc2[0][0] = (sx - sxi) / 2 + sxi;
                pc2[0][1] = szi;
                pc2[0][2] = (sy - syi) / 2 + syi;
                pc2[1][0] = sx;
                pc2[1][1] = szi;
                pc2[1][2] = (sy - syi) / 2 + syi;
                pc2[2][0] = sx;
                pc2[2][1] = szi;
                pc2[2][2] = sy;
                pc2[3][0] = (sx - sxi) / 2 + sxi;
                pc2[3][1] = szi;
                pc2[3][2] = sy;
                pc2[4][0] = pc2[0][0];
                pc2[4][1] = pc2[0][1];
                pc2[4][2] = pc2[0][2];
                pc2[5][0] = pc2[3][0];
                pc2[5][1] = pc2[3][1];
                pc2[5][2] = pc2[3][2];
                pc2[6][0] = (sx - sxi) / 2 + sxi;
                pc2[6][1] = (sz - szi) / 2 + szi;
                pc2[6][2] = sy;
                pc2[7][0] = sx;
                pc2[7][1] = (sz - szi) / 2 + szi;
                pc2[7][2] = sy;
                pc2[8][0] = pc2[2][0];
                pc2[8][1] = pc2[2][1];
                pc2[8][2] = pc2[2][2];
                pc2[9][0] = pc2[7][0];
                pc2[9][1] = pc2[7][1];
                pc2[9][2] = pc2[7][2];
                pc2[10][0] = sx;
                pc2[10][1] = (sz - szi) / 2 + szi;
                pc2[10][2] = (sy - syi) / 2 + syi;
                pc2[11][0] = pc2[1][0];
                pc2[11][1] = pc2[1][1];
                pc2[11][2] = pc2[1][2];
                pc2[12][0] = pc2[10][0];
                pc2[12][1] = pc2[10][1];
                pc2[12][2] = pc2[10][2];
                pc2[13][0] = (sx - sxi) / 2 + sxi;
                pc2[13][1] = (sz - szi) / 2 + szi;
                pc2[13][2] = (sy - syi) / 2 + syi;
                pc2[14][0] = pc2[6][0];
                pc2[14][1] = pc2[6][1];
                pc2[14][2] = pc2[6][2];
                pc2[15][0] = pc2[13][0];
                pc2[15][1] = pc2[13][1];
                pc2[15][2] = pc2[13][2];

//    pc2[0][0] = (sx - sxi)/2 + sxi;
//    pc2[0][1] = (sy - syi)/2 + syi;;
//    pc2[0][2] = szi;
//    pc2[1][0] = sx;
//    pc2[1][1] = (sy - syi)/2 + syi;;
//    pc2[1][2] = szi;
//    pc2[2][0] = sx;
//    pc2[2][1] = sy;
//    pc2[2][2] = szi;
//    pc2[3][0] = (sx - sxi)/2 + sxi;
//    pc2[3][1] = sy;
//    pc2[3][2] = szi;
//    pc2[4][0] = pc2[0][0];
//    pc2[4][1] = pc2[0][1];
//    pc2[4][2] = pc2[0][2];
//    pc2[5][0] = pc2[3][0];
//    pc2[5][1] = pc2[3][1];
//    pc2[5][2] = pc2[3][2];
//    pc2[6][0] = (sx - sxi)/2 + sxi;
//    pc2[6][1] = sy;
//    pc2[6][2] = (sz - szi)/2 + szi;
//    pc2[7][0] = sx;
//    pc2[7][1] = sy;
//    pc2[7][2] = (sz - szi)/2 + szi;
//    pc2[8][0] = pc2[2][0];
//    pc2[8][1] = pc2[2][1];
//    pc2[8][2] = pc2[2][2];
//    pc2[9][0] = pc2[7][0];
//    pc2[9][1] = pc2[7][1];
//    pc2[9][2] = pc2[7][2];
//    pc2[10][0] = sx;
//    pc2[10][1] = (sy - syi)/2 + syi;;
//    pc2[10][2] = (sz - szi)/2 + szi;
//    pc2[11][0] = pc2[1][0];
//    pc2[11][1] = pc2[1][1];
//    pc2[11][2] = pc2[1][2];
//    pc2[12][0] = pc2[10][0];
//    pc2[12][1] = pc2[10][1];
//    pc2[12][2] = pc2[10][2];
//    pc2[13][0] = (sx - sxi)/2 + sxi;
//    pc2[13][1] = (sy - syi)/2 + syi;;
//    pc2[13][2] = (sz - szi)/2 + szi;
//    pc2[14][0] = pc2[6][0];
//    pc2[14][1] = pc2[6][1];
//    pc2[14][2] = pc2[6][2];
//    pc2[15][0] = pc2[13][0];
//    pc2[15][1] = pc2[13][1];
//    pc2[15][2] = pc2[13][2];

                pc3[0][0] = (sx - sxi) / 2 + sxi;
                pc3[0][1] = (sz - szi) / 2 + szi;
                pc3[0][2] = (sy - syi) / 2 + syi;
                pc3[1][0] = sx;
                pc3[1][1] = (sz - szi) / 2 + szi;
                pc3[1][2] = (sy - syi) / 2 + syi;
                pc3[2][0] = sx;
                pc3[2][1] = (sz - szi) / 2 + szi;
                pc3[2][2] = sy;
                pc3[3][0] = (sx - sxi) / 2 + sxi;
                pc3[3][1] = (sz - szi) / 2 + szi;
                pc3[3][2] = sy;
                pc3[4][0] = pc3[0][0];
                pc3[4][1] = pc3[0][1];
                pc3[4][2] = pc3[0][2];
                pc3[5][0] = pc3[3][0];
                pc3[5][1] = pc3[3][1];
                pc3[5][2] = pc3[3][2];
                pc3[6][0] = (sx - sxi) / 2 + sxi;
                pc3[6][1] = sz;
                pc3[6][2] = sy;
                pc3[7][0] = sx;
                pc3[7][1] = sz;
                pc3[7][2] = sy;
                pc3[8][0] = pc3[2][0];
                pc3[8][1] = pc3[2][1];
                pc3[8][2] = pc3[2][2];
                pc3[9][0] = pc3[7][0];
                pc3[9][1] = pc3[7][1];
                pc3[9][2] = pc3[7][2];
                pc3[10][0] = sx;
                pc3[10][1] = sz;
                pc3[10][2] = (sy - syi) / 2 + syi;
                pc3[11][0] = pc3[1][0];
                pc3[11][1] = pc3[1][1];
                pc3[11][2] = pc3[1][2];
                pc3[12][0] = pc3[10][0];
                pc3[12][1] = pc3[10][1];
                pc3[12][2] = pc3[10][2];
                pc3[13][0] = (sx - sxi) / 2 + sxi;
                pc3[13][1] = sz;
                pc3[13][2] = (sy - syi) / 2 + syi;
                pc3[14][0] = pc3[6][0];
                pc3[14][1] = pc3[6][1];
                pc3[14][2] = pc3[6][2];
                pc3[15][0] = pc3[13][0];
                pc3[15][1] = pc3[13][1];
                pc3[15][2] = pc3[13][2];

//    pc3[0][0] = (sx - sxi)/2 + sxi;
//    pc3[0][1] = (sy - syi)/2 + syi;;
//    pc3[0][2] = (sz - szi)/2 + szi;
//    pc3[1][0] = sx;
//    pc3[1][1] = (sy - syi)/2 + syi;;
//    pc3[1][2] = (sz - szi)/2 + szi;
//    pc3[2][0] = sx;
//    pc3[2][1] = sy;
//    pc3[2][2] = (sz - szi)/2 + szi;
//    pc3[3][0] = (sx - sxi)/2 + sxi;
//    pc3[3][1] = sy;
//    pc3[3][2] = (sz - szi)/2 + szi;
//    pc3[4][0] = pc3[0][0];
//    pc3[4][1] = pc3[0][1];
//    pc3[4][2] = pc3[0][2];
//    pc3[5][0] = pc3[3][0];
//    pc3[5][1] = pc3[3][1];
//    pc3[5][2] = pc3[3][2];
//    pc3[6][0] = (sx - sxi)/2 + sxi;
//    pc3[6][1] = sy;
//    pc3[6][2] = sz;
//    pc3[7][0] = sx;
//    pc3[7][1] = sy;
//    pc3[7][2] = sz;
//    pc3[8][0] = pc3[2][0];
//    pc3[8][1] = pc3[2][1];
//    pc3[8][2] = pc3[2][2];
//    pc3[9][0] = pc3[7][0];
//    pc3[9][1] = pc3[7][1];
//    pc3[9][2] = pc3[7][2];
//    pc3[10][0] = sx;
//    pc3[10][1] = (sy - syi)/2 + syi;;
//    pc3[10][2] = sz;
//    pc3[11][0] = pc3[1][0];
//    pc3[11][1] = pc3[1][1];
//    pc3[11][2] = pc3[1][2];
//    pc3[12][0] = pc3[10][0];
//    pc3[12][1] = pc3[10][1];
//    pc3[12][2] = pc3[10][2];
//    pc3[13][0] = (sx - sxi)/2 + sxi;
//    pc3[13][1] = (sy - syi)/2 + syi;;
//    pc3[13][2] = sz;
//    pc3[14][0] = pc3[6][0];
//    pc3[14][1] = pc3[6][1];
//    pc3[14][2] = pc3[6][2];
//    pc3[15][0] = pc3[13][0];
//    pc3[15][1] = pc3[13][1];
//    pc3[15][2] = pc3[13][2];

                pc4[0][0] = sxi;
                pc4[0][1] = (sz - szi) / 2 + szi;
                pc4[0][2] = (sy - syi) / 2 + syi;
                pc4[1][0] = (sx - sxi) / 2 + sxi;
                pc4[1][1] = (sz - szi) / 2 + szi;
                pc4[1][2] = (sy - syi) / 2 + syi;
                pc4[2][0] = (sx - sxi) / 2 + sxi;
                pc4[2][1] = (sz - szi) / 2 + szi;
                pc4[2][2] = sy;
                pc4[3][0] = sxi;
                pc4[3][1] = (sz - szi) / 2 + szi;
                pc4[3][2] = sy;
                pc4[4][0] = pc4[0][0];
                pc4[4][1] = pc4[0][1];
                pc4[4][2] = pc4[0][2];
                pc4[5][0] = pc4[3][0];
                pc4[5][1] = pc4[3][1];
                pc4[5][2] = pc4[3][2];
                pc4[6][0] = sxi;
                pc4[6][1] = sz;
                pc4[6][2] = sy;
                pc4[7][0] = (sx - sxi) / 2 + sxi;
                pc4[7][1] = sz;
                pc4[7][2] = sy;
                pc4[8][0] = pc4[2][0];
                pc4[8][1] = pc4[2][1];
                pc4[8][2] = pc4[2][2];
                pc4[9][0] = pc4[7][0];
                pc4[9][1] = pc4[7][1];
                pc4[9][2] = pc4[7][2];
                pc4[10][0] = (sx - sxi) / 2 + sxi;
                pc4[10][1] = sz;
                pc4[10][2] = (sy - syi) / 2 + syi;
                pc4[11][0] = pc4[1][0];
                pc4[11][1] = pc4[1][1];
                pc4[11][2] = pc4[1][2];
                pc4[12][0] = pc4[10][0];
                pc4[12][1] = pc4[10][1];
                pc4[12][2] = pc4[10][2];
                pc4[13][0] = sxi;
                pc4[13][1] = sz;
                pc4[13][2] = (sy - syi) / 2 + syi;
                pc4[14][0] = pc4[6][0];
                pc4[14][1] = pc4[6][1];
                pc4[14][2] = pc4[6][2];
                pc4[15][0] = pc4[13][0];
                pc4[15][1] = pc4[13][1];
                pc4[15][2] = pc4[13][2];

//    pc4[0][0] = sxi;
//    pc4[0][1] = (sy - syi)/2 + syi;;
//    pc4[0][2] = (sz - szi)/2 + szi;
//    pc4[1][0] = (sx - sxi)/2 + sxi;
//    pc4[1][1] = (sy - syi)/2 + syi;;
//    pc4[1][2] = (sz - szi)/2 + szi;
//    pc4[2][0] = (sx - sxi)/2 + sxi;
//    pc4[2][1] = sy;
//    pc4[2][2] = (sz - szi)/2 + szi;
//    pc4[3][0] = sxi;
//    pc4[3][1] = sy;
//    pc4[3][2] = (sz - szi)/2 + szi;
//    pc4[4][0] = pc4[0][0];
//    pc4[4][1] = pc4[0][1];
//    pc4[4][2] = pc4[0][2];
//    pc4[5][0] = pc4[3][0];
//    pc4[5][1] = pc4[3][1];
//    pc4[5][2] = pc4[3][2];
//    pc4[6][0] = sxi;
//    pc4[6][1] = sy;
//    pc4[6][2] = sz;
//    pc4[7][0] = (sx - sxi)/2 + sxi;
//    pc4[7][1] = sy;
//    pc4[7][2] = sz;
//    pc4[8][0] = pc4[2][0];
//    pc4[8][1] = pc4[2][1];
//    pc4[8][2] = pc4[2][2];
//    pc4[9][0] = pc4[7][0];
//    pc4[9][1] = pc4[7][1];
//    pc4[9][2] = pc4[7][2];
//    pc4[10][0] = (sx - sxi)/2 + sxi;
//    pc4[10][1] = (sy - syi)/2 + syi;;
//    pc4[10][2] = sz;
//    pc4[11][0] = pc4[1][0];
//    pc4[11][1] = pc4[1][1];
//    pc4[11][2] = pc4[1][2];
//    pc4[12][0] = pc4[10][0];
//    pc4[12][1] = pc4[10][1];
//    pc4[12][2] = pc4[10][2];
//    pc4[13][0] = sxi;
//    pc4[13][1] = (sy - syi)/2 + syi;;
//    pc4[13][2] = sz;
//    pc4[14][0] = pc4[6][0];
//    pc4[14][1] = pc4[6][1];
//    pc4[14][2] = pc4[6][2];
//    pc4[15][0] = pc4[13][0];
//    pc4[15][1] = pc4[13][1];
//    pc4[15][2] = pc4[13][2];  

                //PC 5 Ok
                pc5[0][0] = sxi;
                pc5[0][1] = szi;
                pc5[0][2] = syi;
                pc5[1][0] = (sx - sxi) / 2 + sxi;
                pc5[1][1] = szi;
                pc5[1][2] = syi;
                pc5[2][0] = (sx - sxi) / 2 + sxi;
                pc5[2][1] = szi;
                pc5[2][2] = (sy - syi) / 2 + syi;
                pc5[3][0] = sxi;
                pc5[3][1] = szi;
                pc5[3][2] = (sy - syi) / 2 + syi;
                pc5[4][0] = pc5[0][0];
                pc5[4][1] = pc5[0][1];
                pc5[4][2] = pc5[0][2];
                pc5[5][0] = pc5[3][0];
                pc5[5][1] = pc5[3][1];
                pc5[5][2] = pc5[3][2];
                pc5[6][0] = sxi;
                pc5[6][1] = (sz - szi) / 2 + szi;
                pc5[6][2] = (sy - syi) / 2 + syi;
                pc5[7][0] = (sx - sxi) / 2 + sxi;
                pc5[7][1] = (sz - szi) / 2 + szi;
                pc5[7][2] = (sy - syi) / 2 + syi;
                pc5[8][0] = pc5[2][0];
                pc5[8][1] = pc5[2][1];
                pc5[8][2] = pc5[2][2];
                pc5[9][0] = pc5[7][0];
                pc5[9][1] = pc5[7][1];
                pc5[9][2] = pc5[7][2];
                pc5[10][0] = (sx - sxi) / 2 + sxi;
                pc5[10][1] = (sz - szi) / 2 + szi;
                pc5[10][2] = syi;
                pc5[11][0] = pc5[1][0];
                pc5[11][1] = pc5[1][1];
                pc5[11][2] = pc5[1][2];
                pc5[12][0] = pc5[10][0];
                pc5[12][1] = pc5[10][1];
                pc5[12][2] = pc5[10][2];
                pc5[13][0] = sxi;
                pc5[13][1] = (sz - szi) / 2 + szi;
                pc5[13][2] = syi;
                pc5[14][0] = pc5[6][0];
                pc5[14][1] = pc5[6][1];
                pc5[14][2] = pc5[6][2];
                pc5[15][0] = pc5[13][0];
                pc5[15][1] = pc5[13][1];
                pc5[15][2] = pc5[13][2];

                //PC 6 OK
                pc6[0][0] = (sx - sxi) / 2 + sxi;
                pc6[0][1] = szi;
                pc6[0][2] = syi;
                pc6[1][0] = sx;
                pc6[1][1] = szi;
                pc6[1][2] = syi;
                pc6[2][0] = sx;
                pc6[2][1] = szi;
                pc6[2][2] = (sy - syi) / 2 + syi;
                pc6[3][0] = (sx - sxi) / 2 + sxi;
                pc6[3][1] = szi;
                pc6[3][2] = (sy - syi) / 2 + syi;
                pc6[4][0] = pc6[0][0];
                pc6[4][1] = pc6[0][1];
                pc6[4][2] = pc6[0][2];
                pc6[5][0] = pc6[3][0];
                pc6[5][1] = pc6[3][1];
                pc6[5][2] = pc6[3][2];
                pc6[6][0] = (sx - sxi) / 2 + sxi;
                pc6[6][1] = (sz - szi) / 2 + szi;
                pc6[6][2] = (sy - syi) / 2 + syi;
                pc6[7][0] = sx;
                pc6[7][1] = (sz - szi) / 2 + szi;
                pc6[7][2] = (sy - syi) / 2 + syi;
                pc6[8][0] = pc6[2][0];
                pc6[8][1] = pc6[2][1];
                pc6[8][2] = pc6[2][2];
                pc6[9][0] = pc6[7][0];
                pc6[9][1] = pc6[7][1];
                pc6[9][2] = pc6[7][2];
                pc6[10][0] = sx;
                pc6[10][1] = (sz - szi) / 2 + szi;
                pc6[10][2] = syi;
                pc6[11][0] = pc6[1][0];
                pc6[11][1] = pc6[1][1];
                pc6[11][2] = pc6[1][2];
                pc6[12][0] = pc6[10][0];
                pc6[12][1] = pc6[10][1];
                pc6[12][2] = pc6[10][2];
                pc6[13][0] = (sx - sxi) / 2 + sxi;
                pc6[13][1] = (sz - szi) / 2 + szi;
                pc6[13][2] = syi;
                pc6[14][0] = pc6[6][0];
                pc6[14][1] = pc6[6][1];
                pc6[14][2] = pc6[6][2];
                pc6[15][0] = pc6[13][0];
                pc6[15][1] = pc6[13][1];
                pc6[15][2] = pc6[13][2];

                //PC 7 OK
                pc7[0][0] = (sx - sxi) / 2 + sxi;
                pc7[0][1] = (sz - szi) / 2 + szi;
                pc7[0][2] = syi;
                pc7[1][0] = sx;
                pc7[1][1] = (sz - szi) / 2 + szi;
                pc7[1][2] = syi;
                pc7[2][0] = sx;
                pc7[2][1] = (sz - szi) / 2 + szi;
                pc7[2][2] = (sy - syi) / 2 + syi;
                pc7[3][0] = (sx - sxi) / 2 + sxi;
                pc7[3][1] = (sz - szi) / 2 + szi;
                pc7[3][2] = (sy - syi) / 2 + syi;
                pc7[4][0] = pc7[0][0];
                pc7[4][1] = pc7[0][1];
                pc7[4][2] = pc7[0][2];
                pc7[5][0] = pc7[3][0];
                pc7[5][1] = pc7[3][1];
                pc7[5][2] = pc7[3][2];
                pc7[6][0] = (sx - sxi) / 2 + sxi;
                pc7[6][1] = sz;
                pc7[6][2] = (sy - syi) / 2 + syi;
                pc7[7][0] = sx;
                pc7[7][1] = sz;
                pc7[7][2] = (sy - syi) / 2 + syi;
                pc7[8][0] = pc7[2][0];
                pc7[8][1] = pc7[2][1];
                pc7[8][2] = pc7[2][2];
                pc7[9][0] = pc7[7][0];
                pc7[9][1] = pc7[7][1];
                pc7[9][2] = pc7[7][2];
                pc7[10][0] = sx;
                pc7[10][1] = sz;
                pc7[10][2] = syi;
                pc7[11][0] = pc7[1][0];
                pc7[11][1] = pc7[1][1];
                pc7[11][2] = pc7[1][2];
                pc7[12][0] = pc7[10][0];
                pc7[12][1] = pc7[10][1];
                pc7[12][2] = pc7[10][2];
                pc7[13][0] = (sx - sxi) / 2 + sxi;
                pc7[13][1] = sz;
                pc7[13][2] = syi;
                pc7[14][0] = pc7[6][0];
                pc7[14][1] = pc7[6][1];
                pc7[14][2] = pc7[6][2];
                pc7[15][0] = pc7[13][0];
                pc7[15][1] = pc7[13][1];
                pc7[15][2] = pc7[13][2];

                //PC 8 Ok
                pc8[0][0] = sxi;
                pc8[0][1] = (sz - szi) / 2 + szi;
                pc8[0][2] = syi;
                pc8[1][0] = (sx - sxi) / 2 + sxi;
                pc8[1][1] = (sz - szi) / 2 + szi;
                pc8[1][2] = syi;
                pc8[2][0] = (sx - sxi) / 2 + sxi;
                pc8[2][1] = (sz - szi) / 2 + szi;
                pc8[2][2] = (sy - syi) / 2 + syi;
                pc8[3][0] = sxi;
                pc8[3][1] = (sz - szi) / 2 + szi;
                pc8[3][2] = (sy - syi) / 2 + syi;
                pc8[4][0] = pc8[0][0];
                pc8[4][1] = pc8[0][1];
                pc8[4][2] = pc8[0][2];
                pc8[5][0] = pc8[3][0];
                pc8[5][1] = pc8[3][1];
                pc8[5][2] = pc8[3][2];
                pc8[6][0] = sxi;
                pc8[6][1] = sz;
                pc8[6][2] = (sy - syi) / 2 + syi;
                pc8[7][0] = (sx - sxi) / 2 + sxi;
                pc8[7][1] = sz;
                pc8[7][2] = (sy - syi) / 2 + syi;
                pc8[8][0] = pc8[2][0];
                pc8[8][1] = pc8[2][1];
                pc8[8][2] = pc8[2][2];
                pc8[9][0] = pc8[7][0];
                pc8[9][1] = pc8[7][1];
                pc8[9][2] = pc8[7][2];
                pc8[10][0] = (sx - sxi) / 2 + sxi;
                pc8[10][1] = sz;
                pc8[10][2] = syi;
                pc8[11][0] = pc8[1][0];
                pc8[11][1] = pc8[1][1];
                pc8[11][2] = pc8[1][2];
                pc8[12][0] = pc8[10][0];
                pc8[12][1] = pc8[10][1];
                pc8[12][2] = pc8[10][2];
                pc8[13][0] = sxi;
                pc8[13][1] = sz;
                pc8[13][2] = syi;
                pc8[14][0] = pc8[6][0];
                pc8[14][1] = pc8[6][1];
                pc8[14][2] = pc8[6][2];
                pc8[15][0] = pc8[13][0];
                pc8[15][1] = pc8[13][1];
                pc8[15][2] = pc8[13][2];


                draw.drawPolygon(pc);
                draw.drawPolygon(pc2);
                draw.drawPolygon(pc3);
                draw.drawPolygon(pc4);
                draw.drawPolygon(pc5);
                draw.drawPolygon(pc6);
                draw.drawPolygon(pc7);
                draw.drawPolygon(pc8);

                double[] label1 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2}; //Calcula o limite inicial de cada quadrante + seu limite final dividido por 2 para assim chegar na coordenada correta.
                double[] label2 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2}; // sz e sy estão em lugares trocados devido sua ordem também está trocada na hora de colocar os pontos no objeto XYZ(devido a limitações do Jmathplot o y e o z são trocados.
                double[] label3 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};// O Y e o Z controlam respectivamente altura e profundidade e o X a largura.
                double[] label4 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};
                double[] label5 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2};
                double[] label6 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2};
                double[] label7 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};
                double[] label8 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};


                draw.setFont(new Font(draw.getFont().getFontName(), Font.PLAIN, 48));
                draw.drawText("1", label1);
                draw.drawText("2", label2);
                draw.drawText("3", label3);
                draw.drawText("4", label4);
                draw.drawText("5", label5);
                draw.drawText("6", label6);
                draw.drawText("7", label7);
                draw.drawText("8", label8);


            } else {

                draw = new AWTDrawer2D(Interface.plot2D.plotCanvas);
                draw.initGraphics((Graphics2D) Interface.plot2D.plotCanvas.getGraphics());
                draw.canvas = Interface.plot2D.plotCanvas;
                draw.canvas.initDrawer();
                double sx;
                double sxi = 0;
                double sy;  // fim do quadrante y
                double syi = 0; // inicio do quadrante y
                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot2D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot2D.getAxis(0).getStringMap().values());
//              sx = Interface.plot2D.getAxis(0).getStringMap().size()-1; 
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot2D.getAxis(1).getStringMap().values());
                    sy = Collections.max(Interface.plot2D.getAxis(1).getStringMap().values());
//            sy = Interface.plot2D.getAxis(1).getStringMap().size()-1; 
                } else {
                    sy = GetMinMaxValueY()[1];
                    syi = GetMinMaxValueY()[0];
                }


//    double[] label1 = new double[2];
//    double[] label2 = new double[2];
//    double[] label3 = new double[2];
//    double[] label4 = new double[2];


                System.out.println("sxsxsxsx plplplplplpl " + sx);
                System.out.println("sxsxsxsxi lplplplplpl " + sxi);
                System.out.println("sysysysy  " + sy);
                System.out.println("sysysysyi plplplplplpl " + syi);
                System.out.println("Xl1 tst" + xl1);
                System.out.println("Xl1 tst" + yl1);
                System.out.println("Xl1 tst" + cores);
                System.out.println("Xl1 tst" + formas);
                System.out.println("Xl1 tst" + tamanhos);

                double[][] pc = new double[4][2];
                pc[0][0] = sxi;
                pc[0][1] = sy;
                pc[1][0] = (sx - sxi) / 2 + sxi;
                pc[1][1] = sy;
                pc[2][0] = (sx - sxi) / 2 + sxi;
                pc[2][1] = (sy - syi) / 2 + syi;
                pc[3][0] = sxi;
                pc[3][1] = (sy - syi) / 2 + syi;;
//    double[][] pc = new double[4][2];
//    pc[0][0] = 0;
//    pc[0][1] = sy;
//    pc[1][0] = sx/2;
//    pc[1][1] = sy;
//    pc[2][0] = sx/2;
//    pc[2][1] = sy/2;
//    pc[3][0] = 0;
//    pc[3][1] = sy/2;
//    label1[0] = (pc[1][0] - pc[0][0])/2;
//    label1[1] = (pc[1][1] + pc[2][1])/2;


                double[][] pc2 = new double[4][2];
                pc2[0][0] = (sx - sxi) / 2 + sxi;;
                pc2[0][1] = sy;
                pc2[1][0] = sx;
                pc2[1][1] = sy;
                pc2[2][0] = sx;
                pc2[2][1] = (sy - syi) / 2 + syi;;
                pc2[3][0] = (sx - sxi) / 2 + sxi;;
                pc2[3][1] = (sy - syi) / 2 + syi;;
//    double[][] pc2 = new double[4][2];
//    pc2[0][0] = sx/2;
//    pc2[0][1] = sy;
//    pc2[1][0] = sx;
//    pc2[1][1] = sy;
//    pc2[2][0] = sx;
//    pc2[2][1] = sy/2;
//    pc2[3][0] = sx/2;
//    pc2[3][1] = sy/2;
//    label2[0] = (pc2[1][0] + pc2[0][0])/2;
//    label2[1] = (pc2[1][1] + pc2[2][1])/2;

                double[][] pc3 = new double[4][2];
                pc3[0][0] = sxi;
                pc3[0][1] = (sy - syi) / 2 + syi;;
                pc3[1][0] = (sx - sxi) / 2 + sxi;;
                pc3[1][1] = (sy - syi) / 2 + syi;;
                pc3[2][0] = (sx - sxi) / 2 + sxi;;
                pc3[2][1] = syi;
                pc3[3][0] = sxi;
                pc3[3][1] = syi;
//    double[][] pc3 = new double[4][2];
//    pc3[0][0] = 0;
//    pc3[0][1] = sy/2;
//    pc3[1][0] = sx/2;
//    pc3[1][1] = sy/2;
//    pc3[2][0] = sx/2;
//    pc3[2][1] = 0;
//    pc3[3][0] = 0;
//    pc3[3][1] = 0;

//    label3[0] = (pc3[1][0] - pc3[0][0])/2;
//    label3[1] = (pc3[1][1] + pc3[2][1])/2;

                double[][] pc4 = new double[4][2];
                pc4[0][0] = (sx - sxi) / 2 + sxi;
                pc4[0][1] = (sy - syi) / 2 + syi;
                pc4[1][0] = sx;
                pc4[1][1] = (sy - syi) / 2 + syi;
                pc4[2][0] = sx;
                pc4[2][1] = syi;
                pc4[3][0] = (sx - sxi) / 2 + sxi;
                pc4[3][1] = syi;

//    label4[0] = (pc4[1][0] + pc4[0][0])/2;
//    label4[1] = (pc4[1][1] + pc4[2][1])/2;
//    double[][] pc4 = new double[4][2];
//    pc4[0][0] = sx/2;
//    pc4[0][1] = sy/2;
//    pc4[1][0] = sx;
//    pc4[1][1] = sy/2;
//    pc4[2][0] = sx;
//    pc4[2][1] = 0;
//    pc4[3][0] = sx/2;
//    pc4[3][1] = 0;


                double[] label1 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2}; //Versão aprimorada do centralizador se essa não funcionar voltar pra anterior 
                double[] label2 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2};// Tirando o comentário da declaracao das variaveis labels das atribuiçoes das mesmas
                double[] label3 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};
                double[] label4 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};

                draw.drawPolygon(pc);
                draw.drawPolygon(pc2);
                draw.drawPolygon(pc3);
                draw.drawPolygon(pc4);
                draw.setFont(new Font(draw.getFont().getFontName(), Font.PLAIN, 48));
                draw.drawText("1", label1);
                draw.drawText("2", label2);
                draw.drawText("3", label3);
                draw.drawText("4", label4);

            }


//    int[] origin = {(int)pc[0][0], (int)pc[1][0], (int)pc[0][1],(int)pc[1][1]};
//    int[] origin = { min(mouseClick[0], mouseCurent[0]), min(mouseClick[1], mouseCurent[1]) };
//    double[] ratio = { Math.abs((double) (pc[1][0] - pc[0][0]) / (double) getWidth()),
//    Math.abs((double) (pc[1][1] - pc[0][1]) / (double) getHeight())};
//    draw.canvas.setActionMode(0);
//    draw.dilate(origin, ratio);

//    draw.drawLineBase(rC);
//    draw.drawCoordinate(XYp2);
        } else {
            
            
            if (IsPlot3D) {
                
                
                ArrayList<Object> Xlist = new ArrayList<>();
                ArrayList<Object> Ylist = new ArrayList<>();
                ArrayList<Object> Zlist = new ArrayList<>();
                ArrayList<String> paresOrdenados = new ArrayList<>();

                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    for (int i = 0; i <= xl1Coord.size() - 1; i++) {
                        Xlist.add(Interface.plot3D.getAxis(0).getStringMap().get(xl1Coord.get(i)));
                        }
                } else {
                    Xlist.addAll(xl1Coord);
                    }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    for (int i = 0; i <= yl1Coord.size() - 1; i++) {
                        Ylist.add(Interface.plot3D.getAxis(2).getStringMap().get(yl1Coord.get(i)));
                    }
                } else {
                    Ylist.addAll(yl1Coord);
                    }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    for (int i = 0; i <= zl1Coord.size() - 1; i++) {
                        Ylist.add(Interface.plot3D.getAxis(1).getStringMap().get(zl1Coord.get(i)));
                    }
                } else {
                    Zlist.addAll(zl1Coord);
                    }
                double[] parOrdenado = new double[3];
                
                
                int count = 0;
                for (int i = 0; i < xl1Coord.size(); i++) {
                    if (!xl1Coord.get(i).equals(null) || !xl1Coord.get(i).equals("null") || xl1Coord.get(i) != null) {
                        if (paresOrdenados.isEmpty()) {
                            parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
                            parOrdenado[1] = Double.parseDouble(Zlist.get(i).toString());
                            parOrdenado[2] = Double.parseDouble(Ylist.get(i).toString());
                            paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1] + "/" + parOrdenado[2]);
                            draw.drawText("" + count, parOrdenado);
                        } else if (!paresOrdenados.contains(Double.parseDouble(Xlist.get(i).toString()) + "/" + Double.parseDouble(Ylist.get(i).toString()) + "/" + Double.parseDouble(Ylist.get(i).toString()))) {
                            count++;
                            parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
                            parOrdenado[1] = Double.parseDouble(Zlist.get(i).toString());
                            parOrdenado[2] = Double.parseDouble(Ylist.get(i).toString());
                            paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1] + "/" + parOrdenado[2]);
                            draw.drawText("" + count, parOrdenado);
                        }
                    }
                }
//                ArrayList<Object> Xlist = new ArrayList<>();
//                ArrayList<Object> Ylist = new ArrayList<>();
//                ArrayList<Object> Zlist = new ArrayList<>();
//                ArrayList<String> paresOrdenados = new ArrayList<>();
//
//                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//                    for (int i = 0; i <= xl1Coord.size() - 1; i++) {
//                        Xlist.add(Interface.plot3D.getAxis(0).getStringMap().get(xl1Coord.get(i)));
//                        }
//                } else {
//                    Xlist.addAll(xl1Coord);
//                    }
//                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//                    for (int i = 0; i <= yl1Coord.size() - 1; i++) {
//                        Ylist.add(Interface.plot3D.getAxis(2).getStringMap().get(yl1Coord.get(i)));
//                    }
//                } else {
//                    Ylist.addAll(yl1Coord);
//                    }
//                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//                    for (int i = 0; i <= zl1Coord.size() - 1; i++) {
//                        Zlist.add(Interface.plot3D.getAxis(1).getStringMap().get(zl1Coord.get(i)));
//                    }
//                } else {
//                    Ylist.addAll(zl1Coord);
//                    }
//                
//                
////                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
////                    for (int i = 0; i <= xl1.size() - 1; i++) {
////                        Xlist.add(Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)));
////                    }
////                } else {
////                    Xlist.addAll(xl1);
////                }
////                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
////                    for (int i = 0; i <= yl1.size() - 1; i++) {
////                        Ylist.add(Interface.plot3D.getAxis(2).getStringMap().get(zl1.get(i)));
////                    }
////                } else {
////                    Ylist.addAll(zl1);
////
////                }
////                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
////                    for (int i = 0; i <= zl1.size() - 1; i++) {
////                        Ylist.add(Interface.plot3D.getAxis(1).getStringMap().get(yl1.get(i)));
////                    }
////                } else {
////                    Zlist.addAll(yl1);
////
////                }
//                
//                double[] parOrdenado = new double[3];
//
//                int count = 0;
//                for (int i = 0; i < xl1Coord.size(); i++) {
//                    if (!xl1Coord.get(i).equals(null) || !xl1Coord.get(i).equals("null") || xl1Coord.get(i) != null) {
//                        if (paresOrdenados.isEmpty()) {
//                            parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
//                            parOrdenado[1] = Double.parseDouble(Ylist.get(i).toString());
//                            parOrdenado[2] = Double.parseDouble(Zlist.get(i).toString());
//                            paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1] + "/" + parOrdenado[2]);
//                            draw.drawText("" + count, parOrdenado);
//                        } else if (!paresOrdenados.contains(Double.parseDouble(Xlist.get(i).toString()) + "/" + Double.parseDouble(Ylist.get(i).toString()) + "/" + Double.parseDouble(Zlist.get(i).toString()))) {
//                            count++;
//                            parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
//                            parOrdenado[1] = Double.parseDouble(Ylist.get(i).toString());
//                            parOrdenado[2] = Double.parseDouble(Zlist.get(i).toString());
//                            paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1] + "/" + parOrdenado[2]);
//                            draw.drawText("" + count, parOrdenado);
//                        }
////                System.out.println(paresOrdenados.get(i));  
//                    }
//                }


            } else {

                ArrayList<Object> Xlist = new ArrayList<>();
                ArrayList<Object> Ylist = new ArrayList<>();
                ArrayList<String> paresOrdenados = new ArrayList<>();

                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    for (int i = 0; i <= xl1Coord.size() - 1; i++) {
                        Xlist.add(Interface.plot2D.getAxis(0).getStringMap().get(xl1Coord.get(i)));
                        System.out.println("AKAKAKAKAKAKAKAKAK");
                    }
                } else {
                    Xlist.addAll(xl1Coord);
                    System.out.println("LOLOLOLOLOLOLLOLOL");
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    for (int i = 0; i <= yl1Coord.size() - 1; i++) {
                        Ylist.add(Interface.plot2D.getAxis(1).getStringMap().get(yl1Coord.get(i)));
                        System.out.println("AKAKAKAKAKAKAKAKAK2");
                    }
                } else {
                    Ylist.addAll(yl1Coord);
                    System.out.println("LOLOLOLOLOLOLLOLOL2");

                }
                double[] parOrdenado = new double[2];

                System.out.println("SSSSSSSSSSSSSS " + xl1.size());
                System.out.println("SSSSSSSSSSSSSS2222 " + Xlist.size());
                System.out.println("SSSSSSSSSSSSSS2222 " + Ylist.size());
//        System.out.println("SSSSSSSSSSSSSS3333 "+Interface.plot2D.getAxis(0).getStringMap().entrySet());
//        System.out.println("SSSSSSSSSSSSSS444 "+Interface.plot2D.getAxis(0).getStringMap().keySet());
//        System.out.println("SSSSSSSSSSSSSS444 "+Interface.plot2D.getAxis(0).getStringMap().values());
                System.out.println("SSSSSSSSSSSSSS666 " + Xlist);
                System.out.println("SSSSSSSSSSSSSS667 " + xl1);
                System.out.println("SSSSSSSSSSSSSS668 " + yl1);

                int count = 0;
                for (int i = 0; i < xl1Coord.size(); i++) {
                    if (!xl1Coord.get(i).equals(null) || !xl1Coord.get(i).equals("null") || xl1Coord.get(i) != null) {
                        if (paresOrdenados.isEmpty()) {
                            parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
                            parOrdenado[1] = Double.parseDouble(Ylist.get(i).toString());
                            paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1]);
                            draw.drawText("" + count, parOrdenado);
                        } else if (!paresOrdenados.contains(Double.parseDouble(Xlist.get(i).toString()) + "/" + Double.parseDouble(Ylist.get(i).toString()))) {
                            count++;
                            parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
                            parOrdenado[1] = Double.parseDouble(Ylist.get(i).toString());
                            paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1]);
                            draw.drawText("" + count, parOrdenado);
                        }
                    }
//        for(int i = 0; i < xl1.size(); i++){
//            if (!xl1.get(i).equals(null) || !xl1.get(i).equals("null")) {
//                if (paresOrdenados.isEmpty()) {
//                parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
//                parOrdenado[1] = Double.parseDouble(Ylist.get(i).toString());
//                paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1]);
//                draw.drawText(""+count, parOrdenado); 
//                } else if(!paresOrdenados.contains(Double.parseDouble(Xlist.get(i).toString()) + "/" + Double.parseDouble(Ylist.get(i).toString()))){
//                count++;
//                parOrdenado[0] = Double.parseDouble(Xlist.get(i).toString());
//                parOrdenado[1] = Double.parseDouble(Ylist.get(i).toString());
//                paresOrdenados.add(parOrdenado[0] + "/" + parOrdenado[1]);
//                draw.drawText(""+count, parOrdenado);
//                }
////                System.out.println(paresOrdenados.get(i));  
//            }
                }

                ////        Map<Double, String> XHashMap = new HashMap<>();
////        for(Map.Entry<String, Double> entry : Interface.plot2D.getAxis(0).getStringMap().entrySet()){
////            XHashMap.put(entry.getValue(), entry.getKey());
////        }
////        Map<Double, String> YHashMap = new HashMap<>();
////        for(Map.Entry<String, Double> entry : Interface.plot2D.getAxis(1).getStringMap().entrySet()){
////            YHashMap.put(entry.getValue(), entry.getKey());
////        }
////        String XYs2 = new String();   
//        double[] XYp2 = new double[2];   
//        draw = new AWTDrawer2D(Interface.plot2D.plotCanvas);
//        draw.initGraphics((Graphics2D) Interface.plot2D.plotCanvas.getGraphics());
//        draw.canvas = Interface.plot2D.plotCanvas;
//        draw.canvas.initDrawer();
//        if (marcador>=0 && marcador < draw.canvas.getPlot(PlotNumber).getData().length) {    
//        XYp2[0] = draw.canvas.getPlot(PlotNumber).getData()[marcador][0];
//        XYp2[1] = draw.canvas.getPlot(PlotNumber).getData()[marcador][1];
////        XYs2 = XHashMap.get(XYp2[0]);
////        XYs2 += "\n"+YHashMap.get(XYp2[1]);
//        Interface.plot2D.plotCanvas.allowNote = true;   
//        Interface.plot2D.plotCanvas.allowNoteCoord = true;  
////        draw.drawText(Array.cat(reverseMapedData(XYp2)), XYp2);
////        draw.drawText(XYs2,XYp2);
////        Interface.plot2D.getPlot(PlotNumber).note(draw);
//        Interface.plot2D.getPlot(PlotNumber).noteCoord(draw, XYp2);
//        marcador++;
//        }else 
//            marcador = 0;
            }
//            Interface.BtnsDetails();
//           Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());     
            
        }
           
        
        
    }
    
    
    public void DesenhandoCoordenadas(int number) throws IOException{
        double contador = 0;
        draw.setColor(Color.RED);
        if (IsPlot3D) {

                ArrayList<Object> Xlist = new ArrayList<>();
                ArrayList<Object> Ylist = new ArrayList<>();
                ArrayList<Object> Zlist = new ArrayList<>();
                
                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    for (int i = 0; i <= xl1Coord.size() - 1; i++) {
                        Xlist.add(Interface.plot3D.getAxis(0).getStringMap().get(xl1Coord.get(i)));
                    }
                } else {
                    Xlist.addAll(xl1Coord);
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    for (int i = 0; i <= yl1Coord.size() - 1; i++) {
                        Ylist.add(Interface.plot3D.getAxis(2).getStringMap().get(yl1Coord.get(i)));
                    }
                } else {
                    Ylist.addAll(yl1Coord);

                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    for (int i = 0; i <= zl1Coord.size() - 1; i++) {
                        Zlist.add(Interface.plot3D.getAxis(1).getStringMap().get(zl1Coord.get(i)));
                    }
                } else {
                    Zlist.addAll(zl1Coord);
                }
//                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//                    for (int i = 0; i <= xl1.size() - 1; i++) {
//                        Xlist.add(Interface.plot3D.getAxis(0).getStringMap().get(xl1.get(i)));
//                    }
//                } else {
//                    Xlist.addAll(xl1);
//                }
//                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//                    for (int i = 0; i <= yl1.size() - 1; i++) {
//                        Ylist.add(Interface.plot3D.getAxis(2).getStringMap().get(yl1.get(i)));
//                    }
//                } else {
//                    Ylist.addAll(yl1);
//
//                }
//                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//                    for (int i = 0; i <= zl1.size() - 1; i++) {
//                        Zlist.add(Interface.plot3D.getAxis(1).getStringMap().get(zl1.get(i)));
//                    }
//                } else {
//                    Zlist.addAll(zl1);
//                }
                double[] parOrdenado = new double[3];
                double[] parOrdenadoX = new double[3];
                double[] parOrdenadoY = new double[3];
                double[] parOrdenadoZ = new double[3];
                
                double MinX = 0.0;
                double MinY = 0.0;
                double MinZ = 0.0;
                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    MinX = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    MinX = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    MinZ = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    MinZ = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    MinY = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    MinY = GetMinMaxValueY()[0];
                }
                
                
//                    if (!xl1.get(number).equals(null) || !xl1.get(number).equals("null")) {
//                            parOrdenado[0] = Double.parseDouble(Xlist.get(number).toString());
//                            parOrdenado[1] = Double.parseDouble(Ylist.get(number).toString());
//                            parOrdenado[2] = Double.parseDouble(Zlist.get(number).toString());
//                            draw.drawText("" + Xlist.get(number).toString(), parOrdenado);
//                    }
                    if (!xl1Coord.get(number).equals(null) || !xl1Coord.get(number).equals("null") || xl1Coord.get(number) != null) {
                            parOrdenado[0] = Double.parseDouble(Xlist.get(number).toString());
                            parOrdenado[1] = Double.parseDouble(Zlist.get(number).toString());
                            parOrdenado[2] = Double.parseDouble(Ylist.get(number).toString());
                            parOrdenadoX[0] = Double.parseDouble(Xlist.get(number).toString());
                            parOrdenadoX[1] = MinZ; // pegar o valor mínimo do eixo Z, não pode ser o zero pois em muitos casos os valores começam diferentes de zero.
                            parOrdenadoX[2] = Double.parseDouble(Ylist.get(number).toString());
                            parOrdenadoY[0] = Double.parseDouble(Xlist.get(number).toString());
                            parOrdenadoY[1] = Double.parseDouble(Zlist.get(number).toString());
                            parOrdenadoY[2] = MinY;
                            parOrdenadoZ[0] = MinX;
                            parOrdenadoZ[1] = Double.parseDouble(Zlist.get(number).toString());
                            parOrdenadoZ[2] = Double.parseDouble(Ylist.get(number).toString());
                            draw.drawCoordinate(parOrdenado);
                            
                            draw.drawText("" + zl1Coord.get(number), parOrdenadoZ);
                            draw.drawText("" + xl1Coord.get(number), parOrdenadoX);
                            draw.drawText("" + yl1Coord.get(number), parOrdenadoY);
                            draw.setTextAngle(0);
                            for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                            contador = k * ((parOrdenado[2]/100)*10); // Pegar a escala correta do XY // O Ultimo valor é a porcentagem que vai ser aplicada no espaço
                            double[] par = new double[3];
                            par[0] = parOrdenado[0] + (parOrdenado[0]/10);
                            par[1] = parOrdenado[1];
                            par[2] = parOrdenado[2] + contador;
                            draw.drawText("" + MatrizDSD.get((SalvaAttDsdInt.size() * number)+k), par);
                            }
                    }
                    
                    
            } else {

                ArrayList<Object> Xlist = new ArrayList<>();
                ArrayList<Object> Ylist = new ArrayList<>();
                
                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    for (int i = 0; i <= xl1Coord.size() - 1; i++) {
                        Xlist.add(Interface.plot2D.getAxis(0).getStringMap().get(xl1Coord.get(i)));
                    }
                } else {
                    Xlist.addAll(xl1Coord);
                    }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    for (int i = 0; i <= yl1Coord.size() - 1; i++) {
                        Ylist.add(Interface.plot2D.getAxis(1).getStringMap().get(yl1Coord.get(i)));
                    }
                } else {
                    Ylist.addAll(yl1Coord);
                    
                }
                double[] parOrdenado = new double[2];
                double[] parOrdenadoX = new double[2];
                double[] parOrdenadoY = new double[2];
                
                
                double MinX = 0.0;
                double MinY = 0.0;
                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    MinX = Collections.min(Interface.plot2D.getAxis(0).getStringMap().values());
                } else {
                    MinX = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    MinY = Collections.min(Interface.plot2D.getAxis(1).getStringMap().values());
                } else {
                    MinY = GetMinMaxValueY()[0];
                }
                

                    if (!xl1Coord.get(number).equals(null) || !xl1Coord.get(number).equals("null") || xl1Coord.get(number) != null) {
                            parOrdenado[0] = Double.parseDouble(Xlist.get(number).toString());
                            parOrdenado[1] = Double.parseDouble(Ylist.get(number).toString());
                            parOrdenadoX[0] = Double.parseDouble(Xlist.get(number).toString());
                            parOrdenadoX[1] = MinY; // Estava Funcionando Com o 0.0 no lugar do Min
                            parOrdenadoY[0] = MinX; // Aqui também tava o 0.0
                            parOrdenadoY[1] = Double.parseDouble(Ylist.get(number).toString());
//                            draw.drawText("" + Xlist.get(number).toString(), parOrdenado);
                            draw.drawCoordinate(parOrdenado);
//                            draw.drawText("" + Interface.plot2D.getAxis(0).getStringMap().get(xl1Coord.get(number)), parOrdenadoX);
                            draw.drawText("" + xl1Coord.get(number).toString(), parOrdenadoX);
                            draw.drawText("" + yl1Coord.get(number).toString(), parOrdenadoY);
                            
                            for (int k = 0; k < SalvaAttDsdInt.size(); k++) {
                            contador = k * ((parOrdenado[1]/100)); // Pegar a escala correta do XY 
                            double[] par = new double[2];
                            par[0] = parOrdenado[0] + (parOrdenado[0]/10);
                            par[1] = parOrdenado[1] + contador;
//                            draw.drawText("" + MatrizDSD.get((xl1Coord.size() * k)+(number-k)), par);
                            draw.drawText("" + MatrizDSD.get((SalvaAttDsdInt.size() * number)+k), par);
                                System.out.println("MatrizDSD " + ((xl1Coord.size() * k)+(number-k)));
                                System.out.println("MatrizDSD " + xl1Coord);
                                System.out.println("MatrizDSD " + yl1Coord);
                                System.out.println("MatrizDSD " + xl1);
                                System.out.println("MatrizDSD " + yl1);
                                System.out.println("MatrizDSD List" + MatrizDSD);
                            }
//                            draw.drawText("" +  MatrizDSD.get(number * xl1.size()), parOrdenado);
//                            draw.drawText("" + Ylist.get(number).toString(), parOrdenadoY);
                    }
        }
        
    }

    public void Quadrant1() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
        
        
        
        if (IsPlot3D) {
//            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//                sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//            } else {
//                sx = GetMinMaxValueX()[1];
//                sxi = GetMinMaxValueX()[0];
//            }
//            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//                sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//            } else {
//                sy = GetMinMaxValueY()[1];
//                syi = GetMinMaxValueY()[0];
//            }
//            if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//                sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//            } else {
//                sz = GetMinMaxValueZ()[1];
//                szi = GetMinMaxValueZ()[0];
//            }
            
                if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }


            FiltrarXDSD.clear();
            FiltrarYDSD.clear();
            FiltrarZDSD.clear();

            QLX = (sx - sxi) / 2 + sxi;
            QHX = sx;
            QLY = (sy - syi) / 2 + syi;
            QHY = sy;
            QLZ = szi;
            QHZ = (sz - szi) / 2 + szi;
            

            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);
            BCsListValues.add(QLZ);
            BCsListValues.add(QHZ);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);
            FiltrarZDSD.add(QLZ);
            FiltrarZDSD.add(QHZ);
            
            
                double[] label1 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2}; //Calcula o limite inicial de cada quadrante + seu limite final dividido por 2 para assim chegar na coordenada correta.
                double[] label2 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2}; // sz e sy estão em lugares trocados devido sua ordem também está trocada na hora de colocar os pontos no objeto XYZ(devido a limitações do Jmathplot o y e o z são trocados.
                double[] label3 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};// O Y e o Z controlam respectivamente altura e profundidade e o X a largura.
                double[] label4 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (szi + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};
                double[] label5 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2};
                double[] label6 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (sy + ((sy - syi) / 2 + syi)) / 2};
                double[] label7 = new double[]{(sx + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};
                double[] label8 = new double[]{(sxi + ((sx - sxi) / 2 + sxi)) / 2, (sz + ((sz - szi) / 2 + szi)) / 2, (syi + ((sy - syi) / 2 + syi)) / 2};


        } else {
            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                sxi = Collections.min(Interface.plot2D.getAxis(0).getStringMap().values());
                sx = Collections.max(Interface.plot2D.getAxis(0).getStringMap().values());
//            sx = Interface.plot2D.getAxis(0).getStringMap().size()-1; 
            } else {
                sx = GetMinMaxValueX()[1];
                sxi = GetMinMaxValueX()[0];
            }
            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                syi = Collections.min(Interface.plot2D.getAxis(1).getStringMap().values());
                sy = Collections.max(Interface.plot2D.getAxis(1).getStringMap().values());
//            sy = Interface.plot2D.getAxis(1).getStringMap().size()-1; 
            } else {
                sy = GetMinMaxValueY()[1];
                syi = GetMinMaxValueY()[0];
            }

            ////        if (FirstBlood == 0) {
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGG");
            QLX = sxi;
            QHX = (sx - sxi) / 2 + sxi;
            QLY = (sy - syi) / 2 + syi;
            QHY = sy;
//            FirstBlood++;
//        }else{
//            System.out.println("fipsfudhgasjvpa");
//            QLX = sxi;
//            QHX = QHX/2;
//            QLY = (QHY - QLY)/2 + QLY;
//            QHY = sy;
//        }

            FiltrarXDSD.clear();
            FiltrarYDSD.clear();
            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);
            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);

        }
        System.out.println("sxi hghfgdhfdgh" + sxi);
        System.out.println("sx dfghfdghfgh" + sx);
        System.out.println("syi fdghfgdhdfhgf " + syi);
        System.out.println("sy dfghfhgdgfhd " + sy);

        AllAxisChecked();
        IQuad++;
////        Map<Double, String> XHashMap = new HashMap<>();
////        for(Map.Entry<String, Double> entry : Interface.plot2D.getAxis(0).getStringMap().entrySet()){
////            XHashMap.put(entry.getValue(), entry.getKey());
////        }
////        Map<Double, String> YHashMap = new HashMap<>();
////        for(Map.Entry<String, Double> entry : Interface.plot2D.getAxis(1).getStringMap().entrySet()){
////            YHashMap.put(entry.getValue(), entry.getKey());
////        }
////        String XYs2 = new String();   
//        double[] XYp2 = new double[2];   
//        draw = new AWTDrawer2D(Interface.plot2D.plotCanvas);
//        draw.initGraphics((Graphics2D) Interface.plot2D.plotCanvas.getGraphics());
//        draw.canvas = Interface.plot2D.plotCanvas;
//        draw.canvas.initDrawer();
//        if (marcador>=0 && marcador < draw.canvas.getPlot(PlotNumber).getData().length) {    
//        XYp2[0] = draw.canvas.getPlot(PlotNumber).getData()[marcador][0];
//        XYp2[1] = draw.canvas.getPlot(PlotNumber).getData()[marcador][1];
////        XYs2 = XHashMap.get(XYp2[0]);
////        XYs2 += "\n"+YHashMap.get(XYp2[1]);
//        Interface.plot2D.plotCanvas.allowNote = true;   
//        Interface.plot2D.plotCanvas.allowNoteCoord = true;  
////        draw.drawText(Array.cat(reverseMapedData(XYp2)), XYp2);
////        draw.drawText(XYs2,XYp2);
////        Interface.plot2D.getPlot(PlotNumber).note(draw);
//        Interface.plot2D.getPlot(PlotNumber).noteCoord(draw, XYp2);
//        marcador++;
//        }else 
//            marcador = 0;
        
//           Interface.BtnsDetails();
//           Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());     
                
        
    }

    public void EstadoInicial() throws IOException, NumberFormatException, GrammarException {
        Parar();
        if (Interface.menu_Detalhes.isSelected()) { // Colocar o valor original dos QHLs antes de aplicar os detalhes
            Detalhes = false;
            AllAxisChecked();
            Interface.LimparPainel(Interface.desktopInteragir);
            Interface.BtnsDetails();
            Main.Interface.setContentPane(((Interface) Main.Interface).PainelIFC());
            (new ThreadDsd()).start();
        }
        //Checa quando está no zoom ou Mover ou Girar  
        if (BtnListener.EscalaEI) {
            if (IsPlot3D) {
                BtnListener.EscalaValor = 1.3;
                Interface.plot3D.setDefaultZoom(BtnListener.EscalaValor);
                Interface.plot3D.rotate(0, 0);
                Interface.EscalaAtual.setText("Escala Atual: " + BtnListener.EscalaValor);
            } else {
//                BtnListener.EscalaValor = 0;
//                Interface.plot2D.setDefaultZoom(BtnListener.EscalaValor);
//                Interface.plot2D.rotate(0, 0);
//                Interface.EscalaAtual.setText("Escala Atual: " + BtnListener.EscalaValor);
            }

        }
        if (BtnListener.MoverEI) {
            if (IsPlot3D) {
                Interface.plot3D.move(0, 0);
            } else {
                Interface.plot3D.move(0, 0);
            }

        }
        if (BtnListener.GirarEI) {
            Interface.plot3D.rotate(-0.0000001 * ThreadInteraction.E, 0);
            Interface.plot3D.rotate(0.0000001 * ThreadInteraction.D, 0);
            Interface.plot3D.rotate(0, -0.0000001 * ThreadInteraction.C);
            Interface.plot3D.rotate(0, 0.0000001 * ThreadInteraction.B);
            Interface.plot3D.rotate(Interface.plot3D.getX() - 0.0000001 * ThreadInteraction.T, Interface.plot3D.getY() - 0.0000001 * ThreadInteraction.T);
            Interface.plot3D.rotate(Interface.plot3D.getX() - 0.0000001 * ThreadInteraction.F, Interface.plot3D.getY());
        }

    }

    public void Parar() throws IOException, NumberFormatException, GrammarException {
        ThreadInteraction.Continuar = false;

    }

    public void BreadCrumbs1() throws IOException, NumberFormatException, GrammarException {
        Detalhes = false;
        AllAxisChecked();
        System.out.println("BCBCBCBCBCBCBCBCBCBC " + BCsListValues);
    }

    public void BreadCrumbs2() throws IOException, NumberFormatException, GrammarException {
        Detalhes = true;
        int EstadoDoIndice = IQuad - 2;
        int Jumper = 0;
        if (IsPlot3D) {
            Jumper = 7;
        } else {
            Jumper = 5;
        }

        QLX = BCsListValues.get(Jumper + 1);
        QHX = BCsListValues.get(Jumper + 2);
        QLY = BCsListValues.get(Jumper + 3);
        QHY = BCsListValues.get(Jumper + 4);

        FiltrarXDSD.clear();
        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);

        AllAxisChecked();
        System.out.println("BCBCBCBCBCBCBCBCBCBC " + BCsListValues);
    }

    public void BreadCrumbs3() throws IOException, NumberFormatException, GrammarException {
        Detalhes = true;
        int EstadoDoIndice = IQuad - 2;
        int Jumper = 0;
        if (IsPlot3D) {
            Jumper = 7;
        } else {
            Jumper = 5;
        }

        QLX = BCsListValues.get(Jumper + 1);
        QHX = BCsListValues.get(Jumper + 2);
        QLY = BCsListValues.get(Jumper + 3);
        QHY = BCsListValues.get(Jumper + 4);

        FiltrarXDSD.clear();
        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);

        AllAxisChecked();
        System.out.println("BCBCBCBCBCBCBCBCBCBC " + BCsListValues);

    }

    public void BreadCrumbs4() throws IOException, NumberFormatException, GrammarException {
        Detalhes = true;
        int EstadoDoIndice = IQuad - 2;
        int Jumper = 0;
        if (IsPlot3D) {
            Jumper = 7;
        } else {
            Jumper = 5;
        }

        QLX = BCsListValues.get(Jumper + 1);
        QHX = BCsListValues.get(Jumper + 2);
        QLY = BCsListValues.get(Jumper + 3);
        QHY = BCsListValues.get(Jumper + 4);

        FiltrarXDSD.clear();
        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);

        AllAxisChecked();
        System.out.println("BCBCBCBCBCBCBCBCBCBC " + BCsListValues);

    }

//    public void BreadCrumbsLogic() {
//        //Fazer a funcao de tirar screenshot e setar icone
//
//        if (Detalhes) {
//            int TempInput = 0;
//            File f = null;
//            while (true) {
//                f = new File("src/BreadCrumbs/" + TempInput + ".png");
//
//                if (f.exists()) {
//                    TempInput++;
//                } else {
//                    break;
//                }
//            }
//
//
//            try {
//                f.createNewFile();
//                System.out.println("Createjnvjnonfuanfiafjan " + f.getPath());
//                if (Vis3D.IsPlot3D) {
//                    Interface.plot3D.toGraphicFile(f);
//                } else {
//                    Interface.plot2D.toGraphicFile(f);
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(BtnListener.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//        // Fazer a logica da lista de breadcrumbs
//    }

    public void Quadrant2() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
        if (IsPlot3D) {
//            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//                sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//            } else {
//                sx = GetMinMaxValueX()[1];
//                sxi = GetMinMaxValueX()[0];
//            }
//            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//                sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//            } else {
//                sy = GetMinMaxValueY()[1];
//                syi = GetMinMaxValueY()[0];
//            }
//            if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//                sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//            } else {
//                sz = GetMinMaxValueZ()[1];
//                szi = GetMinMaxValueZ()[0];
//            }

if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }


            FiltrarXDSD.clear();
            FiltrarYDSD.clear();
            FiltrarZDSD.clear();

            QLX = sxi;
            QHX = (sx - sxi) / 2 + sxi;
            QLY = (sy - syi) / 2 + syi;
            QHY = sy;
            QLZ = szi;
            QHZ = (sz - szi) / 2 + szi;

            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);
            BCsListValues.add(QLZ);
            BCsListValues.add(QHZ);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);
            FiltrarZDSD.add(QLZ);
            FiltrarZDSD.add(QHZ);

        } else {
            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                sx = Interface.plot2D.getAxis(0).getStringMap().size() - 1;
            } else {
                sx = GetMinMaxValueX()[1];
                sxi = GetMinMaxValueX()[0];
            }
            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                sy = Interface.plot2D.getAxis(1).getStringMap().size() - 1;
            } else {
                sy = GetMinMaxValueY()[1];
                syi = GetMinMaxValueY()[0];
            }
            QLX = (sx - sxi) / 2 + sxi;
            QHX = sx;
            QLY = (sy - syi) / 2 + syi;
            QHY = sy;

            FiltrarXDSD.clear();
            FiltrarYDSD.clear();
            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);

        }


        AllAxisChecked();
        IQuad++;
////        Map<Double, String> XHashMap = new HashMap<>();
////        for(Map.Entry<String, Double> entry : Interface.plot2D.getAxis(0).getStringMap().entrySet()){
////            XHashMap.put(entry.getValue(), entry.getKey());
////        }
////        Map<Double, String> YHashMap = new HashMap<>();
////        for(Map.Entry<String, Double> entry : Interface.plot2D.getAxis(1).getStringMap().entrySet()){
////            YHashMap.put(entry.getValue(), entry.getKey());
////        }
////        String XYs2 = new String();   
//        double[] XYp2 = new double[2];   
//        draw = new AWTDrawer2D(Interface.plot2D.plotCanvas);
//        draw.initGraphics((Graphics2D) Interface.plot2D.plotCanvas.getGraphics());
//        draw.canvas = Interface.plot2D.plotCanvas;
//        draw.canvas.initDrawer();
//        if (marcador>=0 && marcador < draw.canvas.getPlot(PlotNumber).getData().length) {    
//        XYp2[0] = draw.canvas.getPlot(PlotNumber).getData()[marcador][0];
//        XYp2[1] = draw.canvas.getPlot(PlotNumber).getData()[marcador][1];
////        XYs2 = XHashMap.get(XYp2[0]);
////        XYs2 += "\n"+YHashMap.get(XYp2[1]);
//        Interface.plot2D.plotCanvas.allowNote = true;   
//        Interface.plot2D.plotCanvas.allowNoteCoord = true;  
////        draw.drawText(Array.cat(reverseMapedData(XYp2)), XYp2);
////        draw.drawText(XYs2,XYp2);
////        Interface.plot2D.getPlot(PlotNumber).note(draw);
//        Interface.plot2D.getPlot(PlotNumber).noteCoord(draw, XYp2);
//        marcador++;
//        }else 
//            marcador = 0;
    }

    public void Quadrant3() throws IOException, GrammarException {
        boolean FirstBlood = true;
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
        if (IsPlot3D) {
//            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//                sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
//                sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
//            } else {
//                sx = GetMinMaxValueX()[1];
//                sxi = GetMinMaxValueX()[0];
//            }
//            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//                szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
//                sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
//            } else {
//                sz = GetMinMaxValueZ()[1];
//                szi = GetMinMaxValueZ()[0];
//            }
//            if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//                syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
//                sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
//            } else {
//                sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
//                syi = GetMinMaxValueY()[0];
//            }
//            
            

              if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }


            FiltrarXDSD.clear();
            FiltrarYDSD.clear();
            FiltrarZDSD.clear();

            QLX = (sx - sxi) / 2 + sxi;
            QHX = sx;
//            QLY = (sy - syi) / 2 + syi;
            QLY = syi;
//            QHY = sy;
            QHY = (sy - syi) / 2 + syi;
            QLZ = szi;
            QHZ = (sz - szi) / 2 + szi;
            
//        QLX = (sx - sxi) / 2 + sxi;
//        QHX = sx;
//        QLY = syi;
//        QHY = (sy - syi) / 2 + syi;
//        QLZ = (sz - szi) / 2 + szi;
//        QHZ = sz;

            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);
            BCsListValues.add(QLZ);
            BCsListValues.add(QHZ);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);
            FiltrarZDSD.add(QLZ);
            FiltrarZDSD.add(QHZ);

        } else {
            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                sx = Interface.plot2D.getAxis(0).getStringMap().size() - 1;
            } else {
                sx = GetMinMaxValueX()[1];
                sxi = GetMinMaxValueX()[0];
            }
            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                sy = Interface.plot2D.getAxis(1).getStringMap().size() - 1;
            } else {
                sy = GetMinMaxValueY()[1];
                syi = GetMinMaxValueY()[0];
            }

            if (FirstBlood) {
                QLX = sxi;
                QHX = (sx - sxi) / 2 + sxi;
                QLY = syi;
                QHY = (sy - syi) / 2 + syi;
            } else {
                QLX = sxi;
                QHX = QHX / 2;
                QLY = syi;
                QHY = QHY / 2;
            }
            FiltrarXDSD.clear();
            FiltrarYDSD.clear();

            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);

        }



        AllAxisChecked();
        IQuad++;
    }

    public void Quadrant4() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
        FiltrarXDSD.clear();
        FiltrarYDSD.clear();
        FiltrarZDSD.clear();

        if (IsPlot3D) {
//            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//                sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//            } else {
//                sx = GetMinMaxValueX()[1];
//                sxi = GetMinMaxValueX()[0];
//            }
//            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//                sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//            } else {
//                sy = GetMinMaxValueY()[1];
//                syi = GetMinMaxValueY()[0];
//            }
//            if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//                sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//            } else {
//                sz = GetMinMaxValueZ()[1];
//                szi = GetMinMaxValueZ()[0];
//            }

if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }

            QLX = sxi;
            QHX = (sx - sxi) / 2 + sxi;
            QLY = syi;
            QHY = (sy - syi) / 2 + syi;
            QLZ = szi;
            QHZ = (sz - szi) / 2 + szi;

            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);
            BCsListValues.add(QLZ);
            BCsListValues.add(QHZ);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);
            FiltrarZDSD.add(QLZ);
            FiltrarZDSD.add(QHZ);

        } else {
            if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                sx = Interface.plot2D.getAxis(0).getStringMap().size() - 1;
            } else {
                sx = GetMinMaxValueX()[1];
                sxi = GetMinMaxValueX()[0];
            }
            if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                sy = Interface.plot2D.getAxis(1).getStringMap().size() - 1;
            } else {
                sy = GetMinMaxValueY()[1];
                syi = GetMinMaxValueY()[0];
            }

            QLX = (sx - sxi) / 2 + sxi;
            QHX = sx;
            QLY = syi;
            QHY = (sy - syi) / 2 + syi;

            double tempd = IQuad;
            BCsListValues.add(tempd);
            BCsListValues.add(QLX);
            BCsListValues.add(QHX);
            BCsListValues.add(QLY);
            BCsListValues.add(QHY);

            FiltrarXDSD.add(QLX);
            FiltrarXDSD.add(QHX);
            FiltrarYDSD.add(QLY);
            FiltrarYDSD.add(QHY);

        }



        AllAxisChecked();
        IQuad++;
    }

    public void Quadrant5() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;

//        sx = Interface.plot3D.getAxis(0).getStringMap().size()-1;
//        sy = Interface.plot3D.getAxis(1).getStringMap().size()-1;
//        sz = Interface.plot3D.getAxis(2).getStringMap().size()-1; 

//        if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//            sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//        } else {
//            sx = GetMinMaxValueX()[1];
//            sxi = GetMinMaxValueX()[0];
//        }
//        if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//            sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//        } else {
//            sy = GetMinMaxValueY()[1];
//            syi = GetMinMaxValueY()[0];
//        }
//        if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//            sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//        } else {
//            sz = GetMinMaxValueZ()[1];
//            szi = GetMinMaxValueZ()[0];
//        }

if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }

        QLX = (sx - sxi) / 2 + sxi;
        QHX = sx;
        QLY = (sy - syi) / 2 + syi;
        QHY = sy;
        QLZ = (sz - szi) / 2 + szi;
        QHZ = sz;



//    QLX = (sx - sxi)/2 + sxi;
//    QHX = sx;
//    QLY = syi;
//    QHY = (sy - syi)/2 + syi;


        FiltrarXDSD.clear();
        FiltrarYDSD.clear();
        FiltrarZDSD.clear();

        double tempd = IQuad;
        BCsListValues.add(tempd);
        BCsListValues.add(QLX);
        BCsListValues.add(QHX);
        BCsListValues.add(QLY);
        BCsListValues.add(QHY);
        BCsListValues.add(QLZ);
        BCsListValues.add(QHZ);

        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);
        FiltrarZDSD.add(QLZ);
        FiltrarZDSD.add(QHZ);
        IQuad++;
        AllAxisChecked();
    }

    public void Quadrant6() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
//        if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//            sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//        } else {
//            sx = GetMinMaxValueX()[1];
//            sxi = GetMinMaxValueX()[0];
//        }
//        if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//            sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//        } else {
//            sy = GetMinMaxValueY()[1];
//            syi = GetMinMaxValueY()[0];
//        }
//        if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//            sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//        } else {
//            sz = GetMinMaxValueZ()[1];
//            szi = GetMinMaxValueZ()[0];
//        }

if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }

        QLX = sxi;
        QHX = (sx - sxi) / 2 + sxi;
        QLY = (sy - syi) / 2 + syi;
        QHY = sy;
        QLZ = (sz - szi) / 2 + szi;
        QHZ = sz;



//    QLX = (sx - sxi)/2 + sxi;
//    QHX = sx;
//    QLY = syi;
//    QHY = (sy - syi)/2 + syi;


        FiltrarXDSD.clear();
        FiltrarYDSD.clear();
        FiltrarZDSD.clear();

        double tempd = IQuad;
        BCsListValues.add(tempd);
        BCsListValues.add(QLX);
        BCsListValues.add(QHX);
        BCsListValues.add(QLY);
        BCsListValues.add(QHY);
        BCsListValues.add(QLZ);
        BCsListValues.add(QHZ);

        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);
        FiltrarZDSD.add(QLZ);
        FiltrarZDSD.add(QHZ);
        IQuad++;
        AllAxisChecked();
    }

    public void Quadrant7() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
//        if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//            sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//        } else {
//            sx = GetMinMaxValueX()[1];
//            sxi = GetMinMaxValueX()[0];
//        }
//        if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//            sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//        } else {
//            sy = GetMinMaxValueY()[1];
//            syi = GetMinMaxValueY()[0];
//        }
//        if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//            sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//        } else {
//            sz = GetMinMaxValueZ()[1];
//            szi = GetMinMaxValueZ()[0];
//        }

        if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }

        QLX = (sx - sxi) / 2 + sxi;
        QHX = sx;
        QLY = syi;
        QHY = (sy - syi) / 2 + syi;
        QLZ = (sz - szi) / 2 + szi;
        QHZ = sz;



//    QLX = (sx - sxi)/2 + sxi;
//    QHX = sx;
//    QLY = syi;
//    QHY = (sy - syi)/2 + syi;


        FiltrarXDSD.clear();
        FiltrarYDSD.clear();
        FiltrarZDSD.clear();

        double tempd = IQuad;
        BCsListValues.add(tempd);
        BCsListValues.add(QLX);
        BCsListValues.add(QHX);
        BCsListValues.add(QLY);
        BCsListValues.add(QHY);
        BCsListValues.add(QLZ);
        BCsListValues.add(QHZ);

        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);
        FiltrarZDSD.add(QLZ);
        FiltrarZDSD.add(QHZ);
        IQuad++;
        AllAxisChecked();
    }

    public void Quadrant8() throws IOException, NumberFormatException, GrammarException {
        double sx;
        double sxi = 0;
        double sy;  // fim do quadrante y
        double syi = 0; // inicio do quadrante y
        double sz = 0;
        double szi = 0;
//        if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//            sx = Interface.plot3D.getAxis(0).getStringMap().size() - 1;
//        } else {
//            sx = GetMinMaxValueX()[1];
//            sxi = GetMinMaxValueX()[0];
//        }
//        if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//            sy = Interface.plot3D.getAxis(1).getStringMap().size() - 1;
//        } else {
//            sy = GetMinMaxValueY()[1];
//            syi = GetMinMaxValueY()[0];
//        }
//        if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
//            sz = Interface.plot3D.getAxis(2).getStringMap().size() - 1;
//        } else {
//            sz = GetMinMaxValueZ()[1];
//            szi = GetMinMaxValueZ()[0];
//        }

if (Main.att.AttTypes().get(indexX).equals("STRING")) {
                    sxi = Collections.min(Interface.plot3D.getAxis(0).getStringMap().values());
                    sx = Collections.max(Interface.plot3D.getAxis(0).getStringMap().values());
                } else {
                    sx = GetMinMaxValueX()[1];
                    sxi = GetMinMaxValueX()[0];
                }
                if (Main.att.AttTypes().get(indexZ).equals("STRING")) {
                    szi = Collections.min(Interface.plot3D.getAxis(1).getStringMap().values());
                    sz = Collections.max(Interface.plot3D.getAxis(1).getStringMap().values());
                } else {
                    sz = GetMinMaxValueZ()[1];
                    szi = GetMinMaxValueZ()[0];
                }
                if (Main.att.AttTypes().get(indexY).equals("STRING")) {
                    syi = Collections.min(Interface.plot3D.getAxis(2).getStringMap().values());
                    sy = Collections.max(Interface.plot3D.getAxis(2).getStringMap().values());
                } else {
                    sy = GetMinMaxValueY()[1]; // O Z recebe o Y pois a forma como o JmathPlot organiza os dados é com Y e o Z invertidos por isso tenho que fazer esta troca
                    syi = GetMinMaxValueY()[0];
                }

        QLX = sxi;
        QHX = (sx - sxi) / 2 + sxi;
        QLY = syi;
        QHY = (sy - syi) / 2 + syi;
        QLZ = (sz - szi) / 2 + szi;
        QHZ = sz;



//    QLX = (sx - sxi)/2 + sxi;
//    QHX = sx;
//    QLY = syi;
//    QHY = (sy - syi)/2 + syi;


        FiltrarXDSD.clear();
        FiltrarYDSD.clear();
        FiltrarZDSD.clear();

        double tempd = IQuad;
        BCsListValues.add(tempd);
        BCsListValues.add(QLX);
        BCsListValues.add(QHX);
        BCsListValues.add(QLY);
        BCsListValues.add(QHY);
        BCsListValues.add(QLZ);
        BCsListValues.add(QHZ);

        FiltrarXDSD.add(QLX);
        FiltrarXDSD.add(QHX);
        FiltrarYDSD.add(QLY);
        FiltrarYDSD.add(QHY);
        FiltrarZDSD.add(QLZ);
        FiltrarZDSD.add(QHZ);
        IQuad++;
        AllAxisChecked();
    }

    public int VerificaSelecionadoPainelLegenda() {
        int memoryPoint = 0;
        for (int i = 0; i < Interface.plot2D.getPlots().size(); i++) {
            if (Interface.plot2D.getPlot(i).noted) {
                memoryPoint = i;
            }

        }
        if (memoryPoint != PontoSelecionado) {
            Interface.plot2D.plotLegend.nonote(PontoSelecionado);
            PontoSelecionado = memoryPoint;
        }
        Interface.plot2D.plotLegend.updateLegends();
        return PontoSelecionado;
    }

    public void DSD() throws FileNotFoundException, IOException {
//    //Detalhes usando os quadrantes do Simon
//    Interface.plot2D.plotCanvas.allowNote = true;   
//    Interface.plot2D.plotCanvas.allowNoteCoord = true;  
//    Interface.plot3D.plotCanvas.allowNote = true;   
//    Interface.plot3D.plotCanvas.allowNoteCoord = true;  
//    
//    if(IsPlot3D) {
//    double sx = Interface.plot3D.getAxis(0).getStringMap().size()-1;
//    double sy = Interface.plot3D.getAxis(1).getStringMap().size()-1;    
//    double sz = Interface.plot3D.getAxis(2).getStringMap().size()-1;
//    draw = new AWTDrawer3D(Interface.plot3D.plotCanvas);
//    draw.initGraphics((Graphics2D) Interface.plot3D.plotCanvas.getGraphics());
//    draw.canvas = Interface.plot3D.plotCanvas;
//    draw.canvas.initDrawer();
//    
//    double[][] pc =  new double[16][3];
//    double[][] pc2 = new double[16][3];
//    double[][] pc3 = new double[16][3];
//    double[][] pc4 = new double[16][3];
//    double[][] pc5 = new double[16][3];
//    double[][] pc6 = new double[16][3];
//    double[][] pc7 = new double[16][3];
//    double[][] pc8 = new double[16][3];
//
//    pc[0][0] = 0;
//    pc[0][1] = sy/2;
//    pc[0][2] = 0;
//    pc[1][0] = sx/2;
//    pc[1][1] = sy/2;
//    pc[1][2] = 0;
//    pc[2][0] = sx/2;
//    pc[2][1] = sy;
//    pc[2][2] = 0;
//    pc[3][0] = 0;
//    pc[3][1] = sy;
//    pc[3][2] = 0;
//    pc[4][0] = pc[0][0];
//    pc[4][1] = pc[0][1];
//    pc[4][2] = pc[0][2];
//    pc[5][0] = pc[3][0];
//    pc[5][1] = pc[3][1];
//    pc[5][2] = pc[3][2];
//    pc[6][0] = 0;
//    pc[6][1] = sy;
//    pc[6][2] = sz/2;
//    pc[7][0] = sx/2;
//    pc[7][1] = sy;
//    pc[7][2] = sz/2;
//    pc[8][0] = pc[2][0];
//    pc[8][1] = pc[2][1];
//    pc[8][2] = pc[2][2];
//    pc[9][0] = pc[7][0];
//    pc[9][1] = pc[7][1];
//    pc[9][2] = pc[7][2];
//    pc[10][0] = sx/2;
//    pc[10][1] = sy/2;
//    pc[10][2] = sz/2;
//    pc[11][0] = pc[1][0];
//    pc[11][1] = pc[1][1];
//    pc[11][2] = pc[1][2];
//    pc[12][0] = pc[10][0];
//    pc[12][1] = pc[10][1];
//    pc[12][2] = pc[10][2];
//    pc[13][0] = 0;
//    pc[13][1] = sy/2;
//    pc[13][2] = sz/2;
//    pc[14][0] = pc[6][0];
//    pc[14][1] = pc[6][1];
//    pc[14][2] = pc[6][2];
//    pc[15][0] = pc[13][0];
//    pc[15][1] = pc[13][1];
//    pc[15][2] = pc[13][2];
//    
//    pc2[0][0] = sx/2;
//    pc2[0][1] = sy/2;
//    pc2[0][2] = 0;
//    pc2[1][0] = sx;
//    pc2[1][1] = sy/2;
//    pc2[1][2] = 0;
//    pc2[2][0] = sx;
//    pc2[2][1] = sy;
//    pc2[2][2] = 0;
//    pc2[3][0] = sx/2;
//    pc2[3][1] = sy;
//    pc2[3][2] = 0;
//    pc2[4][0] = pc2[0][0];
//    pc2[4][1] = pc2[0][1];
//    pc2[4][2] = pc2[0][2];
//    pc2[5][0] = pc2[3][0];
//    pc2[5][1] = pc2[3][1];
//    pc2[5][2] = pc2[3][2];
//    pc2[6][0] = sx/2;
//    pc2[6][1] = sy;
//    pc2[6][2] = sz/2;
//    pc2[7][0] = sx;
//    pc2[7][1] = sy;
//    pc2[7][2] = sz/2;
//    pc2[8][0] = pc2[2][0];
//    pc2[8][1] = pc2[2][1];
//    pc2[8][2] = pc2[2][2];
//    pc2[9][0] = pc2[7][0];
//    pc2[9][1] = pc2[7][1];
//    pc2[9][2] = pc2[7][2];
//    pc2[10][0] = sx;
//    pc2[10][1] = sy/2;
//    pc2[10][2] = sz/2;
//    pc2[11][0] = pc2[1][0];
//    pc2[11][1] = pc2[1][1];
//    pc2[11][2] = pc2[1][2];
//    pc2[12][0] = pc2[10][0];
//    pc2[12][1] = pc2[10][1];
//    pc2[12][2] = pc2[10][2];
//    pc2[13][0] = sx/2;
//    pc2[13][1] = sy/2;
//    pc2[13][2] = sz/2;
//    pc2[14][0] = pc2[6][0];
//    pc2[14][1] = pc2[6][1];
//    pc2[14][2] = pc2[6][2];
//    pc2[15][0] = pc2[13][0];
//    pc2[15][1] = pc2[13][1];
//    pc2[15][2] = pc2[13][2];
//    
//    pc3[0][0] = sx/2;
//    pc3[0][1] = sy/2;
//    pc3[0][2] = sz/2;
//    pc3[1][0] = sx;
//    pc3[1][1] = sy/2;
//    pc3[1][2] = sz/2;
//    pc3[2][0] = sx;
//    pc3[2][1] = sy;
//    pc3[2][2] = sz/2;
//    pc3[3][0] = sx/2;
//    pc3[3][1] = sy;
//    pc3[3][2] = sz/2;
//    pc3[4][0] = pc3[0][0];
//    pc3[4][1] = pc3[0][1];
//    pc3[4][2] = pc3[0][2];
//    pc3[5][0] = pc3[3][0];
//    pc3[5][1] = pc3[3][1];
//    pc3[5][2] = pc3[3][2];
//    pc3[6][0] = sx/2;
//    pc3[6][1] = sy;
//    pc3[6][2] = sz;
//    pc3[7][0] = sx;
//    pc3[7][1] = sy;
//    pc3[7][2] = sz;
//    pc3[8][0] = pc3[2][0];
//    pc3[8][1] = pc3[2][1];
//    pc3[8][2] = pc3[2][2];
//    pc3[9][0] = pc3[7][0];
//    pc3[9][1] = pc3[7][1];
//    pc3[9][2] = pc3[7][2];
//    pc3[10][0] = sx;
//    pc3[10][1] = sy/2;
//    pc3[10][2] = sz;
//    pc3[11][0] = pc3[1][0];
//    pc3[11][1] = pc3[1][1];
//    pc3[11][2] = pc3[1][2];
//    pc3[12][0] = pc3[10][0];
//    pc3[12][1] = pc3[10][1];
//    pc3[12][2] = pc3[10][2];
//    pc3[13][0] = sx/2;
//    pc3[13][1] = sy/2;
//    pc3[13][2] = sz;
//    pc3[14][0] = pc3[6][0];
//    pc3[14][1] = pc3[6][1];
//    pc3[14][2] = pc3[6][2];
//    pc3[15][0] = pc3[13][0];
//    pc3[15][1] = pc3[13][1];
//    pc3[15][2] = pc3[13][2];
//    
//    pc4[0][0] = 0;
//    pc4[0][1] = sy/2;
//    pc4[0][2] = sz/2;
//    pc4[1][0] = sx/2;
//    pc4[1][1] = sy/2;
//    pc4[1][2] = sz/2;
//    pc4[2][0] = sx/2;
//    pc4[2][1] = sy;
//    pc4[2][2] = sz/2;
//    pc4[3][0] = 0;
//    pc4[3][1] = sy;
//    pc4[3][2] = sz/2;
//    pc4[4][0] = pc4[0][0];
//    pc4[4][1] = pc4[0][1];
//    pc4[4][2] = pc4[0][2];
//    pc4[5][0] = pc4[3][0];
//    pc4[5][1] = pc4[3][1];
//    pc4[5][2] = pc4[3][2];
//    pc4[6][0] = 0;
//    pc4[6][1] = sy;
//    pc4[6][2] = sz;
//    pc4[7][0] = sx/2;
//    pc4[7][1] = sy;
//    pc4[7][2] = sz;
//    pc4[8][0] = pc4[2][0];
//    pc4[8][1] = pc4[2][1];
//    pc4[8][2] = pc4[2][2];
//    pc4[9][0] = pc4[7][0];
//    pc4[9][1] = pc4[7][1];
//    pc4[9][2] = pc4[7][2];
//    pc4[10][0] = sx/2;
//    pc4[10][1] = sy/2;
//    pc4[10][2] = sz;
//    pc4[11][0] = pc4[1][0];
//    pc4[11][1] = pc4[1][1];
//    pc4[11][2] = pc4[1][2];
//    pc4[12][0] = pc4[10][0];
//    pc4[12][1] = pc4[10][1];
//    pc4[12][2] = pc4[10][2];
//    pc4[13][0] = 0;
//    pc4[13][1] = sy/2;
//    pc4[13][2] = sz;
//    pc4[14][0] = pc4[6][0];
//    pc4[14][1] = pc4[6][1];
//    pc4[14][2] = pc4[6][2];
//    pc4[15][0] = pc4[13][0];
//    pc4[15][1] = pc4[13][1];
//    pc4[15][2] = pc4[13][2];  
//    
//    //PC 5 Ok
//    pc5[0][0] = 0;
//    pc5[0][1] = 0;
//    pc5[0][2] = 0;
//    pc5[1][0] = sx/2;
//    pc5[1][1] = 0;
//    pc5[1][2] = 0;
//    pc5[2][0] = sx/2;
//    pc5[2][1] = sy/2;
//    pc5[2][2] = 0;
//    pc5[3][0] = 0;
//    pc5[3][1] = sy/2;
//    pc5[3][2] = 0;
//    pc5[4][0] = pc5[0][0];
//    pc5[4][1] = pc5[0][1];
//    pc5[4][2] = pc5[0][2];
//    pc5[5][0] = pc5[3][0];
//    pc5[5][1] = pc5[3][1];
//    pc5[5][2] = pc5[3][2];
//    pc5[6][0] = 0;
//    pc5[6][1] = sy/2;
//    pc5[6][2] = sz/2;
//    pc5[7][0] = sx/2;
//    pc5[7][1] = sy/2;
//    pc5[7][2] = sz/2;
//    pc5[8][0] = pc5[2][0];
//    pc5[8][1] = pc5[2][1];
//    pc5[8][2] = pc5[2][2];
//    pc5[9][0] = pc5[7][0];
//    pc5[9][1] = pc5[7][1];
//    pc5[9][2] = pc5[7][2];
//    pc5[10][0] = sx/2;
//    pc5[10][1] = 0;
//    pc5[10][2] = sz/2;
//    pc5[11][0] = pc5[1][0];
//    pc5[11][1] = pc5[1][1];
//    pc5[11][2] = pc5[1][2];
//    pc5[12][0] = pc5[10][0];
//    pc5[12][1] = pc5[10][1];
//    pc5[12][2] = pc5[10][2];
//    pc5[13][0] = 0;
//    pc5[13][1] = 0;
//    pc5[13][2] = sz/2;
//    pc5[14][0] = pc5[6][0];
//    pc5[14][1] = pc5[6][1];
//    pc5[14][2] = pc5[6][2];
//    pc5[15][0] = pc5[13][0];
//    pc5[15][1] = pc5[13][1];
//    pc5[15][2] = pc5[13][2];
//    
//    //PC 6 OK
//    pc6[0][0] = sx/2;
//    pc6[0][1] = 0;
//    pc6[0][2] = 0;
//    pc6[1][0] = sx;
//    pc6[1][1] = 0;
//    pc6[1][2] = 0;
//    pc6[2][0] = sx;
//    pc6[2][1] = sy/2;
//    pc6[2][2] = 0;
//    pc6[3][0] = sx/2;
//    pc6[3][1] = sy/2;
//    pc6[3][2] = 0;
//    pc6[4][0] = pc6[0][0];
//    pc6[4][1] = pc6[0][1];
//    pc6[4][2] = pc6[0][2];
//    pc6[5][0] = pc6[3][0];
//    pc6[5][1] = pc6[3][1];
//    pc6[5][2] = pc6[3][2];
//    pc6[6][0] = sx/2;
//    pc6[6][1] = sy/2;
//    pc6[6][2] = sz/2;
//    pc6[7][0] = sx;
//    pc6[7][1] = sy/2;
//    pc6[7][2] = sz/2;
//    pc6[8][0] = pc6[2][0];
//    pc6[8][1] = pc6[2][1];
//    pc6[8][2] = pc6[2][2];
//    pc6[9][0] = pc6[7][0];
//    pc6[9][1] = pc6[7][1];
//    pc6[9][2] = pc6[7][2];
//    pc6[10][0] = sx;
//    pc6[10][1] = 0;
//    pc6[10][2] = sz/2;
//    pc6[11][0] = pc6[1][0];
//    pc6[11][1] = pc6[1][1];
//    pc6[11][2] = pc6[1][2];
//    pc6[12][0] = pc6[10][0];
//    pc6[12][1] = pc6[10][1];
//    pc6[12][2] = pc6[10][2];
//    pc6[13][0] = sx/2;
//    pc6[13][1] = 0;
//    pc6[13][2] = sz/2;
//    pc6[14][0] = pc6[6][0];
//    pc6[14][1] = pc6[6][1];
//    pc6[14][2] = pc6[6][2];
//    pc6[15][0] = pc6[13][0];
//    pc6[15][1] = pc6[13][1];
//    pc6[15][2] = pc6[13][2];
//
//    //PC 7 OK
//    pc7[0][0] = sx/2;
//    pc7[0][1] = 0;
//    pc7[0][2] = sz/2;
//    pc7[1][0] = sx;
//    pc7[1][1] = 0;
//    pc7[1][2] = sz/2;
//    pc7[2][0] = sx;
//    pc7[2][1] = sy/2;
//    pc7[2][2] = sz/2;
//    pc7[3][0] = sx/2;
//    pc7[3][1] = sy/2;
//    pc7[3][2] = sz/2;
//    pc7[4][0] = pc7[0][0];
//    pc7[4][1] = pc7[0][1];
//    pc7[4][2] = pc7[0][2];
//    pc7[5][0] = pc7[3][0];
//    pc7[5][1] = pc7[3][1];
//    pc7[5][2] = pc7[3][2];
//    pc7[6][0] = sx/2;
//    pc7[6][1] = sy/2;
//    pc7[6][2] = sz;
//    pc7[7][0] = sx;
//    pc7[7][1] = sy/2;
//    pc7[7][2] = sz;
//    pc7[8][0] = pc7[2][0];
//    pc7[8][1] = pc7[2][1];
//    pc7[8][2] = pc7[2][2];
//    pc7[9][0] = pc7[7][0];
//    pc7[9][1] = pc7[7][1];
//    pc7[9][2] = pc7[7][2];
//    pc7[10][0] = sx;
//    pc7[10][1] = 0;
//    pc7[10][2] = sz;
//    pc7[11][0] = pc7[1][0];
//    pc7[11][1] = pc7[1][1];
//    pc7[11][2] = pc7[1][2];
//    pc7[12][0] = pc7[10][0];
//    pc7[12][1] = pc7[10][1];
//    pc7[12][2] = pc7[10][2];
//    pc7[13][0] = sx/2;
//    pc7[13][1] = 0;
//    pc7[13][2] = sz;
//    pc7[14][0] = pc7[6][0];
//    pc7[14][1] = pc7[6][1];
//    pc7[14][2] = pc7[6][2];
//    pc7[15][0] = pc7[13][0];
//    pc7[15][1] = pc7[13][1];
//    pc7[15][2] = pc7[13][2];
//  
//    //PC 8 Ok
//    pc8[0][0] = 0;
//    pc8[0][1] = 0;
//    pc8[0][2] = sz/2;
//    pc8[1][0] = sx/2;
//    pc8[1][1] = 0;
//    pc8[1][2] = sz/2;
//    pc8[2][0] = sx/2;
//    pc8[2][1] = sy/2;
//    pc8[2][2] = sz/2;
//    pc8[3][0] = 0;
//    pc8[3][1] = sy/2;
//    pc8[3][2] = sz/2;
//    pc8[4][0] = pc8[0][0];
//    pc8[4][1] = pc8[0][1];
//    pc8[4][2] = pc8[0][2];
//    pc8[5][0] = pc8[3][0];
//    pc8[5][1] = pc8[3][1];
//    pc8[5][2] = pc8[3][2];
//    pc8[6][0] = 0;
//    pc8[6][1] = sy/2;
//    pc8[6][2] = sz;
//    pc8[7][0] = sx/2;
//    pc8[7][1] = sy/2;
//    pc8[7][2] = sz;
//    pc8[8][0] = pc8[2][0];
//    pc8[8][1] = pc8[2][1];
//    pc8[8][2] = pc8[2][2];
//    pc8[9][0] = pc8[7][0];
//    pc8[9][1] = pc8[7][1];
//    pc8[9][2] = pc8[7][2];
//    pc8[10][0] = sx/2;
//    pc8[10][1] = 0;
//    pc8[10][2] = sz;
//    pc8[11][0] = pc8[1][0];
//    pc8[11][1] = pc8[1][1];
//    pc8[11][2] = pc8[1][2];
//    pc8[12][0] = pc8[10][0];
//    pc8[12][1] = pc8[10][1];
//    pc8[12][2] = pc8[10][2];
//    pc8[13][0] = 0;
//    pc8[13][1] = 0;
//    pc8[13][2] = sz;
//    pc8[14][0] = pc8[6][0];
//    pc8[14][1] = pc8[6][1];
//    pc8[14][2] = pc8[6][2];
//    pc8[15][0] = pc8[13][0];
//    pc8[15][1] = pc8[13][1];
//    pc8[15][2] = pc8[13][2];    
//    
//    draw.drawPolygon(pc);
//    draw.drawPolygon(pc2);
//    draw.drawPolygon(pc3);
//    draw.drawPolygon(pc4);
//    draw.drawPolygon(pc5);
//    draw.drawPolygon(pc6);
//    draw.drawPolygon(pc7);
//    draw.drawPolygon(pc8);
//
//    }else{
//        
//    draw = new AWTDrawer2D(Interface.plot2D.plotCanvas);
//    draw.initGraphics((Graphics2D) Interface.plot2D.plotCanvas.getGraphics());
//    draw.canvas = Interface.plot2D.plotCanvas;
//    draw.canvas.initDrawer();
//    double sx;
//    double sxi = 0;
//    double sy;  // fim do quadrante y
//    double syi = 0; // inicio do quadrante y
//        if (Main.att.AttTypes().get(indexX).equals("STRING")) {
//            sx = Interface.plot2D.getAxis(0).getStringMap().size()-1; 
//        }else{
//            sx = GetMinMaxValueX()[1];
//            sxi = GetMinMaxValueX()[0];
//        }
//        if (Main.att.AttTypes().get(indexY).equals("STRING")) {
//            sy = Interface.plot2D.getAxis(1).getStringMap().size()-1; 
//        }else{
//            sy = GetMinMaxValueY()[1];
//            syi = GetMinMaxValueY()[0];    
//        }
//
//        
//    double[] label1 = new double[2];
//    double[] label2 = new double[2];
//    double[] label3 = new double[2];
//    double[] label4 = new double[2];
//    
//    
//    double[][] pc = new double[4][2];
//    pc[0][0] = sxi;
//    pc[0][1] = sy;
//    pc[1][0] = (sx - sxi)/2 + sxi;
//    pc[1][1] = sy;
//    pc[2][0] = (sx - sxi)/2 + sxi;
//    pc[2][1] = (sy - syi)/2 + syi;
//    pc[3][0] = sxi;
//    pc[3][1] = (sy - syi)/2 + syi;;
////    double[][] pc = new double[4][2];
////    pc[0][0] = 0;
////    pc[0][1] = sy;
////    pc[1][0] = sx/2;
////    pc[1][1] = sy;
////    pc[2][0] = sx/2;
////    pc[2][1] = sy/2;
////    pc[3][0] = 0;
////    pc[3][1] = sy/2;
//    label1[0] = (pc[1][0] - pc[0][0])/2;
//    label1[1] = (pc[1][1] + pc[2][1])/2;
//    
//    double[][] pc2 = new double[4][2];
//    pc2[0][0] = (sx - sxi)/2 + sxi;;
//    pc2[0][1] = sy;
//    pc2[1][0] = sx;
//    pc2[1][1] = sy;
//    pc2[2][0] = sx;
//    pc2[2][1] = (sy - syi)/2 + syi;;
//    pc2[3][0] = (sx - sxi)/2 + sxi;;
//    pc2[3][1] = (sy - syi)/2 + syi;;
////    double[][] pc2 = new double[4][2];
////    pc2[0][0] = sx/2;
////    pc2[0][1] = sy;
////    pc2[1][0] = sx;
////    pc2[1][1] = sy;
////    pc2[2][0] = sx;
////    pc2[2][1] = sy/2;
////    pc2[3][0] = sx/2;
////    pc2[3][1] = sy/2;
//    label2[0] = (pc2[1][0] + pc2[0][0])/2;
//    label2[1] = (pc2[1][1] + pc2[2][1])/2;
//    
//    double[][] pc3 = new double[4][2];
//    pc3[0][0] = sxi;
//    pc3[0][1] = (sy - syi)/2 + syi;;
//    pc3[1][0] = (sx - sxi)/2 + sxi;;
//    pc3[1][1] = (sy - syi)/2 + syi;;
//    pc3[2][0] = (sx - sxi)/2 + sxi;;
//    pc3[2][1] = syi;
//    pc3[3][0] = sxi;
//    pc3[3][1] = syi;
////    double[][] pc3 = new double[4][2];
////    pc3[0][0] = 0;
////    pc3[0][1] = sy/2;
////    pc3[1][0] = sx/2;
////    pc3[1][1] = sy/2;
////    pc3[2][0] = sx/2;
////    pc3[2][1] = 0;
////    pc3[3][0] = 0;
////    pc3[3][1] = 0;
//    
//    label3[0] = (pc3[1][0] - pc3[0][0])/2;
//    label3[1] = (pc3[1][1] - pc3[2][1])/2;
//    
//    double[][] pc4 = new double[4][2];
//    pc4[0][0] = (sx - sxi)/2 + sxi;
//    pc4[0][1] = (sy - syi)/2 + syi;
//    pc4[1][0] = sx;
//    pc4[1][1] = (sy - syi)/2 + syi;
//    pc4[2][0] = sx;
//    pc4[2][1] = syi;
//    pc4[3][0] = (sx - sxi)/2 + sxi;
//    pc4[3][1] = syi;
//    
//    label4[0] = (pc4[1][0] + pc4[0][0])/2;
//    label4[1] = (pc4[1][1] - pc4[2][1])/2;
////    double[][] pc4 = new double[4][2];
////    pc4[0][0] = sx/2;
////    pc4[0][1] = sy/2;
////    pc4[1][0] = sx;
////    pc4[1][1] = sy/2;
////    pc4[2][0] = sx;
////    pc4[2][1] = 0;
////    pc4[3][0] = sx/2;
////    pc4[3][1] = 0;
//    
//    draw.drawPolygon(pc);
//    draw.drawPolygon(pc2);
//    draw.drawPolygon(pc3);
//    draw.drawPolygon(pc4);
//    draw.setFont(new Font(draw.getFont().getFontName(),Font.PLAIN,48));
//    draw.drawText("1", label1);
//    draw.drawText("2", label2);
//    draw.drawText("3", label3);
//    draw.drawText("4", label4);
//    
//    }
//    
//    
////    int[] origin = {(int)pc[0][0], (int)pc[1][0], (int)pc[0][1],(int)pc[1][1]};
////    int[] origin = { min(mouseClick[0], mouseCurent[0]), min(mouseClick[1], mouseCurent[1]) };
////    double[] ratio = { Math.abs((double) (pc[1][0] - pc[0][0]) / (double) getWidth()),
////    Math.abs((double) (pc[1][1] - pc[0][1]) / (double) getHeight())};
////    draw.canvas.setActionMode(0);
////    draw.dilate(origin, ratio);
//    
////    draw.drawLineBase(rC);
////    draw.drawCoordinate(XYp2);
    }

    public void Scenario1() throws NumberFormatException, FileNotFoundException, IOException, GrammarException, PropertyVetoException { //Colocar todos os cenários que vão ser avaliados nos testes
    	IsPlot3D = true;
        PlotBaseX(0, "Marca");
        PlotBaseY(14, "Valor$");
        PlotBaseZ(15, "Ano");
    	
    
    }
    public void Scenario2() throws NumberFormatException, FileNotFoundException, IOException, GrammarException, PropertyVetoException { //Colocar todos os cenários que vão ser avaliados nos testes
//Este cenário representará Detalhes Sobre Demanda 3D
    	IsPlot3D = true;
        PlotBaseX(0, "Marca");
        PlotBaseY(14, "Valor$");
//        PlotBaseZ(15, "Ano");
        PlotCor(11, "Cilindros");
        PlotForma(5, "Tipo");
        PlotTamanho(6, "Tração");
   
    }
    public void Scenario3() throws NumberFormatException, FileNotFoundException, IOException, GrammarException, PropertyVetoException { //Colocar todos os cenários que vão ser avaliados nos testes
//Este cenário representará Navegação 2D
        IsPlot3D = true;
        PlotBaseX(0, "Marca");
        PlotBaseY(14, "Valor$");
        PlotBaseZ(15, "Ano");
        PlotCor(11, "Cilindros");
        PlotForma(5, "Tipo");
        PlotTamanho(6, "Tração");
    }
    public void Scenario4() throws NumberFormatException, FileNotFoundException, IOException, GrammarException, PropertyVetoException { //Colocar todos os cenários que vão ser avaliados nos testes
//Este cenário representará Navegação 3D
        IsPlot3D = true;
   
    }
}