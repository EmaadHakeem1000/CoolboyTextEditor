package MainFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.net.http.*;

public class MainFrame extends JFrame implements ActionListener{
    private JTextArea textArea;
    private JButton getData;
    private JTextArea dataLabel;

    public MainFrame (int width, int height, String title) {
        this.setSize(width, height);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(30, 30 , 30));

        textArea = new JTextArea();
        textArea.setBounds(0, 30, width, 40);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setCaretColor(Color.green);

        getData = new JButton("GET DATA");
        getData.setBounds(0, 100, 100, 40);
        getData.setBackground(Color.black);
        getData.setForeground(Color.green);
        getData.addActionListener(this);

        dataLabel = new JTextArea("hello world this is emaad");
        dataLabel.setBackground(Color.black);
        dataLabel.setForeground(Color.green);
        dataLabel.setBounds(0, 0, width-15, 300);

        JScrollPane scroll = new JScrollPane(dataLabel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(0, 200, this.getWidth()-15, this.getHeight()-200-45);

        this.getContentPane().addComponentListener(new ComponentAdapter () {
            public void componentResized(ComponentEvent ce) {
                Component c = (Component) ce.getSource();
                scroll.setBounds(scroll.getX(), scroll.getY(), c.getWidth() -15, c.getHeight()-200-45);
                textArea.setBounds(textArea.getX(), textArea.getY(), c.getWidth(), 40);
                dataLabel.append(" ");
                dataLabel.append("\b");
            }
        });

        this.add(textArea);
        this.add(scroll);
        this.add(getData);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getData) {
            var request = HttpRequest.newBuilder().GET().uri(URI.create(textArea.getText())).build();
            var client = HttpClient.newBuilder().build();

            try {
                var response =  client.send(request, HttpResponse.BodyHandlers.ofString());
                dataLabel.setText(response.body());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
