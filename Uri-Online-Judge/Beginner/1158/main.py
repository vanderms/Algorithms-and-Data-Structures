

n = int(input())

for i in range(n):
    x, y = [int(a) for a in input().split()]
    total = 0
    x = x if x % 2 == 1 else x + 1
    while y > 0:
        total += x
        x+= 2
        y-= 1
    print(total)
