package org.test;

import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.test.annotation.After;
import org.test.annotation.Before;
import org.test.annotation.Test;
import org.reflections.Reflections;
import org.test.exception.AssertionException;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Runner {
    private static final List<Class<? extends Annotation>> orderRunnerList = Arrays.asList(Before.class, Test.class, After.class);

    public static void run(String packageName) {
        AtomicInteger passed = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        orderRunnerList.forEach(annotation ->{
            Set<Method> testList =  getAllMethodsFromClass(annotation, packageName);
            testList.forEach(element -> {
                try {
                    methodRunner(element);
                    if (isTest(element)) {
                        passed.getAndIncrement();
                        System.out.println("Test " + element.getName() + " passed");
                    }
                } catch (AssertionException exception) {
                    failed.getAndIncrement();
                    System.out.println("Test " + element.getName() + " failed: " + exception.getMessage());
                }
            });
        });

        System.out.println("Tests passed: " + passed.get() + ", Tests failed:" + failed.get());
    }

    private static Set<Method> getAllMethodsFromClass(Class<? extends Annotation> clazz, String packageName) {
        Reflections reflections = new Reflections(
                packageName,
                new MethodAnnotationsScanner());
        return reflections.getMethodsAnnotatedWith(clazz);
    }

    private static boolean isTest(Method method) {
        return method.isAnnotationPresent(Test.class);
    }

    private static void methodRunner(Method method) throws AssertionException {
        try {
            method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                 NoSuchMethodException e) {
            try {
                throw e.getCause();
            } catch (AssertionException ex) {
                throw new AssertionException(ex.getMessage());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

    }


}
