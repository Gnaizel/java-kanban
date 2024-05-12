public class Main {

    public static void main(String[] args) {
        hello();

    }

    public boolean CheckingNull(Object o) {
        if (o == null) {
            System.out.println("Такго значения не сущетвует.");
            return false;
        } else {
            return true;
        }
    }

    private static void hello() {
        String hello = "";
        System.out.println(hello);
    }
}
