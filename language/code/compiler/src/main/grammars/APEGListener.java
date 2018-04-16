// Generated from APEG.g4 by ANTLR 4.7.1

    package apeg.parse;
    
    import apeg.parse.ast.*;
    
    import java.util.List;
    import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link APEGParser}.
 */
public interface APEGListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link APEGParser#grammarDef}.
	 * @param ctx the parse tree
	 */
	void enterGrammarDef(APEGParser.GrammarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#grammarDef}.
	 * @param ctx the parse tree
	 */
	void exitGrammarDef(APEGParser.GrammarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(APEGParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(APEGParser.OptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#grammar_opt}.
	 * @param ctx the parse tree
	 */
	void enterGrammar_opt(APEGParser.Grammar_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#grammar_opt}.
	 * @param ctx the parse tree
	 */
	void exitGrammar_opt(APEGParser.Grammar_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(APEGParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(APEGParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#h_text}.
	 * @param ctx the parse tree
	 */
	void enterH_text(APEGParser.H_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#h_text}.
	 * @param ctx the parse tree
	 */
	void exitH_text(APEGParser.H_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#functions}.
	 * @param ctx the parse tree
	 */
	void enterFunctions(APEGParser.FunctionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#functions}.
	 * @param ctx the parse tree
	 */
	void exitFunctions(APEGParser.FunctionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#rules}.
	 * @param ctx the parse tree
	 */
	void enterRules(APEGParser.RulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#rules}.
	 * @param ctx the parse tree
	 */
	void exitRules(APEGParser.RulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#production}.
	 * @param ctx the parse tree
	 */
	void enterProduction(APEGParser.ProductionContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#production}.
	 * @param ctx the parse tree
	 */
	void exitProduction(APEGParser.ProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(APEGParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(APEGParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#optDecls}.
	 * @param ctx the parse tree
	 */
	void enterOptDecls(APEGParser.OptDeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#optDecls}.
	 * @param ctx the parse tree
	 */
	void exitOptDecls(APEGParser.OptDeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#optReturn}.
	 * @param ctx the parse tree
	 */
	void enterOptReturn(APEGParser.OptReturnContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#optReturn}.
	 * @param ctx the parse tree
	 */
	void exitOptReturn(APEGParser.OptReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#optLocals}.
	 * @param ctx the parse tree
	 */
	void enterOptLocals(APEGParser.OptLocalsContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#optLocals}.
	 * @param ctx the parse tree
	 */
	void exitOptLocals(APEGParser.OptLocalsContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#decls}.
	 * @param ctx the parse tree
	 */
	void enterDecls(APEGParser.DeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#decls}.
	 * @param ctx the parse tree
	 */
	void exitDecls(APEGParser.DeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(APEGParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(APEGParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(APEGParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(APEGParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#peg_expr}.
	 * @param ctx the parse tree
	 */
	void enterPeg_expr(APEGParser.Peg_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#peg_expr}.
	 * @param ctx the parse tree
	 */
	void exitPeg_expr(APEGParser.Peg_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#peg_seq}.
	 * @param ctx the parse tree
	 */
	void enterPeg_seq(APEGParser.Peg_seqContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#peg_seq}.
	 * @param ctx the parse tree
	 */
	void exitPeg_seq(APEGParser.Peg_seqContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#peg_capturetext}.
	 * @param ctx the parse tree
	 */
	void enterPeg_capturetext(APEGParser.Peg_capturetextContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#peg_capturetext}.
	 * @param ctx the parse tree
	 */
	void exitPeg_capturetext(APEGParser.Peg_capturetextContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#peg_unary_op}.
	 * @param ctx the parse tree
	 */
	void enterPeg_unary_op(APEGParser.Peg_unary_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#peg_unary_op}.
	 * @param ctx the parse tree
	 */
	void exitPeg_unary_op(APEGParser.Peg_unary_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#peg_factor}.
	 * @param ctx the parse tree
	 */
	void enterPeg_factor(APEGParser.Peg_factorContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#peg_factor}.
	 * @param ctx the parse tree
	 */
	void exitPeg_factor(APEGParser.Peg_factorContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#ntcall}.
	 * @param ctx the parse tree
	 */
	void enterNtcall(APEGParser.NtcallContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#ntcall}.
	 * @param ctx the parse tree
	 */
	void exitNtcall(APEGParser.NtcallContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#range_pair}.
	 * @param ctx the parse tree
	 */
	void enterRange_pair(APEGParser.Range_pairContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#range_pair}.
	 * @param ctx the parse tree
	 */
	void exitRange_pair(APEGParser.Range_pairContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#single_pair}.
	 * @param ctx the parse tree
	 */
	void enterSingle_pair(APEGParser.Single_pairContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#single_pair}.
	 * @param ctx the parse tree
	 */
	void exitSingle_pair(APEGParser.Single_pairContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(APEGParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(APEGParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(APEGParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(APEGParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#condExpr}.
	 * @param ctx the parse tree
	 */
	void enterCondExpr(APEGParser.CondExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#condExpr}.
	 * @param ctx the parse tree
	 */
	void exitCondExpr(APEGParser.CondExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#or_cond}.
	 * @param ctx the parse tree
	 */
	void enterOr_cond(APEGParser.Or_condContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#or_cond}.
	 * @param ctx the parse tree
	 */
	void exitOr_cond(APEGParser.Or_condContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#and_cond}.
	 * @param ctx the parse tree
	 */
	void enterAnd_cond(APEGParser.And_condContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#and_cond}.
	 * @param ctx the parse tree
	 */
	void exitAnd_cond(APEGParser.And_condContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#bool_expr}.
	 * @param ctx the parse tree
	 */
	void enterBool_expr(APEGParser.Bool_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#bool_expr}.
	 * @param ctx the parse tree
	 */
	void exitBool_expr(APEGParser.Bool_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#aexpr}.
	 * @param ctx the parse tree
	 */
	void enterAexpr(APEGParser.AexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#aexpr}.
	 * @param ctx the parse tree
	 */
	void exitAexpr(APEGParser.AexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#termOptUnary}.
	 * @param ctx the parse tree
	 */
	void enterTermOptUnary(APEGParser.TermOptUnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#termOptUnary}.
	 * @param ctx the parse tree
	 */
	void exitTermOptUnary(APEGParser.TermOptUnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(APEGParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(APEGParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(APEGParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(APEGParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#attrORfuncall}.
	 * @param ctx the parse tree
	 */
	void enterAttrORfuncall(APEGParser.AttrORfuncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#attrORfuncall}.
	 * @param ctx the parse tree
	 */
	void exitAttrORfuncall(APEGParser.AttrORfuncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#attribute_ref}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_ref(APEGParser.Attribute_refContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#attribute_ref}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_ref(APEGParser.Attribute_refContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(APEGParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(APEGParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#actPars}.
	 * @param ctx the parse tree
	 */
	void enterActPars(APEGParser.ActParsContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#actPars}.
	 * @param ctx the parse tree
	 */
	void exitActPars(APEGParser.ActParsContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#equalityOp}.
	 * @param ctx the parse tree
	 */
	void enterEqualityOp(APEGParser.EqualityOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#equalityOp}.
	 * @param ctx the parse tree
	 */
	void exitEqualityOp(APEGParser.EqualityOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#relOp}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(APEGParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#relOp}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(APEGParser.RelOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#addOp}.
	 * @param ctx the parse tree
	 */
	void enterAddOp(APEGParser.AddOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#addOp}.
	 * @param ctx the parse tree
	 */
	void exitAddOp(APEGParser.AddOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#mulOp}.
	 * @param ctx the parse tree
	 */
	void enterMulOp(APEGParser.MulOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#mulOp}.
	 * @param ctx the parse tree
	 */
	void exitMulOp(APEGParser.MulOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link APEGParser#meta_peg}.
	 * @param ctx the parse tree
	 */
	void enterMeta_peg(APEGParser.Meta_pegContext ctx);
	/**
	 * Exit a parse tree produced by {@link APEGParser#meta_peg}.
	 * @param ctx the parse tree
	 */
	void exitMeta_peg(APEGParser.Meta_pegContext ctx);
}