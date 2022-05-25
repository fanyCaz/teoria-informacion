using DelimitedFiles, PythonCall

include("huffman.jl")
include("huffman2.jl")
include("arithmetic_encoding.jl")
include("utils.jl")
include("lzss.jl")

function read_meta(path::String)
    n = 0
    m = 0
    meta_data = readdlm(path, ' ')
    n = meta_data[1, 1]
    m = meta_data[1, 2]
    combins = meta_data[2:end, :] # la primera linea es de el tamaño de la matriz
    length_symbol = 9 #Luego hacemos que se anote tambien en el archivo
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

    configuration_processed = Int.(configuration_processed)

    conf_path = "config_restaurada_$n" * "_$m" * ".txt"
    open(conf_path, "w") do io
        writedlm(io, configuration_processed)
    end
    msg = string(compressed...)
    @show msg

    println("Huffman: ")

    freq_table = makefreqdict(msg)
    println("frecuencias $freq_table")
    huffman_tree = create_tree(msg)
    codes = create_codebook(huffman_tree)

    println("codes: $codes")
    bitstring = encode(codes, msg)
    println(bitstring)
    c_ratio = compression_ratio(freq_table, codes)

    println("Compression ratio for Huffman: $c_ratio")
    print_file("huffman", bitstring, c_ratio)
    

    mensaje_descomprimido_huff = decode(huffman_tree, bitstring)
    

    println("Arithmetic: ")
    println(msg)

    collected_keys = []

    for l in unique(msg)
        push!(collected_keys,Pair(l,freq_table[l]))
    end

    sorted_alphabet = collected_keys
    levels = []
    code = ""
    for idx = firstindex(msg):lastindex(msg)
        try
            println("Analizando secuencia: $(msg[1:idx])")
            out,alpha,beta = arithmetic(freq_table, msg[1:idx], sorted_alphabet)
            code = out
            c_ratio = compression_ratio_arithmetic(msg[1:idx], code)
            println( readeable_code(code) )
            push!(levels,(beta+alpha)/2)
        catch
        end
    end
    c_ratio = compression_ratio_arithmetic(msg, code)
    println("Compression ratio for $msg with Arithmetic Encoding: $c_ratio")

    print_file("ac", code, c_ratio)
    println("LZSS:")


    lz_rc = lempel_ziv_encode(msg)

    println("Compression ratio for LZSS: $lz_rc")

    # comprobar

    lempel_ziv_decode("out.bin")

    mensaje_descomprimido_lz = read("salida.txt", String)


    println("COMPROBACIONES")



    @show mensaje_descomprimido_huff

    @show mensaje_descomprimido_lz





    
end

main()
