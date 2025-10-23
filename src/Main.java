public class Main {
    public static void main(String[] args) {
        CheckoutFacade checkout = new CheckoutFacade();
        ConsoleUI ui = new ConsoleUI(checkout);
        ui.start();
    }
}
