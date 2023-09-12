// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser.ASPfiler;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspSmallStmt extends AspSyntax {
    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        // -- Must be changed in part 2:
        return null;
    }
}

