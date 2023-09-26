package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
        
        skip(s, forToken);
        fs.name = AspName.parse(s);
        skip(s, inToken);
        fs.expr = AspExpr.parse(s);
        skip(s, colonToken);
        fs.suite = AspSuite.parse(s);

        leaveParser("for stmt");
        return fs;
    }

    @Override
    void prettyPrint() {
        prettyWrite("for ");
        name.prettyPrint();
        prettyWrite(" in ");
        expr.prettyPrint();
        prettyWrite(": ");
        suite.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
