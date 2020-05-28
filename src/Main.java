package fr.esgi.application;

import java.sql.*;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.nimbus.State;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, SQLException { //JVM will show the error (not likely to on windows or linux)

        String[] listClients = new String[getSizeClient()];
        String[] nameJobs = new String[getSizeJob() + 1];
        String[] serviceProviderName = new String[getSizeServiceProvider() + 1];
        String[] categoryName = new String[getSizeCategory()];
        String[] serviceName = new String[getSizeService(categoryName)];
        String[] listIdUser = new String[getSizeReservation(categoryName,serviceName)+1];
        String[] listIdNomiation = new String[listIdUser.length];
        String[] listInterventions = new String[getListInterventions()];

        int i = 0;
        int j = 0;

        //BDD connection
        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            //Get Job's name
            ResultSet job = req.executeQuery("SELECT * FROM job");
            //Show results Jobs
            i = 1;
            nameJobs[0] = "Toutes les professions";
            while (job.next()) {
                nameJobs[i] = job.getString("name");
                i++;
            }
            i = 0;

            //Get ServiceProvider's nomination
            ResultSet serviceProvider = req.executeQuery("SELECT * FROM serviceprovider");
            //Show results Jobs
            while (serviceProvider.next()) {
                serviceProviderName[i] = serviceProvider.getString("lastName") + " " + serviceProvider.getString("firstName");
                i++;
            }
            i = 0;

            //Get Category's name
            ResultSet category = req.executeQuery("SELECT * FROM SERVICE");
            //Show results Jobs
            while (category.next()) {
                categoryName[i] = category.getString("name");
                i++;
            }


            //serviceName[1] = "Tous les services";
            i = 0;
            //Get Service's name
            for (j = 0; j < categoryName.length; j++) {
                ResultSet service = req.executeQuery("SELECT * FROM " + categoryName[j]);
                while ( service.next() ) {
                    serviceName[i] = service.getString("name");
                    i++;
                }
            }
            i = 0;

            //Get idUser
            for (j = 0; j < serviceName.length; j++) {
                ResultSet idUserName = req.executeQuery("SELECT * FROM " + serviceName[j] + " WHERE idUser IS NOT NULL ");
                while ( idUserName.next() ) {
                    listIdUser[i] = idUserName.getString("idUser");
                    i++;
                }
            }
            i = 0;

            //Get reservation nomination
            for (int k = 0; k < listIdUser.length ; k++) {
                ResultSet idNomination = req.executeQuery("SELECT * FROM client WHERE id = " + listIdUser[k]);
                while ( idNomination.next() ) {
                    listIdNomiation[i] = idNomination.getString("lastName") + " " + idNomination.getString("firstName");
                    i++;
                }
            }


        } catch (Exception exc) {
            exc.printStackTrace();
        }


        //Apply look'n feel on window
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        //Start the window
        MyWindow myWindow = new MyWindow(listIdNomiation, nameJobs, serviceProviderName, serviceName, listInterventions);
        myWindow.setVisible(true); //make the window appear

    }

    public static int getSizeClient(){
        int clientLength = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            //Get column table client
            ResultSet sizeClient = req.executeQuery("SELECT COUNT(*) FROM client");
            while (sizeClient.next()) {
                clientLength = sizeClient.getInt(1);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return clientLength;
    }

    public static int getSizeJob(){
        int jobLength = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            //Get column table job
            ResultSet sizeJob = req.executeQuery("SELECT COUNT(*) FROM job");
            while (sizeJob.next()) {
                jobLength = sizeJob.getInt(1);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return jobLength;
    }

    public static int getSizeServiceProvider(){
        int ServiceProviderLength = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            //Get column table serviceprovider
            ResultSet sizeServiceProvider = req.executeQuery("SELECT COUNT(*) FROM job");
            while (sizeServiceProvider.next()) {
                ServiceProviderLength = sizeServiceProvider.getInt(1);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return ServiceProviderLength;
    }

    public static int getSizeCategory(){
        int serviceLength = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            //Get column table service
            ResultSet sizeService = req.executeQuery("SELECT COUNT(*) FROM service");
            while ( sizeService.next() ) {
                serviceLength = sizeService.getInt(1);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return serviceLength;
    }

    public static int getSizeService(String[] categoryName){
        int ServiceNameLength = 0;
        int i = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            ResultSet category = req.executeQuery("SELECT * FROM SERVICE");
            //Show results Jobs
            while (category.next()) {
                categoryName[i] = category.getString("name");
                i++;
            }

            //Get column table serviceName
            for(int j = 0; j < categoryName.length; j++) {
                ResultSet sizeServiceName = req.executeQuery("SELECT COUNT(*) FROM " + categoryName[j]);
                while ( sizeServiceName.next() ) {
                    ServiceNameLength += sizeServiceName.getInt(1);
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return ServiceNameLength;
    }

    public static int getSizeReservation(String[] categoryName, String[] serviceName){
        int reservationLength = 0;
        int i = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            ResultSet category = req.executeQuery("SELECT * FROM SERVICE");
            //Show results Jobs
            while (category.next()) {
                categoryName[i] = category.getString("name");
                i++;
            }
            i = 0;

            //Get column table serviceName
            for(int j = 0; j < categoryName.length; j++) {
                ResultSet sizeServiceName = req.executeQuery("SELECT * FROM " + categoryName[j]);
                while ( sizeServiceName.next() ) {
                    serviceName[i] = sizeServiceName.getString("name");
                    i++;
                }
            }

            //Get column reservation
            for (int j = 0; j < serviceName.length - 1; j++) {
                ResultSet idUser = req.executeQuery("SELECT COUNT(*) FROM " + serviceName[j] + " WHERE idUser IS NOT NULL");
                while (idUser.next()) {
                    reservationLength += idUser.getInt(1);
                    i++;
                }
            }


        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return reservationLength;
    }

    public static int getListInterventions(){
        int interventionsLength = 0;

        try {
            //Connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/concierge_expert?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tedanvi", "kLKLxEe8M1EfOdvG");

            //Create statement
            Statement req = con.createStatement();

            //Get column table client
            ResultSet sizeIntervention = req.executeQuery("SELECT COUNT(*) FROM intervention");
            while (sizeIntervention.next()) {
                interventionsLength = sizeIntervention.getInt(1);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return interventionsLength;
    }
}

