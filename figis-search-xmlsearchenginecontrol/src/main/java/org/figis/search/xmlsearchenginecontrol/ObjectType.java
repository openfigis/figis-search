package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "objectType")
public class ObjectType {

	@XmlAttribute
	private String name;
	@XmlAttribute
	private String meta;
	@XmlAttribute
	private String base;

	private List<KeyWord> keyWord;

}
