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
 * <p>Java class for SignalPOrganismType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SignalPOrganismType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EUK"/>
 *     &lt;enumeration value="GRAM_POSITIVE"/>
 *     &lt;enumeration value="GRAM_NEGATIVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignalPOrganismType")
@XmlEnum
public enum SignalPOrganismType {

    EUK,
    GRAM_POSITIVE,
    GRAM_NEGATIVE;

    public String value() {
        return name();
    }

    public static SignalPOrganismType fromValue(String v) {
        return valueOf(v);
    }

}
