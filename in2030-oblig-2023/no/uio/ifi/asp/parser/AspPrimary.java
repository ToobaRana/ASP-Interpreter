package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspPrimary extends AspSyntax {

    AspAtom atomType;
    ArrayList<AspPrimarySuffix> primarySuffixes = new ArrayList<>();

    protected AspPrimary(int n) {
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        enterParser("primary");

        AspPrimary p = new AspPrimary(s.curLineNum());
        p.atomType = AspAtom.parse(s);

        while (s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken) {
            p.primarySuffixes.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("primary");
        return p;
    }

    @Override
    void prettyPrint() {

        atomType.prettyPrint();

        for (AspPrimarySuffix aps : primarySuffixes) {
            aps.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        RuntimeValue v = atomType.eval(curScope);

        for (AspPrimarySuffix suffix : primarySuffixes) {
            if (v instanceof RuntimeStringValue || v instanceof RuntimeListValue || v instanceof RuntimeDictValue) {
                v = v.evalSubscription(suffix.eval(curScope), this);
            }

            else if(suffix instanceof AspArguments){
                v = suffix.eval(curScope);
            }
        }
        return v;
    }
}
