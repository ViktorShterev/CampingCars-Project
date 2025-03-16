package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.AdminBindingModel;
import bg.softuni.campingcars.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/add")
    public ModelAndView addAdmin(@ModelAttribute("adminBindingModel") AdminBindingModel adminBindingModel) {
        return new ModelAndView("admin-add");
    }

    @PutMapping("/add")
    public ModelAndView addAdmin(@ModelAttribute("adminBindingModel")
                                 @Valid AdminBindingModel adminBindingModel,
                                 BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("admin-add");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("adminBindingModel", adminBindingModel);
            modelAndView.addObject("org.springframework.validation.BindingResult.adminBindingModel", bindingResult);
            return modelAndView;
        }

        boolean added = this.adminService.addAdmin(adminBindingModel);

        if (!added) {
            modelAndView.addObject("hasErrors", true);
            modelAndView.addObject("adminBindingModel", adminBindingModel);
            return modelAndView;
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/remove")
    public ModelAndView getAdmin(@ModelAttribute("adminBindingModel") AdminBindingModel adminBindingModel) {
        return getAllAdmins();
    }

    @PutMapping("/remove")
    public ModelAndView removeAdmin(@ModelAttribute("adminBindingModel")
                                    @Valid AdminBindingModel adminBindingModel,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return getAllAdmins();
        }

        this.adminService.removeAdmin(adminBindingModel);

        return new ModelAndView("redirect:/");
    }

    private ModelAndView getAllAdmins() {
        List<AdminBindingModel> allAdmins = this.adminService.getAllAdmins();

        ModelAndView modelAndView = new ModelAndView("admin-remove");
        modelAndView.addObject("allAdmins", allAdmins);

        return modelAndView;
    }
}
