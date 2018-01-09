package otus.myorm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
class MySimpleReflectionHelper {

    static Field[] getFieldsByObject(Object object) {
        return object.getClass().getDeclaredFields();

    }

    static Field[] getFieldsByClass(Class clazz) {
        return clazz.getDeclaredFields();

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

    static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                Constructor<T> constructor = type.getConstructor(toClasses(args));
                return constructor.newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    static <T> boolean ifFieldAnnotated(Class<T> type, String name, Class<? extends Annotation> annotation) {
        try {
            Field field = type.getDeclaredField(name);
            if(field.isAnnotationPresent(annotation)) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }


    static private Class<?>[] toClasses(Object[] args) {
        if (args == null) {
            return null;
        }
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }

}
