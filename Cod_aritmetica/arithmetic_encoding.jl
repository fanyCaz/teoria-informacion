import numpy as np
import math

def getMiddleValues(letters, position, first_value: bool= False):
  previus_lengths = math.prod(fi[:position-1]) if position-1 > len(fi) else 0
  print(previus_lengths)
  length = fi[position]
  if first_value:
    alpha = fi[position-1]+(fi[position-1]*previus_lengths)
  beta = length + alpha
  return alpha,beta,length

fi = [0.25,0.2,0.2,0.15,0.1,0.1]

alphabet = np.array(['a','b','c','d','e','f'])

sequence = 'bfac'

first_letter = sequence[0]
position = np.argwhere(alphabet == first_letter).flatten()[0]
print(position)
if position == 0:
  alpha = fi[0]
elif position == len(fi) - 1:
  alpha = 0
else:
  alpha,beta,length = getMiddleValues(first_letter,position,True)

second_letter = sequence[1]
