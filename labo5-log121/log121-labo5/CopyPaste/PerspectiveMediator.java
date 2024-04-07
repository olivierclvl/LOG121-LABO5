package CopyPaste;

import Application.Perspective;

public class PerspectiveMediator implements Mediator {

	private CopyStrategy lastCopyStrategy;

	/**
     * Store a copy of the strategy in the mediator
     * @param strategy The strategy to use for the copy
     */
    @Override
    public void storeCopy(CopyStrategy strategy) {
        lastCopyStrategy = strategy;
    }

    /**
     * Paste the copy of a strategy to a perspective
     * @param p The perspective who got the copy
     */
    @Override
    public void paste(Perspective p) {
        if (lastCopyStrategy != null) {
            lastCopyStrategy.paste(p);
        }
    }

}
