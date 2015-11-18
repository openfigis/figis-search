package org.figis.search.config.ref;

public class FigisSearchException extends RuntimeException {

	public FigisSearchException(Exception e) {
		super(e);
	}

	public FigisSearchException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6053287393875016375L;

}
