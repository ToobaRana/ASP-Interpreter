package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspNotTest extends AspSyntax {

    protected AspNotTest(int n) {
        super(n);
    }

    static AspNotTest parse(Scanner s){

        enterParser("not test");

        AspNotTest nt = new AspNotTest(s.curLineNum());

        

        leaveParser("not test");
        return nt;
    }



    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
