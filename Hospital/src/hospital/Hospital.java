package hospital;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Hospital {
    //0-админ 1-пациент 2-гость
            
    Hospital(){
        JFrame jfrm = new JFrame("Запись на прием");
        Drawing dr=new Drawing(jfrm);
        dr.greeting();
        
//        ArrayList<Doctor> listDoctor=new ArrayList();
//        String sql03="SELECT * FROM doctors";
//        connDB.setDataFromDatabase(sql03, listDoctor,3);
//        ArrayList<Specialization> listSpec=new ArrayList();
//        String sql02="SELECT * FROM specialization";
//        connDB.setDataFromDatabase(sql02, listSpec,2);
        
    }//конец конструктора

    
    public static void main(String[] args) {
        // Создаем фрейм в потоке диспетчиризации событий, там же где создаются события
         SwingUtilities.invokeLater(new Runnable(){
             public void run() {new Hospital();} 
        }); 
         
    }
    
}
