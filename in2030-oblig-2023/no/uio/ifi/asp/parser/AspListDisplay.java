package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {

    ArrayList<AspExpr> exprs = new ArrayList<>();
    Boolean metExpr = false;

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s) {

        enterParser("list display");

        AspListDisplay ld = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        if (s.curToken().kind != rightBracketToken) {
            ld.metExpr = true;
            while (true) {
                ld.exprs.add(AspExpr.parse(s));

                if (s.curToken().kind != commaToken) {
                    break;
                }

                skip(s, commaToken);
            }
        }

        skip(s, rightBracketToken);

        leaveParser("list display");
        return ld;
    }

    @Override
    void prettyPrint() {

        prettyWrite("[");

        int nPrinted = 0;

        for (AspExpr ae : exprs) {

            if (nPrinted > 0) {
                prettyWrite(", ");
            }

            ae.prettyPrint();
            ++nPrinted;
        }

        prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if (metExpr) {

            RuntimeValue v = exprs.get(0).eval(curScope);

            for (int i = 1; i < exprs.size(); ++i) {
                if (!v.getBoolValue(", operand", this)) {
                    return v;
                }
                v = exprs.get(i).eval(curScope);
            }
            return v;
        }
        return null;
    }
}
