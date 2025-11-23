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
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());
    private enum Modo {
    ADMINISTRADOR,
    USUARIO
}

    private Modo modoActual = Modo.ADMINISTRADOR;

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents(); 
        this.sistema = new SistemaDeArchivos(100);        
        actualizarTodasLasVistas();
        menuCambiarModo.setText("Modo: Administrador"); // texto inicial

        menuCambiarModo.addActionListener(evt -> cambiarModo());
    }
    
    private void cambiarModo() {
    Object[] opciones = {"Administrador", "Usuario"};
    int seleccion = JOptionPane.showOptionDialog(this,
            "Seleccione el modo de uso:",
            "Cambiar Modo",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

    if (seleccion == 0) {
        modoActual = Modo.ADMINISTRADOR;
        menuCambiarModo.setText("Modo: Administrador");
        JOptionPane.showMessageDialog(this, "Modo Administrador activado. Todas las operaciones están permitidas.");
    } else if (seleccion == 1) {
        modoActual = Modo.USUARIO;
        menuCambiarModo.setText("Modo: Usuario");
        JOptionPane.showMessageDialog(this, "Modo Usuario activado. Solo lectura y operaciones sobre sus propios archivos.");
    }
    
    actualizarVistasSegunModo();
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
            
            DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(entrada);
            
            nodoPadre.add(nuevoNodo);
            
            if (entrada instanceof Directorio) {
                agregarNodosHijos(nuevoNodo, (Directorio) entrada);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbolArchivos = new javax.swing.JTree();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        panelDisco = new vista.PanelDiscoVisual();
        panelColaProcesos1 = new vista.PanelColaProcesos();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        menuCrearDirectorio = new javax.swing.JMenuItem();
        menuCrearArchivo = new javax.swing.JMenuItem();
        menuEliminar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuCambiarModo = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuCPU = new javax.swing.JMenuItem();

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
            .addGap(0, 608, Short.MAX_VALUE)
        );
        panelDiscoLayout.setVerticalGroup(
            panelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(panelDisco);

        jSplitPane1.setRightComponent(jSplitPane2);

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

        jMenu3.setText("Modo");

        menuCambiarModo.setText("jMenuItem1");
        menuCambiarModo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCambiarModoActionPerformed(evt);
            }
        });
        jMenu3.add(menuCambiarModo);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("CPU");

        menuCPU.setText("jMenuItem1");
        jMenu4.add(menuCPU);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelColaProcesos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelColaProcesos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCrearDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCrearDirectorioActionPerformed
        if (modoActual == Modo.USUARIO) {
        JOptionPane.showMessageDialog(this, "No tiene permisos para crear directorios en modo Usuario.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
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
        if (modoActual == Modo.USUARIO) {
        JOptionPane.showMessageDialog(this, "No tiene permisos para crear archivos en modo Usuario.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
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
            if (sistema.crearArchivo(nombre.trim(), tamano, dirPadre)) {
                actualizarTodasLasVistas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo crear el archivo (nombre duplicado o no hay espacio).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El tamaño debe ser un número válido.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_menuCrearArchivoActionPerformed
    
    private void actualizarVistasSegunModo() {
    boolean admin = modoActual == Modo.ADMINISTRADOR;

    menuCrearArchivo.setEnabled(admin);
    menuCrearDirectorio.setEnabled(admin);
    menuEliminar.setEnabled(admin);

    // Podrías cambiar colores o iconos del árbol para mostrar solo lectura
}
    
    private void menuEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarActionPerformed
        if (modoActual == Modo.USUARIO) {
        JOptionPane.showMessageDialog(this, "No tiene permisos para eliminar archivos o directorios en modo Usuario.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
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

    private void menuCambiarModoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCambiarModoActionPerformed
        cambiarModo();// TODO add your handling code here:
    }//GEN-LAST:event_menuCambiarModoActionPerformed

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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuItem menuCPU;
    private javax.swing.JMenuItem menuCambiarModo;
    private javax.swing.JMenuItem menuCrearArchivo;
    private javax.swing.JMenuItem menuCrearDirectorio;
    private javax.swing.JMenuItem menuEliminar;
    private vista.PanelColaProcesos panelColaProcesos1;
    private vista.PanelDiscoVisual panelDisco;
    private javax.swing.JTable tablaArchivos;
    // End of variables declaration//GEN-END:variables
}

