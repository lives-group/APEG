<h1 align="center">Relatório Manipulações Rats</h1>

## Autores

👤 **Deiverson**

* Github:[@DeiversonMAP](https://github.com/DeiversonMAP) 
* Linkedin: 

👤 **Pedro Henrique**

* Github: [@PHenriqueCEC](https://github.com/PHenriqueCEC)
* LinkedIn: [Pedro Henrique](https://www.linkedin.com/in/pedro-henrique-77baa01a9/)

👤 **Raquel**

* Github:[@rgoulart8](https://github.com/rgoulart8)
* LinkedIn: [Raquel Goulart](https://br.linkedin.com/in/rgoulart8)


# Como rodar o projeto
**Certifique-se de ter o MinGW instalado**

1. Local de execução
```
Abra o diretório ManipulacoesRats
```

2. Digite o comando abaixo:
```
make run
```

## Sobre o programa

```
A pasta raiz contem um programa para avaliarmos o tempo de execução de análise sintática usando a biblioteca rats.jar. O programa foi feito utilizando a linguagem Java onde o dividimos em algumas classes. Essas classes foram utilizadas de modo a deixar o programa mais organizado, visando sua melhor compreensão e entendimento. Além disso, foi utilizado alguns conceitos trabalhados na disciplina de Orientação a Objetos, como Interfaces.

Após a execução do programa foi gerado um arquivo de saida no formato CSV contendo os dados do tempo de execução da análise sintática. Posto a isso, foi implementado um código em Python usando uma biblioteca chamada Matplotlib. Essa biblioteca é responsável por gerar um gráfico com os dados do arquivo CSV a fim de facilitar a visualização dos testes feitos.

```

## Organização das pastas

```
Nesse programa dispusemos de 5 diretórios:

```

• Instancias:
```
Diretório onde se encontra todos os arquivos para teste. Dentro desse diretório organizamos e separamos todos os arquivos em diretórios nomeados com o nome do respectivo Parser a ser executado. 
```

• libs
```
Diretório onde se encontra a biblioteca rats.jar(Caso esse diretório esteja vazio, ou não exista, ele deve ser criado e colocado o arquivo dentro do mesmo).

```

• parsers

```
Esse diretório possui uma pasta rats. Dentro dessa pasta (perguntar a eles sobre o rats)
Dentro do diretório rats encontra-se os parsers separados por diretórios que possuem os nomes
relacionados as sintaxes a serem analisadas.

```

• reports
```
Diretório onde se encontra a saída de texto(Saida.csv) que contêm os dados obtidos dos testes realizados durante a execução do programa.

```

• Tester

```
Diretório onde se encontram a classe Main.java e as demais classes implementadas do programa. Nela contém também o código em Python responsável por gerar o gráfico para análise contendo os dados de saída(encontrado em reports/Saida.csv).

```

## Classes implementadas
• Main.java
```
A classe Main.java é a classe principal do programa. É a classe utilizada para rodar o projeto. Nela passamos o endereço da pasta instância onde chama o parser(classe Run) específico para cada diretório nesta pasta. 

```

• Runner.java
```
É uma Interface onde todas as nossas classes Run(RunPair.java, RunXml.java, RunClosure.java,..), com exceção da RunJava.java, foram extendidas.

```

• Run(RunPair.java, RunXml.java, RunClosure.java,...)

```
Essas classes são responsáveis por coletar o tempo de verificação da sintaxe dos programas. Após a coleta, é utilizado o método AddLi() (Método da classe CSVTable.java), onde passamos esse tempo de verificação e o nome do diretório para ser inserido na tabela.

```

• CSVTable.java
```
Classe utilizada por criar a tabela CSV. Nessa classe fazemos a inserção em linha e coluna do nome da linguagem e o tempo de execução, respectivamente. Além do mais, encontramos nessa classe outros métodos que auxiliam na criação/manipulação da tabela CSV.

```

## Observações
Algumas alterações  que podem ser feitas no programa estarão comentadas no próprio código
do projeto.
