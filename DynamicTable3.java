import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class DynamicTable3 {
	// TextField
	private JTextField text1;

	// JTable Header
	public static final String[] columns = { "Name", "ruta" };
	private String filename;
	private int columna;
	private int row;
	private String rutareproducir;
	private Player player;
	private DefaultTableModel model = new DefaultTableModel(columns, 0);
	private JTable table = new JTable(model) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JPanel textpanel2 = new JPanel();
	private JPanel mainPanel = new JPanel();
	private JTextField textField;
	private String[] rutascanciones;
	private BufferedInputStream buffer;
	private int eno;
	FileInputStream fis;
	private boolean complete = false;

	public synchronized boolean isComplete() {
		return this.complete;
	}

	public void mp3PlayerSample(String filename) {
		this.filename = filename;
	}
////////////////////// IMITAR FUNCION isComplete de PLAYER.CALSSSS AAAAAAAAAAAAAAAAAA

	static boolean pausa = false;
	private JTextField txtcancionactual;
	private BasicPlayer player3;
	private BasicController playercontrol;

	public void papa() {
		try {

			fis = new FileInputStream(rutascanciones[eno]);
			// fis = new FileInputStream(rutareproducir);
			buffer = new BufferedInputStream(fis);

			player3 = new BasicPlayer();
			player3.open(buffer);

			txtcancionactual.setText(String.valueOf(model.getValueAt(eno, 0)));
			playercontrol = (BasicController) player3;

			// AÑADIR FUNCION PARA QUE SIGA REPRODUCIENDO LA SIGUIENTE
		} catch (BasicPlayerException | FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	public void papa2() {

		try {

			fis = new FileInputStream(rutascanciones[eno]);
			// fis = new FileInputStream(rutareproducir);
			buffer = new BufferedInputStream(fis);
			player = new Player(buffer);
			while (true) {

				player.play(1);
				txtcancionactual.setText(String.valueOf(model.getValueAt(eno, 0)));
				if (player.isComplete() == true) {
					eno++;
					papa2();

				}

			}
		} catch (JavaLayerException | FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	public void papa3() {

		while (comprobar() == true) {

			eno = eno + 1;

			try {
				playercontrol.stop();
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				papa();
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				playercontrol.play();
			} catch (BasicPlayerException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}

			papa3();
		}

	}

	public void bucle1() {

		while (true) {
			if (player3.getStatus() == 2) {
				eno = eno + 1;

				try {
					playercontrol.stop();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					papa();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					playercontrol.play();
				} catch (BasicPlayerException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				bucle1();
			} else {
				bucle1();
			}
		}
	}

	public void bucle2() {
		while (true) {
			int a = player3.getStatus();
			switch (a) {
			case 2:
				eno = eno + 1;

				try {
					playercontrol.stop();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					papa();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					playercontrol.play();
				} catch (BasicPlayerException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				break;
			default:
				bucle2();
				break;

			}
		}
	}

	boolean comprobar() {

		// True=stop
		// false= cualquier wea
		if (player3.getStatus() == 2) {
			return true;
		}
		return false;
	}

	public DynamicTable3() {
		Thread pa = new Thread() {
			public void run() {

				papa();
			}
		};

		Thread pa3 = new Thread() {
			public void run() {
				papa3();
			}
		};

		/*
		 * 
		 * player3.addBasicPlayerListener((BasicPlayerListener) new PlaybackListener() {
		 * 
		 * @SuppressWarnings("unused") public void playBackFinished(PlaybackEvent event)
		 * { pausedOnFrame=event.getFrame(); }
		 * 
		 * });
		 */
		// Clear button
		JButton rptButton = new JButton("|>");
		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 460, 684, 33);

		JButton btnAnterior = new JButton("||<|<|");
		btnAnterior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eno = eno - 1;

				try {
					playercontrol.stop();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					papa();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					playercontrol.play();

				} catch (BasicPlayerException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}

			}
		});

		JButton btnNewButton_1 = new JButton("AO");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print(String.valueOf(player3.getListeners()) + "            getListeners" + "\n");
				System.out.print(String.valueOf(player3.getGainValue()) + "           getGainValue" + "\n");
				System.out.print(String.valueOf(player3.getPrecision()) + "           getPrecision" + "\n");
				System.out.print(String.valueOf(player3.getSleepTime()) + "            getSleepTime" + "\n");
				System.out.print(
						String.valueOf(player3.getLineCurrentBufferSize()) + "            current buffe size" + "\n");
				System.out.print(String.valueOf(player3.getLineBufferSize()) + "         buffer size" + "\n");
				System.out.print(String.valueOf(buffer) + "       Buffer" + "\n");

			}
		});

		buttonPanel.add(btnNewButton_1);
		Thread pa2 = new Thread() {
			public void run() {

				while (true) {
					if (player3.getLineCurrentBufferSize() != -1) {

						System.out.print(" 1       Buffer" + "\n");
						break;
					} else if (player3.getLineCurrentBufferSize() == -1) {

						break;
					}
				}

			}
		};
		JButton btnNewButton = new JButton("assjk");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// pa2.start();
				new Thread() {
					public void run() {
						for (int i = 0; i < 1000000000; i++) {
							System.out.print(player3.getStatus() + "\n");

						}
					}
				}.start();
			}
		});
		buttonPanel.add(btnNewButton);
		buttonPanel.add(btnAnterior);
		buttonPanel.add(rptButton);

		JButton btnSiguiente = new JButton("|>|>||");
		btnSiguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eno = eno + 1;

				try {
					playercontrol.stop();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					papa();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					playercontrol.play();
				} catch (BasicPlayerException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});

		buttonPanel.add(btnSiguiente);

		// This code is called when the Clear button is clicked.
		ActionListener action = new ActionListener() {

			@SuppressWarnings({ "static-access" })
			public void actionPerformed(ActionEvent e) {
				String aer = rptButton.getText();
				if (aer.equals("|>")) {

					btnAnterior.setEnabled(true);
					btnSiguiente.setEnabled(true);
					rptButton.setText("||");
					filename = text1.getText();

					columna = table.getSelectedColumn();
					row = table.getSelectedRow();

					textField.setText(String.valueOf(row));
					txtcancionactual.setText(String.valueOf(model.getValueAt(eno, 0)));

					// int index = table.getSelectedRow();
					// Object valorcell = model.getValueAt(index, 1);

					// rutareproducir = String.valueOf(valorcell);

					// 3 IGUAL A SIN INICIAR
					// 0 IGUAL A REPRODUCIENDO
					// 1 IGUAL A PAUSADO
					// 2 IGUAL A DETENIDO

					if (player3.getStatus() == 3) {

						try {
							player3.play();

							System.out.print(player3.getStatus());
						} catch (BasicPlayerException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
					} else if (player3.getStatus() == 1) {
						try {
							player3.resume();
						} catch (BasicPlayerException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
					}

					// Thread.currentThread().stop();
					/*
					 * new Thread() { public void run() {
					 * 
					 * papa();
					 * 
					 * } }.start();
					 */

					// Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
					// System.out.print(threadSet +"\n");
				} else {
					rptButton.setText("|>");

					try {
						player3.pause();
						System.out.print(player3.getStatus());
					} catch (BasicPlayerException e2) {
						// TODO Bloque catch generado automáticamente
						e2.printStackTrace();
					}

					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}

				}

			}
		};
		rptButton.addActionListener(action);

		JLabel lblNewLabel_1 = new JLabel("Reproduciendo:");
		lblNewLabel_1.setBounds(29, 11, 76, 14);

		textpanel2.setBounds(155, 11, 86, 20);

		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {

						bucle1();

					}
				}.start();

			}
		});
		buttonPanel.add(btnNewButton_2);

		mainPanel.setLayout(null);
		JPanel textpanel2 = new JPanel();
		textpanel2.setBounds(21, 424, 587, 39);
		mainPanel.add(textpanel2);
		textpanel2.setLayout(null);
		textpanel2.add(lblNewLabel_1);

		txtcancionactual = new JTextField();
		txtcancionactual.setEditable(false);
		txtcancionactual.setForeground(Color.BLACK);
		txtcancionactual.setBackground(SystemColor.control);
		txtcancionactual.setBounds(136, 8, 394, 20);
		textpanel2.add(txtcancionactual);
		txtcancionactual.setColumns(10);

		// Create the JTextFields panel
		JPanel textPanel = new JPanel();
		textPanel.setBounds(0, 0, 698, 60);
		textPanel.setLayout(null);
		text1 = new JTextField();
		text1.setBounds(0, 29, 320, 20);
		// Add JTextFields to the panel
		textPanel.add(text1);

		// Add panels and table to the main panel
		mainPanel.add(textPanel);

		JLabel lblNewLabel = new JLabel("Ruta de canciones");
		lblNewLabel.setBounds(7, 11, 138, 20);
		textPanel.add(lblNewLabel);
		// Add button
		JButton addButton = new JButton("Importar");
		addButton.setBounds(330, 28, 92, 23);
		textPanel.add(addButton);
		textField = new JTextField();
		textField.setBounds(155, 11, 86, 20);
		textPanel.add(textField);
		textField.setColumns(10);

		// This code is called when the Add button is clicked.
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				FileNameExtensionFilter filtro = new FileNameExtensionFilter("mp3", "mp3");
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(
						new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Music"));
				fc.setFileFilter(filtro);
				fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);

				table.getRowSelectionAllowed();
				int seleccion = fc.showOpenDialog(addButton);
				File name = fc.getSelectedFile();

				if (name.exists()) {
					if (seleccion == JFileChooser.APPROVE_OPTION) {
						rptButton.setEnabled(true);
						if (model.getRowCount() >= 1) {
							model.setRowCount(0);
							Arrays.fill(rutascanciones, null);

						}
						File fichero = fc.getCurrentDirectory();

						String fichero2 = fichero.toString();
						String[] Rutas = { fichero2 };

						text1.setText(Rutas[0]);

						if (name.isDirectory()) {

							String directory[] = name.list();
							// String directory2 []=name.getCanonicalPath();

							File[] filesInDirectory = fichero.listFiles();

							for (String directoryName : directory)
								for (File ruta : filesInDirectory)
									try {
										model.addRow(new Object[] { directoryName,
												ruta.getCanonicalPath() + '\\' + directoryName });
										// remove rows with instances of "5/13/2013"
										for (int i = 0; i < model.getRowCount(); i++) {
											if (((String) model.getValueAt(i, 0)).endsWith(".mp3")) {
											} else {
												model.removeRow(i);
											} // end of if block
										}
										// end of for block*/

										for (int i = 0; i < model.getRowCount(); i++) {
											if (((String) model.getValueAt(i, 1)).contains(".ini")) {

												model.removeRow(i);
											} else {

												String a = "3";

											} // end of if block
										}
										// end of for block*/

									} catch (IOException e1) {
										// TODO Bloque catch generado automáticamente
										e1.printStackTrace();
									}

						}

						int tamañoarray = 0;

						int modelo = model.getRowCount();
						tamañoarray = modelo;
						String[] rutasp = new String[tamañoarray];
						for (int i = 0; i < model.getRowCount(); i++) {
							rutasp[i] = (String) model.getValueAt(i, 1);

							rutascanciones = Arrays.copyOf(rutasp, rutasp.length, String[].class);

							for (String r : rutascanciones) {

								System.out.print(r + "\n");
							}
						}
						papa();
						txtcancionactual.setText("");

					} else if (seleccion == JFileChooser.CANCEL_OPTION) {

					}
				}

				else {
					System.out.print("a");
				}

			}
		}

		);
		table.setFont(new Font("Tahoma", Font.BOLD, 11));

		table.getColumnModel().getColumn(0).setPreferredWidth(900);
		table.getColumnModel().getColumn(1).setPreferredWidth(1);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					eno = table.getSelectedRow();
					try {
						playercontrol.stop();
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						papa();
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						playercontrol.play();
					} catch (BasicPlayerException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					System.out.print("esto hacia algo antes");

					String aer = rptButton.getText();
					btnAnterior.setEnabled(true);
					btnSiguiente.setEnabled(true);
					rptButton.setText("||");

				} else if (e.getClickCount() == 1) {
					// esta opcion existe nomas pa que no se buguee este programa de porqueria yoko
					// ojala te dure ese puto +10 conchudo de mierda
				}

			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(21, 60, 664, 353);
		mainPanel.add(scrollPane);
		mainPanel.add(buttonPanel);

		btnAnterior.setEnabled(false);
		btnSiguiente.setEnabled(false);
		rptButton.setEnabled(false);

		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				AudioInputStream audioStream;
				try {
					audioStream = AudioSystem.getAudioInputStream(fis);

					AudioFormat baseFormat = audioStream.getFormat();
					AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
							baseFormat.getSampleRate(), false);
					AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
					Clip clip = AudioSystem.getClip();
					clip.open(audioStream2);
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(6.0f);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
		buttonPanel.add(slider);

	}

	// Get the main panel
	public JComponent getComponent() {
		return mainPanel;
	}

	public void pas() {
		BasicPlayerListener sajk = new BasicPlayerListener() {

			@Override
			public void stateUpdated(BasicPlayerEvent paramBasicPlayerEvent) {
				// TODO Esbozo de método generado automáticamente

			}

			@Override
			public void setController(BasicController paramBasicController) {
				// TODO Esbozo de método generado automáticamente

			}

			@Override
			public void progress(int paramInt, long paramLong, byte[] paramArrayOfbyte, Map paramMap) {
				// TODO Esbozo de método generado automáticamente

			}

			@Override
			public void opened(Object paramObject, Map paramMap) {
				// TODO Esbozo de método generado automáticamente

			}
		};
		player3.addBasicPlayerListener(sajk);

	}

	// start the application in thread-safe
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame f = new JFrame("Add automatically to JTable");
				f.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\user\\Downloads\\Captura.PNG"));
				f.setTitle("Making The Mother Of All Music Player Here Jack, Can't Fret Over Every Mp3");
				f.getContentPane().add(new DynamicTable3().getComponent());
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(705, 108, 305, 305);

				TextArea textArea = new TextArea();
				scrollPane.setViewportView(textArea);

				JScrollPane scrollPane2 = new JScrollPane();
				scrollPane2.setBounds(494, 71, 216, 275);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(718, 545);
				f.setLocationRelativeTo(null);
				f.setVisible(true);

			}
		});
	}

}