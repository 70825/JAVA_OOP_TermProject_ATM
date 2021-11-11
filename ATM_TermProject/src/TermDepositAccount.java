
public class TermDepositAccount extends Account{ // ���� ���� ����
	private String accountPeriod;
	
	public TermDepositAccount(String Number, String Password, Long Balance, String UserName, String accountPeriod) {
		super(Number, Password, Balance, UserName);
		this.setAccountNumber(Number);
		this.setAccountPassword(Password);
		this.setAccountBalance(Balance);
		this.setAccountUserName(UserName);
		this.setAccountPeriod(accountPeriod);
	}
	
	// ���� ���� ���´� ���� ��¥���� ������ ����
	public String getAccountPeriod() { 
		return accountPeriod;
	}
	
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
}
