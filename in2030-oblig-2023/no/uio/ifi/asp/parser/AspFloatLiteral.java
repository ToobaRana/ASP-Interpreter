package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFloatLiteral extends AspAtom{
    double floatNum;

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s){
        enterParser("float literal");

        AspFloatLiteral fl = new AspFloatLiteral(s.curLineNum());
        fl.floatNum = s.curToken().floatLit;

        leaveParser("float literal");
        return fl;
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

