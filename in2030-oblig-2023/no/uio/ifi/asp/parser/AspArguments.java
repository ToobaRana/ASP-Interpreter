package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix{
    
    ArrayList<AspExpr> exprList = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s){

        enterParser("arguments");

        AspArguments a = new AspArguments(s.curLineNum());

        skip(s, leftParToken);
        
        if(s.curToken().kind != rightParToken){
            while(true){
                a.exprList.add(AspExpr.parse(s));

                if(s.curToken().kind != commaToken){
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
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
    
}
