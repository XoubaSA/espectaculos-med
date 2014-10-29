package es.udc.med.espectaculos.utils;

import java.util.Date;

@SuppressWarnings("serial")
public class InstanceNotFoundException extends Exception {

    private Object instanceId;
    private String instanceType;
    private Date fecha_ini;
    private Date fecha_fin;
    private Date fecha;

    public InstanceNotFoundException(Object instanceId, String instanceType) {

        super("Instance not found (identifier = '" + instanceId + "' - type = '"
                + instanceType + "')");
        this.instanceId = instanceId;
        this.instanceType = instanceType;

    }
    
    public InstanceNotFoundException(Date fecha_ini, Date fecha_fin) {
    	super("There is no instances between " + fecha_ini.toString() +
    			" and " + fecha_fin.toString() + ".");
    	this.fecha_ini = fecha_ini;
    	this.fecha_fin = fecha_fin;
    }
    
    public InstanceNotFoundException(Date fecha) {
    	
    	super("There is no instances in " + fecha.toString() + ".");
    	this.fecha = fecha;
    	
    }

    public Object getInstanceId() {
        return instanceId;
    }

    public String getInstanceType() {
        return instanceType;
    }

	public Date getFecha_ini() {
		return fecha_ini;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}
	
	public Date getFecha() {
		return fecha;
	} 
    
}
