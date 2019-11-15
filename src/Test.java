import annotationutil.AnnotationUtil;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        Pojo pojo = new Pojo();
        AnnotationUtil.format(pojo);
        System.out.println(pojo.getDate());
    }
}
