import java.util.HashMap;
import java.util.Map;

public class DataBase {
	//(id, password) map
    private Map<String, String> info = new HashMap <String, String>();
	
	//(id, cash) map
	private Map<String, Long> account = new HashMap <String, Long>();
   
	//������ -> ������ �ȵ���?
	public DataBase() {
		// �ʱ� ATM�� DataBase�� ���� �ִٴ� �����Ͽ� �����ϱ� ������
		// constructor�� �ʱ� �������� ������� �־��ݴϴ�. ->info (Hashmap) ���ٰ�
		info.put("00000-00000", "0000");
		account.put("00000-00000", (long)10000000);
		info.put("11111-11111", "0000");
		account.put("11111-11111", (long)20000);
		info.put("22222-22222", "");
		account.put("22222-22222", (long)30000);
	}
   
	//�ش� ������ ���� �ܾ��� �����մϴ�.(getter)
	public long getBalance(String id) {
		return account.get(id);
	}
	//�ؽøʿ� �ܾ� ������ ������Ʈ�մϴ�.(setter)
	public void setBalance(String id, long cash) {
		account.put(id, cash);
	}
	//���ڷ� ���� id�� password�� �ʿ� ����� password�� ��ġ�ϴ��� �˷��ݴϴ�.
	public boolean checkPassword(String account, String password) {
		if(password == info.get(account)) return true;
		return false;
	}
	//���ڷ� ���� id�� db�� �����ϴ��� �˷��ݴϴ�
	public boolean checkId(String id) {
		if(info.containsKey(id)) {
			return true;
		}
		return false;
	}
}