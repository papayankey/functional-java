package com.github.papayankey;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        // Data
        Customer[] customers = jsonToObj();

        /* Intermediate Operators */

        // map
        List<String> customerByEmails = Arrays.stream(customers)
                .map(Customer::getEmail)
                .toList();

        System.out.println(customerByEmails);

        final List<String> customersByGender = Arrays.stream(customers)
                .map(Customer::getGender)
                .toList();

        System.out.println(customersByGender);

        // filter
        List<Customer> customersAgeGreaterThanFifty = Arrays.stream(customers)
                .filter(customer -> customer.getAge() > 50)
                .toList();

        System.out.println(customersAgeGreaterThanFifty);

        long customersAgeGreaterThanFiftyCount = Arrays.stream(customers)
                .filter(customer -> customer.getAge() > 50)
                .count();

        System.out.println(customersAgeGreaterThanFiftyCount);

        // sorted
        List<Integer> customersSortedByAge = Arrays.stream(customers)
                .sorted(Comparator.comparing(Customer::getAge, Comparator.naturalOrder()))
                .map(Customer::getAge)
                .toList();

        System.out.println(customersSortedByAge);

        // limit
        List<String> tenCustomerFirstNames = Arrays.stream(customers)
                .map(Customer::getFirstName)
                .skip(5)
                .limit(3)
                .toList();

        System.out.println(tenCustomerFirstNames);

        // Distinct
        List<Integer> customersSortedByAgeAndDistinct = Arrays.stream(customers)
                .sorted(Comparator.comparing(Customer::getAge, Comparator.naturalOrder()))
                .map(Customer::getAge)
                .distinct()
                .toList();

        System.out.println(customersSortedByAgeAndDistinct);


        /* Terminal Operators */

        // findFirst()
        Optional<Customer> firstCustomerAgeLessThanEighteen = Arrays.stream(customers)
                .filter(customer -> customer.getAge() < 20)
                .findFirst();

        System.out.println(firstCustomerAgeLessThanEighteen);

        // findAny()
        Optional<Customer> any = Arrays.stream(customers)
                .filter(customer -> customer.getAge() < 18)
                .findAny();

        System.out.println(any);

        // collect
        TreeSet<Integer> customersSortedByAgeAndDistinctAndCollectAsTreeSet = Arrays.stream(customers)
                .sorted(Comparator.comparing(Customer::getAge, Comparator.naturalOrder()))
                .map(Customer::getAge)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println(customersSortedByAgeAndDistinctAndCollectAsTreeSet);
    }

    private static Customer[] jsonToObj() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("stream-api/MOCK_DATA.json");
        return objectMapper.readValue(file, Customer[].class);
    }
}
