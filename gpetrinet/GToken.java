package pnpl.analysis.dynamic.gpetrinet;

import pnpl.analysis.dynamic.reachabilitygraph.State;

public class GToken {

	//If timestamp == -1, do not consider time
	private int timestamp;
	
	public GToken(int time){
		this.timestamp = time;
	}
	
	public GToken(GToken token){
		this.timestamp = token.getTimestamp();
	}
	
	public GToken(){
		this.timestamp = -1;
	}
	
	public void setTimestamp(int time) {
		this.timestamp = time;
	}
	
	public int getTimestamp() {
		return this.timestamp;
	}
	
	public String toString() {
		return "" + this.timestamp;
	}
	
	/*
	@Override
	public int hashCode() {
        return Integer.hashCode(this.timestamp);
    }*/
	

	public boolean equals(Object obj) {
		
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		GToken other = (GToken) obj;
		if (this.timestamp != other.timestamp)
			return false;
		return true;
	}
	
}
