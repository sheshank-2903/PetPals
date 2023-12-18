package entity;


import java.time.LocalDate;

public class CashDonation extends Donation{
	private double amount;

    public CashDonation(String donorName, double amount,int donation_id,String donation_type,LocalDate donationDate) {
        super(donorName, donationDate,donation_id,donation_type);
        this.amount = amount;
    }

    @Override
    public void recordDonation() {
        System.out.println("Recording cash donation: " + this);
    }

    public double getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "CashDonation [donorName=" + getDonorName() + ", amount=" + getAmount() + ", donationDate=" + getDonationDate() + "]";
    }
}