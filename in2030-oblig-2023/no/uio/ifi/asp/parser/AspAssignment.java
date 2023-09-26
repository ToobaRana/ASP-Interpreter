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

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");

        AspAssignment a = new AspAssignment(s.curLineNum());
        a.name = AspName.parse(s);

        while (s.curToken().kind != equalToken) {
            a.subscriptions.add(AspSubscription.parse(s));
        }

        skip(s, equalToken);

        a.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return a;
    }

    @Override
    void prettyPrint() {

        name.prettyPrint();

        for (AspSubscription as : subscriptions) {
            as.prettyPrint();
        }

        prettyWrite(" = ");
        expr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

}
