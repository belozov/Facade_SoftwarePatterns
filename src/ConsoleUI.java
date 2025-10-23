import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Console UI
class ConsoleUI {
    private final CheckoutFacade checkout;

    public ConsoleUI(CheckoutFacade checkout) {
        this.checkout = checkout;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String emoji = "\uD83E\uDD16";
        System.out.println(emoji + " Cloudly Assistant: Greetings! Welcome to payment system page!");
        System.out.print(emoji + ": Enter the purchase price: ");

        Double amount = null;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid amount.");
            return;
        }

        System.out.println(emoji + ": Choose your Bank System");
        System.out.println("1. KaspiBank  2. FreedomBank");
        String bankName = scanner.nextLine();

        String creditType = null;
        if (!bankName.equalsIgnoreCase("kaspibank") && !bankName.equalsIgnoreCase("freedombank")) {
            System.out.print("💳 Please specify the card type (e.g. Gold, Visa, MasterCard): ");
            creditType = scanner.nextLine();
        }

        System.out.println(emoji + ": Available functions: discount (10%), cashback (5%), fraud (security check)");
        String decoratorInput = scanner.nextLine();
        List<String> decorators = new ArrayList<>();
        if (!decoratorInput.isEmpty()) {
            decorators = Arrays.asList(decoratorInput.split(","));
        }

        System.out.println(emoji + ": Would you like to make a payment of " + amount + "₸ using " + bankName + "? (yes or no)");
        String confirm = scanner.nextLine().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Transaction cancelled.");
            return;
        }

        checkout.processOrder(amount, bankName, creditType, decorators);
    }
}
