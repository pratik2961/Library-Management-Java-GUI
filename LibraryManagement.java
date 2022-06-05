import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LibraryManagement {

    public static void main(String[] args) {

        // database variable
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3307/librarymanagement";
        String DB_USER = "root";
        String DB_PASS = "";

        JFrame frame = new JFrame();
        frame.setTitle("Library Management System");
        frame.setBounds(300, 100, 10, 200);

        JPanel topPanel = new JPanel();
        JLabel titleOfTopPanel = new JLabel("Library Management System");

        // setting layout in topPanel
        topPanel.setLayout(new FlowLayout());

        // adding component in topPanel
        topPanel.add(titleOfTopPanel);

        JPanel middelPanel = new JPanel();
        JLabel titleOfMiddlePanel = new JLabel("Choose Services :) ");
        JRadioButton issueBook = new JRadioButton("Issue Book ");
        JRadioButton checkIssuedBook = new JRadioButton("Check Issued Book ");
        JRadioButton issuedByYou = new JRadioButton("Book issued by you ");
        JRadioButton returnBook = new JRadioButton("Return Book ");
        JRadioButton showAvailableBooks = new JRadioButton("Show Available Books ");
        JRadioButton donateBook = new JRadioButton("Donate Books ");
        JRadioButton checkDonatedBook = new JRadioButton("Check Donated Books ");

        ButtonGroup bg = new ButtonGroup();
        // setting layout in middelPanel
        middelPanel.setLayout(new GridLayout(8, 0));

        // add radio button in ButtonGroup
        bg.add(issueBook);
        bg.add(checkIssuedBook);
        bg.add(issuedByYou);
        bg.add(returnBook);
        bg.add(showAvailableBooks);
        bg.add(donateBook);
        bg.add(checkDonatedBook);

        // declaring bottom panel here
        JPanel bottomPanel = new JPanel();

        // adding action listener in radio buttons
        // issuedBook
        issueBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                bottomPanel.setLayout(new GridLayout(2, 0));
                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel title = new JLabel("ISSUE BOOK : ");
                bottomPanel.add(title);
                int countForGrid = 4;
                try {
                    Connection con = null;
                    Class.forName(JDBC_DRIVER);
                    con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                    String strQuery = "select id,name from books";
                    PreparedStatement query = con.prepareStatement(strQuery);
                    ResultSet result = query.executeQuery();

                    while (result.next()) {
                        countForGrid = countForGrid + 1;
                        String code = result.getString(1);
                        String bookName = result.getString(2);
                        String bookWithCode = code + " is for " + bookName + " Book ";
                        JLabel l = new JLabel(bookWithCode);
                        bottomPanel.add(l);
                    }

                    JLabel forName = new JLabel("Enter Your Name :");
                    JTextField name = new JTextField();

                    JLabel forBookCode = new JLabel("Enter Book code :");
                    JTextField bookCode = new JTextField();

                    JButton issueButton = new JButton("Issue Book");
                    bottomPanel.add(forName);
                    bottomPanel.add(name);
                    bottomPanel.add(forBookCode);
                    bottomPanel.add(bookCode);
                    bottomPanel.add(issueButton);

                    issueButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String getName = name.getText();
                            int getBookCode = Integer.parseInt(bookCode.getText());
                            try {
                                Connection con = null;
                                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                                String strQuery = "select id,name from books where id = ?";
                                PreparedStatement query = con.prepareStatement(strQuery);
                                query.setInt(1, getBookCode);
                                ResultSet result = query.executeQuery();
                                System.out.println("Out Result");

                                if (getName == "" || bookCode.getText() == "") {
                                    System.out.println("Something is empty");
                                } else {
                                    while (result.next()) {
                                        int id = Integer.parseInt(result.getString(1));
                                        String bookName = result.getString(2);
                                        System.out.println("id " + Integer.toString(id));
                                        if (id == getBookCode) {
                                            try {
                                                String strQuery1 = "INSERT INTO issuebook ( name, bookname) VALUES (?, ?)";
                                                PreparedStatement query1 = con.prepareStatement(strQuery1);
                                                String nameUpper = getName.toUpperCase();
                                                query1.setString(1, nameUpper);
                                                query1.setString(2, bookName);

                                                int affectedrows = query1.executeUpdate();

                                                String strQuery2 = "DELETE FROM `books` WHERE `books`.`id` = ?";
                                                PreparedStatement query2 = con.prepareStatement(strQuery2);
                                                query2.setInt(1, id);

                                                int affectedrows1 = query2.executeUpdate();

                                                name.setText("");
                                                bookCode.setText("");

                                            } catch (Exception e3) {
                                                System.out.println("Error from catch is yes here : " + e3);
                                            }
                                        } else {
                                            System.out.println("Somthing went wrong");
                                        }
                                    }
                                }

                            } catch (SQLException e2) {
                                System.out.println("Error from catch is  : " + e2);
                            }

                        }
                    });

                    countForGrid = countForGrid + 3;
                    bottomPanel.setLayout(new GridLayout(countForGrid, 0));

                } catch (Exception e1) {
                    System.out.println("Error from catch is : " + e1);
                }

                // bottomPanel.add(title);
                frame.add(bottomPanel, BorderLayout.SOUTH);

            }
        });

        // checkIssuedBook
        checkIssuedBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                bottomPanel.setLayout(new GridLayout(2, 0));
                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel title = new JLabel("CHECK ISSUED BOOKS : ");
                bottomPanel.add(title);
                JLabel space1 = new JLabel();
                bottomPanel.add(space1);

                int countForGrid = 3;

                try {
                    Class.forName(JDBC_DRIVER);
                    Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                    String steQuery = "select name ,bookname from issuebook";
                    PreparedStatement query = con.prepareStatement(steQuery);

                    ResultSet result = query.executeQuery();

                    while (result.next()) {
                        String name = result.getString(1);
                        String bookName = result.getString(2);

                        countForGrid = countForGrid + 1;

                        String str = bookName + " book is issued by " + name;

                        JLabel l = new JLabel(str);
                        bottomPanel.add(l);

                    }

                } catch (Exception e4) {
                    System.out.println("Error from catch is : " + e4);
                }
                bottomPanel.setLayout(new GridLayout(countForGrid, 0));
                frame.add(bottomPanel, BorderLayout.SOUTH);

            }
        });

        // issuedByYou
        issuedByYou.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                bottomPanel.setLayout(new GridLayout(2, 0));
                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel title = new JLabel("CHECK ISSUED BOOKS BY YOU : ");
                bottomPanel.add(title);

                JLabel lName = new JLabel("Enter your name : ");
                JTextField name = new JTextField();
                JButton check = new JButton("Check");
                bottomPanel.add(lName);
                bottomPanel.add(name);
                bottomPanel.add(check);
                int countForGrid = 5;
                bottomPanel.setLayout(new GridLayout(countForGrid, 0));
                check.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("issued by you");
                        JLabel l2 = new JLabel("BOOK IS ISSUED BY YOU");
                        bottomPanel.add(l2);
                        int countForGrid = 6;
                        try {

                            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                            String strQuery = "select name , bookname from issuebook where name = ?";
                            PreparedStatement query = con.prepareStatement(strQuery);
                            String capitalName = name.getText().toUpperCase();
                            query.setString(1, capitalName);
                            ResultSet result = query.executeQuery();

                            while (result.next()) {
                                String name = result.getString(1);
                                String bookName = result.getString(2);

                                countForGrid = countForGrid + 1;
                                bottomPanel.setLayout(new GridLayout(countForGrid, 0));
                                String label = bookName + " book is issued by " + name;
                                JLabel l = new JLabel(label);
                                bottomPanel.add(l);

                            }

                        } catch (Exception e4) {
                            System.out.println("Error from catch is : " + e4);
                        }

                    }

                });

                frame.add(bottomPanel, BorderLayout.SOUTH);

            }

        });

        // returnBook
        returnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel book1 = new JLabel("RETURN BOOK : ");
                bottomPanel.add(book1);
                int countForGrid = 7;

                JLabel l1 = new JLabel("Enter your name : ");
                JLabel l2 = new JLabel("Enter Your Book Name : ");

                JTextField name = new JTextField();
                JTextField bookName = new JTextField();

                bottomPanel.add(l1);
                bottomPanel.add(name);
                bottomPanel.add(l2);
                bottomPanel.add(bookName);

                JButton bookRetrun = new JButton("RETURN");
                bottomPanel.add(bookRetrun);
                bookRetrun.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String name1 = name.getText().toUpperCase();
                        String bookName1 = bookName.getText().toUpperCase();

                        try {
                            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                            String strQuery = "DELETE FROM issuebook WHERE name = ? and bookname = ?";
                            PreparedStatement query = con.prepareStatement(strQuery);
                            query.setString(1, name1);
                            query.setString(2, bookName1);

                            int affectedrows = query.executeUpdate();
                            System.out.println(affectedrows);
                            if (affectedrows > 0) {
                                System.out.println("inside affected rows");
                                String strQuery1 = "INSERT INTO books (name) VALUES (?)";
                                PreparedStatement query1 = con.prepareStatement(strQuery1);
                                query1.setString(1, bookName1);
                                int affectedrows1 = query1.executeUpdate();
                                name.setText("");
                                bookName.setText("");
                            }
                        } catch (Exception e5) {
                            System.out.println("Error from catch is : " + e5);
                        }

                    }
                });
                bottomPanel.setLayout(new GridLayout(countForGrid, 0));
                frame.add(bottomPanel, BorderLayout.SOUTH);
            }
        });

        // showAvailableBooks
        showAvailableBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                bottomPanel.setLayout(new GridLayout(2, 0));

                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel title = new JLabel("AVAILABLE BOOKS IS :");
                bottomPanel.add(title);
                JLabel space1 = new JLabel();
                bottomPanel.add(space1);
                int countForGrid = 3;
                try {
                    Connection con = null;
                    Class.forName(JDBC_DRIVER);
                    con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                    String strQuery = "select id,name from books";
                    PreparedStatement query = con.prepareStatement(strQuery);
                    ResultSet result = query.executeQuery();

                    while (result.next()) {
                        countForGrid = countForGrid + 1;
                        String code = result.getString(1);
                        String bookName = result.getString(2);
                        String bookWithCode = code + " is for " + bookName + " Book ";
                        JLabel l = new JLabel(bookWithCode);
                        bottomPanel.add(l);
                    }

                    bottomPanel.setLayout(new GridLayout(countForGrid, 0));

                } catch (Exception e1) {
                    System.out.println("Error from catch is : " + e1);
                }

                // bottomPanel.add(title);
                frame.add(bottomPanel, BorderLayout.SOUTH);
            }
        });

        // donateBook
        donateBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                bottomPanel.setLayout(new GridLayout(2, 0));

                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel title = new JLabel("DONATE BOOK HERE :");
                bottomPanel.add(title);

                JLabel l1 = new JLabel("Enter Your Name : ");
                JLabel l2 = new JLabel("Enter book name which you want to donate : ");

                JTextField name = new JTextField();
                JTextField donateBook = new JTextField();

                bottomPanel.add(l1);
                bottomPanel.add(name);
                bottomPanel.add(l2);
                bottomPanel.add(donateBook);

                JButton donate = new JButton("DONATE");
                bottomPanel.add(donate);

                donate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name1 = name.getText().toUpperCase();
                        String donateBook1 = donateBook.getText().toUpperCase();

                        try {
                            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                            String strQuery = "INSERT INTO donatebook (name, bookname) VALUES (?,?)";
                            PreparedStatement query = con.prepareStatement(strQuery);
                            query.setString(1, name1);
                            query.setString(2, donateBook1);

                            int affected = query.executeUpdate();

                            String strQuery1 = "INSERT INTO books (name) VALUES (?)";
                            PreparedStatement query1 = con.prepareStatement(strQuery1);
                            query1.setString(1, donateBook1);

                            int affected1 = query1.executeUpdate();
                            name.setText("");
                            donateBook.setText("");

                        } catch (Exception e4) {
                            System.out.println("Error from catch is : " + e4);
                        }

                    }
                });
                int countForGrid = 7;
                bottomPanel.setLayout(new GridLayout(countForGrid, 0));
                frame.add(bottomPanel, BorderLayout.SOUTH);

            }
        });

        // checkDonatedBook
        checkDonatedBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.removeAll();
                bottomPanel.setLayout(new GridLayout(2, 0));

                JLabel space = new JLabel();
                bottomPanel.add(space);
                JLabel title = new JLabel("DONATED BOOKS :");
                bottomPanel.add(title);
                int countForGrid = 2;
                try {
                    Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                    String strQuery = "select name , bookname from donatebook";
                    PreparedStatement query = con.prepareStatement(strQuery);
                    ResultSet result = query.executeQuery();

                    while (result.next()) {
                        String donateName = result.getString(1);
                        String donateBook = result.getString(2);
                        String str = donateBook + " book is donated by " + donateName;
                        JLabel l = new JLabel(str);
                        bottomPanel.add(l);
                        countForGrid = countForGrid + 1;
                    }

                } catch (Exception e4) {
                    System.out.println("Error from catch is : " + e4);
                }
                bottomPanel.setLayout(new GridLayout(countForGrid, 0));
                frame.add(bottomPanel, BorderLayout.SOUTH);

            }
        });

        // adding component in middlePanel
        middelPanel.add(titleOfMiddlePanel);
        middelPanel.add(issueBook);
        middelPanel.add(checkIssuedBook);
        middelPanel.add(issuedByYou);
        middelPanel.add(returnBook);
        middelPanel.add(showAvailableBooks);
        middelPanel.add(donateBook);
        middelPanel.add(checkDonatedBook);

        // for window closing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adding component
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middelPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}
