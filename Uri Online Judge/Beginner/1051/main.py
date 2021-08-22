salary = float(input())
tax = 0

if salary > 4500:
    tax += (salary - 4500) * 0.28
    salary = 4500.00    

if salary > 3000:
    tax += (salary - 3000.00) * 0.18
    salary = 3000.00    

if salary > 2000:
    tax += (salary - 2000.00) * 0.08       

if salary <= 2000.00:
    print('Isento')
else:
    print(f'R$ {tax:.2f}')