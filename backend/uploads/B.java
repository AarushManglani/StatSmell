class A {
    public void display() { System.out.println("Hello from A"); }
}
public class B {
    public static void main(String[] args) {
        A a = new A();
        a.display();
    }
}