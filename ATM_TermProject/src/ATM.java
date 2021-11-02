public class ATM {
	   
	// °¢ ÁöÆó ¼ö
	private int won_1000, won_5000, won_10000, won_50000;
   
	public ATM() {
		this.won_1000 = 0;
		this.won_50000 = 0;
		this.won_10000 = 1000;
		this.won_50000 = 200;
	}
	
	public int getWon_1000() {
		return won_1000;
	}

	public void setWon_1000(int won_1000) {
		this.won_1000 = won_1000;
	}

	public int getWon_5000() {
		return won_5000;
	}

	public void setWon_5000(int won_5000) {
		this.won_5000 = won_5000;
	}

	public int getWon_10000() {
		return won_10000;
	}

	public void setWon_10000(int won_10000) {
		this.won_10000 = won_10000;
	}

	public int getWon_50000() {
		return won_50000;
	}

	public void setWon_50000(int won_50000) {
		this.won_50000 = won_50000;
	}
	
	public void Deposit(DataBase db, String account, long cash) {
		db.setBalance(account, db.getBalance(account) + cash);
	}
	
	public void WithDraw(DataBase db, String account, long cash) {
		db.setBalance(account, db.getBalance(account) - cash);
	}
	
	public void Remittance(DataBase db, String from_account, String to_account, Long money) {
		this.Deposit(db, from_account, money);
		this.WithDraw(db, to_account, money);
	}
}