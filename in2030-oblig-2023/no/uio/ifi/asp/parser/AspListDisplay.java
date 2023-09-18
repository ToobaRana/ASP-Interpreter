package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom{
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        enterParser("list display");

        AspListDisplay ld = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        if (s.curToken().kind != rightBracketToken) {
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
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}