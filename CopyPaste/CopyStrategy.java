package CopyPaste;

import Application.Perspective;

public interface CopyStrategy {
	void copy(Perspective parameters);
    void paste(Perspective parameters);
}

