package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "objectType")
public class ObjectType {

	private String name;
	private String meta;
	private String base;

	private List<KeyWord> keyWord;

}
