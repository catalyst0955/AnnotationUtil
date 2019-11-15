package annotationutil;

import annotation.CheckNullAndEmpty;
import commonutil.CommonUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckUtil {
    public static boolean check(Object obj){
        try {
            CheckNullAndEmpty annotation1 = obj.getClass().getAnnotation(CheckNullAndEmpty.class);
            if(annotation1 !=null){
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    String filedName = CommonUtil.changeFirstToUpperCase(field.getName());
                    Method method = obj.getClass().getMethod("get" + filedName);
                    Object o = method.invoke(obj, null);
                    if (o == null) {
                        return false;
                    }
                    if ("".equals(String.valueOf(o))) {
                        return false;
                    }
                }
                return true;
            }else {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                List<Field> collect = Arrays.stream(declaredFields).filter(field -> field.getAnnotation(CheckNullAndEmpty.class) != null).collect(Collectors.toList());
                for (Field field : collect) {
                    CheckNullAndEmpty annotation = field.getAnnotation(CheckNullAndEmpty.class);
                    String filedName = CommonUtil.changeFirstToUpperCase(field.getName());
                    Method method = obj.getClass().getMethod("get" + filedName);
                    Object o = method.invoke(obj, null);
                    if (annotation.checkNull().equalsIgnoreCase("Y") && o == null) {
                        return false;
                    }
                    if (annotation.checkEmpty().equalsIgnoreCase("Y") && "".equals(String.valueOf(o))) {
                        return false;
                    }
                }
                return true;
            }
        }catch (NullPointerException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            return false;
        }
    }

//    private static String turnFirstWordToUpperCase(String str){
//        return str.substring(0, 1).toUpperCase() + str.substring(1);
//    }
}
