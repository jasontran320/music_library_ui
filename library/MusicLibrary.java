package library;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicLibrary extends JFrame {
    private JList<Song> songList;
    private DefaultListModel<Song> listModel;
    private JButton addButton, saveButton, deleteButton;
    private JTextArea notesTextArea;
    private JTextField titleTextField, artistTextField, runningTimeTextField;
    private JLabel statusLabel, titleLabel, artistLabel, shareLabel, categoryLabel, runningTimeLabel, notesLabel;
    private JSplitPane splitPane;
    private JPanel leftPanel, buttonPanel, deletePanel, rightPanel, sharePanel, categoryPanel, runningTimePanel, notesPanel, buttonPanel2;
    private JScrollPane scrollPane, notesScrollPane;
    private JCheckBox friendsCheckbox, familyCheckbox, coworkersCheckbox;
    private JComboBox<String> categoryComboBox;

    public MusicLibrary() {
        setTitle("Jason Tran - 88486774");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5);
        add(splitPane, BorderLayout.CENTER);

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 600)); // Set preferred size to half the screen
        splitPane.setLeftComponent(leftPanel);

        // Create a list model for the song list
        listModel = new DefaultListModel<Song>();
        songList = new JList<>(listModel);
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songList.addListSelectionListener(new ListSelectionHandler());
        scrollPane = new JScrollPane(songList);
        leftPanel.add(scrollPane, BorderLayout.CENTER);


        buttonPanel = new JPanel(new GridLayout(1, 2)); // Use GridLayout with 1 row and 2 columns
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        deletePanel = new JPanel(new FlowLayout());
        buttonPanel.add(deletePanel);

        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(95, 23));
        deleteButton.addActionListener(new ButtonClickListener());
        deletePanel.add(deleteButton, BorderLayout.CENTER);

        statusLabel = new JLabel("Total Running Time: 0.0");
        buttonPanel.add(statusLabel);

        rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout with left alignment and padding
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to the top margin

        titleLabel = new JLabel("Title:");
        rightPanel.add(titleLabel);

        titleTextField = new JTextField();
        titleTextField.setPreferredSize(new Dimension(270, 25)); // Set preferred size for the text field
        rightPanel.add(titleTextField);

        rightPanel.add(Box.createVerticalStrut(40)); // Adds a vertical space of 10 pixels

        artistLabel = new JLabel("Artist:");
        rightPanel.add(artistLabel);

        artistTextField = new JTextField();
        artistTextField.setPreferredSize(new Dimension(260, 25));
        rightPanel.add(artistTextField);


        rightPanel.add(Box.createVerticalStrut(40)); // Adds a vertical space of 10 pixels
        sharePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10)); // Use FlowLayout with left alignment and padding
        shareLabel = new JLabel("Share With:");
        sharePanel.add(shareLabel);
        friendsCheckbox = new JCheckBox("Friends");
        familyCheckbox = new JCheckBox("Family");
        coworkersCheckbox = new JCheckBox("Coworkers");
        sharePanel.add(friendsCheckbox);
        sharePanel.add(familyCheckbox);
        sharePanel.add(coworkersCheckbox);
        rightPanel.add(sharePanel);

        rightPanel.add(Box.createVerticalStrut(40)); // Adds a vertical space of 10 pixels

        categoryPanel = new JPanel(new FlowLayout());

        categoryLabel = new JLabel("Category:");
        String[] categories = {"Work", "Exercise", "Party", "Sleep", "Driving", "Other"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setSelectedItem("Work");
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);
        rightPanel.add(categoryPanel);

        rightPanel.add(Box.createVerticalStrut(40));
        runningTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        runningTimeLabel = new JLabel("Running Time:");
        runningTimePanel.add(runningTimeLabel);

        runningTimeTextField = new JTextField(10); // Set the preferred width
        runningTimeTextField.setText("0.0");
        runningTimePanel.add(runningTimeTextField);
        rightPanel.add(runningTimePanel);

        rightPanel.add(Box.createVerticalStrut(40)); // Adds a vertical space of 10 pixels
        notesPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        notesLabel = new JLabel("Notes:");
        notesPanel.add(notesLabel);

        notesTextArea = new JTextArea(3, 30);
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);

        notesScrollPane = new JScrollPane(notesTextArea);
        notesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Remove horizontal scroll bar
        notesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        notesPanel.add(notesScrollPane);
        rightPanel.add(notesPanel);

// Adjust the scrollbar width
        JScrollBar scrollBar = notesScrollPane.getVerticalScrollBar();
        scrollBar.setPreferredSize(new Dimension(10, scrollBar.getHeight())); // Adjust the width as needed



        rightPanel.add(Box.createVerticalStrut(40)); // Adds a vertical space of 10 pixels
        buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));

        saveButton = new JButton("Save Song");
        saveButton.addActionListener(new ButtonClickListener());
        buttonPanel2.add(saveButton);

        addButton = new JButton("New Song");
        addButton.addActionListener(new ButtonClickListener());
        buttonPanel2.add(addButton);
        rightPanel.add(buttonPanel2);


        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                clearInputFields();
            } else if (e.getSource() == saveButton) {
                int selectedIndex = songList.getSelectedIndex();
                if (selectedIndex != -1 && validateInput(selectedIndex)) {
                    modifySong(selectedIndex);
                    JOptionPane.showMessageDialog(MusicLibrary.this, "Song Saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (validateInput(selectedIndex)) {
                    saveSong();
                    JOptionPane.showMessageDialog(MusicLibrary.this, "Song Saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    if (titleTextField.getText().trim().isEmpty()) titleTextField.setText("");
                    if (artistTextField.getText().trim().isEmpty()) artistTextField.setText("");
                    JOptionPane.showMessageDialog(MusicLibrary.this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == deleteButton) {
                int selectedIndex = songList.getSelectedIndex();
                if (selectedIndex != -1) {
                    deleteSong(selectedIndex);
                    clearInputFields();
                }
            }
        }
    }

    // Define a new inner class that implements ListSelectionListener
    private class ListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = songList.getSelectedIndex();
                if (selectedIndex != -1) {
                    fill_InputFields(selectedIndex);
                }
            }
        }
    }

    public boolean isTitleUnique(String title, int index) {
        for (int i = 0; i < listModel.getSize(); i++) {
            if (i != index) {
                Song song = listModel.getElementAt(i);
                if (song.getTitle().equals(title)) {
                    return false; // Title already exists
                }
            }
        }
        return true; // Title is unique
    }

    public String total_running_time() {
        double count = 0;
        for (int i = 0; i < listModel.getSize(); i++) {
            Song song = listModel.getElementAt(i);
            count += song.run_time;
        }
        String formatted = String.format("%.2f", count);
        if (formatted.endsWith("0")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        return formatted;
    }

    public boolean isValidDouble(String str) {
        try {
            double value = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void clearInputFields() {
        songList.clearSelection();
        titleTextField.setText("");
        artistTextField.setText("");
        friendsCheckbox.setSelected(false);
        familyCheckbox.setSelected(false);
        coworkersCheckbox.setSelected(false);
        categoryComboBox.setSelectedItem("Work");
        runningTimeTextField.setText("0.0");
        notesTextArea.setText("");
    }

    private void fill_InputFields(int index) {
        Song selectedSong = listModel.getElementAt(index);
        titleTextField.setText(selectedSong.title);
        artistTextField.setText(selectedSong.artist);
        friendsCheckbox.setSelected(selectedSong.share_privelages[0]);
        familyCheckbox.setSelected(selectedSong.share_privelages[1]);
        coworkersCheckbox.setSelected(selectedSong.share_privelages[2]);
        categoryComboBox.setSelectedItem(selectedSong.category);
        runningTimeTextField.setText(String.valueOf(selectedSong.run_time));
        notesTextArea.setText(selectedSong.notes);
    }

    private boolean validateInput(int index) {
        return !titleTextField.getText().trim().isEmpty() && isTitleUnique(titleTextField.getText(), index) && !artistTextField.getText().trim().isEmpty() && isValidDouble(runningTimeTextField.getText());
    }

    private void saveSong() {
        Song newSong = new Song(titleTextField.getText(), artistTextField.getText(),
                (String) categoryComboBox.getSelectedItem(), notesTextArea.getText(),
                new boolean[] {friendsCheckbox.isSelected(), familyCheckbox.isSelected(), coworkersCheckbox.isSelected()}, Double.parseDouble(runningTimeTextField.getText()));
        int index = 0;
        while (index < listModel.size() && listModel.getElementAt(index).getTitle().compareTo(newSong.getTitle()) < 0) {
            index++;
        }
        listModel.insertElementAt(newSong, index);
        songList.setSelectedIndex(index);
        statusLabel.setText(String.format("Total Running Time: %s", total_running_time()));
    }

    private void modifySong2(int selectedIndex) {
        if (selectedIndex != -1) {
            Song updated_song = new Song(titleTextField.getText(), artistTextField.getText(),
                    (String) categoryComboBox.getSelectedItem(), notesTextArea.getText(),
                    new boolean[] {friendsCheckbox.isSelected(), familyCheckbox.isSelected(), coworkersCheckbox.isSelected()}, Double.parseDouble(runningTimeTextField.getText()));
            listModel.setElementAt(updated_song, selectedIndex);
            songList.repaint();
        }
        statusLabel.setText(String.format("Total Running Time: %s", total_running_time()));
    }

    private void modifySong(int selectedIndex) {
        if (selectedIndex != -1) {
            listModel.removeElementAt(selectedIndex);
            saveSong();
        }
    }

    private void deleteSong(int index) {
        if (index != -1) {
            listModel.removeElementAt(index);
            statusLabel.setText("Song deleted!");
        }
        statusLabel.setText(String.format("Total Running Time: %s", total_running_time()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicLibrary());
    }
}
