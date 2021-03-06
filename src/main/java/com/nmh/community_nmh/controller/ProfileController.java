package com.nmh.community_nmh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nmh.community_nmh.dto.NotificationDTO;
import com.nmh.community_nmh.model.Question;
import com.nmh.community_nmh.model.User;
import com.nmh.community_nmh.service.NotificationService;
import com.nmh.community_nmh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author niminui
 * @date 2020/3/9 15:23
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            PageHelper.startPage(page,size);
            List<Question> questions = questionService.listOfCreator(user.getId());
            PageInfo<Question> pageInfo = new PageInfo<>(questions,5);

            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("questions",questions);
            model.addAttribute("pageInfo",pageInfo);
        } else if ("replies".equals(action)) {
            List<NotificationDTO> notificationDTOS = notificationService.list(user.getId());
            PageInfo<NotificationDTO> pageInfo = new PageInfo<>(notificationDTOS,5);
            PageHelper.startPage(page,size);

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的最新回复");
            model.addAttribute("notificationDTOS",notificationDTOS);
            model.addAttribute("pageInfo",pageInfo);
        }
        return "profile";
    }

}
