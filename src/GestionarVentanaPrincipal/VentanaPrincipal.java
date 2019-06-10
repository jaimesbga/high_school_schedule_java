package GestionarVentanaPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import Utilitario.Autenticacion;
import Utilitario.Centrar;

public class VentanaPrincipal {

	private JFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="43,30"
	private JMenuBar menuBar = null;
	private JMenu m_conexion = null;
	private GestorVentanaPrincipal gestor;
	private JPanel panelVentana = null;
	private JPanel panelAccesos = null;
	private JButton btn_acceso1 = null;
	private JMenuItem mi_salir = null;
	private JMenuItem mi_desconectar = null;	
	private JMenuItem mi_iniciarSesion = null;
	private SystemTray tray = null;
	private TrayIcon trayIcon = null;  //  @jve:decl-index=0:
	private javax.swing.JMenuItem menuItemRestore;
    private javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JPopupMenu popupContextual;
    private javax.swing.JSeparator separator;
	private JMenuItem mi_minimizarBarra = null;
	private JMenu m_temas = null;
	private JRadioButtonMenuItem mi_tema1 = null;
	private JRadioButtonMenuItem mi_tema2 = null;
	private ButtonGroup grupoTemas = null;  //  @jve:decl-index=0:
	private JDesktopPane desktopPanel = null;  //  @jve:decl-index=0:visual-constraint="-390,559"
	private JButton btnPrueba = null;
	private JMenu m_mantenimiento = null;
	private JMenuItem mi_bloques = null;
	private JMenuItem mi_materias = null;
	private JMenuItem mi_usuarios = null;
	private JMenuItem mi_estudiantes = null;
	private JMenuItem mi_profesores = null;
	private JMenu m_aplicacion = null;
	private JMenu m_datos = null;
	private JMenuItem mi_seccion = null;
	private JMenuItem mi_horarios = null;
	private JMenuItem mi_inasistencias = null;
	private JMenuItem mi_reportes = null;
	private JMenu m_reportes = null;
	
	public VentanaPrincipal(Autenticacion a, Properties preferencias){
		getVentana();
		ventana.setLocation(Centrar.centrarEnPantalla(ventana.getSize()));
		gestor = new GestorVentanaPrincipal(this, a, preferencias);		
	}
	
	public GestorVentanaPrincipal getGestor(){
		return gestor;
	}
	
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	public JFrame getVentana() {
		if (ventana == null) {
			ventana = new JFrame();
			ventana.setSize(new Dimension(813, 671));
			ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			ventana.setLayout(null);
			ventana.setJMenuBar(getMenuBar());
			ventana.setTitle("Sistema de Control de Inasistencias");
			ventana.setMaximumSize(new Dimension(21474, 2147483));
			ventana.setContentPane(getPanelVentana());
			ventana.setJMenuBar(getMenuBar());			
			ventana.setMinimumSize(new Dimension(800, 600));
			ventana.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					gestor.salir();
				}
			});
			getTray();
		}
		return ventana;
	}
	
	public void getTray(){
		java.awt.Image image = new ImageIcon(getClass().getResource("/Imagenes/Imagen005.png")).getImage();
        trayIcon = new TrayIcon(image, "Sistema de Control de Inasistencias", null);
        trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupContextual.setLocation(e.getX(), e.getY());
                    popupContextual.setInvoker(popupContextual);
                    popupContextual.setVisible(true);
                }     
            }
        });
        trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    ventana.setVisible(true);                                               
                    ventana.toFront();
                    tray.remove(trayIcon);
                }
            }
        });
        
        popupContextual = new javax.swing.JPopupMenu();
        menuItemRestore = new javax.swing.JMenuItem();
        separator = new javax.swing.JSeparator();
        menuItemSalir = new javax.swing.JMenuItem();

        menuItemRestore.setText("Restaurar");
        menuItemRestore.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen016.png")));
        menuItemRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventana.setVisible(true);                                               
                ventana.toFront();
                tray.remove(trayIcon);
            }
        });
        popupContextual.add(menuItemRestore);
        popupContextual.add(separator);

        menuItemSalir.setText("Salir");
        menuItemSalir.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen008.png")));
        menuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //System.exit(0);
            	gestor.salir();
            }
        });
        popupContextual.add(menuItemSalir);
	}
	
	public void cambiarEstadoVentana() { 
	   ventana.setState(JFrame.NORMAL);  
	   if (SystemTray.isSupported()) {  
	        ventana.setVisible(false);
	        tray = SystemTray.getSystemTray();
	        trayIcon.setImageAutoSize(true);
	        
	         try {
	            tray.add(trayIcon);
	         }
	         catch (Exception e) {                
	            ventana.setVisible(true);
	         }  
	   }
	}
	
	/**
	 * This method initializes menuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.setPreferredSize(new Dimension(0, 32));
			menuBar.add(getM_datos());
			menuBar.add(getM_reportes());
			menuBar.add(getM_mantenimiento());						
			menuBar.add(getM_aplicacion());
			menuBar.add(getM_conexion());			
		}
		return menuBar;
	}
	/**
	 * This method initializes m_conexion	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_conexion() {
		if (m_conexion == null) {
			m_conexion = new JMenu();
			m_conexion.setText("Conexión");
			m_conexion.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen002.png")));
			m_conexion.setFont(new Font("Dialog", Font.BOLD, 12));
			m_conexion.setMnemonic(KeyEvent.VK_C);
			m_conexion.add(getMi_iniciarSesion());
			m_conexion.add(getMi_desconectar());			
			m_conexion.add(getMi_salir());
		}
		return m_conexion;
	}

	/**
	 * This method initializes panelVentana	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelVentana() {
		if (panelVentana == null) {
			panelVentana = new JPanel();
			panelVentana.setLayout(new BorderLayout());
			panelVentana.add(getPanelAccesos(), BorderLayout.NORTH);
			panelVentana.add(getDesktopPanel(), BorderLayout.CENTER);
		}
		return panelVentana;
	}

	/**
	 * This method initializes panelAccesos	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelAccesos() {
		if (panelAccesos == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setHgap(0);
			flowLayout.setVgap(0);
			panelAccesos = new JPanel();
			panelAccesos.setLayout(flowLayout);
			panelAccesos.add(getBtn_acceso1(), null);
			panelAccesos.add(getBtnPrueba(), null);
		}
		return panelAccesos;
	}

	/**
	 * This method initializes btn_acceso1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getBtn_acceso1() {
		if (btn_acceso1 == null) {
			btn_acceso1 = new JButton();
			btn_acceso1.setEnabled(false);
			btn_acceso1.setPreferredSize(new Dimension(70, 70));
			btn_acceso1.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen003.png")));
			btn_acceso1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					gestor.cerrarSesion();
				}
			});
		}
		return btn_acceso1;
	}

	/**
	 * This method initializes mi_salir	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_salir() {
		if (mi_salir == null) {
			mi_salir = new JMenuItem();
			mi_salir.setText("Salir");
			mi_salir.setMnemonic(KeyEvent.VK_S);
			mi_salir.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen008.png")));
			mi_salir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.salir();
				}
			});
		}
		return mi_salir;
	}

	/**
	 * This method initializes mi_desconectar	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMi_desconectar() {
		if (mi_desconectar == null) {
			mi_desconectar = new JMenuItem();
			mi_desconectar.setMnemonic(KeyEvent.VK_C);
			mi_desconectar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen004.png")));
			mi_desconectar.setText("Cerrar sesión");
			mi_desconectar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.cerrarSesion();
				}
			});
		}
		return mi_desconectar;
	}

	/**
	 * This method initializes mi_iniciarSesion1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMi_iniciarSesion() {
		if (mi_iniciarSesion == null) {
			mi_iniciarSesion = new JMenuItem();
			mi_iniciarSesion.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen012.png")));
			mi_iniciarSesion.setText("Iniciar sesión");
			mi_iniciarSesion.setMnemonic(KeyEvent.VK_I);
			mi_iniciarSesion.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.mostrarVentanaUsuario();
				}
			});
		}
		return mi_iniciarSesion;
	}

	/**
	 * This method initializes mi_minimizarBarra	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_minimizarBarra() {
		if (mi_minimizarBarra == null) {
			mi_minimizarBarra = new JMenuItem();
			mi_minimizarBarra.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen016.png")));
			mi_minimizarBarra.setText("Ocultar ventana");
			mi_minimizarBarra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cambiarEstadoVentana();
					trayIcon.displayMessage("Sistema", "El programa se esta ejecutando...", TrayIcon.MessageType.INFO);
				}
			});
		}
		return mi_minimizarBarra;
	}

	/**
	 * This method initializes m_temas	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_temas() {
		if (m_temas == null) {
			m_temas = new JMenu();
			m_temas.setName("");
			m_temas.setMnemonic(KeyEvent.VK_T);
			m_temas.setText("Cambiar tema");
			m_temas.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen024.png")));
			m_temas.add(getMi_tema1());
			m_temas.add(getMi_tema2());
			getGrupoTemas();
		}
		return m_temas;
	}
	
	private ButtonGroup getGrupoTemas(){
		if(grupoTemas == null){
			grupoTemas = new ButtonGroup();
			grupoTemas.add(getMi_tema1());
			grupoTemas.add(getMi_tema2());
		}
		
		return grupoTemas;
	}

	/**
	 * This method initializes mi_tema1	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	public JRadioButtonMenuItem getMi_tema1() {
		if (mi_tema1 == null) {
			mi_tema1 = new JRadioButtonMenuItem();
			mi_tema1.setText("Nimbus");
			mi_tema1.setSelected(true);
			mi_tema1.setMnemonic(KeyEvent.VK_UNDEFINED);			
			mi_tema1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.cambiarTema("Nimbus");
				}
			});
		}
		return mi_tema1;
	}

	/**
	 * This method initializes mi_tema2	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	public JRadioButtonMenuItem getMi_tema2() {
		if (mi_tema2 == null) {
			mi_tema2 = new JRadioButtonMenuItem();
			mi_tema2.setText("Windows");
			mi_tema2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.cambiarTema("Windows");
				}
			});
			
		}
		return mi_tema2;
	}

	/**
	 * This method initializes desktopPanel	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	public JDesktopPane getDesktopPanel() {
		if (desktopPanel == null) {
			desktopPanel = new JDesktopPane();
			desktopPanel.setBackground(Color.white);
		}
		return desktopPanel;
	}

	/**
	 * This method initializes btnPrueba	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrueba() {
		if (btnPrueba == null) {
			btnPrueba = new JButton();
			btnPrueba.setPreferredSize(new Dimension(70, 70));
			btnPrueba.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen009.png")));
			btnPrueba.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.btnPrueba();
				}
			});
		}
		return btnPrueba;
	}

	/**
	 * This method initializes m_mantenimiento	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	public JMenu getM_mantenimiento() {
		if (m_mantenimiento == null) {
			m_mantenimiento = new JMenu();
			m_mantenimiento.setText("Mantenimiento");
			m_mantenimiento.setMnemonic(KeyEvent.VK_M);
			m_mantenimiento.setFont(new Font("SansSerif", Font.BOLD, 12));
			m_mantenimiento.setEnabled(false);
			m_mantenimiento.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen022.png")));
			m_mantenimiento.add(getMi_bloques());
			m_mantenimiento.add(getMi_usuarios());
		}
		return m_mantenimiento;
	}

	/**
	 * This method initializes mi_bloques	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_bloques() {
		if (mi_bloques == null) {
			mi_bloques = new JMenuItem();
			mi_bloques.setText("Gestionar bloques");
			mi_bloques.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen023.png")));
			mi_bloques.setMnemonic(KeyEvent.VK_B);
			mi_bloques.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.gestionarBloques();
				}
			});
		}
		return mi_bloques;
	}

	/**
	 * This method initializes mi_materias	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_materias() {
		if (mi_materias == null) {
			mi_materias = new JMenuItem();
			mi_materias.setMnemonic(KeyEvent.VK_M);
			mi_materias.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen028.png")));
			mi_materias.setText("Gestionar materias");
			mi_materias.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.gestionarMaterias();
				}
			});
		}
		return mi_materias;
	}

	/**
	 * This method initializes mi_usuarios	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_usuarios() {
		if (mi_usuarios == null) {
			mi_usuarios = new JMenuItem();
			mi_usuarios.setText("Gestionar usuarios");
			mi_usuarios.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen026.png")));
			mi_usuarios.setMnemonic(KeyEvent.VK_U);
			mi_usuarios.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.gestionarUsuarios();
				}
			});
		}
		return mi_usuarios;
	}

	/**
	 * This method initializes mi_estudiantes	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_estudiantes() {
		if (mi_estudiantes == null) {
			mi_estudiantes = new JMenuItem();
			mi_estudiantes.setMnemonic(KeyEvent.VK_E);
			mi_estudiantes.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Image018_.png")));
			mi_estudiantes.setText("Gestionar estudiantes");
			mi_estudiantes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.gestionarEstudiantes();
				}
			});
		}
		return mi_estudiantes;
	}

	/**
	 * This method initializes mi_profesores	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_profesores() {
		if (mi_profesores == null) {
			mi_profesores = new JMenuItem();
			mi_profesores.setMnemonic(KeyEvent.VK_P);
			mi_profesores.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen026.png")));
			mi_profesores.setText("Gestionar profesores");
			mi_profesores.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.gestionarProfesores();
				}
			});
		}
		return mi_profesores;
	}

	/**
	 * This method initializes m_aplicacion	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_aplicacion() {
		if (m_aplicacion == null) {
			m_aplicacion = new JMenu();
			m_aplicacion.setText("Aplicación");
			m_aplicacion.setFont(new Font("Dialog", Font.BOLD, 12));
			m_aplicacion.setMnemonic(KeyEvent.VK_A);
			m_aplicacion.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen005.png")));
			m_aplicacion.add(getM_temas());
			m_aplicacion.add(getMi_minimizarBarra());			
		}
		return m_aplicacion;
	}

	/**
	 * This method initializes m_datos	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	public JMenu getM_datos() {
		if (m_datos == null) {
			m_datos = new JMenu("Sistema");
			m_datos.setFont(new Font("SansSerif", Font.BOLD, 12));
			m_datos.setMnemonic(KeyEvent.VK_S);
			m_datos.setEnabled(false);
			m_datos.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen027.png")));
			m_datos.add(getMi_materias());
			m_datos.add(getMi_estudiantes());
			m_datos.add(getMi_profesores());
			m_datos.add(getMi_seccion());
			m_datos.add(getMi_horarios());
			m_datos.add(getMi_inasistencias());
		}
		return m_datos;
	}

	/**
	 * This method initializes mi_seccion	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_seccion() {
		if (mi_seccion == null) {
			mi_seccion = new JMenuItem("Gestionar Secciones");
			mi_seccion.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen023.png")));
			mi_seccion.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						gestor.verVentanaSecciones();
				}
			});
		}
		return mi_seccion;
	}

	/**
	 * This method initializes mi_horarios	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_horarios() {
		if (mi_horarios == null) {
			mi_horarios = new JMenuItem("Ver Horarios");
			mi_horarios.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen025.png")));
			mi_horarios.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						gestor.verVentanaHorarios();
				}
			});
		}
		return mi_horarios;
	}

	/**
	 * This method initializes mi_inasistencias	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_inasistencias() {
		if (mi_inasistencias == null) {
			mi_inasistencias = new JMenuItem("Gestionar Actividad Diaria");
			mi_inasistencias.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen013.png")));
			mi_inasistencias.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						gestor.verVentanaInasistencias();
				}
			});
		}
		return mi_inasistencias;
	}

	/**
	 * This method initializes mi_reportes	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_reportes() {
		if (mi_reportes == null) {
			mi_reportes = new JMenuItem("Generar Reportes");
			mi_reportes.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Image056.png")));
			mi_reportes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.verVentanaReportes();
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return mi_reportes;
	}

	/**
	 * This method initializes m_reportes	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	public JMenu getM_reportes() {
		if (m_reportes == null) {
			m_reportes = new JMenu();
			m_reportes.setText("Reportes");
			m_reportes.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Image055.png")));
			m_reportes.setEnabled(false);
			m_reportes.setFont(new Font("SansSerif", Font.BOLD, 12));
			m_reportes.add(getMi_reportes());
		}
		return m_reportes;
	}
}
