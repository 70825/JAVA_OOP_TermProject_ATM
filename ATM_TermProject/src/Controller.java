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
    private int work;
    
    // 로그인
    private boolean flag_id = false, flag_password = false;
    private boolean flag_check_id = false, flag_check_password = false;
    private String id = null, password = null;
    
    // 입금, 출금, 송금, 잔고 조회 공통
    private boolean kind_account; // true = 입출금계좌, false = 정기예금계좌
    private int total_money;
    
    // 입금
    private int[] putMoney = {0, 0, 0, 0}; // 지폐 종류(1000, 5000, 10000, 50000)
    
    // 출금
    private int[] outMoney = {0, 0}; // 지폐 종류(10000, 50000)
    private boolean flag_atm, flag_account, flag_buffer;
    private long[] errorValue1 = {0} ;
    private long[] errorValue2 = {0, 0};
    
    // 송금
    private String remittance_id;
    private boolean flag_remittance_account, flag_remittance_money;
    private long remittance_money;
    
    // 잔고 조회
    private String checkName, checkPeriod;
    private long checkMoney;
    
    // 트랜잭션 로그
    private long beforeLogCash, afterLogCash;
    private boolean accessLog;
    private int accessNumber;
    
    
    // ====================== 프로그램 시작 ==============================
    
    public boolean run() { // 프로그램 시작
		// 어떤 업무를 수행할 것인지 (1: 입금, 2: 출금: 3: 송금, 4: 잔고 조회 5: 종료, 6: 시스템 종료)
		this.work = GUI.open();
		
		if(this.work == 5) { // 단순 종료 -> 로그인 정보 초기화
			this.emptyLogin(); 
			return true;
		}
		if(this.work == 6) { // 시스템 종료 -> 로그 기록 접근 -> 종료
			this.showLogATM();
			return false;
		}
		
		// 이미 로그인되어 있으면 건너뜀
		if(this.id == null && this.password == null) this.login();
		
		// 통장 종류 확인
		this.kind_account = database.kindAccount(id);
		
		// work 변수에 따른 동작 실행
		switch(this.work) {
		case 1: // 입금
			this.depositATM();
			break;
		case 2: // 출금
			this.withdrawATM();
			break;
		case 3: // 송금
			this.remittanceATM();
			break;
		case 4: // 잔고 조회
			this.checkBalanceATM();
			break;
		}
		
		return true;
	}
    
    // ======================= 컨트롤러 내부 함수 =======================
	
	private void login() { // 로그인 기능 - 아이디와 비밀번호가 맞을 때 무한 루프를 탈출함
		while(!this.flag_id) {
			this.id = GUI.login_account(this.flag_check_id);
			this.flag_id = database.checkAccount(this.id);
			this.flag_check_id = true;
		}
		
		while(!this.flag_password) {
			this.password = GUI.login_password(this.flag_check_password);
			this.flag_password = database.checkPassword(this.id, this.password);
			this.flag_check_password = true;
		}
	}
	
	private void emptyLogin() {
		this.flag_id = false;
		this.flag_password = false;
		this.flag_check_id = false;
		this.flag_check_password =false;
		this.id = null;
		this.password = null;
		
		GUI.close();
	}
	
	private void depositATM() {
		// Controller <-> View
		// 금액을 입력 받고, 모든 돈의 합을 구함
		this.putMoney = GUI.inputShowDeposit();
		this.total_money = this.putMoney[0] * 1000 + this.putMoney[1] * 5000 + this.putMoney[2] * 10000 + this.putMoney[3] * 50000;
		
		// Controller <-> Model, Controller -> Transaction Log
		// 계좌에 돈을 추가함. 이때 필요한 정보를 변수에 담고 트랜잭션 로그에 기록함
		this.beforeLogCash = database.getBalance(this.id);
		this.database.setBalance(this.id, database.getBalance(this.id) + this.total_money);
		this.afterLogCash = database.getBalance(this.id);
		LOG.putLog(this.id, this.work, beforeLogCash, afterLogCash, "");
		
		// Controller -> Model
		// ATM에 돈을 추가함
		atm.setWon_1000(atm.getWon_1000() + this.putMoney[0]);
		atm.setWon_5000(atm.getWon_5000() + this.putMoney[1]);
		atm.setWon_10000(atm.getWon_10000() + this.putMoney[2]);
		atm.setWon_50000(atm.getWon_50000() + this.putMoney[3]);
		
		
		GUI.closeShowDeposit();
		
		return;
	}
	
	private void withdrawATM() {
		if(!this.kind_account) { // 정기 예금 계좌가 할 수 없는 행동
			GUI.inaccessible();
			return;
		}
		
		GUI.openShowWithdraw();
		
		// Controller <-> View
		// 출금할 지폐를 입력 받음
		this.outMoney = GUI.inputShowWithDraw(true);
		this.total_money = this.outMoney[0] * 10000 + this.outMoney[1] * 50000;
		
		
		// Model <-> Controller <-> View
		// ATM에 해당 꺼낼 돈이 있는지, 계좌에 꺼낼 돈이 있는지 확인, 하나라도 돈이 부족하면 에러메세지를 출력하고 다시 입력 받음
		this.flag_atm = false;
		this.flag_account = false;
		
		while(!this.flag_atm || !this.flag_account) {
			if(atm.getWon_10000() >= this.outMoney[0] && atm.getWon_50000() >= this.outMoney[1]) {
				this.flag_atm = true;
				if(database.getBalance(this.id) >= this.total_money) {
					this.flag_account = true;
				}
				else {
					this.errorValue1[0] = database.getBalance(this.id);
					GUI.errorShowWithDraw(1, errorValue1);
				}
			}
			else {
				this.errorValue2[0] = atm.getWon_10000();
				this.errorValue2[1] = atm.getWon_50000();
				GUI.errorShowWithDraw(2, errorValue2);
			}
			if(!this.flag_atm || !this.flag_account) {
				this.outMoney = GUI.inputShowWithDraw(false);
			}
		}
		
		
		// Model <-> Controller, Controller <-> TransactionLog
		// 계좌에서 돈을 출금하고, 트랜잭션 로그에 기록할 정보를 담고 기록해줌
		this.beforeLogCash = database.getBalance(this.id);
		database.setBalance(this.id, database.getBalance(this.id) - this.total_money);
		this.afterLogCash = database.getBalance(this.id);
		LOG.putLog(this.id, this.work, this.beforeLogCash, this.afterLogCash, "");
		
		
		// Controller -> Model
		// ATM에 지폐를 줄여줌
		atm.setWon_10000(atm.getWon_10000() - this.outMoney[0]);
		atm.setWon_10000(atm.getWon_50000() - this.outMoney[1]);
		
		
		GUI.closeShowWithDraw();
		
		return;
	}
	
	private void remittanceATM() {
		if(!this.kind_account) { // 정기 예금 계좌가 할 수 없는 행동
			GUI.inaccessible();
			return;
		}
		
		GUI.openShowRemittance();
		
		// Model <-> Controller <-> View
		// 송금할 계좌가 존재하는지, 송금할 돈이 있는지 확인하고, 하나라도 부족하면 에러메세지 출력하면서 다시 입력 받음
		this.flag_remittance_account = false;
		while(!this.flag_remittance_account) {
			this.remittance_id = GUI.inputAccountShowRemittance(this.flag_remittance_account);
			this.flag_remittance_account = database.checkAccount(this.remittance_id);
		}
		
		this.flag_remittance_money = false;
		while(!this.flag_remittance_money) {
			this.remittance_money = GUI.inputMoneyShowRemittance(this.flag_remittance_money);
			if(database.getBalance(this.id) >= this.remittance_money) {
				this.flag_remittance_money = true;
			}
		}
		
		// Model <-> Controller, Controller -> Transaction Log
		// 송금하는 계좌는 출금 기능 재사용, 송금받는 계좌는 입금 기능 재사용
		// 이와 동시에 트랜잭션 로그 기록에 필요한 값을 저장하여 트랜잭션 로그를 기록함
		this.beforeLogCash = database.getBalance(this.id);
		database.setBalance(this.id, database.getBalance(this.id) - this.remittance_money);
		database.setBalance(this.remittance_id, database.getBalance(this.remittance_id) + this.remittance_money);
		this.afterLogCash = database.getBalance(this.id);
		LOG.putLog(this.id , this.work, this.beforeLogCash, this.afterLogCash, this.remittance_id);
		
		
		GUI.closeShowRemittance();
		
	}
	
	
	private void checkBalanceATM() {
		GUI.openShowCheckBalance();
		
		// Model <-> Controller
		// 필요한 정보를 가지고 옴
		this.checkName = database.getName(this.id);
		this.checkMoney = database.getBalance(this.id);
		this.checkPeriod = database.getPeriod(this.id);
		
		// Controller -> View
		// 고객의 정보를 출력함
		GUI.showCheckBalance(this.checkName, this.id, this.kind_account, this.checkMoney, this.checkPeriod);
		
		LOG.putLog(this.id, this.work, 0, 0, "");
		
		GUI.closeShowCheckBalance();
	}
	
	private void showLogATM() {
		// Controller <-> View
		// 트랜잭션 로그 기능에 접근할 것인지 확인함, N이면 종료 절차를 밟음
		this.accessLog = GUI.openShowLog().equals("Y")? true : false;
		if(!this.accessLog) {
			GUI.systemClose();
			return;
		}
		
		// TransactionLog <-> Controller <-> View
		// 찾고자하는 기록 번호를 통해 트랜잭션 로그 기록을 불러와서 보여줌
		this.accessNumber = -1;
		while(true) {
			this.accessNumber = GUI.showSelectLog();
			
			if(this.accessNumber == 0) break;
			
			GUI.showLog(this.accessNumber, LOG.getLog(this.accessNumber));
		}
		
		GUI.systemClose();
	}
}
