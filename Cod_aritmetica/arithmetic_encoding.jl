include("binary.jl")
include("r_method.jl")

function collected_values(values)
  return sort(collect(keys(values)))
end

function productLetterFrequencies(frequencies, letters)
  return prod([ frequencies[letter] for letter in letters ])
end

function sumLetterFrequencies(frequencies, letters)
  return sum([ frequencies[letter] for letter in letters])
end

function getFirstIndexValues(prev_alpha, frequencies, letters)
  alpha = prev_alpha
  len = productLetterFrequencies(frequencies, letters)
  beta = alpha + len
  return alpha,beta,len
end

function getMiddleValues(position, pre_alpha, pre_len, frequencies, letters, prev_letters, first=false)
  previus_lengths = first ? 0 : sumLetterFrequencies(frequencies, prev_letters)
  len = productLetterFrequencies(frequencies, letters)
  alpha = pre_alpha + pre_len*previus_lengths
  beta = len + alpha
  return alpha,beta,len
end

function getLastIndexValues(pre_beta,letters, frequencies)
  beta = pre_beta
  len = productLetterFrequencies(frequencies, letters)
  alpha = beta - len
  return alpha,beta,len
end

function arithmetic_encoding(frequencies, sorted_alphabet, sequence)
  first = true
  alpha = 0
  betha = 0
  long = 0
  # prev_letters = sorted letters, takes the previous of the current letter, if 'c' then takes 'a','b' and so on
  for (index,letter) in enumerate(sequence)
    println("letra $letter")
    position_letter = findfirst(x-> x == letter, sorted_alphabet) 
    if position_letter == 1
      alpha,betha,long = getFirstIndexValues(alpha,frequencies,sequence[1:index])
    elseif position_letter == length(frequencies)
      betha = first ? frequencies[sorted_alphabet[position_letter]] : betha
      alpha,betha,long = getLastIndexValues(betha,sequence[1:index],frequencies)
    else
      var =frequencies[sorted_alphabet[position_letter-1]]
      prev_letters = sorted_alphabet[1:findfirst(isequal(letter), sorted_alphabet)-1]
      pre_a, pre_l = first ? (var,var) : (alpha,long)
      alpha,betha,long = getMiddleValues(position, pre_a, pre_l, frequencies, sequence[1:index], prev_letters, first)
    end
    first = false
  end
  println("a $alpha b $betha, l $long")
  return alpha,betha,long
end

function arithmetic_adaptative(frequencies, sorted_alphabet, sequence)
  first = true
  alpha = 0
  betha = 0
  long = 0
  # prev_letters = sorted letters, takes the previous of the current letter, if 'c' then takes 'a','b' and so on
  for (index,letter) in enumerate(sequence)
    println("letra $letter")
    position_letter = findfirst(x-> x == letter, sorted_alphabet) 
    if position_letter == 1
      #alpha,betha,long = getFirstIndexValues(alpha,frequencies,sequence[1:index])
    elseif position_letter == length(frequencies)
      #betha = first ? frequencies[sorted_alphabet[position_letter]] : betha
      #alpha,betha,long = getLastIndexValues(betha,sequence[1:index],frequencies)
    else
      #var =frequencies[sorted_alphabet[position_letter-1]]
      #prev_letters = sorted_alphabet[1:findfirst(isequal(letter), sorted_alphabet)-1]
      #pre_a, pre_l = first ? (var,var) : (alpha,long)
      #alpha,betha,long = getMiddleValues(position, pre_a, pre_l, frequencies, sequence[1:index], prev_letters, first)
    end
    first = false
  end
  println("a $alpha b $betha, l $long")
  return alpha,betha,long
end

function main()
  fi = [0.25,0.2,0.2,0.15,0.1,0.1]
  alphabet = ['a','b','c','d','e','f']
  sequence = "bfac"
  frequencies = Dict([ (letter,f) for (letter,f) in zip(alphabet,fi)])
  sorted_alphabet = collected_values(frequencies)
  println("Inicio de ciclo")
  
  alpha,betha,long = arithmetic_encoding(frequencies, sorted_alphabet, sequence)
  run_method_one(alpha,betha,long)
  run_method_two(alpha,betha)
end

main()

