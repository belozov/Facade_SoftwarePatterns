import java.util.concurrent.ThreadLocalRandom;

interface Payment {
    void pay(double amount);

    static double randomAmount() {
        return ThreadLocalRandom.current().nextDouble(0.0, 124000.0);
    }
}
