package students.Alhawas;
import java.util.Date;
import edu.ksu.csc.ai.othello.*;
import edu.ksu.csc.ai.othello.GameState.GameStatus;
import edu.ksu.csc.ai.othello.GameState.Player;



public class Minimax2 extends OthelloPlayer{

	Square Action;
	Player current;
	int maxd=3;
	
	public Minimax2(String name) {
		super(name);
	}

	/**
	 * Returns a random, valid move from <code>currentState</code>.
	 */
	public Square getMove(GameState currentState, Date deadline) {
		current= currentState.getCurrentPlayer();
		Square square= MinMaxD(currentState);
this.registerCurrentBestMove(square);
		
		if(deadline != null)
			log("I have " + this.getMillisUntilDeadline() + "ms remaining until the deadline.");
		
		/* registerCurrentBestMove(...) can be called multiple times to reset the current best
		 * move before returning from this function. */
		
		/* return the move that we have chosen */
		log("Example player is moving to " + square + "...");
		return square;
		
	}
	
	public Square MinMaxD(GameState a) {
		Action=null;
		int depth=0;
		Max(depth,a);
		return Action;
	}
	
	
	public int Min(int depth, GameState g) {
		if(depth>=maxd || g.getStatus()!= GameStatus.PLAYING) {
			return g.getScore(current);
		}
		int v=Integer.MAX_VALUE;
		depth+=1;
		int temp=0;
		Square next=null;
		Square[] arr=  g.getValidMoves().toArray(new Square[0]);
		GameState t=g;
		for(int i=0;i<arr.length;i++) {
			if(v>(temp=Max(depth,t.applyMove(arr[i])))) {
				v=temp;
				next=arr[i];
			}
		}
		Action=next;
		return v;
	}
	
	
	public int Max(int depth, GameState g) {
		if(depth>=maxd || g.getStatus()!= GameStatus.PLAYING) {
			return g.getScore(current);
		}
		
		int v=Integer.MIN_VALUE;
		depth+=1;
		int temp=0;
		Square[] arr=  g.getValidMoves().toArray(new Square[0]);
		Square next=null;
		GameState t=g;
		for(int i=0;i<arr.length;i++) {
			if(v<(temp=Min(depth,t.applyMove(arr[i])))) {
				v=temp;
				next=arr[i];
			}
		}
		
		Action=next;
		return v;
	}
}
