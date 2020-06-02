/* Please, do not rename it */
class Problem {

    public static void main(String[] args) {
        String operator = args[0];
        int[] numbers = new int[args.length - 1];

        int result = 0;
        for (int i = 1; i < args.length; i++) {
            switch (operator) {
                case "MAX":
                    if (Integer.parseInt(args[i]) > result) {
                        result = Integer.parseInt(args[i]);
                    }
                    break;
                case "MIN":
                    if (Integer.parseInt(args[i]) < result || result == 0) {
                        result = Integer.parseInt(args[i]);
                    }
                    break;
                case "SUM":
                    result += Integer.parseInt(args[i]);
                    break;
                default:
                    break;
            }
        }
        System.out.println(result);
    }
}