package apeg.ast;

import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import apeg.ast.Grammar.GrammarOption;

interface BinOPFactory{
    BinaryOP newOP(Expr e, Expr d);
}
