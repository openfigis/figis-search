package org.figis.search.xmlsearchenginecontrol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "element")
public class Element {

	@XmlAttribute
	private String name3;
	@XmlAttribute
	private String lang3;
	@XmlAttribute
	private String attrSetting;

}
