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
    int depth;
    State(State parent, Action action, World child, int depth) {
        this.action = action;
        this.parent = parent;
        this.world = child;
        this.depth = depth;
    }

    public boolean isGoal() {
        return this.world.isGoal();
    }

    List<State> children() {
        return this.world.actions().stream()
                .map(a -> new State(this, a, this.world.successor(a),depth+1))
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

public class IDSSolver {
    public void solve(World world) {
        var root = new State(null, null, world,0);
        int limit_depth;

        // 深さ制限を増やしていくことで反復深化探索を行う
        for(limit_depth = 3;limit_depth <= 20;limit_depth++){
        var goal = search(root, limit_depth);
        
        if (goal != null){
            printSolution(goal);
            break;
            }
        }
    }

    State search(State root, int limit_depth) {
        var openList = toMutable(List.of(root));
        int maxOpenListLength = 0;
        int visitedNodeCount = 0;
        var closedList = new HashSet<String>();
        while (openList.isEmpty() == false) {
            var state = get(openList);
            visitedNodeCount += 1;
            closedList.add(state.world.toString());
            System.out.println(state.depth);
            if (state.isGoal()) {
                System.out.println("オープンリストの最大長" + maxOpenListLength);
                System.out.println("訪問ノード数：" + visitedNodeCount);
                return state;
            }

            var children = state.children();
            
            // 深さ制限を超えるNODEを追加しない
            var selectNode = children.stream()
                                        .filter(element -> (element.depth <= limit_depth))
                                        .toList();
                                
            openList = concat(selectNode, openList);
            
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
