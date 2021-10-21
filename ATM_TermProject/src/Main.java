public class Main {
    private static DataBase db = new DataBase();
    private static ATM atm = new ATM();
    private static Interface GUI = new Interface();
    
    public static void main(String[] args) {
    	
    	boolean start = true;
    	while(true) {
    		int val = GUI.open(db, atm, start);
    		if (val < 5) start = false;
    		else if(val == 5) {
    			GUI.close(); // �ܼ��� ���Ḹ ������ �մ��� �� ������ ��ٸ�
    			start = true;
    		}
    		else{
    			GUI.showLog(db);
    			break;
    		}
    	}
    }
}