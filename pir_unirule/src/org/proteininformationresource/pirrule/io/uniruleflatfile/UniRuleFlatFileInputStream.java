package org.proteininformationresource.pirrule.io.uniruleflatfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

public class UniRuleFlatFileInputStream {

	int pos = 0;
	String line;
	int lineNo = 0;
	BufferedReader reader;
	Logger logger;

	public UniRuleFlatFileInputStream() {
		pos = 0;
	}

	public UniRuleFlatFileInputStream(BufferedReader r) {
		reader = r;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	private char peekChar() {
		prepare();
		return line.charAt(pos);
	}

	public char nextChar() {
		pos++;
		return line.charAt(pos - 1);
	}

	public String rest() {
		prepare();
		if (line == null)
			return null;
		if (pos >= line.length())
			return "";
		return line.substring(pos);
	}

	public void advance(int dist) {
		pos += dist;
	}

	public void prepare() {
		if (line == null) {
			advanceLine();
		}
	}

	public void advanceLine() {
		try {
			line = reader.readLine();
			lineNo++;
			pos = 0;
		} catch (IOException e) {
			logger.severe("lineNo: " + lineNo);
			throw new Error("Error reading from input.", e);
		}
	}

	public void forceEol() {
		if (line == null) {
			return;
		}
		pos = line.length();
	}

	public boolean eol() {
		prepare();
		if (line == null) {
			return false;
		}
		return pos >= line.length();
	}

	public boolean eof() {
		prepare();
		if (line == null) {
			return true;
		}
		return false;
	}

	public String getTag() {
		return "";
	}

	public boolean consume(String s) {
		String r = rest();
		if (r == null)
			return false;
		if (r.startsWith(s)) {
			pos += s.length();
			return true;
		}
		return false;
	}

	public int indexOf(char c) {
		prepare();
		if (line == null)
			return -1;
		return line.substring(pos).indexOf(c);
	}

	@Override
	public String toString() {
		return line + "//" + pos + " LINE:" + lineNo;
	}

	public boolean peekCharIs(char c) {
		if (eol() || eof())
			return false;
		return peekChar() == c;
	}

	public int getLineNo() {
		return lineNo;
	}

}
