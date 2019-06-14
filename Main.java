//public class Main {
//
//    public static void main(String[] args) {
//	// write your code here
//    }
//}
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static Queue<Token> warning;
    public static void main(String[] args) throws Exception {
        // write your code here
        warning=new LinkedList<Token>();
        String inputFile=null;
        if(args.length>0) inputFile=args[0];
        else return;
        CharStream input=CharStreams.fromFileName(inputFile);
        JavaLexer lexer=new JavaLexer(input);
        CommonTokenStream tokens=new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        //System.out.println(tree.toStringTree(parser));
        ParseTreeWalker walker = new ParseTreeWalker();
        Standardizer sta=new Standardizer(tokens);
        walker.walk(sta,tree);
        System.out.println(sta.rewriter.getText());

        input=CharStreams.fromString(sta.rewriter.getText());
        lexer=new JavaLexer(input);
        tokens=new CommonTokenStream(lexer);
        parser = new JavaParser(tokens);
        tree = parser.compilationUnit();
        walker = new ParseTreeWalker();
        Standardizer2 sta2=new Standardizer2(tokens);
        walker.walk(sta2,tree);
        System.out.println(sta2.rewriter.getText());








        while(!warning.isEmpty()){
            CommonToken t=(CommonToken) warning.poll();
            System.out.println("nonstandard name in line "+t.getLine()+" : "+t.getText());
        }
    }
}
