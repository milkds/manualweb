package manualweb.controller;


import manualweb.model.Manual;
import manualweb.model.ManualFilter;
import manualweb.service.ManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class manualController {

    private ManualService manualService;

    @Autowired(required = true)
    @Qualifier (value = "manualService")
    public void setManualService(ManualService manualService) {
        this.manualService = manualService;
    }

    @RequestMapping(value = "manuals", method = RequestMethod.GET)
    public String listManuals (Model model){
        model.addAttribute("manual", new Manual());
        ManualFilter manualFilter = this.manualService.listManuals();
        model.addAttribute("filter", new ManualFilter());
        model.addAttribute("listManuals", manualFilter);
        return "manuals";
    }

    @RequestMapping("filter")
    public String filterManuals (Model model, @ModelAttribute ("filter") ManualFilter filter){
        model.addAttribute("manual", new Manual());
        ManualFilter mf = this.manualService.filterManuals(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("listManuals", mf);

        return "manuals";
    }

    @RequestMapping(value = "manuals/add", method = RequestMethod.POST)
    public String addManual (@ModelAttribute ("manual") Manual manual){
        if (manual.getId()==0){
            this.manualService.addManual(manual);
        }
        else {
            this.manualService.updateManual(manual);
        }

        return "redirect:/manuals";
    }

    @RequestMapping("/remove/{id}")
    public String removeManual(@PathVariable("id") int id){
        this.manualService.removeManual(id);

        return "redirect:/manuals";
    }

    @RequestMapping("edit/{id}")
    public String editManual(@PathVariable("id") int id, Model model){
        model.addAttribute("manual", this.manualService.getManualById(id));
        model.addAttribute("listManuals", this.manualService.listManuals());
        model.addAttribute("filter", new ManualFilter());

        return "manuals";
    }

    @RequestMapping("manualdata/{id}")
    public String manualData(@PathVariable("id") int id, Model model){
        model.addAttribute("manual", this.manualService.getManualById(id));

        return "manualdata";
    }


}
