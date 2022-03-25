import entropy

event_types = {'DATA': 'bits/símbolo', 'STATE': 'nats/símbolo', 'QUANTIFIABLE': 'hartleys/símbolo'}

def main():
  memoryless_source = [0.3,0.21,0.17,0.13,0.09,0.07,0.01,0.02]
  res = entropy.entropy(memoryless_source,'DATA')
  print(f"{res} {event_types['DATA']}")
  res = entropy.autoinformation(memoryless_source,'DATA')
  print("Information given in each event")
  print(res)

if __name__ == '__main__':
  main()

