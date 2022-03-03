import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;


public class KMP {

    public static void main(String[] args) {
    	Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion;
        while(!salir) {
        	System.out.println("1. Manual");
            System.out.println("2. Desde un archivo");
            System.out.println("3. Salir");
        try {
        	System.out.println("Escribe una de las opciones");
            opcion = sn.nextInt();
           switch(opcion) {
           case 1:
        	   Scanner text= new Scanner(System.in);
           		String str= new String();
           		System.out.println("Introduzca los caracteres: ");
           		str=text.nextLine();
           		Scanner pat= new Scanner(System.in);
           		String strr=new String();
           		System.out.println("Introduzca el patron a buscar: ");
           		String pattern=pat.nextLine();
           		KMP obj = new KMP();
           		System.out.print(obj.patternExistKMP(str.toCharArray(), pattern.toCharArray()));
           break;
           case 2:
        	   Scanner patr= new Scanner(System.in);
        	      String strr1= new String();
        	      System.out.println("\nPatron a buscar: ");
        	      strr1= patr.nextLine();
        	      char [] pat2 = strr1.toCharArray();  
        	      char[] ArregloFichero= new char[1000];
        	      String Guardarchar="";
        	      String nombreFichero = "prueba1.txt";
        	        //Declarar una variable FileReader
        	        FileReader fr = null;
        	        try {
        	            fr = new FileReader("prueba1.txt");
        	            int caract = fr.read();
        	            while(caract != -1) {
        	                Guardarchar=Guardarchar+(char)caract;
        	                caract = fr.read();
        	            }
        	            ArregloFichero = Guardarchar.toCharArray();
        	        }
        	        catch (FileNotFoundException e) {
        	            //Operaciones en caso de no encontrar el fichero
        	            System.out.println("Error: Fichero no encontrado");
        	            //Mostrar el error producido por la excepción
        	            System.out.println(e.getMessage());
        	            break;
        	            
        	        }
        	        catch (Exception e) {
        	            //Operaciones en caso de error general
        	            System.out.println("Error de lectura del fichero");
        	            System.out.println(e.getMessage());
        	            break;
        	        }
        	        finally {
        	            //Operaciones que se harán en cualquier caso. Si hay error o no.
        	            try {
        	                //Cerrar el fichero si se ha abierto
        	                if(fr != null)
        	                    fr.close();
        	            }
        	            catch (Exception e) {
        	                System.out.println("Error al cerrar el fichero");
        	                System.out.println(e.getMessage());
        	                break;
        	            }
        	        }
        	        patternExistKMP(ArregloFichero, pat2);
        	   break;
           case 3: 
           	salir = true;
           	break;
           default: 
         	  System.out.println("Solo números entre 1 y 3");
             }   
     	}catch (InputMismatchException e) {
             System.out.println("Debes insertar un número");
             sn.next();
           }    
        } 
    }
    
    public static int[] computeLPS(char[] str){
        int lps[] = new int[str.length];
        
        lps[0] = 0;
        int j = 0;
        for(int i =1;i<str.length;i++){
            if(str[j] == str[i]){
                lps[i] = j+1;
                j++;
                i++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    lps[i] = j+1;
                    i++;
                }
            }
            
        }
        
        return lps;
    }
    
    public static boolean patternExistKMP(char[] text,char[] pat){
        int[] lps = computeLPS(pat);
        int i=0,j=0;
        while(i<text.length && j<pat.length){
            if(text[i] == pat[j]){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
        }
        
        if(j==pat.length) {
        	System.out.println("Si se encontro el patron");
           // return true;
        }else {
        	System.out.println("No se encontro el patron");
       // return false;
    
    }
		return false;
		
}
}