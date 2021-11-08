// MVC 패턴
// Model : Account, TermDepositAccount, ATM
// View : Interface
// Controller : Controller
// Database : Database
// Transaction Log 기록: TransactionLog

public class Main {
    private static Controller controller = new Controller();
    
    // 컨트롤러를 통해 ATM 기기 실행
    public static void main(String[] args) {
    	
    	while(controller.run()){
    		// 프로그램 실행
    	}
    }
}