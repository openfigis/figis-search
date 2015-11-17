package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "keyWord")
@XmlType(name = "keyWord", propOrder = { "name", "base", "elementList" })
public class KeyWord {

	@XmlAttribute
	private String name;
	@XmlAttribute
	private String base;

	@XmlElements(@XmlElement(name = "element"))
	private List<Element> elementList;

}
