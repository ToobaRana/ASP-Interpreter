package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

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
