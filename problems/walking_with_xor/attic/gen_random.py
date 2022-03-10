#!/usr/bin/env python3

# Sample test generator


from random import seed, randint
import sys


def number_from(desc):
    """
    "{number}": Return the same number
    "{lo}:{hi}": Return a random number in the closed interval [lo, hi]
    "~{number}": Return a random number in the interval [number // 2, number]
    """

    if '=' in desc:
        _, desc = desc.split('=')

    if ':' in desc:
        lo, hi = map(int, desc.split(':'))
        return randint(lo, hi)

    elif desc.startswith('~'):
        number = int(desc[1:])
        return randint(number // 2, number)

    else:
        return int(desc)


class Input:
    def __init__(self):
        self.cur_arg = 1
        seed(self.next())

    def next(self):
        ix = self.cur_arg
        self.cur_arg += 1
        return sys.argv[ix]


def main(cin):
    mode = cin.next()

    A = []
    if mode == 'random':
        n = number_from(cin.next())
        k = number_from(cin.next())
        B = number_from(cin.next())

        A = [randint(0, 2**B - 1) for _ in range(n)]

    print(n, k)
    print(' '.join(map(str, A)))


if __name__ == '__main__':
    main(Input())
