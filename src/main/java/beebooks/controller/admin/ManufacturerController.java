package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Manufacturer;
import beebooks.entities.User;
import beebooks.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ManufacturerController extends BaseController {

    private final ManufacturerService manufacturerService;

    @RequestMapping(value = {"/admin/manufacturer/list"}, method = RequestMethod.GET)
    public String getManufacturer(final Model model,
                                  @ModelAttribute("searchModel") Search searchModel) {

        model.addAttribute("manufacturerPage", manufacturerService.search(searchModel));
        model.addAttribute("searchModel", searchModel);

        return "admin/manufacturer";
    }

    @RequestMapping(value = {"/admin/manufacturer/add"}, method = RequestMethod.GET)
    public String viewAddManufacture(final Model model) {

        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("manufacturer", manufacturer);
        return "admin/add-manufacturer";
    }


    @RequestMapping(value = {"/admin/manufacturer/adds"}, method = RequestMethod.POST)
    public String saveManufacturer(final @ModelAttribute("manufacturer") Manufacturer manufacturer,
                                   @ModelAttribute("userLogined") User userLogined) {
        manufacturerService.save(manufacturer);
        return "redirect:/admin/manufacturer/list";
    }

    @RequestMapping(value = {"/admin/manufacturer/update/{manufacturerId}"}, method = RequestMethod.GET)
    public String viewEditManufacturer(final Model model, @PathVariable("manufacturerId") int manufacturerId) {
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId).orElse(null);
        model.addAttribute("manufacturer", manufacturer);

        return "admin/add-manufacturer";
    }
}
