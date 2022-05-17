using DelimitedFiles

include("huffman.jl")
include("huffman2.jl")
include("arithmetic_encoding.jl")

function read_meta(path::String)
    n = 0
    m = 0
    meta_data = readdlm(path, ' ')
    n = meta_data[1, 1]
    m = meta_data[1, 2]
    combins = meta_data[2:end, :] # la primera linea es de el tamaño de la matriz
    length_symbol = 6 #Luego hacemos que se anote tambien en el archivo
    combinaciones = []
    symbols = []
    for i in 1:size(combins)[1]
        linea = combins[i, :]
        linea = [replace(character, "[" => "") for character in linea]
        linea = [replace(character, "]" => "") for character in linea]
        linea = [replace(character, "," => "") for character in linea] # idk
        combinacion = parse.(Bool, linea[1:length_symbol])
        symbol = linea[end]
        push!(combinaciones, combinacion)
        push!(symbols, symbol)
    end
    tabla = Dict(zip(symbols, combinaciones)) # lookup table
    return tabla, n, m
end

function read_compressed(path::String)
    compressed = readdlm(path, ' ')
    return compressed
end

function main()
    lookup_table, n, m = read_meta(ARGS[1])
    compressed = read_compressed(ARGS[2])
    configuration = []
    for symbol in compressed
        comb = lookup_table[symbol]
        for digit in comb
            push!(configuration, digit) # idk
        end
    end
    num_cols = m
    configuration_processed = reshape(configuration, :, num_cols)
    msg = string(compressed...)
    @show msg

    println("Huffman: ")

    freq_table = makefreqdict(msg)

    huffman_tree = create_tree(msg)
    codes = create_codebook(huffman_tree)
    println(freq_table)
    println(codes)
    bitstring = encode(codes, msg)
    println(bitstring)

    println("Arithmetic: ")
    arithmetic(freq_table, msg)
end

main()
