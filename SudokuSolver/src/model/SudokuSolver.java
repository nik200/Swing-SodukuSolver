package model;

import java.util.LinkedList;

public class SudokuSolver 
{ 
	public Grid solve(Grid startingState)
    {
		Long time=System.currentTimeMillis();
		if(!startingState.verifyInitialState()) return null;
		startingState.setProbables();
		startingState.simplify();
		//System.out.println(startingState);		
		LinkedList<Grid> list = new LinkedList<Grid>();
		list.offerLast(startingState);
		//System.out.println("First grid added.");
		int maxLen=0;
		while(list.size()!=0){
			Grid g = list.peekLast().getNext();
			if(g==null){
				if(list.peekLast().isResult()){
					Grid result = list.peekLast();
					time = System.currentTimeMillis()-time;
					result.setTime(time);
					return result;
				}
				else{ list.removeLast();System.gc();}
			}
			else{
				if(g.integrityCheck()){
					list.offerLast(g);
					//System.console().readLine();
				}	
			}
			//System.out.println(list.peekLast());
			if(list.size()>maxLen) maxLen++;
			//System.out.println("Length:"+maxLen);
		}
		return null;
    }
}
