package Welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity = new UserEntity();

    @RequestMapping("/index")
    public String index(HttpSession httpSession) {
        httpSession.getAttribute("userLogin");
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/uregister")
    public String register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String str = "";
        if (password.equals(password2)) {
            userEntity=userRepository.findByUsername(username);
            if (userEntity == null) { //如果用户名不存在，创建新的用户信息并保存,进入登录页面
                userEntity = new UserEntity(username, password);
                userRepository.save(userEntity);
                str = "login";
            }else { //如果用户名已经存在，仍然在注册页面
                str = "register";
            }

        }else { //如果注册时两次输入的密码不一样，仍在注册页面
            str = "register";
        }
        return str;
    }

    @RequestMapping("/login")
    public String login() {
        String str = "";
        String username = userEntity.getUsername();
        if (username != null) { //如果数据库中能查到用户名，进入index界面
            str = "index";
        }else {
            str = "login"; //数据库中不存在该用户名，仍然在登录界面
        }
        return str;
    }

    @RequestMapping("/ulogin")
    public String login(HttpServletRequest request, HttpSession session) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        userEntity = userRepository.findByUsernameAndPassword(username, password);
        String str = "";
        if (userEntity != null) {
            session.setAttribute("userLogin", userEntity);
            str = "index";
        }else {
            str = "login";
        }
        return str;
    }
}
