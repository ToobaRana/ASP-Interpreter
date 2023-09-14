package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFactor extends AspSyntax {

    // ArrayList<AspFactorPrefix> prefixes = new ArrayList<>();
    // ArrayList<AspPrimary> primaries = new ArrayList<>();
    // ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();


    protected AspFactor(int n) {
        super(n);
    }



    static AspFactor parse(Scanner s){

        enterParser("factor");
        AspFactor f = new AspFactor(s.curLineNum());

        

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