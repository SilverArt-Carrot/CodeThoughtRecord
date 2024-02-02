package sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SortLearn02 {

    public static void main(String[] args) {
        Person p1 = new Person(20, "p1");
        Person p2 = new Person(18, "p2");
        Person p3 = new Person(23, "p3");
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Collections.sort(list);  // 通过Person类自己实现Comparable
        System.out.println(list);
        Collections.sort(list, (o1, o2) -> {   // 通过实现Comparator函数式接口的匿名类实现
            if (o1 == null || o1.age == null || o2 == null || o2.age == null) {
                return -1;
            }
            if (o1.age > o2.age) {
                return -1;
            }
            if (o1.age < o2.age) {
                return 1;
            }
            return 0;
        });
    }

    public static class Person implements Comparable<Person>, Serializable {
        private Integer age;
        private String name;

        public Person(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Person o) {
            if (o == null || o.age == null) {
                return -1;
            }
            if (this.age > o.age) {
                return 1;
            }
            if (this.age < o.age) {
                return -1;
            }
            return 0;
        }
    }
}
