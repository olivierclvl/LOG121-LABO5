package Commands;

import Application.Perspective;

public abstract class Command {
    public abstract void execute(Perspective p, double x, double y);
}
