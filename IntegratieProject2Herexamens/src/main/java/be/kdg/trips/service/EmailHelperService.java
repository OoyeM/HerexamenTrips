package be.kdg.trips.service;

/**
 * Created by Matthias on 10/08/2015.
 */
public interface EmailHelperService {
    public void sendEmail(String toAddress, String subject, String msgBody);
}
