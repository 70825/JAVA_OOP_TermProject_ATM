import java.util.*;

public class Interface {
    private String accountNumber, password;
	private Scanner sc = new Scanner(System.in);
	
	public void inaccessible() {
		System.out.println("******************************************");
		System.out.println("     ���⿹�� ������ �Աݰ� �ܾ���ȸ�� �����մϴ�.      ");
		System.out.printf("******************************************\n\n\n\n");
		return;
	}
	
	// ==================== �Ա� ======================
	
	public int[] inputShowDeposit() {
		// õ�� 1��, ��õ�� 2��, ���� 3��, ������ 4���̸� �Է��� '1 2 3 4'�� ��
		System.out.println("******************************************");
		System.out.println("               �Ա��� �����Ͽ����ϴ�.            ");
		System.out.printf("�Ա��� ���� 4���� �Է��ϼ���.(õ��, ��õ��, ����, ������) : ");
		
		sc.nextLine(); // ���� ����
		StringTokenizer st = new StringTokenizer(sc.nextLine());
		
		int won_1000 = Integer.parseInt(st.nextToken());
		int won_5000 = Integer.parseInt(st.nextToken());
		int won_10000 = Integer.parseInt(st.nextToken());
		int won_50000 = Integer.parseInt(st.nextToken());
		
		int[] output = {won_1000, won_5000, won_10000, won_50000};
		
		return output;
	}
	
	public void closeShowDeposit() {
		System.out.println("      �Ա��� �Ϸ�Ǿ����ϴ�. �̿����ּż� �����մϴ�.   ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ==================== ��� ======================
	
	public void openShowWithdraw() {
		System.out.println("******************************************");
		System.out.println("               ����� �����Ͽ����ϴ�.            ");
		return;
	}
	
	public int[] inputShowWithDraw(boolean flag) {
		System.out.printf("����� ���� 2���� �Է��ϼ���.(����, ������) : ");
		if(flag) sc.nextLine(); // ���� ����
		StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
		
		int won_10000 = Integer.parseInt(st.nextToken());
		int won_50000 = Integer.parseInt(st.nextToken());
		
		int[] output = {won_10000, won_50000};
		
		return output;
	}
	
	public void errorShowWithDraw(int select, long[] values) {
		if(select == 1) {
			System.out.printf("���¿� �ִ� �ܾ��� �����մϴ�. ���� ���¿� �ִ� �ݾ��� %d�� �Դϴ�.\n", values[0]);
		}
		else{
			System.out.printf("atm�� �ִ� ���� �����մϴ�. ���� atm�� ������ %d�� �����ϰ�, �������� %d�� �����մϴ�.\n", values[0], values[1]);
		}
	}
	
	public void closeShowWithDraw() {
		System.out.println("    ����� �Ϸ�Ǿ����ϴ�. �̿����ּż� �����մϴ�.     ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ==================== �۱� ======================
	
	public void openShowRemittance() {
		System.out.println("******************************************");
		System.out.println("               �۱��� �����Ͽ����ϴ�.            ");
	}
	
	public String inputAccountShowRemittance(boolean flag) {
		if(!flag) System.out.printf("�۱��� ���� ���¹�ȣ�� �Է��ϼ���. : ");
		else System.out.printf("�۱� ���� ���¹�ȣ�� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���. : ");
		String Remittance_Account = sc.next();
		
		return Remittance_Account;
	}
	
	public long inputMoneyShowRemittance(boolean flag) {
		if(!flag) System.out.printf("�۱��� �ݾ��� �Է����ּ���. : ");
		else System.out.printf("���³��� �ִ� �ݾ��� �����մϴ�. �ٽ� �Է����ּ���. : ");
		long money = sc.nextInt();
		
		return money;
	}
	
	public void closeShowRemittance() {
		System.out.println("�۱��� �Ϸ�Ǿ����ϴ�.");
		System.out.println("            �̿����ּż� �����մϴ�.             ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	// ==================== �ܰ� ��ȸ ======================
	public void openShowCheckBalance() {
		System.out.println("******************************************");
		System.out.println("            �ܾ� ��ȸ�� �����Ͽ����ϴ�.           ");
	}
	
	public void showCheckBalance(String name, String id, boolean flag_kind, long balance, String period) {
		
		System.out.printf("�̸�: %s\n", name);
		System.out.printf("���� ��ȣ: %s\n", id);
		System.out.printf("���� ���� : %s\n", flag_kind? "����� ����" : "���� ���� ����");
		System.out.printf("�ܾ� : %d\n", balance);
		if(!flag_kind){
			System.out.printf("���� ���� ��¥ : %s\n", period);
		}
		
	}
	
	public void closeShowCheckBalance() {
		System.out.println("            �̿����ּż� �����մϴ�.             ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ================= �α��� ======================
   
	public String login_account(boolean flag) { // ���¹�ȣ �Է�
		if(!flag) System.out.print("�α����� ���� ��ȣ�� �Է����ּ���. : ");
		else System.out.print("�������� �ʴ� ���¹�ȣ�Դϴ�. �ٽ� �Է����ּ���. : ");
		
		this.accountNumber = sc.next();
		
		return this.accountNumber;
	}
	
	public String login_password(boolean flag) { // ��й�ȣ �Է�
		if(!flag) System.out.print("��й�ȣ�� �Է����ּ���. : ");
		else System.out.print("��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �Է����ּ���. : ");
		
		this.password = sc.next();
		
		return this.password;
	}
	
	// ======================== ���� �� ���� ===========================
	
	public int open() {
		System.out.println("******************************************");
		System.out.println("           ������ ã���ּż� �����մϴ�.          ");
		System.out.println("******************************************");
		System.out.println("1: �Ա�, 2: ���: 3: �۱�, 4: �ܰ� ��ȸ 5: ����, 6: �ý��� ����");
		System.out.printf("�Ͻ� ������ �������ּ���. : ");
		int val = sc.nextInt();
		
		return val;
	}
	
	public void close() { // �Ϸ�
		System.out.println("******************************************");
		System.out.println("          ������ �̿����ּż� �����մϴ�.          ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	// ===================== Ʈ����� �α� ���� �� �ý��� ���� ==============================
	
	public String openShowLog() {
		System.out.printf("\nƮ����� �α� ����(Y/N) : ");
		String ans = sc.next();
		
		return ans;
	}
	
	public int showSelectLog() {
		System.out.printf("������ �α� ��ȣ�� �Է����ּ���.(0: ����) : ");
		int val = sc.nextInt();
		
		return val;
	}
	
	public void showLog(int val, String Log) {
		System.out.println("�α� ��ȣ " + Integer.toString(val) + ": " + Log);
	}
	
	public void systemClose() {
		System.out.println("******************************************");
		System.out.println("             �ý����� �����մϴ�.              ");
		System.out.println("******************************************");
	}
}