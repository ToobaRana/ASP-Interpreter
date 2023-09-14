package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

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
