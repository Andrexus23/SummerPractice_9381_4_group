import java.util.Date;

public class MyLogger {
    private StringBuilder logMes;
    private Date date;
    MyLogger(){
        logMes = new StringBuilder();
        date = new Date();
    }
    public void writeLog(String str){
        logMes.append(date.toString()).append(" ").append(str);
    }

    public StringBuilder getLogMes(){
      return logMes;
    }
}
