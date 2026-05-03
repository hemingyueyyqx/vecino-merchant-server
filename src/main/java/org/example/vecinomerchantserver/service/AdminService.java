package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductCategory;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.repository.ProductRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final ProductRepository productRepository;
    // 获取类目树形结构
    @Transactional
    public ResultVo getCategoryTree(String role) {
        if(!role.equals(User.ADMIN)){
            return ResultVo.builder().code(Code.FORBIDDEN.getCode()).message(Code.FORBIDDEN.getMessage()).build();
        }
        List<ProductCategory> categories = productRepository.findAll();
        return ResultVo.success(categories);
    }
    @Transactional
    public ResultVo addCategory(ProductCategory category, String role) {
        if(!role.equals(User.ADMIN)){
            return ResultVo.builder().code(Code.FORBIDDEN.getCode()).message(Code.FORBIDDEN.getMessage()).build();
        }
        log.info("传进来的类目{}", category);

        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return ResultVo.error(400, "类目名称不能为空");
        }

        if (category.getLevel() == null || category.getLevel() < 1 || category.getLevel() > 2) {
            return ResultVo.error(400, "类目层级必须在1-2之间");
        }

        String parentId = category.getParentId();
        if (parentId == null || parentId.isEmpty()) {
            parentId = "0";
            category.setParentId(parentId);
        }

        if (category.getSort() == null) {
            category.setSort(0);
        }

        if (category.getStatus() == null) {
            category.setStatus(1);
        }
        ProductCategory productCategory = ProductCategory.builder()
                .categoryName(category.getCategoryName())
                .parentId(category.getParentId())
                .level(category.getLevel())
                .sort(category.getSort())
                .attrStandard(category.getAttrStandard())
                .status(category.getStatus())
                .build();

        productRepository.save(productCategory);
        log.info("新增类目成功: id={}, name={}", category.getCategoryName());

        return ResultVo.ok();
    }

    @Transactional
    public ResultVo editCategory(ProductCategory category, String role) {
        if(!role.equals(User.ADMIN)){
            return ResultVo.builder().code(Code.FORBIDDEN.getCode()).message(Code.FORBIDDEN.getMessage()).build();
        }

        if (category.getId() == null || category.getId().trim().isEmpty()) {
            return ResultVo.error(400, "类目ID不能为空");
        }

        Optional<ProductCategory> existingOpt = productRepository.findById(category.getId());
        if (existingOpt.isEmpty()) {
            return ResultVo.error(404, "类目不存在");
        }

        ProductCategory existing = existingOpt.get();

        if (category.getCategoryName() != null) {
            existing.setCategoryName(category.getCategoryName());
        }
        if (category.getParentId() != null) {
            existing.setParentId(category.getParentId());
        }
        if (category.getLevel() != null) {
            existing.setLevel(category.getLevel());
        }
        if (category.getSort() != null) {
            existing.setSort(category.getSort());
        }
        if (category.getAttrStandard() != null) {
            existing.setAttrStandard(category.getAttrStandard());
        }
        if (category.getStatus() != null) {
            existing.setStatus(category.getStatus());
        }

        productRepository.save(existing);
        log.info("编辑类目成功: id={}", category.getId());

        return ResultVo.ok();
    }

    @Transactional
    public ResultVo deleteCategory(String id, String role) {
        if(!role.equals(User.ADMIN)){
            return ResultVo.builder().code(Code.FORBIDDEN.getCode()).message(Code.FORBIDDEN.getMessage()).build();
        }

        if (id == null || id.trim().isEmpty()) {
            return ResultVo.error(400, "类目ID不能为空");
        }

        Optional<ProductCategory> existingOpt = productRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResultVo.error(404, "类目不存在");
        }

        List<ProductCategory> children = productRepository.findByParentId(id);
        if (!children.isEmpty()) {
            return ResultVo.error(400, "该类目下存在子类目，无法删除");
        }

        productRepository.deleteById(id);
        log.info("删除类目成功: id={}", id);

        return ResultVo.ok();
    }
}

