include("r_method.jl")

# Method 1
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

function run_method_one(alpha,betha,long)
  println("Metodo 1")
  t = smallest_integer(alpha,betha,long)
  r = get_inequalities(alpha,betha,t)
  getCodification(1/r)
end

# Method 2

function run_method_two(alpha,betha)
  println("Metodo 2")
  code = []
  alpha_value = alpha
  betha_value = betha

  while true
    if alpha_value < 1
      alpha_value = alpha_value*2
      alpha_binary = 0
    else
      alpha_value = (alpha_value-1)*2
      alpha_binary = 1
    end
    if betha_value < 1
      betha_value = betha_value*2
      betha_binary = 0
    else
      betha_value = (betha_value-1)*2
      betha_binary = 1
    end
    if alpha_binary == betha_binary
      push!(code,alpha_binary)
    else
      break
    end
  end
  println(code)
end
