package commonutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonUtil {

    public static Map<String, Object> changeFirstToUpperCase(HashMap<String, Object> o) {
        return o.entrySet().stream().collect(Collectors.toMap(
                entry -> {
                    String key = String.valueOf(entry.getKey());
                    return changeFirstToUpperCase(key);
                },
                entry -> entry.getValue()));
    }

    public static String changeFirstToUpperCase(String key) {
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    public static Map<String, Object> changeFirstToLowerCase(HashMap<String, Object> o) {
        return o.entrySet().stream().collect(Collectors.toMap(
                entry -> {
                    String key = String.valueOf(entry.getKey());
                    return changeFirstToLowerCase(key);
                },
                entry -> entry.getValue()));
    }

    public static String changeFirstToLowerCase(String key) {
        return key.substring(0, 1).toLowerCase() + key.substring(1);
    }

    public static String reFormat(String value,String oldPattern,String newPattern) throws ParseException {
        if(value==null || value.equals(""))
            return "";
        StringBuilder sb = new StringBuilder(value.trim());
        Date parse;
        if(oldPattern.contains("eee")){
            int start = oldPattern.indexOf("eee");
            String gYear = tYearToGYear(sb.substring(start, start + 3));
            sb.replace(start, start + 3, gYear);
            oldPattern = oldPattern.replace("eee", "yyyy");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(oldPattern);
        parse = sdf.parse(sb.toString());

        if(newPattern.contains("eee")){
            newPattern = newPattern.replace("eee", "yyy");
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            instance.add(Calendar.YEAR,-1911);
            parse = instance.getTime();
        }
        sdf = new SimpleDateFormat(newPattern);
        return sdf.format(parse);
    }

    private static String tYearToGYear(String tYear){
        int i = Integer.parseInt(tYear) + 1911;
        return String.valueOf(i);
    }
}
