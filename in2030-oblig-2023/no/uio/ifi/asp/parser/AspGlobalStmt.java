package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspGlobalStmt extends AspSmallStmt {

    ArrayList<AspName> names = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
    }

    static AspGlobalStmt parse(Scanner s){
        enterParser("global stmt");

        AspGlobalStmt gs = new AspGlobalStmt(s.curLineNum());
        skip(s, TokenKind.globalToken);

        while (true) {
            gs.names.add(AspName.parse(s));
            if (s.curToken().kind != TokenKind.commaToken) {
                break;
            }
            skip(s, TokenKind.commaToken);
        }

        leaveParser("global stmt");
        return gs;
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
