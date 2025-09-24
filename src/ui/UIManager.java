package ui;

public interface UIManager {
    void show(String msg);

    void showL(String msg);

    String getString(String prompt);

    int getInt(String prompt);

    double getDouble(String prompt);
}
