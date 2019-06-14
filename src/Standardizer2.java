import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Standardizer2 extends JavaParserBaseListener {
    private BufferedTokenStream tokens;
    TokenStreamRewriter rewriter;
    private int tabCnt = 0;

    public Standardizer2(BufferedTokenStream tokens) {
        this.tokens = tokens;
        rewriter = new TokenStreamRewriter(tokens);
    }



    @Override
    public void visitTerminal(TerminalNode node) {
        String regex2 = "=|-=|\\+=|\\*=|/=|<|==|<=|>=|!=|>|\\+|-|\\*|/";
        if (node.getText().equals("{")) {
            int index = node.getSymbol().getTokenIndex() + 1;
            Token t = tokens.get(index);
            tabCnt++;
            String s = "\n";
            for (int i = 1; i <= tabCnt; i++) {
                s += "\t";
            }
            if (t.getText().trim().length() == 0) {
                rewriter.replace(index, s);
            } else {
                rewriter.insertBefore(t, s);
            }
            index = index - 2;
            t = tokens.get(index);
            if (t.getText().trim().length() == 0) {
                rewriter.delete(index);
            }
        } else if (node.getText().equals("}")) {
            int index = node.getSymbol().getTokenIndex() - 1;
            Token t = tokens.get(index);
            tabCnt--;
            String s = "\n";
            for (int i = 1; i <= tabCnt; i++) {
                s += "\t";
            }
            if (t.getText().trim().length() == 0) {
                rewriter.replace(index, s);
            } else {
                rewriter.insertAfter(t, s);
                //index++;
            }
            index=index+2;
            t=tokens.get(index);
            if(t.getText().trim().length()==0){
                rewriter.replace(t,s);
            }
            else{
                rewriter.insertBefore(t,s);
            }
        } else if (Pattern.matches(regex2, node.getText())) {
            int index = node.getSymbol().getTokenIndex();
            Token t = tokens.get(index - 1);
            String s = " ";
            if (t.getText().trim().length() == 0) {
                rewriter.replace(index - 1, s);
            } else {
                rewriter.insertBefore(index, s);
            }
            t = tokens.get(index + 1);
            if (t.getText().trim().length() == 0) {
                rewriter.replace(index + 1, s);
            } else {
                rewriter.insertBefore(index + 1, s);
            }
        } else if (node.getText().equals(";")) {
            if (!node.getParent().getChild(0).getText().equals("for")) {
                Token t = node.getSymbol();
                int index = t.getTokenIndex();
                index++;
                t = tokens.get(index);
                String s = "\n";
                for (int i = 1; i <= tabCnt; i++) {
                    s += "\t";
                }
                if (t.getText().trim().length() == 0) {
                    rewriter.replace(index, s);
                } else if (t.getText().equals("}")) {

                } else {
                    rewriter.insertBefore(t, s);
                }
            }
        } else if(node.getText().equals("else")){
            Token t=node.getSymbol();
            int index = t.getTokenIndex();
            index--;
            t = tokens.get(index);
            if (t.getText().trim().length() == 0) {
                rewriter.replace(index, " ");
            } else if (t.getText().equals("}")) {

            } else {
                rewriter.insertBefore(t, " ");
            }
        }
    }


    @Override
    public void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
        JavaParser.MemberDeclarationContext md = ctx.memberDeclaration();
        JavaParser.MethodDeclarationContext med = md.methodDeclaration();
        if(med!=null){
            Token token=ctx.getStart();
            String s = "\n";
            for (int i = 1; i <= tabCnt; i++) {
                s += "\t";
            }
            rewriter.insertBefore(token,s);
        }
    }

//    @Override
//    public void enterStatement(JavaParser.StatementContext ctx) {
//        Token token = ctx.getStart();
//        if (token.getText().equals("if")) {
//
//        }
//    }

}
