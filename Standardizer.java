import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import  java.util.regex.Matcher;

public class Standardizer extends JavaParserBaseListener {
    private BufferedTokenStream tokens;
    TokenStreamRewriter rewriter;
    private Map<String, String> classNameModify = new HashMap<>();
    private Map<String, String> methodNameModify = new HashMap<>();
    private Map<String, String> constNameModify = new HashMap<>();
    private Map<String, String> nonconstNameModify = new HashMap<>();
    private int tabCnt = 0;
//    private int flag=0;

    public Standardizer(BufferedTokenStream tokens) {
        this.tokens = tokens;
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        JavaParser.QualifiedNameContext context = ctx.qualifiedName();
        List<TerminalNode> nameList = context.IDENTIFIER();
        for (TerminalNode t : nameList) {
            Token token = t.getSymbol();
            String s0 = token.getText();
            String s1 = s0.toLowerCase();
            if (!s0.equals(s1)) {
                rewriter.replace(token, s1);
                Main.warning.add(token);
            }
        }
    }

    @Override
    public void enterClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx) {
        List<TerminalNode> nameList = ctx.IDENTIFIER();
        for (TerminalNode t : nameList) {
            Token token = t.getSymbol();
            String s0 = token.getText();
            if (classNameModify.containsKey(s0)) {
                rewriter.replace(token, classNameModify.get(s0));
                Main.warning.add(token);
            } else {
                String s1 = underline2Camel(s0, true);
                if (!s1.equals(s0)) {
                    classNameModify.put(s0, s1);
                    rewriter.replace(token, s1);
                    Main.warning.add(token);
                }
            }
        }
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        TerminalNode t = ctx.IDENTIFIER();
        Token token = t.getSymbol();
        String s0 = token.getText();
        if (classNameModify.containsKey(s0)) {
            rewriter.replace(token, classNameModify.get(s0));
            Main.warning.add(token);
        } else {
            String s1 = underline2Camel(s0, true);
            if (!s1.equals(s0)) {
                classNameModify.put(s0, s1);
                rewriter.replace(token, s1);
                Main.warning.add(token);
            }
        }
    }

    @Override
    public void enterCreatedName(JavaParser.CreatedNameContext ctx) {
        List<TerminalNode> nameList = ctx.IDENTIFIER();
        for (TerminalNode t : nameList) {
            Token token = t.getSymbol();
            String s0 = token.getText();
            if (classNameModify.containsKey(s0)) {
                rewriter.replace(token, classNameModify.get(s0));
                Main.warning.add(token);
            } else {
                String s1 = underline2Camel(s0, true);
                if (!s1.equals(s0)) {
                    classNameModify.put(s0, s1);
                    rewriter.replace(token, s1);
                    Main.warning.add(token);
                }
            }
        }
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        TerminalNode t = ctx.IDENTIFIER();
        Token token = t.getSymbol();
        String s0 = token.getText();
        if (methodNameModify.containsKey(s0)) {
            rewriter.replace(token, methodNameModify.get(s0));
            Main.warning.add(token);
        } else {
            String s1 = underline2Camel(s0, false);
            if (!s1.equals(s0)) {
                methodNameModify.put(s0, s1);
                rewriter.replace(token, s1);
                Main.warning.add(token);
            }
        }
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        TerminalNode t = ctx.IDENTIFIER();
        Token token = t.getSymbol();
        String s0 = token.getText();
        if (methodNameModify.containsKey(s0)) {
            rewriter.replace(token, methodNameModify.get(s0));
            Main.warning.add(token);
        } else {
            String s1 = underline2Camel(s0, false);
            if (!s1.equals(s0)) {
                methodNameModify.put(s0, s1);
                rewriter.replace(token, s1);
                Main.warning.add(token);
            }
        }
    }

    @Override
    public void enterVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx) {
        TerminalNode t = ctx.IDENTIFIER();
        Token token = t.getSymbol();
        int index = token.getTokenIndex() - 2;
        Token token0 = tokens.get(index);
        int flag = 0;
        String ss = token0.getText();
        while (!ss.equals(";") && !ss.equals("{") && !ss.equals("}") && !ss.equals("(")) {
            index--;
            token0 = tokens.get(index);
            ss = token0.getText();
            if (ss.equals("final")) {
                flag = 1;
                String s0 = token.getText();
                String s1 = s0.toUpperCase();
                if (!s1.equals(s0)) {
                    constNameModify.put(s0, s1);
                    rewriter.replace(token, s1);
                    Main.warning.add(token);
                    break;
                }
            }
        }
        if (flag == 0) {
            String s0 = token.getText();
            if (nonconstNameModify.containsKey(s0)) {
                rewriter.replace(token, nonconstNameModify.get(s0));
                Main.warning.add(token);
            } else {
                String s1 = underline2Camel(s0, false);
                if (!s1.equals(s0)) {
                    nonconstNameModify.put(s0, s1);
                    rewriter.replace(token, s1);
                    Main.warning.add(token);
                }
            }
        }

    }

    @Override
    public void enterPrimary(JavaParser.PrimaryContext ctx) {
        TerminalNode t = ctx.IDENTIFIER();
        if (t != null) {
            Token token = t.getSymbol();
            String s0 = token.getText();
            if (nonconstNameModify.containsKey(s0)) {
                rewriter.replace(token, nonconstNameModify.get(s0));
                Main.warning.add(token);
            } else if (constNameModify.containsKey(s0)) {
                rewriter.replace(token, constNameModify.get(s0));
                Main.warning.add(token);
            } else {
                String s1 = underline2Camel(s0, false);
                if (!s1.equals(s0)) {
                    nonconstNameModify.put(s0, s1);
                    rewriter.replace(token, s1);
                    Main.warning.add(token);
                }
            }
        }
    }

//    @Override
//    public void enterStatement(JavaParser.StatementContext ctx){
//        TerminalNode node=ctx.RPAREN();
//        if(node!=null){
//            Token token=node.getSymbol();
//            int index=token.getTokenIndex();
//            Token t=tokens.get(index+1);
//            if(t.getText().trim().length()==0){
//                index=index+2;
//                t=tokens.get(index);
//            }
//            if(!t.getText().equals("{")){
//                flag=1;
//                tabCnt++;
//                String s="{\n";
//                for(int i=1;i<=tabCnt;i++){
//                    s+="\t";
//                }
//                rewriter.insertAfter(token,s);
//                //token=ctx.getStop();
//                //rewriter.insertAfter(token,s);
//            }
//        }
//
//    }

    @Override
    public void exitStatement(JavaParser.StatementContext ctx) {
        Token token = ctx.getStart();
        if (token.getText().equals("for")) {
            JavaParser.ForControlContext fc = ctx.forControl();
            JavaParser.ForInitContext fi = fc.forInit();
            Token t = fi.getStart();
            int index = t.getTokenIndex();
            String s = "";
            while (index <= fi.getStop().getTokenIndex()) {
                s += t.getText();
                t = tokens.get(++index);
            }
            s += ";\n";
//            for(int i=1;i<=tabCnt;i++){
//                s+="\t";
//            }
            rewriter.insertBefore(token, s);
            rewriter.replace(token, "while");
            JavaParser.ExpressionContext ec = fc.expression();
            JavaParser.ExpressionListContext el = fc.expressionList();
            rewriter.delete(fc.getStart(), fc.getStop());
            t = ec.getStart();
            index = t.getTokenIndex();
            s = "";
            while (index <= ec.getStop().getTokenIndex()) {
                s += t.getText();
                t = tokens.get(++index);
            }
            rewriter.insertAfter(ctx.LPAREN().getSymbol(), s);
            token = ctx.getStop();
            if (token.getText().equals("}")) {
                t = el.getStart();
                index = t.getTokenIndex();
                s = "";
                while (index <= el.getStop().getTokenIndex()) {
                    s += t.getText();
                    t = tokens.get(++index);
                }
                s += ";\n";
//                for(int i=1;i<=tabCnt;i++){
//                    s+="\t";
//                }
//                s="\t"+s;
                rewriter.insertBefore(token, s);
            } else {
                t = el.getStart();
                index = t.getTokenIndex();
                s = "";
                while (index <= el.getStop().getTokenIndex()) {
                    s += t.getText();
                    t = tokens.get(++index);
                }
                s += ";}";
                rewriter.insertAfter(token, s);
                token = ctx.RPAREN().getSymbol();
                index = token.getTokenIndex();
                rewriter.insertAfter(index, "{");
            }

        }
//        else if(token.getText().equals("while")){
//            rewriter.replace(token,"for");
//            JavaParser.ParExpressionContext pe = ctx.parExpression();
//            rewriter.insertAfter(pe.getStart(),";");
//            rewriter.insertBefore(pe.getStop(),";");
//        }
//        if(flag==1){
//            flag=0;
//            tabCnt--;
//            token=ctx.getStop();
//            String s="\n";
//            for(int i=1;i<=tabCnt;i++){
//                s+="\t";
//            }
//            s+="}\n";
//            rewriter.insertAfter(token,s);
//        }
        else if(token.getText().equals("if")){
            List<JavaParser.StatementContext>  staList=ctx.statement();
            JavaParser.StatementContext sta=staList.get(0);
            Token t=sta.getStart();
            if(!t.getText().equals("{")){
                rewriter.insertBefore(t,"{");
                t=sta.getStop();
                rewriter.insertAfter(t,"}");
            }
            sta=staList.get(1);
            if(sta!=null){
                t=sta.getStart();
                if(!t.getText().equals("{")&&!t.getText().equals("if")){
                    rewriter.insertBefore(t,"{");
                    t=sta.getStop();
                    rewriter.insertAfter(t,"}");
                }
                if(t.getText().equals("if")){
                    int index=t.getTokenIndex();
                 //   Token t=
                }
            }
        }
    }

//    @Override
//    public void enterBlockStatement(JavaParser.BlockStatementContext ctx) {
//        Token token=ctx.getStop();
//        if(token.getText().equals(";")){
//            int index=token.getTokenIndex();
//            index++;
//            if(tokens.get(index).getText().equals("}")||tokens.get(index+1).getText().equals("}")){
//                tabCnt--;
//            }
//            Token t=tokens.get(index);
//            String s="\n";
//            for(int i=1;i<=tabCnt;i++){
//                s+="\t";
//            }
//
//            if(t.getText().trim().length()==0){
//                rewriter.replace(index,s);
//            }
//            else{
//                rewriter.insertBefore(t,s);
//            }
//        }
//    }

//    @Override
//    public void visitTerminal(TerminalNode node) {
//        String regex2="=|-=|\\+=|\\*=|/=|<|==|<=|>=|!=|>";
//        if(node.getText().equals("{")){
//            int index=node.getSymbol().getTokenIndex()+1;
//            Token t=tokens.get(index);
//            tabCnt++;
//            String s="\n";
//            for(int i=1;i<=tabCnt;i++){
//                s+="\t";
//            }
//            if(t.getText().trim().length()==0){
//                rewriter.replace(index,s);
//            }
//            else{
//                rewriter.insertBefore(t,s);
//            }
//            index=index-2;
//            t=tokens.get(index);
//            if(t.getText().trim().length()==0){
//                rewriter.delete(index);
//            }
//        }
//        else if(node.getText().equals("}")){
//            int index=node.getSymbol().getTokenIndex()-1;
//            Token t=tokens.get(index);
//            if(tokens.get(index).getText().equals("}")||tokens.get(index-1).getText().equals("}")||tokens.get(index).getText().equals("{")||tokens.get(index-1).getText().equals("{")){
//                tabCnt--;
//                String s="\n";
//                for(int i=1;i<=tabCnt;i++){
//                    s+="\t";
//                }
//                if(t.getText().trim().length()==0){
//                    rewriter.replace(index,s);
//                }
//                else{
//                    rewriter.insertAfter(t,s);
//                }
//            }
//        }
//
//        else if(Pattern.matches(regex2,node.getText())){
//            int index=node.getSymbol().getTokenIndex();
//            Token t=tokens.get(index-1);
//            String s=" ";
//            if(t.getText().trim().length()==0){
//                rewriter.replace(index-1,s);
//            }
//            else{
//                rewriter.insertBefore(index,s);
//            }
//            t=tokens.get(index+1);
//            if(t.getText().trim().length()==0){
//                rewriter.replace(index+1,s);
//            }
//            else{
//                rewriter.insertBefore(index+1,s);
//            }
//        }
//    }

    private String underline2Camel(String line, boolean upperCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if (!upperCamel && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}
