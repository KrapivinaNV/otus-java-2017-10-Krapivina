package otus.myjsonwriter;

import com.google.common.collect.ImmutableSet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

public class JsonWriter {

    public static String writeToString(Object object) {

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        navigateObjectTree(object, jsonBuilder);
        JsonObject jsonObject = jsonBuilder.build();
        System.out.println(jsonObject);

        return jsonObject.toString();
    }


    public static void navigateObjectTree(Object object, JsonObjectBuilder objectBuilder) {
        Field[] fields = MyReflectionHelper.getFields(object);
        if (fields.length != 0) {
            for (Field field : fields) {
                completeObject(field.getType(), field.getName(), object, objectBuilder);
            }
        }
    }

    public static void navigateArrayTree(Object object, JsonArrayBuilder arrayBuilder) {
        completeArray(object, arrayBuilder);
    }

    private static void completeArray(Object object, JsonArrayBuilder arrayBuilder) {
        Class<?> objectClass = object.getClass();
        if (objectClass.equals(Integer.class) || objectClass.equals(int.class)) {
            arrayBuilder.add((int) object);
        } else if (objectClass.equals(float.class)
                || objectClass.equals(double.class)
                || objectClass.equals(Float.class)
                || objectClass.equals(Double.class)) {
            arrayBuilder.add((float) object);
        } else if (objectClass.equals(long.class) || objectClass.equals(Long.class)) {
            arrayBuilder.add((long) object);
        } else if (objectClass.equals(boolean.class) || objectClass.equals(Boolean.class)) {
            arrayBuilder.add((boolean) object);
        } else if (objectClass.equals(BigDecimal.class)) {
            arrayBuilder.add((BigDecimal) object);
        } else if (objectClass.equals(BigInteger.class)) {
            arrayBuilder.add((BigInteger) object);
        } else if (objectClass.equals(String.class)) {
            arrayBuilder.add((String) object);
        }
    }


    public static void completeObject(Class<?> clazz, String name, Object object, JsonObjectBuilder objectBuilder) {
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            objectBuilder.add(name, (int) MyReflectionHelper.getFieldValue(object, name));
        } else if (clazz.equals(float.class)
                || clazz.equals(double.class)
                || clazz.equals(Float.class)
                || clazz.equals(Double.class)) {
            objectBuilder.add(name, Double.valueOf(MyReflectionHelper.getFieldValue(object, name).toString()));
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            objectBuilder.add(name, (long) MyReflectionHelper.getFieldValue(object, name));
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            objectBuilder.add(name, (boolean) MyReflectionHelper.getFieldValue(object, name));
        } else if (clazz.equals(BigInteger.class)) {
            objectBuilder.add(name, (BigDecimal) MyReflectionHelper.getFieldValue(object, name));
        } else if (clazz.equals(BigInteger.class)) {
            objectBuilder.add(name, (BigInteger) MyReflectionHelper.getFieldValue(object, name));
        } else if (clazz.equals(String.class)) {
            objectBuilder.add(name, (String) MyReflectionHelper.getFieldValue(object, name));
        } else if (Collection.class.isAssignableFrom(clazz)) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            Object collection = MyReflectionHelper.getFieldValue(object, name);
            if (collection instanceof Collection) {
                for (Object element : ((Collection) collection).toArray()) {
                    if (SIMPLE_TYPES.contains(element.getClass().getName())) {
                        navigateArrayTree(element, arrayBuilder);
                    } else {
                        JsonObjectBuilder elementBuilder = Json.createObjectBuilder();
                        navigateObjectTree(element, elementBuilder);
                        arrayBuilder.add(elementBuilder);
                    }
                }
            }
            objectBuilder.add(name, arrayBuilder);
        } else if ((clazz).isArray()) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            Object array = MyReflectionHelper.getFieldValue(object, name);
            // Class<?> componentType = ((Class<?>) type).getComponentType();
            for (int index = 0; Array.getLength(array) > index; index++) {
                Object element = Array.get(array, index);
                navigateArrayTree(element, arrayBuilder);
            }
            objectBuilder.add(name, arrayBuilder);

        } else {
            JsonObjectBuilder internalObjectBuilder = Json.createObjectBuilder();
            Object internalObject = MyReflectionHelper.getFieldValue(object, name);
            navigateObjectTree(internalObject, internalObjectBuilder);
            objectBuilder.add(name, internalObjectBuilder);
        }
    }

    private static final Set<String> SIMPLE_TYPES = ImmutableSet.of(
            Integer.class.getName(),
            Long.class.getName(),
            Number.class.getName(),
            String.class.getName(),
            Double.class.getName(),
            Float.class.getName(),
            BigDecimal.class.getName(),
            BigInteger.class.getName(),
            Boolean.class.getName()
    );


}

