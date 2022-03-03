import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransicionProbabilidades {
	Scanner sc;
	int type;
	
	public static void main(String[] args) {
		TransicionProbabilidades obj = new TransicionProbabilidades();
	}
	
	TransicionProbabilidades(){
		this.sc = new Scanner(System.in);
		this.printMenu();
	}
	
	// CARGAR DATOS
	public void cargarDatosDeArchivo() {
		List<String> datos = this.getInfo();
		if(this.type >=1 && this.type <=3 ) {
			if (datos.size() == 3) {
				String[] conjunto = datos.get(0).split(",");
				Double[] frecuencias = this.getFrecuencias(datos.get(1).split(","));
				Double[] probabilidades = this.getProbabilities(datos.get(2).split(","));
				if(conjunto.length == probabilidades.length && conjunto.length == frecuencias.length) {
					this.transicionDeProbabilidades(conjunto, frecuencias, probabilidades);
				}else {
					System.out.println("");
					System.out.println("Debe tener la misma cantidad de elementos el conjunto, frecuencias y las probabilidades");
					System.out.println("");
					System.exit(0);
				}
			}else{
				System.out.println("");
				System.out.println("Debes agregar en la primera linea del archivo el conjunto de datos y en el segundo las probabilidades, y en el tercero las probabilidades de fallo");
				System.out.println("");
				System.exit(0);
			}
		}else {
			System.out.println("");
			System.out.println("Debes agregar el tipo de datos la 4ta linea");
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
				System.out.println("Ingrese el caracter del elemento " + i + ": ");
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
		// FRECUENCIAS
		Double[] frecuencias = new Double[size];
		for (int i= 0;i<size;i++) {
			Double value = 0.0;
			do {
				System.out.println("Ingrese la frecuencia del elemento " + i + ": ");
				String line = this.sc.nextLine();
				Double aux = this.getValueDoublefrom(line);
				if (aux > 0 && aux < 1) {
					value = aux;
				}else {
					System.out.println("--------- Ingrese una frecuencia entre 0 y 1--------");
				}
			}while(value < 0 && value > 1);
			frecuencias[i] = value;
		}
		
		// PROBABILIDADES DE ENTRADA
		Double[] probEntrada = new Double[size];
		for (int i= 0;i<size;i++) {
			Double value = 0.0;
			do {
				System.out.println("Ingrese la probabilidad de entrada del elemento " + i + ": ");
				String line = this.sc.nextLine();
				Double aux = this.getValueDoublefrom(line);
				if (aux > 0 && aux < 1) {
					value = aux;
				}else {
					System.out.println("--------- Ingrese una frecuencia entre 0 y 1--------");
				}
			}while(value < 0 && value > 1);
			probEntrada[i] = value;
		}

		// TIPO
		this.type = 0;
		do {
			System.out.println("Ingrese el el numero de opcion:");
			System.out.println("1.-Cuantificable");
			System.out.println("2.-Transmision binaria");
			System.out.println("3.- Transicion entre estados");
			String line = this.sc.nextLine();
			int aux = this.getValefrom(line);
			if (aux == -1) {
				System.out.println("--------- Ingrese un valor entero positivo --------");
			}else if(aux == 0) {
				System.out.println("--------- Ingrese un valor mayor a cero--------");
			}else {
				this.type = aux;
			}
		}while(this.type < 1 || this.type > 3);
		this.transicionDeProbabilidades(this.castArray(conjunto), frecuencias, probEntrada);
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
		
		// CONJUNTO
		ArrayList<String> conjunto = new ArrayList<String>();
		System.out.println("");
		for (int i= 0;i<size;i++) {
			String value = "";
			do {
				String aux = this.getAlphaNumericString(1);
				if(!conjunto.contains(aux)){
					value = aux;
				}
			}while(value.length() < 1);
			conjunto.add(value);
		}
		// FRECUENCIAS
		Double[] frecuencias = new Double[size];
		for (int i= 0;i<size;i++) {
			Double value = (double) (1 / this.getRandomNumber(0, 20));
			frecuencias[i] = value;
		}
		
		// PROBABILIDADES DE ENTRADA
		Double[] probEntrada = new Double[size];
		for (int i= 0;i<size;i++) {
			Double value = (double) (1 / this.getRandomNumber(0, 20));
			probEntrada[i] = value;
		}
		
		// TIPO
		this.type = 0;
		do {
			System.out.println("Ingrese el el numero de opcion:");
			System.out.println("1.-Cuantificable");
			System.out.println("2.-Transmision binaria");
			System.out.println("3.- Transicion entre estados");
			String line = this.sc.nextLine();
			int aux = this.getValefrom(line);
			if (aux == -1) {
				System.out.println("--------- Ingrese un valor entero positivo --------");
			}else if(aux == 0) {
				System.out.println("--------- Ingrese un valor mayor a cero--------");
			}else {
				this.type = aux;
			}
		}while(this.type < 1 || this.type > 3);
		
		this.transicionDeProbabilidades(this.castArray(conjunto), frecuencias, probEntrada);
	}
	
	// ALGORITMO
	void transicionDeProbabilidades(String[] conjunto, Double[] frecuencias, Double[] probEntrada) {
		Double[] infoMutua = this.traerInfoMutua(frecuencias);
		Double[] entropias = this.traerEntropias(frecuencias, infoMutua);
		Double[][] primerMatriz = this.traerPrimerMatriz(probEntrada);
		Double[][] segundaMatriz = this.traerSegundaMatriz(primerMatriz, frecuencias);
		Double[] probSalida = new Double[conjunto.length];
		for(int i=0;i<conjunto.length;i++) {
			probSalida[i] = 0.0;
			for(int j=0;j<conjunto.length;j++) {
				probSalida[i] = probSalida[i] + segundaMatriz[j][i];
			}
		}
		this.imprimirMatriz("Segunda", segundaMatriz);
		this.imprimirProbabilidadesSalida(conjunto, probSalida);
	}
	
	
	private Double[][] traerPrimerMatriz(Double[] prob) {
		Double[][] matriz = new Double[prob.length][prob.length];
		for (int i=0;i<prob.length;i++) {
			Double value = (1-prob[i])/prob.length;
			for (int j=0;j<prob.length;j++) {
				matriz[i][j] = (i == j) ? prob[i] : value;
			}
		}
		return matriz;
	}
	
	private Double[][] traerSegundaMatriz(Double[][] primera, Double[] prob){
		Double[][] matriz = new Double[prob.length][prob.length];
		for (int i=0;i<prob.length;i++) {
			for (int j=0;j<prob.length;j++) {
				matriz[i][j] = prob[i] * primera[i][j];
			}
		}
		return matriz;
	}
	
	private Double[] traerInfoMutua(Double[] prob) {
		Double[] info= new Double[prob.length];
		for(int i=0;i<prob.length;i++) {
			int base = (this.type == 1) ? 10 : 2;
			Double value = prob[i];
			info[i] = (-1) * this.log(value, base, this.type);
		}
		return info;
	}
	
	private Double[] traerEntropias(Double[] prob, Double[] infoMutua) {
		Double[] info= new Double[prob.length];
		for(int i=0;i<prob.length;i++) {
			info[i] = prob[i]*infoMutua[i];
		}
		return info;
	}
	
	//RESULTADOS
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
	
	public void imprimirMatriz(String name, Double[][] matriz) {
		System.out.println("");
		System.out.println("Matriz " + name);
		System.out.println("");
		for(int i=0; i<matriz.length ; i++) {
			String line = "";
			for(int j=0; j<matriz.length ; j++) {
				line = line + " - " + matriz[i][j];
			}
			System.out.println(line);
		}
	}
	
	public void imprimirProbabilidadesSalida(String[] conjunto, Double[] prob) {
		System.out.println("");
		System.out.println("Frecuencias de salida");
		for(int i=0; i<conjunto.length ; i++) {
			System.out.println(conjunto[i] + " - " + prob[i]);
		}
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
	
	// METODOS AUXILIARES
	public Double[] getProbabilities(String[] array) {
		Double[] probabilidades = new Double[array.length];
		for(int i=0; i<array.length;i++) {
			Double value = Double.parseDouble(array[i]);
			if (value != null) {
				if (value > 0) {
					probabilidades[i] = value;
				}else {
					System.out.println("");
					System.out.println("Solo se permiten probabilidades mayores a 0");
					System.out.println("");
				}
			}else {
				System.out.println("");
				System.out.println("Favor de elegir un numero valido en la posicion " + i);
				System.out.println("");
			}
		}
		return probabilidades;
	}
	
	public Double[] getFrecuencias(String[] array) {
		Double[] frecuencias = new Double[array.length];
		for(int i=0; i<array.length;i++) {
			Double value = Double.parseDouble(array[i]);
			if (value != null) {
				if (value > 0) {
					frecuencias[i] = value;
				}else {
					System.out.println("");
					System.out.println("Solo se permiten probabilidades mayores a 0");
					System.out.println("");
				}
			}else {
				System.out.println("");
				System.out.println("Favor de elegir un numero valido en la posicion " + i);
				System.out.println("");
			}
		}
		return frecuencias;
	}
	
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
	
	public Double getValueDoublefrom(String text) {
		String aux = text.replaceAll("[^\\d./]", "");
		if (aux.length() > 0) {
			Double value = Double.parseDouble(aux);
			return value;
		}else{
			System.out.println("");
			System.out.println("Favor de elegir un numero entero valido");
			System.out.println("");
		}
		return null;
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
			File myObj = new File("src/data.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.length() > 0){
					if (info.size() < 3) {
						info.add(data);
					}else if (!(this.type == 1 || this.type == 2 || this.type == 3)) {
						int aux = this.getValefrom(data);
						if (aux == 1 || this.type == 2 || this.type == 3) {
							this.type = aux;
						}
					}
				}
			}myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred." + e.getMessage());
		}
		return info;
	}
	
	private Double log(double num, int base, int type) {
		if(type == 3) {
			return Math.log(num);
		}
		return (Math.log10(num) / Math.log10(base));
	}
	
	public String[] castArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		return array;
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
