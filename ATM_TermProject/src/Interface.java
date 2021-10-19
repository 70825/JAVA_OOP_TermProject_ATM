import java.util.*;

public class Interface {
    private String account, password;
	private Scanner sc = new Scanner(System.in);
	
	public void Display(DataBase db, ATM atm, String account, int flag) {
		this.account = account;
		
		if(flag == 1) { // 입금
			// temp_cash -> ex) 천원 1장, 오천원 2장, 만원 3장, 오만원 4장이면
			// 입력은 '1 2 3 4'로 함
			System.out.println("******************************************");
			System.out.println("               입금을 선택하였습니다.            ");
			System.out.printf("입금할 지폐 4개를 입력하세요.(천원, 오천원, 만원, 오만원) : ");
			StringTokenizer st = new StringTokenizer(sc.next(), " ");
				
			int won_1000 = Integer.parseInt(st.nextToken());
			int won_5000 = Integer.parseInt(st.nextToken());
			int won_10000 = Integer.parseInt(st.nextToken());
			int won_50000 = Integer.parseInt(st.nextToken());
			
			atm.WithDraw(db, this.account, won_50000 * 50000 + won_10000 * 10000);
			atm.setWon_1000(atm.getWon_1000() + won_1000);
			atm.setWon_5000(atm.getWon_5000() + won_5000);
			atm.setWon_10000(atm.getWon_10000() + won_10000);
			atm.setWon_50000(atm.getWon_50000() + won_50000);
			
			System.out.println("      입금이 완료되었습니다. 이용해주셔서 감사합니다.   ");
			System.out.println("******************************************");
		}
		else if(flag == 2) { // 출금
			System.out.println("******************************************");
			System.out.println("               출금을 선택하였습니다.            ");
			System.out.printf("출금할 지폐 2개를 입력하세요.(만원, 오만원) : ");
			StringTokenizer st = new StringTokenizer(sc.next(), " ");
			
			int won_10000 = Integer.parseInt(st.nextToken());
			int won_50000 = Integer.parseInt(st.nextToken());
			
			boolean flag1 = false;
			boolean flag2 = false;
			
			while(flag1 && flag2) {
				if(atm.getWon_10000() >= won_10000 && atm.getWon_50000() >= won_50000) {
					flag1 = true;
					if(atm.getAccountBalance(db, this.account) >= won_10000 * 10000 + won_50000 * 50000) {
						flag2 = true;
					}
					else {
						System.out.printf("계좌에 있는 잔액이 부족합니다. 현재 계좌에 있는 금액은 %d원 입니다.\n", db.getBalance(this.account));
					}
				}
				else {
					System.out.printf("atm에 있는 지폐가 부족합니다. 현재 atm에 만원은 %d개 존재하고, 오만원은 %d개 존재합니다.\n", atm.getWon_10000(), atm.getWon_50000());
				}
			}
			
			atm.Deposit(db, this.account, won_50000 * 50000 + won_10000 * 10000);
			atm.setWon_10000(atm.getWon_10000() - won_10000);
			atm.setWon_10000(atm.getWon_50000() - won_50000);
			
			System.out.println("    출금이 완료되었습니다. 이용해주셔서 감사합니다.     ");
			System.out.println("******************************************");
		}
		else if(flag == 3) { // 송금
			System.out.println("******************************************");
			System.out.println("               송금을 선택하였습니다.            ");
			System.out.printf("송금을 받을 계좌번호를 입력하세요. : ");
			String Remittance_Account = sc.next();
			
			boolean findAccount = atm.checkAccountId(db, Remittance_Account);
			while(!findAccount) {
				System.out.println("송급 받을 계좌번호가 존재하지 않습니다. 다시 입력해주세요. : ");
				Remittance_Account = sc.next();
				findAccount = atm.checkAccountId(db, Remittance_Account);
			}
			
			System.out.printf("송금할 금액을 입력해주세요. : ");
			int money = sc.nextInt();
			while (money <= atm.getAccountBalance(db, this.account)) {
				System.out.printf("계좌내에 있는 금액이 부족합니다. 다시 입력해주세요. : ");
				money = sc.nextInt();
			}
			
			atm.Remittance(db, this.account, Remittance_Account, money);
			System.out.println("송금이 완료되었습니다.");
			System.out.println("            이용해주셔서 감사합니다.             ");
			System.out.println("******************************************");
		}
		else{ // 잔액조회
			System.out.println("******************************************");
			System.out.println("            잔액 조회를 선택하였습니다.           ");
			System.out.printf("이름: %s\n", atm.getAccountName(db, this.account));
			System.out.printf("계좌 번호: %s\n", this.account);
			System.out.printf("통장 종류 : %s\n", atm.getAccountKind(db, this.account));
			System.out.printf("잔액 : %d\n", atm.getAccountBalance(db, this.account));
			if(atm.getAccountKind(db, this.account).equals("정기 예금 통장")){
				System.out.printf("예금 만기 날짜 : %s\n", atm.getAccountPeriod(db, this.account));
			}
			System.out.println("            이용해주셔서 감사합니다.             ");
			System.out.println("******************************************");
		}
	}
   
	public boolean login_account(DataBase db, ATM atm, boolean flag) { // 계좌번호 입력
		
		if(flag) System.out.print("로그인할 계좌 번호를 입력해주세요. : ");
		else System.out.print("존재하지 않는 계좌번호입니다. 다시 입력해주세요. : ");
		
		this.account = sc.next();
		
		return atm.checkAccountId(db, this.account);
	}
	
	public boolean login_password(DataBase db, ATM atm, boolean flag) { // 비밀번호 입력
		if(flag) System.out.print("비밀번호를 입력주세요. : ");
		else System.out.print("비밀번호가 틀렸습니다. 다시 입력해주세요. : ");
		
		this.password = sc.next();
		return atm.checkAccountPassword(db, this.account, this.password);
	}
	
	public int open(DataBase db, ATM atm) {
		System.out.println("******************************************");
		System.out.println("           은행을 찾아주셔서 감사합니다.          ");
		System.out.println("******************************************");
		System.out.println("1: 입금, 2: 출금: 3: 송금, 4: 잔고 조회 5: 종료, 6: 시스템 종료");
		System.out.printf("하실 업무를 선택해주세요. : ");
		int val = sc.nextInt();
		
		if(val == 5 || val == 6) return val;
		
		boolean flag = this.login_account(db, atm, true);
		while(flag) {
			flag = this.login_account(db, atm, false);
		}
		
		flag = this.login_password(db, atm, true);
		while(flag) {
			flag = this.login_password(db, atm, false);
		}
		
		this.Display(db, atm, this.account, val);
		
		return val;
	}
	
	public void close() { // 완료
		System.out.println("******************************************");
		System.out.println("          은행을 이용해주셔서 감사합니다.          ");
		System.out.printf("******************************************\n\n\n\n");
	}
}