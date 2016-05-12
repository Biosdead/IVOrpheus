package IMATVI;

import IVOrpheus2Final.Main;
import IVOrpheus2Final.ReconhecedorOuvinte;
import br.ufpa.laps.jlapsapi.recognizer.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.DictationGrammar;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.RecognizerModeDesc;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultListener;
import javax.speech.recognition.Rule;
import javax.speech.recognition.RuleAlternatives;
import javax.speech.recognition.RuleCount;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleName;
import javax.speech.recognition.RuleSequence;
import javax.speech.recognition.RuleTag;
import javax.speech.recognition.RuleToken;
import javax.speech.recognition.GrammarAdapter;

public class Reconhecedor extends ResultAdapter {
	
	public static Recognizer rec , rec2;
	public static RuleGrammar gram, gram2,gramTst,
	gramDCarregar,gramIFC,gramInteragir,gramMG,gramEscala,
	gramFC,gramDAtributos,gramTemp,gramFiltrar,gramFiltrarCategoricos,
	gramFiltrarCores,gramFiltrarFormas,gramFiltrarTamanhos,gramDetalhes;
	public static RuleGrammar[] VGrammar; 
	public static DictationGrammar dic;
	public static RuleToken palavra;
	public static FileReader readerCarregar;
	
	public static void main() {
		try {
			RecognizerModeDesc rmd = (RecognizerModeDesc) Central
				.availableRecognizers(null).firstElement();
			
			rec = Central.createRecognizer(rmd);// cria o reconhecedor
			System.out.println("rec created");
			//Grammar();
			rec.allocate();//carrega os arquivos do coruja
			WriteGrammar(); // So pode escrever a grammar depois do coruja alocado
			//carrega as gramaticas em readers
			FileReader readerCarregar = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/DCarregar.grammar");
			FileReader readerIFC = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/IFC.grammar");
			FileReader readerInteragir = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/Interagir.grammar");
			FileReader readerMG = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/MG.grammar");
			FileReader readerEscala = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/Escala.grammar");
			FileReader readerFC = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/FC.grammar");
			FileReader readerAtributos = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/DAtributos.grammar");
			FileReader readerFiltrar = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/Filtrar.grammar");	
			FileReader readerFiltrarCategoricos = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/FiltrarCategorico.grammar");	
			FileReader readerFiltrarCores = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/FiltrarCores.grammar");
			FileReader readerFiltrarFormas = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/FiltrarFormas.grammar");
			FileReader readerFiltrarTamanhos = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/FiltrarTamanhos.grammar");
			FileReader readerDetalhes = new FileReader(System.getProperty("user.home")+"/workspace/IVOrpheus2/Detalhes.grammar");
			
			// faz a leitura do arquivo de gramática de comando e controle
			gramDCarregar = rec.loadJSGF(readerCarregar);
			gramIFC = rec.loadJSGF(readerIFC);
			gramInteragir = rec.loadJSGF(readerInteragir);
			gramMG = rec.loadJSGF(readerMG);
			gramEscala = rec.loadJSGF(readerEscala);
			gramFC = rec.loadJSGF(readerFC);
			gramDAtributos = rec.loadJSGF(readerAtributos);
			gramFiltrar = rec.loadJSGF(readerFiltrar);
			gramFiltrarCategoricos = rec.loadJSGF(readerFiltrarCategoricos);
			gramFiltrarCores = rec.loadJSGF(readerFiltrarCores);
			gramFiltrarFormas = rec.loadJSGF(readerFiltrarFormas);
			gramFiltrarTamanhos = rec.loadJSGF(readerFiltrarTamanhos);
			gramDetalhes = rec.loadJSGF(readerDetalhes);
			
			
			//Adiciona o listener a cada gramatica para que possa reconhecer os comandos
			gramDCarregar.addResultListener(new ReconhecedorOuvinte());
			gramIFC.addResultListener(new ReconhecedorOuvinte());
			gramInteragir.addResultListener(new ReconhecedorOuvinte());
			gramMG.addResultListener(new ReconhecedorOuvinte());
			gramEscala.addResultListener(new ReconhecedorOuvinte());
			gramFC.addResultListener(new ReconhecedorOuvinte());
			gramDAtributos.addResultListener(new ReconhecedorOuvinte());
			gramFiltrar.addResultListener(new ReconhecedorOuvinte());
			gramFiltrarCategoricos.addResultListener(new ReconhecedorOuvinte());
			gramFiltrarCores.addResultListener(new ReconhecedorOuvinte());
			gramFiltrarFormas.addResultListener(new ReconhecedorOuvinte());
			gramFiltrarTamanhos.addResultListener(new ReconhecedorOuvinte());
			gramDetalhes.addResultListener(new ReconhecedorOuvinte());
			
		//	gram = rec.loadJSGF(reader);
		//	gram2 = rec.loadJSGF(reader2);
			//cria a variável para que os comandos possam ser reconhecidos
			dic = rec.getDictationGrammar("dicSr");
			//cria a variável para ser possível o ditado 
			dic.setEnabled(false);
		//	dic.addWord("bom");
		//nessa aplicação não iremos usar o ditado, por isso ele é pausado
		//	gram.addResultListener(new ReconhecedorOuvinte());
		//	gram2.addResultListener(new ReconhecedorOuvinte());
			//cria o listener para a variável da gramática
		//	gram2.setEnabled(false);
		//	System.out.println(gram.getName());
		//	RuleToken palavra = new RuleToken("dia");
		//	gram.setRule("ruleName", palavra, true);
			//System.out.print(gram.getRuleInternal("um"));
		//	System.out.println(gram.getActivationMode());
		//	System.out.println(gram.getRecognizer());
			//System.out.print(gram.getRule("numeros"));
		//	gram.setEnabled("numeros", false);
			VectorGrammar();
		//	Funciona();
			GramCarregar();
		//	WriteTempGrammar();
			rec.resume();
		//	WriteTempGrammar();
		//	gramDCarregar = rec.loadJSGF(readerCarregar);
			rec.commitChanges();
			//Grammar();
		//	rec.suspend();
		

			
			
			
		} catch (IllegalArgumentException e ) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (EngineStateError e) {
			e.printStackTrace();
		} catch (AudioException e) {
			e.printStackTrace();
		} catch (EngineException e) {
			e.printStackTrace();
		} catch (GrammarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Funciona() throws IOException{
		try {
			rec.requestFocus();
			rec.suspend();
		//FileReader reader = new FileReader(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/Tst.grammar");
		//gram = rec.loadJSGF(reader);
		//gram.addResultListener(new ReconhecedorOuvinte());
		FileReader readerCarregar = new FileReader(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/DCarregar.grammar");
		gramDCarregar = rec.loadJSGF(readerCarregar);	
	//	rec.loadJSGF(new URL (System.getProperty("user.home"))+"/workspace/MyCalcJLAPSAPI/DCarregar.grammar","DCarregar.grammar");
		
		/*	try{
			RuleTag teste1 = new RuleTag();
			teste1 = (RuleTag) gramDCarregar.ruleForJSGF("lua");
			RuleToken teste2 = new RuleToken();
			teste2 = (RuleToken) gramDCarregar.ruleForJSGF("lua");
			RuleName teste3 = new RuleName();
			teste3 = (RuleName) gramDCarregar.ruleForJSGF("lua");
			RuleAlternatives teste4 = new  RuleAlternatives();
			teste4 = (RuleAlternatives) gramDCarregar.ruleForJSGF("lua");
			RuleSequence teste5 = new  RuleSequence();
			teste5 = ( RuleSequence) gramDCarregar.ruleForJSGF("lua");
			 RuleCount teste6 = new  RuleCount();
			teste6 = ( RuleCount) gramDCarregar.ruleForJSGF("lua");
			gramDCarregar.setRule(gramDCarregar.getName(),teste1, true);
			gramDCarregar.setRule(gramDCarregar.getName(),teste2, true);
			gramDCarregar.setRule(gramDCarregar.getName(),teste3, true);
			gramDCarregar.setRule(gramDCarregar.getName(),teste4, true);
			gramDCarregar.setRule(gramDCarregar.getName(),teste5, true);
			gramDCarregar.setRule(gramDCarregar.getName(),teste6, true);
			gramDCarregar.setRule("lua", gramDCarregar.ruleForJSGF("lua"), true);
			
			System.out.println(gramDCarregar.getRule("<Carregar>"));
			System.out.println(gramDCarregar.getRule(" carregar "));
			System.out.println(gramDCarregar.getRule("| carregar |"));
			System.out.println(gramDCarregar.getRule("carregar"));
			System.out.println(gramDCarregar.getRule("Carregar"));
			System.out.println(gramDCarregar.getRule("lua"));
		}catch(Exception e){
				
			}
			*/
		
		//	RuleGrammar gramZero = rec.newRuleGrammar("com.sun.speech.test");

			// Rule we are building
		//	RuleAlternatives test;

			// Temporary rules
		//	RuleCount r1;
		//	RuleTag r2;
		//	RuleSequence seq1, seq2;

			// Create "[a]"
		//	r1 = new RuleCount(new RuleToken("a"), RuleCount.OPTIONAL);

			// Create "test {TAG}" - a tagged token
		//	r2 = new RuleTag(new RuleToken("test"), "TAG");

			// Join "[a]" and "test {TAG}" into a sequence "[a] test {TAG}"
		//	seq1 = new RuleSequence(r1);
		//	seq1.append(r2);

			// Create the sequence "another <rule>";
	//		seq2 = new RuleSequence(new RuleToken("tarde"));
		//	seq2.append(new RuleName("rule"));

			// Build "[a] test {TAG} | another <rule>"
//			test = new RuleAlternatives(seq1);
	//		test.append(seq2);

			// Add <test> to the RuleGrammar as a public rule
		//	gramZero.setRule("test", test, true); //ponteiro nulo

			// Provide the definition of <rule>, a non-public RuleToken
		//	gramZero.setRule("rule", new RuleToken("word"), false);
				
		//RuleToken palavra = new RuleToken("tarde");
		//gramDCarregar.setRule("tarde", palavra, true);
		rec.commitChanges();
		rec.resume();
		
	} catch (IllegalArgumentException e ) {
		e.printStackTrace();
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (EngineStateError e) {
		e.printStackTrace();
	} catch (GrammarException e) {
		e.printStackTrace();
//	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (AudioException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void LoadGrammar(String s) throws EngineException, EngineStateError, GrammarException, IOException, AudioException{
		
		try {
			//	rec.deallocate();
		//	RecognizerModeDesc rmd = (RecognizerModeDesc) Central
		//			.availableRecognizers(null).firstElement();
		//	rec = Central.createRecognizer(rmd);// cria o reconhecedor
		//	System.out.println("rec created");
		//	rec.allocate();//carrega os arquivos do coruja	
		//	rec.deallocate();
			
			//gram.setEnabled(false);
			FileReader reader = new FileReader(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/"+s);
		//	gram2 = rec.loadJSGF(reader);
		//	gram2 = rec.loadJSGF(reader);
			rec.loadJSGF(reader);
			
			//	gram.setEnabled(false);
		//	gram2.setEnabled(true);
			
		//	dic = rec.getDictationGrammar("dicSr");
			//cria a variável para ser possível o ditado 
	//		dic.setEnabled(false);
		//nessa aplicação não iremos usar o ditado, por isso ele é pausado
	//		gram.addResultListener(new ReconhecedorOuvinte());
			gram2.addResultListener(new ReconhecedorOuvinte());
		//	gram2 = rec.newRuleGrammar(("user.home")+"/workspace/MyCalcJLAPSAPI/Calc.grammar");
		//	gram2.addResultListener(new ReconhecedorOuvinte());
			
			//System.out.println(gram2.getRule("numeros"));
			
			rec.commitChanges();
			
		
			

			// Create a rule for the word blue
			// Add the rule to the RuleGrammar and make it public
			
			
			//System.out.println(gram2.getRule("action"));
			
			
			// 	palavra = new RuleToken("novo");
			// 	RuleName p = new RuleName("dia");
			// 	gram.addImport(p);
			 	//gram.setRule("PalavraNovo", palavra, true);
				
				//gramTst = rec.newRuleGrammar("Teste");
			//	rec.commitChanges();
				//System.out.print(gram.listImports());
			// Create a new grammar
			// gramTst = rec.newRuleGrammar("Sim");

			// Create the <test> rule
		//	Rule teste = gramTst.ruleForJSGF("[a] test {TAG} | another <rule>");
		//	gramTst.setRule("teste", // rulename
			//		teste, // rule definition
			//		true); // true -> make it public

			// Create the <rule> rule
		//	gramTst.setRule("rule", gramTst.ruleForJSGF("word"), false);

			// Commit the grammar
		
			
			
			
			//inicia o reconhecedor
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e ) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (EngineStateError e) {
			e.printStackTrace();
//		} catch (AudioException e) {
//			e.printStackTrace();
//		} catch (EngineException e) {
//			e.printStackTrace();
		} catch (GrammarException e) {
			e.printStackTrace();
		}
		
		
		//try{
			
		rec.commitChanges();
		rec.suspend();
		rec.resume();
		System.out.print(gram2.getName());
		System.out.print(gram2.getRuleInternal("somar"));
			
		
	//	gram.setRule("hoje", gram.ruleForJSGF("dia"), true);
	//	Rule test = gram.ruleForJSGF("bom | dia");
		
//		gram.setRule("test", // rulename
	//			test, // rule definition
	//			true); // true -> make it public
		
	//	rec.commitChanges();	
	//	System.out.println(gram.getRule("test"));
		
			
	//	FileReader reader = new FileReader(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/"+s);;
	//	gram.setEnabled(false);
		//rec.deallocate();
	//	gram2 = rec.loadJSGF(reader);
	
//		gram2.addResultListener(new ReconhecedorOuvinte());
//		gram2.setEnabled(true);
	//	System.out.print("OKOKOKOKOKOK");
		//gram.setEnabled(true);
	//	rec.resume();
	//	rec.commitChanges(); // Para alterar a gramatica
//	rec.requestFocus();
//		} catch(Exception e) {
		//	e.printStackTrace();
	//	}
		
	}
	
	public static void Grammar() throws GrammarException, EngineStateError, IOException, AudioException, EngineException, SecurityException {
		try{
			RecognizerModeDesc rmd = (RecognizerModeDesc) Central
					.availableRecognizers(null).lastElement();
			rec2 = Central.createRecognizer(rmd);// cria o reconhecedor
			//rec.pause();
			//rec.suspend();
			//rec.deallocate();
			//rec.
			System.out.println("rec2 created");
			rec2.allocate();
			FileReader reader = new FileReader(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/Calc.grammar");
			gramTst = rec2.loadJSGF(reader);
			rec2.resume();
		//	rec.commitChanges();
			rec2.commitChanges();
		} catch (IllegalArgumentException e ) {
			e.printStackTrace();
	//	}catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void AddGrammar(String s) throws GrammarException, EngineStateError, AudioException, EngineException{
		try{
	//	Recognizer rec;
	//	RecognizerModeDesc rmd = (RecognizerModeDesc) Central
		//		.availableRecognizers(null).firstElement();
			
		//	rec = Central.createRecognizer(rmd);
		//	rec.allocate();
		
		// Create a rule for the word blue
		// Add the rule to the RuleGrammar and make it public
		RuleToken palavra = new RuleToken(s);
		gram.setRule("ruleName", palavra, true);

		// Change the word
		palavra.setText("verde");
	
			rec.commitChanges();
			System.out.println(gram.getRule("ruleName"));
			
	//	rec.resume();
		// getRule returns blue (not green)
		
		} catch (IllegalArgumentException e ) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (EngineStateError e) {
			e.printStackTrace();
		} catch (GrammarException e) {
			e.printStackTrace();
		}
		}
	
	public static void CreateGrammar() throws GrammarException{
		try{
		// Create a new grammar
		RuleGrammar gramZero = rec.newRuleGrammar("com.sun.speech.test");

		// Create the <test> rule
		Rule test = gramZero.ruleForJSGF("[a] test {TAG} | another <rule>");
		gramZero.setRule("test", // rulename
				test, // rule definition
				true); // true -> make it public

		// Create the <rule> rule
		gramZero.setRule("rule", gramZero.ruleForJSGF("word"), false);

		// Commit the grammar
		rec.commitChanges();
		System.out.print(gramZero.getRule("test"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
/*
try{
Reconhecedor.gram.setRule("test", Reconhecedor.gram.ruleForJSGF("one two three"),true);
Reconhecedor.gram.setRule("test2",Reconhecedor.gram.ruleForJSGF("one two (four | forty) five"),true);
Reconhecedor.gram.setRule("test3",Reconhecedor.gram.ruleForJSGF("one two three four"),true);
Reconhecedor.gram.setRule("test4",Reconhecedor.gram.ruleForJSGF("(one two three four | one two two four)"),true);
Reconhecedor.gram.setEnabled(true);
String[] rules = Reconhecedor.gram.listRuleNames();
for(int i=0;i<rules.length; i++) {
System.out.println("rule "+rules[i]+" = "+Reconhecedor.gram.getRule(rules[i]));
}
} catch(Exception e) {
	e.printStackTrace();
	}
	*/	
	}
	
	public static void CreateComplexGrammar(){
		try{
		RuleGrammar gramZero = rec.newRuleGrammar("com.sun.speech.test");

		// Rule we are building
		RuleAlternatives test;

		// Temporary rules
		RuleCount r1;
		RuleTag r2;
		RuleSequence seq1, seq2;

		// Create "[a]"
		r1 = new RuleCount(new RuleToken("a"), RuleCount.OPTIONAL);

		// Create "test {TAG}" - a tagged token
		r2 = new RuleTag(new RuleToken("test"), "TAG");

		// Join "[a]" and "test {TAG}" into a sequence "[a] test {TAG}"
		seq1 = new RuleSequence(r1);
		seq1.append(r2);

		// Create the sequence "another <rule>";
		seq2 = new RuleSequence(new RuleToken("another"));
		seq2.append(new RuleName("rule"));

		// Build "[a] test {TAG} | another <rule>"
		test = new RuleAlternatives(seq1);
		test.append(seq2);

		// Add <test> to the RuleGrammar as a public rule
		gramZero.setRule("test", test, true); //ponteiro nulo

		// Provide the definition of <rule>, a non-public RuleToken
		gramZero.setRule("rule", new RuleToken("word"), false);

		// Commit the grammar changes
	
			rec.commitChanges();
			System.out.print(gramZero.getRule("test"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
			
	public static void DynamicGrammar() throws GrammarException, EngineStateError{
		//Recognizer rec;
		//RuleGrammar gram = null;
		try{

		String names[] = {"amy", "alan", "paul"};
		Rule userRule = new RuleAlternatives(names);

		gram.setRule("comandos", userRule, true);

		// apply the changes
		rec.commitChanges();
		System.out.print(gram.getRule("userRule"));
		}  catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void TestGrammar(){
		 
		try {
			 gram.setRule("test", gram.ruleForJSGF("one two three"),true);
			 gram.setRule("test2",gram.ruleForJSGF("one two (four | forty) five"),true);
			 gram.setRule("test3",gram.ruleForJSGF("one two three four"),true);
			 gram.setRule("test4",gram.ruleForJSGF("(one two three four | one two two four)"),true);
			 gram.setEnabled(true);
			 String[] rules = gram.listRuleNames();
			 for(int i=0;i<rules.length; i++) {
			 System.out.println("rule "+rules[i]+" = "+gram.getRule(rules[i]));
			 }
			 }catch (IllegalArgumentException e ) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (EngineStateError e) {
			e.printStackTrace();
		} catch (GrammarException e) {
			e.printStackTrace();
		}
		
	}

	public static void SetVectorGrammar(RuleGrammar gram) throws GrammarException, EngineStateError, IOException{
		VGrammar[12] = gram;
		rec.commitChanges();
	}
	
	public static void VectorGrammar() throws GrammarException, EngineStateError, IOException{
		VGrammar = new RuleGrammar[13];
		VGrammar[0] = gramDCarregar;
		VGrammar[1] = gramIFC;
		VGrammar[2] = gramInteragir;
		VGrammar[3] = gramMG;
		VGrammar[4] = gramEscala;
		VGrammar[5] = gramFC;
		VGrammar[6] = gramDAtributos;
		VGrammar[7] = gramFiltrar;
		VGrammar[8] = gramFiltrarCategoricos;
		VGrammar[9] = gramFiltrarCores;
		VGrammar[10] = gramFiltrarFormas;
		VGrammar[11] = gramFiltrarTamanhos;
		VGrammar[12] = gramDetalhes;
		rec.commitChanges();
	}
	
	public static void GramCarregar() throws GrammarException, EngineStateError, IOException{
		Funciona();
		VGrammar[0].setEnabled(true);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
	/*	gramDCarregar.setEnabled(true);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(false);
		*/
		rec.commitChanges();
	}
	public static void GramIFC() throws GrammarException, EngineStateError{
		
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(true);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
	/*	gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(true);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(false);
		*/
		rec.commitChanges();
	}
	public static void GramInteragir() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(true);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(true);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(false);
		*/
		rec.commitChanges();
	}
	public static void GramMG() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(true);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(true);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(false);
		*/
		rec.commitChanges();
	}
	public static void GramEscala() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(true);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(true);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(false);
		*/
		rec.commitChanges();
	}
	public static void GramFC() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(true);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(true);
		gramDAtributos.setEnabled(false);
		*/
		rec.commitChanges();
	}
	public static void GramAtributos() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(true);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(true);
		*/
		rec.commitChanges();
	}
	public static void GramFiltrar() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(true);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(true);
		*/
		rec.commitChanges();
	}
	public static void GramFiltrarCategoricos() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(true);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		/*
		gramDCarregar.setEnabled(false);
		gramIFC.setEnabled(false);
		gramInteragir.setEnabled(false);
		gramMG.setEnabled(false);
		gramEscala.setEnabled(false);
		gramFC.setEnabled(false);
		gramDAtributos.setEnabled(true);
		*/
		rec.commitChanges();
	}
	
	public static void GramFiltrarCores() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(true);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		
		rec.commitChanges();
	}
	public static void GramFiltrarFormas() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(true);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(false);
		
		
		rec.commitChanges();
	}
	public static void GramFiltrarTamanhos() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(true);
		VGrammar[12].setEnabled(false);
		
		
		rec.commitChanges();
	}
	public static void GramDetalhes() throws GrammarException, EngineStateError{
		VGrammar[0].setEnabled(false);
		VGrammar[1].setEnabled(false);
		VGrammar[2].setEnabled(false);
		VGrammar[3].setEnabled(false);
		VGrammar[4].setEnabled(false);
		VGrammar[5].setEnabled(false);
		VGrammar[6].setEnabled(false);
		VGrammar[7].setEnabled(false);
		VGrammar[8].setEnabled(false);
		VGrammar[9].setEnabled(false);
		VGrammar[10].setEnabled(false);
		VGrammar[11].setEnabled(false);
		VGrammar[12].setEnabled(true);
		
		
		rec.commitChanges();
	}	
	public static void WriteGrammar() throws IOException{ // Escreve a gramatica Carregar Dinamicamente
		CleanGrammar();
		String[] s = new String[Main.dir.GetFileNames().length]; //para ler os nomes dos arquivos
		
		for(int i = 0; i < Main.dir.GetFileNames().length;i++){
			s[i] = Main.dir.GetFileNames()[i];
		}
		
	//	s[0] = "lua";
	//	s[1] = "sol";
	//	s[2] = "estrela";
	//	st.replaceAll("\\s+","")
		FileWriter fw = new FileWriter(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/DCarregar.grammar",true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(System.getProperty("line.separator")
			//	+ "public <"+s[0].replaceAll("\\s+","")+"> = "+s[0].replaceAll("\\s+","")+" | "); //Inicio da grammar
				+ "public <bases> = "+s[0].replaceAll("\\s+","")+" | "); //Inicio da grammar
		for(int j=1;j<=Main.dir.GetFileNames().length-2;j++){
			bw.write(s[j].replaceAll("\\s+","")+" | ");//Meio
		}
		bw.write(s[s.length-1].replaceAll("\\s+","")+";");//Fim
		bw.flush();
		bw.close();
		//public <BaseDinamica> = carros | base | ataques | cabeçalho;
		
	}
	
	public static void CleanGrammar() throws IOException{ //Escreve a gramatica original
		FileWriter fw = new FileWriter(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/DCarregar.grammar");
		//FileWriter fw = new FileWriter(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/DCarregar.grammar");
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("#JSGF V1.0;" + System.getProperty("line.separator") + 
				"grammar DCarregar;"+ System.getProperty("line.separator") +
				"public <global> = ajuda | carregar | legenda | voltar;"+ System.getProperty("line.separator") +
				"public <local> = confirmar | cancelar;");
		bw.flush();
		bw.close();
		
		
	//	#JSGF V1.0;
	//	grammar DCarregar;

	//	# Nomeie categorias de palavras sem por acentos
	//	public <global> = ajuda | carregar | legenda | voltar;
	//	public <local> = carros | base | ataques | cabeçalho;
	}

	public static void WriteTempGrammar() throws IOException, GrammarException, EngineStateError{ // Criar arquivo temporario com gramatica Falta Fazer
		
	/*	try{
			 
	    	   //create a temp file
	    	   File temp = File.createTempFile("temp-file-name", ".tmp"); 
	 
	    	   System.out.println("Temp file : " + temp.getAbsolutePath());
	 
	    	}catch(IOException e){
	    		 
	     	   e.printStackTrace();
	  
	     	} */
		CleanTempGrammar();
		
		String[] s = new String[16];
		s[0] = "Marca";
		s[1] = "Combustivel";
		s[2] = "Turbo";
		s[3] = "Nportas";
		s[4] = "Comprimento";
		s[5] = "Tipo";
		s[6] = "Tracao";
		s[7] = "PosMotor";
		s[8] = "Largura";
		s[9] = "Altura";
		s[10] = "Peso";
		s[11] = "NCilindros";
		s[12] = "Potencia";
		s[13] = "RPM";
		s[14] = "Valor";
		s[15] = "Ano";
		
		//String[] s = new String[Main.att.GetFileAttributes().length]; //para ler os nomes dos arquivos
		
	//	for(int i = 0; i < Main.att.GetFileAttributes().length;i++){
	//		s[i] = Main.att.GetFileAttributes()[i];
	//	}
	
		FileWriter fw = new FileWriter(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/AtributosTemp.grammar",true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		//bw.write("public <"+s[0].replaceAll("\\s+","").toLowerCase()+"> = "+s[0].replaceAll("\\s+","").toLowerCase()+" | "); //Inicio da grammar
		bw.write("public <atributos> = "+s[0].replaceAll("\\s+","").toLowerCase()+" | "); //Inicio da grammar
		for(int j=1;j<=Main.att.GetFileAttributes().length-2;j++){
			bw.write(Main.att.GetFileAttributes()[j].replaceAll("\\s+","").toLowerCase()+" | ");//Meio
		}
		bw.write(Main.att.GetFileAttributes()[Main.att.GetFileAttributes().length-1].replaceAll("\\s+","").toLowerCase()+";");//Fim
		bw.flush();
		bw.close();
		FileReader readerTemp = new FileReader(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/AtributosTemp.grammar");
		gramTemp = rec.loadJSGF(readerTemp);
		SetVectorGrammar(gramTemp);
		//public <BaseDinamica> = carros | base | ataques | cabeçalho;
		
	}
	
	public static void CleanTempGrammar() throws IOException{ //Escreve a gramatica original
		FileWriter fw = new FileWriter(System.getProperty("user.home")+"/workspace/MyCalcJLAPSAPI/AtributosTemp.grammar");
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("#JSGF V1.0;" + System.getProperty("line.separator") + 
				"grammar AtributosTemp;"+ System.getProperty("line.separator") +
				"public <global> = ajuda | carregar | legenda | voltar;"+ System.getProperty("line.separator"));// +
				//"public <local> = confirmar | cancelar;");
		bw.flush();
		bw.close();
	}
}

