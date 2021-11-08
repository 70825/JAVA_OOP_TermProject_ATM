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
    private int work;
    
    // �α���
    private boolean flag_id = false, flag_password = false;
    private boolean flag_check_id = false, flag_check_password = false;
    private String id = null, password = null;
    
    // �Ա�, ���, �۱�, �ܰ� ��ȸ ����
    private boolean kind_account; // true = ����ݰ���, false = ���⿹�ݰ���
    private int total_money;
    
    // �Ա�
    private int[] putMoney = {0, 0, 0, 0}; // ���� ����(1000, 5000, 10000, 50000)
    
    // ���
    private int[] outMoney = {0, 0}; // ���� ����(10000, 50000)
    private boolean flag_atm, flag_account, flag_buffer;
    private long[] errorValue1 = {0} ;
    private long[] errorValue2 = {0, 0};
    
    // �۱�
    private String remittance_id;
    private boolean flag_remittance_account, flag_remittance_money;
    private long remittance_money;
    
    // �ܰ� ��ȸ
    private String checkName, checkPeriod;
    private long checkMoney;
    
    // Ʈ����� �α�
    private long beforeLogCash, afterLogCash;
    private boolean accessLog;
    private int accessNumber;
    
    
    // ====================== ���α׷� ���� ==============================
    
    public boolean run() { // ���α׷� ����
		// � ������ ������ ������ (1: �Ա�, 2: ���: 3: �۱�, 4: �ܰ� ��ȸ 5: ����, 6: �ý��� ����)
		this.work = GUI.open();
		
		if(this.work == 5) { // �ܼ� ���� -> �α��� ���� �ʱ�ȭ
			this.emptyLogin(); 
			return true;
		}
		if(this.work == 6) { // �ý��� ���� -> �α� ��� ���� -> ����
			this.showLogATM();
			return false;
		}
		
		// �̹� �α��εǾ� ������ �ǳʶ�
		if(this.id == null && this.password == null) this.login();
		
		// ���� ���� Ȯ��
		this.kind_account = database.kindAccount(id);
		
		// work ������ ���� ���� ����
		switch(this.work) {
		case 1: // �Ա�
			this.depositATM();
			break;
		case 2: // ���
			this.withdrawATM();
			break;
		case 3: // �۱�
			this.remittanceATM();
			break;
		case 4: // �ܰ� ��ȸ
			this.checkBalanceATM();
			break;
		}
		
		return true;
	}
    
    // ======================= ��Ʈ�ѷ� ���� �Լ� =======================
	
	private void login() { // �α��� ��� - ���̵�� ��й�ȣ�� ���� �� ���� ������ Ż����
		while(!this.flag_id) {
			this.id = GUI.login_account(this.flag_check_id);
			this.flag_id = database.checkAccount(this.id);
			this.flag_check_id = true;
		}
		
		while(!this.flag_password) {
			this.password = GUI.login_password(this.flag_check_password);
			this.flag_password = database.checkPassword(this.id, this.password);
			this.flag_check_password = true;
		}
	}
	
	private void emptyLogin() {
		this.flag_id = false;
		this.flag_password = false;
		this.flag_check_id = false;
		this.flag_check_password =false;
		this.id = null;
		this.password = null;
		
		GUI.close();
	}
	
	private void depositATM() {
		// Controller <-> View
		// �ݾ��� �Է� �ް�, ��� ���� ���� ����
		this.putMoney = GUI.inputShowDeposit();
		this.total_money = this.putMoney[0] * 1000 + this.putMoney[1] * 5000 + this.putMoney[2] * 10000 + this.putMoney[3] * 50000;
		
		// Controller <-> Model, Controller -> Transaction Log
		// ���¿� ���� �߰���. �̶� �ʿ��� ������ ������ ��� Ʈ����� �α׿� �����
		this.beforeLogCash = database.getBalance(this.id);
		this.database.setBalance(this.id, database.getBalance(this.id) + this.total_money);
		this.afterLogCash = database.getBalance(this.id);
		LOG.putLog(this.id, this.work, beforeLogCash, afterLogCash, "");
		
		// Controller -> Model
		// ATM�� ���� �߰���
		atm.setWon_1000(atm.getWon_1000() + this.putMoney[0]);
		atm.setWon_5000(atm.getWon_5000() + this.putMoney[1]);
		atm.setWon_10000(atm.getWon_10000() + this.putMoney[2]);
		atm.setWon_50000(atm.getWon_50000() + this.putMoney[3]);
		
		
		GUI.closeShowDeposit();
		
		return;
	}
	
	private void withdrawATM() {
		if(!this.kind_account) { // ���� ���� ���°� �� �� ���� �ൿ
			GUI.inaccessible();
			return;
		}
		
		GUI.openShowWithdraw();
		
		// Controller <-> View
		// ����� ���� �Է� ����
		this.outMoney = GUI.inputShowWithDraw(true);
		this.total_money = this.outMoney[0] * 10000 + this.outMoney[1] * 50000;
		
		
		// Model <-> Controller <-> View
		// ATM�� �ش� ���� ���� �ִ���, ���¿� ���� ���� �ִ��� Ȯ��, �ϳ��� ���� �����ϸ� �����޼����� ����ϰ� �ٽ� �Է� ����
		this.flag_atm = false;
		this.flag_account = false;
		
		while(!this.flag_atm || !this.flag_account) {
			if(atm.getWon_10000() >= this.outMoney[0] && atm.getWon_50000() >= this.outMoney[1]) {
				this.flag_atm = true;
				if(database.getBalance(this.id) >= this.total_money) {
					this.flag_account = true;
				}
				else {
					this.errorValue1[0] = database.getBalance(this.id);
					GUI.errorShowWithDraw(1, errorValue1);
				}
			}
			else {
				this.errorValue2[0] = atm.getWon_10000();
				this.errorValue2[1] = atm.getWon_50000();
				GUI.errorShowWithDraw(2, errorValue2);
			}
			if(!this.flag_atm || !this.flag_account) {
				this.outMoney = GUI.inputShowWithDraw(false);
			}
		}
		
		
		// Model <-> Controller, Controller <-> TransactionLog
		// ���¿��� ���� ����ϰ�, Ʈ����� �α׿� ����� ������ ��� �������
		this.beforeLogCash = database.getBalance(this.id);
		database.setBalance(this.id, database.getBalance(this.id) - this.total_money);
		this.afterLogCash = database.getBalance(this.id);
		LOG.putLog(this.id, this.work, this.beforeLogCash, this.afterLogCash, "");
		
		
		// Controller -> Model
		// ATM�� ���� �ٿ���
		atm.setWon_10000(atm.getWon_10000() - this.outMoney[0]);
		atm.setWon_10000(atm.getWon_50000() - this.outMoney[1]);
		
		
		GUI.closeShowWithDraw();
		
		return;
	}
	
	private void remittanceATM() {
		if(!this.kind_account) { // ���� ���� ���°� �� �� ���� �ൿ
			GUI.inaccessible();
			return;
		}
		
		GUI.openShowRemittance();
		
		// Model <-> Controller <-> View
		// �۱��� ���°� �����ϴ���, �۱��� ���� �ִ��� Ȯ���ϰ�, �ϳ��� �����ϸ� �����޼��� ����ϸ鼭 �ٽ� �Է� ����
		this.flag_remittance_account = false;
		while(!this.flag_remittance_account) {
			this.remittance_id = GUI.inputAccountShowRemittance(this.flag_remittance_account);
			this.flag_remittance_account = database.checkAccount(this.remittance_id);
		}
		
		this.flag_remittance_money = false;
		while(!this.flag_remittance_money) {
			this.remittance_money = GUI.inputMoneyShowRemittance(this.flag_remittance_money);
			if(database.getBalance(this.id) >= this.remittance_money) {
				this.flag_remittance_money = true;
			}
		}
		
		// Model <-> Controller, Controller -> Transaction Log
		// �۱��ϴ� ���´� ��� ��� ����, �۱ݹ޴� ���´� �Ա� ��� ����
		// �̿� ���ÿ� Ʈ����� �α� ��Ͽ� �ʿ��� ���� �����Ͽ� Ʈ����� �α׸� �����
		this.beforeLogCash = database.getBalance(this.id);
		database.setBalance(this.id, database.getBalance(this.id) - this.remittance_money);
		database.setBalance(this.remittance_id, database.getBalance(this.remittance_id) + this.remittance_money);
		this.afterLogCash = database.getBalance(this.id);
		LOG.putLog(this.id , this.work, this.beforeLogCash, this.afterLogCash, this.remittance_id);
		
		
		GUI.closeShowRemittance();
		
	}
	
	
	private void checkBalanceATM() {
		GUI.openShowCheckBalance();
		
		// Model <-> Controller
		// �ʿ��� ������ ������ ��
		this.checkName = database.getName(this.id);
		this.checkMoney = database.getBalance(this.id);
		this.checkPeriod = database.getPeriod(this.id);
		
		// Controller -> View
		// ���� ������ �����
		GUI.showCheckBalance(this.checkName, this.id, this.kind_account, this.checkMoney, this.checkPeriod);
		
		LOG.putLog(this.id, this.work, 0, 0, "");
		
		GUI.closeShowCheckBalance();
	}
	
	private void showLogATM() {
		// Controller <-> View
		// Ʈ����� �α� ��ɿ� ������ ������ Ȯ����, N�̸� ���� ������ ����
		this.accessLog = GUI.openShowLog().equals("Y")? true : false;
		if(!this.accessLog) {
			GUI.systemClose();
			return;
		}
		
		// TransactionLog <-> Controller <-> View
		// ã�����ϴ� ��� ��ȣ�� ���� Ʈ����� �α� ����� �ҷ��ͼ� ������
		this.accessNumber = -1;
		while(true) {
			this.accessNumber = GUI.showSelectLog();
			
			if(this.accessNumber == 0) break;
			
			GUI.showLog(this.accessNumber, LOG.getLog(this.accessNumber));
		}
		
		GUI.systemClose();
	}
}
