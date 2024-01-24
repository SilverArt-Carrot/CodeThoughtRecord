package test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 打破String本身的不可变性
 * GPT回答String不可变的目的：安全性，StringPool的优化，hashcode不用重复计算，api的设计
 */
public class StringTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String str = "abc";
        System.out.println(str.hashCode());

        Field field = str.getClass().getDeclaredField("value");
        field.setAccessible(true);

        char[] value = (char[]) field.get(str);
        value[0] = '1';

        System.out.println(Arrays.toString(value));
        field.set(str, value);

        System.out.println(str);
        System.out.println(str.hashCode());
    }
}
