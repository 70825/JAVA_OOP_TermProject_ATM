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
    public static int[] outMoney = {0, 0}; // 지폐 종류(10000, 50000)
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
		// 금액을 입력 받은 것으로 모든 돈의 합을 구함
		total_money = putMoney[0] * 1000 + putMoney[1] * 5000 + putMoney[2] * 10000 + putMoney[3] * 50000;
		
		// Database <-> Controller <-> Model, Controller -> Transaction Log
		// 계좌에 돈을 추가함. 이때 필요한 정보를 변수에 담고 트랜잭션 로그에 기록함
		Account nowAccount = database.getAccount(id);
		beforeLogCash = nowAccount.getAccountBalance();
		nowAccount.setAccountBalance(nowAccount.getAccountBalance() + total_money);
		afterLogCash = nowAccount.getAccountBalance();
		database.modifyAccount(id, nowAccount);
		
		LOG.putLog(id, 1, beforeLogCash, afterLogCash, "");
		
		// Controller -> Model
		// ATM에 돈을 추가함
		atm.setWon_1000(atm.getWon_1000() + putMoney[0]);
		atm.setWon_5000(atm.getWon_5000() + putMoney[1]);
		atm.setWon_10000(atm.getWon_10000() + putMoney[2]);
		atm.setWon_50000(atm.getWon_50000() + putMoney[3]);
		
		// 종료 화면
		GUI.closeShowDeposit();
		return;
	}
	
	public static void withdrawATM() {
		// 금액을 입력 받은 것으로 모든 돈의 합을 구함
		total_money = outMoney[0] * 10000 + outMoney[1] * 50000;
		
		// Database <-> Model <-> Controller, Controller <-> TransactionLog
		// 계좌에서 돈을 출금하고, 트랜잭션 로그에 기록할 정보를 담고 기록해줌
		Account nowAccount = database.getAccount(id);
		beforeLogCash = nowAccount.getAccountBalance();
		nowAccount.setAccountBalance(nowAccount.getAccountBalance() - total_money);
		afterLogCash = nowAccount.getAccountBalance();
		database.modifyAccount(id, nowAccount);
		LOG.putLog(id, 2, beforeLogCash, afterLogCash, "");
		
		
		// Controller -> Model
		// ATM에 지폐를 줄여줌
		atm.setWon_10000(atm.getWon_10000() - outMoney[0]);
		atm.setWon_50000(atm.getWon_50000() - outMoney[1]);
		
		GUI.closeShowWithdraw();
		
		return;
	}
	
	public static long getAccountBalance() {
		return database.getAccount(id).getAccountBalance();
	}
	
	public static long[] getATMNumbers() {
		long[] ans = {atm.getWon_10000(), atm.getWon_50000()};
		return ans;
	}
	public static boolean checkATMNumber() {
		if(outMoney[0] <= atm.getWon_10000() && outMoney[1] <= atm.getWon_50000()) return true;
		return false;
	}
	
	
	// 송금
	public static boolean checkRemittanceAccount(String remittance_id) {
		return database.checkAccount(remittance_id);
	}
	
	public static boolean checkAccountBalance(long money) {
		return database.getAccount(id).getAccountBalance() >= money;
	}
	
	public static void remittanceATM() {
		
		// Database <-> Model <-> Controller, Controller -> Transaction Log
		// 송금하는 계좌는 출금 기능 재사용, 송금받는 계좌는 입금 기능 재사용
		// 이와 동시에 트랜잭션 로그 기록에 필요한 값을 저장하여 트랜잭션 로그를 기록함
		if(database.kindAccount(remittance_id)) {
			Account nowAccount = database.getAccount(id);
			Account remittanceAccount = database.getAccount(remittance_id);
			
			beforeLogCash = nowAccount.getAccountBalance();
			nowAccount.setAccountBalance(nowAccount.getAccountBalance() - remittance_money);
			remittanceAccount.setAccountBalance(remittanceAccount.getAccountBalance() + remittance_money);
			afterLogCash = nowAccount.getAccountBalance();
			
			database.modifyAccount(id, nowAccount);
			database.modifyAccount(remittance_id, remittanceAccount);
		}
		else {
			Account nowAccount = database.getAccount(id);
			TermDepositAccount remittanceAccount = database.getTermDepositAccount(remittance_id);
			
			beforeLogCash = nowAccount.getAccountBalance();
			nowAccount.setAccountBalance(nowAccount.getAccountBalance() - remittance_money);
			remittanceAccount.setAccountBalance(remittanceAccount.getAccountBalance() + remittance_money);
			afterLogCash = nowAccount.getAccountBalance();
			
			database.modifyAccount(id, nowAccount);
			database.modifyTermDepositAccount(remittance_id, remittanceAccount);
		}
		LOG.putLog(id , 3, beforeLogCash, afterLogCash, remittance_id);
		
		
		GUI.closeShowRemittance();
		
	}
	
	// 잔고 조회
	public static void checkBalanceATM() {
		// Controller <-> View
		// 잔고 조회 JPanel을 가져옴
		Interface.showCheckBalance sCB = GUI.new showCheckBalance();
		
		// Database <-> Model <-> Controller <-> View
		// 컨트롤러에서 database를 통해 model을 가져와 정보를 얻어내고 뷰에 정보가 나오게 해줌
		if(kind_account) {
			Account acc = database.getAccount(id);
			checkName = acc.getAccountUserName();
			checkMoney = acc.getAccountBalance();
			sCB.showCheckBalance(checkName, id, kind_account, checkMoney, "");
		}
		else {
			TermDepositAccount tdacc = database.getTermDepositAccount(id);
			checkName = tdacc.getAccountUserName();
			checkMoney = tdacc.getAccountBalance();
			checkPeriod = tdacc.getAccountPeriod();
			sCB.showCheckBalance(checkName, id, kind_account, checkMoney, checkPeriod);
		}
		
		// Controller -> TransactionLog
		// 잔액 조회에 접근한 것을 로그에 저장함
		LOG.putLog(id, 0, 0, 0, "");
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
