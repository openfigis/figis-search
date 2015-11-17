package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.NONE)
// @XmlRootElement(name = "objectType")
@XmlType(name = "objectType", propOrder = { "name", "meta", "base", "keyWordList" })
public class ObjectType {

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String meta;

	@XmlAttribute
	private String base;

	@XmlElements(@XmlElement(name = "keyword"))
	private List<KeyWord> keyWordList;

}
