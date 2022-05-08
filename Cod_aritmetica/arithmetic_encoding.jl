#import numpy as np
#import math

function collected_values(values)
  return sort(collect(keys(values)))
end

function getMiddleValues(position, pre_alpha, pre_len, first=false)
  previus_lengths = first ? 0 : prod(fi[1:position])
  len = fi[position]
  alpha = fi[position-1] + fi[position-1]*previus_lengths
  beta = len + alpha
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



