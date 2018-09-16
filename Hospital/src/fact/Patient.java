package fact;

import java.util.Date;


public class Patient {
  public String name;
  public int policy;
  public Date RegistrationDate;
  //public GregorianCalendar dataReg;
  
  public Patient(){
      this.name=null;
      this.policy=0;
      this.RegistrationDate=null;
  }
  
  public Patient(String name,int policy,Date RegistrationDate){
      this.name=name;
      this.policy=policy;
      this.RegistrationDate=RegistrationDate;
  }
  
  public String getName() {
      return name;
  }
  
  public void setName(String sname){
      this.name=sname;
  } 
      
  public int getPolicy(){
      return policy;
  }
  
  public void setPolicy(int spolicy){
      this.policy=spolicy;
  }
  
//  public GregorianCalendar getRegistrationDate() {
//      return RegistrationDate;
//  }
//  
//  public void setRegistrationDate(GregorianCalendar sRegistrationDate){
//      this.RegistrationDate=sRegistrationDate;
//  } 
   
  
}