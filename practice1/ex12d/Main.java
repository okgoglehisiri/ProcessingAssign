package ex12d;

public class Main {
    public static void main(String[] args) {
        var h1 = new Puzzle8H1();
        var h2 = new Puzzle8H2();
        var h3 = new Puzzle8H3();
        int[] map = {2,3,5,7,1,6,4,8,0};
        
        var bestFirstSearch = new InformedSolver(Evaluator.bestFirst(h1));
        long startTime = System.currentTimeMillis();

        bestFirstSearch.solve(new Puzzle8World(map));

        long endTime = System.currentTimeMillis();
        System.out.println("実行時間(ミリ秒):"+ (endTime-startTime));
    }
}