import math
import utils as u

def get_lengths(frequencies):
  Fi = []
  cumulative_sum = 0
  length = []
  for idx,i in enumerate(frequencies):
    if idx > 0:
      cumulative_sum += frequencies[idx-1]
      Fi.append(cumulative_sum)
    else:
      Fi.append(0)
    length.append( math.ceil(math.log2(1/i)) )
  return length

def get_codification(r):
  code = []
  values = []
  val = 1/r
  values.append(str(val))
  while 1:
    if val < 1:
      val *= 2
    else:
      val = (val-1)*2
    formatted_value = "{:.4f}".format(val)
    if formatted_value not in values:
      values.append(formatted_value)
      code.append( 0 if val < 1 else 1 )
    else:
      break

  values = []
  return code

def codificate(code, lengths):
  code_string = "".join( str(x) for x in code )
  print("Codification -")
  for idx,i in enumerate(lengths):
    print(f'length {i} -> {code_string[:i]}')

fi = [0.3,0.22,0.18,0.15,0.1,0.05]

r = u.input_normalized('¿Cuál es el valor de r?')
length = get_lengths(fi)
code = get_codification(r)
codificate(code,length)
u.compression_ratio(length, fi)
