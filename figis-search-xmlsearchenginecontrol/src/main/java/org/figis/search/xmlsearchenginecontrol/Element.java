package org.figis.search.xmlsearchenginecontrol;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "element")
public class Element {

	private String name;
	private String lang;
	private String attrSetting;

}
