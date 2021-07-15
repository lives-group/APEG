import matplotlib.pyplot as plt
import matplotlib.dates as mdates
import numpy as np

# Caso adicione/remova um parser, adicionar/remover o nome referente nesta lista
linguagem = ["closure", "closure_xml", "java_xml", "pair", "pair_closure",
             "pair_closure_xml", "pair_xml", "xml"]

Y = np.zeros(len(linguagem))

java = []
java_value = []

cont = 0


dataset = open('./reports/Saida.csv', 'r')

for line in dataset:
    line = line.strip()  # Tira o \n no final da linha
    x, y, z = line.split(';')  # Quebra a linha com o marcador ;

    cont += 1

    # Caso adicione/remova um parser, adicionar/remover o nome
    # referente nesstes elif's
    if x in linguagem:
        if x == "closure":
            Y[0] += int(y)
        elif x == "closure_xml":
            Y[1] += int(y)
        elif x == "java_xml":
            Y[2] += int(y)
        elif x == "pair":
            Y[3] += int(y)
        elif x == "pair_closure":
            Y[4] += int(y)
        elif x == "pair_closure_xml":
            Y[5] += int(y)
        elif x == "pair_xml":
            Y[6] += int(y)
        elif x == "xml":
            Y[7] += int(y)

    # elif para as instancias Java
    elif x in java:
        java_value[java.index(x)] += int(y)
    else:
        java.append(x)
        java_value.append(int(y))

dataset.close()


for i in range(len(linguagem)):
    Y[i] /= cont

for j in range(len(java_value)):
    java_value[j] /= cont


plt.bar(java, java_value)

plt.title('Tempo de Execução das Instancias Java')
plt.xlabel('Nome arquivo')
plt.ylabel('Tempo')

# plt.xticks(rotation=90)
# plt.xticks(rotation=45, ha='right')
plt.tick_params(axis='x', labelrotation=45)
plt.subplots_adjust(bottom=0.35)

plt.show()

plt.bar(linguagem, Y)

plt.title('Tempo de Execução das demais instancias')
plt.xlabel('Nome arquivo')
plt.ylabel('Tempo')

# plt.xticks(rotation=90)
# plt.xticks(rotation=45, ha='right')
plt.tick_params(axis='x', labelrotation=45)
plt.subplots_adjust(bottom=0.35)

plt.show()
