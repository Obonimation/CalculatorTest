import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите команду: ");
        String s = scanner.nextLine();
        System.out.println("Результат: " + Main.calc(s));
    }
   public static String calc(String input) throws Exception {
        boolean hasRome = false;
        input=input.replaceAll(" ", "");
        input=input.toUpperCase();
        String[] ops = {"+", "-", "/", "*"};
        String[] regOps = {"\\+", "-", "/", "\\*"};
        short opIndex = -1;
        for (short i = 0; i < ops.length; i++) {
            if (input.contains(ops[i])){
                opIndex = i;
                break;
            }
        }
        if (opIndex == -1) {
            throw new Exception("Вы ввели неверную математическую операцию");
        }
        String[] inputArray = input.split(regOps[opIndex]);
        if (inputArray.length == 1 || inputArray.length > 2) {
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        short num1 = 0;
        short num2 = 0;
        if (Main.isRome(inputArray)) {
            RomeNum numGet1 = RomeNum.valueOf(inputArray[0]);
            RomeNum numGet2 = RomeNum.valueOf(inputArray[1]);
            inputArray[0] = numGet1.getArabNum();
            inputArray[1] = numGet2.getArabNum();
            hasRome = true;
        } else if (Main.isArabic(inputArray)){
            short x1 = Short.parseShort(inputArray[0]);
            short x2 = Short.parseShort(inputArray[1]);
            if ((x1 < 1 || x1 > 10) || (x2 < 1 || x2 > 10)){
                throw new Exception("Калькулятор принимает только числа от 1 до 10 включительно");
            }

        } else {
            throw new Exception("Проверьте формат введенных чисел. Калькулятор принимает только целые арабские или только римские числа от 1 до 10 включительно");
        }
        num1 = Short.parseShort(inputArray[0]);
        num2 = Short.parseShort(inputArray[1]);
        String result = null;
        char action = ops[opIndex].charAt(0);
        switch (action) {
            case '+' -> result = String.valueOf(num1 + num2);
            case '-' -> result = String.valueOf(num1 - num2);
            case '*' -> result = String.valueOf(num1 * num2);
            case '/' -> result = String.valueOf(num1 / num2);
        }
        if (hasRome && Short.parseShort(result) > 0) return Main.toRome(result);
        else if (hasRome) {
            throw new Exception("Результат - отрицательное число или ноль. В римской системе только положительные целые числа");
        }
        return result;
   }
    public static String toRome(String romeResult){
        int romeResultInt = Integer.parseInt(romeResult);
        short[] arabArray = {1, 4, 5, 9, 10, 40, 50, 90, 100};
        String[] romeArray = {"I", "IV", "V", "IX", "X", "XL", "L", "LC", "C"};
        String romeNumFinal = "";
        do{
            for (int i = 0; i < arabArray.length; i++) {
             if (arabArray[i] > romeResultInt && arabArray[i-1] < romeResultInt) {
                romeResultInt -= arabArray[i-1];
                romeNumFinal += romeArray[i-1];
                break;
             }
                if (arabArray[i] == romeResultInt) {
                    romeResultInt -= arabArray[i];
                    romeNumFinal += romeArray[i];
                    break;
                }
            }
        } while (romeResultInt != 0);
        return romeNumFinal;
    }
    public static boolean isRome(String[] a) {
        try {
            RomeNum.valueOf(a[0]);
            RomeNum.valueOf(a[1]);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
    public static boolean isArabic(String[] a) {
        try {
            Short.parseShort(a[0]);
            Short.parseShort(a[1]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
