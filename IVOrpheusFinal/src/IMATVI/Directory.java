package IMATVI;

import java.io.File;
import java.util.Arrays;

public class Directory {  // Classe para ter acesso ao diretorio de bases e retornar os nomes das bases.
	public static File folder; 

public Directory(){
	folder = new File(System.getProperty("user.home")+"/Documents/Bases");	
//	folder = new File(System.getProperty("user.home")+"/Documentos/Bases");
}
public static void Directory(String path){
	folder = new File(System.getProperty("user.home")+path);	
} 

public static String GetDirectory(){
	return folder.getPath();
}
public static String[] GetFileNames(){
	File[] files = folder.listFiles();
	String[] s = new String[files.length];
	for(int i = 0; i <=files.length - 1; i++){
		if (files[i].isFile()) {
	        s[i] = System.getProperty("line.separator") + files[i].getName();
	      //  return s[i];
		} 
		
		//else if (files[i].isDirectory()) {
	      //  System.out.println("Directory " + listOfFiles[i].getName());
	     // }
	}
//	return Arrays.toString(s);
	return s;
}
    public static void main(String[] args) {
        Directory dir = new Directory();
        System.out.println(dir.GetDirectory());
        System.out.println(Arrays.toString(dir.GetFileNames()));
        
    }
}
