package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspFactor extends AspSyntax {

    ArrayList<AspFactorPrefix> prefixes = new ArrayList<>();
    ArrayList<AspPrimary> primaries = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();

    protected AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {

        enterParser("factor");

        AspFactor f = new AspFactor(s.curLineNum());

        while (true) {
            if (s.isFactorPrefix()) {
                f.prefixes.add(AspFactorPrefix.parse(s));
            }

            f.primaries.add(AspPrimary.parse(s));

            if (!s.isFactorOpr()) {
                break;
            }

            f.factorOprs.add(AspFactorOpr.parse(s));
        }

        leaveParser("factor");
        return f;
    }

    @Override
    void prettyPrint() {

        






    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}