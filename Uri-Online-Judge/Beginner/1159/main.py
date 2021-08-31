
while True:
    x = int(input())
    if x == 0:
        break
    x = x if x % 2 == 0 else x + 1
    total = 0
    for i in range(5):
        total += x
        x+= 2
    print(total)