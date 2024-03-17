package Interfaces;

import java.util.List;
import Material.Material;
import Servicio.Servicio;

public interface ComunicaDatos {
    void enviaDatosServicios(List<Servicio> servicios);
    void enviaDatosMaterial(List<Material> material);
    void enviaDatosVistoBueno(List<String> vistoBueno);
    void enviaConfirmacionDatosGuardados(boolean isDataSaved);
}
