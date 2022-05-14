function smallest_integer(alpha,betha,long)
  isUnderLength = false
  t = 0
  while !isUnderLength
    t += 1
    isUnderLength = 1/(2^t) <= long
  end
  println("t $t")
  return t
end

function get_inequalities(alpha,betha,t)
  lower = 2.0^(-t)
  upper = 2.0^(-t+1)

  alpha_l = alpha*(2^t)
  betha_l = betha*(2^t)
  println("l $lower u $upper aa $alpha_l bb $betha_l")
  return min( round(alpha_l), round(betha_l) )
end
