package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSuite extends AspSyntax {

    protected AspSuite(int n) {
        super(n);
        // TODO Auto-generated constructor stub
    }

    static AspSuite parse(Scanner s){
        enterParser("");
        AspSuite t = new AspSuite(s.curLineNum());
        leaveParser("");
        return null;
    }


    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
