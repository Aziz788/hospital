package fact;

import java.sql.Time;
import javafx.scene.chart.PieChart.Data;


public class TimetableDoctor {
    public Time MondayStartTime;
    public Time MondayEndTime;
    public Time TuesdayStartTime;
    public Time TuesdayEndTime;
    public Time WednesdayStartTime;
    public Time WednesdayEndTime;
    public Time ThursdayStartTime;
    public Time ThursdayEndTime;
    public Time FridayStartTime;
    public Time FridayEndTime;
    public Time SaturdayStartTime;
    public Time SaturdayEndTime;
    public Time SundayStartTime;
    public Time SundayEndTime;
    
    public TimetableDoctor(){
        this.MondayStartTime=null;
        this.MondayEndTime =null;
        this.TuesdayStartTime =null;
        this.TuesdayEndTime =null;
        this.WednesdayStartTime =null;
        this.WednesdayEndTime =null;
        this.ThursdayStartTime =null;
        this.ThursdayEndTime =null;
        this.FridayStartTime =null;
        this.FridayEndTime =null;
        this.SaturdayStartTime =null;
        this.SaturdayEndTime =null;
        this.SundayStartTime =null;
        this.SundayEndTime =null;
    }
    
    public TimetableDoctor(Time ms,Time me,Time tus,Time tue,Time ws,Time we,Time ths,
            Time the,Time fs,Time fe,Time sas,Time sae,Time sus,Time sue){
        this.MondayStartTime=ms;
        this.MondayEndTime=me;
        this.TuesdayStartTime=tus;
        this.TuesdayEndTime=tue;
        this.WednesdayStartTime=ws;
        this.WednesdayEndTime=we;
        this.ThursdayStartTime=ths;
        this.ThursdayEndTime=the;
        this.FridayStartTime=fs;
        this.FridayEndTime=fe;
        this.SaturdayStartTime=sas;
        this.SaturdayEndTime=sae;
        this.SundayStartTime=sus;
        this.SundayEndTime=sue;
    }
    
    public Time getDayStarTime(int codeDayWeek,int startOrEnd){//start=1,end=2
        if(codeDayWeek==2){
            if(startOrEnd==1){
                return this.MondayStartTime;
            }else return this.MondayEndTime;
            
        }
        if(codeDayWeek==3){
            if(startOrEnd==1){
                return this.TuesdayStartTime;
            }else return this.TuesdayEndTime;
            
        }if(codeDayWeek==4){
            if(startOrEnd==1){
                return this.WednesdayStartTime;
            }else return this.WednesdayEndTime;
            
        }if(codeDayWeek==5){
            if(startOrEnd==1){
                return this.ThursdayStartTime;
            }else return this.ThursdayEndTime;
            
        }if(codeDayWeek==6){
            if(startOrEnd==1){
                return this.FridayStartTime;
            }else return this.FridayEndTime;
            
        }if(codeDayWeek==7){
            if(startOrEnd==1){
                return this.SaturdayStartTime;
            }else return this.SaturdayEndTime;
            
        }if(codeDayWeek==1){
            if(startOrEnd==1){
                return this.SundayStartTime;
            }else return this.SundayEndTime;
            
        }
        return MondayEndTime;
    }
    
}
