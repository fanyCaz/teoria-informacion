
from heapq import heappush, heappop, heapify
from collections import defaultdict
import os
import collections

class DataLoader:
	def __init__(self, filePath):
		self.filePath = filePath
		self.fileHandle = open(self.filePath, "r")
		self.data = self.fileHandle.read()

	# Construir un diccionario de caracteres y sus correspondientes frecuencias
	def buildFrequencyTable(self):
		self.frequencyTable = defaultdict(int)
		for c in self.data:
			if c not in self.frequencyTable:
				self.frequencyTable[c] = 1
			else:
				self.frequencyTable[c] += 1
		print("Type of 'frequencyTable' = ", type(self.frequencyTable))

	# Imprime la tabla de frecuencias en orden descendente
	def printOutFrequencyTable(self):
		self.frequencyTableSorted = sorted(self.frequencyTable.items(), key=lambda x:x[1], reverse=True)
		for x in range(0, len(self.frequencyTableSorted)):
			print(self.frequencyTableSorted[x])


	# Función auxiliar que devuelve el tamaño del archivo cargado
	def getFileSize(self):
		return os.path.getsize(self.filePath)

	# La función toma como entrada una tabla de frecuencias y devuelve los códigos huffman correspondientes
	def returnHuffmanCodes(self):
		
	    pqueue = [[frequency, [character, ""]] for character, frequency in self.frequencyTable.items()]

	    
	    heapify(pqueue)


	    while len(pqueue) > 1:
	        first = heappop(pqueue) # 
	       	#print("first = ", first)
	        second = heappop(pqueue) # 
	       	#print("second = ", second)
	        for merge in first[1:]:
	            merge[1] = '0' + merge[1]
	        for merge in second[1:]:
	            merge[1] = '1' + merge[1]
	        heappush(pqueue, [first[0] + second[0]] + first[1:] + second[1:]) 
	    return sorted(heappop(pqueue)[1:], key=lambda p: (len(p[-1]), p))

def main():
	print("Codificación Huffman adaptativa")
	choice = True
	data = ""
	dataLoader = ""

	# Realizar el bucle principal hasta que el usuario introduzca X o x para salir
	while choice:
			print("\n1. Introducir datos\n2. Crear y mostrar la tabla de frecuencias ordenada\n3. Codificar y mostrar los codigos Huffman\nX. Exit")
			choice = input("\nElija una de las opciones mostradas ")
			if choice == "1":
				path = input("\nIngresar ruta del archivo: ")
				# Cargar archivo desde la ruta dada
				dataLoader = DataLoader(path) 

				
				if(len(dataLoader.data) > 100):
					print("¡Carga exitosa! Sus datos ocupan: ", dataLoader.getFileSize(), "bytes \n")
					print("El siguiente paso es construir una tabla de frecuencias\n\n")
				else:
					print("Has cargado: ", dataLoader.data, " que pesa: ", dataLoader.getFileSize(), "bytes\n")
					print("El siguiente paso es construir una tabla de frecuencias\n")
				data = dataLoader.data

			if choice == "2":
				# si los datos no se han cargado correctamente o están vacíos, no podemos construir la tabla de frecuencias
				if data != "":
					dataLoader.buildFrequencyTable()
					dataLoader.printOutFrequencyTable()
					print("Listo para comprimir informacion\n\n")
				else:
					print("\n Tienes que introducir datos")

			if choice == "3":
				# Si los datos no se han cargado correctamente o están vacíos, no podemos construir la tabla de frecuencias
				if isinstance(dataLoader, str):
					print("Primero tienes que introducir datos")
				else:
					print("Va a comprimir e imprimir las tuplas que contienen (caracter, frecuencia, codigo huffman).")
					codes = dataLoader.returnHuffmanCodes()
					for x in codes:
						print (x[0], dataLoader.frequencyTable[x[0]], x[1])
					print("")

			if choice == "X" or choice == "x":
				print("GRACIAS")
				break;

if __name__ == "__main__":
	main()