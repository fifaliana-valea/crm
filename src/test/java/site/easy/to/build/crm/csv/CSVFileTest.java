package site.easy.to.build.crm.csv;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.Dto.BudgetDto;
import site.easy.to.build.crm.import_csv.CSVFile;
import site.easy.to.build.crm.import_csv.ConstraintCSV;

import java.io.File;
import java.io.FileInputStream;

public class CSVFileTest {

    @Test
    void importCSV()throws Exception{
//        // Chemin du fichier réel
//        String filePath = "C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\test.csv";
//
//        try {
//            // Charger le fichier en tant que FileInputStream
//            File file = new File(filePath);
//            FileInputStream fileInputStream = new FileInputStream(file);
//
//            // Créer un MultipartFile à partir du FileInputStream
//            MultipartFile multipartFile = new MockMultipartFile(
//                    "file",                             // Nom du fichier
//                    file.getName(),                     // Nom du fichier
//                    "text/csv",                         // Type MIME
//                    fileInputStream                     // Le contenu du fichier
//            );
//
//            // Exemple d'utilisation
//            CSVFile<BudgetDto> csvFile = new CSVFile<>(multipartFile,";");
//            csvFile.addConstraint("duree", ConstraintCSV.INT_POSITIVE)
//                    .addConstraint("date", ConstraintCSV.LOCALDATE)
//                    .addConstraint("heure_debut",ConstraintCSV.LOCAL_TIME)
//                    .addConstraint("option",ConstraintCSV.LIST_FOREIGN);
//            csvFile.readAndTransform(v->
//                new BudgetDto(
//                (String)v.get("ref"),
//                (String)v.get("espace")
//            );
//
//            System.out.println("VITA");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
