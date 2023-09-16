package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspStringLiteral extends AspAtom{

    AspStringLiteral(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspStringLiteral parse(Scanner s){
        enterParser("");
        AspStringLiteral t = new AspStringLiteral(s.curLineNum());
        leaveParser("");
        return null;
    }

    @Override
    void prettyPrint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
