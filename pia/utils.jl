using DelimitedFiles

function compression_ratio(frequencies, codes)
  length_entry = 8
  length_out   = sum([frequencies[f]*length(codes[f]) for f in keys(frequencies) ])
  return length_entry*9/length_out
end

function compression_ratio_arithmetic(entry, out)
  if length(out) == 0
    return 0
  end
  return (length(entry)*9)/length(out)
end

function print_file(type, code, ratio)
  name = "compresion_"
  title = ""
  if type == "lz"
    title = "Codificación Lempel Ziv"
    name *= "lempel_ziv.txt"
  elseif type == "ac"
    title = "Codificación con Arithmetic Encoding"
    name *= "arithmetic_encoding.txt"
  elseif type == "huffman"
    title = "Codificación con Huffman"
    name *= "huffman.txt"
  end

  text = " $title "
  c_ratio = "Radio de compresión: $ratio "
  content = title * "\n" * join(code) * "\n" * c_ratio
  write(name,content)
end