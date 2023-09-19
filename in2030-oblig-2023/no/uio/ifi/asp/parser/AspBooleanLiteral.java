package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspBooleanLiteral extends AspAtom{

    TokenKind booleanVal;

    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s){

        enterParser("boolean literal");

        AspBooleanLiteral bl = new AspBooleanLiteral(s.curLineNum());
        
        bl.booleanVal = s.curToken().kind;
        skip(s, bl.booleanVal);

        leaveParser("boolean literal");
        return bl;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + booleanVal + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
