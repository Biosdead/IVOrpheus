package IMATVI;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Attribute {
	public static File file;
	
	public static void SetPath(String FilePath) {
		file = new File(FilePath.replaceAll("\\s+",""));	
	}
	public static String GetFile(){
		return file.getPath().replaceAll("\\s+","");
	}
	public static String[] GetFileAttributes() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
                List<String> list = new ArrayList<String>(); // daqui
		String line = null;
		String[] retorno = null;
		while ((line = reader.readLine()) != null) {
			break;
		}
                retorno = line.split(";");    // Fazer um pré processmento na basse padrão para Extrair qualquer que seja o separador
		if(retorno.length > 1){
                 
            for(String s : retorno) {
                if(s != null && s.length() > 0 && !s.equals("null")) 
                list.add(s); 
            }    
                return retorno;
                }
                
                
                retorno = line.split(","); 
                if(retorno.length > 1){
                 for(String s : retorno) {
                if(s != null && s.length() > 0 && !s.equals("null")) 
                list.add(s); 
            }    
                return retorno;
                }
                                                 //Verificar separado por virgula, por espaço em branco, tabulação, espaço invisivel ...
                retorno = line.split(" ");
                if(retorno.length > 1){
                 for(String s : retorno) {
                if(s != null && s.length() > 0 && !s.equals("null")) 
                list.add(s); 
            }    
                return retorno;
                }
                
                retorno = line.split("  ");
                if(retorno.length > 1){
                 for(String s : retorno) {
                if(s != null && s.length() > 0 && !s.equals("null")) 
                list.add(s); 
            }    
                return retorno;
                }
                
                retorno = line.split("	");
                if(retorno.length > 1){
                 for(String s : retorno) {
                if(s != null && s.length() > 0 && !s.equals("null")) 
                list.add(s); 
            }    
                return retorno;
                }
                
		return retorno;
	}
	public static void PrintAtt() throws IOException{
		System.out.println(Arrays.toString(GetFileAttributes()));
		for(int i = 0; i<=GetFileAttributes().length-1;i++){
			System.out.println(GetFileAttributes()[i]);
		}
	}
        public static void PrintSecondLine() throws IOException{
		for(int i = 0; i<=VectorAttributes().length-1;i++){
			System.out.println(VectorAttributes()[i]);
		}
	}
        
   public static double[][] VectorAttributes() throws FileNotFoundException, IOException{ //Pegar os tipos de atributos e gerar vetores correspondentes
   BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
                String typeLine = "";
                String RestOfFile = "";
                String nameLine = "";
                String[] ThirdReturn = new String[760];
                line = reader.readLine();
                nameLine = line.replace("	", ",");
                typeLine = reader.readLine().replace("	", ",");
               
                
		while ((line = reader.readLine()) != null) {
                    RestOfFile +=reader.readLine()+",";
                }
               RestOfFile = RestOfFile.replace("	",",");
               RestOfFile = RestOfFile.replace(System.getProperty("line.separator"), ",");
               
               
                List<String> attNames = new ArrayList<String>(Arrays.asList(nameLine.split(",")));
                List<String> attTypes = new ArrayList<String>(Arrays.asList(typeLine.split(",")));
                List<String> attValues = new ArrayList<String>(Arrays.asList(RestOfFile.split(",")));
                List<String> Temp = new ArrayList<String>();
                
                double[][] matrix = new double[attTypes.size()][(int)attValues.size()/attTypes.size()+1]; 
     
//                System.out.println("items "+attTypes.size());
//                System.out.println("list "+attValues.size());
//                System.out.println("listValue 1 "+attValues.get(0));

//                Map<Double,Double> map = new HashMap<>();
                
                for (int i = 0; i <= attTypes.size()-1; i++) {
                    Temp.clear();
                    int auxJ = 0; // Como o J tem passos largos de casa no tamanho da qtd de atributos torna-se necessario o AuxJ para preencher as  casa da matrix
//                    System.out.println(attNames.get(i));
//                    System.out.println(attTypes.get(i));
                    for (int j = i; j <= attValues.size()-1;) {
                        if (attTypes.get(i).equals("STRING")) {
//                              System.out.println("Fora do loop "+attValues.get(j));
                            if (Temp.isEmpty()) {
                            matrix[i][auxJ] = 1; 
                            Temp.add(attValues.get(j));    
                            }else{
                              
                                for (int k = 0; k < Temp.size(); k++) {
                                    if (attValues.get(j).replaceAll("\\s+","").equals(Temp.get(k).replaceAll("\\s+",""))){
                                        matrix[i][auxJ] = Temp.indexOf(Temp.get(k))+1; 
//                                        System.out.println(attValues.get(j)+" Primeiro loop "+ Temp.get(k));
                                       
                                    } if (!Temp.contains(attValues.get(j))){ // Não achou na lista
                                        Temp.add(attValues.get(j));
                                        matrix[i][auxJ] = Temp.size();
//                                         System.out.println(attValues.get(j)+" PrimeSec loop "+ Temp.get(k));
                                         System.out.println(" Temp Size "+ Temp.size());
//                                         System.out.println(" Temp 2 "+ Temp.get(1));
                                    break;      
                                    }
                                }
                            }
                        }else{ 
                           matrix[i][auxJ] = Double.parseDouble(attValues.get(j));
                          // attTypes.
                        }
                    System.out.println(matrix[i][auxJ]);
                     // System.out.println(i+" / "+j);
                  
                     j += attTypes.size();
                     auxJ ++;
                    }
                    
                   
		
                    }
                 for (int k = 0; k < matrix[1].length ; k++) {
//                       System.out.println(matrix[4][k]);
                    }
		return matrix;
   }
   
   
   public String[][] VectorBase() throws FileNotFoundException, IOException{
       BufferedReader reader = new BufferedReader(new FileReader(file));
               String line = null;
               String typeLine = "";
               String RestOfFile = "";
               String nameLine = "";
               line = reader.readLine();
               nameLine = line.replace("	", ",");
               typeLine = reader.readLine().replace("	", ",");
              
               
		while ((line = reader.readLine()) != null) {
                   RestOfFile +=reader.readLine()+",";
               }
              RestOfFile = RestOfFile.replace("	",",");
              RestOfFile = RestOfFile.replace(System.getProperty("line.separator"), ",");
              
              
               List<String> attNames = new ArrayList<String>(Arrays.asList(nameLine.split(",")));
               List<String> attTypes = new ArrayList<String>(Arrays.asList(typeLine.split(",")));
               List<String> attValues = new ArrayList<String>(Arrays.asList(RestOfFile.split(",")));
               List<String> Temp = new ArrayList<String>();
               
//               System.out.println(" names " + attNames.size());
//               System.out.println(" types " + attTypes.size());
//               System.out.println(" values " + attValues.size());
//               System.out.println(" temp " + Temp.size());
               
//               System.out.println("aka " + attValues.get(attValues.size()-1));
//                System.out.println("aka " + attValues.get(attValues.size()-2));
//                System.out.println("aka " + attValues.get(attValues.size()-3));
//                System.out.println("aka " + attValues.get(attValues.size()-4));
//                System.out.println("aka " + attValues.get(attValues.size()-5));
//                System.out.println("aka " + attValues.get(attValues.size()-6));
//                System.out.println("aka " + attValues.get(attValues.size()-7));
//                System.out.println("aka " + attValues.get(attValues.size()-8));
//                System.out.println("aka " + attValues.get(attValues.size()-9));
//                System.out.println("aka " + attValues.get(attValues.size()-10));
//                System.out.println("aka " + attValues.get(attValues.size()-11));
//                System.out.println("aka " + attValues.get(attValues.size()-12));
//                System.out.println("aka " + attValues.get(attValues.size()-13));
//                System.out.println("aka " + attValues.get(attValues.size()-14));
//                System.out.println("aka " + attValues.get(attValues.size()-15));
//                System.out.println("aka " + attValues.get(attValues.size()-16));
//                System.out.println("aka " + attValues.get(attValues.size()-17));
                
                  String[][] matrix = new String[attTypes.size()][((int)attValues.size()/attTypes.size())+1]; 
//                    Object[][] XYZ = new Object[attTypes.size()][(int)attValues.size()/attTypes.size()+1];
//                  System.out.println("KKAKDSKDSFLKJBADSJFLBDLJFB"+((int)attValues.size()/attTypes.size()));
               
               for (int i = 0; i <= attTypes.size()-1; i++) {
                   int auxJ = 0; // Como o J tem passos largos de casa no tamanho da qtd de atributos torna-se necessario o AuxJ para preencher as  casa da matrix

                   for (int j = i; j <= attValues.size()-1;) {
                    
                          matrix[i][auxJ] = attValues.get(j);
//                          System.out.println("M "+matrix[i][auxJ] );
                    j += attTypes.size();
                    auxJ ++;
                   }
                   
                  
		
                   }
            
               return matrix;
  }
   
   public String[][] VectorBaseCSV() throws FileNotFoundException, IOException{ // Depois trabalhar aqui para averiguar se está errado a representacao da base
       BufferedReader reader = new BufferedReader(new FileReader(file));
               String line = null;
               String typeLine = "";
               String RestOfFile = "";
               String nameLine = "";
               line = reader.readLine();
               nameLine = reader.readLine();
               line = reader.readLine();
               typeLine = reader.readLine();
               int aka = 0;
               while ((line = reader.readLine()) != null) {
                   RestOfFile +=reader.readLine();
                   aka++;
               }
               System.out.println("aka " + line); 
               line = reader.readLine();
               System.out.println("aka " + line); 
               System.out.println("aka " + aka); 
               List<String> attNames = new ArrayList<String>(Arrays.asList(nameLine.split(";")));
               List<String> attTypes = new ArrayList<String>(Arrays.asList(typeLine.split(";")));
               List<String> attValues = new ArrayList<String>(Arrays.asList(RestOfFile.split(";")));
               List<String> Temp = new ArrayList<String>();
               
//                System.out.println("aka " + attValues.get(attValues.size()-1));
//                System.out.println("aka " + attValues.get(attValues.size()-2));
//                System.out.println("aka " + attValues.get(attValues.size()-3));
//                System.out.println("aka " + attValues.get(attValues.size()-4));
//                System.out.println("aka " + attValues.get(attValues.size()-5));
//                System.out.println("aka " + attValues.get(attValues.size()-6));
//                System.out.println("aka " + attValues.get(attValues.size()-7));
//                System.out.println("aka " + attValues.get(attValues.size()-8));
//                System.out.println("aka " + attValues.get(attValues.size()-9));
//                System.out.println("aka " + attValues.get(attValues.size()-10));
//                System.out.println("aka " + attValues.get(attValues.size()-11));
//                System.out.println("aka " + attValues.get(attValues.size()-12));
//                System.out.println("aka " + attValues.get(attValues.size()-13));
//                System.out.println("aka " + attValues.get(attValues.size()-14));
//                System.out.println("aka " + attValues.get(attValues.size()-15));
//                System.out.println("aka " + attValues.get(attValues.size()-16));
//              System.out.println(" names " + attNames.size());
//               System.out.println(" types " + attTypes.size());
//               System.out.println(" values " + attValues.size());
//               System.out.println(" temp " + Temp.size());
               
                  String[][] matrix = new String[attTypes.size()][((int)attValues.size()/attTypes.size())+1]; 
               
               for (int i = 0; i <= attTypes.size()-1; i++) {
                   int auxJ = 0; // Como o J tem passos largos de casa no tamanho da qtd de atributos torna-se necessario o AuxJ para preencher as  casa da matrix

                   for (int j = i; j <= attValues.size()-1;) {
                    
                          matrix[i][auxJ] = attValues.get(j);
                    j += attTypes.size();
                    auxJ ++;
                   }
                   
                  
		
                   }
            
               return matrix;
  }

   public List<String> AttTypes() throws FileNotFoundException, IOException{
	   BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
               String typeLine = "";
               String RestOfFile = "";
               String nameLine = "";
               String[] ThirdReturn = new String[760];
               line = reader.readLine();
               nameLine = line.replace("	", ",");
               typeLine = reader.readLine().replace("	", ",");
              
               
		while ((line = reader.readLine()) != null) {
                   RestOfFile +=reader.readLine()+",";
               }
              RestOfFile = RestOfFile.replace("	",",");
              RestOfFile = RestOfFile.replace(System.getProperty("line.separator"), ",");
              
              
               List<String> attNames = new ArrayList<String>(Arrays.asList(nameLine.split(",")));
               List<String> attTypes = new ArrayList<String>(Arrays.asList(typeLine.split(",")));
               List<String> attValues = new ArrayList<String>(Arrays.asList(RestOfFile.split(",")));
               List<String> Temp = new ArrayList<String>();
          return attTypes;     
   }
   
   
   public String[] GetUniqueValues(int index) throws FileNotFoundException, IOException{
	   String[] unique = new HashSet<String>(Arrays.asList(VectorBase()[index])).toArray(new String[0]);
           
           List<String> list = new ArrayList<String>(); // daqui 
            for(String s : unique) {
                if(s != null && s.length() > 0 && !s.equals("null")) {
                list.add(s);
                }
            }  
           
            
    unique = list.toArray(new String[list.size()]);  // até aqui remove todos os nulos dos valores unicos
           
           Arrays.sort(unique);
           
	return unique;
   }
   
   public String[] GetUniqueValues(List<String> str) throws FileNotFoundException, IOException{
//	   String[] unique = new HashSet<String>(Arrays.asList(VectorBase()[index])).toArray(new String[0]);
	   String[] unique = str.toArray(new String[str.size()]);
           
           List<String> list = new ArrayList<String>(); // daqui 
            for(String s : unique) {
                if(s != null && s.length() > 0 && !s.equals("null")) {
                list.add(s);
                }
            }  
           
            
    unique = list.toArray(new String[list.size()]);  // até aqui remove todos os nulos dos valores unicos
           
           Arrays.sort(unique);
           
	return unique;
   }
   public Attribute(String FilePath) {
		file = new File(FilePath.replaceAll("\\s+",""));//Tirar todos os espacos brancos da string	 
	}

    public Attribute() {
    	file = new File(System.getProperty("user.home")+"/Documentos/Bases/carros");
        }

   
     public static void main(String[] args) throws IOException {
     Attribute att = new Attribute();    
//    att.VectorAttributes();
//     att.GetFileAttributes();
//     att.PrintSecondLine();
//     att.PrintAtt();
//     att.VectorBase();
         System.out.println(Arrays.toString(att.GetUniqueValues(2)));
     
     
     }
	
}



// TO querendo criar uma lista com todos os nomes divididos OKOKO
//Pegar todos os atributos e dividir em String, FLoats e Intergers.
// Ler uma base e todo caractere especial como paragrafo, espaco , ponto e virgual e etc. Subistituir por virgula.
//Precisa ter uma classe replace para limpar as bases, antes de lê-las.
// Fazer um tratamento nos vetores resultantes dos nomes, tipos e valores para retirar os nulls.
//Pegar os nomes dos eixos dinamicamente
//Terminar o Pre processamento