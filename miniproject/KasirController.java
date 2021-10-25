/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miniproject;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author RIDLO_SHUHARDI
 */
public class KasirController {

    private Kasir view;
    private JTextField inName, inPrice, inQty, inTotal, inPay, inChange, inFind;
    private JTextPane inTable;
    JTextPane textPane = new JTextPane();
    StyledDocument doc = (StyledDocument) textPane.getDocument();
    Style style = doc.addStyle("StyleName", null);
    
    private int id = 0;
    private String nama;
    private int harga, jumlah, total, bayar, kembali;

    public KasirController(Kasir view) {
        this.view = view;

        // Action
        this.view.getBtnHitung().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count();
            }
        });
        this.view.getBtnProses().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process();
            }
        });
        this.view.getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        this.view.getBtnOrder().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // command order();
                saveDatabase();
            }
        });
        this.view.getBtnOpen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDatabase();
            }
        });
        this.view.getBtnPrint().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print();
            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private void count() {
        inName = view.getTxtMenu();
        inPrice = view.getTxtHarga();
        inQty = view.getTxtJumlah();
        inTotal = view.getTxtTotal();
        inPay = view.getTxtBayar();
        inChange = view.getTxtKembali();

        nama = inName.getText();
        harga = Integer.parseInt(inPrice.getText());
        jumlah = Integer.parseInt(inQty.getText());
        total = Integer.parseInt(inPrice.getText()) * Integer.parseInt(inQty.getText());

        if (!(inPay.getText()).equals("")) {
            bayar = Integer.parseInt(inPay.getText());
            inTotal.setText(String.valueOf(total));
            if (bayar == total) {
                inChange.setText("0");
            } else if (bayar > total) {
                kembali = bayar - total;
                inChange.setText(String.valueOf(kembali));
            } else {
                inChange.setText("0");
                JOptionPane.showMessageDialog(null, "Check your money");
            }
        } else {
            inTotal.setText(String.valueOf(total));
        }
    }

    private void process() {
        try {
            inTable = view.getTxtPane();
            StyledDocument doc = inTable.getStyledDocument();
            doc.insertString(0, "", null);
            if (view.getTxtMenu().getText().isEmpty() || view.getTxtHarga().getText().isEmpty() || view.getTxtKembali().getText().isEmpty() || view.getTxtJumlah().getText().isEmpty() || view.getTxtBayar().getText().isEmpty() || view.getTxtTotal().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Null not allowed");
            } else {
                if (getId() == 0) {
                    id++;
                    StyleConstants.setForeground(style, Color.red);
                    doc.insertString(doc.getLength(), "ID\t MENU\t HARGA\t JUMLAH\t TOTAL\t BAYAR\t KEMBALI\n", style);
                    doc.insertString(doc.getLength(), String.format("[%d\t %s\t %d\t %d\t %d\t %d\t %d]\n", getId(), nama, harga, jumlah, total, bayar, kembali), null);
                } else {
                    id++;
                    doc.insertString(doc.getLength(), String.format("[%d\t %s\t %d\t %d\t %d\t %d\t %d]\n", getId(), nama, harga, jumlah, total, bayar, kembali), null);
                }
            }

        } catch (BadLocationException ex) {
            Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    private void clear() {
        view.getTxtHarga().setText(null);
        view.getTxtBayar().setText(null);
        view.getTxtJumlah().setText(null);
        view.getTxtKembali().setText(null);
        view.getTxtMenu().setText(null);
        view.getTxtTotal().setText(null);
        JOptionPane.showMessageDialog(null, "Succes Clear !");

    }

    private void openDatabase() {
        JFileChooser loadFile = view.getLoadFile();
        inTable = view.getTxtPane();
        inTable.setText("");
        StyledDocument doc = inTable.getStyledDocument();
        
        if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view))
        {
            File getFile = loadFile.getSelectedFile();
            BufferedReader reader = null;
            LineNumberReader lineReader = null;
            
            int numOfLine = 0, numOfWords = 0, numOfChars = 0;
            
             // helper
            int decimal;
            char ascii;
            String words[] = null;
             
            try {
                reader = new BufferedReader(new FileReader(getFile));
                lineReader = new LineNumberReader(new FileReader(getFile));
                
                String data = null;
                doc.insertString(0, "", null);
                while ((data = reader.readLine()) != null)
                {
                    doc.insertString(doc.getLength(), data+"\n", null);
                    words = data.split("\\s+");
                    numOfWords+=words.length;
                }
                
                 while ((decimal = lineReader.read()) != -1) {
                    ascii = (char) decimal;
                    if ((ascii != ' ') && (ascii != '\n')) {
                        numOfChars++;
                    }
                    if ((ascii == '[')) {
                        numOfLine++;
                    }
                 }
                 
                 JOptionPane.showMessageDialog(null, String.format("Berkas berhasil dibaca!"
                         + "\nJumlah baris: %d\n"
                         + "Jumlah Kata: %d\n"
                         + "Jumlah Karakter %d"
                         , numOfLine, numOfWords, numOfChars), "Informasi", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveDatabase() {
        JFileChooser loadFile = view.getLoadFile();
        inTable = view.getTxtPane();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view))
        {
            BufferedOutputStream writer = null;
            String path = null;
            try {
                String contents = inTable.getText();
                if (contents != null && !contents.isEmpty())
                {
                    writer = new BufferedOutputStream(new FileOutputStream(loadFile.getSelectedFile()));
                    java.io.File f = loadFile.getSelectedFile();
                    path = f.getPath();
                    writer.write(contents.getBytes());
                    JOptionPane.showMessageDialog(view, "Database Saved in \'" + path + "\'");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null)
                {
                    try {
                        writer.flush();
                        writer.close();
                        inTable.setText("");
                    } catch (IOException ex) {
                        Logger.getLogger(KasirController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    private void print() {
        StyledDocument doc = view.getTxtPane().getStyledDocument();
        inTable = view.getTxtPane();
        inFind = view.getTxtCari();
        LineNumberReader lineReader = null;
        
        if (!(inFind.getText().isEmpty()) || !(inFind.getText().equals("")))
        {
            try {

                lineReader = new LineNumberReader(new StringReader(inTable.getText()));
                String data = null;
                doc.insertString(0, "", null);

                String regex = "[0-9]";
                int decimal =0;
                char ascii;
                char words[] = new char[inTable.getText().length()];
                char arrAscii[] = new char[inTable.getText().length()];
                char arrAsciiHelper[] = new char[(inTable.getText()).length() +1];
                
                boolean gotcha = false;
                int i = 0;
                int j = 0;

                while ((decimal = lineReader.read()) != -1) {
                   ascii = (char) decimal;
                   
                   arrAscii[j] = (char) decimal;
                   arrAsciiHelper[j] = (char) decimal;
                   j++;
                }
                
                for (int l = 0; l < arrAscii.length; l++) {
                    
                    if ((arrAscii[l] == '[') && (String.valueOf(arrAsciiHelper[l+1]).matches(regex))) {
                        if ((inFind.getText()).equalsIgnoreCase(String.valueOf(arrAsciiHelper[l+1]))) {
                            gotcha = true;
                        }
                    }
                    
                    if (gotcha) {
                        if (arrAscii[l] == ']') {
                            break;
                        } else {
                            words[i] = arrAscii[l];
                            i++;
                        }
                    }
                }
                
                if (!gotcha) {
                    System.out.println("Data nya g ada mas");
                }

                BufferedWriter simpan = new BufferedWriter(new FileWriter("kuitansi.txt"));
                String hasil = "ID\t MENU\t HARGA\t JUMLAH\t TOTAL\t BAYAR\t KEMBALI\n" + new String(words);
                simpan.write(hasil);
                JOptionPane.showMessageDialog(null, "Receipt saved !");
                simpan.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(view, "Please put your id");
        }

    }
}
