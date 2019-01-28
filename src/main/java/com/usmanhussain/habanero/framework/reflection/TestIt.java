package com.usmanhussain.habanero.framework.reflection;

import java.lang.reflect.Field;

public class TestIt {

    static class Blue {
        private int testInBlue;
    }
    static class TestMe {
        private int a = 1;
        private Integer b = null;
        private Blue blue = new Blue();
    }


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {

        TestMe t = new TestMe();
        ReflectionMapperUtils.setField2("test", 1, t);
    }
}
