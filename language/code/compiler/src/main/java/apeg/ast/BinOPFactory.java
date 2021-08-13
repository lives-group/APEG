package apeg.ast;

import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import apeg.ast.Grammar.GrammarOption;

public interface BinOPFactory {
    Expr newOP(Expr e, Expr d);
}
