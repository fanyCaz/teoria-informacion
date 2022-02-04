import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Archivo de texto
 * Tipos:
 * 1.- Cuantificable
 * 2.- Transmision de datos binaria 
 * 3.- Transicion entre estados
 * 
 * Orden de datos:
 * 
 * */

public class EntropiaInfoMutua {
	List<String> data;
	List<Double> items;
	Scanner sc;
	
	int type;

	public static void main(String[] args) {
		EntropiaInfoMutua obj = new EntropiaInfoMutua();
	}
	
	EntropiaInfoMutua(){
		this.printMenu();	
	}
	
	// MARK: - Menu
	public void printMenu() {
		System.out.println("------------------------------------------");
		System.out.println("------------------MENU--------------------");
		System.out.println("------------------------------------------");
		System.out.println("Seleccione la opcion de ingresar datos");
		System.out.println("");
		System.out.println("1.- Por archivo");
		System.out.println("2.- Manualmente");
		System.out.println("3.- Aleatoriamente");
		System.out.println("");
		this.sc = new Scanner(System.in);
		String aux = this.sc.nextLine();
		if(aux.length() > 0) {
			Double option = this.getValefrom(aux);
			if (option == 1) {
				this.useDataFile();
			} else if (option == 2) {
				this.requestDatatoUser();
			} else if (option == 3) {
				this.useRandomValues();
			} else {
				this.clearConsole();
				System.out.println("--------- Ingrese un valor valido --------");
				this.printMenu();
			}
		}else {
			this.clearConsole();
			this.printMenu();
		}
		
	}
	
	public void requestDatatoUser() {
		this.requestType();
		Double num = null;
		int cant = 0;
		do {
			cant = 0;
			System.out.println("Ingrese la cantidad de elementos: ");
			String line = this.sc.nextLine();
			num = this.getValefrom(line);
			if (num == null) {
				System.out.println("--------- Ingrese un valor valido --------");
			}else {
				cant = (int)Math.round(num);
				if (cant < 2) {
					System.out.println("------ Ingrese un valor mayor a 1 --------");
				}
			}
		}while(num == null || cant < 2);
		Double suma = 0.0;
		do {
			suma = 0.0;
			this.items = new ArrayList<>();
			for(int i=0; i<cant; i++) {
				double aux = this.requestElement(i);
				this.items.add(aux);
				suma = suma + aux;
			}
			if (suma != 0) {
				System.out.println("------ La suma de los numeros debe ser 1 --------");
			}
		}while(suma != 1.0);
		
		this.validateSumOfProbabilities();
		List<Double> mutualInfo = this.getMutualInfo(this.items);
		List<Double> entropies = this.getEntropiesWith(this.items, mutualInfo);
		Double totalMutualInfo = this.getTotal(mutualInfo);
		Double totalEntropies = this.getTotal(entropies);
		this.printResults(totalMutualInfo, totalEntropies);
	}
	
	public void useDataFile() {
		this.data = this.getInfo();
		this.validateType();
		this.validateAndTransformData();
		this.validateSumOfProbabilities();
		List<Double> mutualInfo = this.getMutualInfo(this.items);
		List<Double> entropies = this.getEntropiesWith(this.items, mutualInfo);
		Double totalMutualInfo = this.getTotal(mutualInfo);
		Double totalEntropies = this.getTotal(entropies);
		this.printResults(totalMutualInfo, totalEntropies);
	}
	
	
	
	public void useRandomValues() {
		this.requestType();
		Double num = null;
		int cant = 0;
		do {
			cant = 0;
			System.out.println("Ingrese la cantidad de elementos: ");
			String line = this.sc.nextLine();
			num = this.getValefrom(line);
			if (num == null) {
				System.out.println("--------- Ingrese un valor valido --------");
			}else {
				cant = (int)Math.round(num);
				if (cant < 2) {
					System.out.println("------ Ingrese un valor mayor a 1 --------");
				}
			}
		}while(num == null || cant < 2);
		Double suma = 1.0;
		this.items = new ArrayList<>();
		do {
			System.out.println(suma);
			if (cant == 1) {
				this.items.add(suma);
				suma = 0.0;
			}else {
				int random = this.getRandomNumber(2, 20);
				Double value = suma/random;
				this.items.add(value);
				suma = suma-value;
			}
			cant = cant - 1;
		}while(suma > 0);
		
		this.validateSumOfProbabilities();
		List<Double> mutualInfo = this.getMutualInfo(this.items);
		List<Double> entropies = this.getEntropiesWith(this.items, mutualInfo);
		Double totalMutualInfo = this.getTotal(mutualInfo);
		Double totalEntropies = this.getTotal(entropies);
		this.printResults(totalMutualInfo, totalEntropies);
	}
	
	// MARK: - Results
	public void printResults(Double mutualInfoTotal, Double entropyTotal) {
		String unidad = (this.type == 3)?"Nats/simbolo":(this.type == 2)?"Bits/simbolo": "hartleys/segundo";
		System.out.println("");
		System.out.println("Informacion Mutua");
		System.out.println("Total de informacion Mutua: " + mutualInfoTotal + " " + unidad);
		System.out.println("Total de Entropia: " + entropyTotal + " " + unidad);
		
	}
	
	// MARK: - Calculos
	public List<Double> getMutualInfo(List<Double> items) {
		List<Double> mutualInfo= new ArrayList<>();
		int base = (this.type == 2)? 2: 10;
		System.out.println("");
		System.out.println("Informacion Mutua");
		for(int i=0;i<items.size();i++){
			Double result = - this.log(items.get(i), base, this.type);
			System.out.println("Probabilidad: "+ this.items.get(i)+ ", Informacion Mutua = " + result);
			mutualInfo.add(result);
		}
		return mutualInfo;
	}
	
	public List<Double> getEntropiesWith(List<Double> items, List<Double>mutualInfo) {
		List<Double> entropies = new ArrayList<>();
		System.out.println("");
		System.out.println("Entropias");
		int base = (this.type == 2)? 2: 10;
		for(int i=0;i<items.size();i++){
			Double result = items.get(i)*mutualInfo.get(i);
			System.out.println("Probabilidad: "+ this.items.get(i)+ ", Entropia = " + result);
			entropies.add(result);
		}
		return entropies;
	}
	
	public Double getTotal(List<Double> values) {
		Double total = 0.0;
		for(int i=0;i<values.size();i++) {
			total = total + values.get(i);
		}
		return total;
	}
	
	private static Double log(double num, int base, int type) {
		if(type == 3) {
			return Math.log(num);
		}
		return (Math.log10(num) / Math.log10(base));
	}
	
	
	// MARK: - Validaciones
	public void validateType() {
		if(this.type == 0) {
			System.out.println("");
			System.out.println("Necesita agregar en el type en el archivo info.txt en una sola linea");
			System.exit(0);
		}else if(this.type < 0 || this.type > 3) {
			System.out.println("");
			System.out.println("Solo se aceptan las opciones 1, 2 y 3 para el tipo");
			System.exit(0);
		}
	}
	
	public void validateAndTransformData() {
		if(this.data.size() > 0) {
			List<Double> items = new ArrayList<>();
			for(int i=0;i<this.data.size();i++){
				Double val = this.getValefrom(this.data.get(i));
				if(val == null) {
					System.out.println("");
					System.out.println("El valor de la fila "+i+" no es valido, solo se aceptan numeros, decimales y fracciones");
					System.exit(0);
				}else {
					items.add(val);
				}
			}
			this.items = items;
		}
		if(this.items == null || this.items.size() == 0) {
			System.out.println("");
			System.out.println("No hay datos validos para trabajar");
			System.exit(0);
		}
	}
	
	public void validateSumOfProbabilities() {
		if(this.items.size() > 0) {
			double total = 0.0;
			for(int i=0;i<this.items.size();i++) {
				total = total + this.items.get(i);
			}
			if(total != 1) {
				System.out.println("");
				System.out.println("La suma de las probabilidades es "+total+", tiene que ser igual a 1");
				System.exit(0);
			}
		}else {
			System.out.println("");
			System.out.println("No hay suficientes datos para calcular");
			System.exit(0);
		}
	}
	
	// MARK: - Recopile and Transform Data
	public List<String> getInfo(){
		List<String> info = new ArrayList<>();
		try {
			File myObj = new File("src/info.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.toLowerCase().contains("type")) {
					this.setType(data.toLowerCase());
				}else if (data.length() > 0){
					info.add(data);
				}
			}myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred." + e.getMessage());
		}
		return info;
	}
	
	public void setType(String aux) {
		String text = aux.replaceAll("[^\\d.]", "");
		text = text.replace("type:", "");
		text = text.replace("type?=", "");
		if(text.length() > 0) {
			this.type = Integer.parseInt(text);
		}
	}
	
	public void requestType() {
		do {
			this.type = 0;
			System.out.println("Ingrese el numero del tipo de problema: ");
			System.out.println("");
			System.out.println("1.- Cuantificable");
			System.out.println("2.- Transmision de datos binaria");
			System.out.println("3.- Transicion entre estados");
			String line = this.sc.nextLine();
			Double num = this.getValefrom(line);
			if (num == null) {
				System.out.println("--------- Ingrese un valor valido --------");
			}else {
				int cant = (int)Math.round(num);
				if (cant > 0 && cant < 4) {
					this.type = cant;
				}else {
					System.out.println("------ Ingrese un entero entre el 1 y 3 --------");
				}
			}
		}while(this.type > 3 && this.type < 1 );
	}
	
	public double requestElement(int index) {
		double value = 0;
		do {
			System.out.println("Ingrese la probabilidad del elemento "+index+": ");
			String line = this.sc.nextLine();
			if (line.length()>0) {
				Double aux = this.getValefrom(line);
				if(aux == null) {
					System.out.println("--------- Ingrese un valor valido --------");
				}else {
					value = aux;
					if (value <= 0) {
						System.out.println("------ Ingrese un valor mayor a 0 --------");
					}else if (value > 1) {
						System.out.println("------ Ingrese un valor menor a 1 --------");
					}
				}
			}
		}while(value < 0 || value > 1);
		return value;
	}
	
	public Double getValefrom(String text) {
		String aux = text.replaceAll("[^\\d./]", "");
		if(text.contains("/")){
			String[] split = text.split("/");
			if(split.length == 2) {
				Double val1 = Double.parseDouble(split[0]);
				Double val2 = Double.parseDouble(split[1]);
				if(val2 == 0) {
					System.out.println("");
					System.out.println("No se aceptan fracciones con 0 en el denominador, revisa los datos");
					System.exit(0);
				}
				return val1/val2;
			}else {
				System.out.println("");
				System.out.println("No aceptan fracciones de 3 niveles o mas");
				System.exit(0);
			}
		}else if (text.length() > 0) {
			Double value = Double.parseDouble(text);
			return value;
		}
		return null;
	}
	
	// MARK - Auxiliars
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
	
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
	
}
