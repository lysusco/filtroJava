package com.campusland.respository.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.campusland.utils.Formato;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Factura {

    private int numeroFactura;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")    
    private LocalDateTime fecha;
    private Cliente cliente;
    private Impuesto impuesto;
    private List<ItemFactura> items;
    private static int nextNumeroFactura;

    public Factura(){

    }

  

    public Factura(int numeroFactura, LocalDateTime fecha, Cliente cliente) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public Factura(LocalDateTime fecha, Cliente cliente) {
        this.numeroFactura = ++nextNumeroFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }


    public double valorDescuentoIva(){
        double valorIva = 0.19;
        return (valorIva * getTotalFactura()); 
    }

    public double valorDescuentoDiez(){
        double valorDiez = 0;
        if(getTotalFactura() >= 1000){
             valorDiez = 0.1;
        }
        return valorDiez * getTotalFactura();
    }

    public double descuentoClienteGold(){
        double gold = 0.15;
        return gold*getTotalFactura();
    }

    public double descuentoNavideño(){
        double navideño = 0.05;
        return navideño*getTotalFactura();
    }

    public double descuentoTres(){
        double tresdes = 0.03;
        return getTotalFactura()*tresdes;
    }

    public double getTotalFactura() {
        double totalFactura = 0;
        for (ItemFactura item : items) {
            totalFactura += item.getImporte();
        }
        return totalFactura;
    }

    public double getTotalDescuento(){
        return (descuentoTres()+descuentoNavideño()+descuentoClienteGold()+valorDescuentoDiez());
    }


    public double getTotalConDescuento(){
        return getTotalFactura()+valorDescuentoIva()-getTotalDescuento();
    }
    public void agregarItem(ItemFactura item){
        this.items.add(item);
    }

    public void display() {
        System.out.println();
        System.out.println("Factura: " + this.numeroFactura);
        System.out.println("Cliente: " + this.cliente.getFullName());
        System.out.println("Fecha: " + Formato.formatoFechaHora(this.getFecha()));
        System.out.println("-----------Detalle Factura----------------------");
        for (ItemFactura item : this.items) {
            System.out.println(item.getProducto().getCodigoNombre() + " " + item.getCantidad() + " "
                    + Formato.formatoMonedaPesos(item.getImporte()));

        }
        System.out.println();
        System.out.println("Total                      " + Formato.formatoMonedaPesos(this.getTotalFactura()));
        System.out.println("IVA                        " + Formato.formatoMonedaPesos(this.valorDescuentoIva()));
        System.out.println("Descuento 10%              " + Formato.formatoMonedaPesos(this.valorDescuentoDiez()));
        System.out.println("Descuento 15%              " + Formato.formatoMonedaPesos(this.descuentoClienteGold()));
        System.out.println("Descuento navideño 5%      " + Formato.formatoMonedaPesos(this.descuentoNavideño()));
        System.out.println("Descuento 3%               " + Formato.formatoMonedaPesos(this.descuentoTres()));
        System.out.println("Total con IVA              " + Formato.formatoMonedaPesos(this.getTotalFactura()+this.valorDescuentoIva()));
        System.out.println("Total descuento            " + Formato.formatoMonedaPesos(this.getTotalDescuento()));
        System.out.println("Total a pagar              " + Formato.formatoMonedaPesos(this.getTotalConDescuento()));

        System.out.println();
    }

}
