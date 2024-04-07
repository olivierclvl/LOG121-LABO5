package SauvegarderCopierColler;

import Modele.Perspective;

public class ZoomTranslationCopieStrategie implements CopieStrategie {
	private Perspective copiedParameters;

	/**
     * Copy both the scale and translation Strategy
     * @param parameters The parameter of the perspective to copy
     */
    @Override
    public void copy(Perspective parameters) {
        copiedParameters = new Perspective();
        copiedParameters.setScale(parameters.getScale());
        copiedParameters.setPosition(parameters.getPosition());
    }

    /**
     * Paste both the scale and translation Strategy
     * @param parameters The parameter of the perspective to paste
     */
    @Override
    public void paste(Perspective parameters) {
        parameters.setScale(copiedParameters.getScale());
        parameters.setPosition(copiedParameters.getPosition());
    }
}
