import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SwingControlDemo implements ActionListener {
    private static JFrame mainFrame;
    private static JLabel statusLabel;
    private static JLabel imageLabel;
    private static JPanel imagePanel;
    private JPanel searchPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta; //typing area
    private int WIDTH = 800;
    private int HEIGHT = 700;


    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) throws IOException {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.addImage();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Layout Images are awesome");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());

        //menu at top
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);
        //end menu at top

        ta = new JTextArea("url: ");
        ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
        mainFrame.add(mb);  //add menu bar

        mainFrame.setJMenuBar(mb); //set menu bar

        //  statusLabel = new JLabel("Label", JLabel.CENTER);
        //   statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        imagePanel = new JPanel();
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JButton okButton = new JButton("OK!!!!!!!!!");
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ButtonClickListener());
        searchPanel.add(okButton, BorderLayout.EAST);

        searchPanel.add(ta, BorderLayout.CENTER);//add typing area
        //controlPanel.setLayout(); //set the layout of the pannel

        mainFrame.add(searchPanel, BorderLayout.NORTH);
        mainFrame.setVisible(true);
    }

    private static void addImage() throws IOException {
        try {
            String path = "";
//            if (!ta.getText().contains("http")) {
//                path = "https://pettownsendvet.com/wp-content/uploads/2023/01/iStock-1052880600-2048x1365.jpg";
//            } else {
//                path = ta.getText();
//                if (path.contains("url")) {
//                    path = path.substring(path.indexOf("http"));
//                }
//            }


            URL url = new URL(path);
            BufferedImage ErrorImage = ImageIO.read(new File("bruins.png"));
            BufferedImage inputImageBuff = ImageIO.read(url.openStream());


            ImageIcon inputImage;
            if (inputImageBuff != null) {
                inputImage = new ImageIcon(inputImageBuff.getScaledInstance(800, 700, Image.SCALE_SMOOTH));
                // = new JLabel();
                if (inputImage != null) {
                    imageLabel = new JLabel(inputImage);
                } else {
                    imageLabel =new JLabel(new ImageIcon(ErrorImage.getScaledInstance(800, 589, Image.SCALE_SMOOTH)));

                }
                imagePanel.removeAll();
                imagePanel.repaint();

                imagePanel.add(imageLabel);
                mainFrame.add(imagePanel, BorderLayout.CENTER);

            }
            else{
                imageLabel =new JLabel(new ImageIcon(ErrorImage.getScaledInstance(800, 589, Image.SCALE_SMOOTH)));

            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("sadness");
            BufferedImage ErrorImage = ImageIO.read(new File("bruins.jpg"));
            JLabel imageLabel = new JLabel(new ImageIcon(ErrorImage.getScaledInstance(800, 589, Image.SCALE_SMOOTH)));

            imagePanel.removeAll();
            imagePanel.repaint();
            imagePanel.add(imageLabel);
            mainFrame.add(imagePanel);

        }

//        JButton submitButton = new JButton("Submit");
//        JButton cancelButton = new JButton("Cancel");
//
//        submitButton.setActionCommand("Submit");
//        cancelButton.setActionCommand("Cancel");
//
//        submitButton.addActionListener(new ButtonClickListener());
//        cancelButton.addActionListener(new ButtonClickListener());
//
//        controlPanel.add(okButton, BorderLayout.EAST);
//        controlPanel.add(submitButton, BorderLayout.CENTER);
//        controlPanel.add(cancelButton, BorderLayout.WEST);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
    }

    static class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("OK")) {
                try {
                    imagePanel.removeAll();
                    addImage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                 statusLabel.setText("Ok Button clicked.");
            }
        }
    }
}