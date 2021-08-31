_, number, price = input().split()
total = int(number) * float(price)

_, number, price = input().split()
total += int(number) * float(price)

print(f'VALOR A PAGAR: R$ {total:.2f}')
