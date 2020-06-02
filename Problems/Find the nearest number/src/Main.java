import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] numberStringArray = scanner.nextLine().split(" ");
        int value = scanner.nextInt();
        List<Integer> integers = new ArrayList<>();

        for (String numberString : numberStringArray) {
            int number = Integer.parseInt(numberString);
            if (integers.isEmpty()) {
                integers.add(number);
            } else {
                for (int i = 0; i < integers.size(); i++) {
                    if (Math.abs(number - value) < Math.abs(integers.get(i) - value)) {
                        integers.set(i, number);
                    } else if (Math.abs(number - value) == Math.abs(integers.get(i) - value)) {
                        integers.add(number);
                        break;
                    }
                }
            }
        }

        Collections.sort(integers);
        for (int integer : integers) {
            System.out.print(integer + " ");
        }
    }
}