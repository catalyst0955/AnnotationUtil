

import com.google.gson.Gson;
import main.annotationutil.AnnotationUtil;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        test.Pojo pojo = new test.Pojo();
        test.Pojo2 pojo2 = new test.Pojo2();
        pojo.setList(Arrays.asList("a", "b", "c"));
        test.Pojo2 merge = AnnotationUtil.merge(pojo2, pojo, pojo2.getClass(), false);
        Gson gson = new Gson();
        System.out.println(gson.toJson(merge));

    }
}
