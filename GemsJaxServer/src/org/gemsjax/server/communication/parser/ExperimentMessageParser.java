package org.gemsjax.server.communication.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsMessage;
import org.gemsjax.shared.communication.message.experiment.ReferenceableExperimentMessage;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ExperimentMessageParser {
	public ReferenceableExperimentMessage parse(String xml) throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		Document dom = documentBuilder.parse(inputStream);

		// Validate root element
		Element rootElement = (Element) dom.getFirstChild();
		if (rootElement == null || rootElement.getTagName() != ReferenceableExperimentMessage.TAG)
			throw new DOMException(DOMException.SYNTAX_ERR, "No tag <" + ReferenceableExperimentMessage.TAG + "> found");
		if (dom.getChildNodes().getLength() != 1)
			throw new DOMException(DOMException.SYNTAX_ERR, "More than one <" + ReferenceableExperimentMessage.TAG
					+ "> tag found");

		// Check for reference number
		String referenceId = rootElement.getAttribute(ReferenceableExperimentMessage.ATTRIBUTE_REFERENCE_ID);

		// Validate child element
		Element childElement = (Element) rootElement.getFirstChild();
		if (childElement == null || rootElement.getChildNodes().getLength() != 1)
			throw new DOMException(DOMException.SYNTAX_ERR, "Exactly one child tag expected in <"
					+ ReferenceableExperimentMessage.TAG + ">");

		// Determine child type
		if (childElement.getTagName() == GetAllExperimentsMessage.TAG)
			return parseGetAllExperimentsMessage(referenceId, childElement);

		throw new DOMException(DOMException.SYNTAX_ERR, "The <" + ReferenceableExperimentMessage.TAG
				+ "> was found, but contains an unknown child tag <" + childElement.getTagName() + ">");
	}

	private GetAllExperimentsMessage parseGetAllExperimentsMessage(String referenceId, Element e) {
		return new GetAllExperimentsMessage(referenceId);
	}
}
