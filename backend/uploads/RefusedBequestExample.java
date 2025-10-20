// Parent class with methods not suitable for all subclasses
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }

    public void giveBirth() {
        System.out.println("Giving birth to live young");
    }
}

// Subclass that doesn't need all parent methods
class Bird extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Chirp chirp");
    }

    // Birds lay eggs; this method is not relevant, but is still inherited!
    @Override
    public void giveBirth() {
        // This subclass must override the method, usually to provide a "noop" or even throw
        System.out.println("Birds lay eggs, not give live birth!");
    }

    public void fly() {
        System.out.println("Flying...");
    }
}
