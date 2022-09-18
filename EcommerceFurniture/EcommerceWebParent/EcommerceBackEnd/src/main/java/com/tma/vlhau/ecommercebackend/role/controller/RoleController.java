package com.tma.vlhau.ecommercebackend.role.controller;

import com.tma.vlhau.ecommercebackend.brand.service.BrandService;

import com.tma.vlhau.ecommercebackend.role.exporter.RoleCSVExporter;
import com.tma.vlhau.ecommercebackend.role.service.RoleService;
import com.tma.vlhau.ecommercecommon.entity.Role;
import com.tma.vlhau.ecommercecommon.exception.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/roles")
    public String listFirstPage(@Param("sortDir") String sortDir, Model model) {
        return listByPage(model, 1, sortDir, null);
    }

    @GetMapping("/roles/page/{pageNum}")
    private String listByPage(Model model, @PathVariable(name = "pageNum") int pageNum,
                              @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword) {
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";
        }

        Page<Role> listRolesPage = roleService.listByPage(pageNum, sortDir, keyword);
        List<Role> listRoles = listRolesPage.getContent();

        long startCount = (long) (pageNum - 1) * RoleService.ROLES_PER_PAGE + 1;
        long endCount = startCount + RoleService.ROLES_PER_PAGE - 1;

        if (endCount > listRolesPage.getTotalElements()) {
            endCount = listRolesPage.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("sortOrder", sortDir);
        model.addAttribute("reverseSortOrder", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", listRolesPage.getTotalElements());
        model.addAttribute("totalPages", listRolesPage.getTotalPages());
        model.addAttribute("sortField", "name");

        model.addAttribute("module", "roles");

        return "role/roles";
    }

    @GetMapping("roles/new")
    public String newBrand(Model model) {

        model.addAttribute("role", new Role());
        model.addAttribute("pageTitle", "Create New Brand");

        return "role/role_form";
    }

    @PostMapping("roles/save")
    public String saveRole(Role role, RedirectAttributes redirectAttributes){

        roleService.save(role);

        redirectAttributes.addFlashAttribute("message", "The role was saved successfully");

        return getRedirectURLToAffectBrand(role);
    }

    private String getRedirectURLToAffectBrand(Role role) {
        return "redirect:/roles/page/1?sortField=id&sortDir=asc&keyword=" + role.getId();
    }

    @GetMapping("/roles/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            Role role = roleService.get(id);

            model.addAttribute("role", role);
            model.addAttribute("pageTitle", "Edit Role (ID: " + id + ")");

            return "role/role_form";
        } catch (RoleNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/roles";
        }
    }


    @GetMapping("/roles/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            roleService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Role by ID = " + id + " has been deleted");
        } catch (RoleNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/roles";
    }

    @GetMapping("/roles/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        List<Role> listRole = roleService.listAll();
        RoleCSVExporter exporter = new RoleCSVExporter();
        exporter.export(listRole, response);
    }
}
