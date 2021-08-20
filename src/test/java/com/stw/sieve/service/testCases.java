package com.stw.sieve.service;
//ideally Mockito or something for mocking, but that takes more time
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.commons.util.ReflectionUtils.*;

public class testCases {
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setOut(systemOut);
        if(testOut.size()>0) System.out.print(testOut.toString());
    }

    private static Optional<Class<?>> getClass(final String className) {
        Try<Class<?>> aClass = tryToLoadClass(className);
        return aClass.toOptional();
    }

    public Optional<Class<?>> getInnerClass(String className) {
        return getClass(className);
    }

    @Test
    public void assertGetPrimesMethodExistence() {
        final String main = "getPrimes";
        final Optional<Class<?>> maybeClass = getInnerClass("com.stw.sieve.service.sieve");
        assertTrue(maybeClass.isPresent(), maybeClass+" class must be present");
        Class<?> c = maybeClass.get();
        List<Method> methods = Arrays.stream(c.getDeclaredMethods())
                .filter(m -> m.getName().equals(main))
                .collect(Collectors.toList());

        assertEquals(1, methods.size(), main + " must be defined as a method in " + c.getCanonicalName());

        final Method method = methods.get(0);
        assertEquals(String.class, method.getReturnType(), main + " must return 'String' as the return type");
        assertTrue(isStatic(method), main + " must be a static method");
        assertTrue(isPublic(method), main + " must be a public method");

        Class<?>[] parameterTypes = method.getParameterTypes();
        assertEquals(1, parameterTypes.length, main + " should accept 1 parameter");
        assertEquals(int.class, parameterTypes[0], main + " parameter type should be of 'int' type");
    }

}
