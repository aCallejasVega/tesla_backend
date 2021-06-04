package bo.com.tesla.recaudaciones.dto;

public enum CampoBusquedaClienteEnum {
    CODIGO_CLIENTE("CÓDIGO CLIENTE"),
    NRO_DOCUMENTO("NRO DOCUMENTO"),
    NOMBRE_CLIENTE("NOMBRE CLIENTE"),
    //NIT("NIT"),
    TELEFONO("TELÉFONO");

    public final String alias;

    CampoBusquedaClienteEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

}
