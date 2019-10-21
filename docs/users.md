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


```
apeg annotation;

options {
 memoize=true;
}

a:
  b '1'
 /
  b '2'
;

@transient
b:
 'b';
 ```

Dentro da subclasse annotation, assumimos a memoização como verdadeira e
então, criamos duas regras, 'a' e 'b'. A regra 'b' contém apenas o 
caractere "b" e a regra 'a', contendo a regra 'b'seguido do caractere
 "1" ou seguido do caractere "2". Com isso, só será aceita expressões
que sejam "b1" ou "b2".

@transient nao esta claro seu significado.


### Grammar02

O arquvio grammar02.apeg no exmaples:

```
apeg annotation;

options {
 memoize=false;
}

a:
  b '1'
 /
  b '2'
;

@memoize
b:
 'b';
 ```

Neste arquivo são definidas as mesmas regras do arquivo "grammar01.apeg",
porem tendo memoização como falsa.

@memoize não está clara seu significado.

### Grammar03

O arquivo grammar03.apeg no examples:

```
apeg expression;

s[int x, int y, int z, int w, boolean i]:
   
  {i = true ;}
;

/*
 /*
(x < y || z == 2) && (w > 10)
{? (x < y) || (z == 2) && (w > 10) }
*/
 */
 ```
Neste arquivo temos a sub-classe Expression, que contém a regra "s", que 
herda as variaveis inteiras 'x','y','z','w', e a booleana 'i'. Atualizamos 'i'
como verdadeiro, então se testa um expressão verificando se obedece à condição
 lógica (x < y || z == 2) && (w > 10) com a restrição (x < y) || (z == 2) && (w > 10). 

obs: não sabemos se o que está escrito dentro dos comandos "/*" são 
comentários ou instruções.


### Grammar04

O arquivo grammar04.apeg no examples:
```
/*
 /*
Tests semantics of operators without discarding changes on environments.
 */
*/

apeg notDiscardChanges;

header {
  // Some unchecked code here
  void f() {
   return;
  }

a returns[int k] : b<0,k>;
 
b[int x] returns[float x1] :
  { x = x + 1; } '0' { x1 = x; } 
  /
  { x = x + 1; } '1' { x1 = x; }
  /
  { x = x + 1; } '2' { x1 = x; }
  ;
 

c returns[int x] :
  { x = 0; }
  (
    { x = x + 1; }
    '2'
  )*
  ;
  ```


Neste arquivo temos a sub-classe notDiscardChanges, e também temos três regras: 'a','b' e 'c'.

regra 'a': retorna uma variável herdada, inteira "k" valendo regra 'b', com o valor de 0 ou k.

regra 'b': herda um inteiro "x" e retorna "x1", sendo "x1" recebendo "x" e "x" assumindo "0" 
se x=x+1 for 0, "1" se x=x+1 for 1 e "2" se x=x+1 for 2.

regra 'c': herda um inteiro "x", o atualiza com 0 e depois realiza um loop com a expressão
x = x + 1 até "x" valer 2.

### Grammar05

O arquivo grammar05.apeg no examples:

```
apeg testfunction;

functions add concat copyGrammar addRule adapt from {Functions1 AdaptableFunctions};

teste[int x, int y] returns [int z] :
  { z = add(x,y); }
  strN<z*2>
  ;

strN[int n] : ( {? n > 0 } CHAR { n = n - 1; } )* {? n == 0 } !. ;

CHAR : . ;
```

Neste arquivo temos o APEG *testfunction*, em seguida um texto explicativo e logo após o início do código:

É declarado uma regra "teste", que herda duas variáveis inteiras, "x" e "y", retornando um inteiro "z". Sua implementação faz "z" ser atualizado pela a soma de "x" e "y".

Temos também a regra strN, que herda uma variável inteira "n". Sua implementação realiza um loop que restringe "n" para '> 0', e atualizando "n" com a expressão *n=n-1*.Em seguida, restringindo "n" para zero.

obs: regra CHAR e o comando "!." não foi entendido.

### Grammar06

O arquivo grammar06.apeg no examples:

```
apeg adapdatadependent;

options {
  isAdaptable = true;
}

functions from { AdaptableFunctions };

literal3[Grammar g] :
    literal<g> literal<g> literal<g> !. ;

literal[Grammar g] locals[int n, Grammar g1]:
    number<n>
	{ g1 = addRule(copyGrammar(g), concat(concat('strN : ', concatN('CHAR ', n)), ';')) ; }
	'['
	strN<g1>
	']'
    ;

strN[Grammar g]:
    {? false }
	;

CHAR : . ;

number returns[int r] locals[int aux] :
    digit<r> ( digit<aux> { r = r * 10 + aux; } )*
    ;
  
digit returns [int x1] :
  '0'  { x1 = 0; }
  / '1'  { x1 = 1; }
  / '2'  { x1 = 2; }
  / '3'  { x1 = 3; }
  / '4'  { x1 = 4; }
  / '5'  { x1 = 5; }
  / '6'  { x1 = 6; }
  / '7'  { x1 = 7; }
  / '8'  { x1 = 8; }
  / '9'  { x1 = 9; }
  ;
  ```

Neste arquivo temos o APEG *adapdatadependent*. Começa com a regra 'options' que torna o "isAdaptable" como verdadeiro. Em seguida temos temos regras provenientes do "APEG AdaptableFunctions":

literal3: herda uma variável 'g' do tipo Grammar e concatena três regras "literals" usando o 'g' herdado.

literal: herda também uma variável 'g' do tipo 'Grammar', porém temos o uso da função "locals", que herda uma variável 'n' do tipo inteiro e uma variável 'g1' do  tipo Grammar". Na implementação, temos a regra "numbers" herdando 'n', variável herdada 'g1', sendo atualizada recebendo uma regra que copia a gramática de 'g', e em seguida concatena as expressões 'strN: ', 'CHAR ', seguido de 'n' e ';'. No final, utiliza a regra "strN" com o 'g1' herdado.

strN: herda a variável 'g' do tipo Grammar, e sua implementação restringe "falso".

number: Retorna um inteiro 'r', e também se utiliza da regra locals, que herda somente uma variável inteira 'aux'. Sua implementação: utiliza a regra "digit", com o 'r' herdado, realiza um loop, utilizando a regra "digit" com 'aux', e também realizando a atualização de variável "r = r * 10 + aux".

digit: retorna um inteiro 'x1' com o valor entre '0' a '9', se 'x1' valer algo de '0' a '9' respectivamente (se 'x1' for '2', retorna 2).

obs: regra CHAR e o comando "!." não foi entendido.


### Grammar07

O arquivo grammar07.apeg no examples:

```

/*
Tests semantics of choice operator, discarding changes on environments
*/

apeg choiceback;

options {
    envSemantics = discardChangesWhenFail;
}

a returns[int k] : b<0,k> ;
 
b[int x] returns[boolean x1] :
  { x = x + 1; } '0' { x1 = x; }
  /
  { x = x + 1; } '1' { x1 = x; }
  /
  { x = x + 1; } '2' { x1 = x; }
  ;
  ```

Neste arquivo temos o APEG *choiceback*. Começa especificando a regra "options", fazendo 'envSemantics' assumir 'discardChangesWhenFail' e em seguida temos duas regras, 'a' e 'b', sendo elas parecidas com as vistas no "Grammar04".

regra 'a': retorna uma variável herdada, inteira 'k' valendo regra 'b', com o valor de 0 ou k.

regra 'b': herda um inteiro 'x' e retorna um booleano 'x1', retornando '0' ,'1' ou '2'. Caso "x = x + 1" for 0, faz 'x1' receber 'x' que será 0, e assim com '1' e '2'.
 
### Grammar08

O arquivo grammar08.apeg no examples:

```
apeg notDiscardChanges;

header {
  // Some unchecked code here
}

g returns[bool b, int na, int nb, int nc] : 
   a<na> b<nb> c<nc> 
   {b = (a==b) && (b==c)};
 
a returns[int na] :
   'a' a<na> {na=na+1;}
    / 
   'a' {na = 1;}
  ;
 
b returns[int nb] :
   'b' b<nb> {nb=nb+1;}
    / 
   'b' {nb = 1;}
  ;

c returns[int nc] :
   'c' c<nc> {nc=nc+1;}
    / 
   'c' {nc = 1;}
  ;
  ```
  
  Neste arquivo temos o APEG *notDiscardChanges*. Temos três regras, "a","b","c" e "g".
  
  Regra "g": Retorna uma variável booleana 'b', um inteiro 'na', 'nb' e 'nc'. Em seguida, é chamado a regra "a" com o valor 'na',"b" com o valor 'nb', e "c" com valor 'nc'. Após isso, ele atualiza a variável 'bool' com a expressão:
  ```
  {b = (a==b) && (b==c)}
  ```
  ou seja, retornando true, caso os valores retornados das regras forem iguais, e false, caso não.
  
  Regra "a": Retorna uma variável inteira 'na'. Sua implementação: dá de saída o carácter '*a*' seguido de um número 'na'. Se for diferente de '1', atualiza-se 'na' somando 1, caso igual a '1', atualiza-se com '1'.
  
  Regras "b" e "c" fazem o memso da regra "a", só com o 'nb e 'nc' respectivamente.
  
  obs: Na regra 'a' nao foi entendido se ele escolhe uma opção baseada em 'na' ou se a primeira opção é prioritária. 

### Grammar09

O arquivo grammar09.apeg no examples:

```
apeg notDiscardChanges;


s:
  a<> e<>
 /
  b<> 
 /
  c<>
;

a:
 d<>
 /
 c<>
;

b:
 'abc' 
 /
 'abb'
 ;

c:
 't'
 / 
  d<>
 ;

d:
  'ab'
 ;

e: 
  c<> d<>
 ; 
```
Neste arquivo temos o APEG *notDiscardChanges*. Nele temos as regas "s","a","b","c","d" e "e"

Regra "s":Chama a regra "a" com parâmetro vazio seguido da regra "e" também seguido de parâmetro vazio, ou chama a regra "b" com parâmetro vazio, ou da regra "c" com parâmetro vazio.

Regra "a": Chama a regra "d" com parâmetro vazio ou chama a regra "c" com parâmetro vazio.

Regra "b": Chama a regra "a" seguido da regra "b" e "c", chama a regra "a" seguido da regra "b" e "b" de novo.

Regra "c": Tem como saída o carácter 't' ou a regra "d" com parâmetro vazio.

Regra "e": Chama a regra "c" com parâmetro vazio em seguida chama a regra "d" com parâmetro vazio.

### Grammar10

O arquivo grammar10.apeg no examples:

```
apeg notDiscardChanges;


s: a1<>  t3<> a6<> a7<>
;

t1: (a4<>)*
;

t2: a3<> t1<> a5<>
;

t3: a2<> 
   /
   t2<>
;

a1: 'a'
;

a2: 'b'
;

a3: 'c'
;

a4: 'd'
;

a5: 'e'
;

a6: 'f'
;

a7: 'g'
;
```
Neste arquivo temos o APEG *notDiscardChanges*. Nele temos as regas "s","t1","t2","t3","a1","a2","a3","a4","a5","a6","a7".

Regra "s": Chama a regra "a1" com parâmtetros vazio, seguido das regras "t3", "a6" e "a7", também com parâmetros vaizos.

Regra "t1": Realiza um loop com a regra "t4" com parâmetros vazio.

Regra "t2": Chama a regra "a3" com parâmtetros vazio, seguido das regras "t1" e "a5", também com parâmetros vaizos.

Regra "t3": Chama a regra "a2" ou a regra "t4" com parâmetros vazios.

Regra "a1": Tem como saída o carácter 'a'. As regras "a2" até "a7" têm como saída o intervalo das letras 'b' até 'g'("a2"->b,"a3"->c,...). 

obs: não se sabe quando que loop da regra "t1" se encerra.

### Grammar11

O arquivo grammar11.apeg no examples:

```
apeg notDiscardChanges;


s: a1<> (a2<> / a4<>)* &a3<> b3<> 'e'+ b1<>;

a2 : 'x' / 'y'?'www' / 'nada' 'aver' a1<> / b0<> ;

b0 : '(' . ')' ;
b2 : .;

b1 : 'm'+ ;

b3: &'xyz' ;

a1: 'a' / 'b'* / !'c' ;

a3: !'zzz';

a4: 'cd'?;
```
