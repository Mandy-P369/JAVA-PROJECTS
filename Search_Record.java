package wms;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Search_Record extends javax.swing.JInternalFrame {

    Connect c;
    ResultSet rs;
    Statement s1;

    public Search_Record() {
        initComponents();
        c = new Connect();
        c.getConnect();
        jPanel3.setVisible(false);
        loadData("");
//        searchThroughProfession("");
        searchProfession();
        getDescription();
        searchReligion();
//        jScrollPane1.setSize(730,1160);
//        jTable1.setSize(730,1160);
//        jPanel2.setVisible(false);
    }

    void getDescription() {
        try {
            jPanel3.setVisible(true);
            String username;
            int row = jTable1.getSelectedRow();
            int column = jTable1.getColumnModel().getColumnIndex("USERNAME");
//            System.out.println("Row" + row);
//            System.out.println("Column" + column);
            if (row != -1) {
                username = jTable1.getValueAt(row, column).toString();
                System.out.println(username);
            } else {
                username = "";
            }
            getinformation(username);
        } catch (Exception e) {
            jOptionPane1.showMessageDialog(this, "Error" + e, "Error", 0);
        }

    }

    void getinformation(String uname) {
        try {

            String address, profession, income, complexion, marital, username, name, email,
                    city, state, contact, religion, cast, subcast, gender, height;
            String sql = "Select * from information_view where username='" + uname + "'";
            PreparedStatement pt = c.con.prepareStatement(sql);
            rs = pt.executeQuery();
            if (rs.next()) {
                name = rs.getString(2);
                email = rs.getString(3);
                address = rs.getString(4);
                city = rs.getString(5);
                state = rs.getString(6);
                contact = rs.getString(7);
                religion = rs.getString(8);
                cast = rs.getString(9);
                subcast = rs.getString(10);
                profession = rs.getString(11);
                gender = rs.getString(12);
                income = rs.getString(13);
                complexion = rs.getString(14);
                height = rs.getString(15);
                marital = rs.getString(16);
                username = rs.getString(17);
                pt.executeQuery(sql);

                sql = "select rname from religion_mas where rncode=" + religion;
                rs = c.s1.executeQuery(sql);
                rs.next();
                religion = rs.getString(1);

                sql = "select cname from cast_mas where ccode=" + cast;
                rs = c.s1.executeQuery(sql);
                rs.next();
                cast = rs.getString(1);

                sql = "select scname from sub_caste where sccode=" + subcast;
                rs = c.s1.executeQuery(sql);
                rs.next();
                subcast = rs.getString(1);

                sql = "select pname from profession_mas where pcd=" + profession;
                rs = c.s1.executeQuery(sql);
                rs.next();
                profession = rs.getString(1);

                jLabel14.setText(name);
                jLabel17.setText(city);
                jLabel18.setText(state);
                jLabel19.setText(contact);
                jLabel21.setText(religion);
                jLabel23.setText(cast);
                jLabel28.setText(subcast);
                jLabel29.setText(profession);
                jLabel15.setText(gender);
                jLabel35.setText(email);
                jLabel36.setText(income);
                jLabel37.setText(complexion);
                jLabel40.setText(height);
                jLabel41.setText(marital);
                jLabel38.setText(username);
                jTextArea1.setText(address);
            }
        } catch (Exception e) {
            jOptionPane1.showMessageDialog(this, "Error" + e, "Error", 0);
        }
    }

    void searchReligion() {
        try {
            int i = 0;
            String sql = "select rname from religion_mas";
            rs = c.s1.executeQuery(sql);
            while (rs.next()) {
                jComboBox2.insertItemAt(rs.getString(1), i++);
            }
        } catch (SQLException e) {
            jOptionPane1.showMessageDialog(this, "Error" + e, "Error!!", 0);
        }
    }

    void searchProfession() {
        try {
            int i = 0;
            String sql = "select pname from profession_mas";
            rs = c.s1.executeQuery(sql);
            while (rs.next()) {
                jComboBox1.insertItemAt(rs.getString(1), i++);
            }

        } catch (SQLException e) {
            jOptionPane1.showMessageDialog(this, "Error" + e, "Error!!", 0);
        }
    }

    void searchThroughProfession(String profession) {
        try {
            String username, name, gender, contact, address, religion, sql;
            int procode;
            DefaultTableModel model;
            model = (DefaultTableModel) jTable1.getModel();
            profession = jComboBox1.getSelectedItem().toString();
//            String sql = "select pname from profession_mas where pcd =" + procode + "";
//            if (jComboBox1.getSelectedIndex() == 0) {
//                jOptionPane1.showMessageDialog(this, "Select from profession", "Warning", 2);
//            }
            if (profession.equals(profession)) {
                sql = "Select * from profile_view order by prcd";
            } else {
                sql = "select * from  profile_view where pname='" + profession + "'";
            }
            rs = c.s1.executeQuery(sql);
            System.out.println(sql);
            int row = jTable1.getRowCount();
            if (row > 0) {
                for (int i = row - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
            }
            while (rs.next()) {
                procode = rs.getInt(1);
                username = rs.getString(17);
                name = rs.getString(2);
                gender = rs.getString(12);
                contact = rs.getString(7);
                address = rs.getString(4);
                religion = rs.getString(8);
                model.addRow(new Object[]{procode, username, name, gender, contact, address, religion});
            }

        } catch (Exception e) {
            jOptionPane1.showMessageDialog(this, "Invalid Selection");
        }
    }

    void loadData(String uname) {
        try {
            String address, profession, income, complexion, marital, username;  
            String sql, name, email, city, state, contact, religion, cast, subcast, gender, height;
            int procode;
            DefaultTableModel model;
            model = (DefaultTableModel) jTable1.getModel();
            if (uname.length() == 0) {
                sql = "select * from profile_view order by prcd";
            } //        create view profile_view as select prcd,prname,email,address,city,state,contact,
            //       rname,cname,scname,pname,gender,income,complexion,height,marital,username from
            //      profiles,religion_mas,cast_mas,sub_caste,profession_mas where rncode=religion and ccode=cast and sccode=subcast and pcd=profession;
            else if (uname.length() == 3) {
                sql = "select * from profile_view where profession=" + uname;
            } else if (uname.length() == 4) {
                sql = "Select * from profile_view where religion=" + uname;
            } else {
                sql = "select * from  profile_view where username like '" + uname + "%'";
            }
            System.out.println(sql);
            int row = jTable1.getRowCount();
            if (row > 0) {
                for (int i = row - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
            }
            rs = c.s1.executeQuery(sql);
            while (rs.next()) {
                procode = rs.getInt(1);
                username = rs.getString(17);
                name = rs.getString(2);
                gender = rs.getString(12);
                contact = rs.getString(7);
                address = rs.getString(4);
                religion = rs.getString(8);

                model.addRow(new Object[]{procode, username, name, gender, contact, address, religion});
            }
            jLabel2.setText(model.getRowCount() + "");
        } catch (SQLException e) {
            jOptionPane1.showMessageDialog(this, "Error" + e, "Error!!", 0);
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(jTextPane1);

        jScrollPane4.setViewportView(jTextPane2);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jTable1.setBackground(new java.awt.Color(102, 102, 102));
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setFont(new java.awt.Font("SimSun-ExtG", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROFILE_CODE", "USERNAME", "NAME", "GENDER", "CONTACT", "ADDRESS", "RELIGION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("RECORDS OF REGISTERED USERS   :");
        jTable1.setAlignmentY(2.0F);
        jTable1.setPreferredSize(new java.awt.Dimension(730, 580));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TOTAL RECORD VIEW");
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("0");
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(730, 580));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SHOW DESCRIPTION OF SELECTED ROW", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Palatino Linotype", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(730, 580));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setBackground(new java.awt.Color(51, 51, 51));
        jLabel7.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("1)  NAME      :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 182, -1));

        jLabel14.setBackground(new java.awt.Color(51, 51, 51));
        jLabel14.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("0");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 200, -1));

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ADDRESS");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 182, -1));

        jLabel15.setBackground(new java.awt.Color(51, 51, 51));
        jLabel15.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("0");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 200, -1));

        jLabel10.setBackground(new java.awt.Color(51, 51, 51));
        jLabel10.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("3)  CITY      :");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 182, 30));

        jLabel17.setBackground(new java.awt.Color(51, 51, 51));
        jLabel17.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("0");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 200, 30));

        jLabel11.setBackground(new java.awt.Color(51, 51, 51));
        jLabel11.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("4)  STATE     :");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 182, 30));

        jLabel18.setBackground(new java.awt.Color(51, 51, 51));
        jLabel18.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("0");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 200, 30));

        jLabel12.setBackground(new java.awt.Color(51, 51, 51));
        jLabel12.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("5)  CONTACT   :");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 182, 30));

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("0");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 200, 30));

        jLabel20.setBackground(new java.awt.Color(51, 51, 51));
        jLabel20.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("6)  RELIGION  :");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 182, 30));

        jLabel22.setBackground(new java.awt.Color(51, 51, 51));
        jLabel22.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("7)  CASTE     :");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 182, 30));

        jLabel23.setBackground(new java.awt.Color(0, 0, 0));
        jLabel23.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("0");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 200, 30));

        jLabel24.setBackground(new java.awt.Color(51, 51, 51));
        jLabel24.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("9)  PROFESSION:");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 180, 30));

        jLabel27.setBackground(new java.awt.Color(51, 51, 51));
        jLabel27.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("8)  SUB-CASTE :");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 180, 30));

        jLabel28.setBackground(new java.awt.Color(51, 51, 51));
        jLabel28.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("0");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 200, 30));

        jLabel29.setBackground(new java.awt.Color(51, 51, 51));
        jLabel29.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("0");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 200, 30));

        jLabel30.setBackground(new java.awt.Color(51, 51, 51));
        jLabel30.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("10) EMAIL     :");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 180, 30));

        jLabel31.setBackground(new java.awt.Color(51, 51, 51));
        jLabel31.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("11) INCOME    :");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 180, 30));

        jLabel32.setBackground(new java.awt.Color(51, 51, 51));
        jLabel32.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("12) COMPLEXION:");
        jPanel3.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 180, 30));

        jLabel33.setBackground(new java.awt.Color(51, 51, 51));
        jLabel33.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("15) USERNAME  :");
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 180, 30));

        jLabel35.setBackground(new java.awt.Color(51, 51, 51));
        jLabel35.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("0");
        jPanel3.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 200, 30));

        jLabel36.setBackground(new java.awt.Color(51, 51, 51));
        jLabel36.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("0");
        jPanel3.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 200, 30));

        jLabel37.setBackground(new java.awt.Color(51, 51, 51));
        jLabel37.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("0");
        jPanel3.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 200, 30));

        jLabel38.setBackground(new java.awt.Color(51, 51, 51));
        jLabel38.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("0");
        jPanel3.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 200, 30));

        jPanel4.setPreferredSize(new java.awt.Dimension(150, 150));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel6.setText("0");
        jLabel6.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, -1, 150));

        jLabel21.setBackground(new java.awt.Color(0, 0, 0));
        jLabel21.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("0");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 200, 30));

        jLabel34.setBackground(new java.awt.Color(51, 51, 51));
        jLabel34.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("13) HEIGHT    :");
        jPanel3.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 180, 30));

        jLabel39.setBackground(new java.awt.Color(51, 51, 51));
        jLabel39.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("14) STATUS    :");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 180, 30));

        jLabel40.setBackground(new java.awt.Color(51, 51, 51));
        jLabel40.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("0");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 200, 30));

        jLabel41.setBackground(new java.awt.Color(51, 51, 51));
        jLabel41.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("0");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 200, 30));

        jLabel13.setBackground(new java.awt.Color(51, 51, 51));
        jLabel13.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("2)  GENDER    :");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 182, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setEnabled(false);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 220, 90));

        jLabel25.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("PROFILE PHOTO");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, -1, -1));

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jButton4.setText(": MESSAGE:");
        jButton4.setToolTipText("CLICK HERE TO TEXT :");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 240, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FILTERS TO SEARCH RECORDS ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USERNAME");
        jLabel1.setAlignmentY(2.0F);
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PROFESSION");
        jLabel4.setAlignmentY(2.0F);
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox1.setBackground(new java.awt.Color(204, 204, 204));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox1FocusLost(evt);
            }
        });
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("RELIGION ");
        jLabel5.setAlignmentY(2.0F);
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox2.setBackground(new java.awt.Color(204, 204, 204));
        jComboBox2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox2FocusLost(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("RESET");
        jButton3.setAlignmentY(2.0F);
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setPreferredSize(new java.awt.Dimension(122, 32));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SHOW ALL");
        jButton2.setAlignmentY(2.0F);
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEARCH");
        jButton1.setAlignmentY(2.0F);
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String uname = jTextField1.getText();
        loadData(uname);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadData("");
        jTextField1.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadData("");
        jTextField1.setText(null);
        jTextField1.requestFocus();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == 10) {
            getDescription();
        }

    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String message = jOptionPane1.showInputDialog(null, "Enter your Message", "Send Message", jOptionPane1.QUESTION_MESSAGE);
        if (message != null && !message.trim().isEmpty()) {
            Message_Box mb = new Message_Box();
            mb.setVisible(true);
            this.setVisible(false);
            jOptionPane1.showMessageDialog(null, "You sent the Message", "Inform", jOptionPane1.INFORMATION_MESSAGE);
        } else {
            jOptionPane1.showMessageDialog(null, "No Message Entered", "Inform", jOptionPane1.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        try {
            String profession, sql;
            profession = jComboBox1.getSelectedItem().toString();
            sql = "select pcd from profession_mas where pname='" + profession + "'";
            rs = c.s1.executeQuery(sql);
            rs.next();
            profession = rs.getString(1);//1
            loadData(profession);
        } catch (SQLException e) {
            jOptionPane1.showMessageDialog(this, "Error" + e);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed

    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        try {
            String uname = jTextField1.getText();
            if (evt.getKeyCode() == 10) {
                loadData(uname);
            }
        } catch (Exception e) {
            jOptionPane1.showMessageDialog(this, "Error" + e, "Error", 0);
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        try {
            String religion = jComboBox2.getSelectedItem().toString();
            String sql = "select rncode from religion_mas where rname = '" + religion + "'";
            rs = c.s1.executeQuery(sql);
            rs.next();
            religion = rs.getString(1);
            System.out.println(religion);
            loadData(religion);
        } catch (Exception e) {
            jOptionPane1.showMessageDialog(this, "Error" + e);
        }

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusLost

    }//GEN-LAST:event_jComboBox1FocusLost

    private void jComboBox1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusGained
        loadData("");
        jTextField1.setText(null);
    }//GEN-LAST:event_jComboBox1FocusGained

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked

    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {

            getDescription();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox2FocusGained
        loadData("");
        jTextField1.setText(null);
    }//GEN-LAST:event_jComboBox2FocusGained

    private void jComboBox2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2FocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
}
//            if (uname.equals("")) {
//                jOptionPane1.showMessageDialog(this, "Please Choose any username", "inform", 1);
//                return;
//            } else {
//                jPanel3.setVisible(true);
//            }

//  create view information_view as select prcd,prname,email,address,city,state,contact,religion,cast,subcast,profession,gender,income,complexion,marital,
//  height,username from profiles,religion_mas,cast_mas,sub_caste,profession_mas where rncode=religion and ccode=cast and sccode=subcast and pcd=profession;
//desc information_view;
// ----------------------------------------- -------- ----------------------------
// PRCD                                      NOT NULL NUMBER(3)
// PRNAME                                             VARCHAR2(15)
// EMAIL                                              VARCHAR2(20)
// ADDRESS                                            VARCHAR2(15)\\not in table record
// CITY                                               VARCHAR2(15)
// STATE                                              VARCHAR2(15)
// CONTACT                                            VARCHAR2(10)
// RELIGION                                           NUMBER(3)
// CAST                                               NUMBER(3)
// SUBCAST                                            NUMBER(3)
// PROFESSION                                         NUMBER(3)\\not in table record
// GENDER                                             VARCHAR2(8)
// INCOME                                             VARCHAR2(15)\\not in table record
// COMPLEXION                                         VARCHAR2(8)\\not in table record
// MARITAL                                            VARCHAR2(15)\\not in table record
// HEIGHT                                             VARCHAR2(7)
// USERNAME                                           VARCHAR2(20)
//----------------------------------------- -------- ----------------------------
