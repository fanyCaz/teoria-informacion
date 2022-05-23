include("binary.jl")
include("r_method.jl")

function makefreqdict(s::String)
  d = Dict{Char, Int}()
  for c in s
    if !haskey(d, c)
        d[c] = 1
    else
        d[c] += 1
    end
  end
  return d
end

function collected_values(freqs)
  return reverse( sort(collect(freqs), by=x->x[2]) )
  #return sort(collect(values(vals)))
  #return collect(keys(values))
  #return sort(collect(keys(values)))
  #push!(sorted_values,frequencies[i])

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

function decoder(sorted_alphabet, frequencies, n, r)
  sorted_values = []
  for i in sorted_alphabet
    push!(sorted_values,frequencies[i])
  end

  println("Decoder")
  letters = []
  cumulative_freqs = cumsum(sorted_values)
  a = 0
  l = 1
  println("alphabet : $sorted_alphabet")
  for iter in 1:n
    for (idx,val) in enumerate(cumulative_freqs)
      min_alpha = idx == 1 ? 0 : sorted_values[idx-1]
      println("what is going on $iter : r $r , a: $a ,l: $l")
      println("condiciones $min_alpha & $val")
      if r >= min_alpha && r < val
        letter = sorted_alphabet[idx]
        push!(letters, letter)
        a = idx == 1 ? min_alpha : sorted_values[idx-1]
        l = frequencies[letter]
        r = (r-a)/l
        break
      end
    end
  end
  println("Letras decodificadas $(join(letters))")
end

function arithmetic_encoding(frequencies, sorted_alphabet, sequence)
  first = true
  alpha = 0
  beta = 0
  long = 0
  # prev_letters = sorted letters, takes the previous of the current letter, if 'c' then takes 'a','b' and so on
  println("sroted alfaphet $sorted_alphabet")
  # el find firts debe encontrar la letra en el sorted_alphabet pero solo en su columna 1
  for (index,letter) in enumerate(sequence)
    position_letter = findfirst(x-> x == letter, sorted_alphabet) 
    println("position $position_letter")
    if position_letter == 1
      alpha,beta,long = getFirstIndexValues(alpha,frequencies,sequence[1:index])
    elseif position_letter == length(frequencies)
      beta = first ? frequencies[sorted_alphabet[position_letter]] : beta
      alpha,beta,long = getLastIndexValues(beta,sequence[1:index],frequencies)
    else
      var =frequencies[sorted_alphabet[position_letter-1]]
      prev_letters = sorted_alphabet[1:findfirst(isequal(letter), sorted_alphabet)-1]
      pre_a, pre_l = first ? (var,var) : (alpha,long)
      alpha,beta,long = getMiddleValues(position, pre_a, pre_l, frequencies, sequence[1:index], prev_letters, first)
    end
    first = false
  end
  return alpha,beta,long
end

function arithmetic_adaptative(frequencies, sorted_alphabet, sequence)
  first = true
  alpha = 0
  beta = 0
  long = 0
  elements = unique(sequence)
  # prev_letters = sorted letters, takes the previous of the current letter, if 'c' then takes 'a','b' and so on
  for (index,letter) in enumerate(elements)
    long = count(isequal(letter), sequence)/length(sequence)
    if first
      alpha = 0
      beta = long
    else
      alpha = beta
      beta = long + alpha
    end
    first = false
  end
  return alpha,beta,long
end

function main()
  sequence = "abac"
  freq_table = makefreqdict(sequence)
  float_freq = Dict()
  len_str = length(sequence)
  for (key, value) in freq_table
    float_freq[key] = value / len_str
  end
  # freq_table = float_freq
  fi = values(float_freq)
  alphabet = keys(float_freq)

  sorted_alphabet = collected_values(float_freq)
  println("Inicio de ciclo")
  println(freq_table)
  println(float_freq)
  frequencies = float_freq
  levels = []
  for (idx,set) in enumerate(sequence)
    println("Analizando secuencia: $(sequence[1:idx])")
    alpha,beta,long = arithmetic_encoding(frequencies, sorted_alphabet, sequence[1:idx])
    #alpha,beta,long = arithmetic_adaptative(frequencies, sorted_alphabet,sequence[1:idx] )
    println("a $alpha b $beta, l $long")
    run_method_one(alpha,beta,long)
    run_method_two(alpha,beta)
    push!(levels,(beta+alpha)/2)
  end
  println("levels : $levels -> $(last(levels))")
  decoder(sorted_alphabet, frequencies, length(sequence), last(levels))

end

main()

