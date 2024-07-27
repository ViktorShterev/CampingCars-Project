package bg.softuni.campingcars.service.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidGeneratorService {
    public UUID generateUuid() {
        return UUID.randomUUID();
    }
}
