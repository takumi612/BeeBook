package beebooks.controller.customer;

import beebooks.entities.Contact;
import beebooks.entities.User;
import beebooks.service.ContactService;
import beebooks.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ContactController{
	
	private final ContactService contactService;
	private final MailService mailService;

	@RequestMapping(value = { "/contact" }, method = RequestMethod.GET)
	public String contact(final Model model)
			throws IOException {

		Contact contact = new Contact();
		model.addAttribute("contact", contact);
		return "customer/contact";
	}


	@RequestMapping(value = { "/contact" }, method = RequestMethod.POST)
	public String addContact(final Model model,
							 @ModelAttribute("userLogined") User userLogined,
							 final @ModelAttribute("contact") Contact contact) throws IllegalStateException{
		model.addAttribute("TB", "Cảm ơn " + contact.getName() + ", chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất!");
		model.addAttribute("contact", "");
		contactService.save(contact);
		//gửi email thông báo
		String to = contact.getEmail();
		String subject = "XÁC NHẬN " + contact.getName() + " ĐÃ ĐĂNG KÝ LIÊN HỆ THÀNH CÔNG!";
		String text = "Chúng tôi sẽ liên hệ bạn trong thời gian sớm nhất. Xin cảm ơn"  + ".";
		mailService.sendEmailAsync(to, subject, text);

		return "customer/contact";

	}

	@RequestMapping(value = { "/sendContact"}, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> sendContact(final Model model,
														   @ModelAttribute("userLogined") User userLogined,
														   final @RequestBody Contact contact) {
		Map<String, Object> jsonResultCt = new HashMap<>();
		model.addAttribute("contact", "");

		Contact contacts = contactService.checkEmailContact(contact);
		if(contacts == null) {
			contactService.save(contact);
			jsonResultCt.put("messages", "Cảm ơn, " + contact.getEmail() + " đã gửi liên hệ thành công cho Beebooks!");
		} else {
			jsonResultCt.put("message", "Bạn chưa nhập email / Trùng email");
		}
		return ResponseEntity.ok(jsonResultCt);
	}
}
