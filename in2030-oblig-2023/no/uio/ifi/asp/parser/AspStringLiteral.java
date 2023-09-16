package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStringLiteral extends AspAtom{

    AspStringLiteral(int n) {
        super(n);
    }

    static AspStringLiteral parse(Scanner s){
        enterParser("string literal");

        AspStringLiteral sl = new AspStringLiteral(s.curLineNum());

        leaveParser("string literal");
        return sl;
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
