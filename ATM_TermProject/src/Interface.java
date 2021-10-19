import java.util.*;

public class Interface {
    private String account, password;
	private Scanner sc = new Scanner(System.in);
	
	public void Display(DataBase db, ATM atm, String account, int flag) {
		this.account = account;
		
		if(flag == 1) { // �Ա�
			// temp_cash -> ex) õ�� 1��, ��õ�� 2��, ���� 3��, ������ 4���̸�
			// �Է��� '1 2 3 4'�� ��
			System.out.println("******************************************");
			System.out.println("               �Ա��� �����Ͽ����ϴ�.            ");
			System.out.printf("�Ա��� ���� 4���� �Է��ϼ���.(õ��, ��õ��, ����, ������) : ");
			StringTokenizer st = new StringTokenizer(sc.next(), " ");
				
			int won_1000 = Integer.parseInt(st.nextToken());
			int won_5000 = Integer.parseInt(st.nextToken());
			int won_10000 = Integer.parseInt(st.nextToken());
			int won_50000 = Integer.parseInt(st.nextToken());
			
			atm.WithDraw(db, this.account, won_50000 * 50000 + won_10000 * 10000);
			atm.setWon_1000(atm.getWon_1000() + won_1000);
			atm.setWon_5000(atm.getWon_5000() + won_5000);
			atm.setWon_10000(atm.getWon_10000() + won_10000);
			atm.setWon_50000(atm.getWon_50000() + won_50000);
			
			System.out.println("      �Ա��� �Ϸ�Ǿ����ϴ�. �̿����ּż� �����մϴ�.   ");
			System.out.println("******************************************");
		}
		else if(flag == 2) { // ���
			System.out.println("******************************************");
			System.out.println("               ����� �����Ͽ����ϴ�.            ");
			System.out.printf("����� ���� 2���� �Է��ϼ���.(����, ������) : ");
			StringTokenizer st = new StringTokenizer(sc.next(), " ");
			
			int won_10000 = Integer.parseInt(st.nextToken());
			int won_50000 = Integer.parseInt(st.nextToken());
			
			boolean flag1 = false;
			boolean flag2 = false;
			
			while(flag1 && flag2) {
				if(atm.getWon_10000() >= won_10000 && atm.getWon_50000() >= won_50000) {
					flag1 = true;
					if(atm.getAccountBalance(db, this.account) >= won_10000 * 10000 + won_50000 * 50000) {
						flag2 = true;
					}
					else {
						System.out.printf("���¿� �ִ� �ܾ��� �����մϴ�. ���� ���¿� �ִ� �ݾ��� %d�� �Դϴ�.\n", db.getBalance(this.account));
					}
				}
				else {
					System.out.printf("atm�� �ִ� ���� �����մϴ�. ���� atm�� ������ %d�� �����ϰ�, �������� %d�� �����մϴ�.\n", atm.getWon_10000(), atm.getWon_50000());
				}
			}
			
			atm.Deposit(db, this.account, won_50000 * 50000 + won_10000 * 10000);
			atm.setWon_10000(atm.getWon_10000() - won_10000);
			atm.setWon_10000(atm.getWon_50000() - won_50000);
			
			System.out.println("    ����� �Ϸ�Ǿ����ϴ�. �̿����ּż� �����մϴ�.     ");
			System.out.println("******************************************");
		}
		else if(flag == 3) { // �۱�
			System.out.println("******************************************");
			System.out.println("               �۱��� �����Ͽ����ϴ�.            ");
			System.out.printf("�۱��� ���� ���¹�ȣ�� �Է��ϼ���. : ");
			String Remittance_Account = sc.next();
			
			boolean findAccount = atm.checkAccountId(db, Remittance_Account);
			while(!findAccount) {
				System.out.println("�۱� ���� ���¹�ȣ�� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���. : ");
				Remittance_Account = sc.next();
				findAccount = atm.checkAccountId(db, Remittance_Account);
			}
			
			System.out.printf("�۱��� �ݾ��� �Է����ּ���. : ");
			int money = sc.nextInt();
			while (money <= atm.getAccountBalance(db, this.account)) {
				System.out.printf("���³��� �ִ� �ݾ��� �����մϴ�. �ٽ� �Է����ּ���. : ");
				money = sc.nextInt();
			}
			
			atm.Remittance(db, this.account, Remittance_Account, money);
			System.out.println("�۱��� �Ϸ�Ǿ����ϴ�.");
			System.out.println("            �̿����ּż� �����մϴ�.             ");
			System.out.println("******************************************");
		}
		else{ // �ܾ���ȸ
			System.out.println("******************************************");
			System.out.println("            �ܾ� ��ȸ�� �����Ͽ����ϴ�.           ");
			System.out.printf("�̸�: %s\n", atm.getAccountName(db, this.account));
			System.out.printf("���� ��ȣ: %s\n", this.account);
			System.out.printf("���� ���� : %s\n", atm.getAccountKind(db, this.account));
			System.out.printf("�ܾ� : %d\n", atm.getAccountBalance(db, this.account));
			if(atm.getAccountKind(db, this.account).equals("���� ���� ����")){
				System.out.printf("���� ���� ��¥ : %s\n", atm.getAccountPeriod(db, this.account));
			}
			System.out.println("            �̿����ּż� �����մϴ�.             ");
			System.out.println("******************************************");
		}
	}
   
	public boolean login_account(DataBase db, ATM atm, boolean flag) { // ���¹�ȣ �Է�
		
		if(flag) System.out.print("�α����� ���� ��ȣ�� �Է����ּ���. : ");
		else System.out.print("�������� �ʴ� ���¹�ȣ�Դϴ�. �ٽ� �Է����ּ���. : ");
		
		this.account = sc.next();
		
		return atm.checkAccountId(db, this.account);
	}
	
	public boolean login_password(DataBase db, ATM atm, boolean flag) { // ��й�ȣ �Է�
		if(flag) System.out.print("��й�ȣ�� �Է��ּ���. : ");
		else System.out.print("��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �Է����ּ���. : ");
		
		this.password = sc.next();
		return atm.checkAccountPassword(db, this.account, this.password);
	}
	
	public int open(DataBase db, ATM atm) {
		System.out.println("******************************************");
		System.out.println("           ������ ã���ּż� �����մϴ�.          ");
		System.out.println("******************************************");
		System.out.println("1: �Ա�, 2: ���: 3: �۱�, 4: �ܰ� ��ȸ 5: ����, 6: �ý��� ����");
		System.out.printf("�Ͻ� ������ �������ּ���. : ");
		int val = sc.nextInt();
		
		if(val == 5 || val == 6) return val;
		
		boolean flag = this.login_account(db, atm, true);
		while(flag) {
			flag = this.login_account(db, atm, false);
		}
		
		flag = this.login_password(db, atm, true);
		while(flag) {
			flag = this.login_password(db, atm, false);
		}
		
		this.Display(db, atm, this.account, val);
		
		return val;
	}
	
	public void close() { // �Ϸ�
		System.out.println("******************************************");
		System.out.println("          ������ �̿����ּż� �����մϴ�.          ");
		System.out.printf("******************************************\n\n\n\n");
	}
}