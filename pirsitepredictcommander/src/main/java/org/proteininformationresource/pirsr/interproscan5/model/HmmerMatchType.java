//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.16 at 02:29:13 PM EDT 
//


package org.proteininformationresource.pirsr.interproscan5.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HmmerMatchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HmmerMatchType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ebi.ac.uk/interpro/resources/schemas/interproscan5}MatchType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="evalue" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="score" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HmmerMatchType")
@XmlSeeAlso({
    Hmmer2MatchType.class,
    Hmmer3MatchType.class
})
public abstract class HmmerMatchType
    extends MatchType
{

    @XmlAttribute(name = "evalue", required = true)
    protected double evalue;
    @XmlAttribute(name = "score", required = true)
    protected double score;

    /**
     * Gets the value of the evalue property.
     * 
     */
    public double getEvalue() {
        return evalue;
    }

    /**
     * Sets the value of the evalue property.
     * 
     */
    public void setEvalue(double value) {
        this.evalue = value;
    }

    /**
     * Gets the value of the score property.
     * 
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     */
    public void setScore(double value) {
        this.score = value;
    }

}
