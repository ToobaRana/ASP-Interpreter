package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspListDisplay extends AspAtom{
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        enterParser("list display");

        AspListDisplay ld = new AspListDisplay(s.curLineNum());

        skip(s, TokenKind.leftBracketToken);

        if (s.curToken().kind != TokenKind.rightBracketToken) {
            while (true) {
                ld.exprs.add(AspExpr.parse(s));

                if (s.curToken().kind != TokenKind.commaToken) {
                    break;
                }

                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightBracketToken);


        leaveParser("list display");
        return ld;
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
