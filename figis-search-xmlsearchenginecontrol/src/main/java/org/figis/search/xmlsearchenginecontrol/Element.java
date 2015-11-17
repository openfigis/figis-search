package org.figis.search.xmlsearchenginecontrol;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "element")
public class Element {

	@XmlAttribute
	private String name;
	@XmlAttribute
	private String lang;
	@XmlAttribute
	private String attrSetting;

}
