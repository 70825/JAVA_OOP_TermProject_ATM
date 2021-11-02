import java.util.HashMap;
import java.util.Map;

public class TransactionLog {
	private int count = 1; // �α� ��ȣ ���
	private Map<Integer, String> transactionLog = new HashMap<>();
	
	public void putLog(String account, int flag, long cash1, long cash2, String remitt_account) {
		transactionLog.put(count, "���¹�ȣ : " + account + "�� " + 
	(flag == 1 ? "�Ա� ����� ����Ͽ� " + Long.toString(cash1) + "������ " + Long.toString(cash2) + "������ �ܾ��� �÷Ƚ��ϴ�.":
	 flag == 2 ? "��� ����� ����Ͽ� " + Long.toString(cash1) + "������ " + Long.toString(cash2) + "������ �ܾ��� �ٿ����ϴ�.":
	 flag == 3? "�۱� ����� ����Ͽ� ���¹�ȣ " + remitt_account + "�� " + Long.toString(cash1) + "���� �۱��Ͽ����ϴ�.":
	 "�ܾ���ȸ ��ɿ� ����Ͽ����ϴ�."));
		
		count++;
	}
	
	public String getLog(int c) {
		if(!transactionLog.containsKey(c)) return "�������� �ʴ� ����Դϴ�.";
		return transactionLog.get(c);
	}
}
