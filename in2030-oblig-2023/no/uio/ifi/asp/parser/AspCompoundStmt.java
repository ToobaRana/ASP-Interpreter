package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.parser.AspStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspCompoundStmt extends AspStmt {

    AspCompoundStmt(int n) {
        super(n);
    }

    static AspCompoundStmt parse(Scanner s){
        enterParser("compound stmt");

        AspCompoundStmt cs = null;

        switch (s.curToken().kind) {

            case forToken:
                cs = AspForStmt.parse(s);
                break;

            case ifToken:
                cs = AspIfStmt.parse(s);
                break;

            case whileToken:
                cs = AspWhileStmt.parse(s);
                break;

            case defToken:
                cs = AspFuncDef.parse(s);
                break;

            default:
                parserError("Expected a compound statement but found a" + s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("compound stmt");
        return cs;
    }

    @Override
    void prettyPrint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
}