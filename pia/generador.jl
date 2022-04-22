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
        top_iob = rand(Bool, 12)
        bot_iob = rand(Bool, 12)
        clb = []
        for clbs in 1:n
            clb_n = rand(Bool, 12)
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
    while end_range < size_vec
        range = start_range:end_range
        digits = vec_conf[range]
        if digits ∉ combinaciones
            push!(combinaciones, digits)
            push!(symbols, start_symbol)
            push!(symbolized_conf, start_symbol)
            if start_codepoint == 0x0000005a # los chars de enmedio no son simbolos validos
                start_codepoint = 0x00000061
            end
            start_codepoint = start_codepoint + 1
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
    open("config4.txt", "w") do io
        writedlm(io, conf)
        writedlm(io, combinaciones)
        writedlm(io, symbolized_conf)
    end
end

generate()
