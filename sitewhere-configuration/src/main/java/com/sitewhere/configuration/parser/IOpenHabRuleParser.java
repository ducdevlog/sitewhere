package com.sitewhere.configuration.parser;

public interface IOpenHabRuleParser {

	// Root element name.
	public static final String ROOT = "openhab-rule";

	/**
	 * Expected child elements.
	 *
	 * @author Duc
	 */
	public static enum Elements {

		/** Default registration manager */
		DefaultOpenHabRuleManager("default-openhab-rule-manager");

		/** Event code */
		private String localName;

		private Elements(String localName) {
			this.localName = localName;
		}

		public static Elements getByLocalName(String localName) {
			for (Elements value : Elements.values()) {
				if (value.getLocalName().equals(localName)) {
					return value;
				}
			}
			return null;
		}

		public String getLocalName() {
			return localName;
		}

		public void setLocalName(String localName) {
			this.localName = localName;
		}
	}
}
