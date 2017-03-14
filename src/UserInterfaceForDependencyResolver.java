import javax.swing.JOptionPane;

public class UserInterfaceForDependencyResolver {
	public static void main(String[] args) {
		String input = JOptionPane.showInputDialog("Enter package list: ");

		DependencyResolver depResolver = new DependencyResolver();
		try {
			depResolver.resolve(input);
			JOptionPane.showMessageDialog(null, "Got these packages... \n\n" + depResolver.getOriginalPackageList());
			JOptionPane.showMessageDialog(null, "Order of installation should be... \n\n" + depResolver.getInstallationOrder());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage(),
				    "Input error",
				    JOptionPane.ERROR_MESSAGE);
		}

	}
}
