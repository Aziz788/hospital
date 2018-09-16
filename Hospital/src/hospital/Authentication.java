package hospital;
// Класс отвечает за вход в систему
// 

import fact.Patient;
import fact.TechnicalDataForRecord;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class Authentication {
    public ConnectionDataBase connDB;
    
    public Authentication(){
        // Конструктор, подключаем класс для работы с базой данных
        this.connDB=new ConnectionDataBase();
    }
    
    public boolean entry(String login,String password){
        // Обрабытывает кнопку вход
        // Пользователь вводит логин и пароль, система обращается к имеющейся 
        // базе пациентов и проверяет наличие такого пользователя
        //
        // Обращаемся к базе данных (вместо нее можно хранить в самой проге массив
        // пользователей с паролями и логинами)
        ArrayList<Patient> listPatient=new ArrayList();
        String sql01="SELECT * FROM patients";
        connDB.setDataFromDatabase(sql01, listPatient,1); // получили список всех пользователей
        // В цикле пройдем по списку пользователей, сравнивая введенные пароль и логин
        // Если найдем нужный, поменяем статус пользователя на 1 (он будет иметь права только пациента)
        for(int i=0;i<listPatient.size();i++){
            if(((Patient)listPatient.get(i)).policy==Integer.valueOf(password) && 
                    (((Patient)listPatient.get(i)).name).equals(login)){
                // В следующей строке меняем данные в базе данных 
                // `CodeUser`='1' отвечает за статус пользователя
                String sql="UPDATE `hospital`.`technicaldata` SET `CodeUser`='1', `PolicyUser`='"+((Patient)listPatient.get(i)).policy+"' WHERE `CodeTD`='1';";
                connDB.updateDataFromDatabase(sql);
                return true;
            }
        }
        return false;
    }
    
    public boolean registration(String login,String password){
        // Здесь регистрируем нового пользователя
        // Србираем введенные данные и отправляем в базу
        ArrayList<Patient> listPatient=new ArrayList();
        String sql01="SELECT * FROM patients";
        connDB.setDataFromDatabase(sql01, listPatient,1);
        
        Date d=new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String dateReg=format1.format(d);
        
        int policy=Integer.valueOf(password);
        int count=0;
        for(int i=0;i<listPatient.size();i++){
            if(listPatient.get(i).policy==policy)
                count++;
            }//Конец проверки на совпадения
        if(count>0)
            return false;
        else {
            String sqlReg="INSERT INTO `hospital`.`patients` (`Name`, `Policy`,`RegistrationDate`) VALUES ('"+login+"', '"+policy+"','"+dateReg+"');";
            connDB.updateDataFromDatabase(sqlReg);
            String sql2="UPDATE `hospital`.`technicaldata` SET `PolicyUser`='"+policy+"' WHERE `CodeTD`='1';";
            connDB.updateDataFromDatabase(sql2);
        }
        
        return true;
    }
    
    public boolean entryAdmin(String login,String password){
        // Вход для админа. Все аналогично обычному входу, только 
        // CodeUser теперь будет 0
        ArrayList<TechnicalDataForRecord> tecData=new ArrayList();
        String sqlExit="SELECT * FROM technicaldata WHERE CodeTD=1;";
        connDB.setDataFromDatabase(sqlExit, tecData, 4);
        String adminLogin=(tecData.get(0)).adminLogin;
        String adminPassword=(tecData.get(0)).adminPassword;
        
        if((login).equalsIgnoreCase(adminLogin) && (password).equalsIgnoreCase(adminPassword)){
            String sql="UPDATE `hospital`.`technicaldata` SET `CodeUser`='"+0+"' WHERE `CodeTD`='1';";
            connDB.updateDataFromDatabase(sql);
            return true;
        }
        return false;
    }
    
}
