package di;

public interface DIContainer {
    <T> void register(Class<T> type);

    <T> void register(Class<T> type, T obj);

    <T> T resolve(Class<T> type);
}