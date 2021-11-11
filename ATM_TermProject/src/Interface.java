import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Interface {
    private String accountNumber, password;
	private Scanner sc = new Scanner(System.in);
	private static Controller controller = new Controller();
	private JFrame application = new JFrame("ATM");
	
	public Interface() {
		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setLayout(null);
		application.setSize(700, 500);
		
		open openGUI = new open();
		application.add(openGUI);
		application.setVisible(true);
	}
	
	public void inaccessible() {
		JOptionPane.showMessageDialog(null, "���⿹�� ������ �Աݰ� �ܾ���ȸ�� �����մϴ�.");
	}
	
	// ==================== �Ա� ======================
	private class inputShowDeposit extends JPanel {
		private JLabel text1;
		private JLabel text2;
		private JTextField won;
		private JButton check;
		private Font font = new Font("�ü�", Font.BOLD, 10);
		private JLabel img;
		private ImageIcon icon, icon2;
		
		public inputShowDeposit() {
			icon = new ImageIcon("images/deposit.png");
			Image ic = icon.getImage();
			Image ic2 = ic.getScaledInstance(600, 300, Image.SCALE_DEFAULT);
			icon2 = new ImageIcon(ic2);
			img = new JLabel(icon2);
			img.setIcon(icon2);
			add(img);
			
			text1 = new JLabel("========================�Ա��� �ݾ��� �Է����ֽð� ����Ű�� ���� �� �Ա� ��ư�� �����ּ���==========================");
			add(text1);
			
			text2 = new JLabel("õ�� ��õ�� ���� ������(�������� ����)");
			text2.setFont(font);
			add(text2);
			
			won = new JTextField(20);
			add(won);
			
			check = new JButton("�Ա�");
			add(check);
			
			TextFieldHandler texthandler = new TextFieldHandler();
			won.addActionListener(texthandler);
			
			ButtonHandler buttonhandler = new ButtonHandler();
			check.addActionListener(buttonhandler);
			
			setBounds(0, 50, 700, 500);
			setSize(new Dimension(700, 400));
			 
			application.add(this);
		}
		private class TextFieldHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == won) {
					StringTokenizer st = new StringTokenizer(event.getActionCommand());
					
					Controller.putMoney[0] = Integer.parseInt(st.nextToken());
					Controller.putMoney[1] = Integer.parseInt(st.nextToken());
					Controller.putMoney[2] = Integer.parseInt(st.nextToken());
					Controller.putMoney[3] = Integer.parseInt(st.nextToken());
				}
			}
		}
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals("�Ա�")) {
					Controller.depositATM();
					setVisible(false);
				}
			}
		}
	}
	
	public void closeShowDeposit() {
		JOptionPane.showMessageDialog(null, "�Ա��� �Ϸ�Ǿ����ϴ�.");
	}
	
	
	// ==================== ��� ======================
	
	public void openShowWithdraw() {
		System.out.println("******************************************");
		System.out.println("               ����� �����Ͽ����ϴ�.            ");
		return;
	}
	
	public int[] inputShowWithdraw(boolean flag) {
		System.out.printf("����� ���� 2���� �Է��ϼ���.(����, ������) : ");
		if(flag) sc.nextLine(); // ���� ����
		StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
		
		int won_10000 = Integer.parseInt(st.nextToken());
		int won_50000 = Integer.parseInt(st.nextToken());
		
		int[] output = {won_10000, won_50000};
		
		return output;
	}
	
	// ���� �޼����� JOptionPane�� �����
	public void errorShowWithdraw(int select, long[] values) {
		if(select == 1) {
			System.out.printf("���¿� �ִ� �ܾ��� �����մϴ�. ���� ���¿� �ִ� �ݾ��� %d�� �Դϴ�.\n", values[0]);
		}
		else{
			System.out.printf("atm�� �ִ� ���� �����մϴ�. ���� atm�� ������ %d�� �����ϰ�, �������� %d�� �����մϴ�.\n", values[0], values[1]);
		}
	}
	
	// �� �κе� JOptionPane�� ��� �Ϸ��ߴٴ� �޼����� ����
	public void closeShowWithdraw() {
		JOptionPane.showMessageDialog(null, "����� �Ϸ�Ǿ����ϴ�.");
	}
	
	
	// ==================== �۱� ======================
	private class inputShowRemittance extends JPanel{
		private JLabel text1;
		private JLabel text2;
		private JLabel text3;
		private JTextField account;
		private JTextField won;
		private JButton check1;
		private JButton check2;
		private JLabel img;
		private ImageIcon icon, icon2;
		private boolean account_flag = false;
		private String accountNumber = "";
		
		public inputShowRemittance() {
			icon = new ImageIcon("images/remittance.png");
			Image ic = icon.getImage();
			Image ic2 = ic.getScaledInstance(600, 300, Image.SCALE_DEFAULT);
			icon2 = new ImageIcon(ic2);
			img = new JLabel(icon2);
			img.setIcon(icon2);
			add(img);
			
			text1 = new JLabel("==================�۱��� ���¿� �۱��� �ݾ��� �Է����ֽð� ���� ����Ű�� ���� �� �۱� ��ư�� �����ּ���====================");
			add(text1);
			
			text2 = new JLabel("�۱��� ���� ��ȣ ");
			add(text2);
			
			account = new JTextField(30);
			add(account);
			
			
			check1 = new JButton("���� Ȯ��");
			add(check1);
			
			text3 = new JLabel("�۱��� �ݾ�");
			add(text3);
			
			won = new JTextField(20);
			add(won);
			
			check2 = new JButton("�۱�");
			add(check2);
			
			TextFieldHandler texthandler = new TextFieldHandler();
			account.addActionListener(texthandler);
			won.addActionListener(texthandler);
			
			ButtonHandler buttonhandler = new ButtonHandler();
			check1.addActionListener(buttonhandler);
			check2.addActionListener(buttonhandler);
			
			setBounds(0, 50, 700, 500);
			setSize(new Dimension(700, 400));
			 
			application.add(this);
		}
		private class TextFieldHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == account) {
					accountNumber = event.getActionCommand();
					Controller.remittance_id = accountNumber;
				}
				else if(event.getSource() == won) {
					Controller.remittance_money = Long.parseLong(event.getActionCommand());
				}
			}
		}
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals("���� Ȯ��")) {
					account_flag = Controller.checkRemittanceAccount(accountNumber);
					if(account_flag) JOptionPane.showMessageDialog(null, "���� Ȯ���� �Ϸ�Ǿ����ϴ�.");
					else JOptionPane.showMessageDialog(null, "�������� ���� �����Դϴ�. �ٽ� �Է��Ͻñ� �ٶ��ϴ�.");
				}
				else if(event.getActionCommand().equals("�۱�")){
					if(account_flag) {
						if(Controller.checkAccountBalance(Controller.remittance_money)) {
							Controller.remittanceATM();
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "���¿� �۱��� �ݾ��� �����մϴ�.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "���� Ȯ���� �ٽ� ���ֽñ� �ٶ��ϴ�.");
					}
				}
			}
		}
	}
	
	public void closeShowRemittance() {
		JOptionPane.showMessageDialog(null, "�۱��� �Ϸ�Ǿ����ϴ�.");
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
		String accountNumber = "";
		if(!flag) accountNumber = JOptionPane.showInputDialog("�α����� ���¹�ȣ�� �Է����ּ���.");
		else accountNumber = JOptionPane.showInputDialog("�������� ���� ���¹�ȣ�Դϴ�. �ٽ� �Է����ּ���: ");
		
		return accountNumber;
	}
	
	public String login_password(boolean flag) { // ��й�ȣ �Է�
		String accountPassword = "";
		if(!flag) accountPassword = JOptionPane.showInputDialog("��й�ȣ�� �Է����ּ���.");
		else accountPassword = JOptionPane.showInputDialog("��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �Է����ּ���.");
		
		return accountPassword;
	}
	
	// ======================== ���� �� ���� ===========================
	
	
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
	
	private class systemClose extends JPanel{
		private JLabel img;
		private ImageIcon icon1, icon2;
		private int selectLog;
		
		public systemClose() {
			icon1 = new ImageIcon("images/systemclose.png");
			Image ic = icon1.getImage();
			Image ic2 = ic.getScaledInstance(600, 400, Image.SCALE_DEFAULT);
			icon2 = new ImageIcon(ic2);
			img = new JLabel(icon2);
			img.setIcon(icon2);
			add(img);
			
			setBounds(0, 50, 700, 500);
			setSize(new Dimension(700, 400));
			
			application.add(this);
			application.repaint();
			application.validate();
			
			if(accessTransactionLog().equals("Y")) {
				while(true) {
					selectLog = Integer.parseInt(selectTransactionLog());
					if(selectLog == 0) break;
					JOptionPane.showMessageDialog(null, Controller.getTransactionLog(selectLog));
				}
			}
			
			Controller.showSystemCloseATM();
		}
	}
	
	public String accessTransactionLog() {
		String access = JOptionPane.showInputDialog("Ʈ����� �α׿� �����Ͻðڽ��ϱ�?(Y:����, N:�ý��� ����)");
		return access;
	}
	
	public String selectTransactionLog() {
		String select = JOptionPane.showInputDialog(null, "�� �� �α׿� �����Ͻðڽ��ϱ�? ���� " + Integer.toString(TransactionLog.count-1)+"���� �αװ� �ֽ��ϴ�.(0: ����)");
		return select;
	}
	
	public void systemCloseMessage() {
		JOptionPane.showMessageDialog(null, "�ý����� �����մϴ�.\n�̿����ּż� �����մϴ�.");
		application.setVisible(false);
	}
	
	private class open extends JPanel {
		private JButton deposit;
		private JButton withdraw;
		private JButton remittance;
		private JButton checkbalance;
		private JButton logout;
		private JButton systemclose;
		
		private String res = "";
		
		public open() {
			deposit = new JButton("�Ա�");
			withdraw = new JButton("���");
			remittance = new JButton("�۱�");
			checkbalance = new JButton("�ܰ� ��ȸ");
			logout = new JButton("�α׾ƿ�");
			systemclose = new JButton("�ý��� ����");
			
			setSize(new Dimension(700, 50));
			setBounds(0, 0, 700, 50);
			
			this.add(deposit);
			this.add(withdraw);
			this.add(remittance);
			this.add(checkbalance);
			this.add(logout);
			this.add(systemclose);
			
			ButtonHandler handler = new ButtonHandler();
			deposit.addActionListener(handler);
			withdraw.addActionListener(handler);
			remittance.addActionListener(handler);
			checkbalance.addActionListener(handler);
			logout.addActionListener(handler);
			systemclose.addActionListener(handler);
			
			
			application.add(this);
		}
			
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				res = event.getActionCommand();
				if(res.equals("�Ա�")) {
					inputShowDeposit isd = new inputShowDeposit();
				}
				else if(res.equals("���")) {
					if(!Controller.kind_account) inaccessible();
					else{
						Controller.withdrawATM(); // �� �κ� �����ؾ���
					}
				}
				else if(res.equals("�۱�")) {
					if(!Controller.kind_account) inaccessible();
					else{
						inputShowRemittance isw = new inputShowRemittance();
					}
				}
				else if(res.equals("�ܰ� ��ȸ")) {
					Controller.checkBalanceATM();
				}
				else if(res.equals("�α׾ƿ�")) {
					JOptionPane.showMessageDialog(null, "�̿����ּż� �����մϴ�.");
					Controller.emptyLogin();
				}
				else if(res.equals("�ý��� ����")) {
					systemClose sc = new systemClose();
					
				}
				application.repaint();
				application.validate();
			}
		}
	}
}

