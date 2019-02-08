package com.usmanhussain.habanero.framework.reflection;

import com.usmanhussain.habanero.framework.element.Decode;
import com.usmanhussain.habanero.framework.element.WebField;

import java.lang.reflect.InvocationTargetException;

public class TestIt {

    static enum Colour {
        RED("Red"),
        BLUE("Blue"),
        GREEN("Green");

        private final String d;

        Colour(String d) {
            this.d = d;
        }

        @Decode
        static Colour from(String colour) {

            for (Colour c : values()) {
                if (c.d.equals(colour)) {
                    return c;
                }
            }
            return null;
        }
    }

    static class Blue {
        private int testInBlue;
    }

    static class TestMe {
        @WebField("a_field")
        private int a = 1;
        private Integer b = null;
        private Blue blue = new Blue();
        @WebField("test_str")
        private String test;
        @WebField("colour")
        private Colour colour;
        @WebField("empty1")
        private Boolean empty = true;
    }


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {

        TestMe t = new TestMe();
        //ReflectionMapperUtils.setField2("test", 1, t);

        ReflectionMapperUtils.setField("empty1", "null", t);

    }
}
