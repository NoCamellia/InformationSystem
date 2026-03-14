-- 插入管理员数据（密码：123456）
INSERT INTO `admin` (`id`, `username`, `password`, `real_name`, `email`, `phone`, `role_id`, `status`) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'Admin', 'admin@example.com', '13800138000', 1, 1),
(2, 'editor', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'Editor', 'editor@example.com', '13800138001', 2, 1);

-- 插入权限数据
INSERT INTO `permission` (`id`, `permission_name`, `permission_code`, `permission_type`, `parent_id`, `path`, `icon`, `sort_order`) VALUES
(1, 'Dashboard', 'dashboard', 1, 0, '/dashboard', 'dashboard', 1),
(2, 'Article', 'article', 1, 0, '/article', 'article', 2),
(3, 'Category', 'category', 1, 0, '/category', 'category', 3);

-- 插入角色权限关联
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2);

-- 插入分类数据
INSERT INTO `category` (`id`, `category_name`, `parent_id`, `level`, `sort_order`) VALUES
(1, 'News', 0, 1, 1),
(2, 'Domestic', 1, 2, 1),
(3, 'International', 1, 2, 2),
(5, 'Technology', 0, 1, 2),
(6, 'Internet', 5, 2, 1),
(7, 'AI', 5, 2, 2);

-- 插入标签数据
INSERT INTO `tag` (`id`, `tag_name`, `color`, `use_count`) VALUES
(1, 'Hot', '#ff4d4f', 15),
(2, 'Recommend', '#1890ff', 20),
(3, 'Original', '#52c41a', 10);

-- 插入测试用户
INSERT INTO `user` (`id`, `openid`, `nickname`, `avatar`, `gender`, `province`, `city`, `country`) VALUES
(1, 'oTest001', 'User1', 'https://thirdwx.qlogo.cn/mmopen/test1.jpg', 1, 'Guangdong', 'Shenzhen', 'China'),
(2, 'oTest002', 'User2', 'https://thirdwx.qlogo.cn/mmopen/test2.jpg', 2, 'Beijing', 'Beijing', 'China');

-- 插入文章数据
INSERT INTO `article` (`id`, `title`, `summary`, `cover_image`, `content`, `category_id`, `author_id`, `author_name`, `source`, `view_count`, `like_count`, `collect_count`, `share_count`, `is_top`, `is_hot`, `is_recommend`, `status`, `publish_time`) VALUES
(1, 'AI Technology Breakthrough', 'Latest research shows significant progress in multimodal AI models.', 'https://picsum.photos/800/450?random=1', '<p>Artificial intelligence is experiencing a profound transformation...</p>', 7, 1, 'Admin', 'Tech Daily', 1580, 89, 45, 23, 1, 1, 1, 1, '2026-03-10 09:00:00'),
(2, 'Economic Growth Steady', 'Latest data shows GDP growth of 5.3% in Q1.', 'https://picsum.photos/800/450?random=2', '<p>National Bureau of Statistics released data today...</p>', 2, 2, 'Editor', 'Economic News', 2340, 156, 78, 34, 1, 1, 1, 1, '2026-03-11 10:30:00'),
(3, 'New Energy Vehicle Sales Hit Record', 'NEV sales up 30% in first two months of 2026.', 'https://picsum.photos/800/450?random=3', '<p>China Association of Automobile Manufacturers data shows...</p>', 6, 2, 'Editor', 'Auto News', 1890, 112, 56, 28, 0, 1, 1, 1, '2026-03-12 14:20:00');

-- 插入文章标签关联
INSERT INTO `article_tag` (`article_id`, `tag_id`) VALUES
(1, 1), (1, 2),
(2, 1), (2, 2),
(3, 1), (3, 3);

-- 插入轮播图
INSERT INTO `banner` (`id`, `title`, `image_url`, `link_type`, `link_id`, `sort_order`) VALUES
(1, 'AI Technology Breakthrough', 'https://picsum.photos/1200/500?random=10', 1, 1, 1),
(2, 'Economic Growth Steady', 'https://picsum.photos/1200/500?random=11', 1, 2, 2),
(3, 'New Energy Vehicle Sales', 'https://picsum.photos/1200/500?random=12', 1, 3, 3);

-- 插入系统配置
INSERT INTO `system_config` (`config_key`, `config_value`, `config_desc`) VALUES
('site_name', 'Info Platform', 'Site Name'),
('wx_appid', 'wx1234567890abcdef', 'WeChat Mini Program AppID'),
('wx_secret', 'your_secret_key_here', 'WeChat Mini Program Secret');
