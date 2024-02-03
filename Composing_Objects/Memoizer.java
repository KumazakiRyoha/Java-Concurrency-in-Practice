import jdk.vm.ci.code.site.Call;

import java.util.concurrent.*;

public class Memoizer<A,V> implements Computable<A,V> {

    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException, ExecutionException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = () -> c.compute(arg);
                FutureTask<V> ft = new FutureTask<>(eval);
                f = cache.putIfAbsent(arg,ft);
                if (f == null) { f = ft;ft.run(); }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg,f);
            } catch (ExecutionException e) {
                throw e;
            }
        }
    }
}
