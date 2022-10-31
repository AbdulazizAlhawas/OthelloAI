package students.Alhawas;
import java.util.Date;
import edu.ksu.csc.ai.othello.*;
import edu.ksu.csc.ai.othello.GameState.GameStatus;
import edu.ksu.csc.ai.othello.GameState.Player;



public class AlphaBeta extends OthelloPlayer{

	Square Action;
	Player current;
	
	int maxd=6;
	
	
	public AlphaBeta(String name) {
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
		
		Max(depth,a,Integer.MAX_VALUE);
		return Action;
	}
	
	
	public int Min(int depth, GameState g, int alpha) {
		
		if(depth>=maxd || g.getStatus()!= GameStatus.PLAYING) {
			//return g.getScore(current);
			return score(g,current);
		}
		int beta=Integer.MAX_VALUE;
		int v=Integer.MAX_VALUE;
		if(g.getCurrentPlayer()== current)
			v=Max(depth,g,beta);
		else {
		depth+=1;
		int temp=0;
		Square next=null;
		Square[] arr=  g.getValidMoves().toArray(new Square[0]);
		GameState t=g;
		for(int i=0;i<arr.length;i++) {
			
			if(v>(temp=Max(depth,t.applyMove(arr[i]),beta))) {
				v=temp;
				if(beta>v)
					beta=v;
				next=arr[i];
				
			}
			if(beta<=alpha)
				break;
		}
		
		Action=next;
		}
		return v;
	}
	
	
	public int Max(int depth, GameState g, int beta) {
		
		if(depth>=maxd || g.getStatus()!= GameStatus.PLAYING) {
			//return g.getScore(current);
			return score(g,current);
		}
		
		int alpha=Integer.MIN_VALUE;
		int v=Integer.MIN_VALUE;
		if(g.getCurrentPlayer()!= current)
			v=Min(depth,g,alpha);
		else {
		depth+=1;
		int temp=0;
		Square[] arr=  g.getValidMoves().toArray(new Square[0]);
		Square next=null;
		GameState t=g;
		for(int i=0;i<arr.length;i++) {
			
			if(v<(temp=Min(depth,t.applyMove(arr[i]),alpha))) {
				v=temp;
				if(alpha<v)
					alpha=v;
				next=arr[i];
				
			}
			if(beta<=alpha)
				break;
		}
		
		
		Action=next;
		}
		return v;
	}
	
	
public int score(GameState curr, Player player) {
		
		int count = 0;
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(curr.getSquare(i,j) == player) {
					count++;
				if((i==0&&j==0)||(i==0&&j==7)||(i==7&&j==0)||(i==7&&j==7))
					count+=2;
				if((i==0&&j==2)||(i==0&&j==3)||(i==0&&j==4)||(i==0&&j==5))
					count++;
				if((i==2&&j==0)||(i==3&&j==0)||(i==4&&j==0)||(i==5&&j==0))
					count++;
				if((i==2&&j==7)||(i==3&&j==7)||(i==4&&j==7)||(i==5&&j==7))
					count++;
				if((i==7&&j==2)||(i==7&&j==3)||(i==7&&j==4)||(i==7&&j==5))
					count++;
			}
		}
	}
		
		return count;
	}
}
