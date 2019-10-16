# Instructions For Contributors

All development needs to be done in branches to avoid inconsistencies in the project and conflicts among the development teams.

## Protocol to Start a Development Branch

All team members needs to folow this proctol: 

### Cloning the Project

The first step is to get the project from the web repository.
It only needs to be done once.
In the directory you want to download, tap the command:
> _git clone https://github.com/lives-group/APEG.git_

Great! After a few seconds, the root of the project, a folder named APEG,
will be on your local computer.

### Creating New Features:
Once you  have downloaded the project, if you are to create a new feature or modify an existing one,
you should do so in a branch.
You can think of a branch as a copy of the source code of the project, so if
anything catastrophic happens to the code you are writing, it won't affect the "original" code, and you can
always start over again! 

To create a new branch type the following commands in the directory of the project:

> _git branch MyBrabch_  
> _git checkout MyBranch_

After executing these commands, you are now working on a copy (branch) of the project. 

You can see the branch your working on by typing the command: 
> _git branch_

### The Daily Work Routine

Every time, when you sit in front of your computer to start developing and programming, always remember to check for updates from the repository.
So, before starting code, type the following git command:

> _git pull_

Also, it is a good practice to check the branch you are working on.
For this, type:

> _git branch_

If you aren't in the correct branch, change it using the following command:

> _git checkout BranchName_

At summary, always execute the followings git commands before starting to coding:

> _git pull_  
> _git branch_

### Uploading Modifications to the Branch
Once you have done all modifications needed to the code you should uplaoad the to the gitHub. To fo this 
first tell git to include all modified files to the list of files pendind to be uploaded:

> _git add --all_

Now commit your changes :

> _git commint -m "Some stuff modified" 

Finnaly upload them to repoistory: 

> _git push origin MyBranch_

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

## Structure of the Abstract Syntax Tree

### The Base AST Class

Every node of the AST is a child of the class [ASTNode](./../language/code/compiler/src/main/java/apeg/parse/ast/ASTNode.java).
That class provides basic information shared by every node of the tree, such as its respectively line and column on source code. 

### Grammar Node


### Rules Nodes

Every grammar must have it's own set of rules, defined in cfg.
Each rule is inherited from the main class starting with the tag "apeg".
After that, given an input, the program will follow the tree until it hits an end point, by giving an output or changing something.

### APEG Parsing Expressions Nodes


### Node for the Attribute Language

## AST Study

The ANTLR parser recognizes the elements present in the source code and builds a parse tree, from that we will obtain the AST. It allows the use of inherited and synthesized attributes and semantic actions as embedded code, generating efficient code, representing each nonterminal symbol as a function, in a recursive descent style. 
An Abstract Syntax Tree (AST) is a form of representation of the abstract syntactic structure of source code, where the nodes of the tree denotes a construct occurring on it. Inherited attributes are implemented as function parameters, and synthesized attributes are implemented as function return values.
The AST does not represent all of the details that are on the real syntax, only the structural and content-related ones, but ASTs allow us to edit and refine it with information like properties and annotations of each element it contains, which would not be possible in the source code because that would imply in alterations on it.


The codes we are analyzing describes the right-hand side of a PEG expression, they give attributes to the types like a Rule type or a Not-PEG, etc. 
For example, the _RuleNode_ defined on the _RuleNode_ class has a name, an annotation, a list named _VarDeclartionNode_ it contains parameters and returns and, finally a _PegNode_.
In _grammar 01_ we got rules _a_ and _b_:

> a: b '1' / b '2';

In the rule _a_ the attributes are:
Name: 'a';
Annotation: none;
List: 'a' rule does not have any parameters or returns.
PegNode: '/' defined on _ChoicePegNode_;
Besides that _a_ "calls" _b_ and _b_ is defined by the character 'b' and has the annotation Transient which means it will conserve his value for each test;

When a nonterminal is referenced inside a PEG expression, the values of the inherited and synthesized attributes are listed between <...>, as can be seen in line 15 of grammar 04:

> a returns[int k] : b<0,k>;

In this example _0_ is the inherited value and _k_ is the synthesized attribute. Synthesized attributes must always be in the variable form.

In our code we've got the _PegNode_ class that is the base interface of all the parsing expressions this parsing expressions are defined in different classes whose extends from _PegNode_, which one has his own attribute including a positions on the AST.
* AndPegNode
* AnyPegNode
* BindPegNode
* ChoicePegNode
* ConstraintPegNode
* GroupPegNode
* LambdaPegNode
* LiteralPegNode
* NonterminalPegNode
* NotPegNode
* OptionalPegNode
* PlusPegNode
* SequencePegNode
* StarPegNode
* UpdatePegNode

Besides the _PegNode_ class we got also _TypeNode_ and _ExprNode_ all extendiing from _ASTNode_. Those three classes defines all that a peg needs.
_PegNodes_ define the peg operators.
_ExprNode_ identify the operator given in language and defines which kind of Peg it is.
_TypeNode_ defines de type of the expression.
