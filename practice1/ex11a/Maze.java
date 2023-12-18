package ex11a;
import java.util.*;
import java.util.stream.*;

public class Maze {
    public static void main(String[] args) {
        var maze = new Maze();
        maze.solve();
    }

    Map<String, List<String>> map = Map.of(
        "A", List.of("B", "C", "D"),
        "B", List.of("E", "F"),
        "C", List.of("G", "H"),
        "D", List.of("I", "J"));
    public void solve() {
        if (search("A") != null)
            System.out.println("found");
    }
    // 経路探索関数
    String search(String root) {
        List<String> openList = new ArrayList<String>();
        openList.add(root);

        while (openList.size() > 0) {
            var state = get(openList);

            if (isGoal(state))
                return state;

            var children = children(state);
            openList = concat(openList, children);
        }

        return null;
    }
    // ゴール判定関数
    boolean isGoal(String state) {
        return "E".equals(state);
    }
    // LISTから次のNODEを取得関数
    String get(List<String> list) {
        return list.remove(0);
    }
    // MAPから現在地点をキーに持つVALUEを取得関数
    List<String> children(String current) {
        return this.map.getOrDefault(current, Collections.emptyList());
    }
    // 探索ノードリストを結合関数
    List<String> concat(List<String> xs, List<String> ys) {
        return toMutable(Stream.concat(xs.stream(), ys.stream()).toList());
    }

    List<String> toMutable(List<String> list) {
        return new ArrayList<String>(list);
    }
}
