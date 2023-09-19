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
        //TokenKind cur = s.curToken().kind;

        AspSmallStmtList ssl = new AspSmallStmtList(s.curLineNum());

        while (true) {
            ssl.smallStmts.add(AspSmallStmt.parse(s));

            if (s.curToken().kind != semicolonToken) {
                break;
            }

            if (s.curToken().kind == semicolonToken) {

                skip(s, semicolonToken);
            }

            if (s.curToken().kind == newLineToken){

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
