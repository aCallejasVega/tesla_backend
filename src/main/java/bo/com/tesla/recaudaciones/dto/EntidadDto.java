package bo.com.tesla.recaudaciones.dto;

public class EntidadDto {

    private Long entidadId;
    private String nombre;
    private String nombreComercial;
    private String pathLogo;

    public EntidadDto() {
    }

    public EntidadDto(Long entidadId, String nombre, String nombreComercial, String pathLogo) {
        this.entidadId = entidadId;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.pathLogo = pathLogo;
    }


    public Long getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Long entidadId) {
        this.entidadId = entidadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getPathLogo() {
        return pathLogo;
    }

    public void setPathLogo(String pathLogo) {
        this.pathLogo = pathLogo;
    }
}

