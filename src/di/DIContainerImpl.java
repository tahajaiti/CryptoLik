package di;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DIContainerImpl implements DIContainer {

    private static final DIContainerImpl instance = new DIContainerImpl();

    private Map<Class<?>, Object> instances = new HashMap<>();

    private DIContainerImpl() {
    }

    public static DIContainerImpl getInstance() {
        return instance;
    }

    // === STATIC HELPERS ===

    public static <T> void registerStatic(Class<T> type) {
        getInstance().register(type);
    }
    public static <T> void registerStatic(Class<T> type, T obj) {
        getInstance().register(type, obj);
    }

    public static <T> T resolveStatic(Class<T> type) {
        return getInstance().resolve(type);
    }

    // === INTERFACE METHODS ===

    public <T> void register(Class<T> type) {
        registerInternal(type);
    }

    public <T> void register(Class<T> type, T obj) {
        instances.put(type, obj);
    }

    public <T> T resolve(Class<T> type) {
        return resolveInternal(type);
    }

    // === PRIVATE METHODS ===

    private <T> void registerInternal(Class<T> type) {
        instances.put(type, createInstance(type));
    }

    private <T> T resolveInternal(Class<T> type) {
        T resolvedInstance = type.cast(instances.get(type));
        if (resolvedInstance == null) {
            resolvedInstance = createInstance(type);
            instances.put(type, resolvedInstance);
        }
        return resolvedInstance;
    }

    private <T> T createInstance(Class<T> type) {
        try {
            Constructor<?>[] constructors = type.getConstructors();
            if (constructors.length == 0) {
                throw new RuntimeException("No public constructors found for " + type.getName());
            }

            Constructor<?> constructor = constructors[0];

            for (Constructor<?> c : constructors) {
                if (c.getParameterCount() > constructor.getParameterCount()) {
                    constructor = c;
                }
            }

            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] params = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = resolveInternal(paramTypes[i]);
            }

            return type.cast(constructor.newInstance(params));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }

}
