package com.lcaohoanq.fucar.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lcaohoanq.fucar.dtos.CarRentalDTO;
import com.lcaohoanq.fucar.models.CarRental;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class WriteFileHandler {

    public static void writeFileObject(
           List<CarRental> filteredList,
           CarRental carRental,
           String destination
    ) {
        try{
            File f = new File(destination);
            FileOutputStream fOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            for(CarRental item : filteredList){
                out.writeObject(item);
            }
            out.close();
            fOut.close();
            AlertHandler.showAlert("Success", "Report data exported successfully.");
        }catch(Exception e) {
            AlertHandler.showAlert("Error", "Error exporting report data.");
            System.out.println("Error exporting report data: " + e.getMessage());
        }
    }

    public static void writeJsonFile(
        List<CarRentalDTO> inputData,
        String destination
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Register the JavaTimeModule to handle LocalDate and other Java 8 date/time types
            mapper.registerModule(new JavaTimeModule());

            File f = new File(destination);

            // Write the filtered list as JSON to the file
            mapper.writeValue(f, inputData);

            AlertHandler.showAlert("Success", "Report data exported successfully.");
        } catch (IOException e) {
            AlertHandler.showAlert("Error", "Error exporting report data.");
            System.out.println("Error exporting report data: " + e.getMessage());
        }
    }

}
