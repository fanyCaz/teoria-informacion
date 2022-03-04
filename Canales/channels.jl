function entries(input)
  # falta que tome en cuenta l para iterar y hacer la concatenación
  comb_a = []
  for i in a
    push!(comb_a,map(x->x*i,a))
  end
  comb_a = collect( Iterators.flatten(comb_a) )
  return comb_a
end

l = 1
a = ["0","1"]
A = entries(a)
print(A)
print("Numero de elementos que puede enviar $(length(A))")

a = ["0000","1111"]

A = entries(a)
print(A)
print("Numero de elementos que puede enviar $(length(A))")

