package es.udc.med.espectaculos.vista;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.eventoservice.EventoService;
import es.udc.med.espectaculos.model.eventoservice.EventoServiceImpl;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class VentanaPrincipalV2 {

	private Shell shell;
	private DateTime dateTime;
	private int day, month, year;
	private String strDate;
	private Tree tree, tree2,tree3;
	private TableTree tableTree;
	private String eventName;
	private EventoService eventoService;
	
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
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(449, 426);
		shell.setText("Aplicacion espectaculos");

		dateTime = new DateTime(shell, SWT.CALENDAR);
		dateTime.setBounds(0, 0, 190, 143);

		getDate();

		tree = new Tree(shell, SWT.BORDER);
		tree.setBounds(0, 149, 190, 238);

//		for (int i = 0; i < 12; i++) {
//			treeItem = new TreeItem(tree, SWT.NONE);
//			treeItem.setText("Item " + i);
//		}
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
		tree2.setBounds(196, 149, 238, 238);
		
		Combo c1 = new Combo(shell, SWT.DEFAULT);
	    c1.setBounds(195, 0, 240, 105);
	    
	    
	    String[] grupos = new String[100];
	    		
	    		List<Grupo> gruposList = eventoService.getGrupos();
//	    for (int i = 0; i<gruposList.size() ; i++) {
//	    	c1.setItem(i, gruposList.get(i).getNombreOrquesta());
//	    }
//	    	c1.setItem(0,"Combo Dominicano");
	    	
	    		String items[] = { "Combo Dominicano" };
	        c1.setItems(items);
		

	}

	private void getDate() {
		dateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				day = dateTime.getDay();
				month = dateTime.getMonth();
				year = dateTime.getYear();
				strDate = Integer.toString(year) + "/";
				strDate += (month < 10) ? "0" + month + "/" : month + "/";
				strDate += (day < 10) ? "0" + day : day; 
				
				System.out.println(strDate);
				Calendar fecha = Calendar.getInstance();
				fecha.set(Calendar.YEAR, year);
				fecha.set(Calendar.MONTH, month);
				fecha.set(Calendar.DATE, day);
				List<Evento> eventos = eventoService.obtenerEventosFecha(fecha);				
				setEvents(eventos);
			}
		});
	}

	private void setEvents(List<Evento> events) {
		tree.removeAll();
		tree2.removeAll();
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
			TreeItem treeItem,treeItem2,treeItem3,treeItem4;
			//Todos los metodos get implementados
			//MAnana corregire las variables globales al principio y demas
			treeItem = new TreeItem(tree2, SWT.NONE);
			treeItem2 = new TreeItem(tree2, SWT.NONE);
			treeItem3 = new TreeItem(tree2, SWT.NONE);
			treeItem.setText("Nombre del evento: " + event.getNombreEvento());
			treeItem2.setText("Fecha de inicio: " + ConvertidorFechas.convertirCalendarString(event.getFechaInicioEvento()));
			treeItem3.setText("Localidad: "+ event.getLocalidad());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
