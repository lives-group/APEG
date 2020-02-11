package apeg.ast;

import java.util.List;





//import apeg.parse.ast.FunctionNode;
import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.AssignmentNode;
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
import apeg.parse.ast.FloatExprNode;
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
import apeg.parse.ast.PlusPegNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.TypeNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.VarDeclarationNode;

public class ASTFactoryImpl implements ASTFactory {

	@Override
	public AndExprNode newAndExpr(ExprNode left, ExprNode right) {
		return new AndExprNode(left, right);
	}

	@Override
	public AndPegNode newAndPeg(PegNode peg) {
		return new AndPegNode(peg);
	}

	@Override
	public AnyPegNode newAnyPeg() {
		return new AnyPegNode();
	}

	@Override
	public AssignmentNode newAssignment(AttributeExprNode varName, ExprNode expr) {
		return new AssignmentNode(varName, expr);
	}

	@Override
	public AttributeExprNode newAttributeExpr(String attrName) {
		return new AttributeExprNode(attrName);
	}

	@Override
	public AttributeExprNode newAttributeGrammarExpr() {
		return new AttributeGrammarExprNode();
	}

	@Override
	public BinaryExprNode newBinaryExpr(ExprNode left, ExprNode right,
			Operator op) {
		return new BinaryExprNode(left, right, op);
	}

	@Override
	public BindPegNode newBindPeg(AttributeExprNode attrName, PegNode peg) {
		return new BindPegNode(attrName, peg);
	}

	@Override
	public BooleanExprNode newBooleanExpr(boolean value) {
		return new BooleanExprNode(value);
	}

	@Override
	public TypeNode newBooleanType() {
		return new BooleanTypeNode();
	}

	@Override
	public CallExprNode newCallExpr(String funcName, List<ExprNode> param) {
		return new CallExprNode(funcName, param);
	}

	@Override
	public ChoicePegNode newChoicePeg(PegNode left, PegNode right) {
		return new ChoicePegNode(left, right);
	}

	@Override
	public ConstraintPegNode newConstraintPeg(ExprNode expr) {
		return new ConstraintPegNode(expr);
	}

	@Override
	public EqualityExprNode newEqualityExpr(ExprNode left, ExprNode right,
			EqualityOperator op) {
		return new EqualityExprNode(left, right, op);
	}

	@Override
	public FloatExprNode newFloatExpr(double value) {
		return new FloatExprNode(value);
	}

	@Override
	public TypeNode newFloatType() {
		return new FloatTypeNode();
	}

	@Override
	public GrammarNode newGrammar(String name, List<GrammarOption> opts,
			String preamble, List<RuleNode> rules, List<String> func,
			List<String> fsource) {
		return new GrammarNode(name, opts, preamble, rules, func, fsource);
	}

	@Override
	public TypeNode newGrammarType() {
		return new GrammarTypeNode();
	}

	@Override
	public GroupPegNode newGroupPeg(String ranges) {
		return new GroupPegNode(ranges);
	}

	@Override
	public IntExprNode newIntExpr(int value) {
		return new IntExprNode(value);
	}

	@Override
	public TypeNode newIntType() {
		return new IntTypeNode();
	}

	@Override
	public LambdaPegNode newLambdaPeg() {
		return new LambdaPegNode();
	}

	@Override
	public LiteralPegNode newLiteralPeg(String value) {
		return new LiteralPegNode(value);
	}

	@Override
	public MetaPegExprNode newMetaPeg(ExprNode expr) {
		return new MetaPegExprNode(expr);
	}

	@Override
	public MinusExprNode newMinusExpr(ExprNode expr) {
		return new MinusExprNode(expr);
	}

	@Override
	public NonterminalPegNode newNonterminalPeg(String name,
			List<ExprNode> attrs) {
		return new NonterminalPegNode(name, attrs);
	}

	@Override
	public NotExprNode newNotExpr(ExprNode expr) {
		return new NotExprNode(expr);
	}

	@Override
	public NotPegNode newNotPeg(PegNode peg) {
		return new NotPegNode(peg);
	}

	@Override
	public OptionalPegNode newOptionalPeg(PegNode peg) {
		return new OptionalPegNode(peg);
	}

	@Override
	public OrExprNode newOrExpr(ExprNode left, ExprNode right) {
		return new OrExprNode(left, right);
	}

	@Override
	public PlusPegNode newPlusPeg(PegNode peg) {
		return new PlusPegNode(peg);
	}

	@Override
	public RuleNode newRule(String name, Annotation anno,
			List<VarDeclarationNode> param, List<VarDeclarationNode> ret,
			PegNode peg) {
		return new RuleNode(name, anno, param, ret, peg);
	}

	@Override
	public TypeNode newRuleType() {
		return new RuleTypeNode();
	}

	@Override
	public SequencePegNode newSequencePeg(List<PegNode> pegs) {
		return new SequencePegNode(pegs);
	}

	@Override
	public StarPegNode newStarPeg(PegNode peg) {
		return new StarPegNode(peg);
	}

	@Override
	public StringExprNode newStringExpr(String value) {
		return new StringExprNode(value);
	}

	@Override
	public TypeNode newStringType() {
		return new StringTypeNode();
	}

	@Override
	public TypeNode newUserType(String type) {
		return new UserTypeNode(type);
	}

	@Override
	public UpdatePegNode newUpdatePeg(List<AssignmentNode> assigs) {
		return new UpdatePegNode(assigs);
	}

	@Override
	public VarDeclarationNode newVarDeclaration(String name, TypeNode type) {
		return new VarDeclarationNode(name, type);
	}

}
