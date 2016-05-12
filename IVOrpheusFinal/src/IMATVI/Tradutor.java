package IMATVI;
import IVOrpheus2Final.Main;
import IVOrpheus2Final.BtnListener;
import IVOrpheus2Final.Vis3D;
import IVOrpheus2Final.Interface;
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
		if(s.equals("confirmar")){
			return Interface.Confirmar;
		}else if(s.equals("cancelar")){
			return Interface.Cancelar;
		}else if(s.equals("abrir")){ //Painel Carregar
			return Interface.menu_Carregar;  
		}else if(s.equals("capturar")){ //Painel Carregar
			return Interface.menu_Screenshot;  
		}else if(s.equals("detalhes")){ //Painel Carregar
			return Interface.menu_Detalhes;  
		}else if(s.equals("salvar")){ //Painel Carregar
			return Interface.menu_Salvar;  
		}else if(s.equals("ajuda")){
			return Interface.menu_ajuda;
		}else if(s.equals("legenda")){
			return Interface.menu_legenda;	
		}else if(s.equals("voltar")){ 
			return Interface.menu_Voltar;
		}else if(s.equals("interagir")){ // Interagir
			return Interface.Interagir;
		}else if(s.equals("escala")){  //Escala
			return Interface.Escala;
		}else if(s.equals("aumentar")){
			return Interface.Aumentar;
		}else if(s.equals("diminuir")){
			return Interface.Diminuir;
		}else if(s.equals("mover")){ //Mover
			return Interface.Mover;
		}else if(s.equals("frente")){ 
				return Interface.Frente;
		}else if(s.equals("atrás")){
			return Interface.Tras;
		}else if(s.equals("cima")){
			return Interface.Cima;
		}else if(s.equals("baixo")){
			return Interface.Baixo;
		}else if(s.equals("esquerda")){
			return Interface.Esquerda;
		}else if(s.equals("direita")){
			return Interface.Direita;
		}else if(s.equals("girar")){ //Girar
			return Interface.Girar;
		}else if(s.equals("filtrar")&&(Interface.Filtrar.isEnabled())){ // Filtrar
			return Interface.Filtrar;
		}else if(s.equals("cor")){
			return Interface.Cor;
		}else if(s.equals("forma")){
			return Interface.Forma;
		}else if(s.equals("tamanho")){
			return Interface.Tamanho;
		}else if(s.equals("xis")){
			return Interface.EixoX;
		}else if(s.equals("ipselon")){
			return Interface.EixoY;
		}else if(s.equals("ze")){
			return Interface.EixoZ;
		}else if(s.equals("configurar")){ //Configurar
			return Interface.Configurar;
		}else if(s.equals("parar")){ //Configurar
			return Interface.Parar;
		}else if(s.equals("estado")){ //Configurar
			return Interface.EstadoInicial;
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
		}else if(s.equals("seis") || s.equals("meia")){ 
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
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
				if(Interface.atributos[k].getText().equals("marca"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[0];
					}else
			return Interface.atributos[0];
		}else if(s.equals("combustivel")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("COMBUSTIVEL"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[1];
					}else
			return Interface.atributos[1];
		}else if(s.equals("turbo")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)||(Interface.menu_Detalhes.isSelected())){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("TURBO"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[2];
					}else
			return Interface.atributos[2];
		}else if(s.equals("portas")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("NRPORTAS"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[3];
					}else
			return Interface.atributos[3];
		}else if(s.equals("comprimento")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("COMPRIMENTO"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[4];
					}else
					return Interface.atributos[4];
		}else if(s.equals("tipo")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("TIPO"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[5];
					}else
			return Interface.atributos[5];
		}else if(s.equals("traçao")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("TRACAO"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[6];
					}else
					return Interface.atributos[6];
		}else if(s.equals("motor")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("MOTOR"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[7];
					}else
					return Interface.atributos[7];
		}else if(s.equals("largura")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("LARGURA"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[8];
					}else
					return Interface.atributos[8];
		}else if(s.equals("altura")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("ALTURA"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[9];
					}else
					return Interface.atributos[9];
		}else if(s.equals("peso")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("PESO"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[10];
					}else
					return Interface.atributos[10];
		}else if(s.equals("cilindros")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("NRCILINDROS"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[11];
					}else
					return Interface.atributos[11];
		}else if(s.equals("potencia")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("POTENCIA"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[12];
					}else
					return Interface.atributos[12];
		}else if(s.equals("rpm")||s.equals("rotaçao")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("RPM"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[13];
					}else
					return Interface.atributos[13];
		}else if(s.equals("valor")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("VALOR"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[14];
					}else
					return Interface.atributos[14];
		}else if(s.equals("ano")){
			if((BtnListener.ConfigurarCor)||(BtnListener.ConfigurarForma)||(BtnListener.ConfigurarTamanho)){
				for(int k = 0; k <= Interface.atributos.length-1;k++){
					System.out.println(Interface.atributos[k].getText());
				if(Interface.atributos[k].getText().substring(Interface.atributos[k].getText().indexOf('-')+1).equals("ANO"))
					return Interface.atributos[k];	
				}
				}else
					if(Interface.menu_Detalhes.isSelected()){
						return Interface.detalhes[15];
					}else
					return Interface.atributos[15];
		}		
		if(s.equals("um")&&(BtnListener.FiltrarCategoricos)){
			if(Interface.menu_Detalhes.isSelected()){
				if(Vis3D.QtdPontosVisuais < 10){
					return Interface.zero;
				}else{
					return Interface.Quadrant1;	
				}
			}
		return Interface.Categoricos[0];
	}
	else if(s.equals("dois")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.one;
			}else{
				return Interface.Quadrant2;	
			}
		}
	return Interface.Categoricos[1];
	}
	else if(s.equals("tres") || s.equals("três")&&(BtnListener.FiltrarCategoricos)){
		System.out.println("IKIKIKIKIKIIIKKKKIIKIIKIKIKKIKIIKIK");
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.three;
			}else{
				return Interface.Quadrant3;	
			}
		}
		return Interface.Categoricos[2];	
	}
	else if(s.equals("quatro")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.four;
			}else{
				return Interface.Quadrant4;	
			}
		}
		return Interface.Categoricos[3];
	}
	else if(s.equals("cinco")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.five;
			}else{
				return Interface.Quadrant5;	
			}
		}
		return Interface.Categoricos[4];
	}
	else if(s.equals("seis")||s.equals("meia")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.six;
			}else{
				return Interface.Quadrant6;	
			}
		}
		return Interface.Categoricos[5];
	}
	else if(s.equals("sete")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.seven;
			}else{
				return Interface.Quadrant7;	
			}
		}
		return Interface.Categoricos[6];
	}
	else if(s.equals("oito")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.eight;
			}else{
				return Interface.Quadrant8;	
			}
		}
		return Interface.Categoricos[7];
	}
	else if(s.equals("nove")&&(BtnListener.FiltrarCategoricos)){
		if(Interface.menu_Detalhes.isSelected()){
			if(Vis3D.QtdPontosVisuais < 10){
				return Interface.nine;
			}
		}
		return Interface.Categoricos[8];
	}
	else if(s.equals("déis")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[9];
	}
	else if(s.equals("onze")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[10];
	}
	else if(s.equals("doze")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[11];
	}
	else if(s.equals("treze")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[12];
	}
	else if(s.equals("quatorze")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[13];
	}
	else if(s.equals("quinze")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[14];
	}
	else if(s.equals("dezesseis")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[15];
	}
	else if(s.equals("dezessete")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[16];
	}
	else if(s.equals("dezoito")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[17];
	}
	else if(s.equals("dezenove")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[18];
	}
	else if(s.equals("vinte")&&(BtnListener.FiltrarCategoricos)){
		return Interface.Categoricos[19];
		
	}else if(s.equals("vinteeum")&&(BtnListener.FiltrarCategoricos)){
	return Interface.Categoricos[20];
	}else if(s.equals("vintedois")&&(BtnListener.FiltrarCategoricos)){
return Interface.Categoricos[21];

}	
else if(((s.equals("azul"))||(s.equals("um")))&&(BtnListener.FiltrarCor)){ //Configurar
			
			return Interface.Categoricos[0];
				}
			else if(((s.equals("vermelho"))||(s.equals("dois")))&&(BtnListener.FiltrarCor)){ //Configurar
					
			return Interface.Categoricos[1];
			}else if(((s.equals("verde"))||(s.equals("tres")))&&(BtnListener.FiltrarCor)){ //Configurar
				
			return Interface.Categoricos[2];
				}
			else if(((s.equals("laranja"))||(s.equals("quatro")))&&(BtnListener.FiltrarCor)){ //Configurar
					
			return Interface.Categoricos[3];
			}else if(((s.equals("ciano"))||(s.equals("cinco")))&&(BtnListener.FiltrarCor)){ //Configurar
			return Interface.Categoricos[4];
				}
			else if(((s.equals("magenta"))||(s.equals("seis")))&&(BtnListener.FiltrarCor)){ //Configurar
					
			return Interface.Categoricos[5];
			}else if(((s.equals("amarelo"))||(s.equals("sete")))&&(BtnListener.FiltrarCor)){ //Configurar
				
			return Interface.Categoricos[6];
				}
			else if(((s.equals("rosa"))||(s.equals("oito")))&&(BtnListener.FiltrarCor)){ //Configurar
					
			return Interface.Categoricos[7];
			}
			//Inicio Das Formas
			
			else if(((s.equals("ponto"))||(s.equals("um")))&&(BtnListener.FiltrarForma)){ //Configurar
				
				Vis3D.PlotForma(0,Interface.Categoricos[0].getText());
				return Interface.Categoricos[0];
					}
				else if(((s.equals("crus"))||(s.equals("xis"))||(s.equals("dois")))&&(BtnListener.FiltrarForma)){ //Configurar
						
					//Interface.PlotForma(0,Interface.Categoricos[1].getText());
				return Interface.Categoricos[1];
				}else if(((s.equals("triangulo"))||(s.equals("tres")))&&(BtnListener.FiltrarForma)){ //Configurar
					
					//Interface.PlotForma(0,Interface.Categoricos[2].getText());
				return Interface.Categoricos[2];
					}
				else if(((s.equals("quadrado"))||(s.equals("quatro")))&&(BtnListener.FiltrarForma)){ //Configurar
						
					//Interface.PlotForma(0,Interface.Categoricos[3].getText());
				return Interface.Categoricos[3];
				}
			
			//Tamanhos Inicio Aqui
			
				else if(((s.equals("pequeno"))||(s.equals("um")))&&(BtnListener.FiltrarTamanho)){ //Configurar
					
					//Interface.PlotTamanho(0,Interface.Categoricos[0].getText());
					return Interface.Categoricos[0];
						}
					else if(((s.equals("medio"))||(s.equals("dois")))&&(BtnListener.FiltrarTamanho)){ //Configurar
							
						//Interface.PlotTamanho(0,Interface.Categoricos[1].getText());
					return Interface.Categoricos[1];
					}else if(((s.equals("grande"))||(s.equals("três")))&&(BtnListener.FiltrarTamanho)){ //Configurar
						
						//Interface.PlotTamanho(0,Interface.Categoricos[2].getText());
					return Interface.Categoricos[2];
					
					}else if(s.equals("zero")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
						}else{
						return Interface.zero;	
						}
					}if(s.equals("um")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant1;
							}else{
							return Interface.one;	
							}
					}
					else if(s.equals("dois")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant2;
							}else{
							return Interface.two;	
							}
					}
					else if(s.equals("tres") || s.equals("três")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant3;
							}else{
							return Interface.three;	
							}	
					}
					else if(s.equals("quatro")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant4;
							}else{
							return Interface.four;	
							}
					}
					else if(s.equals("cinco")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant5;
							}else{
							return Interface.five;	
							}
					}
					else if(s.equals("seis")||s.equals("meia")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant6;
							}else{
							return Interface.six;	
							}
					}
					else if(s.equals("sete")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant7;
							}else{
							return Interface.seven;	
							}
					}
					else if(s.equals("oito")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							return Interface.Quadrant8;
							}else{
							return Interface.eight;	
							}
					}
					else if(s.equals("nove")&&(Interface.menu_Detalhes.isSelected())){
						if(Vis3D.QtdPontosVisuais>9){
							}else{
							return Interface.nine;	
							}
					}
					
					
					else
			for(int i = 0; i <= Main.dir.GetFileNames().length-1;i++){ // Verifica a gramatica do diretorio 
			if(s.equals(Main.dir.GetFileNames()[i].replaceAll("\\s+",""))){
				return Interface.bases[i];
			}
			
	
} 

		return null;
	
	}
}
