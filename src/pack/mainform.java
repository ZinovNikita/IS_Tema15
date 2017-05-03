package pack;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class mainform extends JFrame {
	private static final long serialVersionUID = 1L;
	private export rtf = new export();
	private static dbase dbo = new dbase();

	public JMenuBar mainMenu = new JMenuBar();

	public Label label1 = new Label("������ ���������");
	public Choice choice1 = new Choice();// ������ ���������
	private int[] hiddenchoice1 = new int[0];

	public Label label2 = new Label("������ �������");
	public Choice choice2 = new Choice();// ������ �������
	private int[] hiddenchoice2 = new int[0];

	public Label label3 = new Label("������ ��������");
	public List choice3 = new List();// ������ ��������
	private int[] hiddenchoice3 = new int[0];

	public Label label4 = new Label("����� �������");
	public TextArea textArea1 = new TextArea();
	public Button button4 = new Button("���������");
	public Button button5 = new Button("������");
	private boolean button4_actionType=false;

	public JDialog helpDialog=new JDialog(this);

	public mainform() {
		super("������� �����");
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 50, 800, 480);
		setExtendedState(MAXIMIZED_BOTH);
		menuCreate();
		label1.setBounds(10, 10, 119, 24);
		getContentPane().add(label1);

		choice1.setBounds(135, 12, 307, 22);
		fillchoice1();
		choice1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// ����� ����������, �������� ������ �������
				boolean access = choice1.getSelectedIndex() > 0;
				mainMenu.getMenu(0).getItem(1).setEnabled(access);
				mainMenu.getMenu(1).getItem(1).setEnabled(access);
				mainMenu.getMenu(2).getItem(0).setEnabled(access);
				mainMenu.getMenu(3).getItem(0).setEnabled(access);
				choice2.setEnabled(access);
				choice3.removeAll();
				choice3.setEnabled(false);
				if (access)
					fillchoice2();
			}
		});
		getContentPane().add(choice1);

		label2.setBounds(10, 40, 119, 24);
		getContentPane().add(label2);

		choice2.setBounds(135, 42, 307, 22);
		choice2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// ����� ������ � �������� ������ �������� � ������
				textArea1.setText("");
				textArea1.setEnabled(false);
				button4.setEnabled(false);
				button5.setEnabled(false);
				mainMenu.getMenu(3).getItem(2).setEnabled(false);
				boolean access=choice2.getSelectedIndex() > 0;
				mainMenu.getMenu(1).getItem(2).setEnabled(access);
				mainMenu.getMenu(2).getItem(1).setEnabled(access);
				mainMenu.getMenu(3).getItem(1).setEnabled(access);
				choice3.setEnabled(access);
				if (access)
					fillchoice3();
			}
		});
		choice2.setEnabled(false);// ���� �� ������� ���������, ���������
		getContentPane().add(choice2);

		label3.setBounds(10, 70, 119, 24);
		getContentPane().add(label3);

		choice3.setBounds(135, 72, 307, 100);
		choice3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// ����� ������� � ����������� ��� � ���� ��������������
				boolean access=choice3.getSelectedIndex() >= 0;
				textArea1.setEnabled(access);
				button4.setEnabled(access);
				button5.setEnabled(access);
				mainMenu.getMenu(3).getItem(2).setEnabled(access);
				if (access) {
					textArea1.setText(dbo.questions.select(hiddenchoice3[choice3.getSelectedIndex()], "qtext"));
					button4_actionType=true;
				}
				else
					textArea1.setText("");
			}
		});
		choice3.setEnabled(false);// ���� �� ������ �����, ���������
		getContentPane().add(choice3);

		label4.setBounds(10, 176, 119, 24);
		getContentPane().add(label4);
		textArea1.setBounds(135, 178, 567, 180);
		textArea1.setEnabled(false);// ���� �� ������ ������, ���������
		getContentPane().add(textArea1);

		button4.setBounds(520, 364, 80, 24);
		button4.setEnabled(false);// ���� �� ������ ������, ���������
		button4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(button4_actionType){// ���������� ��� ��������
					dbo.questions.update(
						hiddenchoice3[choice3.getSelectedIndex()],
						new String[]{
							dbo.questions.select(hiddenchoice3[choice3.getSelectedIndex()], "qid"),
							dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()], "cid"),
							textArea1.getText()
						}
					);
					dbo.questions.save();
					fillchoice3();
					JOptionPane.showMessageDialog(null,"������ ��������");
				}
				else{// ���������� ��� ��������
					dbo.questions.insert(new String[] { "" + (dbo.questions.size() + 1),
						dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()], "cid"),
						textArea1.getText() });
					dbo.questions.save();
					fillchoice3();
					JOptionPane.showMessageDialog(null,"������ ��������");
				}
				textArea1.setText("");
				textArea1.setEnabled(false);
				button4.setEnabled(false);
				button5.setEnabled(false);
			}
		});

		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		getContentPane().add(button4);

		button5.setBounds(606, 364, 80, 24);
		button5.setEnabled(false);// ���� �� ������ ������, ���������
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������� � ��������� ������ �� ���������
				textArea1.setText("");
				textArea1.setEnabled(false);
				button4.setEnabled(false);
				button5.setEnabled(false);
			}
		});
		getContentPane().add(button5);
	}

	private void fillchoice1() {
		choice1.removeAll();
		hiddenchoice1 = new int[dbo.subjects.size() + 1];
		choice1.addItem("�������� ����������");
		hiddenchoice1[0] = -1;
		int j = 1;
		for (int i = 0; i < dbo.subjects.size(); i++) {
			choice1.addItem(dbo.subjects.select(i, "sname"));
			hiddenchoice1[j++] = i;
		}
	}

	private void fillchoice2() {
		choice2.removeAll();
		choice2.addItem("�������� �����");
		String sid = dbo.subjects.select(hiddenchoice1[choice1.getSelectedIndex()], "sid");
		for (int i = 0; i < dbo.cards.size(); i++)
			if (dbo.cards.select(i, "sid").equals(sid))
				choice2.addItem(dbo.cards.select(i, "cnum"));
		hiddenchoice2 = new int[choice2.getItemCount()];
		hiddenchoice2[0] = -1;
		if (choice2.getItemCount() > 1) {
			int j = 1;
			for (int i = 0; i < dbo.cards.size(); i++)
				if (dbo.cards.select(i, "sid").equals(sid))
					hiddenchoice2[j++] = i;
			choice2.setEnabled(true);
		}
		choice3.removeAll();
		choice3.setEnabled(false);
	}

	private void fillchoice3() {
		choice3.removeAll();
		String cid = dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()], "cid");
		for (int i = 0; i < dbo.questions.size(); i++)
			if (dbo.questions.select(i, "cid").equals(cid))
				choice3.add(dbo.questions.select(i, "qtext"));
		hiddenchoice3 = new int[choice3.getItemCount()];
		boolean access=choice3.getItemCount() > 0;
		choice3.setEnabled(access);
		if (access) {
			int j = 0;
			for (int i = 0; i < dbo.questions.size(); i++)
				if (dbo.questions.select(i, "cid").equals(cid))
					hiddenchoice3[j++] = i;
		}
	}
	
	private void menuCreate(){
		JMenu fileItem = new JMenu("����");
		Font font=new Font("Tahoma",Font.PLAIN,12);
		fileItem.setFont(font);
		JMenuItem setTemplate=new JMenuItem("������� ������");
		setTemplate.setFont(font);
		setTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ����� ������� �����
				rtf.addTemplate();
				mainMenu.getMenu(0).getItem(1).setEnabled(rtf.tmpLoaded);
			}
		});
		fileItem.add(setTemplate);
		JMenuItem exportFile=new JMenuItem("������� �����");
		exportFile.setFont(font);
		exportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ������� �����
				if(choice1.getSelectedIndex()>0)
					rtf.save(hiddenchoice1[choice1.getSelectedIndex()]);
				else
					JOptionPane.showMessageDialog(null,"�� ������� ����������");
			}
		});
		exportFile.setEnabled(false);
		fileItem.add(exportFile);
		JMenuItem sizeFile=new JMenuItem("������ �����");
		sizeFile.setFont(font);
		sizeFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ������� �����
				if(choice1.getSelectedIndex()>0)
					rtf.fileSize(hiddenchoice1[choice1.getSelectedIndex()]);
				else
					JOptionPane.showMessageDialog(null,"�� ������� ����������");
			}
		});
		fileItem.add(sizeFile);
		fileItem.addSeparator();
		JMenuItem helpButton = new JMenuItem("�������");
		helpButton.setFont(font);
    	helpDialog.setTitle("�������");
		helpDialog.getContentPane().setLayout(null);
    	JLabel html=new JLabel("<html><h1>������� �����</h1><ul><li>������� ������</li><li>������� ����������</li><li>�������������� ����</li></ul></html>");
		html.setBounds(20,0,560,250);
    	helpDialog.add(html);
    	helpDialog.setResizable(false);
		Button ok = new Button("OK");
		ok.setBounds(260,285,80,25);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				helpDialog.setVisible(false);
			}
		});
		helpDialog.add(ok);
		helpButton.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
        		helpDialog.setBounds(getX()+getWidth()/2-300,getY()+getHeight()/2-180,600,360);
            	helpDialog.setVisible(true);
            }           
        });
		fileItem.add(helpButton);
		fileItem.addSeparator();
		JMenuItem exitButton=new JMenuItem("�����");
		exitButton.setFont(font);
		fileItem.add(exitButton);
		exitButton.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }           
        });
		mainMenu.add(fileItem);
		
		JMenu createItem = new JMenu("��������");
		createItem.setFont(font);
		JMenuItem subjectCreate=new JMenuItem("����������");
		subjectCreate.setFont(font);
		subjectCreate.addActionListener(new ActionListener() {           
			public void actionPerformed(ActionEvent arg0) {// �������� ����������
				String name = "" + JOptionPane.showInputDialog("�������� ����������");
				if (name.length() > 0) {
					dbo.subjects.insert(new String[] { "" + (dbo.subjects.size() + 1), name });
					dbo.subjects.save();
					fillchoice1();
				}
			}
        });
		createItem.add(subjectCreate);
		JMenuItem cardCreate=new JMenuItem("������");
		cardCreate.setFont(font);
		cardCreate.setEnabled(false);
		cardCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������� ������
				String name = JOptionPane.showInputDialog("�������� ������");
				if (name.length() > 0) {
					dbo.cards.insert(
							new String[] { "" + (dbo.cards.size() + 1), "" + choice1.getSelectedIndex(), name });
					dbo.cards.save();
					fillchoice2();
				}
			}
        });
		createItem.add(cardCreate);
		JMenuItem questionCreate=new JMenuItem("�������");
		questionCreate.setFont(font);
		questionCreate.setEnabled(false);
		questionCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* ������ �������� ������� */
				textArea1.setText("");
				textArea1.setEnabled(true);
				button4.setEnabled(true);
				button4_actionType=false;
				button5.setEnabled(true);
			}
        });
		createItem.add(questionCreate);
		mainMenu.add(createItem);

		JMenu editItem = new JMenu("��������������");
		editItem.setFont(font);
		JMenuItem subjectEdit=new JMenuItem("����������");
		subjectEdit.setFont(font);
		subjectEdit.setEnabled(false);
		subjectEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������������� ����������
				String name = JOptionPane.showInputDialog("�������� ����������",dbo.subjects.select(hiddenchoice1[choice1.getSelectedIndex()],"sname"));
				if (name!=null) {
					int res = JOptionPane.showConfirmDialog(null,"�������, ��� ������ ������ ���������", "�������������� ����������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (res == JOptionPane.YES_OPTION){
						dbo.subjects.update(
							hiddenchoice1[choice1.getSelectedIndex()],
							new String[] {
								dbo.subjects.select(hiddenchoice1[choice1.getSelectedIndex()],"sid"),
								name
							}
						);
						dbo.subjects.save();
						fillchoice1();
					}
				}
			}
        });
		editItem.add(subjectEdit);
		JMenuItem cardEdit=new JMenuItem("������");
		cardEdit.setFont(font);
		cardEdit.setEnabled(false);
		cardEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������������� ������
				String name = JOptionPane.showInputDialog("��������/����� ������",dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"cnum"));
				if (name!=null) {
					int res = JOptionPane.showConfirmDialog(null,"�������, ��� ������ ������ ���������", "�������������� ����������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (res == JOptionPane.YES_OPTION){
						dbo.cards.update(
							hiddenchoice2[choice2.getSelectedIndex()],
							new String[]{
								dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"cid"),
								dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"sid"),
								name
							}
						);
						dbo.cards.save();
						fillchoice2();
					}
				}
			}
        });
		editItem.add(cardEdit);
		mainMenu.add(editItem);

		JMenu deleteItem = new JMenu("��������");
		deleteItem.setFont(font);
		JMenuItem subjectDelete=new JMenuItem("����������");
		subjectDelete.setFont(font);
		subjectDelete.setEnabled(false);
		subjectDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������� ����������
				int res = JOptionPane.showConfirmDialog(null,"�������, ��� ������ ������� ����������,\r\n������ � ��� �������� ��� ������� � ������", "�������� ����������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.YES_OPTION) {
					String sid = dbo.subjects.select(hiddenchoice1[choice1.getSelectedIndex()], "sid");
					for (int i = 0; i < dbo.cards.size(); i++)
						if (dbo.cards.select(i, "sid").equals(sid)) {
							for (int j = 0; j < dbo.questions.size(); j++){
								if (dbo.questions.select(j, "cid").equals(dbo.cards.select(i, "cid")))
									dbo.questions.delete(j);
							}
							dbo.cards.delete(i);
						}
					dbo.subjects.delete(hiddenchoice1[choice1.getSelectedIndex()]);
					dbo.questions.save();
					dbo.cards.save();
					dbo.subjects.save();
					fillchoice1();
					choice2.removeAll();
					choice2.setEnabled(true);
				}
			}
		});
		deleteItem.add(subjectDelete);
		JMenuItem cardDelete=new JMenuItem("������");
		cardDelete.setFont(font);
		cardDelete.setEnabled(false);
		cardDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������� ������
				int res = JOptionPane.showConfirmDialog(null,"�������, ��� ������ ������� �����,\r\n������ � ��� �������� ��� �������", "�������� ������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.YES_OPTION) {
					String �id = dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()], "�id");
					for (int i = 0; i < dbo.questions.size(); i++){
						if (dbo.questions.select(i, "cid").equals(�id))
							dbo.questions.delete(i);
					}
					dbo.cards.delete(hiddenchoice2[choice2.getSelectedIndex()]);
					dbo.questions.save();
					dbo.cards.save();
					fillchoice2();
				}
			}
		});
		deleteItem.add(cardDelete);
		JMenuItem questionDelete=new JMenuItem("�������");
		questionDelete.setFont(font);
		questionDelete.setEnabled(false);
		questionDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �������� �������
				int res = JOptionPane.showConfirmDialog(null, "�������, ��� ������ ������� ������", "�������� �������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.YES_OPTION) {
					dbo.questions.delete(hiddenchoice3[choice3.getSelectedIndex()]);
					dbo.questions.save();
					fillchoice3();
				}
			}
		});
		deleteItem.add(questionDelete);
		mainMenu.add(deleteItem);
		setJMenuBar(mainMenu);
	}
}
