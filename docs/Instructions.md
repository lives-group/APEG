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




We are developing the APEG project using the Java language and the Eclipse Luna IDE.
We also are using the ANTLR v4 tool for automatic generate the parser code and the plugin for working with it on Eclipse (see https://github.com/jknack/antlr4ide for more details about the ANTLR plugin for Eclipse).

[Maven]: maven.apache.org
