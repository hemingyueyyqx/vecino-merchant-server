package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductCategory;
import org.example.vecinomerchantserver.service.AdminService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AdminController {
    private final AdminService adminService;
//     ============= 类目管理 =============
// 获取类目树形结构
    @GetMapping("admin/category/tree")
    public ResultVo getCategoryTree(@RequestAttribute("role") String role) {
        return adminService.getCategoryTree(role);
    }
    @PostMapping("admin/category/add")
    public ResultVo addCategory(@RequestBody ProductCategory category, @RequestAttribute("role") String role) {
        return adminService.addCategory(category, role);
    }

    @PutMapping("admin/category/edit")
    public ResultVo editCategory(@RequestBody ProductCategory category, @RequestAttribute("role") String role) {
        return adminService.editCategory(category, role);
    }

    @DeleteMapping("admin/category/delete")
    public ResultVo deleteCategory(@RequestParam String id, @RequestAttribute("role") String role) {
        return adminService.deleteCategory(id, role);
    }
}
