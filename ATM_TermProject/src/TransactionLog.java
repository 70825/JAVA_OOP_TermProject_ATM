import java.util.HashMap;
import java.util.Map;

public class TransactionLog {
	private int count = 1; // 로그 번호 기록
	private Map<Integer, String> transactionLog = new HashMap<>();
	
	public void putLog(String account, int flag, long cash1, long cash2, String remitt_account) {
		transactionLog.put(count, "계좌번호 : " + account + "가 " + 
	(flag == 1 ? "입금 기능을 사용하여 " + Long.toString(cash1) + "원에서 " + Long.toString(cash2) + "원으로 잔액을 늘렸습니다.":
	 flag == 2 ? "출금 기능을 사용하여 " + Long.toString(cash1) + "원에서 " + Long.toString(cash2) + "원으로 잔액을 줄였습니다.":
	 flag == 3? "송금 기능을 사용하여 계좌번호 " + remitt_account + "에 " + Long.toString(cash1) + "원을 송금하였습니다.":
	 "잔액조회 기능에 사용하였습니다."));
		
		count++;
	}
	
	public String getLog(int c) {
		if(!transactionLog.containsKey(c)) return "존재하지 않는 기록입니다.";
		return transactionLog.get(c);
	}
}
