import annotation.DateFormat;

public class Pojo {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @DateFormat(origPatter = "yyyy/MM/dd",newPattern = "eee-MM-dd")
    private String date ="2009/01/01";

}



