package CopyPaste;

import Application.Perspective;

public interface Mediator {
	void storeCopy(CopyStrategy strategy);
    void paste(Perspective p);
}
