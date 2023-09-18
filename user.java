import java.awt.*;
import java.util.jar.JarEntry;
import java.awt.event.*;  
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener; 






public class project {  
public static void main(String[] args) {  







        JFrame f=new JFrame();
        
        JLabel l = new JLabel("Search :");
        l.setBounds(10, 20, 50, 40);

        JTextField ta = new JTextField();
        ta.setBounds(60, 20, 400 ,40);
        
        JButton b=new JButton(new ImageIcon("C:\\Users\\Admin\\Desktop\\java\\icon2.png"));
        b.setBounds(480,20,60, 40);

        
        

        JLabel selectedProduct = new JLabel("Selected Product");
        selectedProduct.setBounds(420, 80, 150, 20);

        JTextField selectedProductText = new JTextField();
        selectedProductText.setText("ProductName");
        selectedProductText.setEnabled(false);
        selectedProductText.setForeground(Color.black);
        selectedProductText.setBounds(420, 100, 150, 30);
        
        JLabel Available = new JLabel("Available Quantity");
        Available.setBounds(420, 130, 150, 20);

        JTextField AvailableText = new JTextField();
        AvailableText.setText("Availiable Quantity");
        AvailableText.setEnabled(false);
        AvailableText.setBounds(420, 150, 150, 30);

        JLabel price = new JLabel("Price");
        price.setBounds(420, 180, 150, 20);

        JTextField priceField = new JTextField();
        priceField.setText("Price");
        priceField.setEnabled(false);
        priceField.setBounds(420, 200, 150, 30);


        JLabel quantity = new JLabel("Enter Quantity");
        quantity.setBounds(420, 230, 150, 20);

        JTextField quantityField = new JTextField();
        quantityField.setText("");
        quantityField.setEnabled(true);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        quantityField.setBounds(420, 250, 150, 30);
        String[][] rec = {
            { "001", "david", "90" },
            { "002", "steve", "80" },
            { "003", "yuvraj", "IND" },
            { "004", "kane", "NZ" },
            { "005", "ben", "ENG" },
            { "006", "eion", "ENG" },
            { "007", "miller", "SA" },
            { "008", "rohit", "IND" }
         };
         String[] header = { "Id", "Player", "Team" };
        //  JTable table = new JTable(rec, header);
        final DefaultListModel<String> l1 = new DefaultListModel<>();  
        for (int i = 0; i < rec.length; i++) {
                l1.addElement("Product name :   "+rec[i][0]+"      Quantity:    "+rec[i][1]+"       Price:   "+rec[i][2]);
        } 
        

        final JList<String> list2 = new JList<>(l1);  
        list2.setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // list2.setBounds(10,90,400,500);  

        JScrollPane scrollPane = new JScrollPane(list2);
        scrollPane.setBounds(10,90,400,200);  
         
        JLabel carLabel = new JLabel("CART :");
        carLabel.setBounds(10,300,400,20);

        final DefaultListModel<String> l2 = new DefaultListModel<>();  
        
        

        final JList<String> cartItem = new JList<>(l2);
        cartItem.setBounds(10,330,400,200);
       
        JButton additem =new JButton("Add");
        additem.setBounds(420,370,150, 40);

        JButton addtocart=new JButton(new ImageIcon("C:\\Users\\Admin\\Desktop\\java\\cart.png"));
        addtocart.setBounds(420,420,150, 40);

         
        // f.add(tb);
        f.add(scrollPane);
        f.add(l);
        f.add(b);
        f.add(ta);
        f.add(selectedProduct);
        f.add(selectedProductText);
        f.add(Available);
        f.add(AvailableText);
        f.add(price);
        f.add(priceField);
        f.add(quantity);
        f.add(quantityField);
        f.add(addtocart);
        f.add(carLabel);
        f.add(cartItem);
        f.add(additem);
        f.setSize(600,600);
        f.setLayout(null);
        f.setVisible(true);


        addtocart.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                        if(quantityField.getText().equals("")){
                                JFrame addError = new JFrame();
                                addError.setSize(300, 100);
                                addError.setVisible(true);

                                JLabel select = new JLabel("Please enter required quantity",SwingConstants.CENTER);
                                select.setBounds(50,20,250,20);

                                
                                addError.add(select);
                                addError.setLocationRelativeTo(null);
                        }
                        else{
                                l2.addElement(selectedProductText.getText()+" "+"Price per unit :"+ priceField.getText()+" Total Price:"+ Integer.parseInt(priceField.getText())*Integer.parseInt(quantityField.getText()));
                        }
                }
        });


        additem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                System.out.println(list2.getSelectedValuesList());
                if(list2.getSelectedValuesList().size() == 0){
                        JFrame addError = new JFrame();
                        addError.setSize(300, 100);
                        addError.setVisible(true);

                        JLabel select = new JLabel("Please select any product to ADD",SwingConstants.CENTER);
                        select.setBounds(50,20,250,20);

                        
                        addError.add(select);
                        addError.setLocationRelativeTo(null);  
                        


                }
                else{
                        int index = list2.getSelectedIndex();
                        selectedProductText.setText(String.valueOf(rec[index][0]));
                        AvailableText.setText(String.valueOf(rec[index][1]));
                        priceField.setText(String.valueOf(rec[index][2]));     
                }
        }});
        b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                        String search = ta.getText();
                        search.toLowerCase();
                        int count = 0;
                        for(int i=0;i<rec.length;i++){
                                // System.out.println(rec[i][1]);
                                if(rec[i][1].contains(search)){
                                        // System.out.println("Hi");
                                        count++;
                                }
                        }

                        int[] select = new int[count];
                        for(int i=0;i<rec.length;i++){
                                // System.out.println(rec[i][1]);
                                if(rec[i][1].contains(search)){
                                        count--;
                                        // System.out.println("Hi");
                                        select[count] = i;
                                }
                        }
                        list2.setSelectedIndices(select);
                        
                        
                }
        });  
}  
}

