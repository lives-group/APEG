# Getting Started

Let's make the first steps on developing grammars using APEG. This tutorial provides an introduction to writing APEG grammars and validating input files. After reading this document you should get some knowledge about some things you can do using our tool.


### Get set

First thing we need to do is clone APEG repository and generate the interpreter .jar package. You can do this by running the following command:

```
git clone https://github.com/lives-group/APEG.git
cd APEG/language/code/compiler
mvn package
```

Wait until maven complete the build.


### Writing first APEG grammar

Now, lets write a simple grammar that recognizes every number that is divisible by 3.

Open a new file and write a good grammar name:

```
apeg divisiblebythree;
```

Then, we will write our first production rule. An APEG grammar is basically a set of production rules that describe all possible strings in a given language (in our case, every number divisible by 3). Our production rule will be called *prog* and will be our entry point to the interpreter:

``` 
prog[language g]: ;
```

You should be asking what is that *"language g"* thing and that's is an interesting question. APEG is an adaptable grammar, so you can make new production rules and constructions during runtime. To make this possible, by implementation, we need to **always** pass this language attribute. Later, we will talk about meta-programming on APEG and show how amazing this resource is to write syntax, specially extensible languages.

After all this talking, lets write an production to recognize and return numbers, which are a set of digits. For this, we will be calling the digits non-terminal followed by the PEG Kleene closure to specify zero or more digits:

```
number[language g] returns n: digits<g, r> (digits<g, aux>)* ;
digits[language g] returns d: ;
```

If you are not comfortable with PEG syntax, [here is a good reference](https://www.gnu.org/software/guile/manual/html_node/PEG-Parsing.html#:~:text=Parsing%20Expression%20Grammars%20(PEGs)%20are,(like%20lex%2Fyacc).). PEGs are used to describe and specify production rules.

Also, you might have realized that we are writing some variables inside *''<>"*. These are the attributes that we are passing to the non-terminal and the last ones are the returned attributes, like functions on common programming languages. In APEG, return values are called synthesized attributes and parameters are called inherited attributes.

Adding some code to our called digits production:

```
digits[language g] returns d:
    '0'  { d = 0; }
  / '1'  { d = 1; }
  / '2'  { d = 2; }
  / '3'  { d = 3; }
  / '4'  { d = 4; }
  / '5'  { d = 5; }
  / '6'  { d = 6; }
  / '7'  { d = 7; }
  / '8'  { d = 8; }
  / '9'  { d = 9; }
;
```

In this code, we are using APEG Choice operator *''/''* to specify alternatives (so we can have 0 or 1 or 2 ...) and *"{}''* syntax to assign values to variables. Everything that's inside brackets are variables being updated, and APEG is typed so your variables will have one of those types:

- int
- float
- string
- boolean
- char
- language
- [$type] (list)
- {$type} (map)

And also, types for meta constructions:

- peg
- expr
- type

Back to our number production, lets add a little trick to convert this digits into a complete number:

```
number[language g] returns n: digits<g, n> (digits<g, aux> { n = n * 10 + aux; })*
```

Now, we are almost there. Let's get back to our entry point rule and call number non-terminal:

```
prog[language g]: number<g, n>
```

And add the following code:

```
prog[language g]: number<g, n> {? n % 3 == 0 }
```

The *''{?}''* is APEG constraint and is used to place boolean expressions on productions. So in our case, if n is divisible by 3, using the mod operator, the result must be equal to zero. If this operations is valid, our provided input file will be accepted.

And that's it, we are done. Save the file as *"divisiblebythree.apeg"* extension and close it.


### Validating input file

With our syntax completely defined with APEG, let's write an simple input file and test if it gets accepted by the interpreter.

```
echo '12' >> input.txt
```

Then, find the interpreter .jar and run the following command:

```
java -jar apeg.jar divisiblebythree.apeg -i input.txt
```

If you need some help to figure out interpreter usage, you can use the help (-h) command:

```
java -jar apeg.jar --h
```

If everything is OK, our output should be:

```
accepted
```



### Meta programming

Let's take a step further and introduce the resources that APEG offers for meta programming. As we said earlier, APEG is an adaptable grammar, so it's possible to create new constructions and productions on-the-fly. You can find all documentation about APEG meta programming on [meta-guide](https://github.com/lives-group/APEG/blob/dev/docs/meta_guide.md) page.

Just for learning purpose, we will present a step-by-step tutorial to create a syntax that has n square brackets. Some thing like this:

```
3]]] (accepted)
5]]]]] (accepted)
5]] (rejected)
```

First of all, we are going to reuse the number production written earlier on this tutorial and we will jump the basic steps.

Put the following code on your file:

 ```
 prog[language g]: number<g, n> rep<g, `']'`, n, p> ;
 ```

Here we are calling number and then a not defined yet production called rep, which receives the language, number, and returns a synthesized attribute stored on p. The \`']'\`represents a Literal PEG object and will be used to formulate our new pattern.

Now, we create the rep production:

```
rep[language g, peg pat, int n] returns p: ;
```

Basically, what we will do is create a PEG sequence of n times pat. So now, lets create a loop on our code:

```
rep[language g, peg pat, int n] returns p:
	{i=1;} ({? i < n} {i = i + 1;})*
;
```

And for the interesting part of our production, use meta peg creation operator ({||}) and unquote operator (#) to obtain the AST of a meta type:

```
rep[language g, peg pat, int n] returns p:
	{p=pat; i=1;} ({? i < n} {p = {| #pat #p |}; i = i + 1;})*
;
```

After that, we will have a peg sequence stored on p. Lets go back to our entry point production and do the magic of extending the grammar:

```
prog[language g]: number<g, n> rep<g, `']'`, n, p> {g = g << {| syntax[language g]: #res ; |};};
```

What this code do is formulate a meta production named *syntax* using {| |} operators which contains the AST stored on p that we have created and extend g by using the compose *<<* operator.

Lastly, lets call our new created rule:

```
prog[language g]: number<g, n> rep<g, `']'`, n, p> {g = g << {| syntax[language g]: #res ; |};} syntax<g>
;
```

Wow! That's it. Now we are able to run the interpreter and validate an input file.

### Next steps

You can now start using your creative to specify syntaxes with APEG grammars and discover new resources that APEG can offer. A lot of information is available on our documentations. [Manual reference](https://github.com/lives-group/APEG/blob/dev/docs/contributors.md) contains all information about every APEG construction (types, PEG, expressions, maps, lists, etc), [Meta Guide](https://github.com/lives-group/APEG/blob/dev/docs/meta_guide.md) provides details and examples about meta programming and [Users Guide](https://github.com/lives-group/APEG/blob/dev/docs/users.md) contains some other practical examples.

It is also interesting to keep an eye on ANTLR grammar file that specifies APEG syntax, which can be found [here](https://github.com/lives-group/APEG/blob/dev/language/code/compiler/src/main/grammars/APEG.g4), to know everything that is possible to do in terms of syntax.

We are always open to new contributions, so if you are interested in contributing with some new stuff, [Contributors Guide](https://github.com/lives-group/APEG/blob/dev/docs/contributors.md) is the reference you are searching for.