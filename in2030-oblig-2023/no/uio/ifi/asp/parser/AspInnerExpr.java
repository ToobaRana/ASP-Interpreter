package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom {

    AspExpr expr;

    AspInnerExpr(int n) {
        super(n);
    }

    static AspInnerExpr parse(Scanner s) {

        enterParser("inner expr");

        AspInnerExpr ie = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        ie.expr = AspExpr.parse(s);
        skip(s, rightParToken);

        leaveParser("inner expr");
        return ie;
    }

    @Override
    void prettyPrint() {
        prettyWrite("(");
        expr.prettyPrint();
        prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

}
