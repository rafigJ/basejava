package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println("minValue(new int[]{1, 4, 5, 5, 6}) = " + minValue(new int[]{1, 4, 5, 5, 6}));
        System.out.println("minValue(new int[]{1,2,3,3,2,3}) = " + minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println("minValue(new int[]{9, 8}) = " + minValue(new int[]{9, 8}));
        System.out.println("\n");

        List<Integer> list = Arrays.asList(1, 3, 5, 5, 6, 10);
        System.out.println("list = " + list);
        System.out.println("oddOrEven(list) = " + oddOrEven(list));
        System.out.println("sum list = " + list.stream().reduce(0, Integer::sum));

        System.out.println("\n");
        List<Integer> list1 = Arrays.asList(4, 5, 6, 7, 1);
        System.out.println("list1 = " + list1);
        System.out.println("oddOrEven(list1) = " + oddOrEven(list1));
        System.out.println("sum list1 = " + list1.stream().reduce(0, Integer::sum));
    }

    private static int minValue(int[] values) {
//        return Integer.parseInt(Arrays.stream(values)
//                .distinct()
//                .sorted()
//                .mapToObj(String::valueOf)
//                .collect(Collectors.joining()));
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        boolean isEven = integers.stream().reduce(0, Integer::sum) % 2 == 0;
        return integers.stream()
                .filter(a -> (isEven && a % 2 != 0) || (!isEven && a % 2 == 0))
                .collect(Collectors.toList());
    }

}
