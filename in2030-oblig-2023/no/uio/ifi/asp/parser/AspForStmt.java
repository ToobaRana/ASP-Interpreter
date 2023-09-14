package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspForStmt extends AspCompoundStmt {

    AspName name;
    AspExpr expr;
    AspSuite suite;

    AspForStmt(int n) {
        super(n);
    }

    static AspForStmt parse(Scanner s) {
        enterParser("for stmt");

        AspForStmt fs = new AspForStmt(s.curLineNum());
        skip(s, TokenKind.forToken);
        fs.name = AspName.parse(s);
        skip(s, TokenKind.inToken);
        fs.expr = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        fs.suite = AspSuite.parse(s);

        leaveParser("for stmt");
        return fs;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
