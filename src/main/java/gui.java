import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class gui implements ActionListener {
    JTextField textField;
    JCheckBox checkBox;
    JTextArea textArea;

    public gui(){
        Panel pnl = new Panel();
        Button btn = new Button("Download");
        btn.addActionListener(this);
        textField = new JTextField(30);
        textArea = new JTextArea(5,30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        checkBox = new JCheckBox("audio-only (mp3)");
        pnl.add(textField);
        pnl.add(checkBox);
        pnl.add(btn);
        pnl.add(scrollPane);

        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pnl, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String args[])  {
        new gui();
    }

    public void actionPerformed(ActionEvent e) {
        textArea.setText("Downloading...");
        try {
            String cmd = "";
            if(checkBox.isSelected()){
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
    }
}
