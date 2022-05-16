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

function arithmetic(freq_table, sequence)

  float_freq = Dict()
  len_str = length(sequence)
  for (key, value) in freq_table
    float_freq[key] = value / len_str
  end
  # freq_table = float_freq
  fi = values(float_freq)
  alphabet = keys(float_freq)
  # sequence = "bfac"
  
  sorted_alphabet = collected_values(float_freq)
  println("Inicio de ciclo")
  first = true
  alpha = 0
  beta = 0
  long = 0
  # prev_letters = sorted letters, takes the previous of the current letter, if 'c' then takes 'a','b' and so on
  for (index,letter) in enumerate(sequence)
    println("letra $letter")
    position_letter = findfirst(x-> x == letter, sorted_alphabet) 
    if position_letter == 1
      alpha,beta,long = getFirstIndexValues(alpha,float_freq,sequence[1:index])
    elseif position_letter == length(fi)
      beta = first ? float_freq[sorted_alphabet[position_letter]] : beta
      alpha,beta,long = getLastIndexValues(beta,sequence[1:index], float_freq)
    else
      var =freq_table[sorted_alphabet[position_letter-1]]
      prev_letters = sorted_alphabet[1:findfirst(isequal(letter), sorted_alphabet)-1]
      pre_a, pre_l = first ? (var,var) : (alpha,long)
      alpha,beta,long = getMiddleValues(position, pre_a, pre_l, float_freq, sequence[1:index], prev_letters, first)
    end
    first = false
  end
  println("a $alpha b $beta, l $long")
  run_method_one(alpha,beta,long)
  run_method_two(alpha,beta)
end
# main()

