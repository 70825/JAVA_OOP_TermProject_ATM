import java.util.*;

public class Interface {
    private String accountNumber, password;
	private Scanner sc = new Scanner(System.in);
	
	public void Display(DataBase DB, ATM atm, TransactionLog LOG, String accountNumber, int flag) {
		this.accountNumber = accountNumber;
		
		if(!DB.kindAccount(this.accountNumber) && flag != 1 && flag != 4){
			System.out.println("******************************************");
			System.out.println("     ���⿹�� ������ �Աݰ� �ܾ���ȸ�� �����մϴ�.      ");
			System.out.printf("******************************************\n\n\n\n");
			return;
		}
		
		if(flag == 1) { // �Ա�
			// temp_cash -> ex) õ�� 1��, ��õ�� 2��, ���� 3��, ������ 4���̸�
			// �Է��� '1 2 3 4'�� ��
			System.out.println("******************************************");
			System.out.println("               �Ա��� �����Ͽ����ϴ�.            ");
			System.out.printf("�Ա��� ���� 4���� �Է��ϼ���.(õ��, ��õ��, ����, ������) : ");
			
			sc.nextLine(); // ���� ����
			StringTokenizer st = new StringTokenizer(sc.nextLine());
			
			int won_1000 = Integer.parseInt(st.nextToken());
			int won_5000 = Integer.parseInt(st.nextToken());
			int won_10000 = Integer.parseInt(st.nextToken());
			int won_50000 = Integer.parseInt(st.nextToken());
			
			int total = won_50000 * 50000 + won_10000 * 10000 + won_5000 * 5000 + won_1000 * 1000;
			
			long rogCash1 = DB.getBalance(this.accountNumber);
			atm.Deposit(DB, this.accountNumber, total);
			long rogCash2 = DB.getBalance(this.accountNumber);
			LOG.putLog(this.accountNumber, flag, rogCash1, rogCash2, "");
			
			atm.setWon_1000(atm.getWon_1000() + won_1000);
			atm.setWon_5000(atm.getWon_5000() + won_5000);
			atm.setWon_10000(atm.getWon_10000() + won_10000);
			atm.setWon_50000(atm.getWon_50000() + won_50000);
			
			System.out.println("      �Ա��� �Ϸ�Ǿ����ϴ�. �̿����ּż� �����մϴ�.   ");
			System.out.printf("******************************************\n\n\n\n");
		}
		else if(flag == 2) { // ���
			System.out.println("******************************************");
			System.out.println("               ����� �����Ͽ����ϴ�.            ");
			System.out.printf("����� ���� 2���� �Է��ϼ���.(����, ������) : ");
			sc.nextLine(); // ���� ����
			StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
			
			int won_10000 = Integer.parseInt(st.nextToken());
			int won_50000 = Integer.parseInt(st.nextToken());
			
			boolean flag1 = false;
			boolean flag2 = false;
			
			while(!flag1 || !flag2) {
				if(atm.getWon_10000() >= won_10000 && atm.getWon_50000() >= won_50000) {
					flag1 = true;
					if(DB.getBalance(this.accountNumber) >= (won_10000 * 10000 + won_50000 * 50000)) {
						flag2 = true;
					}
					else {
						System.out.printf("���¿� �ִ� �ܾ��� �����մϴ�. ���� ���¿� �ִ� �ݾ��� %d�� �Դϴ�.\n", DB.getBalance(this.accountNumber));
					}
				}
				else {
					System.out.printf("atm�� �ִ� ���� �����մϴ�. ���� atm�� ������ %d�� �����ϰ�, �������� %d�� �����մϴ�.\n", atm.getWon_10000(), atm.getWon_50000());
				}
				
				if(!flag1 || !flag2) {
					System.out.printf("����� ���� 2���� �Է��ϼ���.(����, ������) : ");
					st = new StringTokenizer(sc.nextLine(), " ");
					
					won_10000 = Integer.parseInt(st.nextToken());
					won_50000 = Integer.parseInt(st.nextToken());
				}
			}
			
			long rogCash1 = DB.getBalance(this.accountNumber);
			atm.WithDraw(DB, this.accountNumber, won_50000 * 50000 + won_10000 * 10000);
			long rogCash2 = DB.getBalance(this.accountNumber);
			LOG.putLog(this.accountNumber, flag, rogCash1, rogCash2, "");
			
			atm.setWon_10000(atm.getWon_10000() - won_10000);
			atm.setWon_10000(atm.getWon_50000() - won_50000);
			
			System.out.println("    ����� �Ϸ�Ǿ����ϴ�. �̿����ּż� �����մϴ�.     ");
			System.out.printf("******************************************\n\n\n\n");
		}
		else if(flag == 3) { // �۱�
			System.out.println("******************************************");
			System.out.println("               �۱��� �����Ͽ����ϴ�.            ");
			System.out.printf("�۱��� ���� ���¹�ȣ�� �Է��ϼ���. : ");
			String Remittance_Account = sc.next();
			
			boolean findAccount = DB.checkAccount(Remittance_Account);
			while(!findAccount) {
				System.out.printf("�۱� ���� ���¹�ȣ�� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���. : ");
				Remittance_Account = sc.next();
				findAccount = DB.checkAccount(Remittance_Account);
			}
			
			System.out.printf("�۱��� �ݾ��� �Է����ּ���. : ");
			Long money = sc.nextLong();
			
			while (money > DB.getBalance(this.accountNumber)) {
				System.out.printf("���³��� �ִ� �ݾ��� �����մϴ�. �ٽ� �Է����ּ���. : ");
				money = sc.nextLong();
			}
			
			long rogCash1 = DB.getBalance(this.accountNumber);
			atm.Remittance(DB, Remittance_Account, this.accountNumber, money);
			long rogCash2 = DB.getBalance(this.accountNumber);
			LOG.putLog(this.accountNumber, flag, rogCash1, rogCash2, Remittance_Account);
			
			System.out.println("�۱��� �Ϸ�Ǿ����ϴ�.");
			System.out.println("            �̿����ּż� �����մϴ�.             ");
			System.out.printf("******************************************\n\n\n\n");
		}
		else{ // �ܾ���ȸ
			LOG.putLog(this.accountNumber, flag, 0, 0, "");
			System.out.println("******************************************");
			System.out.println("            �ܾ� ��ȸ�� �����Ͽ����ϴ�.           ");
			System.out.printf("�̸�: %s\n", DB.getName(this.accountNumber));
			System.out.printf("���� ��ȣ: %s\n", this.accountNumber);
			System.out.printf("���� ���� : %s\n", DB.kindAccount(this.accountNumber)? "����� ����" : "���� ���� ����");
			System.out.printf("�ܾ� : %d\n", DB.getBalance(this.accountNumber));
			if(!DB.kindAccount(this.accountNumber)){
				System.out.printf("���� ���� ��¥ : %s\n", DB.getPeriod(this.accountNumber));
			}
			System.out.println("            �̿����ּż� �����մϴ�.             ");
			System.out.printf("******************************************\n\n\n\n");
		}
	}
   
	public boolean login_account(DataBase db, ATM atm, boolean flag) { // ���¹�ȣ �Է�
		if(flag) System.out.print("�α����� ���� ��ȣ�� �Է����ּ���. : ");
		else System.out.print("�������� �ʴ� ���¹�ȣ�Դϴ�. �ٽ� �Է����ּ���. : ");
		
		this.accountNumber = sc.next();
		
		return db.checkAccount(this.accountNumber);
	}
	
	public boolean login_password(DataBase db, ATM atm, boolean flag) { // ��й�ȣ �Է�
		if(flag) System.out.print("��й�ȣ�� �Է����ּ���. : ");
		else System.out.print("��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �Է����ּ���. : ");
		
		this.password = sc.next();
		
		return db.checkPassword(this.accountNumber, this.password);
	}
	
	public int open(DataBase db, ATM atm, TransactionLog LOG, boolean start) {
		System.out.println("******************************************");
		System.out.println("           ������ ã���ּż� �����մϴ�.          ");
		System.out.println("******************************************");
		System.out.println("1: �Ա�, 2: ���: 3: �۱�, 4: �ܰ� ��ȸ 5: ����, 6: �ý��� ����");
		System.out.printf("�Ͻ� ������ �������ּ���. : ");
		int val = sc.nextInt();
		
		if(val == 5 || val == 6) return val;
		
		if (start) {
			boolean flag = this.login_account(db, atm, true);
			while(!flag) {
				flag = this.login_account(db, atm, false);
			}
			
			flag = this.login_password(db, atm, true);
			while(!flag) {
				flag = this.login_password(db, atm, false);
			}
		}
		
		this.Display(db, atm, LOG, this.accountNumber, val);
		
		return val;
	}
	
	public void close() { // �Ϸ�
		System.out.println("******************************************");
		System.out.println("          ������ �̿����ּż� �����մϴ�.          ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	public void showLog(TransactionLog LOG) {
		System.out.printf("\n\nƮ����� �α� ����(Y/N) : ");
		String ans = sc.next();
		if(ans.equals("N")) return;
		
		while(true) {
			System.out.printf("������ �α� ��ȣ�� �Է����ּ���.(0: ����) : ");
			int val = sc.nextInt();
			if(val == 0) break;
			
			System.out.println("�α� ��ȣ " + Integer.toString(val) + ": " + LOG.getLog(val));
		}
		
		System.out.println("******************************************");
		System.out.println("             �ý����� �����մϴ�.              ");
		System.out.println("******************************************");
	}
}