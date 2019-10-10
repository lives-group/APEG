# Users Guide

## A First APEG Example

An Adaptable Parsing Expression Grammar (APEG) is an extension of
Parsing Expression Grammars (PEG), which includes operators and the
notion of *syntactic attributes*.
Syntactic attributes are values passed through nonterminals
and used during the parsing process.
The grammar above illustrates the use of values to dictate the
parsing process.

```
apeg datadependent;

literal: number<n> "[" strN<n> "]" !.;

strN[int n]: ({? n > 0} CHAR {n = n-1;})* {? n == 0};

number returns[int n]: digit<n> (digit<x> {n = n*10 + x;})*;

digit returns[int x]:
   "0" {x = 0;}
 / "1" {x = 1;}
 / "2" {x = 2;}
 / "3" {x = 3;}
 / "4" {x = 4;}
 / "5" {x = 5;}
 / "6" {x = 6;}
 / "7" {x = 7;}
 / "8" {x = 8;}
 / "9" {x = 9;}
;
 
CHAR: .;
```

This grammar starts, in the first line, telling that it is an APEG grammar named _datadependent_.
Every APEG grammar begins with this piece of code, in which _\<name\>_ is the grammar name given
by the user:
```
apeg <name>;
```



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
se x=x+1 for 0, "1" se x=x+1 for 1 e "2" se x=x+1 for 2.

regra 'c': herda um inteiro "x", o atualiza com 0 e depois realiza um loop com a expressão
x = x + 1 até "x" valer 2.

### Grammar05

O arquivo grammar05.apeg no examples:

Neste arquivo temos o APEG *testfunction*, em seguida um texto explicativo e logo após o início do código:

É declarado uma regra "teste", que herda duas variáveis inteiras, "x" e "y", retornando um inteiro "z". Sua implementação faz "z" ser atualizado pela a soma de "x" e "y".

Temos também a regra strN, que herda uma variável inteira "n". Sua implementação realiza um loop que restringe "n" para '> 0', e atualizando "n" com a expressão *n=n-1*.Em seguida, restringindo "n" para zero.

obs: regra CHAR e o comando "!." não foi entendido.

### Grammar06

Neste arquivo temos o APEG *adapdatadependent*. Começa com a regra 'options' que torna o "isAdaptable" como verdadeiro. Em seguida temos temos regras provenientes do "APEG AdaptableFunctions":

literal3: herda uma variável 'g' do tipo Grammar e concatena três regras "literals" usando o 'g' herdado.

literal: herda também uma variável 'g' do tipo 'Grammar', porém temos o uso da função "locals", que herda uma variável 'n' do tipo inteiro e uma variável 'g1' do  tipo Grammar". Na implementação, temos a regra "numbers" herdando 'n', variável herdada 'g1', sendo atualizada recebendo uma regra que copia a gramática de 'g', e em seguida concatena as expressões 'strN: ', 'CHAR ', seguido de 'n' e ';'. No final, utiliza a regra "strN" com o 'g1' herdado.

strN: herda a variável 'g' do tipo Grammar, e sua implementação restringe "falso".

number: Retorna um inteiro 'r', e também se utiliza da regra locals, que herda somente uma variável inteira 'aux'. Sua implementação: utiliza a regra "digit", com o 'r' herdado, realiza um loop, utilizando a regra "digit" com 'aux', e também realizando a atualização de variável "r = r * 10 + aux".

digit: retorna um inteiro 'x1' com o valor entre '0' a '9', se 'x1' valer algo de '0' a '9' respectivamente (se 'x1' for '2', retorna 2).

obs: regra CHAR e o comando "!." não foi entendido.


### Grammar07

Neste arquivo temos o APEG *choiceback*. Começa especificando a regra "options", fazendo 'envSemantics' assumir 'discardChangesWhenFail' e em seguida temos duas regras, 'a' e 'b', sendo elas parecidas com as vistas no "Grammar04".

regra 'a': retorna uma variável herdada, inteira 'k' valendo regra 'b', com o valor de 0 ou k.

regra 'b': herda um inteiro 'x' e retorna um booleano 'x1', retornando '0' ,'1' ou '2'. Caso "x = x + 1" for 0, faz 'x1' receber 'x' que será 0, e assim com '1' e '2'.
 
### Grammar08
