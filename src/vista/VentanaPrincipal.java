/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author santi
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import planificacion.GestorDeProcesos;
import planificacion.Proceso;
import planificacion.SolicitudES;
import planificacion.TipoSolicitud;
import estructuras.ListaEnlazada;
import planificacion.PoliticaFIFO;
import planificacion.PoliticaSSTF;
import planificacion.PoliticaSCAN;
import planificacion.PoliticaCSCAN; 
import javax.swing.JFileChooser;
import modelo.Archivo;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import modelo.Directorio;
import modelo.EntradaSistemaArchivos;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import modelo.SistemaDeArchivos;
import javax.swing.table.DefaultTableModel;
import modelo.GestorPersistencia;

public class VentanaPrincipal extends javax.swing.JFrame {

    private SistemaDeArchivos sistema; 
    private GestorDeProcesos gestorProcesos;
    private Timer planificadorTimer;

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

    // 1️⃣ Inicializar tu SistemaDeArchivos (compatible con tu persistencia)
    this.sistema = new SistemaDeArchivos(100);

    // 2️⃣ Inicializar el gestor de procesos del otro proyecto
    this.gestorProcesos = new GestorDeProcesos(this.sistema);

    // 3️⃣ Configurar Timer para la planificación de procesos automática
    this.planificadorTimer = new Timer(2000, e -> {
        if (gestorProcesos != null) {
            gestorProcesos.procesarSiguienteSolicitud();
        }
        actualizarTodasLasVistas(); // actualizar UI después de cada ciclo
    });
    planificadorTimer.start();

    // 4️⃣ Actualizar todas las vistas al inicio
    actualizarTodasLasVistas();

    // 5️⃣ Configurar menú de cambio de modo
    menuCambiarModo.setText("Modo: Administrador");
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProcesos = new javax.swing.JTable();
        panelDisco = new vista.PanelDiscoVisual();
        jLabel1 = new javax.swing.JLabel();
        selectorPolitica = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSistema = new javax.swing.JMenu();
        menuNuevoSistema = new javax.swing.JMenuItem();
        menuGuardarSistema = new javax.swing.JMenuItem();
        menuCargarSistema = new javax.swing.JMenuItem();
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 606, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Asignación de archivos", jPanel1);

        tablaProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Solicitud", "Estado", "Detalles"
            }
        ));
        jScrollPane4.setViewportView(tablaProcesos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Cola de procesos", jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName("");

        jScrollPane2.setViewportView(jTabbedPane1);

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

        jLabel1.setText("Política de Planificación:");

        selectorPolitica.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "SSTF", "SCAN", "C-SCAN" }));
        selectorPolitica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorPoliticaActionPerformed(evt);
            }
        });

        menuSistema.setText("Sistema");

        menuNuevoSistema.setText("Nuevo");
        menuNuevoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoSistemaActionPerformed(evt);
            }
        });
        menuSistema.add(menuNuevoSistema);

        menuGuardarSistema.setText("Guardar Sistema");
        menuGuardarSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarSistemaActionPerformed(evt);
            }
        });
        menuSistema.add(menuGuardarSistema);

        menuCargarSistema.setText("Cargar Sistema");
        menuCargarSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCargarSistemaActionPerformed(evt);
            }
        });
        menuSistema.add(menuCargarSistema);

        jMenuBar1.add(menuSistema);

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
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectorPolitica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(selectorPolitica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            // Registrar en el gestor de procesos si existe
            if (gestorProcesos != null) {
                SolicitudES solicitud = new SolicitudES(TipoSolicitud.CREAR_DIRECTORIO, nombre.trim(), dirPadre);
                gestorProcesos.registrarNuevaSolicitud(solicitud);
            }
            actualizarTodasLasVistas(); // mantener tu actualización de vistas
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
            SolicitudES solicitud = new SolicitudES(TipoSolicitud.CREAR_ARCHIVO, nombre.trim(), dirPadre, tamano);
            gestorProcesos.registrarNuevaSolicitud(solicitud);
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
    
    private void actualizarTablaProcesos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaProcesos.getModel();
        modeloTabla.setRowCount(0);
        ListaEnlazada<Proceso> cola = gestorProcesos.getColaDeES();
        for (int i = 0; i < cola.getTamano(); i++) {
            Proceso proceso = cola.get(i);
            if (proceso == null) continue; 
            SolicitudES solicitud = proceso.getSolicitud();
            String detalles = solicitud.getNombre();
                        if (solicitud.getTipo() == TipoSolicitud.CREAR_ARCHIVO) {
                detalles += " (" + solicitud.getTamanoEnBloques() + " bloques)";
            }

            Object[] fila = new Object[]{
                proceso.getId(),
                solicitud.getTipo(),
                proceso.getEstado(),
                detalles
            };
            modeloTabla.addRow(fila);
        }
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

    private void selectorPoliticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorPoliticaActionPerformed
    String seleccion = (String) selectorPolitica.getSelectedItem();
    if (seleccion == null) return;

    switch (seleccion) {
        case "FIFO":
            gestorProcesos.setPolitica(new PoliticaFIFO());
            break;
        case "SSTF":
            gestorProcesos.setPolitica(new PoliticaSSTF());
            break;
        case "SCAN":
            gestorProcesos.setPolitica(new PoliticaSCAN());
            break;
        case "C-SCAN":
            gestorProcesos.setPolitica(new PoliticaCSCAN());
            break;
        default:
            gestorProcesos.setPolitica(new PoliticaFIFO());
            break;
    }
    }//GEN-LAST:event_selectorPoliticaActionPerformed

    private void menuNuevoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoSistemaActionPerformed
     int tamano = sistema.getDisco().bloques.length; // conservar tamaño del disco
    sistema = new SistemaDeArchivos(tamano);        // crear nuevo sistema vacío

    // recrear o reconfigurar gestor de procesos para el nuevo sistema
    if (gestorProcesos == null) {
        gestorProcesos = new GestorDeProcesos(this.sistema);
    } else {
        gestorProcesos = new GestorDeProcesos(this.sistema); // o gestorProcesos.setSistema(this.sistema);
    }

    actualizarTodasLasVistas();
    JOptionPane.showMessageDialog(this, "Sistema reiniciado correctamente.");
    
    }//GEN-LAST:event_menuNuevoSistemaActionPerformed

    private void menuGuardarSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarSistemaActionPerformed
      JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar Sistema de Archivos");
    int result = chooser.showSaveDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        try {
            GestorPersistencia.guardarSistema(
                sistema.getDirectorioRaiz(),
                sistema.getDisco(),
                chooser.getSelectedFile().getAbsolutePath()
            );
            JOptionPane.showMessageDialog(this, "Sistema guardado exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }  // TODO add your handling code here:
    }//GEN-LAST:event_menuGuardarSistemaActionPerformed

    private void menuCargarSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCargarSistemaActionPerformed
        JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Cargar Sistema de Archivos");
    int result = chooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        try {
            // 🔹 Cargar sistema completo (raíz + disco), devuelve la nueva raíz
            Directorio nuevaRaiz = GestorPersistencia.cargarSistema(
                chooser.getSelectedFile().getAbsolutePath(),
                sistema.getDisco()  // disco actual donde se reconstruyen los bloques
            );

            // 🔹 Reemplazar la raíz actual por la cargada
            sistema.setDirectorioRaiz(nuevaRaiz);

            // 🔹 RECREAR o RECONFIGURAR gestorProcesos para el sistema recién cargado
            gestorProcesos = new GestorDeProcesos(this.sistema);

            // 🔹 Actualizar vistas (árbol, tabla, disco)
            actualizarTodasLasVistas();

            JOptionPane.showMessageDialog(this, "Sistema cargado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_menuCargarSistemaActionPerformed

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
        actualizarTablaProcesos();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem menuCPU;
    private javax.swing.JMenuItem menuCambiarModo;
    private javax.swing.JMenuItem menuCargarSistema;
    private javax.swing.JMenuItem menuCrearArchivo;
    private javax.swing.JMenuItem menuCrearDirectorio;
    private javax.swing.JMenuItem menuEliminar;
    private javax.swing.JMenuItem menuGuardarSistema;
    private javax.swing.JMenuItem menuNuevoSistema;
    private javax.swing.JMenu menuSistema;
    private vista.PanelDiscoVisual panelDisco;
    private javax.swing.JComboBox<String> selectorPolitica;
    private javax.swing.JTable tablaArchivos;
    private javax.swing.JTable tablaProcesos;
    // End of variables declaration//GEN-END:variables
}

