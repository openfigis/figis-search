package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "keyWord")
public class KeyWord {

	private String name;
	private String base;

	private List<Element> elementList;

}
