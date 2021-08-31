

n = int(input())

for _ in range(n):
    x = int(input())
    if x == 1:
        print('1 nao eh primo')
    elif x == 2:
        print('2 eh primo')
    elif x % 2 == 0:
        print(f'{x} nao eh primo')
    else:
        eprimo = True
        for i in range(3, int((x) ** 0.5) + 2, 2):
            if x % i == 0:
                eprimo = False
        
        if eprimo:
            print(f'{x} eh primo')
        else:
            print(f'{x} nao eh primo')