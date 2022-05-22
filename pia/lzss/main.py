from encoder import Encoder
import argparse

def main(in_file, out_file, mode):

    encoder = Encoder()
    if mode == 'encode':
        encoder.encode(in_file, out_file)
    else:
        encoder.decode(in_file, out_file)

    return True



