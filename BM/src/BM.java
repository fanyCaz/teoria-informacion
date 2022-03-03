import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom; 
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;

import javax.swing.JOptionPane.*;
import java.io.*;

public class BM {
    static int NO_OF_CHARS = 256;
    static int max (int a, int b)
    {
    return (a > b)? a: b;
    }
    static void badCharHeuristic( char []str, int size, int badchar[])
    {
    int i;
    for (i = 0; i < NO_OF_CHARS; i++)
    badchar[i] = -1;
    for (i = 0; i < size; i++)
    badchar[(int) str[i]] = i;
    }
    static void search( char[] txt,  char[] pat)
    {
    int m = pat.length;
    int n = txt.length;
    int badchar[] = new int[NO_OF_CHARS];
    //llamada de función
    badCharHeuristic(pat, m, badchar);
    int s = 0;
    while(s <= (n - m))
    {
    int j = m-1;
    while(j >= 0 && pat[j] == txt[s+j])
    j--;
    if (j < 0)
    {
    System.out.println("\nLos patrones ocurren en el = " + s+"\n");
    s += (s+m < n)? m-badchar[txt[s+m]] : 1;
    }
    else
    s += max(1, j - badchar[txt[s+j]]);
    
    }
    }

    public static void main(String args[]) throws IOException
    {
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
        	
        	Scanner tex =new Scanner(System.in);
        	String str = new String();
        	System.out.println("Introduzca los caracteres: ");
        	str= tex.nextLine();
        	char[] txt= str.toCharArray();
        /*	System.out.println("Caracteres: ");
        	for(char c:txt) {
        		System.out.print(c);
        	}*/
        	
        	 // PATRON A BUSCAR
        	Scanner patr= new Scanner(System.in);
        	String strr= new String();
        	System.out.println("\nPatron a buscar: ");
        	strr= patr.nextLine();
        	   char [] pat = strr.toCharArray();
        	//   System.out.println("Patron: ");
        	  /* for(char c:pat) {
        		   System.out.print(c);
        	   }*/
        	   search(txt, pat);
        	  
            break; 
        case 2: 
        
      Scanner patr1= new Scanner(System.in);
      String strr1= new String();
      System.out.println("\nPatron a buscar: ");
      strr1= patr1.nextLine();
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
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
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
            }
        }
        	 search(ArregloFichero, pat2);
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
}
    //Listo