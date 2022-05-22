import math
import utils as u
from shannon_method import get_codification, codificate

def get_lengths(frequencies):
  Fi = []
  length = []
  for idx,i in enumerate(frequencies):
    cumulative_sum = 0
    if idx > 0:
      cumulative_sum = sum(frequencies[:idx]) + (i/2)
      Fi.append(cumulative_sum)
    else:
      Fi.append(i/2)
    length.append( math.floor(math.log2(1/i)) + 1 )

  return length,Fi

def main(fi):
  lengths,Fi = get_lengths(fi)
  for idx,i in enumerate(Fi):
    code = get_codification(i)
    codificate(code,lengths[idx])
  u.compression_ratio(lengths,Fi)

if __name__ == '__main__':
  fi = [0.3,0.22,0.18,0.15,0.1,0.05]
  main(fi)
