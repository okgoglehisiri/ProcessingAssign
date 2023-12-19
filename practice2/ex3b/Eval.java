package ex3b;

public class Eval {
    float value(State state) {
        var s = state.winner().getSign();
        return state.isGoal() ? Float.POSITIVE_INFINITY * s : s / state.numStones;
    }
}