package splitter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.Timer;

import java.awt.Panel;
import java.util.ArrayList;
import java.awt.Label;
import java.awt.Button;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TimeSplit {
	
	private static JButton btnSettings;

	private JFrame frame;
	private JTable table;
	private static JLabel lblTimer;
	private ArrayList<Segment> segments = new ArrayList<>();
	private static boolean hasRan;
	private static chronos segmentTime;
	private static chronos totalTime;
	private static int lastH;
	private static int lastM;
	private static int lastS;
	private static int lastMi;
	private static boolean paused;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeSplit window = new TimeSplit();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimeSplit() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 300, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		frame.setMinimumSize(new Dimension(250, 300));
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBackground(new Color(65, 105, 225));
		springLayout.putConstraint(SpringLayout.NORTH, pnlButtons, -110, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pnlButtons, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, pnlButtons, 0, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pnlButtons, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(pnlButtons);
		SpringLayout sl_pnlButtons = new SpringLayout();
		pnlButtons.setLayout(sl_pnlButtons);
		
		JButton btnSplit = new JButton("Start");
		sl_pnlButtons.putConstraint(SpringLayout.NORTH, btnSplit, 5, SpringLayout.NORTH, pnlButtons);
		sl_pnlButtons.putConstraint(SpringLayout.WEST, btnSplit, 10, SpringLayout.WEST, pnlButtons);
		sl_pnlButtons.putConstraint(SpringLayout.SOUTH, btnSplit, -35, SpringLayout.SOUTH, pnlButtons);
		sl_pnlButtons.putConstraint(SpringLayout.EAST, btnSplit, -10, SpringLayout.EAST, pnlButtons);
		pnlButtons.add(btnSplit);
		
		JButton btnStop = new JButton("stop");
		sl_pnlButtons.putConstraint(SpringLayout.NORTH, btnStop, 0, SpringLayout.SOUTH, btnSplit);
		sl_pnlButtons.putConstraint(SpringLayout.WEST, btnStop, 0, SpringLayout.WEST, btnSplit);
		sl_pnlButtons.putConstraint(SpringLayout.SOUTH, btnStop, -2, SpringLayout.SOUTH, pnlButtons);
		sl_pnlButtons.putConstraint(SpringLayout.EAST, btnStop, 70, SpringLayout.WEST, btnSplit);
		pnlButtons.add(btnStop);
		
		JButton btnPause = new JButton("pause");
		sl_pnlButtons.putConstraint(SpringLayout.NORTH, btnPause, 0, SpringLayout.SOUTH, btnSplit);
		sl_pnlButtons.putConstraint(SpringLayout.WEST, btnPause, 6, SpringLayout.EAST, btnStop);
		sl_pnlButtons.putConstraint(SpringLayout.SOUTH, btnPause, -2, SpringLayout.SOUTH, pnlButtons);
		sl_pnlButtons.putConstraint(SpringLayout.EAST, btnPause, 0, SpringLayout.EAST, btnSplit);
		pnlButtons.add(btnPause);
		
		JPanel pnlTitle = new JPanel();
		pnlTitle.setBackground(new Color(65, 105, 225));
		springLayout.putConstraint(SpringLayout.NORTH, pnlTitle, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pnlTitle, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, pnlTitle, 50, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pnlTitle, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(pnlTitle);
		SpringLayout sl_pnlTitle = new SpringLayout();
		pnlTitle.setLayout(sl_pnlTitle);
		
		JLabel lblGameTitle = new JLabel("sm64");
		lblGameTitle.setForeground(new Color(255, 255, 255));
		lblGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameTitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		sl_pnlTitle.putConstraint(SpringLayout.NORTH, lblGameTitle, 10, SpringLayout.NORTH, pnlTitle);
		sl_pnlTitle.putConstraint(SpringLayout.WEST, lblGameTitle, 50, SpringLayout.WEST, pnlTitle);
		sl_pnlTitle.putConstraint(SpringLayout.EAST, lblGameTitle, -50, SpringLayout.EAST, pnlTitle);
		pnlTitle.add(lblGameTitle);
		
		btnSettings = new JButton("");
		sl_pnlTitle.putConstraint(SpringLayout.NORTH, btnSettings, 0, SpringLayout.NORTH, pnlTitle);
		sl_pnlTitle.putConstraint(SpringLayout.WEST, btnSettings, -40, SpringLayout.EAST, pnlTitle);
		sl_pnlTitle.putConstraint(SpringLayout.SOUTH, btnSettings, 40, SpringLayout.NORTH, pnlTitle);
		sl_pnlTitle.putConstraint(SpringLayout.EAST, btnSettings, 0, SpringLayout.EAST, pnlTitle);
		pnlTitle.add(btnSettings);
		  try {
			    Image img = ImageIO.read(getClass().getResource("images/cog.png"));
			    btnSettings.setIcon(new ImageIcon(img));
			    
			    JLabel lblRunCount = new JLabel("0");
			    sl_pnlTitle.putConstraint(SpringLayout.NORTH, lblRunCount, 0, SpringLayout.NORTH, pnlTitle);
			    sl_pnlTitle.putConstraint(SpringLayout.WEST, lblRunCount, 2, SpringLayout.WEST, pnlTitle);
			    lblRunCount.setForeground(new Color(255, 255, 255));
			    pnlTitle.add(lblRunCount);
			    
			    JPanel pnlSegments = new JPanel();
			    pnlSegments.setBackground(new Color(100, 149, 237));
			    springLayout.putConstraint(SpringLayout.NORTH, pnlSegments, 0, SpringLayout.SOUTH, pnlTitle);
			    springLayout.putConstraint(SpringLayout.WEST, pnlSegments, 0, SpringLayout.WEST, frame.getContentPane());
			    springLayout.putConstraint(SpringLayout.SOUTH, pnlSegments, 0, SpringLayout.NORTH, pnlButtons);
			    springLayout.putConstraint(SpringLayout.EAST, pnlSegments, 0, SpringLayout.EAST, frame.getContentPane());
			    frame.getContentPane().add(pnlSegments);
			    SpringLayout sl_pnlSegments = new SpringLayout();
			    pnlSegments.setLayout(sl_pnlSegments);
			    
			    lblTimer = new JLabel("0:00:00.00");
			    sl_pnlSegments.putConstraint(SpringLayout.SOUTH, lblTimer, 0, SpringLayout.SOUTH, pnlSegments);
			    lblTimer.setFont(new Font("News Gothic MT", Font.BOLD, 36));
			    lblTimer.setForeground(new Color(50, 205, 50));
			    sl_pnlSegments.putConstraint(SpringLayout.EAST, lblTimer, -10, SpringLayout.EAST, pnlSegments);
			    pnlSegments.add(lblTimer);
			    
			    JScrollPane scrollPane = new JScrollPane();
			    sl_pnlSegments.putConstraint(SpringLayout.NORTH, scrollPane, 30, SpringLayout.NORTH, pnlSegments);
			    sl_pnlSegments.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, pnlSegments);
			    sl_pnlSegments.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.NORTH, lblTimer);
			    sl_pnlSegments.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, pnlSegments);
			    springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.SOUTH, pnlTitle);
			    springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, pnlSegments);
			    springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -48, SpringLayout.NORTH, pnlButtons);
			    springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, pnlSegments);
			    pnlSegments.add(scrollPane);
			    
			    table = new JTable();
			    scrollPane.setViewportView(table);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setFont(new Font("Tahoma", Font.PLAIN, 12));
				table.setFillsViewportHeight(false);
				table.getSelectedRow();

				TableModel t = new DefaultTableModel(new String[] { "Name", "Latest", "Gold", "PB"},
						segments.size()) {
					/**
					 * 
					 */

					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] { false, true, true, true, true, true, true, true };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
				table.setModel(t);
				table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
				table.getColumnModel().getColumn(0).setPreferredWidth(10);
				table.getColumnModel().getColumn(1).setPreferredWidth(50);
				table.getColumnModel().getColumn(2).setPreferredWidth(10);
				table.getColumnModel().getColumn(3).setPreferredWidth(10);
				for (int i = 0; i < segments.size(); i++) {
					t.setValueAt(segments.get(i).getName(), i, 0);
					t.setValueAt(segments.get(i).getLatestRun(), i, 1);
					t.setValueAt(segments.get(i).getGoldRun(), i, 2);
					t.setValueAt(segments.get(i).getPBRun(), i, 3);
				}
			    
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		  
			btnSplit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!hasRan && !paused) {
						startTimer();
					} else {
						if(paused) {
							chronos.continueTimer();
							paused = false;
						} else {
							doASplit();
						}
					}
				}
			});
			
			btnPause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!paused){
						chronos.pauseTimer();
						paused = true;
					} else {
						chronos.continueTimer();
						paused = false;
					}
				}
			});
			
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chronos.pauseTimer();
					paused = true;
					totalTime.resetTimer();
					segmentTime.resetTimer();
				}
			});
	}
	
	private static void startTimer() {
		hasRan = true;
    	totalTime = new chronos();
    	segmentTime = new chronos();
    	timerRuntime();
	}
	
	private static void doASplit() {
		lastMi = segmentTime.getMilis();
		lastS = segmentTime.getSec();
		lastM = segmentTime.getMin();
		lastH = segmentTime.getHrs();

		segmentTime.resetTimer();
	}
	
	private static String toStringTimer(int hour, int minute, int second, int milisecond) {
		String result = "";
		String minute2;
		String second2;
		String milisecond2;
		if (minute < 10) {
			minute2 = "0" + minute;
		} else {
			minute2 = "" + minute;
		}
		
		if (second < 10) {
			second2 = "0" + second;
		} else {
			second2 = "" + second;
		}
		
		if (milisecond < 10) {
			milisecond2 = "0" + milisecond;
		} else {
			milisecond2 = "" + milisecond;
		}
		
		
		result = hour + ":" + minute2 + ":" + second2 + "." + milisecond2;
		return result;
	}
	
	private static void timerRuntime() {
		SwingWorker<Integer, Integer> timerRuntime = new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
            	while(hasRan) {
            		if(!paused) {
            			lblTimer.setText(toStringTimer(segmentTime.getHrs(),segmentTime.getMin(),segmentTime.getSec(),segmentTime.getMilis()));
            	
            		}
            	}	
            	return null;
            }
        };
        timerRuntime.execute();
	}
}
