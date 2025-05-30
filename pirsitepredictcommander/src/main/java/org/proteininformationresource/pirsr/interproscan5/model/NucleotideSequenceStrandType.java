//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.16 at 02:29:13 PM EDT 
//


package org.proteininformationresource.pirsr.interproscan5.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NucleotideSequenceStrandType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NucleotideSequenceStrandType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SENSE"/>
 *     &lt;enumeration value="ANTISENSE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NucleotideSequenceStrandType")
@XmlEnum
public enum NucleotideSequenceStrandType {

    SENSE,
    ANTISENSE;

    public String value() {
        return name();
    }

    public static NucleotideSequenceStrandType fromValue(String v) {
        return valueOf(v);
    }

}
