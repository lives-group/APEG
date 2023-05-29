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
                                                       "aux[language g, string nt, type ty, string lit]: {g = g << {| #nt[#ty g]: #lit ;|};} ;",
                                                       "begin[language g]: {? false } ;");

    public static final String METANTCALL = String.join("\n",
                                                        "apeg metantcall;",
                                                        "p[language g]: {call = <'ref', [(| g |)]>; g = g << {| s[language g]: #call ; |};} s<g> ;",
                                                        "s[language g]: {? false } ;",
                                                        "ref[language g]: 'abc' ;");

    public static final String ABSGRAMMAR = String.join("\n",
                                                        "apeg abs;",
                                                        "neg[language g]: '|' '-' number<g, n> '|' '=' number<g, x> {? n == x } ;",
                                                        "abs[language g] returns r: '-' number<g, r> {r = -r;} / number<g, r> ;",
                                                        "number[language g] returns r: digit<g, r> (digit<g, aux> {r = r*10 + aux;})* ;",
                                                        "digit[language g] returns x1:",
                                                                "'0'  {x1 = 0;} /",
                                                                "'1'  {x1 = 1;} /",
                                                                "'2'  {x1 = 2;} ;");
    public static final String METASUM = String.join("\n",
                                                     "apeg metasum;",
                                                     "p[language g]: e<g, mr> '=' number<g, y> {g = g << {| beg[language g]: {? #y == #mr }; |};} beg<g> ;",
                                                     "e[language g] returns mr: number<g, mr> ('+' number<g, x> {mr = (| #mr + #x |);})*;",
                                                     "beg[language g]: {? false };",
                                                     "number[language g] returns ~r: digit<g, r> (digit<g, aux> {r=r*10+aux;})*;",
                                                     "digit[language g] returns x1:",
                                                                   "'0'  {x1 = 0;} /",
                                                                   "'1'  {x1 = 1;} /",
                                                                   "'2'  {x1 = 2;} /",
                                                                   "'3'  {x1 = 3;} /",
                                                                   "'4'  {x1 = 4;} /",
                                                                   "'5'  {x1 = 5;} /",
                                                                   "'6'  {x1 = 6;} /",
                                                                   "'7'  {x1 = 7;} /",
                                                                   "'8'  {x1 = 8;} /",
                                                                   "'9'  {x1 = 9;} ;");

    public static final String VARDECLARATION = String.join("\n",
                                                            "apeg vardeclaration;",
                                                            "p[language g]: declList<g, p> {g = g << {| var[language g]: #p ;|};} stmt<g>+ ;",
                                                            "declList[language g] returns p: decl<g, s> {p = {| #s |};} (decl<g, p1> {p = {| #p / #p1 |};})* ;",
                                                            "decl[language g] returns `id`: 'int ' id=(a..z)+ ' = ' (0..9)+ ';' ;",
                                                            "stmt[language g]: var<g> ' = ' var<g> ';' ;",
                                                            "var[language g]: {? false } ; ");

    public static final String PREDICATE = String.join("\n",
                                                       "apeg predicate;",
                                                       "p[language g]: {g = g << {| neg[language g]: !'foo' 'bar' &'end' ; |};} neg<g> ;",
                                                       "neg[language g]: {? false } ;");
}
