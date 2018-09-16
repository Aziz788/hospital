package fact;

import java.sql.Time;


public class TechnicalDataForRecord {
    public int codeUser;
    public int durRec;//DurationReception;
    public Time timeLanch;//StartLunchBreak
    public int durLanch;
    public int policyUser;
    
    public String adminLogin;
    public String adminPassword;
    
    public TechnicalDataForRecord(int cu,int dr,Time tl,int dl,int pu,String alog,String apas){
        this.codeUser=cu;
        this.durRec=dr;
        this.timeLanch=tl;
        this.durLanch=dl;
        this.policyUser=pu;
        this.adminLogin=alog;
        this.adminPassword=apas;
    }
    
}
