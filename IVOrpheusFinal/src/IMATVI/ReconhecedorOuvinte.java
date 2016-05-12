package IMATVI;

import IVOrpheus2Final.Tradutor;
import javax.speech.recognition.Result;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultToken;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ReconhecedorOuvinte extends ResultAdapter {
	public void resultAccepted(ResultEvent e){
		String Reconhecido = null;
		JComponent btn;
	
	try{
		Result r = (Result) (e.getSource());
		ResultToken tokens[] = r.getBestTokens();
		for(int i = 0; i < tokens.length; i++){
			System.out.print(tokens[i].getSpokenText() + " ");
			Reconhecido = tokens[i].getSpokenText();
		}
		btn = Tradutor.traduzir(Reconhecido);
		((AbstractButton) btn).doClick();
	}
	catch (Exception e1) {
		e1.printStackTrace(); 
	}
	
	}
}
