# 关键代码段解析（下-Part1）

## 6. 文章Controller MiniArticleController.java

```java
@RestController
@RequestMapping("/api/miniapp/article")
public class MiniArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<PageResult<Article>> getArticleList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        PageResult<Article> result = articleService.getArticleList(
                current, size, keyword, categoryId, 1);
        return Result.success(result);
    }

    // 获取详情同时浏览量+1
    @GetMapping("/detail/{id}")
    public Result<Article> getArticleDetail(@PathVariable Long id) {
        Article article = articleService.getArticleDetail(id);
        if (article != null) {
            articleService.incrementViewCount(id);
            return Result.success(article);
        }
        return Result.error("文章不存在");
    }

    @PostMapping("/like/{id}")
    public Result<Void> likeArticle(@PathVariable Long id) {
        boolean success = articleService.incrementLikeCount(id);
        return success ? Result.success() : Result.error("操作失败");
    }
}
```

**设计要点**：RESTful风格，URL代表资源，HTTP方法代表操作。GET查询不改变数据，POST修改数据。@PathVariable从URL路径取参数，@RequestParam从查询字符串取参数。

**可能的问题**：收藏有两个接口/article/collect和/collect/add，有何区别？

回答：/article/collect只增加文章的收藏计数，/collect/add才是真正记录用户收藏关系的接口，在user_collect表中插入记录。两个配合使用，既保证文章收藏数准确，又能追踪每个用户的收藏记录。

---

## 7. 用户收藏服务 UserCollectServiceImpl.java

```java
@Service
public class UserCollectServiceImpl 
        extends ServiceImpl<UserCollectMapper, UserCollect> 
        implements UserCollectService {

    @Override
    public boolean addCollect(Long userId, Long articleId) {
        if (isCollected(userId, articleId)) {
            return false;  // 已收藏直接返回false
        }
        UserCollect collect = new UserCollect();
        collect.setUserId(userId);
        collect.setArticleId(articleId);
        return this.save(collect);
    }

    @Override
    public boolean isCollected(Long userId, Long articleId) {
        LambdaQueryWrapper<UserCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollect::getUserId, userId)
               .eq(UserCollect::getArticleId, articleId);
        return this.count(wrapper) > 0;
    }

    @Override
    public boolean removeCollect(Long userId, Long articleId) {
        LambdaQueryWrapper<UserCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollect::getUserId, userId)
               .eq(UserCollect::getArticleId, articleId);
        return this.remove(wrapper);
    }
}
```

**设计要点**：addCollect先调用isCollected防止重复收藏，这是后端业务防护，即使前端没做限制，后端也能保证数据正确。

**可能的问题**：有两次数据库查询能否优化？

回答：可以给(user_id, article_id)加联合唯一索引，直接尝试插入，捕获唯一键冲突异常。只需一次数据库操作性能更好。但当前实现更直观，对毕业设计的数据量足够。
