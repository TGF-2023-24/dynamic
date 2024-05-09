package pnpl.analysis.dynamic.gpetrinet;

public class GToken {

	//If timestamp == -1, do not consider time
	private int timestamp;
	
	GToken(int time){
		this.timestamp = time;
	}
	
	GToken(){
		this.timestamp = -1;
	}
	
	public void setTimestamp(int time) {
		this.timestamp = time;
	}
	
	public int getTimestamp() {
		return this.timestamp;
	}
	
}
