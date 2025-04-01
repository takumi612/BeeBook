package beebooks.controller.customer;

import beebooks.controller.BaseController;
import beebooks.dto.SaleOrderDto;
import beebooks.ultilities.searchUtil.OrderSearch;
import beebooks.entities.User;
import beebooks.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class TransactionHistoryController extends BaseController {

    private final SaleOrderService saleorderService;

    @RequestMapping(value = {"/user/transactionHistory"}, method = RequestMethod.GET)
    public String getTransactionLog(final Model model,
                                    @ModelAttribute("userLogined") User user,
                                    @ModelAttribute("searchModel")OrderSearch searchModel
    ) {

        searchModel.setCreateBy(user.getUsername());
        Page<SaleOrderDto> saleOrders = saleorderService.search(searchModel);

        model.addAttribute("orderPage", saleOrders);
        model.addAttribute("searchModel", searchModel);

        return "customer/transactionHistory";
    }
}
