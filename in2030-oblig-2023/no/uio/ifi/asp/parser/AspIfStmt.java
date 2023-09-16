package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> exprList = new ArrayList<>();
    ArrayList<AspSuite> suiteList = new ArrayList<>();
    AspSuite suite;


    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s){
        enterParser("if stmt");

        AspIfStmt is = new AspIfStmt(s.curLineNum());

        skip(s, TokenKind.ifToken);

        while(true){
            is.exprList.add(AspExpr.parse(s));
            skip(s, TokenKind.colonToken);
            is.suiteList.add(AspSuite.parse(s));

            if (s.curToken().kind != TokenKind.elifToken) {
                break;
            }
            skip(s, TokenKind.elifToken);
        }

        if (s.curToken().kind == TokenKind.elseToken) {
            skip(s, TokenKind.elseToken);
            skip(s, TokenKind.colonToken);
            is.suite = AspSuite.parse(s);
        }

        leaveParser("if stmt");
        return is;
    }
    
}
