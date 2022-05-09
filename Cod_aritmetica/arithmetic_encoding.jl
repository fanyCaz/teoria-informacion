function collected_values(values)
  return sort(collect(keys(values)))
end

function getFirstIndexValues(prev_alpha,letters)
  alpha = prev_alpha
  len = prod([ frequencies[letter] for letter in letters])
  beta = alpha + len
  return alpha,beta,len
end

function getMiddleValues(position, pre_alpha, pre_len, first=false)
  previus_lengths = first ? 0 : prod(fi[1:position])
  len = fi[position]
  println("posss $position len $len")
  alpha = pre_alpha + pre_len*previus_lengths
  beta = len + alpha
  return alpha,beta,len
end

function getLastIndexValues(pre_beta,letters, frequencies)
  beta = pre_beta
  len = prod([ frequencies[letter] for letter in letters])
  alpha = beta - len
  return alpha,beta,len
end

fi = [0.25,0.2,0.2,0.15,0.1,0.1]

alphabet = ['a','b','c','d','e','f']

sequence = "bfac"
frequencies = Dict([ (letter,f) for (letter,f) in zip(alphabet,fi)])
sorted_alphabet = collected_values(frequencies)

first_letter = sequence[1]
position = findfirst(x-> x == first_letter, sorted_alphabet)
if position == 1
  alpha = fi[1]
elseif position == length(fi)
  alpha = 0
else
  alpha,beta,len = getMiddleValues(position,frequencies[sorted_alphabet[position-1]], frequencies[sorted_alphabet[position-1]],true)
end
println("a $alpha b $beta, l $len")

second_letter = sequence[2]
position = findfirst(x-> x == second_letter, sorted_alphabet) 
println(position)

if position == 1
  alpha = fi[1]
elseif position == length(fi)
  println("entro aqui")
  # sequence[valor de index de palabra en sequecia]
  alpha,beta,len = getLastIndexValues(beta,[sequence[2-1],second_letter],frequencies)
else
  alpha,beta,len = getMiddleValues(position,frequencies[sorted_alphabet[position-1]], frequencies[sorted_alphabet[position-1]],true)
end
println("a $alpha b $beta, l $len")

third_letter = sequence[3]
position = findfirst(x-> x == third_letter, sorted_alphabet) 

if position == 1
  println("position 1 entro aqui")
  alpha,beta,len = getFirstIndexValues(alpha,sequence[1:3])
elseif position == length(fi)
  alpha,beta,len = getLastIndexValues(beta,[sequence[2-1],second_letter],frequencies)
else
  alpha,beta,len = getMiddleValues(position,frequencies[sorted_alphabet[position-1]], frequencies[sorted_alphabet[position-1]],true)
end

println("a $alpha b $beta, l $len")


println("Inicio de ciclo")
global first = true
#global alpha, betha, long
for (index,letter) in enumerate(sequence)
  position_letter = findfirst(x-> x == letter, sorted_alphabet) 
  if position_letter == 1
    alpa,betha,long = getFirstIndexValues(alpa,sequence[1:index])
  elseif position == length(fi)
    alpa,betha,long = getLastIndexValues(betha,sequence[1:index],frequencies)
  else
    println("freq $(frequencies[sorted_alphabet[position_letter-1]]) ")
    var =frequencies[sorted_alphabet[position_letter-1]] 
    alpa,betha,long = getMiddleValues(position,var, var,first)
    global first = false
  end
  println("a $alpa b $beta, l $long")
  alpa,beta,long
end


