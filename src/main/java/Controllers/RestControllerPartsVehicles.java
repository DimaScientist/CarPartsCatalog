package Controllers;


import Service.JDBCAnswerPartsVehicles;
import Tables.PartsVehicles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


//Контроллер для partsvehicles: id машины и alpartnum
@Controller
public class RestControllerPartsVehicles {

    @GetMapping(value = "/partsvehicles")
    public @ResponseBody List<PartsVehicles> getData(){
        JDBCAnswerPartsVehicles answer = new JDBCAnswerPartsVehicles();
        return answer.getPartsVehiclesList();
    }
}