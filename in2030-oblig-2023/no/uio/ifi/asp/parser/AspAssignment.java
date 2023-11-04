package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
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
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue rt = expr.eval(curScope);
        RuntimeValue rt2 = null;

        if (subscriptions.isEmpty()) {
            if (curScope.hasGlobalName(name.name)) {
                Main.globalScope.assign(name.name, rt);
                trace(name.name + " = " + rt.showInfo());
            } else {
                curScope.assign(name.name, rt);
                trace(name.name + " = " + rt.showInfo());
            }
        } else if (!subscriptions.isEmpty()) {
            rt2 = name.eval(curScope);
            for (int i = 0; i < subscriptions.size() - 1; i++) {
                rt2 = rt2.evalSubscription(subscriptions.get(i).eval(curScope), this);
            }
            RuntimeValue lastPos = subscriptions.get(subscriptions.size() - 1).eval(curScope);
            rt2.evalAssignElem(lastPos, rt, this);
            trace(name.name + "[" + lastPos + "] = " + rt);
            return rt;
        } /*
           * else {
           * curScope.assign(name.p, rt);
           * trace(name.p + " = " + rt.toString());
           * return rt;
           * }
           */
        return rt;
    }

}
