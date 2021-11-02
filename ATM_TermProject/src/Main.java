public class Main {
    private static DataBase db = new DataBase();
    private static ATM atm = new ATM();
    private static Interface GUI = new Interface();
    private static TransactionLog LOG = new TransactionLog();
    
    public static void main(String[] args) {
    	
    	boolean start = true;
    	while(true) {
    		int val = GUI.open(db, atm, LOG, start);
    		if (val < 5) start = false;
    		else if(val == 5) {
    			GUI.close(); // 단순히 종료만 누르면 손님이 올 때까지 기다림
    			start = true;
    		}
    		else{
    			GUI.showLog(LOG);
    			break;
    		}
    	}
    }
}