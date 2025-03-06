package files;

public class Example2 {
    // Single-line comment

    @Deprecated
    public int methodTwo(int a, int b) {
        if(a > b){
            System.out.println("a is greater than b");
        }else if (true) {
            System.out.println('s');
        }
        return a + b;
    }
}