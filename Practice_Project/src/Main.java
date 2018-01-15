public class Main {
    public static void main(String args[]){
        // Abstract Class Example
        abstractClassExtension temp = new abstractClassExtension();
        temp.foo();

        // Singleton Example
        singletonClass a = singletonClass.getInstance();
        singletonClass b = singletonClass.getInstance();

        a.s = (a.s).toUpperCase();

        System.out.println("String from a is: " + a.s);
        System.out.println("String from b is: " + b.s);

        b.s = (b.s).toLowerCase();

        System.out.println("String from a is: " + a.s);
        System.out.println("String from b is: " + b.s);
        // Inheritance Example



    }
}
