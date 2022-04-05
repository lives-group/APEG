package apeg.interpreter;

public class Programs {
    public static final String VAR_DECLARATION = String.join("\n",
                                                             "apeg varDeclaration;",
                                                             "var[language g]: a..z+ ' = ' expression<g> ';' ;",
                                                             "expression[language g]: factor<g> '+' expression<g> /",
                                                             "                  factor<g> '*' expression<g> /",
                                                             "                  factor<g> ;",
                                                             "factor[language g]: 0..9+ / a..z+ ;");

    // +,-,* : x = 10+20*30;
    public static final String EXPR_DECLARATION = String.join("\n",
                                                             "apeg exprDeclaration;",
                                                             "prog[language g]: ops<g, n> {x={| #n |};} (',' ops<g, n1> {x={| #x / #n1 |};})*",
                                                             "                  ' : ' {g = g << {| op[language g]: #x ; |};} var<g> ;",
                                                             "ops[language g] returns `s`: s='+' / s='-' / s='*' ;",
                                                             "var[language g]: a..z+ ' = ' expression<g> ';' ;",
                                                             "expression[language g]: factor<g> op<g> expression<g> /",
                                                             "                        factor<g> ;",
                                                             "factor[language g]: 0..9+ / a..z+ ;",
                                                             "op[language g]: {? false };");

    public static final String NUMBER_PROGRAM = String.join("\n",
                                                            "apeg quoteValue;",
                                                            "prog[language g]: number<g, q> ;",
                                                            "number[language g] returns ~n:",
                                                            "     '1' {n=1;} / '2' {n=2;} / '3' {n=3;} / '4' {n=4;} / '5' {n=5;} ;");

    public static final String METAPARAMETER = String.join("\n",
                                                           "apeg metaparameter;",
                                                           "prog[language g]: b<g, {| l<g> |}, r> {g = g << {| s[language g]: #r ; |};} s<g> ;",
                                                           "b[language g, peg p] returns {| #p+ |}: {? true } ;",
                                                           "l[language g]: a..z ;",
                                                           "s[language g]: {? false };");

    public static final String RULEBUILD = String.join("\n",
                                                       "apeg rulebuild;",
                                                       "prog[language g]: aux<g, 'begin', (| language |), 'foo'> begin<g> ;",
                                                       "aux[language g, string nt, type ty, string lit]: {g = g << {| #nt[#ty g]: #lit ;|};}",
                                                       "begin[language g]: {? false }");

    public static final String METANTCALL = String.join("\n",
                                                        "apeg metantcall;",
                                                        "p[language g]: {call = <'ref', [(| g |)]>; g = g << {| s[language g]: #call ; |};} s<g> ;",
                                                        "s[language g]: {? false } ;",
                                                        "ref[language g]: 'abc' ;");
}
