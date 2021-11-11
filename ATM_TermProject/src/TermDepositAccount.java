
public class TermDepositAccount extends Account{ // 정기 예금 계좌
	private String accountPeriod;
	
	public TermDepositAccount(String Number, String Password, Long Balance, String UserName, String accountPeriod) {
		super(Number, Password, Balance, UserName);
		this.setAccountNumber(Number);
		this.setAccountPassword(Password);
		this.setAccountBalance(Balance);
		this.setAccountUserName(UserName);
		this.setAccountPeriod(accountPeriod);
	}
	
	// 정기 예금 계좌는 만기 날짜만을 가지고 있음
	public String getAccountPeriod() { 
		return accountPeriod;
	}
	
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
}
