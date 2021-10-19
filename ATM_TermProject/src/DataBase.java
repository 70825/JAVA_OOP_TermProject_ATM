import java.util.HashMap;
import java.util.Map;

public class DataBase {
	//(id, password) map
    private Map<String, String> info = new HashMap <String, String>();
	
	//(id, cash) map
	private Map<String, Long> account = new HashMap <String, Long>();
   
	//생성자 -> 생성이 안돼지?
	public DataBase() {
		// 초기 ATM이 DataBase를 갖고 있다는 가정하에 시작하기 때문에
		// constructor에 초기 정보들을 어느정도 넣어줍니다. ->info (Hashmap) 에다가
		info.put("00000-00000", "0000");
		account.put("00000-00000", (long)10000000);
		info.put("11111-11111", "0000");
		account.put("11111-11111", (long)20000);
		info.put("22222-22222", "");
		account.put("22222-22222", (long)30000);
	}
   
	//해당 계좌의 보유 잔액을 리턴합니다.(getter)
	public long getBalance(String id) {
		return account.get(id);
	}
	//해시맵에 잔액 정보를 업데이트합니다.(setter)
	public void setBalance(String id, long cash) {
		account.put(id, cash);
	}
	//인자로 받은 id와 password가 맵에 저장된 password와 일치하는지 알려줍니다.
	public boolean checkPassword(String account, String password) {
		if(password == info.get(account)) return true;
		return false;
	}
	//인자로 받은 id가 db에 존재하는지 알려줍니다
	public boolean checkId(String id) {
		if(info.containsKey(id)) {
			return true;
		}
		return false;
	}
}