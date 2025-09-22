package di;

public interface DIContainer {
    <T> void register(Class<T> classType, T instance);

    <T> T resolve(Class<T> type);
}
