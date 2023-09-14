package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

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
