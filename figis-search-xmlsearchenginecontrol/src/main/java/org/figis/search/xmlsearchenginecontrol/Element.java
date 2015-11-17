package org.figis.search.xmlsearchenginecontrol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "element")
@XmlType(name = "element", propOrder = { "name", "lang", "attrSetting" })
public class Element {

	@XmlAttribute
	private String name;
	@XmlAttribute
	private String lang;
	@XmlAttribute
	private String attrSetting;

}
