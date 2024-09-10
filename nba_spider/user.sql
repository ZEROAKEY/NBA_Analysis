-- 设置编码为 utf-8
PRAGMA encoding = "UTF-8";

-- ----------------------------
-- Table structure for user (SQLite)
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `rid` INTEGER NOT NULL,
  `username` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `mobile` TEXT,
  `email` TEXT
);

-- ----------------------------
-- Records of user (SQLite)
-- ----------------------------
INSERT INTO `user` VALUES (1, 0, 'ldy', '123456', '18111115000', '799@qq.com');
INSERT INTO `user` VALUES (6, 0, 'ldy3', '1234', '18108065260', '374728407@qq.com');
INSERT INTO `user` VALUES (20, 1, 'zjy', '123456', '17364733504', '374728407@qq.com');
INSERT INTO `user` VALUES (21, 0, 'newaccount222', '123456', '11111111111111111111', '799176874@qq.com');
