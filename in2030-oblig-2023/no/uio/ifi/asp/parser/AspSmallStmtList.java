package no.uio.ifi.asp.parser;


import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
            if (s.curToken().kind != semicolonToken) {
                break;
            }
            skip(s, semicolonToken);
        }

        if (s.curToken().kind == semicolonToken) {
            skip(s, semicolonToken);
        }

        skip(s, newLineToken);


        leaveParser("small stmt list");
        return ssl;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
}
