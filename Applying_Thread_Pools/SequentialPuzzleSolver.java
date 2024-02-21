import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SequentialPuzzleSolver <P,M>{

    private final Puzzle<P,M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P,M> puzzle) {
        this.puzzle = puzzle;
    }
    
    public List<M> solve() {
        P pos = puzzle.initialPosition();
        return search(new PuzzleNode<>(pos,null,null));
    }

    private List<M> search(PuzzleNode<P,M> node) {
        if (!seen.contains(node.pos)) {
            seen.add(node.pos);
            if (puzzle.isGoal(node.pos))
                return node.asMoveList();
            for (M move : puzzle.legalMoves(node.pos)) {
                P pos = puzzle.move(node.pos, move);
                PuzzleNode<P, M> child = new PuzzleNode<>(pos, move, node);
                List<M> result = search(child);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    static class PuzzleNode <P,M> {
        final P pos;
        final M move;
        final PuzzleNode<P,M> prev;

        public PuzzleNode(P pos,M move,PuzzleNode<P,M> prev) {
            this.pos = pos;
            this.move = move;
            this.prev = prev;
        }

        List<M> asMoveList() {
            List<M> solution = new LinkedList<>();
            for (PuzzleNode<P,M> n = this;n.move != null;n = n.prev){
                solution.add(0,n.move);
            }
            return solution;
        }
    }
}
