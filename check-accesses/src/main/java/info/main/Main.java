package info.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import info.checkdata.checkAA;

public class Main {

	static JFrame jframe;
	static String path1;

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				pathSetFrame();
			}
		});
	}

	static void pathSetFrame() {
		jframe = new JFrame("Provide path data.");

		JButton b1 = new JButton("Submit");
		JLabel l1 = new JLabel("Provide path to files");

		final JLabel l4 = new JLabel();
		final JTextField t1 = new JTextField();

		l4.setText("In progress");

		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				path1 = t1.getText();

				checkAA ca = new checkAA(path1);

				ca.mainCheck();

				l4.setText("Done");

			}
		});

		jframe.getContentPane().setLayout(new GridLayout(2, 2));
		jframe.add(l1);
		jframe.add(t1);
		jframe.add(b1);
		jframe.add(l4);

		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setPreferredSize(new Dimension(600, 100));

		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);

	}

}
