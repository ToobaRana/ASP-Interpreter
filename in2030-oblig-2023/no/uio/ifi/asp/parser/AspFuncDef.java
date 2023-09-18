package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt {
    AspName name;
    AspSuite suite;
    ArrayList<AspName> names = new ArrayList<>();


    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s){
        enterParser("func def");

        AspFuncDef fd = new AspFuncDef(s.curLineNum());
        
        skip(s, defToken);
        fd.name = AspName.parse(s);
        skip(s, leftParToken);

        if (s.curToken().kind != rightParToken) {
            while (true) {
                fd.names.add(AspName.parse(s));

                if (s.curToken().kind != commaToken) {
                    break;
                }
                
                skip(s, commaToken);

            }
        }

        skip(s, rightParToken);
        skip(s, colonToken);

        fd.suite = AspSuite.parse(s);


        leaveParser("func def");
        return fd;
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

    
}