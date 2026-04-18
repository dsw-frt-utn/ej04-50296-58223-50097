package views;

import domain.Marca;
import domain.Sucursal;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class IngresarVehiculosView extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private ArrayList<Sucursal> sucursales;
    private ArrayList<Marca> marcas;

    public IngresarVehiculosView() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(600, 650));
        setPreferredSize(new Dimension(800, 750));
        cargarMarcas();
        cargarSucursales();
        configurarFormulario();
        pack();
    }

    private void cargarMarcas() {
        marcas = Controlador.getMarcas();
        String[] marcasArray = new String[marcas.size()];
        for (int i = 0; i < marcas.size(); i++) {
            marcasArray[i] = marcas.get(i).toString();
        }
        jCBMarca.setModel(new DefaultComboBoxModel<>(marcasArray));
    }

    private void cargarSucursales() {
        sucursales = Controlador.getSucursales();
        String[] sucursalesArray = new String[sucursales.size()];
        for (int i = 0; i < sucursales.size(); i++) {
            sucursalesArray[i] = sucursales.get(i).getCodigo() + " - " + sucursales.get(i).getCiudad();
        }
        jCBSucursal.setModel(new DefaultComboBoxModel<>(sucursalesArray));
    }

    private void configurarFormulario() {
        cardLayout = new CardLayout();
        panelCamposEspecificos.setLayout(cardLayout);

        // Panel para vehículo combustible
        javax.swing.JPanel panelCombustible = new javax.swing.JPanel();
        panelCombustible.setLayout(new javax.swing.BoxLayout(panelCombustible, javax.swing.BoxLayout.Y_AXIS));
        
        javax.swing.JLabel kmPorLitroLabel = new javax.swing.JLabel("Km por Litro:");
        jTKmPorLitro = new javax.swing.JTextField();
        jTKmPorLitro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        jTKmPorLitro.setPreferredSize(new Dimension(300, 30));
        
        javax.swing.JLabel litrosExtraLabel = new javax.swing.JLabel("Litros Extra:");
        jTLitrosExtra = new javax.swing.JTextField();
        jTLitrosExtra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        jTLitrosExtra.setPreferredSize(new Dimension(300, 30));
        
        panelCombustible.add(kmPorLitroLabel);
        panelCombustible.add(jTKmPorLitro);
        panelCombustible.add(javax.swing.Box.createVerticalStrut(10));
        panelCombustible.add(litrosExtraLabel);
        panelCombustible.add(jTLitrosExtra);
        panelCombustible.add(javax.swing.Box.createVerticalGlue());

        // Panel para vehículo eléctrico
        javax.swing.JPanel panelElectrico = new javax.swing.JPanel();
        panelElectrico.setLayout(new javax.swing.BoxLayout(panelElectrico, javax.swing.BoxLayout.Y_AXIS));
        
        javax.swing.JLabel kwhBaseLabel = new javax.swing.JLabel("kWh Base:");
        jTKwhBase = new javax.swing.JTextField();
        jTKwhBase.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        jTKwhBase.setPreferredSize(new Dimension(300, 30));
        
        panelElectrico.add(kwhBaseLabel);
        panelElectrico.add(jTKwhBase);
        panelElectrico.add(javax.swing.Box.createVerticalGlue());

        panelCamposEspecificos.add(panelCombustible, "COMBUSTIBLE");
        panelCamposEspecificos.add(panelElectrico, "ELECTRICO");
    }

    private void actualizarFormulario() {
        String tipoSeleccionado = jCBTipoVehiculo.getSelectedItem().toString();
        if ("Combustible".equals(tipoSeleccionado)) {
            cardLayout.show(panelCamposEspecificos, "COMBUSTIBLE");
        } else {
            cardLayout.show(panelCamposEspecificos, "ELECTRICO");
        }
    }

    private void guardarVehiculo() {
        try {
            // Validar campos comunes
            String patente = jTPatente.getText().trim();
            String modelo = jTModelo.getText().trim();
            String anioStr = jTAnio.getText().trim();
            String capacidadStr = jTCapacidaCarga.getText().trim();

            if (patente.isEmpty() || modelo.isEmpty() || anioStr.isEmpty() || capacidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos comunes deben estar completos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (jCBMarca.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una marca", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int anio = Integer.parseInt(anioStr);
            double capacidadCarga = Double.parseDouble(capacidadStr);
            
            if (anio < 1900 || anio > 2100) {
                JOptionPane.showMessageDialog(this, "El año debe ser válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (jCBSucursal.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una sucursal", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Marca marca = marcas.get(jCBMarca.getSelectedIndex());
            Sucursal sucursal = sucursales.get(jCBSucursal.getSelectedIndex());
            String tipoSeleccionado = jCBTipoVehiculo.getSelectedItem().toString();

            if ("Combustible".equals(tipoSeleccionado)) {
                String kmPorLitroStr = jTKmPorLitro.getText().trim();
                String litrosExtraStr = jTLitrosExtra.getText().trim();

                if (kmPorLitroStr.isEmpty() || litrosExtraStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar Km por Litro y Litros Extra", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double kmPorLitro = Double.parseDouble(kmPorLitroStr);
                double litrosExtra = Double.parseDouble(litrosExtraStr);

                Controlador.agregarVehiculoCombustible(patente, marca, modelo, anio, capacidadCarga, sucursal, kmPorLitro, litrosExtra);
            } else {
                String kwhBaseStr = jTKwhBase.getText().trim();

                if (kwhBaseStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar kWh Base", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double kwhBase = Double.parseDouble(kwhBaseStr);
                Controlador.agregarVehiculoElectrico(patente, marca, modelo, anio, capacidadCarga, sucursal, kwhBase);
            }

            JOptionPane.showMessageDialog(this, "Vehículo guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifica que los valores numéricos sean correctos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        jTPatente.setText("");
        jTModelo.setText("");
        jTAnio.setText("");
        jTCapacidaCarga.setText("");
        jTKmPorLitro.setText("");
        jTLitrosExtra.setText("");
        jTKwhBase.setText("");
        jCBTipoVehiculo.setSelectedIndex(0);
        jCBMarca.setSelectedIndex(0);
        jCBSucursal.setSelectedIndex(0);
    }

    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jTPatente = new javax.swing.JTextField();
        jLPatente = new javax.swing.JLabel();
        jCBTipoVehiculo = new javax.swing.JComboBox<>();
        jLTitulo = new javax.swing.JLabel();
        jLTipoVehiculo = new javax.swing.JLabel();
        jCBMarca = new javax.swing.JComboBox<>();
        jLMarca = new javax.swing.JLabel();
        jLNombreMarca = new javax.swing.JLabel();
        jLSucursal = new javax.swing.JLabel();
        jCBSucursal = new javax.swing.JComboBox<>();
        jLCapacidadCarga = new javax.swing.JLabel();
        jTCapacidaCarga = new javax.swing.JTextField();
        jLMadelo = new javax.swing.JLabel();
        jTModelo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTAnio = new javax.swing.JTextField();
        jBGuardar = new javax.swing.JButton();
        jBLimpiar = new javax.swing.JButton();
        jBListar = new javax.swing.JButton();
        panelCamposEspecificos = new javax.swing.JPanel();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logística - Ingresar Vehículos");

        jLPatente.setText("Patente:");

        jCBTipoVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Combustible", "Eléctrico"}));
        jCBTipoVehiculo.addActionListener(evt -> actualizarFormulario());

        jLTitulo.setText("Ingresar Vehículo");
        jLTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));

        jLTipoVehiculo.setText("Tipo");

        jLMarca.setText("Marca");

        jLNombreMarca.setText("Nombre");

        jLSucursal.setText("Sucursal");

        jLCapacidadCarga.setText("Capacidad Carga");

        jLMadelo.setText("Modelo");

        jTModelo.addActionListener(evt -> {});

        jLabel3.setText("Año");

        jBGuardar.setText("Guardar Vehículo");
        jBGuardar.addActionListener(evt -> guardarVehiculo());

        jBLimpiar.setText("Limpiar");
        jBLimpiar.addActionListener(evt -> limpiarFormulario());

        jBListar.setText("Volver");
        jBListar.addActionListener(evt -> {
            MenuPrincipalView menuView = new MenuPrincipalView();
            menuView.setVisible(true);
            this.setVisible(false);
        });

        // Layout responsivo
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLTitulo)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLPatente)
                    .addComponent(jLTipoVehiculo)
                    .addComponent(jLMarca)
                    .addComponent(jLMadelo)
                    .addComponent(jLabel3)
                    .addComponent(jLCapacidadCarga)
                    .addComponent(jLSucursal))
                .addGap(10, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTPatente)
                    .addComponent(jCBTipoVehiculo)
                    .addComponent(jCBMarca)
                    .addComponent(jTModelo)
                    .addComponent(jTAnio)
                    .addComponent(jTCapacidaCarga)
                    .addComponent(jCBSucursal)
                    .addComponent(panelCamposEspecificos, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
            .addComponent(panelCamposEspecificos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jBGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jBLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jBListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLTitulo)
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLPatente)
                    .addComponent(jTPatente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLTipoVehiculo)
                    .addComponent(jCBTipoVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLMarca)
                    .addComponent(jCBMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLMadelo)
                    .addComponent(jTModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLCapacidadCarga)
                    .addComponent(jTCapacidaCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLSucursal)
                    .addComponent(jCBSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addComponent(panelCamposEspecificos, 80, 100, 150)
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBGuardar)
                    .addComponent(jBLimpiar)
                    .addComponent(jBListar))
                .addContainerGap())
        );

        pack();
    }

    // Variables declaration
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBListar;
    private javax.swing.JComboBox<String> jCBSucursal;
    private javax.swing.JComboBox<String> jCBTipoVehiculo;
    private javax.swing.JComboBox<String> jCBMarca;
    private javax.swing.JLabel jLCapacidadCarga;
    private javax.swing.JLabel jLMadelo;
    private javax.swing.JLabel jLMarca;
    private javax.swing.JLabel jLNombreMarca;
    private javax.swing.JLabel jLPatente;
    private javax.swing.JLabel jLSucursal;
    private javax.swing.JLabel jLTipoVehiculo;
    private javax.swing.JLabel jLTitulo;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTAnio;
    private javax.swing.JTextField jTCapacidaCarga;
    private javax.swing.JTextField jTModelo;
    private javax.swing.JTextField jTPatente;
    private javax.swing.JTextField jTKmPorLitro;
    private javax.swing.JTextField jTLitrosExtra;
    private javax.swing.JTextField jTKwhBase;
    private javax.swing.JPanel panelCamposEspecificos;
    // End of variables declaration
}
