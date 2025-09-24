package ui.impl;

import java.util.Scanner;

import ui.UIManager;

public class ConsoleUIManager implements UIManager {

	private Scanner scanner = new Scanner(System.in);

	@Override
	public void show(String msg) {
		System.out.print(msg);
	}

	@Override
	public void showL(String msg) {
		System.out.println(msg);
	}

	@Override
	public String getString(String prompt) {
		this.show(prompt);
		return this.scanner.nextLine();
	}

	@Override
	public int getInt(String prompt) {
		while (true) {
			this.show(prompt);
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				this.showL("Invalid number, please try again. \n");
			}
		}
	}

	@Override
	public double getDouble(String prompt) {
		while (true) {
			this.show(prompt);
			try {
				return Double.parseDouble(scanner.nextLine());
			} catch (Exception e) {
				this.showL("Invalid number, please try again. \n");
			}
		}
	}

}