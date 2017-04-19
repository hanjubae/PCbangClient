package _client_dbDAO;

public class Loop {
	private int num[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
	private int resNum[] = new int[3];
	private int userNum[] = new int[3];
	
	
	
	public int[] getResNum() {
		return resNum;
	}

	

	public String loop(int n1, int n2, int n3){
		userNum[0] = n1;
		userNum[1] = n2;
		userNum[2] = n3;
		
		int strike, ball;
		strike = ball = 0;
		
		for (int i = 0; i < userNum.length; i++) {
			for(int j=0; j<resNum.length; j++){
				if(userNum[i] == resNum[j]){
					if(i==j){
						strike ++;
					}else{
						ball ++;
					}
				}
			}
		}
		if(strike == userNum.length){
			return "성공!!";
		}else{
			return "strike : " + strike + "ball : " + ball;
		}
	}
	
	public void shuffle(){
		for(int i=0; i<30; ++i){
			int r1 = (int)(Math.random()*10);
			int r2 = (int)(Math.random()*10);
			
			swap(r1, r2);
			
		}
		resNum[0] = num[0];
		resNum[1] = num[1];
		resNum[2] = num[2];
	}
	
	private void swap(int r1, int r2){
		int temp;
		
		temp = num[r1];
		num[r1] = num[r2];
		num[r2] = temp;
	}
}
