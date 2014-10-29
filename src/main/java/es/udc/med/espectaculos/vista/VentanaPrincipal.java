package es.udc.med.espectaculos.vista;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class VentanaPrincipal {

	public static void iniciar() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Aplicacion SWT");
		shell.setLayout(new RowLayout());
		Composite parent = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);
		DateTime calendar = new DateTime(parent, SWT.CALENDAR);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
