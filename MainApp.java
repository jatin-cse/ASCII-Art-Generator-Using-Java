import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

public class MainApp extends JFrame {

    JTextArea output;
    String currentASCII = "";

    ASCIIConverter converter = new ASCIIConverter();
    FileHandler fileHandler = new FileHandler();

    public MainApp() {

        setTitle("ASCII Art Generator");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        output = new JTextArea();
        output.setFont(new Font("Monospaced", Font.PLAIN, 6));
	
	output.setBackground(Color.BLACK);
	output.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(output);

        JButton chooseBtn = new JButton("Choose Image");
        JButton saveBtn = new JButton("Save ASCII");

        JPanel panel = new JPanel();
        panel.add(chooseBtn);
        panel.add(saveBtn);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // File chooser
        chooseBtn.addActionListener(e -> chooseImage());

        // Save file
        saveBtn.addActionListener(e -> saveASCII());

        // Drag & Drop
        output.setText("Drag & Drop Image Here OR Click 'Choose Image'");

        output.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            public boolean importData(TransferSupport support) {
                try {
                    List<File> files = (List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    processImage(files.get(0));
                    return true;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });

        setVisible(true);
    }

    void chooseImage() {
        try {
            JFileChooser fc = new JFileChooser();

            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                processImage(fc.getSelectedFile());
            }

        } catch (Exception e) {
            System.out.println("Error selecting file!");
        }
    }

    void processImage(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            currentASCII = converter.convertToASCII(img);
            output.setText(currentASCII);

        } catch (Exception e) {
            System.out.println("Error processing image!");
        }
    }

    void saveASCII() {
        if (currentASCII.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ASCII to save!");
            return;
        }

        fileHandler.saveToFile(currentASCII, "output.txt");
        JOptionPane.showMessageDialog(this, "Saved as output.txt");
    }

    public static void main(String[] args) {
        new MainApp();
    }
}