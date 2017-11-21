package otus;

import otus.annotations.After;
import otus.annotations.Before;
import otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class MyTestExecutor {

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public static <T> void invoke(Class<T> type) {

        List<Method> testMethods = ReflectionHelper.getMethodsByAnnotation(type, Test.class);

        for (Method testMethod : testMethods) {

            MyTest myTest = ReflectionHelper.instantiate(MyTest.class);

            List<Method> beforeMethods = ReflectionHelper.getMethodsByAnnotation(type, Before.class);
            for (Method beforeMethod : beforeMethods) {
                ReflectionHelper.callMethod(myTest, beforeMethod.getName(), beforeMethod.getTypeParameters());
            }

            ReflectionHelper.callMethod(myTest, testMethod.getName(), testMethod.getTypeParameters());

            List<Method> afterMethods = ReflectionHelper.getMethodsByAnnotation(type, After.class);
            for (Method afterMethod : afterMethods) {
                ReflectionHelper.callMethod(myTest, afterMethod.getName(), afterMethod.getTypeParameters());
            }

        }

    }

    @SuppressWarnings("unchecked")
    public static void invoke(String tPackage) {

        Set<Class<?>> classes = ReflectionHelper.getClassesByPackage(tPackage);

        for (Class myClass : classes) {

            if (ReflectionHelper.isMethodsWithAnnotation(myClass, Test.class)) {
                System.out.println(myClass.getName() + ":");
                invoke(myClass);
                System.out.println();
            }

        }
    }
}
