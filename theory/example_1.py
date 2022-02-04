import math

def infoContent(probability: float) -> float:
  return -math.log2(probability)

def autoinformation(items: list):
  info_list = []
  for item in items:
    info_list.append( -math.log2(item) )
  return info_list, sum(info_list)

def entropy(items: list):
  ent_list = []
  for item in items:
    ent_list.append( item*math.log2(item) )
  return ent_list, -sum(ent_list)

def main():
  # head or tail, ambas con la misma probabilidad de 0.5
  # asumiendo que todos tienen la misma probabilidad de aparecer
  items = [1,1]
  probabilities = [item/len(items) for item in items]
  contents, info_content = autoinformation(probabilities)
  print(f"{info_content} bits per symbol")
  print(contents)
  # obtener algún número entre 0 y 255, con la misma probabilidad de 1/256
  probability = 1/256
  info_content = infoContent(probability)
  print(f"{info_content} bits per symbol")
  memoryless_source = [0.3,0.21,0.17,0.13,0.09,0.07,0.01,0.02]
  contents, info_content = autoinformation(memoryless_source)
  print(f"{info_content} bits per symbol")
  print(contents)
  contents_ent, entropy_value = entropy(memoryless_source)
  print(f"{entropy_value} entropy")
  print(contents_ent)
  

if __name__ == '__main__':
  main()
