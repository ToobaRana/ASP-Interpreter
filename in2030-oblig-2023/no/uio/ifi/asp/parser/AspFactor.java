package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
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

            // break if curToken is not a factorOpr
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

            if (i <= factorOprs.size() - 1) {
                factorOprs.get(i).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        RuntimeValue v = null;
        RuntimeValue primaryValue = primaries.get(0).eval(curScope);

        int trueCounter = 0;

        for (int i = 0; i < prefixBeforeList.size(); i++) {

            if (prefixBeforeList.get(i) == true) {
                RuntimeValue p = primaries.get(i).eval(curScope);
                TokenKind k = prefixes.get(trueCounter).fpVal;

                switch (k) {
                    case plusToken:
                        v = p.evalPositive(this);
                        break;

                    case minusToken:
                        v = p.evalNegate(this);
                        break;

                    default:
                        Main.panic("Illegal term operator: " + k + "!");
                }
                trueCounter++;
            }

            if (prefixBeforeList.get(i) == false) {
                System.out.println("bllllll");
                primaryValue = primaries.get(i).eval(curScope);
                System.out.println(i);
                System.out.println(primaryValue);
            }

            if (i > 0) {
                System.out.println("blah");
                TokenKind k = factorOprs.get(i - 1).foVal;
                // if (v == null) {
                //     System.out.println(primaryValue);
                //     v = primaries.get(0).eval(curScope);
                // }

                switch (k) {
                    case astToken:
                        // System.out.println("we in asttoken");
                        v = v.evalMultiply(primaryValue, this);
                        // System.out.println(v);
                        break;

                    case slashToken:
                        System.out.println("we in slashToken");

                        v = v.evalDivide(primaryValue, this);
                        break;

                    case percentToken:
                        System.out.println("we in percentToken");

                        v = v.evalModulo(primaryValue, this);
                        break;

                    case doubleSlashToken:
                        System.out.println("we in doubleSlashToken");

                        v = v.evalIntDivide(primaryValue, this);
                        break;

                    default:
                        Main.panic("Illegal term operator: " + k + "!");
                }

                // primaryValue = v;
            }

            if (i == 0 && trueCounter == 0) {
                v = primaries.get(0).eval(curScope);

            }

        }
        System.out.println(v);
        return v;
    }
}