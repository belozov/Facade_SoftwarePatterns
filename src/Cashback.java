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