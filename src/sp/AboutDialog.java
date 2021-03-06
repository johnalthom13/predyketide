
package sp;

import javax.swing.*;
/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

    /** Creates new form AboutDialog */
    public AboutDialog() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        aboutDialogMainPanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("PREDYKETIDE");

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("A Special Problem for BS Computer Science Degree Program");

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("John Althom A. Mendoza");

        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("Copyright Information 2013");

        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setText("All Rights Reserved.");

        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel6.setText("University of the Philippines - Manila");

        GroupLayout aboutDialogMainPanelLayout = new GroupLayout(aboutDialogMainPanel);
        aboutDialogMainPanel.setLayout(aboutDialogMainPanelLayout);
        aboutDialogMainPanelLayout.setHorizontalGroup(
            aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(aboutDialogMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(aboutDialogMainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(aboutDialogMainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(aboutDialogMainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        aboutDialogMainPanelLayout.setVerticalGroup(
            aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(aboutDialogMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(35, 35, 35))
            .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, aboutDialogMainPanelLayout.createSequentialGroup()
                    .addContainerGap(96, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(24, 24, 24)))
            .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, aboutDialogMainPanelLayout.createSequentialGroup()
                    .addContainerGap(106, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(14, 14, 14)))
            .addGroup(aboutDialogMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, aboutDialogMainPanelLayout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(57, 57, 57)))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(aboutDialogMainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(aboutDialogMainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }// </editor-fold>

 
    // Variables declaration - do not modify
    private JPanel aboutDialogMainPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    // End of variables declaration
}
