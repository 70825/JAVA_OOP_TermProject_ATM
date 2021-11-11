import java.util.stream.IntStream;

// 컨트롤러 : VIEW와 MODEL을 연결해주는 역할
// VIEW와 MODEL은 코드 내부에서 직접적으로 서로 연결해주면 안됌. 그래서 컨트롤러를 사용해 간접적으로 연결함
public class Controller {
	// 클래스 불러오기
	private static DataBase database = new DataBase();
    private static ATM atm = new ATM();
    private static Interface GUI = new Interface();
    private static TransactionLog LOG = new TransactionLog();
    
    // ======== 컨트롤러에서 사용할 변수 ==========
    // 업무선택
    public static int work = 0;
    
    // 로그인
    private static boolean flag_id = false, flag_password = false;
    private static boolean flag_check_id = false, flag_check_password = false;
    private static String id = null, password = null;
    
    // 입금, 출금, 송금, 잔고 조회 공통
    public static boolean kind_account; // true = 입출금계좌, false = 정기예금계좌
    private static int total_money;
    
    // 입금
    public static int[] putMoney = {0, 0, 0, 0}; // 지폐 종류(1000, 5000, 10000, 50000)
    
    // 출금
    private static int[] outMoney = {0, 0}; // 지폐 종류(10000, 50000)
    private static boolean flag_atm, flag_account, flag_buffer;
    private static long[] errorValue1 = {0} ;
    private static long[] errorValue2 = {0, 0};
    
    // 송금
    public static String remittance_id;
    private static boolean flag_remittance_account, flag_remittance_money;
    public static long remittance_money;
    
    // 잔고 조회
    private static String checkName, checkPeriod;
    private static long checkMoney;
    
    // 트랜잭션 로그
    private static long beforeLogCash, afterLogCash;
    private static boolean accessLog;
    private static int accessNumber;
    
    
    // ====================== 프로그램 시작 ==============================
    
    public static boolean run() { // 프로그램 시작
		// 어떤 업무를 수행할 것인지 (1: 입금, 2: 출금: 3: 송금, 4: 잔고 조회 5: 종료, 6: 시스템 종료)
    	
		if(work == 5) { // 단순 종료 -> 로그인 정보 초기화
			emptyLogin(); 
			return true;
		}
		if(work == 6) { // 시스템 종료 -> 로그 기록 접근 -> 종료
			showSystemCloseATM();
			return false;
		}
		
		// 이미 로그인되어 있으면 건너뜀
		if(id == null && password == null) login();
		
		// 통장 종류 확인
		kind_account = database.kindAccount(id);
		
		// work 변수에 따른 동작 실행
		switch(work) {
		case 1: // 입금
			depositATM();
			break;
		case 2: // 출금
			withdrawATM();
			break;
		case 3: // 송금
			remittanceATM();
			break;
		case 4: // 잔고 조회
			checkBalanceATM();
			break;
		}
		
		return true;
	}
    
    // ======================= 컨트롤러 내부 함수 =======================
	
	public static void login() { // 로그인 기능 - 아이디와 비밀번호가 맞을 때 무한 루프를 탈출함
		while(!flag_id) {
			id = GUI.login_account(flag_check_id);
			flag_id = database.checkAccount(id);
			flag_check_id = true;
		}
		
		while(!flag_password) {
			password = GUI.login_password(flag_check_password);
			flag_password = database.checkPassword(id, password);
			flag_check_password = true;
		}
	}
	
	public static void emptyLogin() {
		flag_id = false;
		flag_password = false;
		flag_check_id = false;
		flag_check_password =false;
		id = null;
		password = null;
	}
	
	public static void depositATM() {
		// Controller <-> View
		// 금액을 입력 받고, 모든 돈의 합을 구함
		//GUI.inputShowDeposit();
		total_money = putMoney[0] * 1000 + putMoney[1] * 5000 + putMoney[2] * 10000 + putMoney[3] * 50000;
		
		// Controller <-> Model, Controller -> Transaction Log
		// 계좌에 돈을 추가함. 이때 필요한 정보를 변수에 담고 트랜잭션 로그에 기록함
		beforeLogCash = database.getBalance(id);
		database.setBalance(id, database.getBalance(id) + total_money);
		afterLogCash = database.getBalance(id);
		LOG.putLog(id, work, beforeLogCash, afterLogCash, "");
		
		// Controller -> Model
		// ATM에 돈을 추가함
		atm.setWon_1000(atm.getWon_1000() + putMoney[0]);
		atm.setWon_5000(atm.getWon_5000() + putMoney[1]);
		atm.setWon_10000(atm.getWon_10000() + putMoney[2]);
		atm.setWon_50000(atm.getWon_50000() + putMoney[3]);
		
		GUI.closeShowDeposit();
		return;
	}
	
	public static void withdrawATM() {
		if(!kind_account) { // 정기 예금 계좌가 할 수 없는 행동
			GUI.inaccessible();
			return;
		}
		
		GUI.openShowWithdraw();
		
		// Controller <-> View
		// 출금할 지폐를 입력 받음
		outMoney = GUI.inputShowWithdraw(true);
		total_money = outMoney[0] * 10000 + outMoney[1] * 50000;
		
		
		// Model <-> Controller <-> View
		// ATM에 해당 꺼낼 돈이 있는지, 계좌에 꺼낼 돈이 있는지 확인, 하나라도 돈이 부족하면 에러메세지를 출력하고 다시 입력 받음
		flag_atm = false;
		flag_account = false;
		
		while(!flag_atm || !flag_account) {
			if(atm.getWon_10000() >= outMoney[0] && atm.getWon_50000() >= outMoney[1]) {
				flag_atm = true;
				if(database.getBalance(id) >= total_money) {
					flag_account = true;
				}
				else {
					errorValue1[0] = database.getBalance(id);
					GUI.errorShowWithdraw(1, errorValue1);
				}
			}
			else {
				errorValue2[0] = atm.getWon_10000();
				errorValue2[1] = atm.getWon_50000();
				GUI.errorShowWithdraw(2, errorValue2);
			}
			if(!flag_atm || !flag_account) {
				outMoney = GUI.inputShowWithdraw(false);
			}
		}
		
		
		// Model <-> Controller, Controller <-> TransactionLog
		// 계좌에서 돈을 출금하고, 트랜잭션 로그에 기록할 정보를 담고 기록해줌
		beforeLogCash = database.getBalance(id);
		database.setBalance(id, database.getBalance(id) - total_money);
		afterLogCash = database.getBalance(id);
		LOG.putLog(id, work, beforeLogCash, afterLogCash, "");
		
		
		// Controller -> Model
		// ATM에 지폐를 줄여줌
		atm.setWon_10000(atm.getWon_10000() - outMoney[0]);
		atm.setWon_10000(atm.getWon_50000() - outMoney[1]);
		
		
		GUI.closeShowWithdraw();
		
		return;
	}
	
	
	// 송금
	
	public static boolean checkRemittanceAccount(String remittance_id) {
		return database.checkAccount(remittance_id);
	}
	
	public static boolean checkAccountBalance(long money) {
		return database.getBalance(id) >= money;
	}
	
	public static void remittanceATM() {
		
		// Model <-> Controller, Controller -> Transaction Log
		// 송금하는 계좌는 출금 기능 재사용, 송금받는 계좌는 입금 기능 재사용
		// 이와 동시에 트랜잭션 로그 기록에 필요한 값을 저장하여 트랜잭션 로그를 기록함
		beforeLogCash = database.getBalance(id);
		database.setBalance(id, database.getBalance(id) - remittance_money);
		database.setBalance(remittance_id, database.getBalance(remittance_id) + remittance_money);
		afterLogCash = database.getBalance(id);
		LOG.putLog(id , work, beforeLogCash, afterLogCash, remittance_id);
		
		
		GUI.closeShowRemittance();
		
	}
	
	
	public static void checkBalanceATM() {
		GUI.openShowCheckBalance();
		
		// Model <-> Controller
		// 필요한 정보를 가지고 옴
		checkName = database.getName(id);
		checkMoney = database.getBalance(id);
		checkPeriod = database.getPeriod(id);
		
		// Controller -> View
		// 고객의 정보를 출력함
		GUI.showCheckBalance(checkName, id, kind_account, checkMoney, checkPeriod);
		
		LOG.putLog(id, work, 0, 0, "");
		
		GUI.closeShowCheckBalance();
	}
	
	public static String getTransactionLog(int c) {
		return LOG.getLog(c);
	}
	
	public static void showSystemCloseATM() {
		// Controller <-> View
		// 트랜잭션 로그 기능에 접근할 것인지 확인함, N이면 종료 절차를 밟음
		
		GUI.systemCloseMessage();
		System.exit(0);
	}
}
