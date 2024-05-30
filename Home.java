import java.sql.Date;
import java.sql.Timestamp;
import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        Scanner scanner = new Scanner(System.in);

        Signup signup = new Signup(dbConnector);
        Profile profile = new Profile(dbConnector);
        Patient patient = new Patient(dbConnector);
        Diagnosis diagnosis = new Diagnosis(dbConnector);
        Record record = new Record(dbConnector);
        Alerts alerts = new Alerts(dbConnector);
        Measure measure = new Measure(dbConnector);
        Observation observation = new Observation(dbConnector);

        try {
            while (true) {
                System.out.println("Choose an action:");
                System.out.println("1. Register User");
                System.out.println("2. View Profile");
                System.out.println("3. Update Profile");
                System.out.println("4. Delete Profile");
                System.out.println("5. Add Patient");
                System.out.println("6. Add Diagnosis");
                System.out.println("7. Add Record");
                System.out.println("8. Add Alert");
                System.out.println("9. View Alerts");
                System.out.println("10. Delete Alert");
                System.out.println("11. Add Measure");
                System.out.println("12. View Measures");
                System.out.println("13. Add Observation");
                System.out.println("14. View Observations");
                System.out.println("15. Exit");

                int choice = safeReadInt(scanner); // Safe method to read integers

                switch (choice) {
                    case 1:
                        System.out.println("Enter UserID, First Name, Last Name, DOB (yyyy-mm-dd), Gender, Address, Phone, Password:");
                        String userid = scanner.nextLine();
                        String fname = scanner.nextLine();
                        String lname = scanner.nextLine();
                        Date dob = Date.valueOf(scanner.nextLine());
                        String gender = scanner.nextLine();
                        String address = scanner.nextLine();
                        String phone = scanner.nextLine();
                        String password = scanner.nextLine();
                        signup.registerUser(userid, fname, lname, dob, gender, address, phone, password);
                        break;
                    case 2:
                        System.out.println("Enter UserID to view profile:");
                        String viewUserId = scanner.nextLine();
                        profile.viewProfile(viewUserId);
                        break;
                    case 3:
                        System.out.println("Enter UserID to update profile:");
                        String updateUserId = scanner.nextLine();
                        System.out.println("Enter First Name, Last Name, DOB (yyyy-mm-dd), Gender, Address, Phone:");
                        fname = scanner.nextLine();
                        lname = scanner.nextLine();
                        dob = Date.valueOf(scanner.nextLine());
                        gender = scanner.nextLine();
                        address = scanner.nextLine();
                        phone = scanner.nextLine();
                        profile.updateProfile(updateUserId, fname, lname, dob, gender, address, phone);
                        break;
                    case 4:
                        System.out.println("Enter UserID to delete profile:");
                        String deleteUserId = scanner.nextLine();
                        boolean deleteSuccess = profile.deleteProfile(deleteUserId);
                        if (deleteSuccess) {
                            System.out.println("Profile deleted successfully.");
                        } else {
                            System.out.println("Failed to delete profile.");
                        }
                        break;
                    case 5:
                        System.out.println("Enter UserID to add as patient:");
                        String patientId = scanner.nextLine();
                        patient.addPatient(patientId);
                        break;
                    case 6:
                        System.out.println("Enter PatientID, DiseaseID, Since (yyyy-mm-dd), End Date (yyyy-mm-dd or blank for none):");
                        String patientID = scanner.nextLine();
                        String diseaseID = scanner.nextLine();
                        String sinceStr = scanner.nextLine();
                        String endDateStr = scanner.nextLine().trim();
                        diagnosis.addDiagnosis(patientID, diseaseID, sinceStr, endDateStr.isEmpty() ? null : endDateStr);
                        break;
                    case 7:
                        System.out.println("Enter PatientID, ObsName, ObsType, ObsDate (yyyy-mm-dd hh:mm:ss), ObsValue, RecDate (yyyy-mm-dd hh:mm:ss):");
                        String rPatientId = scanner.nextLine();
                        String obsName = scanner.nextLine();
                        String obsType = scanner.nextLine();
                        Timestamp obsDate = Timestamp.valueOf(scanner.nextLine());
                        float obsValue = Float.parseFloat(scanner.nextLine());
                        Timestamp recDate = Timestamp.valueOf(scanner.nextLine());
                        boolean recordAdded = record.addRecord(rPatientId, obsName, obsType, obsDate, obsValue, recDate);
                        if (!recordAdded) {
                            System.out.println("Failed to add record. Ensure that the patient ID and measure exist.");
                        }
                        break;
                    case 8:
                        System.out.println("Enter PatientID, AlertType, GenerateDate (yyyy-mm-dd), Message:");
                        String aPatientId = scanner.nextLine();
                        String alertType = scanner.nextLine();
                        Date generateDate = Date.valueOf(scanner.nextLine());
                        String message = scanner.nextLine();
                        alerts.addAlert(aPatientId, alertType, generateDate, message);
                        break;
                    case 9:
                        System.out.println("Enter PatientID to view alerts:");
                        String alertPatientId = scanner.nextLine();
                        alerts.viewAlerts(alertPatientId);
                        break;
                    case 10:
                        System.out.println("Enter PatientID and AlertType to delete alert:");
                        String delPatientId = scanner.nextLine();
                        String delAlertType = scanner.nextLine();
                        boolean alertDeleted = alerts.deleteAlert(delPatientId, delAlertType);
                        if (alertDeleted) {
                            System.out.println("Alert deleted successfully.");
                        } else {
                            System.out.println("Failed to delete alert. Ensure the Patient ID and Alert Type are correct.");
                        }
                        break;
                    case 11:
                        System.out.println("Enter Observation Name and Observation Type:");
                        String obsname = scanner.nextLine();
                        String obstype = scanner.nextLine();
                        boolean measureAdded = measure.addMeasure(obsname, obstype);
                        if (!measureAdded) {
                            System.out.println("Failed to add measure. Ensure the input values are correct.");
                        }
                        break;
                    case 12:
                        measure.viewMeasures();
                        break;
                    case 13:
                        System.out.println("Enter PatientID, ObsName, ObsType, ObsDate (yyyy-mm-dd hh:mm:ss), ObsValue, RecDate (yyyy-mm-dd hh:mm:ss):");
                        String oPatientId = scanner.nextLine();
                        String oObsName = scanner.nextLine();
                        String oObsType = scanner.nextLine();
                        Timestamp oObsDate = Timestamp.valueOf(scanner.nextLine());
                        float oObsValue = Float.parseFloat(scanner.nextLine());
                        Timestamp oRecDate = Timestamp.valueOf(scanner.nextLine());
                        boolean observationAdded = observation.addObservation(oPatientId, oObsName, oObsType, oObsDate, oObsValue, oRecDate);
                        if (!observationAdded) {
                            System.out.println("Failed to add observation. Ensure that the patient ID is correct.");
                        }
                        break;
                    case 14:
                        System.out.println("Enter PatientID to view observations:");
                        String viewObsPatientId = scanner.nextLine();
                        observation.viewObservations(viewObsPatientId);
                        break;
                    case 15:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } finally {
            scanner.close(); // Ensure the scanner is closed after use
            dbConnector.closeConnection(); // This is hypothetical, depending on actual implementation
        }
    }

    // A safe method to read integers from the input to avoid InputMismatchException
    private static int safeReadInt(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                return Integer.parseInt(scanner.nextLine().trim()); // Read the entire line and parse it as an integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
