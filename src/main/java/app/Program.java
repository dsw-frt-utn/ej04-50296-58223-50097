package app;

import data.Persistencia;
import java.util.InvalidPropertiesFormatException;
import views.IngresarVehiculosView;
import views.MenuPrincipalView;

public class Program {
    public static void main(String[] args) throws IllegalArgumentException, InvalidPropertiesFormatException {
        Persistencia.inicializar();
        new MenuPrincipalView().setVisible(true);
    }
}
