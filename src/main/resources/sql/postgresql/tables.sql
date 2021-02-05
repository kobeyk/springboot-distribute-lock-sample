-- ----------------------------
-- Table structure for t_commodity_stock
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_commodity_stock";
CREATE TABLE "public"."t_commodity_stock" (
  "id" int8 NOT NULL,
  "commodity_code" varchar(255) COLLATE "pg_catalog"."default",
  "commodity_name" varchar(255) COLLATE "pg_catalog"."default",
  "inventory" int4,
  "version" int8,
  "create_time" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."t_commodity_stock"."commodity_code" IS '商品编码';
COMMENT ON COLUMN "public"."t_commodity_stock"."commodity_name" IS '商品名称';
COMMENT ON COLUMN "public"."t_commodity_stock"."inventory" IS '商品库存量';
COMMENT ON COLUMN "public"."t_commodity_stock"."version" IS '乐观锁，版本标记（当然也可以取更新时间的时间戳）';
COMMENT ON COLUMN "public"."t_commodity_stock"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."t_commodity_stock"."update_time" IS '修改数据';


-- Records of t_commodity_stock
-- ----------------------------
INSERT INTO "public"."t_commodity_stock" VALUES (1, 'CN100124512', 'Mac笔记本', 500, 0, '2020-10-26 16:36:31', '2020-10-28 15:27:08.434');

-- ----------------------------
-- Triggers structure for table t_commodity_stock
-- ----------------------------
CREATE TRIGGER "mt_modify" BEFORE UPDATE ON "public"."t_commodity_stock"
FOR EACH ROW
EXECUTE PROCEDURE "public"."mt_timestamp"();


-- ----------------------------
-- Primary Key structure for table t_commodity_stock
-- ----------------------------
ALTER TABLE "public"."t_commodity_stock" ADD CONSTRAINT "tb_commodity_stock_pkey" PRIMARY KEY ("id");

DROP TABLE IF EXISTS "public"."t_resource_lock";
CREATE TABLE "public"."t_resource_lock" (
    "id" int8 NOT NULL,
    "resource_name" varchar(255) COLLATE "pg_catalog"."default",
    "thread_name" varchar(255) COLLATE "pg_catalog"."default",
    "server_address" varchar(255) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."t_resource_lock"."resource_name" IS '资源名称';
COMMENT ON COLUMN "public"."t_resource_lock"."thread_name" IS '持有该资源的线程名称';
COMMENT ON COLUMN "public"."t_resource_lock"."server_address" IS '持有该资源的线程所在的服务地址';
COMMENT ON COLUMN "public"."t_resource_lock"."create_time" IS '创建资源锁的时间';

-- ----------------------------
-- Uniques structure for table t_resource_lock
-- ----------------------------
ALTER TABLE "public"."t_resource_lock" ADD CONSTRAINT "rName" UNIQUE ("resource_name");

-- ----------------------------
-- Primary Key structure for table t_resource_lock
-- ----------------------------
ALTER TABLE "public"."t_resource_lock" ADD CONSTRAINT "t_resource_lock_pkey" PRIMARY KEY ("id");
