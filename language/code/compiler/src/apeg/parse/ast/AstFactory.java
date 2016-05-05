package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.BinaryExprNode.Operator;
import apeg.parse.ast.EqualityExprNode.EqualityOperator;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;

//import apeg.util.Pair;

public interface AstFactory {
	/**
	 * @param left
	 *            the left-hand side expression
	 * @param right
	 *            the right-hand side expression
	 * @return an AND expression node
	 */
	public AndExprNode newAndExpr(ExprNode left, ExprNode right);

	/**
	 * @param peg
	 *            parsing expression
	 * @return a and-predicate parsing expression node
	 */
	public AndPegNode newAndPeg(PegNode peg);

	/**
	 * @return an any parsing expression node
	 */
	public AnyPegNode newAnyPeg();

	/**
	 * @param varName
	 *            name of the left-hand side variable
	 * @param expr
	 *            right-hand side expression of the assignment
	 * @return an assignment node
	 */
	public AssignmentNode newAssignment(AttributeExprNode varName, ExprNode expr);

	/**
	 * @param attrName
	 *            attribute name
	 * @return an attribute expression node
	 */
	public AttributeExprNode newAttributeExpr(String attrName);

	/**
	 * @return a grammar reference attribute expression node
	 */
	public AttributeExprNode newAttributeGrammarExpr();

	/**
	 * @param left
	 *            left-hand side expression
	 * @param right
	 *            right-hand side expression
	 * @param op
	 *            operator type
	 * @return a binary expression node
	 */
	public BinaryExprNode newBinaryExpr(ExprNode left, ExprNode right,
			Operator op);

	/**
	 * @param attrName
	 *            attribute expression
	 * @param peg
	 *            parsing expression
	 * @return a bind parsing expression node
	 */
	public BindPegNode newBindPeg(AttributeExprNode attrName, PegNode peg);

	/**
	 * @param value
	 *            boolean value
	 * @return a boolean expression node
	 */
	public BooleanExprNode newBooleanExpr(boolean value);

	/**
	 * @return a boolean type node
	 */
	public TypeNode newBooleanType();

	/**
	 * @param funcName
	 *            function name
	 * @param param
	 *            set of parameter expressions
	 * @return a call expression node
	 */
	public CallExprNode newCallExpr(String funcName, List<ExprNode> param);

	/**
	 * @param left
	 *            first parsing expression of the choice
	 * @param right
	 *            second parsing expression of the choice
	 * @return a choice parsing expression node
	 */
	public ChoicePegNode newChoicePeg(PegNode left, PegNode right);

	/**
	 * @param expr
	 *            a boolean expression node
	 * @return a constraint parsing expression node
	 */
	public ConstraintPegNode newConstraintPeg(ExprNode expr);

	/**
	 * 
	 * @param left
	 *            the left-hand side expression
	 * @param right
	 *            the right-hand side expression
	 * @param op
	 *            type of the equality operator. If it is equal or not equal
	 * @return an Equal expression node
	 */
	public EqualityExprNode newEqualityExpr(ExprNode left, ExprNode right,
			EqualityOperator op);

	/**
	 * @param value
	 *            floating point expression value
	 * @return a floating expression node
	 */
	public FloatExprNode newFloatExpr(double value);

	/**
	 * @return a floating point type node
	 */
	public TypeNode newFloatType();

	// public FunctionNode newFunction();
	// FunctionYpe ?????
	/**
	 * @param name
	 *            grammar name
	 * @param opts
	 *            set of grammar options
	 * @param preamble
	 *            any code (unchecked) to be included at the generated parser
	 * @param rules
	 *            set of grammar rules
	 * @param func
	 *            set of external functions
	 * @param func_source
	 *            set of source file where contains the functions
	 * @return a grammar node
	 */
	public GrammarNode newGrammar(String name, List<GrammarOption> opts,
			String preamble, List<RuleNode> rules, List<String> func,
			List<String> func_source);

	/**
	 * @return a grammar type node
	 */
	public TypeNode newGrammarType();

	/**
	 * @param ranges
	 *            set of character ranges
	 * @return a group parsing expression node
	 */
	// public GroupPegNode newGroupPeg(List<Pair<Character, Character>> ranges);
	public GroupPegNode newGroupPeg(String ranges);

	/**
	 * @param value
	 *            int expression value
	 * @return an integer expression node
	 */
	public IntExprNode newIntExpr(int value);

	/**
	 * @return an integer type node
	 */
	public TypeNode newIntType();

	/**
	 * @return a lambda parsing expression node
	 */
	public LambdaPegNode newLambdaPeg();

	/**
	 * @param value
	 *            literal expression value
	 * @return a literal parsing expression node
	 */
	public LiteralPegNode newLiteralPeg(String value);

	/**
	 * @param expr
	 *            rules expression
	 * @return rules expression node (meta-programming)
	 */
	public MetaPegExprNode newMetaPeg(ExprNode expr);

	/**
	 * @param expr
	 *            expression
	 * @return a minus expression node
	 */
	public MinusExprNode newMinusExpr(ExprNode expr);

	/**
	 * @param name
	 *            nonterminal name
	 * @param attrs
	 *            set of attributes (inherited and synthesized)
	 * @return a nonterminal parsing expression node
	 */
	public NonterminalPegNode newNonterminalPeg(String name,
			List<ExprNode> attrs);

	/**
	 * @param expr
	 *            expression
	 * @return a not-expression node
	 */
	public NotExprNode newNotExpr(ExprNode expr);

	/**
	 * @param peg
	 *            parsing expression
	 * @return a not-predicate parsing expression node
	 */
	public NotPegNode newNotPeg(PegNode peg);

	/**
	 * @param peg
	 *            parsing expression
	 * @return an optional parsing expression node
	 */
	public OptionalPegNode newOptionalPeg(PegNode peg);

	/**
	 * @param left
	 *            left-hand side expression
	 * @param right
	 *            right-hand side expression
	 * @return a boolean OR expression node
	 */
	public OrExprNode newOrExpr(ExprNode left, ExprNode right);

	/**
	 * @param peg
	 *            parsing expression
	 * @return a plus parsing expression node
	 */
	public PlusPegNode newPlusPeg(PegNode peg);

	/**
	 * @param name
	 *            rule name
	 * @param anno
	 *            rule annotation
	 * @param param
	 *            set of inherited attributes
	 * @param ret
	 *            set of synthesized attributes
	 * @param peg
	 *            rule parsing expression
	 * @return a rule node
	 */
	public RuleNode newRule(String name, Annotation anno,
			List<VarDeclarationNode> param, List<VarDeclarationNode> ret,
			PegNode peg);

	/**
	 * @return a rule type node
	 */
	public TypeNode newRuleType();

	/**
	 * @param pegs
	 *            set of parsing expressions
	 * @return a sequence parsing expression node
	 */
	public SequencePegNode newSequencePeg(List<PegNode> pegs);

	/**
	 * @param peg
	 *            parsing expression
	 * @return a star parsing expression node
	 */
	public StarPegNode newStarPeg(PegNode peg);

	/**
	 * @param value
	 *            string expression value
	 * @return a string expression node
	 */
	public StringExprNode newStringExpr(String value);

	/**
	 * @return a string type
	 */
	public TypeNode newStringType();

	/**
	 * @param type
	 *            type name
	 * @return an user type node
	 */
	public TypeNode newType(String type);

	/**
	 * @param assigs
	 *            set of assignments
	 * @return an update parsing expression node
	 */
	public UpdatePegNode newUpdatePeg(List<AssignmentNode> assigs);

	/**
	 * @param name
	 *            variable name
	 * @param type
	 *            variable type
	 * @return a variable declaration node
	 */
	public VarDeclarationNode newVarDeclaration(String name, TypeNode type);
}
