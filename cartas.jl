deck = 52
cartas_mano = 5
# cuantas manos de 5 cartas podemos formar de un deck 
manos_de_5 = binomial(deck, cartas_mano) # son combinaciones
@show manos_de_5
# cuantas de estas manos tienen 2 pares 
# cuantas combinaciones de 2 pares podemos tener
# primero seleccionamos 2 de 13 cartas
# de esas 2 de 13 cartas ocupamos formar los pares
# por lo tanto, debemos de seleccionar 2 palos de 4 DOS veces
palos = 4
cartas_por_palo = trunc(Int, deck / palos)
dos_pares = binomial(cartas_por_palo,2) * binomial(palos,2)^2
# tenemos 4 cartas nos falta 1
# para poder seleccionar la ultima carta, quitamos 2 cartas del set de 13 de cada palo
# y escojemos 1 palo de 4 porque solo es una carta
ultima_carta = binomial(cartas_por_palo-2,1) * binomial(palos,1)
# por ultimo, multiplicamos todo para obtener cuantas manos posibles formamos
manos_de_2pares = dos_pares * ultima_carta
@show manos_de_2pares
println("Probabilidad de obtener una mano con 2 pares: $(manos_de_2pares/manos_de_5)")
