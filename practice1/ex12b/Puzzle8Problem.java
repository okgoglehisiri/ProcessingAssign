package ex12b;

import java.util.*;

import search.*;

class Puzzle8Action implements Action {
    int arrow;

    static final Map<Integer, String> DIRECTION_MAP = Map.of(
        1, "右",
        -1, "左",
        3, "下",
        -3, "上"
    );

    static List<Action> all = List.of(
        new Puzzle8Action(1),
        new Puzzle8Action(-1),
        new Puzzle8Action(3),
        new Puzzle8Action(-3));

    Puzzle8Action(int arrow) {
        this.arrow = arrow;
    }

    public float cost() {
        return 1;
    }

    public String toString() {
        String direction = DIRECTION_MAP.getOrDefault(arrow, "不明");
        return String.format("移動方向: %s", direction);
    }
}
class Puzzle8World implements World {
    int[] board =new int[9];
    public Puzzle8World(int[] board) {
        this.board = board.clone();
    }
    public Puzzle8World clone() {
        return new Puzzle8World(board.clone());
    }
    // 1次元配列boardが0から8までのすべての数字を正確に1回ずつ含んでいるかをチェックしています。
    // ただし、このチェックだけでは解けない状態（例えば、タイルの順番が完全に逆の状態）が有効と判断される可能性があります。
    // 完全なチェックを行うには、タイルの順番に基づいて解けるかどうかの判断（パリティチェックなど）を行う必要がありますが、これは実装が複雑になります。
    // 通常は、解ける初期状態から始めて、有効な動きのみを使用して新しい状態を生成することで、この問題を回避します。
    public boolean isValid() {
        boolean[] seen = new boolean[9];
        for (int i = 0; i < board.length; i++) {
            if (board[i] < 0 || board[i] > 8) return false;
        
            if (seen[board[i]]) return false;
            seen[board[i]] = true;
        }
        return true;
    }
    public boolean isGoal() {
        // ゴール状態のチェック
        for (int i = 0; i < board.length; i++) {
            if ((board[i]+1)%9 != i) return false;
        }
        return true;
    }
    public List<Action> actions() {
        return Puzzle8Action.all;
    }
    public World successor(Action action) {
        var a = (Puzzle8Action) action;
        var next = clone();
        int blankIndex = -1;
        for(int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                blankIndex = i;
                break;
            }
        }
        int targetIndex = blankIndex + a.arrow;

        if (targetIndex < 0 || targetIndex >= board.length || !isValidMove(blankIndex, targetIndex)) {
        return null;
        }
        next.board[blankIndex] = next.board[targetIndex];
        next.board[targetIndex] = 0;

        return next;
    }
    
    private boolean isValidMove(int blankIndex, int targetIndex) {
        if (blankIndex % 3 == 0 && targetIndex % 3 == 2) return false;
        if (blankIndex % 3 == 2 && targetIndex % 3 == 0) return false;
        return true;
    }
    public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < board.length; i++) {
                sb.append(board[i]).append(" ");
                if ((i + 1) % 3 == 0) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
}