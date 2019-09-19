# Adaptable Parsing Expression Grammar (APEG)

This project aims to create a parser generator based on Adaptable Parsing Expression Grammars

## How to use 

APEG compiler toolchain is distributed as a single jar file.
To use it just invoke the java interpreter at a command line, passing the target file as a parameter. 

>java apeg.jar source file

Refer to the sections below to more information on how to use or contribute to APEG.

[<img src="docs/img/user.jpeg">](docs/users.md)   [<img src="docs/img/devel.jpeg">](docs/contributors.md)


# Syntax

# AST

Is a tree representation of the abstract syntactic structure of a source code, in this case, written in java. Each node of the tree denotes a construct occurring in the source code.


## Rules

Every grammar must have it's own set of rules, defined in cfg.
Each rule is inherited from the main class starting with the tag "apeg".
After that, given an input, the program will follow the tree until it hits an end point, by giving an output or changing something. 





[Maven]: http://maven.apache.org
