package hospital;

import fact.Specialization;
import fact.Reservation;
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
import java.util.GregorianCalendar;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class FunctionalPatient {
    public ConnectionDataBase connDB;
    public int policyUser;
    public JPanel centerPanel;
    public JPanel bottomPanel;
    
    public int policy;
    public int codeDr;
    public String strDate;
    public Date date;
    public Time time;
    public Time desiredTime1;
    public Time desiredTime2;
    public int spec;
    
     
    public FunctionalPatient(JPanel centerPanel,JPanel bottomPanel){
        connDB=new ConnectionDataBase();
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4);
        policyUser=(tecData.get(0)).policyUser;
        this.centerPanel=centerPanel;
        this.bottomPanel=bottomPanel;
    }
    
    public Object[][] getActiveRecord(){
        //System.out.println("Зашли в метод getActiveRecord");
        
        ArrayList<Doctor> listDoctor=new ArrayList();
        String sql03="SELECT * FROM doctors";
        connDB.setDataFromDatabase(sql03, listDoctor,3);
        Date d=new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Reservation> listReservation=new ArrayList();
        String sql04="SELECT * FROM listreservations WHERE CodePatient='"+policyUser+"' AND (Date>'"+format1.format(d)+"' OR Date='"+format1.format(d)+"');";
        connDB.setDataFromDatabase(sql04, listReservation,5);
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
            
        Object[][] dataTimetable=new Object[listReservation.size()][5]; //массив данных для таблицы
        for(int i=0;i<dataTimetable.length;i++){
            dataTimetable[i][0]=listReservation.get(i).regDate;
            //System.out.println("date="+dataTimetable[i][0]);
            dataTimetable[i][1]=listReservation.get(i).time;
            //System.out.println("time="+dataTimetable[i][1]);
            
            int cDr=listReservation.get(i).codeDoctor;
            ArrayList<Doctor> listDr1=new ArrayList();
            String sql033="SELECT * FROM doctors WHERE CodeDr="+(cDr)+";";
            connDB.setDataFromDatabase(sql033, listDr1,3);
            dataTimetable[i][3]=listDr1.get(0).name;
            
            int cSp=listDr1.get(0).specialization;
            ArrayList<Specialization> listSpec1=new ArrayList();
            String sql031="SELECT * FROM specialization WHERE CodeSpec="+(cSp)+";";
            connDB.setDataFromDatabase(sql031, listSpec1,2);
            dataTimetable[i][2]=listSpec1.get(0).name;
            //System.out.println("spec="+dataTimetable[i][2]);
            //System.out.println("codeDr="+dataTimetable[i][3]);
            dataTimetable[i][4]=listReservation.get(i).comment;
            //System.out.println("код спец"+cSp+" Спец "+dataTimetable[i][2]+" код вр"+cDr+" врач"+dataTimetable[i][3]);
        }
        
        return dataTimetable;
    }
    
    public JPanel drawingActRec(){
        JPanel jp=new JPanel(new BorderLayout());
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        
        JPanel lblPanel=new JPanel(new GridLayout(2,3));
        lblPanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lbl=new JLabel("Предстоящие посещения");
        lbl.setFont(font);
        JLabel l2=new JLabel(" ");
        JLabel l3=new JLabel(" ");
        JLabel l4=new JLabel(" ");
        lblPanel.add(l1);
        lblPanel.add(lbl);
        lblPanel.add(l2);
        lblPanel.add(l3);
        lblPanel.add(l4);
        Object[][] dataForTable=getActiveRecord();
        String[] colHeads = {"Дата","Время приема","Специализация","Врач","Комментарий"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        
        return jp;
    }
    
    public Object[][] getAllRecord(){
        //System.out.println("Зашли в метод getAllRecord");
        //ArrayList allRec=new ArrayList();
        
        ArrayList<Doctor> listDoctor=new ArrayList();
        String sql03="SELECT * FROM doctors";
        connDB.setDataFromDatabase(sql03, listDoctor,3);
        ArrayList<Reservation> listReservation=new ArrayList();
        String sql04="SELECT * FROM listreservations WHERE CodePatient='"+policyUser+"';";
        connDB.setDataFromDatabase(sql04, listReservation,5);
        ArrayList<Specialization> listSpec=new ArrayList();
        String sql02="SELECT * FROM specialization";
        connDB.setDataFromDatabase(sql02, listSpec,2);
            
        //String[] colHeads = {"Дата","Время приема","Специлизация","Врач","Комментарий врача"};
        Object[][] dataTimetable=new Object[listReservation.size()][5]; //массив данных для таблицы
        for(int i=0;i<dataTimetable.length;i++){
            dataTimetable[i][0]=listReservation.get(i).regDate;
            //System.out.println("date="+dataTimetable[i][0]);
            dataTimetable[i][1]=listReservation.get(i).time;
            //System.out.println("time="+dataTimetable[i][1]);
            
            int cDr=listReservation.get(i).codeDoctor;
            ArrayList<Doctor> listDr1=new ArrayList();
            String sql033="SELECT * FROM doctors WHERE CodeDr="+(cDr)+";";
            connDB.setDataFromDatabase(sql033, listDr1,3);
            dataTimetable[i][3]=listDr1.get(0).name;
            
            int cSp=listDr1.get(0).specialization;
            ArrayList<Specialization> listSpec1=new ArrayList();
            String sql031="SELECT * FROM specialization WHERE CodeSpec="+(cSp)+";";
            connDB.setDataFromDatabase(sql031, listSpec1,2);
            dataTimetable[i][2]=listSpec1.get(0).name;
            //System.out.println("spec="+dataTimetable[i][2]);
            //System.out.println("codeDr="+dataTimetable[i][3]);
            dataTimetable[i][4]=listReservation.get(i).comment;
            //System.out.println("код спец"+cSp+" Спец "+dataTimetable[i][2]+" код вр"+cDr+" врач"+dataTimetable[i][3]);
        }
        
//        if(dataTimetable.length!=0){
//            allRec.add(dataTimetable);
//            allRec.add(colHeads);
//        }
        
        return dataTimetable;
    }
    
    public JPanel drawingAllRec(){
        JPanel jp=new JPanel(new BorderLayout());
        
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        
        JPanel lblPanel=new JPanel(new GridLayout(2,3));
        lblPanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lbl=new JLabel("Все посещения");
        lbl.setFont(font);
        JLabel l2=new JLabel(" ");
        JLabel l3=new JLabel(" ");
        JLabel l4=new JLabel(" ");
        lblPanel.add(l1);
        lblPanel.add(lbl);
        lblPanel.add(l2);
        lblPanel.add(l3);
        lblPanel.add(l4);
        Object[][] dataForTable=getAllRecord();
        String[] colHeads = {"Дата","Время приема","Специлизация","Врач","Комментарий врача"};
        JTable tabActRec=new JTable( dataForTable,colHeads);
        JScrollPane jsp=new JScrollPane(tabActRec);
        jp.add(lblPanel,BorderLayout.PAGE_START);
        jp.add(jsp,BorderLayout.CENTER);
        
        
        return jp;
    }
    
    public int getDayWeek(String date){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");//для запросов
            Date todayDate = new Date(); //сегодня
            String strDate=format1.format(todayDate);
            String[] part = strDate.trim().split("-");//yyyy-mm-dd
            int yySt=Integer.valueOf(part[0]);
            int mmSt=Integer.valueOf(part[1]);
            int ddSt=Integer.valueOf(part[2]);//число сегодня
            int dayWeek=0;

            int yy=yySt,mm=mmSt,dd=ddSt;
            try{
                strDate=date;//для записи в базу
                //System.out.println("Введенное число = " + date);
                part = strDate.trim().split("-");//yyyy-mm-dd
                yy=Integer.valueOf(part[0]);
                mm=Integer.valueOf(part[1]);
                dd=Integer.valueOf(part[2]);//число нового дня
                //System.out.println("Введнное число искореженное = " + yy+"-"+mm+"-"+dd);
                if( yy<yySt || (yy==yySt && mm<mmSt)){
                    //System.out.println("Прошедшая дата");
                    return 0;
                }
            }catch(NullPointerException ne){
                //System.out.println("Вылез я");
            }
        
            Long time = todayDate.getTime(); 
                long anotherDate=0;
                if(yy<yySt || (yy==yySt && mm<mmSt) || (yy==yySt && mm==mmSt && dd<ddSt)){//дата уже прошла
                    return 0;
//                    System.out.println(yy+"-"+mm+"-"+dd);
//                    anotherDate=0;
                } else 
                    if(mmSt>=mm){
                    anotherDate = dd-ddSt; //старое-новое
                    }
                    else {//дата в след месяце
                        if(mmSt==2){
                            anotherDate=28-ddSt+dd;
                        } else {
                            if(mmSt==1 || mmSt==3 || mmSt==5 ||mmSt==7 || mmSt==8 || mmSt==10 || mmSt==12){
                                anotherDate=31-ddSt+dd;
                            }
                            else anotherDate=30-ddSt+dd;
                        }
                    }
                time = time + (60*60*24*1000*anotherDate); 
                Date newDate = new Date(time);//новая дата
    //            System.out.println("Разница между днями = " + anotherDate);
    //            System.out.println("newDate = " + newDate);

                Calendar calDate = new GregorianCalendar();
                calDate.setTime(newDate);
                dayWeek = calDate.get(Calendar.DAY_OF_WEEK); //новый день недели
    //            System.out.println("День недели введенного числа = "+dayWeek);
    //            System.out.println("Само число = "+calDate);

        return dayWeek;
    }
    
    public int getDrMinLoad(int codeSpec,String strDate,String tTime1,String tTime2){//код спец,стринговая дата
        int codeDrMinLoad=0;
        int dayWeek=getDayWeek(strDate);
        if(dayWeek==0)
            return (-1);
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4);
        //Найдем врача с минимальной загрузкой
//            System.out.println("Врач не выбран");
//            for(int i=0;i<listDr.size();i++)
//                System.out.println(listDr.get(i).codeDr);
            //System.out.println(codeSpec);
        ArrayList<Doctor> listDr=new ArrayList();
        String sql0="SELECT * FROM doctors WHERE Specialization="+codeSpec+";";
        connDB.setDataFromDatabase(sql0, listDr,3);
        
            ArrayList<ArrayList> alFreeTime=new ArrayList();
            for(int i=0;i<listDr.size();i++){
                ArrayList<Time> freeTime=new ArrayList();//свободное время конкретного врача 
                ArrayList<Time> busyTime=new ArrayList();//занятое время конкретного врача
                String sql02="SELECT * FROM hospital.listreservations WHERE CodeDoctor="+listDr.get(i).codeDr+" AND Date='"+strDate+"';";
                connDB.setDataFromDatabase(sql02,busyTime,6);
                
                ArrayList<Doctor> listForTimetableDoctor=new ArrayList();//коллекция, чтобы не добавлять еще один if в conneection
                String sql03="SELECT * FROM hospital.doctors WHERE CodeDr="+listDr.get(i).codeDr+";";
                connDB.setDataFromDatabase(sql03, listForTimetableDoctor,3);
                //System.out.println( "День недели "+dayWeek+" c "+((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 1)+" до "+((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 2) );
                Time startWork=((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 1);
                Time endWork=((listForTimetableDoctor.get(0)).ttDoctor).getDayStarTime(dayWeek, 2);
                //System.out.println( "День недели "+dayWeek+" c "+startWork+" до "+endWork);
                Time timeStartLanch=(tecData.get(0)).timeLanch;
                int minut=((tecData.get(0)).timeLanch).getMinutes()+(tecData.get(0)).durLanch;
                Time timeEndLanch=new Time(((tecData.get(0)).timeLanch).getHours(),minut,((tecData.get(0)).timeLanch).getSeconds());
                //System.out.println("Перерыв с "+timeStartLanch+" до "+timeEndLanch);
                int durRec=(tecData.get(0)).durRec;
                Time desiredTime1=new Time(0,00,00);//=new Time(startWork.getHours(),startWork.getMinutes(),00);
                Time desiredTime2=new Time(0,00,00);//(endWork.getHours(),endWork.getMinutes(),00);
                try{
                    desiredTime1=new Time(startWork.getHours(),startWork.getMinutes(),00);
                    desiredTime2=new Time(endWork.getHours(),endWork.getMinutes(),00);
                }catch(NullPointerException n){
    //                JOptionPane.showMessageDialog(jfrm,"Свободного времени для приема нет","Уведомление", 
    //                                              JOptionPane.INFORMATION_MESSAGE);
                }            

                
                   
                    String[] part = tTime1.trim().split(":");//yyyy-mm-dd
                    int hh=Integer.valueOf(part[0]);
                    int mm=Integer.valueOf(part[1]);
                    int ss=Integer.valueOf(part[2]);//число сегодня
                    if( hh!=0 && mm!=00 && ss!=0){
                        desiredTime1=new Time(hh,mm,ss);//нижняя граница
                        startWork.setHours(desiredTime1.getHours());
                        startWork.setMinutes(desiredTime1.getMinutes());
                    }
                    

                    String[] part2 = tTime2.trim().split(":");//yyyy-mm-dd
                    int hh2=Integer.valueOf(part2[0]);
                    int mm2=Integer.valueOf(part2[1]);
                    int ss2=Integer.valueOf(part2[2]);//число сегодня
                    if( hh!=0 && mm!=00 && ss!=0){
                        desiredTime2=new Time(hh2,mm2,ss2);//нижняя граница
                        //desiredTime2=Time.valueOf(tTime2);//верхняя граница
                        endWork.setHours(desiredTime2.getHours());
                        endWork.setMinutes(desiredTime2.getMinutes());
                    }
                    


                //System.out.println("Желаемое время с "+startWork+" до "+endWork);

                int countNullTimetable=0;
                try{
                    if(busyTime.size()!=0 ){
                        Time count=new Time( startWork.getHours(),startWork.getMinutes(),00 );
                        if( endWork.getHours()<timeStartLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        timeStartLanch=endWork;
                        }
                        while(count.getHours()<timeStartLanch.getHours()){
                            //System.out.println("Проверяем время до обеда "+count);
                            int scout=0;
                            Time tcount=new Time(0,00,00);
                            for(int y=0;y<busyTime.size();y++){
                                //System.out.println("busy="+busyTime.get(i)+" count="+count);
                                int bh=(busyTime.get(y)).getHours(),ch=count.getHours(),bm=(busyTime.get(y)).getMinutes(),cm=count.getMinutes() ;
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
                                freeTime.add(tcount);
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
                            for(int y=0;y<busyTime.size();y++){
                                //System.out.println("busy="+busyTime.get(i)+" count="+count);
                                int bh=(busyTime.get(y)).getHours(),ch=count.getHours(),bm=(busyTime.get(y)).getMinutes(),cm=count.getMinutes() ;
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
                                freeTime.add(tcount);
                            }
                            count.setMinutes(count.getMinutes()+durRec);
                        }
                    }


                if(busyTime.size()==0) {
                    //Time count=startWork;
                    Time count=new Time( startWork.getHours(),startWork.getMinutes(),00 );
                    //System.out.print("b0 count="+count);
                    if( endWork.getHours()<timeStartLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        timeStartLanch=endWork;
                        }
                    while(count.getHours()<timeStartLanch.getHours()){
                        int ch=count.getHours(),cm=count.getMinutes() ;
                        Time tcount=new Time(ch,cm,00);
                        //System.out.println(count);
                        freeTime.add(tcount);
                        count.setMinutes(count.getMinutes()+durRec);
                    }
                    if( startWork.getHours()>timeEndLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        count=startWork;
                    }
                    else count=timeEndLanch;
                    while(count.getHours()<endWork.getHours()){
                        int ch=count.getHours(),cm=count.getMinutes() ;
                        Time tcount=new Time(ch,cm,00);
                        //System.out.println("Добавили"+tcount);
                        freeTime.add(tcount);
                        count.setMinutes(count.getMinutes()+durRec);
                    }

                }
                }catch(NullPointerException npe){
//                    JOptionPane.showMessageDialog(jfrm,"Свободного времени для приема нет","Уведомление", 
//                                                  JOptionPane.INFORMATION_MESSAGE);
                    countNullTimetable++;

                }
               alFreeTime.add(freeTime);
            }//конец for
            int[] sizeFreeTime=new int[alFreeTime.size()];
            for(int i=0;i<alFreeTime.size();i++){
                sizeFreeTime[i]=alFreeTime.get(i).size();
                //System.out.println(sizeFreeTime[i]);
            }
            int[] masCodeDr=new int[listDr.size()];
            //System.out.println("Массив кодов врачей");
            for(int i=0;i<listDr.size();i++){
                masCodeDr[i]=listDr.get(i).codeDr;
                //System.out.println(masCodeDr[i]);
            }
            for (int i = sizeFreeTime.length - 1; i >= 2; i--) {
                for (int j = 0; j < i; j++) {
                     if (sizeFreeTime[j] < sizeFreeTime[j+1]) {
                         int temp = sizeFreeTime[j];
                         sizeFreeTime[j] = sizeFreeTime[j+1];
                         sizeFreeTime[j+1] = temp;
                         
                         temp=masCodeDr[j];
                         masCodeDr[j]=masCodeDr[j+1];
                         masCodeDr[j+1]=temp;
                    }
                }
            }
            int j=0;
            if(sizeFreeTime.length!=0){
//                System.out.println("Кол-во свободного времени у врачей");
//                for(int i=0;i<sizeFreeTime.length;i++){
//                    System.out.println(sizeFreeTime[i]+" у "+masCodeDr[i]);
//                }
                
                try{
                    while(sizeFreeTime[j]==0){
                    j++;
                    }
                    codeDrMinLoad=masCodeDr[j];
                    //System.out.println("Код врача с минимальной загрузкой codeDr="+codeDrMinLoad);
                }catch(ArrayIndexOutOfBoundsException tr){
                    return 0;
                }
            }else  return 0;
        
        
        return codeDrMinLoad;
    }
    
    public JPanel drawingRecord(){
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4);
        policy=tecData.get(0).policyUser;
        JPanel jp=new JPanel(new BorderLayout());
        
        Font font = new Font("Verdana", Font.ROMAN_BASELINE, 15);
        Font font2 = new Font("Verdana", Font.ROMAN_BASELINE, 11);
        
//        JPanel upPanel=new JPanel(new GridLayout(3,1));
//        jp.add(upPanel,BorderLayout.PAGE_START);
        
        JPanel lblPanel=new JPanel();
        lblPanel.setBackground(Color.WHITE);
        lblPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JPanel titlePanel=new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel l1=new JLabel(" ");
        JLabel lblt=new JLabel("Запись на прием");
        lblt.setFont(font);
        JLabel l2=new JLabel(" ");
        titlePanel.add(l1);
        titlePanel.add(lblt);
        titlePanel.add(l2);
        lblPanel.add(titlePanel);
       
        
        JPanel datetimePanel=new JPanel(new BorderLayout());
        datetimePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        jp.add(datetimePanel,BorderLayout.PAGE_START);
        datetimePanel.add(lblPanel,BorderLayout.PAGE_START);
        JPanel dPan=new JPanel();
        JLabel lblData=new JLabel("Дата (гггг-мм-дд)");
        JTextField tData=new JTextField(10);
        dPan.add(lblData);
        dPan.add(tData);
        JPanel t1Pan=new JPanel();
        JLabel lblTime1=new JLabel("Желательное время с (чч:мм:00)");
        JTextField tTime1=new JTextField(10);
        t1Pan.add(lblTime1);
        t1Pan.add(tTime1);
        JPanel t2Pan=new JPanel();
        JLabel lblTime2=new JLabel("Заканчивая (чч:мм:00)");
        JTextField tTime2=new JTextField(10);
        t2Pan.add(lblTime2);
        t2Pan.add(tTime2);
        JPanel btPan=new JPanel();
        JButton btnOk=new JButton("Ок");
        btPan.add(btnOk);
        
//        datetimePanel.add(dPan);
//        datetimePanel.add(t1Pan);
//        datetimePanel.add(t2Pan);
//        datetimePanel.add(btPan);
        
        JPanel exp=new JPanel(new GridLayout(3,1));
        JPanel panD=new JPanel();
        JPanel panT=new JPanel();
        //JPanel panOk=new JPanel();
        exp.add(panD);
        exp.add(panT);
        //exp.add(panOk);
        
        JLabel ld=new JLabel("Дата");
        JSpinner dateRec = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor jahr = new JSpinner.DateEditor(dateRec, "yyyy-MM-dd");
        //jahr.getTextField().setEditable(false);
        dateRec.setEditor(jahr);
        panD.add(ld);
        panD.add(dateRec);
        
        JLabel lt1=new JLabel("Желаемое время начиная с ");
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
        
        panT.add(lt1);
        panT.add(time1Rec);
        
        JLabel lt2=new JLabel(" до ");
        SpinnerDateModel model2=new SpinnerDateModel();
        JSpinner time2Rec = new JSpinner(model2);
        JSpinner.DateEditor jahr2 = new JSpinner.DateEditor(time2Rec, "kk:mm");
        model2.setValue(c.getTime());
        jahr2.getTextField().setEditable(true);
        time2Rec.setEditor(jahr2);
       
        panT.add(lt2);
        panT.add(time2Rec);
        panT.add(btPan);
        
        datetimePanel.add(exp,BorderLayout.CENTER); 
        //datetimePanel.add(btPan);        
        JPanel panBox=new JPanel();
        panBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panBox.setVisible(false);
        //upPanel.add(panBox);
        exp.add(panBox);
        
//        JPanel panTable=new JPanel();
//        panTable.setVisible(false);
//        jp.add(panTable,BorderLayout.CENTER);
        
        Date todayDate=new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        desiredTime1=new Time(0,00,00);
        desiredTime2=new Time(0,00,00);
        btnOk.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                try{
                    String[] part2 = format1.format(todayDate).trim().split("-");//yyyy-mm-dd
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
                        JOptionPane.showMessageDialog(jp,"Введена прошедшая дата. Будет предложена запись на "+format1.format(todayDate),"Уведомление", 
                                        JOptionPane.INFORMATION_MESSAGE);
                        strDate=format1.format(todayDate);
                    }
                }catch(NumberFormatException e2){
                    JOptionPane.showMessageDialog(jp,"Дата не была введена. Будет предложена запись на "+format1.format(todayDate),"Уведомление", 
                                        JOptionPane.INFORMATION_MESSAGE);
                    strDate=format1.format(todayDate);
                }catch(NullPointerException ne){
                    //System.out.println("Вылез я");
                }
                try{
                    SimpleDateFormat format1 = new SimpleDateFormat("kk:mm"); 
                    Date tdate1=(Date)time1Rec.getValue();
                    String dc1=format1.format(tdate1)+":00";
                    Time tr1=Time.valueOf(dc1);
                    //System.out.println("Введенное значение1: " + tr1);
                    Date tdate2=(Date)time2Rec.getValue();
                    String dc2=format1.format(tdate2)+":00";
                    Time tr2=Time.valueOf(dc2);
                    
                    if(tr1.getHours()>tr2.getHours() || (tr1.getHours()==tr2.getHours() && tr1.getMinutes()>tr2.getMinutes()) ){
                        JOptionPane.showMessageDialog(jp,"Введенное время некорректно и не будет учитываться","Уведомление", 
                                        JOptionPane.INFORMATION_MESSAGE);
                        desiredTime1.setHours(00);
                        desiredTime1.setMinutes(00);
                        desiredTime1.setSeconds(00);

                        desiredTime2.setHours(00);
                        desiredTime2.setMinutes(00);
                        desiredTime2.setSeconds(00);
                        
                    }
                    else{
                        desiredTime1.setHours(tr1.getHours());
                        desiredTime1.setMinutes(tr1.getMinutes());
                        desiredTime1.setSeconds(00);

                        desiredTime2.setHours(tr2.getHours());
                        desiredTime2.setMinutes(tr2.getMinutes());
                        desiredTime2.setSeconds(00);
                    }
                    
                }catch(IllegalArgumentException e2){
                    //System.out.println("Как желаемое время выбрано время начала приема");
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
                ArrayList<Doctor> listDr=new ArrayList();
                String sql03="SELECT * FROM doctors WHERE Specialization="+(spec)+";";
                connDB.setDataFromDatabase(sql03, listDr,3);
                String[] itemsDr=new String[listDr.size()+1];
                for(int i=0;i<listDr.size();i++){
                    itemsDr[i]=(listDr.get(i)).name;
                    //System.out.println(itemsDr[i]);
                }
                itemsDr[listDr.size()]="-любой-";
                
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
                        if(y<listDr.size()){
                             codeDr=listDr.get(y).codeDr;
                             //System.out.println("Зашли в if codeDr="+codeDr);
                             //System.out.println("if spec="+spec);
                        }
                           
                        else {//выбран вариант -любой-
                            String g=String.valueOf(desiredTime1);
                            String h=String.valueOf(desiredTime1);
                            codeDr=getDrMinLoad(spec,strDate,g,h);
                            //System.out.println("Выбрали -любой- "+strDate+" "+g+" "+h+" Получили "+codeDr);
                        }
                        if(codeDr==0){
                            JOptionPane.showMessageDialog(jp,"Нет свободных врачей ","Уведомление", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                        }else if(codeDr==(-1)){
                            JOptionPane.showMessageDialog(jp,"Запись на прошедшее число невозможна ","Уведомление", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                            centerPanel.removeAll();
                            JPanel panRec=drawingRecord();
                            JScrollPane jsp=new JScrollPane(panRec);
                            centerPanel.add(jsp);
                            centerPanel.revalidate();
                        }else{
                            jp.removeAll();                                        //ПОД ВОПРОСОМ
                            jp.add(datetimePanel,BorderLayout.PAGE_START);
                            jp.revalidate();
//                            String g=String.valueOf(desiredTime1);
//                            String h=String.valueOf(desiredTime1);
                            
                            try{
                            String g=String.valueOf(desiredTime1);
                            String h=String.valueOf(desiredTime2);
                            ArrayList<Time> freeTime=getFreeTime(codeDr,strDate,g,h);
                            //System.out.println("Отправили в getFreeTime "+strDate+" "+g+" "+h+" Код="+codeDr);
//                            for(int i=0;i<freeTime.size();i++){
//                                  System.out.println(freeTime.get(i));
//                                  //System.out.println("\n");
//                            }
                            String[] colHeads = {"Время начала приема"};
                            Object[][] dataTimetable=new Object[freeTime.size()][1]; //массив данных для таблицы
                            for(int i=0;i<dataTimetable.length;i++){
                                  //dataTimetable[i][0]=i;
                                  dataTimetable[i][0]=freeTime.get(i);
                                  //System.out.println("\n");
                            }
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
                                    JButton btnEdit=new JButton("Ок");
                                    btnEdit.addActionListener(new ActionListener() { 
                                        public void actionPerformed(ActionEvent ae) {
                                            //System.out.println("Нажали ок в таблице");
                                            Time forBDTime=freeTime.get(0);
                                            try{
                                                Object forTime=dataTimetable[table.getSelectedRow()][0];
                                                forBDTime=(Time)forTime;
                                            //System.out.println(forBDTime);
                                                JPanel dp=new JPanel(new GridLayout(6,1));
                                                JLabel lbl=new JLabel("Для записи на прием к врачу были выбраны следующие параметры");
                                                dp.add(lbl);
                                                JLabel lbl0=new JLabel(" ");
                                                lbl0.setFont(font);
                                                dp.add(lbl0);
//                                                int TcodeSp=spec;
//                                                if(codeSpec!=0){
//                                                    TcodeSp=codeSpec-1;
//                                                }
                                                ArrayList<Specialization> listSpec1=new ArrayList();
                                                String sql0311="SELECT * FROM specialization WHERE CodeSpec="+(spec)+";";
                                                connDB.setDataFromDatabase(sql0311, listSpec1,2);
                                                //System.out.println("spec="+spec);
                                                JLabel lbl1=new JLabel("Специальность: "+listSpec1.get(0).name);
                                                //System.out.println("Name spec="+listSpec1.get(0).name);
                                                //lbl1.setFont(font);
                                                dp.add(lbl1);
                                                String f=(String)cbDr.getSelectedItem();
                                                String par="ФИО врача: "+f;
                                                //System.out.println("Name Dr="+f);
                                                //String par="ФИО врача: "+listDr.get(TcodeDr).name;
                                                JLabel lbl2=new JLabel(par);
                                                dp.add(lbl2);
                                                int dw=getDayWeek(strDate)-1;
                                                if(dw==0)
                                                    dw=7;
                                                JLabel lbl3=new JLabel("Дата: "+strDate+" День недели: "+dw);
                                                dp.add(lbl3);
                                                JLabel lbl4=new JLabel("Время: "+forBDTime);
                                                dp.add(lbl4);
                                                
                                                //System.out.println("Специальность: "+listSpec1.get(0).name+" ФИО врача: "+f+" Дата: "+strDate+" День недели: "+dw+" Время: "+forBDTime);

                                                int res = JOptionPane.showConfirmDialog(centerPanel, dp, "Подтверждение записи на прием",
                                                              JOptionPane.PLAIN_MESSAGE);//удалила JOptionPane.QUESTION_MESSAGE
                                                if (res == JOptionPane.OK_OPTION){
                                                    //record(strDate,forBDTime,policy,codeDr);
                                                    String sql="INSERT INTO `hospital`.`listreservations` (`Date`,  `Time`, `CodePatient`, `CodeDoctor`) "
                                                                                + "VALUES ('"+strDate+"',  '"+forBDTime+"', '"+policy+"', '"+codeDr+"');";
                                                    connDB.updateDataFromDatabase(sql);
                                                    JOptionPane.showMessageDialog(centerPanel,"Запись успешно произведена","Уведомление", 
                                                                                                JOptionPane.INFORMATION_MESSAGE);
//                                                    JOptionPane.showMessageDialog(centerPanel,"Запись произведена","Уведомление", 
//                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                    centerPanel.removeAll();
                                                    JPanel panRec=drawingActRec();
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
                                            JPanel panRec=drawingRecord();
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
                                JPanel panRec=drawingRecord();
                                JScrollPane jsp=new JScrollPane(panRec);
                                centerPanel.add(jsp);
                                centerPanel.revalidate();
                            }   
                            
                            
                            
                        }//конец else
                    }          
                  });//конец обработки списка врачей
                        
                       
            }    
         });//конец спец
                
       
        
        
        
        
        
        return jp;
    }
    
    public ArrayList<Time> getFreeTime(int codeDr,String strDate,String tTime1,String tTime2){
        //System.out.println("Получили "+codeDr+" "+strDate+" "+tTime1+" "+tTime2);
        int dayWeek=getDayWeek(strDate);
        if(dayWeek==0)
            return null;
        //System.out.println("День недели "+dayWeek);
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4);            
        ArrayList<Time> freeTime=new ArrayList();//свободное время конкретного врача 
        ArrayList<Time> busyTime=new ArrayList();//занятое время конкретного врача
        String sql02="SELECT * FROM hospital.listreservations WHERE CodeDoctor="+codeDr+" AND Date='"+strDate+"';";
        connDB.setDataFromDatabase(sql02,busyTime,6);
  
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
           //System.out.println("Перерыв с "+timeStartLanch+" до "+timeEndLanch);
                int durRec=(tecData.get(0)).durRec;
                Time desiredTime1=new Time(0,00,00);//=new Time(startWork.getHours(),startWork.getMinutes(),00);
                Time desiredTime2=new Time(0,00,00);//(endWork.getHours(),endWork.getMinutes(),00);
                try{
                    desiredTime1=new Time(startWork.getHours(),startWork.getMinutes(),00);
                    desiredTime2=new Time(endWork.getHours(),endWork.getMinutes(),00);
                    //System.out.println("Были в try");
                }catch(NullPointerException n){
    //                JOptionPane.showMessageDialog(jfrm,"Свободного времени для приема нет","Уведомление", 
    //                                              JOptionPane.INFORMATION_MESSAGE);
                    //System.out.println("Проблемы с желаемым временем");
                } 
                
                String[] part1 = tTime1.trim().split(":");//yyyy-mm-dd
                int hh1=Integer.valueOf(part1[0]);
                    int mm1=Integer.valueOf(part1[1]);
                    int ss1=Integer.valueOf(part1[2]);//число сегодня
                    //System.out.println("Восстание машин? Распилили строку="+hh1+":"+mm1+":"+ss1);
                    if( !(hh1==0 && mm1==00 && ss1==0) ){
                        desiredTime1=new Time(hh1,mm1,ss1);//нижняя граница
                        startWork.setHours(desiredTime1.getHours());
                        startWork.setMinutes(desiredTime1.getMinutes());
                    }
                    
                    String[] part2 = tTime2.trim().split(":");//yyyy-mm-dd
                    int hh2=Integer.valueOf(part2[0]);
                    int mm2=Integer.valueOf(part2[1]);
                    int ss2=Integer.valueOf(part2[2]);//число сегодня
                    if( !(hh2==0 && mm2==00 && ss2==0) ){
                        desiredTime2=new Time(hh2,mm2,ss2);//нижняя граница
                        //desiredTime2=Time.valueOf(tTime2);//верхняя граница
                        endWork.setHours(desiredTime2.getHours());
                        endWork.setMinutes(desiredTime2.getMinutes());
                    }

          //System.out.println("Желаемое время с "+startWork+" до "+endWork);

                try{
                    if(busyTime.size()!=0 ){
                        //System.out.println("busyTime.size()!=0");
                        Time count=new Time( startWork.getHours(),startWork.getMinutes(),00 );
                        if( endWork.getHours()<timeStartLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        timeStartLanch=endWork;
                        }
                        while(count.getHours()<timeStartLanch.getHours()|| (count.getHours()==timeStartLanch.getHours() && count.getMinutes()<timeStartLanch.getMinutes())){
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
                                freeTime.add(tcount);
                            }
                            count.setMinutes(count.getMinutes()+durRec);
                        }
                        if( startWork.getHours()>timeEndLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        count=startWork;
                        }
                        else count=timeEndLanch;
                        while(count.getHours()<endWork.getHours()|| (count.getHours()==timeStartLanch.getHours() && count.getMinutes()<timeStartLanch.getMinutes())){
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
                                freeTime.add(tcount);
                            }
                            count.setMinutes(count.getMinutes()+durRec);
                        }
                    }


                if(busyTime.size()==0) {
                    //System.out.println("busyTime.size()==0");
                    ArrayList<Doctor> listTD=new ArrayList();//коллекция, чтобы не добавлять еще один if в conneection
                    String sql0321="SELECT * FROM hospital.doctors WHERE CodeDr="+codeDr+";";
                    connDB.setDataFromDatabase(sql0321, listTD,3);
                    Time sw=new Time(listTD.get(0).ttDoctor.getDayStarTime(dayWeek, 1).getHours(),listTD.get(0).ttDoctor.getDayStarTime(dayWeek, 1).getMinutes(),00 );
                    Time ew=new Time(listTD.get(0).ttDoctor.getDayStarTime(dayWeek, 2).getHours(),listTD.get(0).ttDoctor.getDayStarTime(dayWeek, 2).getMinutes(),00 );
                    //System.out.println("C "+sw+" до "+ew);
                    if( sw.getHours()==00 && sw.getMinutes()==00 && sw.getSeconds()==00 && ew.getHours()==00 && ew.getMinutes()==00 && ew.getSeconds()==00){
                        //System.out.println("Нерабочий день");
                        return null;
                    }else{
                    Time count=new Time( startWork.getHours(),startWork.getMinutes(),00 );
                    //System.out.print("b0 count="+count);
                    if( endWork.getHours()<timeStartLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        timeStartLanch=endWork;
                        }
                    while(count.getHours()<timeStartLanch.getHours() || (count.getHours()==timeStartLanch.getHours() && count.getMinutes()<timeStartLanch.getMinutes()) ){
                        int ch=count.getHours(),cm=count.getMinutes() ;
                        Time tcount=new Time(ch,cm,00);
                        //System.out.println("count="+count);
                        if( (count.getHours()>sw.getHours() || (count.getHours()==sw.getHours() && count.getMinutes()>sw.getMinutes()) || (count.getHours()==sw.getHours() && count.getMinutes()==sw.getMinutes()) )
                                && (count.getHours()<ew.getHours() || (count.getHours()==ew.getHours() && count.getMinutes()<ew.getMinutes()) ) ){
                            freeTime.add(tcount);
                        }
                        
                        count.setMinutes(count.getMinutes()+durRec);
                    }
                    if( startWork.getHours()>timeEndLanch.getHours() ){//если время начала работы > времени конца ланча, count=startWork
                        count=startWork;
                    }
                    else count=timeEndLanch;
                    while(count.getHours()<endWork.getHours()|| (count.getHours()==timeStartLanch.getHours() && count.getMinutes()<timeStartLanch.getMinutes())){
                        int ch=count.getHours(),cm=count.getMinutes() ;
                        Time tcount=new Time(ch,cm,00);
                        //System.out.println(count);
                        if( (count.getHours()>sw.getHours() || (count.getHours()==sw.getHours() && count.getMinutes()>sw.getMinutes()) || (count.getHours()==sw.getHours() && count.getMinutes()==sw.getMinutes()))
                                && (count.getHours()<ew.getHours() || (count.getHours()==ew.getHours() && count.getMinutes()<ew.getMinutes()) ) ){
                            freeTime.add(tcount);
                        }
                        count.setMinutes(count.getMinutes()+durRec);
                    }
                    
                }//end else

                }
                }catch(NullPointerException npe){
                    //System.out.println("С какого-то черта return null;");
                    return null;
//                    JOptionPane.showMessageDialog(jfrm,"Свободного времени для приема нет","Уведомление", 
//                                                  JOptionPane.INFORMATION_MESSAGE);
                    //countNullTimetable++;
                }

//                System.out.println("Свободное время:");
//                for(int i=0;i<freeTime.size();i++){
//                    System.out.println(freeTime.get(i));
//                }
               
                
        return freeTime;
    }
    
}
