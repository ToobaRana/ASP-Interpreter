package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspAssignment extends AspSmallStmt {
    AspName name;
    ArrayList<AspSubscription> subscriptions = new ArrayList<>();
    AspExpr expr;

    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s){
        enterParser("assignment");

        AspAssignment a = new AspAssignment(s.curLineNum());
        a.name = AspName.parse(s);

        while (s.curToken().kind != equalToken) {
            a.subscriptions.add(AspSubscription.parse(s));
        }
        System.out.println("maryam");

        System.out.println("MT: "+s.curToken());

        skip(s, equalToken);
        System.out.println("TooBA");

        a.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return a;
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
