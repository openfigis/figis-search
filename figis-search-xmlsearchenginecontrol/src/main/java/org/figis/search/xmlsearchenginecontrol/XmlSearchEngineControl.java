package org.figis.search.xmlsearchenginecontrol;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class XmlSearchEngineControl {

	List<ObjectType> objectTypeList;

}
