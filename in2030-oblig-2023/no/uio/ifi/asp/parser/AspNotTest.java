package no.uio.ifi.asp.parser.ASPfiler;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspNotTest extends AspSyntax {

    protected AspNotTest(int n) {
        super(n);
        // TODO Auto-generated constructor stub
    }

    static AspNotTest parse(Scanner s) {
        enterParser("and test");


        leaveParser("and test");
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
