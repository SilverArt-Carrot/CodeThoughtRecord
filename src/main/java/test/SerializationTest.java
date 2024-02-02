package test;

import sort.SortLearn02;

import java.io.*;
import java.util.*;

public class SerializationTest {

    public static void main(String[] args) {
//        if (true) {
//            System.out.println("0");
//        } else {
//            try {
//                System.out.println("1");
//            } finally {
//                System.out.println("2");
//            }
//        }
//        LinkedList<Integer> list = new LinkedList<>();
//        list.set(100, 1);   // 数组越界
//        ArrayDeque<String> arrayDeque = new ArrayDeque<>();

//        Vector<Integer> vector = new Vector<>();
//        vector.add(null);
//        System.out.println(vector);
//
//        Stack<Integer> stack = new Stack<>();
//        stack.add(null);
//        System.out.println(stack);
//        HashMap hashMap = new HashMap();

        SortLearn02.Person p = new SortLearn02.Person(12, "A");

        // 序列化对象
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serializedObject.ser"))) {
            oos.writeObject(p);
            System.out.println("Object has been serialized");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 反序列化对象
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serializedObject.ser"))) {
            SortLearn02.Person deserializedObject = (SortLearn02.Person) ois.readObject();
            System.out.println("Object has been deserialized");
            System.out.println("Deserialized Object: " + deserializedObject);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 静态泛型方法
//    在 java 中泛型只是一个占位符，必须在传递类型后才能使用。
//    类在实例化时才能真正的传递类型参数，由于静态方法的加载先于类的实例化，
//    也就是说类中的泛型还没有传递真正的类型参数，静态的方法的加载就已经完成了，
//    所以静态泛型方法是没有办法使用类上声明的泛型的。只能使用自己声明的 <E>
    public static <E> void printArray( E[] inputArray )
    {
        for ( E element : inputArray ){
            System.out.printf( "%s ", element );
        }
        System.out.println();
    }

}
