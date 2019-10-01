# Instructions For Contributors

## How to Download the Project

The first step is to get the project from the web repository.
In the directory you want to download, tap the command:
> _git clone https://github.com/lives-group/APEG.git_

Great! After a few seconds, the root of the project, a folder named APEG,
will be in your local computer.

## Project's Structure

In the root of the project, there are other three folders: _docs_, _experiments_,
and _language_.
Documentations instructions on how to use, tutorial, and any stuff produced by our team
and students about the project are placed in _docs_ folders.
_experiments_ folder is dedicated to putting the sources of the experiments conducted
using our tool.
All the tool source code resides in the _language_ folder.
This folder has tow others: _examples_ and _code_.
All examples of APEG grammars resides inside _examples_ folder, and the source code
is inside the _code_ folder and its sub-directories.

## How to Compile and Execute

We are using [Maven][Maven] to manage the project.
If you do not have [Maven][Maven] installed on your computer, install it before proceeding.
To compile the code is easy. You just need to tap _mvn compile_ when in subfolder _compiler_:
>cd language/code/compiler
>
>mvn compile

For executing the tool, you need to pass a grammar as a parameter.
You can find a lot of examples to use in the _examples_ folder.
Tap the following command to execute it, passing the _grammar01.apeg_ as a parameter:
>mvn exec:java -Dgrammar="grammar01.apeg"

Changing the parameter file, you can test other examples.  

[Maven]: http://maven.apache.org

# Structure of the Abstract Syntax Tree

Every node of the AST is a child of the class [ASTNode](./../language/code/compiler/src/main/java/apeg/parse/ast/ASTNode.java).
That class provides basic information shared by every node of the tree, such as its respectively line and column on source code. 



## Rules

Every grammar must have it's own set of rules, defined in cfg.
Each rule is inherited from the main class starting with the tag "apeg".
After that, given an input, the program will follow the tree until it hits an end point, by giving an output or changing something.