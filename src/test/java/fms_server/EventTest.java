package fms_server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    void test() {
        Event event = new Event(1, "dad", 2, -111.45, 40.256,
                "USA", "Provo", "random", 2019);
        Event event2 = new Event(1, "dad", 2, -111.45, 40.256,
                "USA", "Provo", "random", 2019);
        assertEquals(event, event);
    }
}
