import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main {

    //стэк для перевода в ОПЗ
    static Stack stack = new Stack();
    //стэк для вычисления результата выражения по ОПЗ
    static Stack stackNums = new Stack();
    //входная строка с выражением
    static String inStr = "12.2+14.9+(10-2)*4+(11.8-6+(5+4.1)*3)*2";
    //выходная строка
    static ArrayList<String> outStr = new ArrayList<String>();
    //список цифр
    static List<String> nums = Arrays.asList("1","2","3","4","5","6","7","8","9","0",".");
    //список операторов и скобок
    static List<String> operands = Arrays.asList("+","-","/","*","(",")");
    //номер текущего char из входной строки
    static int inStrPos = 0;

//test

    /**
     * Получение символа из входной строки
     * @return очередной символ из входной строки
     */
    public static String getSymFromInString(){
        if (inStrPos == inStr.length())
            return "";
        String out = "";
        if (operands.contains(String.valueOf(inStr.charAt(inStrPos))))
            return String.valueOf(inStr.charAt(inStrPos++));
        while (inStrPos < inStr.length() && nums.contains(String.valueOf(inStr.charAt(inStrPos)))){
            out += inStr.charAt(inStrPos++);
        }
        return out;
    }

    /**
     * Определение приоритета операции
     * @param oper оператор
     * @return значение приоритета
     */
    public static int getPriority(String oper){
        switch (oper) {
            case "+":
                return 2;
            case "-":
                return 2;
            case "/":
                return 3;
            case "*":
                return 3;
            case "(":
                return 1;
            case ")":
                return 1;
            default: return 0;
        }
    }



    public static void intoOPZ(){
        while (inStrPos < inStr.length()){
            String currentSymbol = getSymFromInString();

            if (operands.contains(currentSymbol)){

                if (currentSymbol.equals("(")) {
                    stack.push(currentSymbol);
                    continue;
                }

                if (currentSymbol.equals(")")) {
                    String symb;
                    while (!(symb=stack.pop().toString()).equals("(")){
                        outStr.add(symb);
                    }
                    continue;
                }

                while (!stack.empty() && getPriority(stack.peek().toString()) >= getPriority(currentSymbol)) {
                    outStr.add(stack.pop().toString());
                }

                if (stack.empty() || getPriority(stack.peek().toString()) < getPriority(currentSymbol)){
                    stack.push(currentSymbol);
                }

            } else {
                outStr.add(currentSymbol);
            }
        }

        while (!stack.empty()){
            outStr.add(stack.pop().toString());
        }


        System.out.println("Выходная строка ОПЗ: "+outStr);
    }


    public static double computeTerm(){
        for (String symb : outStr){
            if (operands.contains(symb)){
                double num1 = (double)stackNums.pop();
                double num2 = (double)stackNums.pop();
                switch (symb) {
                    case "/":
                        stackNums.push(num2/num1);
                        break;
                    case "+":
                        stackNums.push(num2+num1);
                        break;
                    case "-":
                        stackNums.push(num2-num1);
                        break;
                    case "*":
                        stackNums.push(num2*num1);
                        break;
                }
            } else {
                stackNums.push(Double.parseDouble(symb));
            }
        }
        return (double)stackNums.pop();
    }



    public static void main(String[] args) {

        intoOPZ();
        System.out.println("Результат: " + computeTerm());

    }
}
