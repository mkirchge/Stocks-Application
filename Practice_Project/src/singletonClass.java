public class singletonClass {

    private static singletonClass single_instance = null;

    public String s;

    private singletonClass() {
        s = "This is a string in a singleton class";
    }

    public static singletonClass getInstance() {
        if (single_instance == null)
            single_instance = new singletonClass();
        return single_instance;
    }

}
