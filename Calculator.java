import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------");
        System.out.print("Enter an expression: ");
        String expression = scanner.nextLine();

        double result = evaluateExpression(expression);

        System.out.println(expression + " = " + result);
    }

    public static double evaluateExpression(String expression) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                double num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                i--;

                operands.push(num);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    double result = applyOperator(operands.pop(), operands.pop(), operators.pop());
                    operands.push(result);
                }
                operators.pop(); // Remove the '('
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    double result = applyOperator(operands.pop(), operands.pop(), operators.pop());
                    operands.push(result);
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            double result = applyOperator(operands.pop(), operands.pop(), operators.pop());
            operands.push(result);
        }

        return operands.pop();
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    public static double applyOperator(double num2, double num1, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
        }
        return 0;
    }
}
