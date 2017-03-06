import java.util.List;

import javax.swing.JOptionPane;

public class UserInterfaceForDependencyResolver {
	public static void main(String[] args) {
		String input = JOptionPane.showInputDialog("Enter package list: ");
		input = DependencyResolver.removeBrackets(input);
		List<String> rawPackages = DependencyResolver.tokenize(input);

		StringBuilder resultingPackages = new StringBuilder();
		for (String string : rawPackages) {
			Package aPackage;
			try {
				aPackage = DependencyResolver.createPackage(string);
				resultingPackages.append(aPackage);
				resultingPackages.append("\n");
			} catch (InvalidPackageException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}

		JOptionPane.showMessageDialog(null, resultingPackages);
	}
}
