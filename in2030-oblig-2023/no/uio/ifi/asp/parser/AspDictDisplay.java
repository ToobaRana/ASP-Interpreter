package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspDictDisplay extends AspAtom{

    ArrayList<AspStringLiteral> stringLitList = new ArrayList<>();
    ArrayList<AspExpr> exprList = new ArrayList<>();


    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        enterParser("dict display");

        AspDictDisplay dd = new AspDictDisplay(s.curLineNum());
        skip(s, TokenKind.leftBraceToken);

        if(s.curToken().kind != TokenKind.rightBraceToken){

            while(true){
                dd.stringLitList.add(AspStringLiteral.parse(s));
                skip(s, TokenKind.colonToken);
                dd.exprList.add(AspExpr.parse(s));

                if(s.curToken().kind != TokenKind.commaToken){
                    break;
                }
                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightBraceToken);


        leaveParser("dict display");
        return dd;
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
