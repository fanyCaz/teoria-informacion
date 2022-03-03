

	package asslz77;
	import java.io.*;
	import java.io.BufferedReader;
	import java.io.DataInputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.util.StringTokenizer;
	import javax.swing.JOptionPane;
	  
	public class LZ77 {
	 
	   public static void main(String args[]) throws FileNotFoundException, IOException
	    {
	 
	        String input=null;
	        try
	        {
	        FileInputStream file=new FileInputStream("input.txt");
	        DataInputStream data=new DataInputStream(file);
	        BufferedReader br=new BufferedReader(new InputStreamReader(data));
	 
	        input=br.readLine();
	        System.out.println(input);
	 
	        FileOutputStream f1=new FileOutputStream("compress.txt");
	        DataOutputStream d1=new DataOutputStream(f1);
	 
	        int pointer=0,len1=0;
	        char o='0',nc='0';
	        String w,y=null,x=null;
	        int i,j=0,z,l=0;
	 
	        for(i=0;i<=input.length();i++)
	        {
	            w=null;
	            w=input.substring(0,i);
	            j=i+1;
	           do{
	               y=input.substring(i,j);
	               z=w.lastIndexOf(y);
	               if(z!=-1)
	               {
	                   l=z;
	                    j++;
	 
	                    if(j>input.length())
	                    {
	                        i=input.length();//ye break mn elloop elkbera (i)
	                        pointer=w.length()-l;
	                        len1=y.length();// length of characters copied
	                        nc='0';
	                        d1.writeInt(pointer);
	                        d1.writeInt(len1);
	                        d1.writeChar(nc);
	                        break;
	                    }
	               }
	               else
	               {
	                   i=w.length()+(y.length()-1);
	                   if(y.length()==1)
	                   {
	                       pointer=0;
	                       len1=0;
	                   }
	                   else
	                   {
	                       pointer=w.length()-l;
	                       len1=y.length()-1;// length of characters copied
	 
	                   }
	                   nc=y.charAt(y.length()-1);//next character
	                   d1.writeInt(pointer);
	                   d1.writeInt(len1);
	                   d1.writeChar(nc);
	                   break;
	               }
	              }while(z!=-1);
	        }
	        d1.close();
	        data.close();
	        }
	        catch (Exception ex)
	        {}
	 
	        FileInputStream f2=new FileInputStream("compress.txt");
	        DataInputStream d2=new DataInputStream(f2);
	        BufferedReader b=new BufferedReader(new InputStreamReader(d2));
	 
	        FileOutputStream fos=new FileOutputStream("decompress.txt");
	        DataOutputStream dos=new DataOutputStream(fos);
	 
	        int p=0,len=0;
	        char n;
	        String decomp = "";
	        while(f2.available()!=0)
	        {
	            p=d2.readInt();//pointer
	            System.out.println(p);
	            len=d2.readInt();//length
	            System.out.println(len);
	            n=d2.readChar();//nextcharacter
	            System.out.println(n);
	            if(p==0)
	                decomp+=n;
	            else
	            {
	                if(n!='0')
	                decomp+=(decomp.substring((decomp.length()-p),((decomp.length()-p)+len))+n);
	                else
	                    decomp+=(decomp.substring((decomp.length()-p),((decomp.length()-p)+len)));
	            }
	        }
	        dos.writeChars(decomp);
	        System.out.println(decomp);
	 
	        b.close();
	        dos.close();
	 
	    }
	 
	 
	 
	}

