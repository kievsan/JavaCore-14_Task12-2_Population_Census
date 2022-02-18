//package knhel7.jd14.javacore14.task122;

// Task122
// Домашнее задание к занятию 1.2.
// Stream API. Потоки, повторные вызовы, основные методы
// Задача 2. Перепись населения
// https://github.com/netology-code/jd-homeworks/blob/master/streams/task2/README.md

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    protected static Predicate<Person> isMinor
            = person -> person.getAge() < 18;
    protected static Predicate<Person> isMilitary
            = person -> person.getAge() >= 18 && person.getAge() <= 27;
    protected static Predicate<Person> isAble_bodied
            = person -> person.getAge() >= 18 &&
                        person.getAge() <= ((person.getSex() == Sex.WOMAN) ? 60 : 65);
    protected static Predicate<Person> hasHigherEducation
            = person -> person.getEducation() == Education.HIGHER;
    protected static Function<Person, String> convertPersonToSurname
            = Person::getFamily;

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long countMinorPerson = persons.stream()
                .filter(isMinor)
                .count();
        System.out.println("1. Number persons of minor age: " + countMinorPerson);

        List<String> listOfConscriptsSurname = persons.stream()
                .filter(isMilitary)
                .map(convertPersonToSurname)
                .toList();
        System.out.println("2. List of names of conscripts, beginning: " +
                listOfConscriptsSurname.subList(0, families.size()));

        List<Person> list = persons.stream()
                .filter(isAble_bodied)
                .filter(hasHigherEducation)
                .sorted(Comparator.<Person>naturalOrder())
                .toList();
        System.out.println("3. Sorted list of potentially able-bodied people" +
                " with higher education, beginning: " +
                list.subList(0, families.size()));

    }
}
