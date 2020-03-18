package Controllers;


import Service.JDBCAnswerCountry;
import Tables.Country;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//Контроллер для вывода стран: id, код, название
@RestController
public class RestControllerCountry {

    @GetMapping(value = "/country")
    public List<Country> getData(){
        JDBCAnswerCountry answer = new JDBCAnswerCountry();
        return answer.getListCountries();
    }
}
