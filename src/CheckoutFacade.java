import java.util.List;

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
            } catch (InterruptedException ignored) {
            }
            System.out.print(".");
        }
        System.out.println();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        payment.pay(amount);
        System.out.println("📩 Receipt and notification sent to customer.\n");
    }
}
