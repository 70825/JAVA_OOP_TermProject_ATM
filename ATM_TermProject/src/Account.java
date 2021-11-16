
public class Account { // 입출금 계좌(정기 예금 계좌는 Account를 상속받음) 
	private String accountNumber; // 계좌번호
	private String accountPassword; // 비밀번호
	private Long accountBalance; // 잔액
	private String accountUserName; // 고객명
	
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
