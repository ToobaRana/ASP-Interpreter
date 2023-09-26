package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIfStmt extends AspCompoundStmt {

    ArrayList<AspExpr> exprList = new ArrayList<>();
    ArrayList<AspSuite> suiteList = new ArrayList<>();
    AspSuite suite;

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {

        enterParser("if stmt");

        AspIfStmt is = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while (true) {

            is.exprList.add(AspExpr.parse(s));
            skip(s, colonToken);
            is.suiteList.add(AspSuite.parse(s));

            if (s.curToken().kind != elifToken) {
                break;
            }

            skip(s, elifToken);
        }

        if (s.curToken().kind == elseToken) {

            skip(s, elseToken);
            skip(s, colonToken);
            is.suite = AspSuite.parse(s);
        }

        leaveParser("if stmt");
        return is;
    }

    @Override
    void prettyPrint() {

        int nPrinted = 0;
        prettyWrite("if ");

        for (int i = 0; i < exprList.size(); i++) {

            if (nPrinted > 0) {
                prettyWrite("elif ");
            }

            exprList.get(i).prettyPrint();
            prettyWrite(": ");
            suiteList.get(i).prettyPrint();

            ++nPrinted;
        }

        if (suite != null) {
            prettyWrite("else");
            prettyWrite(": ");
            suite.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

}
