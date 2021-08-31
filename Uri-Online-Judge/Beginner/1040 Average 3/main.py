scores = [float(x) for x in input().split()]
weight = [2, 3, 4 ,1]

for i in range(4):
    scores[i] *= weight[i]

average = sum(scores) / sum(weight)

print(f'Media: {average:.1f}')

if average >= 7.0:
    print('Aluno aprovado.')
elif average < 5.0:
     print('Aluno reprovado.')
else:
    print('Aluno em exame.')
    exam = float(input())
    print(f'Nota do exame: {exam:.1f}')
    average = (average + exam) / 2
    if average >= 5.0:
        print('Aluno aprovado.')
    else:
        print('Aluno reprovado.')
    print(f'Media final: {average:.1f}')
