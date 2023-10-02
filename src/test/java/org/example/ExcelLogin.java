package org.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.github.dockerjava.api.model.Identifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Test
public class ExcelLogin extends BaseDriver {

    private static final String EXCEL_FILE_PATH = "C:\\Users\\user\\IdeaProjects\\VATPROMPT\\src\\testdata\\Test4.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    public void excelRead() throws InterruptedException, IOException, InvalidFormatException {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Thread.sleep(2000);
        driver.get("http://192.168.10.15:81/LogIn.aspx");


        File file = new File(EXCEL_FILE_PATH);
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(SHEET_NAME);


        Row identifier=sheet.getRow(2);
        Row type=sheet.getRow(1);


        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();


        for (int i = 3; i <= rowCount; i++) {
            Row testCase= sheet.getRow(i);

            for(int j = 0;j<=colCount;j++)
            {
                Cell cellI = identifier.getCell(j);
                Cell cellT = type.getCell(j);
                Cell cellv = testCase.getCell(j);

                if(cellI==null || cellI.getStringCellValue()=="")break;

                switch(cellT.getStringCellValue()){
                    case "click":
                        driver.findElement(By.id(cellI.getStringCellValue())).click();
                        break;
                    case "textbox":
                        driver.findElement(By.id(cellI.getStringCellValue())).clear();
                        driver.findElement(By.id(cellI.getStringCellValue())).sendKeys(cellv.getStringCellValue());
                        break;

                    default:
                        break;


                }


            }

            Thread.sleep(2000);


        }

        // Close the workbook and input stream
        workbook.close();
        fis.close();
    }
}

