public class Main {
    private static DataBase db = new DataBase();
    private static ATM atm = new ATM(db);
    private static Interface GUI = new Interface();
   
    public static void main(String[] args) {
    	while(true) {
    		int val = GUI.open(db, atm);
    		if(val == 5) GUI.close(); // 단순히 종료만 누르면 
    		if(val == 6) {
    			break;
    		}
    	}
    }
}