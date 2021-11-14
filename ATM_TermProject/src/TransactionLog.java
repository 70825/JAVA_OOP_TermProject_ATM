import java.util.HashMap;
import java.util.Map;

public class TransactionLog {
	public static int count; // �α� ��ȣ ���
	private Map<Integer, String> transactionLog;
	
	public TransactionLog() {
		this.count = 1;
		this.transactionLog = new HashMap<>();
	}
	
	public void putLog(String account, int flag, long cash1, long cash2, String remitt_account) {
		this.transactionLog.put(count, "���¹�ȣ : " + account + "�� " + 
	(flag == 1 ? "�Ա� ����� ����Ͽ� " + Long.toString(cash1) + "������ " + Long.toString(cash2) + "������ �ܾ��� �÷Ƚ��ϴ�.":
	 flag == 2 ? "��� ����� ����Ͽ� " + Long.toString(cash1) + "������ " + Long.toString(cash2) + "������ �ܾ��� �ٿ����ϴ�.":
	 flag == 3 ? "�۱� ����� ����Ͽ� ���¹�ȣ " + remitt_account + "�� " + Long.toString(cash1 - cash2) + "���� �۱��Ͽ����ϴ�.":
	 "�ܾ���ȸ ��ɿ� ����Ͽ����ϴ�."));
		
		this.count++;
	}
	
	public String getLog(int c) {
		if(!this.transactionLog.containsKey(c)) return "�������� �ʴ� ����Դϴ�.";
		return this.transactionLog.get(c);
	}
}
