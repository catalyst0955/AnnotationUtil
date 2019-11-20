package main.annotationutil;

import main.annotation.DateFormat;
import main.annotation.MergeAlias;
import com.google.gson.Gson;
import main.commonutil.CommonUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtil {


    public static <T> T merge(Object prime, Object other, Class<T> clazz, boolean isDb) {
        Gson gson = new Gson();
        HashMap<String, Object> otherMap = gson.fromJson(gson.toJson(other), HashMap.class);
        HashMap<String, Object> primeMap = gson.fromJson(gson.toJson(prime), HashMap.class);
        Class<?> aClass = prime.getClass();
        for (Field declaredField : aClass.getDeclaredFields()) {
            MergeAlias annotation = declaredField.getAnnotation(MergeAlias.class);
            if (annotation != null) {
                for (String s : annotation.value()) {
                    Object s1 = otherMap.get(s);
                    if (s1 != null) {

                        try {
                            if(annotation.convert().length>0) {
                                Method otherMethod = other.getClass().getMethod("get" + CommonUtil.changeFirstToUpperCase(s), null);
                                s1= annotation.convert()[0].newInstance().convert(otherMethod.invoke(other));
                                primeMap.put(declaredField.getName(), s1);
                                otherMap.remove(s);
                                break;
                            }

                            if (!annotation.dateToString().equals("")) {
                                //other 的 date filed merg 進prime的string field
                                String patteren = annotation.dateToString();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patteren);
                                Method method = other.getClass().getMethod("get" + CommonUtil.changeFirstToUpperCase(s), null);
                                Object invoke = method.invoke(other);
                                s1 = simpleDateFormat.format(invoke);

                            } else if (!annotation.stringToDate().equals("")) {
                                //other 的 String filed merg 進prime的Date field
                                String pattern = annotation.stringToDate();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                                Date d = simpleDateFormat.parse(String.valueOf(s1));
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
                                s1 = sdf.format(d);
                            }
                            primeMap.put(declaredField.getName(), s1);
                            otherMap.remove(s);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }

        Map<String, Object> stringStringMap;
        if (isDb) {
            stringStringMap = CommonUtil.changeFirstToUpperCase(otherMap);
        } else {
            stringStringMap = CommonUtil.changeFirstToLowerCase(otherMap);
        }
        primeMap.putAll(stringStringMap);
        return gson.fromJson(gson.toJson(primeMap), clazz);
    }

    public static void format(Object prime) {
        Class<?> aClass = prime.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            DateFormat annotation = declaredField.getAnnotation(DateFormat.class);
            if (annotation != null && (declaredField.getType().getName().endsWith("String"))) {
                String value = "";
                boolean accessible = false;
                try {
                    Object o = declaredField.get(prime);
                    value = o.toString();
                    accessible = true;
                } catch (IllegalAccessException e) {
                    try {
                        Method method = aClass.getMethod("get" + CommonUtil.changeFirstToUpperCase(declaredField.getName()), null);
                        value = method.invoke(prime, null).toString();
                    } catch (NoSuchMethodException ex) {
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    String reFormat = CommonUtil.reFormat(value, annotation.origPatter(), annotation.newPattern());
                    if (accessible) {
                        declaredField.set(prime, reFormat);
                    } else {
                        Method method = aClass.getMethod("set" + CommonUtil.changeFirstToUpperCase(declaredField.getName()), String.class);
                        method.invoke(prime, reFormat);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                continue;
            }
        }
    }


}
