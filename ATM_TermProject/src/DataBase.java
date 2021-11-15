import java.util.HashMap;
import java.util.Map;

public class DataBase {
	
	private Map<String, Account> accountDB = new HashMap<>(); // �Ϲ� ����� ���� DB
	private Map<String, TermDepositAccount> termDepositAccountDB = new HashMap<>(); // ���� ���� ���� DB
	
	public DataBase() {
		Account acc1 = new Account("000000-000000", "0000", (long)5000000, "�̽���");
		accountDB.put("000000-000000", acc1);
		
		Account acc2 = new Account("111111-111111", "1111", (long)20000, "������");
		accountDB.put("111111-111111", acc2);
		
		Account acc3 = new Account("222222-222222", "2222", (long)30000, "���ٺ�");
		accountDB.put("222222-222222", acc3);
		
		TermDepositAccount tdAcc1 = new TermDepositAccount("123456-123456", "1234", (long)8156000, "�̽���", "2022��3��12��");
		termDepositAccountDB.put("123456-123456", tdAcc1);
		
		TermDepositAccount tdAcc2 = new TermDepositAccount("999999-999999", "9876", (long)314000, "������", "2021��12��5��");
		termDepositAccountDB.put("999999-999999", tdAcc2);
		
		TermDepositAccount tdAcc3 = new TermDepositAccount("101010-196662", "3456", (long)50000, "���ٺ�", "2022��7��9��");
		termDepositAccountDB.put("101010-196662", tdAcc3);
	}
	
	// ����� ��������, ���⿹�� �������� �����ϴ� Ŭ���� - ������ ���� �� ���Ƽ� �߰�
	public boolean kindAccount(String accountNumber) { // true : ����� ����
		if(this.accountDB.containsKey(accountNumber)) return true;
		return false;
	}
	
	// �����ϴ� ���¹�ȣ���� Ȯ��
	public boolean checkAccount(String accountNumber) {
		if(this.accountDB.containsKey(accountNumber)) {
			return true;
		}
		else if(this.termDepositAccountDB.containsKey(accountNumber)) {
			return true;
		}
		return false;
	}
	
	// ���¹�ȣ�� �����ϴ� ���� Ȯ�������� ��й�ȣ�� �´��� Ȯ��
	public boolean checkPassword(String accountNumber, String accountPassword) {
		if(this.kindAccount(accountNumber) && accountPassword.equals(this.accountDB.get(accountNumber).getAccountPassword())) {
			return true;
		}
		else if(!this.kindAccount(accountNumber) && accountPassword != null && accountPassword.equals(this.termDepositAccountDB.get(accountNumber).getAccountPassword())){
			return true;
		}
		return false;
	}
	
	// ����� ���� ��������
	public Account getAccount(String accountNumber) {
		return this.accountDB.get(accountNumber);
	}
	
	// ���� ���� ���� ��������
	public TermDepositAccount getTermDepositAccount(String accountNumber) {
		return this.termDepositAccountDB.get(accountNumber);
	}
	
	// ����� ���� ����
	public void modifyAccount(String accountNumber, Account newAccount) {
		this.accountDB.put(accountNumber, newAccount);
	}
	
	// ���� ���� ���� ����
	public void modifyTermDepositAccount(String accountNumber, TermDepositAccount newTermDepsoitAccount) {
		this.termDepositAccountDB.put(accountNumber, newTermDepsoitAccount);
	}
	
	// �ܾ� ��������
	public long getBalance(String accountNumber) {
		if(this.kindAccount(accountNumber)) {
			return this.accountDB.get(accountNumber).getAccountBalance();
		}
		return this.termDepositAccountDB.get(accountNumber).getAccountBalance();
	}
	
	// �ܾ� �缳��
	public void setBalance(String accountNumber, long newBalance) {
		if(this.kindAccount(accountNumber)) {
			this.accountDB.get(accountNumber).setAccountBalance(newBalance);
		}
		else {
			this.termDepositAccountDB.get(accountNumber).setAccountBalance(newBalance);
		}
	}
	
	// �� �̸� ��������
	public String getName(String accountNumber) {
		if(this.kindAccount(accountNumber)) {
			return this.accountDB.get(accountNumber).getAccountUserName();
		}
		return this.termDepositAccountDB.get(accountNumber).getAccountUserName();
	}
	
	// ���� ��¥ ��������
	public String getPeriod(String accountNumber) {
		if(this.kindAccount(accountNumber)) return "";
		return this.termDepositAccountDB.get(accountNumber).getAccountPeriod();
	}
}