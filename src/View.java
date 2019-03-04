import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

class View extends JFrame {
    private JTextArea corruptedArea;
    private JTextArea fixedArea;
    private JButton loadCorrupted;
    private JButton loadInstruction;
    private JButton saveFixed;
    private JButton startFixing;

    View() {
        super("Binary changer");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        initComponents();
    }

    private void initComponents() {
        loadCorrupted = new JButton("Otwórz plik");
        loadInstruction = new JButton("Wczytaj instrukcje");
        startFixing = new JButton("Napraw");
        saveFixed = new JButton("Zapisz");

        JPanel toolbox = new JPanel();
        toolbox.add(loadCorrupted);
        toolbox.add(loadInstruction);
        toolbox.add(startFixing);
        toolbox.add(saveFixed);
        this.add(toolbox);

        int rows = 30;
        int columns = 36;
        corruptedArea = new JTextArea(rows, columns);
        fixedArea = new JTextArea(rows, columns);

        corruptedArea.setEditable(false);
        fixedArea.setEditable(false);

        JScrollPane corruptedScroll = new JScrollPane(corruptedArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane fixedScroll = new JScrollPane(fixedArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel textbox = new JPanel();
        textbox.add(corruptedScroll);
        textbox.add(fixedScroll);
        add(textbox);
    }

    void setCorruptedValue(Hex value) {
        corruptedArea.setText(value.toString());
    }

    void setFixedValue(Hex value) {
        fixedArea.setText(value.toString());
    }

    void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    String savePathBin() {
        File currentDir = new File(System.getProperty("user.dir"));
        JFileChooser save = new JFileChooser(currentDir.toString());
        String path = null;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("bin", "bin");
        save.setFileFilter(filter);
        int result = save.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            path = save.getSelectedFile().getPath();
            if(!path.substring(path.length()-4).equals("."+"bin")) {
                path += "."+"bin";
            }
        }
        return path;
    }

    String loadPathBin() {
        File currentDir = new File(System.getProperty("user.dir"));
        JFileChooser open = new JFileChooser(currentDir.toString());
        String path = null;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("bin", "bin");
        open.setFileFilter(filter);
        int result = open.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            path = open.getSelectedFile().getPath();
        }
        return path;
    }

    String loadPathInstruction() {
        File currentDir = new File(System.getProperty("user.dir"));
        JFileChooser open = new JFileChooser(currentDir.toString());
        open.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String path = null;
        int result = open.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            path = open.getSelectedFile().getPath();
        }
        return path;
    }

    void initButtons(Controller controller) {
        loadCorrupted.addActionListener(e -> controller.loadCorruptedListener());
        loadInstruction.addActionListener(e -> controller.loadInstructionListener());
        startFixing.addActionListener(e -> controller.startFixListener());
        saveFixed.addActionListener(e -> controller.saveFixedListener());
    }

    ArrayList<FindedPosition> fixConflict(ArrayList<FindedPosition> conflictList) {
        ConflictDialog conflictDialog = new ConflictDialog(this, conflictList);
        System.out.println(conflictDialog.getConflictList());
        return conflictDialog.getConflictList();
    }





}
