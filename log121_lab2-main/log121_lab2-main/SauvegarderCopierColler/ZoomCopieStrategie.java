package SauvegarderCopierColler;

import Modele.Perspective;

public class ZoomCopieStrategie implements CopieStrategie {
	Perspective copiedParameters;

	/**
     * Copy the scale Strategy
     * @param parameters The parameter of the perspective to copy
     */
	@Override
	public void copy(Perspective parameters) {
		copiedParameters = new Perspective();
		copiedParameters.setScale(parameters.getScale());
	}

	/**
     * Paste the scale Strategy
     * @param parameters The parameter of the perspective to paste
     */
	@Override
	public void paste(Perspective parameters) {
		parameters.setScale(copiedParameters.getScale());
	}

}
