package otus.mytest;

public class StarterHw5 {
    public static void main(String[] args) {
        System.out.println("invoke tests in class");
        MyTestExecutor.invoke(MyTest.class);

        System.out.println("=======================");

        System.out.println("invoke tests in package:");
        MyTestExecutor.invoke("otus");
    }
}
