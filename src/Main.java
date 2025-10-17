import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// Interface
interface Payment {
    void pay(double amount);

    static double randomAmount() {
        return ThreadLocalRandom.current().nextDouble(0.0, 124000.0);
    }
}

// Concrete classes
class FreedomBank implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Payment was successful!\n" + amount + "₸ (FreedomBank)");
    }
}

class KaspiBank implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Payment was successful!\n" + amount + "₸ (KaspiBank)");
    }
}

// Builder
class Bank {
    private String name = "CustomBank";
    private String creditType = "Standard";

    public Bank setName(String name) {
        this.name = name;
        return this;
    }

    public Bank setCreditType(String creditType) {
        this.creditType = creditType;
        return this;
    }

    public Payment buildBank() {
        return new Payment() {
            @Override
            public void pay(double amount) {
                System.out.println("Payment in the amount of: " + amount + "₸ was made through the user's card "
                        + name + "(" + creditType + ")");
            }
        };
    }
}

// Abstract decorator
abstract class PaymentDecorator implements Payment {
    protected Payment wrappee;

    public PaymentDecorator(Payment wrappee) {
        this.wrappee = wrappee;
    }
}

// Discount decorator
class Discount extends PaymentDecorator {
    private double discount;

    public Discount(Payment wrappee, double discount) {
        super(wrappee);
        this.discount = discount;
    }

    @Override
    public void pay(double amount) {
        double discounted = amount * (1 - discount / 100);
        System.out.println("Discount operation was successful!\nNew Total: " + discounted + "₸");
        wrappee.pay(discounted);
    }
}

// Cashback decorator
class Cashback extends PaymentDecorator {
    private double cashbackPercent;

    public Cashback(Payment wrappee, double cashbackPercent) {
        super(wrappee);
        this.cashbackPercent = cashbackPercent;
    }

    @Override
    public void pay(double amount) {
        double cashback = amount * cashbackPercent / 100;
        System.out.println("Cashback operation was successful!\nYour cashback: " + cashback + " coins");
        wrappee.pay(amount);
    }
}

// Fraud detection decorator
class FraudDetection extends PaymentDecorator {
    public FraudDetection(Payment wrappee) {
        super(wrappee);
    }

    @Override
    public void pay(double amount) {
        System.out.println("Running fraud detection for transaction: " + amount + "₸");
        wrappee.pay(amount);
        System.out.println("Fraud check is complete!");
    }
}

// Facade
class CheckoutFacade {
    public void processOrder(double amount, String bankName, String creditType, List<String> decorators) {
        Payment payment;

        switch (bankName.toLowerCase()) {
            case "kaspibank":
                payment = new KaspiBank();
                break;
            case "freedombank":
                payment = new FreedomBank();
                break;
            default:
                payment = new Bank()
                        .setName(bankName)
                        .setCreditType(creditType != null ? creditType : "Standard")
                        .buildBank();
        }

        for (String d : decorators) {
            switch (d.toLowerCase()) {
                case "discount":
                    payment = new Discount(payment, 10.0);
                    break;
                case "cashback":
                    payment = new Cashback(payment, 5.0);
                    break;
                case "fraud":
                    payment = new FraudDetection(payment);
                    break;
            }
        }

        System.out.print("💼 Processing order");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
            System.out.print(".");
        }
        System.out.println();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        payment.pay(amount);
        System.out.println("📩 Receipt and notification sent to customer.\n");
    }
}

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

// Demo
public class Main {
    public static void main(String[] args) {
        CheckoutFacade checkout = new CheckoutFacade();
        ConsoleUI ui = new ConsoleUI(checkout);
        ui.start();
    }
}
