package otus.myjsonwriter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
class MyReflectionHelper {
    private MyReflectionHelper() {
    }

    static Field[] getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return fields;

    }

    static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }


    static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name);
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }


    private static Class<?>[] toClasses(Object[] args) {
        if (args == null) {
            return null;
        } else {
            List<Class<?>> classes = (List)Arrays.stream(args).map(Object::getClass).collect(Collectors.toList());
            return (Class[])classes.toArray(new Class[classes.size()]);
        }
    }

}
