package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {

    protected AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s){
        enterParser("suite");

        AspSuite st = new AspSuite(s.curLineNum());

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
