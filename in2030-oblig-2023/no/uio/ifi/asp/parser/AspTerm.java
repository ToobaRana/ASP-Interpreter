package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factors = new ArrayList<>();
    ArrayList<AspTermOpr> termOprs = new ArrayList<>();

    protected AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {

        enterParser("term");

        AspTerm t = new AspTerm(s.curLineNum());
        t.factors.add(AspFactor.parse(s));

        while (s.isTermOpr()) {
            t.termOprs.add(AspTermOpr.parse(s));
            t.factors.add(AspFactor.parse(s));
        }

        leaveParser("term");
        return t;
    }

    @Override
    void prettyPrint() {

        for (int i = 0; i < factors.size(); i++) {
            factors.get(i).prettyPrint();

            if (i <= termOprs.size() - 1) {
                termOprs.get(i).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
