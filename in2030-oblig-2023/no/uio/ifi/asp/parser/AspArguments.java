package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix {

    ArrayList<AspExpr> exprList = new ArrayList<>();
    Boolean metExpr = false;

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s) {

        enterParser("arguments");

        AspArguments a = new AspArguments(s.curLineNum());

        skip(s, leftParToken);

        if (s.curToken().kind != rightParToken) {
            a.metExpr = true;
            while (true) {
                a.exprList.add(AspExpr.parse(s));

                if (s.curToken().kind != commaToken) {
                    break;
                }
                skip(s, commaToken);
            }
        }

        skip(s, rightParToken);

        leaveParser("arguments");
        return a;
    }

    @Override
    void prettyPrint() {

        prettyWrite("(");

        int nPrinted = 0;

        for (AspExpr ae : exprList) {

            if (nPrinted > 0) {
                prettyWrite(", ");
            }

            ae.prettyPrint();
            ++nPrinted;
        }

        prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        if (metExpr) {

            RuntimeValue v = exprList.get(0).eval(curScope);

            for (int i = 1; i < exprList.size(); ++i) {
                if (!v.getBoolValue(", operand", this)) {
                    return v;
                }
                v = exprList.get(i).eval(curScope);
            }
            return v;
        }
        return null;
    }

}
