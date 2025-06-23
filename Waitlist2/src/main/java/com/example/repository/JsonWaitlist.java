package com.example.repository;
import com.example.model.Party;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.dartamind.ObjectMapper;

public class JsonWaitlist implements WaitlistRepository {
    private ArrayList<Party> parties;
    private final String path;
    private ObjectMapper mapper;

    public JsonWaitlist(String path) {
        this.path = path;
        parties = new ArrayList<>();
        mapper = new ObjectMapper();
        load();
    }

    @Override
    public void load() {
        try {
            parties = mapper.readValue(new File(path), new TypeReference<ArrayList<Party>>() { });

        } catch (IOException e) {
            System.out.println(e);
        }

    }
    @Override
    public void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), parties);
            catch(IOException e){
                System.out.println(e);
            }
        }


    }
}

    @Override
    public void add(Party party) {


    }
}
