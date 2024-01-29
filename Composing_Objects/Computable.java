import java.math.BigInteger;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}

