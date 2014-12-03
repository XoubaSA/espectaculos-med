package es.udc.med.espectaculos.vista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.eventoservice.EventoService;
import es.udc.med.espectaculos.model.eventoservice.EventoServiceImpl;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoServiceImpl;
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.EventoExisteException;
import es.udc.med.espectaculos.utils.GrupoExisteException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;
import es.udc.med.espectaculos.utils.MusicoExisteException;

public class VentanaPrincipalV2 implements MouseListener {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");

	private Shell shell;
	private int day, month, year;
	private String strDate;
	private Tree tree_1, tree2, tree3, tree4;
	private DateTime dateTime;
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

	private Combo combo_1;
	private Combo combo_2;

	private CLabel[] days = new CLabel[42];
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;

	private Combo combo;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_14;
	private Text text_16;
	private Text text_17;

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
		this.musicoGrupoService = new MusicoGrupoServiceImpl();
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		final Display display = Display.getDefault();

		shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE) & (~SWT.MAX));
		shell.setMinimumSize(new Point(80, 28));
		shell.setSize(690, 372);

		this.selectedDate = "2014/11/01";
		shell.setLayout(new FillLayout());

		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);

		final TabItem tbtmCalendario = new TabItem(tabFolder, SWT.NONE);
		tbtmCalendario.setText("Calendario");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmCalendario.setControl(composite_1);
		composite_1.setLayout(new GridLayout(1, false));

		gridLayout = new GridLayout(7, true);
		Composite composite = new Composite(composite_1, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.widthHint = 656;
		gd_composite.heightHint = 151;
		composite.setLayoutData(gd_composite);
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

		Group group0 = new Group(composite_1, SWT.NULL);
		group0.setLayout(null);

		Label lblNewLabel = new Label(group0, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 133, 33);
		lblNewLabel.setText("Filtre los eventos por\n\tgrupo:");

		combo = new Combo(group0, SWT.READ_ONLY);
		combo.setBounds(10, 49, 133, 41);

		tree_1 = new Tree(group0, SWT.BORDER);
		tree_1.setBounds(149, 10, 361, 127);

		combo.setText("Todos los grupos");
		combo.add("Todos los grupos");
		combo.select(0);

		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println(combo.getText());
				Calendar now = Calendar.getInstance();
				now.setTime(nowDate);
				now.set(Calendar.DATE, day);
				pinta(now, display);
			}
		});

		Label lblNewLabel2 = new Label(group0, SWT.NONE);
		lblNewLabel2.setBounds(520, 10, 133, 80);
		lblNewLabel2.setText("Green      -> 0 events\n"
				+ "Blue         -> 1 events\n" + "Dark Red -> 2 events\n"
				+ "Magenta -> 3 events\n" + "Cyan        -> 4 events");

		final TabItem tbtmConsultas = new TabItem(tabFolder, 0);
		tbtmConsultas.setText("Consultas");

		Composite composite_6 = new Composite(tabFolder, SWT.NONE);
		tbtmConsultas.setControl(composite_6);

		Group grpEventos = new Group(composite_6, SWT.NONE);
		grpEventos.setText("Eventos");
		grpEventos.setBounds(0, 0, 222, 315);

		Label lblSeleccioneElEvento = new Label(grpEventos, SWT.NONE);
		lblSeleccioneElEvento.setBounds(10, 20, 202, 15);
		lblSeleccioneElEvento.setText("Seleccione el evento deseado:");

		final Combo combo_7 = new Combo(grpEventos, SWT.READ_ONLY);
		combo_7.setBounds(10, 41, 202, 23);

		Label lblNombre = new Label(grpEventos, SWT.NONE);
		lblNombre.setText("Nombre:");
		lblNombre.setBounds(10, 70, 99, 15);

		text_10 = new Text(grpEventos, SWT.BORDER | SWT.READ_ONLY);
		text_10.setBounds(10, 91, 202, 21);

		Label lblFechaInicio = new Label(grpEventos, SWT.NONE);
		lblFechaInicio.setText("Fecha inicio:");
		lblFechaInicio.setBounds(10, 118, 118, 15);

		Label lblLocalidad = new Label(grpEventos, SWT.NONE);
		lblLocalidad.setText("Localidad:");
		lblLocalidad.setBounds(10, 169, 105, 15);

		text_11 = new Text(grpEventos, SWT.BORDER | SWT.READ_ONLY);
		text_11.setBounds(10, 190, 202, 21);

		text_12 = new Text(grpEventos, SWT.BORDER | SWT.READ_ONLY);
		text_12.setBounds(10, 139, 202, 21);

		Button btnHacerBusquedaEvento = new Button(grpEventos, SWT.NONE);
		btnHacerBusquedaEvento.setBounds(10, 280, 202, 25);
		btnHacerBusquedaEvento.setText("Hacer busqueda evento");
		btnHacerBusquedaEvento.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Evento eventoInfo = eventoService
							.obtenerEventoPorNombre(combo_7.getText());
					text_10.setText(eventoInfo.getNombreEvento());
					text_12.setText(ConvertidorFechas
							.convertirCalendarString(eventoInfo
									.getFechaInicioEvento()));
					text_11.setText(eventoInfo.getLocalidad());
				} catch (InstanceNotFoundException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage("No  has seleccionado ningun grupo");
					messageBox.open();
				}
			}
		});

		Group grpGrupos = new Group(composite_6, SWT.NONE);
		grpGrupos.setText("Grupos");
		grpGrupos.setBounds(228, 0, 222, 315);

		Label lblSeleccioneElGrupo = new Label(grpGrupos, SWT.NONE);
		lblSeleccioneElGrupo.setText("Seleccione el grupo deseado:");
		lblSeleccioneElGrupo.setBounds(10, 20, 202, 15);

		final Combo combo_8 = new Combo(grpGrupos, SWT.READ_ONLY);
		combo_8.setBounds(10, 41, 202, 23);

		Button btnHacerBusquedaGrupo = new Button(grpGrupos, SWT.NONE);
		btnHacerBusquedaGrupo.setText("Hacer busqueda grupo");
		btnHacerBusquedaGrupo.setBounds(10, 280, 202, 25);

		tree4 = new Tree(grpGrupos, SWT.BORDER);
		tree4.setBounds(10, 70, 202, 204);
		btnHacerBusquedaGrupo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Grupo grupoInfo = eventoService
							.obtenerGrupoPorNombre(combo_8.getText());
					tree4.removeAll();
					TreeItem nombre = new TreeItem(tree4, SWT.NONE);
					nombre.setText("Nombre: " + grupoInfo.getNombreOrquesta());
					TreeItem salario = new TreeItem(tree4, SWT.NONE);
					salario.setText("Salario: "
							+ grupoInfo.getSalarioActuacion());
					List<Musico> musicos = musicoGrupoService
							.getFormacion(grupoInfo);
					if (!musicos.isEmpty()) {
						TreeItem formacion = new TreeItem(tree4, SWT.NONE);
						formacion.setText("Integrantes");
						for (Musico musico : musicos) {
							TreeItem musicoItem = new TreeItem(formacion,
									SWT.NONE);
							musicoItem.setText(musico.getNombreMusico());
						}
					}

				} catch (InstanceNotFoundException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				}
			}
		});

		Group grpMusicos = new Group(composite_6, SWT.NONE);
		grpMusicos.setText("Musicos");
		grpMusicos.setBounds(456, 0, 222, 315);

		Label lblSeleccioneElMusico = new Label(grpMusicos, SWT.NONE);
		lblSeleccioneElMusico.setText("Seleccione el musico deseado:");
		lblSeleccioneElMusico.setBounds(10, 20, 202, 15);

		final Combo combo_9 = new Combo(grpMusicos, SWT.READ_ONLY);
		combo_9.setBounds(10, 41, 202, 23);

		Label label_10 = new Label(grpMusicos, SWT.NONE);
		label_10.setText("Nombre:");
		label_10.setBounds(10, 70, 99, 15);

		text_14 = new Text(grpMusicos, SWT.BORDER | SWT.READ_ONLY);
		text_14.setBounds(10, 91, 202, 21);

		Label lblDireccion = new Label(grpMusicos, SWT.NONE);
		lblDireccion.setText("Direccion:");
		lblDireccion.setBounds(10, 118, 118, 15);

		Label lblInstrumento = new Label(grpMusicos, SWT.NONE);
		lblInstrumento.setText("Instrumento:");
		lblInstrumento.setBounds(10, 169, 105, 15);

		text_16 = new Text(grpMusicos, SWT.BORDER | SWT.READ_ONLY);
		text_16.setBounds(10, 190, 202, 21);

		text_17 = new Text(grpMusicos, SWT.BORDER | SWT.READ_ONLY);
		text_17.setBounds(10, 139, 202, 21);

		Button btnHacerBusquedaMusico = new Button(grpMusicos, SWT.NONE);
		btnHacerBusquedaMusico.setText("Hacer busqueda musico");
		btnHacerBusquedaMusico.setBounds(10, 280, 202, 25);
		btnHacerBusquedaMusico.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Musico musicoInfo = musicoGrupoService
							.obtenerMusicoPorNombre(combo_9.getText());
					text_14.setText(musicoInfo.getNombreMusico());
					text_17.setText(musicoInfo.getDireccion());
					text_16.setText(musicoInfo.getInstrumento());
				} catch (InstanceNotFoundException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				}
			}
		});

		final TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Evento");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_2);

		Group grpCrearEvento = new Group(composite_2, SWT.NONE);
		grpCrearEvento.setText("Crear evento");
		grpCrearEvento.setBounds(0, 0, 222, 315);

		Label lblIntroduceNombre = new Label(grpCrearEvento, SWT.NONE);
		lblIntroduceNombre.setBounds(10, 20, 99, 15);
		lblIntroduceNombre.setText("Introduce nombre:");

		text = new Text(grpCrearEvento, SWT.BORDER);
		text.setBounds(10, 41, 202, 21);

		Label lblIntroduceFechaInicio = new Label(grpCrearEvento, SWT.NONE);
		lblIntroduceFechaInicio.setBounds(10, 68, 118, 15);
		lblIntroduceFechaInicio.setText("Introduce fecha inicio:");

		dateTime = new DateTime(grpCrearEvento, SWT.BORDER);
		dateTime.setBounds(10, 89, 80, 24);

		Label lblIntroduceLocalidad = new Label(grpCrearEvento, SWT.NONE);
		lblIntroduceLocalidad.setBounds(10, 119, 105, 15);
		lblIntroduceLocalidad.setText("Introduce localidad:");

		text_1 = new Text(grpCrearEvento, SWT.BORDER);
		text_1.setBounds(10, 140, 202, 21);

		Button btnAnadirEvento = new Button(grpCrearEvento, SWT.NONE);
		btnAnadirEvento.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					eventoService.crearEvento(
							text.getText(),
							ConvertidorFechas.convertirDateTimeWidget(dateTime),
							text_1.getText());
					VentanaPrincipalV2.this.combo_2.add(text.getText());
				} catch (InputValidationException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				} catch (EventoExisteException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				}
			}
		});
		btnAnadirEvento.setBounds(10, 280, 202, 25);
		btnAnadirEvento.setText("Crear evento");

		Group grpAsignarEventoA = new Group(composite_2, SWT.NONE);
		grpAsignarEventoA.setText("Asignar evento a grupo");
		grpAsignarEventoA.setBounds(228, 0, 212, 315);

		Label lblSeleccionaElGrupo = new Label(grpAsignarEventoA, SWT.NONE);
		lblSeleccionaElGrupo.setText("Selecciona el grupo:");
		lblSeleccionaElGrupo.setBounds(10, 20, 116, 15);

		combo_1 = new Combo(grpAsignarEventoA, SWT.READ_ONLY);
		combo_1.setBounds(10, 41, 192, 23);

		Label lblSeleccionaElEvento = new Label(grpAsignarEventoA, SWT.NONE);
		lblSeleccionaElEvento.setText("Selecciona el evento:");
		lblSeleccionaElEvento.setBounds(10, 68, 116, 15);

		combo_2 = new Combo(grpAsignarEventoA, SWT.READ_ONLY);
		combo_2.setBounds(10, 89, 192, 23);

		Label label_1 = new Label(grpAsignarEventoA, SWT.NONE);
		label_1.setText("Introduce fecha inicio:");
		label_1.setBounds(10, 119, 118, 15);

		final DateTime dateTime_1 = new DateTime(grpAsignarEventoA, SWT.BORDER);
		dateTime_1.setBounds(10, 140, 80, 24);
		
		combo_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					Evento evento = eventoService
							.obtenerEventoPorNombre(combo_2.getText());
					Calendar f = evento.getFechaInicioEvento();
					dateTime_1.setDate(f.get(Calendar.YEAR), 
							f.get(Calendar.MONTH), f.get(Calendar.DATE));
				} catch (InstanceNotFoundException e1) {}
			}
		});

		Button btnAsignarEventoA = new Button(grpAsignarEventoA, SWT.NONE);
		btnAsignarEventoA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Grupo grupo = eventoService.obtenerGrupoPorNombre(combo_1
							.getText());
					Evento evento = eventoService
							.obtenerEventoPorNombre(combo_2.getText());
					Calendar fecha = ConvertidorFechas
							.convertirDateTimeWidget(dateTime_1);

					eventoService.asignarGrupoEvento(grupo, evento,
							ConvertidorFechas.convertirCalendarString(fecha));
				} catch (InputValidationException | AsignarGrupoEventoException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				} catch (InstanceNotFoundException e2) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage("Selecciona un grupo y un evento");
					messageBox.open();
				}
			}
		});
		btnAsignarEventoA.setText("Asignar evento a grupo");
		btnAsignarEventoA.setBounds(10, 280, 192, 25);

		Group grpBorrarEvento = new Group(composite_2, SWT.NONE);
		grpBorrarEvento.setText("Borrar evento");
		grpBorrarEvento.setBounds(446, 0, 219, 146);

		Group grpDesasignarGrupoA = new Group(composite_2, SWT.NONE);
		grpDesasignarGrupoA.setText("Desasignar grupo a evento");
		grpDesasignarGrupoA.setBounds(446, 152, 219, 163);

		TabItem tbtmMusico = new TabItem(tabFolder, 0);
		tbtmMusico.setText("Grupo");

		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmMusico.setControl(composite_3);

		Group group = new Group(composite_3, SWT.NONE);
		group.setText("Crear grupo");
		group.setBounds(0, 0, 318, 315);

		Label label = new Label(group, SWT.NONE);
		label.setText("Introduce nombre:");
		label.setBounds(10, 20, 99, 15);

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBounds(10, 41, 298, 21);

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setText("Introduce salario:");
		label_2.setBounds(10, 68, 105, 15);

		text_3 = new Text(group, SWT.BORDER);
		text_3.setBounds(10, 89, 298, 21);

		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					eventoService.crearGrupo(text_2.getText(),
							Float.valueOf(text_3.getText()));
				} catch (NumberFormatException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox
							.setMessage("El salario introducido no es válido");
					messageBox.open();
				} catch (InputValidationException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				} catch (GrupoExisteException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				}
			}
		});
		button.setText("Crear grupo");
		button.setBounds(10, 280, 298, 25);

		Group group_1 = new Group(composite_3, SWT.NONE);
		group_1.setText("Borrar grupo");
		group_1.setBounds(324, 0, 342, 315);

		final TabItem tbtmMusico_1 = new TabItem(tabFolder, 0);
		tbtmMusico_1.setText("Musico");

		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmMusico_1.setControl(composite_4);

		Group grpCrearMusico = new Group(composite_4, SWT.NONE);
		grpCrearMusico.setText("Crear musico");
		grpCrearMusico.setBounds(0, 0, 222, 315);

		Label label_3 = new Label(grpCrearMusico, SWT.NONE);
		label_3.setText("Introduce nombre:");
		label_3.setBounds(10, 20, 99, 15);

		text_4 = new Text(grpCrearMusico, SWT.BORDER);
		text_4.setBounds(10, 41, 202, 21);

		Label lblIntroduceDireccion = new Label(grpCrearMusico, SWT.NONE);
		lblIntroduceDireccion.setText("Introduce direccion:");
		lblIntroduceDireccion.setBounds(10, 68, 118, 15);

		Label lblIntroduceInstrumento = new Label(grpCrearMusico, SWT.NONE);
		lblIntroduceInstrumento.setText("Introduce instrumento:");
		lblIntroduceInstrumento.setBounds(10, 116, 140, 15);

		text_5 = new Text(grpCrearMusico, SWT.BORDER);
		text_5.setBounds(10, 137, 202, 21);

		Button button_1 = new Button(grpCrearMusico, SWT.NONE);
		button_1.setText("Crear musico");
		button_1.setBounds(10, 280, 202, 25);

		text_6 = new Text(grpCrearMusico, SWT.BORDER);
		text_6.setBounds(10, 89, 202, 21);

		Group grpAsignarMusicoA = new Group(composite_4, SWT.NONE);
		grpAsignarMusicoA.setText("Asignar musico a grupo");
		grpAsignarMusicoA.setBounds(228, 0, 212, 315);

		Label label_5 = new Label(grpAsignarMusicoA, SWT.NONE);
		label_5.setText("Selecciona el grupo:");
		label_5.setBounds(10, 20, 116, 15);

		final Combo combo_3 = new Combo(grpAsignarMusicoA, SWT.READ_ONLY);
		combo_3.setBounds(10, 41, 192, 23);

		Label lblSeleccionaElMusico = new Label(grpAsignarMusicoA, SWT.NONE);
		lblSeleccionaElMusico.setText("Selecciona el musico:");
		lblSeleccionaElMusico.setBounds(10, 68, 116, 15);

		final Combo combo_4 = new Combo(grpAsignarMusicoA, SWT.READ_ONLY);
		combo_4.setBounds(10, 89, 192, 23);

		Button button_3 = new Button(grpAsignarMusicoA, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String grupoNombre = combo_3.getText();
				String musicoNombre = combo_4.getText();
				try {
					musicoGrupoService.asignarMusicoGrupo(
							musicoGrupoService.obtenerMusicoPorNombre(musicoNombre),
							eventoService.obtenerGrupoPorNombre(grupoNombre));
				} catch (InputValidationException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();				
				} catch (InstanceNotFoundException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage("El musico o el grupo seleccionado no existe");
					messageBox.open();
				} catch (MusicoAsignadoException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				}				
			}
		});
		button_3.setText("Asignar musico a grupo");
		button_3.setBounds(10, 280, 192, 25);

		Group grpBorrarMusico = new Group(composite_4, SWT.NONE);
		grpBorrarMusico.setText("Borrar musico");
		grpBorrarMusico.setBounds(446, 0, 219, 146);

		Group grpDesasignarMusicoA = new Group(composite_4, SWT.NONE);
		grpDesasignarMusicoA.setText("Desasignar musico a evento");
		grpDesasignarMusicoA.setBounds(446, 152, 219, 163);

		TabItem tbtmTransporte = new TabItem(tabFolder, 0);
		tbtmTransporte.setText("Transporte");

		Composite composite_5 = new Composite(tabFolder, SWT.NONE);
		tbtmTransporte.setControl(composite_5);

		Group grpCrearTransporte = new Group(composite_5, SWT.NONE);
		grpCrearTransporte.setText("Crear transporte");
		grpCrearTransporte.setBounds(0, 0, 222, 315);

		Label label_4 = new Label(grpCrearTransporte, SWT.NONE);
		label_4.setText("Introduce nombre:");
		label_4.setBounds(10, 20, 99, 15);

		text_7 = new Text(grpCrearTransporte, SWT.BORDER);
		text_7.setBounds(10, 41, 202, 21);

		Label lblIntroduceMatricula = new Label(grpCrearTransporte, SWT.NONE);
		lblIntroduceMatricula.setText("Introduce matricula:");
		lblIntroduceMatricula.setBounds(10, 68, 118, 15);

		Label lblIntroduceDescripcion = new Label(grpCrearTransporte, SWT.NONE);
		lblIntroduceDescripcion.setText("Introduce descripcion:");
		lblIntroduceDescripcion.setBounds(10, 116, 140, 15);

		text_8 = new Text(grpCrearTransporte, SWT.BORDER);
		text_8.setBounds(10, 137, 202, 21);

		Button button_2 = new Button(grpCrearTransporte, SWT.NONE);
		button_2.setText("Crear evento");
		button_2.setBounds(10, 280, 202, 25);

		text_9 = new Text(grpCrearTransporte, SWT.BORDER);
		text_9.setBounds(10, 89, 202, 21);

		Group grpAsignarTransporteA = new Group(composite_5, SWT.NONE);
		grpAsignarTransporteA.setText("Asignar transporte a grupo");
		grpAsignarTransporteA.setBounds(228, 0, 212, 315);

		Label label_6 = new Label(grpAsignarTransporteA, SWT.NONE);
		label_6.setText("Selecciona el grupo:");
		label_6.setBounds(10, 20, 116, 15);

		Combo combo_5 = new Combo(grpAsignarTransporteA, SWT.READ_ONLY);
		combo_5.setBounds(10, 41, 192, 21);

		Label lblSeleccionaElTransporte = new Label(grpAsignarTransporteA,
				SWT.NONE);
		lblSeleccionaElTransporte.setText("Selecciona el transporte:");
		lblSeleccionaElTransporte.setBounds(10, 68, 142, 15);

		Combo combo_6 = new Combo(grpAsignarTransporteA, SWT.READ_ONLY);
		combo_6.setBounds(10, 89, 192, 23);

		Label label_9 = new Label(grpAsignarTransporteA, SWT.NONE);
		label_9.setText("Introduce fecha inicio:");
		label_9.setBounds(10, 119, 118, 15);

		DateTime dateTime_3 = new DateTime(grpAsignarTransporteA, SWT.BORDER);
		dateTime_3.setBounds(10, 140, 80, 24);

		Button button_4 = new Button(grpAsignarTransporteA, SWT.NONE);
		button_4.setText("Asignar evento a grupo");
		button_4.setBounds(10, 280, 192, 25);

		Label lblIntroduceFechaFin = new Label(grpAsignarTransporteA, SWT.NONE);
		lblIntroduceFechaFin.setText("Introduce fecha fin:");
		lblIntroduceFechaFin.setBounds(10, 170, 118, 15);

		DateTime dateTime_4 = new DateTime(grpAsignarTransporteA, SWT.BORDER);
		dateTime_4.setBounds(10, 191, 80, 24);

		Group grpBorrarTransporte = new Group(composite_5, SWT.NONE);
		grpBorrarTransporte.setText("Borrar transporte");
		grpBorrarTransporte.setBounds(446, 0, 219, 146);

		Group grpDesasignarTransporteA = new Group(composite_5, SWT.NONE);
		grpDesasignarTransporteA.setText("Desasignar transporte a evento");
		grpDesasignarTransporteA.setBounds(446, 152, 219, 163);

		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nombre = text_4.getText();
				String direccion = text_6.getText();
				String instrumento = text_5.getText();
				try {
					musicoGrupoService.crearMusico(nombre, direccion,
							instrumento);
				} catch (InputValidationException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				} catch (MusicoExisteException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage(e1.getMessage());
					messageBox.open();
				}
				combo_4.add(nombre);
			}
		});

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
		pinta(now, display);

		getDate();

		List<Grupo> grupos = musicoGrupoService.getGrupos();
		for (Grupo grupo : grupos) {
			combo.add(grupo.getNombreOrquesta());
		}

		/*
		 * //TODO: For que recorra los componentes de un grupo y LLamada al
		 * servicio cuando este
		 * 
		 * tree3 = new Tree(group0, SWT.BORDER); gridData = new
		 * GridData(GridData.FILL_HORIZONTAL); gridData.widthHint = 20;
		 * gridData.heightHint = 200; tree3.setLayoutData(gridData);
		 * 
		 * Combo c2 = new Combo(group0, SWT.DEFAULT); gridData = new
		 * GridData(GridData.FILL_HORIZONTAL); gridData.widthHint = 20;
		 * gridData.verticalAlignment = SWT.TOP; c2.setLayoutData(gridData);
		 * 
		 * List<Musico> musicos = musicoGrupoService.getMusicos(); for (Musico
		 * musico : musicos) { c2.add(musico.getNombreMusico()); }
		 */

		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(
						tbtmConsultas)) {
					combo_7.removeAll();
					List<Evento> eventosCombo7 = eventoService.findAllEvents();
					for (Evento evento : eventosCombo7) {
						combo_7.add(evento.getNombreEvento());
					}

					combo_8.removeAll();
					List<Grupo> gruposCombo8 = eventoService.obtenerGrupos();
					for (Grupo grupo : gruposCombo8) {
						combo_8.add(grupo.getNombreOrquesta());
					}

					combo_9.removeAll();
					List<Musico> musicosCombo9 = musicoGrupoService
							.getMusicos();
					for (Musico musico : musicosCombo9) {
						combo_9.add(musico.getNombreMusico());
					}
				} else if (tabFolder.getItem(tabFolder.getSelectionIndex())
						.equals(tbtmNewItem)) {
					combo_1.removeAll();
					List<Grupo> gruposCombo1 = eventoService.obtenerGrupos();
					for (Grupo grupo : gruposCombo1) {
						combo_1.add(grupo.getNombreOrquesta());
					}

					combo_2.removeAll();
					List<Evento> eventosCombo2 = eventoService.findAllEvents();
					for (Evento evento : eventosCombo2) {
						combo_2.add(evento.getNombreEvento());
					}

				} else if (tabFolder.getItem(tabFolder.getSelectionIndex())
						.equals(tbtmMusico_1)) {
					combo_3.removeAll();
					List<Grupo> gruposCombo3 = eventoService.obtenerGrupos();
					for (Grupo grupo : gruposCombo3) {
						combo_3.add(grupo.getNombreOrquesta());
					}

					combo_4.removeAll();
					List<Musico> musicosCombo4 = musicoGrupoService
							.getMusicos();
					for (Musico musico : musicosCombo4) {
						combo_4.add(musico.getNombreMusico());
					}

				} else if (tabFolder.getItem(tabFolder.getSelectionIndex())
						.equals(tbtmCalendario)) {
					pinta(Calendar.getInstance(), display);
				}
			}
		});

		shell.open();
		// shell.layout();
		// shell.pack();
		// composite.pack();
		// group0.pack();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void getDate() {
		try {
			day = Integer.valueOf(this.selectedDate.substring(8, 10));
		} catch (Exception e) {
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
			fechaItem.setText("Fecha: "
					+ ConvertidorFechas.convertirCalendarString(events.get(i)
							.getFechaInicioEvento()));
			TreeItem gruposItem = new TreeItem(treeItem, 0);
			gruposItem.setText("Grupos: ");
			List<Grupo> grupos = eventoService.obtenerGruposEvento(events
					.get(i));
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
		pinta(now, display);
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

	public void pinta(Calendar now, Display display) {
		now.add(Calendar.DAY_OF_MONTH, -(now.get(Calendar.DATE) - 1)); //
		int startIndex = now.get(Calendar.DAY_OF_WEEK) - 1; //
		int year = now.get(Calendar.YEAR); //
		int month = now.get(Calendar.MONTH) + 1; //
		int lastDay = this.getLastDayOfMonth(year, month); //
		int endIndex = startIndex + lastDay - 1; //
		int startday = 1;
		List<Grupo> grupos = musicoGrupoService.getGrupos();
		for (int i = 0; i < 42; i++) {
			if (i >= startIndex && i <= endIndex) {
				days[i].setText("" + startday);

				// if (startday == currentDay) {
				days[i].setBackground(display.getSystemColor(SWT.COLOR_BLUE)); //
				// }

				Calendar fecha = Calendar.getInstance();
				fecha.set(Calendar.YEAR, year);
				fecha.set(Calendar.MONTH, month - 1);
				fecha.set(Calendar.DATE, startday);
				List<Evento> eventos = new ArrayList<>();
				if (combo.getSelectionIndex() == 0)
					eventos = eventoService.obtenerEventosFecha(fecha);
				else {
					Calendar fechaFin = (Calendar) fecha.clone();
					fechaFin.add(Calendar.DATE, 1);
					for (Grupo grupo : grupos) {
						if (grupo.getNombreOrquesta().equals(combo.getText())) {
							try {
								eventos = eventoService
										.obtenerEventosDeGrupoDia(grupo, fecha);
							} catch (InputValidationException e) {
								eventos = eventoService
										.obtenerEventosFecha(fecha);
							}
						}
					}
					// eventos = eventoService.filtrarEventosGrupo(grupo,
					// fechaInicio, fechaFin)
				}
				if (eventos.size() == 0) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_GREEN));
				}
				if (eventos.size() == 1) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_BLUE));
				}
				if (eventos.size() == 2) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_DARK_RED));
				}
				if (eventos.size() == 3) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_MAGENTA));
				}
				if (eventos.size() >= 4) {
					days[i].setBackground(display
							.getSystemColor(SWT.COLOR_CYAN));
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
			dateTime.setDate(year, month - 1, this.day);
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
