package com.gmail.assamoa.MemGraph;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

class HelloSwing extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4464998464680176802L;

	public HelloSwing(String title) {
		super(title);
		display();
	}

	public void display() {
		Container cpane;
		JMenuBar mb = new JMenuBar();
		JMenu menu;
		JMenuItem open = new JMenuItem("Open");
		open.setActionCommand("o");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("'Open' clicked");
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					BMemParser parser = new BMemParser();
					FileReader fr = null;
					BufferedReader br = null;

					try {
						File f = chooser.getSelectedFile();
						fr = new FileReader(f);
						br = new BufferedReader(fr);
						String line = "";
						DefaultCategoryDataset dataset = new DefaultCategoryDataset();

						int count = 1;
						while ((line = br.readLine()) != null) {
							String free = parser.getFreeBMEM(line);
							if (free != null) {
								try {
									int freeMem = Integer.parseInt(free);
									// TODO: add freeMem to draw Graph
									dataset.addValue(freeMem, "Free BMem", "" + count++ );

								} catch (Exception ex) {
								}
							}
						}
						JFreeChart chart = ChartFactory.createLineChart("MEM", "time", "byte", dataset);
						chart.setBackgroundPaint(java.awt.Color.white);
						chart.setTitle(f.getName());
						File outFile = new File(f.getName()+".jpg");
						ChartUtilities.saveChartAsPNG(outFile, chart, 1280, 720);
						System.out.println("DONE:" + count + " memory logs");
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						try {
							br.close();
						} catch (Exception ex) {
						}
						try {
							fr.close();
						} catch (Exception ex) {
						}
					}
				}
			}
		});

		menu = new JMenu("File");
		menu.add(open);

		menu.addSeparator();

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("'Exit' clicked");
				System.exit(0);
			}
		});
		menu.add(exit);
		mb.add(menu);
		setJMenuBar(mb);
		// cpane = getContentPane();
		// cpane.setLayout(new BorderLayout());
		// cpane.add(new JButton("¾È³ç, ½ºÀ®"), BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		HelloSwing hs = new HelloSwing("BMem Graph Painter");
		hs.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		hs.pack();
		hs.setSize(300, 200);
		hs.setVisible(true);
	}
}
