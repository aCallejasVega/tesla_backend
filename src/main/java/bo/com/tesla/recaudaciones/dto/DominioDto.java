package bo.com.tesla.recaudaciones.dto;

public class DominioDto {

    private Long dominioId;
    private String dominio;
    private String descripcion;
    private String abreviatura;

    public DominioDto() {
    }

    public DominioDto(Long dominioId, String dominio, String descripcion, String abreviatura) {
        this.dominioId = dominioId;
        this.dominio = dominio;
        this.descripcion = descripcion;
        this.abreviatura = abreviatura;
    }

    public Long getDominioId() {
        return dominioId;
    }

    public void setDominioId(Long dominioId) {
        this.dominioId = dominioId;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}

