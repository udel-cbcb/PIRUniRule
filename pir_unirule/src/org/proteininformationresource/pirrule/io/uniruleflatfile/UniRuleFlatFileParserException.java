package org.proteininformationresource.pirrule.io.uniruleflatfile;




/**
 * 
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 27, 2013<br><br>
 *
 */
public class UniRuleFlatFileParserException extends UniRuleFlatFileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 30402L;
	
	private int lineNumber;
	
	private int columnNumber;

	public UniRuleFlatFileParserException() {
		this.lineNumber = -1;
		this.columnNumber = -1;
	}
	
	/**
	 * @param message the message String
	 */
	public UniRuleFlatFileParserException(String message) {
		super(message);
		this.lineNumber = -1;
		this.columnNumber = -1;
	}

	/**
	 * @param message the message String
	 * @param cause the cause Throwable
	 */
	public UniRuleFlatFileParserException(String message, Throwable cause) {
		super(message, cause);
		this.lineNumber = -1;
		this.columnNumber = -1;
	}
	
	
	/**
	 * 
	 * @param cause the cause Throwable
	 */
	public UniRuleFlatFileParserException(Throwable cause) {
		super(cause);
		this.lineNumber = -1;
		this.columnNumber = -1;
	}
	
	/**
	 * 
	 * @param message the message String
	 * @param lineNumber the line number int
	 * @param columnNumber the column number int
	 */
	public UniRuleFlatFileParserException(String message, int lineNumber, int columnNumber) {
		super(message);
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}
	
	/**
	 * 
	 * @param message the message String
	 * @param lineNumber the line number int
	 */
	public UniRuleFlatFileParserException(String message, int lineNumber) {
		super(message);
		this.lineNumber = lineNumber;
	}
	
	/**
	 * 
	 * @param cause the cause Throwable
	 * @param lineNumber the line number int
	 * @param columnNumber the column number int
	 */
	public UniRuleFlatFileParserException(Throwable cause, int lineNumber, int columnNumber) {
		super(cause);
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}
	
	/**
	 * 
	 * @param message the message String
	 * @param cause the cause Throwable
	 * @param lineNumber the line number int
	 * @param columnNumber the column number int
	 */
	public UniRuleFlatFileParserException(String message, Throwable cause, int lineNumber, int columnNumber) {
		super(message, cause);
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}
	
	 /**
     * Gets the line number of the line that the parser
     * was parsing when the error occurred.
     * @return A positive integer which represents the line
     * number, or -1 if the line number could not be determined.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the column number of the line that the parser was parsing when the error occurred.
     * @return the column number
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
	public String getMessage() {
        if (lineNumber != -1 && columnNumber != -1) {
            return "\n"+super.getMessage() + " (Line " + this.lineNumber + "; Column "+ this.columnNumber +")";
        }
        else if(lineNumber != -1) {
        	return "\n"+super.getMessage() + " (Line " + this.lineNumber+")";
        }
        else {
            return "\n"+super.getMessage();
        }
    }
}
