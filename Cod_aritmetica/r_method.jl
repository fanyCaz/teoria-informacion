function getCodification(val)
  code = []
  values = []
  while true
    if !(round(val,digits=6) in values)
      push!(values,round(val,digits=6))
      push!(code, val < 1 ? 0 : 1)
    else
      break
    end
    if val < 1
      val *= 2
    else
      val = (val-1)*2
    end
  end
  values = []
  println("final code $code")
  return code
end
