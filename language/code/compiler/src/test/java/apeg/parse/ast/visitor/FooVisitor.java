

public class FooVisitor implements ASTNodeVisitor {


    void visit(AttributeExprNode expr) {
	System.out.println("AttributeNode - Name: " expr.getName());
    }
}
