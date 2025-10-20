package com.company.security;
public class AccessModifierDemo {
    private int privateField;
    String defaultField;
    protected double protectedField;
    public boolean publicField;

    private void privateMethod() { System.out.println("Private method called"); }
    void defaultMethod() { System.out.println("Default method called"); }
    protected void protectedMethod() { System.out.println("Protected method called"); }
    public void publicMethod() { System.out.println("Public method called"); }

    public AccessModifierDemo(int a, String b, double c, boolean d) {
        this.privateField = a;
        this.defaultField = b;
        this.protectedField = c;
        this.publicField = d;
    }

    public void testInternalAccess() {
        System.out.println(privateField + " " + defaultField + " " + protectedField + " " + publicField);
        privateMethod();
        defaultMethod();
        protectedMethod();
        publicMethod();
    }

    public static void main(String[] args) {
        AccessModifierDemo demo = new AccessModifierDemo(1, "x", 2.5, true);
        System.out.println(demo.publicField);
        demo.publicMethod();
        System.out.println(demo.defaultField);
        demo.defaultMethod();
        System.out.println(demo.protectedField);
        demo.protectedMethod();
        // demo.privateField;       // not allowed
        // demo.privateMethod();    // not allowed
        demo.testInternalAccess();
        SamePackageTest.testAccess();
    }
}

class SamePackageTest {
    public static void testAccess() {
        AccessModifierDemo d = new AccessModifierDemo(7, "pkg", 9.9, false);
        System.out.println(d.publicField);
        d.publicMethod();
        System.out.println(d.defaultField);
        d.defaultMethod();
        System.out.println(d.protectedField);
        d.protectedMethod();
    }
}
