package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "objectType")
public class ObjectType {

	@XmlAttribute
	private String name1;

	@XmlAttribute
	private String meta1;

	@XmlAttribute
	private String base1;

	private List<KeyWord> keyWord;

}
