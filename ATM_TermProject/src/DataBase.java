import java.util.HashMap;
import java.util.Map;

public class DataBase {

    private Map<String, String> accountInfo = new HashMap <String, String>();
	
	private Map<String, Long> money = new HashMap <String, Long>();
	
	private Map<String, String> accountName = new HashMap <String, String>();
	
	private Map<String, Integer> accountKind = new HashMap <String, Integer>();
	
	private Map<String, String> accountPeriod = new HashMap <String, String>();
	
	private Map<Integer, String> transactionLog = new HashMap <Integer, String>();
	public int count = 1;
	
	public DataBase() {

		accountInfo.put("000000-000000", "0000");
		money.put("000000-000000", (long)10000000);
		accountName.put("000000-000000", "�̽���");
		accountKind.put("000000-000000", 1); 		//1 = "����� ����"	2= "���⿹�� ����"
		accountPeriod.put("000000-000000", "NULL");
		
		accountInfo.put("111111-111111", "1111");
		money.put("111111-111111", (long)20000);
		accountName.put("111111-111111", "������");
		accountKind.put("111111-111111", 1);
		accountPeriod.put("111111-111111", "NULL");
		
		accountInfo.put("222222-222222", "2222");
		money.put("222222-222222", (long)30000);
		accountName.put("222222-222222", "���ٺ�");
		accountKind.put("222222-222222", 1);
		accountPeriod.put("222222-222222", "NULL");
		
		accountInfo.put("333333-333333", "1234");
		money.put("333333-333333", (long)8156000);
		accountName.put("333333-333333", "������");
		accountKind.put("333333-333333", 2);
		accountPeriod.put("333333-333333", "2022��3��12��");
		
		accountInfo.put("444444-444444", "9876");
		money.put("444444-444444", (long)314000);
		accountName.put("444444-444444", "���ٺ�");
		accountKind.put("444444-444444", 2);
		accountPeriod.put("444444-444444", "2021��12��5��");

	}

	public long getBalance(String account) {
		return money.get(account);
	}

	public void setBalance(String account, long cash) {
		money.put(account, cash);
	}
	
	public boolean checkPassword(String account, String password) {
		if(password.equals(accountInfo.get(account))) return true;
		return false;
	}

	public boolean checkAccount(String account) {
		if(accountInfo.containsKey(account)) return true;
		return false;
	}
	
	public String getName(String account) {
		return accountName.get(account);
	}
	
	public String getKindAccount(String account) {
		if(accountKind.get(account) == 1) return "����� ����";
		return "���⿹�� ����";
	}
	
	public String getPeriod(String account) {
		return accountPeriod.get(account);
	}
	
	public void setLog(int c, String account, int flag, long cash1, long cash2, String remitt_account) {
		transactionLog.put(c, "���¹�ȣ : " + account + "�� " + 
	(flag == 1 ? "�Ա� ����� ����Ͽ� " + Long.toString(cash1) + "������ " + Long.toString(cash2) + "������ �ܾ��� �÷Ƚ��ϴ�.":
	 flag == 2 ? "��� ����� ����Ͽ� " + Long.toString(cash1) + "������ " + Long.toString(cash2) + "������ �ܾ��� �ٿ����ϴ�.":
	 flag == 3? "�۱� ����� ����Ͽ� ���¹�ȣ " + remitt_account + "�� " + Long.toString(cash1) + "���� �۱��Ͽ����ϴ�.":
	 "�ܾ���ȸ ��ɿ� ����Ͽ����ϴ�."));
	}
	
	public String getLog(int c) {
		if(!transactionLog.containsKey(c)) return "�������� �ʴ� ����Դϴ�.";
		return transactionLog.get(c);
	}
}