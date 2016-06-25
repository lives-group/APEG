package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.AstFactory;
import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.BinaryExprNode;
import apeg.parse.ast.BinaryExprNode.Operator;
import apeg.parse.ast.BindPegNode;
import apeg.parse.ast.BooleanExprNode;
import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.EqualityExprNode.EqualityOperator;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.FunctionNode;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.GroupPegNode;
import apeg.parse.ast.IntExprNode;
import apeg.parse.ast.LambdaPegNode;
import apeg.parse.ast.LiteralPegNode;
import apeg.parse.ast.MetaPegExprNode;
import apeg.parse.ast.MinusExprNode;
import apeg.parse.ast.NonterminalPegNode;
import apeg.parse.ast.NotExprNode;
import apeg.parse.ast.NotPegNode;
import apeg.parse.ast.OptionalPegNode;
import apeg.parse.ast.OrExprNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.PlusPegNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.TypeNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.VarDeclarationNode;

public class AstFactoryImpl implements AstFactory {

	@Override
	public AndExprNode newAndExpr(ExprNode left, ExprNode right) {
		return new AndExprNodeImpl(left, right);
	}

	@Override
	public AndPegNode newAndPeg(PegNode peg) {
		return new AndPegNodeImpl(peg);
	}

	@Override
	public AnyPegNode newAnyPeg() {
		return new AnyPegNodeImpl();
	}

	@Override
	public AssignmentNode newAssignment(AttributeExprNode varName, ExprNode expr) {
		return new AssignmentNodeImpl(varName, expr);
	}

	@Override
	public AttributeExprNode newAttributeExpr(String attrName) {
		return new AttributeExprNodeImpl(attrName);
	}

	@Override
	public AttributeExprNode newAttributeGrammarExpr() {
		return new AttributeGrammarExprNodeImpl();
	}

	@Override
	public BinaryExprNode newBinaryExpr(ExprNode left, ExprNode right,
			Operator op) {
		return new BinaryExprNodeImpl(left, right, op);
	}

	@Override
	public BindPegNode newBindPeg(AttributeExprNode attrName, PegNode peg) {
		return new BindPegNodeImpl(attrName, peg);
	}

	@Override
	public BooleanExprNode newBooleanExpr(boolean value) {
		return new BooleanExprNodeImpl(value);
	}

	@Override
	public TypeNode newBooleanType() {
		return new BooleanTypeNodeImpl();
	}

	@Override
	public CallExprNode newCallExpr(String funcName, List<ExprNode> param) {
		return new CallExprNodeImpl(funcName, param);
	}

	@Override
	public ChoicePegNode newChoicePeg(PegNode left, PegNode right) {
		return new ChoicePegNodeImpl(left, right);
	}

	@Override
	public ConstraintPegNode newConstraintPeg(ExprNode expr) {
		return new ConstraintPegNodeImpl(expr);
	}

	@Override
	public EqualityExprNode newEqualityExpr(ExprNode left, ExprNode right,
			EqualityOperator op) {
		return new EqualityExprNodeImpl(left, right, op);
	}

	@Override
	public FloatExprNode newFloatExpr(double value) {
		return new FloatExprNodeImpl(value);
	}

	@Override
	public TypeNode newFloatType() {
		return new FloatTypeNodeImpl();
	}

	@Override
	public GrammarNode newGrammar(String name, List<GrammarOption> opts,
			String preamble, List<RuleNode> rules, List<String> func,
			List<String> fsource) {
		return new GrammarNodeImpl(name, opts, preamble, rules, func, fsource);
	}

	@Override
	public TypeNode newGrammarType() {
		return new GrammarTypeNodeImpl();
	}

	@Override
	public GroupPegNode newGroupPeg(String ranges) {
		return new GroupPegNodeImpl(ranges);
	}

	@Override
	public IntExprNode newIntExpr(int value) {
		return new IntExprNodeImpl(value);
	}

	@Override
	public TypeNode newIntType() {
		return new IntTypeNodeImpl();
	}

	@Override
	public LambdaPegNode newLambdaPeg() {
		return new LambdaPegNodeImpl();
	}

	@Override
	public LiteralPegNode newLiteralPeg(String value) {
		return new LiteralPegNodeImpl(value);
	}

	@Override
	public MetaPegExprNode newMetaPeg(ExprNode expr) {
		return new MetaPegExprNodeImpl(expr);
	}

	@Override
	public MinusExprNode newMinusExpr(ExprNode expr) {
		return new MinusExprNodeImpl(expr);
	}

	@Override
	public NonterminalPegNode newNonterminalPeg(String name,
			List<ExprNode> attrs) {
		return new NonterminalPegNodeImpl(name, attrs);
	}

	@Override
	public NotExprNode newNotExpr(ExprNode expr) {
		return new NotExprNodeImpl(expr);
	}

	@Override
	public NotPegNode newNotPeg(PegNode peg) {
		return new NotPegNodeImpl(peg);
	}

	@Override
	public OptionalPegNode newOptionalPeg(PegNode peg) {
		return new OptionalPegNodeImpl(peg);
	}

	@Override
	public OrExprNode newOrExpr(ExprNode left, ExprNode right) {
		return new OrExprNodeImpl(left, right);
	}

	@Override
	public PlusPegNode newPlusPeg(PegNode peg) {
		return new PlusPegNodeImpl(peg);
	}

	@Override
	public RuleNode newRule(String name, Annotation anno,
			List<VarDeclarationNode> param, List<VarDeclarationNode> ret,
			PegNode peg) {
		return new RuleNodeImpl(name, anno, param, ret, peg);
	}

	@Override
	public TypeNode newRuleType() {
		return new RuleTypeNodeImpl();
	}

	@Override
	public SequencePegNode newSequencePeg(List<PegNode> pegs) {
		return new SequencePegNodeImpl(pegs);
	}

	@Override
	public StarPegNode newStarPeg(PegNode peg) {
		return new StarPegNodeImpl(peg);
	}

	@Override
	public StringExprNode newStringExpr(String value) {
		return new StringExprNodeImpl(value);
	}

	@Override
	public TypeNode newStringType() {
		return new StringTypeNodeImpl();
	}

	@Override
	public TypeNode newType(String type) {
		return new TypeNodeImpl(type);
	}

	@Override
	public UpdatePegNode newUpdatePeg(List<AssignmentNode> assigs) {
		return new UpdatePegNodeImpl(assigs);
	}

	@Override
	public VarDeclarationNode newVarDeclaration(String name, TypeNode type) {
		return new VarDeclarationNodeImpl(name, type);
	}

}
