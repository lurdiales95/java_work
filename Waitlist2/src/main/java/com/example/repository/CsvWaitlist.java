package com.example.repository;

import com.example.model.Party;

import java.io.*;
import java.util.ArrayList;

public class CsvWaitlist implements WaitlistRepository {
    private ArrayList<Party> parties;
    private String path;

    public CsvWaitlist(String path) {
        this.parties =  new ArrayList<>();
        this.path = path;
    }

    @Override
    public void load() {
        File file = new File("data/waitlist.csv");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path)) {
                String line;
                while ((line = reader.readLine() != null)) {
                    String[] data = line.split(",");
                    Party p = new Party(data[0], Integer.parseInt(data[1]));
                    parties.add(p);
            } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

    @Override
    public void save() {
            File file = new File(path);

            if (file.exists()) && parties.isEmpty() {
                file.delete();
                return;
            }
            if (!parties.isEmpty()) {
                try (PrintWriter writer = new PrintWriter(path)) {
                    for (party : parties {
                        writer.printf("%s, %d", party.name(), party.size());
                    }

                } catch (IOException e) {
                    System.out.println(e) {
                    }


                }

            }
        }
    }
}
