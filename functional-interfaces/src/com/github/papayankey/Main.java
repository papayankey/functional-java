package com.github.papayankey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.*;

@FunctionalInterface
interface Greeting {
    String greet(String name);
}

class EnglishGreeting implements Greeting {
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}


public class Main {

    public static void main(String[] args) throws IOException {
        // Example 1 - named(regular) class
        EnglishGreeting englishGreeting = new EnglishGreeting();
        String greeting = englishGreeting.greet("Turntabl");
        System.out.println(greeting);

        // Example 2 - anonymous class
        Greeting spanishGreeting = new Greeting() {
            @Override
            public String greet(String name) {
                return "Hola " + name;
            }
        };

        String greeting2 = spanishGreeting.greet("Jordan");
        System.out.println(greeting2);

        // Example 3 - lambda expression
        Greeting frenchGreeting = (String name) -> "Bonjour " + name;
        String greeting3 = frenchGreeting.greet("Francis");
        System.out.println(greeting3);

        /* Built-In Lambda Expressions */
        // Four Categories:
        // 1. Function
        // 2. Supplier
        // 3. Consumer
        // 4. Predicate

        // Function
        Function<String, String> greeting1 = (String name) -> "Hello " + name;
        String paul = greeting1.apply("Paul");
        System.out.println(paul);

        Function<Integer, Integer> multiplyByTwo = (Integer number) -> 2 * number;
        Integer multiply = multiplyByTwo.apply(4);
        System.out.println(multiply);

        // Supplier
        Supplier<String> fruits = () -> "Mango, Guava";
        String suppliedFruits = fruits.get();
        System.out.println(suppliedFruits);

        // Consumer
        Consumer<String> consumeName = (String value) -> System.out.println("Hello: " + value);
        consumeName.accept("Daniel");

        List<Integer> scores = List.of(45, 29, 13);
        Consumer<Integer> multiplyByThree = (Integer num) -> System.out.printf("value: %d%n", num);
        scores.forEach(multiplyByThree);

        // Practical consumer example, writing or saving
        // scores value to a file
        writeScoresToFile(scores);

        // Predicate
        Predicate<Integer> canVote = (Integer age) -> age >= 18;
        System.out.println(canVote.test(12)); // false
        System.out.println(canVote.test(18)); // true

        Predicate<String> isValidEmail = (String email) -> email.contains("@");
        System.out.println(isValidEmail.test("school")); // false
        System.out.println(isValidEmail.test("turntable@gmail.com")); // true

        // UnaryOperator
        UnaryOperator<Integer> addThree = (Integer num) -> num + 3;
        System.out.println(addThree.apply(3));
    }

    private static void writeScoresToFile(List<Integer> scores) throws IOException {
        File file = new File("functional-interfaces/scores.txt");

        if (file.exists()) {
            boolean deletedFile = file.delete();
            if (deletedFile) System.out.println("file deleted");
        }

        FileWriter out = new FileWriter(file, true);

        Consumer<Integer> saveToFileConsumer = num -> {
            try {
                out.write(String.format("%d ", num));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        scores.forEach(saveToFileConsumer);
        out.close();
    }
}











