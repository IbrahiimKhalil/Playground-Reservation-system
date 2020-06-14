package Playground;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;
import java.util.Random;  
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
  
  

class Client {
	public String Email, Password, Name;
	public int ClientId;
	public int VerificationCode;
	public boolean verified;
	public static int ClientCount = 1;
	public static int ClientType;
    static Scanner scanner = new Scanner(System.in);
	
	public Client(String email, String password, String name, int verificationCode, int clientType) {
		Email = email;
		Password = password;
		Name = name;
		ClientId = ClientCount;
		ClientCount++;
		VerificationCode = verificationCode;
		verified = false;
		ClientType = clientType;
	}
	
	public void verify(int vf)
	{
		if(vf == VerificationCode)
			verified = true;
		else 
			System.out.println("wrong verification code");
	}
}


		
public class Program {

	static List<Client> clients = new ArrayList<Client>();
	static int connectedClient;
	static List<Playground.PG> PG = new ArrayList<Playground.PG>();
    static List<Playground.BookPG> bookPG = new ArrayList<Playground.BookPG>();
    static List<Playground.Payment> payment = new ArrayList<Playground.Payment>();
    static Scanner scanner = new Scanner(System.in);


	public static void main(String[] args) {
		mainMenu();
		while(true) {
		System.out.println(clients.get(connectedClient).ClientType);
		switch(clients.get(connectedClient).ClientType) {
		case 1:
			BookingPlayground();
			break;
		case 2:
			creatingPlayground();
			break;
		case 3:
			AdminMenu();
			break;
		}
		}
	}
	public static void mainMenu()
	{
		String email; String password; String name; int accType;
		int option; int flag=0;
		while (flag==0) {
			
			System.out.println("Hello in Playgrounds reservation system please enter your choice: ");
			System.out.println("1) Sign in");
			System.out.println("2) Register");
			option = scanner.nextInt();
			switch(option) {
			case 1:
				System.out.println("Enter your email");
				email = scanner.next();

				System.out.println("Enter your password");
				password = scanner.next();
			
				if(verifyClient(email, password)) {
					flag =1;
					continue;
					/*System.out.println("Welcome " + clients.get(connectedClient).Name);
					if(!clients.get(connectedClient).verified) {
						System.out.println("Enter your Verification Code");
						int vf = scanner.nextInt();
						clients.get(connectedClient).verify(vf);
					}*/
				}
				else {
					System.out.println("Invalid username or password");
					for(int i=0;i<clients.size();i++)
						System.out.println(clients.get(i).Email + clients.get(i).Password);
				}
			break;
			case 2:
				System.out.println("Enter your email");
				email = scanner.next();
			
				System.out.println("Enter your name");
				name = scanner.next();

				System.out.println("Enter your password");
				password = scanner.next();
				
				System.out.println("choose account type");
				System.out.println("1- Player");
				System.out.println("2- Playground Owner");
				System.out.println("3- Adminstrator");
				accType = scanner.nextInt();
				
				int vf = GenerateVerificationCode();
				clients.add(new Client(email, password,name, vf, accType));
				//AddClient(email, name, password,accType);
				break;
			}
		}
	}
	public static String createPGid(int idscount) {
        int id;
        id = idscount + 1;
        return String.valueOf(id);
    }
	
	public static void creatingPlayground() {
        String PGid;
        String name;
        String PGdescription;
        String address;
        int pricehour;
        int bookingNo;
        Boolean approved;
        String[][] Avilablehours = new String[24][2];
        String state = "";
        String ownerid = "";

        int optionowner;
            int idscount = 0;
            System.out.println("Hello in Playgrounds reservation system please enter your choice: ");
            System.out.println("1) Add playground. ");
            System.out.println("2) Sign Out ");
            optionowner = scanner.nextInt();
            switch (optionowner) {
            case 2:
            	mainMenu();
            	break;
                case 1:
                    PGid = createPGid(idscount);
                    idscount++;
                    System.out.println("Enter Playground name: ");
                    name = scanner.next();
                    System.out.println("Enter Playground description: ");
                    PGdescription = scanner.next();
                    System.out.println("Enter Playground address: ");
                    address = scanner.next();
                    System.out.println("Enter Playground price hour: ");
                    pricehour = scanner.nextInt();

                    String hour = "00:00";
                    for (int i = 0; i < 24; i++) {
                        Avilablehours[i][0] = String.valueOf(hour);
                        hour = String.valueOf(LocalTime.parse(hour).plusHours(1));
                    }
                    for (int i = 0; i < 24; i++) {
                        Avilablehours[i][1] = "A";
                    }

                    PG.add(new PG(PGid, name, PGdescription, address, pricehour, 0, false, Avilablehours, state, ownerid));
                    for (int i = 0; i < 24; i++) {
                        for (int j = 0; j < 2; j++) {
                            System.out.println(PG.get(0).Avilablehours[i][j]);
                        }
                    }
                    break;

            }
        
    }
	
    public static String createBPGid(int idbookcount) {
        int id;
        id = idbookcount + 1;
        return String.valueOf(id);
    }


    public static void BookingPlayground(){
        String id;
        String playgroundid;
        String accountid = "0";
        String date;
        String total;
        String description;
        String starthour;
        String duration;
        String paymentid;
        int optionplayer;
            int idbookcount = 0;
            System.out.println("Hello in Playgrounds reservation system please enter your choice: ");
            System.out.println("1) Book a playground. ");
            System.out.println("2) Complaint on a playground.");
            System.out.println("3) Create a team.");
            System.out.println("4) Sign Out ");
            optionplayer = scanner.nextInt();
            int Coption;
            String complaint;
            switch (optionplayer) {
            case 4:
            	mainMenu();
            	break;
                case 1:
                    for (int i = 0; i < PG.size(); i++) {
                        if (PG.get(i).approved == true)
                            System.out.println((i + 1) + ") " + PG.get(i).name);
                    }
                    break;
                case 2:
                	ViewingPlaygrounds();
                    System.out.println("Enter playground number:");
                    Coption = scanner.nextInt();
                    System.out.println("Enter your complaint:");
                    complaint = scanner.next();
                    for (int j = 0; j < Coption; j++) {
                        PG.get(j).ComplaintsN++;
                        PG.get(j).Complaints.add(complaint);
                    }
                case 3:
                	CreatingTeam();
                	break;
            }
            if(optionplayer==1) {
            int optionbook;
            optionbook = scanner.nextInt();
            /*for (int i = 0; i < 24; i++) {
                if (PG.get(optionbook).Avilablehours[i][1] == "A")
                    System.out.println(PG.get(optionbook).Avilablehours[i][0]);

            }*/
            System.out.println("Do you want to reserve? (Y/N) ");
            String answer = scanner.next();
            if (answer.equalsIgnoreCase("Y")) {
                int fromhour;
                int tohour;
                System.out.println("The slot you want is from? (ex:12,7,23) ");
                fromhour = scanner.nextInt();
                System.out.println("The slot you want is to? (ex:13,8,0) ");
                tohour = scanner.nextInt();
                System.out.println("Enter the description:  ");
                description = scanner.next();
                starthour = String.valueOf(fromhour);
                id = createBPGid(idbookcount);
                playgroundid = PG.get(optionbook-1).id;
                //accountid
                date = String.valueOf(LocalDate.now());
                duration = String.valueOf(tohour - fromhour);
                total = String.valueOf((tohour - fromhour) * (PG.get(optionbook-1).pricehour));
                paymentid = "1";//payment.get(optionbook-1).id;
                bookPG.add(new BookPG(id, playgroundid, Integer.toString(clients.get(connectedClient).ClientId), date, total, description, starthour, duration, paymentid));
                for (int i = fromhour; i < tohour; i++) {
                    PG.set(optionbook-1, null).Avilablehours[i][1] = "B";
                }
            }
        }
            
    }

    public static void AdminMenu() {
    	int option;
    	System.out.println("Hello in Playgrounds reservation system please enter your choice: ");
        System.out.println("1) Approve a playground. ");
        System.out.println("2) Filter playgrounds.");
        System.out.println("3) Sign Out ");
        option = scanner.nextInt();
        switch(option)
        {
        case 3:
        	mainMenu();
        	break;
        case 1:
        	ApprovingRequests();
        	break;
        case 2:
        	FilteringPlaygrounds();
        	break;
        }
    }
    public static void ApprovingRequests() {
        if (PG.size() == 0) {
            System.out.println("There are no Playgrounds in the system");
        } else {
            System.out.println("Requested Playgrounds:           *Playgrounds Appear sequentially");
            System.out.println("");
            String choice;
            for (int i = 0; i < PG.size(); i++) {
                if (PG.get(i).approved == false) {
                    System.out.println((i + 1) + ") ");
                    System.out.println("Name: " + PG.get(i).name);
                    System.out.println("ID: " + PG.get(i).id);
                    System.out.println("Address: " + PG.get(i).address);
                    System.out.println("Description: " + PG.get(i).description);
                    System.out.println("");
                    System.out.println("Enter 'A' to approve playground or 'R' to reject Playground.");
                    choice = scanner.next();
                    switch (choice) {
                        case "A":
                            PG.get(i).approved = true;
                            break;
                        case "R":
                            PG.remove(i);
                            break;
                    }
                }
            }
        }
    }

    public static void ViewingPlaygrounds() {
        for (int i = 0; i < PG.size(); i++) {
            if (PG.get(i).approved == true) {
                System.out.println((i + 1) + ") ");
                System.out.println("Name: " + PG.get(i).name);
                System.out.println("ID: " + PG.get(i).id);
                System.out.println("Address: " + PG.get(i).address);
                System.out.println("Description: " + PG.get(i).description);
                System.out.println("Number of complaints: " + PG.get(i).ComplaintsN);
            }
        }
    }

    public static void FilteringPlaygrounds() {
        String Input;
        String Choice;
        System.out.println("Enter Playground name or id");
        Input = scanner.next();
        for (int i = 0; i < PG.size(); i++) {
            if (PG.get(i).name == Input || PG.get(i).id == Input) {
                System.out.println((i + 1) + ") ");
                System.out.println("Address: " + PG.get(i).address);
                System.out.println("Number of complaints: " + PG.get(i).ComplaintsN);
                System.out.println("Complaints: ");
                for (int j = 0; j < PG.get(i).Complaints.size(); j++) {
                    System.out.println(PG.get(i).Complaints.get(j));
                }
                System.out.println(" ");
                System.out.println("Do you wanna remove this playground  (Y or N) ");
                Choice=scanner.next();
                while (Choice!="Y" || Choice!="N"){
                    System.out.println("Invalid input, Enter Y or N");
                    Choice=scanner.next();
                }
                if(Choice=="Y")
                    PG.remove(i);

            }
        }
    }

    public static void CreatingTeam(){
        int TeamNumber;
        String name, Gmail;
        System.out.println("Enter your team number         *includes you*");
        TeamNumber=scanner.nextInt();
        while (TeamNumber!=5){
            System.out.println("Invalid input, Your must should contains 5 players");
            System.out.println("Enter valid number");
            TeamNumber=scanner.nextInt();
        }
        for(int i=0;i<5;i++){
            System.out.println("Enter player ("+(i+1)+")'s name: ");
            name=scanner.next();
            System.out.println("Enter player ("+(i+1)+")'s gmail: ");
            Gmail=scanner.next();
//            sendEmail(name,Gmail,/*Player_Name*/, /*Playground_Name*/, /*Playground_Address*/, /*Booking_Time*/ );
        }

    }
	
	public static void AddClient(String email, String name, String password, int accType)
	{
		File file = new File(System.getProperty("user.dir") + "/emails.txt");
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file,true);
			
			int vf = GenerateVerificationCode();
			//sendEmail(email, vf);
	        Writer bw = new BufferedWriter(fw);
	        bw.append(email + "\n" + name + "\n" + password + "\n" + vf + "\n" + accType + "\n");
	        bw.flush();
	        bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	   
	public static int GenerateVerificationCode() {
	      Random rand = new Random(); 
	      int upperbound = 10000;
	      int int_random = rand.nextInt(upperbound);
	      return int_random;
	    }
	public static void LoadUsers() {
		File file = new File(System.getProperty("user.dir") + "/emails.txt");
		BufferedReader br = null;
		try {
			file.createNewFile();
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String st;
		String ml = "";
		String nm = "";
		String pass = "";
		int accType = 0;
		int vf=0;
		int i = 0;
		try {
			while ((st = br.readLine()) != null) {
				if (i == 0) {
					ml = st;
					i++;
				} else if (i == 1) {
					nm = st;
					i++;
				} 
				else if (i == 2) {
					pass = st;
					i++;
				} 
				else if (i == 3) {
					vf = Integer.parseInt(st);
					i++;
				} 
				else {
					accType = Integer.parseInt(st);
					System.out.println(accType);
					clients.add(new Client(ml, pass, nm, vf, accType));
					i = 0;
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static boolean verifyClient(String email, String password) {
		for (int i = 0; i < clients.size(); i++) {
			if (email.equalsIgnoreCase(clients.get(i).Email)) {
					if (password.equalsIgnoreCase(clients.get(i).Password)) {
						connectedClient = i;
						return true;
					}
			}
		}
		return false;
	}
	public static void sendEmail(String to, int vf){
		String from = "";
		String password = "";
             
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
        //get Session   
        Session session = Session.getDefaultInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);    
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
         message.setSubject("Playground Reservation System");    
         message.setText("Your verification code is " + vf);    
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {throw new RuntimeException(e);}    
           
  }   	
	
}
