# APEG Manual

This file contains all information about APEG syntax. If you haven't seen our "Getting Started" tutorial, we highly recommend the read in order to get more comfortable with the tool before seeing the manuals. APEG stands for Attribute Parsing Expression Grammar and consists of a grammar that supports attributes and extensions during runtime. Due to this resources, APEG is very useful for specifying the syntax of extensible languages.

On this manual we introduce the most basic concepts with code examples as well as the most robust resources which are presented later in this document.




### APEG Grammars

Every APEG grammar needs to start with the word *apeg* followed by a name (most of the time we use the same as the file):

```
apeg beautifulGrammar;
```



### Types

Every value in APEG has a type associated to it. You can have the following types which, in terms of implementation, are represented in the tool as Java Types:

* int: integer numbers. Ex: 10, 20, -500, 1000.
* float: real numbers (floating point values). Ex: 10.50, 20.22, 20.2222.
* boolean: true or false.
* char: one character. Ex: 
* string: sequence of characters. Ex: 'tool', 'grammar', 'extensible language'.
* map: an associative array that maps an string to a value (APEG maps only associate to a value of one type). Ex: {: 'abc' -> 10, 'def' -> 20 :}, {: 'firstRule': g, 'secondRule': g1 :}.
* list: a list of values of only one type. Ex: [10, 20, 30], [{: 'abc' -> 10 :}, {: 'def' -> 20 :}].

* language: an APEG attribute that represents a composed grammar.



### Type ranges

* int:  *-2147483648 to 2147483647*

* float: *3.40282347 x 10<sup>38</sup> to 1.40239846 x 10<sup>-45</sup>*
* boolean: <i>TRUE</i> or <i>FALSE</i>
* char: <i>\0000</i> to <i>\ffff</i>



### Attributes

Attributes are like variables on any programming language. To store values on variables, you can use the {} operators to specify an assignment. See some examples of variables updates below:

```
entry[language g]: {n = 10; s = {: 'abc' -> 20 :}; l = [10, 20, 30]; s = l[0];} ;
```

APEG attributes names must start with any letter and after that allow only letter, digits and underscores (_).



### Productions

Basically, productions are the rules which specify the syntax of the language you are trying to design (called non-terminals very often). For example, if we have the language of an infinite number of a's, we could have the following APEG production:

```
p[language g]: 'a'+ ;
```

As you might have seen, all APEG non-terminals must receive a language attribute. This is an implementation issue and is caused by the fact that APEG grammars are extensible on-the-fly. It is good to notice that attributes work like function parameters on any programming language. In this case, when they are working as parameters for productions, they are called inherited attributes. You can specify multiple inherited attributes of any APEG type:

```
p[language g, int n, {int} m, [int] l, string key]: ... ;
```

Also, the content of a production, where the rule is in fact specified, must be a sequence of PEGs. In the example above, we used a Literal and Positive Kleene factors. If you need some help with PEG syntax, here are some good references:

GNU PEG Reference: https://www.gnu.org/software/guile/manual/html_node/PEG-Syntax-Reference.htmll
Wikipedia PEG Article: https://en.wikipedia.org/wiki/Parsing_expression_grammar

An interesting way of learning PEG syntax can be also by reading APEG examples, which you can find here. Further, we introduce some special syntax that can be used inside production rules.

APEG productions also support return values. Return values are called synthesized attributes:

```
p[language g] returns n: {n = 10;} ;
```

```
s[language g] returns x, y, 20: {x = 10; y = 20;} ;
```

Rule names follows the same pattern as attribute declarations: they can start with any letter and can be followed by letters, digits and underscores.



### Accessing attributes

It is very simple to get an attribute value. For simple variables, you can do this by just referencing the name:

```
p[language g, int n]: {tmp = n;} ;
```



### Maps

In APEG, maps consists of associative arrays that are static typed and responsible for mapping a string to a determined value. They can be declared using the {: :} operators. See an example below:

```
p[language g]: {myMap = {: 'abc' -> 10 :};} ;
```

But they cannot be declared empty. Maps can also be extended:

```
p[language g, {int} myMap]: {myMap = myMap['def' -> 20];} ;
```

Here, we are extending the map called *myMap* with a new key *def* associated to the integer number *20*. Notice that when you extend a map, you need to store the result on a variable. Otherwise, you will lost the new extended map.

For accessing a value inside a map, you just specify the key on the index space:

```
p[language g, {int} myMap: {k = myMap['def'];} ;
```



### Lists

An APEG List works as an homogeneous array, storing sequencially values of same type. You can declare it by using [] operators, see below:

```
p[language g]: {myList = [10, 20, 30];}
```

As maps, lists cannot be declared empty. To access a value, place the desired index on address area:

```
p[language g, [int] myList, int index]: {tmp = myList[index];} ;
```

You can also insert new values on the list by concatenating lists. To do this, use the ++ concatenation operator:

```
p[language g]: {l1 = [10]; l2 = [20]; l3 = l1 ++ l2;} ;
```



### Expressions

APEG supports arithmetic and boolean expressions conforming with usual operator precedence. Here goes a list of supported expressions operators:

* x and y represents random values

| Relational operator | Meaning                      |
| ------------------- | ---------------------------- |
| ==                  | If x is equal to y           |
| <                   | If x is less than y          |
| >                   | If x is bigger than y        |
| <=                  | If x is less or equal to y   |
| >=                  | if x is bigger or equal to y |
| !=                  | if x is different than y     |

| Logical operator | Meaning       |
| ---------------- | ------------- |
| &&               | AND operator  |
| \|\|             | OR operator   |
| !                | NOT operatoor |

| Arithmetic operator | Meaning           |
| ------------------- | ----------------- |
| +                   | x plus y          |
| -                   | x minus y         |
| *                   | x multiplied by y |
| /                   | x divided by y    |
| %                   | x mod y           |

| APEG special operator | Meaning                    |
| --------------------- | -------------------------- |
| <<                    | Compose grammars           |
| ++                    | Concatenate list or string |

APEG expressions works as regular programming languages expressions. You can also use parenthesis () to specify a desired precedence.




### APEG Constructions

APEG Factors are constructions used to formulate sequences and describe strings which are very inspired on PEG syntax. The following constructions can be used to create APEG sequences:

* **Choice (/)**

  Choicer operator is used to indicate alternatives in a production. If the syntax fail for a specific production, it tries the next possible sequence on the rule. For example:

  ```
  p[language g]: 'aaa' / 'bbb' / 'a' 'b'+ 'c' ;
  ```

  So the interpreter will try the literal *'aaa'*, if it fails it will try the literal 'bbb' and if it fails it will try the last sequence.

  As every APEG factor, choices can also be used inside parenthesis:

  ```
  p[language g]: ('a' / 'b' / 'c') 'd'+ ;
  ```

  This production describes the syntax of a word which starts with *a*, *b* or *c* followed by at least one letter *d*.

* **APEG Constraint ({?})**

  Constraints can only be used with boolean expressions to verify conditions during productions:

  ```
  p[language g, int x, int y] returns n: {? x + 1 != x - 1 } {n = true;};
  ```

  If the boolean expressions is satisfied, the interpreter will succeed and the next factor on the sequence will be analyzed. On this example, if the constraint result is valid, p should return the attribute stored in n.

* **Bind (=)**

  Used to associate an APEG factor to a variable. For example:

  ```
  p[language g]: s=a..z+ {? s == 'hello' } ;
  ```

  This examples grab a string captured from the APEG Range and checks if the string corresponds to the word *hello*.

* **Range (..)**

  Used to describe possible intervals from x to y (x..y):

  - a..z for any low case letter of the alphabet.
  - 0..9 any possible digit.
  - g..) any character between the letter g and ).

  The order corresponds to the character values in the ASCII table.

* **Non-terminal call (<>)**

  To call another production, which has a set of sequences, you need use a non-terminal call. In APEG, you can do this using the following syntax:

  ```
  non_terminal_name <arg1, arg2, ..., argN, syn1, syn2, ..., synN>
  ```

  Here is an example to illustrate the use of this resource:

  ```
  entry[language g]: evaluate<g, 10, 11, res> {? res } ;
  evaluate[language g, int x, int y] returns result: {result = (x == y);} ;
  ```

  So entry will go to evaluate, process the production, return to entry and analyze the next item of the sequence (in this case, the evaluate the constraint).

* **Any** **(_)**

  This one is very basic. It specifies any character:

  ```
  p[language g]: _+ ;
  ```

  So in this example, p will recognize any input.

* **Star Kleene (*)**

  Indicate a sequence of zero or more APEG constructions. The interpreter will always succeed when it finds a Star Kleene, since this construction do not require to recognize the language. See an example:

  ```
  p[language g]: 'd'* ;
  ```

  Notice that we can have one or more letters *d* but it is not a must have.

* **Positive Kleene (+)**

  Used to indicate a sequence of one or more APEG constructions. Since his behavior is similar to Star Kleene and we've already seen a lot of examples using this constructions before on this manual, we will jump the code example step.



### Primary literals

Primary literals are the primitive constructions you can use to build sophisticated expressions, create inherited attributes and assign variables, see some examples below.

Using primary literals to create an attribute called name:

```
name = 'first' ++ namesList[1] ++ namesMap['third'];
```

Passing boolean literals, numbers, variables and maps as inherited attribute values to 

```
ntcall<g, TRUE, FALSE, 10, 20 + 30, m['abc' -> 20]>
```

It is also possible to indicate precedence using parenthesis:

```
{? (10 + 50) < 30 }
```

Meta constructions are also primary literals, see a few examples:

```
meta_expr = (| 10 + 20 |);
meta_peg = {| 'hello' world<g> };
meta_type = (| int |);
```

To know more about meta programming in APEG, which is one of the most interesting resources this tool offers, you can read the [meta guide](https://github.com/lives-group/APEG/blob/dev/docs/meta_guide.md) and get all the needed information including other primary literals which are purpose specific.

List of APEG primary literals:

| Literal          | Meaning                                               |
| ---------------- | ----------------------------------------------------- |
| TRUE/FALSE       | Boolean values                                        |
| 100, 10.24, 2022 | Numerical values (integers or floating point numbers) |
| ''               | String (Sequence of characters)                       |
| {: :}            | Map (Associative array)                               |
| []               | List (Sequence of values)                             |
| $                | Empty grammar                                         |

More documentation about lists, maps and number ranges can be found at the manual begin.



### Links

[Meta Guide](https://github.com/lives-group/APEG/blob/dev/docs/meta_guide.md) |
[Users Guide](https://github.com/lives-group/APEG/blob/dev/docs/users.md) |
[Contributors](https://github.com/lives-group/APEG/blob/dev/docs/contributors.md) |
[Getting Started](https://github.com/lives-group/APEG/blob/dev/docs/getting-started.md)
