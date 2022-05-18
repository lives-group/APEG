
# Meta Guide
No APEG, é possível elaborar novas construções durante a execução. Para isso, basta utilizar os recursos meta fornecidos, os quais servem para construir e modificar a AST em tempo de execução.

* {| |}:
	- MetaPEG:
    Criação de PEGs durante tempo de execução. Ex:

	    ```
	    s = {| str=(a..z)+ {? str = 'foo' } |}
	    ```
	  
    - MetaRule:
	    Utilizado para extender ou criar produções com novas alternativas durante execução. Ex:
	    
	    ```
	    production = {| new_rule[language g]: str(a..z)+ {? str == 'foo' }; |}
	    ```
* ~:
	- QuoteValue:
	    Criar uma nova construção primaria qualquer. Ex:

	    ```
	    n = ~1
	    ```

* (| |):
	- MetaExpr:
	    Criar novas expressões. Ex:

	    ```
	    exp = (| 10 + 20 * 30 |)
	    ```

	- MetaType:
	    Utilizar tipos durante tempo de execução. Ex:

	    ```
	    t = (| int |)
	    ```

* \` \`
	- MetaLiteralPEG:
	    Criar um novo literal de PEG. Ex:

	    ```
	    peg_lit = `abcdef`
	    ```
	    
	- MetaNonterminal:
	    Criar novas chamadas a não-terminais. Sintaxe: <nome, args>. Ex:

	    ```
	    r = <n, params>
	    ```

- Unquote (#):
    Utilizavel apenas em nível meta. Através dele é possível obter as ASTs armazenadas nos atributos, ou seja, obtem uma construção meta criada. Veja alguns exemplos:
    ```
    exp_tmp = (| 10 + #exp |)
    ```
    Obtendo a AST que está associada a <em>exp</em> e depois formando uma nova AST que será armazenada em <em>exp_tmp</em>.
    ```
    production = {| name[language g]: #peg_literal+ ; |}
    ```
    Obtendo o valor associado a <em>peg_literal</em> que representa a AST de tipo MetaPeg.
    ```
    ty = (| int |)
    prod_name = "foo"
    production = {| #prod_name[language g, #ty n]: {? true } ; |}
    ```
    Especificando o nome de uma regra a partir do unquote de uma string e definindo um parametro <em>n</em> associado ao tipo meta associado à <em>ty</em>.
- Passagem de tipos meta para não-terminais:
	Assim como os tipos primitivos de APEG, também é possível passar valores do tipo meta para as regras de produção. Para isso, basta declarar o tipo de construção meta que será recebida:
	- <em>peg</em>: recebe um meta peg.
	- <em>expr</em>: recebe uma meta expressão.
	- <em>type</em>: recebe um meta type.

	Veja um exemplo:
	```
	prod[language g, peg pat]: {p = {| 'hello' #pat |};} 
	```
- Compondo gramáticas:
	Um dos principais recursos que APEG oferece é a capacidade de alterar e criar novas regras de produção durante tempo de execução. Para isso, basta utilizar o operador <em><<</em> sobre algum atributo de linguagem juntamente de alguma construção meta peg representando uma produção nova ou ja existente:
	```
	comp[language g]: 'conc' {g = g << {| comp[language g]: {? true }; |};}
	```
	Portanto, g armazena a linguagem na qual comp possui a opção com um constraint true. Visualmente, isto que acontece:
	```
	comp[language g]: {? true } / 'conc' {g = g << {| comp[language g]: {? true }; |};}
	```
	Este exemplo não possui um propósito tão significante porém é útil para demonstrar a utilização do operador <em><<</em>.
## Exemplos práticos
Alguns exemplos de arquivos APEG que utilizam as construções meta:
### meta_int.apeg
```
apeg meta_int;

foo[language g] returns meta_int: 'bar' { meta_int = (| 1 |); };
```

Nesse exemplo, meta_int é uma variavel que armazena a expressão AST de corpo (MetaIntLit 1)

### quote_value.apeg

```
apeg quote_value;

foo[language g] returns meta_int: number<g, n> { meta_int = (| #n |); }
;

number[language g] returns ~n:
    '0' { n = 0; } /
    '1' { n = 1; } /
    '2' { n = 2; } /
    '3' { n = 3; } /
    '4' { n = 4; } /
    '5' { n = 5; } /
    '6' { n = 6; } /
    '7' { n = 7; } /
    '8' { n = 8; } /
    '9' { n = 9; } 
;
```

Aqui é realizada a utilização do operador ~. É possível criar uma expressão AST de corpo (IntLit (~ n)) para qualquer valor inteiro armazenado na variavel n.

### concatenate_letter.apeg
```
apeg concatenateletter;

prog[language g]:
    number<g, r> '[' rep<g, {| _ |}, r, res> {g = g << {| syntax[language g]: #res ; |};} syntax<g> ']'
;

rep[language g, peg pat, int n] returns p:
    {p = pat; i=1;} ({? i < n} {p = {| #pat #p |}; i = i + 1;})*
;

syntax[language g]: {? false }
;

number[language g] returns r:
    digit<g, r> (digit<g, aux> { r = r * 10 + aux; })*
;
```
Esta gramática APEG demonstra a passagem de um meta peg representando um padrão, neste caso qualquer caractere, o qual é concatenado uma quantidade n de vezes especificado no início da entrada. Ex: 3[abc], 7[dddeeef].

É possível perceber a utilização de unquote, composição de regras, criação e passagem de atributos de tipo meta. Este é um exemplo mais robusto e que demonstra bem o potencial de APEG para descrever sintaxes.
