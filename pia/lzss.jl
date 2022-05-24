using PythonCall

function lempel_ziv_encode(msg)


    # hack to add module from path

    sys = pyimport("sys")
    sys.path.append("lzss\\")
    lempel = pyimport("main")
    
    open("temp.txt", "w") do file
        write(file, msg)
    end


    lempel.main("temp.txt", "out.bin", "encode")


    Rc = filesize("temp.txt") * 1.125 / filesize("out.bin")
    # rm("temp.txt")
    return Rc
    
    
end


function lempel_ziv_decode(input_file)

    sys = pyimport("sys")
    sys.path.append("lzss\\")
    lempel = pyimport("main")

    lempel.main(input_file, "salida.txt", "decode")
end