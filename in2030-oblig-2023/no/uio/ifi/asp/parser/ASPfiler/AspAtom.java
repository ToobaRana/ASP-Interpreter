// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser.ASPfiler;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspSyntax {
    AspAtom(int n) {
        super(n);
    }

    static AspAtom parse(Scanner s) {
        // -- Must be changed in part 2:
        return null;
    }
}
