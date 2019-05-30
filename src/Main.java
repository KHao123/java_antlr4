import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        String inputFile=null;
        if(args.length>0) inputFile=args[0];
        else return;
        //InputStream is=System.in;
        //if(inputFile!=null) is=new FileInputStream(inputFile);
        //ANTLRInputStream input=new ANTLRInputStream(is);
        CharStream input=CharStreams.fromFileName(inputFile);
        Java9Lexer lexer=new Java9Lexer(input);
        CommonTokenStream tokens=new CommonTokenStream(lexer);
        Java9Parser parser = new Java9Parser(tokens);
        ParseTree tree = parser.compilationUnit();
        //System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        NamingListener naming=new NamingListener(parser);
        walker.walk(naming,tree);
    }
}
