package no.uio.ifi.asp.parser;


import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmtList> smallStmtList = new ArrayList<>();

    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s){
        enterParser("small stmt list");

        AspSmallStmtList ssl = new AspSmallStmtList(s.curLineNum());

        while (true) {
            ssl.smallStmtList.add(AspSmallStmtList.parse(s));
            if (s.curToken().kind != TokenKind.semicolonToken) {
                break;
            }
            skip(s, TokenKind.semicolonToken);
        }

        if (s.curToken().kind == TokenKind.semicolonToken) {
            skip(s, TokenKind.semicolonToken);
        }

        skip(s, TokenKind.newLineToken);


        leaveParser("small stmt list");
        return ssl;
    }

    @Override
    void prettyPrint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
}
