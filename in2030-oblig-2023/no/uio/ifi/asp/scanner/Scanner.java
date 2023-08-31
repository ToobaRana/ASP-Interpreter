// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
	private LineNumberReader sourceFile = null;
	private String curFileName;
	private ArrayList<Token> curLineTokens = new ArrayList<>();
	private Stack<Integer> indents = new Stack<>();
	private final int TABDIST = 4;

	public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
							new FileInputStream(fileName),
							"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
	}

	private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
	}

	// Henter naavaerende symbol, som alltid er forste symbol i curLineTokens.
	// Fjerner ikke symbolet
	// Om nodvendig, kaller paa readNextLine for aa faa leste flere linjer
	public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
	}

	// Fjerner naavaerede symbol, som er forste symbol i curLineTokens
	public void readNextToken() {
		if (!curLineTokens.isEmpty())
			curLineTokens.remove(0);
	}


	//Leser neste linje og deler den opp i symbolene og legger 
	//symbolene til i curLineToken
	private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;

		try {
			line = sourceFile.readLine();
			System.out.println(line);

			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}
		System.out.println("PRINT"+line);

		// -- Must be changed in part 1:

		Boolean isCommentOrBlank = false;

		//If the line contains something
		if(!line.isBlank()){
			for (char l : line.toCharArray()) {

				//hvis hashtag/kommentar
				if (l == '#') {
					isCommentOrBlank = true;
					break;
				}
				
				//hvis blank linje
				// else if (line ) {
				// 	isCommentOrBlank = true;
				// 	continue;
				// }

				expandLeadingTabs(line);

				// else if (l == ' ' || l == '\t'){
				// 	expandLeadingTabs(line);
				// }
	
			}

			// Terminate line:
			//Hvis kommentar/erBlanke er false
			if (!isCommentOrBlank) {
				curLineTokens.add(new Token(newLineToken, curLineNum()));
			}
		}

		

		

		for (Token t : curLineTokens)
			Main.log.noteToken(t);
	}

	//returnerer linjenummeret til linja man er paa
	public int curLineNum() {
		return sourceFile != null ? sourceFile.getLineNumber() : 0;
	}

	// Teller antall blanke i starten av den naavaerende linjen
	private int findIndent(String s) {
		int indent = 0;

		while (indent < s.length() && s.charAt(indent) == ' ')
			indent++;
		return indent;
	}

	//-- Must be changed in part 1:
    
	
	//Omformer innledende TAB-tegn til det rikige antall blanke



	private String expandLeadingTabs(String string) {

		int n = 0;
		int index = 0;
		String newString = "";

		for (char s : string.toCharArray()){

			if (s == ' '){
				newString += " ";
				n++;
			}

			else if (s == '\t'){
				int tabToBlank = TABDIST -(n % TABDIST);
				newString += " ".repeat(tabToBlank);
				n += tabToBlank;
			}

			else{
				newString += string.substring(index); 
				break;
			}
			index++;
		}

		System.out.println("Antall blanke = "+ n);
		return newString;
    } 



	private boolean isLetterAZ(char c) {
		return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || (c == '_');
	}

	private boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}

	public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean anyEqualToken() {
		for (Token t : curLineTokens) {
			if (t.kind == equalToken)
				return true;
			if (t.kind == semicolonToken)
				return false;
		}
		return false;
	}


	public static void main(String[] args) {
        Scanner s = new Scanner(
			"/Users/toobarana/Documents/Semester5/IN2030/Prosjektoppgave/in2030-oblig-2023/blanke-linjer.asp");
        String q = "\t hvordan gpr det";
        String endraq = s.expandLeadingTabs(q);
        // s.checkIndentToken(q);
        try {
            FileWriter writer = new FileWriter("test.txt");
            writer.write(endraq);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
