package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspFactor extends AspSyntax {

    ArrayList<AspFactorPrefix> prefixes = new ArrayList<>();
    ArrayList<AspPrimary> primaries = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();
    ArrayList<Boolean> prefixBeforeList = new ArrayList<>();
    


    protected AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {

        enterParser("factor");

        AspFactor f = new AspFactor(s.curLineNum());
        Boolean prefixBefore = false;

        while (true) {
            if (s.isFactorPrefix()) {
                prefixBefore = true;
                f.prefixes.add(AspFactorPrefix.parse(s));
                f.prefixBeforeList.add(true);
            }

            
            f.primaries.add(AspPrimary.parse(s));

            if (!prefixBefore) {
                f.prefixBeforeList.add(false);
            }

            //break if curToken is not a factorOpr
            if (!s.isFactorOpr()) {
                break;
            }

            f.factorOprs.add(AspFactorOpr.parse(s));
            prefixBefore = false;
        }

        leaveParser("factor");
        return f;
    }

    @Override
    void prettyPrint() {

        for (int i = 0; i < primaries.size(); i++) {
            
            if (prefixBeforeList.get(i) == true) {
                prefixes.get(i).prettyPrint();
            }

            primaries.get(i).prettyPrint();

            if (i<= factorOprs.size()-1) {
                factorOprs.get(i).prettyPrint();
            }
        }
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}