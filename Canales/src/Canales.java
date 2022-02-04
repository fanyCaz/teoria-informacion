import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Canales {
	Scanner sc;
	int iteracion;
	
	public static void main(String[] args) {
		Canales obj = new Canales();
	}
	
	Canales(){
		this.iteracion = 0;
		this.sc = new Scanner(System.in);
		this.printMenu();
	}
	
	// Recopilar Datos
	void printMenu() {
		System.out.println("------------------------------------------");
		System.out.println("---------------MENU CANALES---------------");
		System.out.println("------------------------------------------");
		System.out.println("Seleccione la opcion de ingresar datos");
		System.out.println("");
		System.out.println("1.- Por archivo");
		System.out.println("2.- Manualmente");
		System.out.println("3.- Aleatoriamente");
		System.out.println("");
		
		String aux = this.sc.nextLine();
		int option = this.getValefrom(aux);
		if (option == 1) {
			this.cargarDatosDeArchivo();
		}else if(option == 2) {
			this.solicitarDatosAlUsuario();
		}else if(option == 3) {
			this.cargarDatosRandom();
		}else {
			System.out.println("--------- Ingrese un valor valido --------");
			this.clearConsole();
			this.printMenu();
		}
	}
	
	// ALGORITMO
	public void canales(String[] conjunto) {
		ArrayList<String> resultado = new ArrayList<String>();
		int pos = 0;
		do {
			pos = pos+1;
			for(int i=0;i<conjunto.length;i++) {
				String item = conjunto[i];
				if(item.length() == pos) {
					resultado.add(item);
				}else {
					for(int j=0;j<resultado.size(); j++) {
						String first = item.concat(resultado.get(j));
						String second = resultado.get(j).concat(item);
						if ((first.length() == pos || first.length() == this.iteracion) && !resultado.contains(first)) {
							resultado.add(first);
						}
						if ((second.length() == pos  || first.length() == this.iteracion) && !resultado.contains(second)) {
							resultado.add(second);
						}
					}
				}
			}
			resultado = this.limpiarArreglo(resultado, pos);
		}while(pos < this.iteracion);
		this.imprimirRonda(resultado, pos);
		System.exit(0);
	}

	public ArrayList<String> limpiarArreglo(ArrayList<String> array, int pos) {
		ArrayList<String> nuevo = new ArrayList<String>();
		for(int i=0;i<array.size();i++) {
			if(array.get(i).length() == pos || array.get(i).length() == this.iteracion) {
				nuevo.add(array.get(i));
			}
		}
		return nuevo;
	}
	
	// CARGAR DATOS
	public void cargarDatosDeArchivo() {
		List<String> datos = this.getInfo();
		if (this.iteracion > 0) {
			if(datos.size() == 1) {
				String[] conjunto= datos.get(0).split(",");
				if(this.isAllDiferent(conjunto)) {
					this.canales(conjunto);
				}else {
					System.out.println("");
					System.out.println("No se pueden repetir elementos en el conjunto");
					System.out.println("");
					System.exit(0);
				}
			}else {
				System.out.println("");
				System.out.println("la primer linea debe tener un conjunto de caracteres separados por comas");
				System.out.println("");
				System.exit(0);
			}
		}else {
			System.out.println("");
			System.out.println("Debes agregar una segunda linea con la cantidad de iteraciones en un numero entero positivo");
			System.out.println("");
			System.exit(0);
		}
		
	}
	
	public void solicitarDatosAlUsuario() {
		int size = 0;
		do{
			System.out.println("Ingrese la cantidad de elementos del conjunto: ");
			String line = this.sc.nextLine();
			size = this.getValefrom(line);
			if (size == -1) {
				System.out.println("--------- Ingrese un valor entero positivo --------");
			}
		}while(size <= 0);
		ArrayList<String> conjunto = new ArrayList<String>();
		System.out.println("");
		for (int i= 0;i<size;i++) {
			String item = "";
			do {
				System.out.println("Ingrese el elemento " + i + ": ");
				String line = this.sc.nextLine();
				if (line.length() > 0) {
					if(conjunto.contains(line)) {
						System.out.println("--------- Ingrese un caracter o cadena que no haya sido incluido anteriormente --------");
					}else {
						item = line;
					}
				}else {
					System.out.println("--------- Ingrese un caracter o cadena--------");
				}
			}while(item.length() <= 0);
			conjunto.add(item);
		}
		do {
			System.out.println("Ingrese el numero de iteracion (n): ");
			String line = this.sc.nextLine();
			int aux = this.getValefrom(line);
			if (aux == -1) {
				System.out.println("--------- Ingrese un valor entero positivo --------");
			}else if(aux == 0) {
				System.out.println("--------- Ingrese un valor mayor a cero--------");
			}else {
				this.iteracion = aux;
			}
		}while(this.iteracion == 0);
		this.canales(this.castArray(conjunto));
	}
	
	public void cargarDatosRandom() {
		int size = 0;
		do{
			System.out.println("Ingrese la cantidad de elementos del conjunto: ");
			String line = this.sc.nextLine();
			size = this.getValefrom(line);
			if (size == -1) {
				System.out.println("--------- Ingrese un valor entero positivo --------");
			}
		}while(size <= 0);
		do {
			System.out.println("Ingrese el numero de iteracion (n): ");
			String line = this.sc.nextLine();
			int aux = this.getValefrom(line);
			if (aux == -1) {
				System.out.println("--------- Ingrese un valor entero positivo --------");
			}else if(aux == 0) {
				System.out.println("--------- Ingrese un valor mayor a cero--------");
			}else {
				this.iteracion = aux;
			}
		}while(this.iteracion == 0);
		
		String[] conjunto = new String[size];
		for(int i=0;i<size;i++) {
			int cant = this.getRandomNumber(1, this.iteracion);
			conjunto[i] = this.getAlphaNumericString(cant);
		}
		this.imprimirConjunto(conjunto);
		this.canales(conjunto);
	}
	
	// RESULTADOS
	public void imprimirRonda(ArrayList<String> conjunto, int iteracion) {
		System.out.println("--------------------------------------");
		System.out.println("Iteracion: " + iteracion + ", numero de elementos: " + conjunto.size());
		for(int i=0; i<conjunto.size(); i++) {
			System.out.print(conjunto.get(i));
			if (i<(conjunto.size() - 1)) {
				System.out.print(" - ");
			}
		}
		System.out.println("");
	}
	
	public void imprimirConjunto(String[] conjunto) {
		System.out.println("--------------------------------------");
		System.out.println("Conjunto");
		for(int i=0; i<conjunto.length ; i++) {
			System.out.print(conjunto[i]);
			if (i<(conjunto.length - 1)) {
				System.out.print(" - ");
			}
		}
		System.out.println("");
	}

	// VALIDACIONES
	public boolean isAllDiferent(String[] array) {
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array.length;j++) {
				if (i != j && array[i].equals(array[j])) {
					return false;
				}
			}
		}
		return true;
	}
	
	// METODOS AUXILIARES
	public int getValefrom(String text) {
		String aux = text.replaceAll("[^\\d./]", "");
		if (aux.length() > 0) {
			int value = Integer.valueOf(aux);
			return value;
		}else{
			System.out.println("");
			System.out.println("Favor de elegir un numero entero valido");
			System.out.println("");
		}
		return -1;
	}
	
	public String[] castArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public void clearConsole(){
		try{
			Runtime.getRuntime().exec("cls");
	    }catch(Exception e){
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}
	}
	
	public List<String> getInfo(){
		List<String> info = new ArrayList<>();
		try {
			File myObj = new File("src/canales.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.length() > 0){
					if (info.size() < 1) {
						info.add(data);
					} else if (this.iteracion == 0){
						this.iteracion = this.getValefrom(data);
					}
				}
			}myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred." + e.getMessage());
		}
		return info;
	}
	
    private String getAlphaNumericString(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
	
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
