package apeg.rts;
import java.io.IOException;
import java.util.Stack;

/**
 * StatefullBaseParser is framework for sate based descent recursive parser expression grammar. 
 * This class provides a colection of functions in a DSL style to implement descent
 * recursive parsers as a state machine. The state is composed by a stream of text, a stack of makrs 
 * (kept in the straem), the result of the last parsing expression grammar and the derivation tree.  
 *
 *
 * @author Deise Kelley
 * @author Leonardo V. S. Reis
 * @author Elton M Cardoso
 * 
 * Last modification 21/04/2018  
 *
 */

public abstract class StateFullBaseParser {
    
    /**
     * Inner class for the stack's symbol. For allowing each
     * Symbol on the stack to have a mark or not. 
     */
    private class SContainer {
        public Symbol sym;
        public boolean marked; 
        SContainer(Symbol s){ this.sym = s; marked = false;}
        public String toString() {
        	return sym.toString();
        }
    }
    
    private Stack<Symbol> stk;
    private Stack<RuleInfo> ruleStk;
    private Stack<SContainer> stkMark;
    private boolean result;
    
    private PageStream s;
    
    /**
     * Default constructor. Construct a parser for processsing the given file.
     * @param fname: The name of the input file.
     */
    public StateFullBaseParser(String fname){
        stk = new Stack<Symbol>();
        ruleStk = new Stack<RuleInfo>();
        stkMark = new Stack<SContainer>();
        result = true;
        startRule("root"); // The magic root is in place :-) !
        try {
            s = new PageStream(fname);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /** 
     * Starts a new rule. Pushes a new rule context into rule context stack.
     * @param ruleName: the of the rule that just started.
     */
    
    public void startRule(String ruleName){
        ruleStk.push(new RuleInfo(ruleName));
    }
    
    /** 
     * End the current rule and return a sucessfull result. 
     * Buil the derivation tree with the current rule being the root. Remove the current
     * rule from the rule context, and update the underneath rule context incremenig the number  
     * of subexpression recognized.
     * @param ruleName: the of the rule that just started.
     * @return A sucessfull PegResult
     */
    
    public PegResult success(){
    NonTerminal raiz = new NonTerminal(ruleStk.peek().rname);
        for(int i=0; i < ruleStk.peek().n;i++){
            raiz.addFrontChilds(stk.pop());
        }
        stk.push(raiz);
        ruleStk.pop();
        ruleStk.peek().n++;
        result = true;
        return new PegResult(result);
    }
    
    /**
     * Set the result to true. Diferently from success, this function only affect the result state. 
     * Nothing is done to the symbol stack or rule stack.
     */
    public void done(){
        result = true;
    }
    
    /**
     * Set the result to false. Diferently from fail, this function won't clear all the symbols
     * pushed since the start of the rule, but only the sybols pushed since the last result.
     */
    public void failure(){
        retracStack();
        result = false;
    }
    
    
    /**
     * Utility method to clear the stack of the current rule. 
     * Remove all symbols pushed since the last startRule invocation;  
     */
    private void clearStack(){
        for(int i=0; i< ruleStk.peek().n;i++){
            stk.pop();
        }
        ruleStk.peek().n = 0;
    }
    
    /**
     * Remove the symbols pushed since the last mark. Like clear stack,
     * but only remove the symbols since the last mark.
     */
    private void retracStack(){
        int i =0;
        for(i=0; (i< ruleStk.peek().n) && (stk.peek() != stkMark.peek().sym);i++){
            stk.pop();
        }
        ruleStk.peek().n = ruleStk.peek().n - i;
    }
    
    /**
     * Utility method to put the machine in a "failed state". 
     * Remove all symbols pushed since the last startRule invocation,
     * remove the lsat rule inf from the rule context 
     */
    private void makeFailState(){
        clearStack();
        ruleStk.pop();
        result = false;
    }
    
    /**
     * End the current rule and return a failed result.
     * remove all the symbols from related to the current rule from the stack,
     * remove the current ruel contexto from the stack and resturn a fail PegResult.
     * @return A failed peg result.
     */
    public PegResult fail(){
        makeFailState();
        return new PegResult(result);
    }
    
    /**
     * End the current rule and return a failed result.
     * Overloaded version of fail that includes an error msg to 
     * the PegResult.
     * @return A failed peg result.
     */
    public PegResult fail(String msg){
        makeFailState();
        return new PegResult(msg);
    }
    
    /**
     * Start a state where choices can be made. 
     * Mark the current position in the input stream so that 
     * the input strem can return to this point later. 
     */
    public void mkBacktracPoint(){
        mark();
    }
    
    /**
     * End a state where choices can be made. 
     * Undo the last mark to the current position in the input stream.
     */
    public void dismissBacktracPoint(){
        unmark();
    }
    
    /**
     * Retract the current branch of the derivation tree and restore the input stream to the point
     * where it was when the last starBactrackPoint was called.
     * Clear the symbol stack an restore to a previos made mark. After that it mark the input stream again so 
     * that it can try another alternative. Note tha this function don't end a rule, i.e., the rule info of the current
     * rule is never removed from rule stack.
     */
    public void undo(){
        retracStack();
        restore();
    }
    
    /**
     * Consult the las result.
     * Return true if the las operation was successfull.
     */
    public boolean isOk(){ return result;}

    /**
     * Try to match the given string in the input from the current position.
     * This operation either succed and consume the input or it fails without consuming any input.
     * @return True if the given string match the input, false otherwise. 
     */
    public boolean match(String p){
        try {
            if(s.match(p)){
                stk.push(new Terminal(0,0,p));
                ruleStk.peek().n++;
                result = true;
                return result;
            }else{
                result = false;
                return result;
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = false;
        return result;
    }
    
    
    /**
     * Overloaded version of macth for a single char.
     * This operation either succed and consume the input or it fails without consuming any input.
     * @return True if the given char match the input, false otherwise. 
     */
    public boolean match(char c){ return match(""+c);}
    
    /**
     * Match the next input char regardlees of eich character it is. 
     * This operation either succed and consume the input or it fails without consuming any input.
     * @return True if it was possible to retrive a char from the input, false otherwise. 
     */
    public boolean matchNext(){
         try {
            char c = s.next();
            stk.push(new Terminal(0,0,c+""));
        ruleStk.peek().n++;
        result = true;
        return result;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = false;
        return result;
    }
    
    /**
     * Returns the top of the symbol stack. 
     * At the end of the process, this top of the stack should contain the root of derivation tree.
     * @return The top of the symbol stack. 
     */
    public Symbol getDerivationTreeRoot() {
        return stk.peek();
    }
    
    public void mark() {
        s.mark();
        stkMark.push(new SContainer(stk.isEmpty() ? null: stk.peek() ));
    }
    
    public void unmark() {
        stkMark.pop();
        s.unmark();
    }
    
    public void restore() {
        s.restore();
        retracStack();
        stkMark.pop();
    }

}
