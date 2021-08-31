prices = [0.0, 4.0, 4.50, 5.00, 2.00, 1.50]

product, quantity = [int(x) for x in input().split()]

total = prices[product] * quantity

print(f'Total: R$ {total:.2f}')
