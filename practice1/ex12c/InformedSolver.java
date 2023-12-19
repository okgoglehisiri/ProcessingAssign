package ex12c;

import java.util.*;
import java.util.stream.*;

public class InformedSolver {
    Evaluator eval;
    long visited = 0;
    long maxLen = 0;
    long maxclosedlen = 0;
    public InformedSolver(Evaluator eval) {
        this.eval = eval;
    }

    public void solve(World world) {
        var root = new State(null, null, world);
        var goal = search(root);

        if (goal != null)
            printSolution(goal);

        System.out.printf("visited: %d, max length: %d, max closedList: %d\n", this.visited, this.maxLen, this.maxclosedlen);
    }

    State search(State root) {
        var openList = toMutable(List.of(root));
        Set<Integer> visitedSet = new HashSet<>();  // 訪れた状態を記録するセット（整数型）

        while (!openList.isEmpty()) {
            var state = get(openList);
            this.visited += 1;

            Integer stateKey = state.world.toInt();
            if (visitedSet.contains(stateKey)) {
                continue;  // 既に訪れた状態の場合はスキップ
            }
            visitedSet.add(stateKey);  // 状態を訪れたセットに追加
            maxclosedlen = visitedSet.size();
            System.out.println(state);
            if (state.isGoal())
                return state;

            var children = state.children().stream()
                .filter(s -> !visitedSet.contains(s.world.toInt()))  // 未訪問の子状態のみを選択
                .collect(Collectors.toList());
            openList = concat(openList, children);

            sort(openList);
            this.maxLen = Math.max(this.maxLen, openList.size());
        }

        return null;
    }

    State get(List<State> list) {
        return list.remove(0);
    }

    List<State> concat(List<State> xs, List<State> ys) {
        return toMutable(Stream.concat(xs.stream(), ys.stream()).toList());
    }

    void sort(List<State> list) {
        list.sort(Comparator.comparing(s -> this.eval.f(s)));
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
