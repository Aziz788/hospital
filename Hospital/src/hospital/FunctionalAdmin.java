package hospital;

import fact.Specialization;
import fact.Reservation;
import fact.Patient;
import fact.TechnicalDataForRecord;
import fact.Doctor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class FunctionalAdmin {
    public ConnectionDataBase connDB;
    public JPanel bottomPanel;
    public JPanel centerPanel;
    
    public int codeDr;
    public String strDate;
    public int spec;
    public Date date;
    public int policy;
    
    public FunctionalPatient funcPatient;
                                
    
    public FunctionalAdmin(JPanel bottomPanel,JPanel centerPanel){
        connDB=new ConnectionDataBase();
        this.bottomPanel=bottomPanel;
        this.centerPanel=centerPanel;
        funcPatient=new FunctionalPatient(centerPanel,bottomPanel);
    }
    
    public Object[][] getListPatient(){
        ArrayList<Patient> listPatient=new ArrayList();
        String sql01="SELECT * FROM patients";
        connDB.setDataFromDatabase(sql01, listPatient,1);
        Object[][] dataTable=new Object[listPatient.size()][3]; //массив данных для таблицы
        for(int i=0;i<dataTable.length;i++){
            dataTable[i][0]=listPatient.get(i).name;
            dataTable[i][1]=listPatient.get(i).policy;
            dataTable[i][2]=listPatient.get(i).RegistrationDate;
            //System.out.println("\n");
        }
        return dataTable;
    }
    
    public JPanel drawingListPatient(){
        //scout=false;
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        bottomPanel.revalidate();
        
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(4,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Пациенты");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel("Для добавления нового пациента заполните поля форм ");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Для редактирования/удаления пациента выберите строку с ним. Изменять можно только имя");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        JPanel parPan=new JPanel();
        lblPanel.add(parPan);
        JLabel lblName=new JLabel("ФИО");
        JTextField tName=new JTextField(10);
        JLabel lblPol=new JLabel("№ полиса");
        JTextField tPol=new JTextField(10);
        JButton btOkPar=new JButton("Добавить");
        parPan.add(lblName);
        parPan.add(tName);
        parPan.add(lblPol);
        parPan.add(tPol);
        parPan.add(btOkPar);
        btOkPar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                try{
                                String name=tName.getText();
                                String forPolicy=tPol.getText();
                                Date todayDate=new Date();
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                String date=format1.format(todayDate);
                                int policy=Integer.valueOf(forPolicy);
                                int countP=0;
                                ArrayList<Patient> listPatient=new ArrayList();
                                String sql01="SELECT * FROM patients";
                                connDB.setDataFromDatabase(sql01, listPatient,1);
                                //System.out.println(dataForTable.length);
                                for(int i=1;i<listPatient.size();i++){
                                    if(listPatient.get(i).policy==policy)
                                        countP++;
                                    }//Конец проверки на совпадения
                                if(countP>0){
                                     JOptionPane.showMessageDialog(jp,"Пациент с таким полисом уже зарегистрирован","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                } else{
                                    //System.out.println("Хотите добавить "+name+" "+forPolicy+" "+policy+" "+date);
                                    String sqlReg="INSERT INTO `hospital`.`patients` (`Name`, `Policy`,`RegistrationDate`) VALUES ('"+name+"', '"+policy+"','"+date+"');";
                                    connDB.updateDataFromDatabase(sqlReg);
                                    JOptionPane.showMessageDialog(jp,"Пациент добавлен","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                    //jp=drawingListPatient();
                                    centerPanel.removeAll();
                                    JPanel panPat=drawingListPatient();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                                
                                }
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
//                            catch(ClassCastException tr){//неверный обход массива
//                                System.out.println("Ввели неверный тип данных для полиса ");
//                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            
            }
        });
        
        
        
        
        Object[][] dataForTable=getListPatient();
        String[] colHeads = {"ФИО","Номер полиса","Дата регистрации"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        ListSelectionModel selModel = tabActRec.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {               
            public void valueChanged(ListSelectionEvent e) {
                int selRow = tabActRec.getSelectedRow();
                //System.out.println("Выбрали строку номер "+selectedRow);
                bottomPanel.removeAll();
                JPanel btBotPan=new JPanel();
                bottomPanel.add(btBotPan,BorderLayout.EAST);
               
                    JButton btnEdit=new JButton("Редактировать");
                    btnEdit.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            //System.out.println("st="+st);
                            try{
                                String name=(String)tabActRec.getValueAt(st,0);
                                int policy=(int)(dataForTable[st][1]);
                                //System.out.println("Хотите изменить "+name+" "+policy+" ");
                                String sqlReg="UPDATE `hospital`.`patients` SET `Name`='"+name+"' WHERE `Policy`='"+policy+"';";
                                connDB.updateDataFromDatabase(sqlReg);
                                JOptionPane.showMessageDialog(jp,"Информация обновлена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                JPanel panPat=drawingListPatient();
                                JScrollPane jsp=new JScrollPane(panPat);
                                centerPanel.add(jsp);
                                centerPanel.revalidate();
                                  
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch(ClassCastException tr){//неверный обход массива
                                JOptionPane.showMessageDialog(jp,"Несовместимые типы","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Введите новую информацию в строке, затем нажмите кнопку 'Редактировать'","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }


                        }
                    });
                    btBotPan.add(btnEdit);
                    JButton btnRem=new JButton("Удалить");
                    btnRem.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            int policy=(int)dataForTable[st][1];
                            //System.out.println("Хотите удалить "+policy);
                            String sql="DELETE FROM `hospital`.`patients` WHERE `Policy`='"+policy+"';";
                            connDB.updateDataFromDatabase(sql);
                            JOptionPane.showMessageDialog(jp,"Информация удалена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            centerPanel.removeAll();
                            JPanel panPat=drawingListPatient();
                            JScrollPane jsp=new JScrollPane(panPat);
                            centerPanel.add(jsp);
                            centerPanel.revalidate();
                        }
                    });
                    btBotPan.add(btnRem);
                    bottomPanel.repaint();
                //}
                bottomPanel.revalidate();
            }
          });
        
        
        return jp;
        //return scout;
    }
    
    public Object[][] getListDoctor(){
        ArrayList<Doctor> listDoctor=new ArrayList();
        String sql03="SELECT * FROM doctors";
        connDB.setDataFromDatabase(sql03, listDoctor,3);
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql0="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql0, listSpec,2);
            
        Object[][] dataTable=new Object[listDoctor.size()][3]; //массив данных для таблицы
        for(int i=0;i<dataTable.length;i++){
            dataTable[i][0]=listDoctor.get(i).codeDr;
            dataTable[i][1]=listDoctor.get(i).name;
            //dataTable[i][2]=listDoctor.get(i).specialization;
            
            int poNumSpec=0;
            for(int y=0;y<listSpec.size();y++){
                if(listSpec.get(y).codeSpec==listDoctor.get(i).specialization)
                   poNumSpec=y;
            }
            //System.out.println("spec="+spec+" name="+listSpec.get(poNumSpec).name);
            dataTable[i][2]=listSpec.get(poNumSpec).name;
            //System.out.println(dataTable[i][0]+""+dataTable[i][1]+" "+dataTable[i][2]);
        }
        return dataTable;
    }
    
    public JPanel drawingListDoctor(){
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JPanel jp=new JPanel(new BorderLayout());
        
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(4,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Врачи");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel("Для добавления нового врача заполните поля форм");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Для удаления врача выберите строку с ним");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        JPanel parPan=new JPanel();
        lblPanel.add(parPan);
//        JLabel lblPol=new JLabel("Код");
//        JTextField tPol=new JTextField(10);
        JLabel lblName=new JLabel("ФИО");
        JTextField tName=new JTextField(10);
        JLabel lblSpec=new JLabel("Специальность");
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
        String[] itemsSpec=new String[listSpec.size()];
        for(int i=0;i<listSpec.size();i++){
            itemsSpec[i]=(listSpec.get(i)).name;
            //System.out.println(itemsSpec[i]);
        }
        JComboBox cbSpec = new JComboBox(itemsSpec);
        
        JButton btOkPar=new JButton("Добавить");
        parPan.add(lblName);
        parPan.add(tName);
//        parPan.add(lblPol);
//        parPan.add(tPol);
        parPan.add(lblSpec);
        parPan.add(cbSpec);
        parPan.add(btOkPar);
        
        btOkPar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                ArrayList<Specialization> listSpec=new ArrayList();
                String sql02="SELECT * FROM specialization";
               connDB.setDataFromDatabase(sql02, listSpec,2);
                try{
                    String name=tName.getText();
                    //String forPolicy=tPol.getText();
                    int forSpec=cbSpec.getSelectedIndex();
                    int spec=listSpec.get(forSpec).codeSpec;
                    //int policy=Integer.valueOf(forPolicy);
                    int countD=0;
                    //System.out.println(spec);
                    ArrayList<Doctor> listDoctor=new ArrayList();
                    String sql03="SELECT * FROM doctors";
                    connDB.setDataFromDatabase(sql03, listDoctor,3);
//                    for(int i=1;i<listDoctor.size();i++){
//                        if(listDoctor.get(i).codeDr==policy)
//                            countP++;
//                    }//Конец проверки на совпадения
                    for(int i=0;i<listSpec.size();i++){
                        if(listSpec.get(i).codeSpec==spec)
                            countD++;
                    }//Конец проверки на совпадения
                    if(countD==0){
                        JOptionPane.showMessageDialog(jp,"Несуществующая специальность","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                    } else{
                        //System.out.println("Хотите добавить "+name+" spec="+spec+" "+policy);
                        String sqlReg="INSERT INTO `hospital`.`doctors` (`Name`, `Specialization`) "
                                            + "VALUES ('"+name+"','"+spec+"');";
                        connDB.updateDataFromDatabase(sqlReg);
                        JOptionPane.showMessageDialog(jp,"Врач добавлен","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                        //jp=drawingListPatient();
                                    centerPanel.removeAll();
                                    JPanel panPat=drawingListDoctor();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                                
                                }
                    }catch(NullPointerException re){
                        JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                    }catch(NumberFormatException rf){
                        JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                    }
                
            }
        });
        
        
        Object[][] dataForTable=getListDoctor();
        String[] colHeads = {"Код врача","ФИО","Специализация"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        ListSelectionModel selModel = tabActRec.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {               
            public void valueChanged(ListSelectionEvent e) {
                int selRow = tabActRec.getSelectedRow();
                //System.out.println("Выбрали строку номер "+selectedRow);
                bottomPanel.removeAll();
                JPanel btBotPan=new JPanel();
                bottomPanel.add(btBotPan,BorderLayout.EAST);
                
                    JButton btnRem=new JButton("Удалить");
                    btnRem.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            int code=(int)dataForTable[st][0];
                            //System.out.println("Хотите удалить "+policy);
                            String sql="DELETE FROM `hospital`.`doctors` WHERE `CodeDr`='"+code+"';";
                            connDB.updateDataFromDatabase(sql);
                            //String sql2="DELETE FROM `hospital`.`listreservations` WHERE `CodeDr`='"+code+"';";
//                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//                            Date d=new Date();
//                            String dw=format1.format(d);
//                            String sql2="UPDATE `hospital`.`listreservations` SET `Comment`='Запись отменена,т.к. врач недоступен' WHERE `CodeDoctor`='"+code+"' AND `Date`>'"+dw+"';";
//                            connDB.updateDataFromDatabase(sql2);
                            JOptionPane.showMessageDialog(jp,"Информация удалена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            centerPanel.removeAll();
                            JPanel panPat=drawingListDoctor();
                            JScrollPane jsp=new JScrollPane(panPat);
                            centerPanel.add(jsp);
                            centerPanel.revalidate();
                        }
                    });
                    btBotPan.add(btnRem);
                    bottomPanel.repaint();
                
                bottomPanel.revalidate();
            }
          });
        
        
        return jp;
    }
    
    public Object[][] getListReservation(){
        bottomPanel.setLayout(new BorderLayout());
        Font font = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        ArrayList<Doctor> listDoctor=new ArrayList();
        String sql03="SELECT * FROM doctors";
        connDB.setDataFromDatabase(sql03, listDoctor,3);
        ArrayList<Reservation> listReservation=new ArrayList();
        String sql04="SELECT * FROM listreservations";
        connDB.setDataFromDatabase(sql04, listReservation,5);
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
        ArrayList<Patient> listPatient=new ArrayList();
        String sql01="SELECT * FROM patients";
        connDB.setDataFromDatabase(sql01, listPatient,1);
        Object[][] dataTimetable=new Object[listReservation.size()][11]; //массив данных для таблицы
        for(int i=0;i<dataTimetable.length;i++){
            dataTimetable[i][0]=listReservation.get(i).codeReservation;
            dataTimetable[i][1]=listReservation.get(i).regDate;
            dataTimetable[i][2]=listReservation.get(i).time;
            
            int cDr=listReservation.get(i).codeDoctor;
            int poNumDr=0;
            for(int y=0;y<listDoctor.size();y++){
                if(listDoctor.get(y).codeDr==cDr)
                   poNumDr=y;
            }
            //System.out.println("spec="+spec+" name="+listSpec.get(poNumSpec).name);
            dataTimetable[i][5]=cDr;
            dataTimetable[i][6]=listDoctor.get(poNumDr).name;
            
            int cSp=listDoctor.get(poNumDr).specialization;
            int poNumSpec=0;
            for(int y=0;y<listSpec.size();y++){
                if(listSpec.get(y).codeSpec==cSp)
                   poNumSpec=y;
            }
//            //System.out.println("spec="+spec+" name="+listSpec.get(poNumSpec).name);
            dataTimetable[i][3]=cSp;
            dataTimetable[i][4]=listSpec.get(poNumSpec).name;
            
            int cPa=listReservation.get(i).codePatient;
            int poNumPat=0;
            for(int y=0;y<listPatient.size();y++){
                if(listPatient.get(y).policy==cPa)
                   poNumPat=y;
            }
            
            dataTimetable[i][7]=cPa;
            dataTimetable[i][8]=listPatient.get(poNumPat).name;
           
            dataTimetable[i][9]=listReservation.get(i).comment;
            
            //System.out.println(dataTimetable[i][0]+" "+dataTimetable[i][1]+" "+dataTimetable[i][2]+" "+dataTimetable[i][3]+" "
            //      +dataTimetable[i][4]+" "+dataTimetable[i][5]+" "+dataTimetable[i][6]+" "+dataTimetable[i][7]+" "+dataTimetable[i][8]+" "+dataTimetable[i][9]);
        }
        return dataTimetable;
    }
    
    public JPanel drawingListReservation(){
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(3,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Записи на прием");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel("Для добавления новой записи заполните 1-ую строку ");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Для редактирования/удаления информации о записи выберите строку с ним");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        Object[][] dataForTable=getListReservation();
        String[] colHeads = {"Код записи","Дата","Время приема","Код специализации","Специлизация врача","Код врача","Врач","Полис пациента","ФИО","Комментарий"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        ListSelectionModel selModel = tabActRec.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {               
            public void valueChanged(ListSelectionEvent e) {
                int selRow = tabActRec.getSelectedRow();
                //System.out.println("Выбрали строку номер "+selectedRow);
                bottomPanel.removeAll();
                JPanel btBotPan=new JPanel();
                bottomPanel.add(btBotPan,BorderLayout.EAST);
                if(selRow==0){
                    JButton btnAdd=new JButton("Добавить"); //{"Код записи","Дата","Время приема",Код сп "Специлизация врача",Код врача "ФИО врача","Полис пациента","ФИО пациента","Комментарий"};
                    btBotPan.add(btnAdd);
                    btnAdd.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            ArrayList<Specialization> listSpec=new ArrayList();
                            String sql02="SELECT * FROM specialization";
                            connDB.setDataFromDatabase(sql02, listSpec,2);
                            try{
                                String forCodeRec=(String)tabActRec.getValueAt(0,0);
                                int codeRec=Integer.valueOf(forCodeRec);
                                String date=(String)tabActRec.getValueAt(0,1);
                                String time=(String)tabActRec.getValueAt(0,2);
                                String forSpec=(String)tabActRec.getValueAt(0,3);
                                int spec=Integer.valueOf(forSpec);
                                String forCodeDr=(String)tabActRec.getValueAt(0,5);
                                int codeDr=Integer.valueOf(forCodeDr);
                                String forPolicy=(String)tabActRec.getValueAt(0,7);
                                int policy=Integer.valueOf(forPolicy);
                                String comm=(String)tabActRec.getValueAt(0,9);
                                //System.out.println("Хотите добавить "+codeRec+" "+date+" "+time+" "+spec+" "+codeDr+" "+policy+" "+comm);
                                   
                                int countCR=0,countD=0;
                                //System.out.println(dataForTable.length);
                                for(int i=1;i<dataForTable.length;i++){
                                    if((int)dataForTable[i][0]==codeRec)
                                        countCR++;
                                    }//Конец проверки на совпадения
                                try{
                                    String[] part = date.trim().split("-");//yyyy-mm-dd
                                    int yy=Integer.valueOf(part[0]);
                                    int mm=Integer.valueOf(part[1]);
                                    int dd=Integer.valueOf(part[2]); 
                                    if(mm>12 || dd>30){
                                        countD++;
                                    }
                                }catch(NumberFormatException e2){
                                    countD++;
                                }catch(ArrayIndexOutOfBoundsException as){
                                    countD++;
                                }
                                
                                if(countCR>0){
                                     JOptionPane.showMessageDialog(jp,"Запись с таким кодом уже существует","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                }else 
                                if(countD>0){
                                     JOptionPane.showMessageDialog(jp,"Некорректная дата","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                } else{
                                    //System.out.println("Хотите добавить "+codeRec+" "+date+" "+time+" "+spec+" "+codeDr+" "+policy+" "+comm);
                                    String sqlReg="INSERT INTO `hospital`.`listreservations` (`CodeReservations`, `Date`, `Time`, `CodePatient`, `CodeDoctor`, `Comment`) "
                                            + "VALUES ('"+codeRec+"', '"+date+"', '"+time+"', '"+policy+"', '"+codeDr+"', '"+comm+"');";
                                    connDB.updateDataFromDatabase(sqlReg);
                                    JOptionPane.showMessageDialog(jp,"Запись добавлена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                    //jp=drawingListPatient();
                                    centerPanel.removeAll();
                                    JPanel panPat=drawingListReservation();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                                
                                }
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
//                            catch(ClassCastException tr){//неверный обход массива
//                                System.out.println("Ввели неверный тип данных для полиса ");
//                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            
                            
                        }
                    });
                    bottomPanel.repaint();
                }else {
                    JButton btnEdit=new JButton("Добавить комментарий");
                    btnEdit.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            //System.out.println("st="+st);
                            try{
                                String comm=(String)tabActRec.getValueAt(st,9);
                                //String name=(String)tabActRec.getValueAt(st,0);
                                int codeRec=(int)(dataForTable[st][0]);
                                //System.out.println("Хотите изменить "+codeRec+" "+comm+" ");
                                String sqlReg="UPDATE `hospital`.`listreservations` SET `Comment`='"+comm+"' WHERE `CodeReservations`='"+codeRec+"';";
                                connDB.updateDataFromDatabase(sqlReg);
                                JOptionPane.showMessageDialog(jp,"Комментарий добавлен","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                JPanel panPat=drawingListReservation();
                                JScrollPane jsp=new JScrollPane(panPat);
                                centerPanel.add(jsp);
                                centerPanel.revalidate();
                                  
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch(ClassCastException tr){//неверный обход массива
                                JOptionPane.showMessageDialog(jp,"Несовместимые типы","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Введите новую информацию в строке, затем нажмите кнопку 'Редактировать'","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }




                        }
                    });
                    btBotPan.add(btnEdit);
                    JButton btnRem=new JButton("Удалить");
                    btnRem.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            int codeRec=(int)(dataForTable[st][0]);
                            //System.out.println("Хотите удалить "+policy);
                            String sql="DELETE FROM `hospital`.`listreservations` WHERE `CodeReservations`='"+codeRec+"';";
                            connDB.updateDataFromDatabase(sql);
                            JOptionPane.showMessageDialog(jp,"Запись удалена удалена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            centerPanel.removeAll();
                            JPanel panPat=drawingListReservation();
                            JScrollPane jsp=new JScrollPane(panPat);
                            centerPanel.add(jsp);
                            centerPanel.revalidate();


                        }
                    });
                    btBotPan.add(btnRem);
                    bottomPanel.repaint();
                }
                bottomPanel.revalidate();
            }
          });
        
        
        return jp;
    }
    
    public Object[][] getTecnDate(){
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4);
        Object[][] dataTimetable=new Object[tecData.size()][3]; //массив данных для таблицы
        for(int i=0;i<dataTimetable.length;i++){
            dataTimetable[i][0]=String.valueOf(tecData.get(i).durRec);
            dataTimetable[i][1]=String.valueOf(tecData.get(i).durLanch);
            dataTimetable[i][2]=String.valueOf(tecData.get(i).timeLanch);
        }
        return dataTimetable;
    }
    
    public JPanel drawingTecnDate(){
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(3,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Технические данные");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel("  ");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Для редактирования информации выберите нужную ячейку");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        Object[][] dataForTable=getTecnDate();
        String[] colHeads = {"Длительность приема (мин)","Длительность обеда (мин)","Начало обеда"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        ListSelectionModel selModel = tabActRec.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {               
            public void valueChanged(ListSelectionEvent e) {
                bottomPanel.removeAll();
                JPanel btBotPan=new JPanel();
                bottomPanel.add(btBotPan,BorderLayout.EAST);
                JButton btnEdit=new JButton("Редактировать");
                btnEdit.addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent ae) {
                        int st = tabActRec.getSelectedRow();
                        try{
                                String forDurRec=(String)tabActRec.getValueAt(st,0);
                                String forDurLanch=(String)tabActRec.getValueAt(st,1);
                                String forStartLanch=(String)tabActRec.getValueAt(st,2);
                                
                                int durRec=Integer.valueOf(forDurRec);
                                int durLanch=Integer.valueOf(forDurLanch);
                                Time startLanch=Time.valueOf(forStartLanch);
                                    
                                    //System.out.println("Хотите добавить "+name+" "+forPolicy+" "+policy+" "+date);
                                    String sqlReg="UPDATE `hospital`.`technicaldata` SET `DurationReception`='"+forDurRec+"',`DurationLunchBreak`='"+forDurLanch+"',`StartLunchBreak`='"+forStartLanch+"' "
                                            + "WHERE `CodeTD`='1';";
                                    connDB.updateDataFromDatabase(sqlReg);
                                    JOptionPane.showMessageDialog(jp,"Информация изменена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                    //jp=drawingListPatient();
                                    centerPanel.removeAll();
                                    JPanel panPat=drawingTecnDate();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                                
                                
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                    JPanel panPat=drawingTecnDate();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                            }
//                            catch(ClassCastException tr){//неверный обход массива
//                                System.out.println("Ввели неверный тип данных для полиса ");
//                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                    JPanel panPat=drawingTecnDate();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                            }
                        catch(IllegalArgumentException iae){
                            JOptionPane.showMessageDialog(jp,"Не оставляйте поля пустыми","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                    JPanel panPat=drawingTecnDate();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                        }

                    }
                });
                btBotPan.add(btnEdit);  
                bottomPanel.repaint();
                bottomPanel.revalidate();
            }
                
        });
        
        
        return jp;
    }
    
    public Object[][] getListSpecialization(){
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
        Object[][] dataTable=new Object[listSpec.size()][2]; //массив данных для таблицы
        for(int i=0;i<dataTable.length;i++){
            dataTable[i][0]=listSpec.get(i).codeSpec;
            dataTable[i][1]=listSpec.get(i).name;
            //System.out.println("\n");
        }
        return dataTable;
    }
    
    public JPanel drawingListSpecialization(){//drawingEmploymentDay
        //scout=false;
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        bottomPanel.revalidate();
        
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(4,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Специальности врачей");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel("Для добавления новой специальности заполните поля форм ");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Для редактирования специальности выберите строку с ней");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        JPanel panPar=new JPanel();
        lblPanel.add(panPar);
        JLabel lblSpec=new JLabel("Специальность");
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
        JTextField tSpec=new JTextField(10);
        JButton btOkPar=new JButton("Добавить");
        panPar.add(lblSpec);
        panPar.add(tSpec);
        panPar.add(btOkPar);
        btOkPar.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            try{
                                String name=tSpec.getText();
                                int countP=0;
                                //System.out.println(forPolicy+" "+policy);
                                for(int i=1;i<listSpec.size();i++){
                                    if(name.equals(listSpec.get(i).name))
                                        countP++;
                                    }//Конец проверки на совпадения
                                
                                if(countP>0){
                                     JOptionPane.showMessageDialog(jp,"Такая специальность уже существует","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    //System.out.println("Хотите добавить "+name+" "+forPolicy+" "+policy+" "+date);
                                    String sqlReg="INSERT INTO `hospital`.`specialization` (`Name`) VALUES ('"+name+"');";
                                    connDB.updateDataFromDatabase(sqlReg);
                                    JOptionPane.showMessageDialog(jp,"Специальность добавлена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                    //jp=drawingListPatient();
                                    centerPanel.removeAll();
                                    JPanel panPat=drawingListSpecialization();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                                
                                }
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
//                            catch(ClassCastException tr){//неверный обход массива
//                                System.out.println("Ввели неверный тип данных для полиса ");
//                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            
                            
                        }
                    });
        
        
        
        Object[][] dataForTable=getListSpecialization();
        String[] colHeads = {"Код специальности","Наименование"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        ListSelectionModel selModel = tabActRec.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {               
            public void valueChanged(ListSelectionEvent e) {
                int selRow = tabActRec.getSelectedRow();
                //System.out.println("Выбрали строку номер "+selectedRow);
                bottomPanel.removeAll();
                JPanel btBotPan=new JPanel();
                bottomPanel.add(btBotPan,BorderLayout.EAST);
                JButton btnEdit=new JButton("Редактировать");
                    btnEdit.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            //System.out.println("st="+st);
                            try{
                                String name=(String)tabActRec.getValueAt(st,1);
                                int policy=(int)(dataForTable[st][0]);
                                //System.out.println("Хотите изменить "+name+" "+policy+" ");
                                String sqlReg="UPDATE `hospital`.`specialization` SET `Name`='"+name+"' WHERE `CodeSpec`='"+policy+"';";
                                connDB.updateDataFromDatabase(sqlReg);
                                JOptionPane.showMessageDialog(jp,"Информация обновлена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                JPanel panPat=drawingListSpecialization();
                                JScrollPane jsp=new JScrollPane(panPat);
                                centerPanel.add(jsp);
                                centerPanel.revalidate();
                                  
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch(ClassCastException tr){//неверный обход массива
                                JOptionPane.showMessageDialog(jp,"Несовместимые типы","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Введите новую информацию в строке, затем нажмите кнопку 'Редактировать'","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }


                        }
                    });
                    btBotPan.add(btnEdit);
                    JButton btnRem=new JButton("Удалить");
                    btnRem.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            int st=tabActRec.getSelectedRow();
                            int policy=(int)dataForTable[st][0];
                            //System.out.println("Хотите удалить "+policy);
                            String sql="DELETE FROM `hospital`.`specialization` WHERE `CodeSpec`='"+policy+"';";
                            connDB.updateDataFromDatabase(sql);
                            JOptionPane.showMessageDialog(jp,"Информация удалена","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            centerPanel.removeAll();
                            JPanel panPat=drawingListSpecialization();
                            JScrollPane jsp=new JScrollPane(panPat);
                            centerPanel.add(jsp);
                            centerPanel.revalidate();
                        }
                    });
                    btBotPan.add(btnRem);
                    bottomPanel.repaint();
                
                bottomPanel.revalidate();
            }
          });
        
        
        return jp;
        //return scout;
    }
    
    public Object[][] getListTimetable(){
        ArrayList<Doctor> listDoctor=new ArrayList();
        String sql03="SELECT * FROM doctors";
        connDB.setDataFromDatabase(sql03, listDoctor,3);
        Object[][] dataTable=new Object[listDoctor.size()][9]; //массив данных для таблицы
        
        for(int i=0;i<dataTable.length;i++){
            dataTable[i][0]="№"+listDoctor.get(i).codeDr+"  "+listDoctor.get(i).name+" ("+listDoctor.get(i).specialization+")";
            //dataTable[i][1]=listDoctor.get(i).name;
            //dataTable[i][1]=listDoctor.get(i).specialization;
            
            String ms=String.valueOf(listDoctor.get(i).ttDoctor.MondayStartTime);
            String[] pms=ms.trim().split(":");
            String me=String.valueOf(listDoctor.get(i).ttDoctor.MondayEndTime);
            String[] pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][1]="-";
            else dataTable[i][1]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
            
            ms=String.valueOf(listDoctor.get(i).ttDoctor.TuesdayStartTime);
            pms=ms.trim().split(":");
            me=String.valueOf(listDoctor.get(i).ttDoctor.TuesdayEndTime);
            pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][2]="-";
            else dataTable[i][2]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
             
            ms=String.valueOf(listDoctor.get(i).ttDoctor.WednesdayStartTime);
            pms=ms.trim().split(":");
            me=String.valueOf(listDoctor.get(i).ttDoctor.WednesdayEndTime);
            pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][3]="-";
            else dataTable[i][3]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
            
            ms=String.valueOf(listDoctor.get(i).ttDoctor.ThursdayStartTime);
            pms=ms.trim().split(":");
            me=String.valueOf(listDoctor.get(i).ttDoctor.ThursdayEndTime);
            pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][4]="-";
            else dataTable[i][4]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
            
            ms=String.valueOf(listDoctor.get(i).ttDoctor.FridayStartTime);
            pms=ms.trim().split(":");
            me=String.valueOf(listDoctor.get(i).ttDoctor.FridayEndTime);
            pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][5]="-";
            else dataTable[i][5]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
            
            ms=String.valueOf(listDoctor.get(i).ttDoctor.SaturdayStartTime);
            pms=ms.trim().split(":");
            me=String.valueOf(listDoctor.get(i).ttDoctor.SaturdayEndTime);
            pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][6]="-";
            else dataTable[i][6]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
            
            ms=String.valueOf(listDoctor.get(i).ttDoctor.SundayStartTime);
            pms=ms.trim().split(":");
            me=String.valueOf(listDoctor.get(i).ttDoctor.SundayEndTime);
            pme=me.trim().split(":");
            if(Integer.valueOf(pms[0])==0 && Integer.valueOf(pms[1])==0 )
                dataTable[i][7]="-";
            else dataTable[i][7]=pms[0]+":"+pms[1]+" - "+pme[0]+":"+pme[1];
        //System.out.println(dataTable[0][16]+" "+listDoctor.get(i-1).ttDoctor.SundayEndTime);
        }
        return dataTable;
    }
    
    public JPanel drawingListTimetable(){
        //scout=false;
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("Verdana", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        bottomPanel.revalidate();
        
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(3,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Расписания врачей");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel("Для изменения расписания заполните все поля форм");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Если день нерабочий вводите '24:00:00' в оба поля");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        JPanel timePan=new JPanel();
        JLabel lc=new JLabel("Врач");
        JTextField tc=new JTextField(5);
//        ArrayList<Doctor> listDr=new ArrayList();
//        String sql03="SELECT * FROM doctors;";
//        connDB.setDataFromDatabase(sql03, listDr,3);
//        String[] itemsDr=new String[listDr.size()];
//        int[] itemsCodeDr=new int[listDr.size()];
//        for(int i=0;i<listDr.size();i++){
//            itemsDr[i]="№"+(listDr.get(i)).codeDr+"  "+(listDr.get(i)).name;
//            itemsCodeDr[i]=(listDr.get(i)).codeDr;
//                    //System.out.println(itemsDr[i]);
//        }
//        JComboBox cbDr = new JComboBox(itemsDr);
        JLabel ldw=new JLabel("День недели"); 
        String[] itemsDw={"Пн","Вт","Ср","Чт","Птн","Суб","Вск"};
        int[] itemsCodeDw={2,3,4,5,6,7,1};
        JComboBox cbDw = new JComboBox(itemsDw);
        JLabel lt1=new JLabel("с ");
        SpinnerDateModel model=new SpinnerDateModel();
        JSpinner time1Rec = new JSpinner(model);
        JSpinner.DateEditor jahr3 = new JSpinner.DateEditor(time1Rec, "kk:mm");
        Date today=new Date();
        Long time = today.getTime(); 
        time = time-time-(60*60*1000*3);//time + (60*60*1000); 
        Date newDate = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        model.setValue(c.getTime());
        jahr3.getTextField().setEditable(true);
        time1Rec.setEditor(jahr3);
        JLabel lt2=new JLabel(" до ");
        SpinnerDateModel model2=new SpinnerDateModel();
        JSpinner time2Rec = new JSpinner(model2);
        JSpinner.DateEditor jahr2 = new JSpinner.DateEditor(time2Rec, "kk:mm");
        model2.setValue(c.getTime());
        jahr2.getTextField().setEditable(true);
        JButton btnAdd=new JButton("Ок");
        time2Rec.setEditor(jahr2);
        timePan.add(lc);
        timePan.add(tc);//cbDr);
        timePan.add(ldw);
        timePan.add(cbDw);
        timePan.add(lt1);
        timePan.add(time1Rec);
        timePan.add(lt2);
        timePan.add(time2Rec);
        timePan.add(btnAdd);
        
        
        JPanel parPanel=new JPanel(new BorderLayout());
        parPanel.add(lblPanel,BorderLayout.PAGE_START);
        parPanel.add(timePan,BorderLayout.CENTER);
        
        Object[][] dataForTable=getListTimetable();
        String[] colHeads = {"№ ФИО (спец)","Пн","Вт","Ср","Чт","Птн","Суб","Вск"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(parPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        btnAdd.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent ae) {
                            try{
                                //int codeDr=itemsCodeDr[cbDr.getSelectedIndex()];
                                int codeDr=Integer.valueOf(tc.getText());
                                int countD=0;
                                //System.out.println(spec);
                                ArrayList<Doctor> listDoctor=new ArrayList();
                                String sql03="SELECT * FROM doctors";
                                connDB.setDataFromDatabase(sql03, listDoctor,3);
                                for(int i=1;i<listDoctor.size();i++){
                                    if(listDoctor.get(i).codeDr==codeDr)
                                        countD++;
                                }//Конец проверки на совпадения
                                if(countD==0){
                                    JOptionPane.showMessageDialog(jp,"Несуществующий код врача","Уведомление", 
                                                          JOptionPane.INFORMATION_MESSAGE);
                                } else{
                                //System.out.println("codeDr="+codeDr);
                                int dayWeek=itemsCodeDw[cbDw.getSelectedIndex()];
                                //System.out.println("dayWeek="+dayWeek);
                                SimpleDateFormat format1 = new SimpleDateFormat("kk:mm"); 
                                Date tdate1=(Date)time1Rec.getValue();
                                String dc1=format1.format(tdate1)+":00";
                                Time tr1=Time.valueOf(dc1);
                                Date tdate2=(Date)time2Rec.getValue();
                                String dc2=format1.format(tdate2)+":00";
                                Time tr2=Time.valueOf(dc2);
                                //System.out.println("с"+tr1+" до "+tr2);
//                                String ms=(String)tabActRec.getValueAt(st,3);
//                                String me=(String)tabActRec.getValueAt(st,4);
//                                String ts=(String)tabActRec.getValueAt(st,5);
//                                String te=(String)tabActRec.getValueAt(st,6);
//                                String ws=(String)tabActRec.getValueAt(st,7);
//                                String we=(String)tabActRec.getValueAt(st,8);
//                                String ths=(String)tabActRec.getValueAt(st,9);
//                                String the=(String)tabActRec.getValueAt(st,10);
//                                String fs=(String)tabActRec.getValueAt(st,11);
//                                String fe=(String)tabActRec.getValueAt(st,12);
//                                String sas=(String)tabActRec.getValueAt(st,13);
//                                String sae=(String)tabActRec.getValueAt(st,14);
//                                String sus=(String)tabActRec.getValueAt(st,15);
//                                String sue=(String)tabActRec.getValueAt(st,16);
                                String g1=null,g2=null;
                                if(dayWeek==1){
                                    g1="SundayStartTime";
                                    g2="SundayEndTime";
                                }
                                if(dayWeek==2){
                                    g1="MondayStarTime";
                                    g2="MondayEndTime";
                                }
                                if(dayWeek==3){
                                    g1="TuesdayStartTime";
                                    g2="TuesdayEndTime";
                                }
                                if(dayWeek==4){
                                    g1="WednesdayStartTime";
                                    g2="WednesdayEndTime";
                                }
                                if(dayWeek==5){
                                    g1="ThursdayStartTime";
                                    g2="ThursdayEndTime";
                                }
                                if(dayWeek==6){
                                    g1="FridayStartTime";
                                    g2="FridayEndTime";
                                }
                                if(dayWeek==7){
                                    g1="SaturdayStartTime";
                                    g2="SaturdayEndTime";
                                }
                                   //System.out.println("Хотите добавить "+name+" "+forPolicy+" "+policy+" "+date);
                                    String sqlReg="UPDATE `hospital`.`doctors` SET `"+g1+"`='"+tr1+"',`"+g2+"`='"+tr2+"' WHERE `CodeDr`='"+codeDr+"';";
                                    connDB.updateDataFromDatabase(sqlReg);
                                    JOptionPane.showMessageDialog(jp,"Расписание изменено","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                                    //jp=drawingListPatient();
                                    centerPanel.removeAll();
                                    JPanel panPat=drawingListTimetable();
                                    JScrollPane jsp=new JScrollPane(panPat);
                                    centerPanel.add(jsp);
                                    centerPanel.revalidate();
                                }//конец else
                                
                            }catch(NullPointerException re){
                                JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
//                            catch(ClassCastException tr){//неверный обход массива
//                                System.out.println("Ввели неверный тип данных для полиса ");
//                            }
                            catch(NumberFormatException rf){
                                 JOptionPane.showMessageDialog(jp,"Данные некоректны либо не введены","Уведомление", 
                                              JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
                    bottomPanel.repaint();
                bottomPanel.revalidate();
            
        
        
        return jp;
        //return scout;
    }
   
    public Object[][] getWorkTime(){
        //System.out.println("Получили "+codeDr+" "+strDate);
        int dayWeek=funcPatient.getDayWeek(strDate);
        if(dayWeek==0)
            return null;
        //System.out.println("День недели "+dayWeek);
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4); 
        int durRec=tecData.get(0).durRec;
        ArrayList<Time> freeTime=funcPatient.getFreeTime(codeDr, strDate, "00:00:00", "00:00:00");//свободное время конкретного врача 
        ArrayList<Time> busyTime=new ArrayList();//занятое время конкретного врача
        String sql02="SELECT * FROM hospital.listreservations WHERE CodeDoctor='"+codeDr+"' AND Date='"+strDate+"';";
        connDB.setDataFromDatabase(sql02,busyTime,6);
//        for(int y=0;y<busyTime.size();y++){
//            System.out.println("Занятое время в функции "+busyTime.get(y));
//        }
  
                //Для получения расписания нашего врача на этот день
                ArrayList<Doctor> listForTimetableDoctor=new ArrayList();//коллекция, чтобы не добавлять еще один if в conneection
                String sql03="SELECT * FROM hospital.doctors WHERE CodeDr="+codeDr+";";
                connDB.setDataFromDatabase(sql03, listForTimetableDoctor,3);
                //System.out.println( "День недели "+dayWeek+" c "+((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 1)+" до "+((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 2) );
                Time startWork=((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 1);
                Time endWork=((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 2);
                //System.out.println( "День недели "+dayWeek+" c "+startWork+" до "+endWork);
                Time timeStartLanch=(tecData.get(0)).timeLanch;
                int minut=((tecData.get(0)).timeLanch).getMinutes()+(tecData.get(0)).durLanch;
                Time timeEndLanch=new Time(((tecData.get(0)).timeLanch).getHours(),minut,((tecData.get(0)).timeLanch).getSeconds());
           
                Object[][] dataTable=new Object[freeTime.size()+busyTime.size()][2]; //массив данных для таблицы
                try{
                    //System.out.println("busyTime.size()="+busyTime.size());
                    if(busyTime.size()!=0 ){
                        
                        int y=0;
                        
                        Time count=new Time( startWork.getHours(),startWork.getMinutes(),00 );
                        if( endWork.getHours()<timeStartLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        timeStartLanch=endWork;
                        }
                        while(count.getHours()<timeStartLanch.getHours()){
                            //System.out.println("Проверяем время до обеда "+count);
                            int scout=0;
                            Time tcount=new Time(0,00,00);
                            for(int i=0;i<busyTime.size();i++){
                                //System.out.println("busy="+busyTime.get(i)+" count="+count);
                                int bh=(busyTime.get(i)).getHours(),ch=count.getHours(),bm=(busyTime.get(i)).getMinutes(),cm=count.getMinutes() ;
                                if( (bh!=ch && bm!=cm) || (bh!=ch && bm==cm) || (bh==ch && bm!=cm) ){
                                    tcount.setHours(ch);
                                    tcount.setMinutes(cm);
                                    //tcount=new Time(ch,cm,00);
                                    //System.out.println("Обнаружено свободное?"+tcount);
                                    scout++;

                                }
                            }
                            if(scout==busyTime.size()){
                                //System.out.println("Запись свободного tcount="+tcount);
                                //freeTime.add(tcount);
                                dataTable[y][0]=tcount;
                                y++;
                            } else{
                                //System.out.println("Запись занятого count="+count);
                                Time se=new Time(count.getHours(),count.getMinutes(),00);
                                dataTable[y][0]=se;
                                
                                ArrayList als=new ArrayList();
                                String sql090="SELECT * FROM hospital.listreservations WHERE CodeDoctor='"+codeDr+"' AND Date='"+strDate+"' AND Time='"+se+"';";
                                connDB.setDataFromDatabase(sql090,als,7);
                                dataTable[y][1]=String.valueOf(als.get(0));
                                y++;
                            }
                            
                            count.setMinutes(count.getMinutes()+durRec);
                        }
                        if( startWork.getHours()>timeEndLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        count=startWork;
                        }
                        else count=timeEndLanch;
                        while(count.getHours()<endWork.getHours()){
                            //System.out.println("Проверяем время после обеда "+count);
                            int scout=0;
                            Time tcount=new Time(0,00,00);
                            for(int i=0;i<busyTime.size();i++){
                                //System.out.println("busy="+busyTime.get(i)+" count="+count);
                                int bh=(busyTime.get(i)).getHours(),ch=count.getHours(),bm=(busyTime.get(i)).getMinutes(),cm=count.getMinutes() ;
                                if( (bh!=ch && bm!=cm) || (bh!=ch && bm==cm) || (bh==ch && bm!=cm) ){
                                    tcount.setHours(ch);
                                    tcount.setMinutes(cm);
                                    //tcount=new Time(ch,cm,00);
                                    //System.out.println("Обнаружено свободное?"+tcount);
                                    scout++;

                                }
                            }
                            if(scout==busyTime.size()){
                                //System.out.println("Запись свободного tcount="+tcount);
                                //freeTime.add(tcount);
                                dataTable[y][0]=tcount;
                                y++;
                            } else{
                                Time se=new Time(count.getHours(),count.getMinutes(),00);
                                dataTable[y][0]=se;
                                ArrayList als=new ArrayList();
                                String sql090="SELECT * FROM hospital.listreservations WHERE CodeDoctor='"+codeDr+"' AND Date='"+strDate+"' AND Time='"+se+"';";
                                connDB.setDataFromDatabase(sql090,als,7);
                                dataTable[y][1]=String.valueOf(als.get(0));
                                y++;
                            }
                            count.setMinutes(count.getMinutes()+durRec);
                        }
                    }//конец if


                if(busyTime.size()==0) {
                    for(int i=0;i<dataTable.length;i++){
                        dataTable[i][0]=freeTime.get(i);
                        //System.out.println(dataTable[i][0]+" "+dataTable[i][1]);
                    }
                }
                   
                    
                
                }catch(NullPointerException njpe){
                    return null;
                }
        
        
        
        
        
        
        
        
      
        return dataTable;
    }
    
    public JPanel drawingEmploymentDay(){
        bottomPanel.removeAll();
        bottomPanel.setLayout(new BorderLayout());
        Font font1 = new Font("VerdaFontna", Font.PLAIN, 10);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
        lbDate.setFont(font1);
        bottomPanel.add(lbDate,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        bottomPanel.revalidate();
        
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
        JPanel lblPanel=new JPanel(new GridLayout(3,1));
        lblPanel.setBackground(Color.WHITE);
        
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Дневная занятость");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
        
        JPanel sybtitlePanel=new JPanel();
        sybtitlePanel.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel);
        JLabel lblst=new JLabel(" ");
        lblst.setFont(font2);
        sybtitlePanel.add(lblst);
        JPanel sybtitlePanel2=new JPanel();
        sybtitlePanel2.setBackground(Color.WHITE);
        lblPanel.add(sybtitlePanel2);
        JLabel lblst2=new JLabel("Для вывода информации заполните формы");
        lblst2.setFont(font2);
        sybtitlePanel2.add(lblst2);
        
        JPanel datetimePanel=new JPanel(new GridLayout(2,1));
        //datetimePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        jp.add(datetimePanel,BorderLayout.PAGE_START);
        datetimePanel.add(lblPanel);
        JPanel paramPan=new JPanel(new GridLayout(2,1));
        paramPan.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        datetimePanel.add(paramPan);
        
        JPanel dPan=new JPanel();
        JLabel lblData=new JLabel("Дата (гггг-мм-дд)");
        //JTextField tData=new JTextField(10);
        JSpinner dateRec = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor jahr = new JSpinner.DateEditor(dateRec, "yyyy-MM-dd");
        //jahr.getTextField().setEditable(false);
        dateRec.setEditor(jahr);
        dPan.add(lblData);
        dPan.add(dateRec);
        
        JButton btnOk=new JButton("Ок");
        dPan.add(btnOk);
        
        paramPan.add(dPan);
        
        JPanel panBox=new JPanel();
        panBox.setVisible(false);
        paramPan.add(panBox);
        
        Date todayDate=new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        btnOk.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                try{
                    String[] part2 = format2.format(todayDate).trim().split("-");//yyyy-mm-dd
                    int yy2=Integer.valueOf(part2[0]);
                    int mm2=Integer.valueOf(part2[1]);
                    int dd2=Integer.valueOf(part2[2]);//число нового дня
                    
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
                    Date tdate=(Date)dateRec.getValue();
                    strDate=format1.format(tdate);
                    //strDate=tData.getText();//для записи в базу
                    //System.out.println("Ввели дату = " + strDate);
                    String[] part = strDate.trim().split("-");//yyyy-mm-dd
                    int yy=Integer.valueOf(part[0]);
                    int mm=Integer.valueOf(part[1]);
                    int dd=Integer.valueOf(part[2]);//число нового дня
                    if(yy<yy2 || (yy==yy2 && mm<mm2) || (yy==yy2 && mm==mm2 && dd<dd2) ){
                        JOptionPane.showMessageDialog(jp,"Введена прошедшая дата. Будет предложена информация на "+format1.format(todayDate),"Уведомление", 
                                        JOptionPane.INFORMATION_MESSAGE);
                        strDate=format1.format(todayDate);
                    }
                    //System.out.println("Введнное число искореженное = " + yy+"-"+mm+"-"+dd);
                }catch(NumberFormatException e2){
                    JOptionPane.showMessageDialog(jp,"Дата не была введена. Будет предложена информация на "+format1.format(todayDate),"Уведомление", 
                                        JOptionPane.INFORMATION_MESSAGE);
                    strDate=format1.format(todayDate);
                }catch(NullPointerException ne){
                    //System.out.println("Вылез я");
                }
                panBox.setVisible(true);
            }
        });
        
        JPanel panSpec=new JPanel();
        panBox.add(panSpec);
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
        JLabel lblSpec=new JLabel("Специальность врача");
        String[] itemsSpec=new String[listSpec.size()];
        int[] itemsSpecCode=new int[listSpec.size()];
        for(int i=0;i<listSpec.size();i++){
            itemsSpec[i]=(listSpec.get(i)).name;
            itemsSpecCode[i]=(listSpec.get(i)).codeSpec;
            //System.out.println(itemsSpec[i]);
        }
        JComboBox cbSpec = new JComboBox(itemsSpec);
        panSpec.add(lblSpec);
        panSpec.add(cbSpec);

        JPanel panDr=new JPanel();
        panBox.add(panDr);
        
        cbSpec.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent ae) {
                panBox.removeAll();
                panBox.add(panSpec);
                panBox.add(panDr);
                panBox.revalidate();
                int it=cbSpec.getSelectedIndex();
                spec=itemsSpecCode[it];
                //System.out.println("spec="+spec);
                ArrayList<Doctor> listDr=new ArrayList();
                String sql03="SELECT * FROM doctors WHERE Specialization="+(spec)+";";
                connDB.setDataFromDatabase(sql03, listDr,3);
                String[] itemsDr=new String[listDr.size()];
                for(int i=0;i<listDr.size();i++){
                    itemsDr[i]=(listDr.get(i)).name;
                    //System.out.println(itemsDr[i]);
                }
                //itemsDr[listDr.size()]="-любой-";
                
                JPanel panDr=new JPanel();
                panBox.add(panDr);
                JLabel lblDr=new JLabel("Фамилия врача");
                JComboBox cbDr = new JComboBox(itemsDr);
                panDr.add(lblDr);
                panDr.add(cbDr);
                cbDr.addActionListener(new ActionListener() { //слушатель на выбор врача
                    public void actionPerformed(ActionEvent ae) {
                        jp.repaint();
                        jp.revalidate();
                        int y=cbDr.getSelectedIndex();
                        codeDr=listDr.get(y).codeDr;
                        if(codeDr==(-1)){
                            JOptionPane.showMessageDialog(jp,"Изменение записей на прошедшее число невозможна ","Уведомление", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                            centerPanel.removeAll();
                            JPanel panRec=drawingEmploymentDay();
                            JScrollPane jsp=new JScrollPane(panRec);
                            centerPanel.add(jsp);
                            centerPanel.revalidate();
                        }else{
                            dawingTableWork(jp,datetimePanel,cbDr);
                            
                            
                        }//конец else
                    }          
                  });//конец обработки списка врачей
                        
                       
            }    
         });//конец спец
        
        return jp;
    }
    
    public void dawingTableWork(JPanel jp,JPanel datetimePanel,JComboBox cbDr){
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
        jp.removeAll();                                       
                            jp.add(datetimePanel,BorderLayout.PAGE_START);
                            jp.revalidate();
                            
                            try{
                                String g="00:00:00";
                                String h=null;
                                ArrayList<Time> freeTime=funcPatient.getFreeTime(codeDr,strDate,g,g);
                            //System.out.println("Отправили в getFreeTime "+strDate+" "+g+" "+h+" Код="+codeDr);
//                            for(int i=0;i<freeTime.size();i++){
//                                  System.out.println(freeTime.get(i));
//                                  //System.out.println("\n");
//                            }
                            String[] colHeads = {"Время","Записанный пациент"};
                            
                            Object[][] dataTimetable=getWorkTime(); //массив данных для таблицы
                            
                            JTable table=new JTable(dataTimetable,colHeads);
                            JScrollPane jsp=new JScrollPane(table);
                            //panelTable.add(jsp,BorderLayout.CENTER);
                            jp.add(jsp,BorderLayout.CENTER);
                            
                            ListSelectionModel selModel2 = table.getSelectionModel();
                            selModel2.addListSelectionListener(new ListSelectionListener() {
                                public void valueChanged(ListSelectionEvent e) {
                                    bottomPanel.removeAll();
                                    JPanel btBotPan=new JPanel();
                                    bottomPanel.add(btBotPan,BorderLayout.EAST);
                                    JButton btnEdit=new JButton("Изменить");
                                    btnEdit.addActionListener(new ActionListener() { 
                                        public void actionPerformed(ActionEvent ae) {
                                            Time forBDTime=freeTime.get(0);
                                            try{
                                                Object forTime=dataTimetable[table.getSelectedRow()][0];
                                                forBDTime=(Time)forTime;
                                            //System.out.println(forBDTime);
                                                JPanel dp=new JPanel(new GridLayout(7,1));
                                                JLabel lbl=new JLabel("Для записи на прием к врачу были выбраны следующие параметры");
                                                dp.add(lbl);
                                                JLabel lbl0=new JLabel(" ");
                                                Font font = new Font("VerdaFontna", Font.PLAIN, 10);
                                                lbl0.setFont(font);
                                                dp.add(lbl0);
//                                                int TcodeSp=spec;
//                                                if(codeSpec!=0){
//                                                    TcodeSp=codeSpec-1;
//                                                }
                                                int poNumSpec=0;
                                                for(int i=0;i<listSpec.size();i++){
                                                    if(listSpec.get(i).codeSpec==spec)
                                                        poNumSpec=i;
                                                }
                                                //System.out.println("spec="+spec+" name="+listSpec.get(poNumSpec).name);
                                                JLabel lbl1=new JLabel("Специальность: "+listSpec.get(poNumSpec).name);
                                                //lbl1.setFont(font);
                                                dp.add(lbl1);
                                                String f=(String)cbDr.getSelectedItem();
                                                String par="ФИО врача: "+f;
                                                //String par="ФИО врача: "+listDr.get(TcodeDr).name;
                                                JLabel lbl2=new JLabel(par);
                                                dp.add(lbl2);
                                                int dw=funcPatient.getDayWeek(strDate)-1;
                                                if(dw==0)
                                                    dw=7;
                                                JLabel lbl3=new JLabel("Дата: "+strDate+" День недели: "+dw);
                                                dp.add(lbl3);
                                                JLabel lbl4=new JLabel("Время: "+forBDTime);
                                                dp.add(lbl4);
                                                
                                                JPanel jpp=new JPanel(new GridLayout(1,2));
                                                ArrayList<Patient> listPatient=new ArrayList();
                                                String sql01="SELECT * FROM patients";
                                                connDB.setDataFromDatabase(sql01, listPatient,1);
                                                JLabel lblPat=new JLabel("Пациент");
                                                String[] itemsPat=new String[listPatient.size()];
                                                for(int i=0;i<listPatient.size();i++){
                                                    String g="№"+String.valueOf((listPatient.get(i)).policy)+" "+((listPatient.get(i)).name);
                                                    itemsPat[i]=g;
                                                    //System.out.println(itemsPat[i]);
                                                }
                                                policy=listPatient.get(0).policy;
                                                JComboBox cbPat = new JComboBox(itemsPat);
                                                cbPat.addActionListener(new ActionListener() { 
                                                    public void actionPerformed(ActionEvent ae) {
                                                        int t=cbPat.getSelectedIndex();
                                                        policy=listPatient.get(t).policy;
                                                        //System.out.println(policy);
                                                    }
                                                });
                                                jpp.add(lblPat);
                                                jpp.add(cbPat);
                                                dp.add(jpp);
                                                

                                                int res = JOptionPane.showConfirmDialog(centerPanel, dp, "Подтверждение записи на прием",
                                                              JOptionPane.PLAIN_MESSAGE);//удалила JOptionPane.QUESTION_MESSAGE
                                                if (res == JOptionPane.OK_OPTION){
                                                    Object forPol=dataTimetable[table.getSelectedRow()][1];
                                                    String forBDPol=String.valueOf(forPol);
                                                    if( (forBDPol+"n").equals("nulln")){
                                                        //System.out.println("Пустая строка INSERT");
                                                        String sql="INSERT INTO `hospital`.`listreservations` (`Date`,  `Time`, `CodePatient`, `CodeDoctor`) "
                                                                                + "VALUES ('"+strDate+"',  '"+forBDTime+"', '"+policy+"', '"+codeDr+"');";
                                                        connDB.updateDataFromDatabase(sql);
                                                    }
                                                    else {
                                                        //System.out.println("UPDATE forBDPol="+forBDPol);
                                                        String sql="UPDATE `hospital`.`listreservations` SET `CodePatient`='"+policy+"' "
                                                                + "WHERE `Date`='"+strDate+"' AND Time='"+forBDTime+"' AND CodeDoctor='"+codeDr+"';";
                                                        connDB.updateDataFromDatabase(sql);
                                                    }
                                                    JOptionPane.showMessageDialog(centerPanel,"Данные успешно изменены","Уведомление", 
                                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                    dawingTableWork(jp,datetimePanel,cbDr);                                                
                                                }
                                                
                                                
                                                
                                                
                                            }catch(ArrayIndexOutOfBoundsException e){
                                                JOptionPane.showMessageDialog(centerPanel,"Время не было выбрано","Уведомление", 
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                            }
                                           
                                        }
                                    });
                                    btBotPan.add(btnEdit);
                                    JButton btnRem=new JButton("Отмена");
                                    btnRem.addActionListener(new ActionListener() { 
                                        public void actionPerformed(ActionEvent ae) {
                                            centerPanel.removeAll();
                                            JPanel panRec=drawingEmploymentDay();
                                            JScrollPane jsp=new JScrollPane(panRec);
                                            centerPanel.add(jsp);
                                            centerPanel.revalidate();
                                            bottomPanel.removeAll();
                                            bottomPanel.setLayout(new BorderLayout());
                                            Font font = new Font("Verdana", Font.PLAIN, 10);
                                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                            Date d=new Date();
                                            JLabel lbDate=new JLabel("Сегодня "+format1.format(d));
                                            lbDate.setFont(font);
                                            bottomPanel.add(lbDate,BorderLayout.EAST);
                                            bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                                            bottomPanel.repaint();
                                        }
                                    });
                                    btBotPan.add(btnRem);
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();
                                }
                             });//конец слушателя таблицы
                         
                            }catch(NullPointerException gt ){
                                JOptionPane.showMessageDialog(jp,"Нет времени доступного для приема","Уведомление", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                                centerPanel.removeAll();
                                JPanel panRec=drawingEmploymentDay();
                                JScrollPane jsp=new JScrollPane(panRec);
                                centerPanel.add(jsp);
                                centerPanel.revalidate();
                            }  
    }
}


