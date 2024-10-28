package disco_bracelet.configurations;

import java.util.UUID;

public class CodeGenerator {

    public static String generateUniqueCode(){
        return UUID.randomUUID().toString();
    }
}
