package org.example.objects;

import java.io.*;
import java.util.ArrayList;

public class DoctorRatingManager {
    private static final String RATINGS_FILE = "doctor_ratings.txt";


    // Сохранить данные рейтинга
    public static void saveRatings(ArrayList<Doctor> doctors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RATINGS_FILE))) {
            for (Doctor doctor : doctors) {
                for (Doctor.Rating rating : doctor.getRatings()) {
                    String line = doctor.getId() + "," + rating.getRating() + "," + rating.getOwner() + "," + rating.getComment();
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Загрузить данные рейтинга
    public static ArrayList<Doctor> loadRatings(ArrayList<Doctor> doctors) {
        File file = new File(RATINGS_FILE);
        if (file.exists()) {
            System.out.println("Файл успешно создан: " + file.getAbsolutePath());
        } else {
            System.out.println("Не удалось создать файл: " + file.getAbsolutePath());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(RATINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                long doctorId = Long.parseLong(parts[0]);
                int rating = Integer.parseInt(parts[1]);
                String owner = parts[2];
                int comment = Integer.parseInt(parts[3]);

                Doctor doctor = findDoctor(doctors, doctorId);
                if (doctor != null) {
                    doctor.getRatings().add(new Doctor.Rating(rating, owner));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    private static Doctor findDoctor(ArrayList<Doctor> doctors, long doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == doctorId) {
                return doctor;
            }
        }
        return null;
    }
}