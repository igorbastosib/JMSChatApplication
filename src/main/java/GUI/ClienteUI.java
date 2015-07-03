/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JOptionPane;

import chat.ChatJMSSub;
import jogoAssassino.JogoCliente;

/**
 *
 * @author Lupyep
 */
public class ClienteUI extends javax.swing.JFrame {

	private static JogoCliente jogo;
	
    /**
     * Creates new form ClienteUI
     */
    public ClienteUI() {
        initComponents();
        enableFields(false);
        
        try {
			jogo = new JogoCliente();
			jogo.setNomeJogador("ERRO");
		} catch (Exception ex) {
			System.out.println("ClienteUI exception: " + ex.getMessage());
			ex.printStackTrace();
		}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        panelRegistrar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textRegistrar = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        panelJogo = new javax.swing.JPanel();
        btnEnviar = new javax.swing.JButton();
        textSendMsg = new javax.swing.JTextField();
        panelShowPlayers = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textPlayers = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textMsgs = new javax.swing.JTextArea();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo - Mafioso");

        panelRegistrar.setBorder(javax.swing.BorderFactory.createTitledBorder("   Registrar Jogador   "));

        jLabel1.setText("Digite o nome do Jogador:");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRegistrarLayout = new javax.swing.GroupLayout(panelRegistrar);
        panelRegistrar.setLayout(panelRegistrarLayout);
        panelRegistrarLayout.setHorizontalGroup(
            panelRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistrarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistrarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(textRegistrar))
                .addGap(18, 18, 18)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRegistrarLayout.setVerticalGroup(
            panelRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelRegistrarLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelJogo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        panelShowPlayers.setBorder(javax.swing.BorderFactory.createTitledBorder("Jogadores"));

        textPlayers.setEditable(false);
        textPlayers.setColumns(20);
        textPlayers.setRows(5);
        jScrollPane3.setViewportView(textPlayers);

        javax.swing.GroupLayout panelShowPlayersLayout = new javax.swing.GroupLayout(panelShowPlayers);
        panelShowPlayers.setLayout(panelShowPlayersLayout);
        panelShowPlayersLayout.setHorizontalGroup(
            panelShowPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShowPlayersLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelShowPlayersLayout.setVerticalGroup(
            panelShowPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("   Chat   "));

        textMsgs.setEditable(false);
        textMsgs.setColumns(20);
        textMsgs.setRows(5);
        jScrollPane2.setViewportView(textMsgs);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelJogoLayout = new javax.swing.GroupLayout(panelJogo);
        panelJogo.setLayout(panelJogoLayout);
        panelJogoLayout.setHorizontalGroup(
            panelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogoLayout.createSequentialGroup()
                .addGroup(panelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelJogoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textSendMsg)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelShowPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelJogoLayout.setVerticalGroup(
            panelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogoLayout.createSequentialGroup()
                .addGroup(panelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelShowPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textSendMsg)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
    	try {
			if (textRegistrar.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Digite um nome de Jogador.", "Registrar nome do Jogador", JOptionPane.WARNING_MESSAGE);
			} else {
				if (jogo.registraNome(textRegistrar.getText()).equals("ERRO")) {
					JOptionPane.showMessageDialog(null, "Impossível registrar este nome, tente outro.", "Erro", JOptionPane.ERROR_MESSAGE);
				} else {
					ChatJMSSub chatSub = new ChatJMSSub(jogo.getNomeJogador(), textMsgs);
					enableFields(true);
					textRegistrar.setEnabled(false);
					btnRegistrar.setEnabled(false);
					
					ListaJogadores listaJogadores = new ListaJogadores();
		    		Thread threadListaJogadores = new Thread(listaJogadores);
		    		threadListaJogadores.start();
		    		
		    		VerificaJob verificaJob = new VerificaJob();
		    		Thread threadVerificaJob = new Thread(verificaJob);
		    		threadVerificaJob.start();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
    	try {
			jogo.enviaMensagem(textSendMsg.getText());
			textSendMsg.setText("");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void enableFields(Boolean bool){
        textMsgs.setEnabled(bool);
        textSendMsg.setEnabled(bool);
        btnEnviar.setEnabled(bool);
        textPlayers.setEnabled(bool);
    }
    
    public class ListaJogadores implements Runnable {
		public void run () {
			while(true){
				try{
					textPlayers.setText(jogo.enviaMensagem("/listPlayers"));
					Thread.sleep(3000);
				} catch (Exception e) {
		    		System.out.println(e.getMessage());
	        	}
			}
		}
	}
    
    public class VerificaJob implements Runnable {
		public void run () {
			while(true){
				try{
					if(jogo.getJogo().isJogoIniciado()){
						jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("   Chat   - Você é "+jogo.getJogo().localizaJob(jogo.getNomeJogador())));
					}else{
						jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("   Chat   "));
					}
					Thread.sleep(3000);
				} catch (Exception e) {
		    		System.out.println(e.getMessage());
	        	}
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClienteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel panelJogo;
    private javax.swing.JPanel panelRegistrar;
    private javax.swing.JPanel panelShowPlayers;
    private javax.swing.JTextArea textMsgs;
    private javax.swing.JTextArea textPlayers;
    private javax.swing.JTextField textRegistrar;
    private javax.swing.JTextField textSendMsg;
    // End of variables declaration//GEN-END:variables
}