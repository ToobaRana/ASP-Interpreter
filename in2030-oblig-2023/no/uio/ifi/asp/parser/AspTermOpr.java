package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspTermOpr extends AspSyntax {

    protected AspTermOpr(int n) {
        super(n);
    }

    static AspTermOpr parse(Scanner s){
        enterParser("term opr");
        AspTermOpr to = new AspTermOpr(s.curLineNum());

        skip(s, s.curToken().kind);

        leaveParser("term opr");
        return to;
    }


    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
