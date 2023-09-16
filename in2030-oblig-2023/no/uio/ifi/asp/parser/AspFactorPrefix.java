package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax {

    protected AspFactorPrefix(int n) {
        super(n);
    }

    static AspFactorPrefix parse(Scanner s){
        enterParser("factor prefix");

        AspFactorPrefix fp = new AspFactorPrefix(s.curLineNum());
        skip(s, s.curToken().kind);

        leaveParser("factor prefix");
        return fp;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
