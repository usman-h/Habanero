package com.usmanhussain.habanero.framework.reflection;

import com.usmanhussain.habanero.framework.element.WebField;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
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

    public static boolean setField(String fieldName, Object value, Object object) throws IllegalAccessException, ClassNotFoundException {

        if (object == null) {
            return false;
        }

        for (Field f : object.getClass().getDeclaredFields()) {

            WebField[] fields = f.getAnnotationsByType(WebField.class);

            if (fields.length > 0 && fields[0].value().equals(fieldName)) {
                f.setAccessible(true);
                //TODO - marshall all types, i.e. string, int, enum, list
                f.set(object, value);
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

    public static void setField2(String fieldName, Object value, Object object) throws ClassNotFoundException, IllegalAccessException {

        for (Field f : object.getClass().getDeclaredFields()) {
            boolean primitive = f.getType().isPrimitive() || primitives.contains(Class.forName(f.getType().getName()));
            System.out.println(f.getName() + " " + primitive);

            if (!primitive) {

                f.setAccessible(true);
                Object go = f.get(object);
                setField2(fieldName, value, go);
            }
        }
    }
}
