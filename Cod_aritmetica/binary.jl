include("r_method.jl")

function readeable_code(code)
  return join(code)
end

# Method 1
function smallest_integer(long)
  isUnderLength = false
  t = 0
  while !isUnderLength
    t += 1
    isUnderLength = 1/(2^t) <= long
  end
  return t
end

function get_inequalities(alpha,beta,t)
  lower = 2.0^(-t)
  upper = 2.0^(-t+1)

  alpha_l = alpha*(2^t)
  beta_l = beta*(2^t)
  return min( round(alpha_l), round(beta_l) )
end

function run_method_one(alpha,beta,long)
  println("Metodo 1")
  t = smallest_integer(long)
  r = get_inequalities(alpha,beta,t)
  code = getCodification(1/r)
  println( readeable_code(code) )
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
  println( readeable_code(code) )
end
