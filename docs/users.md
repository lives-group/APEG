# Users Guide

## A Fist APEG Example



### Grammar 01

O arquivo  grammar01.apeg no examples:

Dentro da subclasse annotation, assumimos a memoização como verdadeira e
então, criamos duas regras, 'a' e 'b'. A regra 'b' contém apenas o 
caractere "b" e a regra 'a', contendo a regra 'b'seguido do caractere
 "1" ou seguido do caractere "2". Com isso, só será aceita expressões
que sejam "b1" ou "b2".

@transient nao esta claro seu significado.


### Grammar02

O arquvio grammar02.apeg no exmaples:

Neste arquivo são definidas as mesmas regras do arquivo "grammar01.apeg",
porem tendo memoização como falsa.

@memoize não está clara seu significado.

### Grammar03

O arquivo grammar03.apeg no examples:

Neste arquivo temos a sub-classe Expression, que contém a regra "s", que 
herda as variaveis inteiras 'x','y','z','w', e a booleana 'i'. Atualizamos 'i'
como verdadeiro, então se testa um expressão verificando se obedece à condição
 lógica (x < y || z == 2) && (w > 10) com a restrição (x < y) || (z == 2) && (w > 10). 

obs: não sabemos se o que está escrito dentro dos comandos "/*" são 
comentários ou instruções.


### Grammar04

O arquivo grammar04.apeg no examples:

Neste arquivo temos a sub-classe notDiscardChanges, e também temos três regras: 'a','b' e 'c'.
regra 'a': retorna uma variável herdada, inteira "k" valendo regra 'b', com o valor de 0 ou k.
regra 'b': herda um inteiro "x" e retorna "x1", sendo "x1" recebendo "x" e "x" assumindo "0" 
se x=x+1 for 0, "1" se x=x+1 for 1 e "2" se x=x+1 for 2.]
regra 'c': herda um interio "x", o autualiza com 0 e depois realiza um loop com a expressão
x = x + 1 até "x" valer 2.


### Grammar05
