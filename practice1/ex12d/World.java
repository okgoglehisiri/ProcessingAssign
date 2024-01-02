package ex12d;

import java.util.List;

public interface World extends Cloneable {
    boolean isValid();
    boolean isGoal();
    List<Action> actions();
    World successor(Action action);
    Integer toInt();
}

