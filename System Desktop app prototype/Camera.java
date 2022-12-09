package net.codejava.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;

import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.core.Mat;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit; 

class Login extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton Login;
    public Container pane = getContentPane();

    public Login() {
        setTitle("Login Screen");
        setSize(400, 300);
        setLayout(new GridLayout(4,1));
        setLocationRelativeTo(null);
      


        Login = new JButton("Parent Login");
        pane.add(new JLabel());
        pane.add(new JLabel("                                         Child Monitoring System"));
        pane.add(new JLabel());
        
        pane.add(Login);
        customerListener cl = new customerListener();
        Login.addActionListener(cl);
       
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public class customerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new customerWindow();
        }
    }


  class customerWindow extends JFrame{
		
	    public JButton reg_B, login_B; //we will have 1 button (register now button)
	    public JTextField Tid; //we will have 2 text fields in login page (one for id and one for password login)
	    public JPasswordField Tpass;
	    public Container pane = getContentPane();

	    public customerWindow(){ //constructor
	        setTitle("Parent Login"); //title name
	        setSize(600, 200);
	        setLocationRelativeTo(null);
	        setLayout(new GridLayout(5, 2));

	        reg_B = new JButton("Register Now!");//name of the button for registration
	        login_B= new JButton("Login");// login button

	        //the 2 textfields
	        Tid = new JTextField();
	        Tpass = new JPasswordField();

	        //setting the layout
	        add(new JLabel("ID: "));
	        add(Tid);
	        add(new JLabel("Password "));
	        add(Tpass);
	        add(new JLabel());
	        add(login_B);
	        add(new JLabel());
	        add(new JLabel());
	      
	        
	        loginListener log = new loginListener();
	        login_B.addActionListener(log);

	       
	        //
	        setVisible(true);
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    }
	    
	    class loginListener implements ActionListener { //login button handler for customers login
	        public void actionPerformed(ActionEvent e) {
	        	if(Tid.getText().equals("") || Tpass.getPassword().length == 0 ) { //checking if one of the fields is empty
	        		JOptionPane.showMessageDialog(new JFrame(), "You have left one or more empty Text fields, please fill them", "Message", JOptionPane.INFORMATION_MESSAGE); 
	        	} 
	        	else { 
	        		try {
	        	
					Scanner readfile = new Scanner(new FileReader("parentData.txt")); //else we open customer.txt to read and check the names
					
					String passwordText = new String(Tpass.getPassword()); //saving the password from the password field as text
					String FName,pass,mobile; //other info to read from the file
					
					int checking=0; //a check will be used to know if we found the user or not
				
					while(readfile.hasNext()) { //reading from the file
						FName=readfile.next();
						pass = readfile.next();
						if(Tid.getText().equals(FName) && passwordText.equals(pass)) { //if we found the username and password we can break
							checking=1;
							break;
						}
						mobile=readfile.next();
					}
						readfile.close();
						
						if(checking==0) { //checking=0 means no user was found
							JOptionPane.showMessageDialog(new JFrame(), "Wrong id or password", "Message", JOptionPane.INFORMATION_MESSAGE);
						} else {
	            			//else login was successful
						    new parentHome();
	            			setVisible(false);
	                        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	                  
						}
						
					
	        	
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	}
	        	
	            
	        }
	    }
	    
	   
  }
}

class parentHome extends JFrame {
	

    JButton openFeed, Exit, Notification;
    JTextField searchField;

    public parentHome() {
        super("Parent Dashboard");
      
        setSize(600, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,1));
        Notification = new JButton("Notification");
        openFeed = new JButton("Open Feed");
        Exit = new JButton("Exit");
        add(new JLabel("Welcome Parent!"));
        add(openFeed);
        add(Notification);
        add(Exit);

        openFeed.addActionListener(new cameraHandler());
        Notification.addActionListener(new notifyHandler());
        Exit.addActionListener(new exitHandler());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    class exitHandler implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		new Login();
    		setVisible(false);
    	}
    }
    
    class notifyHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	try {
				if (Camera.status == 0) {
					JOptionPane.showMessageDialog(Notification, "Temperature of the baby is normal", "Information",
					        JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
					JOptionPane.showMessageDialog(Notification, "Temperature of the baby is abnormal", "Warning",
					        JOptionPane.WARNING_MESSAGE);
					Camera.status = 0;
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	
        	
             
        }
    }
    class cameraHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	setVisible(false);
        	EventQueue.invokeLater(new Runnable() {
    			
    			@Override
    			public void run() {
    				Camera camera = new Camera();
    				//start camera in thread 
    				new Thread(new Runnable() {
    					@Override
    					public void run() {
    						camera.startCamera();
    					}
    				}).start();
    			}
    		});
             
        }
    }

    
}



public class Camera extends JFrame{
    	//camera screen 
    	public static int status;
    	public static int meal_time1;
    	public static int meal_time;
    	public static int meal_time2;

    	public JLabel cameraScreen;
    	
    	public JButton btnMain;
    	
    	public VideoCapture capture;
    	
    	public Mat image;
    	
    	LocalTime Breakfast = LocalTime.of(8,0,0,0).truncatedTo(ChronoUnit.MICROS) ;
    	LocalTime lunch = LocalTime.of(12,0,0,0).truncatedTo(ChronoUnit.MICROS) ;
    	LocalTime Dinner = LocalTime.of(19,5,0,0).truncatedTo(ChronoUnit.MICROS) ;

    	public Camera() {
    		Container frame = this.getContentPane();
    	    frame.setLayout(new BorderLayout(8, 6));
    		setSize(new Dimension(640,560));
    		setLocationRelativeTo(null);
    		//design UI
    		cameraScreen = new JLabel();
    		cameraScreen.setLayout(new FlowLayout(4, 4, 4));
    	    frame.add(cameraScreen, BorderLayout.CENTER);
    		//cameraScreen.setBounds(0,0 , 640, 480);
    		//add(cameraScreen);
    		
    		
    		btnMain = new JButton("Close Feed");
    		btnMain.setLayout(new FlowLayout(1,1,1));
    		frame.add(btnMain, BorderLayout.SOUTH);
    		//add(btnMain);
    		
    		btnMain.addActionListener(new menuHandler());
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		setVisible(true);
    	}
    	class menuHandler implements ActionListener {
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	new parentHome();
        		
                 
            }
        }
    	//create camera
    	
    	public void startCamera() {
    		capture =  new VideoCapture(0);
    		image =  new Mat();
    		byte[] imageData;
    		
    		ImageIcon icon;
    		
    	
    		while(true) {
    			//read image to matrix 
    			capture.read(image);
    			
    			//convert matrix to byte 
    			final MatOfByte buf = new MatOfByte();
    			Imgcodecs.imencode(".jpg",image,buf);
    			
    			imageData = buf.toArray();
    			
    			//add to JLabel 
    			icon = new ImageIcon(imageData);
    			cameraScreen.setIcon(icon);
    			
    			 Random randNum =  new Random();
    		     double temperature = randNum.nextDouble(35,39);
    		     final DecimalFormat dfZero = new DecimalFormat("0.0");
    		     int timer = randNum.nextInt(1,50);
    		     //System.out.println(temperature);
    		   //	System.out.println(Dinner);
    		    	//System.out.println(lunch);
	    		   //System.out.println(java.time.LocalTime.now().truncatedTo( ChronoUnit.MINUTES ));

    		    // System.out.println(java.time.LocalTime.now());
    		     
    		     if (timer == 20) {
	    		     if (temperature < 36.5 || temperature > 37.5) {
	    		    	 status = 1;
	    		    	 for(int i=0;i<2;i++) {
	    		        		Toolkit.getDefaultToolkit().beep(); //generate beep sound to notify
	    		        		
	    		            	
	    		        		try {
	    		    				Thread.sleep(1000); // introduce delay
	    		    			} catch (InterruptedException e1) {
	    		    			}
	    		        		
	    		        	}
	    		    	 JOptionPane.showMessageDialog(null, "Temperature of the baby is abnormal " + dfZero.format(temperature) + " degree celsius", "Warning",
	 		        	        JOptionPane.WARNING_MESSAGE);
	    		    	 
	    		     }
	    		     LocalTime t = java.time.LocalTime.now();
    		     }
	    		     meal_time = Breakfast.compareTo(java.time.LocalTime.now().truncatedTo( ChronoUnit.SECONDS ));

					
	    		     if (meal_time == 0) {
 		        		Toolkit.getDefaultToolkit().beep(); 

	    		    	 JOptionPane.showMessageDialog(null, "Baby's breakfast Reminder! " + Breakfast , "Information",
		 		        	        JOptionPane.INFORMATION_MESSAGE);
	    		    	 meal_time = 1;
	    		     }

	    		     meal_time1 = lunch.compareTo(java.time.LocalTime.now().truncatedTo( ChronoUnit.SECONDS ));
	    		    
	    		     if (meal_time1 == 0) {
 		        		Toolkit.getDefaultToolkit().beep(); 

	    		    	 JOptionPane.showMessageDialog(null, "Baby's lunch Reminder! " + lunch , "Information",
		 		        	        JOptionPane.INFORMATION_MESSAGE);
	    		    	 meal_time1 = 1;
	    		     }

	    		     meal_time2 = Dinner.compareTo(java.time.LocalTime.now().truncatedTo( ChronoUnit.SECONDS ));
	    		    
	    		     if (meal_time2 == 0) {
 		        		Toolkit.getDefaultToolkit().beep(); 

	    		    	 JOptionPane.showMessageDialog(null, "Baby's dinner Reminder! " + Dinner , "Information",
		 		        	        JOptionPane.INFORMATION_MESSAGE);
	    		    	 meal_time2 = 1;
	    		     }

    		     double k = 999999999;
    				for(int i=0;i<100;i++)
    					k = k / 2;
    		     } 
    		    
    		
    		
    	}
	
    public static void main(String[] args) throws IOException {
        new Login();
        
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       
		
	}

}