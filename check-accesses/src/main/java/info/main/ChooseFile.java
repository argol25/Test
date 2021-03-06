package info.main;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import info.checkdata.checkAA;

public class ChooseFile extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static private String path;
	JButton opButton;
	JButton stButton;
	JTextArea log;
	JFileChooser fc;

	public ChooseFile() {
		super(new BorderLayout());

		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create a file chooser
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		opButton = new JButton("Select path to files...");
		opButton.addActionListener(this);
		
		stButton = new JButton("Start checking...");
		stButton.addActionListener(this);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(opButton);
		buttonPanel.add(stButton);

		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open button or start button action
		if (e.getSource() == opButton) {
			int returnVal = fc.showOpenDialog(ChooseFile.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();

				log.append("Selecting: " + file.getPath() + "\n");
				path = file.getPath();
			} else {
				log.append("Cancelled by user." + "\n");
			}
			log.setCaretPosition(log.getDocument().getLength());
		} else if(e.getSource() == stButton){
			
			
			if(path != null){
				log.append("Program started.");
				checkAA ca = new checkAA(path);
				ca.mainCheck();
				
				log.append("Program finished successfully." + "\n");
			} else
				log.append("Please select path to the files!" + "\n");
		}
	}

	private static void SelectGUI() {
		// Create and set up the window
		JFrame frame = new JFrame("Select file");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window
		frame.add(new ChooseFile());

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SelectGUI();
			}
		});
	}
}
