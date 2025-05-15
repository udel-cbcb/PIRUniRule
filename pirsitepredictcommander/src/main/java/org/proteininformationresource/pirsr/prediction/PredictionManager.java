package org.proteininformationresource.pirsr.prediction;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;

import org.proteininformationresource.pirsr.model.PIRRuleManager;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br><br>
 * 
 * 
 * Manage site feature prediction related tasks.
*/
public interface PredictionManager {

	/**
	 * Gets the <code>PredictionDataFactory</code>.
	 * @return the <code>PredictionDataFactory</code>.
	 */
	PredictionDataFactory getPredictionDataFactory();
	
	/**
	 * Sets the <code>PredictionDataFactory</code>.
	 * @param predictionDataFactory the <code>PredictionDataFactory</code>.
	 */
	//void setPredictionDataFactory(PredictionDataFactory predictionDataFactory);
	
	/**
	 * Load a list of <code>Alignment</code>s specified by an URL.
	 * @param url can be used to obtain a list of <code>Alignment<code>s.
	 * @return a list of <code>Alignment<code>.
	 */
	List<Alignment> loadAlignment(URL url);
	
	/**
	 * Load a list of <code>Alignment</code>s contained in a local file.
	 * @param file A local file containing a list of <code>Alignment</code>s.
	 * @return a list of <code>Alignment</code>s.
	 */
	List<Alignment> loadAlignment(File file);
	
	/**
	 * Load a list of <code>Alignment</code>s from an input stream.
	 * @param inputStream that can be used to obtain the list of <code>Alignment</code>.
	 * @return a list of <code>Alignment</code>.
	 */
	List<Alignment> loadAlignment(InputStream inputStream);
	
	/**
	 * Gets an alignment
	 * @param ruleAC rule accession number.
	 * @param proteinId protein ID.
	 * @param templateAC template accession number.
	 * @return an <code>Alignment</code>.
	 */
	Alignment getAlignment(String ruleAC, String proteinId, String templateAC);
	
	/**
	 * Gets an <code>Alignment</code>.
	 * @param proteinId protein ID.
	 * @param ruleAC rule accession number.
	 * @return an <code>Alignment</code>.
	 */
	Alignment getAlignmentByProteinAndRule(String proteinId, String ruleAC);
	
	/**
	 * Gets the <code>PIRRuleManager</code> used in the prediction task.
	 * @return the <code>PIRRuleManager</code> used in the prediction task.
	 */
	PIRRuleManager getPIRRuleManager();
	
	/**
	 * Sets the <code>PIRRuleManager</code> used in the prediction task.
	 * @param pirRuleManager the <code>PIRRuleManager</code> used in the prediction task.
	 */
	//void setPIRRuleManager(PIRRuleManager pirRuleManager);

	/**
	 * Perform the site feature prediction.
	 * @param predictionDirectory per rule based directory holding rule, alignment and prediction results.
	 * @param fastaDirectory 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	void predict(String predictionDirectory, String fastaDirectory) throws IllegalAccessException, InvocationTargetException;
	
	
}
