public class test {
    public static void main(String[] args) {
        DLStack<Integer> stack = new DLStack<Integer>();

        for (int i = 0; i < 10; ++i) {
            stack.push(i);
        }
        System.out.println(stack);
    }
}