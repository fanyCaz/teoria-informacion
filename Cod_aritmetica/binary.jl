function smallest_integer(alpha,betha,long)
  isUnderLength = false
  t = 0
  while !isUnderLength
    t += 1
    isUnderLength = 1/(2^t) <= long
  end
  println("t $t")

end
