package IVOrpheus2Final;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javax.speech.AudioException;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.swing.JButton;
import javax.swing.JComponent;

// Classe que transforma comandos recebidos em apertar de botões
public class Tradutor {
	public static boolean MoverGirar = false, Redimensionar = false
			,ConfigurarBool = false, FiltrarBool = false,Inicio = false, Fim = false,
			PrimeiroNumeroInicio = true, PrimeiroNumeroFim = true; // true para mover e false para girar
	public static double EscalaValor = 1.0; // true para mover e false para girar
	public static double EscalaLimiteMax, EscalaLimiteMin; // true para mover e false para girar
	public static boolean EixoXTradutor = false, EixoYTradutor = false,EixoZTradutor = false; 
	public static int EixoIndice;
	public static JComponent traduzir(String s) throws AudioException, EngineStateError, EngineException, GrammarException, IOException, ParseException{
		EscalaLimiteMax = 1.8;
		EscalaLimiteMin = 0.2;
		System.out.print("foi dito - "+s);
	//	if((s.equals("confirmar")==true)&&(IMATVI.Interface.GetBgBase())){
		if(s.equals("confirmar")){
			IVOrpheus2Final.Interface.AllAxisChecked();
			Main.att.SetPath(Interface.GetBgBase().replaceAll("\\s+",""));
			Reconhecedor.GramIFC();
			Interface.BtnsIFC();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
			return Interface.Confirmar;
		}else if(s.equals("cancelar")){
			IVOrpheus2Final.Interface.Cancelar();
			return Interface.Cancelar;
		}else if(s.equals("carregar")){ //Painel Carregar
			Reconhecedor.GramCarregar();
			//Reconhecedor.Funciona();
			IVOrpheus2Final.Interface.BtnsBase();
			Interface.painelCarregar.setTitle("Carregar Base");
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			return Interface.menu_Carregar;  
		}else if(s.equals("ajuda")){
			return Interface.menu_ajuda;
		}else if(s.equals("legenda")){
			if(Interface.menu_legenda.isSelected()){
				Interface.plot.plotLegend.setVisible(true);
			}else
				Interface.plot.plotLegend.setVisible(false);
			return Interface.menu_legenda;	
		}else if(s.equals("voltar")){ 
				Reconhecedor.GramIFC();
				Interface.Voltar();
				Interface.BtnsIFC();
				Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
			return Interface.menu_Voltar;
		}else if(s.equals("interagir")){ // Interagir
			Reconhecedor.GramInteragir();
			Interface.BtnsI();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
			return Interface.Interagir;
		}else if(s.equals("escala")){  //Escala
			Interface.EscalaAtual.setText("Escala "+EscalaValor);
			Reconhecedor.GramEscala();
			Interface.BtnEscala();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelInteragir());
			return Interface.Escala;
		}else if(s.equals("aumentar")){
			if(EscalaValor > EscalaLimiteMin)
			EscalaValor -= 0.2;
			Interface.plot.setDefaultZoom(EscalaValor);
			Interface.plot.rotate(0, 0);
			Interface.EscalaAtual.setText("Escala "+EscalaValor);
			return Interface.Aumentar;
			
		}else if(s.equals("diminuir")){
			if(EscalaValor < EscalaLimiteMax)
			EscalaValor += 0.2;
			Interface.plot.setDefaultZoom(EscalaValor);
			Interface.plot.rotate(0, 0);
			Interface.EscalaAtual.setText("Escala "+EscalaValor);
			return Interface.Diminuir;
			
		}else if(s.equals("mover")){ //Mover
			MoverGirar = true;
			Reconhecedor.GramMG();
			Interface.BtnMover();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelInteragir());
                        Main.Interface.setVisible(true);
			return Interface.Mover;
			
		}else if(s.equals("frente")){ 
			if(MoverGirar){
				Interface.plot.move(Interface.plot.getX()+10, Interface.plot.getY()+10);
				return Interface.Frente;
			} else
				Interface.plot.rotate(Interface.plot.getX()+1, Interface.plot.getY());
				return Interface.Frente;
				
		}else if(s.equals("atrás")){
			if(MoverGirar){	
			Interface.plot.move(Interface.plot.getX()-10, Interface.plot.getY()-10);
			return Interface.Tras;
		} else
			Interface.plot.rotate(Interface.plot.getX()+10, Interface.plot.getY()+10);
			return Interface.Tras;
			
		}else if(s.equals("cima")){
			if(MoverGirar){	
				Interface.plot.move(Interface.plot.getX(), Interface.plot.getY()-10);	
				return Interface.Cima;
			} else
				//Interface.plot.rotate(Interface.plot.getX(), Interface.plot.getY()+2);
				Interface.plot.rotate(0,2);
			return Interface.Cima;
			
		}else if(s.equals("baixo")){
			if(MoverGirar){	
				Interface.plot.move(Interface.plot.getX(), Interface.plot.getY()+10);
				return Interface.Baixo;
			}else
			//Interface.plot.rotate(Interface.plot.getX(), Interface.plot.getY()-2);
			Interface.plot.rotate(0,-2);
			return Interface.Baixo;
			
		}else if(s.equals("esquerda")){
			if(MoverGirar){	
				Interface.plot.move(Interface.plot.getX()-10, Interface.plot.getY());
				return Interface.Esquerda;
			}else
			//Interface.plot.rotate(Interface.plot.getX()-2, Interface.plot.getY());
			Interface.plot.rotate(-2, 0);
			return Interface.Esquerda;
			
		}else if(s.equals("direita")){
			if(MoverGirar){	
				Interface.plot.move(Interface.plot.getX()+10, Interface.plot.getY());
				return Interface.Direita;
			}else
			//Interface.plot.rotate(Interface.plot.getX()+2, Interface.plot.getY());
			Interface.plot.rotate(2, 0);
			return Interface.Direita;
			
		}else if(s.equals("girar")){ //Girar
			MoverGirar = false;
			Reconhecedor.GramMG();
			Interface.BtnGirar();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelInteragir());
			return Interface.Girar;
		//}else if(s.equals("voltar")){ // Voltar
		//	Reconhecedor.GramIFC();
		//	Interface.Voltar();
		//	Interface.BtnsIFC();
		//	Main.Interface.setContentPane(((IMATVI.Interface) Main.Interface).PainelIFC());
		//	return Interface.Voltar;
		}else if(s.equals("filtrar")&&(Interface.Filtrar.isEnabled())){ // Filtrar
			ConfigurarBool = false;
			FiltrarBool = true;
			Reconhecedor.GramFC();
			Interface.painelCarregar.setTitle("Filtar");
			Interface.BtnsF();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
			return Interface.Filtrar;
		}else if(s.equals("cor")){
			Reconhecedor.GramAtributos();
			Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle()+" Cor");
			IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			return Interface.Cor;
		}else if(s.equals("forma")){
			Reconhecedor.GramAtributos();
			Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle()+" Forma");
			IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			return Interface.Forma;
		}else if(s.equals("tamanho")){
			Reconhecedor.GramAtributos();
			Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle()+" Tamanho");
			IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			return Interface.Tamanho;
		}else if(s.equals("xis")){
			PrimeiroNumeroInicio = true; // S'o para teste usar no confirmar do filtrar pensar em colocar os comandos da gramatica filtrar na barra de status
			PrimeiroNumeroFim = true;
			if(FiltrarBool){
				if(Main.att.AttTypes().get(Interface.indexX).equals("FLOAT")){
				EixoIndice = 0;
				Reconhecedor.GramFiltrar();
				Interface.BtnFiltrarDecimal();
				Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
				Interface.InicioLbl.setText(""+Interface.GetMinMaxValueX()[0]);
				Interface.FimLbl.setText(""+Interface.GetMinMaxValueX()[1]);
				Interface.InicioFixoLbl.setText(Interface.InicioFixoLbl.getText()+Interface.GetMinMaxValueX()[0]);
				Interface.FimFixoLbl.setText(Interface.FimFixoLbl.getText()+Interface.GetMinMaxValueX()[1]);
				}else{
				EixoIndice = 0;
				Reconhecedor.GramFiltrar();
				Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(Interface.indexX));
				Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
				}
				}
			if(ConfigurarBool){
			EixoXTradutor = true;
			EixoZTradutor = false;
			EixoYTradutor = false;
			Reconhecedor.GramAtributos();
			Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle()+" Eixo X");
			IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			}
			return Interface.EixoX;
		}else if(s.equals("ipselon")){
			if(FiltrarBool){
				if(Main.att.AttTypes().get(Interface.indexY).equals("FLOAT")){
				EixoIndice = 1;
				Reconhecedor.GramFiltrar();
				Interface.BtnFiltrarDecimal();
				Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
				Interface.InicioLbl.setText(""+Interface.GetMinMaxValueY()[0]);
				Interface.FimLbl.setText(""+Interface.GetMinMaxValueY()[1]);
				Interface.InicioFixoLbl.setText(Interface.InicioFixoLbl.getText()+Interface.GetMinMaxValueY()[0]);
				Interface.FimFixoLbl.setText(Interface.FimFixoLbl.getText()+Interface.GetMinMaxValueY()[1]);
			}else{
				EixoIndice = 1;
				Reconhecedor.GramFiltrar();
				Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(Interface.indexY));
				Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
				}
			}
			if(ConfigurarBool){
			EixoYTradutor = true;
			EixoXTradutor = false;
			EixoZTradutor = false;
			Reconhecedor.GramAtributos();
			Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle()+" Eixo Y");
			IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			}
			return Interface.EixoY;
		}else if(s.equals("ze")){
			if(FiltrarBool){
				if(Main.att.AttTypes().get(Interface.indexZ).equals("FLOAT")){
				Reconhecedor.GramFiltrar();
				Interface.BtnFiltrarDecimal();
				Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
				Interface.InicioLbl.setText(""+Interface.GetMinMaxValueZ()[0]);
				Interface.FimLbl.setText(""+Interface.GetMinMaxValueZ()[1]);
				Interface.InicioFixoLbl.setText(Interface.InicioFixoLbl.getText()+Interface.GetMinMaxValueZ()[0]);
				Interface.FimFixoLbl.setText(Interface.FimFixoLbl.getText()+Interface.GetMinMaxValueZ()[1]);
				EixoIndice = 2;
				}else{
					EixoIndice = 2;
					Reconhecedor.GramFiltrar();
					Interface.BtnFiltrarCategorico(Main.att.GetUniqueValues(Interface.indexZ));
					Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
					}	
			}
			if(ConfigurarBool){
			EixoZTradutor = true;
			EixoXTradutor = false;
			EixoYTradutor = false;
			Reconhecedor.GramAtributos();
			Interface.painelCarregar.setTitle(Interface.painelCarregar.getTitle()+" Eixo Z");
			IVOrpheus2Final.Interface.BaseFC(Main.att.GetFileAttributes());
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelCarregar());
			}
			return Interface.EixoZ;
		}else if(s.equals("configurar")){ //Configurar
			ConfigurarBool = true;
			FiltrarBool = false;
			Reconhecedor.GramFC();
			Interface.painelCarregar.setTitle("Configurar");
			Interface.BtnsC();
			Main.Interface.setContentPane(((IVOrpheus2Final.Interface) Main.Interface).PainelIFC());
			return Interface.Configurar;
			
		}else if(s.equals("inicio")){ //Painel Filtrar
			Inicio = true;
			Fim = false;
			return Interface.InicioBtn;
			
		}else if(s.equals("termino")){
			Fim = true;
			Inicio = false;
			return Interface.FimBtn;
			
		}else if(s.equals("um")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("1");
				PrimeiroNumeroInicio = false;
			} else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "1");
			}
			
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("1");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "1");
			}
					
			}else if(s.equals("dois")){ 
				if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
					Interface.InicioLbl.setText("2");
					PrimeiroNumeroInicio = false;
				} else if(Inicio && !PrimeiroNumeroInicio){
					Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "2");
			}
				if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
					Interface.FimLbl.setText("2");
					PrimeiroNumeroFim = false;
				} else if(Fim && !PrimeiroNumeroFim){
				Interface.FimLbl.setText(Interface.FimLbl.getText() + "2");
				}
		
			}else if(s.equals("três")){ 
				if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
					Interface.InicioLbl.setText("3");
					PrimeiroNumeroInicio = false;
				}else if(Inicio && !PrimeiroNumeroInicio){
					Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "3");
			}
				if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
					Interface.FimLbl.setText("3");
					PrimeiroNumeroFim = false;
				}else if(Fim && !PrimeiroNumeroFim){
				Interface.FimLbl.setText(Interface.FimLbl.getText() + "3");
				}
				
		}else if(s.equals("quatro")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("4");
				PrimeiroNumeroInicio = false;
			}else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "4");
		}
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("4");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "4");
			}
		}else if(s.equals("cinco")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("5");
				PrimeiroNumeroInicio = false;
			}else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "5");
		}
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("5");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "5");
			}
		}else if(s.equals("seis")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("6");
				PrimeiroNumeroInicio = false;
			}else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "6");
			}
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("6");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "6");
			}
		}else if(s.equals("sete")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("7");
				PrimeiroNumeroInicio = false;
			}else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "7");
		}
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("7");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "7");
			}
		}else if(s.equals("oito")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("8");
				PrimeiroNumeroInicio = false;
			}else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "8");
		}
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("8");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "8");
			}
		}else if(s.equals("nove")){ 
			if((Inicio && PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.InicioLbl.setText("9");
				PrimeiroNumeroInicio = false;
			}else if(Inicio && !PrimeiroNumeroInicio){
				Interface.InicioLbl.setText(Interface.InicioLbl.getText() + "9");
		}
			if((Fim && PrimeiroNumeroFim)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0"))){
				Interface.FimLbl.setText("9");
				PrimeiroNumeroFim = false;
			}else if(Fim && !PrimeiroNumeroFim){
			Interface.FimLbl.setText(Interface.FimLbl.getText() + "9");
			}
		}else if(s.equals("zero")){ 
			if(Inicio){
				 // o primeiro caracter é 0 ?
               // if((Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0")))
				if((PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0")))
				{
                	Interface.InicioLbl.setText("0");
                }
                else
                {
                	Interface.InicioLbl.setText(Interface.InicioLbl.getText()+"0");
                }
            }
		if(Fim){
			 // o primeiro caracter é 0 ?
          //  if((Interface.FimLbl.getText().length() == 1) && (Interface.FimLbl.getText().equals("0")))
			if((!PrimeiroNumeroInicio)||(Interface.InicioLbl.getText().length() == 1) && (Interface.InicioLbl.getText().equals("0")))
			{
            	Interface.FimLbl.setText("0");
            }
            else
            {
            	Interface.FimLbl.setText(Interface.FimLbl.getText()+"0");
            }
        }
		}else if(s.equals("ponto")||s.equals("virgula")){ 
			if(Inicio){
			// tem ponto ?
            if(Interface.InicioLbl.getText().indexOf(".") < 0)
            {
                // é o primeiro caracter ?
                if(Interface.InicioLbl.getText().length() < 1)
                {
                	Interface.InicioLbl.setText("0.");
                }
                else
                {
                	Interface.InicioLbl.setText(Interface.InicioLbl.getText()+".");
                }
            }
			}
				if(Fim){
					 if(Interface.FimLbl.getText().indexOf(".") < 0)
			            {
			                // é o primeiro caracter ?
			                if(Interface.FimLbl.getText().length() < 1)
			                {
			                	Interface.FimLbl.setText("0.");
			                }
			                else
			                {
			                	Interface.FimLbl.setText(Interface.FimLbl.getText()+".");
			                }
			            }
				}
		}else if(s.equals("apagar")){ 
			if(Inicio){
				//Interface.InicioLbl.setText(Interface.InicioLbl.getText() + ".");
				if(Interface.InicioLbl.getText().length() > 0)
	            {
					Interface.InicioLbl.setText(Interface.InicioLbl.getText() .substring(0,Interface.InicioLbl.getText().length()-1));
	            }
	           
	            // o tamanho da string é igual a zero ?    
	            if(Interface.InicioLbl.getText().length() == 0)
	            {
	                Interface.InicioLbl.setText("0");
	            }
			}if(Fim){
				//Interface.FimLbl.setText(Interface.FimLbl.getText() + ".");
				if(Interface.FimLbl.getText().length() > 0)
	            {
					Interface.FimLbl.setText(Interface.FimLbl.getText() .substring(0,Interface.FimLbl.getText().length()-1));
	            }
	           
	            // o tamanho da string é igual a zero ?    
	            if(Interface.FimLbl.getText().length() == 0)
	            {
	                Interface.FimLbl.setText("0");
	            }
			}
			
		}else if(s.equals("limpar")){ 
			if(Inicio)
				Interface.InicioLbl.setText("0");
            
			if(Fim)
				Interface.FimLbl.setText("0");
			
		}else if(s.equals("marca")){
			if(EixoXTradutor){
				System.out.println("DENTRO DO X");
				Interface.PlotBaseX(0,"Marca");
			} if(EixoYTradutor){
				System.out.println("DENTRO DO Y");
				Interface.PlotBaseY(0,"Marca");
			} if(EixoZTradutor){
				System.out.println("DENTRO DO Z");
				Interface.PlotBaseZ(0,"Marca");
			}
			return Interface.atributos[0];
		}else if(s.equals("combustivel")){
			if(EixoXTradutor){
				Interface.PlotBaseX(1,"combustivel");
			} if(EixoYTradutor){
				Interface.PlotBaseY(1,"combustivel");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(1,"combustivel");
			}
			return Interface.atributos[1];
		}else if(s.equals("turbo")){
			if(EixoXTradutor){
				Interface.PlotBaseX(2,"turbo");
			} if(EixoYTradutor){
				Interface.PlotBaseY(2,"turbo");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(2,"turbo");
			}
			return Interface.atributos[2];
		}else if(s.equals("portas")){
			if(EixoXTradutor){
				Interface.PlotBaseX(3,"Portas");
			} if(EixoYTradutor){
				Interface.PlotBaseY(3,"Portas");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(3,"Portas");
			}
			return Interface.atributos[3];
		}else if(s.equals("comprimento")){
			if(EixoXTradutor){
				Interface.PlotBaseX(4,"Comprimento");
			} if(EixoYTradutor){
				Interface.PlotBaseY(4,"Comprimento");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(4,"Comprimento");
			}
			return Interface.atributos[4];
		}else if(s.equals("tipo")){
			if(EixoXTradutor){
				Interface.PlotBaseX(5,"tipo");
			} if(EixoYTradutor){
				Interface.PlotBaseY(5,"tipo");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(5,"tipo");
			}
			return Interface.atributos[5];
		}else if(s.equals("traçao")){
			if(EixoXTradutor){
				Interface.PlotBaseX(6,"tração");
			} if(EixoYTradutor){
				Interface.PlotBaseY(6,"tração");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(6,"tração");
			}
			return Interface.atributos[6];
		}else if(s.equals("motor")){
			if(EixoXTradutor){
				Interface.PlotBaseX(6,"motor");
			} if(EixoYTradutor){
				Interface.PlotBaseY(6,"motor");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(6,"motor");
			}
			return Interface.atributos[7];
		}else if(s.equals("largura")){
			if(EixoXTradutor){
				Interface.PlotBaseX(7,"largura");
			} if(EixoYTradutor){
				Interface.PlotBaseY(7,"largura");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(7,"largura");
			}
			return Interface.atributos[8];
		}else if(s.equals("altura")){
			if(EixoXTradutor){
				Interface.PlotBaseX(8,"altura");
			} if(EixoYTradutor){
				Interface.PlotBaseY(8,"altura");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(8,"altura");
			}
			return Interface.atributos[9];
		}else if(s.equals("peso")){
			if(EixoXTradutor){
				Interface.PlotBaseX(9,"peso");
			} if(EixoYTradutor){
				Interface.PlotBaseY(9,"peso");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(9,"peso");
			}
			return Interface.atributos[10];
		}else if(s.equals("cilindros")){
			if(EixoXTradutor){
				Interface.PlotBaseX(10,"cilindros");
			} if(EixoYTradutor){
				Interface.PlotBaseY(10,"cilindros");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(10,"cilindros");
			}
			return Interface.atributos[11];
		}else if(s.equals("potencia")){
			if(EixoXTradutor){
				Interface.PlotBaseX(11,"potência");
			} if(EixoYTradutor){
				Interface.PlotBaseY(11,"potência");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(11,"potência");
			}
			return Interface.atributos[12];
		}else if(s.equals("rpm")||s.equals("rotaçao")){
			if(EixoXTradutor){
				Interface.PlotBaseX(12,"rotação");
			} if(EixoYTradutor){
				Interface.PlotBaseY(12,"rotação");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(12,"rotação");
			}
			return Interface.atributos[13];
		}else if(s.equals("valor")){
			if(EixoXTradutor){
				Interface.PlotBaseX(13,"valor");
			} if(EixoYTradutor){
				Interface.PlotBaseY(13,"valor");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(13,"valor");
			}
			return Interface.atributos[14];
		}else if(s.equals("ano")){
			if(EixoXTradutor){
				Interface.PlotBaseX(14,"ano");
			} if(EixoYTradutor){
				Interface.PlotBaseY(14,"ano");
			} if(EixoZTradutor){
				Interface.PlotBaseZ(14,"ano");
			}
			return Interface.atributos[15];
		// else if (s.equals(Main.dir.GetFileNames()[3].replaceAll("\\s+",""))){
		//	return Interface.menu_ajuda;
	}	else
			for(int i = 0; i <= Main.dir.GetFileNames().length-1;i++){ // Verifica a gramatica do diretorio 
			if(s.equals(Main.dir.GetFileNames()[i].replaceAll("\\s+",""))){
				return Interface.bases[i];
			}
	
} // for(int j = 0; j <= Main.att.GetFileAttributes().length-1;j++){ // Verifica a gramatica do diretorio 
//	if(s.equals(Main.att.GetFileAttributes()[j].replaceAll("\\s+",""))){
//		return Interface.atributos[j];
//	}

//}

	
	
		return null;
	
	}
}
