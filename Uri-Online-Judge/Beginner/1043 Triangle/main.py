original = [float(x) for x in input().split()]
sorted_list = sorted(original)

if sorted_list[2] >= sorted_list[1] + sorted_list[0]:
    a, b, c = original
    area = (a + b) * (c / 2)
    print(f'Area = {area:.1f}')
else:
    print(f'Perimetro = {sum(original):.1f}')


