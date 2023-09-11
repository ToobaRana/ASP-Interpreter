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

	EnumSet<TokenKind> getTextTokens = EnumSet.range(andToken, yieldToken);
	EnumSet<TokenKind> getSymbolTokens = EnumSet.range(astToken, semicolonToken);

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

		// -- Must be changed in part 1:

		Boolean skipLine = false;

		// Boolean exitIf = false;
		int indentAmount = 0;
		Token indentToken = new Token(TokenKind.indentToken, curLineNum());
		Token dedentToken = new Token(TokenKind.dedentToken, curLineNum());
		Token newLineToken = new Token(TokenKind.newLineToken, curLineNum());
		Token eofToken = new Token(TokenKind.eofToken, curLineNum());

		// If the line contains something
		if (line != null) {

			if (line.trim().length() == 0 || line.trim().charAt(0) == '#') {
				skipLine = true;
			}

			String newLine = expandLeadingTabs(line);

			if (!skipLine) {
				indentAmount = findIndent(newLine);
				int indentTop = indents.peek();

				// if indent amount is higher than the top element
				if (indentAmount > indentTop) {
					indents.push(indentAmount);

					curLineTokens.add(indentToken);

				}

				else if (indentAmount < indentTop) {

					while (indentAmount < indents.peek()) {
						indents.pop();
						curLineTokens.add(dedentToken);
					}

				}

				else if (indentAmount != indents.peek()) {
					scannerError("Indent error on line: ");
				}

				splitSymbols(newLine);

			}

			// Terminate line:
			// Hvis kommentar/erBlanke er false
			if (!line.isEmpty() && !skipLine) {
				curLineTokens.add(newLineToken);
			}
		} else {
			while (indents.size() > 1) {
				indents.pop();
				curLineTokens.add(dedentToken);
			}
			curLineTokens.add(eofToken);

		}

		skipLine = false;

		for (Token t : curLineTokens) {
			Main.log.noteToken(t);
		}

	}

	public void splitSymbols(String line) {

		String lineCopy = line;

		for (int mainCounter = 0; mainCounter < lineCopy.length(); mainCounter++) {
			char l = lineCopy.charAt(mainCounter);

			if (l == ' ') {

				continue;
			}

			if (l == '#') {
				break;
			}

			else if (l == '"' || l == '\'') {
				int startIndex = mainCounter;
				int stopIndex = startIndex + 1;

				while (stopIndex < lineCopy.length() && (lineCopy.charAt(stopIndex) != l)) {
					stopIndex++;
				}

				if (stopIndex < lineCopy.length() && lineCopy.charAt(stopIndex) == l) {
					String stringLit = lineCopy.substring(startIndex + 1, stopIndex);
					Token stringLitToken = new Token(TokenKind.stringToken, curLineNum());
					stringLitToken.stringLit = stringLit;

					curLineTokens.add(stringLitToken);

					mainCounter = stopIndex;
				}

				else{
					scannerError("Invalid string literal");
				}

			}

			else if (isLetterAZ(l)) {

				Boolean sameTokenKind = false;
				String wordString = "";
				int counter = mainCounter;

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

				// increases the counter to next character after the processed word
				mainCounter += wordString.length() - 1;

			}

			else if (isDigit(l)) {
				int indexCounter = mainCounter;
				char currentChar = lineCopy.charAt(indexCounter);
				String numberString = "";

				// builds up the number
				do {
					numberString += currentChar;
					indexCounter++;
					if (indexCounter == lineCopy.length()) {
						break;
					}
					currentChar = lineCopy.charAt(indexCounter);
				} while (indexCounter < (lineCopy.length()) && (currentChar == '.' || isDigit(currentChar)));

				if (numberString.contains(".")) {

					double floatNumber = Double.parseDouble(numberString);

					Token floatToken = new Token(TokenKind.floatToken, curLineNum());
					floatToken.floatLit = floatNumber;
					curLineTokens.add(floatToken);

				}

				else {

					long integerNumber = Long.parseLong(numberString);
					Token integerToken = new Token(TokenKind.integerToken, curLineNum());
					integerToken.integerLit = integerNumber;
					curLineTokens.add(integerToken);

				}

				// sets mainCounter to the already updated indexCounter value (do-while)
				mainCounter = indexCounter - 1;

			}

			else if (isSymbol(l + "")) {

				String symbolString = "";
				int symbolCounter = mainCounter;
				char nextChar = ' ';

				char currentChar = lineCopy.charAt(symbolCounter);
				// need to fix that if its the last character
				if (symbolCounter != lineCopy.length() - 1) {
					nextChar = lineCopy.charAt(symbolCounter + 1);
				}

				symbolString += currentChar;
				String twoSymbolCheck = symbolString + nextChar;
				if (isSymbol(twoSymbolCheck)) {
					symbolString += nextChar;

					mainCounter++;
				}

				for (TokenKind symbolToken : getSymbolTokens) {
					if (symbolToken.image.equals(symbolString)) {
						Token token = new Token(symbolToken, curLineNum());
						curLineTokens.add(token);
						token.name = symbolString;
						break;
					}
				}

			}

			else {
				scannerError("Invalid character: " + l);
			}
		}
	}

	public boolean isSymbol(String s) {
		for (TokenKind symbolToken : getSymbolTokens) {
			String image = symbolToken.image;

			// Check if the string is a symbol
			if (s.equals(image)) {
				return true;
			}

		}
		return false;
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
			}

			else if (s == '\t') {
				int tabToBlank = TABDIST - (n % TABDIST);
				newString += " ".repeat(tabToBlank);
				n += tabToBlank;
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

		String filePath = "/Users/toobarana/Documents/Semester5/IN2030/Prosjektoppgave/in2030-oblig-2023/blanke-linjer.asp";
		Scanner s = new Scanner(filePath);
		s.splitSymbols("$a == \'\'");

		try {
			FileWriter writer = new FileWriter("test.txt");
			// writer.write(endraq);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
