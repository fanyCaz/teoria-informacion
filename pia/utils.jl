function compression_ratio(frequencies, codes)
  length_entry = 8
  length_out   = sum([frequencies[f]*length(codes[f]) for f in keys(frequencies) ])
  return length_entry/length_out
end

function compression_ratio_arithmetic(entry, out)
  if length(out) == 0
    return missing
  end
  return (length(entry)*8)/length(out)
end
