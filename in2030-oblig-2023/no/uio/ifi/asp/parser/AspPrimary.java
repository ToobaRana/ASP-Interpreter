package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimary extends AspSyntax {

    protected AspPrimary(int n) {
        super(n);
    }

    static AspPrimary parse(Scanner s){
        enterParser("primary");
        AspPrimary p = new AspPrimary(s.curLineNum());
        leaveParser("primary");
        return p;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
