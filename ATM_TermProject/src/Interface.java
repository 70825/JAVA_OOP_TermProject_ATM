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
		JOptionPane.showMessageDialog(null, "���� ���� ������ �Աݰ� �ܾ���ȸ�� �����մϴ�.", "���� ���� ���� ���� �Ұ�", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// ==================== �Ա� ======================
	private class inputShowDeposit extends JPanel {
		private JLabel text1;
		private JLabel text2;
		private JTextField won;
		private JButton check;
		private JButton cancel;
		private Font font = new Font("�ü�", Font.BOLD, 10);
		private JLabel img;
		private ImageIcon icon, icon2;
		
		public inputShowDeposit() {
			icon = new ImageIcon("images/deposit.png");
			Image ic = icon.getImage();
			Image ic2 = ic.getScaledInstance(700, 300, Image.SCALE_DEFAULT);
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
			
			cancel = new JButton("���");
			add(cancel);
			
			TextFieldHandler texthandler = new TextFieldHandler();
			won.addActionListener(texthandler);
			
			ButtonHandler buttonhandler = new ButtonHandler();
			check.addActionListener(buttonhandler);
			cancel.addActionListener(buttonhandler);
			
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
				else if(event.getActionCommand().equals("���")) {
					JOptionPane.showMessageDialog(null, "����Ͽ����ϴ�.", "���", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		}
	}
	
	public void closeShowDeposit() {
		JOptionPane.showMessageDialog(null, "�Ա��� �Ϸ�Ǿ����ϴ�.", "�Ա� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	// ==================== ��� ======================
	public class inputShowWithdraw extends JPanel {
		private JLabel text1;
		private JLabel text2;
		private JTextField Wwon;
		private JButton check;
		private JButton cancel;
		private Font font = new Font("�ü�", Font.BOLD, 10);
		private JLabel img;
		private ImageIcon icon, icon2;
		
		public inputShowWithdraw() {
			icon = new ImageIcon("./images/withdraw.png");
			Image ic = icon.getImage();
			Image ic2 = ic.getScaledInstance(700, 300, Image.SCALE_DEFAULT);
			icon2 = new ImageIcon(ic2);
			img = new JLabel(icon2);
			img.setIcon(icon2);
			add(img);
			
			text1 = new JLabel("========================����� �ݾ��� �Է����ֽð� ����Ű�� ���� �� �Ա� ��ư�� �����ּ���==========================");
			add(text1);
			
			text2 = new JLabel("���� ������(�������� ����)");
			text2.setFont(font);
			add(text2);
			
			Wwon = new JTextField(20);
			add(Wwon);
			
			check = new JButton("���");
			add(check);
			
			cancel = new JButton("���");
			add(cancel);
			
			WTextFieldHandler Wtexthandler = new WTextFieldHandler();
			Wwon.addActionListener(Wtexthandler);
			
			ButtonHandler buttonhandler = new ButtonHandler();
			check.addActionListener(buttonhandler);
			cancel.addActionListener(buttonhandler);
			
			setBounds(0, 50, 700, 500);
			setSize(new Dimension(700, 400));
			 
			application.add(this);
		}
		
		private class WTextFieldHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == Wwon) {
					StringTokenizer st = new StringTokenizer(event.getActionCommand());
					Controller.outMoney[0] = Integer.parseInt(st.nextToken());
					Controller.outMoney[1] = Integer.parseInt(st.nextToken());
				}
			}
		}
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals("���")) {
					int total = Controller.outMoney[0] * 10000 + Controller.outMoney[1] * 50000;
					if(!Controller.checkAccountBalance(total)) {
						long[] values = {Controller.getAccountBalance()};
						errorShowWithdraw(1, values);
					}
					else if(!Controller.checkATMNumber()) {
						long[] values = Controller.getATMNumbers();
						errorShowWithdraw(2, values);
					}
					else {
						Controller.withdrawATM();
						setVisible(false);
					}
				}
				else if(event.getActionCommand().equals("���")){
					JOptionPane.showMessageDialog(null, "����Ͽ����ϴ�.", "���", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		}
	}
	
	public void errorShowWithdraw(int select, long[] values) {
		if(select == 1) {
			String ERROR1 = "���¿� �ִ� �ܾ��� �����մϴ�. ���� ���¿� �ִ� �ݾ��� " + values[0] + "�� �Դϴ�.\n";
			JOptionPane.showMessageDialog(null, ERROR1, "ERRROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			
		}
		
		else if(select == 2){
			String ERROR2 = "atm�� �ִ� ���� �����մϴ�. ���� atm�� ������ " + values[0] + "�� �����ϰ�, �������� " +values[1] + "�� �����մϴ�.\n";
			JOptionPane.showMessageDialog(null, ERROR2, "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void closeShowWithdraw() {
		JOptionPane.showMessageDialog(null, "����� �Ϸ�Ǿ����ϴ�.", "��� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
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
		private JButton remittance;
		private JButton cancel;
		private JLabel img;
		private ImageIcon icon, icon2;
		private boolean account_flag = false;
		private boolean money_flag = false;
		private String accountNumber = "";
		
		public inputShowRemittance() {
			setLayout(null);
			
			icon = new ImageIcon("images/remittance.png");
			Image ic = icon.getImage();
			Image ic2 = ic.getScaledInstance(700, 300, Image.SCALE_DEFAULT);
			icon2 = new ImageIcon(ic2);
			img = new JLabel(icon2);
			img.setBounds(0, 0, 700, 300);
			img.setIcon(icon2);
			add(img);
			
			text1 = new JLabel("=============�۱��� ���¿� �۱��� �ݾ��� �Է����ֽð� ���� ����Ű�� ���� �� �۱� ��ư�� �����ּ���=============");
			text1.setBounds(0, 302, 700, 10);
			add(text1);
			
			text2 = new JLabel(" �۱��� ���� ��ȣ�� �Է��ϼ��� ");
			text2.setBounds(20, 330, 230, 20);
			add(text2);
			
			account = new JTextField(35);
			account.setBounds(220, 330, 200, 20);
			add(account);
			
			
			check1 = new JButton("  ���� Ȯ��  ");
			check1.setBounds(430, 330, 100, 20);
			add(check1);
			
			text3 = new JLabel(" �۱��� �ݾ��� �Է��ϼ���  ");
			text3.setBounds(20, 360, 230, 20);
			add(text3);
			
			won = new JTextField(35);
			won.setBounds(220, 360, 200, 20);
			add(won);
			
			check2 = new JButton("  �ݾ� Ȯ��  ");
			check2.setBounds(430, 360, 100, 20);
			add(check2);
			
			remittance = new JButton("�۱�");
			remittance.setBounds(540, 330, 60, 50);
			add(remittance);
			
			cancel = new JButton("���");
			cancel.setBounds(610, 330, 60, 50);
			add(cancel);
			
			TextFieldHandler texthandler = new TextFieldHandler();
			account.addActionListener(texthandler);
			won.addActionListener(texthandler);
			
			ButtonHandler buttonhandler = new ButtonHandler();
			check1.addActionListener(buttonhandler);
			check2.addActionListener(buttonhandler);
			remittance.addActionListener(buttonhandler);
			cancel.addActionListener(buttonhandler);
			
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
				if(event.getActionCommand().equals("  ���� Ȯ��  ")) {
					account_flag = Controller.checkRemittanceAccount(accountNumber);
					if(account_flag) JOptionPane.showMessageDialog(null, "���� Ȯ���� �Ϸ�Ǿ����ϴ�.", "���� Ȯ�� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
					else JOptionPane.showMessageDialog(null, "�������� ���� �����Դϴ�. �ٽ� �Է��Ͻñ� �ٶ��ϴ�.", "���� Ȯ�� ����", JOptionPane.WARNING_MESSAGE);
				}
				else if(event.getActionCommand().equals("  �ݾ� Ȯ��  ")){
					if(account_flag) {
						if(Controller.checkAccountBalance(Controller.remittance_money)) {
							JOptionPane.showMessageDialog(null, "�ݾ� Ȯ���� �Ϸ�Ǿ����ϴ�.", "�ݾ� Ȯ�� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
							money_flag = true;
						}
						else JOptionPane.showMessageDialog(null, "���¿� �۱��� �ݾ��� �����մϴ�.", "�ݾ� Ȯ�� ����", JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "���� Ȯ���� �ٽ� ���ֽñ� �ٶ��ϴ�.", "���� Ȯ�� ����", JOptionPane.WARNING_MESSAGE);
					}
				}
				else if(event.getActionCommand().equals("�۱�")) {
					if(account_flag && money_flag) {
						Controller.remittanceATM();
						setVisible(false);
					}
					else if(!account_flag) JOptionPane.showMessageDialog(null, "���� Ȯ���� �ٽ� ���ֽñ� �ٶ��ϴ�.", "�ݾ� Ȯ�� ����", JOptionPane.WARNING_MESSAGE);
					else if(!money_flag) JOptionPane.showMessageDialog(null, "�ݾ��� �ٽ� Ȯ�����ֽñ� �ٶ��ϴ�.", "���� Ȯ�� ����", JOptionPane.WARNING_MESSAGE);
				}
				else if(event.getActionCommand().equals("���")) {
					JOptionPane.showMessageDialog(null, "����Ͽ����ϴ�.", "���", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		}
	}
	
	public void closeShowRemittance() {
		JOptionPane.showMessageDialog(null, "�۱��� �Ϸ�Ǿ����ϴ�.", "�۱� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// ==================== �ܰ� ��ȸ ======================
	public class showCheckBalance extends JPanel{
		private JLabel textname;
		private JLabel textaccount;
		private JLabel textaccountkind;
		private JLabel textbalance;
		private JLabel textd_day;
		private JButton check;
		
		private int x = 250, x_i = 200, y = 0, y_i = 30;
	
		public void showCheckBalance(String name, String id, boolean flag_kind, long balance, String period){
			setLayout(null);
			
			textname = new JLabel("�̸�: " +name);
			textname.setBounds(x, y, x_i, y_i);
			add(textname);

			y = y + y_i;
			
			textaccount = new JLabel("���� ��ȣ: "+ id);
			textaccount.setBounds(x, y, x_i, y_i);
			add(textaccount);
			
			
			if(flag_kind == true) {
				y = y + y_i;
				textaccountkind = new JLabel("���� ���� : ����� ����");
				textaccountkind.setBounds(x, y, x_i, y_i);
				add(textaccountkind);
			}
			
			else {
				y = y + y_i;
				textaccountkind = new JLabel("���� ���� : ���� ���� ����");
				textaccountkind.setBounds(x, y, x_i, y_i);
				add(textaccountkind);
		
			}
			
			y = y + y_i;
			textbalance = new JLabel("�ܾ� : "+balance);
			textbalance.setBounds(x, y, x_i, y_i);
			add(textbalance);
			
			if(!flag_kind){
				y = y + y_i;
				textd_day = new JLabel("���� ���� ��¥ :" + period);
				textd_day.setBounds(x, y, x_i, y_i);
				add(textd_day);
			}
			
			y = y + y_i;
			check = new JButton("Ȯ��");
			check.setBounds(x, y, x_i, y_i);
			add(check);
			
			this.setBounds(0, 50, 700, 500);
			this.setSize(new Dimension(700, 400));
			 
			application.add(this);
			
			ButtonHandler buttonhandler = new ButtonHandler();
			check.addActionListener(buttonhandler);
		}
		
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals("Ȯ��")) {
					setVisible(false);
				}
			}
		}
	}
	
	
	// ================= �α��� ======================
   
	public String login_account(boolean flag) { // ���¹�ȣ �Է�
		String accountNumber = "";
		if(!flag) accountNumber = JOptionPane.showInputDialog(null, "�α����� ���¹�ȣ�� �Է����ּ���.", "���¹�ȣ �Է�", JOptionPane.QUESTION_MESSAGE);
		else accountNumber = JOptionPane.showInputDialog(null, "�������� ���� ���¹�ȣ�Դϴ�. �ٽ� �Է����ּ���.", "���¹�ȣ ����", JOptionPane.WARNING_MESSAGE);
		
		return accountNumber;
	}
	
	public String login_password(boolean flag) { // ��й�ȣ �Է�
		String accountPassword = "";
		if(!flag) accountPassword = JOptionPane.showInputDialog(null, "��й�ȣ�� �Է����ּ���.", "��й�ȣ �Է�", JOptionPane.QUESTION_MESSAGE);
		else accountPassword = JOptionPane.showInputDialog(null, "��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �Է����ּ���.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
		
		return accountPassword;
	}
	
	
	// ===================== Ʈ����� �α� ���� �� �ý��� ���� ==============================
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
					JOptionPane.showMessageDialog(null, Controller.getTransactionLog(selectLog), Integer.toString(selectLog) + "�� �α� ���", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			Controller.showSystemCloseATM();
		}
	}
	
	public String accessTransactionLog() {
		String access = JOptionPane.showInputDialog(null, "Ʈ����� �α׿� �����Ͻðڽ��ϱ�?(Y:����, N:�ý��� ����)", "Ʈ����� �α� ����", JOptionPane.QUESTION_MESSAGE);
		return access;
	}
	
	public String selectTransactionLog() {
		String select = JOptionPane.showInputDialog(null, "�� �� �α׿� �����Ͻðڽ��ϱ�? ���� " + Integer.toString(TransactionLog.count-1)+"���� �αװ� �ֽ��ϴ�.(0: ����)", 
				"�α� ����", JOptionPane.INFORMATION_MESSAGE);
		return select;
	}
	
	public void systemCloseMessage() {
		JOptionPane.showMessageDialog(null, "�ý����� �����մϴ�.\n�̿����ּż� �����մϴ�.", "�ý��� ����", JOptionPane.WARNING_MESSAGE);
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
						inputShowWithdraw isw = new inputShowWithdraw();
					}
				}
				else if(res.equals("�۱�")) {
					if(!Controller.kind_account) inaccessible();
					else{
						inputShowRemittance isr = new inputShowRemittance();
					}
				}
				else if(res.equals("�ܰ� ��ȸ")) {
					Controller.checkBalanceATM();
				}
				else if(res.equals("�α׾ƿ�")) {
					JOptionPane.showMessageDialog(null, "�̿����ּż� �����մϴ�.", "�α׾ƿ�", JOptionPane.INFORMATION_MESSAGE);
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

