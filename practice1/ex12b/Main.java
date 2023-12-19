package ex12b;
import search.*;
public class Main {
    public static void main(String[] args) {
        var h = new Puzzle8Heuristic();
        int[] map = {2,3,5,7,1,6,4,8,0};
        
        var minCostSearch = new InformedSolver(Evaluator.minCost());
        var bestFirstSearch = new InformedSolver(Evaluator.bestFirst(h));
        var aStarSearch = new InformedSolver(Evaluator.aStar(h));


        // minCostSearch.solve(new Puzzle8World(map));
        // bestFirstSearch.solve(new Puzzle8World(map));
        minCostSearch.solve(new Puzzle8World(map));

    }
}