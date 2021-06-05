def solve():
    a, b, c = [float(x) for x in input().split()]
    
    delta = b ** 2 - 4 * a * c
    
    if a == 0 or delta < 0:
        print("Impossivel calcular")
        return
    
    r1 = (-b + delta ** 0.5) / (2 * a)
    r2 = (-b - delta ** 0.5) / (2 * a)
    print(f'R1 = {r1:.5f}')
    print(f'R2 = {r2:.5f}')


solve()

