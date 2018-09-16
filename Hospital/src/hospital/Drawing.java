package hospital;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class Drawing {
    public JFrame jfrm;
    public JPanel topPanel;
    public JPanel bottomPanel;
    public JPanel leftPanel;
    public JPanel centerPanel;
    public JPanel rightPanel;
    public FunctionalPatient funcPatient;
    public FunctionalAdmin funcAdmin;
   
    public Drawing(JFrame jf){
        try{
            //UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
            UIManager.setLookAndFeel(new NimbusLookAndFeel()); 
        }catch(Exception e){}
        
        jfrm=jf;
        jfrm.setLayout(new BorderLayout());
        jfrm.setSize(830, 580); //шв
        jfrm.setLocationRelativeTo(null);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setVisible(true); 
        
        JMenuBar menuBar = new JMenuBar();
        jfrm.setJMenuBar(menuBar);
        JMenu helpMenu=new JMenu("Справка");
        menuBar.add(helpMenu);
        JMenuItem prhelpMenu = new JMenuItem("О программе");
        helpMenu.add(prhelpMenu);
        prhelpMenu.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent ae) {
                Font font = new Font("Verdana", Font.ROMAN_BASELINE, 13);
                Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 10);
                JPanel nnPanel=new JPanel(new GridLayout(5,1));
                String textH="<html>Программа позволяет осуществить запись на прием к врачу, отследить свою историю <br>посещений "
                            + "+ дополнительные функции для администратора";
                JLabel lbltext=new JLabel(textH);
                JLabel lblLogin = new JLabel("Запись на прием 3000турбо");
                lblLogin.setFont(font);
                JLabel lbl = new JLabel(" ");
                JLabel lbl2 = new JLabel(" ");
                JLabel lbl3 = new JLabel("Версия 1.0.3");
                lbl3.setFont(font2);
                nnPanel.add(lblLogin);
                nnPanel.add(lbl);
                nnPanel.add(lbltext);
                nnPanel.add(lbl2);
                nnPanel.add(lbl3);
                JOptionPane.showMessageDialog(jfrm, nnPanel, "О программе",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        
        topPanel=new JPanel();
        topPanel.setVisible(true);
        //topPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        //topPanel.setBackground(Color.getHSBColor(0, 104, 139));
        
        bottomPanel=new JPanel();
        bottomPanel.setVisible(true);
        //bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        leftPanel=new JPanel();
        leftPanel.setVisible(true);
        //leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        rightPanel=new JPanel();
        rightPanel.setVisible(true);
        //leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        centerPanel=new JPanel();
        centerPanel.setVisible(true);
        //centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        jfrm.getContentPane().setLayout(new BorderLayout());
        jfrm.getContentPane().add(topPanel,BorderLayout.PAGE_START);
        jfrm.getContentPane().add(bottomPanel,BorderLayout.PAGE_END);
        jfrm.getContentPane().add(leftPanel,BorderLayout.BEFORE_LINE_BEGINS);
        jfrm.getContentPane().add(centerPanel,BorderLayout.CENTER);
        jfrm.getContentPane().add(rightPanel,BorderLayout.EAST);
    }
    
    public void greeting(){
        jfrm.repaint();
        centerPanel.removeAll();
        topPanel.removeAll();
        leftPanel.removeAll();
        bottomPanel.removeAll();
        rightPanel.removeAll();
        centerPanel.revalidate();
        topPanel.revalidate();
        leftPanel.revalidate();
        bottomPanel.revalidate();
        rightPanel.revalidate();
        
        Font font = new Font("GabriolaGabriola", Font.PLAIN, 13);
        centerPanel.setLayout(new FlowLayout());
        JPanel helloPanel=new JPanel();
        //helloPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        helloPanel.setVisible(true);
        helloPanel.setLayout(new GridLayout(3,1));
        centerPanel.add(helloPanel);
        
        JLabel lblInf=new JLabel("Прежде чем приступить к работе, войдите в систему");
        lblInf.setFont(font);
        helloPanel.add(lblInf);
        
        JPanel dataPanel=new JPanel();
        dataPanel.setLayout(new GridLayout(3,1));
        //dataPanel.setBackground(Color.white);
        dataPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        helloPanel.add(dataPanel);
        
        JPanel log=new JPanel();
        dataPanel.add(log);
        JLabel lblName=new JLabel("Имя   ");
        JTextField tfName = new JTextField(10);
        log.add(lblName);
        log.add(tfName);
        
        JPanel pas=new JPanel();
        dataPanel.add(pas);
        JLabel lblPol=new JLabel("Полис");
        JTextField tfPol = new JTextField(10);
        pas.add(lblPol);
        pas.add(tfPol);
//        dataPanel.add(lblName);
//        dataPanel.add(tfName);
//        dataPanel.add(lblPol);
//        dataPanel.add(tfPol);
        
        JPanel btPanel=new JPanel();
        dataPanel.add(btPanel);
        JButton btnEnt=new JButton("Вход");
        btPanel.add(btnEnt);
        
        JPanel btn=new JPanel(new GridLayout(3,1));
        helloPanel.add(btn);
        JPanel reg=new JPanel();
        btn.add(reg);
        JLabel lblReg=new JLabel("Впервые у нас?");
        JButton btReg = new JButton("Регистрация");
        reg.add(lblReg);
        reg.add(btReg);
        
        JPanel adm=new JPanel();
        btn.add(adm);
        JLabel lblAdm=new JLabel("Войти как админ");
        JButton btAdm = new JButton("Вход");
        adm.add(lblAdm);
        adm.add(btAdm);

        Authentication aut=new Authentication();
        btnEnt.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    String login,password;
                    try{
                        login=tfName.getText();
                        password=tfPol.getText();
                        if(aut.entry(login, password)){
                            forPatient(login);
                        }else{
                            JOptionPane.showMessageDialog(jfrm,"Ваши данные не найдены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            greeting();
                        }
                    }catch(NumberFormatException e1){
                    JOptionPane.showMessageDialog(jfrm,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                    greeting();
                    }
                    
                    
                }
            });
        btReg.addActionListener(new ActionListener() { //кнопка регистрации
                public void actionPerformed(ActionEvent ae) {
                    helloPanel.removeAll();
                    JLabel lblInf=new JLabel("Для регистрации введите имя и номер страхового полиса");
                    Font font = new Font("GabriolaGabriola", Font.PLAIN, 13);
                    lblInf.setFont(font);
                    helloPanel.add(lblInf);

                    JPanel dataPanel=new JPanel();
                    dataPanel.setLayout(new GridLayout(3,1));
                    dataPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    helloPanel.add(dataPanel);

                    JPanel log=new JPanel();
                    dataPanel.add(log);
                    JLabel lblName=new JLabel("Имя   ");
                    JTextField tfName = new JTextField(10);
                    log.add(lblName);
                    log.add(tfName);

                    JPanel pas=new JPanel();
                    dataPanel.add(pas);
                    JLabel lblPol=new JLabel("Полис");
                    JTextField tfPol = new JTextField(10);
                    pas.add(lblPol);
                    pas.add(tfPol);
            //        dataPanel.add(lblName);
            //        dataPanel.add(tfName);
            //        dataPanel.add(lblPol);
            //        dataPanel.add(tfPol);

                    JPanel btPanel=new JPanel();
                    dataPanel.add(btPanel);
                    JButton btnEnt=new JButton("Ок");
                    btPanel.add(btnEnt);
                    btnEnt.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            String login,password;
                            try{
                                login=tfName.getText();
                                password=tfPol.getText();
                                if(aut.registration(login, password)){
                                    forPatient(login);
                                }else{
                                    JOptionPane.showMessageDialog(jfrm,"Пользователь с таким полисом уже зарегистрирован","Уведомление", 
                                                      JOptionPane.INFORMATION_MESSAGE);
                                }
                            }catch(NumberFormatException e1){
                            JOptionPane.showMessageDialog(jfrm,"Данные некоректны либо не введены","Уведомление", 
                                                      JOptionPane.INFORMATION_MESSAGE);
                            }

                            
                                
                        }
                    });
                    JButton btnCanc=new JButton("Отмена");
                    btPanel.add(btnCanc);
                    btnCanc.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            greeting();
                        }
                    });
                    topPanel.revalidate();

                    JPanel btn=new JPanel(new GridLayout(3,1));
                    helloPanel.add(btn);
                    JPanel reg=new JPanel();
                    btn.add(reg);
                    JLabel lblReg=new JLabel("");
                    reg.add(lblReg);
                    

                    JPanel adm=new JPanel();
                    btn.add(adm);
                    JLabel lblAdm=new JLabel("");
                    adm.add(lblAdm);
                    
                    helloPanel.revalidate();
                    
                }
            });
        btAdm.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    helloPanel.removeAll();
                    JLabel lblInf=new JLabel("Для входа в качестве администратора введите логин и пароль");
                    Font font = new Font("GabriolaGabriola", Font.PLAIN, 13);
                    lblInf.setFont(font);
                    helloPanel.add(lblInf);

                    JPanel dataPanel=new JPanel();
                    dataPanel.setLayout(new GridLayout(3,1));
                    dataPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    helloPanel.add(dataPanel);

                    JPanel log=new JPanel();
                    dataPanel.add(log);
                    JLabel lblName=new JLabel("Логин  ");
                    JTextField tfName = new JTextField(10);
                    log.add(lblName);
                    log.add(tfName);

                    JPanel pas=new JPanel();
                    dataPanel.add(pas);
                    JLabel lblPol=new JLabel("Пароль");
                    JPasswordField tfPol = new JPasswordField(10);
                    pas.add(lblPol);
                    pas.add(tfPol);
            
                    JPanel btPanel=new JPanel();
                    dataPanel.add(btPanel);
                    JButton btnEnt=new JButton("Ок");
                    btPanel.add(btnEnt);
                    btnEnt.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            String login,password;
                            try{
                                login=tfName.getText();
                                password=tfPol.getText();
                                if(aut.entryAdmin(login, password)){
                                    forAdmin();
                                }else{
                                    JOptionPane.showMessageDialog(jfrm,"Неверные данные","Уведомление", 
                                                      JOptionPane.INFORMATION_MESSAGE);
                                    jfrm.revalidate();
                                }
                            }catch(NumberFormatException e1){
                            JOptionPane.showMessageDialog(jfrm,"Данные некоректны либо не введены","Уведомление", 
                                                      JOptionPane.INFORMATION_MESSAGE);
                            jfrm.repaint();
                            }

                            
                                
                        }
                    });
                    JButton btnCanc=new JButton("Отмена");
                    btPanel.add(btnCanc);
                    btnCanc.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            greeting();
                        }
                    });
                    topPanel.revalidate();

                    JPanel btn=new JPanel(new GridLayout(3,1));
                    helloPanel.add(btn);
                    JPanel reg=new JPanel();
                    btn.add(reg);
                    JLabel lblReg=new JLabel("");
                    reg.add(lblReg);
                    

                    JPanel adm=new JPanel();
                    btn.add(adm);
                    JLabel lblAdm=new JLabel("");
                    adm.add(lblAdm);
                    
                    helloPanel.revalidate();
                }
            });
        
        
        
        
    }
    
    public void forPatient(String login){
        funcPatient=new FunctionalPatient(centerPanel,bottomPanel);
        centerPanel.removeAll();
        topPanel.removeAll();
        leftPanel.removeAll();
        bottomPanel.removeAll();
        rightPanel.removeAll();
        centerPanel.revalidate();
        topPanel.revalidate();
        leftPanel.revalidate();
        bottomPanel.revalidate();
        rightPanel.revalidate();
        
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JPanel exitPanel=new JPanel();
        exitPanel.setLayout(new BorderLayout());
        topPanel.add(exitPanel,BorderLayout.EAST);
        String hello="Добро пожаловать, "+login+"! ";
        JLabel lblHello=new JLabel(hello);
        exitPanel.add(lblHello,BorderLayout.WEST);
        JButton btExit=new JButton("Выход");
        exitPanel.add(btExit,BorderLayout.EAST);
        btExit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent ae) {
                greeting();
            }
        });
        topPanel.revalidate();
        
        bottomPanel.setLayout(new BorderLayout());
        Font font = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        leftPanel.setLayout(new GridLayout(8,1));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JButton btRec=new JButton("Запись на прием");
        leftPanel.add(btRec);
        btRec.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panRec=funcPatient.drawingRecord();
                    JScrollPane jsp=new JScrollPane(panRec);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        JButton btAct=new JButton("Предстоящие посещения");
        leftPanel.add(btAct);
        btAct.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panActHys=funcPatient.drawingActRec();
                    JScrollPane jsp=new JScrollPane(panActHys);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        JButton btHys=new JButton("Все посещения");
        leftPanel.add(btHys);
        btHys.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panHys=funcPatient.drawingAllRec();
                    JScrollPane jsp=new JScrollPane(panHys);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        centerPanel.setLayout(new BorderLayout());
        centerPanel.removeAll();
        JPanel panActHys=funcPatient.drawingActRec();
        JScrollPane jsp=new JScrollPane(panActHys);
        centerPanel.add(jsp);
        centerPanel.revalidate();
        
        
    }
    
    public void forAdmin(){
        funcAdmin=new FunctionalAdmin(bottomPanel,centerPanel);
        centerPanel.removeAll();
        topPanel.removeAll();
        leftPanel.removeAll();
        bottomPanel.removeAll();
        rightPanel.removeAll();
        centerPanel.revalidate();
        topPanel.revalidate();
        leftPanel.revalidate();
        bottomPanel.revalidate();
        rightPanel.revalidate();
        
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JPanel exitPanel=new JPanel();
        exitPanel.setLayout(new BorderLayout());
        topPanel.add(exitPanel,BorderLayout.EAST);
        String hello="Добро пожаловать, администратор! ";
        JLabel lblHello=new JLabel(hello);
        exitPanel.add(lblHello,BorderLayout.WEST);
        JButton btExit=new JButton("Выход");
        exitPanel.add(btExit,BorderLayout.EAST);
        btExit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent ae) {
                greeting();
            }
        });
        topPanel.revalidate();
        
        bottomPanel.setLayout(new BorderLayout());
        Font font = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        bottomPanel.revalidate();
        bottomPanel.repaint();
        jfrm.repaint();
        
        leftPanel.setLayout(new GridLayout(8,1));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JButton btRec=new JButton("Пациенты");
        leftPanel.add(btRec);
        btRec.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panPat=funcAdmin.drawingListPatient();
                    JScrollPane jsp=new JScrollPane(panPat);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
//                    boolean sc=true;
//                    while(sc){
//                        sc=funcAdmin.drawingListPatient();
//                    }
                }
            });
        JButton btAct=new JButton("Врачи");
        leftPanel.add(btAct);
        btAct.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panDor=funcAdmin.drawingListDoctor();
                    JScrollPane jsp=new JScrollPane(panDor);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        JButton btSpec=new JButton("Специальности");
        leftPanel.add(btSpec);
        btSpec.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panDor=funcAdmin.drawingListSpecialization();
                    JScrollPane jsp=new JScrollPane(panDor);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        JButton btTt=new JButton("Расписания");
        leftPanel.add(btTt);
        btTt.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panDor=funcAdmin.drawingListTimetable();
                    JScrollPane jsp=new JScrollPane(panDor);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        String textH="<html>Дневная занятость и<br>запись на прием";
        JButton btDe=new JButton(textH);
        leftPanel.add(btDe);
        btDe.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panDor=funcAdmin.drawingEmploymentDay();//employment
                    JScrollPane jsp=new JScrollPane(panDor);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        String textHq="<html>История записей и<br>добавление комментариев";
        JButton btHys=new JButton(textHq);
        leftPanel.add(btHys);
        btHys.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panDor=funcAdmin.drawingListReservation();
                    JScrollPane jsp=new JScrollPane(panDor);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        JButton btTecn=new JButton("Технические данные");
        leftPanel.add(btTecn);
        btTecn.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent ae) {
                    centerPanel.removeAll();
                    JPanel panDor=funcAdmin.drawingTecnDate();
                    JScrollPane jsp=new JScrollPane(panDor);
                    centerPanel.add(jsp);
                    centerPanel.revalidate();
                }
            });
        centerPanel.setLayout(new BorderLayout());
        centerPanel.removeAll();
        JPanel panPat=funcAdmin.drawingListPatient();
        JScrollPane jsp=new JScrollPane(panPat);
        centerPanel.add(jsp);
        centerPanel.revalidate();
        
        
    }
    
    
}
