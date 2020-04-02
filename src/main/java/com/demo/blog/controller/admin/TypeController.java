package com.demo.blog.controller.admin;

import com.demo.blog.model.Type;
import com.demo.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/findAlltypes")
    public String types(@PageableDefault(size = 5, sort = {"id"},
            direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("types/input")
    public String inputPage(Model model) {
        Type type = new Type();
        model.addAttribute("type",type);
        return "admin/types-input";
    }
    @GetMapping("types/{id}/input")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Type typeById = this.typeService.getTypeById(id);
        model.addAttribute("type",typeById);
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String  saveType(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //已存在该名称(校验)
        Type type1 = typeService.getTypeByname(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){//非空校验（后端）
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/findAlltypes";
    }

    @PostMapping("/types/{id}")
    public String  editType(@PathVariable Long id,@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //已存在该名称(校验)
        Type type1 = typeService.getTypeByname(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){//非空校验（后端）
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/findAlltypes";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteTypeById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/findAlltypes";
    }
}