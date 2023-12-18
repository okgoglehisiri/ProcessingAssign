package ex11d;

import java.util.*;

public class MisCanProblem {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        var solver = new DFSSolver();
        solver.solve(new MisCanWorld(3, 3, 1));
        long endTime = System.currentTimeMillis();
        System.out.println("実行時間(ミリ秒):"+ (endTime-startTime));

        
    }
}

class MisCanAction implements Action {
    int missionary;
    int cannibal;
    int boat;

    static List<Action> all = List.of(
            new MisCanAction(-2, 0, -1),
            new MisCanAction(-1, -1, -1),
            new MisCanAction(0, -2, -1),
            new MisCanAction(-1, 0, -1),
            new MisCanAction(0, -1, -1),
            new MisCanAction(+2, 0, +1),
            new MisCanAction(+1, +1, +1),
            new MisCanAction(0, +2, +1),
            new MisCanAction(+1, 0, +1),
            new MisCanAction(0, +1, +1));

    MisCanAction(int missionary, int cannibal, int boat) {
        this.missionary = missionary;
        this.cannibal = cannibal;
        this.boat = boat;
    }

    public String toString() {
        var dir = this.boat < 0 ? "right" : "left ";
        var m = Math.abs(this.missionary);
        var c = Math.abs(this.cannibal);
        return String.format("move %d Missionary and %d Cannibal to %s", m, c, dir);
    }
}

class MisCanWorld implements World {
    int missionary;
    int cannibal;
    int boat;

    public MisCanWorld(int missionary, int cannibal, int boat) {
        this.missionary = missionary;
        this.cannibal = cannibal;
        this.boat = boat;
    }
    

    public MisCanWorld clone() {
        return new MisCanWorld(this.missionary, this.cannibal, this.boat);
    }

    public boolean isValid() {
        if (this.missionary < 0 || this.missionary > 3) return false;
        if (this.cannibal < 0 || this.cannibal > 3) return false;
        if (this.boat < 0 || this.boat > 1) return false;
        if (this.missionary > 0 && this.missionary < this.cannibal) return false;
        if ((3 - this.missionary) > 0 && (3 - this.missionary) < (3 - this.cannibal)) return false;
        return true;
    }

    public boolean isGoal() {
        return this.missionary == 0 && this.cannibal == 0;
    }

    public List<Action> actions() {
        return MisCanAction.all;
    }

    public World successor(Action action) {
        var a = (MisCanAction) action;
        var next = clone();
        next.missionary += a.missionary;
        next.cannibal += a.cannibal;
        next.boat += a.boat;
        return next;
    }
    public String toString() {
        var ms = List.of("...", "m..", "mm.", "mmm");
        var cs = List.of("...", "c..", "cc.", "ccc");
        var bs = List.of("| ~~~b|", "|b~~~ |");
        var ml = ms.get(this.missionary);
        var cl = cs.get(this.cannibal);
        var b = bs.get(this.boat);
        var mr = ms.get(3 - this.missionary);
        var cr = cs.get(3 - this.cannibal);
        return String.format("%s%s %s %s%s", ml, cl, b, mr, cr);
    }
}