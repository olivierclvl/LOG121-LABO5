package SauvegarderCopierColler;

import Modele.Perspective;

public class AucuneCopieStrategie implements CopieStrategie {

	/**
     * Copy nothing Strategy
     * @param parameters The parameter of the perspective to copy
     */
	@Override
	public void copy(Perspective parameters) {
		
	}

	/**
     * Paste the null Strategy
     * @param parameters The parameter of the perspective to paste
     */
	@Override
	public void paste(Perspective parameters) {

	}

}
