seconds = int(input())
hours = seconds // (60 * 60)
seconds %= 3600
minutes = seconds // 60
seconds %= 60

print(f'{hours}:{minutes}:{seconds}')