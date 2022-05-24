import math

def getLogarithmType(event_type: str):
  logarithm = 0
  if event_type == 'STATE':
    logarithm = math.log
  elif event_type == 'DATA':
    logarithm = math.log2
  elif event_type == 'QUANTIFIABLE':
    logarithm = math.log10
  else:
    print("No se ha podido establecer que tipo de evento es")
    return None
  return logarithm

def autoinformation(items: list, event_type: str) -> list:
  logarithm = getLogarithmType(event_type)
  if logarithm is not None:
    res = [ -logarithm(item) for item in items ]
    return res

def entropy(items: list, event_type: str) -> float:
  logarithm = getLogarithmType(event_type)
  if logarithm is not None:
    res = [ -item*logarithm(item) for item in items]
    return sum(res)

