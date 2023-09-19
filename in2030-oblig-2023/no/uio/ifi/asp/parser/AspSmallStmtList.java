package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {

    ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();

    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        TokenKind cur = s.curToken().kind;

        AspSmallStmtList ssl = new AspSmallStmtList(s.curLineNum());

        while (true) {
            ssl.smallStmts.add(AspSmallStmt.parse(s));

            if (cur != semicolonToken) {
                break;
            }

            if (cur == semicolonToken) {
                skip(s, semicolonToken);
            }

            if (cur == newLineToken){
                break;
            }
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
