package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

import Exceptions.AdoptionException;
import Exceptions.InsufficientFundsException;
import Exceptions.InvalidAgeException;
import Exceptions.InvalidInputException;
import entity.CashDonation;
import entity.ItemDonation;
import util.DBConnUtil;

public class ServiceProviderImp implements ServiceProvider{

	@Override
	public void displayPetInfo() throws InvalidInputException, SQLException {
		Connection con = DBConnUtil.getConnection();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("SELECT Pets.p_id,Pets.p_name,Pets.p_age,"
				+ "Pets.p_breed,Shelters.s_name,Pet_Dog.dog_breed,Pet_Cat.cat_color "
				+ "FROM Pets "
				+ "JOIN Shelters ON Pets.s_id = Shelters.s_id "
				+ "LEFT JOIN Pet_Dog ON Pets.p_id = Pet_Dog.p_id "
				+ "LEFT JOIN Pet_Cat ON Pets.p_id = Pet_Cat.p_id;");
		while(rs.next()){
			while (rs.next()) {
			    int petId = rs.getInt(1);
			    String petName = rs.getString(2);
			    int petAge = rs.getInt(3);
			    String petBreed = rs.getString(4);
			    String shelterName = rs.getString(5);
			    String dogBreed = rs.getString(6);
			    String catColor = rs.getString(7);

			   
			    if (petName == null) {
			        petName = "Name not available";
			    }

			    if (petAge == 0) {
			        petAge = -1; 
			    }

			    if (petBreed == null) {
			        petBreed = "Breed not available";
			    }

			    if (shelterName == null) {
			        shelterName = "Shelter name not available";
			    }

			    if (dogBreed == null) {
			        dogBreed = "Dog breed not available";
			    }

			    if (catColor == null) {
			        catColor = "Cat color not available";
			    }
			    System.out.println("Pet ID: " + petId +
			                       "\nPet Name: " + petName +
			                       "\nPet Age: " + petAge +
			                       "\nPet Breed: " + petBreed +
			                       "\nShelter Name: " + shelterName +
			                       "\nDog Breed: " + dogBreed +
			                       "\nCat Color: " + catColor +
			                       "\n------------------------");
			}
		}
		
	}

	@Override
	public void PetRecordEntry() throws InvalidInputException, InsufficientFundsException, SQLException {
		// TODO Auto-generated method stub
		try {
        	Connection con = DBConnUtil.getConnection();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select Pet type:");
            System.out.println("1. Dog ");
            System.out.println("2. Cat ");
            System.out.print("Enter choice (1 or 2): ");
            int choice = scanner.nextInt();

            if (choice == 2) {
                recordCatEntry(con);
            } else if (choice == 1) {
                recordDogEntry(con);
            } else {
            	scanner.close();
            	throw new InvalidInputException("You have entered invalid input ");
            }
            scanner.close();
            con.close();
        } 
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } 
        catch (InvalidInputException e) {
        	System.out.println(e.getMessage());
        }
        catch (InsufficientFundsException e) {
        	System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
		
	}

	@Override
	public void DonationRecordingApp() throws Exception, SQLException {
		try{
			Connection con = DBConnUtil.getConnection();
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Select donation type:");
	        System.out.println("1. Cash Donation");
	        System.out.println("2. Item Donation");
	        System.out.print("Enter choice (1 or 2): ");
	        int choice = scanner.nextInt();
	
	        if (choice == 1) {
	            recordCashDonation(con);
	        } else if (choice == 2) {
	            recordItemDonation(con);
	        } else {
	        	scanner.close();
	        	throw new InvalidInputException("You have entered invalid input ");
	        }
	        scanner.close();
	        con.close();
		} 
	    catch(SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	    } 
	    catch (InvalidInputException e) {
	    	System.out.println(e.getMessage());
	    }
	    catch (InsufficientFundsException e) {
	    	System.out.println(e.getMessage());
	    }
	    catch (Exception e) {
	        System.err.println("An unexpected error occurred: " + e.getMessage());
	    }
	}
	@Override
	public void AdoptionEventManagement() throws AdoptionException, InvalidInputException, SQLException {
		try {
        	Scanner sc = new Scanner(System.in);
        	Connection con = DBConnUtil.getConnection();
			Statement st=con.createStatement();
		    ResultSet rs = st.executeQuery("select * from adoption_events");
		    System.out.println("Press 1 to show Upcoming events \n Press 2 for adoption Registration");
		    int t= sc.nextInt();
		    if(t==1) {
			    while(rs.next()) {
			    		System.out.println(rs.getInt(1)+" "+rs.getDate(2));
			    }
			    
		    }
		    else if(t==2) {
		        PreparedStatement ps = con.prepareStatement("insert into participants(participant_id,event_id,participant_name) values(?,?,?)");
		        System.out.println("Enter Participant ID: ");
		        int id =  sc.nextInt();
		        System.out.println("Enter Event ID: ");
		        int e_id=  sc.nextInt();
		        System.out.println("Enter Name: ");
		        String name = sc.next();
		        ps.setInt(1, id);
		        ps.setInt(2, e_id);
		        ps.setString(3,name);
		        int c = ps.executeUpdate(); System.out.println(c+" record inserted...");
		        Statement stmt = con.createStatement();
		        rs = stmt.executeQuery("SELECT * FROM participants");
		        while (rs.next()) {
		        	System.out.println(rs.getInt(1) + " " + rs.getInt(2));
		        } 
		    }
		    else{
		    	sc.close();
		    	throw new InvalidInputException("Invalid Input Please try again. ");
		    }
        } 
        
        catch (SQLException e) {
            if(e.getErrorCode()==1452) {
            	System.out.println("please enter correct event id this event id is not present. ");
            }
            System.err.println("SQL Error: " + e.getMessage());
        } 
        catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
		
	}
	private static void recordDogEntry(Connection connection) throws SQLException, InsufficientFundsException, InvalidAgeException {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Pet name: ");
        String PetName = scanner.nextLine();
        System.out.print("Enter Dog Breed: ");
        String PetBreed = scanner.nextLine();
        System.out.print("Enter Pet Age: ");
        int PetAge = scanner.nextInt();
        System.out.print("Enter Shelter ID : ");
        int Shelter_id = scanner.nextInt(); 
        int Pet_id=(int) (Math.random()*1000);
        scanner.close();
        if(PetAge<0) {
        	throw new InvalidAgeException("Pet age cannot be negative ");
        }
        PreparedStatement ps = connection.prepareStatement("insert into Pets(p_id,p_name,p_age,p_breed,s_id) values(?,?,?,?,?)"); 
        ps.setInt(1, Pet_id);
        ps.setString(2, PetName);
        ps.setInt(3, PetAge);
        ps.setString(4, "Dog");
        ps.setInt(5, Shelter_id);
        
        int c = ps.executeUpdate();
        
        ps = connection.prepareStatement("insert into Pet_Dog(p_id,dog_breed) values(?,?)"); 
        ps.setInt(1, Pet_id);
        ps.setString(2, PetBreed);
        int cd = ps.executeUpdate(); System.out.println(cd +" record inserted...");
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Pets");
        while (rs.next()) {
        	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3)+" "+rs.getString(4));
        }
        System.out.println("Pet Record inserted successfully.");
    }

    private static void recordCatEntry(Connection connection) throws SQLException, InvalidAgeException {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Pet name: ");
        String PetName = scanner.nextLine();
        System.out.print("Enter Pet Color: ");
        String PetColor = scanner.nextLine(); 
        System.out.print("Enter Pet Age: ");
        int PetAge = scanner.nextInt(); 
        System.out.print("Enter Shelter ID : ");
        int Shelter_id = scanner.nextInt(); 
        int Pet_id=(int) (Math.random()*1000);
        scanner.close();
        if(PetAge<0) {
        	throw new InvalidAgeException("Pet age cannot be negative ");
        }
        PreparedStatement ps = connection.prepareStatement("insert into Pets(p_id,p_name,p_age,p_breed,s_id) values(?,?,?,?,?)"); 
        ps.setInt(1, Pet_id);
        ps.setString(2, PetName);
        ps.setInt(3, PetAge);
        ps.setString(4, "Cat");
        ps.setInt(5, Shelter_id);
        
        int c = ps.executeUpdate();
        
        ps = connection.prepareStatement("insert into Pet_Cat(p_id,cat_color) values(?,?)"); 
        ps.setInt(1, Pet_id);
        ps.setString(2, PetColor);
        int cd = ps.executeUpdate(); System.out.println(cd +" record inserted...");
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Pets");
        while (rs.next()) {
        	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3)+" "+rs.getString(4));
        }
        System.out.println("Pet Record inserted successfully.");
    }
    private static void recordCashDonation(Connection connection) throws SQLException, InsufficientFundsException {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter donor name: ");
        String donorName = scanner.nextLine();
        System.out.print("Enter donation Amount: ");
        Double donationAmount = scanner.nextDouble();    
        int donation_id=(int) (Math.random()*1000);
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        scanner.close();
        if(donationAmount<10) {
        	throw new InsufficientFundsException("minimum donation amount must be greater then 10 Please try again ");
        }
        CashDonation itemDonation = new CashDonation(donorName,donationAmount,donation_id,"Cash",currentDate);
        PreparedStatement ps = connection.prepareStatement("insert into Donation(d_id,d_donarName,d_type,d_date) values(?,?,?,?)"); 
        ps.setInt(1, donation_id);
        ps.setString(2, donorName);
        ps.setString(3, "Cash");
        ps.setDate(4, sqlDate);
        int c = ps.executeUpdate();
        
        ps = connection.prepareStatement("insert into Donation_Cash(d_id,d_amount) values(?,?)"); 
        ps.setInt(1, donation_id);
        ps.setDouble(2, donationAmount);
        int cd = ps.executeUpdate(); System.out.println(cd +" record inserted...");
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Donation_Cash");
        while (rs.next()) {
        	System.out.println(rs.getInt(1) + " " + rs.getString(2));
        }
        System.out.println("Item donation recorded successfully.");
    }

    private static void recordItemDonation(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter donor name: ");
        String donorName = scanner.nextLine();
        System.out.print("Enter donation Item: ");
        String donationItem = scanner.nextLine();    
        System.out.print("Enter donation Quantity: ");
        int donationQuantity = scanner.nextInt();
        System.out.print("Enter item type: ");
        String itemType = scanner.nextLine();
        int donation_id=(int) (Math.random()*1000);
        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        scanner.close();
        ItemDonation itemDonation = new ItemDonation(donorName,donationItem,donationQuantity,donation_id,"Item",currentDate);
        PreparedStatement ps = connection.prepareStatement("insert into Donation(d_id,d_donarName,d_type,d_date) values(?,?,?,?)"); 
        ps.setInt(1, donation_id);
        ps.setString(2, donorName);
        ps.setString(3, "Item");
        ps.setDate(4, sqlDate);
        
        ps = connection.prepareStatement("insert into Donation_Item(d_id,d_item,d_quantity) values(?,?,?)"); 
        ps.setInt(1, donation_id);
        ps.setString(2, donationItem);
        ps.setInt(3, donationQuantity);
        int cd = ps.executeUpdate(); System.out.println(cd +" record inserted...");
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Donation_Item");
        while (rs.next()) {
        	System.out.println(rs.getInt(1) + " " + rs.getString(2));
        }
        System.out.println("Item donation recorded successfully.");
    }
}

