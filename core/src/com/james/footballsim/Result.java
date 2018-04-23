package com.james.footballsim;

public class Result {
	
	int resultType = 0;
	
	public Result(int resultType){
		this.resultType = resultType;
		//System.out.println("New Result: "+resultType);
	}
	
	public int getResult(){
		return resultType;
	}
	
	public boolean goalScored(){
		if(resultType == ResultType.GOAL_OPEN_PLAY){
			return true;
		} else if(resultType == ResultType.GOAL_FREE_KICK){
			return true;
		} else if(resultType == ResultType.GOAL_PENALTY){
			return true;
		}		
		return false;
	}

	public boolean freekickScored(){
		if(resultType == ResultType.GOAL_FREE_KICK){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean penaltyScored(){
		if(resultType == ResultType.GOAL_PENALTY){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean openPlayGoal(){
		if(resultType == ResultType.GOAL_OPEN_PLAY){
			return true;
		} else {
			return false;
		}
	}
}
