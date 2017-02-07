package demo.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class Control {
	@RequestMapping("/client.action")
	public void toHtml(Model model){
		System.out.println(11111);
	}
}
