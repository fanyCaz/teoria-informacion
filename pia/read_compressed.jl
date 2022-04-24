using DelimitedFiles

function read_raw(path::String)
    conf = readdlm(path, '\t', Bool, '\n')
    return conf
end

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
    println(length(compressed))
    return compressed
end


function main()
    original_conf = read_raw(ARGS[1])
    lookup_table, n, m = read_meta(ARGS[2])
    compressed = read_compressed(ARGS[3])
    configuration = []
    for symbol in compressed
        comb = lookup_table[symbol]
        println(comb)
        for digit in comb
            push!(configuration, digit) # idk
        end
    end
    num_cols = m
    configuration_processed = reshape(configuration, :, num_cols) # lossless config original
    println(configuration_processed == original_conf)
end

main()
