package app;

import data.Persistencia;
import java.util.InvalidPropertiesFormatException;
import views.IngresarVehiculosView;

public class Program {
    public static void main(String[] args) throws IllegalArgumentException, InvalidPropertiesFormatException {
        Persistencia.inicializar();
        IngresarVehiculosView view = new IngresarVehiculosView();
        view.setVisible(true);
    }
}
