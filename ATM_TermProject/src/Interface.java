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
		JOptionPane.showMessageDialog(null, "정기예금 통장은 입금과 잔액조회만 가능합니다.");
	}
	
	// ==================== 입금 ======================
	private class inputShowDeposit extends JPanel {
		private JLabel text1;
		private JLabel text2;
		private JTextField won;
		private JButton check;
		private Font font = new Font("궁서", Font.BOLD, 10);
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
			
			text1 = new JLabel("========================입금할 금액을 입력해주시고 엔터키를 누른 후 입금 버튼을 눌러주세요==========================");
			add(text1);
			
			text2 = new JLabel("천원 오천원 만원 오만원(공백으로 구분)");
			text2.setFont(font);
			add(text2);
			
			won = new JTextField(20);
			add(won);
			
			check = new JButton("입금");
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
				if(event.getActionCommand().equals("입금")) {
					Controller.depositATM();
					setVisible(false);
				}
			}
		}
	}
	
	public void closeShowDeposit() {
		JOptionPane.showMessageDialog(null, "입금이 완료되었습니다.");
	}
	
	
	// ==================== 출금 ======================
	
	public void openShowWithdraw() {
		System.out.println("******************************************");
		System.out.println("               출금을 선택하였습니다.            ");
		return;
	}
	
	public int[] inputShowWithdraw(boolean flag) {
		System.out.printf("출금할 지폐 2개를 입력하세요.(만원, 오만원) : ");
		if(flag) sc.nextLine(); // 버퍼 비우기
		StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
		
		int won_10000 = Integer.parseInt(st.nextToken());
		int won_50000 = Integer.parseInt(st.nextToken());
		
		int[] output = {won_10000, won_50000};
		
		return output;
	}
	
	// 에러 메세지는 JOptionPane로 만들기
	public void errorShowWithdraw(int select, long[] values) {
		if(select == 1) {
			System.out.printf("계좌에 있는 잔액이 부족합니다. 현재 계좌에 있는 금액은 %d원 입니다.\n", values[0]);
		}
		else{
			System.out.printf("atm에 있는 지폐가 부족합니다. 현재 atm에 만원은 %d개 존재하고, 오만원은 %d개 존재합니다.\n", values[0], values[1]);
		}
	}
	
	// 이 부분도 JOptionPane로 출금 완료했다는 메세지를 띄우기
	public void closeShowWithdraw() {
		JOptionPane.showMessageDialog(null, "출금이 완료되었습니다.");
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
			
			text1 = new JLabel("==================송금할 계좌와 송금할 금액을 입력해주시고 각각 엔터키를 누른 후 송금 버튼을 눌러주세요====================");
			add(text1);
			
			text2 = new JLabel("송금할 계좌 번호 ");
			add(text2);
			
			account = new JTextField(30);
			add(account);
			
			
			check1 = new JButton("계좌 확인");
			add(check1);
			
			text3 = new JLabel("송금할 금액");
			add(text3);
			
			won = new JTextField(20);
			add(won);
			
			check2 = new JButton("송금");
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
				if(event.getActionCommand().equals("계좌 확인")) {
					account_flag = Controller.checkRemittanceAccount(accountNumber);
					if(account_flag) JOptionPane.showMessageDialog(null, "계좌 확인이 완료되었습니다.");
					else JOptionPane.showMessageDialog(null, "존재하지 않은 계좌입니다. 다시 입력하시길 바랍니다.");
				}
				else if(event.getActionCommand().equals("송금")){
					if(account_flag) {
						if(Controller.checkAccountBalance(Controller.remittance_money)) {
							Controller.remittanceATM();
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "계좌에 송금할 금액이 부족합니다.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "계좌 확인을 다시 해주시길 바랍니다.");
					}
				}
			}
		}
	}
	
	public void closeShowRemittance() {
		JOptionPane.showMessageDialog(null, "송금이 완료되었습니다.");
	}
	
	// ==================== 잔고 조회 ======================
	public void openShowCheckBalance() {
		System.out.println("******************************************");
		System.out.println("            잔액 조회를 선택하였습니다.           ");
	}
	
	public void showCheckBalance(String name, String id, boolean flag_kind, long balance, String period) {
		
		System.out.printf("이름: %s\n", name);
		System.out.printf("계좌 번호: %s\n", id);
		System.out.printf("통장 종류 : %s\n", flag_kind? "입출금 통장" : "정기 예금 통장");
		System.out.printf("잔액 : %d\n", balance);
		if(!flag_kind){
			System.out.printf("예금 만기 날짜 : %s\n", period);
		}
		
	}
	
	public void closeShowCheckBalance() {
		System.out.println("            이용해주셔서 감사합니다.             ");
		System.out.printf("******************************************\n\n\n\n");
	}
	
	
	// ================= 로그인 ======================
   
	public String login_account(boolean flag) { // 계좌번호 입력
		String accountNumber = "";
		if(!flag) accountNumber = JOptionPane.showInputDialog("로그인할 계좌번호를 입력해주세요.");
		else accountNumber = JOptionPane.showInputDialog("존재하지 않은 계좌번호입니다. 다시 입력해주세요: ");
		
		return accountNumber;
	}
	
	public String login_password(boolean flag) { // 비밀번호 입력
		String accountPassword = "";
		if(!flag) accountPassword = JOptionPane.showInputDialog("비밀번호를 입력해주세요.");
		else accountPassword = JOptionPane.showInputDialog("비밀번호가 틀렸습니다. 다시 입력해주세요.");
		
		return accountPassword;
	}
	
	// ======================== 시작 및 종료 ===========================
	
	
	// ===================== 트랜잭션 로그 접근 및 시스템 종료 ==============================
	
	public String openShowLog() {
		System.out.printf("\n트랜잭션 로그 접근(Y/N) : ");
		String ans = sc.next();
		
		return ans;
	}
	
	public int showSelectLog() {
		System.out.printf("접근할 로그 번호를 입력해주세요.(0: 종료) : ");
		int val = sc.nextInt();
		
		return val;
	}
	
	public void showLog(int val, String Log) {
		System.out.println("로그 번호 " + Integer.toString(val) + ": " + Log);
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
		String access = JOptionPane.showInputDialog("트랜잭션 로그에 접근하시겠습니까?(Y:접근, N:시스템 종료)");
		return access;
	}
	
	public String selectTransactionLog() {
		String select = JOptionPane.showInputDialog(null, "몇 번 로그에 접속하시겠습니까? 현재 " + Integer.toString(TransactionLog.count-1)+"개의 로그가 있습니다.(0: 종료)");
		return select;
	}
	
	public void systemCloseMessage() {
		JOptionPane.showMessageDialog(null, "시스템을 종료합니다.\n이용해주셔서 감사합니다.");
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
						Controller.withdrawATM(); // 이 부분 수정해야함
					}
				}
				else if(res.equals("송금")) {
					if(!Controller.kind_account) inaccessible();
					else{
						inputShowRemittance isw = new inputShowRemittance();
					}
				}
				else if(res.equals("잔고 조회")) {
					Controller.checkBalanceATM();
				}
				else if(res.equals("로그아웃")) {
					JOptionPane.showMessageDialog(null, "이용해주셔서 감사합니다.");
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

