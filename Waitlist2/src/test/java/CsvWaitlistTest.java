package com.example;

import com.example.model.Party;
import com.example.repository.CsvWaitlist;
import com.example.repository. InMemoryWaitlist;
import com.example.repository.WaitlistRepository;
import com.example.service.WaitlistService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CsvWaitlistTest {
    @Test

    public void waitlistInitliazedAsEmpty() {
        WaitlistService wl = new WaitlistService(new CsvWaitlist("data/waitlist.csv"));

        assertTrue(wl.getList().isEmpty());
    }

    @Test
    public void canAddParty() {
        WaitlistService wl = new WaitlistService(new CsvWaitlist("data/waitlist.csv"));
        Party p = new Party("Wise", 3);
        wl.addParty(p);

        assertEquals(1, wl.getList().size());
        assertEquals (p, wl.getList().get(0));
    }
@Test
    public void canReloadData (){
    WaitlistService wl = new WaitlistService(new CsvWaitlist("data/waitlist.csv"));
    Party p = new Party("Wise", 3);
    wl.addParty(p);

    WaitlistService wl2 = new WaitlistService(new CsvWaitlist());
    assertEquals (1, wl2.getList().size());
    assertEquals (p, wl2.getList().get(0));
}



}
