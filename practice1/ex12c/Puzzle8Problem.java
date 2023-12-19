package ex12c;

import java.util.*;


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
        for(int i = 0; i < board.length; i++){
            this.board[i] = board[i];
        }
    }
    public Puzzle8World clone() {
        
        return new Puzzle8World(this.board);
    }

    public boolean isValid() {
        boolean[] seen = new boolean[9];
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i] < 0 || this.board[i] > 8) return false;
        
            if (seen[this.board[i]]) return false;
            seen[this.board[i]] = true;
        }
        return true;
    }
    public boolean isGoal() {
        // ゴール状態のチェック
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i]%9 != i) return false;
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

        // 空白の位置を見つける
        for(int i = 0; i < this.board.length; i++) {
            if (this.board[i] == 0) {
                blankIndex = i;
                break;
            }
        }

        int targetIndex = blankIndex + a.arrow;

        // 移動が盤面内で有効かチェック,無効なら捨てる
        if (targetIndex < 0 || targetIndex > 8) {

            return this;
        }
        // 空白とターゲットを交換
        next.board[blankIndex] = next.board[targetIndex];
        next.board[targetIndex] = 0;
        return next;
        
    }

 


    public Integer toInt(){
        int ib = 0;
        for (int i = 0; i < this.board.length; i++){
            ib = ib * 10 + this.board[i];
        }
        return ib;
    }


    public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.board.length; i++) {
                sb.append(this.board[i]).append(" ");
                if ((i + 1) % 3 == 0) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
}
class Puzzle8Heuristic implements Heuristic {
    public float eval(State s) {
        var w = (Puzzle8World) s.world();
        int distance = 0;
        for (int i = 0; i < w.board.length; i++) {
            if (w.board[i]%9 != i) distance += 1;
        }
        return distance;
    }
}