package es.udc.med.espectaculos.vista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.eventoservice.EventoService;
import es.udc.med.espectaculos.model.eventoservice.EventoServiceImpl;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoServiceImpl;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class VentanaPrincipalV2 implements MouseListener {

	private Shell shell;
	private int day, month, year;
	private String strDate;
	private Tree tree_1, tree2, tree3;
	private TableTree tableTree;
	private String eventName;
	private EventoService eventoService;
	private MusicoGrupoService musicoGrupoService;

	private Date nowDate = null; // current date
	private String selectedDate = null; // selected date
	private GridLayout gridLayout = null;
	private GridData gridData = null;
	private GridData gridData2 = null;

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
		this.musicoGrupoService= new MusicoGrupoServiceImpl();
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		final Display display = Display.getDefault();

		shell = new Shell(display);
		shell.setMinimumSize(new Point(80, 28));
		shell.setSize(697, 363);

		this.selectedDate = "2014/11/01";

		gridLayout = new GridLayout(7, true);
		shell.setLayout(new FillLayout());
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		yearUp = new Button(composite, SWT.PUSH | SWT.FLAT);
		yearUp.setText("<");
		yearUp.setLayoutData(gridData);
		yearUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				previousYear(display);
			}
		});

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		monthUp = new Button(composite, SWT.PUSH | SWT.FLAT);
		monthUp.setText("<<");
		monthUp.setLayoutData(gridData);
		monthUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				previousMonth(display);
			}
		});

		nowLabel = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		nowLabel.setLayoutData(gridData);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
		nowLabel.setText(formatter.format(new Date()));

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		monthNext = new Button(composite, SWT.PUSH | SWT.FLAT);
		monthNext.setText(">>");
		monthNext.setLayoutData(gridData);
		monthNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				nextMonth(display);
			}
		});

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		yearNext = new Button(composite, SWT.PUSH | SWT.FLAT);
		yearNext.setText(">");
		yearNext.setLayoutData(gridData);
		yearNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				nextYear(display);
			}
		});

		sunday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		sunday.setLayoutData(gridData);
		sunday.setText("Sun");

		monday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		monday.setLayoutData(gridData);
		monday.setText("Mon");

		tuesday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		tuesday.setLayoutData(gridData);
		tuesday.setText("Tue");

		wednesday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		wednesday.setLayoutData(gridData);
		wednesday.setText("Wed");

		thursday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		thursday.setLayoutData(gridData);
		thursday.setText("Thu");

		friday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		friday.setLayoutData(gridData);
		friday.setText("Fri");

		saturday = new CLabel(composite, SWT.CENTER | SWT.SHADOW_OUT);
		gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.widthHint = 20;
		gridData.heightHint = 20;
		saturday.setLayoutData(gridData);
		saturday.setText("Sat");

		for (int i = 0; i < 42; i++) {
			days[i] = new CLabel(composite, SWT.FLAT | SWT.CENTER);
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
		pinta(now,display);
		
		getDate();
		
		Group group0 = new Group(sashForm, SWT.NULL);
		group0.setLayout(null);
		
		Label lblNewLabel = new Label(group0, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 133, 33);
		lblNewLabel.setText("Filtre los eventos por\n\tgrupo:");
		
		final Combo combo = new Combo(group0, SWT.NONE);
		combo.setBounds(10, 49, 133, 41);
		
		Button botonAnadirGrupoEvento = new Button(group0,SWT.NONE);
		botonAnadirGrupoEvento.setBounds(10, 80, 133, 41);
		botonAnadirGrupoEvento.setText("AnadirGrupoEvento");
		
		botonAnadirGrupoEvento.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//TODO: Recuperar las 3 cosas que faltan
				//eventoService.asignarGrupoEvento(grupo, evento, fecha);
			}
		});
		
		tree_1 = new Tree(group0, SWT.BORDER);
		tree_1.setBounds(149, 10, 361, 145);
		
		combo.setText("Todos los grupos");
		combo.select(0);
		
		List<Grupo> grupos = musicoGrupoService.getGrupos();
		for (Grupo grupo : grupos) {
			combo.add(grupo.getNombreOrquesta());
		}
		
		combo.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		          System.out.println(combo.getText());
		          //combo.getText() devuelve el nombre del grupo
		          //TODO: Debemos pasar el grupo, de forma que la lista devuelta de eventos sea solo en la que dicho grupo actua
		        }
		      });
		
		Label lblNewLabel2 = new Label(group0, SWT.NONE);
		lblNewLabel2.setBounds(520, 10, 133, 80);
		lblNewLabel2.setText("Green      -> 0 events\n"
				+ "Blue         -> 1 events\n"
				+ "Dark Red -> 2 events\n"
				+ "Magenta -> 3 events\n"
				+ "Cyan        -> 4 events");
		/*		
		//TODO: For que recorra los componentes de un grupo y LLamada al servicio cuando este		

		tree3 = new Tree(group0, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 20;
		gridData.heightHint = 200;
		tree3.setLayoutData(gridData);
		
		Combo c2 = new Combo(group0, SWT.DEFAULT);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 20;
		gridData.verticalAlignment = SWT.TOP;
		c2.setLayoutData(gridData);
		
		List<Musico> musicos = musicoGrupoService.getMusicos();
		for (Musico musico : musicos) {
			c2.add(musico.getNombreMusico());
		}
		*/

		shell.open();
		//shell.layout();
		//shell.pack();
		//composite.pack();
		//group0.pack();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void getDate() {
		try {
			day = Integer.valueOf(this.selectedDate.substring(8, 10));
		} catch(Exception e) {
			day = Integer.valueOf(this.selectedDate.substring(8, 9));
		}
		month = Integer.valueOf(this.selectedDate.substring(5, 7));
		year = Integer.valueOf(this.selectedDate.substring(0, 4));
		strDate = Integer.toString(year) + "/";
		strDate += (month < 10) ? "0" + month + "/" : month + "/";
		strDate += (day < 10) ? "0" + day : day;
		Calendar fecha = Calendar.getInstance();
		fecha.set(Calendar.YEAR, year);
		fecha.set(Calendar.MONTH, month - 1);
		fecha.set(Calendar.DATE, day);
		List<Evento> eventos = eventoService.obtenerEventosFecha(fecha);
		setEvents(eventos);
	}

	private void setEvents(List<Evento> events) {
		TreeItem treeItem;
		for (int i = 0; i < events.size(); i++) {
			treeItem = new TreeItem(tree_1, SWT.NONE);
			treeItem.setText(events.get(i).getNombreEvento());
			TreeItem localidadItem = new TreeItem(treeItem, 0);
			localidadItem.setText("Localidad: " + events.get(i).getLocalidad());
			TreeItem fechaItem = new TreeItem(treeItem, 0);
			fechaItem.setText("Fecha: " + ConvertidorFechas.convertirCalendarString(events.get(i).getFechaInicioEvento()));
			TreeItem gruposItem = new TreeItem(treeItem, 0);
			gruposItem.setText("Grupos: " + "(falta por implementar llamada que obtenga grupos partiendo de un evento)");
			List<Grupo> grupos= new ArrayList<>();//TODO: llamada al servicio, recuperar grupos del evento
			for (Grupo grupo : grupos) {
				TreeItem grupoItem = new TreeItem(gruposItem, 0);
				grupoItem.setText(grupo.getNombreOrquesta());
			}
			
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
		pinta(now,display);
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
			if (!temp.equals(display.getSystemColor(SWT.COLOR_WHITE))) {
				days[i].setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			}
		}
	}

	public void pinta (Calendar now, Display display) {
		now.add(Calendar.DAY_OF_MONTH, -(now.get(Calendar.DATE) - 1)); //
		int startIndex = now.get(Calendar.DAY_OF_WEEK) - 1; //
		int year = now.get(Calendar.YEAR); //
		int month = now.get(Calendar.MONTH) + 1; //
		int lastDay = this.getLastDayOfMonth(year, month); //
		int endIndex = startIndex + lastDay - 1; //
		int startday = 1;
		for (int i = 0; i < 42; i++) {
			if (i >= startIndex && i <= endIndex) {
				days[i].setText("" + startday);

				//if (startday == currentDay) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_BLUE)); //
				//}
					
					Calendar fecha = Calendar.getInstance();
					fecha.set(Calendar.YEAR, year);
					fecha.set(Calendar.MONTH, month - 1);
					fecha.set(Calendar.DATE, startday);
					List<Evento> eventos = eventoService.obtenerEventosFecha(fecha);
					if (eventos.size()==0) {
						days[i].setBackground(display.getSystemColor(SWT.COLOR_GREEN));
					}
					if (eventos.size()==1) {
						days[i].setBackground(display.getSystemColor(SWT.COLOR_BLUE));
					}
					if (eventos.size()==2) {
						days[i].setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));
					}
					if (eventos.size()==3) {
						days[i].setBackground(display.getSystemColor(SWT.COLOR_MAGENTA));
					}
					if (eventos.size()==4) {
						days[i].setBackground(display.getSystemColor(SWT.COLOR_CYAN));
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
			// Limpiamos el árbol si ya contiene elementos
			if (tree_1.getItemCount() > 0) {
				tree_1.removeAll();
			}
			this.selectedDate = nowLabel.getText() + "/" + day.getText();
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
