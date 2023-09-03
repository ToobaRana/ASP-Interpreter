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
		//Boolean exitIf = false;
		int indentAmount = 0;
		String newLine = "";


		//If the line contains something
		// outerIf:
		if(!line.isBlank()){
			for (char l : line.toCharArray()) {

				//hvis hashtag/kommentar
				if (l == '#') {
					isCommentOrBlank = true;
					break;
					// exitIf = true;
					// break outerIf;
				}
				
				else if (l == ' ' || l == '\t'){
					newLine = expandLeadingTabs(line);
				}
			}

			indentAmount = findIndent(newLine);
			System.out.println("Antall indents funnet med findIndent " +indentAmount);
			int indentTop = indents.peek();
			Token indentToken = new Token(TokenKind.indentToken);
			Token dedentToken = new Token(TokenKind.dedentToken);

			
			//if indent amount is higher than the top element 
			if(indentAmount > indentTop){
				//Continue pushing elements until indent amount is reached
				//Start on top of the stack, and stop when indent amount is reached
				for (int i = indentTop +1; i <= indentAmount; i++){
					indents.push(i);
					System.out.println("indentttttt "+i);
				}

				curLineTokens.add(indentToken);
				
			}

			//As long as indent amount is lower than top element on the stack,
			//pop the indents and place a dedent token in curLineTokens
			else if (indentAmount < indentTop){
				for(int i = indentTop; i<= indentAmount; i--){
					indents.pop();
					curLineTokens.add(dedentToken);
				}
			}


			// Terminate line:
			//Hvis kommentar/erBlanke er false
			if (!isCommentOrBlank) {
				curLineTokens.add(new Token(newLineToken, curLineNum()));
			}
		} 


		// String lastLine = null;
		// String readLastLine;

		// while (line != null) {
		// 	lastLine = line;
		// }

		// if(lastLine != null){
		// 	curLineTokens.add(new Token(TokenKind.eofToken));
		// }


		
		// else if(line.isEmpty()){

		// 	//
		// 	// while(indents.size() > 1){
		// 	// 	indents.pop();
		// 	// 	curLineTokens.add(new Token(TokenKind.dedentToken));
		// 	// }

		// 	curLineTokens.add(new Token(TokenKind.eofToken));
		// }
		
		

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
				newString += ' ';
				n++;
				System.out.println("Her er det en enkel blanke");
			}

			else if (s == '\t'){
				int tabToBlank = TABDIST -(n % TABDIST);
				newString += " ".repeat(tabToBlank);
				n += tabToBlank;
				System.out.println("Her er det en tab");
			}

			else{
				newString += string.substring(index); 
				break;
			}
			index++;
		}

		System.out.println("Antall blanke til slutt = "+ n);
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
