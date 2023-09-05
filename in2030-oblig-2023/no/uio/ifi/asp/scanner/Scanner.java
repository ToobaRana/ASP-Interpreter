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

	// Leser neste linje og deler den opp i symbolene og legger
	// symbolene til i curLineToken
	private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;

		try {
			line = sourceFile.readLine();
			System.out.println(line);

			if (line == null) {

				for (int i = indents.peek(); i > 0; i--) {
					curLineTokens.add(new Token(TokenKind.dedentToken, curLineNum()));
					System.out.println("Lagt til sluttDedent");
				}

				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}
		System.out.println("PRINT" + line);

		// -- Must be changed in part 1:

		Boolean isCommentOrBlank = false;
		// Boolean exitIf = false;
		int indentAmount = 0;
		String newLine = "";

		Token indentToken = new Token(TokenKind.indentToken, curLineNum());
		Token dedentToken = new Token(TokenKind.dedentToken, curLineNum());
		Token newLineToken = new Token(TokenKind.newLineToken, curLineNum());
		Token eofToken = new Token(TokenKind.eofToken, curLineNum());

		// If the line contains something
		if (line != null) {

			if (!line.isBlank()) {

				for (char l : line.toCharArray()) {

					// hvis hashtag/kommentar
					if (l == '#') {
						isCommentOrBlank = true;
						break;
					}

					if (l == ' ' || l == '\t') {
						newLine = expandLeadingTabs(line);
					}
				}

				// for (char l : newLine.toCharArray()){
				// if(l == ' '){

				// continue;
				// }
				// else if(l == '#'){
				// break;
				// }
				// }

				indentAmount = findIndent(newLine) / TABDIST;
				System.out.println("INDENT AMOUNT: " + indentAmount);
				int indentTop = indents.peek();

				// if indent amount is higher than the top element
				if (indentAmount > indentTop) {
					// Continue pushing elements until indent amount is reached
					// Start on top of the stack, and stop when indent amount is reached
					for (int i = indentTop + 1; i <= indentAmount; i++) {
						indents.push(i);
						System.out.println("indentttttt " + i);
					}

					curLineTokens.add(indentToken);

				}

				// As long as indent amount is lower than top element on the stack,
				// pop the indents and place a dedent token in curLineTokens

				Boolean ifTrue = true;
				if (ifTrue) {
					splitSymbols(newLine);
				}

				else if (indentAmount < indentTop) {
					for (int i = indentTop; i > indentAmount; i--) {
						indents.pop();
						System.out.println("PPOPOPOPOPOPO:" + "");
					}
					curLineTokens.add(dedentToken);
				}

				System.out.println("Indent amount: " + indentAmount + "Indent top: " + indents.peek());
				if (indentAmount != indents.peek()) {
					System.out.println("Indent error on line: " + curLineNum());
				}

				System.out.println("-----------------------------------NY LINJE--------------------------------------");

				// Terminate line:
				// Hvis kommentar/erBlanke er false
				if (!isCommentOrBlank) {
					curLineTokens.add(newLineToken);
				}
			}
		} else {
			while (indents.size() > 1) {
				indents.pop();
				curLineTokens.add(dedentToken);
			}
			curLineTokens.add(eofToken);

		}

		for (Token t : curLineTokens)
			Main.log.noteToken(t);
	}

	public void splitSymbols(String line) {

		String lineCopy = line;

		EnumSet<TokenKind> getTextTokens = EnumSet.range(andToken, yieldToken);
		EnumSet<TokenKind> getSymbolTokens = EnumSet.range(astToken, semicolonToken);

		Boolean sameTokenKind = false;

		for (char l : lineCopy.toCharArray()) {

			if (l == ' ') {
				continue;
			}

			if (l == '#') {
				break;
			}

			else if (l == '"') {
				int startIndex = lineCopy.indexOf('"');
				int endIndex = lineCopy.indexOf('"', startIndex + 1);

				String stringLit = lineCopy.substring(startIndex, endIndex + 1);
				Token stringLitToken = new Token(TokenKind.stringToken, curLineNum());
				stringLitToken.stringLit = stringLit;

				System.out.println(
						"Startindex: " + startIndex + "\n EndIndex: " + endIndex + "\nString literal: " + stringLit);

				curLineTokens.add(stringLitToken);

				lineCopy = lineCopy.substring(endIndex + 1);
				System.out.println("Line copy: " + lineCopy);
			}

			else if (l == '‘') {
				int startIndex = lineCopy.indexOf('‘');
				int endIndex = lineCopy.indexOf('’', startIndex + 1);

				String stringLit = lineCopy.substring(startIndex, endIndex + 1);
				Token stringLitToken = new Token(TokenKind.stringToken, curLineNum());
				stringLitToken.stringLit = stringLit;

				curLineTokens.add(stringLitToken);

				lineCopy = lineCopy.substring(endIndex + 1);
			}

			else if (isLetterAZ(l)) {
				String wordString = "";
				int counter = lineCopy.indexOf(l);

				// ask gruppelarer
				while (counter < lineCopy.length()
						&& (isLetterAZ(lineCopy.charAt(counter)) || isDigit(lineCopy.charAt(counter)))) {
					wordString += lineCopy.charAt(counter);
					counter++;
				}

				for (TokenKind tokenkind : getTextTokens) {

					if (tokenkind.image.equals(wordString)) {
						Token token = new Token(tokenkind, curLineNum());
						curLineTokens.add(token);
						token.name = wordString;
						sameTokenKind = true;
						break;
					}
				}

				// If it's not in the list of TokenKind -> then it must be a name or a string
				// literal
				if (sameTokenKind == false) {
					Token nameToken = new Token(TokenKind.nameToken, curLineNum());
					nameToken.name = wordString;
					curLineTokens.add(nameToken);
					sameTokenKind = true;
				}

			}

			else if (isDigit(l)) {
				int counter = lineCopy.indexOf(l);
				int currentChar = lineCopy.charAt(counter);
				String numberString = "";

				while (counter < lineCopy.length() && (l == '.' || isDigit(lineCopy.charAt(counter)))) {
					numberString += currentChar;
					counter++;
					System.out.println("this is numberstring in whileloop: " + numberString);
				}

				System.out.println("NUMBERSTRING " + numberString);

				if (numberString.contains(".")) {
					System.out.println("Gaar inn for contains");
					try {
						Token floatToken = new Token(TokenKind.floatToken, curLineNum());
						floatToken.floatLit = l - '0';
						curLineTokens.add(floatToken);

					} catch (NumberFormatException e) {
						scannerError("Ikke gyldig float.");
					}
				} else {
					try {

						Token integerToken = new Token(TokenKind.integerToken, curLineNum());
						integerToken.integerLit = l - '0';
						curLineTokens.add(integerToken);
					} catch (NumberFormatException e) {
						scannerError("Ikke gyldig integer.");
					}
				}

			}

			else {
				String symbolString = "";
				int counter = lineCopy.indexOf(l);

				while (counter < lineCopy.length()) {
					symbolString += lineCopy.charAt(counter);

					for (TokenKind symbolToken : getSymbolTokens) {

						if (symbolToken.image.equals(symbolString)) {
							Token token = new Token(symbolToken, curLineNum());
							curLineTokens.add(token);
							token.name = symbolString;
							break;
						}
					}
					counter++;
				}
				symbolString = "";

			}
		}

		System.out.println(getSymbolTokens);

	}

	// returnerer linjenummeret til linja man er paa
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

	// -- Must be changed in part 1:

	// Omformer innledende TAB-tegn til det rikige antall blanke
	private String expandLeadingTabs(String string) {

		int n = 0;
		int index = 0;
		String newString = "";

		for (char s : string.toCharArray()) {

			if (s == ' ') {
				newString += ' ';
				n++;
				System.out.println("Her er det en enkel blanke");
			}

			else if (s == '\t') {
				int tabToBlank = TABDIST - (n % TABDIST);
				newString += " ".repeat(tabToBlank);
				n += tabToBlank;
				System.out.println("Her er det en tab");
			}

			else {
				newString += string.substring(index);
				break;
			}
			index++;
		}

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
		String q = "     hvordan gar det";
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
