package otus;

import otus.annotations.After;
import otus.annotations.Before;
import otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

class MyTestExecutor {

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    static <T> void invoke(Class<T> type) {
        System.out.println(type.getName() + ":");

        List<Method> testMethods = ReflectionHelper.getMethodsByAnnotation(type, Test.class);

        for (Method testMethod : testMethods) {
            T myTest = ReflectionHelper.instantiate(type);
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
    static void invoke(String packageToScan) {

        Set<Class<?>> classes = ReflectionHelper.getClassesByPackage(packageToScan);
        for (Class myClass : classes) {
            if (ReflectionHelper.hasMethodAnnotation(myClass, Test.class)) {
                invoke(myClass);
                System.out.println();
            }
        }
    }
}