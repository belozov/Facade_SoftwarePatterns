abstract class PaymentDecorator implements Payment {
    protected Payment wrappee;

    public PaymentDecorator(Payment wrappee) {
        this.wrappee = wrappee;
    }
}