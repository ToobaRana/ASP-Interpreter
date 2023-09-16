package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspSuite extends AspSyntax {
    AspSmallStmtList smallStmtList;
    ArrayList<AspStmt> stmts = new ArrayList<>();

    protected AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s){
        enterParser("suite");

        AspSuite st = new AspSuite(s.curLineNum());

        if (s.curToken().kind == newLineToken) {
            skip(s, newLineToken);
            skip(s, indentToken);
            
            while(s.curToken().kind != dedentToken){
                st.stmts.add(AspStmt.parse(s));
            }

            skip(s, dedentToken);
        } 
        
        
        else {
            st.smallStmtList = AspSmallStmtList.parse(s);
        }

        leaveParser("suite");
        return st;
    }


    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
