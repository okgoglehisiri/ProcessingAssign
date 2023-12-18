package ex11e;

import java.util.*;
import java.util.stream.*;

interface World extends Cloneable {
    boolean isValid();
    boolean isGoal();
    List<Action> actions();
    World successor(Action action);
}

interface Action {
}

class State {
    State parent;
    Action action;
    World world;

    State(State parent, Action action, World child) {
        this.action = action;
        this.parent = parent;
        this.world = child;
    }

    public boolean isGoal() {
        return this.world.isGoal();
    }

    List<State> children() {
        return this.world.actions().stream()
                .map(a -> new State(this, a, this.world.successor(a)))
                .filter(s -> s.world.isValid())
                .toList();
    }

    public String toString() {
        if (this.action != null) {
            return String.format("%s (%s)", this.world, this.action);
        } else {
            return this.world.toString();
        }
    }
}

public class CLSSolver {
    public void solve(World world) {
        var root = new State(null, null, world);
        var goal = search(root);

        if (goal != null)
            printSolution(goal);
    }

    State search(State root) {
        var openList = toMutable(List.of(root));
        int maxOpenListLength = 0;
        int visitedNodeCount = 0;
        var closedList = new HashSet<String>();
        while (openList.isEmpty() == false) {
            var state = get(openList);
            visitedNodeCount += 1;
            System.out.println(state.world);

            if (state.isGoal()) {
                System.out.println("オープンリストの最大長" + maxOpenListLength);
                System.out.println("訪問ノード数：" + visitedNodeCount);
                return state;
            }
            // 現在のSTATEがすでにCHILDRENを展開したNODEならCONTINUEすることで無駄な展開を行わない。
            if (closedList.contains(state.world.toString())) continue;
            // CLOSEDLISTに現在のSTATEを追加
            closedList.add(state.world.toString());
            
            var children = state.children();
            openList = concat(openList, children);
            
            if (openList.size() > maxOpenListLength) maxOpenListLength = openList.size();

        }

        return null;
    }

    State get(List<State> list) {
        return list.remove(0);
    }

    List<State> concat(List<State> xs, List<State> ys) {
        return toMutable(Stream.concat(xs.stream(), ys.stream()).toList());
    }

    List<State> toMutable(List<State> list) {
        return new ArrayList<State>(list);
    }

    void printSolution(State goal) {
        var list = new ArrayList<State>();

        while (goal != null) {
            list.add(0, goal);
            goal = goal.parent;
        }

        list.forEach(System.out::println);
    }
}
