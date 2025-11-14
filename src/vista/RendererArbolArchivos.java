/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author santi
 */
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import modelo.Directorio;
import modelo.EntradaSistemaArchivos;

public class RendererArbolArchivos extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
        Object objetoUsuario = nodo.getUserObject();
        
        if (objetoUsuario instanceof EntradaSistemaArchivos) {
            EntradaSistemaArchivos entrada = (EntradaSistemaArchivos) objetoUsuario;
            setText(entrada.getNombre());
            
            if (entrada instanceof Directorio) {
                setIcon(getDefaultOpenIcon());
            } else {
                setIcon(getDefaultLeafIcon());
            }
        }
        return this;
    }
}

