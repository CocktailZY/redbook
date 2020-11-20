package io.github.nihadguluzade.redbook.controller;

import io.github.nihadguluzade.redbook.dao.PageRepository;
import io.github.nihadguluzade.redbook.entity.SubmissionEntity;
import io.github.nihadguluzade.redbook.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/create")
    public String submit(Model model) {
        model.addAttribute("metaTitle", "Create for Redbook");
        model.addAttribute("submission", new SubmissionEntity());
        model.addAttribute("pageList", pageRepository.findAll());
        return "submission/submit-form";
    }

    @PostMapping("/postSubmission")
    public String postSubmission(@ModelAttribute("submission") SubmissionEntity submission) {
        if (submission.getPageId() == 0) {
            return "redirect:/submission/create?error=zeropage";
        }

        submission.setPageByPageId(pageRepository.findById(submission.getPageId()));
        submissionService.save(submission);
        return "redirect:/";
    }
}
