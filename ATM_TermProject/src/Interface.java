import java.util.*;

public class Interface {
    private String accountNumber, password;
	private Scanner sc = new Scanner(System.in);
	
	public void inaccessible() {
		System.out.println("******************************************");
		System.out.println("     정기예금 통장은 입금과 잔액조회만 가능합니다.      ");
		System.out.printf("******************************************\n\n\n\n");
		return;
	}
	
	// ==================== 입금 ======================
	
	public int[] inputShowDeposit() {
		// 천원 1장, 오천원 2장, 만원 3장, 오만원 4장이면 입력은 '1 2 3 4'로 함
		System.out.println("******************************************");
		System.out.println("               입금을 선택하였습니다.            ");
		System.out.printf("입금할 지폐 4개를 입력하세요.(천원, 오천원, 만원, 오만원) : ");
		
		sc.nextLine(); // 버퍼 비우기
		StringTokenizer st = new StringTokenizer(sc.nextLine());
		
		int won_1000 = Integer.parseInt(st.nextToken());
		int won_5000 = Integer.parseInt(st.nextToken());
		int won_10000 = Integer.parseInt(st.nextToken());
		int won_50000 = Integer.parseInt(st.nextToken());
		
		int[] output = {won_1000, won_5000, won_10000, won_50000};
		
		return output;
	}
	
	public void closeShowDeposit() {
		System.out.println("      입금이 완료되었습니다. 이용해주셔서 감사합니다.   ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ==================== 출금 ======================
	
	public void openShowWithdraw() {
		System.out.println("******************************************");
		System.out.println("               출금을 선택하였습니다.            ");
		return;
	}
	
	public int[] inputShowWithDraw(boolean flag) {
		System.out.printf("출금할 지폐 2개를 입력하세요.(만원, 오만원) : ");
		if(flag) sc.nextLine(); // 버퍼 비우기
		StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
		
		int won_10000 = Integer.parseInt(st.nextToken());
		int won_50000 = Integer.parseInt(st.nextToken());
		
		int[] output = {won_10000, won_50000};
		
		return output;
	}
	
	public void errorShowWithDraw(int select, long[] values) {
		if(select == 1) {
			System.out.printf("계좌에 있는 잔액이 부족합니다. 현재 계좌에 있는 금액은 %d원 입니다.\n", values[0]);
		}
		else{
			System.out.printf("atm에 있는 지폐가 부족합니다. 현재 atm에 만원은 %d개 존재하고, 오만원은 %d개 존재합니다.\n", values[0], values[1]);
		}
	}
	
	public void closeShowWithDraw() {
		System.out.println("    출금이 완료되었습니다. 이용해주셔서 감사합니다.     ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ==================== 송금 ======================
	
	public void openShowRemittance() {
		System.out.println("******************************************");
		System.out.println("               송금을 선택하였습니다.            ");
	}
	
	public String inputAccountShowRemittance(boolean flag) {
		if(!flag) System.out.printf("송금을 받을 계좌번호를 입력하세요. : ");
		else System.out.printf("송급 받을 계좌번호가 존재하지 않습니다. 다시 입력해주세요. : ");
		String Remittance_Account = sc.next();
		
		return Remittance_Account;
	}
	
	public long inputMoneyShowRemittance(boolean flag) {
		if(!flag) System.out.printf("송금할 금액을 입력해주세요. : ");
		else System.out.printf("계좌내에 있는 금액이 부족합니다. 다시 입력해주세요. : ");
		long money = sc.nextInt();
		
		return money;
	}
	
	public void closeShowRemittance() {
		System.out.println("송금이 완료되었습니다.");
		System.out.println("            이용해주셔서 감사합니다.             ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	// ==================== 잔고 조회 ======================
	public void openShowCheckBalance() {
		System.out.println("******************************************");
		System.out.println("            잔액 조회를 선택하였습니다.           ");
	}
	
	public void showCheckBalance(String name, String id, boolean flag_kind, long balance, String period) {
		
		System.out.printf("이름: %s\n", name);
		System.out.printf("계좌 번호: %s\n", id);
		System.out.printf("통장 종류 : %s\n", flag_kind? "입출금 통장" : "정기 예금 통장");
		System.out.printf("잔액 : %d\n", balance);
		if(!flag_kind){
			System.out.printf("예금 만기 날짜 : %s\n", period);
		}
		
	}
	
	public void closeShowCheckBalance() {
		System.out.println("            이용해주셔서 감사합니다.             ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ================= 로그인 ======================
   
	public String login_account(boolean flag) { // 계좌번호 입력
		if(!flag) System.out.print("로그인할 계좌 번호를 입력해주세요. : ");
		else System.out.print("존재하지 않는 계좌번호입니다. 다시 입력해주세요. : ");
		
		this.accountNumber = sc.next();
		
		return this.accountNumber;
	}
	
	public String login_password(boolean flag) { // 비밀번호 입력
		if(!flag) System.out.print("비밀번호를 입력해주세요. : ");
		else System.out.print("비밀번호가 틀렸습니다. 다시 입력해주세요. : ");
		
		this.password = sc.next();
		
		return this.password;
	}
	
	// ======================== 시작 및 종료 ===========================
	
	public int open() {
		System.out.println("******************************************");
		System.out.println("           은행을 찾아주셔서 감사합니다.          ");
		System.out.println("******************************************");
		System.out.println("1: 입금, 2: 출금: 3: 송금, 4: 잔고 조회 5: 종료, 6: 시스템 종료");
		System.out.printf("하실 업무를 선택해주세요. : ");
		int val = sc.nextInt();
		
		return val;
	}
	
	public void close() { // 완료
		System.out.println("******************************************");
		System.out.println("          은행을 이용해주셔서 감사합니다.          ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	// ===================== 트랜잭션 로그 접근 및 시스템 종료 ==============================
	
	public String openShowLog() {
		System.out.printf("\n트랜잭션 로그 접근(Y/N) : ");
		String ans = sc.next();
		
		return ans;
	}
	
	public int showSelectLog() {
		System.out.printf("접근할 로그 번호를 입력해주세요.(0: 종료) : ");
		int val = sc.nextInt();
		
		return val;
	}
	
	public void showLog(int val, String Log) {
		System.out.println("로그 번호 " + Integer.toString(val) + ": " + Log);
	}
	
	public void systemClose() {
		System.out.println("******************************************");
		System.out.println("             시스템을 종료합니다.              ");
		System.out.println("******************************************");
	}
}