package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UploadController {
    @Value("${upload.local-path}")
    private String localPath;

    @Value("${upload.visit-prefix}")
    private String visitPrefix;

    /**
     * 营业执照图片上传接口
     * 前端请求：/api/upload
     * @param file 上传的图片文件
     * @return 图片可访问路径 imgPath
     */
    @PostMapping("upload")
    public ResultVo uploadFile(@RequestParam("file") MultipartFile file) {
        // 1. 判断文件是否为空
        if (file.isEmpty()) {
            return ResultVo.error(Code.FILE_NOT_EMPTY);
        }

        // 2. 获取原始文件名和后缀
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return ResultVo.error(Code.FILE_NAME_EMPTY);
        }
        // 获取文件后缀 .jpg .png
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 3. 校验文件格式（只允许图片）
        if (!suffix.equalsIgnoreCase(".jpg")
                && !suffix.equalsIgnoreCase(".png")
                && !suffix.equalsIgnoreCase(".jpeg")) {
            return ResultVo.error(Code.FILE_NOT_SUPPORT);
        }

        // 4. 生成唯一文件名（UUID防止重名覆盖）
        String uuidFileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        // 5. 创建存储目录（不存在则自动创建 E:\vecinopictures）
        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 6. 拼接文件完整保存路径
        File destFile = new File(dir, uuidFileName);

        try {
            // 7. 保存文件到 E 盘目录
            file.transferTo(destFile);

            // 8. 拼接可访问的图片 URL（返回给前端）
            // 格式：http://localhost:8080/pictures/xxx.jpg
            String imgPath = visitPrefix + uuidFileName;

            // 重点：返回 imgPath，前端自动赋值给 businessImage 字段
            return ResultVo.success(imgPath);
        } catch (IOException e) {
            log.error("图片上传失败", e);
            return ResultVo.error(Code.ERROR, "图片上传失败：" + e.getMessage());
        }
    }
}
