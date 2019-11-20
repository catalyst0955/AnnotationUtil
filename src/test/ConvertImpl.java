
import main.annotation.interfacepkg.Convertable;

import java.util.ArrayList;
import java.util.Collection;

public class ConvertImpl implements Convertable {
    @Override
    public Object convert(Object o2) {
        ArrayList<?> objects = new ArrayList<>((Collection<?>) o2);
        return objects.toString();
    }
}
