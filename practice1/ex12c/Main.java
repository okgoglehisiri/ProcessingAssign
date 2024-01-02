package ex12c;

public class Main {
    public static void main(String[] args) {
        var h = new Puzzle8Heuristic();

        int[] map = {2,3,5,7,1,6,4,8,0};
        var minCostSearch = new InformedSolver(Evaluator.minCost());
        var bestFirstSearch = new InformedSolver(Evaluator.bestFirst(h));
        var aStarSearch = new InformedSolver(Evaluator.aStar(h));
        long startTime = System.currentTimeMillis();

        // minCostSearch.solve(new Puzzle8World(map));
        // bestFirstSearch.solve(new Puzzle8World(map));
        bestFirstSearch.solve(new Puzzle8World(map));

        long endTime = System.currentTimeMillis();
        System.out.println("実行時間(ミリ秒):"+ (endTime-startTime));
    }
}