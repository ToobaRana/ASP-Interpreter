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

    // @Override
    // RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

    // RuntimeValue v = primaries.get(0).eval(curScope);
    // if (prefixes.size() != 0) {
    // if (prefixes.get(0) != null) {
    // TokenKind k = prefixes.get(0).fpVal;

    // switch (k) {
    // case plusToken:
    // v = v.evalPositive(this);
    // break;
    // case minusToken:
    // v = v.evalNegate(this);
    // break;
    // default:
    // Main.panic("Illegal Factor operator" + k + "!");
    // }
    // }
    // }

    // for (int i = 1; i < primaries.size(); i++) {
    // TokenKind k = factorOprs.get(i-1).foVal;
    // RuntimeValue next = primaries.get(i).eval(curScope);
    // if (prefixBeforeList.get(i) != false) {
    // TokenKind n = prefixes.get(i).fpVal;

    // switch (n) {
    // case plusToken:
    // next = next.evalPositive(this);
    // break;
    // case minusToken:
    // next = next.evalNegate(this);
    // break;
    // default:
    // Main.panic("Illegal Factor operator" + k + "!");
    // }
    // }

    // switch (k) {
    // case astToken:
    // v = v.evalMultiply(next, this);
    // break;
    // case percentToken:
    // v = v.evalModulo(next, this);
    // break;
    // case slashToken:
    // v = v.evalDivide(next, this);
    // break;
    // case doubleSlashToken:
    // v = v.evalIntDivide(next, this);
    // break;
    // default:
    // Main.panic("illefal Factor operator " + k + "!");
    // }
    // }
    // return v;
    // }

    // @Override
    // RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    // // factor = (+-) primary(feks et tall) fctor opr(feks *) primary = - 6 * 3
    // RuntimeValue v = primaries.get(0).eval(curScope); // evaluerer primary

    // if (prefixes.size() != 0) {
    // if (prefixes.get(0) != null) { // hvis factorprefix ikke er null = vi har
    // factorprefix
    // TokenKind k = prefixes.get(0).fpVal; // henter factorprefix-klassen sin
    // tokenkind

    // switch (k) { // finner ut av hvilken type
    // case plusToken:
    // v = v.evalPositive(this);
    // break;
    // case minusToken:
    // v = v.evalNegate(this);
    // break;
    // default:
    // Main.panic("Illeagl factor prefix " + k + "!");
    // }
    // }
    // }

    // for (int i = 1; i < primaries.size(); i++) {// looper oss gjennom primary
    // RuntimeValue primary = primaries.get(i).eval(curScope); // evaluerer et og et
    // element i primary listen
    // TokenKind k = null;

    // if (prefixes.get(i) != null) { // hvis factorprefix ikke er null = vi har
    // factorprefix
    // k = prefixes.get(i).fpVal; // henter tokenkind'et

    // switch (k) { // sjekker for hvilket symbol
    // case minusToken:
    // primary = primary.evalNegate(this);
    // break;
    // case plusToken:
    // primary = primary.evalPositive(this);
    // break;
    // default:
    // Main.panic("Illeagl factor prefix " + k + "!");
    // }
    // }

    // TokenKind kk = factorOprs.get(i - 1).foVal;// factoroperator sin tokenkind

    // switch (kk) { // sjekker for hvilken tokenkind
    // case astToken:
    // v = v.evalMultiply(primary, this);
    // break;
    // case slashToken:
    // v = v.evalDivide(primary, this);
    // break;
    // case percentToken:
    // v = v.evalModulo(primary, this);
    // break;
    // case doubleSlashToken:
    // v = v.evalIntDivide(primary, this);
    // break;
    // default:
    // Main.panic("Illegal term operator " + k + "!");
    // }
    // }
    // return v;
    // }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // trace("inne i factor");
        RuntimeValue v = null;
        RuntimeValue primaryValue = null;
        int trueCounter = 0;

        for (int i = 0; i < prefixBeforeList.size(); i++) {

            // If there is a prefix before primary
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

            // If there is no prefix before primary
            else {
                primaryValue = primaries.get(i).eval(curScope);

                if (i == 0) {
                    v = primaryValue;

                }
                // break;
            }

            // Handles factor opr after the first loop
            if (i > 0) {
                TokenKind k = factorOprs.get(i - 1).foVal;

                switch (k) {
                    case astToken:
                        v = v.evalMultiply(primaryValue, this);
                        break;

                    case slashToken:
                        v = v.evalDivide(primaryValue, this);
                        break;

                    case percentToken:
                        v = v.evalModulo(primaryValue, this);
                        break;

                    case doubleSlashToken:
                        v = v.evalIntDivide(primaryValue, this);
                        break;

                    default:
                        Main.panic("Illegal term operator: " + k + "!");
                }

            }

            // For the first loop
            // If there is no factor prefix, set v
            // else if (i == 0 && trueCounter == 0) {
            // v = primaryValue;
            // //break;
            // }
        }
        return v;
    }
}