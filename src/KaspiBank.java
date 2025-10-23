class KaspiBank implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Payment was successful!\n" + amount + "₸ (KaspiBank)");
    }
}
