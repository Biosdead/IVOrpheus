package IVOrpheus2Final;
import static IVOrpheus2Final.Vis3D.IsPlot3D;
import static org.math.array.StatisticSample.randomNormal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import org.math.plot.Plot2DPanel; //

import org.math.plot.Plot3DPanel;//
import org.math.plot.PlotPanel;//
import org.math.plot.plots.LayerPlot;//
import org.math.plot.render.AbstractDrawer; //

public class Interface extends JFrame {
        //InfoVis Module
        public static Vis3D infoVisModule;
	//Criando os btns
	public static JButton Confirmar,Cancelar,Quadrant1,Quadrant2,Interagir,Configurar,Filtrar,
	Cor,Forma,Tamanho,EixoX,EixoY,EixoZ,Mover,Escala,Girar,Aumentar,Diminuir,Esquerda
	,Direita,Cima,Baixo,Frente,Tras,InicioBtn,FimBtn,ConfirmarFiltro,ConfirmarCaptura, Quadrant3, Quadrant4, Quadrant5,Quadrant6,Quadrant7,Quadrant8//,Voltar;
        ,BreadCrumb1, BreadCrumb2, BreadCrumb3, BreadCrumb4, Parar, EstadoInicial, zero, one, two, three, four, five, six, seven, eight, nine;
	
	//Labels
	public static JLabel EscalaAtual,InicioFixoLbl,FimFixoLbl;
        
        //Text Fields
	public static JTextField InicioLbl,FimLbl,CapturaDeTela;
        
	//Barra de menu
	public static JMenuBar barra_menu;

	static JMenuBar barra_Botton;
	
	//plot3D e 2D
	public static Plot3DPanel plot3D;
	public static Plot2DPanel plot2D;
   	
	//BooleanFiltro - habilita quando a visualizacao tiver um dado plotado
	public static boolean FiltroHabilita = false;
	
	//Menu
	public static JMenu menu_Carregar,menu_Voltar,menu_Interagir,menu_Filtrar,menu_Configurar,menu_Salvar,menu_Screenshot;
	
	//Altura e largura da tela
	public static int WidthScreen,HeightScreen;
	
	//Check Boxes Menu Item
	public static JCheckBoxMenuItem menu_ajuda,menu_legenda,menu_Detalhes;
	
	//Check Boxes
	public static JCheckBox[] Categoricos;
	
	//RadioButton e button Group
	//public static JRadioButton Ataques,Base,Cabecalho;
	public static ButtonGroup BgBase,BgIFC;
	public static JRadioButton[] atributos,bases;
	public static JCheckBox[] detalhes = new JCheckBox[0];
	//Indices originais para auxiliar com a configuração de cores
	public static int[] IndicesOriginais;
	//PainelIFC Interno
	protected static JDesktopPane desktopIFC;
	protected static JDesktopPane desktopCarregar;
	protected static JDesktopPane desktopInteragir;
	protected static JInternalFrame painelIFC; 
	protected static JInternalFrame painelCarregar;
	protected static JInternalFrame painelInteragir;
	protected static int ScreenInternalW, 
		ScreenInternalH,
		ScreenInternalX, 
		ScreenInternalY,
		ButtonW, ButtonH;
        
        //String para Explicar o Filtro Decimal
        private static String SVoz,LegendaForma,LegendaTamanho;
   	public static JLabel LblVoz,LblLegendaForma,LblLegendaTamanho; 
        
        //Construtor
	public Interface()
    {
        plot2D = new Plot2DPanel();
        plot3D = new Plot3DPanel();
        
        //Inicializa o Modulo de Visualização
        infoVisModule= new Vis3D();
        plot2D.removePlotToolBar();    //Tirar a barra de ferramentas
        plot3D.removePlotToolBar();    //Tirar a barra de ferramentas
        
        
        setTitle("IVOrpheus 2.5"); // seta um título para a janela
   	
        //Inicializando o tamanho da tela
   	   WidthScreen = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); //1280
   	   HeightScreen =(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(); //800
          setSize(WidthScreen,HeightScreen); // seta a dimensão da tela
          setLocationRelativeTo(null); // seta a posição
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define que a execução será finalizada quando a janela for fechada
          setResizable(false); // seta para não ser possível redimensionar a janela
          getContentPane().setLayout(null); //informa que não vai usar Gerenciador de Layout
          
//Seta o módulo de visualização disposto na área de visualização
          if (infoVisModule.IsPlot3D) {
             setContentPane(plot3D);
        }else{
              setContentPane(plot2D);
              plot2D.plotCanvas.ActionMode = 1; // Coloca o action mode para translation
          }
          // instancia os botões e atribuindo os seus nomes
          Confirmar = new JButton("Confirmar");
          Cancelar = new JButton("Cancelar");
          Quadrant1 = new JButton("Quadrante 1");
          Quadrant2 = new JButton("Quadrante 2");
          Quadrant3 = new JButton("Quadrante 3");
          Quadrant4 = new JButton("Quadrante 4");
          Quadrant5 = new JButton("Quadrante 5");
          Quadrant6 = new JButton("Quadrante 6");
          Quadrant7 = new JButton("Quadrante 7");
          Quadrant8 = new JButton("Quadrante 8");
          BreadCrumb1 = new JButton(" - A ");
          BreadCrumb2 = new JButton(" B - ");
          BreadCrumb3 = new JButton(" C - ");
          BreadCrumb4 = new JButton(" D - ");
          Interagir = new JButton("Interagir");
          Configurar = new JButton("Configurar");
          Filtrar = new JButton("Filtrar");
          Cor = new JButton("Cor");
          Forma = new JButton("Forma");
          Tamanho = new JButton("Tamanho");
          EixoX = new JButton("X");
          EixoY = new JButton("Y");
          EixoZ = new JButton("Z");
          Mover = new JButton("Mover");
          Escala = new JButton("Escala");
          Girar = new JButton("Girar");
          Aumentar = new JButton("Aumentar");
          Diminuir = new JButton("Diminuir");
          Esquerda=  new JButton("Esquerda");
          Direita = new JButton("Direita");
          Cima = new JButton("Cima");
          Baixo = new JButton("Baixo");
          Frente = new JButton("Frente");
          Tras = new JButton("Tras");
          InicioBtn = new JButton("Início");
          FimBtn = new JButton("Termino");
          ConfirmarFiltro = new JButton("Confirmar");
          Parar = new JButton("Parar");
          EstadoInicial = new JButton("Estado Inicial");
          zero = new JButton("0");
          one = new JButton("1");
          two = new JButton("2");
          three = new JButton("3");
          four = new JButton("4");
          five = new JButton("5");
          six = new JButton("6");
          seven = new JButton("7");
          eight = new JButton("8");
          nine = new JButton("9");
          
      	BgBase = new ButtonGroup();
      	
              bases = new JRadioButton[Main.dir.GetFileNames().length];
              for(int i = 0; i <= Main.dir.GetFileNames().length-1;i++){
      			bases[i] = new JRadioButton(i+1+"-"+Main.dir.GetFileNames()[i].substring(0,Main.dir.GetFileNames()[i].indexOf('.')));
      			bases[i].addActionListener(new BtnListener());
      			BgBase.add(bases[i]);
              }
      
          //Labels
          EscalaAtual = new JLabel("Escala Atual: ");
          InicioFixoLbl = new JLabel("Inicia em: ");
          FimFixoLbl = new JLabel("Termina em: ");
          LblVoz = new JLabel();
          LblLegendaForma = new JLabel();
          LblLegendaTamanho = new JLabel();
          
          //JTextField
          InicioLbl = new JTextField("Inicio");
          FimLbl = new JTextField("Termino");
          
          //Centralizando o JTextField
          InicioLbl.setHorizontalAlignment(InicioLbl.CENTER);
          FimLbl.setHorizontalAlignment(FimLbl.CENTER);
          
          //Setando Cores para os botões
          Confirmar			.setForeground(Color.BLACK);
          Cancelar			.setForeground(Color.BLACK);
          Quadrant1			.setForeground(Color.BLACK);
          Quadrant2			.setForeground(Color.BLACK);
          Quadrant3			.setForeground(Color.BLACK);
          Quadrant4			.setForeground(Color.BLACK);
          Quadrant5			.setForeground(Color.BLACK);
          Quadrant6			.setForeground(Color.BLACK);
          Quadrant7			.setForeground(Color.BLACK);
          Quadrant8			.setForeground(Color.BLACK);
          Interagir			.setForeground(Color.BLACK);
          Configurar                    .setForeground(Color.BLACK);
          Filtrar			.setForeground(Color.BLACK);
          Cor				.setForeground(Color.BLACK);
          Forma				.setForeground(Color.BLACK);
          Tamanho			.setForeground(Color.BLACK);
          EixoX				.setForeground(Color.BLACK);
          EixoY				.setForeground(Color.BLACK);
          EixoZ				.setForeground(Color.BLACK);
          Mover				.setForeground(Color.BLACK);
          Escala                        .setForeground(Color.BLACK);
          Girar				.setForeground(Color.BLACK);
          EstadoInicial			.setForeground(Color.BLACK);
          Parar				.setForeground(Color.BLACK);
          
          //Barra de Menu
          barra_menu = new JMenuBar();
          barra_Botton = new JMenuBar();
          menu_Carregar = new JMenu("Abrir");
          menu_Carregar.setEnabled(true);
          barra_menu.add(menu_Carregar);
          menu_Salvar = new JMenu("Salvar");
          menu_Salvar.setEnabled(true);
          barra_menu.add(menu_Salvar);
          menu_Voltar = new JMenu("Voltar");
          menu_Voltar.setEnabled(true); 
          barra_menu.add(menu_Voltar);
          menu_Screenshot = new JMenu("Capturar Tela");
          menu_Screenshot.setEnabled(true); 
          barra_menu.add(menu_Screenshot);
          menu_ajuda = new JCheckBoxMenuItem("Ajuda",false);
//          menu_ajuda.setSize(menu_ajuda.WIDTH/2, menu_ajuda.HEIGHT);
          menu_ajuda.setEnabled(true);
          barra_menu.add(menu_ajuda);
          menu_legenda = new JCheckBoxMenuItem("Legenda",false);
//          menu_legenda.setSize(menu_legenda.WIDTH/2,menu_legenda.HEIGHT);
          menu_legenda.setEnabled(true);
          barra_menu.add(menu_legenda);
          menu_Detalhes = new JCheckBoxMenuItem("Detalhes",false);
          menu_Detalhes.setEnabled(true);
          barra_menu.add(menu_Detalhes);
         
          
          //Barra_Botton  
          menu_Interagir = new JMenu("Interagir");
          menu_Filtrar = new JMenu("Filtrar");
          menu_Configurar = new JMenu("Configurar");
          menu_Interagir.setEnabled(true); 
          menu_Filtrar.setEnabled(true); 
          menu_Configurar.setEnabled(true); 

          add(BorderLayout.SOUTH, barra_Botton); 
          add(BorderLayout.NORTH, barra_menu); 

          
          //Registra os Botões no Listener
          menu_Voltar.addActionListener(new BtnListener());
          Confirmar.addActionListener(new BtnListener());
          Cancelar.addActionListener(new BtnListener());
          Interagir.addActionListener(new BtnListener());
          Configurar.addActionListener(new BtnListener());
          Filtrar.addActionListener(new BtnListener());
          Cor.addActionListener(new BtnListener());
          Forma.addActionListener(new BtnListener());
          Tamanho.addActionListener(new BtnListener());
          EixoX.addActionListener(new BtnListener());
          EixoY.addActionListener(new BtnListener());
          EixoZ.addActionListener(new BtnListener());
          Mover.addActionListener(new BtnListener());
          Girar.addActionListener(new BtnListener());
          Escala.addActionListener(new BtnListener());
          Aumentar.addActionListener(new BtnListener());
          Diminuir.addActionListener(new BtnListener());
          Esquerda.addActionListener(new BtnListener());
          Direita.addActionListener(new BtnListener());
          Cima.addActionListener(new BtnListener());
          Baixo.addActionListener(new BtnListener());
          Frente.addActionListener(new BtnListener());
          Tras.addActionListener(new BtnListener());
          menu_legenda.addActionListener(new BtnListener());
          menu_ajuda.addActionListener(new BtnListener());
          menu_Detalhes.addActionListener(new BtnListener());
          Quadrant1.addActionListener(new BtnListener());
          Quadrant2.addActionListener(new BtnListener());
          Quadrant3.addActionListener(new BtnListener());
          Quadrant4.addActionListener(new BtnListener());
          Quadrant5.addActionListener(new BtnListener());
          Quadrant6.addActionListener(new BtnListener());
          Quadrant7.addActionListener(new BtnListener());
          Quadrant8.addActionListener(new BtnListener());
          BreadCrumb1.addActionListener(new BtnListener());
          BreadCrumb2.addActionListener(new BtnListener());
          BreadCrumb3.addActionListener(new BtnListener());
          BreadCrumb4.addActionListener(new BtnListener());
          EstadoInicial.addActionListener(new BtnListener());
          Parar.addActionListener(new BtnListener());
          zero.addActionListener(new BtnListener());
          one.addActionListener(new BtnListener());
          two.addActionListener(new BtnListener());
          three.addActionListener(new BtnListener());
          four.addActionListener(new BtnListener());
          five.addActionListener(new BtnListener());
          six.addActionListener(new BtnListener());
          seven.addActionListener(new BtnListener());
          eight.addActionListener(new BtnListener());
          nine.addActionListener(new BtnListener());
           
          
          

          //MenuListener
          menu_Carregar.addMenuListener(new BtnListener());
          menu_Voltar.addMenuListener(new BtnListener());
          menu_Salvar.addMenuListener(new BtnListener());
          menu_Screenshot.addMenuListener(new BtnListener());
//          menu_Detalhes.addActionListener(new BtnListener());
          
          //Inicializa Os paineis
  		desktopIFC = new JDesktopPane();
  		desktopCarregar = new JDesktopPane();
  		desktopInteragir = new JDesktopPane();
  		painelIFC = new JInternalFrame(); 
  		painelCarregar = new JInternalFrame();  
  		painelInteragir = new JInternalFrame();
  		ScreenInternalW = WidthScreen - WidthScreen/5; 
//  		ScreenInternalH = HeightScreen/6; //Tava Funcionando com esse aqui 
  		ScreenInternalH = HeightScreen/7;
  		ScreenInternalX = WidthScreen/100 * 10; 
  		ScreenInternalY = HeightScreen/100 * 85;
  		ButtonW = ScreenInternalW/4; ButtonH = ScreenInternalH/4;
                
                //Desktop Managers
                desktopIFC.setDesktopManager( new NoDragDesktopManager() );   // Serve para tirar a funcionalidade dragmode
                desktopCarregar.setDesktopManager( new NoDragDesktopManager() );
                desktopInteragir.setDesktopManager( new NoDragDesktopManager() );
                
  		
                //Desabilita Btns
//  		Proximo.setEnabled(false); 
//  		Anterior.setEnabled(false);
  		Filtrar.setEnabled(false);
  		Interagir.setEnabled(false);
                Cancelar.setEnabled(false);
                menu_Voltar.setEnabled(false);
                Cor.setEnabled(false);
                Forma.setEnabled(false);
                Tamanho.setEnabled(false);
                
                SVoz = new String();
                SVoz = "<html><h2 align ='center'>Comandos de Voz</h2>";
                SVoz = SVoz+"<br align = 'center'>Início : Atribui o valor inicial a ser filtrado.</br>";
                SVoz = SVoz+"<br align = 'center'>Término : Atribui o valor final a ser filtrado.</br>";
                SVoz = SVoz+"<br align = 'center'>Dígitos de 0..9.</br>";
                SVoz = SVoz+"<br align = 'center'>Ponto : Marca o início de uma casa decimal.</br>";
                SVoz = SVoz+"<br align = 'center'>Limpar : Apaga todos os números ditados.</br>";
                SVoz = SVoz+"<br align = 'center'>Apagar: Apaga o último número ditado.</br>";
               
        
                //Setando as fontes dos btns
                Font f = new Font(Interagir.getFont().getFontName(),Font.PLAIN,24);
                Interagir.setFont(f);
                Configurar.setFont(f);
                Filtrar.setFont(f);
                Cor.setFont(f);
                Forma.setFont(f);
                Tamanho.setFont(f);
                EixoX.setFont(f);
                EixoY.setFont(f);
                EixoZ.setFont(f);
                Mover.setFont(f);
                Girar.setFont(f);
                Escala.setFont(f);
                
                plot2D.plotCanvas.addMouseListener(new BtnListener());
                plot2D.plotCanvas.addMouseMotionListener(new BtnListener());
                plot2D.plotCanvas.addMouseWheelListener(new BtnListener());
                
    }
	
	public static JDesktopPane PainelCarregar() throws IOException, GrammarException, EngineStateError{
		int ScreenInternalW = WidthScreen - WidthScreen/10, 
			ScreenInternalH = HeightScreen - HeightScreen/5,
			ScreenInternalX = WidthScreen/100 * 5, 
			ScreenInternalY = HeightScreen/100 * 5,
			ButtonW = ScreenInternalW/5, ButtonH = ScreenInternalH/10,
			ButtonX = ScreenInternalW/2, ButtonY = ScreenInternalH-ScreenInternalH/8;
                
                
		
		Confirmar.setSize(ButtonW,ButtonH);
		Confirmar.setLocation(ButtonX - ButtonW/2 - ButtonW/10, ButtonY);
		Cancelar.setSize(ButtonW,ButtonH);
		Cancelar.setLocation(ButtonX + ButtonW/2 + ButtonW/10 ,ButtonY);
//		Proximo.setSize(ButtonW,ButtonH);
//		Proximo.setLocation(ButtonX + ButtonW/2 + ButtonW/10 ,ButtonY - ButtonH - 10);
//		Anterior.setSize(ButtonW,ButtonH);
//		Anterior.setLocation(ButtonX - ButtonW/2 - ButtonW/10 ,ButtonY - ButtonH - 10);
	
		painelCarregar.setSize(ScreenInternalW,ScreenInternalH); // Descreve o tamanho do internal frame em porcentagem. 
		painelCarregar.setLocation(ScreenInternalX, ScreenInternalY);
		painelCarregar.setClosable(false);
		painelCarregar.setDefaultCloseOperation(painelCarregar.DISPOSE_ON_CLOSE);
		painelCarregar.setVisible(true);
                desktopCarregar.add(painelCarregar);
                
                if (infoVisModule.IsPlot3D) {
                desktopCarregar.add(plot3D);
                }else
                desktopCarregar.add(plot2D);
		desktopCarregar.add(Confirmar);
		desktopCarregar.add(Cancelar);
//		desktopCarregar.add(Proximo);
//		desktopCarregar.add(Anterior);
		painelCarregar.setFocusable(true);
		desktopCarregar.setDoubleBuffered(true);
                desktopCarregar.setComponentZOrder(painelCarregar,desktopCarregar.getComponentCount()-2);
                painelCarregar.putClientProperty("dragMode", "fixed");
                return desktopCarregar;
	}
        
        public static void SetPane(JFrame Frame) throws PropertyVetoException, GrammarException, EngineStateError, IOException{
//        infoVisModule.IsPlot3D = true;    
        if (infoVisModule.IsPlot3D) {
        	Frame.setContentPane(plot3D);
            Frame.add(BorderLayout.NORTH, barra_menu);	
//            LimparPainel(desktopIFC);
              //  Frame.setContentPane(plot3D);
              //  Frame.add(BorderLayout.NORTH, barra_menu);
//                menu_Voltar.doClick(10000);
//                (new ThreadDsd()).start();   
//                if (BtnListener.DetalhesFlag) {
             if (Interface.menu_Detalhes.isSelected()) {
            Interface.BtnsDetails();
            Frame.setContentPane(((Interface)Frame).PainelIFC());
            }else{
            SwingUtilities.invokeLater((new ThreadIFC()));
                	//                Interface.BtnsIFC(); 
//                Frame.setContentPane(((Interface)Frame).PainelIFC());
//                Frame.setContentPane(((Interface)Frame).PainelIFC());
//                Interface.painelIFC.setSelected(true);
//                Interface.desktopIFC.setComponentZOrder(Interface.painelIFC,Interface.desktopIFC.getComponentCount()-2);
//                Frame.repaint();
                }//Ao nao comentar isso o Panel do 3D fica com tela azul, mas aqi está a possivel solucao para o nao chamar da tela plot3d 
//		System.out.println(Arrays.toString(Frame.getContentPane().getComponents()));
                }else{
                Frame.setContentPane(plot2D);
                Frame.add(BorderLayout.NORTH, barra_menu);
                if (Interface.menu_Detalhes.isSelected()) {
//                if (BtnListener.DetalhesFlag) {
                Interface.BtnsDetails();
                (new ThreadDsd()).start();   
		}else{
                Interface.BtnsIFC();
                }
		Frame.setContentPane(((Interface)Frame).PainelIFC());
//                Interface.painelIFC.setSelected(true);
//                Interface.desktopIFC.setComponentZOrder(Interface.painelIFC,Interface.desktopIFC.getComponentCount()-2);
//                System.out.println(Arrays.toString(Frame.getContentPane().getComponents()));
            }
        }
	
	public static void BtnsBase(){
		int ScreenInternalW = WidthScreen - WidthScreen/10, 
				ScreenInternalH = HeightScreen - HeightScreen/5,
				ScreenInternalX = WidthScreen/100 * 5, 
				ScreenInternalY = HeightScreen/100 * 1,
				ButtonW = ScreenInternalW/5, ButtonH = ScreenInternalH/10,
				ButtonX = ScreenInternalW/2 , ButtonY = ScreenInternalY+ButtonH*2,
				Pace = 0;
                
                
                LimparPainel(desktopCarregar);
		for(int i = 0; i <= Main.dir.GetFileNames().length-1;i++){
			bases[i].setSize(ButtonW,ButtonH);
			bases[i].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); // No Linux se caso necessário substituir ScreenInternalX/5 por +1
			bases[i].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			desktopCarregar.add(bases[i]);
		}
		
	}

	public static void Cancelar(){
		desktopCarregar.removeAll();
		desktopCarregar.revalidate();
		desktopCarregar.repaint();
	}
	
            public static void BaseFC(String[] s) throws GrammarException, EngineStateError, IOException{
		int ScreenInternalW = WidthScreen - WidthScreen/10, 
				ScreenInternalH = HeightScreen - HeightScreen/5,
				ScreenInternalX = WidthScreen/100 * 5, 
				ScreenInternalY = HeightScreen/100 * 1,
				ButtonW = ScreenInternalW/5, ButtonH = ScreenInternalH/10,
				ButtonX = ScreenInternalW/2, ButtonY = ScreenInternalY+ButtonH*2,
				Pace = 0;
		atributos = new JRadioButton[s.length];
                IndicesOriginais = new int[s.length];
		LimparPainel(desktopCarregar);
		BgIFC = new ButtonGroup();
		
                if(BtnListener.ConfigurarCor){
                int limiarCor = 8;
                int mark = 0;
                for(int i = 0; i <= s.length-1;i++){
			if(Main.att.GetUniqueValues(i).length <= limiarCor){
			atributos[mark] = new JRadioButton(mark+1+"-"+s[i]);
			atributos[mark].addActionListener(new BtnListener());
			atributos[mark].setSize(ButtonW,ButtonH);
			atributos[mark].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); // No Linux Subistituir ScreenInternalX/5 por +1
			atributos[mark].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			BgIFC.add(atributos[mark]);
			desktopCarregar.add(atributos[mark]);
                        IndicesOriginais[mark] = i;
			mark++;
		}
                }
                }else if(BtnListener.ConfigurarForma){
                int limiarForma = 4; 
                int mark = 0;
                for(int i = 0; i <= s.length-1;i++){
			if(Main.att.GetUniqueValues(i).length <= limiarForma){
			atributos[mark] = new JRadioButton(mark+1+"-"+s[i]);
			atributos[mark].addActionListener(new BtnListener());
			atributos[mark].setSize(ButtonW,ButtonH);
			atributos[mark].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); // No Linux Subistituir ScreenInternalX/5 por +1
			atributos[mark].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			BgIFC.add(atributos[mark]);
			desktopCarregar.add(atributos[mark]);
                        IndicesOriginais[mark] = i;
			mark++;
		}
                }
                }else if(BtnListener.ConfigurarTamanho){
                int limiarTamanho = 3;  
                int mark = 0;
                for(int i = 0; i <= s.length-1;i++){
			if(Main.att.GetUniqueValues(i).length <= limiarTamanho){
			atributos[mark] = new JRadioButton(mark+1+"-"+s[i]);
			atributos[mark].addActionListener(new BtnListener());
			atributos[mark].setSize(ButtonW,ButtonH);
			atributos[mark].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); // No Linux Subistituir ScreenInternalX/5 por +1
			atributos[mark].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			BgIFC.add(atributos[mark]);
			desktopCarregar.add(atributos[mark]);
                        IndicesOriginais[mark] = i;
			mark++;
		}
                }
                }else {
                for(int i = 0; i <= s.length-1;i++){
			
			atributos[i] = new JRadioButton(i+1+"-"+s[i]);
			atributos[i].addActionListener(new BtnListener());
			atributos[i].setSize(ButtonW,ButtonH);
			atributos[i].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); // No Linux Subistituir ScreenInternalX/5 por +1
			atributos[i].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			BgIFC.add(atributos[i]);
			desktopCarregar.add(atributos[i]);
			
		}
                }
		
	}
	public static void Detalhes(String[] s) throws GrammarException, EngineStateError, IOException{
		int ScreenInternalW = WidthScreen - WidthScreen/10, 
				ScreenInternalH = HeightScreen - HeightScreen/5,
				ScreenInternalX = WidthScreen/100 * 5, 
				ScreenInternalY = HeightScreen/100 * 1,
				ButtonW = ScreenInternalW/5, ButtonH = ScreenInternalH/10,
				ButtonX = ScreenInternalW/2, ButtonY = ScreenInternalY+ButtonH*2,
				Pace = 0;
		detalhes = new JCheckBox[s.length];
//                IndicesOriginais = new int[s.length];
		LimparPainel(desktopCarregar);
//		BgIFC = new ButtonGroup();
		int mark = 0;
                for(int i = 0; i <= s.length-1;i++){
			detalhes[mark] = new JCheckBox(mark+1+"-"+s[i]);
			detalhes[mark].addActionListener(new BtnListener());
			detalhes[mark].setSize(ButtonW,ButtonH);
			detalhes[mark].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); // No Linux Subistituir ScreenInternalX/5 por +1
			detalhes[mark].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
//			BgIFC.add(detalhes[mark]);
			desktopCarregar.add(detalhes[mark]);
//                        IndicesOriginais[mark] = i;
			mark++;
		
                }
                
                
                if (!infoVisModule.SalvaAttDsd.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.SalvaAttDsd.contains(detalhes[i].getText().substring(detalhes[i].getText().indexOf('-')+1))) {
                            detalhes[i].setSelected(true);
                        }
                    }
                }
               }
	
	public static JDesktopPane PainelIFC() throws GrammarException, EngineStateError, IOException{ // Interagir Filtrar e Configurar
		LimparPainel(desktopCarregar);
                painelIFC.setSize(ScreenInternalW,ScreenInternalH); // Descreve o tamanho do internal frame em porcentagem. 
		painelIFC.setLocation(ScreenInternalX, ScreenInternalY-20); //retirar o 20 para ficar só os eixos XYZ
		painelIFC.setVisible(true); // Deixar isso aqui setavel
		desktopIFC.add(painelIFC);
                desktopIFC.setDoubleBuffered(true);
                desktopIFC.setComponentZOrder(painelIFC,desktopIFC.getComponentCount()-2);
                painelIFC.putClientProperty("dragMode", "fixed");
                return desktopIFC;
		}
	
	public static void BtnsIFC(){
                LimparPainel(desktopIFC);
                int 
		ButtonW = ScreenInternalW/4, ButtonH = ScreenInternalH/4,
		ButtonX = ScreenInternalX/2, ButtonY = ScreenInternalY+ScreenInternalY/10;  //Ajustar em 8(tirar o 10 e subistuir) para o Linux
		
                //Seta titulo do painel
		painelIFC.setTitle("Base Carregada");
		
		Interagir.setSize(ButtonW,ButtonH);
		Interagir.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
		Configurar.setSize(ButtonW,ButtonH); 
		Configurar.setLocation((int) (ButtonX + 1.75*ButtonW) ,ButtonY- ButtonY/50);
		Filtrar.setSize(ButtonW,ButtonH);
		Filtrar.setLocation(ButtonX + 3*ButtonW  ,ButtonY- ButtonY/50);
//                Interagir.setFont(new Font(Interagir.getFont().getFontName(),Font.PLAIN,24));
//                Configurar.setFont(new Font(Interagir.getFont().getFontName(),Font.PLAIN,24));
//                Filtrar.setFont(new Font(Interagir.getFont().getFontName(),Font.PLAIN,24));
               
                //Adiciona os btns no desktop
		desktopIFC.add(Interagir);
		desktopIFC.add(Configurar);
		desktopIFC.add(Filtrar);
                
                if (infoVisModule.IsPlot3D) {
                desktopIFC.add(plot3D);
                plot3D.getAxis(0).setLabelFont(new Font("ArialMT",Font.PLAIN,24));
                plot3D.getAxis(1).setLabelFont(new Font("ArialMT",Font.PLAIN,24));
                plot3D.getAxis(2).setLabelFont(new Font("LucidaGrande",Font.PLAIN,24));
                }else{
                desktopIFC.add(plot2D);
                plot2D.getAxis(0).setLabelFont(new Font("ArialMT",Font.PLAIN,24));
                plot2D.getAxis(1).setLabelFont(new Font("ArialMT",Font.PLAIN,24));
                }
	}
	
	public static void BtnsC(){
		//Seta W,H,X,Y dos Btns
		int 
		ButtonW = ScreenInternalW/4, ButtonH = ScreenInternalH/4,
		ButtonX = ScreenInternalX/2, ButtonY = ScreenInternalY+ScreenInternalY/100 *3; //estava em 10 porcento para aparecer apenas os eixos XYZ //Ajustar em 8(tirar o 10 e subistuir) para o Linux
		LimparPainel(desktopIFC);
		
                //Seta titulo do painel
		painelIFC.setTitle("Configurar");
				
		//Seta posicao e atributos dos btns
		EixoX.setSize(ButtonW,ButtonH);
		EixoX.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
		EixoY.setSize(ButtonW,ButtonH); 
		EixoY.setLocation((int) (ButtonX + 1.75*ButtonW) ,ButtonY- ButtonY/50);
		EixoZ.setSize(ButtonW,ButtonH);
		EixoZ.setLocation(ButtonX + 3*ButtonW  ,ButtonY- ButtonY/50);
		Cor.setSize(ButtonW,ButtonH);
		Cor.setLocation(ButtonX + ButtonW/2, ButtonY + ButtonY/20);
		Forma.setSize(ButtonW,ButtonH); 
		Forma.setLocation((int) (ButtonX + 1.75*ButtonW) ,ButtonY + ButtonY/20);
		Tamanho.setSize(ButtonW,ButtonH);
		Tamanho.setLocation(ButtonX + 3*ButtonW  ,ButtonY + ButtonY/20);
		
//		Cor.enable(false);
		Forma.enable(false); 
		Tamanho.enable(false);

                
                
//                Cor.setFont(new Font(Cor.getFont().getFontName(),Font.PLAIN,24));
//                Forma.setFont(new Font(Forma.getFont().getFontName(),Font.PLAIN,24));
//                Tamanho.setFont(new Font(Tamanho.getFont().getFontName(),Font.PLAIN,24));
//                EixoX.setFont(new Font(EixoX.getFont().getFontName(),Font.PLAIN,24));
//                EixoY.setFont(new Font(EixoY.getFont().getFontName(),Font.PLAIN,24));
//                EixoZ.setFont(new Font(EixoZ.getFont().getFontName(),Font.PLAIN,24));
                
                //Adiciona os btns no desktop
		desktopIFC.add(Cor);
		desktopIFC.add(Forma);
		desktopIFC.add(Tamanho);	
		desktopIFC.add(EixoX);
		desktopIFC.add(EixoY);
		desktopIFC.add(EixoZ);
                if (infoVisModule.IsPlot3D) {
                desktopIFC.add(plot3D);
                }else
                desktopIFC.add(plot2D);
	}
	
	public static void BtnsF(){
		//Seta W,H,X,Y dos Btns
		int 
		ButtonW = ScreenInternalW/4, ButtonH = ScreenInternalH/4,
		ButtonX = ScreenInternalX/2, ButtonY = ScreenInternalY+ScreenInternalY/100 *3; //Ajustar em 8(tirar o 10 e subistuir) para o Linux
				
		LimparPainel(desktopIFC);
		//Seta titulo do painel
		painelIFC.setTitle("Filtrar");
				
		//Seta posicao e atributos dos btns
		EixoX.setSize(ButtonW,ButtonH);
		EixoX.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
		EixoY.setSize(ButtonW,ButtonH); 
		EixoY.setLocation((int) (ButtonX + 1.75*ButtonW) ,ButtonY- ButtonY/50);
		EixoZ.setSize(ButtonW,ButtonH);
		EixoZ.setLocation(ButtonX + 3*ButtonW  ,ButtonY- ButtonY/50);
		Cor.setSize(ButtonW,ButtonH);
		Cor.setLocation(ButtonX + ButtonW/2, ButtonY + ButtonY/20);
		Forma.setSize(ButtonW,ButtonH); 
		Forma.setLocation((int) (ButtonX + 1.75*ButtonW) ,ButtonY + ButtonY/20);
		Tamanho.setSize(ButtonW,ButtonH);
		Tamanho.setLocation(ButtonX + 3*ButtonW  ,ButtonY + ButtonY/20);
		
//		Cor.enable(false);
		Forma.enable(false); 
		Tamanho.enable(false);
		
		//Adiciona os btns no desktop
		desktopIFC.add(Cor);
		desktopIFC.add(Forma);
		desktopIFC.add(Tamanho);	
		desktopIFC.add(EixoX);
		desktopIFC.add(EixoY);
		desktopIFC.add(EixoZ);

                if (infoVisModule.IsPlot3D) {
                desktopIFC.add(plot3D);
                }else
                desktopIFC.add(plot2D);
	}

	public static void BtnsI(){
		//Seta W,H,X,Y dos Btns
		int 
		ButtonW = ScreenInternalW/4, ButtonH = ScreenInternalH/4,
		ButtonX = ScreenInternalX/2, ButtonY = ScreenInternalY+ScreenInternalY/10; //Ajustar em 8(tirar o 10 e subistuir) para o Linux
		
		LimparPainel(desktopIFC);
		
		//Seta titulo do painel
		painelIFC.setTitle("Interagir");
		
		//Seta posicao e atributos dos btns
		Mover.setSize(ButtonW,ButtonH);
		Mover.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
		Escala.setSize(ButtonW,ButtonH); 
		Escala.setLocation((int) (ButtonX + 1.75*ButtonW) ,ButtonY- ButtonY/50);
		Girar.setSize(ButtonW,ButtonH);
		Girar.setLocation(ButtonX + 3*ButtonW  ,ButtonY- ButtonY/50);
	
		//Adiciona os btns no desktop
		desktopIFC.add(Mover);
		desktopIFC.add(Escala);
		desktopIFC.add(Girar);	
                if (infoVisModule.IsPlot3D) {
                desktopIFC.add(plot3D);
                }else
                desktopIFC.add(plot2D);
		
	}
	
	public static void Voltar(){
		
		
		desktopInteragir.removeAll();
		desktopInteragir.revalidate();
		desktopInteragir.repaint();
		desktopIFC.removeAll();
		desktopIFC.revalidate();
		desktopIFC.repaint();
		desktopCarregar.removeAll();
		desktopCarregar.revalidate();
		desktopCarregar.repaint();
		
	}

	public static JDesktopPane PainelInteragir(){ 
		int
		ScreenInternalW = WidthScreen/5, 
		ScreenInternalH = HeightScreen - HeightScreen/5,
		ScreenInternalX = WidthScreen - WidthScreen/5, 
		ScreenInternalY = HeightScreen/20,
		ButtonW = ScreenInternalW - ScreenInternalW/10, ButtonH = ScreenInternalH/10,
		ButtonX = ScreenInternalX  + ButtonW/17, ButtonY = ScreenInternalY;
			
		
		painelInteragir.setSize(ScreenInternalW,ScreenInternalH); 
		painelInteragir.setLocation(ScreenInternalX, ScreenInternalY);
		painelInteragir.setVisible(true);
		desktopInteragir.add(painelInteragir);
                desktopInteragir.setDoubleBuffered(true);
                desktopInteragir.setComponentZOrder(painelInteragir,desktopInteragir.getComponentCount()-2);
                painelInteragir.putClientProperty("dragMode", "fixed");
                
                
		return desktopInteragir;
		}
	public static void BtnEscala(){
		int
		ScreenInternalW = WidthScreen/5, 
		ScreenInternalH = HeightScreen - HeightScreen/5,
		ScreenInternalX = WidthScreen - WidthScreen/5, 
		ScreenInternalY = HeightScreen/20,
		ButtonW = ScreenInternalW - ScreenInternalW/10, ButtonH = ScreenInternalH/10,
		ButtonX = ScreenInternalX  + ButtonW/17, ButtonY = ScreenInternalY;
                
                
		
		painelInteragir.setTitle("Escala");
		EscalaAtual.setSize(ButtonW,ButtonH);
		EscalaAtual.setLocation(ButtonX+ButtonX/100*2,ButtonY+ButtonH); //Caso AtualEscala der problema remover o ButtonX/100*2
		Aumentar.setSize(ButtonW,ButtonH);
		Aumentar.setLocation(ButtonX,ButtonY+ButtonH*4);
		Diminuir.setSize(ButtonW,ButtonH);
		Diminuir.setLocation(ButtonX,ButtonY+ButtonH*5);
		EstadoInicial.setSize(ButtonW,ButtonH);
		EstadoInicial.setLocation(ButtonX,ButtonY+ButtonH*7);
		Parar.setSize(ButtonW,ButtonH);
		Parar.setLocation(ButtonX,ButtonY+ButtonH*8);
		
		
		desktopInteragir.add(Aumentar);
		desktopInteragir.add(Diminuir);
		desktopInteragir.add(EscalaAtual);
		desktopInteragir.add(EstadoInicial);
		desktopInteragir.add(Parar);
                if (infoVisModule.IsPlot3D) {
                desktopInteragir.add(plot3D);
                }else
                desktopInteragir.add(plot2D);
		
		
	}
        public static void BreadCrumbs(){
//		int
//		ScreenInternalW = WidthScreen/5, 
//		ScreenInternalH = HeightScreen - HeightScreen/5,
//		ScreenInternalX = WidthScreen - WidthScreen/5, 
//		ScreenInternalY = HeightScreen/20,
//		ButtonW = ScreenInternalW - ScreenInternalW/10, ButtonH = ScreenInternalH/10,
//		ButtonX = ScreenInternalX  + ButtonW/17, ButtonY = ScreenInternalY;
//		
//		BreadCrumb1.setSize(ButtonW,ButtonH*3);
//                BreadCrumb1.setLocation(ButtonX,ButtonY+ButtonH*4);
//		
//		
//		if (infoVisModule.IsPlot3D) {
//                plot3D.plotCanvas.add(BreadCrumb1);
//                }else
//                plot2D.plotCanvas.add(BreadCrumb1);
//		
//		
//	}
//        public static void LabelsLegenda() throws FileNotFoundException, IOException{
//		int
//		ScreenInternalW = WidthScreen/5, 
//		ScreenInternalH = HeightScreen - HeightScreen/5,
//		ScreenInternalX = WidthScreen - WidthScreen/5, 
//		ScreenInternalY = HeightScreen/20,
//		ButtonW = ScreenInternalW - ScreenInternalW/10, ButtonH = ScreenInternalH/10,
//		ButtonX = ScreenInternalX  + ButtonW/17, ButtonY = ScreenInternalY;
//		
//                LegendaForma = new String();
//                LegendaTamanho = new String();
//                
//		painelInteragir.setTitle("Legenda");
//                
//                infoVisModule.Legend(LegendaForma,LegendaTamanho);
//                
//                LblLegendaForma.setSize(ScreenInternalW,ScreenInternalH/2);
//                LblLegendaForma.setLocation(WidthScreen - WidthScreen/10-LblLegendaForma.getPreferredSize().width/2,ButtonY); //Caso AtualEscala der problema remover o ButtonX/100*2
//		
//                LblLegendaTamanho.setSize(ScreenInternalW,ScreenInternalH/2);
//		LblLegendaTamanho.setLocation(WidthScreen - WidthScreen/10-LblLegendaTamanho.getPreferredSize().width/2,ScreenInternalH/2-ButtonY);
//		
	}
		

	public static void BtnFiltrarDecimal(){
		int
		ScreenInternalW = WidthScreen - WidthScreen/10, 
		ScreenInternalH = HeightScreen - HeightScreen/5,
		ScreenInternalX = WidthScreen/100 * 5, 
		ScreenInternalY = HeightScreen/100 * 1,
		ButtonW = ScreenInternalW/5, ButtonH = ScreenInternalH/10,
		ButtonX = ScreenInternalW/5 , ButtonY = ScreenInternalH/10;
	
                LimparPainel(desktopCarregar);
                infoVisModule.FiltroNumerico = true;
		InicioLbl.setSize(ButtonW,ButtonH);
		InicioLbl.setLocation(ButtonX - ButtonW/4, ButtonY+ButtonW/4);
		InicioFixoLbl.setSize(ButtonW*2,ButtonH);
		InicioFixoLbl.setLocation(ButtonX - ButtonW/8,ButtonY*3+ButtonW/4);
		InicioBtn.setSize(ButtonW,ButtonH);
		InicioBtn.setLocation(ButtonX - ButtonW/4, ButtonY*2+ButtonW/4);
		FimLbl.setSize(ButtonW,ButtonH);
		FimLbl.setLocation(ScreenInternalW/2+ScreenInternalW/4,ButtonY+ButtonW/4);
		FimFixoLbl.setSize(ButtonW*2,ButtonH);
		FimFixoLbl.setLocation(ScreenInternalW/2+ScreenInternalW/4,ButtonY*3+ButtonW/4);
		FimBtn.setSize(ButtonW,ButtonH);
		FimBtn.setLocation(ScreenInternalW/2+ScreenInternalW/4,ButtonY*2+ButtonW/4);
                
                LblVoz.setSize(ScreenInternalW/2,ScreenInternalH/2);
                LblVoz.setLocation(ScreenInternalW/2-ScreenInternalW/8,ScreenInternalH/3);
                
                LblVoz.setText(SVoz);
                
		//painelCarregar
		desktopCarregar.add(InicioLbl);
		desktopCarregar.add(FimLbl);
		desktopCarregar.add(InicioFixoLbl);
		desktopCarregar.add(FimFixoLbl);
		desktopCarregar.add(InicioBtn);
		desktopCarregar.add(FimBtn);
		desktopCarregar.add(LblVoz);
//                
//                Proximo.setVisible(false); 
//  		Anterior.setVisible(false);
//                
                InicioLbl.setVisible(true);
                InicioFixoLbl.setVisible(true);
                InicioBtn.setVisible(true);
                FimLbl.setVisible(true);
                FimFixoLbl.setVisible(true);
                FimBtn.setVisible(true);
                
	}
	
	public static void BtnFiltrarCategorico(String[] s){
            //Se caso houver algum bug inesperado referente esta parte, antes da " ➞ " havia um " ( " " ) "
		int ScreenInternalW = WidthScreen - WidthScreen/10, 
				ScreenInternalH = HeightScreen - HeightScreen/5,
				ScreenInternalX = WidthScreen/100 * 5, 
				ScreenInternalY = HeightScreen/100 * 1,
				ButtonW = ScreenInternalW/5, ButtonH = ScreenInternalH/10,
				ButtonX = ScreenInternalW/2, ButtonY = ScreenInternalY+ButtonH*2,
				Pace = 0;
		Categoricos = new JCheckBox[s.length];
                
		LimparPainel(desktopCarregar);
                
                infoVisModule.FiltroNumerico = false;
    
                int ValorCor = 0;
                
                if (BtnListener.FiltrarCor) {
                
		for(int i = 0; i <= s.length-1;i++){
			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+ " ➞ " + infoVisModule.NomeDasCores[i] + " " );
			Categoricos[i].addActionListener(new BtnListener());
			Categoricos[i].setSize(ButtonW,ButtonH);
			Categoricos[i].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); //Subistituir ScreenInternalX/5 por +1 no Linux
			Categoricos[i].setOpaque(false);
                        if (ValorCor<8) {
                         Categoricos[i].setForeground(infoVisModule.ModeloCores[ValorCor]);
                         }else{
                         ValorCor = 0; 
                        Categoricos[i].setForeground(infoVisModule.ModeloCores[ValorCor]);  
                        }
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			desktopCarregar.add(Categoricos[i]);
			
		ValorCor++;	
		}
        }else if (BtnListener.FiltrarForma) {
		for(int i = 0; i <= s.length-1;i++){
			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+ " ➞ " + infoVisModule.NomeDasFormas[i] + " " );
			Categoricos[i].addActionListener(new BtnListener());
			Categoricos[i].setSize(ButtonW,ButtonH);
			Categoricos[i].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); //Subistituir ScreenInternalX/5 por +1 no Linux
			Categoricos[i].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			desktopCarregar.add(Categoricos[i]);
		}
        }else if (BtnListener.FiltrarTamanho) {
                
		for(int i = 0; i <= s.length-1;i++){
			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]+ " ➞ " + infoVisModule.NomeDosTamanhos[i] + " " );
			Categoricos[i].addActionListener(new BtnListener());
			Categoricos[i].setSize(ButtonW,ButtonH);
			Categoricos[i].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); //Subistituir ScreenInternalX/5 por +1 no Linux
			Categoricos[i].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			desktopCarregar.add(Categoricos[i]);
		}
        }else {
              for(int i = 0; i <= s.length-1;i++){
			Categoricos[i] = new JCheckBox(i+1+"-"+s[i]);
			Categoricos[i].addActionListener(new BtnListener());
			Categoricos[i].setSize(ButtonW,ButtonH);
			Categoricos[i].setLocation(ScreenInternalX/5+ScreenInternalX + Pace, ButtonY); //Subistituir ScreenInternalX/5 por +1 no Linux
			Categoricos[i].setOpaque(false);
			Pace += ButtonW;
			if(Pace == 5*ButtonW){ // A largura do botao corresponde a 20% do painel
				ButtonY += ButtonH;
				Pace = 0;
			}
			desktopCarregar.add(Categoricos[i]);
		}
                }
                
            if (!infoVisModule.FiltrarXCategorico.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.FiltrarXCategorico.contains(Categoricos[i].getText().substring(Categoricos[i].getText().indexOf('-')+1))) {
                            Categoricos[i].setSelected(true);
                        }
                    }
            }
            if (!infoVisModule.FiltrarYCategorico.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.FiltrarYCategorico.contains(Categoricos[i].getText().substring(Categoricos[i].getText().indexOf('-')+1))) {
                            Categoricos[i].setSelected(true);
                        }
                    }
            }
            if (!infoVisModule.FiltrarZCategorico.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.FiltrarZCategorico.contains(Categoricos[i].getText().substring(Categoricos[i].getText().indexOf('-')+1))) {
                            Categoricos[i].setSelected(true);
                        }
                    }
            }
             if (!infoVisModule.FiltrarCorCategorico.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.FiltrarCorCategorico.containsKey(Categoricos[i].getText().substring(Categoricos[i].getText().indexOf('-')+1))) {
                            Categoricos[i].setSelected(true);
                        }
                    }
            }
             if (!infoVisModule.FiltrarFormaCategorico.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.FiltrarFormaCategorico.containsKey(Categoricos[i].getText().substring(Categoricos[i].getText().indexOf('-')+1))) {
                            Categoricos[i].setSelected(true);
                        }
                    }
            }
             if (!infoVisModule.FiltrarTamanhoCategorico.isEmpty()) {
                    for (int i = 0; i < s.length; i++) {
                        if (infoVisModule.FiltrarTamanhoCategorico.containsKey(Categoricos[i].getText().substring(Categoricos[i].getText().indexOf('-')+1))) {
                            Categoricos[i].setSelected(true);
                        }
                    }
            }
	}
	
	public static void BtnMover(){
		int
		ScreenInternalW = WidthScreen/5, 
		ScreenInternalH = HeightScreen - HeightScreen/5,
		ScreenInternalX = WidthScreen - WidthScreen/5, 
		ScreenInternalY = HeightScreen/20,
		ButtonW = ScreenInternalW - ScreenInternalW/10, ButtonH = ScreenInternalH/10,
		ButtonX = ScreenInternalX  + ButtonW/17, ButtonY = ScreenInternalY;
		
		painelInteragir.setTitle("Mover");
		
		Esquerda.setSize(ButtonW,ButtonH);
		Esquerda.setLocation(ButtonX,ButtonY+ButtonH);
		Direita.setSize(ButtonW,ButtonH);
		Direita.setLocation(ButtonX,ButtonY+ButtonH*2);
		Cima.setSize(ButtonW,ButtonH);
		Cima.setLocation(ButtonX,ButtonY+ButtonH*3);
		Baixo.setSize(ButtonW,ButtonH);
		Baixo.setLocation(ButtonX,ButtonY+ButtonH*4);
		Frente.setSize(ButtonW,ButtonH);
		Frente.setLocation(ButtonX,ButtonY+ButtonH*5);
		Tras.setSize(ButtonW,ButtonH);
		Tras.setLocation(ButtonX,ButtonY+ButtonH*6);
		EstadoInicial.setSize(ButtonW,ButtonH);
		EstadoInicial.setLocation(ButtonX,ButtonY+ButtonH*7);
		Parar.setSize(ButtonW,ButtonH);
		Parar.setLocation(ButtonX,ButtonY+ButtonH*8);
		
                if (infoVisModule.IsPlot3D) {
                desktopInteragir.add(plot3D);
                }else
                desktopInteragir.add(plot2D);
                
		desktopInteragir.add(Esquerda);
		desktopInteragir.add(Direita);
		desktopInteragir.add(Cima);
		desktopInteragir.add(Baixo);
		desktopInteragir.add(Frente);
		desktopInteragir.add(Tras);
		desktopInteragir.add(EstadoInicial);
		desktopInteragir.add(Parar);
		
	}
	
	public static void BtnGirar(){
		int
		ScreenInternalW = WidthScreen/5, 
		ScreenInternalH = HeightScreen - HeightScreen/5,
		ScreenInternalX = WidthScreen - WidthScreen/5, 
		ScreenInternalY = HeightScreen/20,
		ButtonW = ScreenInternalW - ScreenInternalW/10, ButtonH = ScreenInternalH/10,
		ButtonX = ScreenInternalX  + ButtonW/17, ButtonY = ScreenInternalY;
		
		painelInteragir.setTitle("Girar");
		
		Esquerda.setSize(ButtonW,ButtonH);
		Esquerda.setLocation(ButtonX,ButtonY+ButtonH);
		Direita.setSize(ButtonW,ButtonH);
		Direita.setLocation(ButtonX,ButtonY+ButtonH*2);
		Cima.setSize(ButtonW,ButtonH);
		Cima.setLocation(ButtonX,ButtonY+ButtonH*3);
		Baixo.setSize(ButtonW,ButtonH);
		Baixo.setLocation(ButtonX,ButtonY+ButtonH*4);
		Frente.setSize(ButtonW,ButtonH);
		Frente.setLocation(ButtonX,ButtonY+ButtonH*5);
		Tras.setSize(ButtonW,ButtonH);
		Tras.setLocation(ButtonX,ButtonY+ButtonH*6);
		EstadoInicial.setSize(ButtonW,ButtonH);
		EstadoInicial.setLocation(ButtonX,ButtonY+ButtonH*7);
		Parar.setSize(ButtonW,ButtonH);
		Parar.setLocation(ButtonX,ButtonY+ButtonH*8);
		
		desktopInteragir.add(Esquerda);
		desktopInteragir.add(Direita);
		desktopInteragir.add(Cima);
		desktopInteragir.add(Baixo);
		desktopInteragir.add(Frente);
		desktopInteragir.add(Tras);
                desktopInteragir.add(EstadoInicial);
		desktopInteragir.add(Parar);
                
                if (infoVisModule.IsPlot3D) {
                desktopInteragir.add(plot3D);
                }else
                desktopInteragir.add(plot2D);
		
	}
        
        public static void BtnsDetails(){
                LimparPainel(desktopIFC);
		int 
		ButtonW = ScreenInternalW/4, ButtonH = ScreenInternalH/4,
		ButtonX = ScreenInternalX/2, ButtonY = ScreenInternalY+ScreenInternalY/10;  //Ajustar em 8(tirar o 10 e subistuir) para o Linux
//		LimparPainel(desktopIFC);
		//Seta titulo do painel
		painelIFC.setTitle("Detalhes Sobre Demanda");
               
                
          if (Vis3D.QtdPontosVisuais > 10) {
            
              if (infoVisModule.IsPlot3D) {
                Quadrant1.setSize(ButtonW/2,ButtonH/2); 
		Quadrant1.setLocation(ButtonX + ButtonW/2, ButtonY - (ButtonY/50)* 4);
                
		Quadrant2.setSize(ButtonW/2,ButtonH/2); 
//		Quadrant2.setLocation(ButtonX + 3*ButtonW  ,ButtonY - (ButtonY/50)* 4);
		Quadrant2.setLocation((ButtonW/2) + ButtonX + ButtonW/2  ,ButtonY - (ButtonY/50)* 4);
		
		Quadrant3.setSize(ButtonW/2,ButtonH/2); 
//		Quadrant3.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
		Quadrant3.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
                
		Quadrant4.setSize(ButtonW/2,ButtonH/2); 
//		Quadrant4.setLocation(ButtonX + 3*ButtonW  ,ButtonY- ButtonY/50);
		Quadrant4.setLocation((ButtonW/2) + ButtonX + ButtonW/2 ,ButtonY- ButtonY/50);
                
                Quadrant5.setSize(ButtonW/2,ButtonH/2); 
		Quadrant5.setLocation(ButtonX + 3*ButtonW, ButtonY - (ButtonY/50)* 4);
                
		Quadrant6.setSize(ButtonW/2,ButtonH/2); 
		Quadrant6.setLocation((ButtonX + 3*ButtonW) + ButtonW/2  ,ButtonY - (ButtonY/50)* 4);
		
		Quadrant7.setSize(ButtonW/2,ButtonH/2); 
		Quadrant7.setLocation(ButtonX + 3*ButtonW, ButtonY - ButtonY/50);
                
		Quadrant8.setSize(ButtonW/2,ButtonH/2); 
		Quadrant8.setLocation((ButtonX + 3*ButtonW) + ButtonW/2  ,ButtonY- ButtonY/50);
                
                EstadoInicial.setText("Estado Inicial");
		EstadoInicial.setSize(ButtonW,ButtonH); 
		EstadoInicial.setLocation(ScreenInternalW/2, (int) (ButtonY - (ButtonY/50)*3));
                
                
		Quadrant1.setEnabled(true);
		Quadrant2.setEnabled(true);
		Quadrant3.setEnabled(true);
		Quadrant4.setEnabled(true);
		Quadrant5.setEnabled(true);
		Quadrant6.setEnabled(true);
		Quadrant7.setEnabled(true);
		Quadrant8.setEnabled(true);
		EstadoInicial.setEnabled(true);
                //Adiciona os btns no desktop
		desktopIFC.add(Quadrant1);
		desktopIFC.add(Quadrant2);
		desktopIFC.add(Quadrant3);
		desktopIFC.add(Quadrant4);
		desktopIFC.add(Quadrant5);
		desktopIFC.add(Quadrant6);
		desktopIFC.add(Quadrant7);
		desktopIFC.add(Quadrant8);
		desktopIFC.add(EstadoInicial);
                    
                desktopIFC.add(plot3D);
                
                }else{
                Quadrant1.setSize(ButtonW,ButtonH); 
		Quadrant1.setLocation(ButtonX + ButtonW/2, ButtonY - (ButtonY/50)* 4);
                
		Quadrant2.setSize(ButtonW,ButtonH);
		Quadrant2.setLocation(ButtonX + 3*ButtonW  ,ButtonY - (ButtonY/50)* 4);
		
		Quadrant3.setSize(ButtonW,ButtonH); 
		Quadrant3.setLocation(ButtonX + ButtonW/2, ButtonY - ButtonY/50);
                
		Quadrant4.setSize(ButtonW,ButtonH); 
		Quadrant4.setLocation(ButtonX + 3*ButtonW  ,ButtonY- ButtonY/50);
                
                BreadCrumb1.setText("Estado Inicial");
		BreadCrumb1.setSize(ButtonW,ButtonH); 
//		BreadCrumb1.setLocation((int) (ButtonX + (2.2*ButtonW)), (int) (ButtonY - (ButtonY/50)*3));
		BreadCrumb1.setLocation(ScreenInternalW/2, (int) (ButtonY - (ButtonY/50)*3));
                
                
		Quadrant1.setEnabled(true);
		Quadrant2.setEnabled(true);
		Quadrant3.setEnabled(true);
		Quadrant4.setEnabled(true);
		BreadCrumb1.setEnabled(true);
                //Adiciona os btns no desktop
		desktopIFC.add(Quadrant1);
		desktopIFC.add(Quadrant2);
		desktopIFC.add(Quadrant3);
		desktopIFC.add(Quadrant4);
		desktopIFC.add(BreadCrumb1);
                    
                    desktopIFC.add(plot2D);
                
                
                }
          }else{
              System.out.println("ENKENJKENKENKENKENKENKENEKNEKNEKNEKN");
              JButton[] ZeroToNine = {zero,one,two,three,four,five,six,seven,eight,nine};
              zero.setEnabled(false);
              one.setEnabled(false);
              two.setEnabled(false);
              three.setEnabled(false);
              four.setEnabled(false);
              five.setEnabled(false);
              six.setEnabled(false);
              seven.setEnabled(false);
              eight.setEnabled(false);
              nine.setEnabled(false);
              
              for (int i = 0; i < Vis3D.QtdPontosVisuais; i++) {
                  ZeroToNine[i].setEnabled(true);
              }
              
              
                zero.setSize(ButtonW/2,ButtonH/2); 
		zero.setLocation(ButtonX + ButtonW/2, ButtonY - (ButtonY/50)* 4);
                
		one.setSize(ButtonW/2,ButtonH/2); 
		one.setLocation(ButtonX + 2*ButtonW/2 + ButtonW/4   ,ButtonY - (ButtonY/50)* 4);
		
		two.setSize(ButtonW/2,ButtonH/2); 
		two.setLocation(ButtonX + 3*ButtonW/2 + 2*ButtonW/4, ButtonY - (ButtonY/50)* 4);
                
		three.setSize(ButtonW/2,ButtonH/2); 
		three.setLocation(ButtonX + 4*ButtonW/2 + 3*ButtonW/4 ,ButtonY- (ButtonY/50)* 4);
                
                four.setSize(ButtonW/2,ButtonH/2); 
		four.setLocation(ButtonX + 5*ButtonW/2 + 4*ButtonW/4, ButtonY - (ButtonY/50)* 4);
                
		five.setSize(ButtonW/2,ButtonH/2); 
		five.setLocation(ButtonX + ButtonW/2  ,ButtonY - (ButtonY/50));
		
		six.setSize(ButtonW/2,ButtonH/2); 
		six.setLocation(ButtonX + 2*ButtonW/2 + ButtonW/4, ButtonY - ButtonY/50);
                
		seven.setSize(ButtonW/2,ButtonH/2); 
		seven.setLocation(ButtonX + 3*ButtonW/2 + 2*ButtonW/4  ,ButtonY- ButtonY/50);
                
		eight.setSize(ButtonW/2,ButtonH/2); 
		eight.setLocation(ButtonX + 4*ButtonW/2 + 3*ButtonW/4  ,ButtonY- ButtonY/50);
		
                nine.setSize(ButtonW/2,ButtonH/2); 
		nine.setLocation(ButtonX + 5*ButtonW/2 + 4*ButtonW/4 ,ButtonY- ButtonY/50);
                
                EstadoInicial.setText("Estado Inicial");
                EstadoInicial.setSize(ButtonW,ButtonH); 
                EstadoInicial.setLocation(ScreenInternalW/2, (int) (ButtonY - (ButtonY/50)*3));
                EstadoInicial.setEnabled(true);
                
                //Adiciona os btns no desktop
		desktopIFC.add(zero);
		desktopIFC.add(one);
		desktopIFC.add(two);
		desktopIFC.add(three);
		desktopIFC.add(four);
		desktopIFC.add(five);
		desktopIFC.add(six);
		desktopIFC.add(seven);
		desktopIFC.add(eight);
		desktopIFC.add(nine);
		desktopIFC.add(EstadoInicial);
                 
                if (Vis3D.IsPlot3D) {
                  desktopIFC.add(plot3D);
              }else{
                desktopIFC.add(plot2D);
                }
          }
		
	}
        
	public static String GetBgBase(){
		String path = "";
		boolean base = true;
		for(int i = 0; i <= Main.dir.GetFileNames().length-1;i++){ // Verifica a gramatica do diretorio 
					if(bases[i].isSelected()){
						base = true;
						path = Main.dir.GetDirectory()+"/"+Main.dir.GetFileNames()[i];
					}else
						base = false;

		}
		return path;
	} 

	public static void LimparPainel(JDesktopPane desk){
		desk.removeAll();
		desk.revalidate();
		desk.repaint();
	}
}