package test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.TreeSet;

public class Son extends Father{

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Son son = new Son();
        System.out.println(son.getStr());

        Class<? extends Son> sonClass = son.getClass();
        Field privateField = sonClass.getSuperclass().getDeclaredField("str");

        // 通过反射获取了父类的私有成员变量
        privateField.setAccessible(true);
//        Object o = privateField.get(son);
        String value = (String) privateField.get(son);
        System.out.println(value);

        // 修改父类私有成员变量
        privateField.set(son, "changed private str");
        System.out.println(son.getStr());

        System.out.println("------------------");

        test01();
    }

    public static void test01() throws NoSuchFieldException, IllegalAccessException {
        Son son = new Son();
        char[] chars = son.getChars();
        System.out.println(Arrays.toString(chars));
        chars[0] = 'a';
        chars = son.getChars();
        System.out.println(Arrays.toString(chars));

        Class<? extends Son> sonClass = son.getClass();
        Field privateField = sonClass.getSuperclass().getDeclaredField("chars");

        // 通过反射获取了父类的私有成员变量
        privateField.setAccessible(true);
//        Object o = privateField.get(son);
        char[] value = (char[]) privateField.get(son);
        System.out.println(Arrays.toString(value));
        value[1] = 'b';

        // 修改父类私有成员变量
        privateField.set(son, value);
        System.out.println(Arrays.toString(son.getChars()));

        System.out.println("------------------");
    }

}
