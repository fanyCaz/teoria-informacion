import bitio
from helpers import CircularBuffer, Reference, SmartOpener

class Encoder():

    DICTIONARY_SIZE = 4095 #12 bits for position pointer- max value is 4095
    MIN_MATCH_SIZE = 2
    MAX_MATCH_SIZE = 15 #4 bits for length - max value is 15
    ENCODED_FLAG = 0b1

    def __init__(self):
        pass

    def _byte2int(self, byte) -> int:
        assert len(byte) == 1
        return int.from_bytes(byte, byteorder="big")

    def _read_next(self, buffer: CircularBuffer, dictionary: CircularBuffer, infile) -> bool:
        dictionary.put_byte(buffer.get_byte_at(0))
        last_byte = infile.read(1)
        if last_byte:
            buffer.put_byte(self._byte2int(last_byte))
            return True
        else: # EOF?
            buffer.pop()
            return buffer.get_fill() > 0

    def encode(self, inpath, outpath):
        ocho_veces = 8
        dictionary = CircularBuffer(self.DICTIONARY_SIZE)
        buffer = CircularBuffer(self.MAX_MATCH_SIZE)
        with SmartOpener.smart_read(inpath) as infile, SmartOpener.smart_write(outpath) as outfile:
            with bitio.BitWriter(outfile) as writer:
                for i in range(0, self.MAX_MATCH_SIZE):
                    buffer.put_byte(self._byte2int(infile.read(1)))
                is_there_something_to_read = True
                while is_there_something_to_read:
                    longest_match_ref = dictionary.get_longest_match(self.MAX_MATCH_SIZE, buffer)
                    if ocho_veces >= 0:
                        caracteres = [chr(numero) for numero in dictionary._buffer]
                        print(caracteres)
                        ocho_veces = ocho_veces - 1
                    if (longest_match_ref.get_length() > self.MIN_MATCH_SIZE):
                        print(longest_match_ref)
                        writer.writebits(self.ENCODED_FLAG, 1)
                        writer.writebits(longest_match_ref.get_bits(), 16)
                        for i in range(0, longest_match_ref.get_length()):
                            is_there_something_to_read = self._read_next(buffer, dictionary, infile)
                    else:
                        writer.writebits(0, 1)
                        writer.writebits(buffer.get_byte_at(0), 8)
                        is_there_something_to_read = self._read_next(buffer, dictionary, infile)

    def decode(self, inpath, outpath):
        dictionary = CircularBuffer(self.DICTIONARY_SIZE)
        with SmartOpener.smart_read(inpath) as infile, SmartOpener.smart_write(outpath) as outfile:
            with bitio.BitReader(infile) as reader, bitio.BitWriter(outfile) as writer:
                while True:
                    bit = reader.readbits(1)
                    if (bit == self.ENCODED_FLAG):
                        reference_pos_bits = reader.readbits(12)
                        reference_length_bits = reader.readbits(4)
                        reference = Reference(reference_pos_bits, reference_length_bits)
                        if reference.get_length() <= self.MIN_MATCH_SIZE:
                            raise Exception("No referencia: " + str(reference) + "pos: " + str(infile.tell()))
                        for byte in dictionary.get_match(reference.get_pos(), reference.get_length()):
                            dictionary.put_byte(byte)
                            writer.writebits(byte, 8)
                        if not reader.read:
                            break
                    else:
                        byte = reader.readbits(8)
                        if not reader.read:
                            break
                        dictionary.put_byte(byte)
                        writer.writebits(byte, 8)