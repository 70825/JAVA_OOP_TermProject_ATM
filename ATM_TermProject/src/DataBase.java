import java.util.HashMap;
import java.util.Map;

public class DataBase {
	
	private Map<String, Account> accountDB = new HashMap<>(); // 일반 입출금 계좌 DB
	private Map<String, TermDepositAccount> termDepositAccountDB = new HashMap<>(); // 정기 예금 계좌 DB
	
	public DataBase() {
		Account acc1 = new Account("000000-000000", "0000", (long)5000000, "이신혁");
		accountDB.put("000000-000000", acc1);
		
		Account acc2 = new Account("111111-111111", "1111", (long)20000, "이지원");
		accountDB.put("111111-111111", acc2);
		
		Account acc3 = new Account("222222-222222", "2222", (long)30000, "정다빈");
		accountDB.put("222222-222222", acc3);
		
		TermDepositAccount tdAcc1 = new TermDepositAccount("123456-123456", "1234", (long)8156000, "이신혁", "2022년3월12일");
		termDepositAccountDB.put("333333-333333", tdAcc1);
		
		TermDepositAccount tdAcc2 = new TermDepositAccount("999999-999999", "9876", (long)314000, "이지원", "2021월12월5일");
		termDepositAccountDB.put("444444-444444", tdAcc2);
		
		TermDepositAccount tdAcc3 = new TermDepositAccount("101010-196662", "3456", (long)50000, "정다빈", "2022년7월9일");
		termDepositAccountDB.put("101010-196662", tdAcc3);
	}
	
	// 입출금 계좌인지, 정기예금 계좌인지 구별하는 클래스 - 재사용이 많을 것 같아서 추가
	public boolean kindAccount(String accountNumber) { // true : 입출금 계좌
		if(this.accountDB.containsKey(accountNumber)) return true;
		return false;
	}
	
	// 존재하는 계좌번호인지 확인
	public boolean checkAccount(String accountNumber) {
		if(this.accountDB.containsKey(accountNumber)) {
			return true;
		}
		else if(this.termDepositAccountDB.containsKey(accountNumber)) {
			return true;
		}
		return false;
	}
	
	// 계좌번호가 존재하는 것을 확인했으니 비밀번호가 맞는지 확인
	public boolean checkPassword(String accountNumber, String accountPassword) {
		if(this.kindAccount(accountNumber) && accountPassword.equals(this.accountDB.get(accountNumber).getAccountPassword())) {
			return true;
		}
		else if(!this.kindAccount(accountNumber) && accountPassword.equals(this.termDepositAccountDB.get(accountNumber).getAccountPassword())){
			return true;
		}
		return false;
	}
	
	// 잔액 가져오기
	public long getBalance(String accountNumber) {
		if(this.kindAccount(accountNumber)) {
			return this.accountDB.get(accountNumber).getAccountBalance();
		}
		return this.termDepositAccountDB.get(accountNumber).getAccountBalance();
	}
	
	// 잔액 재설정
	public void setBalance(String accountNumber, long newBalance) {
		if(this.kindAccount(accountNumber)) {
			this.accountDB.get(accountNumber).setAccountBalance(newBalance);
		}
		else {
			this.termDepositAccountDB.get(accountNumber).setAccountBalance(newBalance);
		}
	}
	
	// 고객 이름 가져오기
	public String getName(String accountNumber) {
		if(this.kindAccount(accountNumber)) {
			return this.accountDB.get(accountNumber).getAccountUserName();
		}
		return this.termDepositAccountDB.get(accountNumber).getAccountUserName();
	}
	
	// 만기 날짜 가져오기
	public String getPeriod(String accountNumber) {
		return this.termDepositAccountDB.get(accountNumber).getAccountPeriod();
	}
}