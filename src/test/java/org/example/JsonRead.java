package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class JsonRead extends BaseDriver {

    private static final String JSON_FILE_PATH = "C://Users//user//IdeaProjects//VATPROMPT//src//testdata//GoodsServiceSetup.json";

    @Test
    public void performActionsFromJson() throws InterruptedException, IOException {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Thread.sleep(2000);
        driver.get("http://192.168.10.15:81/LogIn.aspx");

        DriverUtility util = new DriverUtility(driver);
        util.InputIdValue("ContentPlaceHolder1_txtUserId", "tcl");


        util.InputIdValue("ContentPlaceHolder1_txtPassword", "123");


        WebElement login = driver.findElement(By.id("ContentPlaceHolder1_btnLogin"));
        login.click();


        util.selectByVisibleBothText("ContentPlaceHolder1_drpSchema", "Runners Motors Limited");


        util.GoButtonId("ContentPlaceHolder1_btnBusinessUnit");
        Thread.sleep(2000);


        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray actions = jsonObject.getJSONArray("actions");

        for (int i = 0; i < actions.length(); i++) {
            JSONObject action = actions.getJSONObject(i);
            String Type = action.getString("Type");
            String locators = action.getString("locators");
            String value = action.optString("value", "");

            switch (Type) {
                case "click":
                    driver.findElement(By.xpath(locators)).click();
                    Thread.sleep(1000);
                    break;
                case "textbox":
                    Thread.sleep(1000);
                    driver.findElement(By.xpath(locators)).clear();
                    Thread.sleep(1000);
                    driver.findElement(By.xpath(locators)).sendKeys(value);
                    Thread.sleep(1000);
                    break;
                case "alert":
                    Thread.sleep(1000);
                    driver.switchTo().alert().accept();
                    Thread.sleep(1000);
                    break;
                default:
                    break;
            }
        }


        driver.quit();
    }
}
