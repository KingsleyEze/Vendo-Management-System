package org.ng.undp.vdms.services;

/**
 * Created by Samuel on 10/18/2016.
 */

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.interfaces.importService;
import org.ng.undp.vdms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;



public class ImportService implements importService {

    private final UserRepository repository;
    private int errorCount = 0;
    private String iError = null;

    @Autowired
    public ImportService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public int importUsers(String filePath, String userType){
        //ByteArrayInputStream bis = new ByteArrayInputStream(fileBean.getFileData().getBytes());
        InputStream ExcelFileToRead = null;
        String fileExtension = null;
        if(filePath.contains(".xlsx")){
            fileExtension = "xlsx";
        }else if(filePath.contains(".xls")){
            fileExtension = "xls";
        }else{
            this.iError = "Invalid excel file format.";
            return 0;
        }
        try{
            ExcelFileToRead = new FileInputStream(filePath);
        }catch(Exception e){

        }
        HSSFWorkbook workbook = null;
        XSSFWorkbook workbook2 = null;
        try {
            if (fileExtension== "xlsx"/*ExcelFileToRead.endsWith("xlsx")*/) {
                workbook2 = new XSSFWorkbook(ExcelFileToRead);

            } else if (fileExtension == "xls"/*ExcelFileToRead.con("xls")*/) {
                workbook = new HSSFWorkbook(ExcelFileToRead);
            } else {
                throw new IllegalArgumentException("Received file does not have a standard excel extension.");
            }

            //set DataFormatter
            DataFormatter formatter = new DataFormatter();

            if(workbook != null){
                //handle office 97 to 2007
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row;
                HSSFCell cell;
                Iterator rows = sheet.rowIterator();
                while (rows.hasNext())
                {
                    row=(HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    int index = 0;
                    User user = new User();
                    while (cells.hasNext())
                    {
                        cell=(HSSFCell) cells.next();

                        if(index == 0){
                            user.setLastname(cell.getStringCellValue());
                        }
                        else if(index == 1){
                            user.setFirstname(cell.getStringCellValue());
                        }
                        else if(index == 2){
                            user.setEmail(cell.getStringCellValue());
                        }
                        else if(index == 3){
                            user.setUsername(cell.getStringCellValue());
                        }
                        else if(index == 4){
                            user.setPassword(cell.getStringCellValue());
                        }

                        else
                        {
                            //U Can Handel Boolean, Formula, Errors
                        }
                        index += 1;
                    }
                    if(userType == "consultant"){
                        user.setUserTypes(new String[]{UserType.CONSULTANT.toString()});
                    }
                    if(userType == "unstaff"){
                        user.setUserTypes(new String[]{UserType.UNSTAFF.toString()});
                    }

                    if(userType == "ngo"){
                        user.setUserTypes(new String[]{UserType.NGO.toString()});
                    }

                    if(userType == "supplier"){
                        user.setUserTypes(new String[]{UserType.SUPPLIER.toString()});
                    }
                    System.out.println("user type: "+userType);
                    index = 0;
                    //do the insertion into the database
                    if(user.getUsername() != "" && repository.findOneByUsername(user.getUsername()) == null){
                        repository.save(user);
                    }

                }
                return withProcessStatus();
            }else if(workbook2 != null){
                //handle the latest office

                XSSFSheet sheet = workbook2.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;

                Iterator rows = sheet.rowIterator();

                while (rows.hasNext())
                {
                    row=(XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    int index2 = 0;
                    User user = new User();
                    String usernameForInsert = "";
                    while (cells.hasNext())
                    {
                        cell=(XSSFCell) cells.next();

                        if(index2 == 0){
                            user.setLastname(cell.getStringCellValue());
                        }
                        else if(index2 == 1){
                            user.setFirstname(cell.getStringCellValue());
                        }
                        else if(index2 == 2){
                            user.setEmail(cell.getStringCellValue());
                        }
                        else if(index2 == 3){
                            user.setUsername(cell.getStringCellValue());
                        }
                        else if(index2 == 4){
                            user.setPassword(cell.getStringCellValue());
                        }

                        else
                        {
                            //U Can Handel Boolean, Formula, Errors
                        }
                        index2 += 1;
                    }
                    if(userType == "consultant"){
                        user.setUserTypes(new String[]{UserType.CONSULTANT.toString()});
                    }
                    if(userType == "unstaff"){
                        user.setUserTypes(new String[]{UserType.UNSTAFF.toString()});
                    }

                    if(userType == "ngo"){
                        user.setUserTypes(new String[]{UserType.NGO.toString()});
                    }



                    if(userType == "supplier"){
                        user.setUserTypes(new String[]{UserType.SUPPLIER.toString()});
                    }
                    index2 = 0;
                    if(user.getUsername() != "" && repository.findOneByUsername(user.getUsername()) == null){
                        repository.save(user);
                    }
                }
                return withProcessStatus();
            }else{
                //do the rest processing
            }

        } catch (IOException e) {
            e.printStackTrace();
            this.iError = "Error: "+e.getMessage();
        }
        return 0;
    }

    public boolean hasError(){
        if(this.iError != null){
            return true;
        }
        return false;
    }

    private int withProcessStatus(){
        if(this.hasError()){
            return 0;
        }
        return 1;
    }

    //end of class
}
