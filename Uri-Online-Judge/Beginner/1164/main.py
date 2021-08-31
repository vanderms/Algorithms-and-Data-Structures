

n = int(input())
PERFECT_NUMBERS = [6, 28, 496, 8128, 33550336, 8589869056]


for _ in range(n):
    x = int(input())
    if x in PERFECT_NUMBERS:
        print(f'{x} eh perfeito')
    else:
        print(f'{x} nao eh perfeito')
