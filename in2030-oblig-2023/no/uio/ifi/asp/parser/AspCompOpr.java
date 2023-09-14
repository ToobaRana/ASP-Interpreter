package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspCompOpr extends AspSyntax {

    protected AspCompOpr(int n) {
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        enterParser("comp opr");

        AspCompOpr co = new AspCompOpr(s.curLineNum());
        skip(s, s.curToken().kind);

        leaveParser("comp opr");
        return co;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
