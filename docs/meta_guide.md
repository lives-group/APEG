# Meta Guide

No APEG, é possível elaborar novas construções durante a execução. Para isso, basta utilizar os recursos meta fornecidos, os quais servem
para construir novas ASTs.

* {| |}:

- MetaPEG:
    Criação de PEGs durante tempo de execução. Ex:

    ```
    s = {| str=(a..z)+ {? str = 'foo' } |}
    ```
    
    Portanto, s passa a estar associado a AST de corpo ...

- MetaRule:
    Utilizado para extender produções com novas alternativas (choice) durante execução. Ex:

    ```
    production = {| new_rule[language g]: str(a..z)+ {? str == 'foo' }; |}
    ```

    Portanto, production passa a estar associado a AST de corpo ...

* ~:

- QuoteValue:
    Criar uma nova construção primaria qualquer. Ex:

    ```
    n = ~1
    ```

    Sendo assim, n esta associado a AST de corpo ('Int 1)

* (| |):

- MetaExpr:
    Criar novas expressões. Ex:

    ```
    exp = (| 10 + 20 * 30 |)
    ```

    Portanto, exp esta associado a AST de corpo (+' 10 (*' 20 30))

- MetaType:
    Utilizar tipos durante tempo de execução. Ex:

    ```
    {t = (| int |);} call<g, t, ret>
    ```

    Portanto, t passa a estar associado a AST de corpo (MetaTyInt t)

* \` \`

- MetaLiteralPEG:
    Criar um novo literal de PEG. Ex:

    ```
    peg_lit = `abcdef`;
    ```

    Portanto, t assoacia-se a AST de corpo (''' abcdef)

* nt<args, ret>

- MetaNonterminal:
    Criar novas chamadas a não-terminais. Sintaxe: <nome, args>. Ex:

    ```
    r = <n, params>
    ```

    Sendo assim, nt está associado a AST de corpo ...

* #:

- Unquote:
    Utilizavel apenas em nível meta. Através dele é possível obter as ASTs armazenadas nos atributos. Veja um exemplo considerando
    o atributo utilizado em MetaExpr:

    ```
    exp_tmp = (| 10 + #exp |)
    ```

    Sendo assim, #exp fornece a AST de exp, que pode ser utilizada em uma nova construção de AST, como demonstrado no código acima.
    O corpo da AST resultande deste exemplo é ...

## Exemplos praticos

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

Aqui é realizada a utilização do operador ~. É possível criar uma expressão AST de corpo ...
para qualquer valor inteiro armazenado na variavel n.
