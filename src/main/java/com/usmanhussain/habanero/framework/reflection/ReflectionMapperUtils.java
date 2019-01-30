package com.usmanhussain.habanero.framework.reflection;

import com.usmanhussain.habanero.framework.element.Decode;
import com.usmanhussain.habanero.framework.element.WebField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectionMapperUtils {

    private static Set<Class<?>> primitives = new HashSet<>();

    static {
        primitives.add(Boolean.class);
        primitives.add(Character.class);
        primitives.add(Byte.class);
        primitives.add(Short.class);
        primitives.add(Integer.class);
        primitives.add(Long.class);
        primitives.add(Float.class);
        primitives.add(Double.class);
        primitives.add(Void.class);
        primitives.add(String.class);
    }

    private static void setField(Field field, Object value, Object object) throws IllegalAccessException, InvocationTargetException {

        Object valueToSet = value;

        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            valueToSet = Integer.parseInt((String) value);
        } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            valueToSet = Boolean.valueOf((String) value);
        } else if (field.getType().isEnum()) {

            for (Method m : field.getType().getDeclaredMethods()) {
                if (m.getAnnotation(Decode.class) != null) {
                    valueToSet = m.invoke(field.getType(), valueToSet);
                    break;
                }
            }

        }

        field.set(object, valueToSet);

    }

    public static boolean setField(String fieldName, Object value, Object object) throws IllegalAccessException, ClassNotFoundException, InvocationTargetException {

        if (object == null) {
            return false;
        }

        for (Field f : object.getClass().getDeclaredFields()) {

            WebField[] fields = f.getAnnotationsByType(WebField.class);

            if (fields.length > 0 && fields[0].value().equals(fieldName)) {
                f.setAccessible(true);
                //TODO - marshall all types, i.e. string, int, enum, list of things..
                //f.set(object, value);
                setField(f, value, object);
                return true;
            } else {
                //TODO optimise this by using company name, don't drill in enums, lists etc
                boolean primitive = f.getType().isPrimitive() || primitives.contains(Class.forName(f.getType().getName())) || f.isEnumConstant();

                if (!primitive) {
                    f.setAccessible(true);
                    if (setField(fieldName, value, f.get(object))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
