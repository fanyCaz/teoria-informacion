include("r_method.jl")

# Method 1
function smallest_integer(alpha,beta,long)
  isUnderLength = false
  t = 0
  while !isUnderLength
    t += 1
    isUnderLength = 1/(2^t) <= long
  end
  println("t $t")
  return t
end

function get_inequalities(alpha,beta,t)
  lower = 2.0^(-t)
  upper = 2.0^(-t+1)

  alpha_l = alpha*(2^t)
  beta_l = beta*(2^t)
  println("l $lower u $upper aa $alpha_l bb $beta_l")
  return min( round(alpha_l), round(beta_l) )
end

function run_method_one(alpha,beta,long)
  println("Metodo 1")
  t = smallest_integer(alpha,beta,long)
  r = get_inequalities(alpha,beta,t)
  getCodification(1/r)
end

# Method 2

function run_method_two(alpha,beta)
  println("Metodo 2")
  code = []
  alpha_value = alpha
  beta_value = beta

  while true
    if alpha_value < 1
      alpha_value = alpha_value*2
      alpha_binary = 0
    else
      alpha_value = (alpha_value-1)*2
      alpha_binary = 1
    end
    if beta_value < 1
      beta_value = beta_value*2
      beta_binary = 0
    else
      beta_value = (beta_value-1)*2
      beta_binary = 1
    end
    if alpha_binary == beta_binary
      push!(code,alpha_binary)
    else
      break
    end
  end
  println(code)
end
