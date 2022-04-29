import math

fi = [0.3,0.22,0.18,0.15,0.1,0.05]

Fi = []
t_sum = 0
length = []
for idx,i in enumerate(fi):
  if idx > 0:
    t_sum += fi[idx-1]
    Fi.append(t_sum)
  else:
    Fi.append(0)
  length.append( math.ceil(math.log2(1/i)) )

r = 32
not_repeated = True
code = []
values = []
val = 1/r
values.append(str(val))
while not_repeated:
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

code_string = "".join( str(x) for x in code )
print("Codification -")
for idx,i in enumerate(length):
  print(f'length {i} -> {code_string[:i]}')

out_length = sum(list(map(lambda l,f: l*f,length,fi)) )
in_length = 8

compression_ratio = in_length/out_length
print(f'Radio de compresión: {compression_ratio}')

