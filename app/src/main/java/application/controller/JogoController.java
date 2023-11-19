package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import application.model.Plataforma;
import application.repository.PlataformaRepository;

@Controller
@RequestMapping("/jogo")
public class jogoController {
    @Autowired
    private PlataformaRepository jogoRepo;

    @RequestMapping("/list")
    public String list(Model ui) {
        ui.addAttribute("jogos", jogoRepo.findAll());
        return "jogo/list";
    }

    @RequestMapping("/insert")
    public String insert() {
        return "jogo/insert";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam("nome") String nome) {
        Plataforma jogo = new Plataforma();
        jogo.setNome(nome);
        jogoRepo.save(jogo);
        return "redirect:/jogo/list";
    }

    @RequestMapping("/update")
    public String update(
        @RequestParam("id") long id,
        Model ui) {
            Optional<Plataforma> jogo = jogoRepo.findById(id);
            if(jogo.isPresent()) {
                ui.addAttribute("jogo", jogo.get());
                return "jogo/update";
            }
        return "redirect:/jogo/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
        @RequestParam("id") long id,
        @RequestParam("nome") String nome) {
            Optional<Plataforma> jogo = jogoRepo.findById(id);
            if(jogo.isPresent()) {
                jogo.get().setNome(nome);
                jogoRepo.save(jogo.get());
            }
            return "redirect:/jogo/list";
    }

    @RequestMapping("/delete")
    public String delete(
        @RequestParam("id") long id,
        Model ui) {
            Optional<Plataforma> jogo = jogoRepo.findById(id);
            if(jogo.isPresent()) {
                ui.addAttribute("jogo", jogo.get());
                return "jogo/delete";
            }
            return "redirect:/jogo/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") long id) {
        jogoRepo.deleteById(id);
        return "redirect:/jogo/list";
    }
}