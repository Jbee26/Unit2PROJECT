import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Window implements ActionListener {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta; //typing area
    private JFrame jf;
    private JLabel ljf;
    private JTextArea ta3;
    private JScrollPane sp;
    private JPanel CtrlP;
    private JPanel CtrlP2;

    private int WIDTH = 800;
    private int HEIGHT = 700;
    private JButton B1;
    private JButton B2;
    private JButton B3;
    private JButton B4;
    private ImageIcon IC;


    public Window() {
        prepareGUI();
    }


    public static void main(String[] args) {
        Window E1 = new Window();

    }

    private void prepareGUI() {
        mainFrame = new JFrame("Pokedex");
        CtrlP = new JPanel();
        CtrlP2 = new JPanel();
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(3, 1));
        CtrlP.setLayout(new GridLayout(2, 1));
        CtrlP2.setLayout(new GridLayout(2, 1));



        B1 = new JButton("ENTER");
        B2 = new JButton("CLEAR");
        B3 = new JButton("SKATER");
        B4 = new JButton("GOALIE");


        ta = new JTextArea("ENTER POKEMON HERE");
        ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
        ta.setBorder(new LineBorder(Color.BLACK));

        ta3 = new JTextArea();
        ta3.setBorder(new LineBorder(Color.BLACK));
        sp = new JScrollPane(ta3);
        B1.setForeground(Color.green);
        B2.setForeground(Color.red);
        Font f = new Font("Chalkboard", Font.ITALIC, 15);
        Font f2 = new Font("Chalkboard", Font.ITALIC, 12);
        ta.setFont(f);
        ta3.setEditable(false);

        B1.setFont(f);
        B2.setFont(f);

        JFrame jf = new JFrame();
        ImageIcon IC = new ImageIcon("Ditto-Anime.avif");
        jf.add(new JLabel(IC));
        ljf = new JLabel();
        jf.pack();
        jf.setVisible(true);


        mainFrame.add(CtrlP);
        mainFrame.add(CtrlP2);


        mainFrame.add(ta);


        mainFrame.add(sp);

        mainFrame.add(ljf);


        CtrlP.add(B1);
        CtrlP.add(B2);
        CtrlP2.add(B3);
        CtrlP2.add(B4);


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


        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);




        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());


        B1.setActionCommand("ENTER");
        B2.setActionCommand("CLEAR");


        B1.addActionListener(new ButtonClickListener());
        B2.addActionListener(new ButtonClickListener());


        mainFrame.setVisible(true);


    }


    public class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("ENTER")) {


                try {
                    pull();
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                //  AddImage();





            } else if (command.equals("CLEAR")) {

                ta.setText("ENTER POKEMON HERE");
//                ta2.setText("");
                ta3.setText("");


            }
        }
    }


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


//    public class ReadJson {
//        public void main(String args[]) throws ParseException {
//            // In java JSONObject is used to create JSON object
//            // which is a subclass of java.util.HashMap.
//
//            JSONObject file = new JSONObject();
//            file.put("Full Name", "Ritu Sharma");
//            file.put("Roll No.", 1704310046);
//            file.put("Tution Fees", 65400);
//
//
//            // To print in JSON format.
//            System.out.print(file.get("Tution Fees"));
//            ReadJson readingIsWhat = new ReadJson();
//
//        }
//
//        public ReadJson() {
//            try {
//                pull();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }

    public void pull() throws ParseException {
        String output = "abc";
        String totlaJson = "";


        try {

        

            URL url = new URL( "https://api-web.nhle.com/v1/skater-stats-leaders/20222023/2?categories=assists&limit=3");
            URL url2 = new URL( "https://api-web.nhle.com/v1/goalie-stats-leaders/20232024/2?categories=wins&limit=3");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);



        try {


            //String name = (String)jsonObject.get("name");
            //  String mass = (String)jsonObject.get("mass");
            //  String eColor = (String)jsonObject.get("eye_color");
            //String bYear = (String)jsonObject.get("birth_year");
            //JSONArray starships = (JSONArray)jsonObject.get("starships");


            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("points");
            org.json.simple.JSONArray msg2 = (org.json.simple.JSONArray) jsonObject.get("goals");
            org.json.simple.JSONArray msg3 = (org.json.simple.JSONArray) jsonObject.get("assists");

            int n = msg.size(); //(msg).length();
            int n2 = msg2.size();
            int n3 = msg3.size();


            for (int a = 0; a < n2; ++a) {
                JSONObject test2 = (JSONObject) msg2.get(a);
                System.out.println(test2);
                String title = (String) test2.get("name");
                System.out.println(title);
                ta3.append("Who's that Pokemon? Its " + title + "\n");

//
            }

            for (int i = 0; i < n; ++i) {
                JSONObject test = (JSONObject) msg.get(i);
                System.out.println(test);
                // System.out.println(person.getInt("key"));
                JSONObject name1 = (JSONObject) test.get("ability");
                System.out.println(name1);
                String abName = (String) name1.get("name");
                System.out.println(abName);
                ta3.append("They can use " + abName +"\n");
            }



            for (int l = 0; l < n3; ++l) {
                JSONObject test3 = (JSONObject) msg3.get(l);
                System.out.println(test3);
                JSONObject version = (JSONObject) test3.get("version");
                System.out.println(version);
                String vName = (String) version.get("name");
                System.out.println(vName);
                ta3.append("Appeared in " + vName +"\n");
            }


            // System.out.println(mass);
            //  System.out.println(eColor);
            // System.out.println(bYear);
            //  for (int i = 0; i < starships.size(); i++) {
            // System.out.println(starships.get(i));

            // }
//            for (int i = 0; i < ability.size(); i++) {
//                 System.out.println(ability.get(i));
//                 }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    //Ask Mx. Bradford how to add image!
    public void AddImage(){
        try {
            BufferedImage image = ImageIO.read(new File("download.jpeg"));
            if(!(image == null)) {
                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(WIDTH / 2, HEIGHT / 2, Image.SCALE_SMOOTH));
                ljf = new JLabel(imageIcon);
                mainFrame.removeAll();
                mainFrame.add(ljf);
            }

        } catch (IOException p) {
            p.printStackTrace();
        }


    }



}



