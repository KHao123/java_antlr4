import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

public class NamingListener extends Java9BaseListener{
    Java9Parser parser;
    public NamingListener(Java9Parser parser){
        this.parser=parser;
    }

    @Override
    public void enterPackageName(Java9Parser.PackageNameContext ctx){
        //TokenStream tokens=parser.getTokenStream();
       // String name=tokens.getText(ctx.identifier());
        //System.out.print(name.toLowerCase());
    }

    @Override
    public void enterIdentifier(Java9Parser.IdentifierContext ctx) {
        //TokenStream tokens = parser.getTokenStream();
        String name = ctx.getText();
        if(ctx.parent.toString()=="packageName") name.toLowerCase();
        //System.out.print(name);
    }

    @Override
    public void visitTerminal(TerminalNode t){
        CommonToken tok=(CommonToken) t.getSymbol();
        tok.setText(tok.getText().toLowerCase());
        System.out.print(t.getText()+" ");
    }
}
