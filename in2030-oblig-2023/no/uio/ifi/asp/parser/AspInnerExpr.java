package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.scanner.TokenKind;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspInnerExpr extends AspPrimarySuffix{
    AspExpr expr;

    AspInnerExpr(int n) {
        super(n);
    }

    static AspInnerExpr parse(Scanner s){
        enterParser("inner expr");
        AspInnerExpr ie = new AspInnerExpr(s.curLineNum());
        skip(s, TokenKind.leftParToken);
        ie.expr = AspExpr.parse(s);
        skip(s, TokenKind.rightParToken);
        leaveParser("inner expr");
        return ie;
    }

    @Override
    void prettyPrint() {
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
}
