public class Main {
    private static DataBase db = new DataBase();
    private static ATM atm = new ATM(db);
    private static Interface GUI = new Interface();
   
    public static void main(String[] args) {
    	while(true) {
    		int val = GUI.open(db, atm);
    		if(val == 5) GUI.close(); // �ܼ��� ���Ḹ ������ 
    		if(val == 6) {
    			break;
    		}
    	}
    }
}