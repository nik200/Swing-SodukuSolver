package model;

public class Grid implements Cloneable{
	private byte array[][];
	private boolean probables[][][] = new boolean[9][9][10];
	private int nextCount = 0;
	private long time;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Grid(byte arr[][]){
		setArray(arr);
	}
	public Grid(){
	
	}
	
	public boolean[][][] getProbables(){
		return probables;
	}
	public byte[][] getArray(){
		return array;
	}
	public void setProbables(boolean[][][] probs){
		this.probables = probs;
	}
	public void setArray(byte[][] arr){
		this.array = new byte[9][9];
		for(int i=0;i<81;i++)
				array[i/9][i%9] = arr[i/9][i%9];
	}
	public void setProbables(){
		int i,j,k,a,b;
		probables = new boolean[9][9][10];
		for(i=0;i<9;i++){
			for(j=0;j<9;j++){
				if(array[i][j] !=0) probables[i][j][0]=true;
				else{
					for(k=1;k<10;k++) probables[i][j][k] = true;
					a = (i/3)*3;
					b = (j/3)*3;
					for(k=0;k<9;k++){
						if(array[i][k]!=0) probables[i][j][array[i][k]] = false;
						if(array[k][j]!=0) probables[i][j][array[k][j]] = false;
						if(array[a+(k/3)][b+(k%3)]!=0) probables[i][j][array[a+(k/3)][b+(k%3)]] = false;
					}
				}
			}
		}
	}
	
	public boolean fix(){
		int count=0,pos=0;//a,b;
		boolean change=false;
		for(int i=0;i<9;i++)for(int j=0;j<9;j++){
			count=0;
			if(!probables[i][j][0]){	
				for(int k=1;k<10;k++)
					if(probables[i][j][k]){ count++; pos=k;}
				if(count==1){ 
					change = true;
					array[i][j] = (byte)pos;
					this.setProbables();
					/*probables[i][j][pos]=false;
					probables[i][j][0] = true;
					a = (i/3)*3;
					b = (j/3)*3;
					for(int k=0;k<9;k++){
						if(probables[i][k][array[i][j]]) probables[i][k][array[i][j]] = false;
						if(probables[k][j][array[i][j]]) probables[k][j][array[i][j]] = false;
						if(probables[a+(k/3)][b+(k%3)][array[i][j]]) probables[a+(k/3)][b+(k%3)][array[i][j]] = false;					
					}*/
				}
			}
		}
		return change;
	}
	
	public boolean integrityCheck(){
	int count=0;
		for(int i=0;i<9;i++)for(int j=0;j<9;j++){
			count=0;
			if(!probables[i][j][0]){	
				for(int k=1;k<10;k++)
					if(probables[i][j][k]){count++;}
				if(count==0) return false;
			}
		}
		return true;	
	}
	
	public boolean verifyInitialState(){
		boolean[] arr;
		int count=0;
		for(int i=0;i<81;i++)
			if(array[i/9][i%9]!=0) count++;
		if(count<17) return false;
		for(int i=0;i<9;i++){
			arr = new boolean[9];
			for(int k=0;k<9;k++){
				if(array[i][k]!=0){
					if(!arr[array[i][k]-1])
						arr[array[i][k]-1]=true;
					else return false;
				}
			}
		}
		for(int i=0;i<9;i++){
			arr = new boolean[9];
			for(int k=0;k<9;k++){
				if(array[k][i]!=0){
					if(!arr[array[k][i]-1])
						arr[array[k][i]-1]=true;
					else return false;
				}
			}
		}
		for(int a=0;a<9;a+=3)
			for(int b=0;b<9;b+=3){
				arr = new boolean[9];
				for(int k=0;k<9;k++){
					if(array[a+(k/3)][b+(k%3)]!=0){
						if(!arr[array[a+(k/3)][b+(k%3)]-1])
							arr[array[a+(k/3)][b+(k%3)]-1]=true;
						else return false;
					}
				}
			}
		return true;
	}
	
	public void simplify(){
		while(fix());
		
	}
	
	public Grid getNext(){
		int count = ++nextCount;
		Grid c;
		
		byte[][] arr = new byte[9][9];
		boolean[][][] probs = new boolean[9][9][10];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				arr[i][j]=array[i][j];
				for(int k=0;k<10;k++)
					probs[i][j][k]=probables[i][j][k];
			}
		c = new Grid();
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				for(int k=1;k<10;k++){
					if(probs[i][j][k])
						count--;
					if(count==0){
						for(int a=0;a<(i*9)+j;a++)
							if(!probs[a/9][a%9][0]) return null;
						arr[i][j] = (byte)k;
						probs[i][j][0]=true;
						for(int l=1;l<10;l++)
							probs[i][j][l]=false;
						c.setArray(arr);
						c.setProbables();
						c.simplify();
						return c;
					}
				}
			}
		}
		return null;
	}
	
	public boolean isResult(){
		int i,j,k,a,b;
		boolean[] arr = new boolean[9];
		for(i=0;i<9;i++)
			for(j=0;j<9;j++)
				if(array[i][j]==0) return false;
		
		for(i=0;i<9;i++)
			for(k=0;k<9;k++)
				arr[array[i][k]-1]=true;
		for(k=0;k<9;k++)
			if(arr[k]) arr[k]=false ;
			else return false;
		for(j=0;j<9;j++)
			for(k=0;k<9;k++)
				arr[array[k][j]-1]=true;
		for(k=0;k<9;k++) 
			if(arr[k]) arr[k]=false ;
			else return false;
		for(a=0;a<9;a+=3)
			for(b=0;b<9;b+=9)
				for(k=0;k<9;k++)
					arr[array[a+(k/3)][b+(k%3)]-1]=true;
		for(k=0;k<9;k++)
			if(arr[k]) arr[k]=false ;
			else return false;
		return true;
	}			
	
	@Override
	public String toString(){
		String str="";
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(probables[i][j][0]) str+=array[i][j]+"   ";
				else{
					str+="(";
					for(int k=1;k<10;k++) 
						if(probables[i][j][k]) str+=k+"";
					str+=")   ";
				}
			}
			str+="\n";
		}
		//str+="Next count = "+nextCount+"\n";
		return str;
	}
	
	@Override
	protected Grid clone(){
	/*	byte[][] arr = new byte[9][9];
		boolean[][][] probs = new boolean[9][9][10];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				arr[i][j]=this.array[i][j];
				for(int k=0;k<10;k++)
					probs[i][j][k]=this.probables[i][j][k];
			}
		Grid g = new Grid();
		g.setArray(arr);
		g.setProbables(probs);
		System.gc();
		return g;*/
		return null;
	}
}
