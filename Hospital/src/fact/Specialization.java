package fact;

public class Specialization {
    public int codeSpec;
    public String name;
    
    public Specialization(){
        this.codeSpec=0;
        this.name=null;
    }
    
    public Specialization(int scode,String sname){
        this.codeSpec=scode;
        this.name=sname;
    }
    
    public int getCodeSpec(){
        return codeSpec;
    }
    public void setCodeSpec(int scode){
        this.codeSpec=scode;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String sname){
        this.name=sname;
    }
    
}
