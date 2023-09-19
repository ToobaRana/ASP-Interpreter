package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom{

    ArrayList<AspStringLiteral> stringLitList = new ArrayList<>();
    ArrayList<AspExpr> exprList = new ArrayList<>();

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s){

        enterParser("dict display");

        AspDictDisplay dd = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        if(s.curToken().kind != rightBraceToken){

            while(true){
                dd.stringLitList.add(AspStringLiteral.parse(s));
                skip(s, colonToken);
                dd.exprList.add(AspExpr.parse(s));

                if(s.curToken().kind != commaToken){
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
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
