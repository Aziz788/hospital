//Осуществляет соединение с базой данных

package hospital;

import fact.Specialization;
import fact.TimetableDoctor;
import fact.Reservation;
import fact.Patient;
import fact.TechnicalDataForRecord;
import fact.Doctor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;


public class ConnectionDataBase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/hospital";

    // Database credentials
    static final String USER = "root";
    static final String PASS ="atlanta"; //"ksololoh11";
    static Connection conn;
    static Statement stmt;
    
//    public ConnectionDataBase(){
//        //System.out.println("Кто бы знал, зачем я здесь?");
//    }
    
    public void setDataFromDatabase(String sql,ArrayList al,int type){//sql Select
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            //1-ый тип запроса: SELECT
                ResultSet rs = stmt.executeQuery(sql);
                //STEP 5: Extract data from result set
                
                if(type==1){//пациент
                    while (rs.next()) {
                    String name = rs.getString("Name");
                    int policy = Integer.valueOf(rs.getString("Policy"));
                    
                    Date regDate;
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    try{
                        regDate = rs.getDate("RegistrationDate");
                        //System.out.println(format1.format(regDate)); //25.02.2013 09:03
                    }catch(NullPointerException e){
                        regDate=new Date();
                        //System.out.println(format1.format(regDate));
                    }
                    
                    Patient dbPatient=new Patient(name,policy,regDate);
                    al.add(dbPatient);
                    //System.out.println(name + "  " + policy+" "+registrationDate);
                    }
                }
                if(type==2){//специализация
                    while (rs.next()) {
                    String name = rs.getString("Name");
                    int codeSpec = Integer.valueOf(rs.getString("CodeSpec"));
                    Specialization dbSpec=new Specialization(codeSpec,name);
                    al.add(dbSpec);
                    //System.out.println(name + "  " + codeSpec);
                    }
                }
                if(type==3){//врач
                    while (rs.next()) {
                    int code = Integer.valueOf(rs.getString("codeDr"));
                    String name = rs.getString("Name");
                    int spec = Integer.valueOf(rs.getString("Specialization"));
                    
                    Time ms=rs.getTime("MondayStarTime");
                    Time me=rs.getTime("MondayEndTime");
                    Time tus=rs.getTime("TuesdayStartTime");
                    Time tue=rs.getTime("TuesdayEndTime");
                    Time ws=rs.getTime("WednesdayStartTime");
                    Time we=rs.getTime("WednesdayEndTime");
                    Time ths=rs.getTime("ThursdayStartTime");
                    Time the=rs.getTime("ThursdayEndTime");
                    Time fs=rs.getTime("FridayStartTime");
                    Time fe=rs.getTime("FridayEndTime");
                    Time sas=rs.getTime("SaturdayStartTime");
                    Time sae=rs.getTime("SaturdayEndTime");
                    Time sus=rs.getTime("SundayStartTime");
                    Time sue=rs.getTime("SundayEndTime");
                    TimetableDoctor ttDr=new TimetableDoctor(ms,me,tus,tue,ws,we,ths,the,fs,fe,sas,sae,sus,sue);
                    
                    Doctor dbDoctor=new Doctor(code,name,spec,ttDr);
                    al.add(dbDoctor);
                    
                    //System.out.println(code + "  " + name+" "+spec);
                    }
                    
                }
                if(type==4){//технические данные для записи
                        while (rs.next()) {
                            int codeUs = Integer.valueOf(rs.getString("CodeUser"));
                            int durRec= Integer.valueOf(rs.getString("DurationReception"));
                            Time timeLanch=rs.getTime("StartLunchBreak");
                            int durLanch= Integer.valueOf(rs.getString("DurationLunchBreak"));
                            int polUser=Integer.valueOf(rs.getString("PolicyUser"));
                            String aLog=rs.getString("AdminLogin");
                            String aPas=rs.getString("AdminPassword");
                            TechnicalDataForRecord tecData=new TechnicalDataForRecord(codeUs,durRec,timeLanch,durLanch,polUser,aLog,aPas);
//                            System.out.println("Технические данные: код пользователя="+codeUs+" длит приема="+durRec+
//                                    " начало перерыва="+timeLanch+" длит перерыв="+durLanch);
                            al.add(tecData);
                        }
                    }
                if(type==5){//список записей
                    while (rs.next()) {
                    int codeRes = Integer.valueOf(rs.getString("CodeReservations"));
                    //int dayWeek=Integer.valueOf(rs.getString("DayWeek"));
                    Time time=rs.getTime("Time");
                    int codePatient = Integer.valueOf(rs.getString("CodePatient"));
                    int codeDoctor = Integer.valueOf(rs.getString("CodeDoctor"));
                    String comment=rs.getString("Comment");
                    
                    String recDate=rs.getString("Date");
//                    Date recDate;
//                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
//                    try{
//                        recDate = rs.getDate("Date");
//                        //System.out.println(format1.format(regDate)); //25.02.2013 09:03
//                    }catch(NullPointerException e){
//                        recDate=new Date();
//                        //System.out.println(format1.format(regDate));
//                    }
                    Reservation dbReservation=new Reservation(codeRes,recDate,time,codePatient,codeDoctor,comment);
                    al.add(dbReservation);
                    //System.out.println(recDate);
                    }
                 
                }
                if(type==6){//список занятого времени конкретного врача на конкретную дату
                    //System.out.println("Зашли в базу");
                    while (rs.next()) {
                    Time time=rs.getTime("Time");
                    al.add(time);
                    System.out.println("Занятое время "+time);
                    }
                }
                if(type==7){//список пациентов записанных к конкретному врачу на конкретную дату
                    while (rs.next()) {
                    int pat=rs.getInt("CodePatient");
                    al.add(pat);
                    //System.out.println("Занятое время "+time);
                    }
                }//CodePatient
                
                
                rs.close();
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } 
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    //System.out.println("Закрыли stmt");
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                    //System.out.println("Закрыли conn");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
       
        
    }
    
    public void updateDataFromDatabase(String sql){
        //Connection conn = null;
        //Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            //2-ой тип запроса: обновление данных
                stmt.executeUpdate(sql);
                
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } 
        
      
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    //System.out.println("Закрыли stmt");
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                    //System.out.println("Закрыли conn");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
       
        
    }//конец конструктора
    
}