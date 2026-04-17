package data;

import domain.*;
import java.util.ArrayList;
import java.util.Optional;

public class Persistencia {
    private static ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private static ArrayList<Responsable> responsables = new ArrayList<>();
    private static ArrayList<Sucursal> sucursales = new ArrayList<>();
    private static ArrayList<Marca> marcas = new ArrayList<>();
    
    private static void inicializarResponsables(){
        Responsable r1 = new Responsable("Carlos Gómez", "25444111", "3815551111");
        Responsable r2 = new Responsable("Laura Pérez", "30111222", "3815552222");
        responsables.add(r1);
        responsables.add(r2);
    }
    
    private static void inicializarSucursales(){
        Sucursal s1 = new Sucursal("SUC01", "Av. Belgrano 1200", "Tucumán", responsables.get(0));
        Sucursal s2 = new Sucursal("SUC02", "San Martín 450", "Yerba Buena", responsables.get(1));
        
        sucursales.add(s1);
        sucursales.add(s2);
    }
    
    private static void inicializarMarcas(){
        // Unificamos las marcas de ambas ramas para no perder datos
        Marca m1 = new Marca("Ford", "Estados Unidos");
        Marca m2 = new Marca("Toyota", "Japón");
        Marca m3 = new Marca("Volkswagen", "Alemania");
        Marca m4 = new Marca("Renault", "Francia");
        Marca m5 = new Marca("Iveco", "Italia");
        Marca m6 = new Marca("Mercedes", "Alemania");
        
        marcas.add(m1);
        marcas.add(m2);
        marcas.add(m3);
        marcas.add(m4);
        marcas.add(m5);
        marcas.add(m6);
    }
    
    private static void inicializarVehiculos(){
        // Prevalece la lógica de la rama feature/alta-vehiculo
        // No inicializar vehículos desde código, permitir ingreso por UI
    }
    
    public static ArrayList<Vehiculo> getVehiculos(){
        return vehiculos;
    }
    
    public static Optional<Vehiculo> getVehiculo(String patente){
        return vehiculos.stream()
                .filter(v -> v.getPatente().equals(patente))
                .findFirst();
    }
    
    public static ArrayList<Sucursal> getSucursales(){
        return sucursales;
    }
    
    public static ArrayList<Marca> getMarcas(){
        return marcas;
    }
    
    public static void agregarVehiculoCombustible(String patente, Marca marca, String modelo, int anio, 
            double capacidadCarga, Sucursal sucursal, double kmPorLitro, double litrosExtra){
        VehiculoCombustible vehiculo = new VehiculoCombustible(patente, marca, modelo, anio, capacidadCarga, 
            sucursal, kmPorLitro, litrosExtra);
        vehiculos.add(vehiculo);
    }
    
    public static void agregarVehiculoElectrico(String patente, Marca marca, String modelo, int anio, 
            double capacidadCarga, Sucursal sucursal, double kwhBase){
        VehiculoElectrico vehiculo = new VehiculoElectrico(patente, marca, modelo, anio, capacidadCarga, 
            sucursal, kwhBase);
        vehiculos.add(vehiculo);
    }
    
    public static void inicializar(){
        inicializarResponsables();
        inicializarSucursales();
        inicializarMarcas();
        inicializarVehiculos();
    }
}