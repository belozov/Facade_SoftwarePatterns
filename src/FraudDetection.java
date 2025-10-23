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
