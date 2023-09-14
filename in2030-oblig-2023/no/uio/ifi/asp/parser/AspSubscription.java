package no.uio.ifi.asp.parser;

//import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.scanner.TokenKind;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspSubscription extends AspPrimarySuffix{
    AspExpr expr;

    AspSubscription(int n) {
        super(n);
    }

    static AspSubscription parse(Scanner s){
        enterParser("subscription");
        AspSubscription as = new AspSubscription(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);
        as.expr = AspExpr.parse(s);
        skip(s, TokenKind.rightBracketToken);
        leaveParser("subscription");
        return as;
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
