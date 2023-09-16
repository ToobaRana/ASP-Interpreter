package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

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
        
        skip(s, TokenKind.defToken);
        fd.name = AspName.parse(s);
        skip(s, TokenKind.leftParToken);

        if (s.curToken().kind != TokenKind.rightParToken) {
            while (true) {
                fd.names.add(AspName.parse(s));

                if (s.curToken().kind != TokenKind.commaToken) {
                    break;
                }
                
                skip(s, TokenKind.commaToken);

            }
        }

        skip(s, TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);

        fd.suite = AspSuite.parse(s);


        leaveParser("func def");
        return fd;
    }

    
}
