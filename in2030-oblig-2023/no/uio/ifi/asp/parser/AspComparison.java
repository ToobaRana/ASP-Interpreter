package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

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


        //As long as currentoken is a compOpr
        while(s.isCompOpr()){
            c.compOprs.add(AspCompOpr.parse(s));
            c.terms.add(AspTerm.parse(s));
        }

        leaveParser("comparison");
        return c;
    }

    @Override
    void prettyPrint() {

        for (int i = 0; i < terms.size(); i++){
            terms.get(i).prettyPrint();

            if (i <= compOprs.size()-1){
                compOprs.get(i).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}