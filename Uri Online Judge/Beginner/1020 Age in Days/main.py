days = int(input())

print(f'{days // 365} ano(s)')
days %= 365

print(f'{days // 30} mes(es)')
days %= 30

print(f'{days} dia(s)')
