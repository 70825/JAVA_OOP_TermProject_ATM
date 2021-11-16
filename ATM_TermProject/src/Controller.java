import java.util.stream.IntStream;

// ��Ʈ�ѷ� : VIEW�� MODEL�� �������ִ� ����
// VIEW�� MODEL�� �ڵ� ���ο��� ���������� ���� �������ָ� �ȉ�. �׷��� ��Ʈ�ѷ��� ����� ���������� ������
public class Controller {
	// Ŭ���� �ҷ�����
	private static DataBase database = new DataBase();
    private static ATM atm = new ATM();
    private static Interface GUI = new Interface();
    private static TransactionLog LOG = new TransactionLog();
    
    // ======== ��Ʈ�ѷ����� ����� ���� ==========
    // ��������
    public static int work = 0;
    
    // �α���
    private static boolean flag_id = false, flag_password = false;
    private static boolean flag_check_id = false, flag_check_password = false;
    private static String id = null, password = null;
    
    // �Ա�, ���, �۱�, �ܰ� ��ȸ ����
    public static boolean kind_account; // true = ����ݰ���, false = ���⿹�ݰ���
    private static int total_money;
    
    // �Ա�
    public static int[] putMoney = {0, 0, 0, 0}; // ���� ����(1000, 5000, 10000, 50000)
    
    // ���
    public static int[] outMoney = {0, 0}; // ���� ����(10000, 50000)
    private static boolean flag_atm, flag_account, flag_buffer;
    private static long[] errorValue1 = {0} ;
    private static long[] errorValue2 = {0, 0};
    
    // �۱�
    public static String remittance_id;
    private static boolean flag_remittance_account, flag_remittance_money;
    public static long remittance_money;
    
    // �ܰ� ��ȸ
    private static String checkName, checkPeriod;
    private static long checkMoney;
    
    // Ʈ����� �α�
    private static long beforeLogCash, afterLogCash;
    private static boolean accessLog;
    private static int accessNumber;
    
    
    // ====================== ���α׷� ���� ==============================
    
    public static boolean run() { // ���α׷� ����
		// � ������ ������ ������ (1: �Ա�, 2: ���: 3: �۱�, 4: �ܰ� ��ȸ 5: ����, 6: �ý��� ����)
    	
		if(work == 5) { // �ܼ� ���� -> �α��� ���� �ʱ�ȭ
			emptyLogin(); 
			return true;
		}
		if(work == 6) { // �ý��� ���� -> �α� ��� ���� -> ����
			showSystemCloseATM();
			return false;
		}
		
		// �̹� �α��εǾ� ������ �ǳʶ�
		if(id == null && password == null) login();
		
		// ���� ���� Ȯ��
		kind_account = database.kindAccount(id);
		
		// work ������ ���� ���� ����
		switch(work) {
		case 1: // �Ա�
			depositATM();
			break;
		case 2: // ���
			withdrawATM();
			break;
		case 3: // �۱�
			remittanceATM();
			break;
		case 4: // �ܰ� ��ȸ
			checkBalanceATM();
			break;
		}
		
		return true;
	}
    
    // ======================= ��Ʈ�ѷ� ���� �Լ� =======================
	
	public static void login() { // �α��� ��� - ���̵�� ��й�ȣ�� ���� �� ���� ������ Ż����
		while(!flag_id) {
			id = GUI.login_account(flag_check_id);
			flag_id = database.checkAccount(id);
			flag_check_id = true;
		}
		
		while(!flag_password) {
			password = GUI.login_password(flag_check_password);
			flag_password = database.checkPassword(id, password);
			flag_check_password = true;
		}
	}
	
	public static void emptyLogin() {
		flag_id = false;
		flag_password = false;
		flag_check_id = false;
		flag_check_password =false;
		id = null;
		password = null;
	}
	
	public static void depositATM() {
		// �ݾ��� �Է� ���� ������ ��� ���� ���� ����
		total_money = putMoney[0] * 1000 + putMoney[1] * 5000 + putMoney[2] * 10000 + putMoney[3] * 50000;
		
		// Database <-> Controller <-> Model, Controller -> Transaction Log
		// ���¿� ���� �߰���. �̶� �ʿ��� ������ ������ ��� Ʈ����� �α׿� �����
		Account nowAccount = database.getAccount(id);
		beforeLogCash = nowAccount.getAccountBalance();
		nowAccount.setAccountBalance(nowAccount.getAccountBalance() + total_money);
		afterLogCash = nowAccount.getAccountBalance();
		database.modifyAccount(id, nowAccount);
		
		LOG.putLog(id, 1, beforeLogCash, afterLogCash, "");
		
		// Controller -> Model
		// ATM�� ���� �߰���
		atm.setWon_1000(atm.getWon_1000() + putMoney[0]);
		atm.setWon_5000(atm.getWon_5000() + putMoney[1]);
		atm.setWon_10000(atm.getWon_10000() + putMoney[2]);
		atm.setWon_50000(atm.getWon_50000() + putMoney[3]);
		
		// ���� ȭ��
		GUI.closeShowDeposit();
		return;
	}
	
	public static void withdrawATM() {
		// �ݾ��� �Է� ���� ������ ��� ���� ���� ����
		total_money = outMoney[0] * 10000 + outMoney[1] * 50000;
		
		// Database <-> Model <-> Controller, Controller <-> TransactionLog
		// ���¿��� ���� ����ϰ�, Ʈ����� �α׿� ����� ������ ��� �������
		Account nowAccount = database.getAccount(id);
		beforeLogCash = nowAccount.getAccountBalance();
		nowAccount.setAccountBalance(nowAccount.getAccountBalance() - total_money);
		afterLogCash = nowAccount.getAccountBalance();
		database.modifyAccount(id, nowAccount);
		LOG.putLog(id, 2, beforeLogCash, afterLogCash, "");
		
		
		// Controller -> Model
		// ATM�� ���� �ٿ���
		atm.setWon_10000(atm.getWon_10000() - outMoney[0]);
		atm.setWon_50000(atm.getWon_50000() - outMoney[1]);
		
		GUI.closeShowWithdraw();
		
		return;
	}
	
	public static long getAccountBalance() {
		return database.getAccount(id).getAccountBalance();
	}
	
	public static long[] getATMNumbers() {
		long[] ans = {atm.getWon_10000(), atm.getWon_50000()};
		return ans;
	}
	public static boolean checkATMNumber() {
		if(outMoney[0] <= atm.getWon_10000() && outMoney[1] <= atm.getWon_50000()) return true;
		return false;
	}
	
	
	// �۱�
	public static boolean checkRemittanceAccount(String remittance_id) {
		return database.checkAccount(remittance_id);
	}
	
	public static boolean checkAccountBalance(long money) {
		return database.getAccount(id).getAccountBalance() >= money;
	}
	
	public static void remittanceATM() {
		
		// Database <-> Model <-> Controller, Controller -> Transaction Log
		// �۱��ϴ� ���´� ��� ��� ����, �۱ݹ޴� ���´� �Ա� ��� ����
		// �̿� ���ÿ� Ʈ����� �α� ��Ͽ� �ʿ��� ���� �����Ͽ� Ʈ����� �α׸� �����
		if(database.kindAccount(remittance_id)) {
			Account nowAccount = database.getAccount(id);
			Account remittanceAccount = database.getAccount(remittance_id);
			
			beforeLogCash = nowAccount.getAccountBalance();
			nowAccount.setAccountBalance(nowAccount.getAccountBalance() - remittance_money);
			remittanceAccount.setAccountBalance(remittanceAccount.getAccountBalance() + remittance_money);
			afterLogCash = nowAccount.getAccountBalance();
			
			database.modifyAccount(id, nowAccount);
			database.modifyAccount(remittance_id, remittanceAccount);
		}
		else {
			Account nowAccount = database.getAccount(id);
			TermDepositAccount remittanceAccount = database.getTermDepositAccount(remittance_id);
			
			beforeLogCash = nowAccount.getAccountBalance();
			nowAccount.setAccountBalance(nowAccount.getAccountBalance() - remittance_money);
			remittanceAccount.setAccountBalance(remittanceAccount.getAccountBalance() + remittance_money);
			afterLogCash = nowAccount.getAccountBalance();
			
			database.modifyAccount(id, nowAccount);
			database.modifyTermDepositAccount(remittance_id, remittanceAccount);
		}
		LOG.putLog(id , 3, beforeLogCash, afterLogCash, remittance_id);
		
		
		GUI.closeShowRemittance();
		
	}
	
	// �ܰ� ��ȸ
	public static void checkBalanceATM() {
		// Controller <-> View
		// �ܰ� ��ȸ JPanel�� ������
		Interface.showCheckBalance sCB = GUI.new showCheckBalance();
		
		// Database <-> Model <-> Controller <-> View
		// ��Ʈ�ѷ����� database�� ���� model�� ������ ������ ���� �信 ������ ������ ����
		if(kind_account) {
			Account acc = database.getAccount(id);
			checkName = acc.getAccountUserName();
			checkMoney = acc.getAccountBalance();
			sCB.showCheckBalance(checkName, id, kind_account, checkMoney, "");
		}
		else {
			TermDepositAccount tdacc = database.getTermDepositAccount(id);
			checkName = tdacc.getAccountUserName();
			checkMoney = tdacc.getAccountBalance();
			checkPeriod = tdacc.getAccountPeriod();
			sCB.showCheckBalance(checkName, id, kind_account, checkMoney, checkPeriod);
		}
		
		// Controller -> TransactionLog
		// �ܾ� ��ȸ�� ������ ���� �α׿� ������
		LOG.putLog(id, 0, 0, 0, "");
	}
	
	public static String getTransactionLog(int c) {
		return LOG.getLog(c);
	}
	
	public static void showSystemCloseATM() {
		// Controller <-> View
		// Ʈ����� �α� ��ɿ� ������ ������ Ȯ����, N�̸� ���� ������ ����
		
		GUI.systemCloseMessage();
		System.exit(0);
	}
}
