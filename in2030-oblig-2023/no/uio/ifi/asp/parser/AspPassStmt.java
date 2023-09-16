package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPassStmt extends AspSmallStmt {

    AspPassStmt(int n) {
        super(n);
    }

    static AspPassStmt parse(Scanner s){
        enterParser("pass stmt");

        AspPassStmt ps = new AspPassStmt(s.curLineNum());
        skip(s, passToken);
        leaveParser("pass stmt");

        return ps;
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
