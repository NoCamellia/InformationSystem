package com.info.consultation.controller.admin;

import com.info.consultation.common.PageResult;
import com.info.consultation.common.Result;
import com.info.consultation.entity.Tag;
import com.info.consultation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public Result<PageResult<Tag>> getTagList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return Result.success(tagService.getTagPage(current, size, keyword));
    }

    @GetMapping("/detail/{id}")
    public Result<Tag> getTagDetail(@PathVariable Long id) {
        Tag tag = tagService.getById(id);
        return tag != null ? Result.success(tag) : Result.error("标签不存在");
    }

    @PostMapping("/add")
    public Result<Void> addTag(@RequestBody Tag tag) {
        boolean success = tagService.save(tag);
        return success ? Result.success() : Result.error("新增标签失败");
    }

    @PutMapping("/update")
    public Result<Void> updateTag(@RequestBody Tag tag) {
        boolean success = tagService.updateById(tag);
        return success ? Result.success() : Result.error("更新标签失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteTag(@PathVariable Long id) {
        boolean success = tagService.removeById(id);
        return success ? Result.success() : Result.error("删除标签失败");
    }
}
