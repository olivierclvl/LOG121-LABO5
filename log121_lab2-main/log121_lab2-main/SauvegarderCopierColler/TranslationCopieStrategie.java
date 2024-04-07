package SauvegarderCopierColler;

import Modele.Perspective;

public class TranslationCopieStrategie implements CopieStrategie {
	Perspective copiedParameters;

	/**
     * Copy and translation Strategy
     * @param parameters The parameter of the perspective to copy
     */
    @Override
    public void copy(Perspective parameters) {
        copiedParameters = new Perspective();
        copiedParameters.setPosition(parameters.getPosition());
    }

    /**
     * Paste the translation Strategy
     * @param parameters The parameter of the perspective to paste
     */
    @Override
    public void paste(Perspective parameters) {
        parameters.setPosition(copiedParameters.getPosition());
    }
}
