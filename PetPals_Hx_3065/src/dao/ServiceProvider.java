package dao;
import java.sql.SQLException;
import Exceptions.AdoptionException;
import Exceptions.InsufficientFundsException;
import Exceptions.InvalidInputException;

public interface ServiceProvider {
    void displayPetInfo() throws InvalidInputException,SQLException;

    void PetRecordEntry() throws InvalidInputException,InsufficientFundsException,SQLException;

    void DonationRecordingApp() throws Exception,SQLException;

    void AdoptionEventManagement() throws AdoptionException,InvalidInputException,SQLException;
    
}
