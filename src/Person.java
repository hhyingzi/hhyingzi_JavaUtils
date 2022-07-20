import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Person implements Comparable<Person> {
    private String name;
    private int age;
    public String getName() { return name; }
    public int getAge() { return age; }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

    @Override
    public int compareTo(Person person) {
        return this.age - person.getAge();
    }
}

class DescAgeComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person2.getAge() - person1.getAge();
    }
}