package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNotTest extends AspSyntax {
    AspComparison comparison;

    protected AspNotTest(int n) {
        super(n);
    }

    static AspNotTest parse(Scanner s){

        enterParser("not test");

        AspNotTest nt = new AspNotTest(s.curLineNum());

        if(s.curToken().kind == notToken){
            skip(s, notToken);
        }

        nt.comparison = AspComparison.parse(s);

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
