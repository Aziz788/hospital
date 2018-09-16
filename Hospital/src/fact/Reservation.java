//пока без даты
package fact;

import java.sql.Time;
import java.util.Date;

public class Reservation {
    public int codeReservation;
    //public Date date;
    public String regDate;
    //public int dayWeek;
    public Time time;
    public int codePatient;
    public int codeDoctor;
    public String comment;
    
    public Reservation(){
        this.codeReservation=0;
        //this.date=null;
        this.time=null;
        this.codePatient=0;
        this.codeDoctor=0;
        this.comment=null;
    }
    
    public Reservation(int codeRes,String date,Time time,int codePatient,int codeDoctor,String comment){//дата String data
        this.codeReservation=codeRes;
        this.regDate=date;
        //this.dayWeek=dW;
        this.time=time;
        this.codePatient=codePatient;
        this.codeDoctor=codeDoctor;
        this.comment=comment;
    }
    
    public int getCodeReservation(){
        return codeReservation;
    }
    public void setCodeReservation(int codeRes){
        this.codeReservation=codeRes;
    }
//    public Data getData(){
//        return data;
//    }
//    public void setDate(Data dat){
//        this.data=dat;
//    }
    public Time getTime(){
        return time;
    }
    public void setTime(Time tim){
        this.time=tim;
    }
    public int getCodePatien(){
        return codePatient;
    }
    public void setCodePatien(int codePat){
        this.codePatient=codePat;
    }
     public int getCodeDoctor(){
        return codeDoctor;
    }
    public void setCodeDoctor(int codeDr){
        this.codeDoctor=codeDr;
    }
     public String getComment(){
        return comment;
    }
    public void setComment(String comm){
        this.comment=comm;
    }
   
    
}
