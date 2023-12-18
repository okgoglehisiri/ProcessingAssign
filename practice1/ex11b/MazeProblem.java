package ex11b;

import java.util.*;

public class MazeProblem {
    public static void main(String[] args) {
        var solver = new Solver();
        solver.solve(new MazeWorld("A"));
    }
}

class MazeAction implements Action {
    String next;

    MazeAction(String next) {
        this.next = next;
    }

    public String toString() {
        return "move to " + this.next;
    }
}

class MazeWorld implements World {
    Map<String, List<String>> map = Map.of(
        "A", List.of("B", "C", "D"),
        "B", List.of("E", "F"),
        "C", List.of("G", "H"),
        "D", List.of("I", "J"));

    String current;

    MazeWorld(String current) {
        this.current = current;
    }
    // 有効なWORLDかどうか判定関数
    public boolean isValid() {
        return true;
    }
    // ゴール地点判定関数
    public boolean isGoal() {
        return "E".equals(this.current);
    }
    // 現在地点をキーに持つ次点ノードを取得関数
    public List<Action> actions() {
        return this.map.getOrDefault(current, Collections.emptyList()).stream()
            .map(p -> (Action) new MazeAction(p))
            .toList();
    }
    // 移動後のWORLDを生成関数
    public World successor(Action action) {
        var a = (MazeAction) action;
        return new MazeWorld(a.next);
    }

    public String toString() {
        return this.current;
    }
}
