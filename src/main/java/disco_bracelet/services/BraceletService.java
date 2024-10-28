package disco_bracelet.services;


import com.google.zxing.WriterException;
import disco_bracelet.configurations.BarCodeGenerator;
import disco_bracelet.configurations.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.repositories.BraceletRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BraceletService {

    @Autowired
    private BraceletRepository braceletRepository;

    public BraceletEntity createNewBracelet (LocalDate yearOfProduction) throws WriterException, IOException {
        BraceletEntity bracelet = new BraceletEntity();
        bracelet.setYearOfProduction(yearOfProduction);

        //generisi jedinstveni barkod
        String uniqueCode = CodeGenerator.generateUniqueCode();
        bracelet.setBarCode(uniqueCode);

        //generisi novi i sacuvaj barkod
        BarCodeGenerator.generateBarCode(uniqueCode);

        braceletRepository.save(bracelet);
        return bracelet;


    }

}

