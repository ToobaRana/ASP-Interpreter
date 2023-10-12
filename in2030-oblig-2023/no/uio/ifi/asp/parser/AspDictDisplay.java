package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom {

    ArrayList<AspStringLiteral> stringLitList = new ArrayList<>();
    ArrayList<AspExpr> exprList = new ArrayList<>();
    Boolean metString = false;

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s) {

        enterParser("dict display");

        AspDictDisplay dd = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        if (s.curToken().kind != rightBraceToken) {
            dd.metString = true;

            while (true) {
                dd.stringLitList.add(AspStringLiteral.parse(s));
                skip(s, colonToken);
                dd.exprList.add(AspExpr.parse(s));

                if (s.curToken().kind != commaToken) {
                    break;
                }
                skip(s, commaToken);
            }
        }

        skip(s, rightBraceToken);

        leaveParser("dict display");
        return dd;
    }

    @Override
    void prettyPrint() {

        prettyWrite("{");

        int nPrinted = 0;

        for (int i = 0; i < stringLitList.size(); i++) {

            if (nPrinted > 0) {
                prettyWrite(", ");
            }

            stringLitList.get(i).prettyPrint();
            prettyWrite(": ");
            exprList.get(i).prettyPrint();

            ++nPrinted;
        }

        prettyWrite("}");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if (metString) {// need to be fixed

            RuntimeValue v1 = stringLitList.get(0).eval(curScope);
            RuntimeValue v2 = exprList.get(0).eval(curScope);
             

            for (int i = 1; i < exprList.size(); ++i) {
                v1 = stringLitList.get(i).eval(curScope);

                if (!v2.getBoolValue(", operand", this)) {
                    return v2;
                }

                v2 = exprList.get(i).eval(curScope);
            }
            return v2;
        }
        return null;
    }
}
