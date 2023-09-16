package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

public class AspIfStmt extends AspCompoundStmt {
    AspExpr expr;
    AspSuite suite;


    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s){
        enterParser("if stmt");


        AspIfStmt is = new AspIfStmt(s.curLineNum());


        leaveParser("if stmt");
        return is;
    }
    
}
