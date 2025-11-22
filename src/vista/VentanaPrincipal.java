/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author santi
 */
import modelo.Archivo;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import modelo.Directorio;
import modelo.EntradaSistemaArchivos;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import modelo.SistemaDeArchivos;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends javax.swing.JFrame {

    private final SistemaDeArchivos sistema; 
    private String usuarioActual; // nombre del usuario logueado

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());


    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
    initComponents(); 
    this.sistema = new SistemaDeArchivos(100);
    this.usuarioActual = "diego";

    // Eliminar listeners previos de NetBeans
    for (java.awt.event.ActionListener al : comboModoUsuario.getActionListeners()) {
        comboModoUsuario.removeActionListener(al);
    }

    // Configurar el combo
    comboModoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuario" }));
    comboModoUsuario.setSelectedItem("Usuario"); // inicial

    // Listener real
    comboModoUsuario.addActionListener(evt -> {
        String seleccionado = (String) comboModoUsuario.getSelectedItem();
        SistemaDeArchivos.ModoUsuario nuevoModo = seleccionado.equals("Administrador") ?
                SistemaDeArchivos.ModoUsuario.ADMINISTRADOR :
                SistemaDeArchivos.ModoUsuario.USUARIO;

        sistema.setModoActual(nuevoModo);
        actualizarTodasLasVistas();
        System.out.println("Modo cambiado a: " + nuevoModo); // para debug
    });

    // Inicializa el modo según el combo
    sistema.setModoActual(SistemaDeArchivos.ModoUsuario.USUARIO);

    actualizarTodasLasVistas();
}





    
    private void actualizarArbolDeArchivos() {
        Directorio raiz = sistema.getDirectorioRaiz();
                DefaultMutableTreeNode nodoRaiz = new DefaultMutableTreeNode(raiz);
        
        arbolArchivos.setCellRenderer(new RendererArbolArchivos());

        DefaultTreeModel modeloArbol = new DefaultTreeModel(nodoRaiz);
        
        agregarNodosHijos(nodoRaiz, raiz);

        arbolArchivos.setModel(modeloArbol);
    }
    
    private void agregarNodosHijos(DefaultMutableTreeNode nodoPadre, Directorio dirPadre) {
    for (int i = 0; i < dirPadre.getContenido().getTamano(); i++) {
        EntradaSistemaArchivos entrada = dirPadre.getContenido().get(i);

        // FILTRADO POR MODO
        if (sistema.getModoActual() == SistemaDeArchivos.ModoUsuario.USUARIO 
                && entrada instanceof Archivo) {
            Archivo archivo = (Archivo) entrada;
            if (!archivo.getPropietario().equals(usuarioActual) && !archivo.esPublico()) {
                continue; // saltar archivo
            }
        }

        DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(entrada);
        nodoPadre.add(nuevoNodo);

        if (entrada instanceof Directorio) {
            agregarNodosHijos(nuevoNodo, (Directorio) entrada);
        }
    }
}


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbolArchivos = new javax.swing.JTree();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        panelDisco = new vista.PanelDiscoVisual();
        comboModoUsuario = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        menuCrearDirectorio = new javax.swing.JMenuItem();
        menuCrearArchivo = new javax.swing.JMenuItem();
        menuEliminar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(arbolArchivos);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        tablaArchivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Tamano (Bloques)", "Bloque Inicial", "Directorio Padre"
            }
        ));
        jScrollPane3.setViewportView(tablaArchivos);

        jScrollPane2.setViewportView(jScrollPane3);

        jSplitPane2.setTopComponent(jScrollPane2);

        javax.swing.GroupLayout panelDiscoLayout = new javax.swing.GroupLayout(panelDisco);
        panelDisco.setLayout(panelDiscoLayout);
        panelDiscoLayout.setHorizontalGroup(
            panelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );
        panelDiscoLayout.setVerticalGroup(
            panelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(panelDisco);

        jSplitPane1.setRightComponent(jSplitPane2);

        comboModoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jMenu1.setText("Sistema");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Acciones");

        menuCrearDirectorio.setText("Crear Directorio");
        menuCrearDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCrearDirectorioActionPerformed(evt);
            }
        });
        jMenu2.add(menuCrearDirectorio);

        menuCrearArchivo.setText("Crear Archivo");
        menuCrearArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCrearArchivoActionPerformed(evt);
            }
        });
        jMenu2.add(menuCrearArchivo);

        menuEliminar.setText("Eliminar Selección");
        menuEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEliminarActionPerformed(evt);
            }
        });
        jMenu2.add(menuEliminar);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCrearDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCrearDirectorioActionPerformed
        System.out.println("Modo actual: " + sistema.getModoActual());
        Directorio dirPadre = obtenerDirectorioSeleccionado();
        if (dirPadre == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un directorio en el árbol donde crear el nuevo directorio.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo directorio:", "Crear Directorio", JOptionPane.PLAIN_MESSAGE);
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            if (sistema.crearDirectorio(nombre.trim(), dirPadre)) {
                actualizarTodasLasVistas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo crear el directorio (quizás el nombre ya existe).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }                                           
    }//GEN-LAST:event_menuCrearDirectorioActionPerformed

    private void menuCrearArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCrearArchivoActionPerformed
     
        
        
        Directorio dirPadre = obtenerDirectorioSeleccionado();
    if (dirPadre == null) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un directorio para crear el archivo.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo archivo:", "Crear Archivo", JOptionPane.PLAIN_MESSAGE);
    if (nombre == null || nombre.trim().isEmpty()) return;

    String tamanoStr = JOptionPane.showInputDialog(this, "Ingrese el tamaño en bloques:", "Crear Archivo", JOptionPane.PLAIN_MESSAGE);
    if (tamanoStr == null || tamanoStr.trim().isEmpty()) return;

    try {
        int tamano = Integer.parseInt(tamanoStr.trim());
        if (tamano <= 0) {
            JOptionPane.showMessageDialog(this, "El tamaño debe ser un número positivo.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Nuevo código: elegir si el archivo será público o privado ---
        Object[] opciones = {"Privado", "Público"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "Seleccione el tipo de archivo:",
                "Visibilidad del archivo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        
        boolean esPublico = (seleccion == 1); // 0 = Privado, 1 = Público
        // -------------------------------------------------------------------

        if (sistema.crearArchivo(nombre.trim(), tamano, dirPadre, usuarioActual, esPublico)) {
            actualizarTodasLasVistas();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo crear el archivo (nombre duplicado o no hay espacio).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El tamaño debe ser un número válido.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_menuCrearArchivoActionPerformed

    private void menuEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarActionPerformed
        TreePath rutaSeleccionada = arbolArchivos.getSelectionPath();
        if (rutaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo o directorio para eliminar.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) rutaSeleccionada.getLastPathComponent();
        EntradaSistemaArchivos entradaAEliminar = (EntradaSistemaArchivos) nodoSeleccionado.getUserObject();

        if (entradaAEliminar.getPadre() == null) {
            JOptionPane.showMessageDialog(this, "No se puede eliminar el directorio raíz.", "Operación no permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar '" + entradaAEliminar.getNombre() + "'?\nEsta acción no se puede deshacer.", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean exito;
        if (entradaAEliminar instanceof Archivo) {
            exito = sistema.eliminarArchivo(entradaAEliminar.getNombre(), entradaAEliminar.getPadre());
        } else {
            exito = sistema.eliminarDirectorio(entradaAEliminar.getNombre(), entradaAEliminar.getPadre());
        }

        if (exito) {
            actualizarTodasLasVistas();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al eliminar la selección.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_menuEliminarActionPerformed

        private Directorio obtenerDirectorioSeleccionado() {
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolArchivos.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            return sistema.getDirectorioRaiz();
        }

        Object objetoSeleccionado = nodoSeleccionado.getUserObject();
        
        if (objetoSeleccionado instanceof Directorio) {
            return (Directorio) objetoSeleccionado;
        } 
        else if (objetoSeleccionado instanceof Archivo) {
            return ((Archivo) objetoSeleccionado).getPadre();
        }
        
        return null; 
    }
       
    private void actualizarTodasLasVistas() {
        actualizarArbolDeArchivos();
        panelDisco.actualizarVista(sistema.getDisco());
        actualizarTablaDeArchivos();
    }
    
    private void actualizarTablaDeArchivos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
                modeloTabla.setRowCount(0);
                poblarTablaRecursivamente(sistema.getDirectorioRaiz(), modeloTabla);
    }
    
    /**
     * Método recursivo que recorre un directorio y sus subdirectorios
     * para encontrar todos los archivos y añadirlos a la tabla.
     */
    private void poblarTablaRecursivamente(Directorio directorio, DefaultTableModel modeloTabla) {
    for (int i = 0; i < directorio.getContenido().getTamano(); i++) {
        EntradaSistemaArchivos entrada = directorio.getContenido().get(i);
        if (entrada instanceof Archivo) {
            Archivo archivo = (Archivo) entrada;

            // FILTRADO POR MODO
            if (sistema.getModoActual() == SistemaDeArchivos.ModoUsuario.USUARIO) {
                // Solo mostrar si el archivo es del usuario actual o es público
                if (!archivo.getPropietario().equals(usuarioActual) && !archivo.esPublico()) {
                    continue; // saltar este archivo
                }
            }

            Object[] fila = new Object[4];
            fila[0] = archivo.getNombre();
            fila[1] = archivo.getTamanoEnBloques();
            fila[2] = archivo.getIdBloqueInicial();
            fila[3] = archivo.getPadre().getNombre(); 
            modeloTabla.addRow(fila);

        } else if (entrada instanceof Directorio) {
            poblarTablaRecursivamente((Directorio) entrada, modeloTabla);
        }
    }
}


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arbolArchivos;
    private javax.swing.JComboBox<String> comboModoUsuario;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuItem menuCrearArchivo;
    private javax.swing.JMenuItem menuCrearDirectorio;
    private javax.swing.JMenuItem menuEliminar;
    private vista.PanelDiscoVisual panelDisco;
    private javax.swing.JTable tablaArchivos;
    // End of variables declaration//GEN-END:variables
}

