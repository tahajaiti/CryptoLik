package di;

import java.util.HashMap;
import java.util.Map;

public class DIContainerImpl implements DIContainer{

    private Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void register(Class<T> type, T instance) {
        instances.put(type, instance);
    }

    public <T> T resolve(Class<T> type) {
        @SuppressWarnings("unchecked")
        T instance = (T) instances.get(type);

        if (instance == null) {
            throw new IllegalArgumentException("No registered instance found for type: " + type.getName());
        }

        return instance;
    }
}
