package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br>
 * <br>
 * 
 * The fusion block UniRules to which a given UniRule may be fused in some
 * instances
 */

public interface FusionBlock extends Line {
	/**
	 * Gets a list of proteins to be fused at N-terminal. Protein may be either
	 * a UniRule accession followed by an identifiers between round brackets
	 * (e.g. 'MF_00222 (aroE)'), or a designation between angle brackets (e.g.
	 * '<Thioredoxin domain>') if no UniRule is available.
	 *
	 * Example:
	 *
	 * Fusion: NT: None CT: MF_00222 (aroE); <Unknown>
	 *
	 * @return a list of proteins to be fused at N-terminal.
	 */
	List<String> getNTList();

	/**
	 * Sets a list of proteins to be fused at N-terminal. Protein may be either
	 * a UniRule accession followed by an identifiers between round brackets
	 * (e.g. 'MF_00222 (aroE)'), or a designation between angle brackets (e.g.
	 * '<Thioredoxin domain>') if no UniRule is available.
	 * 
	 * @param ntList
	 *            a list of proteins to be fused at N-terminal.
	 */
	void setNTList(List<String> ntList);

	/**
	 * Gets a list of proteins to be fused at C-terminal. Protein may be either
	 * a UniRule accession followed by an identifiers between round brackets
	 * (e.g. 'MF_00222 (aroE)'), or a designation between angle brackets (e.g.
	 * '<Thioredoxin domain>') if no UniRule is available.
	 *
	 * Example:
	 *
	 * Fusion: NT: None CT: MF_00222 (aroE); <Unknown>
	 *
	 * @return a list of proteins to be fused at C-terminal.
	 */
	List<String> getCTList();

	/**
	 * Sets a list of proteins to be fused at C-terminal. Protein may be either
	 * a UniRule accession followed by an identifiers between round brackets
	 * (e.g. 'MF_00222 (aroE)'), or a designation between angle brackets (e.g.
	 * '<Thioredoxin domain>') if no UniRule is available.
	 * 
	 * @param ntList
	 *            a list of proteins to be fused at C-terminal.
	 */
	void setCTList(List<String> ctList);
}
