package fact;

public class Doctor {
    public int codeDr;
    public String name;
    public int specialization;
    public TimetableDoctor ttDoctor;
    
    public Doctor(){
        this.codeDr=0;
        this.name=null;
        this.specialization=0;
        this.ttDoctor=new TimetableDoctor();
    }
    
    public Doctor(int scodeDr,String sname,int sspec,TimetableDoctor sttDr){
        this.codeDr=scodeDr;
        this.name=sname;
        this.specialization=sspec;
        this.ttDoctor=sttDr;
    }
    
    public int getCodeDr(){
        return codeDr;
    }
    public void setPolicy(int scodeDr){
        this.codeDr=scodeDr;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String sname){
        this.name=sname;
    }
    
    public int getSpecialization(){
        return specialization;
    }
    public void setSpecialization(int sspec){
        this.specialization=sspec;
    }

    
    
}
