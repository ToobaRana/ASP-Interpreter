package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt {
    AspExpr expr;


    AspExprStmt(int n) {
        super(n);
    }

    static AspExprStmt parse(Scanner s){

        enterParser("expr stmt");
        
        AspExprStmt es = new AspExprStmt(s.curLineNum());
        es.expr = AspExpr.parse(s);

        leaveParser("expr stmt");
        return es;
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
