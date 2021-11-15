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
		JOptionPane.showMessageDialog(null, "정기 예금 통장은 입금과 잔액조회만 가능합니다.", "정기 예금 통장 접속 불가", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// ==================== 입금 ======================
	private class inputShowDeposit extends JPanel {
		private JLabel text1;
		private JLabel text2;
		private JTextField won;
		private JButton check;
		private JButton cancel;
		private Font font = new Font("궁서", Font.BOLD, 10);
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
			
			text1 = new JLabel("========================입금할 금액을 입력해주시고 엔터키를 누른 후 입금 버튼을 눌러주세요==========================");
			add(text1);
			
			text2 = new JLabel("천원 오천원 만원 오만원(공백으로 구분)");
			text2.setFont(font);
			add(text2);
			
			won = new JTextField(20);
			add(won);
			
			check = new JButton("입금");
			add(check);
			
			cancel = new JButton("취소");
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
				if(event.getActionCommand().equals("입금")) {
					Controller.depositATM();
					setVisible(false);
				}
				else if(event.getActionCommand().equals("취소")) {
					JOptionPane.showMessageDialog(null, "취소하였습니다.", "취소", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		}
	}
	
	public void closeShowDeposit() {
		JOptionPane.showMessageDialog(null, "입금이 완료되었습니다.", "입금 완료", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	// ==================== 출금 ======================
	public class inputShowWithdraw extends JPanel {
		private JLabel text1;
		private JLabel text2;
		private JTextField Wwon;
		private JButton check;
		private JButton cancel;
		private Font font = new Font("궁서", Font.BOLD, 10);
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
			
			text1 = new JLabel("========================출금할 금액을 입력해주시고 엔터키를 누른 후 입금 버튼을 눌러주세요==========================");
			add(text1);
			
			text2 = new JLabel("만원 오만원(공백으로 구분)");
			text2.setFont(font);
			add(text2);
			
			Wwon = new JTextField(20);
			add(Wwon);
			
			check = new JButton("출금");
			add(check);
			
			cancel = new JButton("취소");
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
				if(event.getActionCommand().equals("출금")) {
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
				else if(event.getActionCommand().equals("취소")){
					JOptionPane.showMessageDialog(null, "취소하였습니다.", "취소", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		}
	}
	
	public void errorShowWithdraw(int select, long[] values) {
		if(select == 1) {
			String ERROR1 = "계좌에 있는 잔액이 부족합니다. 현재 계좌에 있는 금액은 " + values[0] + "원 입니다.\n";
			JOptionPane.showMessageDialog(null, ERROR1, "ERRROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			
		}
		
		else if(select == 2){
			String ERROR2 = "atm에 있는 지폐가 부족합니다. 현재 atm에 만원은 " + values[0] + "개 존재하고, 오만원은 " +values[1] + "개 존재합니다.\n";
			JOptionPane.showMessageDialog(null, ERROR2, "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void closeShowWithdraw() {
		JOptionPane.showMessageDialog(null, "출금이 완료되었습니다.", "출금 완료", JOptionPane.INFORMATION_MESSAGE);
	}
	// ==================== 송금 ======================
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
			
			text1 = new JLabel("=============송금할 계좌와 송금할 금액을 입력해주시고 각각 엔터키를 누른 후 송금 버튼을 눌러주세요=============");
			text1.setBounds(0, 302, 700, 10);
			add(text1);
			
			text2 = new JLabel(" 송금할 계좌 번호를 입력하세요 ");
			text2.setBounds(20, 330, 230, 20);
			add(text2);
			
			account = new JTextField(35);
			account.setBounds(220, 330, 200, 20);
			add(account);
			
			
			check1 = new JButton("  계좌 확인  ");
			check1.setBounds(430, 330, 100, 20);
			add(check1);
			
			text3 = new JLabel(" 송금할 금액을 입력하세요  ");
			text3.setBounds(20, 360, 230, 20);
			add(text3);
			
			won = new JTextField(35);
			won.setBounds(220, 360, 200, 20);
			add(won);
			
			check2 = new JButton("  금액 확인  ");
			check2.setBounds(430, 360, 100, 20);
			add(check2);
			
			remittance = new JButton("송금");
			remittance.setBounds(540, 330, 60, 50);
			add(remittance);
			
			cancel = new JButton("취소");
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
				if(event.getActionCommand().equals("  계좌 확인  ")) {
					account_flag = Controller.checkRemittanceAccount(accountNumber);
					if(account_flag) JOptionPane.showMessageDialog(null, "계좌 확인이 완료되었습니다.", "계좌 확인 완료", JOptionPane.INFORMATION_MESSAGE);
					else JOptionPane.showMessageDialog(null, "존재하지 않은 계좌입니다. 다시 입력하시길 바랍니다.", "계좌 확인 오류", JOptionPane.WARNING_MESSAGE);
				}
				else if(event.getActionCommand().equals("  금액 확인  ")){
					if(account_flag) {
						if(Controller.checkAccountBalance(Controller.remittance_money)) {
							JOptionPane.showMessageDialog(null, "금액 확인이 완료되었습니다.", "금액 확인 완료", JOptionPane.INFORMATION_MESSAGE);
							money_flag = true;
						}
						else JOptionPane.showMessageDialog(null, "계좌에 송금할 금액이 부족합니다.", "금액 확인 오류", JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "계좌 확인을 다시 해주시길 바랍니다.", "계좌 확인 오류", JOptionPane.WARNING_MESSAGE);
					}
				}
				else if(event.getActionCommand().equals("송금")) {
					if(account_flag && money_flag) {
						Controller.remittanceATM();
						setVisible(false);
					}
					else if(!account_flag) JOptionPane.showMessageDialog(null, "계좌 확인을 다시 해주시길 바랍니다.", "금액 확인 오류", JOptionPane.WARNING_MESSAGE);
					else if(!money_flag) JOptionPane.showMessageDialog(null, "금액을 다시 확인해주시길 바랍니다.", "계좌 확인 오류", JOptionPane.WARNING_MESSAGE);
				}
				else if(event.getActionCommand().equals("취소")) {
					JOptionPane.showMessageDialog(null, "취소하였습니다.", "취소", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		}
	}
	
	public void closeShowRemittance() {
		JOptionPane.showMessageDialog(null, "송금이 완료되었습니다.", "송금 완료", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// ==================== 잔고 조회 ======================
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
			
			textname = new JLabel("이름: " +name);
			textname.setBounds(x, y, x_i, y_i);
			add(textname);

			y = y + y_i;
			
			textaccount = new JLabel("계좌 번호: "+ id);
			textaccount.setBounds(x, y, x_i, y_i);
			add(textaccount);
			
			
			if(flag_kind == true) {
				y = y + y_i;
				textaccountkind = new JLabel("통장 종류 : 입출금 통장");
				textaccountkind.setBounds(x, y, x_i, y_i);
				add(textaccountkind);
			}
			
			else {
				y = y + y_i;
				textaccountkind = new JLabel("통장 종류 : 정기 예금 통장");
				textaccountkind.setBounds(x, y, x_i, y_i);
				add(textaccountkind);
		
			}
			
			y = y + y_i;
			textbalance = new JLabel("잔액 : "+balance);
			textbalance.setBounds(x, y, x_i, y_i);
			add(textbalance);
			
			if(!flag_kind){
				y = y + y_i;
				textd_day = new JLabel("예금 만기 날짜 :" + period);
				textd_day.setBounds(x, y, x_i, y_i);
				add(textd_day);
			}
			
			y = y + y_i;
			check = new JButton("확인");
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
				if(event.getActionCommand().equals("확인")) {
					setVisible(false);
				}
			}
		}
	}
	
	
	// ================= 로그인 ======================
   
	public String login_account(boolean flag) { // 계좌번호 입력
		String accountNumber = "";
		if(!flag) accountNumber = JOptionPane.showInputDialog(null, "로그인할 계좌번호를 입력해주세요.", "계좌번호 입력", JOptionPane.QUESTION_MESSAGE);
		else accountNumber = JOptionPane.showInputDialog(null, "존재하지 않은 계좌번호입니다. 다시 입력해주세요.", "계좌번호 오류", JOptionPane.WARNING_MESSAGE);
		
		return accountNumber;
	}
	
	public String login_password(boolean flag) { // 비밀번호 입력
		String accountPassword = "";
		if(!flag) accountPassword = JOptionPane.showInputDialog(null, "비밀번호를 입력해주세요.", "비밀번호 입력", JOptionPane.QUESTION_MESSAGE);
		else accountPassword = JOptionPane.showInputDialog(null, "비밀번호가 틀렸습니다. 다시 입력해주세요.", "비밀번호 오류", JOptionPane.WARNING_MESSAGE);
		
		return accountPassword;
	}
	
	
	// ===================== 트랜잭션 로그 접근 및 시스템 종료 ==============================
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
					JOptionPane.showMessageDialog(null, Controller.getTransactionLog(selectLog), Integer.toString(selectLog) + "번 로그 결과", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			Controller.showSystemCloseATM();
		}
	}
	
	public String accessTransactionLog() {
		String access = JOptionPane.showInputDialog(null, "트랜잭션 로그에 접근하시겠습니까?(Y:접근, N:시스템 종료)", "트랜잭션 로그 접근", JOptionPane.QUESTION_MESSAGE);
		return access;
	}
	
	public String selectTransactionLog() {
		String select = JOptionPane.showInputDialog(null, "몇 번 로그에 접속하시겠습니까? 현재 " + Integer.toString(TransactionLog.count-1)+"개의 로그가 있습니다.(0: 종료)", 
				"로그 접근", JOptionPane.INFORMATION_MESSAGE);
		return select;
	}
	
	public void systemCloseMessage() {
		JOptionPane.showMessageDialog(null, "시스템을 종료합니다.\n이용해주셔서 감사합니다.", "시스템 종료", JOptionPane.WARNING_MESSAGE);
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
			deposit = new JButton("입금");
			withdraw = new JButton("출금");
			remittance = new JButton("송금");
			checkbalance = new JButton("잔고 조회");
			logout = new JButton("로그아웃");
			systemclose = new JButton("시스템 종료");
			
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
				if(res.equals("입금")) {
					inputShowDeposit isd = new inputShowDeposit();
				}
				else if(res.equals("출금")) {
					if(!Controller.kind_account) inaccessible();
					else{
						inputShowWithdraw isw = new inputShowWithdraw();
					}
				}
				else if(res.equals("송금")) {
					if(!Controller.kind_account) inaccessible();
					else{
						inputShowRemittance isr = new inputShowRemittance();
					}
				}
				else if(res.equals("잔고 조회")) {
					Controller.checkBalanceATM();
				}
				else if(res.equals("로그아웃")) {
					JOptionPane.showMessageDialog(null, "이용해주셔서 감사합니다.", "로그아웃", JOptionPane.INFORMATION_MESSAGE);
					Controller.emptyLogin();
				}
				else if(res.equals("시스템 종료")) {
					systemClose sc = new systemClose();
					
				}
				application.repaint();
				application.validate();
			}
		}
	}
}

