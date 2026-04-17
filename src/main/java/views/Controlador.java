package views;

import data.Persistencia;
import domain.Marca;
import domain.Sucursal;
import domain.Vehiculo;
import domain.VehiculoTipo;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Controlador {
    
    public static ArrayList<VehiculoViewModel> getVehiculos(){
        ArrayList<VehiculoViewModel> vehiculos = new ArrayList<>();
        for(Vehiculo vehiculo : Persistencia.getVehiculos()) {
            vehiculos.add(new VehiculoViewModel(vehiculo));
        }
        return vehiculos;
    }
    
    public static ArrayList<Sucursal> getSucursales(){
        return Persistencia.getSucursales();
    }
    
    public static ArrayList<Marca> getMarcas(){
        return Persistencia.getMarcas();
    }
    
    public static void agregarVehiculoCombustible(String patente, Marca marca, String modelo, int anio, 
            double capacidadCarga, Sucursal sucursal, double kmPorLitro, double litrosExtra){
        Persistencia.agregarVehiculoCombustible(patente, marca, modelo, anio, capacidadCarga, sucursal, kmPorLitro, litrosExtra);
    }
    
    public static void agregarVehiculoElectrico(String patente, Marca marca, String modelo, int anio, 
            double capacidadCarga, Sucursal sucursal, double kwhBase){
        Persistencia.agregarVehiculoElectrico(patente, marca, modelo, anio, capacidadCarga, sucursal, kwhBase);
    }
    
    public static double[] calcularConsumos(Map<String, Double> vehiculos){
        double consumoElectricos = 0;
        double consumoCombustible= 0;
        for(Map.Entry<String, Double> entry : vehiculos.entrySet()){
           double consumo = 0;
           Optional<Vehiculo> vehiculo = Persistencia.getVehiculo(entry.getKey());
           if(vehiculo.isPresent()){
               consumo = vehiculo.get().calcularConsumo(entry.getValue());
               consumoElectricos += vehiculo.get().esDe(VehiculoTipo.ELECTRICO) ? consumo : 0;
               consumoCombustible += vehiculo.get().esDe(VehiculoTipo.COMBUSTIBLE) ? consumo : 0;
           }
        }
        return new double[] {consumoElectricos, consumoCombustible};
    }
}
