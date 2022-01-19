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

class Customer {
    private String accountNumber;
    private String email;

    public Customer(String accountNumber, String email) {
        this.accountNumber = accountNumber;
        this.email = email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        // Practical consumer example
        // save scores to a file
        writeScoresToFile(scores);

        // Predicate
        Predicate<Integer> canVote = (Integer age) -> age >= 18;
        System.out.println(canVote.test(12)); // false
        System.out.println(canVote.test(18)); // true

        Predicate<String> isValidEmail = (String email) -> email.contains("@");
        System.out.println(isValidEmail.test("school")); // false
        System.out.println(isValidEmail.test("turntable@gmail.com")); // true


        /* Functional Derivatives */

        // UnaryOperator
        UnaryOperator<Integer> decrement = (Integer number) -> number - 1;
        Integer dec = decrement.apply(10);
        System.out.println(dec);

        // BiFunction
        BiFunction<Integer, Integer, Integer> addTwoNumbers = (Integer a, Integer b) -> a + b;
        System.out.println(addTwoNumbers.apply(10, 20));

        // BinaryOperator
        BinaryOperator<Integer> subtractTwoNumbers = (Integer a, Integer b) -> a - b;
        System.out.println(subtractTwoNumbers.apply(20, 10));

        /* Primitive Functional Interfaces */

        // IntToDoubleFunction : takes a primitive int parameter and returns double result
        IntToDoubleFunction doubleResult = (int number) ->
                Double.parseDouble(String.valueOf(number));
        System.out.println(doubleResult.applyAsDouble(10));



        /* Functional Composition */

        // Manual
        Predicate<String> hasValidEmail = (String email) -> email.contains("@");
        Predicate<Integer> isOfAge = (Integer age) -> age >= 18;

        BiPredicate<String, Integer> hasValidEmailAndCanVote =
                (String email, Integer age) -> hasValidEmail.test(email) && isOfAge.test(age);

        boolean result = hasValidEmailAndCanVote.test("kojo@gmail.com", 18);
        System.out.println("Candidate can vote?: " + result);


        // Using Built-In Features

        // Predicate Compositions
        //  and(), or(), negate(), isEqual(), not()

        // and() : same as logical operator, &&
        Customer james = new Customer("123456789", "james@zohoo.com");

        Predicate<Customer> customerHasValidEmail = (Customer customer) -> customer.getEmail().contains("@");
        Predicate<Customer> customerHasValidAccountNumber = (Customer customer) -> customer.getAccountNumber().length() == 9;

        boolean customerValidation = customerHasValidEmail
                .and(customerHasValidAccountNumber)
                .test(james);

        System.out.println("James is a valid customer? " + customerValidation);

        // or() : same as logical operator, ||
        Customer prince = new Customer("12345678", "prince.com");

        boolean customerValidation2 = customerHasValidEmail
                .or(customerHasValidAccountNumber)
                .test(prince);

        System.out.println("Prince is a valid customer? " + customerValidation2);

        // negate() : returns logical negated predicate
        Predicate<Customer> customerHasInvalidEmail = customerHasValidEmail.negate();

        System.out.println("Prince has invalid email? " + customerHasInvalidEmail.test(prince));
        System.out.println("James has invalid email? " + customerHasInvalidEmail.test(james));


        // not() : similar to negate() except is static. It takes in predicate to be negated instead.
        Predicate<Customer> customerHasInvalidAccountNumber = Predicate.not(customerHasValidAccountNumber);

        System.out.println("Prince has invalid account number? " + customerHasInvalidAccountNumber.test(prince));
        System.out.println("James has invalid account number? " + customerHasInvalidAccountNumber.test(james));

        // isEqual() : it is static method. It returns predicate which tests if two arguments are the same
        Predicate<Object> sameValues = Predicate.isEqual(124);
        System.out.println("124 and 124 are the same? " + sameValues.test(124));


        // Function Composition
        // compose(), andThen()

        // compose() : returns a composed function that first applies
        // before(inside parenthesis) function and after(outside parenthesis) function on the result
        Function<Integer, Integer> addTwo = (Integer a) -> 2 + a;
        Function<Integer, Integer> subTwo = (Integer a) -> a - 2;
        Function<Integer, Integer> mulThree = (Integer a) -> 3 * a;

        Integer composed = addTwo
                .compose(subTwo)
                .compose(mulThree)
                .apply(2);

        System.out.println("mulThree, subTwo and addTen is? " + composed);


        // andThen() : returns a composed function that first applies
        // after(outside parenthesis) and applies before(inside parenthesis) on the result
        Function<Integer, Integer> addThree = (Integer a) -> 3 + a;
        Function<Integer, Integer> subOne = (Integer a) -> a - 1;
        Function<Integer, Integer> mulTwo = (Integer a) -> 2 * a;

        Integer composed2 = addThree
                .andThen(subOne)
                .andThen(mulTwo)
                .apply(2);

        System.out.println("addThree and then subOne and then mulTwo is? " + composed2);
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












