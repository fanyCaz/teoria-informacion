include("binary.jl")
include("r_method.jl")

function collected_values(values)
  return reverse( sort(collect(freqs), by=x->x[2]) )
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
  cumulative_freqs = []
  for (jdx,i) in enumerate(sorted_alphabet)
    push!(sorted_values,frequencies[i[1]])
  end

  println("Decoder")
  letters = []
  cumulative_freqs = cumsum(sorted_values)
  a = 0
  l = 1
  r = (r-a)/l
  println(sorted_alphabet)
  println(cumulative_freqs)
  for iter in 1:n
    for (idx,val) in enumerate(cumulative_freqs)
      min_alpha = idx == 1 ? 0 : cumulative_freqs[idx-1]
      letter = sorted_alphabet[idx][1]
      if r >= min_alpha && r < val
        r = (r - min_alpha)/(val-min_alpha)
        push!(letters, letter)
        break
      end
    end
  end
  println("Letras decodificadas $(join(letters))")
end

function arithmetic(freq_table, sequence, sorted_alphabet)
  float_freq = Dict()
  len_str = length(sequence)
  for (key, value) in freq_table
    float_freq[key] = value / len_str
  end

  fi = values(float_freq)
  alphabet = keys(float_freq)

  first = true
  alpha = 0
  beta = 0
  long = 0
  # prev_letters = sorted letters, takes the previous of the current letter, if 'c' then takes 'a','b' and so on
  for (index,letter) in enumerate(sequence)
    position_letter = findfirst(x-> x[1] == letter, sorted_alphabet)
    if position_letter == 1
      alpha,beta,long = getFirstIndexValues(alpha,float_freq,sequence[1:index])
    elseif position_letter == length(fi)
      beta = first ? float_freq[ sorted_alphabet[position_letter][1]] : beta
      alpha,beta,long = getLastIndexValues(beta,sequence[1:index], float_freq)
    else
      var =float_freq[ sorted_alphabet[position_letter][1] ]
      prev_letters = sorted_alphabet[1:findfirst(x->x[1] == letter, sorted_alphabet)-1]
      prev_letters = [ pair[1] for pair in prev_letters ]
      pre_a, pre_l = first ? (var,var) : (alpha,long)
      alpha,beta,long = getMiddleValues(position, pre_a, pre_l, float_freq, sequence[1:index], prev_letters, first)
    end
    first = false
  end
  code_m1 = run_method_one(alpha,beta,long)
  code_m2 = run_method_two(alpha,beta)
  code = ( length(code_m1) > length(code_m2) ) ? code_m1 : code_m2
  return code,alpha,beta
end

