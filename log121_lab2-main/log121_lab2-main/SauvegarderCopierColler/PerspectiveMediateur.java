package SauvegarderCopierColler;

import Modele.Perspective;

public class PerspectiveMediateur implements Mediateur {

	private CopieStrategie lastCopieStrategie;

	/**
     * Store a copy of the strategy in the mediator
     * @param strategy The strategy to use for the copy
     */
    @Override
    public void storeCopy(CopieStrategie strategy) {
        lastCopieStrategie = strategy;
    }

    /**
     * Paste the copy of a strategy to a perspective
     * @param p The perspective who got the copy
     */
    @Override
    public void paste(Perspective p) {
        if (lastCopieStrategie != null) {
            lastCopieStrategie.paste(p);
        }
    }

}
