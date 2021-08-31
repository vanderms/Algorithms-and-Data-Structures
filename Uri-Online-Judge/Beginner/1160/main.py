

n = int(input())

for _ in range(n):
    line = input().split()
    pa, pb = int(line[0]), int(line[1])
    ga, gb = 1 + (float(line[2]) / 100), 1 + (float(line[3]) / 100)

    
    i = 1
    while i <= 101:
        pa = int(pa * ga)
        pb = int(pb * gb)
        if pa > pb:
            break
        i+= 1
    
    if i >= 101:
        print('Mais de 1 seculo.')
    else:
        print(f'{i} anos.')
