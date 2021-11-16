

public class Account { // ����� ����(���� ���� ���´� Account�� ��ӹ���)
	private String accountNumber; // ���¹�ȣ
	private String accountPassword; // ��й�ȣ
	private Long accountBalance; // �ܾ�
	private String accountUserName; // ����
	
	public Account(String Number, String Password, Long Balance, String UserName) {
		this.setAccountNumber(Number);
		this.setAccountPassword(Password);
		this.setAccountBalance(Balance);
		this.setAccountUserName(UserName);
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountPassword() {
		return this.accountPassword;
	}
	
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	
	public Long getAccountBalance() {
		return this.accountBalance;
	}
	
	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getAccountUserName() {
		return this.accountUserName;
	}
	
	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}
	
	
}
