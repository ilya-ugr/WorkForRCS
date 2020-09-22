package nalogRf.controller;

import nalogRf.entity.NalogTable;
import nalogRf.service.SqlBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private SqlBaseImpl sqlBase;

    @Autowired
    public Controller(SqlBaseImpl sqlBase) {
        this.sqlBase = sqlBase;
    }

    @GetMapping(value = "/", produces = {"application/json; charset=UTF-8"})
    public List<NalogTable> request(@RequestParam String row, @RequestParam String col) {
        return sqlBase.getResponse(row, col);
    }
}
