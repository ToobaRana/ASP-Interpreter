package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {

    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();


    protected AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s){
        enterParser("comparison");

        AspComparison c = new AspComparison(s.curLineNum());
        c.terms.add(AspTerm.parse(s));

        //The while-loop needs to be modified
        while(true){
            c.compOprs.add(AspCompOpr.parse(s));
            c.terms.add(AspTerm.parse(s));
        }

        leaveParser("comparison");
        return c;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}