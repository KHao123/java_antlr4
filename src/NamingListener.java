import org.antlr.v4.runtime.TokenStream;

public class NamingListener extends Java9BaseListener{
    Java9Parser parser;
    public NamingListener(Java9Parser parser){
        this.parser=parser;
    }

    @Override
    public void enterPackageName(Java9Parser.PackageNameContext ctx){
        TokenStream tokens=parser.getTokenStream();
        String name=tokens.getText(ctx.identifier()) ;
        System.out.println(name.toLowerCase());
    }
}
