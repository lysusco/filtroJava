package com.campusland.respository;

import java.util.List;

import com.campusland.exceptiones.facturaexceptions.FacturaExceptionInsertDataBase;
import com.campusland.respository.models.Factura;

public interface RepositoryFactura {

    List<Factura> listar();

    List<Factura> listarVentasTotales();

    void crear(Factura factura)throws FacturaExceptionInsertDataBase;
    
}
