import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class gui implements ActionListener {
    JTextField textField;
    JCheckBox checkBox;
    JTextArea textArea;

    public gui() {
        Button btn = new Button("Download");
        btn.addActionListener(this);
        textField = new JTextField(30);
        textArea = new JTextArea(5, 30);
        JTextArea textArea1 = new JTextArea("This program requires ffmpeg.org and youtube-dl.org");

        textArea.setEditable(false);
        textArea1.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        checkBox = new JCheckBox("audio-only (mp3)");


        JFrame frame = new JFrame("Youtube-dl-gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(textField);
        frame.add(checkBox);
        frame.add(btn);
        frame.add(scrollPane);
        frame.add(textArea1);
        frame.setLayout(new FlowLayout());
        frame.setSize(600, 200);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        new gui();
    }

    public void actionPerformed(ActionEvent e) {
        textArea.setText("Downloading...");
        Thread t = new Thread(() -> {
            try {
                String cmd = "";
                if (checkBox.isSelected()) {
                    cmd = "youtube-dl.exe " + textField.getText() + " -x --audio-format mp3";
                } else {
                    cmd = "youtube-dl.exe " + textField.getText();
                }
                String line;
                Process p = Runtime.getRuntime().exec(cmd);
                BufferedReader bri = new BufferedReader
                        (new InputStreamReader(p.getInputStream()));
                BufferedReader bre = new BufferedReader
                        (new InputStreamReader(p.getErrorStream()));
                while ((line = bri.readLine()) != null) {
                    System.out.println(line);
                    textArea.append(line + "\n");
                }
                bri.close();
                while ((line = bre.readLine()) != null) {
                    System.out.println(line);
                    textArea.append(line + "\n");
                }
                bre.close();
                p.waitFor();
                System.out.println("Done.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println(textField.getText());
        });
        t.start();
    }
}
