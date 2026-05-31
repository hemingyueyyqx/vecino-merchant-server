package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductCategory;
import org.example.vecinomerchantserver.dto.ActivityConfigDTO;
import org.example.vecinomerchantserver.dto.AiCheckResultDTO;
import org.example.vecinomerchantserver.service.ActivityAiService;
import org.example.vecinomerchantserver.service.AdminService;
import org.example.vecinomerchantserver.service.CouponService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AdminController {
    private final AdminService adminService;
    private final ActivityAiService activityAiService;
    private final CouponService couponService;

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
    /**
     * AI 规则校验接口（前端AI组件调用）
     */
    @PostMapping("ai-check-rule")
    public ResultVo aiCheckRule(@RequestBody ActivityConfigDTO dto) {
        List<AiCheckResultDTO> aiCheckResultDTOS = activityAiService.checkActivityRule(dto);
        return ResultVo.success(aiCheckResultDTOS);
    }

    /**
     * 创建平台活动（主表单提交）
     */
    @PostMapping("create-activity")
    public ResultVo createActivity(@RequestBody ActivityConfigDTO dto) {
        couponService.addActivity(dto);
        return ResultVo.ok();
    }
}
