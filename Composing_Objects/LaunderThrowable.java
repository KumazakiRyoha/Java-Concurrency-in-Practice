public class LaunderThrowable {

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error) {
            throw (Error) t;
        }
        else
            throw new IllegalArgumentException("Not unchecked",t);
    }

}