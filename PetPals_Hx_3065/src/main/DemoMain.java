package main;

import java.util.Scanner;
import dao.ServiceProvider;
import dao.ServiceProviderImp;

import Exceptions.FileExceptionDemo;
import Exceptions.InvalidInputException;

public class DemoMain {
	public static void main(String args[])throws InvalidInputException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Press 1 : To get Pet List : ");
		    System.out.println("Press 2 : To get Donation : ");
		    System.out.println("Press 3 : To get Adoption Event Management : ");
		    System.out.println("Press 4 : To Enter Pet Record : ");
		    System.out.println("Press 5 : To ReadFile : ");
		    System.out.println("Press 6 : To Exit : ");
		    Scanner sc = new Scanner(System.in); 
		    int task = sc.nextInt();
		    ServiceProvider serviceProvider = new ServiceProviderImp();
				if(task==1) {
					serviceProvider.displayPetInfo();
				}
				else if(task==2) {
					serviceProvider.DonationRecordingApp();
				}
				else if(task == 3) {
					serviceProvider.AdoptionEventManagement();
				}
				else if(task==4) {
					serviceProvider.PetRecordEntry();
				}
				else if(task==5) {
					FileExceptionDemo fed=new FileExceptionDemo();
					fed.runIndex();
				}
				else if(task == 6) {
					System.out.println("Thanks you for visiting ");
				}
				else {
					throw new InvalidInputException("You have entered invalid input ");
				}
		}
		catch(Exception e) {
			System.out.println("Error ");
			e.printStackTrace();
		}
		
	}
}
