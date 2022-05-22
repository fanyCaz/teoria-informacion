struct letra
    char::String
    posicion::Int64
end

"""
    boyer_moore(patron::String, texto::String)
Aplica el algoritmo de Boyer-Moore para encontrar un patrón `P` dentro de un texto `T`

"""
function boyer_moore(P::String, T::String)
    m = length(P)
    n = length(T)
    bad_table = bad_char_table(T, n)

end

"""
    bad_char_table(T::String)
Retorna la tabla de caracteres malos que contiene cuantas alineaciones se deben de saltar
en caso de encontrarse con un caracter malo

"""
function bad_char_table(T::String, n::Int64)
    table = Dict()
    T_cop = reverse(T)
    for char in T_cop
        table[char] = findlast(char, T_cop) - 1
        # value = length (de T) - index (del char) - 1
        # en este caso decidí reversear el string para no hacer length de T
    end
    table["wildcard"] = n
    # cuando hay cualquier otro char, debemos de shiftear TODA la longitud de T
    return table
end

function good_suffix_table(T::String, n::Int64)
    table = Dict()
    for (char, index) in enumerate(T)
        dict[index] = letra(char, )
    end

end
