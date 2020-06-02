class Problem {

    public static void main(String[] args) {
        int valOne = Integer.parseInt(args[1]);
        int valTwo = Integer.parseInt(args[2]);

        switch (args[0]) {
            case "+":
                System.out.println(valOne + valTwo);
                break;
            case "-":
                System.out.println(valOne - valTwo);
                break;
            case "*":
                System.out.println(valOne * valTwo);
                break;
            default:
                System.out.println("Unknown operator");
                break;
        }
    }
}