package SauvegarderCopierColler;

import Modele.Perspective;

public interface Mediateur {
	void storeCopy(CopieStrategie strategy);
    void paste(Perspective p);
}
