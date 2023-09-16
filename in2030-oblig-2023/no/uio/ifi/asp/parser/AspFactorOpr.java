package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax {

    protected AspFactorOpr(int n) {
        super(n);
    }

    static AspFactorOpr parse(Scanner s){
        enterParser("factor opr");

        AspFactorOpr fo = new AspFactorOpr(s.curLineNum());
        skip(s, s.curToken().kind);

        leaveParser("factor opr");
        return fo;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
