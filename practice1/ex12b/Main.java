package ex12b;
import search.*;
public class Main {
    public static void main(String[] args) {
        // var h = new MisCanHeuristic();
        
        var minCostSearch = new InformedSolver(Evaluator.minCost());
        int[] map = {2,3,5,7,1,6,4,8,0};
        minCostSearch.solve(new Puzzle8World(map));

        // var bestFirstSearch = new InformedSolver(Evaluator.bestFirst(h));
        // bestFirstSearch.solve(new MisCanWorld(3, 3, 1));

        // var aStarSearch = new InformedSolver(Evaluator.aStar(h));
        // aStarSearch.solve(new MisCanWorld(3, 3, 1));

    }
}