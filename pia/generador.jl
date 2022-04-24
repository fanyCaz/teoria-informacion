using DelimitedFiles

function generate()
    print("Ingrese el número de CLBs por frame: ")
    n = parse(Int, readline())
    print("Ingrese el número de frames: ")
    m = parse(Int, readline())
    # un CLB mide 18 bits, un IOB 18 tambien
    # a fuerzas tenemos un bloque TOP de IOBs y uno BOTTOM
    configuracion = []

    for frame in 1:m
        top_iob = rand(Bool, 18)
        bot_iob = rand(Bool, 18)
        clb = []
        for clbs in 1:n
            clb_n = rand(Bool, 18)
            push!(clb, clb_n)
        end
        marco = vcat(top_iob, clb..., bot_iob)
        push!(configuracion, marco)
    end

    conf = hcat(configuracion...)

    vec_conf = vec(conf) # para pasarlo a un vector
    size_vec = length(vec_conf)
    length_symbol = 6 # puede ser 9
    start_range = 1
    end_range = length_symbol
    combinaciones = []
    symbols = []
    symbolized_conf = []
    start_symbol = 'A' # ascii codepoint de 0x00000041, esto nos servirá más adelante
    start_codepoint = codepoint('A')
    # el codepoint de la Z es 0x0000005a
    # el codepoint de 'a' es 0x00000061
    # quizas empezar en 0x21? el codepoint del !
    # hasta 0x7f el codepoint de ~
    # son 94 combinaciones posibles
    # actualmente solo tenemos 50
    while end_range <= size_vec
        range = start_range:end_range
        digits = vec_conf[range]
        if digits ∉ combinaciones
            push!(combinaciones, digits)
            push!(symbols, start_symbol)
            push!(symbolized_conf, start_symbol)
            if start_codepoint == 0x0000005a # los chars de enmedio no son simbolos validos
                start_codepoint = 0x00000061
            else
                start_codepoint += 1
            end
            start_symbol = Char(start_codepoint)
        else
            for i in 1:length(combinaciones)
                if digits == combinaciones[i]
                    symbol = symbols[i]
                    push!(symbolized_conf, symbol)
                end
            end
        end
        start_range += length_symbol
        end_range += length_symbol
    end
    conf_int = Int.(conf)
    open("config_raw.txt", "w") do io
        writedlm(io, conf_int)
    end
    symbols = hcat(symbols)
    symbolized_conf = hcat(symbolized_conf)
    comb_int = [Int.(vector) for vector in combinaciones]
    open("config_metadata.txt", "w") do io
        writedlm(io, [n m], ' ')
        writedlm(io, [comb_int symbols], ' ')
    end
    open("config_compressed.txt", "w") do io
        writedlm(io, [symbolized_conf], ' ')
    end
    vec_int_conf = Int.(vec_conf)
end

generate()
