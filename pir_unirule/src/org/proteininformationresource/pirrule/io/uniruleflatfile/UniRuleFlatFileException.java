package org.proteininformationresource.pirrule.io.uniruleflatfile;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 27, 2014<br><br>
 *
 */
public abstract class UniRuleFlatFileException extends Exception {

	private static final long serialVersionUID = 30402L;
	
	protected UniRuleFlatFileException() {
		
	}
	
	/**
	 *
	 * @param message the message String
	 */
	public UniRuleFlatFileException(String message) {
		super(message);
	}
	
	
	/**
	 * @param message the message String
	 * @param cause the cause String
	 */
	public UniRuleFlatFileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param cause the cause Throwable
	 */
	public UniRuleFlatFileException(Throwable cause) {
		super(cause);
	}
}
