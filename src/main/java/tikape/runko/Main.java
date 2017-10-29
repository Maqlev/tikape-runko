package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Annos;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.AnnosRaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:reseptit.db");
        database.init();

        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao aineDao = new RaakaAineDao(database);
        AnnosRaakaAineDao annosAineDao = new AnnosRaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("ainekset", aineDao.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());
        
        post("/annokset", (req, res) -> {
            if (req.queryParams("lisaaAnnos") != null) {
                Annos annos = new Annos(-1, req.queryParams("name"));
                annosDao.saveOrUpdate(annos);
            } else if (req.queryParams("lisaaAnnosRaakaAine") != null) {
                if (!req.queryParams("jarjestys").isEmpty() && !req.queryParams("maara").isEmpty()) {
                    AnnosRaakaAine annosRaakaAine = new AnnosRaakaAine(
                            Integer.parseInt(req.queryParams("annosId")),
                            Integer.parseInt(req.queryParams("raakaAineId")),
                            Integer.parseInt(req.queryParams("jarjestys")),
                            req.queryParams("maara"),
                            req.queryParams("ohje"));
                    annosAineDao.saveOrUpdate(annosRaakaAine);
                }
            }
            res.redirect("/annokset");
            return "";
        });
        
        post("/annokset/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            annosDao.delete(id);
            
            res.redirect("/annokset");
            return "";
        });

        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("annosAine", annosAineDao.findAineListaus(Integer.parseInt(req.params("id"))));
            
            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", aineDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        post("/ainekset", (req, res) -> {
            RaakaAine aines = new RaakaAine(-1, req.queryParams("nimi"));
            aineDao.saveOrUpdate(aines);

            res.redirect("/ainekset");

            return "";
        });
        
        post("/ainekset/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            aineDao.delete(id);
            
            res.redirect("/ainekset");
            return "";
        });
    }
}
