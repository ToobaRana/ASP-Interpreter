package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> exprList = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s){
        enterParser("argument");

        AspArguments a = new AspArguments(s.curLineNum());

        skip(s, TokenKind.leftParToken);
        
        if(s.curToken().kind != TokenKind.rightParToken){
            while(true){
                a.exprList.add(AspExpr.parse(s));

                if(s.curToken().kind != TokenKind.commaToken){
                    break;
                }
                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightParToken);

        leaveParser("argument");
        return a;
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
