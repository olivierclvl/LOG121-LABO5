package SauvegarderCopierColler;

import Modele.Perspective;

public interface CopieStrategie {
	void copy(Perspective parameters);
    void paste(Perspective parameters);
}

