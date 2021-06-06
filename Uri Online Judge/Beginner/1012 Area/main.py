pi = 3.14159
a, b, c = [float(x) for x in input().split()]
triangle = (a * c) / 2
circle = pi * (c ** 2)
trapezium = ((a + b) * c) / 2
square = b ** 2
rectangle = a * b
print(f"TRIANGULO: {triangle:.3f}")
print(f"CIRCULO: {circle:.3f}")
print(f'TRAPEZIO: {trapezium:.3f}')
print(f"QUADRADO: {square:.3f}")
print(f'RETANGULO: {rectangle:.3f}')
