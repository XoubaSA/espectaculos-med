package es.udc.med.espectaculos.vista;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.eventoservice.EventoService;
import es.udc.med.espectaculos.model.eventoservice.EventoServiceImpl;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class VentanaPrincipalV2 implements MouseListener {

	private Shell shell;
	private int day, month, year;
	private String strDate;
	private Tree tree, tree2, tree3;
	private TableTree tableTree;
	private String eventName;
	private EventoService eventoService;
	private MusicoGrupoService musicoGrupoService;

	private Date nowDate = null; // current date
	private String selectedDate = null; // selected date
	private GridLayout gridLayout = null;
	private GridData gridData = null;

	private CLabel sunday = null;
	private CLabel monday = null;
	private CLabel tuesday = null;
	private CLabel wednesday = null;
	private CLabel thursday = null;
	private CLabel friday = null;
	private CLabel saturday = null;

	private Button yearUp = null;
	private Button yearNext = null;
	private Button monthUp = null;
	private Button monthNext = null;
	private CLabel nowLabel = null;

	private CLabel[] days = new CLabel[42];

	public String getStrDate() {
		return strDate;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public VentanaPrincipalV2() {
		this.eventoService = new EventoServiceImpl();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		final Display display = Display.getDefault();

		shell = new Shell(SWT.TOP);
		// FillLayout fillLayout = new FillLayout();
		// shell.setLayout(fillLayout);
		// shell.setText("Aplicacion espectaculos");
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		this.selectedDate = "2014/11/01";

		gridLayout = new GridLayout(7, true);
		shell.setLayout(gridLayout);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		yearUp = new Button(shell, SWT.PUSH | SWT.FLAT);
		yearUp.setText("<");
		yearUp.setLayoutData(gridData);
		yearUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				previousYear(display);
			}
		});

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		monthUp = new Button(shell, SWT.PUSH | SWT.FLAT);
		monthUp.setText("<<");
		monthUp.setLayoutData(gridData);
		monthUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				previousMonth(display);
			}
		});

		nowLabel = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		nowLabel.setLayoutData(gridData);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
		nowLabel.setText(formatter.format(new Date()));

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		monthNext = new Button(shell, SWT.PUSH | SWT.FLAT);
		monthNext.setText(">>");
		monthNext.setLayoutData(gridData);
		monthNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				nextMonth(display);
			}
		});

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		yearNext = new Button(shell, SWT.PUSH | SWT.FLAT);
		yearNext.setText(">");
		yearNext.setLayoutData(gridData);
		yearNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				nextYear(display);
			}
		});

		sunday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		sunday.setLayoutData(gridData);
		sunday.setText("Sun");

		monday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		monday.setLayoutData(gridData);
		monday.setText("Mon");

		tuesday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		tuesday.setLayoutData(gridData);
		tuesday.setText("Tue");

		wednesday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		wednesday.setLayoutData(gridData);
		wednesday.setText("Wed");

		thursday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		thursday.setLayoutData(gridData);
		thursday.setText("Thu");

		friday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		friday.setLayoutData(gridData);
		friday.setText("Fri");

		saturday = new CLabel(shell, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		saturday.setLayoutData(gridData);
		saturday.setText("Sat");

		for (int i = 0; i < 42; i++) {
			days[i] = new CLabel(shell, SWT.FLAT | SWT.CENTER);
			gridData = new GridData(GridData.FILL_HORIZONTAL
					| GridData.FILL_VERTICAL);
			days[i].setLayoutData(gridData);
			days[i].setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			days[i].addMouseListener(this);
			days[i].setToolTipText("double click get current date.");
		}

		Calendar now = Calendar.getInstance(); //
		nowDate = new Date(now.getTimeInMillis());
		setDayForDisplay(now, display);

		getDate();
		
		tree = new Tree(shell, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 20;
		gridData.heightHint = 200;
		tree.setLayoutData(gridData);
		// Obtener el elemento seleccionado del tree1
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(arg0.item);
				int startsWith = arg0.item.toString().indexOf("{") + 1;
				int endsWith = arg0.item.toString().indexOf("}");
				eventName = arg0.item.toString()
						.substring(startsWith, endsWith);
				System.out.println(eventName);
				setEvent(eventName);
			}
		});
		tree2 = new Tree(shell, SWT.BORDER);
		tree2.setLayoutData(gridData);

		Combo c1 = new Combo(shell, SWT.DEFAULT);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 20;
		gridData.verticalAlignment = SWT.TOP;
		c1.setLayoutData(gridData);
		
		String[] grupos = new String[100];

		// List<Grupo> gruposList = musicoGrupoService.getGrupos();
		// for (int i = 0; i<gruposList.size() ; i++) {
		// c1.setItem(i, gruposList.get(i).getNombreOrquesta());
		// }
		// c1.setItem(0,"Combo Dominicano");

		String items[] = { "Combo Dominicano" };
		c1.setItems(items);

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void getDate() {
		System.out.println("Hola");
		System.out.println(this.selectedDate);

		day = Integer.valueOf(this.selectedDate.substring(0, 4));
		System.out.println(day);
		month = Integer.valueOf(this.selectedDate.substring(5, 7));
		System.out.println(month);
		year = Integer.valueOf(this.selectedDate.substring(8, 10));
		System.out.println(year);
		strDate = Integer.toString(year) + "/";
		strDate += (month < 10) ? "0" + month + "/" : month + "/";
		strDate += (day < 10) ? "0" + day : day;

		System.out.println(strDate);
		Calendar fecha = Calendar.getInstance();
		fecha.set(Calendar.YEAR, year);
		fecha.set(Calendar.MONTH, month - 1);
		fecha.set(Calendar.DATE, day);
		List<Evento> eventos = eventoService.obtenerEventosFecha(fecha);
		System.out.println(eventos.size());
		setEvents(eventos);

	}

	private void setEvents(List<Evento> events) {
		// tree.removeAll();
		// tree2.removeAll();
		TreeItem treeItem;
		for (int i = 0; i < events.size(); i++) {
			treeItem = new TreeItem(tree, SWT.NONE);
			treeItem.setText(events.get(i).getNombreEvento());
		}
	}

	private void setEvent(String eventName) {
		try {

			Evento event = eventoService.obtenerEventoPorNombre(eventName);
			tree2.removeAll();
			TreeItem treeItem, treeItem2, treeItem3, treeItem4;
			// Todos los metodos get implementados
			// MAnana corregire las variables globales al principio y demas
			treeItem = new TreeItem(tree2, SWT.NONE);
			treeItem2 = new TreeItem(tree2, SWT.NONE);
			treeItem3 = new TreeItem(tree2, SWT.NONE);
			treeItem.setText("Nombre del evento: " + event.getNombreEvento());
			treeItem2.setText("Fecha de inicio: "
					+ ConvertidorFechas.convertirCalendarString(event
							.getFechaInicioEvento()));
			treeItem3.setText("Localidad: " + event.getLocalidad());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	private void moveTo(int type, int value, Display display) {
		Calendar now = Calendar.getInstance(); // get current Calendar object
		now.setTime(nowDate); // set current date
		now.add(type, value); // add to spec time.
		nowDate = new Date(now.getTimeInMillis()); // result
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");// format
																		// date
		nowLabel.setText(formatter.format(nowDate)); // set to label
		setDayForDisplay(now, display);
	}

	private void setDayForDisplay(Calendar now, Display display) {
		int currentDay = now.get(Calendar.DATE);
		now.add(Calendar.DAY_OF_MONTH, -(now.get(Calendar.DATE) - 1)); //
		int startIndex = now.get(Calendar.DAY_OF_WEEK) - 1; //
		int year = now.get(Calendar.YEAR); //
		int month = now.get(Calendar.MONTH) + 1; //
		int lastDay = this.getLastDayOfMonth(year, month); //
		int endIndex = startIndex + lastDay - 1; //
		int startday = 1;
		for (int i = 0; i < 42; i++) {
			Color temp = days[i].getBackground();
			if (temp.equals(display.getSystemColor(SWT.COLOR_BLUE))) {
				days[i].setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			}
		}
		for (int i = 0; i < 42; i++) {
			if (i >= startIndex && i <= endIndex) {
				days[i].setText("" + startday);
				if (startday == currentDay) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_BLUE)); //
				}
				startday++;
			} else {
				days[i].setText("");
			}
		}
	}

	public void previousYear(Display display) {
		moveTo(Calendar.YEAR, -1, display);
	}

	public void nextYear(Display display) {
		moveTo(Calendar.YEAR, 1, display);
	}

	public void nextMonth(Display display) {
		moveTo(Calendar.MONTH, 1, display);
	}

	public void previousMonth(Display display) {
		moveTo(Calendar.MONTH, -1, display);
	}

	public void mouseDoubleClick(MouseEvent e) {
		CLabel day = (CLabel) e.getSource();
		if (!day.getText().equals("")) {
			this.selectedDate = nowLabel.getText() + "/" + day.getText();
			System.out.println(this.selectedDate);
			getDate();
		}
	}

	private String getSelectedDate() {
		return this.selectedDate;
	}

	public void mouseDown(MouseEvent e) {
	}

	public void mouseUp(MouseEvent e) {
	}

}
